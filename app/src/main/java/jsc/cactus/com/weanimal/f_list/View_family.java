package jsc.cactus.com.weanimal.f_list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    public static Activity vi_f;
    public static Boolean vi_f_t = false;

    private Family_ListViewAdapter Adapter;

    public int familyMember = 1;

    private ListView list;

    private Button acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_family);

        vi_f = this;
        vi_f_t = true;

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

        list.setDivider(new ColorDrawable(Color.argb(255,253,96,41)));

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
        vi_f_t = false;

        MyService.mSocket.off("ME", memberRecive);

        super.onStop();
    }

    public void codeSendMessage() throws JSONException {
        JSONObject data = new JSONObject();

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
                            else {
                                Adapter.insert(new Family_Item("[가장] " + fna,fid + "\n" + fge + "\n" + fbd),0);
                            }
                            list.setAdapter(Adapter);
                        }

                        OftenMethod.message(View_family.this, "가족을 불러옵니다.");

                        MyService.mSocket.off("ME", memberRecive);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}
