package com.andriod.weddingbells.Adapters;

import java.io.File;

/**
 * Created by inksriniva on 4/4/2016.
 */
public class EventListDataObject {

    private String mTitle;
    private File mImage;

    public EventListDataObject(String title, String imagePath) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public File getImage() {
        return mImage;
    }
}
