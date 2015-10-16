package jsc.cactus.com.weanimal.a_logo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.b_loading.Loding;
import jsc.cactus.com.weanimal.c_login.Have_id;
import jsc.cactus.com.weanimal.c_login.Id_query;
import jsc.cactus.com.weanimal.d_regist.Family_query;
import jsc.cactus.com.weanimal.d_regist.Have_family_1;
import jsc.cactus.com.weanimal.d_regist.Have_family_2;
import jsc.cactus.com.weanimal.d_regist.Havenot_family;

public class Logos extends AppCompatActivity {

    public static Boolean isTurn = false;
    public static Boolean isStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logos);

        isTurn = true;

        final Thread serveroff = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    try {
                        Thread.sleep(1000L);
                        if (!isTurn) {
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!MyService.server_turn) {
                        Id_query id_q_s = (Id_query) Id_query.id_q;
                        Have_id ha_i_s = (Have_id) Have_id.ha_i;
                        Family_query fa_q_s = (Family_query) Family_query.fa_q;
                        Have_family_1 ha_fa_1_s = (Have_family_1) Have_family_1.ha_fa_1;
                        Have_family_2 ha_fa_2_s = (Have_family_2) Have_family_2.ha_fa_2;
                        Havenot_family han_fa_s = (Havenot_family) Havenot_family.han_fa;

                        id_q_s.finish();
                        ha_i_s.finish();
                        fa_q_s.finish();
                        ha_fa_1_s.finish();
                        ha_fa_2_s.finish();
                        han_fa_s.finish();

                        Intent intent = new Intent(Logos.this, Logos.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                } while (MyService.server_turn);
            }
        });

        final Thread serveron = new Thread(new Runnable() {
            @Override
            public void run() {
                if (MyService.server_turn) {
                    Intent intent = new Intent(Logos.this, Loding.class);
                    startActivity(intent);
                    finish();
                }
                do {
                    try {
                        Thread.sleep(5000L);
                        if (!isTurn) {
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (MyService.server_turn) {
                        Intent intent = new Intent(Logos.this, Loding.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                } while (!MyService.server_turn);
                serveroff.start();
            }
        });
        if (isTurn)
            serveron.start();
    }

    @Override
    protected void onStop()
    {


        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //isTurn = false;

        super.onDestroy();
    }
}
