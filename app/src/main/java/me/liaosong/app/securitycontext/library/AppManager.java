package me.liaosong.app.securitycontext.library;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by squirrel20 on 2015/4/6.
 */
public class AppManager {
    private Context context = null;
    public ArrayList<AppInfo> appInfos = null;

    public AppManager(Context context) {
        this.context = context;
        appInfos = new ArrayList<AppInfo>();
        getAllApps();
    }

    private void getAllApps() {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos =packageManager.getInstalledPackages(0);

        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo packageInfo = packageInfos.get(i);
            AppInfo appInfo = new AppInfo();
            appInfo.appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            appInfo.appIcon = packageInfo.applicationInfo.loadIcon(packageManager);
            appInfo.packageName = packageInfo.packageName;
            appInfos.add(appInfo);

            Log.v(AppManager.class.getName(), appInfo.appName);
        }
    }
}
