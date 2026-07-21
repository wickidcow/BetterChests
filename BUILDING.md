# Building BetterChests Albion

## Requirements

- JDK 25 (a JRE alone is not enough; `javac` must be present)
- Maven 3.9 or newer
- Internet access to PaperMC's Maven repository and JitPack

Verify the tools:

```text
java -version
javac -version
mvn -version
```

All three Java commands should report version 25.

## Windows

Open Command Prompt in the project folder and run:

```bat
build.bat
```

Or directly:

```bat
mvn clean package
```

## Linux/macOS

```sh
./build.sh
```

Or directly:

```sh
mvn clean package
```

The usable plugin jar is created at:

```text
target/BetterChests-Albion-2.0.2-26.2.jar
```

Do not copy `original-*.jar` or dependency jars to the server.

## IntelliJ IDEA

1. Open this folder as a Maven project.
2. Select a JDK 25 project SDK.
3. Open the Maven tool window.
4. Run `Lifecycle > clean`, then `Lifecycle > package`.
5. Copy the jar from `target/` to the server's `plugins/` folder.

## GitHub Actions

The included `.github/workflows/build.yml` builds on every push and pull request. A downloadable jar appears under the workflow run's **Artifacts** section.

## Dependency override

The default build targets Paper API `26.2.build.62-beta` and Slimefun RC-37 (the upstream published addon API). A fork can override either version without editing the POM:

```sh
mvn clean package \
  -Dpaper.version=26.2.build.62-beta \
  -Dslimefun.version=RC-37
```
