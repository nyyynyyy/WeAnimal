package jsc.cactus.com.weanimal.g_animal.main.mission.missions;

import jsc.cactus.com.weanimal.g_animal.main.mission.MissionType;

/**
 * Created by INSI on 15. 9. 30..
 */
public class MessageSendMission extends Mission {

    public String message;

    public MessageSendMission(String message){
        this.message = message;
        missionType = MissionType.문자보내기;
    }
}
