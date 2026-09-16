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