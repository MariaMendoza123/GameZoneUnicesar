# Developer 1 GRASP Audit


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

- High Cohesion

Verdict: Correct

Explanation: This is a compact class (69 lines) focused exclusively on a single and well-defined purpose: modeling the properties and behaviors of a memory expansion unit.

- Low Coupling

Verdict: Correct

Explanation: The class exhibits low coupling since it only depends directly on its abstract base class (Accessory) and standard types (List, String), avoiding unnecessary structural links.

- Polymorphism

Verdict: Correct

Evidence: Override of getDescription() method

Explanation: Unlike the Cable class, this component correctly overrides the polymorphic getDescription() method defined in Product, ensuring smooth integration with the unified domain hierarchy.

- Protected Variations

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

---

#  Promotion.java Audit

- Information Expert

Verdict: Correct

Evidence: Promotion.isActive() method (Lines 94-96)

Explanation: The class acts as a perfect information expert for managing its validity timeframe. Because it encapsulates the fields startDate and endDate, it natively resolves whether a promotion is active on a specific date without leaking structural temporal state logic to upper business handlers.

- Creator

Verdict: Not Applicable

Explanation: Promotion is defined with the abstract modifier, preventing any direct structural instantiation using the new keyword. Object creation responsibilities are safely deferred to specialized subclasses or factory patterns.

- Controller

Verdict: Not Applicable

Explanation: The component is a core business domain entity model. It remains isolated from interface inputs, console streams, or orchestrating system use case flows.

- Low Coupling

Verdict: Correct

Explanation: The class features low, safe coupling boundaries. It interacts exclusively with standard Java data types (java.time.LocalDate) and references the Sale contract as a generic execution parameter to decouple its interface.

- High Cohesion

Verdict: Correct

Explanation: The class exhibits high, focused cohesion. Its properties and methods are entirely dedicated to a single conceptual task: managing the common attributes of store promotions and declaring the contract for discount evaluation.

- Polymorphism

Verdict: Correct

Evidence: public abstract double calculateDiscount(Sale sale);

Explanation: Instead of allowing the service layer to handle discount calculations using complex conditional type branches (such as checking if a promotion is a percentage or bulk type), the class establishes an abstract hook. This delegates calculation behaviors polimorphically to its concrete subclasses.

- Pure Fabrication

Verdict: Not Applicable

Explanation: The class maps a direct conceptual requirement of the GameZone business domain (Store Promotions), so it represents an organic entity component rather than an artificial technical utility.

- Indirection

Verdict: Correct

Explanation: The abstract class serves as a structural intermediate layer in the polymorphic promotion hierarchy (Promotion -> PercentageDiscount / BulkDiscount), isolating generic use case validation from specialized calculations.

- Protected Variations

Verdict: Correct

Evidence: public abstract class Promotion

Explanation: It serves as a stable, predictable contract for the system. External components (like billing controllers or sale services) depend entirely on this abstraction. Adding new promotion styles in the future will not cause ripple modifications or destabilize existing system modules.


# PercentageDiscount.java Audit

- Information Expert

Verdict: Correct

Evidence: PercentageDiscount.calculateDiscount() body

Explanation: It operates as the correct expert because it knows its own internal configuration parameter (percentage) and directly executes the corresponding mathematical discount formula.

- Creator

Verdict: Not Applicable

Explanation: This class carries no structural initialization responsibilities and does not instantiate other complex domain software components.

- Controller

Verdict: Not Applicable

Explanation: The class is a pure domain entity and behavioral strategy. It has no contact with capturing user interface commands, menu loops, or managing system event streams.

- Low Coupling

Verdict: Correct

Explanation: It exhibits minimal coupling boundaries since it only binds directly to its superclass contract (Promotion) and the generic transaction interface (Sale).

- High Cohesion

Verdict: Correct

Explanation: This is a highly focused, small component (53 lines) with a single, well-defined purpose: calculating and applying the percentage-based discount rule.

- Polymorphism

Verdict: Correct

Evidence: @Override public double calculateDiscount(Sale sale)

Explanation: The class successfully implements calculateDiscount(Sale sale) polymorphically, allowing the execution layout to resolve the math dynamically without requiring conditional statements or type evaluations.

- Pure Fabrication

Verdict: Not Applicable

Explanation: The class maps a direct, concrete commercial concept from the real-world business domain (a percentage discount) rather than an artificial technical utility class built for system support.

- Indirection

Verdict: Correct

Explanation: The component acts as the concrete operational strategy layer that effectively decouples the transaction model (Sale) from specific underlying discount evaluation algorithms.

- Protected Variations

Verdict: Correct

Explanation: It safely encapsulates the unique percentage calculation algorithms internally without generating unexpected ripple impacts across the model hierarchy or the calling client.


# CategoryDiscount.java Audit

- Information Expert

Verdict: Violation

ID: V-12

Module: Promotions (Requerimiento 2)

Evidence: CategoryDiscount.calculateDiscount() structural flow

