package me.liaosong.app.securitycontext.library;

import java.io.Serializable;

/**
 * Created by squirrel on 2015-05-19.
 * …Ë÷√–≈œ¢
 */
public class SetInfo implements Serializable {
    public int statusID;
    public String name;
    public SetInfo(int statusID, String name) {
        this.statusID = statusID;
        this.name = name;
    }
}
