# Hands-on with Vehicle SDK

The **Vehicle SDK** provides APIs that enable seamless integration with in-vehicle infotainment (IVI) systems, allowing for easy access to vehicle
information and control over key functionalities such as checking vehicle status, retrieving driving data, and modifying vehicle settings.

This document explores the process of retrieving and utilizing vehicle information using the **Vehicle SDK**, offering hands-on practice on how to
effectively leverage its capabilities.

## Features

The **Vehicle SDK** provides the following features:

- Check vehicle status (battery level, tire pressure, etc.)
- Access real-time driving data (speed, mileage, engine status, etc.)
- Retrieve and modify vehicle settings (climate control, seat position, etc.)
- Access vehicle diagnostics and alert information
- More details available at the [Developers Site](https://document.pleos.ai/api-reference/connect-sdk-pleos)

## Configuration

Add this to your `settings.gradle.kts` dependencies:

```kotlin
dependencyResolutionManagement {
    repositories {
        maven("https://nexus-playground.pleos.ai/repository/maven-releases/")
    }
}
```

Add this to your app module’s `build.gradle.kts` dependencies:

```kotlin
dependencies {
    implementation("ai.pleos.playground:Vehicle:2.0.3")
}
```

## Build a Driving Safety Score Calculator App

We are developing a sample app that allows easy retrieval of vehicle status and calculates a score.

Through this process, you will experience how to request vehicle data using the **Vehicle SDK**, retrieve and utilize vehicle information, and gain
hands-on knowledge of its capabilities.

## Excluded Features

- The **Vehicle SDK** requires special permissions and runs on the **Pleos Connect Emulator**, but it does not work on a standard AVD.
- For more details about the **Pleos Connect Emulator**, please refer to
  the [Developers Site](https://document.pleos.ai/docs/connect/guide/getting-started/application-development/setup-connect-sdk-emulator).
- For the design, please refer to the [common design guide](https://document.pleos.ai/docs/pleos-only/docs-design-pleos/intro).

## Applicable Target

- Service providers aiming to develop services using the **Vehicle SDK** on **Android Automotive OS(Connect Pleos)**.
- Developers who need hands-on experience with the **Vehicle SDK**.

## How to use Vehicle Simulator app

Before launching the simulator, execute the following commands:

```shell
adb root
adb shell setenforce 0
```

## Environment

| Component | Requirement                          |
|-----------|--------------------------------------|
| SDK       | Pleos Connect.v2.0.5                 |
| OS        | Android 6.0 (API level 23) or higher |
| Gradle    | 8.0 or higher                        |
| Language  | Java, Kotlin                         |
| AVD       | Pleos Connect Emulator               |

