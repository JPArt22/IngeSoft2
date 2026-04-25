# 🚀 Tarea de APIs: REST y GraphQL

## 📘 Parte 1: API REST con Rick and Morty

- **¿Qué API elegiste y por qué?**
  Elegí la API de **Rick and Morty** porque es extremadamente amigable para empezar, no requiere procesos de autenticación y permite practicar búsquedas avanzadas mediante parámetros de consulta (Query Params) de forma muy clara.

- **¿Qué datos devuelve?**
  Devuelve objetos en formato JSON con información detallada e interconectada sobre personajes (especie, origen, estado, imagen), ubicaciones (tipo de planeta, residentes) y episodios (fecha de emisión, personajes que aparecen).

- **¿Usa token o no? ¿Qué tipo?**
  No utiliza ningún tipo de token, API Key o registro previo. Es una API pública y de acceso totalmente libre.

- **¿Qué código de estado recibiste en cada request?**
  En todas mis peticiones correctas recibí el código **200 OK**, lo que confirma que el servidor procesó la solicitud exitosamente y me devolvió la información esperada.

- **¿Qué aprendiste diferente a JSONPlaceholder?**
  Aprendí que las APIs reales tienen una estructura más profunda orientada a la escalabilidad. Por ejemplo, la API de Rick and Morty incluye un objeto `info` en sus respuestas que indica cuántas páginas de datos existen, lo cual es vital para la paginación. También aprendí a combinar múltiples filtros en una misma URL usando el símbolo `&` (ej. `?name=morty&status=alive`).

---

## 🔮 Parte 2: API GraphQL con Countries API

- **¿Qué diferencia encontraste vs REST?**
  La mayor diferencia es la flexibilidad y la eficiencia. En REST necesitaba consultar múltiples URLs (endpoints) y a menudo recibía mucha información innecesaria (Overfetching). Con GraphQL, envié todas mis peticiones (tipo POST) a una **única URL**, escribiendo una Query con la estructura exacta de los campos que necesitaba, y el servidor me devolvió estrictamente eso, ni un dato más ni uno menos.

- **¿Cuántos requests REST necesitarías para reemplazar tu query más compleja?**
  Para reemplazar mi Query más compleja (donde pedí un país específico, la lista de sus idiomas y sus estados/departamentos al mismo tiempo), en una API REST tradicional habría necesitado al menos **3 requests separadas**: 
  1. `/countries/CO` (Datos del país)
  2. `/countries/CO/languages` (Idiomas)
  3. `/countries/CO/states` (Estados)

- **¿En qué proyecto real usarías GraphQL?**
  Lo usaría sin duda en aplicaciones móviles, donde optimizar el consumo de datos y el ancho de banda es crucial para la experiencia del usuario. También sería ideal en *Dashboards* o paneles de administración complejos que necesitan agrupar y mostrar en una sola pantalla información que proviene de múltiples tablas o microservicios (por ejemplo: perfil de usuario, sus últimas compras y recomendaciones, todo en una sola carga).