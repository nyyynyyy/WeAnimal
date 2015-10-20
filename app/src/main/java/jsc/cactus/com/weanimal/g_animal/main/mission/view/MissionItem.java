package jsc.cactus.com.weanimal.g_animal.main.mission.view;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.mission.MissionType;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.MessageReceiveMission;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.MessageSendMission;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.Mission;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.TelMission;

/**
 * Created by INSI on 2015. 10. 7..
 */
public class MissionItem {

    private Mission mission;

    public MissionItem(Mission mission) {
        this.mission = mission;
    }

    public Integer getImageId() {
        MissionType missionType = mission.missionType;
        return missionType == MissionType.전화걸기 ? R.drawable.phoneicon : R.drawable.post;
    }

    public Mission getMission(){
        return mission;
    }

    public String getText() {
        String str = "";
        if (mission instanceof TelMission) {
            TelMission telMission = (TelMission) mission;
            str = telMission.second + "초 전화 걸기";
        } else if (mission instanceof MessageSendMission) {
            MessageSendMission messageSendMission = (MessageSendMission) mission;
            str = '"'+messageSendMission.message + '"' +"라고 보내기";
        } else {
            MessageReceiveMission messageReceiveMission = (MessageReceiveMission) mission;
            str = '"'+messageReceiveMission.message + '"' +"라는 말 받기";
        }
        return mission.target.getName() + "에게 "+str;
    }
}
