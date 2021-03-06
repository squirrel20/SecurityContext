package me.liaosong.app.securitycontext.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.MyContext;
import me.liaosong.app.securitycontext.library.MyContextTime;

public class DefineContextTimeActivity extends ActionBarActivity {
    public final static DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();

    private SlideDateTimePicker picker;
    private TextView startDateView;
    private TextView endDateView;
    private float weight;

    private int currentPick;
    private final int pickStartDate = 0;
    private final int pickEndDate = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_context_time);

        weight = 0;
        startDateView = (TextView)findViewById(R.id.start_time);
        endDateView = (TextView)findViewById(R.id.end_time);

        SlideDateTimeListener listener = new SlideDateTimeListener() {
            @Override
            public void onDateTimeSet(Date date) {
                if (currentPick == pickStartDate) {
                    startDateView.setText(dateFormat.format(date));
                }
                else if (currentPick == pickEndDate) {
                    endDateView.setText((dateFormat.format(date)));
                }
            }

            @Override
            public void onDateTimeCancel() {

            }
        };
        picker = new SlideDateTimePicker(getSupportFragmentManager());
        picker.setInitialDate(new Date());
        picker.setListener(listener);
    }

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_define_context_time, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.set_date_done) {
//            this.finish();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void finish() {
        if (startDateView.length() > 0 && endDateView.length() > 0) {
            Intent data = new Intent();
            MyContextTime contextTime = new MyContextTime(R.string.context_time,
                    this.getString(R.string.context_time),
                    startDateView.getText().toString(),
                    endDateView.getText().toString(),
                    false,
                    weight);
            Bundle bundle = new Bundle();
            // 使用serializable在activity之间传递object对象
            bundle.putSerializable(MyContext.key, contextTime);
            data.putExtras(bundle);
            setResult(RESULT_OK, data);
        } else {
            setResult(RESULT_CANCELED);
        }
        super.finish();
    }

    public void onSetStartTimeClick(View v) {
        currentPick = pickStartDate;
        picker.show();
    }

    public void onSetEndTimeClick(View v) {
        currentPick = pickEndDate;
        picker.show();
    }

    public void onButtonDoneClick(View v) {
        EditText text = (EditText)findViewById(R.id.editText_time);
        weight = Float.valueOf(text.getText().toString());
        this.finish();
    }
}
