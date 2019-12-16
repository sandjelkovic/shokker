plugins {
    kotlin("jvm") version "1.3.61"
}

group = "com.sandjelkovic.shokker"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.apache.kafka:kafka-clients:2.0.0")
    implementation("com.twitter:hbc-core:2.2.0")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("org.slf4j:slf4j-log4j12:1.7.30")
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("io.github.microutils:kotlin-logging:1.7.7")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
