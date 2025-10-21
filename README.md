# 🔍 App Search Engine 

![Imagen del proyecto](https://raw.githubusercontent.com/victorgranadosjimenez/search-engine/refs/heads/master/diagrama.PNG?raw=true)
![Imagen del proyecto](https://raw.githubusercontent.com/victorgranadosjimenez/search-engine/refs/heads/master/Captura.JPG?raw=true)
![Imagen del proyecto](https://raw.githubusercontent.com/victorgranadosjimenez/search-engine/refs/heads/master/Captura1.JPG?raw=true)




## Ejemplo en vivo
https://victorgranados.com/search-engine/




## Descripción 📑
Cuando haces una búsqueda en Google o en un sitio con miles de productos, no se recorren todos los registros en tiempo real, sino que se crea un índice, una estructura de datos optimizada (como un diccionario invertido).
Cada palabra apunta a los documentos donde aparece.




## Etapas del proyecto:

🧩 1. Indexación
- Añadimos documentos (título + contenido) con endpoint POST "/api/documents"
- Los procesamos (tokenizar, eliminar stopwords, normalizar).
- Creamos un índice invertido que nos dice en qué documentos aparece cada palabra 
y lo guardamos en Base de datos.


🔍 2. Búsqueda
 Dado un texto de búsqueda, encontrar los documentos más relevantes por palabras clave.
 Usar algoritmos de ranking básicos (TF-IDF).




⚡ 3. Interfaz o API
Buscamos con un endpoint REST  /search?q=palabra
Muestra primero los documentos más relevantes, según cuántas veces aparece la palabra.
Además:
- Resaltamos en amarillo las coincidencias en los resultados.
- podemos ver el índice invertido.
- Permitimos búsquedas con ranking de relevancia (TF-IDF)




## 🧱 Arquitectura del proyecto
Este proyecto sigue una estructura **MVC simplificada**, organizada de la siguiente forma:




## 🚀 Endpoints principales (API REST)

# GET	/api/documents
Devuelve todos los documentos
# POST	/api/documents
Crea un nuevo documento
# GET	/api/documents/search?q=palabra
Busca documentos que contengan la palabra
# GET	/api/documents/search/ranked?q=palabra
Devuelve resultados ordenados por relevancia
# GET	/api/documents/index
Devuelve el índice invertido completo




💻 Frontend
El frontend es una interfaz sencilla desarrollada con HTML, CSS y JavaScript, que interactúa con el backend mediante fetch.
Funcionalidades:
- Añadir nuevos documentos
- Buscar por palabra clave o ranking
- Ver el índice invertido en formato JSON
Interfaz responsive con estilo minimalista tipo Google




🧠 Lógica del índice invertido
Cada documento se tokeniza (palabras individuales), y se crea una estructura Map<String, Set<Long>>, donde cada palabra apunta a los IDs de los documentos que la contienen.
Esto permite realizar búsquedas rápidas sin escanear todo el texto.




⚙️ Cómo ejecutar el proyecto
1. Clona el repositorio:
git clone https://github.com/victorgranadosjimenez/search-engine.git
2. Abre el proyecto en tu IDE (IntelliJ, VSCode o Eclipse).
- Asegúrate de tener MySQL ejecutándose en tu máquina.
3. Ejecuta la aplicación:
mvn spring-boot:run
4. Abre el frontend (por ejemplo con http-server o doble clic en index.html):
npx http-server frontend
5. Visita:
http://localhost:8080/api/documents






## Tecnologías 🛠
[![JAVA](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://es.wikipedia.org/wiki/Java_(lenguaje_de_programaci%C3%B3n))
[![SPRINGBOOT](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring&logoColor=white)](https://en.wikipedia.org/wiki/Spring_Boot)
[![MYSQL](https://img.shields.io/badge/-SQL-000?&logo=MySQL&logoColor=4479A1)](https://en.wikipedia.org/wiki/MySql)
[![JAVASCRIPT](https://shields.io/badge/JavaScript-F7DF1E?logo=JavaScript&logoColor=000&style=flat-square)](https://en.wikipedia.org/wiki/JavaScript)



## Vista previa del proyecto
Si quieres hechas un vistazo al proyecto, te recomiendo:

![Imagen del proyecto](https://raw.githubusercontent.com/victorgranadosjimenez/search-engine/refs/heads/master/Captura.JPG?raw=true)
![Imagen del proyecto](https://raw.githubusercontent.com/victorgranadosjimenez/search-engine/refs/heads/master/Captura1.JPG?raw=true)




## Autor ✒️
VÍCTOR GRANADOS JIMÉNEZ

<img src="https://avatars.githubusercontent.com/u/57761479?v=4" width=115><br>

* [Portafolio](https://victorgranados.com/)
* [Perfil Github](https://github.com/victorgranadosjimenez)
* [Correo](granadosvictor01@gmail.com)
* [LinkedIn](www.linkedin.com/in/victorgranadosjimenez/)




## Instalación 
Este proyecto no necesita de instalación. Simplemente abre la carpeta o haz doble click en el .html


  
## Licencia 📄
MIT Public License v3.0
No puede usarse comencialmente.





