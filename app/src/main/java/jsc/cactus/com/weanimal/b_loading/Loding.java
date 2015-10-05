package jsc.cactus.com.weanimal.b_loading;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.c_login.Id_query;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.f_list.View_family;

public class Loding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        if (MyService.login) {
            Intent intent = new Intent(this, View_family.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, Id_query.class);
            startActivity(intent);
            finish();
        }
    }
}
