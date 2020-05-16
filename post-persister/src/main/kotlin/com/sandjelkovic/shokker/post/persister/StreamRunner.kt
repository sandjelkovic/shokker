package com.sandjelkovic.shokker.post.persister

import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import reactor.core.Disposable
import reactor.core.publisher.Flux


class StreamRunner<T : Any?>(private val streamSupplier: () -> Flux<T>) : AutoCloseable, CommandLineRunner {
    private val logger = KotlinLogging.logger {}
    private var disposable: Disposable? = null

    override fun close() {
        disposable?.dispose()
    }

    private fun runStream() {
        check(disposable == null) { "Stream is already running"}
        disposable = streamSupplier().subscribe { logger.info { "Finished processing $it" } }
    }

    override fun run(vararg args: String?) {
        runStream()
    }
}
