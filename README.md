# ParkingMeter-Backend

O ParkingMeter é um sistema de gerência de parquimetros que permite o registro e gerenciamento de parquimetros, motoriastas, veículos, métodos de pagamento, validação de estacionamento e sistema de alertas.

## Funcionalidades

- Registrar motoristas
- Registrar veículos
- Registar Parquimetros
- Gerenciar métodos de pagamento
- Validar tempo e alertas de estacionamento

## Tecnologias Utilizadas

- Java
- Spring Boot
- Jakarta Persistence (JPA)
- Mockito e JUnit para testes unitários

## Estrutura do Projeto

```plaintext
.
└── src
    ├── main
    │   ├── java
    │   │   └── com.fiap.parkingMeter
    │   │       ├── controller
    │   │       ├── domain
    │   │       ├── repository
    │   │       ├── service
    │   │       └── job
    │   └── resources
    └── test
        └── java
            └── com.fiap.parkingMeter
                ├── controller
                ├── service
                └── job
Como Executar
Clone o repositório:

bash
git clone https://github.com/Nakandakare9/ParkingMeter-Backend.git
Navegue até o diretório do projeto:

bash
cd parkingMeter
Compile e execute o projeto utilizando Maven:

bash
mvn clean install
mvn spring-boot:run

Endpoints

Motoristas
Registrar Motorista: POST /drivers
Consultar Motoristas: GET /cpf/{driverCpf}
Relatório de Motoristas: GET /cpf/
Editar Motorista: PUT /drivers/{driverCpf}
Exclui Motorista: DELETE /drivers/{driverCpf}

Veículos
Registrar Veículo: POST /vehicles
Consultar Veículo por motorista e placa: GET /vehicles/cpfplate/{driverCpf}/{driverVehicleLicensePlate}
Consultar Veículo por placa: GET /vehicles/plate/{driverVehicleLicensePlate}
Relatório de Veículos de um Motorista: GET /vehicles/driver/{driverCpf}
Editar Veículo: PUT /vehicles/{driverCpf}/{driverVehicleLicensePlate}
Excluir Veículo: DELETE /vehicles/{driverCpf}/{driverVehicleLicensePlate}

Parquimetro
Registrar Parquimetro: POST /parkingmeters
Consultar Parquimetro: GET /parkingmeters/id/{parkingIdentifierCode}
Relatório de Parquimetros: GET /parkingmeters/
Editar Parquimetro: PUT /parkingmeters/{parkingIdentifierCode}
Excluir Parquimetro: DELETE /parkingmeters/{parkingIdentifierCode}

Métodos de Pagamento
Registrar Método de Pagamento: POST /paymentmethods
Buscar Métodos de Pagamento especifico: GET /paymentmethods/cpfsequence/{driverCpf}/{parkingIdentifierCode}
Buscar todos os Métodos Pagamento do Motorista: GET /paymentmethods/driver/{driverCpf}
Relatório de Métodos de Pagamento: GET /paymentmethods/
Editar Método de Pagamento: PUT /paymentmethods/{driverCpf}/{parkingIdentifierCode}
Excluir Método de Pagamento: DELETE /{driverCpf}/{parkingIdentifierCode}

Transações de Estacionamento
Registrar Transação de Estacionamento: POST /parking-transaction

Validação de Estacionamento
Validar Estacionamento: Método agendado @Scheduled que valida o tempo de estacionamento a cada 60 segundos.

Testes Unitários
Os testes unitários utilizam Mockito e JUnit e estão localizados no diretório src/test/java/com/fiap/parkingMeter.

Licença
Este projeto é licenciado sob a Licença MIT.

Contato
Camila Marques de Lima - cml.isa.17@gmail.com
Eduardo Bento Nakandakare - nakandakare9@gmail.com
