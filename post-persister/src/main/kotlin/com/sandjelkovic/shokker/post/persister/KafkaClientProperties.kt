package com.sandjelkovic.shokker.post.persister

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import reactor.kafka.receiver.ReceiverOptions


@ConfigurationProperties("persister.kafka.client")
@ConstructorBinding
data class KafkaClientProperties(
    val bootstrapServers: String,
    val topic: String,
    val clientId: String,
    val groupId: String,
    val offsetReset: OffsetReset,
    val commitBatchSize: Int
) {

    fun toReceiverOptions() : ReceiverOptions<String, String> =
        ReceiverOptions.create(
            mapOf(
                BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
                CLIENT_ID_CONFIG to clientId,
                GROUP_ID_CONFIG to groupId,
                KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                AUTO_OFFSET_RESET_CONFIG to offsetReset.toString()
            )
        )
}

enum class OffsetReset {
    //    earliest: automatically reset the offset to the earliest offset
    earliest,
    //    latest: automatically reset the offset to the latest offset
    latest,
    //    none: throw exception to the consumer if no previous offset is found for the consumer's group
    none
}
