import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CalculateOperationsTest {

    Gson gson = new GsonBuilder().setLenient().create();

    @BeforeEach
    void resetStaticVariables() {
        CalculateOperations.reset();
    }

    @ParameterizedTest
    @CsvSource({
            "'[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}]', '[{\"tax\":\"0,00\"},{\"tax\":\"10000,00\"}]'",
            "'[{\"operation\":\"buy\", \"unit-cost\":20.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":10.00, \"quantity\": 5000}]', '[{\"tax\":\"0,00\"},{\"tax\":\"0,00\"}]'",
            "'[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100},{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50},{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}]', '[{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"0,00\"}]'",
            "'[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000},{\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\": 5000}]', '[{\"tax\":\"0,00\"},{\"tax\":\"10000,00\"},{\"tax\":\"0,00\"}]'",
            "'[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100},{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50},{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}]', '[{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"0,00\"}]'",
            "'[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000},{\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\": 5000}]', '[{\"tax\":\"0,00\"},{\"tax\":\"10000,00\"},{\"tax\":\"0,00\"}]'",
            "'[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\": 5000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 3000}]', '[{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"1000,00\"}]'",
            "'[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"buy\", \"unit-cost\":25.00, \"quantity\": 5000},{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 10000}]', '[{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"0,00\"}]'",
            "'[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"buy\", \"unit-cost\":25.00, \"quantity\": 5000},{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":25.00, \"quantity\": 5000}]', '[{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"10000,00\"}]'",
            "'[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":2.00, \"quantity\": 5000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 2000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 2000},{\"operation\":\"sell\", \"unit-cost\":25.00, \"quantity\": 1000}]', '[{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"3000,00\"}]'",
            "'[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":2.00, \"quantity\": 5000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 2000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 2000},{\"operation\":\"sell\", \"unit-cost\":25.00, \"quantity\": 1000},{\"operation\":\"buy\", \"unit-cost\":20.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 5000},{\"operation\":\"sell\", \"unit-cost\":30.00, \"quantity\": 4350},{\"operation\":\"sell\", \"unit-cost\":30.00, \"quantity\": 650}]', '[{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"3000,00\"},{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"3700,00\"},{\"tax\":\"0,00\"}]'",
            "'[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":50.00, \"quantity\": 10000},{\"operation\":\"buy\", \"unit-cost\":20.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":50.00, \"quantity\": 10000}]', '[{\"tax\":\"0,00\"},{\"tax\":\"80000,00\"},{\"tax\":\"0,00\"},{\"tax\":\"60000,00\"}]'",
            "'[{\"operation\": \"buy\", \"unit-cost\": 5000.00, \"quantity\": 10},{\"operation\": \"sell\", \"unit-cost\": 4000.00, \"quantity\": 5},{\"operation\": \"buy\", \"unit-cost\": 15000.00, \"quantity\": 5},{\"operation\": \"buy\", \"unit-cost\": 4000.00, \"quantity\": 2},{\"operation\": \"buy\", \"unit-cost\": 23000.00, \"quantity\": 2},{\"operation\": \"sell\", \"unit-cost\": 20000.00, \"quantity\": 1},{\"operation\": \"sell\", \"unit-cost\": 12000.00, \"quantity\": 10},{\"operation\": \"sell\", \"unit-cost\": 15000.00, \"quantity\": 3}]', '[{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"0,00\"},{\"tax\":\"1000,00\"},{\"tax\":\"2400,00\"}]'"
    })
    void calculateOperationsTest(String jsonInput, String jsonExpectedOutput) {
        Type listType = new TypeToken<List<JsonObject>>() {
        }.getType();

        // Processa as operações
        List<JsonObject> jsonObjects = CalculateOperations.processOperations(gson.fromJson(jsonInput, listType));

        assertNotNull(jsonObjects);

        // Transforma a saída processada em JSON para comparar corretamente
        String jsonResult = gson.toJson(jsonObjects);

        assertEquals(jsonExpectedOutput, jsonResult);
    }

    @Test
    void testGetOperationsFromInput() {
        String inputJson = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100}]";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputJson.getBytes()));

        List<JsonObject> operations = CalculateOperations.getOperationsFromInput(scanner);

        assertNotNull(operations);
        assertEquals(1, operations.size());
        assertEquals("buy", operations.get(0).get("operation").getAsString());
    }


    @Test
    void testGetOperationExitFromInput() {
        String inputJson = "exit";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputJson.getBytes()));

        List<JsonObject> operations = CalculateOperations.getOperationsFromInput(scanner);

        assertNull(operations);
    }

}
