package jsc.cactus.com.weanimal;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jsc.cactus.com.weanimal.a_logo.Logos;
import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;

public class StartActivty extends AppCompatActivity {

    RestartService receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        MyService.AppTurn = true;

        receiver = new RestartService();

        IntentFilter mainFilter = new IntentFilter("jsc.cactus.com.weanimal");

        registerReceiver(receiver, mainFilter);

        Intent intent;

        if (!MyService.service_turn) {
            intent = new Intent(StartActivty.this, MyService.class);
            startService(intent);
            MyService.cons(StartActivty.this, getApplicationContext());
        }

        if (MyService.push) {
            intent = new Intent(this, MainActivity.class);
            MyService.push = false;
        } else {
            intent = new Intent(this, Logos.class);
        }
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        finish();
    }

    @Override
    protected void onDestroy() {
        OftenMethod.message(this, "이용해주셔서 감사합니다.");

        Logos.isTurn = false;
        MyService.AppTurn = false;
        unregisterReceiver(receiver);

        super.onDestroy();
    }
}
