package jsc.cactus.com.weanimal;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

import io.socket.client.IO;
import io.socket.emitter.Emitter;
import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.Share_status;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.Status;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.StatusType;
import jsc.cactus.com.weanimal.g_animal.main.familychat.DateFormat;

/**
 * Created by nyyyn on 2015-10-03.
 */
public class MyService extends Service {
    static boolean turn = false;

    public static boolean login = false;

    static Activity activity;
    static Context context;

    public static io.socket.client.Socket mSocket;

    {
        try {
            mSocket = IO.socket("http://gondr.iptime.org:52273");
            //OftenMethod.message(this,"CREATE SOCKET");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cons(Activity act, Context ctx) {
        Variable.service_activity = act;
        Variable.service_context = ctx;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        activity = Variable.service_activity;
        context = Variable.service_context;

        turn = true;

        FileMethod file = new FileMethod(new File("/data/data/jsc.cactus.com.weanimal/files/login/"), "login.txt");

        String userData = file.readFile();

        if (userData != "") {
            String data[] = userData.split("/");

            Variable.user_id = data[0];
            Variable.user_name = data[1];
            Variable.user_familycode = Integer.parseInt(data[2]);
            Variable.user_birthday = data[3];
            Variable.user_gender = data[4];
            login = true;
        }

        Log.i("TEST", Boolean.toString(login));

        Toast.makeText(this, "위애니멀", Toast.LENGTH_SHORT).show();

        Log.i("TEST", "Service Command");

        unregisterRestartAlarm();

        do {
            mSocket.connect();
            Toast.makeText(this, "연결 시도", Toast.LENGTH_SHORT).show();

            /*try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {

            }*/

            if (!mSocket.connected()) {
                Toast.makeText(this, "연결 실패", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "연결 성공", Toast.LENGTH_SHORT).show();
                Log.i("TEST", "Socket connect");

                if (login) {
                    try {
                        sendMessage();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                mSocket.on("SEND_MSG", Recive);


                try {
                    onSocket();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } while (!mSocket.connected());


        return Service.START_STICKY;
    }

    public void registerRestartAlarm() {
        Log.d("PersistentService", "registerRestartAlarm");
        Intent intent = new Intent(MyService.this, RestartService.class);
        intent.setAction("ACTION.RESTART.PersistentService");
        PendingIntent sender = PendingIntent.getBroadcast(MyService.this, 0, intent, 0);
        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 5 * 1000;                                               // 10초 후에 알람이벤트 발생
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 10 * 1000, sender);
    }

    public void unregisterRestartAlarm() {
        Log.d("PersistentService", "unregisterRestartAlarm");
        Intent intent = new Intent(MyService.this, RestartService.class);
        intent.setAction("ACTION.RESTART.PersistentService");
        PendingIntent sender = PendingIntent.getBroadcast(MyService.this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }


    @Override
    public IBinder onBind(Intent arg0) {
        Log.i("TEST", "Service Bind");
        return null;
    }

    @Override
    public void onDestroy() {

        mSocket.off("SEND_MSG", Recive);
        mSocket.off("RES_SET", StatusRecive);
        mSocket.off("RES_LEVEL", LevelRecive);

        turn = false;

        Toast.makeText(this, "서비스 종료", Toast.LENGTH_SHORT).show();

        Log.i("TEST", "Service Destroy");

        registerRestartAlarm();

        super.onDestroy();

    }

    public void onSocket() throws JSONException {
        mSocket.on("RES_SET", StatusRecive);
        mSocket.on("RES_LEVEL", LevelRecive);
    }

    public void sendMessage() throws JSONException {
        JSONObject data = new JSONObject();

        int msgfc = Variable.user_familycode;

        data.put("FC", msgfc);

        MyService.mSocket.emit("AUTO_LOGIN", data);
    }

    //로그인
    private Emitter.Listener Recive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String ID;
                    long time;

                    try {
                        ID = data.getString("id");
                        time = data.getLong("synctime");
                        
                        Log.i("TEST", "push");
                        push(1, ID + "님이 접속하셨습니다.",time);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
    };

    //상태 변화
    private Emitter.Listener StatusRecive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            Log.i("TEST", activity.toString());
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int family_love;
                    int family_food;
                    int family_water;

                    long time;

                    try {
                        family_water = data.getInt("WA");
                        family_food = data.getInt("FO");
                        family_love = data.getInt("LO");
                        time = data.getLong("synctime");

                        Log.i("TEST", "push");
                        push(2, "동물의 상태가 변화하였습니다.",time);

                        Animal.animal.getStatus().setStatus(StatusType.FOOD, family_food);
                        Animal.animal.getStatus().setStatus(StatusType.LOVE, family_love);
                        Animal.animal.getStatus().setStatus(StatusType.WATER, family_water);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
    };

    //레벨업
    private Emitter.Listener LevelRecive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int animal_level;

                    long time;

                    try {
                        animal_level = data.getInt("level");
                        time = data.getLong("synctime");

                        Log.i("TEST", "push");
                        push(3, "동물의 성장하였습니다.",time);

                        Animal.animal.setAge(animal_level);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
    };

    //채팅
    private Emitter.Listener ChatRecive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String id;
                    String name;
                    String text;
                    String time;
                    String day;

                    BufferedWriter bw;

                    try {
                        id = data.getString("ID");
                        name = data.getString("NAME");
                        text = data.getString("TEXT");
                        day = data.getString("DAY");
                        time = data.getString("TIME");

                        Log.i("TEST", "push");

                        bw = new BufferedWriter(new FileWriter("/data/data/jsc.cactus.com.weanimal/files/chat/" + day, true));


                        bw.append(" " + time + "|" + name + "|" + text);
                        bw.newLine();

                        bw.close();

                       // push(4, id + " \n " + text);
                    } catch (Exception e) {
                    }
                }
            });


        }
    };

    public void push(int id, String msg, long time) {
        OftenMethod.LoginNoti(id, this, msg, (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE), time);
    }
}
