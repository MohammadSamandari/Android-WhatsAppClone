package com.mohammadsamandari.whatsappclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent=getIntent();
        String activeUser=intent.getStringExtra("username");
        setTitle("Chat With "+activeUser);
        Log.i("Lord","Active User:" + activeUser);
    }
}