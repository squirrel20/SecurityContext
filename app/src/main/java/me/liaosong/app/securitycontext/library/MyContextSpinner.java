package me.liaosong.app.securitycontext.library;

import android.content.Context;

/**
 * Created by squirrel on 2015/5/16.
 */
public class MyContextSpinner extends MyContext {
    private int arrayId;
    private int selectedItemId;
    public MyContextSpinner(int id, String contextName, int arrayId, long selectedItemId) {
        super(id, contextName);
        this.arrayId = arrayId;
        this.selectedItemId = (int)selectedItemId;
    }

    public String getValue() {
        return  MyApplication.application.getResources().getStringArray(arrayId)[selectedItemId];
    }
}
