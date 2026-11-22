# 🐶 EdadCanina · v1

Aplicación Android (Java) que calcula la edad "humana" de un perro multiplicando su edad por 7.

Esta es la **versión 1**: funciona, pero no comprueba lo que escribe el usuario. Tu tarea es convertirla en la **versión 2** (rama `v2-validacion`), que valida la entrada y controla los errores sin que la app se cierre.

---

## Objetivos

Al terminar sabrás:

- Detectar por qué una app se cierra (`NumberFormatException`) y evitarlo con `try/catch`.
- Validar datos de entrada: campo vacío, texto no numérico y rango de valores.
- Mostrar errores al usuario con `EditText.setError()`.
- Sustituir "números mágicos" por constantes.
- Llevar los textos a `strings.xml` y usar formatos con varios parámetros.
- Trabajar en una rama de Git distinta de `main`.

---

## Paso 0 · Prepara el proyecto

1. Haz un **fork** de este repositorio (botón *Fork*) y clona tu fork:
   ```bash
   git clone https://github.com/TU_USUARIO/edadCaninav1.git
   ```
2. Ábrelo en Android Studio y ejecútalo en el emulador.

## Paso 1 · Rompe la app (a propósito)

Antes de arreglar nada, comprueba qué falla. Prueba estas entradas y anota qué pasa:

| Entrada | ¿Qué ocurre? |
|---|---|
| *(campo vacío)* | |
| `abc` | |
| `3.5` | |
| `0` | |
| `-4` | |
| `99999999999` | |
| `400000000` | |

> 💡 Cuando la app se cierre, abre el **Logcat** y busca la excepción. ¿Qué método la lanza? ¿Por qué el último caso no cierra la app pero da un resultado absurdo?

## Paso 2 · Crea la rama de trabajo

```bash
git checkout -b v2-validacion
```

Todo lo que hagas a partir de aquí irá en esta rama; `main` se queda como está.

## Paso 3 · Corrige los fallos que ya trae la v1

Antes de añadir nada nuevo, limpia lo que hay:

1. **`activity_main.xml`**: busca la etiqueta de cierre `</TextView>>` y quita el `>` que sobra.
2. **`strings.xml`**: en `tu_edad_format` hay una falta de ortografía ("fuero"). Corrígela.
3. **Texto escrito "a fuego"**: el `android:hint="Ejemplo: 6"` del `EditText` está directamente en el layout. Llévalo a `strings.xml` con el nombre `hint_edad` y úsalo con `@string/hint_edad`.
   > 💡 Android Studio marca este aviso en amarillo (*Hardcoded string*). Prueba `Alt + Enter` encima.
4. **`MainActivity.java`**: hay tres `import` que no se usan (`Insets`, `ViewCompat`, `WindowInsetsCompat`). Elimínalos (`Ctrl + Alt + O`).

## Paso 4 · Añade los mensajes de error

En `res/values/strings.xml` añade tres textos nuevos:

```xml
<string name="error_vacio">Introduce la edad de tu perro</string>
<string name="error_no_numero">Introduce un número entero válido</string>
<string name="error_rango">La edad debe estar entre %1$d y %2$d años</string>
```

> ❓ ¿Qué significan `%1$d` y `%2$d`? ¿En qué orden se rellenan?

## Paso 5 · Restringe la entrada desde el layout

En el `EditText` (`@+id/age_input`) añade:

```xml
android:inputType="number"
android:maxLength="2"
android:gravity="center"
android:importantForAutofill="no"
```

| Atributo | Para qué sirve |
|---|---|
| `inputType="number"` | Muestra el teclado numérico: sin letras, sin signo menos ni decimales. |
| `maxLength="2"` | No deja escribir más de 2 cifras. |
| `gravity="center"` | Centra el número en el campo. |
| `importantForAutofill="no"` | Evita que el sistema ofrezca autocompletar. |

> ⚠️ Esto **no sustituye** a la validación en Java: el campo puede quedarse vacío y nunca conviene fiarse solo de la interfaz. Por eso seguimos con el código.

## Paso 6 · Sustituye los números mágicos por constantes

Al principio de la clase `MainActivity` declara:

