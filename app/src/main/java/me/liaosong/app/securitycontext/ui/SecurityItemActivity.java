package me.liaosong.app.securitycontext.ui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.AppInfo;
import me.liaosong.app.securitycontext.library.AppManager;


public class SecurityItemActivity extends ActionBarActivity {
    ArrayList<AppInfo> appInfos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_item);

        AppManager appManager = new AppManager(this);
        appInfos = appManager.appInfos;

        ListView listView = (ListView)this.findViewById(R.id.list_apps);
        AppArrayAdapter appArrayAdapter = new AppArrayAdapter(this, appInfos);
        listView.setAdapter(appArrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_security_item, menu);
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

    static public class ViewHolder {
        public TextView textView;
        public ImageView imageView;
    }

    private class AppArrayAdapter extends ArrayAdapter<AppInfo> {
        private ArrayList<AppInfo> arrayList;
        private Activity context;

        public AppArrayAdapter(Activity context,  ArrayList<AppInfo> arrayList) {
            super(context, R.layout.list_item_app, arrayList);
            this.arrayList = arrayList;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_item_app, null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.textView = (TextView)rowView.findViewById(R.id.list_app_name);
                viewHolder.imageView = (ImageView)rowView.findViewById(R.id.list_app_icon);
                rowView.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder)rowView.getTag();
            holder.textView.setText(arrayList.get(position).appName);
            holder.imageView.setImageDrawable(arrayList.get(position).appIcon);

            return rowView;
        }
    }
}
