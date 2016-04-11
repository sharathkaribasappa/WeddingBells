package com.andriod.weddingbells.Adapters;

import java.io.File;

public class EventDetailsUserListObject {
    
    private File mImage;
    private String mName;
    private String mStatus;
    
    public EventDetailsUserListObject(File image, String name,String status) {
        mStatus = status;
        mName = name;
        mImage = image;
    }

    public CharSequence getStatus() {
        return mStatus;
    }

    public File getImage() {
        return mImage;
    }

    public String getName() {
        return mName;
    }

}
