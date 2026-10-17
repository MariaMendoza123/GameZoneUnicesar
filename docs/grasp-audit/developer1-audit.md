# Developer 1 GRASP Audit


  Scope of the Audit

Module: Product Management (Taller 1)

Audited Classes: Product, Console, VideoGame


# Product.java Audit


- Information Expert

Verdict: Correct.
Evidence: Product class attributes (id, title, price, stockQuantity)
Explanation: The class holds the fundamental data fields for any item in the store. Since it owns this data, it naturally acts as the expert. Any future logic regarding base product states belongs here.

- Creator

Verdict: Not Applicable
Explanation: Product is an abstract class. It cannot be instantiated using the new keyword, so it cannot take the responsibility of creating objects.

- Controller

Verdict: Not Applicable
Explanation: This is a pure domain entity. It does not handle console menu inputs, user interactions, or system event coordination.

- Low Coupling

Verdict: Correct
Explanation: It has no dependencies or imports linking it to other custom classes in the project. Its fields are private, preventing external components from directly modifying its internal state, which keeps coupling minimal.

- High Cohesion

Verdict: Correct
Explanation: The class does one job: representing a product. It only contains fields, a constructor, and basic getters/setters.

- Polymorphism

Verdict: Correct
Evidence: public abstract String getDescription()
Explanation:  the class uses an abstract hook so subclasses resolve their own formatting dynamically.

- Pure Fabrication

Verdict: Not Applicable
Explanation: Product reflects a real, fundamental business concept in the GameZone domain. It is not an artificial technical utility class like a logger or a database handler.

- Indirection

Verdict: Not Applicable
Explanation: It does not act as a mediator or proxy between two separate components. Its relation to other elements is handled directly through standard inheritance.

- Protected Variations

Verdict: Correct
Evidence: public abstract class Product
Explanation: The abstract class acts as a stable contract. Upper layers depend on this abstraction rather than concrete variants. 

---

 # VideoGame.java Audit

- Information Expert

Verdict: Correct

Evidence: VideoGame.getDescription() method

Explanation: It stores its own specific parameters like platform, genre, and classification. It uses these fields along with the parent getters to print its own description without needing external data helpers.

- Creator

Verdict: Not Applicable

Explanation: This class has no business logic for creating or instantiating other objects. It represents data, so the pattern does not apply.

- Controller

Verdict: Not Applicable

Explanation: It belongs to the domain data layer and does not capture user actions or input streams from the console menus.

- Low Coupling

Verdict: Correct

Explanation: It depends on its parent class Product through standard inheritance. Coupling is safe because it fetches inherited fields using getters, isolating it from internal changes to the superclass fields.

- High Cohesion

Verdict: Correct

Explanation: The class strictly represents video game properties. It remains small and free of unrelated responsibilities like inventory operations or pricing calculations.

- Polymorphism

Verdict: Correct

Evidence: Override of getDescription()

Explanation: It satisfies the abstract contract by defining its own string output format, avoiding structural conditional logic in managers or controllers.

- Pure Fabrication

Verdict: Not Applicable

Explanation: The class is a direct mapping of a real domain object and not a utility component built for engineering support.

- Indirection

Verdict: Not Applicable

Explanation: It has a single, straight inheritance relationship with Product. It does not act as an intermediate bridge for other decoupled objects.

- Protected Variations

Verdict: Correct

Explanation: Internal changes to the game's fields or metadata formatting are fully encapsulated. The rest of the system is protected since it references the stable Product superclass instead of this specific concrete implementation.

---

# Console.java Audit

- Information Expert

Verdict: Correct

Evidence: Console.getDescription() method

Explanation: The class fields handle hardware specifics like brand, model, and generation. It relies on these local fields plus the inherited attributes via getters to build its own description string without querying other classes.

- Creator

Verdict: Not Applicable

Explanation: This is just a data model class. It does not instantiate other business entities, so the Creator pattern does not apply within this file.

- Controller

Verdict: Not Applicable

Explanation: It is part of the domain model layer. It has zero interaction with the UI console menus or application control flow.

- Low Coupling

Verdict: Correct

