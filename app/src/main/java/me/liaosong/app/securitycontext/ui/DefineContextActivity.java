package me.liaosong.app.securitycontext.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.Constants;
import me.liaosong.app.securitycontext.library.ContextItem;

public class DefineContextActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_context);

        ListView listView = (ListView) this.findViewById(R.id.define_context);
        View header = this.getLayoutInflater().inflate(R.layout.list_header, null);
        View footer = this.getLayoutInflater().inflate(R.layout.list_footer, null);

        ((EditText) header.findViewById(R.id.header_edit_text))
                .setHint(R.string.input_context_name);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });


        listView.addHeaderView(header);
        listView.addFooterView(footer);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, new ArrayList<String>());
        listView.setAdapter(arrayAdapter);

        this.registerForContextMenu(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_define_context, menu);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle(R.string.context_item);

        menu.add(0, Constants.TIME_CONTEXT_ITEM, Menu.NONE, R.string.time_context_item);
        menu.add(0, Constants.LOCATION_CONTEXT_ITEM, Menu.NONE, R.string.location_context_item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(this, String.valueOf(item.getItemId()), Toast.LENGTH_SHORT).show();

        return true;
    }
}
