package me.liaosong.app.securitycontext.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.MyContext;
import me.liaosong.app.securitycontext.library.MyContextSpinner;

public class DefineContextSpinnerActivity extends ActionBarActivity {

    private int id;
    private int arrayId;
    private float weight;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_context_spinner);

        weight = 0;

        Intent intent = this.getIntent();
        id = intent.getIntExtra(MyContext.key, 0);

        String[] arr = null;
        switch (id){
            case R.string.context_speed:
                arr = this.getResources().getStringArray(R.array.context_speed_arrays);
                arrayId = R.array.context_speed_arrays;
                break;
            case R.string.context_noise:
                arr = this.getResources().getStringArray(R.array.context_noise_arrays);
                arrayId = R.array.context_noise_arrays;
                break;
            case R.string.context_light:
                arr = this.getResources().getStringArray(R.array.context_light_arrays);
                arrayId = R.array.context_light_arrays;
                break;
            case R.string.context_distance:
                arr =this.getResources().getStringArray(R.array.context_distance_arrays);
                arrayId = R.array.context_distance_arrays;
                break;
        }

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arr);
        spinner.setAdapter(adapter);
    }

    @Override
    public void finish() {
        MyContextSpinner contextSpinner =
                new MyContextSpinner(
                        id, getString(id), arrayId, spinner.getSelectedItemId(),weight);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MyContext.key, contextSpinner);
        Intent data = new Intent();
        data.putExtras(bundle);
        setResult(RESULT_OK, data);

        super.finish();
    }

    public void onButtonDoneClick(View v) {
        EditText text = (EditText)findViewById(R.id.editText_spinner);
        weight = Float.valueOf(text.getText().toString());
        this.finish();
    }
}