Explanation: The class violates the pattern regarding categorization rules. The Product entity itself should be the expert that knows which category it belongs to, rather than forcing the promotion logic to inspect attributes externally using instanceof.

Consequence: Object encapsulation is weakened because the promotion layer must actively deduce structural data domains that belong exclusively to the models.

Proposed Solution: Add a generic category categorization hook or string contract inside the abstract Product structure.

Other Member Code: No.

- Creator

Verdict: Not Applicable

Explanation: This class carries no structural initialization responsibilities and does not instantiate other complex domain software components.

- Controller

Verdict: Not Applicable

Explanation: The class is a pure domain entity and behavioral strategy layout. It has no contact with capturing user interface commands, menu loops, or managing system event streams.

- Low Coupling

Verdict: Violation

ID: V-13

Module: Promotions (Requerimiento 2)

Evidence: CategoryDiscount concrete product instance checks

Explanation: The class exhibits a partial violation as it unnecessarily couples its calculation routine directly to the concrete implementations of VideoGame, Console, and Accessory.

Consequence: Any structural rearrangement or removal of a concrete product subclass will directly cause compilation or execution errors inside the promotion layer.

Proposed Solution: Rely exclusively on abstract superclass string accessor contracts to decouple the method layout from direct class type checks.

Other Member Code: No.

- High Cohesion

Verdict: Correct

Explanation: The component strictly complies with its conceptual purpose. It focuses entirely on calculating category-specific discounts, even though its internal code utilizes poor design techniques.

- Polymorphism

Verdict: Violation

ID: V-14

Module: CategoryDiscount.calculateDiscount() type evaluation loops

Explanation: The class severely violates this principle by using explicit type-checking structures (instanceof VideoGame, instanceof Console, instanceof Accessory) combined with magic strings instead of exploiting clean polymorphism.

Consequence: Adding any new product category to the store catalog will break the calculation logic, preventing dynamic system extensibility.

Proposed Solution: Replace the type evaluation branch rules with a uniform polymorphic string evaluation mechanism driven by the product superclass.

Other Member Code: No.

- Pure Fabrication

Verdict: Not Applicable

Explanation: The class maps a direct, concrete commercial concept from the real-world business domain (a category discount rule) rather than an artificial technical utility class built for system support.

- Indirection

Verdict: Correct

Explanation: The component serves as an intermediate architectural layer that mediates between the business sale transaction model and the specific discount calculations for this promotion variant.

- Protected Variations

Verdict: Violation

ID: V-15

Module: Promotions (Requerimiento 2)

Evidence: CategoryDiscount calculation verification blocks

Explanation: The class violates this principle because it fails to protect the code against change. If a new type of product is introduced into the store system, this strategy class must be manually opened and modified.

Consequence: The system layer becomes fragile and highly volatile every time the core business domain model expands its inventory categories.

Proposed Solution: Wrap the variable classification validation rules under a stable, standard product property contract layout.

Other Member Code: No.


# BulkPurchaseDiscount.java Audit

- Information Expert

Verdict: Correct

Evidence: BulkPurchaseDiscount.calculateDiscount() body

Explanation: The class acts as the correct expert because it encapsulates and knows its own configuration parameters, which are the minimum threshold (minimumQuantity) and the percentage to apply (discountPercentage).

- Creator

Verdict: Not Applicable

Explanation: This class carries no structural initialization responsibilities and does not hold the responsibility to instantiate other complex domain software components.

- Controller

Verdict: Not Applicable

Explanation: The class is a pure domain entity and behavioral strategy layout. It has no contact with capturing user interface commands, menu loops, or managing system event streams.

- Low Coupling

Verdict: Correct

Explanation: It exhibits minimal and clean coupling boundaries since it only binds directly to its abstract superclass contract (Promotion) and the generic transaction interface (Sale).

- High Cohesion

Verdict: Correct

Explanation: The class exhibits a single and well-defined responsibility: calculating the commercial discount based strictly on bulk purchase volume.

- Polymorphism

Verdict: Correct

Evidence: @Override public double calculateDiscount(Sale sale)

Explanation: Unlike the CategoryDiscount class, this component successfully realizes the polymorphic contract of the Promotion hierarchy by cleanly implementing calculateDiscount(Sale sale) without type conditional statements or instanceof checks.

- Pure Fabrication

Verdict: Not Applicable

Explanation: The class maps a direct, concrete commercial concept from the real-world business domain (a wholesale/bulk discount) rather than an artificial technical utility class built for system support.

- Indirection

Verdict: Correct

Explanation: The component acts as a concrete operational strategy layer (Strategy pattern) that effectively mediates between the business sale transaction model and the final discount calculations.

- Protected Variations

Verdict: Correct

Explanation: It safely encapsulates the unique wholesale volume calculation rules internally, protecting and shielding the rest of the system classes from modifications.


# Return.java Audit

- Information Expert

Verdict: Correct

Evidence: Return class properties and list handling

Explanation: As the aggregator of the return transaction, the class naturally knows the returned products and encapsulates the state data necessary to compute the proportional refund locally.

