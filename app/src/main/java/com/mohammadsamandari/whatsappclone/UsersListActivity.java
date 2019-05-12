package com.mohammadsamandari.whatsappclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //Defining the Views.
    ListView userListView;
    ArrayList<String> users = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        //Initiallizing the variables:
        userListView = findViewById(R.id.userListView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users);
        userListView.setAdapter(arrayAdapter);

        //Populating the list view with parse query.
        loadUsersFromParse();

        //Listview on item click listener.
        userListView.setOnItemClickListener(this);
    }

    private void loadUsersFromParse() {
        users.clear();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseUser user : objects) {
                            users.add(user.getUsername());
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.i("Lord", "Getting Users Error " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Jumping to chat activity with the username of the user that has been clicked.
        Intent intent=new Intent(getApplicationContext(),ChatActivity.class);
        intent.putExtra("username",users.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(getApplicationContext());
        inflater.inflate(R.menu.users_list_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.btnLogout){
            Log.i("Lord","Loging Out");
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        Log.i("Lord","Log Out Successfull");
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }else{
                        Log.i("Lord","Log Out Error "+e.getMessage());
                        Toast.makeText(UsersListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
