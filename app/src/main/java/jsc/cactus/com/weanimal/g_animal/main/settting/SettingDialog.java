package jsc.cactus.com.weanimal.g_animal.main.settting;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jsc.cactus.com.weanimal.R;

/**
 * Created by INSI on 15. 9. 27..
 */
public class SettingDialog extends DialogFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, container, false);

        return view;
    }
}
