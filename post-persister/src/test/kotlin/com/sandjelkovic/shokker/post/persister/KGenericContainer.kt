package com.sandjelkovic.shokker.post.persister

import org.testcontainers.containers.GenericContainer

// Testcontainers workaround for Generics magic
open class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)
