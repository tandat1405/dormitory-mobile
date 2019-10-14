package com.datnt.dormitorymanagement.InAppModel;

import android.content.res.Resources;

public class Home {
    private int id, imgRes;
    private String name;

    public Home(int id, int imgRes, String name) {
        this.id = id;
        this.imgRes = imgRes;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
