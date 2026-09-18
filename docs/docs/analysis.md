1.Common Attributes: Name (name), Identification Number (id), and Contact Phone Number (phone) are shared by every person interacting with the store.  Specific Attributes:Customer (Customer): Email address (email) and Purchase History (purchaseHistory).  Salesperson (Salesperson): Employee Code (employeeId) and Work Shift (workShift).  Hierarchy Reflection: A base/super class named Person contains all common attributes. Customer and Salesperson inherit from Person using generalization (extends), adding their role-specific attributes.

2.Decision: Yes, a generic Person class should exist to avoid code duplication and enforce shared structure. However, it should be declared as an abstract class.  Justification & Implication: In the domain of GameZone Unicesar, every person entering or working at the store is explicitly either a Customer or a Salesperson. A generic "Person" cannot make a purchase or register a sale on its own. Making Person abstract prevents direct instantiation (new Person()), ensuring that only valid, role-specific objects (Customer or Salesperson) can exist in runtime memory.

3.All products share an ID, title, price, and stock quantity, as these attributes are necessary for any item sold in the store, regardless of its type. The *Video Game* class adds platform, genre, and age rating, since these describe properties specific to video games. The *Console* class adds brand, model, and generation, since these describe properties specific to the hardware. This distinction is reflected by placing the common attributes in an abstract *Product* class and the specific ones in its subclasses, *Video Game* and *Console*.

4.The description behavior must be declared as an abstract method in the Product base class (e.g., public abstract String getDescription();) without an implementation. This compels each subclass (VideoGame, Console) to provide its own implementation; otherwise, the code will not compile. The object-oriented mechanism enabling this is polymorphism: when getDescription() is called on a Product reference, Java executes the implementation specific to the actual runtime type (VideoGame or Console), allowing each subclass to construct its description by incorporating its own specific attributes.

5.Customer (Customer): Association (Multiplicity 1). A sale is associated with exactly one customer who makes the purchase.  Salesperson (Salesperson): Association (Multiplicity 1). A sale is associated with exactly one salesperson who handles the transaction.  Product (Product): Aggregation / Association (Multiplicity 1..*). A sale holds references to one or more products.  Justification: These are not inheritance relationships because a Sale is neither a Person nor a Product. They are structural associations because Sale coordinates these entities to fulfill a business transaction.

6.Decision: The Sale class should be responsible for calculating its own total.  Argument: According to the Information Expert GRASP principle, the class that possesses all necessary data to perform an operation should perform it. Since Sale maintains the collection of purchased products and their respective quantities/prices, encapsulating calculateTotal() within Sale preserves domain encapsulation and keeps business rules cohesive.

7.The Sale class constructor or addProduct() method should prohibit finalizing a sale if the product list is empty.  Validation Point: Validation occurs in the SaleService layer prior to processing or saving the sale. The service layer verifies that sale.getProducts() contains at least one item; if empty, it throws a domain exception (e.g., EmptySaleException) before delegating to persistence. 

8.Design Reflection: When SaleService.registerSale(Sale sale) is executed, the service iterates through the list of products in the sale.  Involved Classes:SaleService: Coordinates the transaction and calls product stock updates.  Product / Subclasses: Invokes deductStock(int quantity) to update the internal stockQuantity attribute.  ProductService & ProductRepository: Saves the updated product inventory back to persistent file storage.

9.model (Domain Model Layer): Entities (Person, Customer, Salesperson, Product, VideoGame, Console, Sale). Contains state, simple constructors, getters/setters, and core domain behavior.  persistence (Data Access Layer): Repositories/DAOs (PersonRepository, ProductRepository, SaleRepository). Responsible solely for reading and writing model objects to/from physical files.  service (Business Logic Layer): Services (PersonService, ProductService, SaleService). Enforces business constraints (stock checking, total computation, non-empty validation) and coordinates interaction between model and persistence.  ui (User Interface Layer): Console menus and inputs (ConsoleMenu). Captures user inputs, formats output text, and delegates requests to the service layer.  Criterion: Classes are assigned based on the Single Responsibility Principle (SRP) and separation of concerns.  

10.Why: To maintain Low Coupling and adhere to the Single Responsibility Principle. Domain entities should only represent real-world concepts and business logic.  Problems Avoided: If entities handled file storage directly:Changing storage mechanisms (e.g., switching from flat files to SQL or JSON) would require modifying core domain logic.  Testing domain logic independently would be impossible without relying on physical disk I/O.  Reusability in other UI environments (desktop UI, web, mobile) would be severely compromised.

