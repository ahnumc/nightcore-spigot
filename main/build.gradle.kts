dependencies {
    implementation(project(":bridge"))
    implementation(project(":utils"))
    implementation(project(":paper"))
    implementation(project(":spigot"))
    implementation(project(":intergration-placeholderapi"))

    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    compileOnly("com.zaxxer:HikariCP:6.3.2")
    compileOnly("com.mojang:datafixerupper:8.0.16")
    compileOnly("org.xerial:sqlite-jdbc:3.49.1.0")
    compileOnly("it.unimi.dsi:fastutil-core:8.5.16")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7") {
        exclude(group = "org.bukkit", module = "bukkit")
    }
    compileOnly("net.luckperms:api:5.5")
    compileOnly("org.geysermc.floodgate:api:2.2.4-SNAPSHOT")
    compileOnly("org.geysermc.cumulus:cumulus:1.1.2")
    compileOnly("org.black_ixx:playerpoints:3.3.3")
    compileOnly("com.bencodez:votingplugin:7.0")
    compileOnly("com.magmaguy:EliteMobs:10.0.3")
    compileOnly("su.nightexpress.excellenteconomy:ExcellentEconomy:2.8.0")
    compileOnly("com.nexomc:nexo:0.9.0")
    compileOnly("io.th0rgal:Oraxen:1.155.1")
    compileOnly("com.github.LoneDev6:api-itemsadder:3.6.2-beta-r3-b")
    compileOnly("io.lumine:MythicLib-dist:1.7.1-SNAPSHOT")
    compileOnly("net.Indyuce:MMOItems-API:6.10.1-SNAPSHOT")
    compileOnly("net.momirealms:craft-engine-core:0.0.67")
    compileOnly("net.momirealms:craft-engine-bukkit:0.0.67")
    compileOnly("maven.modrinth:SCore:5.25.7.19")
}

// ExcellentCrates is an optional integration that is not part of this server
// deployment. Keep its source available, but do not require its external API
// or register its adapters in this build.
sourceSets {
    named("main") {
        java.exclude(
            "**/ExcellentCratesHandler.java",
            "**/ECratesCrateAdapter.java",
            "**/ECratesKeyAdapter.java",
        )
    }
}

tasks.register<Jar>("shaded") {
    archiveClassifier.set("shaded")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(tasks.jar)
    from(tasks.jar.map { zipTree(it.archiveFile) })
    configurations.runtimeClasspath.get()
        .filter { it.name.startsWith("bridge-") || it.name.startsWith("utils-") || it.name.startsWith("paper-") || it.name.startsWith("spigot-") || it.name.startsWith("intergration-placeholderapi-") }
        .forEach { from(zipTree(it)) }
}
