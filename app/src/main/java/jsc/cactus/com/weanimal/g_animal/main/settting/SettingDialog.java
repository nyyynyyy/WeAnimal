package jsc.cactus.com.weanimal.g_animal.main.settting;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;

/**
 * Created by INSI on 15. 9. 27..
 */
public class SettingDialog extends Dialog {

    private Button lgout;

    public SettingDialog(Activity activity) {
        super(activity);
        setTitle("환경 설정");
        setContentView(R.layout.activity_setting);
        setCanceledOnTouchOutside(true);
        lgout = (Button) findViewById(R.id.logoutButton);
        lgout.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity mainActivity = MainActivity.mainActivity;
            mainActivity.sendLogout();
            mainActivity.clearFile();
            mainActivity.finish();
            MyService.login = false;
        }
    };

}
