package me.liaosong.app.securitycontext;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by squirrel on 2015/2/4.
 */
public class MyArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> values;

    public MyArrayAdapter(Activity context, ArrayList<String> values) {
        super(context, R.layout.securityitemlayout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.securityitemlayout, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text1 = (TextView) rowView.findViewById(R.id.security_item);
            viewHolder.text2 = (TextView) rowView.findViewById(R.id.security_item_value);

            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        String s = values.get(position);
        holder.text1.setText(s);
        holder.text2.setText(s);

        return rowView;
    }

    static class ViewHolder {
        public TextView text1;
        public TextView text2;
    }
}