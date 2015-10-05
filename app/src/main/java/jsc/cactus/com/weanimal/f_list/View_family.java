package jsc.cactus.com.weanimal.f_list;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;
import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.OftenMethod;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.Variable;
import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;

public class View_family extends AppCompatActivity {

    public static boolean on_ac = false;

    private Family_ListViewAdapter Adapter;

    public int familyMember = 1;

    private ListView list;

    private Button acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_family);

        on_ac = true;

        try {
            codeSendMessage();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        acceptButton = (Button) findViewById(R.id.button);

        Adapter = new Family_ListViewAdapter(this, android.R.layout.activity_list_item);

        list = (ListView) findViewById(R.id.a6_list_family);
        list.setAdapter(Adapter);

        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        list.setDivider(new ColorDrawable(Color.WHITE));

        list.setDividerHeight(2);



        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(View_family.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();

        MyService.mSocket.off("ME", memberRecive);
    }

    public void codeSendMessage() throws JSONException {
        JSONObject data = new JSONObject();

        // perform the user login attempt.
        int msg = Variable.user_familycode;

        data.put("CO", msg);

        MyService.mSocket.emit("NU", data);

        MyService.mSocket.on("ME", memberRecive);
    }

    private Emitter.Listener memberRecive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int FM;
                    String OI;

                    try {
                        OI = data.getString("ownerid");

                        if (data.has("cnt")) {
                            FM = data.getInt("cnt");
                            familyMember = FM;
                        } else {
                            OftenMethod.message(View_family.this, "당신의 가족을 찾을 수 없소이다.");
                        }

                        OftenMethod.message(View_family.this, "당신의 가족원은 " + Integer.toString(familyMember) + "명 입니다.");
                        for (int i = 0; i < familyMember; i++) {
                            JSONArray Member;
                            Member = data.getJSONArray("members");

                            JSONObject fam = Member.getJSONObject(i);
                            String fid = fam.getString("id");
                            String fna = fam.getString("name");
                            String fge = fam.getString("gender");
                            String fbd = fam.getString("birth");

                            if (!(fid.equals(OI))) {
                                Adapter.add(new Family_Item(fna, fid + "\n" + fge + "\n" + fbd));
                            }
                            // family.add(fid + "\n" + fna + "\n" + fge + "\n" + fbd);
                            else {
                                Adapter.insert(new Family_Item("[PRESIDENT] " + fna,fid + "\n" + fge + "\n" + fbd),0);
                            }
                            // family.set(0, family.get(0).toString() + "\n" + fna + "\n" + fge + "\n" + fbd);
                            list.setAdapter(Adapter);

                           // push(i + 1, fam.getString("id") + "님께서 로그인 하셨습니다.");


                        }

                        Log.i("sss", "try!");
                        OftenMethod.message(View_family.this, "가족을 불러옵니다.");

                        MyService.mSocket.off("ME", memberRecive);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("sss", e.toString());
                        OftenMethod.message(View_family.this, "오류 발생 !!!");
                    }
                }
            });
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity06, menu);
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

    public void push(int id, String msg) {
        OftenMethod.onBtnNotification(id, this, msg, (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
    }
}
