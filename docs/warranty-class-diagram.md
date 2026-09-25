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
        +void saveAll(List~Warranty~ warranties)
        +List~WarrantyRecord~ loadAll()
    }

    class WarrantyRecord {
        -String type
        -String id
        -String saleId
        -String productId
        -LocalDate startDate
    }

    class WarrantyService {
        -WarrantyRepository warrantyRepository
        -SaleRepository saleRepository
        -ProductService productService
        -List~Warranty~ warranties
        +BasicWarranty assignBasicWarranty(Product product, Sale sale, LocalDate startDate)
        +ExtendedWarranty assignExtendedWarranty(Product product, Sale sale, LocalDate startDate)
        +Warranty findWarrantyByProduct(String productId, String saleId)
        +List~Warranty~ listAllWarranties()
        +List~Warranty~ listActiveWarranties()
        +List~Warranty~ listWarrantiesExpiringSoon(int daysAhead)
    }

    class SaleRepository {
        +List~Sale~ findAll()
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

    WarrantyRepository --> WarrantyRecord : loads / saves

    WarrantyService --> WarrantyRepository : persists warranties
    WarrantyService --> SaleRepository : resolves sale by id
    WarrantyService --> ProductService : resolves product by id
    WarrantyService --> Sale : associates warranty
    WarrantyService --> Product : assigns coverage

    SaleService --> WarrantyService : assigns warranties
    SaleService --> ProductService : manages stock

    ConsoleMenu --> WarrantyService : delegates warranty operations
```


## Layer Integration

- `Warranty`, `BasicWarranty`, and `ExtendedWarranty` belong to the model layer.
- `WarrantyRepository` belongs to the persistence layer and has no dependency on any service; it only persists and loads raw `WarrantyRecord` identifiers (sale id, product id).
- `WarrantyService` belongs to the service layer. It depends on `WarrantyRepository`, `SaleRepository`, and `ProductService`, resolving `WarrantyRecord` entries into real `Warranty` objects by looking up the referenced `Sale` and `Product`.
- `ConsoleMenu` belongs to the ui layer.
- `SaleService` integrates warranty assignment into the existing sales flow, receiving `WarrantyService` through constructor injection.
- `WarrantyService` coordinates warranty assignment and warranty queries.
- `WarrantyRepository` is responsible for persistence in `data/warranties.csv`.

## Fix A2: Removing the Circular Dependency

Before this fix, the object graph formed a cycle: `SaleService → WarrantyService → WarrantyRepository → SaleService`, since `WarrantyRepository` needed `SaleService` to resolve sale references when loading warranties from disk, while `SaleService` needed `WarrantyService` to assign warranties during sale registration. This cycle made pure constructor injection impossible in `Main`, forcing a setter-based workaround.

The fix breaks the cycle by changing what `WarrantyRepository` stores and who resolves references:

- `WarrantyRepository` no longer resolves anything; it just reads/writes the raw `saleId` and `productId` of each warranty as a `WarrantyRecord`.
- `WarrantyService` takes over reference resolution. It receives `SaleRepository` (not `SaleService`) and `ProductService` by constructor, and reconstructs `Warranty` objects from the loaded `WarrantyRecord` list.
- `SaleService` now receives `WarrantyService` directly through its constructor instead of a `setWarrantyService` setter.
- In `Main`, `WarrantyRepository` and `WarrantyService` are now built **before** `SaleService`, since neither depends on it anymore. `SaleService` is built last, receiving the already-constructed `WarrantyService`.

This removes the cycle entirely: `WarrantyService` now depends on `SaleRepository`, a class with no dependency on `SaleService` or `WarrantyService`, so the whole graph becomes a proper directed acyclic graph.