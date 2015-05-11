package me.liaosong.app.securitycontext.ui;

/**
 * 如何使用百度地图定位SDK
 * 第一步：在工程app/libs目录下放入BaiduLBS_Android.jar,在src/main/目录下新建jniLibs目录，放入liblocSDK5.so，注意jar和so的前3位版本号必须一致，并且保证使用一次下载的文件夹中的两个文件，不能不同功能组件的jar或so交叉使用。
 * 第二步：导入jar包。菜单栏选择File->Project Structor->Modules->Dependencies,点击+号，选择File dependency，选择jar包导入。
 * 通过以上两步操作后，您就可以正常使用百度地图SDK为您提供的全部功能了。
 */

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.MyApplication;

public class DefineContextLocationActivity extends ActionBarActivity {

    /**
     * 定位SDK的核心类
     */
    private LocationClient locationClient;
    private BDLocation location;
    private TextView locationView;
    private TextView locationStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_context_location2);

        locationView = (TextView)findViewById(R.id.location);
        locationStatus = (TextView)findViewById(R.id.get_location_status);

        locationClient = ((MyApplication)getApplication()).locationClient;
        initLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_define_context_location2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        locationClient.stop();
        super.onStop();
    }

    public void onButtonClick(View v) {
        if (locationClient == null) {
            Log.d(this.getLocalClassName(), "locationClient == null");
            return;
        }

        if (locationClient.isStarted()) {
            locationClient.stop();
            Log.d(this.getLocalClassName(), "stop");
        }
        else {
            locationClient.start();
            Log.d(this.getLocalClassName(), "start");
        }
    }

    private void initLocation() {
        // 设置定位参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);   // 高精度定位模式
        option.setOpenGps(true);    // 当用户打开gps时，设置是否打开gps，默认不打开
        option.setIsNeedAddress(true);  // 返回的定位结果包含地址信息
        option.setScanSpan(1000);   // 请求间隔时间1000ms
        option.setCoorType("bd0911");   // 返回的定位结果是百度经纬度，默认值gcj02
        option.setProdName(this.getPackageName());  // 设置产品线名称

        locationClient.setLocOption(option);
    }
}
