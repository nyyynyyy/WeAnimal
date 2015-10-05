package jsc.cactus.com.weanimal;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.emitter.Emitter;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.Share_status;

/**
 * Created by nyyyn on 2015-10-03.
 */
public class MyService extends Service {
    static boolean turn = false;

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
        activity = act;
        context = ctx;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        turn = true;

        Log.i("TEST", "Service Command");

        while (!mSocket.connected()) {
            mSocket.connect();
            Toast.makeText(this, "연결 시도", Toast.LENGTH_SHORT).show();

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {

            }

            if (!mSocket.connected()) {
                Toast.makeText(this, "연결 실패", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "연결 성공", Toast.LENGTH_SHORT).show();
                Log.i("TEST", "Socket connect");
                mSocket.on("SEND_MSG", Recive);
                try {
                    onSocket();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        return Service.START_STICKY;
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

        turn = false;

        push(1, "서비스가 종료되었습니다.");

        Log.i("TEST", "Service Destroy");

    }

    public void onSocket() throws JSONException {
        JSONObject data = new JSONObject();


//        data.put("FA", Variable.user_familycode);

//        MyService.mSocket.emit("STATUS", data);
        MyService.mSocket.on("RES_SET", StatusRecive);
    }

    private Emitter.Listener Recive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String ID;

                    try {
                        ID = data.getString("id");

                        Log.i("TEST", "push");
                        push(1, ID + "님이 접속하셨습니다.");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
    };


    private Emitter.Listener StatusRecive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int family_love;
                    int family_food;
                    int family__water;

                    try {
                        family__water = data.getInt("WA");
                        family_food = data.getInt("FO");
                        family_love = data.getInt("LO");

                        Log.i("TEST", "push");
                        push(2, "동물의 상태가 변화하였습니다.");

                        new Share_status(family__water, family_food, family_love);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
    };

    public void push(int id, String msg) {
        OftenMethod.onBtnNotification(id, this, msg, (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
    }
}
