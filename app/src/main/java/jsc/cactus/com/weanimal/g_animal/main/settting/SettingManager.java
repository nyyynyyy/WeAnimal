package jsc.cactus.com.weanimal.g_animal.main.settting;

import android.app.Activity;

import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;

/**
 * Created by INSI on 2015. 10. 6..
 */
public class SettingManager {

    private static SettingDialog settingDialog;

    public SettingManager(Activity activity) {
        settingDialog = new SettingDialog();
    }
}
