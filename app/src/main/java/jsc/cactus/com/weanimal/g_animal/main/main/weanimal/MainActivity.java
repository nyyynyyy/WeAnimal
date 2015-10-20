package jsc.cactus.com.weanimal.g_animal.main.main.weanimal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;


import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.socket.emitter.Emitter;
import jsc.cactus.com.weanimal.FileMethod;
import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.Variable;
import jsc.cactus.com.weanimal.g_animal.main.DateFormat;

import jsc.cactus.com.weanimal.g_animal.main.SoundUtil;
import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;
import jsc.cactus.com.weanimal.g_animal.main.animal.AnimalType;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.Status;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.StatusType;
import jsc.cactus.com.weanimal.g_animal.main.familychat.ChatManager;
import jsc.cactus.com.weanimal.g_animal.main.familychat.view.ChatViewManager;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.Mission;
import jsc.cactus.com.weanimal.g_animal.main.mission.MissionListener;
import jsc.cactus.com.weanimal.g_animal.main.mission.MissionManager;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.TelMission;
import jsc.cactus.com.weanimal.g_animal.main.mission.view.MissionViewManager;
import jsc.cactus.com.weanimal.g_animal.main.settting.SettingManager;
import jsc.cactus.com.weanimal.g_animal.main.users.User;
import jsc.cactus.com.weanimal.g_animal.main.users.UserGender;
import jsc.cactus.com.weanimal.g_animal.main.users.UserManager;

/**
 * Created by INSI on 15. 9. 23..
 */
public class MainActivity extends AppCompatActivity implements MissionListener {

    private FileMethod file;
    private File filesDir;

    public static MainActivity mainActivity;
    public static Activity animal_hill_a;
    public static Boolean animal_hill_t = false;

    private ChatViewManager familyChatViewManager;
    private MissionViewManager missionViewManager;
    private Animal animal;
    private UserManager userManager;
    private SettingManager settingManager;

    private LinearLayout animal_hill;
    private ImageView sign;
    private ImageView set;

    private TextView txt_name;
    private TextView txt_id;
    private TextView txt_family_number;

    private boolean exit = false;
    private boolean run = false;

    private Long missionTime = 0L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filesDir = getFilesDir();

        animal_hill_t = true;

        animal_hill_a = this;

        setContentView(R.layout.activity_main);

        txt_name = (TextView) findViewById(R.id.txt_myname);
        txt_id = (TextView) findViewById(R.id.txt_myid);
        txt_family_number = (TextView) findViewById(R.id.txt_familynumber);

        txt_name.setText(Variable.user_name);
        txt_id.setText(Variable.user_id);
        txt_family_number.setText(Integer.toString(Variable.user_familycode));


        // 시간에 따른 배경 설정
        animal_hill = (LinearLayout) findViewById(R.id.AnimalHill);
        sign = (ImageView) findViewById(R.id.imageView);

        setBackground();

