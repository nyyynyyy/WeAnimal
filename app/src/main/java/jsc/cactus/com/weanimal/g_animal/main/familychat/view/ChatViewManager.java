package jsc.cactus.com.weanimal.g_animal.main.familychat.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.familychat.ChatDialog;
import jsc.cactus.com.weanimal.g_animal.main.familychat.ChatMission;

/**
 * Created by INSI on 15. 9. 30..
 */
public class ChatViewManager {

    private ChatDialog familyChatDialog;
    private FragmentManager fragmentManager;
    private ImageView signImageView;
    private boolean isOnMission = false;

    public ChatViewManager(Activity activity) {
        fragmentManager = activity.getFragmentManager();
        signImageView = (ImageView) activity.findViewById(R.id.imageView);
        familyChatDialog = new ChatDialog(activity);
        new ChatMission();

        signImageView.setOnClickListener(clickSign);
    }

    private View.OnClickListener clickSign = new View.OnClickListener() {
        public void onClick(View v) {
            if (!familyChatDialog.isShowing())
                familyChatDialog.show();
        }
    };
}
