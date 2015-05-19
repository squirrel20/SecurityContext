package me.liaosong.app.securitycontext.ui;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.AppInfo;
import me.liaosong.app.securitycontext.library.MyApplication;


public class DefineSecurityAppActivity extends ActionBarActivity {
    private ArrayList<AppInfo> appInfos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_security_app);

        if (((MyApplication)getApplication()).appInfos == null)
            ((MyApplication)getApplication()).getAllApps();
        appInfos = ((MyApplication)getApplication()).appInfos;

        ListView listView = (ListView)this.findViewById(R.id.list_apps);
        AppArrayAdapter appArrayAdapter = new AppArrayAdapter(this, appInfos);
        listView.setAdapter(appArrayAdapter);

    }

    @Override
    public void finish() {
        ArrayList<String> selectedApps  = new ArrayList<>();
        for (int i = 0; i < appInfos.size(); i++) {
            if (appInfos.get(i).isChecked)
                selectedApps.add(appInfos.get(i).packageName);
        }

        if (selectedApps.size() > 0) {
            Intent intent = new Intent();
            intent.putExtra("apps", selectedApps);
            setResult(RESULT_OK, intent);
        } else
            setResult(RESULT_CANCELED);

        super.finish();
    }

    public void onButtonDoneClick(View v) {
        finish();
    }

    static public class ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public Switch appSwitch;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_item_app, null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.textView = (TextView)rowView.findViewById(R.id.list_app_name);
                viewHolder.imageView = (ImageView)rowView.findViewById(R.id.list_app_icon);
                viewHolder.appSwitch = (Switch)rowView.findViewById(R.id.switch_app);
                rowView.setTag(viewHolder);
            }

            final ViewHolder holder = (ViewHolder)rowView.getTag();
            holder.textView.setText(arrayList.get(position).appName);
            holder.imageView.setImageDrawable(arrayList.get(position).appIcon);
            holder.appSwitch.setChecked(arrayList.get(position).isChecked);
            holder.appSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arrayList.get(position).isChecked = ((Switch)v).isChecked();
                }
            });

            return rowView;
        }
    }
}
