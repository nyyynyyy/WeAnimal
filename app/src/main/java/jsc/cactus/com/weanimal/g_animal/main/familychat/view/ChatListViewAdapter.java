package jsc.cactus.com.weanimal.g_animal.main.familychat.view;

import android.app.Activity;
import android.database.DataSetObservable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.DateFormat;
import jsc.cactus.com.weanimal.g_animal.main.familychat.ChatDialog;
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

        notifyDataSetChanged();
    }

    public List<ChatItem> getChatItems(){
        return list;
    }

    private boolean isSame(Date date, Date date2) {
        return date.getTime() / (60 * 1000) == date2.getTime() / (60 * 1000);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChatItem citem = list.get(position);
        UserManager.getLocalUser().getId();
        citem.getUser().getId();
        boolean isLocalUser = UserManager.getLocalUser().getId() == citem.getUser().getId();
        View item = activity.getLayoutInflater().inflate(isLocalUser ? R.layout.familychat_item_me : R.layout.familychat_item_you, null);

        TextView ctext = (TextView) item.findViewById(R.id.item_text), cinfo = (TextView) item.findViewById(R.id.item_info);

        ctext.setText(citem.getText());

        Log.i("jsc", "id: "+citem.getUser().getId() +" name: " +citem.getUser().getName()+" text: "+citem.getText()+" date: "+ DateFormat.formatDate(citem.getDate(), DateFormat.Type.SECOND));

        if (list.size() >= position - 1 && list.size() != 0 && position - 1 != -1) {
            ChatItem previous_citem = list.get(position - 1);

            if (!isSame(previous_citem.getDate(), citem.getDate())) {
                if (previous_citem.getUser().getId() != citem.getUser().getId())
                    if (!isLocalUser) {
                        ImageView cicon = (ImageView) item.findViewById(R.id.item_icon);
                        TextView cname = (TextView) item.findViewById(R.id.item_name);
                        if (citem.getIconId() != null) {
                            cicon.setImageResource(citem.getIconId());
                            cicon.setVisibility(View.VISIBLE);
                            cname.setText(citem.getUser().getName());
                        }
                    }
            }
        }

        if (list.size() > position + 1) {
            ChatItem next_citem = list.get(position + 1);
            if (next_citem.getUser().getId() != citem.getUser().getId()) {
                cinfo.setText(citem.getInfo());
                cinfo.setVisibility(View.VISIBLE);
            }

            if (!isSame(next_citem.getDate(), citem.getDate())) {
                cinfo.setText(citem.getInfo());
                cinfo.setVisibility(View.VISIBLE);
            }
        } else {
            cinfo.setText(citem.getInfo());
            cinfo.setVisibility(View.VISIBLE);
        }

        return item;
    }
}