- Creator

Verdict: Not Applicable

Explanation: The class does not create instances of sales or products. It safely receives them already instantiated as parameters associated with the current transaction context.

- Controller

Verdict: Not Applicable

Explanation: The class behaves as a core domain data and business rule entity. It has no contact with capturing user interface commands, terminal inputs, or system event streams.

- Low Coupling

Verdict: Correct

Explanation: It exhibits thin and safe coupling boundaries because it only links directly to the Sale transaction contract and the abstract Product superclass, without knowing or referencing any concrete item subtypes.

- High Cohesion

Verdict: Violation

ID: V-16

Module: Returns (Requerimiento 3)

Evidence: Return.generateReturnReceipt() method layout

Explanation: The class features a partial violation with an observation. While it remains highly focused on modeling the return transaction concepts, it dilutes its core cohesion by mixing raw domain properties with text receipt formatting and layout layout parameters.

Consequence: Any structural update to the layout print design or a language localization change forces dangerous manual code modifications directly inside this business entity.

Proposed Solution: Relocate the text formatting and receipt layout construction code blocks into an outer view helper or a separate printing service utility.

Other Member Code: No.

- Polymorphism

Verdict: Correct

Evidence: Returned products evaluation loops

Explanation: The class cleanly acts as a polymorphic consumer. It processes and iterates over the collection of returned items in a 100% polymorphic manner, without utilizing type-checking operations or instanceof checks.

- Pure Fabrication

Verdict: Not Applicable

Explanation: The component maps a concrete, physical commercial concept from the real-world business model (a transaction return process) rather than an artificial technical class built for system engineering support.

- Indirection

Verdict: Correct

Explanation: The component functions as a clean design layer that effectively encapsulates the historical trace and the financial adjustments between the original transaction (Sale) and the individual returned products.

- Protected Variations

Verdict: Correct

Explanation: Introducing new types of custom items into the store dynamic product catalog will not impact or break the execution of the return verification logic because the module interacts strictly with stable base abstractions.


# Sale.canBeReturned() Method Audit

- Information Expert

Verdict: Correct

Evidence: Sale.canBeReturned() execution scope

Explanation: The method complies in essence with this pattern because it directly evaluates internal transaction parameters (the sale date attribute) to resolve whether the item is within the store policy timeframe.

- Creator

Verdict: Not Applicable

Explanation: This specific method contains no instantiation responsibility and does not trigger creation routines for any domain or system components.

- Controller

Verdict: Not Applicable

Explanation: The method executes core domain evaluation logic and has no contact with capturing user interface commands, terminal menus, or application interaction loops.

- Low Coupling

Verdict: Violation

ID: V-17

Module: Returns (Requerimiento 3)

Evidence: Sale.canBeReturned() string parsing (Line 151)

Explanation: The method features a partial violation with an observation. While it operates within the boundaries of the transaction parameters, it suffers from Primitive Obsession by forcing a low-level text string to be parsed into a date object during evaluation.

Consequence: Altering date field presentation schemas or formatting properties inside the application will directly cause runtime execution errors inside this verification block.

Proposed Solution: Modify the underlying model property to manage a native java.time.LocalDate data type from its initialization to decouple the calculation logic.

Other Member Code: No.

- High Cohesion

Verdict: Correct

Explanation: The business validation logic to determine whether an entire transaction item is eligible for a return policy naturally belongs to the operational scope and lifecycle of this method block.

- Polymorphism

Verdict: Not Applicable

Explanation: There is no point of application for this pattern since there are no alternative operational paths or subtype variations within this policy verification workflow.

- Pure Fabrication

Verdict: Not Applicable

Explanation: The calculation logic is an organic domain business validation check directly required by the retail workflow, rather than an artificial structural helper class.

- Indirection

Verdict: Correct

Explanation: The method safely wraps the calendar check logic natively, preventing outer application service handlers from needing to manually compute days between transactions or date metrics.

- Protected Variations

Verdict: Violation

ID: V-18

Module: Returns (Requerimiento 3)

Evidence: Hardcoded return limits and current date lookups (Lines 152-153)

Explanation: The method partially violates this principle because it embeds the 30-day temporal threshold limit and the direct call to LocalDate.now() fixed (hardcoded) inside its operational path, leaving the policy unprotected against alterations.

Consequence: If store return policies shift or if automated testing strategies require injecting a specific temporal timeline to mock system behaviors, the validation routine breaks.

Proposed Solution: Inject the policy threshold parameter externally via system configuration properties or pass the evaluation timeline directly as a method signature contract.

Other Member Code: No.


# Warranty.java Audit

- Information Expert

Verdict: Correct

Evidence: Warranty.isActive() method layout

Explanation: The class operates as the correct expert because it directly manages its coverage timeframe and natively evaluates its own validity through the isActive(LocalDate date) routine.

- Creator

Verdict: Not Applicable

