package me.liaosong.app.securitycontext.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.MyContext;

public class DefineContextActivity extends ActionBarActivity {
    private ArrayList<MyContext> myContextList;
    private MyContextAdapter arrayAdapter;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_context);

        myContextList = new ArrayList<>();
        editText = (EditText)findViewById(R.id.editText_context);

        ListView listView = (ListView) this.findViewById(R.id.define_context);
        View footer = this.getLayoutInflater().inflate(R.layout.list_footer, null);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });
        listView.addFooterView(footer);

        arrayAdapter = new MyContextAdapter(this);
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
                //cla = DefineContextSpeedActivity.class;
                cla = DefineContextSpinnerActivity.class;
                break;
            case R.string.context_light:
                requestCode = R.string.context_light;
                //cla = DefineContextLightActivity.class;
                cla = DefineContextSpinnerActivity.class;
                break;
            case R.string.context_noise:
                requestCode = R.string.context_noise;
                //cla = DefineContextNoiseActivity.class;
                cla = DefineContextSpinnerActivity.class;
                break;
            case R.string.context_distance:
                requestCode = R.string.context_distance;
                //cla = DefineContextDistanceActivity.class;
                cla = DefineContextSpinnerActivity.class;
                break;
            default:
                status = false;
                Log.d(DefineContextActivity.class.getName(), "Undefined context item");
        }

        if (status) {
            Intent intent = new Intent(this, cla);
            intent.putExtra(MyContext.key, requestCode);
            // 低版本的SDK中，requestCode只能为16bit长
            startActivityForResult(intent, (short) requestCode);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        
        MyContext myContext = null;
        switch (requestCode) {
            // TODO 实现围栏功能 百度地图API
            case (short)R.string.context_location:
            case (short)R.string.context_time:
            case (short)R.string.context_light:
            case (short)R.string.context_noise:
            case (short)R.string.context_speed:
            case (short)R.string.context_distance:
                myContext = (MyContext)data.getSerializableExtra(MyContext.key);
                myContextList.add(myContext);
                // Adapter数据有更新
                arrayAdapter.notifyDataSetChanged();
                break;
            default: Log.d(DefineContextActivity.class.getName(),
                    "not exist this request code : " +
                    String.valueOf(requestCode));
        }

        if (myContext != null) {
            Toast.makeText(this, myContext.getContextName(), Toast.LENGTH_SHORT).show();
        } else {
            Log.d(this.getLocalClassName(), "myContext is null");
        }
    }

    @Override
    public void finish() {
        Bundle bundle = new Bundle();
        for (int i = 0; i < myContextList.size(); i++) {
            bundle.putSerializable(String.valueOf(i), myContextList.get(i));
        }
        Intent data = new Intent();
        data.putExtra(MyContext.key, myContextList.size());
        data.putExtra("ContextName", editText.getText().toString());
        data.putExtras(bundle);
        setResult(RESULT_OK, data);

        super.finish();
    }

    public void onButtonDoneClick(View v) {
        this.finish();
    }

    // TODO 静态类是什么意思
    public static class ContextViewHolder {
        public TextView contextViewName;
        public TextView contextViewValue;
        public TextView contextViewWeight;
    }

    public class MyContextAdapter extends ArrayAdapter<MyContext> {
        private Activity activity;
        public MyContextAdapter(Activity activity) {
            super(activity, R.layout.list_item_context, myContextList);
            this.activity = activity;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            if (rowView == null) {
                LayoutInflater inflater = activity.getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_item_context, null);

                ContextViewHolder holder = new ContextViewHolder();
                holder.contextViewName = (TextView)rowView.findViewById(R.id.context_name);
                holder.contextViewValue = (TextView)rowView.findViewById(R.id.context_value);
                holder.contextViewWeight = (TextView)rowView.findViewById(R.id.context_weight);

                rowView.setTag(holder);
            }

            ContextViewHolder viewHolder = (ContextViewHolder)rowView.getTag();
            MyContext myContext = myContextList.get(position);
            viewHolder.contextViewName.setText(myContext.getContextName());
            viewHolder.contextViewValue.setText(myContext.getValue());
            viewHolder.contextViewWeight.setText(String.valueOf(myContext.getWeight()));

            return rowView;
        }
    }
}
