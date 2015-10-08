package jsc.cactus.com.weanimal.g_animal.main.familychat;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
    private final String filePath = MainActivity.mainActivity.getFilesDir().getPath()+"/";// + "/chatData/";
    private static final File folder = new File(MainActivity.mainActivity.getFilesDir()+"/chat/");

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
            File file = new File(MainActivity.mainActivity.getFilesDir()+"/chat/"+DateFormat.formatDate(chatItem.getDate(), DateFormat.Type.DAY)+".txt");

            if (!file.exists()) {
                folder.mkdir();
                file.createNewFile();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));

            CharSequence test = " " + DateFormat.formatDate(chatItem.getDate(), DateFormat.Type.SECOND) + "|" + chatItem.getUser().getName() + "|" + chatItem.getText();
            bw.append(test);
            bw.newLine();

            bw.close();

        } catch (IOException e) {
            Log.i("jsc", "chat writer failed: " + e.getMessage());
        }
    }

    public void sendMessage(String text ,String day , String time)
    {

    }

    public static List<ChatItem> getChatData(int before) {
        try {
            Date day = new Date();
            day.setTime(day.getTime() - (before*86400000));

            File file = new File(MainActivity.mainActivity.getFilesDir()+"/chat/"+DateFormat.formatDate(day, DateFormat.Type.DAY)+".txt");

            BufferedReader br = new BufferedReader(new FileReader(file));

            List<ChatItem> chatItems = new ArrayList<ChatItem>();
            String[] str = null;
            while (br.read() != -1) {
                str = br.readLine().split("\\|");
                chatItems.add(new ChatItem(UserManager.getUserByName(str[1]).getProfileImageId(), str[2], UserManager.getUserByName(str[1]), DateFormat.parseDate(str[0], DateFormat.Type.SECOND)));
            }

            Log.i("jsc", "6");
            br.close();

            return chatItems;
        } catch (Exception ex) {
            Log.i("jsc", "read failed: "+ ex.getMessage());
            return null;
        }
    }
}
