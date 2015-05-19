package me.liaosong.app.securitycontext.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.AppInfo;
import me.liaosong.app.securitycontext.library.MyContext;
import me.liaosong.app.securitycontext.library.SCInfo;
import me.liaosong.app.securitycontext.library.SetInfo;

public class SecurityContextActivity extends ActionBarActivity {
    private Context context;
    private final static int CONTEXT_CODE = 1;
    private final static int SECURITY_CODE = 2;
    private ArrayList<MyContext> myContextList;
    /**
     * 情景集名
     */
    private String myContextsName;
    private ArrayList<String> appsPackageName;
    private ArrayList<SetInfo> setInfos;
    private ArrayList<String> files;

    private ArrayAdapter<SCInfo> scInfoArrayAdapter;
    private ArrayList<SCInfo> scInfoArrayList;

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

        scInfoArrayList = new ArrayList<>();
        scInfoArrayAdapter = new MyArrayAdapter(this);
        listView.setAdapter(scInfoArrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        if (requestCode == CONTEXT_CODE) {
//            int size = data.getIntExtra(MyContext.key, 0);
//            for (int i = 0; i < size; i++) {
//                MyContext myContext = (MyContext)data.getSerializableExtra(String.valueOf(i));
//                myContextList.add(myContext);
//            }

            myContextsName = data.getStringExtra("ContextName");
            myContextList = (ArrayList<MyContext>)data.getSerializableExtra("context");

            Intent intent = new Intent(this, DefineSecurityActivity.class);
            startActivityForResult(intent, SECURITY_CODE);
            //Toast.makeText(this, myContextList.get(0).getContextName(), Toast.LENGTH_SHORT).show();
        } else if (requestCode == SECURITY_CODE) {
            setInfos = (ArrayList<SetInfo>)data.getSerializableExtra("set");
            files = data.getStringArrayListExtra("file");
            appsPackageName = data.getStringArrayListExtra("app");

            scInfoArrayList.add(new SCInfo(R.string.running, myContextsName));
            scInfoArrayAdapter.notifyDataSetChanged();

            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                Log.d(getLocalClassName(), "SD card is not available/writeable right now");
                return;
            }
            // TODO 怎么把安全情景保存到数据库当中，并且运行该安全情景
            // TODO 把安全情景名和状态放在数据库中，把具体安全规则信息放在文件中
            try {
                String pathName = "/sdcard/sc/";
                String fileName = myContextsName;
                File path = new File(pathName);
                File file = new File(pathName + fileName);
                if (!path.exists())
                    path.mkdir();
                if (!file.exists())
                    file.createNewFile();
                else {
                    file.delete();
                    file.createNewFile();
                }
                FileOutputStream fs = new FileOutputStream(file);
                ObjectOutputStream outputStream = new ObjectOutputStream(fs);
                outputStream.writeObject(myContextList);
                outputStream.writeObject(setInfos);
                outputStream.writeObject(files);
                outputStream.writeObject(appsPackageName);
                outputStream.close();
                fs.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public class MyArrayAdapter extends ArrayAdapter<SCInfo> {
        private Activity activity;
        public MyArrayAdapter(Activity activity) {
            super(activity, R.layout.list_item_sc, scInfoArrayList);
            this.activity = activity;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            if (rowView == null) {
                LayoutInflater inflater = activity.getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_item_sc, null);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.text1 = (TextView) rowView.findViewById(R.id.sc_name);
                viewHolder.text2 = (TextView) rowView.findViewById(R.id.sc_status);

                rowView.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) rowView.getTag();
            SCInfo scInfo = scInfoArrayList.get(position);
            holder.text1.setText(scInfo.SCName);
            holder.text2.setText(activity.getString(scInfo.statusID));

            holder.text2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (scInfoArrayList.get(position).statusID == R.string.running)
                        scInfoArrayList.get(position).statusID = R.string.stop;
                    else
                        scInfoArrayList.get(position).statusID = R.string.running;
                    ((TextView)v).setText(activity.getString(scInfoArrayList.get(position).statusID));
                    // TODO 如何使安全情景开始和停止
                }
            });

            return rowView;
        }

        class ViewHolder {
            public TextView text1;
            public TextView text2;
        }
    }


}
