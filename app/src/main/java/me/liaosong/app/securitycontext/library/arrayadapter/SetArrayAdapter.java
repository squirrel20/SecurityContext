package me.liaosong.app.securitycontext.library.arrayadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.SetInfo;

/**
 * Created by squirrel on 2015/5/19.
 * …Ë÷√µƒ  ≈‰∆˜
 */
public class SetArrayAdapter extends ArrayAdapter<SetInfo> {
    private final Activity context;
    private ArrayList<SetInfo> setInfos;

    public SetArrayAdapter(Activity context, ArrayList<SetInfo> setInfos) {
        super(context, R.layout.securityitemlayout, setInfos);
        this.context = context;
        this.setInfos = setInfos;
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
        String s = setInfos.get(position).name;
        int id = setInfos.get(position).statusID;
        holder.text1.setText(s);
        holder.text2.setText(context.getString(id));

        holder.text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setInfos.get(position).statusID == R.string.the_default)
                    setInfos.get(position).statusID = R.string.open;
                else if (setInfos.get(position).statusID == R.string.open)
                    setInfos.get(position).statusID = R.string.close;
                else if (setInfos.get(position).statusID == R.string.close)
                    setInfos.get(position).statusID = R.string.the_default;

                ((TextView) v).setText(context.getString(setInfos.get(position).statusID));
            }
        });

        return rowView;
    }

    class ViewHolder {
        public TextView text1;
        public TextView text2;
    }

}

