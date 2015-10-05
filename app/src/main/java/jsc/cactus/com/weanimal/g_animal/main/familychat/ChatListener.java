package jsc.cactus.com.weanimal.g_animal.main.familychat;


import jsc.cactus.com.weanimal.g_animal.main.users.User;

/**
 * Created by INSI on 2015. 10. 4..
 */
public interface ChatListener {
    public void UserChatEvent(User user, String text);
}
