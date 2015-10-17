package jsc.cactus.com.weanimal.g_animal.main.settting;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;

/**
 * Created by INSI on 2015. 10. 6..
 */
public class SettingManager {

    private static SettingDialog settingDialog;

    private ImageView button;

    public SettingManager(Activity activity) {
        settingDialog = new SettingDialog(activity);

        button = (ImageView) activity.findViewById(R.id.btn_setting);
        button.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!settingDialog.isShowing())
                settingDialog.show();
        }
    };
}
