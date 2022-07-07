# ComparticionArchivosPC

Funcionamiento del servidor:
-Descarga de ficheros .txt

-El servidor lee de inicio un fichero con los usuarios registrados en el servidor. En este caso tenemos un ejemplo en la carpeta ArchivosPC con un fichero users en el que aparece el nombre del usuario seguido de los ficheros que tiene.

-Cada cliente tiene una carpeta con nombre 'ArchivosPC' + su nombre de usuario. Esta carpeta contiene un archivo llamado 'archivos' + nombre de usuario. También contiene los ficheros para que los descarguen otros clientes del servidor. En este caso tenemos dos clientes ejemplo en las carpetas 'ArchivosPCCesar' y 'ArchivosPCJuan'. Estas tres carpetas tienen que estar en el disco C. Si quieres modificarlo hay que cambiar la ubicacion en la clase cliente, linea 39 y en la clase servidor linea 68.

-Al iniciar cada cliente nos preguntará el nombre de usuario. Si no esta registrado, da error. Para registrarlo escribir el nombre en el fichero 'users.txt' de la carpeta 'ArchivosPC'.

-Una vez iniciado el servidor, empezará a aceptar clientes. Si no esta iniciado el servidor da error. Iniciar primero el servidor.

-La funcionalidad del cliente es la indicada en el guión de la práctica. Muestra la tabla de clientes en el servidor con sus ficheros, descargar un fichero, inidicando primero la opción 2 y después nos preguntará por el nombre del fichero que queremos descargar. Al descargarlo se modifican las tablas y podemos ver el fichero descargado y otro cliente puede descargarlo de él también. Al salir del servidor de modifica la base de datos de los clientes. Cada vez que se añade un fichero a un cliente, se actualiza el fichero del servidor.

-He usado un semáforo para escribir en el fichero de salida ya que solo puede escribir uno a la vez. Para leerlo no hace falta ya que se lee solo al arrancar el servidor y nadie puede acceder al fichero hasta que no reciba clientes.

-He usado un lock justo, lock Rompe Empate, para que los oyentes clientes modifiquen la tabla de datos del servidor.

-He usado un monitor para que se muestren los elementos de la consola en orden. Primero el de la acción que hemos realizado(en la conexión, en la tabla de ficheros y al salir) y después el contenido del menú del usuario.

-He probado a conectarme con un usuario en otra máquina y funciona.
