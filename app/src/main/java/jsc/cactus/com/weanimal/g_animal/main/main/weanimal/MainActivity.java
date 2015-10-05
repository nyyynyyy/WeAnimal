package jsc.cactus.com.weanimal.g_animal.main.main.weanimal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;
import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.Variable;
import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;
import jsc.cactus.com.weanimal.g_animal.main.animal.AnimalType;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.Status;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.StatusType;
import jsc.cactus.com.weanimal.g_animal.main.familychat.FamilyChatShowManager;
import jsc.cactus.com.weanimal.g_animal.main.users.User;
import jsc.cactus.com.weanimal.g_animal.main.users.UserGender;
import jsc.cactus.com.weanimal.g_animal.main.users.UserManager;

/**
 * Created by INSI on 15. 9. 23..
 */
public class MainActivity extends AppCompatActivity {

    long missionTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            getStatus();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        init();
    }

    @Override
    public void onStop(){
        super.onStop();
        MyService.mSocket.off("RES_STATUS",statusRecive);
    }

    private void getStatus() throws JSONException {
        sendMessage();
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
                        FOOD  = STATUS.getInt("feed");
                        WATER = STATUS.getInt("thirst");
                        LOVE = STATUS.getInt("love");

                        Log.i("TEST", "RES_STATUS");
                        Log.i("TEST", Integer.toString(FOOD));
                        Log.i("TEST", Integer.toString(WATER));
                        Log.i("TEST", Integer.toString(LOVE));


                        Animal animal = Animal.animal;
                        animal.setAge(LEVEL);
                        animal.setName(NAME);
                        animal.setType(AnimalType.getType(TYPE));

                        Status status = Animal.animal.getStatus();
                        status.setStatus(StatusType.FOOD, FOOD);
                        status.setStatus(StatusType.WATER, WATER);
                        status.setStatus(StatusType.LOVE, LOVE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    };

    public void init() {

        new FamilyChatShowManager(this);
        new Animal(this);
        new UserManager(new User(Variable.user_id , Variable.user_name , Variable.user_birthday, UserGender.MALE));

        Button test = (Button) findViewById(R.id.missionButton);

        test.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-2975-7544"));
                        startActivity(callIntent);
                    }
                });
    }

    /*public void onPause() {
        missionTime = System.currentTimeMillis();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        handler.sendMessage(Message.obtain());
    }*/

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(String.format("Time : %.2f", (float) ((float) (System.currentTimeMillis() - missionTime) / 1000F)));
        }
    };
}