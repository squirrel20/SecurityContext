package me.liaosong.app.securitycontext.library;

/**
 * Created by squirrel on 2015-05-19.
 * 安全情景信息
 */
public class SCInfo {
    public int statusID;
    public String SCName;
    public SCInfo(int statusID, String SCName) {
        this.statusID = statusID;
        this.SCName = SCName;
    }
}
