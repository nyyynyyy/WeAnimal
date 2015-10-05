package jsc.cactus.com.weanimal.g_animal.main.mission.missions;

import jsc.cactus.com.weanimal.g_animal.main.mission.Mission;
import jsc.cactus.com.weanimal.g_animal.main.mission.MissionType;

/**
 * Created by INSI on 15. 9. 30..
 */
public class TelMission extends Mission {

    public int second;

    public TelMission(int second) {
        this.second = second;
        missionType = MissionType.전화걸기;
    }
}
