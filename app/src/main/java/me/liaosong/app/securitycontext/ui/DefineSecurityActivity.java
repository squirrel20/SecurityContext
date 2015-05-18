package me.liaosong.app.securitycontext.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
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

// TODO 定义安全规则
public class DefineSecurityActivity extends ActionBarActivity {
    private ListView selectedApp;
    private ListView selectedSet;
    private ListView selectedFile;

    private final static int APP_CODE = 1;
    private final static int SET_CODE = 2;

    private ArrayList<AppInfo> appInfos;
    private ArrayList<String> appsPackageName;
    AppArrayAdapter appArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_security);

        selectedApp = (ListView)findViewById(R.id.selected_app);
        selectedSet = (ListView)findViewById(R.id.selected_set);
        selectedFile = (ListView)findViewById(R.id.selected_file);

        View v1 = getLayoutInflater().inflate(R.layout.list_item_simple, null);
        View v2 = getLayoutInflater().inflate(R.layout.list_item_simple, null);
        View v3 = getLayoutInflater().inflate(R.layout.list_item_simple, null);

        ((TextView)v1.findViewById(R.id.textViewSimple)).setText(R.string.app);
        ((TextView)v2.findViewById(R.id.textViewSimple)).setText(R.string.settings);
        ((TextView)v3.findViewById(R.id.textViewSimple)).setText(R.string.file);

        v1.setOnClickListener(
                new SecurityClickListener(this, DefineSecurityAppActivity.class, APP_CODE));
        v2.setOnClickListener(
                new SecurityClickListener(this, DefineSecuritySetActivity.class, SET_CODE));

        selectedApp.addHeaderView(v1);
        selectedSet.addHeaderView(v2);
        selectedFile.addHeaderView(v3);

        appInfos = new ArrayList<>();
        appArrayAdapter = new AppArrayAdapter(this, appInfos);
        selectedApp.setAdapter(appArrayAdapter);

        // fot test
        ArrayList<String> arrayList1 = new ArrayList<>();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList1);
        selectedSet.setAdapter(adapter1);
        selectedFile.setAdapter(adapter1);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        if (requestCode == APP_CODE) {
            ArrayList<String> apps = data.getStringArrayListExtra("apps");
            appsPackageName = apps;
            if (((MyApplication)getApplication()).appInfos == null)
                ((MyApplication)getApplication()).getAllApps();
            ArrayList<AppInfo> allApps = ((MyApplication)getApplication()).appInfos;
            if (apps.size() > 0)
                appInfos.clear();
            for (int i = 0; i < apps.size(); i++)
                for (int j = 0; j < allApps.size(); j++) {
                    if (allApps.get(j).packageName.equals(apps.get(i))) {
                        appInfos.add(allApps.get(j));
                        break;
                    }
                }
            appArrayAdapter.notifyDataSetChanged();
        }
    }

    public class SecurityClickListener implements View.OnClickListener {
        private Class<?> aClass;
        private int requestCode;
        private Context context;
        public SecurityClickListener(Context context, Class<?> cla, int requestCode) {
            this.aClass = cla;
            this.requestCode = requestCode;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, aClass);
            startActivityForResult(intent, requestCode);
        }
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
                DefineSecurityAppActivity.ViewHolder viewHolder = new DefineSecurityAppActivity.ViewHolder();
                viewHolder.textView = (TextView)rowView.findViewById(R.id.list_app_name);
                viewHolder.imageView = (ImageView)rowView.findViewById(R.id.list_app_icon);
                viewHolder.appSwitch = (Switch)rowView.findViewById(R.id.switch_app);
                rowView.setTag(viewHolder);
            }

            final DefineSecurityAppActivity.ViewHolder holder = (DefineSecurityAppActivity.ViewHolder)rowView.getTag();
            holder.textView.setText(arrayList.get(position).appName);
            holder.imageView.setImageDrawable(arrayList.get(position).appIcon);
            holder.appSwitch.setVisibility(View.INVISIBLE);

            return rowView;
        }
    }

}
