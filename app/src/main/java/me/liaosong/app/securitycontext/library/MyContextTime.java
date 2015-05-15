package me.liaosong.app.securitycontext.library;

/**
 * Created by squirrel on 2015/5/13.
 */
public class MyContextTime extends MyContext {
    private String startTime;
    private String endTime;
    private boolean isEveryDay;


    /**
     * 默认为一次性事件，即指定了开始的具体时间，结束的具体时间。
     * 如果是每天发生的，则没有指定日期，只指定了小时、分钟等。
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param isEveryDay 是否每天重复
     */
    public MyContextTime(int id, String contextName, String startTime, String endTime, boolean isEveryDay) {
        super(id, contextName);
        this.startTime = startTime;
        this.endTime = endTime;
        this.isEveryDay = isEveryDay;
    }
}
