package me.liaosong.app.securitycontext.library;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by squirrel20 on 2015/4/6.
 * 应用信息
 */
public class AppInfo{
    public boolean isChecked = false;
    public String appName = "";
    public String packageName = "";
    public Drawable appIcon = null; // 有这个没法集成 Serializabel
}
