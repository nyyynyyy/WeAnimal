package jsc.cactus.com.weanimal.g_animal.main.main.weanimal;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;


import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.socket.emitter.Emitter;
import jsc.cactus.com.weanimal.FileMethod;
import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.Variable;
import jsc.cactus.com.weanimal.f_list.View_family;
import jsc.cactus.com.weanimal.g_animal.main.DateFormat;

import jsc.cactus.com.weanimal.g_animal.main.SoundUtil;
import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;
import jsc.cactus.com.weanimal.g_animal.main.animal.AnimalType;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.Status;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.StatusType;
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

    public static MainActivity mainActivity;
    public static Activity animal_hill_a;
    public static Boolean animal_hill_t = false;

    private ChatViewManager familyChatViewManager;
    private MissionViewManager missionViewManager;
    private Animal animal;
    private UserManager userManager;
    private SettingManager settingManager;

    private LinearLayout animal_hill;
    private ImageView set;

    private boolean exit = false;

    private Long missionTime = 0L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        animal_hill_t = true;

        animal_hill_a = this;

        setContentView(R.layout.activity_main);


        // 시간에 따른 배경 설정
        animal_hill = (LinearLayout) findViewById(R.id.AnimalHill);

        setBackground();
        //

        //setting();

        try {
            getStatus();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        init();
    }

   // private void setting() {
    /*private void setting() {
>>>>>>> origin/master
        set = (ImageView) findViewById(R.id.btn_setting);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendLogout();
                clearFile();
                finish();
                MyService.login = false;
            }
        });
    }*/

    public void sendLogout() {
        MyService.mSocket.emit("LOGOUT");
    }

    public void clearFile() {
        file = new FileMethod(new File("/data/data/jsc.cactus.com.weanimal/files/login/"), "login.txt");
        FileMethod file = new FileMethod(new File("/data/data/jsc.cactus.com.weanimal/files/login/"), "login.txt");

        file.getFile().delete();
        Log.i("TEST", "CLEAR FILE");

        //  "/data/data/jsc.cactus.com.weanimal/files"
    }

    public long getLastTime() {
        String day = null;

        File files[] = new File("/data/data/jsc.cactus.com.weanimal/files/chat/").listFiles();
        File f = files[files.length - 1];

        day = f.getName();

        file = new FileMethod(new File("/data/data/jsc.cactus.com.weanimal/files/chat/"), day);

        String lastLine = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            List<String> stringList = new ArrayList<String>();

            while (br.readLine() != null)
                stringList.add(br.readLine());

            lastLine = stringList.get(stringList.size() - 2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return DateFormat.parseDate(lastLine.split("\\|")[0], DateFormat.Type.SECOND).getTime();
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "백프레스", Toast.LENGTH_SHORT).show();
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

        if (Variable.morning(hour))
            animal_hill.setBackgroundResource(R.drawable.background_evening);
        if (Variable.daytime(hour))
            animal_hill.setBackgroundResource(R.drawable.background_morning);
        if (Variable.evening(hour))
            animal_hill.setBackgroundResource(R.drawable.background_evening);
        if (Variable.night(hour))
            animal_hill.setBackgroundResource(R.drawable.background_night);
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

        long time = getLastTime();

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
                        status.setStatus(StatusType.FOOD, FOOD);
                        status.setStatus(StatusType.WATER, WATER);
                        status.setStatus(StatusType.LOVE, LOVE);

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
                    int length;

                    try {
                        chat_logs = data.getJSONArray("chat_logs");
                        length = data.getInt("length");



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
        userManager = new UserManager(new User(Variable.user_id, Variable.user_name, Variable.user_birthday, UserGender.MALE));
        familyChatViewManager = new ChatViewManager(this);
        settingManager = new SettingManager(this);

        MissionManager.instance.addMissionListener(this);


    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(String.format("Time : %.2f", (float) ((float) (System.currentTimeMillis() - missionTime) / 1000F)));
        }
    };

    //미션에 대한 이벤트


    @Override
    public void onResume() {
        super.onResume();
        if (missionTime != null) {
            MissionManager.instance.clearMission();
        }
    }


    @Override
    public void startMission(Mission mission) {
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
    public void clearMission(Mission mission) {
        if (!(mission instanceof TelMission))
            return;

        boolean b = ((new Date().getTime() - missionTime) / 1000) < ((TelMission) mission).second;
        Toast.makeText(MainActivity.mainActivity, b ? "전화 미션 실패.." : "전화 미션 성공 !!", Toast.LENGTH_SHORT).show();
        SoundUtil.playSound(b ? R.raw.bass : R.raw.pong);
        if (!b)
            Animal.animal.getStatus().addStatus(StatusType.LOVE, 100);
        missionTime = null;
    }

    @Override
    public void giveupMission(Mission mission) {
        missionTime = null;
    }
}
