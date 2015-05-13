package me.liaosong.app.securitycontext.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
    /**
     * 时间情景唯一标识符
     */
    public final static int TIME_ID = 100;
    /**
     * 位置情景唯一标识符
     */
    public final static int LOCATION_ID = 101;
     /**
     * 移动速度情景唯一标识符
     */
    public final static int SPEED_ID = 102;
     /**
     * 光照强度情景唯一标识符
     */
    public final static int LIGHT_ID = 103;
     /**
     * 环境噪声情景唯一标识符
     */
    public final static int NOISE_ID = 104;
     /**
     * 用户与手机距离情景唯一标识符
     */
    public final static int DISTANCE_ID = 105;

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
        menu.add(0, TIME_ID, Menu.NONE, R.string.time_context_item);    // TODO 实现时间选项
        menu.add(0, LOCATION_ID, Menu.NONE, R.string.location_context_item);
        menu.add(0, SPEED_ID, Menu.NONE, "移动速度");
        menu.add(0, LIGHT_ID, Menu.NONE, "光照强度");
        menu.add(0, NOISE_ID, Menu.NONE, "环境噪声");
        menu.add(0, DISTANCE_ID, Menu.NONE, "手机与用户的距离");
    }

    /**
     * 上下文菜单选择事件处理
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case TIME_ID:
                intent = new Intent(this, DefineContextTimeActivity.class);
                this.startActivityForResult(intent, TIME_ID);
                break;
            case LOCATION_ID:
                intent = new Intent(this, DefineContextLocationActivity.class);
                startActivityForResult(intent, LOCATION_ID);
                break;
            case SPEED_ID: break;
            case LIGHT_ID: break;
            case NOISE_ID: break;
            case DISTANCE_ID: break;
            default:
                Log.d(DefineContextActivity.class.getName(), "Undefined context item");
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        MyContext myContext = null;
        switch (requestCode) {
            case LOCATION_ID:
                // TODO 实现围栏功能 百度地图API
            case TIME_ID:
                myContext = (MyContext)data.getSerializableExtra(MyContext.key);
                // TODO 添加到情景列表中
                break;
            default: Log.d(DefineContextActivity.class.getName(), "not exist this request code : " +
                    String.valueOf(requestCode));
        }

        if (myContext != null) {
            Toast.makeText(this, myContext.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
