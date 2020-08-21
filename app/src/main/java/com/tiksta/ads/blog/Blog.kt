package com.tiksta.ads.blog

data class Blog(
    var name: String,
    var listOfTagsTikTok: Array<String>,
    var listOfTagsInstagram: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Blog

        if (name != other.name) return false
        if (!listOfTagsTikTok.contentEquals(other.listOfTagsTikTok)) return false
        if (!listOfTagsInstagram.contentEquals(other.listOfTagsInstagram)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + listOfTagsTikTok.contentHashCode()
        result = 31 * result + listOfTagsInstagram.contentHashCode()
        return result
    }
}
