@ECHO OFF
REM Este script comprueba que el archivo zip que se pasa cumple con los requisitos solicitados 
REM para le entrega 2 de SDI 16-17
REM el formato de llamada es
REM sdi2-fmt.bat n DirectorioOrigen DirectorioDestino (Debe estar el archivo sdi2-n.zip en el DirectorioOrigen y se descomprimirá en el Directorio Destino.)
REM Si no se pasan parámetros, muestra la ayuda...
IF %1.==. GOTO HELP
IF %2.==. GOTO HELP
IF %3.==. GOTO HELP
ECHO ==========================================================
ECHO sdi2-fmt.bat SCRIPT de Comprobacion de formato de entrega
ECHO ==========================================================
ECHO 1) Comprobando carpeta de origen: %2 ...
IF NOT EXIST %2 GOTO NODIRORIG
ECHO OK
ECHO 2) Creando carpeta destino %3 si fuera necesario ....
IF NOT EXIST %3 mkdir %3
IF NOT EXIST %3 GOTO NODIRDEST
ECHO OK
ECHO 3) Comprobando existencia de archivo "%2/sdi2-%1.zip" ...
REM Si no existe el archivo, envía un mensaje de error.
IF NOT EXIST "%2\sdi2-%1.zip" GOTO NOFILE
ECHO OK
ECHO 4) Copiando archivo "%2\sdi2-%1.zip" a %3
REM Copiamos el archivo sdi al directorio destino
copy %2\sdi2-%1.zip %3
REM Borramos la carpeta de la entrega si se hubiera descompromido anteriormente
ECHO 5)Borramos la carpeta de la entrega si se hubiera descompromido anteriormente
IF EXIST %3\sdi2-%1 rd %3\sdi2-%1 /s /q
ECHO OK
ECHO 6) Descomprimiendo "%3\sdi2-%1.zip"...
7z.exe x %3\sdi2-%1.zip -o%3
set "diractual=%cd%"
REM Nos movemos al directorio destino
ECHO 7) Nos movemos al directorio %3
cd /D %3
ECHO 8) Comprobando carpeta de la entrega "sdi2-%1" ... 
IF NOT EXIST sdi2-%1 GOTO UNZIPFAIL1
ECHO OK
cd sdi2-%1
ECHO 9) Comprobando carpeta de proyecto "sdi2-%1\sdi2-%1" ... 
IF NOT EXIST sdi2-%1 GOTO PRO
ECHO OK
ECHO 10) Comprobando la existencia del archivo de lanzamiento de la base de datos "sdi2-%1\sdi2-%1\data\startup.bat" ...  
IF NOT EXIST sdi2-%1\data\startup.bat GOTO DATA
ECHO OK
ECHO 10) Comprobando la existencia de la librería "sdi2-%1\sdi2-%1\lib\hsqldb.jar" ...  
IF NOT EXIST sdi2-%1\lib\hsqldb.jar GOTO DATA
ECHO OK
ECHO 11) Comprobando el archivo war "sdi2-%1\sdi2-%1.war" ... 
IF NOT EXIST sdi2-%1 GOTO WAR
ECHO OK
ECHO 12) Comprobando la carpeta de pruebas "sdi2-%1\sdi2-%1-pruebas" ... 
IF NOT EXIST sdi2-%1-pruebas GOTO PROPRUEBAS
ECHO OK
ECHO 13) Comprobando el archivo JAR de pruebas "sdi2-%1\sdi2-%1-pruebas.jar" ... 
IF NOT EXIST sdi2-%1-pruebas.jar GOTO PRUEBASjar
ECHO OK
ECHO 14) Comprobando documento de Informe "sdi2-%1\sdi2-%1.pdf" ... 
IF NOT EXIST sdi2-%1.pdf GOTO PDF
ECHO OK
ECHO 15) Comprobando archivos de resultados de pruebas "sdi2-%1\sdi2-%1.xlsx" ... 
IF NOT EXIST sdi2-%1.xlsx GOTO XLSX
ECHO OK
GOTO FINNORMAL

:HELP
ECHO =================================================
ECHO Comprobacion de formato entrega SDI 2.
ECHO =================================================
ECHO sdi2-fmt.bat n DirectorioOrigen DirectorioDestino (Debe estar el archivo sdi2-n.zip en el DirectorioOrigen.)
ECHO.
GOTO FIN

:NODIRORIG
ECHO ERROR: El directorio origen %2 no existe
ECHO.
GOTO FIN

:NODIRDEST
ECHO ERROR: El directorio destino %3 no se puedo crear
ECHO.
GOTO FIN


:NOFILE
ECHO ERROR: El archivo "%2/sdi2-%1.zip" no existe
ECHO.
GOTO FIN

:UNZIPFAIL1
ECHO ERROR: Carpeta "sdi2-%1" no creada al descomprimir
ECHO.
GOTO FIN

:PRO
ECHO ERROR: Carpeta de proyecto "sdi2-%1\sdi2-%1" no existente
GOTO FIN

:DATA 
ECHO ERROR: Script de lanzamiento de la base de datos "sdi2-%1\sdi2-%1\data\startup.bat" no existente o falta libreria "sdi2-%1\sdi2-%1\lib\hsqldb.jar"
GOTO FIN

:WAR
ECHO ERROR: Archivo "sdi2-%1.war" no existente
GOTO FIN

:PROPRUEBAS
ECHO ERROR: Carpeta de proyecto de pruebas "sdi2-%1\sdi2-%1-pruebas" no existente
GOTO FIN

:PRUEBASJAR
ECHO ERROR: Archivo JAR de pruebas "sdi2-%1\sdi2-%1-pruebas.jar" no existente
GOTO FIN

:XLSX
ECHO ERROR: Archivo excel de resultados "sdi2-%1\sdi2-%1.xlsx" no existente
GOTO FIN

:PDF
ECHO ERROR: Falta el archivo de INFORME "sdi2-%1.pdf"
GOTO FIN 

:FIN
cd /D %diractual%
EXIT /b 1

:FINNORMAL
cd /D %diractual%
EXIT /b 0
