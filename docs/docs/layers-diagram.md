graph TD
%% DEFINICIÓN DE CAPAS

    subgraph UI ["User Interface Layer (com.gamezone.ui)"]
        Main["Main.java"]
        ConsoleMenu["ConsoleMenu"]
    end

    subgraph Service ["Service Layer (com.gamezone.service)"]
        ProductService["ProductService"]
        PersonService["PersonService"]
        SaleService["SaleService"]
    end

    subgraph Persistence ["Persistence Layer (com.gamezone.persistence)"]
        ProductRepository["ProductRepository"]
        PersonRepository["PersonRepository"]
        SaleRepository["SaleRepository"]
    end

    subgraph Model ["Model / Domain Layer (com.gamezone.model)"]
        Person["Person (Abstract)"]
        Customer["Customer"]
        Salesperson["Salesperson"]
        Product["Product (Abstract)"]
        VideoGame["VideoGame"]
        Console["Console"]
        Sale["Sale"]
    end

    %% DEPENDENCIAS PERMITIDAS ENTRE CAPAS

    %% UI -> Service
    ConsoleMenu --> ProductService
    ConsoleMenu --> PersonService
    ConsoleMenu --> SaleService
    Main --> ConsoleMenu

    %% Service -> Persistence
    ProductService --> ProductRepository
    PersonService --> PersonRepository
    SaleService --> SaleRepository

    %% Service -> Service (Inter-service coordination)
    SaleService --> ProductService
    SaleService --> PersonService

    %% Service -> Model
    ProductService --> Product
    PersonService --> Person
    SaleService --> Sale

    %% Persistence -> Model
    ProductRepository --> Product
    PersonRepository --> Person
    SaleRepository --> Sale

    %% REGALAS Y RESTRICCIONES REFORZADAS EN ESTILOS (PROHIBICIONES)
    %% - Model no depende de ninguna capa.
    %% - UI no accede directamente a Persistence.