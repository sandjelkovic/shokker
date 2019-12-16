package com.sandjelkovic.shokker.tweet.mapper

import org.junit.jupiter.api.Test

internal class MapperKtTest {

    @Test
    fun noTags() {
        assert(extractTags("Text without tags").isEmpty())
    }

    @Test
    fun singleTag() {
        assert(extractTags("Text with a #single tag")[0].value == "single")
    }

    @Test
    fun twoTags() {
        val twoTags = extractTags("Text with a #two #awesome tags")
        assert(twoTags[0].value == "two")
        assert(twoTags[1].value == "awesome")
    }

    @Test
    fun twoTagsAtStartAndEnd() {
        val twoTagsAtStartAndEnd = extractTags("#Text with a   two  tags #awesome")
        assert(twoTagsAtStartAndEnd[0].value == "Text")
        assert(twoTagsAtStartAndEnd[1].value == "awesome")
    }

    @Test
    fun falseTags() {
        val falseTags = extractTags("Text with a false#tags which aren't tags")
        assert(falseTags.isEmpty())
    }
//    Wed Oct 10 20:19:24 +0000 2018
}
