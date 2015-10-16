package jsc.cactus.com.weanimal;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.emitter.Emitter;
import jsc.cactus.com.weanimal.g_animal.main.DateFormat;
import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.StatusType;

import jsc.cactus.com.weanimal.g_animal.main.familychat.ChatManager;

import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;
import jsc.cactus.com.weanimal.g_animal.main.users.User;
import jsc.cactus.com.weanimal.g_animal.main.users.UserGender;

/**
 * Created by nyyyn on 2015-10-03.
 */
public class MyService extends Service {

    private Handler toastHandler;
//    private Handler pushHandler;

    private Thread connect;

    //토스트-------------------------------------------------
    private class ToastRunnable implements Runnable {
        String mText;

        public ToastRunnable(String text) {
            mText = text;
        }

        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), mText, Toast.LENGTH_SHORT).show();
        }
    }

    private void toast(String text) {
        toastHandler.post(new ToastRunnable(text));
    }
    //--------------------------------------------------------

    //푸쉬-------------------------------------------------
    private void noti(int ID, String tickerText, String titleText, String mainText, long time) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(this);
        // 작은 아이콘 이미지.
        builder.setSmallIcon(R.mipmap.ic_launcher);
        // 알림이 출력될 때 상단에 나오는 문구.
        builder.setTicker(tickerText);
        // 알림 출력 시간.
        builder.setWhen(time);
        // 알림 제목.
        builder.setContentTitle(titleText);
        // 알림 내용.
        builder.setContentText(mainText);
        // 알림 터치시 반응.
        builder.setContentIntent(pendingIntent);
        // 알림 터치시 반응 후 알림 삭제 여부.
        builder.setAutoCancel(true);
        // 우선순위.
        //builder.setPriority(NotificationCompat.PRIORITY_MAX);
        // 고유ID로 알림을 생성.
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(ID, builder.build());
    }

    private void loginPush(String user_name, long time) {
        noti(1, "누군가가 접속하였습니다.", user_name, "님이 접속하였습니다.", time);
    }

    private void statusPush(String user_name, String type, long time) {
        noti(2, "동물의 상태가 변화하였습니다.", user_name, "님이 " + type + " 주셨습니다.", time);
    }

    private void levelupPush(String animal_name, long time) {
        noti(3, "동물이 성장하였습니다.", animal_name, "이(가) 성장하였습니다.", time);
    }

    private void chatPush(String user_name, String msg, long time) {
        noti(4, user_name + " : " + msg, user_name, msg, time);
    }
    //--------------------------------------------------------

    public static boolean service_turn = false;
    public static boolean server_turn = false;
    public static boolean connect_turn = false;
    public static boolean login = false;
    public static boolean animal = false;

    static Activity activity;
    static Context context;

    //소켓----------------------------------------------------
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
    //--------------------------------------------------------


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        animal = false;

        activity = Variable.service_activity;
        context = Variable.service_context;

        //핸들러
        toastHandler = new Handler();
