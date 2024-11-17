import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.0-RC"
    id("com.gradleup.shadow") version "8.3.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

group = "me.aroze"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.incendo:cloud-core:2.0.0-SNAPSHOT")
    implementation("org.incendo:cloud-paper:2.0.0-SNAPSHOT")
    implementation("org.incendo:cloud-kotlin-extensions:2.0.0-SNAPSHOT")
    implementation("org.incendo:cloud-kotlin-coroutines:2.0.0-SNAPSHOT")
    implementation("org.incendo:cloud-kotlin-coroutines-annotations:2.0.0-SNAPSHOT")
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

tasks.withType<KotlinCompile>() {
    compilerOptions {
        freeCompilerArgs.add("-java-parameters")
    }
}

bukkit {
    main = "me.aroze.spyglass.Spyglass"
    authors = listOf("Aroze").sorted()
    apiVersion = "1.20"
    description = "Lesbians :3"
}