Explanation: Since it is explicitly declared as an abstract class, it cannot be instantiated directly using the new keyword, and it does not instantiate other entities or itself.

- Controller

Verdict: Not Applicable

Explanation: The class is a core domain model entity. It has no contact with capturing user interface commands, menu loops, or managing application event streams.

- Low Coupling

Verdict: Correct

Explanation: It exhibits minimal and clean coupling boundaries since it only binds directly to the stable abstractions Product and Sale, and the standard platform data type LocalDate.

- High Cohesion

Verdict: Correct

Explanation: The class maintains a clear and focused responsibility, which is strictly limited to modeling the characteristics and lifecycle of a commercial warranty.

- Polymorphism

Verdict: Correct

Evidence: Use of abstract hooks getDurationInMonths(), getWarrantyType(), and getAdditionalCost()

Explanation: The component complies exemplarily with this pattern by defining abstract polymorphic method hooks to delegate variable subtype characteristics directly to its subclasses.

- Pure Fabrication

Verdict: Not Applicable

Explanation: The class maps a direct, concrete commercial concept and an organic contract from the real-world retail store business domain (a warranty) rather than an artificial technical utility class built for system support.

- Indirection

Verdict: Correct

Explanation: The component serves as an intermediate architectural layer that effectively mediates between the product sale transaction model and the various post-sale coverage policies.

- Protected Variations

Verdict: Correct

Evidence: public abstract class Warranty structure

Explanation: The class acts as an abstract contract that complies exemplarily with this principle, effectively protecting and shielding the rest of the system from modifications whenever a new type of warranty is introduced.


# BasicWarranty.java Audit

- Information Expert

Verdict: Correct

Evidence: BasicWarranty class method overrides

Explanation: The class acts as the correct expert because it knows its own specific business rules, which are a duration of 6 months, an additional cost of $0.0, and its descriptive label text.

- Creator

Verdict: Not Applicable

Explanation: This class does not carry any initialization responsibilities to create or instantiate other classes within the system architecture.

- Controller

Verdict: Not Applicable

Explanation: The class is a core domain data entity. It has no contact with managing system events, interface components, or orchestrating core software use cases.

- Low Coupling

Verdict: Correct

Explanation: It exhibits minimal and clean coupling boundaries since it only binds directly to its superclass Warranty and the necessary entities required in the constructor parameters (Product, Sale, LocalDate).

- High Cohesion

Verdict: Correct

Explanation: This is an extremely compact class (48 lines) with a single and well-defined goal: defining the fixed operational parameters of a basic baseline warranty.

- Polymorphism

Verdict: Correct

Evidence: @Override implementation of abstract warranty contracts

Explanation: The class successfully complies with this pattern by cleanly implementing the three polymorphic methods inherited from Warranty (getDurationInMonths, getWarrantyType, getAdditionalCost).

- Pure Fabrication

Verdict: Not Applicable

Explanation: The class maps a direct, concrete commercial concept and a contract from the real-world store business domain (a standard/base warranty) rather than an artificial technical utility class built for system support.

- Indirection

Verdict: Correct

Explanation: The component functions as a concrete leaf node within the architecture's structural indirection tree driven by inheritance (Warranty -> BasicWarranty).

- Protected Variations

Verdict: Correct

Explanation: It safely encapsulates all the internal rules and conditions of the basic warranty inside its own boundaries without creating unexpected impacts across the rest of the system layout.


# ExtendedWarranty.java Audit

- Information Expert

Verdict: Correct

Evidence: ExtendedWarranty.getAdditionalCost() implementation

Explanation: The class acts as the correct expert because it knows its own parameters and safely queries the price of the associated product to calculate the 10% additional cost surcharge natively.

- Creator

Verdict: Not Applicable

Explanation: This class does not carry any initialization responsibilities to create or instantiate other classes within the system architecture.

- Controller

Verdict: Not Applicable

Explanation: The class is a core domain data entity. It has no contact with managing system events, interface components, or orchestrating core software use cases.

- Low Coupling

Verdict: Correct

Explanation: It exhibits minimal and clean coupling boundaries since it only binds directly to its superclass Warranty and the necessary entities required in the constructor parameters (Product, Sale, LocalDate).

- High Cohesion

Verdict: Correct

Explanation: This is a small class (48 lines) focused exclusively on a single and well-defined purpose: defining the conditions and operational metrics of an extended warranty.

- Polymorphism

Verdict: Correct

Evidence: @Override implementation of abstract warranty contracts

Explanation: The class successfully complies with this pattern by overriding the three methods of Warranty, dynamically resolving the timeframe (12 months) and the operational formula for the additional cost surcharge.

- Pure Fabrication

Verdict: Not Applicable

Explanation: The class maps a direct, concrete commercial product/service concept offered in the real-world store business domain rather than an artificial technical utility class built for system support.

- Indirection

Verdict: Correct

Explanation: The component functions as a concrete leaf node within the architecture's structural indirection tree driven by inheritance (Warranty -> ExtendedWarranty).

- Protected Variations

Verdict: Correct

