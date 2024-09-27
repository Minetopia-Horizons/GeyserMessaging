plugins {
    id("java")
    id("io.freefair.lombok") version "8.6"
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "nl.mthorizons"
version = "1.0-SNAPSHOT"

tasks.shadowJar {
    archiveFileName = "geysermessaging-geyser.jar";
}

repositories {
    mavenCentral()
    maven("https://repo.opencollab.dev/main/")
}

dependencies {
    implementation(project(":common"))
    implementation("org.spongepowered:configurate-core:4.1.2")
    implementation("org.spongepowered:configurate-hocon:4.1.2")
    compileOnly("org.geysermc.geyser:api:2.2.0-SNAPSHOT")
    compileOnly(files("libs/Geyser-Standalone.jar"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.shadowJar {
    relocate("com.fasterxml.jackson", "nl.mthorizons.jackson")
}