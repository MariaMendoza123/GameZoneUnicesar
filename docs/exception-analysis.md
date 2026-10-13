### 1. Should `GameZoneException` extend `Exception` or `RuntimeException`? Why, and how does this affect service and repository method signatures?

`GameZoneException` should extend `RuntimeException` because the system's validation and business errors should be reported without forcing every method to declare or handle them. This is appropriate for errors such as invalid data, missing resources, and business rule violations.

Since `RuntimeException` is unchecked, services and repositories do not need to add `throws GameZoneException` to their method signatures. The exceptions can propagate through the layers until `ConsoleMenu` catches them and displays the appropriate message.

### 2. Why is a custom exception hierarchy with a common root class (`GameZoneException`) preferable to using `IllegalArgumentException` for every case? What design principles apply?

A custom exception hierarchy makes it possible to distinguish between different errors, such as missing resources, business rule violations, invalid data, and persistence failures. Using only `IllegalArgumentException` would make these situations harder to identify and handle correctly.

This design supports the Single Responsibility Principle and separation of concerns because each exception represents a specific type of failure, while `ConsoleMenu` is responsible for displaying the corresponding message.

### 3. When are validators invoked, and why is this consistent with the layered architecture? Can validators access the persistence package?

Validators are invoked by the service layer before performing operations that require data or business rule validation. For example, `ProductService` uses `ProductValidator` to verify product information and stock availability before updating inventory.

Validators should not directly access the persistence package because their responsibility is to validate data, not to read or write files. Services coordinate with repositories when information must be retrieved, preserving separation of responsibilities and reducing coupling.

### 4. How does the team ensure that the ten menu operations continue working as before? What evidence demonstrates that nothing has been broken?

The team uses regression testing to verify that the ten menu operations preserve their existing behavior after the refactoring. Each operation should be tested with valid inputs and relevant error scenarios.

The evidence should include a successful Maven build, the results of available automated tests, and manual verification of the ten operations. Console outputs can demonstrate that successful operations still work and that errors display the appropriate messages.

### 5. Should `ConsoleMenu` use one `try` block with multiple `catch` clauses, nested `try-catch` blocks, or a general `catch (GameZoneException)`? Why?

`ConsoleMenu` should use one `try` block with multiple specific `catch` clauses for `ResourceNotFoundException`, `BusinessRuleException`, `InvalidDataException`, and `PersistenceException`.

This approach keeps the code clear and allows each exception to display a different user-friendly message. Catching only `GameZoneException` would require additional checks to distinguish the errors, while nested `try-catch` blocks would make the code unnecessarily complex.