Explanation: It safely encapsulates the unique 10% cost calculation formula and the 12-month coverage timeframe internally without creating unexpected impacts across the rest of the system layout.

# audit of exceptions and validations

# GameZoneException.java Audit

- Information Expert

Verdict: Correct

Evidence: GameZoneException.getErrorCode() implementation (Lines 32-34)

Explanation: The class acts as the correct information expert by encapsulating the root error message and the application-level errorCode, providing a dedicated accessor to retrieve it without exposing internal variables.

- Creator

Verdict: Not Applicable

Explanation: As an abstract base exception class, it does not carry responsibility for instantiating or managing other domain entities.

- Controller

Verdict: Not Applicable

Explanation: It is an infrastructure error abstraction. It does not handle user input, interface actions, or use-case orchestration.

- Low Coupling

Verdict: Correct

Explanation: It maintains minimal coupling by depending solely on the core Java runtime library (java.lang.RuntimeException and java.lang.String). It has zero dependencies on domain models or persistence frameworks.

- High Cohesion

Verdict: Correct

Explanation: The class is compact (38 lines) and focused strictly on establishing the base unchecked exception contract with standardized error codes for the entire application.

- Polymorphism

Verdict: Correct

Evidence: public abstract class GameZoneException extends RuntimeException declaration (Line 6)

Explanation: It defines the root polymorphic type for all application errors, allowing high-level services and presentation handlers to catch GameZoneException polymorphically and inspect error codes without type branching.

- Pure Fabrication

Verdict: Correct

Explanation: Exceptions are artificial software constructs created to decouple failure signaling from normal business logic execution paths.

- Indirection

Verdict: Correct

Explanation: It mediates between low-level failure sites and higher-level application error consumers, establishing a standard error reporting contract.

- Protected Variations

Verdict: Violation

ID: V-19

Module: Exceptions (Requerimiento 6)

Evidence: GameZoneException constructor declarations (Lines 14-26) lacking Throwable cause chaining

Explanation: The base class fails to provide constructors that accept a Throwable cause parameter (e.g., GameZoneException(String message, Throwable cause)). This breaks the standard Java exception chaining contract, leaving the hierarchy unprotected when wrapping low-level system exceptions.

Consequence: Subclasses that wrap checked or external exceptions (such as PersistenceException wrapping IOException) cannot pass the cause to super(...) and are forced to use post-construction workarounds like initCause().

Proposed Solution: Add overloaded constructors in GameZoneException that accept a Throwable cause and delegate to super(message, cause).

Other Member Code: No.

---

# InvalidDataException.java Audit

- Information Expert

Verdict: Violation

ID: V-20

Module: Exceptions (Requerimiento 6)

Evidence: Constructor implementation super("el campo " + fieldName + " es inválido: " + problem, "INVALID_DATA") (Lines 14-16)

Explanation: The class violates the Information Expert principle because it discards the structured parameters fieldName and problem upon instantiation, concatenating them into a raw string rather than encapsulating them as accessible attributes.

Consequence: Consuming presentation or API layers cannot query ex.getFieldName() programmatically to highlight the specific input field in the user interface, forcing consumers to rely on fragile string parsing.

Proposed Solution: Add private final fields private final String fieldName; and private final String problem; with public getter methods, while passing the formatted string to super.

Other Member Code: No.

- Creator

Verdict: Not Applicable

Explanation: It carries no responsibility for creating, tracking, or managing other application objects.

- Controller

Verdict: Not Applicable

Explanation: It is a specialized domain error class and does not process system events or coordinate workflows.

- Low Coupling

Verdict: Correct

Explanation: It depends exclusively on its superclass GameZoneException and Java String parameters, maintaining loose coupling with the rest of the application.

- High Cohesion

Verdict: Correct

Explanation: The class is concise (18 lines) and focused exclusively on signaling field-level validation and format failures.

- Polymorphism

Verdict: Correct

Evidence: Subclassing of GameZoneException with the specialized "INVALID_DATA" code

Explanation: It fulfills the polymorphic exception hierarchy by allowing field validation errors to be captured specifically or handled generically as GameZoneException.

- Pure Fabrication

Verdict: Correct

Explanation: It is an artificial design construction engineered to standardize validation failure reporting.

- Indirection

Verdict: Correct

Explanation: It decouples input validation routines from the visual error presentation layer.

- Protected Variations

Verdict: Correct

Explanation: Encapsulates validation failure classification into a distinct type, protecting callers from relying on generic runtime exceptions.

---

# BusinessRuleException.java Audit

- Information Expert

Verdict: Correct

Evidence: Constructor delegating to super(message, "BUSINESS_RULE_VIOLATION") (Lines 12-14)

Explanation: The class acts as the correct expert in identifying domain invariant violations, stamping each instance with the authoritative "BUSINESS_RULE_VIOLATION" system code.

- Creator

Verdict: Not Applicable

Explanation: It does not instantiate or initialize other objects in the system.

- Controller

Verdict: Not Applicable

