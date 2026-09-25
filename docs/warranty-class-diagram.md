# Warranty Module Class Diagram

## Updated Class Diagram

```mermaid
classDiagram

    class Warranty {
        <<abstract>>
        -String id
        -Product product
        -Sale sale
        -LocalDate startDate
        -LocalDate endDate
        +Warranty(String id, Product product, Sale sale, LocalDate startDate)
        +String getId()
        +Product getProduct()
        +Sale getSale()
        +LocalDate getStartDate()
        +LocalDate getEndDate()
        +int getDurationInMonths()
        +String getWarrantyType()
        +double getAdditionalCost()
        +boolean isActive(LocalDate date)
        +String generateWarrantyCertificate()
    }

    class BasicWarranty {
        +BasicWarranty(String id, Product product, Sale sale, LocalDate startDate)
        +int getDurationInMonths()
        +String getWarrantyType()
        +double getAdditionalCost()
    }

    class ExtendedWarranty {
        +ExtendedWarranty(String id, Product product, Sale sale, LocalDate startDate)
        +int getDurationInMonths()
        +String getWarrantyType()
        +double getAdditionalCost()
    }

    class Product {
        <<abstract>>
        -String id
        -String title
        -double price
        -int stockQuantity
    }

    class Console {
        -String brand
        -String model
        -String generation
    }

    class Sale {
        -String id
        -String date
        -Client client
        -Seller seller
        -List~Product~ products
        -double totalAmount
        -String appliedPromotionName
        -double discountAmount
        -double extendedWarrantyCost
        +boolean canBeReturned()
        +double calculateTotal()
        +double calculateFinalTotal()
        +String generateReceipt()
    }

    class WarrantyRepository {
        -SaleService saleService
        -ProductService productService
        +void saveAll(List~Warranty~ warranties)
        +List~Warranty~ loadAll()
    }

    class WarrantyService {
        -WarrantyRepository warrantyRepository
        -List~Warranty~ warranties
        +BasicWarranty assignBasicWarranty(Product product, Sale sale, LocalDate startDate)
        +ExtendedWarranty assignExtendedWarranty(Product product, Sale sale, LocalDate startDate)
        +Warranty findWarrantyByProduct(String productId, String saleId)
        +List~Warranty~ listAllWarranties()
        +List~Warranty~ listActiveWarranties()
        +List~Warranty~ listWarrantiesExpiringSoon(int daysAhead)
    }

    class SaleService {
        -WarrantyService warrantyService
        +Sale registerSale(String id, String date, String clientId, String sellerId, List~Product~ products, List~String~ productIdsWithExtendedWarranty)
    }

    class ProductService {
        +Product findProduct(String id)
        +void updateStock(String productId, int quantity)
    }

    class ConsoleMenu {
        -WarrantyService warrantyService
        +void showWarrantyMenu()
        +void handleWarrantyMenu()
    }

    Warranty <|-- BasicWarranty
    Warranty <|-- ExtendedWarranty

    Product <|-- Console

    Warranty --> Product : covers
    Warranty --> Sale : belongs to

    Sale --> Product : contains

    WarrantyService --> WarrantyRepository : persists warranties
    WarrantyService --> Sale : associates warranty
    WarrantyService --> Product : assigns coverage

    WarrantyRepository --> SaleService : resolves sales
    WarrantyRepository --> ProductService : resolves products

    SaleService --> WarrantyService : assigns warranties
    SaleService --> ProductService : manages stock

    ConsoleMenu --> WarrantyService : delegates warranty operations
```


## Layer Integration

- `Warranty`, `BasicWarranty`, and `ExtendedWarranty` belong to the model layer.
- `WarrantyRepository` belongs to the persistence layer.
- `WarrantyService` belongs to the service layer.
- `ConsoleMenu` belongs to the ui layer.
- `SaleService` integrates warranty assignment into the existing sales flow.
- `WarrantyService` coordinates warranty assignment and warranty queries.
- `WarrantyRepository` is responsible for persistence in `data/warranties.csv`.