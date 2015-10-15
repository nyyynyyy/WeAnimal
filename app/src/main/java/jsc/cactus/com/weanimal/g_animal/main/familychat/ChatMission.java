package jsc.cactus.com.weanimal.g_animal.main.familychat;

import android.widget.Toast;

import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.StatusType;
import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.Mission;
import jsc.cactus.com.weanimal.g_animal.main.mission.MissionListener;
import jsc.cactus.com.weanimal.g_animal.main.mission.MissionManager;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.MessageSendMission;
import jsc.cactus.com.weanimal.g_animal.main.users.User;
import jsc.cactus.com.weanimal.g_animal.main.users.UserManager;

/**
 * Created by INSI on 2015. 10. 10..
 */
public class ChatMission implements ChatListener, MissionListener {

    private boolean isMission = false;
    private MessageSendMission mission;

    public ChatMission(){
        ChatManager.addChatListener(this);
        MissionManager.instance.addMissionListener(this);
    }

    @Override
    public void UserChatEvent(User user, String text) {
        if(!isMission)
            return;
        if(user.getId().equals(UserManager.getLocalUser().getId())){
            if(text.contains(mission.message)){
                MissionManager.instance.clearMission();
                Toast.makeText(MainActivity.mainActivity, "문자 메세지 전송 미션 성공 !!", Toast.LENGTH_SHORT).show();
                Animal.animal.getStatus().addStatus(StatusType.LOVE, 40);
            }
        }
    }

    @Override
    public void startMission(Mission mission) {
        if(mission instanceof MessageSendMission){
            this.mission = (MessageSendMission) mission;
            isMission = true;
        }
    }

    @Override
    public void clearMission() {
        isMission = false;
        mission = null;
    }
}
