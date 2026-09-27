# GameZone Unicesar – Integrated Class Diagram

## Overview

The following diagram represents the integrated GameZone Unicesar architecture after the A1–A7 adjustments.

The diagram combines the main application layers and the integrated domain modules:

- Person management
- Product management
- Accessory management
- Promotion management
- Sale management
- Return management
- Warranty management

The architecture follows the dependency direction:

`UI → Service → Persistence → Model`

The model layer does not depend on the persistence layer.

## Integrated Class Diagram

```mermaid
classDiagram

%% =========================
%% UI LAYER
%% =========================

namespace UI {
    class Main
    class ConsoleMenu
}

%% =========================
%% SERVICE LAYER
%% =========================

namespace Service {
    class PersonService
    class ProductService
    class AccessoryService
    class PromotionService
    class SaleService
    class ReturnService
    class WarrantyService
}

%% =========================
%% PERSISTENCE LAYER
%% =========================

namespace Persistence {
    class PersonRepository
    class ProductRepository
    class AccessoryRepository
    class PromotionRepository
    class SaleRepository
    class ReturnRepository
    class WarrantyRepository
}

%% =========================
%% MODEL LAYER
%% =========================

namespace Model {

    class Person {
        <<abstract>>
        -id
        -name
        -phone
        +getRoleDescription()
    }

    class Client {
        -email
        -purchaseHistory
    }

    class Seller {
        -employeeId
        -workShift
    }

    class Product {
        <<abstract>>
        -id
        -title
        -price
        -stockQuantity
        +getDescription()
    }

    class VideoGame {
        -platform
        -genre
        -classification
    }

    class Console {
        -brand
        -model
        -generation
    }

    class Accessory {
        -compatibility
    }

    class Controller
    class Cable
    class Memory

    class Promotion {
        <<abstract>>
        -id
        -name
        -validFrom
        -validUntil
    }

    class PercentageDiscount
    class CategoryDiscount
    class BulkPurchaseDiscount

    class Sale {
        -id
        -date
        -totalAmount
        +generateReceipt()
    }

    class Return {
        -id
        -date
        -refundAmount
        +calculateRefundAmount()
        +generateReturnReceipt()
        +canBeReturned()
    }

    class Warranty {
        <<abstract>>
        -id
        -startDate
        -endDate
    }

    class BasicWarranty
    class ExtendedWarranty
}

%% =========================
%% MODEL INHERITANCE
%% =========================

Person <|-- Client
Person <|-- Seller

Product <|-- VideoGame
Product <|-- Console
Product <|-- Accessory

Accessory <|-- Controller
Accessory <|-- Cable
Accessory <|-- Memory

Promotion <|-- PercentageDiscount
Promotion <|-- CategoryDiscount
Promotion <|-- BulkPurchaseDiscount

Warranty <|-- BasicWarranty
Warranty <|-- ExtendedWarranty

%% =========================
%% DOMAIN RELATIONSHIPS
%% =========================

Client "1" --> "0..*" Sale : purchases
Seller "1" --> "0..*" Sale : registers

Sale "1" --> "1..*" Product : contains
Sale "0..*" --> "0..1" Promotion : applies
Sale "1" --> "0..*" Warranty : includes

Return "0..*" --> "1" Sale : refers to
Return "0..*" --> "1" Product : returns
Return "0..*" --> "0..*" Warranty : cancels

%% =========================
%% UI DEPENDENCIES
%% =========================

Main --> ConsoleMenu : starts

ConsoleMenu --> PersonService
ConsoleMenu --> ProductService
ConsoleMenu --> AccessoryService
ConsoleMenu --> PromotionService
ConsoleMenu --> SaleService
ConsoleMenu --> ReturnService
ConsoleMenu --> WarrantyService

%% =========================
%% SERVICE DEPENDENCIES
%% =========================

PersonService --> PersonRepository
PersonService --> Person

ProductService --> ProductRepository
ProductService --> Product

AccessoryService --> AccessoryRepository
AccessoryService --> Accessory

PromotionService --> PromotionRepository
PromotionService --> Promotion

SaleService --> SaleRepository
SaleService --> Sale

ReturnService --> ReturnRepository
ReturnService --> Return

WarrantyService --> WarrantyRepository
WarrantyService --> Warranty

%% =========================
%% CROSS-SERVICE INTEGRATION
%% =========================

SaleService --> ProductService : resolves products
SaleService --> PromotionService : applies promotion
SaleService --> WarrantyService : manages warranties

ReturnService --> SaleService : validates sale
ReturnService --> AccessoryService : restores accessory stock
ReturnService --> WarrantyService : cancels warranties

%% =========================
%% PERSISTENCE DEPENDENCIES
%% =========================

PersonRepository --> Person
ProductRepository --> Product
AccessoryRepository --> Accessory
PromotionRepository --> Promotion
SaleRepository --> Sale
ReturnRepository --> Return
WarrantyRepository --> Warranty