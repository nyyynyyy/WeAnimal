package jsc.cactus.com.weanimal.c_login;

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
import jsc.cactus.com.weanimal.f_list.View_family;
import jsc.cactus.com.weanimal.OftenMethod;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.Variable;

public class Have_id extends AppCompatActivity {

    private EditText edit_id;
    private EditText edit_fc;
    private EditText edit_fp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.have_id);

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

        MyService.mSocket.off("OK", loginRecive);
    }

    public void sendMessage() throws JSONException {
        JSONObject data = new JSONObject();

        // perform the user login attempt.
        String msgid = edit_id.getText().toString();
        int msgfc = Integer.valueOf(edit_fc.getText().toString());
        String msgfp = edit_fp.getText().toString();

        // OftenMethod.message(this, "CODE : " + msgcode + "\nPASS : " + msgpass);

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
                    String test = "C" + "H";

                    try {
                        CH = data.getInt(test);
                        switch (CH) {
                            case 1:
                                if(!View_family.on_ac) {
                                    Variable.user_id = data.getString("id");
                                    Variable.user_name = data.getString("name");
                                    Variable.user_familycode= data.getInt("familycode");
                                    Variable.user_gender = data.getString("gender");
                                    Variable.user_birthday = data.getString("birth");

                                    FileMethod file = new FileMethod(new File("/data/data/jsc.cactus.com.weanimal/files/login/"),"login.txt");

                                    file.writeFile("@" + Variable.user_id + "/" + Variable.user_name + "/" + Variable.user_familycode + "/" + Variable.user_birthday + "/" + Variable.user_gender);
                                    Log.i("TEST", file.readFile());

                                    Log.i("TEST", Have_id.this.getFilesDir().toString());

                                    //  "/data/data/jsc.cactus.com.weanimal/files"

                                    goin();
                                    MyService.login = true;
                                    finish();
                                }
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
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    void goin() {
        Id_query act03 = (Id_query) Id_query.ac03;
        act03.finish();
        Intent intent = new Intent(this, View_family.class);
        startActivity(intent);
    }

    public void push(int id, String msg, Long time) {
        OftenMethod.LoginNoti(id, this, msg, (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE), time);
    }
}
