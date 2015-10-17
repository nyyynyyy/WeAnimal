package jsc.cactus.com.weanimal.a_logo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.b_loading.Loding;
import jsc.cactus.com.weanimal.c_login.Have_id;
import jsc.cactus.com.weanimal.c_login.Id_query;
import jsc.cactus.com.weanimal.d_regist.Family_query;
import jsc.cactus.com.weanimal.d_regist.Have_family_1;
import jsc.cactus.com.weanimal.d_regist.Have_family_2;
import jsc.cactus.com.weanimal.d_regist.Havenot_family;
import jsc.cactus.com.weanimal.f_list.View_family;
import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;

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
                        View_family vi_fa_s = (View_family) View_family.vi_f;
                        MainActivity animal_hill_s = (MainActivity) MainActivity.animal_hill_a;

                        if (Id_query.id_q_t) id_q_s.finish();
                        if (Have_id.ha_i_t) ha_i_s.finish();
                        if (Family_query.fa_q_t) fa_q_s.finish();
                        if (Have_family_1.ha_fa_1_t) ha_fa_1_s.finish();
                        if (Have_family_2.ha_fa_2_t) ha_fa_2_s.finish();
                        if (Havenot_family.han_fa_t) han_fa_s.finish();
                        if (View_family.vi_f_t) vi_fa_s.finish();
                        if (MainActivity.animal_hill_t) animal_hill_s.finish();

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
                Log.i("TEST", "SERVERON");
                if (MyService.server_turn) {
                    Intent intent = new Intent(Logos.this, Loding.class);
                    startActivity(intent);
                    finish();
                } else {
                    do {
                        try {
                            Thread.sleep(5000L);
                            if (!isTurn) {
                                break;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } while (!MyService.server_turn);
                    if (MyService.server_turn) {
                        Intent intent = new Intent(Logos.this, Loding.class);
                        startActivity(intent);
                        finish();
                    }
                }
                serveroff.start();
            }
        });

        if (isTurn)
            serveron.start();
    }

    @Override
    protected void onStop() {


        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
