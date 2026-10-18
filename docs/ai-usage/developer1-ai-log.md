Fecha: [2026-09-08]
Herramienta usada: Claude (Anthropic)
Módulo trabajado: Persistencia — Módulo de Productos (ProductRepository)

Consultas realizadas:

Resolví dudas conceptuales sobre el rol de la clase de persistencia dentro de la arquitectura en capas, y cómo debía relacionarse con el modelo sin violar la separación de capas.
Pregunté sobre ventajas y desventajas de distintos formatos de archivo (texto plano, CSV, serialización de Java) antes de elegir uno.
Comparé mi diseño contra dos clases del repositorio guía de mi profesor (FileManager y BookRepositoryImpl de BibliotecaUnicesar), para entender el patrón toLine/fromLine y la técnica java.nio.file (Files/Path/Paths).
Pedí explicación línea por línea del código para entender sintaxis que no conocía (try-with-resources, instanceof, split(), Files.readAllLines, Files.write, Files.createDirectories).
Pregunté por qué mis métodos toLine/fromLine eran más largos que el ejemplo del profesor, para entender la diferencia entre una clase plana (Book) y una jerarquía con subclases (Product).

Decisiones tomadas (mías):

Formato: texto plano delimitado por |, con tag de tipo (VIDEOGAME/CONSOLE) al inicio de cada línea, para reconstruir el subtipo correcto de Product.
Verifiqué en el enunciado del taller que el Desarrollador 1 tiene asignadas exactamente 5 clases (jerarquía de productos + persistencia + servicio), así que descarté agregar una clase FileManager separada aunque el ejemplo del profesor sí la tiene — mantuve la lectura/escritura de archivo dentro de la misma clase ProductRepository.
Adopté java.nio.file (Files/Path) en vez de BufferedReader/BufferedWriter, siguiendo el estilo técnico del repo guía.
Usé concatenación con + en toLine() en vez de String.join, para mantener consistencia de estilo con el repo guía del profesor.
Diseñé operaciones granulares (save, findAll, findById, update) en vez de guardar/cargar toda la lista de una vez, porque update() es necesario para persistir el descuento automático de inventario cuando se registre una venta.
Ruta del archivo: data/products.txt, para mantener consistencia con la carpeta data/ que exige el entregable final del taller.



* **Date:** 2026-09-08
* **Tool Used:** Gemini (Google)
* **Module:** Service — Product Module (`ProductService`)

### Prompts / Queries:
* Reviewed business rules required for the product service layer according to the project requirements.
* Compared `ProductService` structure with the guide repository (`BibliotecaUnicesar`) to align exception handling and ID generation strategies.
* Clarified the role of ID prefixing (`VG-` for VideoGames, `CN-` for Consoles) and String-based auto-increment logic in `ProductService`.
* Refactored attribute validation to ensure all specific fields (platform, genre, classification, brand, model, generation) and common fields (title, price, stock) are checked before persistence.
* Verified that all exception messages and JavaDoc comments strictly comply with the English language requirement.

### Applied Decisions:
* **Auto-generated ID:** Implemented `generateNextId(prefix)` to produce clean String IDs (`VG-1`, `CN-1`) automatically, keeping UI responsibility lightweight and avoiding duplicate IDs.
* **Validation Strategy:** Created a helper method `validateCommonAttributes()` alongside specific attribute checks in `registerVideoGame` and `registerConsole`.
* **Stock Management:** Implemented `updateStock()` to handle inventory reduction and stock sufficiency validation for integration with the sales module.
* **Language Standardization:** Standardized all exception messages and JavaDoc documentation to English to fulfill project requirements.

