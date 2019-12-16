package com.sandjelkovic.shokker.twitter.producer

import mu.KotlinLogging

fun main(args: Array<String>) {
    val logger = KotlinLogging.logger {}

    val twitterConfiguration = TwitterConfiguration(args)
    val twitterConsumer = TwitterConsumer(twitterConfiguration)
    logger.info("Starting to listen hashtags: ${twitterConfiguration.HASHTAGS}")
    KafkaSink().use { sink ->
        twitterConsumer.sequence()
            .onEach { logger.debug("Before Kafka publish") }
            .map(sink::publish)
            .forEach { logger.debug("Published a tweet to Kafka") }
    }

    logger.info("Shutting down")
}