Explanation: It is a data error entity without presentation or workflow orchestration roles.

- Low Coupling

Verdict: Correct

Explanation: It depends only on GameZoneException and standard Java String classes.

- High Cohesion

Verdict: Correct

Explanation: The class is strictly focused on a single concern (16 lines): signaling violations of domain business rules (such as stock deficits or empty sale carts).

- Polymorphism

Verdict: Correct

Evidence: Subclass of GameZoneException providing concrete rule violation classification

Explanation: It allows business rule infractions to be dispatched and handled polymorphically at upper layers.

- Pure Fabrication

Verdict: Correct

Explanation: It is a pure technical fabrication created to segregate business rule rejections from technical failures.

- Indirection

Verdict: Correct

Explanation: It decouples domain rule enforcement points from presentation reporting.

- Protected Variations

Verdict: Correct

Explanation: It isolates semantic business rule failures from infrastructure errors, protecting callers from ambiguous error handling.

---

# PersistenceException.java Audit

- Information Expert

Verdict: Correct

Evidence: Constructor setting "PERSISTENCE_ERROR" and preserving root cause via initCause(cause) (Lines 12-15)

Explanation: The class acts as the information expert for storage failures by binding the high-level persistence failure message, assigning the "PERSISTENCE_ERROR" code, and retaining the underlying root cause exception.

- Creator

Verdict: Not Applicable

Explanation: It does not create or manage domain or storage entities.

- Controller

Verdict: Not Applicable

Explanation: It has no operational role in handling UI events or use-case execution.

- Low Coupling

Verdict: Correct

Explanation: Despite representing storage issues, it maintains low coupling by depending only on GameZoneException and Throwable, without coupling to concrete file paths or database APIs.

- High Cohesion

Verdict: Correct

Explanation: It has a single, well-defined responsibility (17 lines): signaling input/output and data storage failures.

- Polymorphism

Verdict: Correct

Evidence: Extension of GameZoneException wrapping lower-level causes

Explanation: It integrates into the polymorphic error hierarchy, allowing persistence failures to be caught uniformly without exposing I/O details.

- Pure Fabrication

Verdict: Correct

Explanation: An artificial design construction created to abstract raw storage errors from domain logic.

- Indirection

Verdict: Correct

Evidence: Wrapping checked IOException instances into unchecked PersistenceException

Explanation: It serves as an indirection barrier, translating low-level checked system exceptions into domain-level runtime exceptions.

- Protected Variations

Verdict: Violation

ID: V-21

Module: Exceptions (Requerimiento 6)

Evidence: Manual workaround initCause(cause);; in constructor (Line 14)

Explanation: Because GameZoneException lacks a constructor accepting a Throwable cause, PersistenceException is forced to execute a post-super manual initialization (initCause(cause)) containing a redundant double semicolon syntax defect, violating clean Protected Variations for exception wrapping.

Consequence: If a cause has already been set or initialized prior to this call, initCause() throws an IllegalStateException at runtime, creating an unstable exception propagation mechanism.

Proposed Solution: Update GameZoneException with a constructor GameZoneException(String message, String errorCode, Throwable cause) and invoke super(message, "PERSISTENCE_ERROR", cause) directly.

Other Member Code: No.

---

# ResourceNotFoundException.java Audit

- Information Expert

Verdict: Violation

ID: V-22

Module: Exceptions (Requerimiento 6)

Evidence: Constructor implementation super("El " + resourceType + " con identificador " + resourceId + " no fue encontrado", "RESOURCE_NOT_FOUND") (Lines 14-17)

Explanation: The class violates the Information Expert principle because it accepts resourceType and resourceId but fails to retain them as encapsulated, queryable attributes, concatenating them into a plain string message upon construction.

Consecuence: Consuming services and controllers cannot programmatically extract the missing resource type or ID (e.g., to trigger an automated redirect, create an audit log, or return a structured JSON response) without fragile string manipulation.

Proposed Solution: Define private final String resourceType; and private final String resourceId; fields with public getters, retaining the structured data alongside the formatted super message.

Other Member Code: No.

- Creator

Verdict: Not Applicable

Explanation: It does not instantiate or initialize other system objects.

- Controller

Verdict: Not Applicable

Explanation: It is a domain error representation that does not coordinate UI actions or system workflows.

- Low Coupling

Verdict: Correct

Explanation: It maintains loose coupling by relying solely on GameZoneException and String parameters, avoiding coupling to concrete entity classes like Product or Sale.

- High Cohesion

Verdict: Correct

Explanation: Focused strictly on a single concern (19 lines): signaling missing entity lookup failures.

- Polymorphism

Verdict: Correct

Evidence: Subclass of GameZoneException providing semantic not-found classification

Explanation: It integrates into the polymorphic error structure, allowing upper layers to capture not-found conditions specifically or handle them via the generic GameZoneException handler.

- Pure Fabrication

Verdict: Correct

Explanation: An artificial software artifact designed to standardize not-found error conditions without polluting domain models.

- Indirection

