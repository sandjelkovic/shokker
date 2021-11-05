package com.sandjelkovic.shokker.post.persister

import com.google.gson.Gson
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions
import java.time.Duration
import java.util.*


class PostPersister(
        private val kafkaClientProperties: KafkaClientProperties,
        private val gson: Gson,
        private val postRepository: PostRepository,
        private val cassandraScheduler: Scheduler
) {
    private val logger = KotlinLogging.logger {}
    private val topic: String = kafkaClientProperties.topic
    private val receiverOptions: ReceiverOptions<String, String> = kafkaClientProperties.toReceiverOptions()

    fun fluxPipeline(): Flux<Post> = KafkaReceiver.create(receiverOptions()).receiveAutoAck()
        .doOnNext { logger.info { "Received batch" } }
        .doOnError { logger.error { "Error while receiving new batch: $it" } }
        .retry()
        .map(this::deserialiseFluxElements)
        .map(this::saveToCassandra)
        .flatMap { it }
        .doOnError { logger.error { "Error at the end: $it" } }

    private fun saveToCassandra(flux: Flux<Post>): Flux<Post> = flux.publishOn(cassandraScheduler)
        .map(this::enhanceWithId)
        .map(this::saveOne) // TODO check if saveAll can be used
        .retry()
        .flatMap { it }

    private fun saveOne(post: Post): Mono<Post> = postRepository.save(post)
        .doOnNext { logger.debug { "Saved $it" } }
        .doOnError { logger.error { "Error after saving: $it" } }

    private fun deserialiseFluxElements(recordsFlux: Flux<ConsumerRecord<String, String>>) =
        recordsFlux.map(this::jsonRecordToPost)

    private fun enhanceWithId(post: Post) = post.copy(id = UUID.randomUUID().toString())

    private fun jsonRecordToPost(it: ConsumerRecord<String, String>) = gson.fromJson(it.value(), Post::class.java)

    private fun receiverOptions(): ReceiverOptions<String, String> {
        return receiverOptions.subscription(listOf(topic))
            .addAssignListener { logger.debug { "onPartitionsAssigned $it" } }
            .addRevokeListener { logger.debug { "onPartitionsRevoked $it" } }
            .commitInterval(Duration.ZERO)
            .commitBatchSize(kafkaClientProperties.commitBatchSize)
    }
}
