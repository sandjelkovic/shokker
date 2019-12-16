package com.sandjelkovic.shokker.tweet.mapper

import com.google.gson.Gson
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import reactor.core.publisher.Flux
import java.util.function.Function

@SpringBootApplication
class TweetMapperApplication {
    private val gson = Gson()
    private val logger = KotlinLogging.logger {}

    @Bean
    fun tweetToPost() = Function<Flux<String>, Flux<Message<String>>> { inboundFlux ->
        inboundFlux
            .map(this::deserializeTweet)
            .doOnNext(::preMapLogger)
            .map(::mapTweetToPost)
            .doOnNext(::postMapLogger)
            .map(this::convertToMessage)
    }

    private fun convertToMessage(post: Post): Message<String> {
        return MessageBuilder
            .withPayload(serializePost(post))
            .setHeader("partitionKey", post.externalId)
            .build()
    }

    private fun postMapLogger(post: Post) = logger.info { "Sending post: ${post.externalId} : $post" }

    private fun preMapLogger(tweet: Tweet) = logger.info { "Received tweet ${tweet.id} : $tweet" }

    private fun serializePost(post: Post): String = gson.toJson(post)

    private fun deserializeTweet(input: String): Tweet = gson.fromJson(input, Tweet::class.java)
}

fun main(args: Array<String>) {
    runApplication<TweetMapperApplication>(*args)
}
