package jsc.cactus.com.weanimal.g_animal.main.familychat;

import android.app.Activity;
import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.g_animal.main.familychat.view.ChatItem;
import jsc.cactus.com.weanimal.g_animal.main.familychat.view.ChatListViewAdapter;
import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;
import jsc.cactus.com.weanimal.g_animal.main.users.UserManager;

/**
 * Created by INSI on 15. 9. 28..
 */
public class ChatDialog extends Dialog {

    private static ChatListViewAdapter chatListViewAdapter;
    private ListView listView;
    private EditText textEdit;
    private Button acceptButton;
    private boolean isOnMission = false;

    private static List<ChatItem> items = new ArrayList<ChatItem>();

    public ChatDialog(Activity activity) {
        super(activity);
        setContentView(R.layout.activity_familychat);
        init();
    }

    private void init() {
        Log.i("jsc", "ChatDialog init");
        setTitle("Family Chat");
        textEdit = (EditText) findViewById(R.id.familychat_editText);
        acceptButton = (Button) findViewById(R.id.familychat_acceptButton);
        listView = (ListView) findViewById(R.id.family_listView);

        if (chatListViewAdapter == null) {
            chatListViewAdapter = new ChatListViewAdapter(MainActivity.mainActivity, R.layout.familychat_item, items);

            new ChatManager();
        }

        listView.setAdapter(chatListViewAdapter);
        listView.setSelection(listView.getCount() - 1);

        textEdit.addTextChangedListener(edit);
        acceptButton.setOnClickListener(confirm);
        acceptButton.setEnabled(false);
    }

    public static ChatListViewAdapter getChatListViewAdapter() {
        return chatListViewAdapter;
    }

    private View.OnClickListener confirm = new View.OnClickListener() {
        public void onClick(View v) {

            ChatManager.callChatEvent(UserManager.getLocalUser(), textEdit.getText().toString());
            try {
                ChatManager.sendMessage(textEdit.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listView.setSelection(listView.getCount() - 1);
            textEdit.setText("");
            acceptButton.setEnabled(false);
        }
    };

    //
    private TextWatcher edit = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            acceptButton.setEnabled(textEdit.getText().toString().replace(" ", "").equalsIgnoreCase("") ? false : true);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
