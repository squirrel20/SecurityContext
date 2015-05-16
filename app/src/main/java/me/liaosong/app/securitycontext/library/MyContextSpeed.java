package me.liaosong.app.securitycontext.library;

/**
 * Created by squirrel on 2015/5/15.
 */
public class MyContextSpeed extends MyContext {
    private long seletedItemId;
    public MyContextSpeed(int id, String name, long selectedItemId) {
        super(id, name);
        this.seletedItemId = selectedItemId;
    }
}
