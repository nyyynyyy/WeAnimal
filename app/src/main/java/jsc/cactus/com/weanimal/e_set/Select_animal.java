package jsc.cactus.com.weanimal.e_set;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;
import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.OftenMethod;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.Variable;
import jsc.cactus.com.weanimal.c_login.Id_query;
import jsc.cactus.com.weanimal.d_regist.Family_query;
import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;
import jsc.cactus.com.weanimal.g_animal.main.animal.AnimalType;

public class Select_animal extends AppCompatActivity {

    private ImageView typeI;
    private ImageView typeII;
    private ImageView typeIII;

    private EditText edit_animal_name;

    private String animal_type;
    private String animal_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_animal);

        typeI = (ImageView) findViewById(R.id.type_1);
        typeII = (ImageView) findViewById(R.id.type_2);
        typeIII = (ImageView) findViewById(R.id.type_3);

        edit_animal_name = (EditText)findViewById(R.id.edit_animal_name);

        Button button = (Button) findViewById(R.id.btn_adopt);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(animal_type == null)
                {
                    OftenMethod.message(Select_animal.this, "동물을 골라주세요.");
                }
                else
                {
                    if(edit_animal_name.getText().toString().equals("")){
                        OftenMethod.message(Select_animal.this, "이름을 지어주세요.");
                    }
                    else
                    {
                        animal_name = edit_animal_name.getText().toString();
                        try {
                            sendMessage();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        });


        typeI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                type1Scale(1.5F);
                type2Scale(1F);
                type3Scale(1F);

                animal_type = AnimalType.CHICKEN.toString();

            }

        });
        typeII.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                type1Scale(1F);
                type2Scale(1.5F);
                type3Scale(1F);

                animal_type = AnimalType.CHICKEN.toString();

            }

        });
        typeIII.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                type1Scale(1F);
                type2Scale(1F);
                type3Scale(1.5F);

                animal_type = AnimalType.CHICKEN.toString();

            }

        });
    }

    public void sendMessage() throws JSONException {
        JSONObject data = new JSONObject();

        // perform the user login attempt.
        int msgco = Variable.user_familycode;
        String msgname = animal_name;
        String msgtype = animal_type;


        //OftenMethod.message(this, "ID : " + msgId + "\nNAME : " + msgname + "\nPASSWORD : " + msgfp);

        data.put("CO", msgco);
        data.put("TYPE", msgtype);
        data.put("NA", msgname);

        MyService.mSocket.emit("CREATE_ANIMAL", data);

        goin();
    }

    private void type1Scale(float x){
        typeI.setScaleX(x);
        typeI.setScaleY(x);
    }
    private void type2Scale(float x){
        typeII.setScaleX(x);
        typeII.setScaleY(x);
    }
    private void type3Scale(float x){
        typeIII.setScaleX(x);
        typeIII.setScaleY(x);
    }

    void goin() {
        Intent intent = new Intent(this, Set_birthday_gender.class);

        startActivity(intent);
        finish();
    }
}
