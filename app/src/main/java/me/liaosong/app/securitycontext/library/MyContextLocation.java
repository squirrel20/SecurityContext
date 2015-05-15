package me.liaosong.app.securitycontext.library;

import com.baidu.location.BDLocation;

/**
 * Created by squirrel on 2015/5/15.
 */
public class MyContextLocation extends MyContext {
    BDLocation location;
    public MyContextLocation(int id, String name, BDLocation location) {
        super(id, name);
        this.location = location;
    }
}
