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
import jsc.cactus.com.weanimal.OftenMethod;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.Variable;

public class Have_family_1 extends Activity {

    public static Activity ha_fa_1;

    public static Boolean ha_fa_1_t = false;

    private EditText edit_code;
    private EditText edit_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.have_family_1);

        ha_fa_1 = Have_family_1.this;

        ha_fa_1_t = true;

        edit_code = (EditText) findViewById(R.id.a4_2_edit_name);
        edit_pass = (EditText) findViewById(R.id.a4_2_edit_password);
        Button btn_check = (Button) findViewById(R.id.a4_2_btn_check);

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edit_code.getText().toString().equals("")) {
                    OftenMethod.message(Have_family_1.this, "가족코드를 알려주세요.");
                } else {
                    if (edit_pass.getText().toString().equals("")) {
                        OftenMethod.message(Have_family_1.this, "비밀번호를 알려주세요.");
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
        ha_fa_1_t = false;

        MyService.mSocket.off("OK", floginRecive);

        super.onStop();
    }

    public void sendMessage() throws JSONException {
        JSONObject data = new JSONObject();

        int msgcode = Integer.valueOf(edit_code.getText().toString());

        String msgpass = edit_pass.getText().toString();

        data.put("CO", msgcode);
        data.put("PA", msgpass);

        Variable.user_familycode = msgcode;

        MyService.mSocket.emit("FLOGIN", data);
        MyService.mSocket.on("OK", floginRecive);
    }

    private Emitter.Listener floginRecive = new Emitter.Listener() {
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
                            case -2:
                                OftenMethod.message(Have_family_1.this, "그 가족은 동물이 없습니다.");
                                OftenMethod.message(Have_family_1.this, "가장이 로그인하여 동물을 골라주세요.");
                                break;

                            case -1:
                                OftenMethod.message(Have_family_1.this, "존재하지 않는 가족코드입니다.");
                                break;

                            case 0:
                                OftenMethod.message(Have_family_1.this, "비밀번호가 틀렸습니다.");
                                break;

                            case 1:
                                goin(Have_family_2.class);
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
        Family_query fa_q_s = (Family_query) Family_query.fa_q;
        fa_q_s.finish();

        Intent intent = new Intent(this, go);

        startActivity(intent);
        finish();
    }
}
