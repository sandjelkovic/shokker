package com.sandjelkovic.shokker.tweet.mapper

import com.google.gson.annotations.SerializedName

data class Tweet(
    @SerializedName("id_str")
    val id: String? = null,
    val text: String? = null,
    val lang: String? = null,
    val user: User? = null,
    @SerializedName("retweet_count")
    val retweetCount: Long = 0,
    @SerializedName("favorite_count")
    val favoriteCount: Long = 0,
    val retweeted: Boolean = false,
    val ingestionTime: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    val place: Place? = null
)

data class User(
    @SerializedName("id_str")
    val id: String? = null,
    val name: String? = null,
    @SerializedName("screen_name")
    val screenName: String? = null,
    val location: String? = null,
    @SerializedName("followers_count")
    val followersCount: Int = 0
)

data class Place(
    val name: String? = null,
    @SerializedName("place_type")
    val placeType: String? = null,
    @SerializedName("country_code")
    val countryCode: String? = null
)
