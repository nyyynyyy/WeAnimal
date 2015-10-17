package jsc.cactus.com.weanimal.g_animal.main.settting;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jsc.cactus.com.weanimal.R;

/**
 * Created by INSI on 15. 9. 27..
 */
public class SettingDialog extends Dialog {

    public SettingDialog(Activity activity) {
        super(activity);
        setTitle("환경 설정");
        setContentView(R.layout.activity_setting);
        setCanceledOnTouchOutside(true);
    }
}
