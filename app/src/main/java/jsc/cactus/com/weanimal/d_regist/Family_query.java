package jsc.cactus.com.weanimal.d_regist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import jsc.cactus.com.weanimal.R;

public class Family_query extends AppCompatActivity {

    public static Activity ac04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family_query);

        ac04 = Family_query.this;

        Button yesBtn = (Button) findViewById(R.id.a4_btn_yes);
        Button noBtn = (Button) findViewById(R.id.a4_btn_no);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goinII();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent.putExtras(bundle);
                goinI();
            }
        });

    }

    void goinI(){
        Intent intent = new Intent(this, Havenot_family.class);
        startActivity(intent);
    }

    void goinII(){
        Intent intent = new Intent(this, Have_family_1.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity04, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
