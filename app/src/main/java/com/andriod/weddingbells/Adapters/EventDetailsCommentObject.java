
package com.andriod.weddingbells.Adapters;

import java.io.File;

public class EventDetailsCommentObject {
    private File mImage;
    private String mComment;

    public EventDetailsCommentObject(String comment, File image) {
        mComment = comment;
    }

    public String getComment() {
        return mComment;
    }

    public Object getImage() {
        return null;
    }

}
