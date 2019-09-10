package com.example.faceteknik;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faceteknik.API.Friends;
import com.example.faceteknik.Database.Configuration;
import com.example.faceteknik.Database.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AddFriend extends AppCompatActivity {

    private boolean friendFound = false;
    private boolean friendAlreadyAdd = false;
    private boolean resultAddFriend = false;
    private boolean wantToAddFriend = false;

    private String JSON_STRING;

    public int currentId;

    public String searchGetUsername, searchGetFullname;
    public int searchGetFriendId;

    String searchUsername;

    TextView fullname;
    TextView usernameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        final EditText findFriend = (EditText) findViewById(R.id.inputUsernameFriend_activity_add_friend);

        fullname = (TextView) findViewById(R.id.fullname_activity_add_friend);
        usernameView = (TextView) findViewById(R.id.username_activity_add_friend);

        final Button addFriend = (Button) findViewById(R.id.addfriendbtn_activity_add_friend);
        final Button home = (Button) findViewById(R.id.homebtn_activity_add_friend);



        fullname.setVisibility(View.INVISIBLE);
        usernameView.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        if(intent.getExtras() != null)
        {
            currentId = intent.getExtras().getInt("currentId", 0);
            Toast.makeText(getApplicationContext(),"currentId = " + currentId, Toast.LENGTH_SHORT).show();
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuIntent = new Intent(AddFriend.this , Menu.class);
                mainMenuIntent.putExtra("currentId", currentId);
                startActivity(mainMenuIntent);
            }
        });

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get string from EditText
                searchUsername = findFriend.getText().toString().trim();

                if (!wantToAddFriend) {
                    // here is when before find the user

                    // get data from database
                    // using username as parameter which input into EditText
                    getJSON(searchUsername);

                    // event after search user from database
                    // if success

                }
                else {
                    // here is when after search and want to add user into friend list (click again to add
                    addUserAsFriend(currentId, searchGetFriendId);

                    // if add is success
                    if (resultAddFriend) {
                        // reset all flags
                        friendFound = false;
                        friendAlreadyAdd = false;
                        wantToAddFriend = false;
                        resultAddFriend = false;

//                        Toast.makeText(getApplicationContext(),"Add " + searchGetFullname + " as friend is success!", Toast.LENGTH_LONG).show();
                    } else {
                        // unsuccess add friend
//                        Toast.makeText(getApplicationContext(),"Add " + searchGetFullname + " as friend is failed, please try again later!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    /**
     * This is for search user from database
     */

    private void getUser (String username) {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Configuration.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String userName = jo.getString(Configuration.KEY_USERNAME);
                if (userName.equals(username)) {
//                    Toast.makeText(AddFriend.this, userName, Toast.LENGTH_LONG).show();
                    // add into field variable
                    searchGetUsername = userName;
                    searchGetFullname = jo.getString(Configuration.KEY_FULLNAME);
                    searchGetFriendId = jo.getInt(Configuration.KEY_ID);

                    // return flag friendFound as true
                    friendFound = true;
                }
            }

            if (friendFound) {
                // set text into layout
                usernameView.setText(searchGetUsername);
                fullname.setText(searchGetFullname);

                fullname.setVisibility(View.VISIBLE);
                usernameView.setVisibility(View.VISIBLE);

                // want to add into friend list
                // using my user id as parameter
                getJSON2(currentId);
            }
            else {
                // unsuccess search user
                Toast.makeText(getApplicationContext(),"User is not found!", Toast.LENGTH_SHORT).show();
                fullname.setVisibility(View.INVISIBLE);
                usernameView.setVisibility(View.INVISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON(final String username){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddFriend.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                getUser(username);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Configuration.URL_GET_USER);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    /**
     * This is for search friend if already in friend list or not
     */

    private void showFriend(int id){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Configuration.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                int idUser = jo.getInt(Configuration.KEY_ID_USER);
                int idFriend = jo.getInt(Configuration.KEY_ID_FRIEND);

                if(searchGetFriendId == idFriend)
                {
                    // friend already in friend list
                    friendAlreadyAdd = true;
                }
            }

            // if friend is already in friend list
            if (!friendAlreadyAdd) {
                wantToAddFriend = true;
                Toast.makeText(getApplicationContext(),"User has found, Click Again to Add into friend list!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),"Already added this friend!", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON2(final int id){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddFriend.this,"Mengambil Data","Mohon Tunggu...",false,false);
//                Toast.makeText(getApplicationContext(),"disini", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showFriend(id);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Configuration.URL_GET_FRIEND + "?id=" + id);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    /**
     * This is when friend to add into database
     */

    private void addUserAsFriend (final int idUser, final int idFriend ) {

        class AddFriendIntoFriendList extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddFriend.this,"Mohon tunggu...","",false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                resultAddFriend = true;
                finish();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();

                params.put(Configuration.KEY_ID_USER, String.valueOf(idUser));
                params.put(Configuration.KEY_ID_FRIEND, String.valueOf(idFriend));

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Configuration.URL_ADD_FRIEND, params);
                return res;
            }
        }

        AddFriendIntoFriendList ae = new AddFriendIntoFriendList();
        ae.execute();
    }
}
