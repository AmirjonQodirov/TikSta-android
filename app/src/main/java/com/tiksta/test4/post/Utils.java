package com.tiksta.test4.post;

import android.view.View;

public class Utils {
    private static int tabPosition = 0;
    private static boolean tiktokMaxLengthSet = false;
    private static View postActivityView;

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

    public static View getPostActivityView() {
        return postActivityView;
    }

    public static void setPostActivityView(View postActivityView) {
        Utils.postActivityView = postActivityView;
    }
}
