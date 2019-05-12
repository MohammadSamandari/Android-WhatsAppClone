package com.mohammadsamandari.whatsappclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        //Connecting the Views.
        txtUsername=findViewById(R.id.txtUserName);
        txtPassword=findViewById(R.id.txtPassword);

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
                }else{
                    Log.i("Lord","Sign up Error "+e.getMessage());
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loginFunction(View view){

    }
}