Explanation: The coupling to the parent class is handled safely. Because the parent fields are private, Console must interact through public methods, keeping the subclass clean and independent from internal structural changes in Product.

- High Cohesion

Verdict: Correct

Explanation: The class is completely focused on modeling hardware console entities. It does not mix software properties, customer data, or store transaction mechanics.

- Polymorphism

Verdict: Correct

Evidence: Override of getDescription()

Explanation: It overrides the parent contract to run its own specific implementation. This removes the need for type checking or hardcoded conditional checks elsewhere in the application.

- Pure Fabrication

Verdict: Not Applicable

Explanation: Console represents a concrete, physical item in the real-world business model. It is not a technical abstraction invented for code design purposes.

- Indirection

Verdict: Not Applicable

Explanation: This class is a straight subclass. It does not sit between other collaborating classes to reduce friction or decouple them.

- Protected Variations

Verdict: Correct

Explanation: Changes to how console details are structured are entirely self-contained here. Modifications to the text format or local attributes won't alter or affect how the UI layer processes the generic product list.

# ProductRepository.java Audit

- Information Expert

Verdict: Correct

Evidence: ProductRepository class body

Explanation: The class acts as the expert for file system persistence logic. It centralizes the operational tasks of writing and reading lines from the products data file, ensuring that no domain models are polluted with java.nio or file-handling logic.

- Creator

Verdict: Correct

Explanation: The class reads data lines from a file and reconstructs the objects. This process is considered standard domain entity rehydration from a data stream rather than an improper creation logic under GRASP.

- Controller

Verdict: Not Applicable

Explanation: This class handles low-level file data access operations within the persistence layer. It has no contact with user actions, UI components, or console menus, and it does not coordinate or capture system events.

- Low Coupling

Verdict: Violation

ID: V-01

Module: Products & Persistence (Taller 1)

Evidence: ProductRepository concrete imports and instance checks

Explanation: The repository is tightly coupled to the concrete implementations of VideoGame and Console instead of interacting solely with the abstract Product superclass. It explicitly imports and processes these concrete types inside its private helper methods.

Consequence: Any structural modification or deletion of a concrete product subclass will directly cause a compilation or runtime error inside the persistence layer.

Proposed Solution: Encapsulate type-specific operations inside the domain model hierarchy to reduce the repository's dependency graph.

Other Member Code: No.

- High Cohesion

Verdict: Correct

Explanation: The class remains highly focused on data storage and operations involving the physical data file. It does not perform validation rules, calculate prices, or handle display functions.

- Polymorphism

Verdict: Violation

ID: V-02

Module: Products & Persistence (Taller 1)

Evidence: ProductRepository.toLine() and fromLine() methods

Explanation: The methods evaluate the specific concrete subclasses using explicit type-checking structures (instanceof and string tag checks). This completely avoids object-oriented polymorphism by using conditional code branches to decide formatting and parsing.

Consequence: Adding any new product category to the GameZone store will require manual modifications inside these conditional branches, making the persistence layer brittle and prone to structural bugs whenever the store catalog grows. 

Proposed Solution: Define a polymorphic serialization method hook in the Product abstract class so each subclass handles its own file format representation.

Other Member Code: No.

- Pure Fabrication

Verdict: Correct

Evidence: ProductRepository class definition

Explanation: The repository itself is an excellent application of a Pure Fabrication pattern. It represents an artificial technical component created to centralize file database interactions, keeping the clean entity models free of infrastructure dependencies.

- Indirection

Verdict: Not Applicable

Explanation: The component serves as a direct access point for file persistence operations and does not act as an intermediate architectural proxy between separate layers.

- Protected Variations

Verdict: Violation

ID: V-03

Module: Products & Persistence (Taller 1)

Evidence: ProductRepository class structure

Explanation: The class fails to protect the persistence mechanism against predictable business variations, such as expanding the product catalog. Because the serialization and deserialization routines explicitly expect only VideoGame or Console types, any structural change or new product category addition will break the repository's execution.

Consequence: The persistence layer becomes highly volatile and unstable, requiring dangerous code modifications every time a new product type is introduced into the store's business domain.

