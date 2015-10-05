package jsc.cactus.com.weanimal.c_login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import jsc.cactus.com.weanimal.d_regist.Family_query;
import jsc.cactus.com.weanimal.R;

public class Id_query extends AppCompatActivity {

    public static Activity ac03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id_query);

        ac03 = Id_query.this;

        Button yesBtn = (Button) findViewById(R.id.a3_btn_yes);
        Button noBtn = (Button) findViewById(R.id.a3_btn_no);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goinYes();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goinNo();
            }
        });
    }

    void goinYes() {
        Intent intent = new Intent(this, Have_id.class);
        startActivity(intent);
    }

    void goinNo() {
        Intent intent = new Intent(this, Family_query.class);
        startActivity(intent);
    }
}
