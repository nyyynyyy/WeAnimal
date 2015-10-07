package jsc.cactus.com.weanimal.g_animal.main.mission.view;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import jsc.cactus.com.weanimal.R;

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

        init(view);

        return view;
    }

    public void init(View view){
        getDialog().setTitle("미션");
        if(missionAdapter == null)
            missionAdapter = new MissionAdapter(getActivity() ,R.layout.mission_item, items);

        listView = (ListView) view.findViewById(R.id.mission_listView);
        listView.setAdapter(missionAdapter);

        missionAdapter.add(new MissionItem(R.drawable.phoneicon, "전화 걸기"));
   }
}
