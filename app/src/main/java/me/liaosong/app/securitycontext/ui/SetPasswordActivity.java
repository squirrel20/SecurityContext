package me.liaosong.app.securitycontext.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.PWSQLiteOpenHelper;


public class SetPasswordActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_password, menu);
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

    public void setPasswordDoneClick(View view) {
        TextView textView = (TextView) findViewById(R.id.set_password_status);

        EditText set_password = (EditText) findViewById(R.id.set_password);
        if (set_password.getText().toString().length() != 4) {
            textView.setText(this.getText(R.string.set_password_wrong));
            return;
        }

        EditText set_password_repeat = (EditText) findViewById(R.id.set_password_repeat);
        if (!set_password.getText().toString().equals(set_password_repeat.getText().toString())) {
            textView.setText(this.getText(R.string.set_password_repeat_wrong));
            return;
        }

        EditText password_protection_question =
                (EditText) findViewById(R.id.password_protection_question);
        if (password_protection_question.getText().length() == 0) {
            textView.setText(this.getText(R.string.password_protection_question_wrong));
            return;
        }

        EditText password_protection_answer =
                (EditText) findViewById(R.id.password_protection_answer);
        if (password_protection_answer.getText().length() == 0) {
            textView.setText(this.getText(R.string.password_protection_answer_wrong));
            return;
        }

        savePassword(set_password.getText().toString(),
                password_protection_question.getText().toString(),
                password_protection_answer.getText().toString());

        Intent intent = new Intent(this, AccessActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void savePassword(String password, String ppQuestion, String ppAnswer) {
        PWSQLiteOpenHelper pwsqLiteOpenHelper = new PWSQLiteOpenHelper(this);
        pwsqLiteOpenHelper.savePassword(pwsqLiteOpenHelper.getWritableDatabase(),
                password, ppQuestion, ppAnswer);
    }
}
