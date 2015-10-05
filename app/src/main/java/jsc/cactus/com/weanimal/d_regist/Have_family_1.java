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

public class Have_family_1 extends AppCompatActivity {

    public static Activity ac04_2;

    private EditText edit_code;
    private EditText edit_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.have_family_1);

        ac04_2 = Have_family_1.this;

        edit_code = (EditText) findViewById(R.id.a4_2_edit_name);
        edit_pass = (EditText) findViewById(R.id.a4_2_edit_password);
        Button btn_check = (Button) findViewById(R.id.a4_2_btn_check);

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edit_code.getText().toString().equals("")) {
                OftenMethod.message(Have_family_1.this, "가족코드를 알려주세요.");
                }
                else
                {
                    if(edit_pass.getText().toString().equals("")){
                        OftenMethod.message(Have_family_1.this , "비밀번호를 알려주세요.");
                    }
                    else
                    {
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

        MyService.mSocket.off("OK", floginRecive);
    }

    public void sendMessage() throws JSONException {
        JSONObject data = new JSONObject();

        // perform the user login attempt.
        int msgcode = Integer.valueOf(edit_code.getText().toString());

        String msgpass = edit_pass.getText().toString();

        //OftenMethod.message(this, "CODE : " + msgcode + "\nPASS : " + msgpass);

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
                            case -1:
                                OftenMethod.message(Have_family_1.this, "존재하지 않는 가족코드입니다.");
                                break;

                            case 0:
                                OftenMethod.message(Have_family_1.this, "비밀번호가 틀렸습니다.");
                                break;

                            case 1:
                                Family_query act03 = (Family_query) Family_query.ac04;
                                act03.finish();
                                goin();
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

    void goin() {
        Intent intent = new Intent(this, Have_family_2.class);

        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity04_2, menu);
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
