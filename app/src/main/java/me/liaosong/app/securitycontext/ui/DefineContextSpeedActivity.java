package me.liaosong.app.securitycontext.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import me.liaosong.app.securitycontext.R;

public class DefineContextSpeedActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_context_speed);
    }

    @Override
    public void finish() {

        super.finish();
    }

    public void onButtonDoneClick(View v) {
        this.finish();
    }

}
