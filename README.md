Informe breve del proyecto
Red Segura Sincelejo: cálculo de rutas mínimas sobre un grafo mixto (Parcial 2)
Corporación Universitaria Antonio José de Sucre (UNIAJS)
Contenido del paquete analizado
Elemento	Descripción	Detalle
pom.xml	Configuración Maven: Java 17, UTF-8 y JUnit Jupiter 5.9.2	1 archivo · 3 dependencias
Redsegura.java	Clase principal con main y la validación de la ruta S02 → S08 (Q5)	~60 líneas · 3 KB
Vertex.java	Modelo del vértice del grafo (id y etiqueta)	~30 líneas · 1 KB
src/test/java/	Carpeta de pruebas unitarias	Vacía
README · documentación	Guía de uso del proyecto incluidos

1. Resumen general
Proyecto	Red Segura Sincelejo: modelo de la red de puntos seguros de la ciudad como grafo mixto y cálculo de la ruta de menor tiempo con Dijkstra.
Versión	1.0-SNAPSHOT – 11/11/2026
Autor	juan jose garcia ramos 
Fuentes	pom.xml, Redsegura.java y Vertex.java del archivo redsegura.zip.
Alcance	Siete preguntas del parcial (Q1 a Q7). Solo la Q5 tiene código: ruta mínima S02 (UAJS Sede E) → S08 (Terminal de Transporte), con costo esperado de 24 minutos.
Tecnologías	Java 17, Apache Maven, JUnit Jupiter 5.9.2 y aplicación de consola.
Estado actual	No compila: faltan 4 de las 6 clases que usa main y hay desajustes entre archivos, paquetes y carpetas.

La aplicación separa en paquetes el punto de entrada, el modelo, el grafo, la carga de datos y el algoritmo. Del paquete entregado existen el punto de entrada y la clase Vertex; el resto está referenciado en los import, pero no viene incluido, por lo que el resultado de 24 minutos todavía no puede comprobarse.
2. Arquitectura
 
Figura 1. Paquetes de Red Segura y clases presentes o faltantes.
Paquete	Clase	Responsabilidad
app	RedSeguraApplication	Punto de entrada: carga el grafo, ejecuta Dijkstra y valida la Q5.
model	Vertex	Punto de la red con id (S02) y label (UAJS Sede E). Inmutable; equals y hashCode usan solo el id.
graph	MixedGraph	Vértices y aristas dirigidas y no dirigidas; expone getVertices() y getVertexById(String).
factory	DataFactory	createSincelejoGraph() construye el grafo con los datos del parcial.
algorithms	DijkstraSolver	findShortestPath(graph, start, end) devuelve un DijkstraResult con totalCost y path.

3. Detalle técnico
3.1 Clase Vertex
Miembro	Firma	Comportamiento
id, label	private final String	Identificador (S02) y nombre legible; la clase es inmutable.
Vertex(...)	public Vertex(String, String)	Asigna ambos campos sin validar nulos ni vacíos.
equals / hashCode	public	Comparan solo id (con getClass); permiten usar Vertex como clave en Map y Set.
toString()	public String	Devuelve «id (label)», por ejemplo S02 (UAJS Sede E).

3.2 Flujo de main
Paso	Acción	Detalle
1	Cargar el grafo	DataFactory.createSincelejoGraph() y conteo de vértices.
2	Buscar extremos	getVertexById("S02") y getVertexById("S08"); error si alguno es nulo.
3	Calcular la ruta	DijkstraSolver.findShortestPath(graph, start, end).
4	Mostrar resultado	Origen, destino, totalCost en minutos y path con flechas.
5	Validar	Si totalCost es 24 imprime «VALIDACIÓN EXITOSA»; si no, el valor obtenido.
6	Errores	Un catch general imprime el mensaje y la traza.

Solo se verifica que los vértices no sean nulos: no se contempla que no exista ruta entre ellos. Salida esperada con el proyecto completo:
[1] Cargando grafo de Sincelejo...
    -> Grafo cargado con éxito. Vértices: N
