package jsc.cactus.com.weanimal.g_animal.main.animal.status;

import android.app.Activity;
import android.util.Log;

import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;

/**
 * Created by INSI on 15. 9. 27..
 */
public class Status {

    //캡슐화를 위해서 프라이베이트로 선언함
    private Animal animal;

    private int food, water, love;
    private StatusProgressBar statusProgressBar;
    private StatusButton_test statusButton_test;
    private StatusListenerManager statusListenerManager;

    public Status(Activity activity, Animal animal) {

        this.animal = animal;

        statusProgressBar = new StatusProgressBar(activity);
        statusButton_test = new StatusButton_test(activity);
        statusListenerManager = new StatusListenerManager();

        food = statusProgressBar.pbFood.getMax();
        water = statusProgressBar.pbWater.getMax();
        love = 0;

        //thread();
    }

    private void resize() {
        food = Math.max(Math.min(food, statusProgressBar.pbFood.getMax()), 0);
        water = Math.max(Math.min(water, statusProgressBar.pbWater.getMax()), 0);
        love = Math.max(Math.min(love, statusProgressBar.pbLove.getMax()), 0);
        if(love == statusProgressBar.pbLove.getMax()) {
            new Level_up();
            love = 0;
        }
    }

    public void setStatus(StatusType type, int value) {
        switch (type) {
            case FOOD:
                food = value;
                break;
            case WATER:
                water = value;
                break;
            case LOVE:
                love = value;
                break;
        }
        resize();
        statusProgressBar.notifyDataChanged();
        statusListenerManager.StatusChangeEventCall();
    }

   public void addStatus(StatusType type, int value) {
        switch (type) {
            case FOOD:
                food += value;
                break;
            case WATER:
                water += value;
                break;
            case LOVE:
                love += value;
                break;
        }
        resize();
        statusProgressBar.notifyDataChanged();
        statusListenerManager.StatusChangeEventCall();
        new Share_status(food,water,love);
        Log.i("TEST", "GET");
    }

    public int getStatus(StatusType type) {
        return type == StatusType.FOOD ? food : type == StatusType.WATER ? water : love;
    }


    /*********************************
     * 테스트
     *****************************/
   /* public void thread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        food--;
                        water--;
                        resize();
                        statusProgressBar.notifyDataChanged();
                        statusListenerManager.StatusChangeEventCall();
                        Thread.sleep(1000);
                    }
                } catch (Exception ex) {
                    thread();
                }
            }
        }).start();
    }*/
    /********************************************************************/
}
