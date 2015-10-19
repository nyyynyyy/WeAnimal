package jsc.cactus.com.weanimal.d_regist;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;
import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.e_set.Complete_regist;
import jsc.cactus.com.weanimal.OftenMethod;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.Variable;
import jsc.cactus.com.weanimal.c_login.Id_query;

public class Havenot_family extends Activity {

    public static Activity han_fa;

    public static Boolean han_fa_t = false;

    private EditText name_ed;
    private EditText id_ed;
    private EditText fp_ed;
    private EditText fpt_ed;

    private String nextPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.havenot_family);

        han_fa = Havenot_family.this;

        han_fa_t = true;

        name_ed = (EditText) findViewById(R.id.a4_1_edit_name);
        id_ed = (EditText) findViewById(R.id.a4_1_edit_id);
        fp_ed = (EditText) findViewById(R.id.a4_1_edit_password);
        fpt_ed = (EditText) findViewById(R.id.a4_1_edit_passwordcheck);
        Button resBtn = (Button) findViewById(R.id.a4_1_btn_regist);

        resBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (name_ed.getText().toString().contains(" ")) {
                            OftenMethod.message(Havenot_family.this, "이름에 공백이 포함되어 있습니다.");
                        } else {
                            if (id_ed.getText().toString().contains(" ")) {
                                OftenMethod.message(Havenot_family.this, "아이디에 공백이 포함되어 있습니다.");
                            } else {
                                if (fp_ed.getText().toString().contains(" ")) {
                                    OftenMethod.message(Havenot_family.this, "비밀번호에 공백이 포함되어 있습니다.");
                                } else {
                                    if (fp_ed.getText().toString().equals(fpt_ed.getText().toString())) {
                                        if (name_ed.getText().toString().equals("")) {
                                            OftenMethod.message(Havenot_family.this, "이름을 알려주세요.");
                                        } else {
                                            if (id_ed.getText().toString().equals("")) {
                                                OftenMethod.message(Havenot_family.this, "아이디를 알려주세요.");
                                            } else {
                                                if (fp_ed.getText().toString().equals("")) {
                                                    OftenMethod.message(Havenot_family.this, "비밀번호를 알려주세요.");
                                                } else {
                                                    if (fp_ed.getText().length() >= 4) {
                                                        try {
                                                            sendMessage();
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    } else {
                                                        OftenMethod.message(Havenot_family.this, "비밀번호가 너무 짧아요.");
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        OftenMethod.message(Havenot_family.this, "비밀번호가 다릅니다.");
                                    }
                                }
                            }
                        }
                    }
                }
        );
    }

    @Override
    protected void onStop() {
        han_fa_t = false;

        MyService.mSocket.off("RESULT", fregistRecive);

        super.onStop();
    }

    void goin(Class go) {
        Id_query id_q_s = (Id_query) Id_query.id_q;
        Family_query fa_q_s = (Family_query) Family_query.fa_q;

        id_q_s.finish();
        fa_q_s.finish();

        Intent intent = new Intent(this, go);

        Bundle bundle = new Bundle();
        bundle.putString("PW", nextPW);

        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    public void sendMessage() throws JSONException {
        JSONObject data = new JSONObject();

        // perform the user login attempt.
        String msgId = id_ed.getText().toString();
        String msgname = name_ed.getText().toString();
        String msgfp = fp_ed.getText().toString();

        nextPW = msgfp;

        data.put("ID", msgId);
        data.put("NA", msgname);
        data.put("PW", msgfp);

        Variable.user_id = msgId;
        Variable.user_name = msgname;

        MyService.mSocket.emit("FREGIST", data);
        MyService.mSocket.on("RESULT", fregistRecive);
    }

    private Emitter.Listener fregistRecive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    boolean CH;
                    int FN;

                    try {
                        CH = data.getBoolean("success");
                        FN = data.getInt("familycode");
                        if (CH == true) {
                            Variable.user_familycode = FN;

                            goin(Complete_regist.class);
                        } else {
                            OftenMethod.message(Havenot_family.this, "누군가가 사용하고 있는 아이디입니다.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}
