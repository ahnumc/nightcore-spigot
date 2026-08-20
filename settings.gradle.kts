pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "nightcore"

include(":main")
include(":bridge")
include(":utils")
include(":spigot")
include(":paper")
include(":intergration-placeholderapi")
