package jsc.cactus.com.weanimal.g_animal.main.familychat;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
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
    private static final String filePath = MainActivity.mainActivity.getFilesDir().getAbsolutePath()+"/chatData/";

    public static void addChatListener(ChatListener listener){
        listeners.add(listener);
    }

    public static void callChatEvent(User user, String text){
        for(ChatListener listener : listeners)
            listener.UserChatEvent(user, text);
        FamilyChatDialog.getChatListViewAdapter().add(new ChatItem(null, text, user.getName(), new Date()));
    }

    public static List<ChatItem> getChatData(int before){
        try {
            Date day = new Date();
            day.setTime(day.getTime()-86400000);
            BufferedReader bis = new BufferedReader(new FileReader(filePath + DateFormat.formatDate(day, DateFormat.Type.DAY)));

            List<ChatItem> chatItems = new ArrayList<ChatItem>();
            String[] str = null;
            while(bis.read() != -1){
                str = bis.readLine().split("\\|");
                chatItems.add(new ChatItem(UserManager.getUserByName(str[1]).getProfileImageId(), str[2], str[0], DateFormat.parseDate(str[1], DateFormat.Type.SECOND)));
            }

            return chatItems;
        }catch(Exception ex){
            return null;
        }
    }
}
