package jsc.cactus.com.weanimal.g_animal.main.animal;

import android.util.Log;

/**
 * Created by INSI on 2015. 10. 2..
 */
public enum AnimalStatusType {
    DEFAULT, FLABBY, WEAK;

    public static AnimalStatusType getStatusType(int totally) {
        if (totally <= 80)
            return WEAK;
        if (totally <= 150)
            return FLABBY;
        Log.i("jsc", "totally: "+totally);
        return DEFAULT;
    }
}
