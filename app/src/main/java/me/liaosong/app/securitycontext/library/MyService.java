package me.liaosong.app.securitycontext.library;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.liaosong.app.securitycontext.ui.AccessActivity;
import me.liaosong.app.securitycontext.ui.AppCheckActivity;

public class MyService extends Service {
    private ArrayList<ArrayList<MyContext>> allContext;
    private ArrayList<ArrayList<SetInfo>> allSets;
    private ArrayList<ArrayList<String>> allPackageNames;
    private ArrayList<ArrayList<String>> allFiles;

    private Timer timer;

    public MyService() {
        allContext = new ArrayList<>();
        allSets = new ArrayList<>();
        allPackageNames = new ArrayList<>();
        allFiles = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        timer.cancel();
        timer.purge();
        timer = null;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(MyService.class.getName(), "on start command");
        startTimer();
        return Service.START_STICKY;    // 当重新启动service时，将会调用onStartCommand
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void notifyServiceUpdate() {
        readMyContexts();
        Log.d(MyService.class.getName(), "updated");
    }

    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }
    private final IBinder binder = new MyBinder();

    private void readMyContexts() {
        String pathNmae = "sdcard/sc/";

        try {
            File path = new File(pathNmae);
            File[] listFiles = path.listFiles();
            allContext.clear();
            allSets.clear();
            allFiles.clear();
            allPackageNames.clear();
            for (File file : listFiles) {
                FileInputStream fs = new FileInputStream(file);
                ObjectInputStream inputStream = new ObjectInputStream(fs);
                ArrayList<MyContext> myContexts = (ArrayList<MyContext>) inputStream.readObject();
                ArrayList<SetInfo> setInfos = (ArrayList<SetInfo>) inputStream.readObject();
                ArrayList<String> files = (ArrayList<String>) inputStream.readObject();
                ArrayList<String> appsPackageName = (ArrayList<String>) inputStream.readObject();

                allContext.add(myContexts);
                allSets.add(setInfos);
                allFiles.add(files);
                allPackageNames.add(appsPackageName);

                inputStream.close();
                fs.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void startTimer() {
        if (timer == null) {
            timer = new Timer();
            LockTask lockTask = new LockTask(this);
            timer.schedule(lockTask, 0L, 1000L);
        }
    }

    public class LockTask extends TimerTask {
        public static final String TAG = "LockTask";
        private Context mContext;
        String testPackageName = "com.htc.notes";
        String testClassName = "com.htc.notes.collection.NotesGridViewActivity";

        private ActivityManager mActivityManager;

        public LockTask(Context context) {
            mContext = context;
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }

        @Override
        public void run() {
//            ComponentName topActivity = mActivityManager.getRunningTasks(1).get(0).topActivity;
            String processName = mActivityManager.getRunningAppProcesses().get(0).processName;
//            String packageName = topActivity.getPackageName();
//            String className = topActivity.getClassName();

//            Log.v(TAG, "packageName " + packageName);
//            Log.v(TAG, "className " + className);
            Log.v(TAG, "processName " + processName);
            if (processName.equals("com.miui.gallery")) {
                // TODO 为什么会跑到SecurityContextActivity去呢
                Intent intent = new Intent(mContext.getApplicationContext(), AccessActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("service", true);
                mContext.startActivity(intent);
            }

        }
    }
}
