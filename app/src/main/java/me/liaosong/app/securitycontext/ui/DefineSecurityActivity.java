package me.liaosong.app.securitycontext.ui;

import android.app.Activity;
import android.content.Context;
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

import java.io.File;
import java.util.ArrayList;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.AppInfo;
import me.liaosong.app.securitycontext.library.MyApplication;
import me.liaosong.app.securitycontext.library.SetInfo;
import me.liaosong.app.securitycontext.library.arrayadapter.SetArrayAdapter;

import paul.arian.fileselector.FileSelectionActivity;

public class DefineSecurityActivity extends ActionBarActivity {
    private ListView selectedApp;
    private ListView selectedSet;
    private ListView selectedFile;

    private final static int APP_CODE = 1;
    private final static int SET_CODE = 2;
    private final static int FILE_CODE = 3;

    private ArrayList<AppInfo> appInfos;
    private ArrayList<String> appsPackageName;
    AppArrayAdapter appArrayAdapter;

    private ArrayList<SetInfo> setInfos;
    private SetArrayAdapter setArrayAdapter;

    private ArrayList<String> files;
    ArrayAdapter<String> fileArrayAdapter;

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

        v1.setBackgroundResource(R.color.header);
        v2.setBackgroundResource(R.color.header);
        v3.setBackgroundResource(R.color.header);

        v1.setOnClickListener(
                new SecurityClickListener(this, DefineSecurityAppActivity.class, APP_CODE));
        v2.setOnClickListener(
                new SecurityClickListener(this, DefineSecuritySetActivity.class, SET_CODE));
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), FileSelectionActivity.class);
                startActivityForResult(intent, FILE_CODE);
            }
        });

        selectedApp.addHeaderView(v1);
        selectedSet.addHeaderView(v2);
        selectedFile.addHeaderView(v3);

        appInfos = new ArrayList<>();
        appArrayAdapter = new AppArrayAdapter(this, appInfos);
        selectedApp.setAdapter(appArrayAdapter);

        setInfos = new ArrayList<>();
        setArrayAdapter = new SetArrayAdapter(this, setInfos);
        selectedSet.setAdapter(setArrayAdapter);

        // fot test
        files = new ArrayList<>();
        fileArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, files);
        selectedFile.setAdapter(fileArrayAdapter);
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
        else if (requestCode == FILE_CODE) {
            ArrayList<File> Files = (ArrayList<File>) data.getSerializableExtra(FileSelectionActivity.FILES_TO_UPLOAD); //file array list
            files.clear();
            for(File file : Files){
                //String fileName = file.getName();
                files.add(file.getAbsolutePath());
            }
            fileArrayAdapter.notifyDataSetChanged();
        }
        else if (requestCode == SET_CODE) {
            ArrayList<SetInfo> tmp = (ArrayList<SetInfo>)data.getSerializableExtra("setInfos");
            setInfos.clear();
            for (int i = 0; i < tmp.size(); i++)
                if (tmp.get(i).statusID != R.string.the_default)
                    setInfos.add(tmp.get(i));
            setArrayAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void finish() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("set", setInfos);                // 返回设置信息
        Intent data = new Intent();
        data.putExtras(bundle);
        data.putStringArrayListExtra("app", appsPackageName);   // 返回包名
        data.putStringArrayListExtra("file", files);            // 返回文件绝对路径
        setResult(RESULT_OK, data);
        super.finish();
    }

    public void onButtonDoneClick(View v) {
        finish();
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
