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
import jsc.cactus.com.weanimal.f_list.View_family;
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

        edit_animal_name = (EditText) findViewById(R.id.edit_animal_name);

        Button button = (Button) findViewById(R.id.btn_adopt);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (animal_type == null) {
                    OftenMethod.message(Select_animal.this, "동물을 골라주세요.");
                } else {
                    if (edit_animal_name.getText().toString().equals("")) {
                        OftenMethod.message(Select_animal.this, "이름을 지어주세요.");
                    } else {
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

                typeScale(typeI, 1.5F);
                typeScale(typeII, 1.0F);
                typeScale(typeIII, 1.0F);

                animal_type = AnimalType.CHICKEN.toString();

            }

        });
        typeII.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                typeScale(typeI, 1.0F);
                typeScale(typeII, 1.5F);
                typeScale(typeIII, 1.0F);

                animal_type = AnimalType.CHICKEN.toString();

            }

        });
        typeIII.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                typeScale(typeI, 1.0F);
                typeScale(typeII, 1.0F);
                typeScale(typeIII, 1.5F);

                animal_type = AnimalType.CAT.toString();

            }

        });
    }

    public void sendMessage() throws JSONException {
        JSONObject data = new JSONObject();

        int msgco = Variable.user_familycode;
        String msgname = animal_name;
        String msgtype = animal_type;


        data.put("CO", msgco);
        data.put("TYPE", msgtype);
        data.put("NA", msgname);

        Variable.animal_name = animal_name;

        MyService.mSocket.emit("CREATE_ANIMAL", data);

        goin(View_family.class);
    }

    private void typeScale(ImageView type, float x) {
        type.setScaleX(x);
        type.setScaleY(x);
    }

    void goin(Class go) {
        Intent intent = new Intent(this, go);

        startActivity(intent);
        finish();
    }
}
