import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalculateOperations {
    private static double averagePrice = 0;
    private static int quantityTotal = 0;
    private static double balance = 0;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            List<JsonObject> operations = getOperationsFromInput(scanner);

            if (operations == null) {
                System.out.println("Saindo...");
                break;
            }
            CalculateOperations.reset();
            List<JsonObject> result = processOperations(operations);
            System.out.println("Resultado: " + result);
        }

        scanner.close();
    }

    protected static List<JsonObject> getOperationsFromInput(Scanner scanner) {
        System.out.println("Digite a lista de operações (ou 'exit' para sair):");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("exit")) {
            return null;
        }

        Gson gson = new GsonBuilder().setLenient().create();
        Type listType = new TypeToken<List<JsonObject>>() {
        }.getType();
        return gson.fromJson(input, listType);
    }

    protected static List<JsonObject> processOperations(List<JsonObject> operations) {
        List<JsonObject> result = new ArrayList<>();

        for (JsonObject jsonObject : operations) {
            validateParams(jsonObject);
            String action = jsonObject.get("operation").getAsString();
            double unitCost = jsonObject.get("unit-cost").getAsDouble();
            int quantity = jsonObject.get("quantity").getAsInt();

            if (action.equalsIgnoreCase("buy")) {
                handleBuyOperation(result, unitCost, quantity);
            } else if (action.equalsIgnoreCase("sell")) {
                handleSellOperation(result, unitCost, quantity);
            }
        }
        return result;
    }

    private static void handleBuyOperation(List<JsonObject> result, double unitCost, int quantity) {
        averagePrice = (averagePrice == 0) ? unitCost :
                ((quantityTotal * averagePrice) + (quantity * unitCost)) / (quantityTotal + quantity);
        quantityTotal += quantity;

        JsonObject obj = new JsonObject();
        obj.addProperty("tax", String.format("%.2f", 0d));
        result.add(obj);
    }

    private static void handleSellOperation(List<JsonObject> result, double unitCost, int quantity) {
        JsonObject obj = new JsonObject();
        obj.addProperty("tax", String.format("%.2f", 0d));

        if (quantity > quantityTotal) {
            System.out.println("Não pode vender mais ações que possui");
            return;
        }

        double totalOperation = unitCost * quantity;
        quantityTotal -= quantity;

        double profit = (unitCost * quantity) - (averagePrice * quantity);


        if (totalOperation > 20000 && profit > 0) {
            obj.addProperty("tax", String.format("%.2f", calculateTax(profit)));
        } else {
            if (totalOperation > 20000 || profit < 0) {
                calculateBalance(profit);
            }
        }

        result.add(obj);
    }

    private static double calculateTax(double profit) {
        if (balance < 0) {
            double adjustedProfit = profit + balance;
            calculateBalance(profit);
            if (adjustedProfit < 0) {
                return 0;
            }
            return adjustedProfit * 0.2;
        }
        calculateBalance(profit);
        return profit * 0.2;
    }

    private static void calculateBalance(Double profit) {
        balance += profit;

        if (balance > 0) {
            balance = 0;
        }
    }

    private static void validateParams(JsonObject jsonObject) {
        if (!jsonObject.has("operation") || !jsonObject.has("unit-cost") || !jsonObject.has("quantity")) {
            throw new IllegalArgumentException("Parâmetros inválidos na operação: " + jsonObject);
        }
    }

    public static void reset() {
        averagePrice = 0;
        quantityTotal = 0;
        balance = 0;
    }
}
