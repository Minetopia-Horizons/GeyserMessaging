plugins {
    id("java")
    id("io.freefair.lombok") version "8.6"
}

group = "nl.mthorizons"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.0.1")
}
