package com.sandjelkovic.shokker.twitter.producer

import com.google.gson.Gson
import mu.KotlinLogging
import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import java.io.Closeable

class KafkaSink : Closeable {
    private val logger = KotlinLogging.logger {}
    private val objectMapper = Gson()
    private val producer: KafkaProducer<Long, String> = KafkaConfiguration.createKafkaProducer()

    fun publish(tweet: Tweet) {
        val key = tweet.id
        val msg = objectMapper.toJson(tweet)
        logger.debug("Publishing to Kafka")
        val producerRecord = ProducerRecord(KafkaConfiguration.TOPIC, key?.toLong(), msg)
        producer.send(producerRecord, loggingKafkaCallback)
    }

    override fun close() {
        producer.close()
        logger.info("Kafka publisher closed")
    }

    private val loggingKafkaCallback = Callback { metadata, exception ->
        if (exception == null) {
            logger.info("Message with offset ${metadata?.offset()} acknowledged by partition ${metadata?.partition()}")
        } else {
            logger.error(exception.message)
        }
    }

}
