package jsc.cactus.com.weanimal.g_animal.main.mission.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.StatusType;

/**
 * Created by INSI on 2015. 10. 7..
 */
public class MissionViewManager {

    public static MissionViewManager missionViewManager;

    private MissionDialog missionDialog;
    private ImageView bFood, bWater, bLove;

    public MissionViewManager(Activity activity) {
        missionViewManager = this;
        missionDialog = new MissionDialog(activity);

        bFood = (ImageView) activity.findViewById(R.id.bt_food);
        bWater = (ImageView) activity.findViewById(R.id.bt_water);
        bLove = (ImageView) activity.findViewById(R.id.bt_love);

        bFood.setOnClickListener(clickButton);
        bWater.setOnClickListener(clickButton);
        bLove.setOnClickListener(clickButton);

        bLove.setOnTouchListener(buttontest1);
        bFood.setOnTouchListener(buttontest1);
        bWater.setOnTouchListener(buttontest1);
    }

    private ImageView.OnTouchListener buttontest1 = new ImageView.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setAlpha(0.7F);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.setAlpha(1F);
                    break;
            }

            return false;
        }
    };

    private View.OnClickListener clickButton = new View.OnClickListener() {
        public void onClick(View v) {
            if (!missionDialog.isShowing()) {
                missionDialog.show(v.getId() == bFood.getId() ? StatusType.FOOD : v.getId() == bWater.getId() ? StatusType.WATER : StatusType.LOVE);
                Log.i("jsc", "click misstion button");
            }
            Log.i("jsc", "click misstion button");
        }
    };
}