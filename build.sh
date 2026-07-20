#!/usr/bin/env sh
set -eu
command -v java >/dev/null 2>&1 || { echo "Java was not found in PATH."; exit 1; }
command -v javac >/dev/null 2>&1 || { echo "javac was not found; install a full JDK 25, not only a JRE."; exit 1; }
command -v mvn >/dev/null 2>&1 || { echo "Maven 3.9+ was not found in PATH."; exit 1; }
java -version 2>&1 | grep -Eq 'version "25|openjdk version "25' || {
  echo "This project requires JDK 25."
  java -version
  exit 1
}
javac -version 2>&1 | grep -Eq 'javac 25' || {
  echo "This project requires javac 25."
  javac -version
  exit 1
}
mvn clean package
printf '\nBuilt jar:\n'
ls -1 target/BetterChests-Albion-*.jar
