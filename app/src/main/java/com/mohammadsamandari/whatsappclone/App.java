package com.mohammadsamandari.whatsappclone;

import com.parse.Parse;
import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("123")
                // if desired
                .clientKey("123")
                .server("https://lord-whatsappclone.herokuapp.com/parse")
                .build()
        );
    }
}