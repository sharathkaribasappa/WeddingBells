
package com.andriod.weddingbells.Adapters;

import java.io.File;

public class EventDetailsUpdatesObject {

    private String mUserId;
    private String[] mComments;
    private File mImage;
    private int mLikesCount;
    private int mCommentsCount;
    private File mThumbNail;

    public EventDetailsUpdatesObject(String UserID, File image, int likesCount, int commentsCount,
            String[] comments) {
        mUserId = UserID;
        mImage = image;
        mLikesCount = likesCount;
        mComments = comments;
        mCommentsCount = commentsCount;
    }

    public int getmCommentsCount() {
        return mCommentsCount;
    }

    public String getmUserId() {
        return mUserId;
    }

    public String[] getmComments() {
        return mComments;
    }

    public File getmImage() {
        return mImage;
    }

    public int getmLikesCount() {
        return mLikesCount;
    }

}
