package me.liaosong.app.securitycontext.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.PWSQLiteOpenHelper;

public class AppCheckActivity extends ActionBarActivity {
    private String password;
    private TextView textView;
    private boolean isFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_check);

        this.getSupportActionBar().hide();
        password = PWSQLiteOpenHelper.getPassword(this);
        textView = (TextView) this.findViewById(R.id.check_password_status);
        EditText editText = (EditText) this.findViewById(R.id.check_password);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 4) {
                    if (s.toString().equals(password)) {
                        isFinish = true;
                        finish();
                    } else {
                        textView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_check, menu);
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
    public void onBackPressed() {}

    @Override
    public void onPause() {
        super.onPause();
        if (!isFinish)
            finish();
    }
}
