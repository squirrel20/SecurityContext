package me.liaosong.app.securitycontext.ui;

/**
 * ���ʹ�ðٶȵ�ͼ��λSDK
 * ��һ�����ڹ���app/libsĿ¼�·���BaiduLBS_Android.jar,��src/main/Ŀ¼���½�jniLibsĿ¼������liblocSDK5.so��ע��jar��so��ǰ3λ�汾�ű���һ�£����ұ�֤ʹ��һ�����ص��ļ����е������ļ������ܲ�ͬ���������jar��so����ʹ�á�
 * �ڶ���������jar�����˵���ѡ��File->Project Structor->Modules->Dependencies,���+�ţ�ѡ��File dependency��ѡ��jar�����롣
 * ͨ�������������������Ϳ�������ʹ�ðٶȵ�ͼSDKΪ���ṩ��ȫ�������ˡ�
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
     * ��λSDK�ĺ�����
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
        // ���ö�λ����
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);   // �߾��ȶ�λģʽ
        option.setOpenGps(true);    // ���û���gpsʱ�������Ƿ��gps��Ĭ�ϲ���
        option.setIsNeedAddress(true);  // ���صĶ�λ���������ַ��Ϣ
        option.setScanSpan(1000);   // ������ʱ��1000ms
        option.setCoorType("bd0911");   // ���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
        option.setProdName(this.getPackageName());  // ���ò�Ʒ������

        locationClient.setLocOption(option);
    }
}
