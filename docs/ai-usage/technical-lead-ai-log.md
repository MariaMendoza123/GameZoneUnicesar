# Technical Lead - AI Usage Log

## Log Entry 1

* **Date:** 2026-09-08
* **Tool Used:** ChatGPT
* **Role:** Technical Lead
* **Module:** Taller 1 - System Integration and Project Coordination

### Consultations Made

I used AI to clarify questions related to the responsibilities of the technical lead within the project and the organization of the existing layered architecture.

I consulted about:

* How the existing modules should be integrated without breaking the established architecture.
* How the `ui`, `service`, `persistence`, and `model` layers should communicate.
* How to organize the console menu so that each module keeps its own responsibilities.
* How to identify the appropriate service responsible for a specific operation instead of placing business logic directly in the user interface.
* How to organize Git branches and commits according to the Git Flow established for the project.
* How to use small and descriptive commits following the Conventional Commits convention.
* How to verify the project state before integrating changes from another feature branch.

### Applied Decisions

The AI was used as a technical consultation tool. The final implementation and integration decisions were made by me as Technical Lead and according to the requirements established by the professor.

I maintained the established layered architecture and avoided moving business logic into the console interface.

I also followed the project's Git workflow by working with feature branches, verifying the state of branches before integration, and using descriptive commit messages in English.

---

## Log Entry 2

* **Date:** 2026-09-15
* **Tool Used:** ChatGPT
* **Role:** Technical Lead
* **Module:** Requirement 1 - Accessory Module Integration

### Consultations Made

I used AI to review the technical implications of integrating the new accessory module into the existing GameZone system.

I consulted about:

* How `SaleService` could support both existing products and the new accessories without duplicating the sales rules.
* How inventory validation and stock updates should work when a sale contains different types of items.
* How `ProductService` and `AccessoryService` should remain responsible for their corresponding inventory operations.
* How the `ConsoleMenu` could be extended with an accessory management submenu while preserving the existing product, person, and sales operations.
* How accessories could be selected together with consoles and videogames during the same sale.
* How to verify that the integration was additive and did not remove or alter the existing functionality unnecessarily.
* How to verify the project after merging the accessory feature into `develop`.
* How to check Git status, branches, commits, and differences before and after integration.
* How to verify that the final application could start correctly and that the main menu exposed the required functionality.

### Applied Decisions

The AI was used to review possible implementation approaches and to clarify technical questions. The final decisions were made by the team according to the examination requirements.

The integration maintained the existing product hierarchy and extended the system with the new accessory hierarchy.

`SaleService` was extended so that accessories could participate in sales while preserving the existing validation of minimum items, stock availability, total calculation, and inventory updates.

The console menu was extended with the required accessory operations and the sales menu was adapted to allow accessories to participate in a sale.

I also verified the Git integration process by comparing the feature branches with `develop`, checking the commit history, merging completed work, and verifying that the working tree remained clean.

---

## Log Entry 3

* **Date:** 2026-09-16
* **Tool Used:** ChatGPT
* **Role:** Technical Lead
* **Module:** Requirement 1 - Integration Verification and Git Coordination

### Consultations Made

I used AI to review the final integration state of the project before closing the work for Requirement 1.

I consulted about:

* How to verify that the feature branches contained the expected commits.
* How to compare a feature branch with `develop` before merging.
* How to identify whether a branch was already integrated into `develop`.
* How to verify that a merge did not remove previously implemented modules.
* How to interpret `git status`, `git log`, `git diff`, and `git branch -r` outputs.
* How to verify that the application still exposed the product, person, accessory, and sales menus after integration.
* How to verify that the final working tree was clean before pushing the integrated branch.
* How to organize the documentation required by Requirement 1, including the accessory analysis, class diagram, README update, and AI usage documentation.

### Applied Decisions

The final repository state was verified through Git commands and by executing the application.

The accessory module was integrated into the existing system, including its model, persistence, service, and console integration.

The required Requirement 1 documentation was kept in the `docs` directory:

* `docs/accessory-analysis.md`
* `docs/accessory-class-diagram.md`
* `docs/ai-usage/`

The application was also executed after the integration to verify that the main menu and the required management submenus were available.

All final Git operations were performed without force pushes and without modifying the `main` branch directly.

### Final Note

AI assistance was used as a technical consultation and learning resource. It did not replace the team's implementation, testing, Git operations, or final technical decisions.

## Log Entry 4

* **Date:** 2026-09-17
* **Tool Used:** ChatGPT
* **Role:** Technical Lead
* **Module:** Requirement 2 - Promotion Module Integration

