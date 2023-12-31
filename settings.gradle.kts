pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RadiusAgentWeather"
include(":app")
include(":core")
include(":core-ui")
include(":weather")
include(":weather:data")
include(":weather:domain")
include(":weather:presentation")
