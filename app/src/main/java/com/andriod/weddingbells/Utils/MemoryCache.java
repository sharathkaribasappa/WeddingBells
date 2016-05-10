package com.andriod.weddingbells.Utils;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by sharath on 5/10/2016.
 */
public class MemoryCache implements ImageLoader.ImageCache{
    private static final String TAG = "MemoryCache";

    // Get max available VM memory, exceeding this amount will throw an
    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
    // int in its constructor.
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    // Use 1/8th of the available memory for this memory cache.
    final int cacheSize = maxMemory / 8;
    
    protected LruCache<String, Bitmap> mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            // The cache size will be measured in kilobytes rather than
            // number of items.
            return bitmap.getByteCount() / 1024;
        }
    };

    public void putBitmap(String key, Bitmap bitmap) {
        if (getBitmap(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmap(String key) {
        return mMemoryCache.get(key);
    }
}
