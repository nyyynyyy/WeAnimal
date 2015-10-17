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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = activity.getLayoutInflater().inflate(R.layout.familychat_item, null);

        ChatItem citem = list.get(position);
        CardView cardview = (CardView) item.findViewById(R.id.item_cardview);
        ImageView cicon = (ImageView) item.findViewById(R.id.item_icon);
        TextView ctext = (TextView) item.findViewById(R.id.item_text), cinfo = (TextView) item.findViewById(R.id.item_info);

        if (citem.getUser().getName().equals(UserManager.getLocalUser().getName())) {
            cardview.setCardBackgroundColor(Color.argb(255, 235, 215, 0));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity  = Gravity.RIGHT;
            cardview.setLayoutParams(params);
        }

        if(citem.getIconId()!=null){
            cicon.setImageResource(citem.getIconId());
            cicon.setVisibility(View.VISIBLE);
        }else
            cicon.setVisibility(View.GONE);

        ctext.setText(citem.getText());
        cinfo.setText(citem.getInfo());

        //Animation animationY = new TranslateAnimation(0, 0, new Holder().llParent.getHeight()/4, 0);
        //animationY.setDuration(1000);

        //item.startAnimation(animationY);

        return item;
    }
}