package jsc.cactus.com.weanimal.g_animal.main.animal.status.progress;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.Status;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.StatusType;

/**
 * Created by INSI on 2015. 10. 2..
 */
public class StatusProgressBar {

    public ProgressBar pbFood, pbWater, pbLove;

    public StatusProgressBar(Activity activity) {
        pbFood = ((ProgressBar) activity.findViewById(R.id.progressBar_food));
        pbWater = ((ProgressBar) activity.findViewById(R.id.progressBar_water));
        pbLove = ((ProgressBar) activity.findViewById(R.id.progressBar_love));
    }

    public void notifyDataChanged() {
        setProgressBar.sendMessage(Message.obtain());
    }

    private Handler setProgressBar = new Handler() {
        @Override
        public synchronized void handleMessage(Message msg) {
            Status status = Animal.animal.getStatus();
            progressAnim(pbFood, status.getStatus(StatusType.FOOD));
            progressAnim(pbWater, status.getStatus(StatusType.WATER));
            progressAnim(pbLove, status.getStatus(StatusType.LOVE));
        }
    };

    private void progressAnim(ProgressBar pb, int progress) {
        ProgressBarAnimation anim = new ProgressBarAnimation(pb, 600);
        anim.setProgress(progress);
    }

}
