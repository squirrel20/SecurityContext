package me.liaosong.app.securitycontext.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import me.liaosong.app.securitycontext.library.arrayadapter.MyArrayAdapter;
import me.liaosong.app.securitycontext.R;


public class DefineSecurityActivity extends ActionBarActivity {

    private ListView listView;
    private ArrayList<String> titleList;
    private MyArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_security);

        View footer = getLayoutInflater().inflate(R.layout.list_footer, null);

        titleList = new ArrayList<String>();
        titleList.add("0");
        adapter = new MyArrayAdapter(this, titleList);
        listView = (ListView) this.findViewById(R.id.listView);
        listView.addFooterView(footer);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_define_security, menu);
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

    public void doneClick(View view) {
        titleList.add(String.valueOf(titleList.size()));
        // 当数据发生变化的时候更新adapter
        adapter.notifyDataSetChanged();
    }
}