Verdict: Correct

Explanation: It acts as an intermediary signaling device between repository lookup misses and user-facing error presentation layers.

- Protected Variations

Verdict: Correct

Explanation: It encapsulates missing-resource reporting behind a uniform contract, protecting consumers from inconsistent null checks and custom missing-record string formats across the application.

# PersonValidator.java Audit

- Information Expert

Verdict: Correct

Evidence: PersonValidator.validatePersonData(String id, String name, String email, String phone) implementation (Lines 19-34)

Explanation: The class acts as the correct information expert for structural input format validation, verifying syntactic integrity rules such as non-empty identification, email format containing '@' and '.', and phone numbers matching the 10-digit regex specification ("\\d{10}").

- Creator

Verdict: Not Applicable

Explanation: It is a stateless verification utility class. It has no responsibility to instantiate, track, or manage the lifecycle of Person entities.

- Controller

Verdict: Not Applicable

Explanation: The class operates purely as an assertion utility. It does not handle user input events, manage system workflows, or coordinate use cases.

- Low Coupling

Verdict: Violation

ID: V-23

Module: Validations (Requerimiento 6)

Evidence: PersonValidator static method declarations (Lines 19, 42, 54)

Explanation: Implementing validations as public static methods tightly couples consumers (such as PersonService) to the concrete PersonValidator class at compile time. Consuming classes cannot inject mock validators or substitute alternative implementations.

Consequence: Unit testing PersonService in total isolation from validation side-effects is impossible without bytecode manipulation tools. Any architectural shift in validation signatures directly breaks all callers.

Proposed Solution: Refactor PersonValidator into an instantiable component implementing a common Validator<Person> interface and inject it via constructor into PersonService.

Other Member Code: No.

- High Cohesion

Verdict: Correct

Explanation: The class is compact (64 lines) and exhibits high cohesion, focusing exclusively on a single concern: verifying data integrity, uniqueness, and existence preconditions for persons.

- Polymorphism

Verdict: Violation

ID: V-24

Module: Validations (Requerimiento 6)

Evidence: PersonValidator procedural static routines lacking interface contracts (Lines 19-60)

Explanation: The class bypasses object-oriented polymorphism entirely by utilizing static procedural methods rather than implementing a shared validation contract (e.g., Validator<T>).

Consequence: Validation behaviors cannot be dynamically substituted or specialized for Person subtypes (such as Client vs. Seller) through polymorphic dispatch.

Proposed Solution: Introduce a Validator<T> generic interface and implement polymorphic validation strategies for distinct domain types.

Other Member Code: No.

- Pure Fabrication

Verdict: Correct

Explanation: PersonValidator is an artificial software construct engineered to keep the Person domain model and PersonService free from repetitive boilerplate validation logic.

- Indirection

Verdict: Correct

Explanation: It acts as an indirection barrier between raw user inputs and entity instantiation, preventing corrupted or malformed person data from entering the application core.

- Protected Variations

Verdict: Violation

ID: V-25

Module: Validations (Requerimiento 6)

Evidence: Hardcoded regex rules in Line 31 (phone.matches("\\d{10}")) and direct static calls

Explanation: Client services are tightly coupled to concrete static validation routines and hardcoded regex rules without an interface or configuration boundary protecting against changing validation criteria.

Consequence: Any shift in validation requirements (such as international phone numbers or distinct email formats) forces direct code modifications in core validation files and cascades into client callers.

Proposed Solution: Encapsulate validation rules behind an interface or configurable strategy pattern to isolate client services from format policy variations.

Other Member Code: No.

---

# ProductValidator.java Audit

- Information Expert

Verdict: Violation

ID: V-26

Module: Validations (Requerimiento 6)

Evidence: ProductValidator.validateStockAvailability(Product product, int requestedQuantity) method (Lines 39-45)

Explanation: The class violates the Information Expert principle regarding stock validation because Product holds its own inventory state and business invariants. Extracting stock via getters to evaluate thresholds externally creates an Anemic Domain Model; the check belongs naturally within Product (e.g., product.hasStock(requestedQuantity) or product.decreaseStock(quantity)). However, it correctly acts as an expert for checking raw input parameter boundaries (id, title, price, stock).

Consequence: Fosters an Anemic Domain Model where the Product class is reduced to a passive data bag, while business invariants are scattered across external static utility classes.

Proposed Solution: Move stock availability validation inside the Product domain model (e.g., product.hasStock(requestedQuantity) or product.decreaseStock(quantity)).

Other Member Code: No.

- Creator

Verdict: Not Applicable

Explanation: It is a stateless validation utility class that carries no responsibilities for creating, instantiating, or tracking domain entities.

- Controller

Verdict: Not Applicable

Explanation: It is an assertion utility component. It has no contact with capturing UI events, handling user input, or orchestrating software use cases.

- Low Coupling

Verdict: Violation

ID: V-27

Module: Validations (Requerimiento 6)

Evidence: ProductValidator static method declarations (Lines 18, 39, 52)

