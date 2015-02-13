package me.liaosong.app.securitycontext.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.Constants;
import me.liaosong.app.securitycontext.library.ContextItem;

public class DefineContextItemActivity extends ActionBarActivity {

    private ArrayList<ContextItem> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_context_item);

        arrayList = new ArrayList<ContextItem>();
        arrayList.add(new ContextItem(Constants.TIME_CONTEXT_ITEM,
                this.getString(R.string.time_context_item)));
        arrayList.add(new ContextItem(Constants.LOCATION_CONTEXT_ITEM,
                this.getString(R.string.location_context_item)));

        ListView listView = (ListView) this.findViewById(R.id.define_context_item);
        listView.setAdapter(new ContextArrayAdapter(this));
        listView.setOnItemClickListener(new ItemOnClickListener());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_define_context_item, menu);
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

    private class ContextArrayAdapter extends ArrayAdapter<ContextItem> {

        private Activity context;

        public ContextArrayAdapter(Activity context) {
            super(context, R.layout.list_item_simple, arrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            if (rowView == null) {
                rowView = context.getLayoutInflater().inflate(R.layout.list_item_simple, null);
                TextView textView = (TextView) rowView.findViewById(R.id.textViewSimple);
                rowView.setTag(textView);
            }

            TextView textView = (TextView) rowView.getTag();
            textView.setText(arrayList.get(position).getName());

            return rowView;
        }
    }

    private class ItemOnClickListener implements AdapterView.OnItemClickListener {

        ContextItem contextItem;

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            contextItem = arrayList.get(position);
        }
    }
}
