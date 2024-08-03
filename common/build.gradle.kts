plugins {
    id("java")
    id("io.freefair.lombok") version "8.6"
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "nl.mthorizons"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
}

tasks.shadowJar {
    relocate("com.fasterxml.jackson", "nl.mthorizons.jackson")
}