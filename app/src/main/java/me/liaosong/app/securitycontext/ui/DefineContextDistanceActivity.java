package me.liaosong.app.securitycontext.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.MyContext;
import me.liaosong.app.securitycontext.library.MyContextSpinner;

public class DefineContextDistanceActivity extends ActionBarActivity {
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_context_distance);

        spinner = (Spinner)findViewById(R.id.spinner_distance);
    }

    @Override
    public void finish() {
        MyContextSpinner distance = new MyContextSpinner(
                R.string.context_distance,
                getString(R.string.context_distance),
                R.array.context_distance_arrays,
                spinner.getSelectedItemId());
        Bundle bundle = new Bundle();
        bundle.putSerializable(MyContext.key, distance);
        Intent  data = new Intent();
        data.putExtras(bundle);
        setResult(RESULT_OK, data);
        super.finish();
    }

    public void onButtonDoneClick(View v) {
        this.finish();
    }

}
