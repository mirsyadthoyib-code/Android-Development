package com.example.faceteknik;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faceteknik.API.User;
import com.example.faceteknik.Database.Configuration;
import com.example.faceteknik.Database.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.faceteknik.Database.Configuration.STATIC_USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab3Profile extends Fragment {

    private User myUser;
    private String JSON_STRING;

    TextView username;
    TextView fullname;
    TextView email;
    TextView birthdate;
    TextView bio;

    Button btnLogout;
    SharedPreferences sharedPreferences;

    private int currentId;

    public Tab3Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3_profile, container, false);

        Intent intent = getActivity().getIntent();
        if(intent.getExtras() != null)
        {
            currentId = intent.getExtras().getInt("currentId", 0);
//            Toast.makeText(getActivity(), "currentId = " + currentId, Toast.LENGTH_LONG).show();
        }

        username = (TextView) view.findViewById(R.id.username_profile);
        fullname = (TextView) view.findViewById(R.id.fullname_profile);
        email = (TextView) view.findViewById(R.id.email_profile);
        birthdate = (TextView) view.findViewById(R.id.birthdate_profile);
        bio = (TextView) view.findViewById(R.id.bio_profile);

        btnLogout = (Button) view.findViewById(R.id.btn_tab3_logout);

        // call getJson to get user identity
        getJSON(currentId);

        // log out process
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(getContext());
                myBuilder.setTitle("Apakah anda ingin logout?");
                myBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // system logout
                        if (removeKeyPreference()) {
                            getActivity().finish();
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        } else {
                            Toast.makeText(getContext(), "Logout failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                myBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                myBuilder.show();
            }
        });

        return view;
    }

    private boolean removeKeyPreference () {

        try {
            // open shared preference
            sharedPreferences = this.getActivity().getSharedPreferences(Configuration.STATIC_PREFERENCE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // remove key
            editor.remove(STATIC_USER_ID);

            // commit
            editor.commit();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void showUser(int id){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Configuration.TAG_JSON_ARRAY);
            if(result != null)
            {
                for(int i = 0; i<result.length(); i++){
                    JSONObject jo = result.getJSONObject(i);
                    int idUser = jo.getInt(Configuration.KEY_ID);
                    String userName = jo.getString(Configuration.KEY_USERNAME);
                    String fullName = jo.getString(Configuration.KEY_FULLNAME);
                    String emailData = jo.getString(Configuration.KEY_EMAIL);
                    String tanggalLahir = jo.getString(Configuration.KEY_TANGGALLAHIR);
                    String bioData = jo.getString(Configuration.KEY_BIO);

                    if(idUser == id)
                    {
                        // display identity in activity
                        fullname.setText(fullName);
                        username.setText(userName);
                        email.setText(emailData);
                        birthdate.setText(tanggalLahir);
                        bio.setText(bioData);
                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getJSON(final int id){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showUser(id);
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


}
