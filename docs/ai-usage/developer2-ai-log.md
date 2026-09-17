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

