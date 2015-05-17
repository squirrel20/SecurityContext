package me.liaosong.app.securitycontext.library;

import com.baidu.location.BDLocation;

/**
 * Created by squirrel on 2015/5/15.
 */
public class MyContextLocation extends MyContext {
    private String addr;
    private double latitude;
    private double longitude;
    private float radius;
    private String time;

    public MyContextLocation(int id, String contextName, BDLocation location) {
        super(id, contextName);
        addr = location.getAddrStr();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        radius = location.getRadius();
        time = location.getTime();
    }

    public String getValue() {
        return addr;
    }
}
