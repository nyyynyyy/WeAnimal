package jsc.cactus.com.weanimal.g_animal.main.animal.status;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.Variable;
import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;

/**
 * Created by nyyyn on 2015-10-07.
 */
public class Level_up {
    private int LEVEL;

    public Level_up(){
        LEVEL = Animal.animal.getAge();
        LEVEL += 1;
        Animal.animal.setAge(LEVEL);
        try {
            sendMessage();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("TEST", "LEVEL_UP");
    }

    public void sendMessage() throws JSONException {
        JSONObject data = new JSONObject();

        // perform the user login attempt.

        data.put("LE", LEVEL);
        data.put("CO", Variable.user_familycode);

        MyService.mSocket.emit("LEVELUP", data);
    }
}
