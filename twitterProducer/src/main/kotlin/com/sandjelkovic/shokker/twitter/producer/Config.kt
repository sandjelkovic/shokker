package com.sandjelkovic.shokker.twitter.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.LongSerializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

fun extractConfig(args: Array<String>, key: String): String = args.first { it.startsWith(key) }.substringAfter("=")
fun extractConfigs(args: Array<String>, key: String): List<String> =
    args.first { it.startsWith(key) }.substringAfter("=").split(delimiters = *arrayOf(","))

object KafkaConfiguration {
    const val SERVERS = "localhost:9092" // TODO extract to configuration
    const val TOPIC = "rawTweets" // TODO extract to configuration

    fun createKafkaProducer(): KafkaProducer<Long, String> = KafkaProducer(producerProperties())

    private fun producerProperties(): Properties {
        val properties = Properties()
        properties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = SERVERS
        properties[ProducerConfig.ACKS_CONFIG] = "1"
        properties[ProducerConfig.LINGER_MS_CONFIG] = 500
        properties[ProducerConfig.RETRIES_CONFIG] = 0
        properties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = LongSerializer::class.java.name
        properties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name
        return properties
    }
}

class TwitterConfiguration(args: Array<String>) {
    var CONSUMER_KEY = ""
    var CONSUMER_SECRET = ""
    var ACCESS_TOKEN = ""
    var TOKEN_SECRET = ""
    var HASHTAGS = listOf<String>()

    init {
        CONSUMER_KEY = extractConfig(args, "CONSUMER_KEY")
        CONSUMER_SECRET = extractConfig(args, "CONSUMER_SECRET")
        ACCESS_TOKEN = extractConfig(args, "ACCESS_TOKEN")
        TOKEN_SECRET = extractConfig(args, "TOKEN_SECRET")
        HASHTAGS = extractConfigs(args, "HASHTAGS")
    }
}
