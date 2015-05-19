package me.liaosong.app.securitycontext.library.arrayadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import me.liaosong.app.securitycontext.R;

/**
 * Created by squirrel on 2015/5/19.
 */
public class SetArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] values;
    private int[] setStatus;
    private boolean notDefault = false;

    public SetArrayAdapter(Activity context, String[] values, int[] setStatus) {
        super(context, R.layout.securityitemlayout, values);
        this.context = context;
        this.values = values;
        this.setStatus = setStatus;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_item_set, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text1 = (TextView) rowView.findViewById(R.id.set_name);
            viewHolder.text2 = (TextView) rowView.findViewById(R.id.set_status);

            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        String s = values[position];
        holder.text1.setText(s);
        holder.text2.setText(context.getString(setStatus[position]));

        holder.text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setStatus[position] == R.string.the_default)
                    setStatus[position] = R.string.open;
                else if(setStatus[position] == R.string.open)
                    setStatus[position] = R.string.close;
                else if(setStatus[position] == R.string.close)
                    setStatus[position] = R.string.the_default;

                ((TextView)v).setText(context.getString(setStatus[position]));
            }
        });
        return rowView;
    }

    class ViewHolder {
        public TextView text1;
        public TextView text2;
    }

}

