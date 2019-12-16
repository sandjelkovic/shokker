package com.sandjelkovic.shokker.tweet.mapper

data class Post(
    val externalId: String,
    val user: PostUser? = null,
    val text: String? = null,
    val tags: List<Tag> = listOf(),
    val lang: String? = null,
    val source: String = "",
    val upvotes: Long? = null,
    val downvotes: Long? = null,
    val repost: Boolean = false,
    val ingestionDateTime: String? = null,
    val createdAt: String? = null,
    val place: PostPlace? = null,
    val unorganisedData: Map<String, Any> = mapOf()
)

data class PostPlace(
    val name: String? = null,
    val type: String? = null,
    val countryCode: String? = null
)

data class Tag(
    val name: String,
    val value: String
)

data class PostUser(
    val externalId: String,
    val username: String? = null,
    val name: String? = null,
    val location: String? = null,
    val followersCount: Long = 0L
)
