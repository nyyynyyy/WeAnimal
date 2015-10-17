package jsc.cactus.com.weanimal.g_animal.main.animal.status;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.Variable;
import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;

/**
 * Created by nyyyn on 2015-10-04.
 */
public class Share_status {

    int FOOD;
    int WATER;
    int LOVE;

    public Share_status(int food,int water,int love) {
        FOOD = food;
        WATER = water;
        LOVE = love;
        try {
            sendMessage();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("TEST","SET");
    }

    public void sendMessage() throws JSONException {
        JSONObject data = new JSONObject();

        data.put("FO", FOOD);
        data.put("WA", WATER);
        data.put("LO",LOVE);
        data.put("ID",Variable.user_id);
        data.put("CO",Variable.user_familycode);

        MyService.mSocket.emit("SETSTATUS", data);
    }
}
