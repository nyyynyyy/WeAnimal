package jsc.cactus.com.weanimal.g_animal.main.familychat;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import jsc.cactus.com.weanimal.g_animal.main.users.User;
import jsc.cactus.com.weanimal.g_animal.main.users.UserManager;

/**
 * Created by INSI on 15. 9. 28..
 */
public class ChatDialog extends DialogFragment implements ChatListener{

    private static ChatListViewAdapter chatListViewAdapter;
    private ListView listView;
    private EditText textEdit;
    private Button acceptButton;
    private boolean isOnMission = false;

    private static List<ChatItem> items = new ArrayList<ChatItem>();

    public ChatDialog(Activity activity){
        if(chatListViewAdapter==null) {
            ChatManager.addChatListener(this);
            chatListViewAdapter = new ChatListViewAdapter(activity, R.layout.familychat_item, items);
            MainActivity.mainActivity.init2();
            new ChatManager();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_familychat, container, false);

        init(view);

        return view;
    }

    private void init(View view) {
        Log.i("jsc", "ChatDialog init");
        getDialog().setTitle("에니멀톡");
        textEdit = (EditText) view.findViewById(R.id.familychat_editText);
        acceptButton = (Button) view.findViewById(R.id.familychat_acceptButton);
        listView = (ListView) view.findViewById(R.id.family_listView);

        listView.setAdapter(chatListViewAdapter);
        setSelectionEnd();

        textEdit.addTextChangedListener(edit);
        acceptButton.setOnClickListener(confirm);
        acceptButton.setEnabled(false);
    }

    public static ChatListViewAdapter getChatListViewAdapter(){
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
            setSelectionEnd();
            textEdit.setText("");
            acceptButton.setEnabled(false);
        }
    };

    public void setSelectionEnd(){
        listView.setSelection(listView.getCount() - 1);
    }

    //
    private TextWatcher edit = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            acceptButton.setEnabled(textEdit.getText().toString().replace(" ", "").replace("\n", "").equalsIgnoreCase("") ? false : true);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void UserChatEvent(User user, String text) {
        if(listView != null)
        listView.setSelection(listView.getCount() - 1);
    }
}
