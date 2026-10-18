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

---

# Accessory.java Audit

- Information Expert

Verdict: Correct

Evidence: Accessory class implementation and dynamic list utilities

Explanation: The class acts as a perfect information expert for managing its internal list of compatible platforms. It provides clean domain methods like isCompatibleWith, addCompatibleConsole, and removeCompatibleConsole to manage state internally, rather than forcing outer layers to manipulate its collection manually.

- Creator

Verdict: Not Applicable

Explanation: Accessory is an abstract class and cannot be directly initialized using the new keyword. Object instantiation tasks are safely deferred to its concrete subclasses or factory utilities.

- Controller

Verdict: Not Applicable

Explanation: This class is a standard entity in the domain model layer. It remains isolated from capture systems, user inputs, and application control sequences.

- Low Coupling

Verdict: Correct

Explanation: The class remains highly decoupled. It does not depend on peripheral services or database repositories, and it decouples dynamic compatibility validation by storing console references as simple String identifiers instead of linking directly to concrete Console object models.

- High Cohesion

Verdict: Correct

Explanation: Its conceptual scope is completely focused on modeling structural accessory data and handling its specific console compatibility lists. It carries no peripheral logic like menu printing or persistence mechanisms.

- Polymorphism

Verdict: Correct

Evidence: public abstract class Accessory extends Product

Explanation: It expands the base Product structure to establish a specific abstract tier for peripheral items. By implementing a standardized getDescription routine, it fulfills its polymorphic role smoothly within the core domain hierarchy.

- Pure Fabrication

Verdict: Not Applicable

Explanation: The component maps a concrete business requirement from the store domain, so it represents an intrinsic model object rather than an engineering support utility class.

- Indirection

Verdict: Correct

Explanation: The class acts as a structural intermediate level within the domain design hierarchy (Product -> Accessory -> Controller), serving as an abstract layer that isolates generic core product behaviors from specialized individual controller attributes.

- Protected Variations

Verdict: Correct

Explanation: As an abstract class contract, it shields client components from needing directly to know or bind into specific concrete accessory implementations, containing variation impacts internally.

---

# Controller.java Audit

- Information Expert

Verdict: Correct

Evidence: Controller.getDescription() method

Explanation: The class operates as the local expert for its specific attribute. It knows and manages its unique field (ConnectionType) and uses super.getDescription() to complement its dynamic text representation natively.

- Creator

Verdict: Not Applicable

Explanation: This class does not instantiate or build other complex domain data models; its scope is strictly restricted to initializing its own internal state variables.

- Controller

Verdict: Not Applicable

Explanation: Even though the class name is Controller, it represents a physical gaming hardware device (gamepad/joystick). It does not act as the GRASP architectural system controller component and has no interaction with system event streams.

- Low Coupling

Verdict: Correct

Explanation: The class exhibits low coupling since it only depends directly on its abstract base class (Accessory) and standard Java types such as String and List, avoiding foreign dependencies.

- High Cohesion

Verdict: Correct

Explanation: This is a small and highly focused class (around 50 lines) with a single, well-defined purpose: modeling the specific data attributes and behavior of a gaming controller device.

- Polymorphism

Verdict: Correct

Evidence: Override of getDescription() method

Explanation: It overrides the getDescription() method polymorphically, extending the string behavior of the super class to append its connection type without requiring type conditional evaluations in upper layers.

- Pure Fabrication

Verdict: Not Applicable

Explanation: Controller maps a physical, concrete entity from the store business domain (a game controller) and is not an artificial or technical class invented for engineering support.

- Indirection

Verdict: Correct

Explanation: It represents the concrete leaf node of the system's polymorphic domain hierarchy path (Product -> Accessory -> Controller), inheriting behavioral contracts through a structured intermediate layout.

- Protected Variations

Verdict: Correct

Explanation: It safely encapsulates all specific variations and fields inherent to game controllers inside its own boundaries, ensuring that internal model modifications do not affect or break the upper hierarchy structures.

---

# Cable.java Audit

- Information Expert

Verdict: Correct

Explanation: The class acts as the proper expert for its specific hardware metrics. It handles its unique physical attributes (lengthInMeters and connectorType) locally, relying on parent accessors via super to build its core identity data fields.

