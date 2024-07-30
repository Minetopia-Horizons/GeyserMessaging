group = "nl.mthorizons"
version = "1.0-SNAPSHOT"

tasks.jar {
    manifest {
        attributes["Automatic-Module-Name"] = "nl.mthorizons.api"
    }
}

dependencies {
    implementation(project(":common"))
}
