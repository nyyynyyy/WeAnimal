package jsc.cactus.com.weanimal.g_animal.main.mission.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.view.View;

/**
 * Created by INSI on 2015. 10. 7..
 */
public class MissionViewManager {

    public static MissionViewManager missionViewManager;

    private MissionDialog missionDialog;

    public MissionViewManager(Activity activity) {
        missionViewManager = this;
        missionDialog = new MissionDialog(activity);
    }

    public void test() {
        if (!missionDialog.isShowing())
            missionDialog.show();
    }

    private View.OnClickListener clickButton = new View.OnClickListener() {
        public void onClick(View v) {
            if (!missionDialog.isShowing())
                missionDialog.show();
        }
    };

}