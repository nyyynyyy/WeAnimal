package jsc.cactus.com.weanimal;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import jsc.cactus.com.weanimal.a_logo.Logos;

public class StartActivty extends AppCompatActivity {

    RestartService receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        receiver = new RestartService();

        IntentFilter mainFilter = new IntentFilter("jsc.cactus.com.weanimal");

        registerReceiver(receiver, mainFilter);

        if(!MyService.turn) {
            Intent intent = new Intent(StartActivty.this, MyService.class);
            startService(intent);
            MyService.cons(StartActivty.this, getApplicationContext());
        }


        Intent intentL = new Intent(this, Logos.class);
        startActivity(intentL);
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        finish();
    }

    @Override
    protected  void onDestroy(){
        OftenMethod.message(this, "이용해주셔서 감사합니다.");

        unregisterReceiver(receiver);

        super.onDestroy();
    }
}
