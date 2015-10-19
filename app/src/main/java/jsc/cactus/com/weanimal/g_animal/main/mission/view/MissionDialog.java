package jsc.cactus.com.weanimal.g_animal.main.mission.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.SoundUtil;
import jsc.cactus.com.weanimal.g_animal.main.animal.status.StatusType;
import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.Mission;
import jsc.cactus.com.weanimal.g_animal.main.mission.MissionListener;
import jsc.cactus.com.weanimal.g_animal.main.mission.MissionManager;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.MessageSendMission;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.TelMission;

/**
 * Created by INSI on 2015. 10. 7..
 */
public class MissionDialog extends Dialog implements MissionListener {

    private ListView listView;
    private static MissionAdapter missionAdapter;
    private static List<MissionItem> items = new ArrayList<MissionItem>();
    private boolean isMission = false;

    private StatusType type;

    private long missionTime;

    public MissionDialog(Activity activity) {
        super(activity);
        setContentView(R.layout.activity_mission);
        getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
        setCanceledOnTouchOutside(true);
        init();
    }

    public void init() {
        if (missionAdapter == null) {
            MissionManager.instance.addMissionListener(this);
            missionAdapter = new MissionAdapter(MainActivity.mainActivity, R.layout.mission_item, items);
            missionAdapter.add(new MissionItem(R.drawable.phoneicon, "전화 걸기"));
            missionAdapter.add(new MissionItem(R.drawable.post, "메세지 보내기"));
        }

        listView = (ListView) findViewById(R.id.mission_listView);
        listView.setAdapter(missionAdapter);

        listView.setOnItemClickListener(itemClick);
    }


    public void show(StatusType type) {
        super.show();
        this.type = type;
        setTitle((type==StatusType.FOOD?"먹이":type==StatusType.WATER?"물":"애정")+" 미션");
    }

    private AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (MissionManager.instance.getCurrentMission() != null) {
                Toast.makeText(MainActivity.mainActivity, "당신은 이미 미션 중 입니다.\n미션 : " + MissionManager.instance.getCurrentMission().missionType.name(), Toast.LENGTH_SHORT).show();
                return;
            }
            switch (position) {
                case 0:
                    MissionManager.instance.startMission(new TelMission(6), type);
                    break;
                case 1:
                    MissionManager.instance.startMission(new MessageSendMission("파이팅"), type);
                    break;
                case 2:
                    MissionManager.instance.startMission(new MessageSendMission("파이팅"), type);
                    break;
            }

            Toast.makeText(MainActivity.mainActivity, missionAdapter.getItem(position).getText() + " 미션 시작!", Toast.LENGTH_SHORT).show();
            cancel();
        }
    };

//    @Override
//    public void onResume() {
//
//        if(!isMission)
//            return;
//        if(missionTime - new Date().getTime() <= ((TelMission)MissionManager.instance.getCurrentMission()).second)
//            MissionManager.instance.clearMission();
//    }

    @Override
    public void startMission(Mission mission, StatusType type) {
        if (!(mission instanceof TelMission))
            return;
        isMission = true;
        missionTime = new Date().getTime();
    }

    @Override
    public void clearMission(Mission mission, StatusType type) {
        if (!(mission instanceof TelMission))
            return;
        SoundUtil.playSound(R.raw.pong);
        //Toast.makeText(MainActivity.mainActivity, "미션 성공", Toast.LENGTH_SHORT);
    }

    @Override
    public void giveupMission(Mission mission) {
        isMission = false;
    }


}
