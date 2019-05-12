package com.mohammadsamandari.whatsappclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends AppCompatActivity {

    String activeUser = "";
    EditText txtChat;
    ArrayList<String> messages = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ListView messageListView;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        txtChat = findViewById(R.id.txtChat);

        Intent intent = getIntent();
        activeUser = intent.getStringExtra("username");
        setTitle("Chat With " + activeUser);
        Log.i("Lord", "Active User:" + activeUser);

        //Initiallizing the variables:
        messageListView = findViewById(R.id.messagesListView);
        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, messages);
        messageListView.setAdapter(arrayAdapter);

        //Loading All The Messages.
        loadAllTheMessages();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                loadAllTheMessages();
            }
        },5000,5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    public void sendChatFunction(View view) {
        ParseObject message = new ParseObject("Message");
        message.put("sender", ParseUser.getCurrentUser().getUsername());
        message.put("recipient", activeUser);
        message.put("message", txtChat.getText().toString());

        messages.add(txtChat.getText().toString());
        txtChat.setText("");
        arrayAdapter.notifyDataSetChanged();

        message.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(ChatActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Lord", "Message sent Error " + e.getMessage());
                    Toast.makeText(ChatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadAllTheMessages() {
        //Loading all the previous messasges:
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Message");
        query1.whereEqualTo("sender", ParseUser.getCurrentUser().getUsername());
        query1.whereEqualTo("recipient", activeUser);

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Message");
        query2.whereEqualTo("sender", activeUser);
        query2.whereEqualTo("recipient", ParseUser.getCurrentUser().getUsername());

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);
        queries.add(query2);

        ParseQuery<ParseObject> query = ParseQuery.or(queries);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        messages.clear();
                        for (ParseObject message : objects) {
                            String messageContent = message.getString("message");
                            if (!message.getString("sender").equals(ParseUser.getCurrentUser().getUsername())) {
                                messageContent = "> " + messageContent;
                            }
                            messages.add(messageContent);
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.i("Lord", "Get Messages Error " + e.getMessage());
                }
            }
        });
    }
}
