# vwap-test

## Goal

The goal of this test is to compare data coming from a provider and react to given conditions.

The provider notifies two types of data:

* Pricing Data: Fair Value of a product, calculated by an internal system.
* Market Data: Quantity and Price of the latest exchange transaction on a product.

For more information about the project read this [documentation](src/markdown/README.md) 

## Environment setup

Make sure this following software is installed

* [OpenJDK 17](https://adoptium.net/releases.html?variant=openjdk17&jvmVariant=hotspot)
* [Java 17](https://www.oracle.com/ca-fr/java/technologies/downloads/#jdk17-windows)
* [Junit](https://junit.org/junit5/)

The project runs with [intellij](https://www.jetbrains.com/idea/) on Windows and builds with gradle.

## Installation

To launch the project 

``./gradlew``

To execute tests 

```./gradlew test```

