1. Should accessories extend the existing Product hierarchy or form an independent hierarchy? Justify your decision considering code reuse and model coherence.

Accessories should extend the existing Product hierarchy rather than form an independent one. All three accessory types (Controller, Cable, Memory) share the same base attributes as any other sellable item in the store: an identifier, a title, a price, and a stock quantity. Extending Product lets Accessory reuse these attributes and their getters/setters instead of duplicating them.

More importantly, SaleService already depends on Product to register sales, validate stock, and calculate totals. If accessories were a separate hierarchy, SaleService would need parallel logic to handle two unrelated item types, doubling the validation and total-calculation code. By making Accessory extends Product, a sale can hold a single list of Product references that includes videogames, consoles, and accessories interchangeably, and all of them respond to the same abstract getDescription() contract through polymorphism. This keeps the model coherent and avoids duplicating logic across two independent trees.

2. What attributes are common to the three accessory types and which are specific to each type? How is this distinction reflected in the module's class hierarchy?

The three accessory types share the attributes already inherited from Product (id, title, price, stockQuantity), plus one additional attribute common to accessories specifically: the list of compatible console IDs (compatibleConsoleIds), declared in Accessory.

Each concrete subclass adds its own specific attributes: Controller adds connectionType; Cable adds lengthInMeters and connectorType; Memory adds capacityInGb and memoryType.

This distinction is reflected in the hierarchy by placing every shared attribute and behavior in the abstract Accessory class — including the compatibility list and its query/modification methods (isCompatibleWith, addCompatibleConsole, removeCompatibleConsole) — while each concrete subclass only declares what is unique to it. Accessory also provides a base implementation of getDescription() covering the shared information, which each subclass extends via super.getDescription() to append its specific attributes, avoiding duplicated description logic across the three subclasses.

3. The compatibility between an accessory and a console is a relationship between two entities in the system. How is this relationship represented in the design and in persistence? Is compatibility an attribute of the accessory, of the console, or of both?

Compatibility is represented as an attribute of the accessory only, not of the console. Accessory holds a `List<String> compatibleConsoleIds`, storing the IDs of the consoles it works with, along with the methods to query and modify that list (`isCompatibleWith`, `addCompatibleConsole`, `removeCompatibleConsole`). The Console class itself is not modified and has no knowledge of which accessories are compatible with it.

We chose this direction for two reasons. First, it keeps the change strictly additive: since Console belongs to another team member's module, storing the relationship on the accessory side avoids touching a class outside our responsibility. Second, it matches how the relationship is actually queried in the system — AccessoryService.findAccessoriesCompatibleWith(consoleId) needs to answer "which accessories work with this console", which is a natural filter over the accessory list rather than a lookup that would require Console to hold references back to accessories.

In persistence, the list of console IDs is serialized as a single field within the accessory's own record in data/accessories.csv, using a secondary separator (";") to join multiple IDs into one field (e.g. CO-001;CO-002). AccessoryRepository splits that field back into a List<String> when loading. This avoids creating a separate join file or table just for the relationship, keeping persistence for the accessory module self-contained in a single file.
4. What modifications are necessary in SaleService so that sales can include accessories without breaking the existing behavior with video games and consoles?

SaleService must be extended to recognize accessories as valid Product objects while preserving the existing sales behavior for video games and consoles. Since Accessory extends Product, the sale can continue using a single List<Product> without changing the Sale model or duplicating the sales structure.

The main modification is to validate the stock of each item according to its type. When an item is an Accessory, SaleService delegates the search and stock update to AccessoryService. For existing products such as VideoGame and Console, it continues using ProductService. This keeps each service responsible for the inventory it manages.

During the registration process, SaleService first validates that the sale contains at least one item and that all required client and seller information is valid. It then verifies the availability of every product or accessory before modifying any inventory. After all items pass validation, the corresponding service updates their stock.

The sale total continues to be calculated through the common Product abstraction, allowing video games, consoles, and accessories to participate in the same transaction. This approach preserves the existing sales behavior while extending it to the new accessory types without duplicating the complete sales process.

5. In which layer of the system architecture should the new accessory module classes be located? Justify your decision based on the responsibilities of each layer.

The new accessory classes should be distributed across the existing layers according to their responsibilities. The domain classes Accessory, Controller, Cable, and Memory belong in the model layer because they represent the entities and characteristics of the accessory domain.

AccessoryRepository belongs in the persistence layer because its responsibility is to read and write accessory information to the physical data file. It should not contain business rules or user interface logic.

AccessoryService belongs in the service layer because it contains the business rules for registering, listing, searching, validating, and updating accessory inventory. It also provides the operations required by other parts of the system without exposing persistence details.

Any accessory-related options presented to the user belong in the ui layer, where the console menu handles input and output and delegates operations to the corresponding service.

This organization preserves the existing layered architecture and separation of concerns. The model represents the domain, persistence manages data storage, service manages business rules, and ui manages interaction with the user. As a result, the accessory module can be integrated without mixing responsibilities or creating unnecessary dependencies between layers.
