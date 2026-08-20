import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.Copy
import org.gradle.api.plugins.JavaPluginExtension

group = "su.nightexpress.nightcore"
version = "2.16.4"

allprojects {
    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.opencollab.dev/maven-snapshots/")
        maven("https://repo.opencollab.dev/maven-releases/")
        maven("https://libraries.minecraft.net/")
        maven("https://jitpack.io")
        maven("https://repo.codemc.io/repository/maven-releases/")
        maven("https://repo.rosewooddev.io/repository/public/")
        maven("https://nexus.bencodez.com/repository/maven-public/")
        maven("https://nexus.phoenixdevt.fr/repository/maven-public/")
        maven {
            url = uri("https://repo.nightexpressdev.com/releases")
            metadataSources {
                mavenPom()
                artifact()
            }
        }
        maven("https://repo.oraxen.com/releases")
        maven("https://repo.nexomc.com/releases")
        maven("https://repo.magmaguy.com/releases")
        maven("https://mvn.lumine.io/repository/maven-public/")
        maven("https://api.modrinth.com/maven/")
        maven("https://repo.bg-software.com/repository/dependencies/")
        maven("https://repo.alessiodp.com/releases/")
        maven("https://repo.momirealms.net/releases/")
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
}

subprojects {
    apply(plugin = "java-library")

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
        withSourcesJar()
    }

    dependencies {
        add("compileOnly", "org.jspecify:jspecify:1.0.0")
    }

    tasks.withType<JavaCompile>().configureEach {
        options.release.set(25)
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }

    tasks.withType<Copy>().matching { it.name == "processResources" }.configureEach {
        filteringCharset = "UTF-8"
        filesMatching(listOf("plugin.yml", "paper-plugin.yml")) {
            expand(mapOf("project" to mapOf("version" to project.version.toString())))
        }
    }
}
