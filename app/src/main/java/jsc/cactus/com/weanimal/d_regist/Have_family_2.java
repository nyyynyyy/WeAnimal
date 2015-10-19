package jsc.cactus.com.weanimal.d_regist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;
import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.e_set.Set_birthday_gender;
import jsc.cactus.com.weanimal.OftenMethod;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.Variable;
import jsc.cactus.com.weanimal.c_login.Id_query;

public class Have_family_2 extends Activity {

    public static Activity ha_fa_2;

    public static Boolean ha_fa_2_t = false;

    private EditText id_edit;
    private EditText name_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.have_family_2);

        ha_fa_2 = Have_family_2.this;

        ha_fa_2_t = true;

        id_edit = (EditText) findViewById(R.id.a4_3_edit_id);
        name_edit = (EditText) findViewById(R.id.a4_3_edit_name);
        Button btn_check = (Button) findViewById(R.id.a4_3_btn_check);

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id_edit.getText().toString().equals("")) {
                    OftenMethod.message(Have_family_2.this, "ID를 알려주세요.");
                } else {
                    if (name_edit.getText().toString().equals("")) {
                        OftenMethod.message(Have_family_2.this, "이름을 알려주세요.");
                    } else {
                        try {
                            sendMessage();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onStop() {
        ha_fa_2_t = false;

        MyService.mSocket.off("RESULT", registRecive);

        super.onStop();
    }

    public void sendMessage() throws JSONException {
        JSONObject data = new JSONObject();

        String msgid = id_edit.getText().toString();
        String msgname = name_edit.getText().toString();
        int msgco = Variable.user_familycode;

        data.put("ID", msgid);
        data.put("NA", msgname);
        data.put("CO", msgco);

        Variable.user_id = msgid;
        Variable.user_name = msgname;

        MyService.mSocket.emit("REGIST", data);
        MyService.mSocket.on("RESULT", registRecive);


    }

    private Emitter.Listener registRecive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    boolean CH;

                    try {
                        CH = data.getBoolean("success");

                        if (CH == true) {
                            goin(Set_birthday_gender.class);
                            finish();
                            MyService.mSocket.off("RESULT", registRecive);
                        } else if (CH == false) {
                            OftenMethod.message(Have_family_2.this, "다른 사람이 사용하고 있는 아이디입니다. 다른 아이디를 사용해주세요.");
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
        Family_query fa_q_s = (Family_query) Family_query.fa_q;
        Have_family_1 ha_fa_1 = (Have_family_1) Have_family_1.ha_fa_1;

        id_q_s.finish();
        fa_q_s.finish();
        ha_fa_1.finish();

        Intent intent = new Intent(this, go);

        startActivity(intent);
        finish();
    }
}
