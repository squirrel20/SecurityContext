package me.liaosong.app.securitycontext.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.MyContext;

public class SecurityContextActivity extends ActionBarActivity {
    private Context context;
    private final static int CONTEXT_CODE = 1;
    private final static int SECURITY_CODE = 2;
    private ArrayList<MyContext> myContextList;
    /**
     * 情景集名
     */
    private String myContextsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_context);

        context = this;
        myContextList = new ArrayList<>();

        View footer = this.getLayoutInflater().inflate(R.layout.list_footer, null);
        ListView listView = (ListView)this.findViewById(R.id.security_context);
        listView.addFooterView(footer);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DefineContextActivity.class);
                startActivityForResult(intent, CONTEXT_CODE);
            }
        });

        String[] arr = new String[0];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arr);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        if (requestCode == CONTEXT_CODE) {
            int size = data.getIntExtra(MyContext.key, 0);
            myContextsName = data.getStringExtra("ContextName");
            for (int i = 0; i < size; i++) {
                MyContext myContext = (MyContext)data.getSerializableExtra(String.valueOf(i));
                myContextList.add(myContext);
            }

            Intent intent = new Intent(this, DefineSecurityActivity.class);
            startActivityForResult(intent, SECURITY_CODE);
            //Toast.makeText(this, myContextList.get(0).getContextName(), Toast.LENGTH_SHORT).show();
        } else if (requestCode == SECURITY_CODE) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_security_context, menu);
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
}
