package jsc.cactus.com.weanimal.a_logo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.b_loading.Loding;

public class Logos extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logos);

        Intent intent = new Intent(this, Loding.class);
        //intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
