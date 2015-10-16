package jsc.cactus.com.weanimal.c_login;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import io.socket.emitter.Emitter;
import jsc.cactus.com.weanimal.FileMethod;
import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.e_set.Select_animal;
import jsc.cactus.com.weanimal.e_set.Set_birthday_gender;
import jsc.cactus.com.weanimal.f_list.View_family;
import jsc.cactus.com.weanimal.OftenMethod;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.Variable;

public class Have_id extends AppCompatActivity {

    public static Activity ha_i;

    public static Boolean ha_i_t = false;

    private EditText edit_id;
    private EditText edit_fc;
    private EditText edit_fp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.have_id);

        ha_i = this;

        ha_i_t = true;

        edit_id = (EditText) findViewById(R.id.a3_1_edit_id);
        edit_fc = (EditText) findViewById(R.id.a3_1_edit_familycode);
        edit_fp = (EditText) findViewById(R.id.a3_1_edit_familypassword);

        Button btn_ok = (Button) findViewById(R.id.a3_1_btn_login);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edit_id.getText().toString().equals("")) {
                    OftenMethod.message(Have_id.this, "ID를 알려주세요.");
                } else {
                    if (edit_fc.getText().toString().equals("")) {
                        OftenMethod.message(Have_id.this, "가족코드를 알려주세요.");
                    } else {
                        if (edit_fp.getText().toString().equals("")) {
                            OftenMethod.message(Have_id.this, "비밀번호를 알려주세요.");
                        } else {
                            try {
                                sendMessage();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        ha_i_t = false;

        MyService.mSocket.off("OK", loginRecive);
    }

    public void sendMessage() throws JSONException {
        JSONObject data = new JSONObject();

        String msgid = edit_id.getText().toString();
        int msgfc = Integer.valueOf(edit_fc.getText().toString());
        String msgfp = edit_fp.getText().toString();

        data.put("ID", msgid);
        data.put("FC", msgfc);
        data.put("FP", msgfp);

        Variable.user_id = msgid;
        Variable.user_familycode = msgfc;

        MyService.mSocket.emit("LOGIN", data);
        MyService.mSocket.on("OK", loginRecive);
    }

    private Emitter.Listener loginRecive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int CH;

                    try {
                        CH = data.getInt("CH");
                        switch (CH) {
                            case 1:
                                Variable.user_id = data.getString("id");
                                Variable.user_name = data.getString("name");
                                Variable.user_familycode = data.getInt("familycode");
                                Variable.user_gender = data.getString("gender");
                                Variable.user_birthday = data.getString("birth");

                                FileMethod file = new FileMethod(new File("/data/data/jsc.cactus.com.weanimal/files/login/"), "login.txt");

                                file.writeFile("@" + Variable.user_id + "/" + Variable.user_name + "/" + Variable.user_familycode + "/" + Variable.user_birthday + "/" + Variable.user_gender);
                                Log.i("TEST", file.readFile());

                                Log.i("TEST", Have_id.this.getFilesDir().toString());

                                //  "/data/data/jsc.cactus.com.weanimal/files"

                                goin(View_family.class);
                                MyService.login = true;
                                finish();

                                break;
                            case -3:
                                OftenMethod.message(Have_id.this, "비밀번호를 다시 생각해보세요.");
                                break;
                            case -1:
                                OftenMethod.message(Have_id.this, "존재하지 않는 가족코드입니다.");
                                break;
                            case 0:
                                OftenMethod.message(Have_id.this, "아이디를 다시 생각해보세요.");
                                break;
                            case -2:
                                OftenMethod.message(Have_id.this, "가족코드를 다시 생각해보세요.");
                                break;
                            case -4:
                                OftenMethod.message(Have_id.this, "이미 접속종인 아이디입니다.");
                                break;
                            case -5:
                                OftenMethod.message(Have_id.this, "성별과 생년월일을 모르겠습니다.");

                                Variable.user_id = data.getString("id");
                                Variable.user_name = data.getString("name");
                                Variable.user_familycode = data.getInt("familycode");

                                goin(Set_birthday_gender.class);
                                MyService.login = true;
                                finish();

                                break;
                            case -6:
                                OftenMethod.message(Have_id.this, "동물을 분양 받아주세요.");

                                Variable.user_id = data.getString("id");
                                Variable.user_name = data.getString("name");
                                Variable.user_familycode = data.getInt("familycode");

                                goin(Select_animal.class);
                                MyService.login = true;
                                finish();

                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    void goin(Class go) {
        Id_query id_q_s = (Id_query) Id_query.id_q;
        id_q_s.finish();
        Intent intent = new Intent(this, go);
        startActivity(intent);
    }
}
