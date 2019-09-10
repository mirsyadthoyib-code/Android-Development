package com.example.faceteknik;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.faceteknik.API.Notification;
import com.example.faceteknik.API.Post;
import com.example.faceteknik.Database.Configuration;
import com.example.faceteknik.Database.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Notification extends Fragment {

    private ListView lvNotification;
    private ArrayList<Notification> mNotificationList;
    private String JSON_STRING;

    private int currentId;
    SharedPreferences sharedPreferences;

    public Tab1Notification() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1_notification, container, false);

        Intent intent = getActivity().getIntent();
        if(intent.getExtras() != null)
        {
            currentId = intent.getExtras().getInt("currentId", 0);
//            Toast.makeText(getActivity(), "currentId = " + currentId, Toast.LENGTH_LONG).show();
        }

        mNotificationList = new ArrayList<>();

        getJSON(currentId);

        ListView lv = (ListView)view.findViewById(R.id.listView);
        lvNotification = lv;

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //DATABASE ALREADY READ//
                notifRead((int) view.getTag());
            }
        });

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_tab1_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        ((Menu) getActivity()).refreshNow();
//                        Toast.makeText(getContext(), "Refresh", Toast.LENGTH_LONG).show();
                    }
                }
        );

        return view;
    }

    private void showNotification(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Configuration.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                int id = jo.getInt(Configuration.KEY_ID);
                String fullName = jo.getString(Configuration.KEY_FULLNAME);
                String date = jo.getString(Configuration.KEY_DATE);
                String alreadyRead = jo.getString(Configuration.KEY_ALREADY_READ);
                mNotificationList.add(new Notification(id, fullName, date, Integer.valueOf(alreadyRead)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Tab1Adapter adapter = new Tab1Adapter(getActivity(), mNotificationList);
        lvNotification.setAdapter(adapter);
    }

    private void getJSON(final int id){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),"","",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showNotification();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Configuration.URL_GET_NOTIFICATION + "?id=" + id);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void notifRead(final int idPost){

        class NotifRead extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"","",false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                System.out.println("ppppppppppppppppppppppppppp    " + s);
                if(s.equals("success"))
                {
                    Intent postIntent = new Intent(getActivity() , PostActivity.class);
                    postIntent.putExtra("currentId", currentId);
                    postIntent.putExtra("postId", idPost);
                    startActivity(postIntent);
                }
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_ID_POST, Integer.toString(idPost));

                RequestHandler rh = new RequestHandler();
System.out.println("fjiaejdfcvkoeadcmvoeiafmcoaienfoaefnvo" + idPost);
                String res = rh.sendPostRequest(Configuration.URL_ALREADY_READ, params);
                return res;
            }
        }

        NotifRead ae = new NotifRead();
        ae.execute();
    }
}