        init();

    }

    public void sendLogout() {
        MyService.mSocket.emit("LOGOUT");
    }

    public void clearFile() {
        file = new FileMethod(new File(filesDir + "/login/"), "login.txt");
        FileMethod file = new FileMethod(new File(filesDir + "/login/"), "login.txt");

        file.getFile().delete();
        Log.i("TEST", "CLEAR FILE");

        //  "/data/data/jsc.cactus.com.weanimal/files"
    }


    public long getLastTime() {
        String day = null;

        File files[] = new File(filesDir + "/chat/").listFiles();
        File f = files[files.length - 1];

        day = f.getName();

        Log.i("jsc", day);
        //file = new FileMethod(new File(filesDir+"/chat/"), day);

        String lastLine = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            List<String> stringList = new ArrayList<String>();

            String s;
            while ((s = br.readLine()) != null) {
                Log.i("TEST", "enter while: " + s);
                stringList.add(s);
            }

            lastLine = stringList.get(stringList.size() - 1);
        } catch (Exception e) {
            Log.i("jsc", e.getMessage());
            return -1;
        }

        return Long.parseLong(lastLine.split("\\|")[0].replace(" ", ""));
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            exit = true;
        }
    }

    @Override
    public void onStop() {
        animal_hill_t = false;

        MyService.mSocket.off("RES_STATUS", statusRecive);
        super.onStop();
    }

    private void getStatus() throws JSONException {
        sendMessage();
    }

    private void setBackground() {
        int hour = new Date().getHours();

        if (Variable.morning(hour)) {
            animal_hill.setBackgroundResource(R.drawable.background_evening);
            sign.setImageResource(R.drawable.sign_evening);
        } else if (Variable.daytime(hour)) {
            animal_hill.setBackgroundResource(R.drawable.background_morning);
            sign.setImageResource(R.drawable.sign_day);
        } else if (Variable.evening(hour)) {
            animal_hill.setBackgroundResource(R.drawable.background_evening);
            sign.setImageResource(R.drawable.sign_evening);
        } else if (Variable.night(hour)) {
            animal_hill.setBackgroundResource(R.drawable.background_night);
            sign.setImageResource(R.drawable.sign_night);
        }
    }

    public void sendMessage() throws JSONException {
        JSONObject data = new JSONObject();

        // perform the user login attempt.
        int msgcode = Variable.user_familycode;

        data.put("CO", msgcode);

        Variable.user_familycode = msgcode;

        MyService.mSocket.emit("GETSTATUS", data);
        MyService.mSocket.on("RES_STATUS", statusRecive);
    }

    public void sendTime() throws JSONException {
        JSONObject data = new JSONObject();

        long time;

        if (new File(filesDir + "/chat/").listFiles() != null) {
            time = getLastTime();
        } else {
            time = -1;
        }

        data.put("LAST_TIME", time);

        MyService.mSocket.emit("UPDATE_CHAT", data);
        MyService.mSocket.on("RES_UPDATE_CHAT", chRecive);
    }

    private Emitter.Listener statusRecive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    String NAME;
                    String TYPE;
                    int LEVEL;
                    int FOOD;
                    int WATER;
                    int LOVE;

                    try {
                        JSONObject STATUS = data.getJSONObject("status");
                        NAME = data.getString("name");
                        TYPE = data.getString("type");
                        LEVEL = data.getInt("level");
                        FOOD = STATUS.getInt("feed");
                        WATER = STATUS.getInt("thirst");
                        LOVE = STATUS.getInt("love");

                        Log.i("TEST", "RES_STATUS");
                        Log.i("TEST", Integer.toString(FOOD));
                        Log.i("TEST", Integer.toString(WATER));
                        Log.i("TEST", Integer.toString(LOVE));

                        Animal animal = Animal.animal;
                        animal.setAge(LEVEL);
                        animal.setName(NAME);
                        animal.setType(AnimalType.valueOf(TYPE));

                        Status status = Animal.animal.getStatus();
                        status.setStatus(FOOD, WATER, LOVE);

                        animal.setReady(true);

                        Log.i("TEST", AnimalType.valueOf(TYPE).toString());

                        findViewById(R.id.AnimalHill).setVisibility(View.VISIBLE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    };

    private Emitter.Listener chRecive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    JSONArray chat_logs;

                    try {
                        chat_logs = data.getJSONArray("chat_logs");

                        Log.i("TEST", "" + chat_logs.length());

                        for (int i = 0; i < chat_logs.length(); i++) {
                            // Log.i("TEST", "인덱스 " + i);
                            JSONObject chatObject = chat_logs.getJSONObject(i);

                            try {
                                Date date = new Date();
                                date.setTime(chatObject.getLong("time"));

                                Log.i("TEST", "Filename: " + filesDir + "/chat/" + DateFormat.formatDate(date, DateFormat.Type.DAY) + ".txt");

                                Log.i("TEST", "들어온 채팅내역: " + DateFormat.formatDate(date, DateFormat.Type.SECOND) + "|" + chatObject.getString("username") + "|" + chatObject.getString("msg"));

                                ChatManager.callChatEvent(UserManager.getUser(chatObject.getString("userid")), chatObject.getString("msg"), new Date(chatObject.getLong("time")));

                            } catch (Exception ioex) {
                                ioex.printStackTrace();
                            }
                        }

                        Log.i("TEST", "for문 끝남");
                        run = true;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    };

    public void init() {

        mainActivity = this;
        new MissionManager(this);
        missionViewManager = new MissionViewManager(this);
        animal = new Animal(this);
        MyService.animal = true;
        Log.i("jsc", "베이어블 :" + Variable.user_name);
        userManager = new UserManager(new User(Variable.user_id, Variable.user_name, Variable.user_birthday, UserGender.MALE, Variable.user_phonenumber));
        familyChatViewManager = new ChatViewManager(this);
        settingManager = new SettingManager(this);

        MissionManager.instance.addMissionListener(this);

    }

    public void init2() {
        try {
            getStatus();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            sendTime();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //미션에 대한 이벤트


    @Override
    public void onResume() {
        super.onResume();
        if (missionTime != null) {
            MissionManager.instance.clearMission();
        }
    }


    @Override
    public void startMission(Mission mission, StatusType type) {
        if (!(mission instanceof TelMission))
            return;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-2975-7544"));
        try {
            startActivity(intent);
        } catch (Exception ex) {
        }
        missionTime = new Date().getTime();
    }

    @Override
    public void clearMission(Mission mission, StatusType type) {
        if (!(mission instanceof TelMission))
            return;

        boolean b = ((new Date().getTime() - missionTime) / 1000) < ((TelMission) mission).second;
        Toast.makeText(MainActivity.mainActivity, type.toKoreanString() + " 주기\n" + (b ? "전화 미션 실패.." : "전화 미션 성공 !!"), Toast.LENGTH_SHORT).show();
        SoundUtil.playSound(b ? R.raw.bass : R.raw.pong);
        if (!b)
            Animal.animal.getStatus().addStatus(type, 100);
        missionTime = null;
    }

    @Override
    public void giveupMission(Mission mission) {
        missionTime = null;
    }

    /*public class ChatData {
        private int familyCode;
        private String userid;
        private long time;
        private String msg;

        public ChatData(int familyCode, String userid, long time, String msg) {
            this.familyCode = familyCode;
            this.userid = userid;
            this.time = time;
            this.msg = msg;
        }

        public int getFamilyCode() {
            return this.familyCode;
        }

        public String getUserId() {
            return this.userid;
        }

        public long getTime() {
            return this.time;
        }

        public String getMSG() {
            return this.msg;
        }

        public void setFamilyCode(int familyCode) {
            this.familyCode = familyCode;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public void setMSG(String msg) {
            this.msg = msg;
        }
    }*/
}