11.Allowed Dependencies:The user interface layer depends on the service layer.  The service layer depends on the persistence layer.  The service layer depends on the model layer.  The persistence layer depends on the model layer.  Prohibited Dependencies:The user interface layer must not depend on or directly access the persistence layer.  The model layer must not depend on any other layer in the application.  Upward dependencies, such as the persistence layer or service layer depending on the user interface layer, are strictly prohibited.  Justification: Strict top-down dependencies ensure high modularity, easy maintenance, and simple unit testing. Lower layers, such as the domain model, remain completely isolated from technological changes in presentation or data storage engines.

## Requirement 1 - Guiding Questions

### 1. Should accessories extend the existing Product hierarchy or form an independent hierarchy? Justify your decision considering code reuse and model coherence.

Accessories should extend the existing Product hierarchy rather than form an independent hierarchy. All three accessory types (Controller, Cable, Memory) are sellable items and share the common attributes already defined in Product, such as id, title, price, and stockQuantity.

Extending Product allows the accessory module to reuse the existing attributes and behavior instead of duplicating them. It also keeps the model coherent because accessories, videogames, and consoles can be treated as Product objects when they participate in a sale.

This decision also simplifies integration with SaleService. Since accessories are Products, the sales process can receive them through the same List<Product> used for videogames and consoles. The service only needs to distinguish which inventory service is responsible for updating the stock. This avoids creating a separate sales mechanism for accessories and preserves the existing behavior.

### 2. What attributes are common to the three accessory types and which are specific to each type? How is this distinction reflected in the module's class hierarchy?

The three accessory types inherit the common Product attributes: id, title, price, and stockQuantity.

In addition, all accessories share the compatibleConsoleIds attribute, which stores the identifiers of the consoles compatible with the accessory. This attribute and its related compatibility operations are defined in the abstract Accessory class.

Each concrete accessory subclass defines its own specific attributes:

- Controller: connectionType.
- Cable: lengthInMeters and connectorType.
- Memory: capacityInGb and memoryType.

The hierarchy therefore places the common accessory-specific behavior in the abstract Accessory class, while Controller, Cable, and Memory contain only the attributes and behavior specific to each accessory type. This structure promotes inheritance, code reuse, and polymorphism.

### 3. The compatibility between an accessory and a console is a relationship between two entities in the system. How is this relationship represented in the design and in persistence? Is compatibility an attribute of the accessory, of the console, or of both?

Compatibility is represented on the accessory side through the compatibleConsoleIds attribute in Accessory. This attribute contains a list of console identifiers that are compatible with the accessory.

The Console class does not store a list of accessories. Therefore, the relationship is maintained as a unidirectional association from Accessory to Console identifiers. This keeps the accessory module self-contained and avoids modifying the existing Console class.

In persistence, the compatibility list is stored as part of each accessory record in data/accessories.csv. Multiple console IDs are separated using a semicolon. When the repository loads the file, AccessoryRepository converts this field back into a List<String>. This allows the compatibility relationship to be persisted without requiring an additional file or persistence structure.

### 4. What modifications are necessary in the sales service (SaleService) so that sales can include accessories without breaking the existing behavior with videogames and consoles?

SaleService must be extended so that accessories can participate in the existing sales process while preserving the behavior already implemented for videogames and consoles.

The service receives the sale items as a List<Product>, allowing videogames, consoles, and accessories to be processed through the same collection. During stock validation, SaleService identifies whether an item is an Accessory. If it is an accessory, the service uses AccessoryService to find the item and validate its stock. Otherwise, it continues using ProductService for videogames and consoles.

The same distinction is applied when updating inventory after the sale. AccessoryService.updateStock() is used for accessories, while ProductService.updateStock() continues to handle the existing product types.

This approach keeps the existing sales workflow intact while adding support for accessories. The Sale class and the total calculation can continue working with Product references, so no separate sales mechanism is required for accessories.

### 5. In which layer of the system architecture should the new accessory module classes be located? Justify your decision based on the responsibilities of each layer.

The new accessory classes should be distributed across the existing layered architecture according to their responsibilities.

The model layer contains the domain entities and their relationships, including Accessory, Controller, Cable, and Memory. These classes represent the concepts of the accessory domain and contain their attributes and core domain behavior.

The persistence layer contains AccessoryRepository, which is responsible for reading and writing accessory information to data/accessories.csv. It handles the conversion between persisted records and model objects.

The service layer contains AccessoryService, which implements the business operations and validations for registering, listing, searching, and updating accessories.

The user interface layer is responsible for receiving user input and displaying the accessory management options through the console menu. It delegates the operations to AccessoryService instead of accessing the repository directly.

This organization preserves the project's existing separation of concerns and follows the Single Responsibility Principle. Each layer has a specific responsibility, making the accessory module easier to maintain and integrate with the rest of the system.