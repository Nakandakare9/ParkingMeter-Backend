# ParkingMeter-Backend

O ParkingMeter é um sistema de gerência de parquimetros que permite o registro e gerenciamento de parquimetros, motoriastas, veículos, métodos de pagamento, validação de estacionamento e sistema de alertas.
Essa documentação tem como objetivo apresentar o projeto, as requisições e modelo de dados utilizados. 

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

## Modelo de Dados
![image](https://github.com/user-attachments/assets/47633dc8-d0a3-42cd-9973-deb4698c1e71)


## Estrutura do Projeto
![image](https://github.com/user-attachments/assets/736252c5-50c4-43a7-8784-148f75cdb035)

## Como Executar
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


## Recursos

### Motoristas
- Registrar Motorista: POST /drivers
    Request
  ```json
    {
      "cpfDriver": "379.497.818-82",
      "nameDriver": "Soriano de Albuquerque",
      "cellphoneDriver": "(11) 98765-4321",
      "emailDriver": "soriano9@example.com",
      "streetAddressDriver": "Rua Camila Lima, 102",
      "neighborhoodAddressDriver": "Centro",
      "cityAddressDriver": "Sao Paulo"
    }
- Consultar Motoristas: GET /cpf/{driverCpf}
- Relatório de Motoristas: GET /cpf/
- Editar Motorista: PUT /drivers/{driverCpf}
    Request
  ```json
    {
      "cpfDriver": "379.497.818-82",
      "nameDriver": "Soriano de Albuquerque",
      "cellphoneDriver": "(13) 98765-4321",
      "emailDriver": "soriano9@example.com",
      "streetAddressDriver": "Rua Camila Lima, 102",
      "neighborhoodAddressDriver": "Centro",
      "cityAddressDriver": "Sao Paulo"
    }
- Exclui Motorista: DELETE /drivers/{driverCpf}

### Veículos
- Registrar Veículo: POST /vehicles
    Request
  ```json
    {
      "driver": {
        "cpfDriver": "379.497.818-82"
      },
      "driverVehicleLicensePlate": "ABC-1234",
      "driverVehicleBrand": "Toyota",
      "driverVehicleModel": "Corolla",
      "yearOfManufactureDriverVehicle": "2020",
      "modelYearVehicleDriver": "2021"
    }
- Consultar Veículo por motorista e placa: GET /vehicles/cpfplate/{driverCpf}/{driverVehicleLicensePlate}
- Consultar Veículo por placa: GET /vehicles/plate/{driverVehicleLicensePlate}
- Relatório de Veículos de um Motorista: GET /vehicles/driver/{driverCpf}
- Editar Veículo: PUT /vehicles/{driverCpf}/{driverVehicleLicensePlate}
    Request
  ```json
    {
        "id": {
            "cpfDriver": {
                "cpfDriver": "379.497.818-82"
            },
            "driverVehicleLicensePlate": "ABC-1234"
        },
        "driver": {
            "cpfDriver": {
                "cpfDriver": "379.497.818-82"
            },
            "nameDriver": "Soriano de Albuquerque",
            "cellphoneDriver": "(13) 98765-5555",
            "emailDriver": "soriano9@example.com",
            "streetAddressDriver": "Rua Camila Lima, 102",
            "neighborhoodAddressDriver": "Centro",
            "cityAddressDriver": "Sao Paulo"
        },
        "driverVehicleLicensePlate": "ABC-1234",
        "driverVehicleBrand": "Toyota",
        "driverVehicleModel": "Corolla",
        "yearOfManufactureDriverVehicle": "2020",
        "modelYearVehicleDriver": "2021"
    }
- Excluir Veículo: DELETE /vehicles/{driverCpf}/{driverVehicleLicensePlate}

### Parquimetro
- Registrar Parquimetro: POST /parkingmeters
    Request
  ```json
        {
      "cnpjParking": "12.345.678/0001-95",
      "name": "Parking Ltda",
      "telephoneParking": "987654321",
      "emailParking": "contact@parkinglotexample.com",
      "streetAddressParking": "123 Main Street",
      "neighborhoodAddressParking": "Downtown",
      "cityAddressParking": "Sao Paulo",
      "hourlyParkingFee": 10.50
    }
- Consultar Parquimetro: GET /parkingmeters/id/{parkingIdentifierCode}
- Relatório de Parquimetros: GET /parkingmeters/
- Editar Parquimetro: PUT /parkingmeters/{parkingIdentifierCode}
    Request
  ```json
        {
      "cnpjParking": "12.345.678/0001-95",
      "name": "Parking Ltda 2",
      "telephoneParking": "987654321",
      "emailParking": "contact@parkinglotexample.com",
      "streetAddressParking": "123 Main Street",
      "neighborhoodAddressParking": "Downtown",
      "cityAddressParking": "Sao Paulo",
      "hourlyParkingFee": 10.50
    }
- Excluir Parquimetro: DELETE /parkingmeters/{parkingIdentifierCode}

### Métodos de Pagamento
- Registrar Método de Pagamento: POST /paymentmethods
    Request
  ```json
        {
      "driver": {
        "cpfDriver": "379.497.818-82"
      },
      "paymentMethodTypeCode": 1,
      "paymentMethodCardNumber": "5555555555554444",
      "paymentMethodCardholderName": "SORIANO ALBUQUERQUE",
      "preferredPaymentMethodIndicator": "Y"
    }
- Buscar Métodos de Pagamento especifico: GET /paymentmethods/cpfsequence/{driverCpf}/{parkingIdentifierCode}
- Buscar todos os Métodos Pagamento do Motorista: GET /paymentmethods/driver/{driverCpf}
- Relatório de Métodos de Pagamento: GET /paymentmethods/
- Editar Método de Pagamento: PUT /paymentmethods/{driverCpf}/{parkingIdentifierCode}
    Request
  ```json
        {
      "driver": {
        "cpfDriver": "379.497.818-82"
      },
      "paymentMethodTypeCode": 1,
      "paymentMethodCardNumber": "5555555555554444",
      "paymentMethodCardholderName": "SORIANO ALBUQUERQUE 2",
      "preferredPaymentMethodIndicator": "cartao2"
    }
- Excluir Método de Pagamento: DELETE /{driverCpf}/{parkingIdentifierCode}

### Transações de Estacionamento
- Registrar Transação de Estacionamento: POST /parking-transaction
    Request
  ```json
        {
      "parking": {
        "parkingIdentifierCode": 1
      },
        "driverVehicle": {
            "id": {
                "cpfDriver": {
                    "cpfDriver": "379.497.818-82"
                },
                "driverVehicleLicensePlate": "DIH-1234"
            }
        },
    "timeOptionCode": 0,
    "desiredParkingDuration": 120
    }

### Validação de Estacionamento
- Validar Estacionamento: Método agendado @Scheduled que valida o tempo de estacionamento a cada 60 segundos.

## Testes Unitários
Os testes unitários utilizam Mockito e JUnit e estão localizados no diretório src/test/java/com/fiap/parkingMeter.

## Documentação Swagger
Com a aplicação rodando: http://localhost:8080/swagger-ui/index.html#/

## Pré-Requisitos
- Java 17
- Maven 3.6+

## Console H2
Com a aplicação rodando: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:file:~/parkingMeter
USERNAME=sa
PASSWORD=

## Licença
Este projeto é licenciado sob a Licença MIT.

## Contato
Camila Marques de Lima - cml.isa.17@gmail.com
Eduardo Bento Nakandakare - nakandakare9@gmail.com