Explanation: Exposing all validation routines as public static methods binds consumers such as ProductService directly to this concrete class at compile time.
Consequence: Prevents dependency injection and mock substitution in unit testing suites, creating rigid dependencies throughout the application core.

Proposed Solution: Convert ProductValidator into an instantiable component implementing a Validator<Product> interface and inject it through constructor injection in ProductService.

Other Member Code: No.

- High Cohesion

Verdict: Correct

Explanation: The class has high cohesion (58 lines) focused exclusively on a single area of responsibility: asserting boundary preconditions, stock rules, and existence checks for products.

- Polymorphism

Verdict: Violation

ID: V-28

Module: Validations (Requerimiento 6)

Evidence: Absence of a polymorphic interface hierarchy for product validation (Lines 18-56)

Explanation: The class does not leverage polymorphism. It cannot be extended or dynamically dispatched to vary validation rules for different product categories or operating environments, relying on fixed static calls instead.

Consequence: Adding new product categories requires either creating new static methods or modifying existing validation blocks, violating the Open/Closed Principle.

Proposed Solution: Define a polymorphic Validator<T> contract that can be implemented per product category.

Other Member Code: No.

- Pure Fabrication

Verdict: Correct

Explanation: A validator is a quintessential Pure Fabrication: an artificial technical construct designed to prevent domain entities and application services from being overloaded with repetitive assertion logic.

- Indirection

Verdict: Correct

Explanation: It acts as an indirection barrier between service workflows and raw input arguments, shielding business workflows from corrupted or negative data.

- Protected Variations

Verdict: Violation

ID: V-29

Module: Validations (Requerimiento 6)

Evidence: Static method invocation points across ProductService

Explanation: Callers are directly bound to fixed static methods. The class provides no abstraction layer to protect callers if validation strategies change (such as switching from fail-fast exception throwing to an error accumulator pattern).

Consequence: Any redesign of validation error reporting or rule evaluation mandates intrusive modifications to all calling service layers.

Proposed Solution: Introduce an interface-based validation contract to decouple callers from validation execution mechanics.

Other Member Code: No.

---

# SaleValidator.java Audit

- Information Expert

Verdict: Correct

Evidence: SaleValidator.validateSaleData(Client client, Seller seller, List<Product> products) implementation (Lines 23-33)

Explanation: The class acts as the correct expert for cross-cutting transaction preconditions, verifying that all mandatory participants (client, seller) and non-empty items are provided before the sale is processed or constructed.

- Creator

Verdict: Not Applicable

Explanation: This class does not create or instantiate objects; it merely evaluates references and raises domain exceptions upon contract violation.

- Controller

Verdict: Not Applicable

Explanation: It performs validation assertions and does not coordinate presentation events or manage use-case lifecycles.

- Low Coupling

Verdict: Violation

ID: V-30

Module: Validations (Requerimiento 6)

Evidence: Static procedural methods (public static void) coupled to multiple domain models (Lines 23, 40)

Explanation: Implementing validations as static methods creates rigid compile-time coupling with consuming services, preventing mock substitution and dependency inversion in unit test suites.

Consequence: Restricts unit testing by preventing mock substitution and introduces tight compile-time coupling across transactional components.

Proposed Solution: Turn SaleValidator into a non-static service or bean implementing a Validator<Sale> contract and inject it via constructor.

Other Member Code: No.

- High Cohesion

Verdict: Correct

Explanation: It is a concise class (47 lines) focused strictly on verifying the preconditions, participant presence, and persistence existence checks required for sales transactions.

- Polymorphism

Verdict: Violation

ID: V-31

Module: Validations (Requerimiento 6)

Evidence: Static method signatures without polymorphic abstraction (Lines 23-44)

Explanation: It relies entirely on static procedural routines. It cannot participate in polymorphic validation pipelines or strategy-driven validation rules.

Consequence: New sale validation rules cannot be plugged in dynamically; they must be procedurally hardcoded into static methods.

Proposed Solution: Implement a polymorphic validation strategy pattern based on a common Validator interface.

Other Member Code: No.

- Pure Fabrication

Verdict: Correct

Explanation: It is a pure technical utility class invented to keep the Sale model and SaleService clean, preventing validation boilerplate from polluting business and persistence logic.

- Indirection

Verdict: Correct

Explanation: It introduces an indirection checkpoint between incoming transaction parameters and sale creation, ensuring only validated data flows into downstream components.

- Protected Variations

Verdict: Violation

ID: V-32

Module: Validations (Requerimiento 6)

Evidence: Direct static method invocations across consuming services

Explanation: The static design does not protect client classes from changes in validation mechanisms or alternative validation rules (e.g., wholesale rules vs. retail rules), requiring direct code modifications in callers if validation contracts evolve.

Consequence: Calling services are tightly bound to the current static exception-throwing implementation and will require rewrites if validation mechanisms evolve.

Proposed Solution: Wrap sale validation logic behind an injectable interface to preserve Protected Variations.

Other Member Code: No.