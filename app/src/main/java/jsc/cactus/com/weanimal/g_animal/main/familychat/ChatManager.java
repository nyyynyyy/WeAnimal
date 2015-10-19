package jsc.cactus.com.weanimal.g_animal.main.familychat;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jsc.cactus.com.weanimal.MyService;
import jsc.cactus.com.weanimal.ServerTime;
import jsc.cactus.com.weanimal.g_animal.main.DateFormat;
import jsc.cactus.com.weanimal.g_animal.main.familychat.view.ChatItem;
import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;
import jsc.cactus.com.weanimal.g_animal.main.users.User;
import jsc.cactus.com.weanimal.g_animal.main.users.UserManager;

/**
 * Created by INSI on 2015. 10. 4..
 */
public class ChatManager {

    private static List<ChatListener> listeners = new ArrayList<ChatListener>();
    private final String filePath = MainActivity.mainActivity.getFilesDir().getPath() + "/";// + "/chatData/";
    private static final File folder = new File(MainActivity.mainActivity.getFilesDir() + "/chat/");

    public static void addChatListener(ChatListener listener) {
        listeners.add(listener);
    }

    public static void callChatEvent(User user, String text, Date time) {

        ChatItem chatItem = new ChatItem(user.getProfileImageId(), text, user, time);
        chatSave(chatItem);

        try {
            ChatDialog.getChatListViewAdapter().add(chatItem);
        } catch (Exception ex) {
        }

        for (ChatListener listener : listeners) {
            listener.UserChatEvent(user, text);
        }
    }

    public static void chatSave(ChatItem chatItem) {
        try {
            File file = new File(MainActivity.mainActivity.getFilesDir() + "/chat/" + DateFormat.formatDate(chatItem.getDate(), DateFormat.Type.DAY) + ".txt");

            if (!file.exists()) {
                Log.i("jsc", "파일 없음");
                folder.mkdir();
                file.createNewFile();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));


            bw.append(" " + chatItem.getDate().getTime() + "|" + chatItem.getUser().getName() + "|" + chatItem.getText().replace("\n", "</n>"));
            bw.newLine();

            bw.flush();
            bw.close();

        } catch (IOException e) {
            Log.i("jsc", "chat writer failed: " + e.getMessage());
        }
    }

    public static void sendMessage(String msg) throws JSONException {
        JSONObject data = new JSONObject();

        data.put("MSG", msg);

        MyService.mSocket.emit("UPLOAD_CHAT", data);
    }

    public static List<ChatItem> getChatData(int before) {
            File file = new File(MainActivity.mainActivity.getFilesDir() + "/chat/" + DateFormat.formatDate(new Date(ServerTime.getTime() - (before * 86400000)), DateFormat.Type.DAY) + ".txt");
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
            }catch (Exception ex){
                Log.i("jsc", "1");
            }

            List<ChatItem> chatItems = new ArrayList<ChatItem>();

            String s=null;
            String[] str = null;

            try {
                while ((s = br.readLine()) != null) {
                    str = s.split("\\|");
                    Log.i("jsc", s);
                    chatItems.add(new ChatItem(UserManager.getUserByName(str[1]).getProfileImageId(), str[2].replace("</n>", "\n"), UserManager.getUserByName(str[1]), new Date(Long.parseLong(str[0]))));
                    Log.i("jsc", "챗아이템 애드 성공적");
                }
            }catch (Exception ex){
                Log.i("jsc", "2");
            }

            try {
                br.close();
            }catch (Exception ex){
                Log.i("jsc", "3");
            }

            return chatItems;

    }

    public static List<ChatItem> getChatData(Date date) {
        File file = new File(MainActivity.mainActivity.getFilesDir() + "/chat/" + DateFormat.formatDate(date, DateFormat.Type.DAY) + ".txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        }catch (Exception ex){
            Log.i("jsc", "1");
        }

        List<ChatItem> chatItems = new ArrayList<ChatItem>();

        String s=null;
        String[] str = null;

        try {
            while ((s = br.readLine()) != null) {
                str = s.split("\\|");
                Log.i("jsc", s);
                chatItems.add(new ChatItem(UserManager.getUserByName(str[1]).getProfileImageId(), str[2].replace("</n>", "\n"), UserManager.getUserByName(str[1]), new Date(Long.parseLong(str[0]))));
                Log.i("jsc", "챗아이템 애드 성공적");
            }
        }catch (Exception ex){
            Log.i("jsc", "2");
        }

        try {
            br.close();
        }catch (Exception ex){
            Log.i("jsc", "3");
        }

        return chatItems;

    }

}
