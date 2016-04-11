
package com.andriod.weddingbells.common;

import java.io.File;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.Environment;


public class CommonUtils {

    public static File getImage(int position) {

        File filePath = new File(getDirectory() + "batman.jpg");
        try {
            BitmapScaler scaler = new BitmapScaler(filePath, 100);
            Bitmap bmp = scaler.getScaled();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    private static String getDirectory() {
        String mImagesFolder = null;
        if (Environment.getExternalStorageState() == null) {
            mImagesFolder = Environment.getDataDirectory().getAbsolutePath()
                    + "/MyAppImagesFolder/";
        } else if (Environment.getExternalStorageState() != null) {
            mImagesFolder = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/MyAppImagesFolder/";
        }

        return mImagesFolder;
    }

}
