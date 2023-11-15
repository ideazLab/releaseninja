import io.micronaut.testresources.buildtools.KnownModules.MONGODB

plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("com.github.johnrengelman.shadow")
    id("io.micronaut.crac")
    id("io.micronaut.application")
    id("com.google.cloud.tools.jib")
    id("org.asciidoctor.jvm.convert")
    id("io.micronaut.test-resources")
    // id("io.micronaut.aot")
    kotlin("plugin.lombok")
    id("io.freefair.lombok")
}

version = "0.1"
group = "com.fitltd.releaseit"
val kotlinVersion: String by System.getProperties()
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
}

dependencies {
    ksp("io.micronaut:micronaut-http-validation")
    ksp("io.micronaut.data:micronaut-data-document-processor")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    ksp("io.micronaut.security:micronaut-security-annotations")
    ksp("io.micronaut.tracing:micronaut-tracing-opentelemetry-annotation")
    //amt
    implementation(project(":artifactory"))
    implementation(project(":nexus"))
    //scm
    implementation(project(":github"))
    implementation("io.micronaut.crac:micronaut-crac")
    implementation("io.micronaut.security:micronaut-security-oauth2")
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.micronaut.views:micronaut-views-thymeleaf")
    compileOnly("org.graalvm.nativeimage:svm")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.mongodb:mongodb-driver-sync")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.testcontainers:mongodb")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.awaitility:awaitility-kotlin:4.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    // aotPlugins("io.micronaut.security:micronaut-security-aot:4.0.0")
}

application {
    mainClass.set("com.ideazlab.releaseninja.ApplicationKt")
}
kotlin{
    jvmToolchain(17)
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
    jib {
        to {
            image = "gcr.io/myapp/jib-image"
        }
    }
}
graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.ideazlab.releaseninja.*")
    }
    testResources {
        additionalModules.addAll(MONGODB)                 // (2)
    }
//    aot {
//        // Please review carefully the optimizations enabled below
//        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
//        optimizeServiceLoading.set(false)
//        convertYamlToJava.set(false)
//        precomputeOperations.set(true)
//        cacheEnvironment.set(true)
//        optimizeClassLoading.set(true)
//        deduceEnvironment.set(true)
//        optimizeNetty.set(true)
//        configurationProperties.put("micronaut.security.jwks.enabled","false")
//    }
}



