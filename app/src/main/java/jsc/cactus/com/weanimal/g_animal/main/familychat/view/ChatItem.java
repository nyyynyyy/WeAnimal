package jsc.cactus.com.weanimal.g_animal.main.familychat.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import jsc.cactus.com.weanimal.g_animal.main.users.User;

/**
 * Created by INSI on 15. 9. 30..
 */
public class ChatItem {

    private Integer id;
    private String text;
    private User user;
    private Date date;

    public ChatItem(Integer IconId, String Text, User User, Date Date) {
        id = IconId;
        text = Text;
        user = User;
        date = Date;
    }

    public Integer getIconId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public User getUser(){
        return user;
    }

    public Date getDate(){
        return date;
    }

    public String getInfo() {
        Date currentDate = new Date();
        return user.getName() + " - " + new SimpleDateFormat(
//                ((currentDate.getTime() / 1000 / 86400) - (date.getTime() / 1000 / 86400) >= 1 ? "yy년 MM월 dd일 " : "" )+
                        "a hh:mm").format(date).replace("AM", "오전").replace("PM", "오후");
    }
}