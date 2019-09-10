package com.example.faceteknik;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.faceteknik.API.NewsFeed;
import com.example.faceteknik.Database.Configuration;
import com.example.faceteknik.Database.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private ListView listView;
    private String JSON_STRING;
    private int getId;

    SharedPreferences sharedPreferences;

    private static String myEmail, myPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText usernameInput = (EditText) findViewById(R.id.emailLogin);
        final EditText passwordInput = (EditText) findViewById(R.id.passwordLogin);
        final Button loginButton = (Button) findViewById(R.id.loginLogin);
        final Button registerButton = (Button) findViewById(R.id.registerLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            final String email = usernameInput.getText().toString();
            final String password = passwordInput.getText().toString();

            getJSON(email, password);

            // ver 2
//          myEmail = usernameInput.getText().toString();
//          myPassword = passwordInput.getText().toString();
//          getJSON();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regisIntent = new Intent(LoginActivity.this , RegisterActivity.class);
                startActivity(regisIntent);
            }
        });
    }

    private void getUser(String email, String password){
        JSONObject jsonObject = null;
        boolean loginResult = false;

        try {
            System.out.println("Hasil JSON String: "+JSON_STRING);
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Configuration.TAG_JSON_ARRAY);
            String getEmail, getPassword;

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                getEmail = jo.getString(Configuration.KEY_EMAIL);
                getPassword = jo.getString(Configuration.KEY_PASSWORD);

                if (getEmail.equals(email) && getPassword.equals(password)) {
                    loginResult = true;
                    getId = Integer.valueOf(jo.getString(Configuration.KEY_ID));
                }
            }

            if (loginResult) {
                // open shared preference
                sharedPreferences = getSharedPreferences(Configuration.STATIC_PREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // put the value into the preference
                editor.putString(Configuration.STATIC_USER_ID, Integer.toString(getId));

                // commit the value (WRITE)
                editor.commit();

                // go to dashboard
                Intent mainIntent = new Intent(LoginActivity.this, Menu.class);
                mainIntent.putExtra("currentId", getId);
                startActivity(mainIntent);
            } else {
                Toast.makeText(getApplicationContext(), "Email or password is incorrect!", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON(final String email, final String password){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                getUser(email, password);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Configuration.URL_LOGIN);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}