- Creator

Verdict: Not Applicable

Explanation: This class focuses strictly on mapping structural entity parameters and initializing its own internal fields. It contains no responsibility for instantiating other domain software elements.

- Controller

Verdict: Not Applicable

Explanation: The class is a dead data model component located deep inside the domain layer. It remains completely isolated from capturing system execution triggers or coordinating console menus.

- Low Coupling

Verdict: Correct

Explanation: It maintains thin coupling boundaries, depending exclusively on its abstract superclass (Accessory) and standard data types, avoiding complex structural hooks with outer manager layers.

- High Cohesion

Verdict: Correct

Explanation: The structural scope is highly focused and cohesive. The component limits its responsibilities to modeling the physical attributes and accessors of a connection cable device.

- Polymorphism

Verdict: Violation

ID: V-10

Module: Accessories (Requerimiento 1)

Evidence: Cable class method overrides (Lines 56-59)

Explanation: The class breaks the established polymorphic contract of the product hierarchy by declaring a toString() method instead of overriding the abstract getDescription() method. When client modules iterate over a list of generic products and call getDescription(), the unique specific attributes of the cable will be completely ignored, falling back to the generic Accessory output format.

Consecuence: The polymorphic rendering framework fails for this specific class, preventing upper layer components from displaying complete hardware specifications uniformly.

Proposed Solution: Change the method signature from toString() to @Override public String getDescription() to satisfy the object hierarchy contract.

Other Member Code: No.

- Pure Fabrication

Verdict: Not Applicable

Explanation: Cable mirrors a tangible, real-world retail inventory item from the business domain. It is a fundamental model entity rather than an artificial technical class built for system support.

- Indirection

Verdict: Correct

Explanation: The class represents a concrete leaf node attached to the unified inheritance path layout (Product -> Accessory -> Cable), using a structured intermediate layout to inherit global contracts.

- Protected Variations

Verdict: Violation

ID: V-11

Module: Accessories (Requerimiento 1)

Evidence: Cable class description interface mismatch

Explanation: Because the specific textual representation is locked inside toString() instead of matching the getDescription() contract, the class exposes the presentation layer to data loss or unexpected visualization bugs if the catalog is evaluated polymorphically.

Consecuence: Client modules are unprotected against structural inconsistencies, forcing special case type-checking workarounds in upper layer views.

Proposed Solution: Align the method signature with the global description hook to protect caller interfaces from structural text inconsistencies.

Other Member Code: No.


---

# Memory.java Audit

- Information Expert

Verdict: Correct

Explanation: The class acts as the proper expert for its specific hardware metrics. It knows and manages its own unique attributes (capacityInGb and memoryType) locally and complements its descriptions natively.

- Alta Cohesión

Verdict: Correct

Explanation: This is a compact class (69 lines) focused exclusively on a single and well-defined purpose: modeling the properties and behaviors of a memory expansion unit.

- Bajo Acoplamiento

Verdict: Correct

Explanation: The class exhibits low coupling since it only depends directly on its abstract base class (Accessory) and standard types (List, String), avoiding unnecessary structural links.

- Polimorfismo

Verdict: Correct

Evidence: Override of getDescription() method

Explanation: Unlike the Cable class, this component correctly overrides the polymorphic getDescription() method defined in Product, ensuring smooth integration with the unified domain hierarchy.

- Variaciones Protegidas

Verdict: Correct

Explanation: It safely encapsulates all specific particularities and fields inherent to memory hardware inside its boundaries without impacting the rest of the inheritance hierarchy structure.

- Indirection

Verdict: Correct

Explanation: It represents a concrete leaf node within the dynamic polymorphic domain hierarchy path (Product -> Accessory -> Memory), inheriting contracts through a structured layout.

- Pure Fabrication

Verdict: Not Applicable

Explanation: Memory represents a concrete concept and a physical retail inventory item from the store business domain, rather than an artificial technical class built for engineering support.

- Creator

Verdict: Not Applicable

Explanation: This class focuses strictly on initializing its own internal state variables and does not instantiate other complex domain software elements.

- Controller

Verdict: Not Applicable

Explanation: The class is a domain data entity component located deep inside the model layer. It does not handle system interface interaction events, coordinate software workflows, or manage user input flows.




