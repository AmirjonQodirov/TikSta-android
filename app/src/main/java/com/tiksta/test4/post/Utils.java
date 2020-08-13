package com.tiksta.test4.post;

public class Utils {
    private static int tabPosition = 0;
    private static boolean tiktokMaxLengthSet = false;

    public static int getTabPosition() {
        return tabPosition;
    }

    public static void setTabPosition(int tabPosition) {
        Utils.tabPosition = tabPosition;
    }

    public static boolean isTiktokMaxLengthSet() {
        return tiktokMaxLengthSet;
    }

    public static void setTiktokMaxLengthSet(boolean tiktokMaxLengthSet) {
        Utils.tiktokMaxLengthSet = tiktokMaxLengthSet;
    }
}
