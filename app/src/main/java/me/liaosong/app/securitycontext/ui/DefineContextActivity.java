package me.liaosong.app.securitycontext.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.MyContext;

public class DefineContextActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_context);

        ListView listView = (ListView) this.findViewById(R.id.define_context);
        View header = this.getLayoutInflater().inflate(R.layout.list_header, null);
        View footer = this.getLayoutInflater().inflate(R.layout.list_footer, null);

        ((EditText) header.findViewById(R.id.header_edit_text))
                .setHint(R.string.input_context_name);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });


        listView.addHeaderView(header);
        listView.addFooterView(footer);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView.setAdapter(arrayAdapter);

        this.registerForContextMenu(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_define_context, menu);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle(R.string.context_item);

        /*
        时间
        位置
        环境温度
        噪声
        速度
        光线
         */
        menu.add(0, R.string.context_time, Menu.NONE, R.string.context_time);
        menu.add(0, R.string.context_location, Menu.NONE, R.string.context_location);
        menu.add(0, R.string.context_speed, Menu.NONE, R.string.context_speed);
        menu.add(0, R.string.context_light, Menu.NONE, R.string.context_light);
        menu.add(0, R.string.context_noise, Menu.NONE, R.string.context_noise);
        menu.add(0, R.string.context_distance, Menu.NONE, R.string.context_distance);
    }

    /**
     * 上下文菜单选择事件处理
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        int requestCode = -1;
        Class<?> cla = null;
        boolean status = true;
        switch (id) {
            case R.string.context_time:
                requestCode = R.string.context_time;
                cla = DefineContextTimeActivity.class;
                break;
            case R.string.context_location:
                requestCode = R.string.context_location;
                cla = DefineContextLocationActivity.class;
                break;
            case R.string.context_speed:
                requestCode = R.string.context_speed;
                cla = DefineContextSpeedActivity.class;
                break;
            case R.string.context_light:
                requestCode = R.string.context_light;
                cla = DefineContextLightActivity.class;
                break;
            case R.string.context_noise:
                requestCode = R.string.context_noise;
                cla = DefineContextNoiseActivity.class;
                break;
            case R.string.context_distance:
                requestCode = R.string.context_distance;
                cla = DefineContextDistanceActivity.class;
                break;
            default:
                status = false;
                Log.d(DefineContextActivity.class.getName(), "Undefined context item");
        }

        if (status) {
            Intent intent = new Intent(this, cla);
            // 低版本的SDK中，requestCode只能为16bit长
            startActivityForResult(intent, (short)requestCode);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        
        MyContext myContext = null;
        switch (requestCode) {
            // TODO 移动速度，光照强度，环境噪声，手机与用户的距离
            // TODO 实现围栏功能 百度地图API
            case (short)R.string.context_location:
            case (short)R.string.context_time:
            case (short)R.string.context_light:
            case (short)R.string.context_noise:
            case (short)R.string.context_speed:
            case (short)R.string.context_distance:
                myContext = (MyContext)data.getSerializableExtra(MyContext.key);
                // TODO 添加到情景列表中
                break;
            default: Log.d(DefineContextActivity.class.getName(), "not exist this request code : " +
                    String.valueOf(requestCode));
        }

        if (myContext != null) {
            Toast.makeText(this, myContext.getContextName(), Toast.LENGTH_SHORT).show();
        } else {
            Log.d(this.getLocalClassName(), "myContext is null");
        }
    }
}