//        pushHandler = new Handler();

        //서비스 작동 여부
        service_turn = true;

        //파일 위치
        FileMethod file = new FileMethod(new File("/data/data/jsc.cactus.com.weanimal/files/login/"), "login.txt");

        String userData = file.readFile();

        //파일 로딩 ----------------------------------
        if (userData != "") {
            String data[] = userData.split("/");

            Variable.user_id = data[0];
            Variable.user_name = data[1];
            Variable.user_familycode = Integer.parseInt(data[2]);
            Variable.user_birthday = data[3];
            Variable.user_gender = data[4];
            login = true;
        }
        //-------------------------------------------

        Toast.makeText(this, "위애니멀", Toast.LENGTH_SHORT).show();

        Log.i("TEST", "Service Command");

        unregisterRestartAlarm();

        setupConnect();

        connect.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!mSocket.connected()&&!connect_turn) {
                        server_turn = false;
                        Log.i("TEST", "SERVER DOWN");
                        Log.i("TEST", "스레드는 과연 뒤졌을까? " + Boolean.toString(connect.isAlive()));
                        setupConnect();
                        connect.start();
                    }
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        if (login) {
            return Service.START_STICKY;
        } else {
            return Service.START_NOT_STICKY;
        }


    }

    public void setupConnect(){
        connect = new Thread(new Runnable() {
            @Override
            public void run() {
                connect_turn = true;
                do {
                    mSocket.connect();
                    toast("연결시도");

                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (!mSocket.connected()) {
                        toast("연결실패");
                    } else {
                        toast("연결성공");
                        Log.i("TEST", "Socket connect");

                        connect_turn = false;
                        server_turn = true;

                        if (login) {
                            try {
                                sendMessage();
                                Log.i("TEST", "LOGIN OK");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        try {
                            onSocket();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        connect.interrupt();
                    }
                } while (!mSocket.connected());
            }
        });
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
        mSocket.off("RES_CHAT", ChatRecive);
        mSocket.off("RESET", ResetRecive);

        service_turn = false;
        server_turn = false;
        connect_turn = false;

        Toast.makeText(this, "서비스 종료", Toast.LENGTH_SHORT).show();

        Log.i("TEST", "Service Destroy");

        registerRestartAlarm();

        super.onDestroy();

    }

    //소켓 받을 준비
    public void onSocket() throws JSONException {
        mSocket.on("SEND_MSG", Recive);
        mSocket.on("RES_SET", StatusRecive);
        mSocket.on("RES_LEVEL", LevelRecive);
        mSocket.on("RES_CHAT", ChatRecive);
        mSocket.on("RESET", ResetRecive);

        Log.i("TEST", "ON SOCKET");
    }

    //서버에게 방 가입을 요청
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

            new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String ID;
                    long time;

                    try {
                        ID = data.getString("id");
                        time = data.getLong("nowtime");

                        Log.i("TEST", "push");
                        loginPush(ID, time);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        }
    };

    //상태 변화
    private Emitter.Listener StatusRecive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            Log.i("TEST", "StatusRecive");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i("TEST", "RUN!!");

                    JSONObject data = (JSONObject) args[0];
                    int family_love;
                    int family_food;
                    int family_water;
                    int typeI = -1;
                    String send_name = "";
                    String typeII = "";

                    long time;

                    try {
                        family_water = data.getInt("WA");
                        family_food = data.getInt("FO");
                        family_love = data.getInt("LO");

                        send_name = data.getString("name");

                        typeI = data.getInt("TYPE");
                        time = data.getLong("nowtime");

                        Log.i("TEST", Integer.toString(typeI));
                        switch (typeI) {
                            case 0:
                                Log.i("TEST", "이게 출력되면 종현이 병신새끼");
                                break;
                            case 1:
                                typeII = "먹이를";
                                statusPush(send_name, typeII, time);
                                Log.i("TEST", "push");
                                break;
                            case 2:
                                typeII = "물을";
                                statusPush(send_name, typeII, time);
                                break;
                            case 3:
                                typeII = "사랑을";
                                statusPush(send_name, typeII, time);
                                break;
                            default:
                                typeII = "무언가를";
                                statusPush(send_name, typeII, time);
                        }

                        Log.i("TEST", Integer.toString(family_food));

                        if (animal) {
                            Animal.animal.getStatus().setStatus(StatusType.FOOD, family_food);
                            Animal.animal.getStatus().setStatus(StatusType.LOVE, family_love);
                            Animal.animal.getStatus().setStatus(StatusType.WATER, family_water);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        }
    };

    //레벨업
    private Emitter.Listener LevelRecive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int animal_level;

                    long time;

                    Log.i("TEST", "LEVEL!!");

                    try {
                        animal_level = data.getInt("level");
                        time = data.getLong("nowtime");

                        Log.i("TEST", Integer.toString(animal_level));
                        levelupPush(Variable.animal_name, time);

                        Animal.animal.setAge(animal_level);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };

    //채팅
    private Emitter.Listener ChatRecive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    String id;
                    String name;
                    String text;
                    Long time;
                    String day;

                    BufferedWriter bw;

                    try {
                        id = data.getString("ID");
                        name = data.getString("NAME");
                        text = data.getString("TEXT");
                        time = data.getLong("TIME");
                        day = data.getString("DAY");

                        Log.i("TEST", "push");

                        Log.i("TEST", day.split(" ")[0]);
                        Log.i("TEST", day.split(" ")[1]);

//                        bw = new BufferedWriter(new FileWriter("/data/data/jsc.cactus.com.weanimal/files/chat/" + day.split(" ")[0] + ".txt", true));
//
//                        bw.append(" " + time + "|" + name + "|" + text);
//                        bw.newLine();
//
//                        bw.close();
//                        new User(id,name,Variable.user_birthday, UserGender.FEMALE);
                        ChatManager.callChatEvent(new User(id, name, Variable.user_birthday, UserGender.FEMALE), text);

                        chatPush(name, text, time);
                    } catch (Exception e) {
                    }
                }
            }).start();
        }
    };

    //서버 다운
    private Emitter.Listener ResetRecive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    server_turn = false;
                    Log.i("TEST", "SERVER DOWN");
                }
            }).start();
        }
    };


}
