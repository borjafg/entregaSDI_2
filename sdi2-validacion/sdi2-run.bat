
@echo off
REM Este script ejecuta la prueba de la entrega a partir del archivo sdi2-n.zip descomprimido y ya validado con el script sdi2-fmt.bat
REM El formato de llamada es
REM sdi2-run.bat n Raiz (La carpeta descomprimida de la entrega (sdi2-n) debe colgar del directorio Raiz)
REM Si no se pasan parámetros, muestra la ayuda...
IF %1.==. GOTO HELP
IF %2.==. GOTO HELP
ECHO.
ECHO.
ECHO ==========================================================
ECHO sdi2-run.bat SCRIPT de lanzamiento de las pruebas
ECHO Asegurate de que:
ECHO *** Has lanzado previamente Wilfly externo: s:\wildflyext.bat 
ECHO *** Has cerrado el servidor de base de datos hsqldb.
ECHO ==========================================================

REM Definimos variables de entorno necesarias para lanzar las pruebas
call s:\_common.bat
set UNIDAD=S:
set JAVA=%UNIDAD%\jdk
set "diractual=%cd%"
set "dirlib=%cd%\libtest"
set JARTEST=sdi2-%1-pruebas.jar
set LIBSTEST=.;%JARTEST%;%dirlib%\junit.jar;%dirlib%\hamcrest.jar;%dirlib%\hsqldb.jar;%dirlib%\selenium-server-standalone-2.53.0.jar;c:\windows\system32
set "dirlib=%cd%"

REM Nos movemos a la carpeta de entrega
ECHO 1) Nos movemos a la carpeta de la entrega
cd /D %2\sdi2-%1
ECHO 2) Lanzamos la base de datos y esperamos 1 segundo antes de desplegar el proyecto
cd /D sdi2-%1\data
start startup.bat
TIMEOUT /T 1
ECHO 3) Volvemos a la carpeta de entrega
cd /D %diractual%
cd /D %2\sdi2-%1
ECHO 4) Borramos su existiera previamente, copiamos el proyecto al servidor de explotacion externo y esperamos 2 segundos antes de lanzar las pruebas
IF EXIST s:\wildflyext\standalone\deployments\sdi2-%1.war del s:\wildflyext\standalone\deployments\sdi2-%1.war
TIMEOUT /T 2
copy sdi2-%1.war s:\wildflyext\standalone\deployments
TIMEOUT /T 2
IF EXIST s:\wildflyext\standalone\deployments\sdi2-%1.war.undeployed del s:\wildflyext\standalone\deployments\sdi2-%1.war.undeployed
ECHO 5) Ejecutamos las pruebas
IF NOT EXIST S:\firefox\FirefoxPortable.exe GOTO :FIREFOX
%JAVA%\bin\java -cp %LIBSTEST% org.junit.runner.JUnitCore com.sdi.tests.Tests.PlantillaSDI2_Tests1617
GOTO FINNORMAL

:HELP
ECHO =================================================
ECHO Lanzamiento de las pruebas de la entrega SDI 2.
ECHO =================================================
ECHO sdi2-run.bat n Raiz (La carpeta descomprimida de la entrega (sdi2-n) debe colgar del directorio Raiz)
ECHO.
GOTO FIN

:FIREFOX
ECHO ERROR: No se encuentra Firefox portable S:\firefox\FirefoxPortable.exe
GOTO FIN
:FIN
cd /D %diractual%
EXIT /b 1

:FINNORMAL
cd /D %diractual%
EXIT /b 0
