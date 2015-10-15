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

public class Logos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logos);

        final Thread serveroff = new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!MyService.server_turn) {
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
        serveron.start();
    }
}