## 3. Consultations Made
date: 2026-09-15
tool used: Gemini (Google)
module worked: Model — Product Hierarchy (Accessory, Controller, Cable, Memory

1. Design of the getDescription() method within the hierarchy.
   I consulted on how to declare getDescription() in Accessory so that it met the requirement of "overriding" the method inherited from Product without negating the utility of the implementation, given that it would be overridden again by each concrete subclass. The AI suggested a pattern where Accessory implements the common elements (title, price, stock, compatibility) and each concrete subclass extends this implementation by calling super.getDescription() and adding its specific attribute.

2. **Representation of compatibility between accessories and consoles.**
   I asked about the pros and cons of representing compatibleConsoleIds as a List<String> (IDs) versus a List<Console> (full objects). The AI explained that using IDs avoids coupling the accessory's persistence with that of the product and aligns better with the service's findAccessoriesCompatibleWith(String consoleId) method signature.

3. **Handling null and empty values.**
   I consulted on the risk of a NullPointerException when using String.join on a potentially null list, and how to prevent this in getDescription() and the Accessory constructor.
   AI Usage Log — Requerimiento 2 (Promotion Module)


Date: 2026-09-16
module worked: Promotion class hierarchy — model layer
AI tool used: Claude (Anthropic)

I used Claude as a tutor to understand and implement the Promotion abstract class and its three subclasses (PercentageDiscount, CategoryDiscount, BulkPurchaseDiscount). For isActive, I learned to check a date range using LocalDate methods (isBefore, isAfter, isEqual) combined with logical operators. For calculateDiscount, I understood why it must be declared abstract in Promotion, forcing each subclass to provide its own calculation without the rest of the system knowing the concrete type.

In PercentageDiscount, I applied the discount percentage to sale.calculateTotal() rather than a plain getter, to make sure the total is freshly computed. In CategoryDiscount, since Product has no category field and categories are represented through the VideoGame/Console subclasses, I used instanceof checks instead of comparing strings, iterating over the sale's products and summing only the matching ones before applying the percentage. This also helped me fix a Cannot resolve symbol 'getCategory' compile error. In BulkPurchaseDiscount, I compared the number of products in the sale against a minimum threshold, applying the discount only if the condition was met and returning zero otherwise.

Overall, the AI helped reinforce concepts of abstraction, polymorphism, and Java syntax (logical operators, compound assignment), while all final code was adapted and validated against the real project classes before being committed.

date: 2026-09-17
module worked: Return class implementation — model layer, plus canBeReturned in Sale.
AI tool used: Claude (Anthropic)

I used Claude as a tutor to design and implement the Return class and the additive canBeReturned() method in Sale. This clarified that the association between Return and Sale is a simple reference (association), not inheritance or composition, since the sale exists independently of the return.

For calculateRefundAmount(), I iterated over the returnedProducts list, summed the prices, assigned the result to the refundAmount attribute, and returned it — reusing the same accumulator pattern from the promotion module. For generateReturnReceipt(), I first saw a StringBuilder-based approach with .append(), then chose a simpler version using plain string concatenation (+=) since I was not familiar with StringBuilder, producing the same result in Spanish as required for user-facing text.

For canBeReturned(), I compared two approaches to calculate the difference between two dates: ChronoUnit.DAYS.between(saleDate, today) compared against 30, versus saleDate.plusDays(30) combined with isAfter/isBefore. I chose the ChronoUnit.DAYS.between() approach because it maps more directly to the business rule ("how many days have passed") and is easier to justify without a negation. I also confirmed that Sale.date is stored as a String, so LocalDate.parse(date) is required before performing date calculations.

All final code was adapted to match the real project classes (Sale, Product) and reviewed for compile errors before being committed.

date: 2026-09-18
module worked: Warranty class hierarchy — model layer
AI tool used: Claude (Anthropic)

I used Claude to design and implement the Warranty class hierarchy: the abstract Warranty class and its two subclasses, BasicWarranty and ExtendedWarranty. I learned that the constructor of an abstract class can safely call an abstract method (getDurationInMonths()) to calculate endDate automatically, since by the time the constructor runs, the real object is already a concrete subclass, so Java resolves the correct implementation through polymorphism. I used IntelliJ's Implement Methods and Generate Constructor shortcuts to scaffold the abstract methods and the super(...) call instead of writing them by hand.

For BasicWarranty, I implemented getDurationInMonths() (6), getWarrantyType() ("Garantía Básica"), and getAdditionalCost() (0.0). For ExtendedWarranty, the difference was getAdditionalCost(), which required accessing the associated product through the inherited getProduct() getter (since product is private in Warranty) and calculating 10% of its price. I also caught and fixed an early version of the Warranty constructor that incorrectly received endDate as a parameter instead of calculating it internally, which also required removing that parameter from the subclasses' calls to super(...).

All code was adapted to the real project classes and validated for compilation before being committed.


Field:	Content

Fecha:	2026-09-24

Herramienta:	Claude

Fase y rama:	Fase 2, feature/accessory-category-discount

Objetivo:	Extend PromotionService.registerCategoryDiscount and ConsoleMenu to support the ACCESSORY category, and add a preloaded promotion

Consulta:	What changes are needed in PromotionService, ConsoleMenu, and promotions.csv to complete adjustment A1

Respuesta:	Add ACCESSORY to the category validation in registerCategoryDiscount, update the console prompt text to mention ACCESSORY, and add a CATEGORY line in promotions.csv with dates covering the integration work week (2026-09-21 to 2026-09-26)

Decisión:	Accepted all three changes; verified the CSV date range against the work period stated in the requirement so the promotion is active during the oral defense

Commit relacionado:	feat: allow ACCESSORY category in registerCategoryDiscount; feat: show ACCESSORY option in category promotion prompt; feat: add preloaded accessory category promotion

Field	Content


Fecha	2026-09-26

Herramienta	Claude

Fase y rama	Fase 4, fix/return-discounted-refund

Objetivo	Understand and implement adjustment A5: refund proportional to the original sale's discount

Consulta	How to fix calculateRefundAmount so it does not refund more than the client actually paid when the original sale had a promotion

Respuesta	Calculate paidProportion as 1 - (discountAmount / subtotal) from the original sale, then multiply each returned product's price by that proportion instead of summing list prices directly

Decisión:	Implemented the formula manually with guidance; declined the suggestion to extract a shared private helper method for paidProportion since it was not requested by the requirement, and duplicated the calculation in generateReturnReceipt instead

Commit relacionado	fix: calculate proportional refund based on original sale discount; fix: show proportional discount and refund per item in return receipt


## Requerimiento 6 — Exceptions and Validation (Developer 1)

Branch: feature/exception-handling. AI tool used: Claude (Anthropic).

### Package com.gamezone.exceptions

#### Entry 4

| Field | Content |
|---|---|
| Fecha | 2026-10-10 |
| Herramienta | Claude |
| Fase y rama | Requerimiento 6, feature/exception-handling |
| Objetivo | Understand and implement GameZoneException, the root of the custom exception hierarchy |
| Consulta | Must it be abstract? What does "extends RuntimeException" mean? How do I write its two constructors and the getter? |
| Respuesta | Explained that an abstract class cannot be instantiated and that the team section of the requirement calls it abstract; explained that RuntimeException is unchecked, so service and repository methods do not need throws in their signatures. Pointed out a self-assignment in the first constructor (this.errorCode = errorCode with no such parameter) and a missing class keyword in the declaration |
| Decisión | Declared it abstract; chose an explicit null as the default error code for the message-only constructor so the decision is visible; no setter, since an exception records an event that already happened; fixed the missing keyword |
| Commit relacionado | feat: add abstract GameZoneException as root of the exception hierarchy |

#### Entry 5

| Field | Content |
|---|---|
| Fecha | 2026-10-10 |
| Herramienta | Claude |
| Fase y rama | Requerimiento 6, feature/exception-handling |
| Objetivo | Implement ResourceNotFoundException, which builds a Spanish message from the resource type and identifier |
| Consulta | What does the constructor need? How do I build the message inside super(...)? |
| Respuesta | Explained that the constructor takes two String parameters and only calls super. Warned that putting "no fue encontrado" after the comma stores it as the error code and cuts the message, and that the resource type must not be hardcoded as "producto". Also noted that the "El" article is wrong for feminine words such as "venta" |
| Decisión | Moved the phrase inside the message and used RESOURCE_NOT_FOUND as the error code; removed the hardcoded resource type; added JavaDoc. The gendered-article issue was noted and left as a pending decision |
| Commit relacionado | feat: add ResourceNotFoundException for missing resources; fix: move not-found text into message in ResourceNotFoundException |

#### Entry 6

| Field | Content |
|---|---|
| Fecha | 2026-10-10 |
| Herramienta | Claude |
| Fase y rama | Requerimiento 6, feature/exception-handling |
| Objetivo | Implement BusinessRuleException for business rule violations |
| Consulta | Review of my BusinessRuleException constructor |
| Respuesta | Confirmed the structure was correct, and recommended passing an error code so all subclasses follow the same criterion as ResourceNotFoundException |
| Decisión | Used BUSINESS_RULE_VIOLATION as the error code so every subclass of GameZoneException behaves consistently; added JavaDoc |
| Commit relacionado | feat: add BusinessRuleException for business rule violations |

#### Entry 7

| Field | Content |
|---|---|
| Fecha | 2026-10-10 |
| Herramienta | Claude |
| Fase y rama | Requerimiento 6, feature/exception-handling |
| Objetivo | Implement InvalidDataException with a field name and a problem description |
| Consulta | I do not understand the constructor parameters; how does the class look? |
| Respuesta | Explained that the parameters are the invalid field name and a short description, supplied by whoever throws the exception (for example a validator). Noted that ConsoleMenu already adds the prefix "Los datos ingresados no son válidos", so the exception message should only carry the detail |
| Decisión | Built the message from both parameters and used INVALID_DATA as the error code, keeping the same criterion as the other subclasses |
| Commit relacionado | feat: add InvalidDataException for invalid input values |

#### Entry 8

| Field | Content |
|---|---|
| Fecha | 2026-10-10 |
| Herramienta | Claude |
| Fase y rama | Requerimiento 6, feature/exception-handling |
| Objetivo | Implement PersistenceException, which must keep the original cause |
| Consulta | Review of super(message, cause, "PERSISTENCE_ERROR") |
| Respuesta | Explained that this does not compile because GameZoneException has no three-parameter constructor, and proposed calling super(message, code) followed by initCause(cause) |
| Decisión | Used initCause instead of modifying GameZoneException, because the specification defines exactly two constructors and the class was already committed |
| Commit relacionado | feat: add PersistenceException preserving the original cause |

#### Entry 9

| Field | Content |
|---|---|
| Fecha | 2026-10-10 |
| Herramienta | Claude |
| Fase y rama | Requerimiento 6, feature/exception-handling |
| Objetivo | Undo a local commit that had not been pushed |
| Consulta | How do I cancel a commit if I have not pushed it yet? |
| Respuesta | Explained Undo Commit in GitHub Desktop and git reset --soft HEAD~1, warned against --hard because it deletes changes, and mentioned amend for changing only the message |
| Decisión | Used Undo Commit before pushing, corrected the class, and committed again; did not use any force push, which is prohibited |
| Commit relacionado | N/A |


### Package com.gamezone.validation

#### Entry 10

| Field | Content |
|---|---|
| Fecha | 2026-10-11 |
| Herramienta | Claude |
| Fase y rama | Requerimiento 6, feature/exception-handling |
| Objetivo | Understand the validator layer and implement ProductValidator |
| Consulta | What is a validator? How do I write validateProductData? Provide the three methods |
| Respuesta | Explained that validators are classes with static void methods that throw typed exceptions and sit between service and model. Corrected my first attempt (InvalidDataException needs two arguments) and recommended isBlank over isEmpty. Provided the full class with validateProductData, validateStockAvailability and validateProductExists |
| Decisión | Accepted the code after reviewing it: InvalidDataException for malformed data, BusinessRuleException for insufficient stock, ResourceNotFoundException for a null product; isBlank so whitespace-only values are rejected. Noted that callers must call validateProductExists before validateStockAvailability |
| Commit relacionado | feat: add ProductValidator with product data and stock validations |

#### Entry 11

| Field | Content |
|---|---|
| Fecha | 2026-10-11 |
| Herramienta | Claude |
| Fase y rama | Requerimiento 6, feature/exception-handling |
| Objetivo | Implement PersonValidator |
| Consulta | Guidance for the three PersonValidator methods; I uploaded Person.java so the names match the project |
| Respuesta | Provided validatePersonData, validatePersonExists and validateUniquePerson using Person.getId(). The email is checked in two steps (blank, then '@' and '.') so each error has its own message, and a single validator serves both Client and Seller through polymorphism |
| Decisión | Accepted after reviewing it; kept Person as the parameter type instead of Client or Seller; kept separate messages for an empty email and a malformed one |
| Commit relacionado | feat: add PersonValidator with data, existence and uniqueness checks |

#### Entry 12

| Field | Content |
|---|---|
| Fecha | 2026-10-11 |
| Herramienta | Claude |
| Fase y rama | Requerimiento 6, feature/exception-handling |
| Objetivo | Implement SaleValidator |
| Consulta | How should SaleValidator look given that the requirement uses Customer but the project uses Client? |
| Respuesta | Provided validateSaleData and validateSaleExists using Client instead of Customer. Suggested InvalidDataException for a null client or seller and BusinessRuleException for an empty product list, since the requirement lists "venta sin productos" as a business rule. Flagged the gendered-article problem in ResourceNotFoundException messages |
| Decisión | Used Client because Customer does not exist in the project; adopted the exception criteria above; the article issue remains a pending decision |
| Commit relacionado | feat: add SaleValidator with sale data and existence checks |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Requerimiento 6, feature/audit-exceptions-validations |
| Objetivo | Structure the GRASP design audit documentation for exception handling and validation layers |
| Consulta | Verification of theoretical GRASP criteria applied to static validators and review of the standard reporting schema for violations starting from ID V-19 |
| Respuesta | Assisted by clarifying the theoretical impact of static procedural methods on Low Coupling and Protected Variations, and recommended structuring each violation entry with concrete evidence, technical consequences, and proposed solutions to thoroughly justify every finding |
| Decisión | Adopted the suggested audit template across the documentation, sequentially indexing violations from V-19 to V-32 for Requirement 6, and documented decoupling refactoring strategies based on the Validator<T> interface |
| Commit relacionado | docs: add GRASP design audit for exceptions and validation layers (Req 6) |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Fase 2 (Refactorización GRASP), `feature/grasp-refactor` |
| Objetivo | Refactor `ProductRepository.java` and `Product` hierarchy to resolve GRASP violations V-01, V-02, and V-03 without altering file persistence format |
| Consulta | How to refactor `ProductRepository.java` to remove concrete subclass dependencies (`VideoGame`, `Console`) and `instanceof` checks while maintaining strict non-regression on `data/products.txt`? |
| Respuesta | Recommended defining a polymorphic serialization contract (`toCsvLine()`) in `Product` hierarchy and a static rehydration method (`fromCsvLine()`), allowing `ProductRepository` to fully delegate persistence responsibility to domain models |
| Decisión | Implemented `toCsvLine()` in `Product`, `VideoGame`, and `Console`, updated `ProductRepository.java` to delegate file operations, and verified that the output CSV format remained 100% identical |
| Commit relacionado | `refactor: implement polymorphic CSV serialization in Product hierarchy (V-01, V-02, V-03)` |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Fase 2 (Refactorización GRASP), `feature/grasp-refactor` |
| Objetivo | Refactor `ProductService.java` and `Product.java` to resolve Information Expert violation V-04 |
| Consulta | How to refactor stock updates in `ProductService` to comply with Information Expert without affecting existing validation exceptions? |
| Respuesta | Recommended adding `reduceStock` and `increaseStock` encapsulation methods to `Product`, updating `ProductService` to delegate stock mutation directly to the domain object |
| Decisión | Implemented `reduceStock` and `increaseStock` in `Product`, refactored `updateStock` and `restoreStock` in `ProductService`, and verified compilation and non-regression |
| Commit relacionado | `refactor: delegate stock management to Product domain model (V-04)` |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Fase 2 (Refactorización GRASP), `feature/grasp-refactor` |
| Objetivo | Refactor `ProductService.java` to resolve High Cohesion violation V-06 |
| Consulta | How to refactor `generateNextId` out of `ProductService` to comply with High Cohesion? |
| Respuesta | Recommended moving sequence calculation to `ProductRepository` as the data owner and invoking it from `ProductService` |
| Decisión | Implemented `generateNextId` in `ProductRepository`, removed private helper from `ProductService`, and verified ID format preservation |
| Commit relacionado | `refactor: delegate ID generation logic to ProductRepository (V-06)` |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Fase 2 (Refactorización GRASP), `feature/grasp-refactor` |
| Objetivo | Refactor `ProductService.java` and `ProductValidator.java` to resolve High Cohesion violations V-07, V-08, V-09 |
| Consulta | How to encapsulate attribute validation logic out of `ProductService` into `ProductValidator` without altering exception messages? |
| Respuesta | Recommended creating `validateVideoGameData` and `validateConsoleData` in `ProductValidator` and delegating validation calls from `ProductService` |
| Decisión | Implemented validation methods in `ProductValidator`, cleaned up `ProductService`, and verified compilation and exception string exactness |
| Commit relacionado | `refactor: encapsulate product attribute validation in ProductValidator (V-07, V-08, V-09)` |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Fase 2 (Refactorización GRASP), `feature/grasp-refactor` |
| Objetivo | Refactor `Cable.java` to resolve Polymorphism (V-10) and Protected Variations (V-11) |
| Consulta | How to fix description rendering contract in `Cable.java` according to GRASP audit findings? |
| Respuesta | Recommended replacing `toString()` signature with `@Override public String getDescription()` and implementing `@Override public String toCsvLine()` |
| Decisión | Updated `Cable.java` to override `getDescription()`, verified compilation and uniform polymorphic output |
| Commit relacionado | `refactor(model): override getDescription in Cable to satisfy polymorphic contract (V-10, V-11)` |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Fase 2 (Refactorización GRASP), `feature/grasp-refactor` |
| Objetivo | Refactor `CategoryDiscount.java` and `Product` hierarchy to resolve violations V-12 through V-15 |
| Consulta | How to replace `instanceof` type checks in `CategoryDiscount.calculateDiscount()` using GRASP Polymorphism and Information Expert? |
| Respuesta | Recommended adding abstract `getCategoryName()` method in `Product`, implementing it in sub-models, and evaluating eligibility via string matching in `CategoryDiscount` |
| Decisión | Implemented `getCategoryName()` across `Product` subclasses, refactored `CategoryDiscount.calculateDiscount()`, and verified non-regression in discount calculations |
| Commit relacionado | `refactor(promotions): replace instanceof checks with polymorphic category evaluation in CategoryDiscount (V-12 to V-15)` |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Fase 2 (Refactorización GRASP), `feature/grasp-refactor` |
| Objetivo | Refactor `Return.java` to resolve High Cohesion violation V-16 |
| Consulta | How to separate receipt formatting concerns from the `Return` domain model to comply with High Cohesion? |
| Respuesta | Recommended delegating receipt layout string construction to a dedicated `ReturnReceiptFormatter` utility class |
| Decisión | Implemented `ReturnReceiptFormatter`, updated `Return.generateReturnReceipt()` to delegate execution, and verified non-regression in text layout |
| Commit relacionado | `refactor(returns): extract receipt formatting logic into ReturnReceiptFormatter (V-16)` |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Fase 2 (Refactorización GRASP), `feature/grasp-refactor` |
| Objetivo | Refactor `Sale.canBeReturned()` to resolve Low Coupling (V-17) and Protected Variations (V-18) |
| Consulta | How to resolve date parsing issues and parameterize return policy in `Sale.canBeReturned()` without modifying existing class fields? |
| Respuesta | Recommended local conversion to `LocalDate` within `canBeReturned()` and method overloading with `currentDate` and `maxDaysAllowed` parameters |
| Decisión | Implemented method overloading in `canBeReturned()` for testing and flexible limits, keeping class fields intact |
| Commit relacionado | `refactor(sales): parameterize canBeReturned eligibility in Sale (V-17, V-18)` |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Fase 2 (Refactorización GRASP), `feature/grasp-refactor` |
| Objetivo | Refactor `GameZoneException.java` to resolve Protected Variations (V-19) |
| Consulta | How to support Throwable cause chaining in base exception GameZoneException without altering existing constructor contracts? |
| Respuesta | Recommended adding overloaded constructors accepting `Throwable cause` and chaining through `super(message, cause)` |
| Decisión | Implemented constructor overloading with `Throwable cause` and `errorCode` support |
| Commit relacionado | `refactor(exceptions): enable Throwable cause chaining in GameZoneException (V-19)` |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Fase 2 (Refactorización GRASP), `feature/grasp-refactor` |
| Objetivo | Refactor `InvalidDataException.java` to resolve Information Expert violation V-20 |
| Consulta | How to structure `InvalidDataException` to retain `fieldName` and `problem` as accessible attributes for presentation layers? |
| Respuesta | Recommended storing `fieldName` and `problem` as private final fields, adding getters, and preserving super string formatting |
| Decisión | Added `fieldName` and `problem` fields, getters, and overloaded constructor with `Throwable cause` |
| Commit relacionado | `refactor(exceptions): encapsulate fieldName and problem in InvalidDataException (V-20)` |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Fase 2 (Refactorización GRASP), `feature/grasp-refactor` |
| Objetivo | Refactor `PersistenceException.java` to resolve Protected Variations (V-21) |
| Consulta | How to replace `initCause` workaround in `PersistenceException` using the updated `GameZoneException` constructor? |
| Respuesta | Recommended invoking `super(message, "PERSISTENCE_ERROR", cause)` directly and removing `initCause(cause)` |
| Decisión | Updated constructor delegation to super with cause parameter and removed manual initCause call |
| Commit relacionado | `refactor(exceptions): delegate root cause via super in PersistenceException (V-21)` |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Fase 2 (Refactorización GRASP), `feature/grasp-refactor` |
| Objetivo | Refactor `ResourceNotFoundException.java` to resolve Information Expert violation V-22 |
| Consulta | How to encapsulate `resourceType` and `resourceId` as accessible attributes in `ResourceNotFoundException`? |
| Respuesta | Recommended adding `resourceType` and `resourceId` private final fields with getters and an overloaded constructor with `Throwable cause` |
| Decisión | Implemented encapsulated fields, public getters, and cause-enabled constructor overloading |
| Commit relacionado | `refactor(exceptions): encapsulate resourceType and resourceId in ResourceNotFoundException (V-22)` |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Fase 2 (Refactorización GRASP), `feature/grasp-refactor` |
| Objetivo | Refactor `PersonValidator.java` to resolve V-23, V-24, and V-25 |
| Consulta | How to introduce a generic `Validator<T>` interface and decouple hardcoded regex in `PersonValidator` while maintaining backwards compatibility? |
| Respuesta | Recommended introducing `Validator<T>` interface, transforming `PersonValidator` into an instantiable configurable component, and adding static delegates |
| Decisión | Created `Validator<T>`, refactored `PersonValidator` to implement `Validator<Person>` with configurable `phoneRegex`, and added static compatibility methods |
| Commit relacionado | `refactor(validation): implement Validator interface and parameterize regex in PersonValidator (V-23, V-24, V-25)` |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Fase 2 (Refactorización GRASP), `feature/grasp-refactor` |
| Objetivo | Refactor `ProductValidator.java` and `Product.java` to resolve V-26 to V-29 |
| Consulta | How to refactor `ProductValidator` to delegate stock checking to `Product.hasStock()` using existing `stockQuantity` field? |
| Respuesta | Recommended adding `hasStock()` in `Product` evaluating `stockQuantity`, implementing `Validator<Product>`, and adding static delegate accessors |
| Decisión | Implemented `Product.hasStock()`, refactored `ProductValidator` to implement `Validator<Product>` with `stockQuantity` accessors |
| Commit relacionado | `refactor(validation): delegate stock check to Product and implement Validator in ProductValidator (V-26 to V-29)` |

| Campo | Detalle |
|---|---|
| Fecha | 2026-10-18 |
| Herramienta | Gemini |
| Fase y rama | Fase 2 (Refactorización GRASP), `feature/grasp-refactor` |
| Objetivo | Refactor `SaleValidator.java` to resolve V-30, V-31, and V-32 |
| Consulta | How to refactor `SaleValidator` to implement `Validator<Sale>` while preserving backwards compatibility for callers? |
| Respuesta | Recommended implementing `Validator<Sale>`, creating instance validation methods, and providing static delegate methods |
| Decisión | Refactored `SaleValidator` to implement `Validator<Sale>` and added static delegates |
| Commit relacionado | `refactor(validation): implement Validator in SaleValidator (V-30, V-31, V-32)` |