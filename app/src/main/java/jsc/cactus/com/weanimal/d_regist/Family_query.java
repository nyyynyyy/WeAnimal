package jsc.cactus.com.weanimal.d_regist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import jsc.cactus.com.weanimal.R;

public class Family_query extends AppCompatActivity {

    public static Activity fa_q;

    public static boolean fa_q_t = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family_query);

        fa_q = Family_query.this;

        fa_q_t = true;

        Button yesBtn = (Button) findViewById(R.id.a4_btn_yes);
        Button noBtn = (Button) findViewById(R.id.a4_btn_no);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goin(Have_family_1.class);
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goin(Havenot_family.class);
            }
        });

    }

    @Override
    protected void onStop(){
        fa_q_t = false;

        super.onStop();
    }

    void goin(Class go) {
        Intent intent = new Intent(this, go);
        startActivity(intent);
    }
}
