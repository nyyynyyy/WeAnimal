package jsc.cactus.com.weanimal.g_animal.main;

import android.media.AudioManager;
import android.media.SoundPool;

import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;

/**
 * Created by INSI on 2015. 10. 16..
 */
public class SoundUtil {
    //private MediaPlayer mp;

    public static void playSound(int rawFileSound) {
        SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_SYSTEM, 0);
        soundPool.play(soundPool.load(MainActivity.mainActivity.getApplicationContext(), rawFileSound, 1), 1f, 1f, 1, 0, 1f);
    }

}