```java
private static final int FACTOR_CANINO = 7;
private static final int EDAD_MINIMA = 1;
private static final int EDAD_MAXIMA = 30; // ningún perro supera esta edad
```

y usa `FACTOR_CANINO` en lugar del `7`.

## Paso 7 · Convierte las vistas en atributos de la clase

Ahora `ageEdit` y `resultText` son variables locales de `onCreate()`. Como vas a usarlas desde otros métodos, decláralas como atributos:

```java
private EditText ageEdit;
private TextView resultText;
```

y en `onCreate()` asígnalas **sin volver a escribir el tipo**:

```java
ageEdit = findViewById(R.id.age_input);
resultText = findViewById(R.id.result_text);
```

> ❓ ¿Qué pasaría si dejaras `EditText ageEdit = findViewById(...)` dentro de `onCreate()`?

## Paso 8 · Saca la lógica del botón a un método

El `setOnClickListener` debe quedar así de corto:

```java
button1.setOnClickListener(view -> calcularEdad());
```

Toda la lógica pasa a un método nuevo: `private void calcularEdad()`.

## Paso 9 · Crea un método para mostrar errores

```java
private void mostrarError(String mensaje) {
    // 1. Mostrar el mensaje en el EditText (setError)
    // 2. Poner el foco en el EditText (requestFocus)
    // 3. Borrar el resultado anterior del TextView
}
```

## Paso 10 · Valida la entrada en `calcularEdad()`

Implementa el método siguiendo este orden. En cuanto algo falle, llama a `mostrarError(...)` y sal con `return`:

1. **Leer** el texto del `EditText` quitando espacios (`trim()`).
2. **¿Está vacío?** → `error_vacio`. Recuerda que `Integer.parseInt("")` lanza `NumberFormatException`.
3. **Convertir a `int`** dentro de un `try/catch (NumberFormatException e)` → si falla, `error_no_numero`.
4. **¿Está en rango?** Si `age < EDAD_MINIMA || age > EDAD_MAXIMA` → `error_rango`. Para rellenar `%1$d` y `%2$d`:
   ```java
   getString(R.string.error_rango, EDAD_MINIMA, EDAD_MAXIMA)
   ```
5. **Todo correcto**: quita el error del campo (`setError(null)`), calcula `age * FACTOR_CANINO` y muestra el resultado con `tu_edad_format`.

> ❓ ¿Por qué limitar la edad a 30 evita también el desbordamiento que viste en el Paso 1?

## Paso 11 · Vuelve a probar

Comprueba que **la app nunca se cierra**:

| Entrada | Resultado esperado |
|---|---|
| *(vacío)* | Error: "Introduce la edad de tu perro" |
| `0` | Error: "La edad debe estar entre 1 y 30 años" |
| `31` | Error de rango |
| `1` | "Si tu perro fuera humano tendría 7 años" |
| `30` | "Si tu perro fuera humano tendría 210 años" |

> 💡 Con el teclado numérico no puedes escribir `abc` ni `-4`. ¿Cómo comprobarías que el `try/catch` funciona? (Pista: quita `inputType` un momento.)

## Paso 12 · Guarda y sube tu trabajo

```bash
git add .
git commit -m "Validación de la entrada y control de errores aritméticos"
git push -u origin v2-validacion
```

Comprueba en GitHub que tu fork tiene ahora dos ramas: `main` y `v2-validacion`.

---

## ✅ Lista de comprobación

- [ ] La app no se cierra con ninguna entrada.
- [ ] Todos los textos están en `strings.xml` (ningún aviso *Hardcoded string*).
- [ ] No quedan números mágicos en `MainActivity`.
- [ ] `onCreate()` solo prepara la pantalla; la lógica está en `calcularEdad()`.
- [ ] El trabajo está en la rama `v2-validacion`, no en `main`.

## EdadCaninaMVC

La rama `v3-mvc` parte de `v2-validacion`. Su identificador de aplicación es `es.davante.edadcanina.mvc`, así que se pueden instalar las dos versiones en el mismo dispositivo.

### Reto elegido · Casos raros

