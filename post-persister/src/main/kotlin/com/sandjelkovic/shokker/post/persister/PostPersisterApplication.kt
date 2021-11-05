package com.sandjelkovic.shokker.post.persister

import com.google.gson.Gson
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories
import reactor.core.scheduler.Schedulers


@SpringBootApplication
@EnableConfigurationProperties(KafkaClientProperties::class)
@EnableReactiveCassandraRepositories(basePackageClasses = [Post::class])

class PostPersisterApplication {
    @Bean
    fun postPersister(kafkaClientProperties: KafkaClientProperties, postRepository: PostRepository, reactiveCassandraTemplate: ReactiveCassandraTemplate) =
        PostPersister(kafkaClientProperties, Gson(), postRepository, Schedulers.newBoundedElastic(50, Schedulers.DEFAULT_BOUNDED_ELASTIC_QUEUESIZE, "CassandraScheduler"))

    @Bean
    fun runner(postPersister: PostPersister) = StreamRunner {
        postPersister.fluxPipeline()
    }
}

fun main(args: Array<String>) {
    runApplication<PostPersisterApplication>(*args)
}
