package jsc.cactus.com.weanimal.g_animal.main.mission.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.Mission;
import jsc.cactus.com.weanimal.g_animal.main.mission.MissionListener;
import jsc.cactus.com.weanimal.g_animal.main.mission.MissionManager;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.MessageSendMission;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.TelMission;

/**
 * Created by INSI on 2015. 10. 7..
 */
public class MissionDialog extends Dialog {

    private ListView listView;
    private static MissionAdapter missionAdapter;
    private static List<MissionItem> items = new ArrayList<MissionItem>();

    public MissionDialog(Activity activity) {
        super(activity);
        setContentView(R.layout.activity_mission);
        getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
        setCanceledOnTouchOutside(true);
        init();
    }

    public void init() {
        setTitle("미션");
        if (missionAdapter == null) {
            missionAdapter = new MissionAdapter(MainActivity.mainActivity, R.layout.mission_item, items);
            missionAdapter.add(new MissionItem(R.drawable.phoneicon, "전화 걸기"));
            missionAdapter.add(new MissionItem(R.drawable.post, "메세지 보내기"));
        }

        listView = (ListView) findViewById(R.id.mission_listView);
        listView.setAdapter(missionAdapter);

        listView.setOnItemClickListener(itemClick);
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
                    MissionManager.instance.startMission(new TelMission(60));
                    break;
                case 1:
                    MissionManager.instance.startMission(new MessageSendMission("파이팅"));
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


}
