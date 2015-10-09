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

public class Havenot_family extends AppCompatActivity {

    public static Activity ac04_1;

    private EditText name_ed;
    private EditText id_ed;
    private EditText fp_ed;
    private EditText fpt_ed;

    private String nextID;
    private String nextPW;
    private int nextFN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.havenot_family);

        name_ed = (EditText) findViewById(R.id.a4_1_edit_name);
        id_ed = (EditText) findViewById(R.id.a4_1_edit_id);
        fp_ed = (EditText) findViewById(R.id.a4_1_edit_password);
        fpt_ed = (EditText) findViewById(R.id.a4_1_edit_passwordcheck);
        Button resBtn = (Button) findViewById(R.id.a4_1_btn_regist);

        resBtn.setOnClickListener(new View.OnClickListener() {
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
                                                                      if(fp_ed.getText().length() >= 4) {
                                                                          try {
                                                                              sendMessage();
                                                                          } catch (JSONException e) {
                                                                              e.printStackTrace();
                                                                          }
                                                                      }
                                                                      else{
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
    public void onStop() {
        MyService.mSocket.off("RESULT", fregistRecive);
        super.onStop();
    }

    void goin() {
        Intent intent = new Intent(this, Complete_regist.class);

        Bundle bundle = new Bundle();
        bundle.putString("ID", nextID);
        bundle.putString("PW", nextPW);
        bundle.putInt("FN", nextFN);

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

        nextID = msgId;
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

                            Id_query act03 = (Id_query) Id_query.ac03;
                            Family_query act04 = (Family_query) Family_query.ac04;

                            act03.finish();
                            act04.finish();

                            goin();
                            finish();

                        } else {
                            OftenMethod.message(Havenot_family.this,"누군가가 사용하고 있는 아이디입니다.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity04_1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
