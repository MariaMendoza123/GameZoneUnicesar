# AI Usage Log — Developer 2 (Person, Accessory & Promotion Modules)

## Herramienta utilizada
Claude (Anthropic), como apoyo para repasar y practicar los requerimientos del taller y de los examenes fuera del horario de evaluacion.

## Usos legitimos aplicados

- Consulte como estructurar PersonRepository, AccessoryRepository y PromotionRepository siguiendo el mismo patron de persistencia en archivo plano ya usado por ProductRepository, manteniendo consistencia de estilo en el equipo.
- Pedi explicacion de como declarar el metodo abstracto calculateDiscount(Sale sale) en Promotion y por que garantiza que las subclases lo implementen (uso de abstract y @Override).
- Resolvi dudas conceptuales sobre por que la logica de seleccion de la mejor promocion (findBestPromotionFor) debe vivir en la capa de servicio y no en Sale ni en la interfaz de usuario, en el contexto de la arquitectura en capas.
- Consulte errores de Git y PowerShell durante el flujo de trabajo (ramas anidadas, rutas de archivos duplicadas, mensajes de warning de CRLF/LF).
- Pedi revision de mi propio codigo (PersonService, AccessoryService, PromotionService) para verificar consistencia de nombres de metodos con el resto del proyecto.

## Decisiones tomadas por mi, no por la IA

- La decision de que campo de compatibilidad (compatibleConsoleIds) va en Accessory y no en Console fue evaluada y confirmada por mi despues de revisar el enunciado y el codigo ya existente de mi companero.
- Verifique manualmente los IDs reales de las consolas precargadas (CO-001, CO-002) en products.txt antes de crear los datos de prueba de accesorios y promociones, en lugar de usar valores genericos sugeridos inicialmente.
- Las respuestas de las preguntas orientadoras de analisis (docs/accessory-analysis.md, docs/promotion-analysis.md) correspondientes a mi modulo fueron redactadas con ayuda de la IA para expresar en ingles las decisiones de diseno, pero basadas en el codigo que yo mismo implemente y entendi.


## Sesión: Módulo de devoluciones (Requerimiento 3)

- Consulté cómo estructurar ReturnRepository para resolver referencias a Sale y Product durante la carga desde CSV, dado que SaleService no expone un método findById.
- Pedí explicación de por qué la validación de plazo (30 días) debe vivir en el modelo (Sale.canBeReturned) mientras la decisión de rechazar la operación vive en el servicio (ReturnService.registerReturn).
- Identifiqué junto con la IA una limitación real del enunciado: ProductService.restoreStock no tiene equivalente en AccessoryService, por lo que las devoluciones de accesorios no restauran stock automáticamente. Esta decisión de alcance quedó documentada para discutirla con el equipo.
- Redacté con apoyo de la IA las respuestas en inglés a las preguntas 3 y 5 de docs/return-analysis.md, basadas en el código que implementé.

## Sesión: Módulo de garantías (Requerimiento 4)

- Consulté cómo diseñar WarrantyRepository para resolver referencias a Sale y Product durante la carga desde CSV, reutilizando el mismo patrón ya aplicado en ReturnRepository (buscar primero en los productos de la venta, luego en ProductService como respaldo).
- Pedí ayuda para dividir WarrantyService en commits atómicos por método (assignBasicWarranty/assignExtendedWarranty, findWarrantyByProduct/listAllWarranties, listActiveWarranties, listWarrantiesExpiringSoon), ya que no había trabajo previo real que dividir en esta sesión.
- Resolví dudas sobre por qué listWarrantiesExpiringSoon pertenece a la capa de servicio y no a persistencia ni a la interfaz de consola, en el contexto de la arquitectura en capas.
- Redacté con apoyo de la IA la respuesta en inglés a la pregunta 5 de docs/warranty-analysis.md, basada en el código que implementé.
- Verifiqué que WarrantyRepository y WarrantyService no compilarán hasta que el Líder Técnico modifique SaleService.registerSale e integre el módulo; esto se documentó como una dependencia esperada del flujo de trabajo, no un error.

## Requirement 5 — Integration (Phase 3 & Phase 4)

The table below follows the exact format required by Requirement 5, section 11.

| Date | Tool | Phase and branch | Objective | Query | Response | Decision | Related commit |
|---|---|---|---|---|---|---|---|
| 2026-09-25 | Claude (claude.ai) | Phase 3 — `fix/warranty-circular-dependency` | Resolve the A2 circular dependency (`SaleService → WarrantyService → WarrantyRepository → SaleService`) without breaking constructor injection | Explained the A2 requirement and asked for an implementation over the actual project files | Proposed making `WarrantyRepository` persist only raw identifiers via a new `WarrantyRecord` class, resolving references inside `WarrantyService` (now depending on `SaleRepository`/`ProductService`), switching `SaleService` to receive `WarrantyService` via constructor, and reordering object construction in `Main` | Accepted in full. Verified it compiled cleanly before committing | `42679d3`, `b73af21`, `9257fd9`, `7562a6e`, `2c4fef2` (PR #13) |
| 2026-09-25 | Claude (claude.ai) | Phase 3 — `fix/restore-warranty-circular-dependency` | Diagnose why the A2 fix, already merged via PR #13, was missing from `develop` after PR #16 (A3) was merged | Shared `git log` output and the current file contents, asking why the A2 pattern was gone despite appearing in the commit history | Identified that PR #16 was merged as a fast-forward from a point in `develop`'s history prior to A2 being stably applied, so A3's older version of the affected files became the final state without reintegrating A2. Confirmed using file timestamps and a compiled `.class` file that still had the A2-only `WarrantyRecord` class | Reapplied A2 on top of the current `develop` on a new branch, since the original branch was already an ancestor of `develop` and would not produce a usable diff. Verified it compiled and ran correctly | `115572e` (PR #17) |
| 2026-09-25 | Claude (claude.ai) | Phase 4 — `fix/return-accessory-stock` | Implement A4 — restore accessory stock when an accessory is returned | Explained the A4 requirement and asked for an implementation over `ReturnService`, `AccessoryService`, `ReturnRepository`, and `Main` | Proposed adding `AccessoryService.restoreStock(String, int)`, injecting `AccessoryService` into `ReturnService`, and using `instanceof Accessory` in the stock-restoration loop to delegate to the correct service. Verified `ReturnRepository` needed no changes, since it already resolves accessories correctly via `sale.getProducts()` | Accepted in full. Confirmed no `ReturnRepository` changes were needed after tracing how `Sale`/`SaleRepository` reconstruct accessories. Verified the project compiled successfully before committing | `3d2f2eb`, `0100c9a` (PR pending) |