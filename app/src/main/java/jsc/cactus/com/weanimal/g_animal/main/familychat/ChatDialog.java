package jsc.cactus.com.weanimal.g_animal.main.familychat;

import android.app.Activity;
import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jsc.cactus.com.weanimal.R;
import jsc.cactus.com.weanimal.ServerTime;
import jsc.cactus.com.weanimal.g_animal.main.DateFormat;
import jsc.cactus.com.weanimal.g_animal.main.familychat.view.ChatItem;
import jsc.cactus.com.weanimal.g_animal.main.familychat.view.ChatListViewAdapter;
import jsc.cactus.com.weanimal.g_animal.main.main.weanimal.MainActivity;
import jsc.cactus.com.weanimal.g_animal.main.users.User;
import jsc.cactus.com.weanimal.g_animal.main.users.UserManager;

/**
 * Created by INSI on 15. 9. 28..
 */
public class ChatDialog extends Dialog implements ChatListener {

    private static ChatListViewAdapter chatListViewAdapter;
    private static ListView listView;
    private EditText textEdit;
    private Button acceptButton;
    private boolean isOnMission = false;

    private static List<ChatItem> items = new ArrayList<ChatItem>();

    public ChatDialog(Activity activity) {
        super(activity);
        setContentView(R.layout.activity_familychat);
        chatListViewAdapter = new ChatListViewAdapter(activity, R.layout.familychat_item_you, items);
        if (chatListViewAdapter == null)
            Log.i("jsc", "챗 리스트 뷰 어뎁터 ");
        ChatManager.addChatListener(this);
        init();
        MainActivity.mainActivity.init2();
        List<ChatItem> chatItems = ChatManager.getChatData(0);
        if (chatItems != null)
            for (ChatItem chatItem : chatItems) {
                Log.i("jsc", "chatitem add");
                chatListViewAdapter.add(chatItem);
            }
        listView.setOnScrollListener(scrollListener);
    }

    public AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (listView.isShown()) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (view.getFirstVisiblePosition() == 0) {
                        //List<ChatItem> currentChatItems = chatListViewAdapter.getChatItems();
                        Log.i("jsc", "채팅 로드 !");
                    }
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };

    public List<ChatItem> getPreviousChatItems() {
        File file = new File(MainActivity.mainActivity.getFilesDir() + "/chat/");
        File[] files = file.listFiles();

        if (files.length <= 1)
            return null;

        for (int i = files.length - 1; i >= 0; i--) {
            if (files[i].getName().replace(".txt", "").equals(DateFormat.formatDate(chatListViewAdapter.getItem(0).getDate(), DateFormat.Type.DAY)))
                return ChatManager.getChatData(DateFormat.parseDate(files[i - 1].getName().replace(".txt", ""), DateFormat.Type.DAY));
        }

        return null;
    }

    private void init() {
        Log.i("jsc", "ChatDialog init");
        setTitle("애니멀톡");
        textEdit = (EditText) findViewById(R.id.familychat_editText);
        acceptButton = (Button) findViewById(R.id.familychat_acceptButton);
        listView = (ListView) findViewById(R.id.family_listView);

        listView.setAdapter(chatListViewAdapter);
        //setSelectionEnd();

        textEdit.addTextChangedListener(edit);
        acceptButton.setOnClickListener(confirm);
        acceptButton.setEnabled(false);
    }

    public static ChatListViewAdapter getChatListViewAdapter() {
        return chatListViewAdapter;
    }

    private View.OnClickListener confirm = new View.OnClickListener() {
        public void onClick(View v) {

            ChatManager.callChatEvent(UserManager.getLocalUser(), textEdit.getText().toString(), new Date(ServerTime.getTime()));

            try {
                ChatManager.sendMessage(textEdit.getText().toString());
            } catch (JSONException e) {
                Log.i("jsc", "채팅 보내는 중 오류 발생");
                e.printStackTrace();
            }
            textEdit.setText("");
            acceptButton.setEnabled(false);
        }
    };

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
        if (listView != null)
            listView.smoothScrollToPosition(listView.getAdapter().getCount() - 1);
    }
}
