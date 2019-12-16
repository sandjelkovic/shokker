package com.sandjelkovic.shokker.post.persister

import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository

/**
 * Use [ReactiveCassandraRepository] instead of [ReactiveCrudRepository] to get Cassandra specific utilities
  */
interface PostRepository : ReactiveCassandraRepository<Post, String>
