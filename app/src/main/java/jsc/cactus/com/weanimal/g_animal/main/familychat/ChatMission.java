package jsc.cactus.com.weanimal.g_animal.main.familychat;

import android.widget.Toast;

import jsc.cactus.com.weanimal.g_animal.main.animal.Animal;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.StatusType;
import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.MessageReceiveMission;
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
    private MessageSendMission sendMission;
    private MessageReceiveMission receiveMission;

    public ChatMission() {
        ChatManager.addChatListener(this);
        MissionManager.instance.addMissionListener(this);
    }

    @Override
    public void UserChatEvent(User user, String text) {
        if (!isMission)
            return;
        if (sendMission != null) {
            if (user.getId().equals(UserManager.getLocalUser().getId())) {
                if (text.contains(sendMission.message) && text.contains(sendMission.target.getName())) {
                    MissionManager.instance.clearMission();
                }
            }
        } else if (user.getId().equals(receiveMission.target.getId())) {
            if (text.contains(receiveMission.message) && text.contains(UserManager.getLocalUser().getName())) {
                MissionManager.instance.clearMission();
            }
        }
    }

    @Override
    public void startMission(Mission mission, StatusType type) {
        if (mission instanceof MessageSendMission) {
            this.sendMission = (MessageSendMission) mission;
            isMission = true;
        } else if (mission instanceof MessageReceiveMission) {
            this.receiveMission = (MessageReceiveMission) mission;
            isMission = true;
        }
    }

    @Override
    public void clearMission(Mission mission, StatusType type) {
        if (mission instanceof MessageSendMission) {
            isMission = false;
            this.sendMission = null;
            Toast.makeText(MainActivity.mainActivity, type.toKoreanString() + " 주기\n문자 메세지 전송 미션 성공 !!", Toast.LENGTH_SHORT).show();
            Animal.animal.getStatus().addStatus(type, 40);
        } else if (mission instanceof MessageReceiveMission) {
            isMission = false;
            this.receiveMission = null;
            Toast.makeText(MainActivity.mainActivity, type.toKoreanString() + " 주기\n문자 메세지 받기 미션 성공 !!", Toast.LENGTH_SHORT).show();
            Animal.animal.getStatus().addStatus(type, 40);
        }
    }

    @Override
    public void giveupMission(Mission mission) {
        isMission = false;
        receiveMission = null;
        sendMission = null;
    }
}
