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

public class Have_family_2 extends AppCompatActivity {

    public static Activity ac04_3;

    private EditText id_edit;
    private EditText name_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.have_family_2);

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
        super.onStop();

        MyService.mSocket.off("RESULT", registRecive);
    }

    public void sendMessage() throws JSONException {
        JSONObject data = new JSONObject();

        // perform the user login attempt.
        String msgid = id_edit.getText().toString();
        String msgname = name_edit.getText().toString();
        int msgco = Variable.user_familycode;

        //OftenMethod.message(this, "ID : " + msgid + "\nNAME : " + msgname);

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


                            goin();
                            //Log.i("error", "??");
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

    void goin() {
        Id_query act03 = (Id_query) Id_query.ac03;
        Family_query act04 = (Family_query) Family_query.ac04;
        Have_family_1 act04_2 = (Have_family_1) Have_family_1.ac04_2;

        act03.finish();
        act04.finish();
        act04_2.finish();

        Intent intent = new Intent(this, Set_birthday_gender.class);

        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity04_3, menu);
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
