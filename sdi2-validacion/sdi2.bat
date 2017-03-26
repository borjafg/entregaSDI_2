@ECHO OFF
REM sdi2.bat n DirectorioOrigen DirectorioDestino (Debe estar el archivo sdi2-n.zip en el DirectorioOrigen y se descomprimirá en el Directorio Destino.)
REM Si no se pasan parámetros, muestra la ayuda...
IF %1.==. GOTO HELP
IF %2.==. GOTO HELP
IF %3.==. GOTO HELP
ECHO ==========================================================
ECHO sdi2.bat SCRIPT de Comprobacion de prueba completa:
ECHO - Primero se comprueba el formato: sdi2-fmt.bat
ECHO - Despues se lanza la prueba: sdi2-run.bat
ECHO ==========================================================
ECHO.
REM Primero comprobamos que el formato de la entrega sea el aducuado usando el script sdi2-fmt.bat 
call sdi2-fmt.bat %1 %2 %3
if "%errorlevel%"=="0" goto success
:failure
ECHO Error en el formato de la entrega "sdi2-%1"
goto FIN
:success
REM Segundo Podemos proceder con la prueba
call sdi2-run.bat %1 %3
GOTO FIN

:HELP
ECHO Prueba completa de la segunda entrega de SDI.
ECHO =================================================
ECHO sdi2.bat n DirectorioOrigen DirectorioDestino (Debe estar el archivo sdi2-n.zip en el DirectorioOrigen.)
ECHO.
GOTO FIN

:FIN
