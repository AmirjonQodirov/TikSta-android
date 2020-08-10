package com.tiksta.test4.post;

public class Utils {
    private static int tabPosition;

    public static int getTabPosition() {
        return tabPosition;
    }

    public static void setTabPosition(int tabPosition) {
        Utils.tabPosition = tabPosition;
        System.out.println("Utils: " + tabPosition);
    }
}
