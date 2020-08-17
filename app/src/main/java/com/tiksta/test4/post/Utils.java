package com.tiksta.test4.post;

public class Utils {
    private static int tabPosition = 0;
    private static boolean tiktokMaxLengthSet = false;
    private static String tag = null;
    private static String post = null;
    private static boolean chipClosed = false;
    

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
}
