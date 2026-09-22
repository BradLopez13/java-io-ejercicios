# java-io-ejercicios

Ejercicios de clase del ciclo de DAM (2023) sobre ficheros de texto en Java con `FileReader`, `BufferedWriter` y ficheros temporales. La interfaz son diálogos de `JOptionPane`.

## Qué hay

| Clase | Qué hace |
| --- | --- |
| `Fichero1` | Añadir, buscar y modificar registros en `fichero1.txt`. |
| `fichero2` | Lo mismo sobre `fichero2.txt`, más un borrado lógico: primero se marcan registros y después se eliminan todos los marcados. |

Para modificar un registro, el programa reescribe el fichero entero en un temporal, porque un fichero de texto secuencial no se puede editar en su sitio.

## Cómo ejecutarlo

Requiere un JDK 8 o superior. Desde la carpeta `Ficheros`:

```bash
javac -d out src/*.java
java -cp out Fichero1
```

## Limitaciones conocidas

- Es un ejercicio de clase: sin tests, y las rutas de los ficheros están escritas en el código.
- Las excepciones se muestran como un mensaje genérico, sin detalle del fallo.
