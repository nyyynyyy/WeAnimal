package jsc.cactus.com.weanimal.g_animal.main.animal.status;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;

/**
 * Created by INSI on 2015. 10. 2..
 */
public class StatusProgressBar {

    public ProgressBar pbFood, pbWater, pbLove;
    public StatusProgressBar(Activity activity){
        pbFood = ((ProgressBar) activity.findViewById(R.id.progressBar_food));
        pbWater = ((ProgressBar) activity.findViewById(R.id.progressBar_water));
        pbLove = ((ProgressBar) activity.findViewById(R.id.progressBar_love));
    }

    public void notifyDataChanged(){
        setProgressBar.sendMessage(Message.obtain());
    }

    private Handler setProgressBar = new Handler() {
        @Override
        public synchronized void handleMessage(Message msg) {
            Status status = Animal.animal.getStatus();
            pbFood.setProgress(status.getStatus(StatusType.FOOD));
            pbWater.setProgress(status.getStatus(StatusType.WATER));
            pbLove.setProgress(status.getStatus(StatusType.LOVE));
        }
    };

}
