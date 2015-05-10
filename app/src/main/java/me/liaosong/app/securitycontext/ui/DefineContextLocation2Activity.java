package me.liaosong.app.securitycontext.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import me.liaosong.app.securitycontext.R;

public class DefineContextLocation2Activity extends ActionBarActivity {

    /**
     * ��λSDK�ĺ�����
     */
    private LocationClient locationClient = null;
    private BDLocationListener listener = null;
    private TextView locationView = null;
    private TextView locationStatus = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_context_location2);

        locationView = (TextView)findViewById(R.id.location);
        locationStatus = (TextView)findViewById(R.id.get_location_status);

        locationClient = new LocationClient(getApplicationContext());
        listener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.d(DefineContextActivity.class.getName(), "receive location");
                locationStatus.setText("receive location data");
                locationView.setText(bdLocation.getAddrStr());
            }
        };
        locationClient.registerLocationListener(listener);

        // ���ö�λ����
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);   // �߾��ȶ�λģʽ
        option.setOpenGps(true);    // ���û���gpsʱ�������Ƿ��gps��Ĭ�ϲ���
        option.setIsNeedAddress(true);  // ���صĶ�λ���������ַ��Ϣ
        option.setScanSpan(2000);   // ������ʱ��1000ms
        option.setCoorType("bd0911");   // ���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
        option.setProdName(this.getPackageName());  // ���ò�Ʒ������
        locationClient.setLocOption(option);

        locationClient.start();
        locationClient.requestLocation();
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

    public void onButtonClick(View v) {
        if (locationClient != null && locationClient.isStarted()) {
            locationClient.requestLocation();
            locationStatus.setText("getting location address");
            Log.d(this.getLocalClassName(), "getting location address");
        }
        else
            Log.d(this.getLocalClassName(), "locClient is null or not started");
    }
}