### Consultations Made

I used AI as a technical consultation tool during the integration and verification of the promotion module into the existing GameZone system.

I consulted about:

* How the promotion functionality could be integrated with the existing sales module without breaking the previously implemented sales and inventory functionality.
* How `Sale` should store promotion-related information, including the applied promotion name and discount amount.
* How `SaleService` should interact with `PromotionService` when registering a sale.
* How to calculate the subtotal, apply the best available promotion, calculate the discount, and obtain the final sale total.
* How the promotion logic could remain separated from the sales and console interface responsibilities.
* How to connect the new `PromotionService` dependency to `SaleService` and update the application initialization in `Main`.
* How to verify the integration through compilation and execution of the complete application.
* How to use Git to review the modified files, stage the complete Requirement 2 changes, create a descriptive commit, and push the completed feature branch to GitHub.

### Applied Decisions

The AI was used to review implementation alternatives, clarify technical concepts, and verify the integration process. The final implementation and technical decisions were made by me as Technical Lead according to the project requirements and the existing architecture.

The promotion functionality was integrated with the existing sales process while preserving the responsibilities of the established layers.

`Sale` was extended to store the applied promotion name and discount amount, while keeping the existing sale information and product list.

`SaleService` was extended to use `PromotionService` when registering a sale. The service calculates the subtotal, searches for the best applicable promotion, calculates the corresponding discount, and stores the final sale information.

The existing inventory validation and stock update process was preserved so that the promotion functionality did not interfere with product and accessory inventory management.

The integration was verified by compiling the project successfully with Maven and executing the application. A complete sale was also registered using a videogame priced at $250,000, resulting in a final total of $200,000 after the applicable promotion was applied.

The completed Requirement 2 changes were committed and pushed to the `feature/promotion-module` branch using the commit:

`5722279 feat: implement promotion module`

### Final Note

AI assistance was used as a technical consultation and learning resource. It did not replace the team's implementation, testing, Git operations, or final technical decisions. The final integration, verification, commit, and push were performed as part of my responsibilities as Technical Lead.

---

## Log Entry 5

* **Date:** 2026-09-17
* **Tool Used:** ChatGPT
* **Role:** Technical Lead
* **Module:** Requirement 2 - Promotion Management Console Integration

### Consultations Made

I used AI as a technical consultation tool to review the integration of promotion management into the existing console application.

I consulted about:

* How to connect PromotionService to ConsoleMenu while preserving the existing layered architecture.
* How to add a promotion management submenu without affecting the existing product, person, accessory, and sales menus.
* How to expose the required promotion operations through the console: registering percentage, category, and bulk purchase promotions, listing all promotions, and listing currently active promotions.
* How to display promotion information through the user interface while keeping business logic inside PromotionService.
* How to connect PromotionService through Main using dependency injection.
* How to verify the new integration through Maven compilation and Git status.

### Applied Decisions

The promotion management submenu was integrated into ConsoleMenu as an extension of the existing user interface.

The console menu delegates promotion operations to PromotionService instead of implementing promotion business rules directly in the UI.

Main was updated to create PromotionRepository and PromotionService and inject PromotionService into the console menu and SaleService.

The new functionality was compiled successfully with Maven, confirming that the promotion management integration is compatible with the existing application structure.

### Final Note

AI assistance was used as a technical consultation and learning resource. The final implementation, verification, Git operations, and technical decisions were performed by me as Technical Lead.

---

## Log Entry 6

* **Date:** 2026-09-17
* **Tool Used:** ChatGPT
* **Role:** Technical Lead
* **Module:** Requirement 3 - Return Module Integration

### Consultations Made

I used AI as a technical consultation and debugging resource during the integration of the return module.

I consulted about:

* How to connect the return module with the existing application structure.
* How to integrate ReturnService into Main and ConsoleMenu.
* How to extend the console with the required return management options.
* How to verify and correct compilation errors during the integration process.
* How to review the return class diagram according to the implemented classes.
* How to synchronize the feature branch with changes pushed by other team members.

### Applied Decisions

I integrated the return module into the existing application according to the project requirements and the established layered architecture.

I connected ReturnRepository and ReturnService through the existing services and application entry point, and extended ConsoleMenu with the required return management operations.

I verified the integration by compiling the project with Maven and running the application. The return management menu was successfully displayed and accessible from the main menu.

I also updated the return module documentation and class diagram to reflect the implemented integration.

### Final Note

AI assistance was used for technical guidance, debugging, and clarification. The final implementation decisions, code changes, testing, Git operations, and integration coordination were performed by me as Technical Lead.
