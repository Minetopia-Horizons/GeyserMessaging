plugins {
    id("java")
    id("io.freefair.lombok") version "8.6"
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "nl.mthorizons"
version = "1.0-SNAPSHOT"

tasks.shadowJar {
    archiveFileName = "geysermessaging-spigot.jar";
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    implementation(project(":common"))
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
}
