package jsc.cactus.com.weanimal.g_animal.main.mission.missions;

import jsc.cactus.com.weanimal.g_animal.main.mission.MissionType;
import jsc.cactus.com.weanimal.g_animal.main.users.User;

/**
 * Created by INSI on 15. 9. 30..
 */
public class TelMission extends Mission {

    public int second;

    public TelMission(int second, User target) {
        this.second = second;
        this.target = target;
        missionType = MissionType.전화걸기;
    }
}
