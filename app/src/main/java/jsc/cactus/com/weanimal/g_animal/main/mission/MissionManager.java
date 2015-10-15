package jsc.cactus.com.weanimal.g_animal.main.mission;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import jsc.cactus.com.weanimal.g_animal.main.mission.missions.Mission;

/**
 * Created by INSI on 15. 9. 27..
 */
public class MissionManager {

    public static MissionManager instance;
    private static Mission mission;

    private List<MissionListener> missionListeners = new ArrayList<MissionListener>();

    public MissionManager(Activity Activity) {
        instance = this;
    }

    public void startMission(Mission mission) {
        this.mission = mission;
        for (MissionListener listener : missionListeners)
            listener.startMission(mission);
    }

    public void clearMission() {
        mission = null;
        for (MissionListener listener : missionListeners)
            listener.clearMission();
    }

    public void addMissionListener(MissionListener listener) {
        if (!missionListeners.contains(listener))
            missionListeners.add(listener);
    }

    public void removeMissionListener(MissionListener listener) {
        missionListeners.remove(listener);
    }

    public Mission getCurrentMission(){
        return mission;
    }

}
