package com.sandjelkovic.shokker.tweet.mapper

fun mapTweetToPost(tweet: Tweet) = Post(
    externalId = tweet.id ?: "",
    text = tweet.text,
    tags = extractTags(tweet.text),
    lang = tweet.lang,
    upvotes = tweet.favoriteCount,
    source = "Twitter",
    createdAt = tweet.createdAt,
    ingestionDateTime = tweet.ingestionTime,
    repost = tweet.retweeted,
    unorganisedData = mapOf("retweetCount" to tweet.retweetCount),
    place = tweet.place?.let(::mapPlace),
    user = tweet.user?.let(::mapUser)
)

private fun mapUser(user: User): PostUser = PostUser(
    externalId = user.id ?: "",
    location = user.location,
    name = user.name,
    username = user.screenName
)

private fun mapPlace(place: Place) = PostPlace(
    name = place.name,
    countryCode = place.countryCode,
    type = place.placeType
)

fun extractTags(text: String?): List<Tag> {
    return splitIntoWords(text ?: "")
        .filter(::isHashtag)
        .map(String::trim)
        .map(::removeHashtagCharacter)
        .map { Tag(name = it, value = it) }
}

private fun removeHashtagCharacter(s: String) = s.removePrefix("#")

private fun isHashtag(it: String) = it.startsWith("#") && !it.startsWith("##")

private fun splitIntoWords(it: String) = it.split(" ", "\t", "\n")
