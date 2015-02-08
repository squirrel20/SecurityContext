package me.liaosong.app.securitycontext.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import me.liaosong.app.securitycontext.R;
import me.liaosong.app.securitycontext.library.Constants;
import me.liaosong.app.securitycontext.library.PWSQLiteOpenHelper;
import me.liaosong.app.securitycontext.ui.SetPasswordActivity;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PWSQLiteOpenHelper pwsqLiteOpenHelper = new PWSQLiteOpenHelper(this);
        Cursor cursor = pwsqLiteOpenHelper.getPassword(pwsqLiteOpenHelper.getReadableDatabase());
//        if (cursor != null) {
//            cursor.moveToFirst();
//            int count = cursor.getCount();
//            int columnCount = cursor.getColumnCount();
//            int index = cursor.getColumnIndex(PWSQLiteOpenHelper.COLUMN_NAME_PASSWORD);
//            String password = cursor.getString(index);
//            int i = 0;
//        }

        Intent intent;
        if (cursor == null || cursor.getCount() != 1) {
            intent = new Intent(this, SetPasswordActivity.class);
        } else {
            // 难道默认不是在第一个？
            // 为什么没有这行代码不能执行 cursor.getString
            cursor.moveToFirst();
            intent = new Intent(this, AccessActivity.class);
            intent.putExtra(
                    Constants.KEY_PASSWORD, cursor.getString(
                            cursor.getColumnIndex(PWSQLiteOpenHelper.COLUMN_NAME_PASSWORD)));
        }

        cursor.close();
        startActivity(intent);
        this.finish();

        //packageList();  // 测试当前安装的包

        //Intent intent = new Intent(this, DefineSecurityActivity.class);
        //startActivity(intent);
        // TODO 定义安全情景数据结构
        // 分为定义情景，定义安全规则，此处应该有两张表
        // 然后定义安全情景，就是链接情景和安全规则，此处应该有一张表
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void packageList() {
        PackageManager packageManager = this.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);

        Log.d("PACKAGE", String.valueOf(packageInfoList.size()));
        for (int i = 0; i < packageInfoList.size(); i++) {
            PackageInfo packageInfo = packageInfoList.get(i);
            Log.d("PACKAGE", packageInfo.applicationInfo.loadLabel(packageManager).toString());
        }
    }
}
