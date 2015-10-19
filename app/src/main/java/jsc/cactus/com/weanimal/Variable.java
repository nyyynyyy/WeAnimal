package jsc.cactus.com.weanimal;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by nyyyn on 2015-09-22.
 */
public class Variable {
    public static final String SERVER_ADDRESS = "http://gondr.iptime.org:52273";

    public static String user_id = "nyyynyyy";
    public static String user_name = "김재현";
    public static int user_familycode = 0;
    public static String user_gender = "male";
    public static String user_birthday = "1999-12-23";
    public static String user_phonenumber = "010-1999-1223";

    public static String animal_name = "카투스";

    public static boolean morning(int hour) {
        if (hour >= 7 && hour < 9)
            return true;
        else
            return false;
    }
    public static boolean daytime(int hour) {
        if (hour >= 9 && hour < 16)
            return true;
        else
            return false;
    }
    public static boolean evening(int hour) {
        if (hour >= 16 && hour < 21)
            return true;
        else
            return false;
    }
    public static boolean night(int hour) {
        if ((hour >= 21 && hour <= 24) || (hour >= 0 && hour < 7))
            return true;
        else
            return false;
    }

    public static Activity service_activity;
    public static Context service_context;
}
