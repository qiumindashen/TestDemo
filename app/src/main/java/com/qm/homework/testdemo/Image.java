package com.qm.homework.testdemo;

/**
 * Created by acer on 2017/5/6.
 */
public class Image {
    private int id;
    private int tag;
    public Image(int imageId) {
        this.id = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getTag(){
        return tag;
    }
    public void setTag(int tag){
        this.tag = tag;
    }
}
