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

    public static long getTime() {
        MyService.mSocket.emit("GETTIME");
        MyService.mSocket.on("TIME", Recive);

        while(time == 0)
        {

        }
        return time;
    }

    //로그인
    private static Emitter.Listener Recive = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            try {
                time = data.getLong("time");

                Log.i("TEST", "Server: "+Long.toString(time));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
