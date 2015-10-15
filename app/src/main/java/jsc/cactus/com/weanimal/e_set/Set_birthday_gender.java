package jsc.cactus.com.weanimal.e_set;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.f_list.View_family;
import jsc.cactus.com.weanimal.OftenMethod;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.Variable;

public class Set_birthday_gender extends AppCompatActivity {

    public static Activity ac05_1;

    public int Year;
    public int Month;
    public int Day;

    String gender;

    private RadioButton radio_m;
    private RadioButton radio_f;


    private TextView txt_birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_birthday_gender);

        ac05_1 = Set_birthday_gender.this;

        radio_m = (RadioButton)findViewById(R.id.a5_1_radio_male);
        radio_f = (RadioButton)findViewById(R.id.a5_1_radio_femeal);

        Year = 1999;
        Month = 12;
        Day = 23;

        txt_birthday = (TextView) findViewById(R.id.a5_1_txt_birthday);

        Button btn_ok = (Button) findViewById(R.id.a5_1_btn_ok);

        txt_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(Year, Month, Day);
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radio_f.isChecked()){
                    gender = "female";
                }
                else if(radio_m.isChecked())
                {
                    gender = "male";
                }


                if(radio_f.isChecked()||radio_m.isChecked()) {
                    if(txt_birthday.getText().toString().equals("생년월일 선택")){
                        OftenMethod.message(Set_birthday_gender.this, "생년월일을 알려주세요.");
                    }
                    else {
                        try {
                            sendMessage();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    OftenMethod.message(Set_birthday_gender.this, "성별을 알려주세요.");
                }
            }
        });

    }



    public void sendMessage() throws JSONException {
        JSONObject data = new JSONObject();

        String msggendr = gender;
        String msgbirthday = Integer.toString(Year) + "-" + Integer.toString(Month) + "-" + Integer.toString(Day);


        data.put("ID", Variable.user_id);
        data.put("GE", msggendr);
        data.put("BD", msgbirthday);

        Variable.user_gender = msggendr;
        Variable.user_birthday = msgbirthday;

        MyService.mSocket.emit("PED", data);

        goin();

    }



    void goin() {
        Intent intent = new Intent(this, Select_animal.class);

        startActivity(intent);
        finish();
    }

    public void openDatePicker(int year, int month, int day) {
        DatePickerDialog dialog = new DatePickerDialog(this, listener, year, month - 1, day);

        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            Year = year;
            Month = monthOfYear + 1;
            Day = dayOfMonth;
            txt_birthday.setText(Year + "년 " + Month + "월 " + Day + "일");

        }

    };
}
