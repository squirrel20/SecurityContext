package me.liaosong.app.securitycontext.library;

import java.io.Serializable;

import me.liaosong.app.securitycontext.R;

/**
 * 情景元素信息
 * 目前已有的情景元素为：
 * 时间
 * 位置
 * Created by squirrel on 2015/2/10.
 */
public abstract class MyContext implements Serializable {
    public final static String key = "key";

    private String contextName;
    private int id;

    public abstract String getValue();

    public MyContext(int id, String contextName) {
        this.id = id;
        this.contextName = contextName;
    }

    /**
     * @return 情景名
     */
    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    /**
     * @return 情景唯一标识符，默认使用的是R.string....
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
