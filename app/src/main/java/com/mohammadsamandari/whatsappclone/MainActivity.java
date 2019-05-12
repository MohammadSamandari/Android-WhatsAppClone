package com.mohammadsamandari.whatsappclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    //Definig The Views.
    EditText txtUsername,txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("What's App Login");
        //Tracks this application being launched (and if this happened as the result of the user opening a push notification, this method sends along information to correlate this open with that push)
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        //Connecting the Views.
        txtUsername=findViewById(R.id.txtUserName);
        txtPassword=findViewById(R.id.txtPassword);

        //redirect if user is logged in.
        redirectIfLoggedIn();
    }

    public void signupFunction(View view){
        //Creating parse user
        ParseUser user=new ParseUser();
        user.setUsername(txtUsername.getText().toString());
        user.setPassword(txtPassword.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Log.i("Lord","User signed up successfully");
                    redirectIfLoggedIn();
                }else{
                    Log.i("Lord","Sign up Error "+e.getMessage());
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void loginFunction(View view){
        //Loggin in the user
        ParseUser.logInInBackground(txtUsername.getText().toString(),
                txtPassword.getText().toString(),
                new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e==null){
                            Log.i("Lord","Login Successfull");
                            redirectIfLoggedIn();
                        }else {
                            Log.i("Lord","Login Error " +e.getMessage());
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();;
                        }
                    }
                });
    }
    public void redirectIfLoggedIn(){
        //Going to users activiry if logged in.
        if(ParseUser.getCurrentUser()!=null){
            Intent intent=new Intent(getApplicationContext(),UsersListActivity.class);
            startActivity(intent);
            finish();
        }
    }


}
