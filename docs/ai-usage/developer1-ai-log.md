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