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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.SetInfo;
import me.liaosong.app.securitycontext.library.arrayadapter.SetArrayAdapter;

public class DefineSecuritySetActivity extends ActionBarActivity {
    private ListView listView;
    private int[] setStatus;
    private String[] sets;
    private ArrayList<SetInfo> setInfos;

    // TODO SET
    // 0 默认 1 打开 2 关闭
    // {SetName, SetValue(0,1,2)} 只需要返回设置了打开和关闭的设置项
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_security_set);

        listView = (ListView)findViewById(R.id.security_set);
        sets = getResources().getStringArray(R.array.sets);
        setInfos = new ArrayList<>();
        for (String set : sets) {
            setInfos.add(new SetInfo(R.string.the_default, set));
        }

        //MyArrayAdapter adapter = new MyArrayAdapter(this, sets, setStatus);
        SetArrayAdapter setArrayAdapter = new SetArrayAdapter(this, setInfos);
        listView.setAdapter(setArrayAdapter);
    }

    @Override
    public void finish() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("setInfos", setInfos);
        Intent data = new Intent();
        data.putExtras(bundle);
        setResult(RESULT_OK, data);
        super.finish();
    }

    public void onButtonDoneClick(View v) {
        this.finish();
    }
}
