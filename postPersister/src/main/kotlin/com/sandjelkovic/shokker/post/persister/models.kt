package com.sandjelkovic.shokker.post.persister

import com.datastax.driver.core.DataType
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.CassandraType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import org.springframework.data.cassandra.core.mapping.UserDefinedType


@Table
data class Post(
    // TODO Revisit partition/cluster keys
    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    val id: String = "",
    @PrimaryKeyColumn(name = "externalId", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    val externalId: String = "",
    val source: String = "",
    val user: PostUser? = null,
    val text: String? = null,
    val tags: List<Tag> = listOf(),
    val lang: String? = null,
    val upvotes: Long? = null,
    val downvotes: Long? = null,
    val repost: Boolean = false,
    val ingestionDateTime: String? = null,
    val createdAt: String? = null,
    val place: PostPlace? = null,
    @CassandraType(type = DataType.Name.TEXT, typeArguments = [DataType.Name.TEXT, DataType.Name.TEXT])
    val unorganisedData: Map<String, String> = mapOf()
)

@UserDefinedType
data class Tag(
    val name: String,
    val value: String
)

@UserDefinedType
data class PostUser(
    val externalId: String,
    val username: String? = null,
    val name: String? = null,
    val location: String? = null,
    val followersCount: Long = 0L
)

@UserDefinedType
data class PostPlace(
    val name: String? = null,
    val type: String? = null,
    val countryCode: String? = null
)