[2] Ejecutando Dijkstra (Validación Q5)...
    Costo total: 24 minutos
    Ruta: S02 -> ... -> S08
    ✅ ¡VALIDACIÓN EXITOSA! El costo es exactamente 24 minutos.

3.3 Algoritmo de Dijkstra
Encuentra el camino de menor tiempo con pesos no negativos. En un grafo mixto, las aristas dirigidas se recorren en un solo sentido y las no dirigidas en ambos (se guardan como dos aristas dirigidas). Con lista de adyacencia y cola de prioridad la complejidad es O((V + E) log V). Contrato que main espera del solver:
DijkstraResult r = DijkstraSolver.findShortestPath(graph, start, end);
r.totalCost   // costo total en minutos (tipo no deducible; se compara con 24)
r.path        // List<Vertex> con la ruta ordenada de S02 a S08

3.4 Configuración Maven
Java 17, codificación UTF-8 y JUnit Jupiter 5.9.2 (API y engine) en alcance test. No hay plugins declarados: conviene agregar maven-surefire-plugin 2.22 o superior para que JUnit 5 se ejecute y exec-maven-plugin con la clase principal com.redsegura.app.RedSeguraApplication.
4. Estado de implementación
 
Figura 2. Clases presentes y faltantes, y preguntas del parcial por estado.
Componente	Paquete	Estado	Observaciones
RedSeguraApplication	app	Presente	Nombre del archivo distinto al de la clase pública.
Vertex	model	Presente	Completa; carpeta distinta a su paquete.
MixedGraph, DataFactory	graph, factory	Falta	Se usan en main pero no se entregaron.
DijkstraSolver, DijkstraResult	algorithms	Falta	Contrato deducido del uso en main.
Pruebas JUnit y menú Q1–Q4, Q6, Q7	test, app	Pendiente	JUnit configurado sin pruebas; el menú solo está anunciado en un comentario.

5. Hallazgos y recomendaciones
N.º	Prioridad	Hallazgo	Recomendación
1	Alta	Faltan MixedGraph, DataFactory, DijkstraSolver y DijkstraResult; sin ellas no compila.	Crear las cuatro clases en sus paquetes.
2	Alta	Redsegura.java declara public class RedSeguraApplication.	Renombrar el archivo a RedSeguraApplication.java.
3	Alta	Los archivos están en com/mycompany/redsegura/ y declaran com.redsegura.app y com.redsegura.model.	Mover a com/redsegura/app/ y com/redsegura/model/.
4	Media	El 24 está fijo en main y no hay pruebas.	Escribir DijkstraSolverTest con el caso S02 → S08; si totalCost es decimal, usar tolerancia.
5	Media	No se contempla ruta inexistente ni pesos negativos; faltan plugins en el pom.xml.	Lanzar una excepción clara, validar pesos y agregar surefire y exec.
6	Baja	Las preguntas Q1–Q4, Q6 y Q7 y el README no existen; los nombres de grupo y carpeta no coinciden.	Implementar el menú, agregar README y unificar el paquete base.

6. Puesta en marcha y conclusiones
Paso	Acción
1	Instalar JDK 17 y Maven; comprobar con java -version y mvn -version.
2	Corregir nombres y paquetes (hallazgos 2 y 3) y agregar las clases faltantes (hallazgo 1).
3	Ejecutar mvn clean compile y mvn test.
4	Ejecutar mvn exec:java -Dexec.mainClass="com.redsegura.app.RedSeguraApplication".

•  Diseño claro: la separación en cinco paquetes facilita agregar las preguntas restantes.
•  Avance parcial: existen el punto de entrada y la clase Vertex, completa e inmutable.
•  Siguiente paso: corregir los hallazgos 1 a 3, escribir la prueba de la Q5 (24 minutos) y luego el menú de las demás preguntas.

