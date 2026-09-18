# Return Module Class Diagram

## Updated Class Diagram

```mermaid
classDiagram

    class Sale {
        -String id
        -String date
        -Client client
        -Seller seller
        -List~Product~ products
        -double totalAmount
        +boolean canBeReturned()
    }

    class Product {
        <<abstract>>
        -String id
        -String title
        -double price
        -int stockQuantity
    }

    class ProductService {
        -ProductRepository productRepository
        +void restoreStock(String productId, int quantity)
        +Product findProduct(String id)
        +List~Product~ findAllProducts()
    }

    class Return {
        -String id
        -LocalDate returnDate
        -Sale originalSale
        -List~Product~ returnedProducts
        -String returnReason
        -double refundAmount
        +Return(...)
        +String getId()
        +LocalDate getReturnDate()
        +Sale getOriginalSale()
        +List~Product~ getReturnedProducts()
        +String getReturnReason()
        +double getRefundAmount()
        +double calculateRefundAmount()
        +String generateReturnReceipt()
    }

    class ReturnRepository {
        -SaleService saleService
        -ProductService productService
        +void saveAll(List~Return~ returns)
        +List~Return~ loadAll()
    }

    class ReturnService {
        -ReturnRepository returnRepository
        -SaleService saleService
        -ProductService productService
        -List~Return~ returns
        +Return registerReturn(String saleId, List~String~ productIds, String reason)
        +List~Return~ viewAllReturns()
        +List~Return~ viewReturnsByCustomer(String customerId)
        +List~Return~ viewReturnsBySale(String saleId)
        +double generateMonthlyBalance(int month, int year)
    }

    class SaleService {
        +List~Sale~ findAllSales()
        +List~Sale~ findSalesByClient(String clientId)
        +List~Sale~ findSalesBySeller(String sellerId)
    }

    class ConsoleMenu {
        -ReturnService returnService
        +void showReturnMenu()
        +void handleReturnMenu()
        -void registerReturn()
        -void showAllReturns()
        -void showReturnsByCustomer()
        -void showReturnsBySale()
        -void showMonthlyBalance()
    }

    Return }o--|| Sale : references original sale
    Return }o--|{ Product : contains returned products
    Sale --> Product : contains products

    ReturnService --> ReturnRepository : persists returns
    ReturnService --> SaleService : retrieves sales
    ReturnService --> ProductService : restores stock
    ConsoleMenu --> ReturnService : delegates return operations
    ProductService --> Product : manages inventory



## Layer Integration
- `Return` belongs to the `model` layer.
- `ReturnRepository` belongs to the `persistence` layer.
- `ReturnService` belongs to the `service` layer.
- `ConsoleMenu` belongs to the `ui` layer.
- `ReturnService` coordinates sales and inventory through `SaleService` and `ProductService`.
- `ProductService.restoreStock()` is reused to restore inventory after a successful return.
- `ConsoleMenu` delegates return operations to `ReturnService` instead of implementing business rules directly.