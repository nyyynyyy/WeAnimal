package jsc.cactus.com.weanimal;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by nyyyn on 2015-09-23.
 */
public class OftenMethod {
    //토스트
    public static void message(Activity ac, String msg){
        Toast.makeText( ac , msg ,Toast.LENGTH_SHORT).show();
    }
}
