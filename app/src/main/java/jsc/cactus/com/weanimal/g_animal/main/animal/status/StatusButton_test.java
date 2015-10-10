package jsc.cactus.com.weanimal.g_animal.main.animal.status;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;


/**
 * Created by INSI on 2015. 10. 2..
 */
public class StatusButton_test {

    private ImageView bFood, bWater, bLove;

    public StatusButton_test(Activity activity) {

        bFood = (ImageView) activity.findViewById(R.id.bt_food);
        bWater = (ImageView) activity.findViewById(R.id.bt_water);
        bLove = (ImageView) activity.findViewById(R.id.bt_love);

        bFood.setOnClickListener(buttontest);
        bWater.setOnClickListener(buttontest);
        bLove.setOnClickListener(buttontest);

        bLove.setOnTouchListener(buttontest1);
        bFood.setOnTouchListener(buttontest1);
        bWater.setOnTouchListener(buttontest1);
    }

    private View.OnClickListener buttontest = new View.OnClickListener() {
        //배고픔, 목마름, 애정 버튼을 누르면 각각 수치가 5 오름
        public void onClick(View view) {
            Animal.animal.getStatus().addStatus(view.getId() == R.id.bt_food ? StatusType.FOOD : view.getId() == R.id.bt_water ? StatusType.WATER : StatusType.LOVE, 5);
        }
    };

    private ImageView.OnTouchListener buttontest1 = new ImageView.OnTouchListener(){

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
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
}
