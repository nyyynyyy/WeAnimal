package jsc.cactus.com.weanimal.g_animal.main.familychat;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;
import jsc.cactus.com.weanimal.g_animal.main.users.User;
import jsc.cactus.com.weanimal.g_animal.main.users.UserManager;

/**
 * Created by INSI on 2015. 10. 4..
 */
public class ChatManager {

    private static List<ChatListener> listeners = new ArrayList<ChatListener>();
    private static final String filePath = MainActivity.mainActivity.getFilesDir().getAbsolutePath() + "/chatData/";

    public ChatManager() {
        List<ChatItem> chatItems = getChatData(0);
        if (chatItems != null)
            for (ChatItem chatItem : chatItems)
                FamilyChatDialog.getChatListViewAdapter().add(chatItem);
    }

    public static void addChatListener(ChatListener listener) {
        listeners.add(listener);
    }

    public static void callChatEvent(User user, String text) {
        for (ChatListener listener : listeners)
            listener.UserChatEvent(user, text);
        ChatItem chatItem = new ChatItem(user.getProfileImageId(), text, user, new Date());
        FamilyChatDialog.getChatListViewAdapter().add(chatItem);
        try {
            BufferedWriter bos = new BufferedWriter(new FileWriter(filePath + DateFormat.formatDate(chatItem.getDate(), DateFormat.Type.DAY) + ".chat", true));

            CharSequence test = " " + DateFormat.formatDate(chatItem.getDate(), DateFormat.Type.SECOND) + "|" + chatItem.getUser().getName() + "|" + chatItem.getText();
            bos.append(test);
            bos.newLine();

            bos.close();
        } catch (IOException e) {
            File file = new File(MainActivity.mainActivity.getFilesDir().getAbsolutePath() + "/chatData");
            file.mkdir();
            //file.
            //Log.i("jsc", "chat writer failed: " + e.getMessage());
        }
    }

    public static List<ChatItem> getChatData(int before) {
        try {
            Date day = new Date();
            day.setTime(day.getTime() - 86400000);
            BufferedReader bis = new BufferedReader(new FileReader(filePath + DateFormat.formatDate(day, DateFormat.Type.DAY) + ".chat"));

            List<ChatItem> chatItems = new ArrayList<ChatItem>();
            String[] str = null;
            while (bis.read() != -1) {
                str = bis.readLine().split("\\|");
                chatItems.add(new ChatItem(UserManager.getUserByName(str[1]).getProfileImageId(), str[2], UserManager.getUserByName(str[1]), DateFormat.parseDate(str[0], DateFormat.Type.SECOND)));
            }

            bis.close();

            return chatItems;
        } catch (Exception ex) {
            Log.i("jsc", "read failed");
            return null;
        }
    }
}
