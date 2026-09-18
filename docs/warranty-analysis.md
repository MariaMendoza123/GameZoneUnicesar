# Warranty Module Analysis

## 1. Warranty hierarchy and polymorphism

The common attributes and behaviors of the two warranty types are defined in the abstract `Warranty` class. It contains the warranty identifier, associated product, associated sale, start date, end date, and the common behavior for checking whether a warranty is active and generating its certificate.

`BasicWarranty` and `ExtendedWarranty` extend `Warranty` and provide their specific rules through method overriding.

The mechanism used to avoid duplicating the duration logic is polymorphism through an abstract method. `Warranty` defines `getDurationInMonths()` as abstract, while each concrete subclass implements its own duration. `BasicWarranty` returns 6 months and `ExtendedWarranty` returns 12 months.

The superclass constructor uses `getDurationInMonths()` to calculate the end date, so the common date calculation is implemented only once in `Warranty` while the duration remains specific to each subclass.

## 2. Automatic basic warranty for consoles

The decision that only consoles receive an automatic basic warranty is a business rule and is implemented in the service layer, specifically in `SaleService.registerSale`.

The Java mechanism used to determine the real type of a product is the `instanceof` operator. The registration flow checks whether each product is an instance of `Console` before assigning the basic warranty.

This decision belongs in the service layer because it controls the business behavior of the sale and warranty assignment. The model classes represent the domain objects, while the console menu only collects user input and delegates the operation.

Using `instanceof Console` also prevents videogames and other product types from receiving an automatic basic warranty.

## 3. Warranty duration and end-date calculation

The end date is calculated differently according to the warranty subtype. `BasicWarranty` returns 6 from `getDurationInMonths()`, while `ExtendedWarranty` returns 12.

The common `Warranty` constructor calculates the end date with:

`startDate.plusMonths(getDurationInMonths())`

This keeps the date calculation in one place and uses polymorphism to obtain the correct duration for each subtype.

The calculation is performed in the constructor because the end date is a derived attribute that depends directly on the start date and the fixed duration rule of the warranty type. Once a warranty object is created, its start and end dates are established consistently.

The subtype-specific behavior remains in the overridden `getDurationInMonths()` methods instead of duplicating the date calculation in both subclasses.

## 4. Extended warranty cost in the sale flow

The extended warranty cost is calculated during the registration of the sale in `SaleService.registerSale`.

The method receives an additional parameter containing the identifiers of the products for which the seller requested extended warranty coverage:

`List<String> productIdsWithExtendedWarranty`

After the sale is created and the product stock has been validated, `SaleService` processes the products in the sale. When a product is a `Console`, the service first assigns the automatic basic warranty. If the console identifier is included in the list of products with extended warranty, the service also assigns an `ExtendedWarranty`.

`ExtendedWarranty.getAdditionalCost()` calculates 10% of the associated product price. The service accumulates the additional warranty cost, stores it in the sale, and includes it in the final sale total.

The existing promotion logic remains responsible for calculating the promotion discount. The warranty cost is handled separately so that the warranty rule does not become part of the promotion classes.

## 5. Location of the expiring-soon warranty query

The `listWarrantiesExpiringSoon(int daysAhead)` method belongs in `WarrantyService`. It needs the full in-memory list of warranties loaded from `WarrantyRepository` through the constructor and Java's `LocalDate` to compute today's date and the upper bound of the search window.

The service filters the warranties whose `endDate` is greater than or equal to today's date and less than or equal to the calculated upper limit.

This location is coherent with the layered architecture because deciding what counts as "expiring soon" is a business rule. `WarrantyRepository` is responsible for persistence, while `ConsoleMenu` is responsible only for receiving the number of days from the user and displaying the results.

Keeping this filtering logic in `WarrantyService` preserves the separation of responsibilities between the `ui`, `service`, `persistence`, and `model` layers.