Acepto `05` y `+5` porque ambos representan cinco años. El modelo quita los espacios de los extremos y usa `Integer.parseInt` en base diez antes de comprobar el rango de 1 a 30. En los dos casos debe devolver `OK` y 35 años humanos. Las pruebas `ceroInicial` y `signoPositivo` fijan esta decisión.

### Explícalo con tus palabras

1. **¿Qué diferencia hay entre `@{…}` y `@={…}`?** El resultado usa `@{…}` porque solo necesita leer el modelo y actualizar el texto en pantalla. El campo de entrada usa `@={…}` porque también tiene que escribir en el modelo cuando tecleo. Ese mismo enlace vacía el campo cuando pulso Limpiar.
2. **¿Qué pasa desde que pulso Calcular hasta que aparece 42?** Al escribir 6, el enlace ya ha guardado «6» en el modelo. El clic del XML llama al controlador, que pide al modelo que calcule. El modelo convierte el texto a entero, comprueba el rango y multiplica 6 por 7. Al cambiar la edad humana y la validación, avisa a DataBinding. El enlace del resultado da formato a 42 y lo hace visible porque el estado es `OK`.
3. **¿Qué cambiaría si el factor pasara de 7 a 6?** En el código de la aplicación solo cambiaría la constante `FACTOR` de `EdadCaninaModelo.java`. El controlador pide un cálculo y la vista muestra la respuesta. Ninguno necesita saber la fórmula. También actualizaría los resultados esperados en las pruebas, por ejemplo de 42 a 36 para una entrada de 6.

### Autoevaluación

- [x] El campo vacío o con espacios, las letras y los valores fuera de 1–30 reciben su error. Con 6 aparece 42. Limpiar vacía la entrada, oculta el resultado y quita el error, incluso después de calcular con el campo vacío.
- [x] La Activity crea el modelo y el controlador, los conecta al binding y guarda o restaura el estado.
- [x] El modelo expone tres propiedades observables, contiene las reglas y devuelve un enum. Los mensajes viven en recursos. Al editar la entrada caduca el cálculo anterior.
- [x] El controlador acepta las acciones Calcular y Limpiar y delega en el modelo sin guardar vistas.
- [x] El XML enlaza la entrada en ambos sentidos, muestra el resultado en un sentido y presenta el error con el adaptador del paquete `vista`.
- [x] El estado se reconstruye a partir del texto y de si se había pulsado Calcular. Esto también distingue el campo vacío inicial del campo vacío que ya muestra un error.
- [x] Hay diez pruebas JUnit del modelo y una del controlador. Se ejecutan con `./gradlew testDebugUnitTest`.
- [x] Los comentarios explican decisiones como aceptar el signo positivo, invalidar el resultado y guardar solo el estado indispensable.
- [x] Cada paso tiene un commit y una etiqueta, de `paso-1` a `paso-7`.

### Preguntas de los pasos

- **1. Clases generadas.** Activar DataBinding prepara el compilador. El fichero `ActivityMainBinding` aparece cuando `activity_main.xml` tiene la raíz `<layout>`.
- **2. Identificadores.** Con el binding, un campo que no existe falla al compilar. Un `R.id` inexistente también fallaba antes. La diferencia es que un identificador existente en *otro* layout sí podía compilar con `findViewById` y devolver `null` al ejecutar la app.
- **3. Idiomas.** Para traducir la aplicación añadiría recursos de texto. El modelo devuelve estados como `VACIO`, que la vista traduce al idioma activo.
- **4. Ida y vuelta.** Al teclear, la vista llama a `setEdadTexto`. Si el texto es nuevo, el modelo lo guarda y avisa. Si el enlace devuelve el mismo texto, la comprobación de igualdad corta la cadena antes de volver a avisar o borrar el resultado.
- **5. Adaptador.** Traducir un estado a un mensaje y dar el foco al campo son decisiones de pantalla. Por eso el adaptador vive en `vista`.
- **6. Controlador.** Cada botón solo expresa una intención. Un método corto basta porque el modelo ya sabe validar, calcular y limpiar.
- **7. Recreación.** El modelo pertenece a la instancia de la Activity. Un `ViewModel` podría conservarlo durante un giro. En este ejercicio, el `Bundle` permite reconstruirlo y distinguir una entrada recién editada de otra ya calculada.
