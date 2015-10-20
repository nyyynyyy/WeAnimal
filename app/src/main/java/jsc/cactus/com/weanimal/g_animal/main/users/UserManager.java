package jsc.cactus.com.weanimal.g_animal.main.users;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by INSI on 2015. 10. 2..
 */
public class UserManager {

    private static List<User> users = new ArrayList<User>();
    private static String localUserId;

    public UserManager(User localUser) {
        addUser(localUser);
        localUserId = localUser.getId();
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static User getUser(String id) {
        for (User user : users)
            if (user.getId().equals(id))
                return user;

        return null;
    }

    public static User getUserByName(String name) {
        for (User user : users)
            if (user.getName().equals(name))
                return user;
        return null;
    }

    public static User getLocalUser() {
        return getUser(localUserId);
    }
}
