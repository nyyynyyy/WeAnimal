package jsc.cactus.com.weanimal.g_animal.main.familychat.view;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.users.UserManager;

/**
 * Created by INSI on 15. 10. 1..
 */
public class ChatListViewAdapter extends ArrayAdapter<ChatItem> {

    List<ChatItem> list = new ArrayList<ChatItem>();
    Activity activity;

    public ChatListViewAdapter(Activity activity, int resource, List<ChatItem> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.list = objects;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void add(ChatItem object) {
        super.add(object);
        super.notifyDataSetChanged();
    }

    private boolean isSame(Date date, Date date2) {
        long dateT = date.getTime(), date2T = date2.getTime();
        dateT = dateT - (dateT % (60 * 1000));
        date2T = date2T - (date2T % (60 * 1000));
        return dateT == date2T;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChatItem citem = list.get(position), past_citem;
        View item = activity.getLayoutInflater().inflate(UserManager.getLocalUser().getId() == citem.getUser().getId() ? R.layout.familychat_item_me : R.layout.familychat_item_you, null);

        ImageView cicon = (ImageView) item.findViewById(R.id.item_icon);
        TextView ctext = (TextView) item.findViewById(R.id.item_text), cinfo = (TextView) item.findViewById(R.id.item_info), cname = (TextView) item.findViewById(R.id.item_name);


//        if(citem.getIconId()!= null) {
//            cicon.setImageResource(citem.getIconId());
//            cicon.setVisibility(View.VISIBLE);
//        }

        ctext.setText(citem.getText());

        if (list.size() >= position - 1 && list.size() != 0 && position - 1 != -1) {
            past_citem = list.get(position - 1);
            if (past_citem.getUser().getId() != citem.getUser().getId()) {
                if (citem.getIconId() != null) {
                    cicon.setImageResource(citem.getIconId());
                    cicon.setVisibility(View.VISIBLE);
                    cname.setText(citem.getUser().getName());
                }
            }

            if (!isSame(past_citem.getDate(), citem.getDate()))
                cinfo.setText(citem.getInfo());

        }
        cinfo.setText(citem.getInfo());

        //Animation animationY = new TranslateAnimation(0, 0, new Holder().llParent.getHeight()/4, 0);
        //animationY.setDuration(1000);

        //item.startAnimation(animationY);

        return item;
    }
}