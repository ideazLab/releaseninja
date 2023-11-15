plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
    id("io.micronaut.minimal.library")
    kotlin("plugin.lombok")
    id("io.freefair.lombok")
}
repositories {
    mavenCentral()
    maven {
        credentials {
            username = "maven"
            password = "CjHc&8TEhQK&snjj"
        }
        isAllowInsecureProtocol = true
        url = uri("http://maven.fitltd.com/repository/maven-releases/")
    }
    maven { url = uri("https://maven.pkg.jetbrains.space/data2viz/p/maven/dev") }
    maven { url = uri("https://maven.pkg.jetbrains.space/data2viz/p/maven/public") }
}
val kotlinVersion: String by System.getProperties()
val micronautVersion: String by System.getProperties()
dependencies {
    ksp("io.micronaut:micronaut-http-validation")
    ksp("io.micronaut.data:micronaut-data-document-processor")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    ksp("io.micronaut.security:micronaut-security-annotations")
    ksp("io.micronaut.tracing:micronaut-tracing-opentelemetry-annotation")
    api(project(":common"))
}
micronaut {
    version(micronautVersion)
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.ideazlab.releaseninja.*")
    }
}
tasks.test {
    useJUnitPlatform()
}
tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}
kotlin {
    jvmToolchain(17)
}