Proposed Solution: Rely on a stable, polymorphic contract in the abstract Product class so that variations are handled by the models, shielding the repository from direct structural dependencies.

Other Member Code: No.

# ProductService.java Audit

- Information Expert

Verdict: Violation

ID: V-04

Module: Products & Services (Taller 1)

Evidence: ProductService.updateStock() and restoreStock() methods

Explanation: The service alters item stock levels externally by extracting data values through getters, performing mathematical calculations locally, and writing values back using setters. According to GRASP, the Product class owns this state data and should hold the operational responsibility to update its own stock.

Consequence: Object encapsulation is weakened since internal domain state computations are leaked and driven by an external coordinator service.

Proposed Solution: Relocate stock modification workflows into the Product abstract hierarchy by exposing domain methods such as reduceStock or addStock.

Other Member Code: No.

- Creator

Verdict: Violation

ID: V-05

Module: Products & Services (Taller 1)

Evidence: Explicit invocations of concrete constructors via the new keyword

Explanation: The service triggers concrete instance creations directly inside the registration routines. Under GRASP, the orchestration layer should remain detached from object instantiations to preserve clean layers.

Consequence: Tight structural rigidity prevents the service layer from scaling smoothly when object definitions are updated.

Proposed Solution: Separate instantiation operations using a specialized factory component.

Other Member Code: No.

- Controller

Verdict: Not Applicable

Explanation: This class is a domain service handling core business use cases. It does not interface directly with the console UI streams or capture user interface interaction events.

- Low Coupling

Verdict: Violation

ID: V-06

Module: Products & Services (Taller 1)

Evidence: Concrete subclass imports, constructors, and specific validator invocations

Explanation: The service is tightly coupled to concrete subclass representations and specific static verification tools. It fails to isolate domain orchestration from detailed structural types.

Consequence: Modifications to domain fields or structural constructors will immediately generate compilation issues across multiple business service routines.

Proposed Solution: Decouple the component by using dynamic polymorphic structures and abstract verification contracts.

Other Member Code: No.

- High Cohesion

Verdict: Violation

ID: V-07

Module: Products & Services (Taller 1)

Evidence: ProductService.generateNextId() and local attribute validation chains

Explanation: The component lacks high cohesion because it dilutes its business logic by handling lower-level technical tasks, such as tracking maximum identifier counts, string substitution routines, and parsing entity metadata.

Consequence: The class grows unnecessarily complex and carries too many distinct reasons to change, making maintenance tedious.

Proposed Solution: Offload parsing, identification generation, and custom field tracking to independent structural utilities or data providers.

Other Member Code: No.

- Polymorphism

Verdict: Violation

ID: V-08

Module: Products & Services (Taller 1)

Evidence: ProductService.registerVideoGame() and registerConsole() methods

Explanation: The class completely avoids polymorphism by exposing separate, rigid, type-specific registration methods for each subclass instead of routing creations through a unified polymorphic operational flow.

Consequence: Expanding the GameZone catalog requires writing entirely new method blocks for every added product category, forcing repetitive structural patterns in the service layer.

Proposed Solution: Refactor the registration entry point to dynamically accept a generic Product abstraction or abstract data transport component.

Other Member Code: No.

- Pure Fabrication

Verdict: Correct

Evidence: ProductService class definition

Explanation: The service behaves as a standard technical fabrication pattern. It is an artificial construction created to maintain business operational separation, keeping domain structures unpolluted by execution coordination responsibilities.

- Indirection

Verdict: Not Applicable

Explanation: The class acts as a direct business logic execution component rather than an architectural routing middleware or proxy designed to decouple two conflicting boundaries.

- Protected Variations

Verdict: Violation

ID: V-09

Module: Products & Services (Taller 1)

Evidence: ProductService class layout and ID prefix rules

Explanation: The service layer fails to protect itself from predictable catalog expansions. It features structural dependencies on concrete variants, specific database identifiers, and rigid type rules, making it volatile against model evolution.

Consequence: Any change to existing product data layouts or the introduction of a new line of inventory forces code alterations directly inside this core business component.

Proposed Solution: Shield the service layer by wrapping dynamic behaviors in abstract contracts or structural creation strategies.

Other Member Code: No.
