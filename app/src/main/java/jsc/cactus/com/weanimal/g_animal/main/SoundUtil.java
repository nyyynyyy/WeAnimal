package jsc.cactus.com.weanimal.g_animal.main;

import android.media.AudioManager;
import android.media.SoundPool;

import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;

/**
 * Created by INSI on 2015. 10. 16..
 */
public class SoundUtil {
    private static SoundPool soundPool;

    public static void playSound(int rawFileSound) {
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        int sound = soundPool.load(MainActivity.mainActivity, rawFileSound, 1);

        soundPool.play(sound, 1, 1, 1, 0, 1);
    }

}
