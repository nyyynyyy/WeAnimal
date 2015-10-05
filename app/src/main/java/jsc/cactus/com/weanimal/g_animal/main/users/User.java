package jsc.cactus.com.weanimal.g_animal.main.users;

import java.util.Calendar;

/**
 * Created by INSI on 2015. 10. 2..
 */
public class User {
    private String id;
    private String name;
    private String birth;
    private UserGender gender;

    public User(String id, String name, String birth, UserGender gender) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserGender getGender() {
        return gender;
    }

    public boolean isTodayBirthDay() {
        Calendar today = Calendar.getInstance();
        String str[] = birth.split("-");
        return today.get(Calendar.MONTH) + 1 == Integer.parseInt(str[1]) && today.get(Calendar.DATE) == Integer.parseInt(str[2]);
    }

}
