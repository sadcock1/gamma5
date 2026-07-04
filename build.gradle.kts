plugins {
    id("java")
    id("net.fabricmc.fabric-loom") version "1.16.1"
}

group = "net.s1a"
version = "1.0.0"

base { archivesName = "gamma5" }

repositories {
    // needed only for modCompileOnly against Sodium's Modrinth-hosted jar
    exclusiveContent {
        forRepository { maven { name = "Modrinth"; url = uri("https://api.modrinth.com/maven") } }
        filter { includeGroup("maven.modrinth") }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:26.2")
    implementation("net.fabricmc:fabric-loader:0.19.2")

    // soft/optional — compile against Sodium's API, never bundled or required at runtime
    compileOnly("maven.modrinth:sodium:mc26.2-0.9.0-fabric")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(25)
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") { expand("version" to project.version) }
}
