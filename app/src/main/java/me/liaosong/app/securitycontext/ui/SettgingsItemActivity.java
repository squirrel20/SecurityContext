package me.liaosong.app.securitycontext.ui;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import me.liaosong.app.securitycontext.R;

public class SettgingsItemActivity extends ActionBarActivity {
    private String[] settings = {"音量", "蓝牙", "数据", "WLAN", "震动", "GPS", "同步"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settgings_item);

        ListView listView = (ListView)this.findViewById(R.id.list_settings);
//        SetArrayAdapter setArrayAdapter = new SetArrayAdapter(this, settings);
//        listView.setAdapter(setArrayAdapter);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settings);
        listView.setAdapter(arrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settgings_item, menu);
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

    private class SetArrayAdapter extends ArrayAdapter<String> {

        public SetArrayAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
        }
    }

}
