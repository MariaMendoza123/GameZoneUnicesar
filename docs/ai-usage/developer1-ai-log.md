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