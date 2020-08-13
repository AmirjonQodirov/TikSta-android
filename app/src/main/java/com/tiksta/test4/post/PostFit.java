package com.tiksta.test4.post;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class PostFit {
    private Integer len;
    private ArrayList<String> tags;

    public PostFit(Integer len, ArrayList<String> tags) {
        this.len = len;
        this.tags = tags;
    }


    private int calculatePrefixSums(ArrayList<String> tags, ArrayList<Integer> prefixSums, int len) {
//        tags.sort(Comparator.comparing(String::length));
        if (tags.get(0).length() + 2 > len) {
            return 0;
        }

        prefixSums.add(tags.get(0).length() + 2);

        int index = tags.size();
        for (int i = 1; i < tags.size(); ++i) {
            prefixSums.add(prefixSums.get(i - 1) + tags.get(i).length() + 2);

            if (prefixSums.get(i) > len) {
                index = i;
                break;
            }
        }

        return index;
    }

    public ArrayList<String> instagramFit(int alreadyHave) {
        // Constants from Instagram official website
        int MAX_LEN = 2200;
        int MAX_TAGS = 30 - alreadyHave;

        len = MAX_LEN - len;
        if (tags.size() > MAX_TAGS) {
            tags = (ArrayList<String>) tags.stream().limit(MAX_TAGS).collect(Collectors.toList());
        }

        ArrayList<Integer> prefixSums = new ArrayList<>();
        int index = calculatePrefixSums(tags, prefixSums, len);

        tags = (ArrayList<String>) tags.stream().limit(index).collect(Collectors.toList());
        return tags;
    }

    public ArrayList<String> tikTokFit() {
        // Constants from TikTok official website
        int MAX_LEN = 100;

        len = MAX_LEN - len;

        ArrayList<Integer> prefixSums = new ArrayList<>();
        int index = calculatePrefixSums(tags, prefixSums, len);

        tags = (ArrayList<String>) tags.stream().limit(index).collect(Collectors.toList());
        return tags;
    }
}
