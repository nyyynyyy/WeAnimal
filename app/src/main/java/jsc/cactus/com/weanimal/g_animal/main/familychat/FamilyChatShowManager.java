package jsc.cactus.com.weanimal.g_animal.main.familychat;

import android.app.Activity;
import android.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.mission.Mission;
import jsc.cactus.com.weanimal.g_animal.main.mission.MissionListener;

/**
 * Created by INSI on 15. 9. 30..
 */
public class FamilyChatShowManager {

    private FamilyChatDialog familyChatDialog;
    private FragmentManager fragmentManager;
    private ImageView signImageView;
    private boolean isOnMission = false;

    public FamilyChatShowManager(Activity activity){
        fragmentManager = activity.getFragmentManager();
        signImageView = (ImageView) activity.findViewById(R.id.imageView);
        familyChatDialog = new FamilyChatDialog();



        signImageView.setOnClickListener(clickSign);
    }

    View.OnClickListener clickSign = new View.OnClickListener(){
        public void onClick(View v){
            if(!familyChatDialog.isVisible())
                familyChatDialog.show(fragmentManager, "");
        }
    };
}
