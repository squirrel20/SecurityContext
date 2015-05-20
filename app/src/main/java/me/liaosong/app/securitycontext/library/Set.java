package me.liaosong.app.securitycontext.library;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by squirrel on 2015-05-20.
 */
public class Set {
    /**
     * WIFI���翪��
     * @param context
     * @param enabled
     */
    public static void toggleWiFi(Context context, boolean enabled) {
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(enabled);
    }

    /**
     * �ƶ����翪��
     */
    public static void toggleMobileData(Context context, boolean enabled) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> conMgrClass = null; // ConnectivityManager��
        Field iConMgrField = null; // ConnectivityManager���е��ֶ�
        Object iConMgr = null; // IConnectivityManager�������
        Class<?> iConMgrClass = null; // IConnectivityManager��
        Method setMobileDataEnabledMethod = null; // setMobileDataEnabled����
        try {
            // ȡ��ConnectivityManager��
            conMgrClass = Class.forName(conMgr.getClass().getName());
            // ȡ��ConnectivityManager���еĶ���mService
            iConMgrField = conMgrClass.getDeclaredField("mService");
            // ����mService�ɷ���
            iConMgrField.setAccessible(true);
            // ȡ��mService��ʵ������IConnectivityManager
            iConMgr = iConMgrField.get(conMgr);
            // ȡ��IConnectivityManager��
            iConMgrClass = Class.forName(iConMgr.getClass().getName());
            // ȡ��IConnectivityManager���е�setMobileDataEnabled(boolean)����
            setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            // ����setMobileDataEnabled�����ɷ���
            setMobileDataEnabledMethod.setAccessible(true);
            // ����setMobileDataEnabled����
            setMobileDataEnabledMethod.invoke(iConMgr, enabled);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>GPS����
     * <p>��ǰ�������
     * <p>��ǰ������ر�
     */
    public static void toggleGPS(Context context) {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");  gpsIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, gpsIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

}
