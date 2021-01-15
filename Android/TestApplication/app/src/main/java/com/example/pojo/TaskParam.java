package com.example.pojo;

import android.content.res.AssetManager;


//加载图片相关参数实体类
/**
 * Created by Spring on 2015/11/4.
 *
 */
public class TaskParam {
    private String filename;
    private AssetManager assetManager;
    private int ItemWidth;

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setItemWidth(int itemWidth) {
        ItemWidth = itemWidth;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public String getFilename() {
        return filename;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public int getItemWidth() {
        return ItemWidth;
    }
}