classDiagram
%% CAPA MODELO (com.gamezone.model)
namespace model {
class Person {
<<abstract>>
- String id
- String name
- String phone
+ Person(id: String, name: String, phone: String)
+ getId() String
+ getName() String
+ getPhone() String
+ setId(id: String) void
+ setName(name: String) void
+ setPhone(phone: String) void
+ getRoleDescription()* String
}

        class Customer {
            - String email
            - List~Sale~ purchaseHistory
            + Customer(id: String, name: String, phone: String, email: String)
            + getEmail() String
            + getPurchaseHistory() List~Sale~
            + setEmail(email: String) void
            + addPurchase(sale: Sale) void
            + getRoleDescription() String
        }

        class Salesperson {
            - String employeeId
            - String workShift
            + Salesperson(id: String, name: String, phone: String, employeeId: String, workShift: String)
            + getEmployeeId() String
            + getWorkShift() String
            + setEmployeeId(employeeId: String) void
            + setWorkShift(workShift: String) void
            + getRoleDescription() String
        }

        class Product {
            <<abstract>>
            - String id
            - String title
            - double price
            - int stockQuantity
            + Product(id: String, title: String, price: double, stockQuantity: int)
            + getId() String
            + getTitle() String
            + getPrice() double
            + getStockQuantity() int
            + setId(id: String) void
            + setTitle(title: String) void
            + setPrice(price: double) void
            + setStockQuantity(stockQuantity: int) void
            + getDescription()* String
            + toString() String
        }

        class VideoGame {
            - String platform
            - String genre
            - String classification
            + VideoGame(id: String, title: String, price: double, stockQuantity: int, platform: String, genre: String, classification: String)
            + getPlatform() String
            + getGenre() String
            + getClassification() String
            + setPlatform(platform: String) void
            + setGenre(genre: String) void
            + setClassification(classification: String) void
            + getDescription() String
            + toString() String
        }

        class Console {
            - String brand
            - String model
            - String generation
            + Console(id: String, title: String, price: double, stockQuantity: int, brand: String, model: String, generation: String)
            + getBrand() String
            + getModel() String
            + getGeneration() String
            + setBrand(brand: String) void
            + setModel(model: String) void
            + setGeneration(generation: String) void
            + getDescription() String
            + toString() String
        }

        class Sale {
            - String id
            - String date
            - Customer customer
            - Salesperson salesperson
            - List~Product~ products
            - double totalAmount
            + Sale(id: String, date: String, customer: Customer, salesperson: Salesperson, products: List~Product~)
            + getId() String
            + getDate() String
            + getCustomer() Customer
            + getSalesperson() Salesperson
            + getProducts() List~Product~
            + getTotalAmount() double
            + calculateTotal() double
        }
    }

    %% CAPA PERSISTENCIA (com.gamezone.persistence)
    namespace persistence {
        class ProductRepository {
            - String filePath
            + ProductRepository(filePath: String)
            + saveAll(products: List~Product~) void
            + findAll() List~Product~
            + findById(id: String) Product
        }

        class PersonRepository {
            - String filePath
            + PersonRepository(filePath: String)
            + saveAll(people: List~Person~) void
            + findAll() List~Person~
            + findById(id: String) Person
        }

        class SaleRepository {
            - String filePath
            + SaleRepository(filePath: String)
            + saveAll(sales: List~Sale~) void
            + findAll() List~Sale~
        }
    }

    %% CAPA SERVICIOS (com.gamezone.service)
    namespace service {
        class ProductService {
            - ProductRepository productRepository
            + ProductService(productRepository: ProductRepository)
            + registerVideoGame(game: VideoGame) void
            + registerConsole(console: Console) void
            + getAllProducts() List~Product~
            + getProductById(id: String) Product
            + updateStock(productId: String, quantity: int) void
        }

        class PersonService {
            - PersonRepository personRepository
            + PersonService(personRepository: PersonRepository)
            + registerCustomer(customer: Customer) void
            + registerSalesperson(salesperson: Salesperson) void
            + getAllCustomers() List~Customer~
            + getAllSalespersons() List~Salesperson~
            + getPersonById(id: String) Person
        }

        class SaleService {
            - SaleRepository saleRepository
            - ProductService productService
            - PersonService personService
            + SaleService(saleRepository: SaleRepository, productService: ProductService, personService: PersonService)
            + registerSale(customer: Customer, salesperson: Salesperson, products: List~Product~) Sale
            + getAllSales() List~Sale~
            + getCustomerPurchases(customerId: String) List~Sale~
            + getSalespersonSales(employeeId: String) List~Sale~
        }
    }

    %% CAPA INTERFAZ DE USUARIO (com.gamezone.ui & root)
    namespace ui {
        class ConsoleMenu {
            - ProductService productService
            - PersonService personService
            - SaleService saleService
            + ConsoleMenu(productService: ProductService, personService: PersonService, saleService: SaleService)
            + start() void
            - showMainMenu() void
            - handleProductMenu() void
            - handlePersonMenu() void
            - handleSaleMenu() void
        }

        class Main {
            + main(args: String[]) void
        }
    }

    %% RELACIONES DE HERENCIA (MODEL)
    Person <|-- Customer
    Person <|-- Salesperson
    Product <|-- VideoGame
    Product <|-- Console

    %% RELACIONES DE ASOCIACIÓN Y AGREGACIÓN (MODEL)
    Sale "1" --> "1" Customer : purchased by
    Sale "1" --> "1" Salesperson : handled by
    Sale "1" o-- "1..*" Product : contains

    %% DEPENDENCIAS Y RELACIONES ENTRE CAPAS
    ProductService --> ProductRepository : uses
    PersonService --> PersonRepository : uses
    SaleService --> SaleRepository : uses
    SaleService --> ProductService : uses
    SaleService --> PersonService : uses

    ProductRepository ..> Product : manages
    PersonRepository ..> Person : manages
    SaleRepository ..> Sale : manages

    ConsoleMenu --> ProductService : uses
    ConsoleMenu --> PersonService : uses
    ConsoleMenu --> SaleService : uses

    Main ..> ConsoleMenu : instantiates
    Main ..> ProductRepository : initializes
    Main ..> PersonRepository : initializes
    Main ..> SaleRepository : initializes