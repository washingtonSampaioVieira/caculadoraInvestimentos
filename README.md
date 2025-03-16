## Decisões técnicas e arquiteturais do seu desafio

No que diz respeito às decisões arquiteturais, optei pela abordagem mais simples possível para resolver o problema,
utilizando apenas lógica de programação em um único arquivo, sem recorrer a muitos recursos da linguagem. No material "
Seu guia para o nosso processo seletivo", foi dada grande ênfase ao princípio de "não adicionar mais complexidade do que
o necessário". Sendo assim, busquei incluir no projeto apenas o essencial para solucionar o desafio, evitando a
implementação de padrões de design desnecessários ou a divisão da solução em múltiplos arquivos ou pacotes.

## Uso de frameworks ou bibliotecas

No projeto, foram importadas apenas algumas bibliotecas para facilitar a escrita de testes de unidade e a conversão de
JSON. Para isso, utilizei as seguintes bibliotecas:

- **Gson 2.9.0**: Para manipulação e conversão de JSON.
- **JUnit 5**:
    - `junit-jupiter-api-5.12.1`
    - `junit-jupiter-engine-5.12.1`
    - `junit-jupiter-params-5.12.1`
    - `junit-platform-console-standalone-1.12.1`

Essas ferramentas foram escolhidas por sua eficiência e simplicidade, alinhando-se ao objetivo de manter o projeto
enxuto e funcional.

## Como Executar o Projeto

O projeto foi desenvolvido em **Java 17** e contém um arquivo principal chamado `CalculateOperations`, que inclui o
método `main`. Abaixo estão as instruções para executá-lo:

### Pré-requisitos

- Certifique-se de ter o **Java 17** instalado em sua máquina.
- Verifique se as bibliotecas necessárias estão na pasta `/jars`.

### Passos para Execução

- O projeto já foi compilado, use o comando abaixo para execução

```bash
java -jar .\build\artifacts\Calculo_investimentos_jar\Calculo_investimentos.jar
```
Caso seja necessario compilar o projeto novamente para debug ou manutenção, use a pasta `jars` para importar todas as bibliotecas que o projeto precisa antes da execução.

## Como executar os testes da solução
O arquivo Massa de testes.txt contém vários modelos de dados de entrada e saída que podem ser usados para validar a solução.

Para testar manualmente:
1. Copie os dados de entrada do arquivo Massa de testes.txt.
2. Cole os dados no terminal.
3. Compare a saída gerada pelo programa com a saída esperada, que também está descrita no arquivo Massa de testes.txt.

