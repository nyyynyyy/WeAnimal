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
    public static void message(Activity ac, String msg){
        Toast.makeText( ac , msg ,Toast.LENGTH_SHORT).show();
    }




    //로그인
    public static void LoginNoti(int ID, Context c, String msg , NotificationManager no ,long time) {
        PendingIntent pendingIntent = PendingIntent.getActivity(c, 0, new Intent(c, StartActivty.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(c);

        // 작은 아이콘 이미지.
        builder.setSmallIcon(R.mipmap.ic_launcher);

        // 알림이 출력될 때 상단에 나오는 문구.
        builder.setTicker("가족이 방문하였습니다.");

        // 알림 출력 시간.
        builder.setWhen(time);

        // 알림 제목.
        builder.setContentTitle("위애니멀");

        // 알림 내용.
        builder.setContentText(msg);

        // 알림 터치시 반응.
        builder.setContentIntent(pendingIntent);

        // 알림 터치시 반응 후 알림 삭제 여부.
        builder.setAutoCancel(true);

        // 우선순위.
        //builder.setPriority(NotificationCompat.PRIORITY_MAX);

        // 고유ID로 알림을 생성.
        NotificationManager nm = no;
        nm.notify(ID, builder.build());
    }

    //상태
    public static void StatusNoti(int ID, Context c, String msg , NotificationManager no, long time) {
        PendingIntent pendingIntent = PendingIntent.getActivity(c, 0, new Intent(c, StartActivty.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(c);

        // 작은 아이콘 이미지.
        builder.setSmallIcon(R.mipmap.ic_launcher);

        // 알림이 출력될 때 상단에 나오는 문구.
        builder.setTicker("동물이 주인님을 부릅니다.");

        // 알림 출력 시간.

        builder.setWhen(time);

        // 알림 제목.
        builder.setContentTitle("위애니멀");

        // 알림 내용.
        builder.setContentText(msg);

        // 알림 터치시 반응.
        builder.setContentIntent(pendingIntent);

        // 알림 터치시 반응 후 알림 삭제 여부.
        builder.setAutoCancel(true);

        // 고유ID로 알림을 생성.
        NotificationManager nm = no;
        nm.notify(ID, builder.build());
    }

    //채팅
    public static void ChatNoti(int ID, String OtherName, Context c, String msg , NotificationManager no, long time) {
        PendingIntent pendingIntent = PendingIntent.getActivity(c, 0, new Intent(c, StartActivty.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(c);

        // 작은 아이콘 이미지.
        builder.setSmallIcon(R.mipmap.ic_launcher);

        // 알림이 출력될 때 상단에 나오는 문구.
        builder.setTicker(OtherName + "님이 말합니다.");

        // 알림 출력 시간.

        builder.setWhen(time);

        // 알림 제목.
        builder.setContentTitle("위애니멀");

        // 알림 내용.
        builder.setContentText(msg);

        // 알림 터치시 반응.
        builder.setContentIntent(pendingIntent);

        // 알림 터치시 반응 후 알림 삭제 여부.
        builder.setAutoCancel(true);

        // 고유ID로 알림을 생성.
        NotificationManager nm = no;
        nm.notify(ID, builder.build());
    }
}
