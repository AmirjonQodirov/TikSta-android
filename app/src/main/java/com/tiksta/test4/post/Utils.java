package com.tiksta.test4.post;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class Utils {
    private static int tabPosition = 0;
    private static boolean tiktokMaxLengthSet = false;
    private static String tag = null;
    private static String post = null;
    private static boolean chipClosed = false;
    private static ArrayList<Chip> chipsFromGroup = new ArrayList<>();
    private static boolean copyToClipboardVisible = false;
    private static boolean resultWithHashtagsVisible = false;
    private static boolean div2Visible = false;
    private static boolean chipGroupVisible = false;
    private static String resultWithHashtagsText = "";
    private static int hashTagsFound = 0;
    private static String errorMessage = null;
    private static boolean considerPostLength = false;

    public static int getTabPosition() {
        return tabPosition;
    }

    public static void setTabPosition(int tabPosition) {
        tag = null;
        post = null;
        chipClosed = false;
        Utils.tabPosition = tabPosition;
    }

    public static boolean isTiktokMaxLengthSet() {
        return tiktokMaxLengthSet;
    }

    public static void setTiktokMaxLengthSet(boolean tiktokMaxLengthSet) {
        Utils.tiktokMaxLengthSet = tiktokMaxLengthSet;
    }

    public static String getTag() {
        return tag;
    }

    public static void setTag(String tag) {
        Utils.tag = tag;
    }

    public static String getPost() {
        return post;
    }

    public static void setPost(String post) {
        Utils.post = post;
    }

    public static boolean isChipClosed() {
        return chipClosed;
    }

    public static void setChipClosed(boolean chipClosed) {
        Utils.chipClosed = chipClosed;
    }

    public static ArrayList<Chip> getChipsFromGroup() {
        return chipsFromGroup;
    }

    public static void setChipsFromGroup(ArrayList<Chip> chipsFromGroup) {
        Utils.chipsFromGroup = chipsFromGroup;
    }

    public static void addChip(Chip chip) {
        chipsFromGroup.add(chip);
    }

    public static boolean isCopyToClipboardVisible() {
        return copyToClipboardVisible;
    }

    public static void setCopyToClipboardVisible(boolean copyToClipboardVisible) {
        Utils.copyToClipboardVisible = copyToClipboardVisible;
    }

    public static boolean isResultWithHashtagsVisible() {
        return resultWithHashtagsVisible;
    }

    public static void setResultWithHashtagsVisible(boolean resultWithHashtagsVisible) {
        Utils.resultWithHashtagsVisible = resultWithHashtagsVisible;
    }

    public static boolean isDiv2Visible() {
        return div2Visible;
    }

    public static void setDiv2Visible(boolean div2Visible) {
        Utils.div2Visible = div2Visible;
    }

    public static boolean isChipGroupVisible() {
        return chipGroupVisible;
    }

    public static void setChipGroupVisible(boolean chipGroupVisible) {
        Utils.chipGroupVisible = chipGroupVisible;
    }

    public static String getResultWithHashtagsText() {
        return resultWithHashtagsText;
    }

    public static void setResultWithHashtagsText(String resultWithHashtagsText) {
        Utils.resultWithHashtagsText = resultWithHashtagsText;
    }

    public static int getHashTagsFound() {
        return hashTagsFound;
    }

    public static void setHashTagsFound(int hashTagsFound) {
        Utils.hashTagsFound = hashTagsFound;
    }

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static void setErrorMessage(String errorMessage) {
        Utils.errorMessage = errorMessage;
    }

    public static boolean isConsiderPostLength() {
        return considerPostLength;
    }

    public static void setConsiderPostLength(boolean considerPostLength) {
        Utils.considerPostLength = considerPostLength;
    }
}
