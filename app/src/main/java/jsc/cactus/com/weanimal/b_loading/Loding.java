package jsc.cactus.com.weanimal.b_loading;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.c_login.Have_id;
import jsc.cactus.com.weanimal.c_login.Id_query;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.f_list.View_family;

public class Loding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        Intent intent = null;

        if (MyService.login) {
            if (!View_family.vi_f_t) {
                intent = new Intent(this, View_family.class);
            }
        } else {
            if (!Id_query.id_q_t) {
                intent = new Intent(this, Id_query.class);
                Log.i("TEST", "START LOGIN");
            }
        }
        startActivity(intent);
        finish();
    }
}
