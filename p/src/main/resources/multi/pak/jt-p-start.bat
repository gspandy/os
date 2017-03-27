@title HITLER-DB RUNING %1

@echo %~dp0

echo off
set "BASE_HOME=%~dp0.."
set LIBS=%BASE_HOME%/lib
set CLASSES=%BASE_HOME%
set CLASS_PATH=%CLASSPATH%;%LIBS%/*;%CLASSES%

echo BASE_HOME : %BASE_HOME%
echo CLASS_PATH:%CLASS_PATH%

pause
goto okHome

:okHome
java -Xms64m -Xmx256m -classpath %CLASS_PATH% com.hitler.server.TowerProvider &

:end
pause