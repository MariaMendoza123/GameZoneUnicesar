1. Should accessories extend the existing Product hierarchy or form an independent hierarchy? Justify your decision considering code reuse and model coherence.

Accessories should extend the existing Product hierarchy rather than form an independent one. All three accessory types (Controller, Cable, Memory) share the same base attributes as any other sellable item in the store: an identifier, a title, a price, and a stock quantity. Extending Product lets Accessory reuse these attributes and their getters/setters instead of duplicating them.

More importantly, SaleService already depends on Product to register sales, validate stock, and calculate totals. If accessories were a separate hierarchy, SaleService would need parallel logic to handle two unrelated item types, doubling the validation and total-calculation code. By making Accessory extends Product, a sale can hold a single list of Product references that includes videogames, consoles, and accessories interchangeably, and all of them respond to the same abstract getDescription() contract through polymorphism. This keeps the model coherent and avoids duplicating logic across two independent trees.

2. What attributes are common to the three accessory types and which are specific to each type? How is this distinction reflected in the module's class hierarchy?

The three accessory types share the attributes already inherited from Product (id, title, price, stockQuantity), plus one additional attribute common to accessories specifically: the list of compatible console IDs (compatibleConsoleIds), declared in Accessory.

Each concrete subclass adds its own specific attributes: Controller adds connectionType; Cable adds lengthInMeters and connectorType; Memory adds capacityInGb and memoryType.

This distinction is reflected in the hierarchy by placing every shared attribute and behavior in the abstract Accessory class — including the compatibility list and its query/modification methods (isCompatibleWith, addCompatibleConsole, removeCompatibleConsole) — while each concrete subclass only declares what is unique to it. Accessory also provides a base implementation of getDescription() covering the shared information, which each subclass extends via super.getDescription() to append its specific attributes, avoiding duplicated description logic across the three subclasses.