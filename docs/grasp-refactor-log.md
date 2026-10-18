# GRASP Refactoring Log - Developer 1

## Overview
This document logs all refactoring activities performed during Phase 2 to resolve GRASP design violations identified in the audit log (`developer1-audit.md`), ensuring system behavior and non-regression are strictly preserved.

---

## Log Entries

| Entry ID | Date | Affected Classes / Files | Violations Resolved | GRASP Principles Applied | Description of Refactoring | Non-Regression Verification |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **LOG-01** | 2026-10-18 | `Product.java`<br>`VideoGame.java`<br>`Console.java`<br>`ProductRepository.java` | V-01, V-02, V-03 | Low Coupling, Polymorphism, Protected Variations | Introduced abstract `toCsvLine()` and static `fromCsvLine()` in the `Product` hierarchy. Removed `instanceof` checks and concrete class imports (`VideoGame`, `Console`) from `ProductRepository.java`. | **PASS**: Text file persistence format (`data/products.txt`) and repository operations remain 100% identical. |

---

## Detailed Violation Traceability

### Block 1: Product Persistence Decoupling
* **V-01 (Low Coupling):** Eliminated direct imports of `VideoGame` and `Console` in `ProductRepository.java`.
* **V-02 (Polymorphism):** Replaced conditional `instanceof` checks in `toLine()` with a polymorphic call to `product.toCsvLine()`.
* **V-03 (Protected Variations):** `ProductRepository` is now shielded from future additions of new product categories.


| **LOG-02** | 2026-10-18 | `Product.java`<br>`ProductService.java` | V-04 | Information Expert | Delegated stock modification logic (`reduceStock`, `increaseStock`) from `ProductService` directly to `Product`. | **PASS**: Stock updates and validation exception messages remain unchanged. |

| **LOG-03** | 2026-10-18 | `ProductRepository.java`<br>`ProductService.java` | V-06 | High Cohesion | Delegated ID sequence generation from `ProductService` to `ProductRepository`. | **PASS**: ID formats (`VG-1`, `CN-1`, etc.) remain 100% identical. |

| **LOG-04** | 2026-10-18 | `ProductValidator.java`<br>`ProductService.java` | V-07, V-08, V-09 | High Cohesion, Protected Variations | Delegated specific input attribute validations (`validateVideoGameData`, `validateConsoleData`) to `ProductValidator`. | **PASS**: Exception types and error message strings remain 100% identical. |

| **LOG-05** | 2026-10-18 | `Cable.java` | V-10, V-11 | Polymorphism, Protected Variations | Replaced `toString()` with `@Override public String getDescription()` to honor polymorphic hierarchy contracts and protect upper layers from data omission. | **PASS**: Polymorphic description rendering now includes cable length and connector type without requiring downcasting. |

| **LOG-06** | 2026-10-18 | `CategoryDiscount.java`<br>`Product.java` | V-12, V-13, V-14, V-15 | Information Expert, Low Coupling, Polymorphism, Protected Variations | Refactored `CategoryDiscount.calculateDiscount()` to use abstract `getCategoryName()` polymorphic hook, eliminating `instanceof` checks and decoupling from concrete product subclasses. | **PASS**: Discount amounts match 100% with previous calculation logic for all categories. |

| **LOG-07** | 2026-10-18 | `Return.java`<br>`ReturnReceiptFormatter.java` | V-16 | High Cohesion | Extracted raw text receipt formatting logic out of `Return` domain entity into `ReturnReceiptFormatter`, restoring single responsibility. | **PASS**: Receipt output format string matches 100% with previous formatting tests. |