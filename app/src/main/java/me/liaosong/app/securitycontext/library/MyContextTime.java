package me.liaosong.app.securitycontext.library;

/**
 * Created by squirrel on 2015/5/13.
 */
public class MyContextTime extends MyContext {
    private String startTime;
    private String endTime;
    private boolean isEveryDay;


    /**
     * Ĭ��Ϊһ�����¼�����ָ���˿�ʼ�ľ���ʱ�䣬�����ľ���ʱ�䡣
     * �����ÿ�췢���ģ���û��ָ�����ڣ�ָֻ����Сʱ�����ӵȡ�
     * @param startTime ��ʼʱ��
     * @param endTime ����ʱ��
     * @param isEveryDay �Ƿ�ÿ���ظ�
     */
    public MyContextTime(int id, String contextName, String startTime, String endTime, boolean isEveryDay) {
        super(id, contextName);
        this.startTime = startTime;
        this.endTime = endTime;
        this.isEveryDay = isEveryDay;
    }
}
