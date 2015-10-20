package jsc.cactus.com.weanimal.g_animal.main.settting;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;

/**
 * Created by INSI on 15. 9. 27..
 */
public class SettingDialog extends Dialog {

    private Button lgout;
    private Switch pushSwitch;

    public SettingDialog(Activity activity) {
        super(activity);
        setTitle("환경 설정");
        setContentView(R.layout.activity_setting);
        setCanceledOnTouchOutside(true);
        lgout = (Button) findViewById(R.id.logoutButton);
        lgout.setOnClickListener(clickListener);
        pushSwitch = (Switch) findViewById(R.id.settingSwitch);
        pushSwitch.setChecked(settingLoad());
        pushSwitch.setOnCheckedChangeListener(changeListener);
        MyService.settingPush = pushSwitch.isChecked();
    }

    private Switch.OnCheckedChangeListener changeListener = new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            settingInit(isChecked);
        }
    };

    private void settingInit(boolean b) {
        try {
            File file = new File(MainActivity.mainActivity.getFilesDir() + "/setting.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(b ? "true" : "false");
            MyService.settingPush = b;
            bw.flush();
            bw.close();
        } catch (IOException e) {
        }
    }

    private boolean settingLoad() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(MainActivity.mainActivity.getFilesDir() + "/setting.txt")));
            Boolean.parseBoolean(br.readLine());
        } catch (Exception e) {
            Log.i("jsc", e.getMessage());
        }
        return false;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity mainActivity = MainActivity.mainActivity;
            mainActivity.sendLogout();
            mainActivity.clearFile();
            MyService.login = false;
        }
    };

}
