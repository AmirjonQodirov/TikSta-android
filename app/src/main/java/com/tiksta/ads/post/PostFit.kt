package com.tiksta.ads.post

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*
import java.util.stream.Collectors

@RequiresApi(api = Build.VERSION_CODES.N)
class PostFit(private var len: Int, private var tags: ArrayList<String>) {
    private fun calculatePrefixSums(
        tags: ArrayList<String>,
        prefixSums: ArrayList<Int>,
        len: Int
    ): Int {
        if (tags[0].length + 2 > len) {
            return 0
        }

        prefixSums.add(tags[0].length + 2)
        var index = tags.size

        for (i in 1 until tags.size) {
            prefixSums.add(prefixSums[i - 1] + tags[i].length + 2)

            if (prefixSums[i] > len) {
                index = i
                break
            }
        }

        return index
    }

    fun instagramFit(alreadyHave: Int): ArrayList<String> {
        // Constants from Instagram official website
        val maxLen = 2200
        val maxTags = 30 - alreadyHave
        len = maxLen - len

        if (tags.size > maxTags) {
            tags = tags.stream().limit(maxTags.toLong())
                .collect(Collectors.toList()) as ArrayList<String>
        }

        val prefixSums = ArrayList<Int>()
        val index = calculatePrefixSums(tags, prefixSums, len)
        tags = tags.stream().limit(index.toLong())
            .collect(Collectors.toList()) as ArrayList<String>

        return tags
    }

    fun tikTokFit(): ArrayList<String> {
        // Constants from TikTok official website
        val maxLen = 100
        len = maxLen - len
        val prefixSums = ArrayList<Int>()
        val index = calculatePrefixSums(tags, prefixSums, len)

        tags = tags.stream().limit(index.toLong())
            .collect(Collectors.toList()) as ArrayList<String>

        return tags
    }
}
