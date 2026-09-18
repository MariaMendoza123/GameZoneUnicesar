# Return Module Analysis
## 1. Relationship between Return and Sale

Return holds a reference to its originalSale through composition of association, not inheritance: Return does not extend Sale, it simply stores a Sale object as one of its attributes.

This relationship is a plain association, not aggregation or composition. Aggregation and composition model whole-part relationships, where one object is structurally made up of another (a Console being "part of" a Sale's product list, for example). Here, Sale is not a part of Return; it is an independent entity that existed before the Return was created and continues to exist regardless of whether a Return referencing it is created, modified, or deleted. Return simply points to (references) an already-existing Sale to know which transaction it originated from.

The multiplicity of this association is one-directional and many-to-one: a single Sale can have zero, one, or several associated Returns (partial returns over time), while each Return references exactly one original Sale.

## 2. Representing partial returns in Return's attributes

Return stores the returned items in a `List<Product> returnedProducts` attribute, separate from the original sale's own product list (`Sale.getProducts()`). This list only contains the specific Product instances the client is returning in that particular transaction, not the full list of products from the original sale.

Since each unit sold is represented as one entry in Sale.products (following the same one-item-per-unit convention already used for sales), returnedProducts follows the same pattern: it stores references to the same Product objects that exist in the original sale, but only the subset the client chose to return. This allows a client to keep some products from a sale while returning others, since ReturnService validates that every product ID given for the return actually belongs to the referenced sale before building this list.

## 3. Return deadline validation

The 30-day deadline validation is placed partly in the model and partly in the service layer, each with a distinct responsibility. Sale.canBeReturned() contains the actual date comparison, using java.time.LocalDate.parse to convert the stored sale date into a LocalDate, and java.time.temporal.ChronoUnit.DAYS.between(saleDate, currentDate) to calculate the number of days elapsed since the sale.

ReturnService.registerReturn(...) is the class that actually enforces the business rule: before creating a Return, it calls sale.canBeReturned() and rejects the operation with an IllegalArgumentException if the sale is no longer within the 30-day window. This belongs in the service layer because deciding whether an operation is allowed to proceed is a business rule, not something the model itself should refuse or the persistence layer should check. Sale simply exposes the fact (can this sale still be returned), while ReturnService decides what to do with that fact.

## 4. Reusing existing stock-update logic

The devolución de productos reuses ProductService.restoreStock(String productId, int quantity), a method added specifically for this requirement in ProductService (the class that already owns the responsibility of managing product inventory). It is invoked from ReturnService.registerReturn(...), once the return has been validated (deadline and ownership) and the Return object has been created, for every returned product that corresponds to an existing catalog product.

Reusing this method instead of duplicating stock-update logic inside ReturnService is important because inventory management is the responsibility of ProductService alone: it already knows how to safely modify a product's stockQuantity and persist that change through ProductRepository. If ReturnService updated stock directly (for example, by casting to Product and calling setStockQuantity itself), the system would have two independent places capable of mutating the same piece of state, risking inconsistencies (e.g., forgetting to persist the change, or applying the update differently than the sales flow does). Delegating to the existing method keeps a single source of truth for stock changes and respects the ui → service → persistence → model dependency direction, since ReturnService (service layer) coordinates with another service rather than reaching into persistence or model logic that isn't its own.

## 5. Location of the monthly balance report

The monthly balance report is implemented in ReturnService, through the generateMonthlyBalance(int month, int year) method. This location is coherent with the layered architecture because generating this report requires combining business data from two different modules (sales and returns) and applying a business rule (net balance = sales total - returns total), which is exactly what the service layer is responsible for: coordinating operations across the domain model on behalf of a use case, without embedding that coordination in the model or the UI.

To generate the report, ReturnService needs two dependencies: SaleService, to retrieve all registered sales and filter them by month and year using their date; and its own list of returns (loaded through ReturnRepository), filtered by the return date using the same month/year criteria. No direct dependency on the persistence layer is needed beyond what ReturnRepository already provides, keeping the ui -> service -> persistence -> model dependency direction intact.
