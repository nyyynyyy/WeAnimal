package jsc.cactus.com.weanimal.g_animal.main.mission.view;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.mission.Mission;
import jsc.cactus.com.weanimal.g_animal.main.mission.MissionManager;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.MessageSendMission;
import jsc.cactus.com.weanimal.g_animal.main.mission.missions.TelMission;

/**
 * Created by INSI on 2015. 10. 7..
 */
public class MissionDialog extends DialogFragment {

    private ListView listView;
    private static MissionAdapter missionAdapter;
    private static List<MissionItem> items = new ArrayList<MissionItem>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.activity_mission, container, false);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
        init(view);

        return view;
    }

    public void init(View view){
        getDialog().setTitle("미션");
        if(missionAdapter == null){
            missionAdapter = new MissionAdapter(getActivity() ,R.layout.mission_item, items);
            missionAdapter.add(new MissionItem(R.drawable.phoneicon, "전화 걸기"));
            missionAdapter.add(new MissionItem(R.drawable.post, "메세지 보내기"));
        }

        listView = (ListView) view.findViewById(R.id.mission_listView);
        listView.setAdapter(missionAdapter);

        listView.setOnItemClickListener(itemClick);
    }

    private AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    MissionManager.instance.startMission(new TelMission(60));
                    break;
                case 1:
                    MissionManager.instance.startMission(new MessageSendMission("파이팅"));
                    break;
            }

            Toast.makeText(getActivity(), missionAdapter.getItem(position).getText()+" 미션 시작!", Toast.LENGTH_SHORT).show();
            getDialog().cancel();
        }
    };
}
