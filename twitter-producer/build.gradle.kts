plugins {
    kotlin("jvm")
    id("com.google.cloud.tools.jib")
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

jib {
    to {
        image = "sandjelkovic/shokker/${project.name}"
    }
    container {
        mainClass = "com.sandjelkovic.shokker.twitter.producer.MainKt"
        creationTime = "USE_CURRENT_TIMESTAMP"
    }
}
