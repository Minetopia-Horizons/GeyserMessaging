plugins {
    id("java-library")
    id("maven-publish")
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "nl.mthorizons"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}


dependencies {
    implementation(project(":common"))
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = "geysermessaging-api"
            pom {
                name.set("GeyserMessaging API")
                description.set("Description of API module")
                url.set("https://github.com/Minetopia-Horizons/GeyserMessaging")
                licenses {
                    license {
                        name.set("GNU Affero General Public License v3.0")
                        url.set("https://github.com/Minetopia-Horizons/GeyserMessaging/blob/main/LICENSE")
                    }
                }
                scm {
                    url.set("https://github.com/Minetopia-Horizons/GeyserMessaging/tree/main")
                }
            }
        }
    }
    repositories {
        maven {
            url = uri("$buildDir/repo")
        }
    }
}

tasks.shadowJar {
    archiveClassifier.set("")
    mergeServiceFiles()
}