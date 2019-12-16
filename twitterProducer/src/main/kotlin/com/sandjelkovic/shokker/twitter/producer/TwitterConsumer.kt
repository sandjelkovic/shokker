package com.sandjelkovic.shokker.twitter.producer

import com.google.gson.Gson
import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.Client
import com.twitter.hbc.core.Constants
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint
import com.twitter.hbc.core.processor.StringDelimitedProcessor
import com.twitter.hbc.httpclient.BasicClient
import com.twitter.hbc.httpclient.auth.OAuth1
import mu.KotlinLogging
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class TwitterConsumer(private val configuration: TwitterConfiguration) {
    private val logger = KotlinLogging.logger {}
    private val objectMapper = Gson()
    fun sequence(): Sequence<Tweet> =
        generateNewTweetSequence()
            .map { objectMapper.fromJson(it, Tweet::class.java) }
            .onEach { logger.info("Fetched tweet with id: ${it.id}") }

    private fun generateNewTweetSequence(): Sequence<String> {
        val queue: BlockingQueue<String> = LinkedBlockingQueue(10000)
        val twitterClient: Client = createTwitterClient(queue)
        twitterClient.connect()
        return generateSequence {
            try {
                logger.debug("Taking an item from Twitter queue")
                queue.take()
            } catch (e: InterruptedException) {
                e.printStackTrace()
                twitterClient.stop()
                null
            } catch (e : Exception) {
                e.printStackTrace()
                twitterClient.stop()
                null
            }
        }
    }

    private fun createTwitterClient(queue: BlockingQueue<String>): BasicClient = ClientBuilder()
        .hosts(Constants.STREAM_HOST)
        .authentication(authentication())
        .endpoint(endpoint())
        .processor(StringDelimitedProcessor(queue))
        .build()!!

    private fun endpoint(): StatusesFilterEndpoint = StatusesFilterEndpoint().apply {
        trackTerms(configuration.HASHTAGS)
    }

    private fun authentication(): OAuth1 = OAuth1(
        configuration.CONSUMER_KEY,
        configuration.CONSUMER_SECRET,
        configuration.ACCESS_TOKEN,
        configuration.TOKEN_SECRET
    )

}
