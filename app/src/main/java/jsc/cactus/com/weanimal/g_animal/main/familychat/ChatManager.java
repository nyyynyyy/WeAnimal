package jsc.cactus.com.weanimal.g_animal.main.familychat;

import java.util.ArrayList;
import java.util.List;

import jsc.cactus.com.weanimal.g_animal.main.users.User;

/**
 * Created by INSI on 2015. 10. 4..
 */
public class ChatManager {
    private static List<ChatListener> listeners = new ArrayList<ChatListener>();

    public static void addChatListener(ChatListener listener){
        listeners.add(listener);
    }

    public static void callChatEvent(User user, String text){
        for(ChatListener listener : listeners)
            listener.UserChatEvent(user, text);
    }

}
