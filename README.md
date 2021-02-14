# Proyecto Curso Android
Proyecto final del [Curso de Android](https://http://android.jediupc.com/) en la Universitat Politècnica de Catalunya (UPC), realizado con Android Studio y utilizando el lenguaje oficial de desarrollo en Android, Kotlin.

El proyecto incluye la utilización de:

- SharedPreferences para guardar datos localmente
- [SQLite](https://www.sqlite.org/index.html) como base de datos local, usando lenguaje SQL
- Una API para la persistencia de datos de forma remota (con la ayuda de Gson/Json)
- [Material Design](https://material.io/develop/android) para un mejor diseño de la interfaz
- Gradle para la gestión de dependencias
- Activities & Frames (y transmisión de datos entre ellos)
- Dialogs y alertas
- RecyclerView, ViewPager, Navigation Drawer
- GPS & Google Maps

La app consta con las siguientes pantallas/funcionalidades:

- Log-In para guardar el nombre del usuario
- Activity principal con un menú para ir a las diferentes pantallas de la app
- Calculadora simple
- Juego de cartas (encontrar parejas), con un cronómetro que cuenta los segundos pasados desde el inicio del juego. Al finalizar una partida, los resultados del usuario se guardan tanto en la base de datos SQLite como en la API.
- Fragment con las estadísticas locales (SQLite) y globales (API)
- Mapa que muestra nuestra localización actual, con un círculo de 500m de radio alrededor de nuestra posición, y botones para hacer y quitar zoom del mapa, así como otros dos para aumentar o reducir el radio en 500m.
