package jsc.cactus.com.weanimal.g_animal.main;

import android.media.AudioManager;
import android.media.MediaPlayer;

import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;

/**
 * Created by INSI on 2015. 10. 16..
 */
public class SoundUtil {
    private static MediaPlayer mp;

    public static void playSound(int rawFileSound) {
        mp = MediaPlayer.create(MainActivity.mainActivity, rawFileSound);
        mp.start();
    }

}
