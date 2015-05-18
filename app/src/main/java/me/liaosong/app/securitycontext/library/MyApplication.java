package me.liaosong.app.securitycontext.library;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by squirrel on 2015/5/11.
 * 主Application
 */
public class MyApplication extends Application {
	public TextView locationResult;
	public LocationClient locationClient;
	public BDLocation currentLocation;

	public static Application application;

	public ArrayList<AppInfo> appInfos = null;

    @Override
    public void onCreate() {
        super.onCreate();

        locationClient = new LocationClient(this.getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener());

		application = this;
    }

    public void getAllApps() {
		if (appInfos != null) return;
		appInfos = new ArrayList<>();

        PackageManager packageManager = this.getPackageManager();
        List<PackageInfo> packageInfos =packageManager.getInstalledPackages(0);

        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo packageInfo = packageInfos.get(i);
            AppInfo appInfo = new AppInfo();
            appInfo.appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            appInfo.appIcon = packageInfo.applicationInfo.loadIcon(packageManager);
            appInfo.packageName = packageInfo.packageName;
            appInfos.add(appInfo);

            Log.d(this.getPackageName(), appInfo.appName);
        }
    }

    /**
     * 位置回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
			Log.d("location", "receive location data");
            //Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				//运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			Log.d("location", sb.toString());

			if (locationResult != null)
				locationResult.setText(sb);

			currentLocation = location;
        }
    }
}
