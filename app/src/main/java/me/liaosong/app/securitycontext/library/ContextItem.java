package me.liaosong.app.securitycontext.library;

/**
 * 情景元素信息
 * Created by squirrel on 2015/2/10.
 */
public class ContextItem {
    private String name;
    private int id;

    public ContextItem() {

    }

    public ContextItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
