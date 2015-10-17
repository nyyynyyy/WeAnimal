package jsc.cactus.com.weanimal.g_animal.main.mission;

import jsc.cactus.com.weanimal.g_animal.main.mission.missions.Mission;

/**
 * Created by INSI on 15. 9. 30..
 */
public interface MissionListener {
    public void startMission(Mission mission);
    public void clearMission(Mission mission);
    public void giveupMission(Mission mission);
}
