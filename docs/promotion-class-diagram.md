# Promotion Module Class Diagram

```mermaid
classDiagram

    class Promotion {
        <<abstract>>
        -String id
        -String name
        -LocalDate startDate
        -LocalDate endDate
        +Promotion(String id, String name, LocalDate startDate, LocalDate endDate)
        +String getId()
        +void setId(String id)
        +String getName()
        +void setName(String name)
        +LocalDate getStartDate()
        +void setStartDate(LocalDate startDate)
        +LocalDate getEndDate()
        +void setEndDate(LocalDate endDate)
        +boolean isActive(LocalDate date)
        +double calculateDiscount(Sale sale)* 
    }

    class PercentageDiscount {
        -double discountPercentage
        +PercentageDiscount(String id, String name, LocalDate startDate, LocalDate endDate, double discountPercentage)
        +double getDiscountPercentage()
        +void setDiscountPercentage(double discountPercentage)
        +double calculateDiscount(Sale sale)
    }

    class CategoryDiscount {
        -double discountPercentage
        -String targetCategory
        +CategoryDiscount(String id, String name, LocalDate startDate, LocalDate endDate, double discountPercentage, String targetCategory)
        +double getDiscountPercentage()
        +void setDiscountPercentage(double discountPercentage)
        +String getTargetCategory()
        +void setTargetCategory(String targetCategory)
        +double calculateDiscount(Sale sale)
    }

    class BulkPurchaseDiscount {
        -int minimumQuantity
        -double discountPercentage
        +BulkPurchaseDiscount(String id, String name, LocalDate startDate, LocalDate endDate, int minimumQuantity, double discountPercentage)
        +int getMinimumQuantity()
        +void setMinimumQuantity(int minimumQuantity)
        +double getDiscountPercentage()
        +void setDiscountPercentage(double discountPercentage)
        +double calculateDiscount(Sale sale)
    }

    class PromotionRepository {
        -String FILE_PATH
        +void saveAll(List~Promotion~ promotions)
        +List~Promotion~ loadAll()
    }

    class PromotionService {
        -PromotionRepository promotionRepository
        +PromotionService(PromotionRepository promotionRepository)
        +Promotion registerPercentageDiscount(...)
        +Promotion registerCategoryDiscount(...)
        +Promotion registerBulkPurchaseDiscount(...)
        +List~Promotion~ listAllPromotions()
        +List~Promotion~ listActivePromotions()
        +Promotion findBestPromotionFor(Sale sale)
        +Promotion findById(String id)
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
        +double calculateTotal()
        +String generateReceipt()
        +String getAppliedPromotionName()
        +void setAppliedPromotionName(String name)
        +double getDiscountAmount()
        +void setDiscountAmount(double amount)
    }

    class SaleService {
        -SaleRepository saleRepository
        -ProductService productService
        -PersonService personService
        -AccessoryService accessoryService
        -PromotionService promotionService
        +Sale registerSale(...)
    }

    class Product {
        <<abstract>>
        -String id
        -String title
        -double price
        -int stockQuantity
        +double getPrice()
        +String getId()
        +String getTitle()
    }

    class ConsoleMenu {
        -PromotionService promotionService
        +void handlePromotionMenu()
    }

    Promotion <|-- PercentageDiscount
    Promotion <|-- CategoryDiscount
    Promotion <|-- BulkPurchaseDiscount

    PromotionRepository --> Promotion : persists
    PromotionService --> PromotionRepository : uses
    PromotionService --> Promotion : manages
    PromotionService --> Sale : evaluates

    SaleService --> PromotionService : applies promotion
    Sale --> Product : contains
    CategoryDiscount --> Product : evaluates category
    Promotion --> Sale : calculates discount for
    SaleService --> Sale : creates
    ConsoleMenu --> PromotionService : manages promotions

Design Notes
Promotion is an abstract class that defines the common structure and behavior of all promotions.
PercentageDiscount, CategoryDiscount, and BulkPurchaseDiscount inherit from Promotion.
Each concrete promotion overrides calculateDiscount(Sale sale) according to its own business rule.
PromotionRepository is responsible for persistence in data/promotions.csv.
PromotionService contains the business logic for registering, listing, filtering, and selecting promotions.
SaleService uses PromotionService when registering a sale.
Sale stores the promotion name and monetary discount applied to the sale.
CategoryDiscount evaluates the products included in a sale because its calculation depends on the products' categories.
ConsoleMenu communicates with PromotionService to provide the promotion management options to the user.