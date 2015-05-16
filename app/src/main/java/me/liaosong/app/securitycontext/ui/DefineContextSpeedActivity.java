package me.liaosong.app.securitycontext.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.MyContext;
import me.liaosong.app.securitycontext.library.MyContextSpinner;

public class DefineContextSpeedActivity extends ActionBarActivity {
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_context_speed);

        spinner = (Spinner)findViewById(R.id.spinner_speed);
    }

    @Override
    public void finish() {
        long selectedItemId = spinner.getSelectedItemId();
        MyContextSpinner myContextSpeed = new MyContextSpinner(
                R.string.context_speed,
                getString(R.string.context_speed),
                R.array.context_speed_arrays,
                spinner.getSelectedItemId()
        );
        Bundle bundle = new Bundle();
        bundle.putSerializable(MyContext.key, myContextSpeed);

        Intent data = new Intent();
        data.putExtras(bundle);
        setResult(RESULT_OK, data);

        super.finish();
    }

    public void onButtonDoneClick(View v) {
        this.finish();
    }

}
