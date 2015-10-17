package jsc.cactus.com.weanimal;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jsc.cactus.com.weanimal.a_logo.Logos;

public class StartActivty extends AppCompatActivity {

    RestartService receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        receiver = new RestartService();

        IntentFilter mainFilter = new IntentFilter("jsc.cactus.com.weanimal");

        registerReceiver(receiver, mainFilter);

        if (!MyService.service_turn) {
            Intent intent = new Intent(StartActivty.this, MyService.class);
            startService(intent);
            MyService.cons(StartActivty.this, getApplicationContext());
        }


        Intent intentL = new Intent(this, Logos.class);
        startActivity(intentL);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Logos.isTurn = false;
        finish();
    }

    @Override
    protected void onDestroy() {
        OftenMethod.message(this, "이용해주셔서 감사합니다.");

        Logos.isTurn = false;
        unregisterReceiver(receiver);

        super.onDestroy();
    }
}
