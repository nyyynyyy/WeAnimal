package jsc.cactus.com.weanimal.e_set;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.Variable;

public class Complete_regist extends AppCompatActivity {

    private TextView txtfn;
    private TextView txtfp;
    private TextView txtid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_regist);

        txtid = (TextView) findViewById(R.id.a5_txt_id);
        txtfn = (TextView) findViewById(R.id.a5_txt_fn);
        txtfp = (TextView) findViewById(R.id.a5_txt_fp);
        Button btn_acc = (Button) findViewById(R.id.a5_btn_acc);


        Bundle bundle = getIntent().getExtras();
//        String id = bundle.getString("ID");
        String pw = bundle.getString("PW");
//        int fn = bundle.getInt("FN");

        String id = Variable.user_id;
        int fn = Variable.user_familycode;

        txtid.setText(id);
        txtfp.setText(pw);
        txtfn.setText(Integer.toString(fn));

        btn_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goin();
            }
        });

    }

    void goin() {
        Intent intent = new Intent(this, Set_birthday_gender.class);

        startActivity(intent);
        finish();
    }

}
