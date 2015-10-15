package jsc.cactus.com.weanimal.g_animal.main.animal;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.Status;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.StatusChangeListener;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.StatusListenerManager;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.StatusType;
import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;


/**
 * Created by INSI on 2015. 10. 2..
 */
public class Animal implements StatusChangeListener {

    public static Animal animal;

    private ImageView animalView;
    private Bitmap bitmap;

    private Status status;

    private int animalCode;
    private String name;
    private AnimalType type;
    private int age;

    private boolean ready;

    public Animal(Activity activity) {

        animal = this;

        status = new Status(activity, this);
        StatusListenerManager.addStatusChangeListener(this);
        animalView = (ImageView) activity.findViewById(R.id.imageView2);
    }

    public int getAge() {
        return age;
    }

    public int getAnimalCode() {
        return animalCode;
    }

    public String getName() {
        return name;
    }

    public Status getStatus(){
        return status;
    }

    public void addAge(int age) {
        this.age += age;
        reimage();
    }

    public void setReady(boolean bool){
        ready = true;
        if(ready)
            reimage();
    }

    public void setName(String name){
        this.name = name;
    }

    public void setType(AnimalType type){
        this.type = type;
        reimage();
    }

    public void setAnimalCode(int animalCode){
        this.animalCode = animalCode;
    }

    public void setAge(int age){
        this.age = age;
        reimage();
    }

    public void reimage() {
        reimage.sendMessage(Message.obtain());
    }

    @Override
    public void StatusChangeEvent() {
        reimage();
    }

    private Handler reimage = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(!ready)
                return;

            try {
                Bitmap bitmap = ((BitmapDrawable) animalView.getDrawable()).getBitmap();
                bitmap.recycle();
            }catch (Exception ex){}

            animalView.setImageBitmap(BitmapFactory.decodeResource(MainActivity.mainActivity.getResources(), AnimalKind.getImageResource(type, AnimalStatusType.getStatusType((status.getStatus(StatusType.FOOD) + status.getStatus(StatusType.WATER))), age)));
        }
    };
}
