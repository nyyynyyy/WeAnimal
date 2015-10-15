package jsc.cactus.com.weanimal.g_animal.main.mission.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import jsc.cactus.com.weanimal.R;

/**
 * Created by INSI on 2015. 10. 7..
 */
public class MissionViewManager {

    private MissionDialog missionDialog;
    private FragmentManager fragmentManager;
    private Button button;
    private boolean isOnMission = false;

    public MissionViewManager(Activity activity){
        fragmentManager = activity.getFragmentManager();
        missionDialog = new MissionDialog();
        button = (Button) activity.findViewById(R.id.missionButton);

        button.setOnClickListener(clickButton);
    }

    private View.OnClickListener clickButton = new View.OnClickListener(){
        public void onClick(View v){
            if(!missionDialog.isAdded())
                missionDialog.show(fragmentManager, "");
        }
    };
}