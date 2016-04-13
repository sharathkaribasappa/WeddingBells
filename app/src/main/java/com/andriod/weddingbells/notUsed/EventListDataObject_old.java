
package com.andriod.weddingbells.notUsed;

import java.io.File;

public class EventListDataObject_old {
    private String mTitle;
    private String mText1;
    private String mText2;
    private File mImg;

    EventListDataObject_old(String title, String text1, String text2) {
        mTitle = title;
        mText1 = text1;
        mText2 = text2;
    }

    EventListDataObject_old(File i, String text1, String text2) {
        mText1 = text1;
        mText2 = text2;
        mImg = i;
    }

    EventListDataObject_old(File i, String title, String text1, String text2) {
        mImg = i;
        mTitle = title;
        mText1 = text1;
        mText2 = text2;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmTitle() {
        return mTitle;
    }

    public File getImage() {
        return mImg;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }
}
