package jsc.cactus.com.weanimal.f_list;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import jsc.cactus.com.weanimal.R;

/**
 * Created by nyyyn on 2015-10-01.
 */
public class Family_ListViewAdapter extends ArrayAdapter<Family_Item> {

    private Activity activity;

    public Family_ListViewAdapter(Activity activity, int resource) {
        super(activity, resource);
        this.activity = activity;
    }

    @Override
    public void add(Family_Item object) {
        super.add(object);
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.family_item, null);

        Family_Item item = getItem(position);
        TextView title = (TextView) itemView.findViewById(R.id.activity_06_item_title), subtitle = (TextView) itemView.findViewById(R.id.activity_06_item_subtitle);

        title.setText(item.getTitle());
        subtitle.setText(item.getSubtitle());

        return itemView;
    }


}
