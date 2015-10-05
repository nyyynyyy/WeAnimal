package jsc.cactus.com.weanimal.g_animal.main.familychat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by INSI on 15. 9. 30..
 */
public class ChatItem {

    private Integer id;
    private String text, name;
    private Date date;

    public ChatItem(Integer IconId, String Text, String Name, Date Date) {
        id = IconId;
        text = Text;
        name = Name;
        date = Date;
    }

    public Integer getIconId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getName(){
        return name;
    }

    public String getInfo() {
        Date currentDate = new Date();
        return name + " - " + new SimpleDateFormat((currentDate.getTime() / 1000 / 86400) - (date.getTime() / 1000 / 86400) >= 1 ? "yy년 MM월 dd일" : "" + "a hh:mm").format(date).replace("AM", "오전").replace("PM", "오후");
    }
}