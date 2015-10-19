package jsc.cactus.com.weanimal;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;

/**
 * Created by nyyyn on 2015-10-15.
 */
public class ServerTime {

    public static long time = 0;
    private static boolean isEnd = false;

    public static long getTime() {
        MyService.mSocket.emit("ST");
        Log.i("TEST", "에밋끝");
        MyService.mSocket.on("TIME", Recive);

        while (!isEnd) {}
        isEnd = false;

        return time;
    }



    //로그인
    private static Emitter.Listener Recive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            try {
                JSONObject data = (JSONObject) args[0];
                time = data.getLong("time");

                //Log.i("TEST", "Server: " + Long.toString(time));
                isEnd = true;

             //Log.i("TEST", "Server: "+Long.toString(time));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
