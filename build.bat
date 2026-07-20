@echo off
setlocal
where java >nul 2>nul || (echo Java was not found in PATH.& exit /b 1)
where javac >nul 2>nul || (echo javac was not found. Install a full JDK 25, not only a JRE.& exit /b 1)
where mvn >nul 2>nul || (echo Maven was not found in PATH. Install Maven 3.9+ or build in IntelliJ.& exit /b 1)
java -version 2>&1 | findstr /R /C:"version \"25" /C:"openjdk version \"25" >nul || (
  echo This project requires JDK 25.
  java -version
  exit /b 1
)
javac -version 2>&1 | findstr /R /C:"javac 25" >nul || (
  echo This project requires javac 25.
  javac -version
  exit /b 1
)
call mvn clean package
if errorlevel 1 exit /b %errorlevel%
echo.
echo Built jar:
dir /b target\BetterChests-Albion-*.jar
