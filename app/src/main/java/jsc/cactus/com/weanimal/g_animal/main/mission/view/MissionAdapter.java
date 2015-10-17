package jsc.cactus.com.weanimal.g_animal.main.mission.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jsc.cactus.com.weanimal.R;

/**
 * Created by INSI on 2015. 10. 7..
 */
public class MissionAdapter extends ArrayAdapter<MissionItem> {

    private Activity activity;
    private List<MissionItem> items;

    public MissionAdapter(Activity activity, int resource, List<MissionItem> objects){
        super(activity, resource, objects);

        this.activity = activity;
        items = objects;
    }

    @Override
    public void add(MissionItem object) {
        super.add(object);
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.mission_item, null);

        MissionItem item = items.get(position);
        ImageView imageView = (ImageView) view.findViewById(R.id.mission_item_img);
        TextView textView = (TextView) view.findViewById(R.id.mission_item_text);

        imageView.setImageResource(item.getImageId());
        textView.setText(item.getText());

        return view;
    }
}
