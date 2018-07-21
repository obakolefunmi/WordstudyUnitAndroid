package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Common;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Meetingclass;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.MyResponse;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Notification;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Sender;
import com.cuwordstudy.solomolaiye.wordstudyunit.Remote.ApiService;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Discussion extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    FirebaseAuth auth;
    FirebaseListAdapter<Meetingclass> adapter;
    LinearLayout no_meetting;
    RelativeLayout yes_metting;
    EditText message_from_user;
    ImageView send_message;
    ListView meeting_list;
    DatabaseReference myRef;
    ApiService mService;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meeting, menu);
        MenuItem call = menu.findItem(R.id.menu_call);
        MenuItem clear = menu.findItem(R.id.menu_cancel);

        if (auth.getCurrentUser().getEmail().equals("wordstudycu@gmail.com"))
        {
            call.setVisible(true);
            clear.setVisible(true);
        }
        else
        {
            call.setVisible(false);
            clear.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_call:
                Notification notification = new Notification("Word Study online meeting has started","Word Study Unit");
                Sender sender =  new Sender("/topics/Meeting",notification);
                mService.sendNotification(sender)
                        .enqueue(new Callback<MyResponse>() {
                            @Override
                            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                            }

                            @Override
                            public void onFailure(Call<MyResponse> call, Throwable t) {
                                Toast.makeText(Discussion.this, "connect to the internet and restart the meeting",Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.menu_cancel:
                myRef.removeValue();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        auth = FirebaseAuth.getInstance();
        Common.currentToken = FirebaseInstanceId.getInstance().getToken();

        FirebaseMessaging.getInstance().subscribeToTopic("Meeting");

        mService = Common.getFCMClient();


        message_from_user = findViewById(R.id.meeting_message);
        no_meetting = findViewById(R.id.no_meeting);
        yes_metting = findViewById(R.id.yes_meeting);
        send_message = findViewById(R.id.meeting_send);
        meeting_list = findViewById(R.id.metting_list);
        meeting_list.setOnItemLongClickListener(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(message_from_user.getText().toString().trim())) {
                    myRef.push().setValue(new Meetingclass(message_from_user.getText().toString(),
                            auth.getCurrentUser().getDisplayName()));
                    message_from_user.setText("");

                } else
                    message_from_user.setError("Required");
            }
        });

        displayChat();
    }
    private void displayChat() {

        adapter = new FirebaseListAdapter<Meetingclass>(this, Meetingclass.class, R.layout.message_item,myRef )
        {  
            TextView message_text,message_user,message_time;
            RelativeLayout message_shifter;

            @Override
            protected void populateView(View v, Meetingclass model, int position) {
                List<Meetingclass> newmessage = new ArrayList<>();

                message_text = v.findViewById(R.id.message_item_text);
                message_user = v.findViewById(R.id. message_user);
                message_time = v.findViewById(R.id.message_time);
                message_shifter = v.findViewById(R.id.message_shifter);
                message_text.setText(model.getMessageText().toString());
                message_user.setText(model.getMessageUser().toString());
                message_time.setText(DateFormat.format("hh:mm",model.getMessageTime()));
                newmessage.add(model);
            

                for (int i = 0; i < newmessage.size(); i++) {
                    if (newmessage.get(i).getMessageUser().toString().equals(auth.getCurrentUser().getDisplayName().toString())){
                        message_user.setGravity(Gravity.RIGHT);
                        message_shifter.setGravity(Gravity.RIGHT);
                        message_user.setVisibility(View.GONE);

                    }
                    else
                    {
                        message_user.setGravity(Gravity.LEFT);
                        message_shifter.setGravity(Gravity.LEFT);
                        message_user.setVisibility(View.VISIBLE); 
                    }
            }
            }
           

        };

        meeting_list.setAdapter(adapter);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        //pop up code here
        //var announsel = view.Id;
        final DatabaseReference messageselected_id = adapter.getRef(i);

        final Meetingclass model = adapter.getItem(i);
        android.widget.PopupMenu menu = new android.widget.PopupMenu(this, view);

        // with Android 3 need to use MenuInfater to inflate the menu
        //  menu.MenuInflater.Inflate(Resource.Menu.popup, menu.Menu);

        // with Android 4 Inflate can be called directly on the menu
        menu.inflate(R.menu.popup);

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                //Console.WriteLine("{0} selected", arg1.Item.TitleFormatted);
                if (menuItem.getItemId() == R.id.delete) {
                    if (MainActivity.currmail.equals("wordstudycu@gmail.com") || model.getMessageUser().toString().equals(auth.getCurrentUser().getDisplayName().toString())) {
                        Toast.makeText(Discussion.this, "deleting", Toast.LENGTH_SHORT).show();
                        messageselected_id.removeValue();
                    //    new PlaceholderFragment.DelCommment(comentselected, PlaceholderFragment.this).execute(Common.getAddressSingleComment(comentselected));
                    } else {
                        Toast.makeText(Discussion.this, "You can't delete this message", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });

        // Android 4 now has the DismissEvent
        menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
             //   String textss = "\"" + textid + "\"";
               // new PlaceholderFragment.GetComentSpecifictData(PlaceholderFragment.this).execute(Common.getAddresApiAnsSpecificComment(textss));

            }
        });

        menu.show();


        return true;
    }
}