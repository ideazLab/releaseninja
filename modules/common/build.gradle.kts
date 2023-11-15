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

    api("io.micronaut:micronaut-aop")
    api("io.micronaut.views:micronaut-views-thymeleaf")
    api("io.micronaut:micronaut-http-client")
    api("io.micronaut:micronaut-http-server")
    api("io.micronaut:micronaut-jackson-databind")
    api("com.fasterxml.jackson.module:jackson-module-kotlin")
    api("io.micronaut:micronaut-management")
    api("io.micronaut.session:micronaut-session")
    api("io.micronaut.beanvalidation:micronaut-hibernate-validator")
    api("io.micronaut.cache:micronaut-cache-caffeine")
    api("io.micronaut.data:micronaut-data-mongodb")
//    api("io.micronaut.redis:micronaut-redis-lettuce")
//    api("io.micronaut.email:micronaut-email-javamail")
//    api("io.micronaut.email:micronaut-email-template")
    api("io.micronaut.flyway:micronaut-flyway")
    api("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    api("io.micronaut.kotlin:micronaut-kotlin-runtime")
    api("io.micronaut.reactor:micronaut-reactor")
    api("io.micronaut.reactor:micronaut-reactor-http-client")
    api("io.micronaut.tracing:micronaut-tracing-opentelemetry-http")
    api("io.opentelemetry:opentelemetry-exporter-zipkin")
    api("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    api("org.jetbrains.kotlin:kotlin-reflect")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.4")
    api("io.micronaut.security:micronaut-security")
    api("org.springframework.security:spring-security-crypto:6.0.2")
    api("commons-logging:commons-logging:1.2")
    api("jakarta.annotation:jakarta.annotation-api")
    api("org.apache.commons:commons-lang3:3.12.0")
    api("com.konghq:unirest-java:3.14.1:standalone")
    api("org.yaml:snakeyaml:2.0")
    testImplementation("org.mongodb:mongodb-driver-sync")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.awaitility:awaitility-kotlin:4.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
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
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    compileTestKotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
}
kotlin{
    jvmToolchain(17)
}
