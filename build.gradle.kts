plugins {
    val kotlinVersion: String by System.getProperties()
    val micronautPluginVersion: String by System.getProperties()
    kotlin("jvm") version kotlinVersion
    //id("org.jetbrains.kotlin.kapt") version kotlinVersion apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.11" apply false
    id("io.micronaut.minimal.library") version micronautPluginVersion apply false
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion apply false
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
    id("io.micronaut.crac") version micronautPluginVersion apply false
    id("io.micronaut.application") version micronautPluginVersion apply false
    id("com.google.cloud.tools.jib") version "2.8.0" apply false
    id("org.asciidoctor.jvm.convert") version "3.3.2" apply false
    id("io.micronaut.test-resources") version micronautPluginVersion apply false
    id("io.micronaut.aot") version "3.7.10" apply false
    kotlin("plugin.lombok") version "1.9.0" apply false
    id("io.freefair.lombok") version "5.3.0" apply false
}
repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
    maven{url = uri("https://maven.pkg.jetbrains.space/data2viz/p/maven/dev")}
    maven{url = uri("https://maven.pkg.jetbrains.space/data2viz/p/maven/public")}
}