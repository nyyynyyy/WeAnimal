package jsc.cactus.com.weanimal.g_animal.main.animal.status;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;


/**
 * Created by INSI on 2015. 10. 2..
 */
public class StatusButton_test {

    Button bFood, bWater, bLove;

    public StatusButton_test(Activity activity) {
        bFood = (Button) activity.findViewById(R.id.bt_food);
        bWater = (Button) activity.findViewById(R.id.bt_water);
        bLove = (Button) activity.findViewById(R.id.bt_love);

        bFood.setOnClickListener(buttontest);
        bWater.setOnClickListener(buttontest);
        bLove.setOnClickListener(buttontest);
    }

    private View.OnClickListener buttontest = new View.OnClickListener() {
        //배고픔, 목마름, 애정 버튼을 누르면 각각 수치가 5 오름
        public void onClick(View view) {
            Animal.animal.getStatus().addStatus(view.getId() == R.id.bt_food ? StatusType.FOOD : view.getId() == R.id.bt_water ? StatusType.WATER : StatusType.LOVE, 5);
        }
    };

}
