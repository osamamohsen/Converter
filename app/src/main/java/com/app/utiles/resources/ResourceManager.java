package com.app.utiles.resources;

import android.graphics.drawable.Drawable;

import com.app.app.BaseApplication;

import androidx.core.content.ContextCompat;


public class ResourceManager {

    public static Drawable getDrawable(int id){
        return ContextCompat.getDrawable(BaseApplication.getInstance(),id);
    }

}
