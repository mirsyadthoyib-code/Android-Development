package com.example.faceteknik;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.faceteknik.API.TextPost;
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
public class Tab2Newsfeed extends Fragment {

    private ListView lvPost;
    private Tab2Adapter adapter;
    private ArrayList<TextPost> mPostList;
    private String JSON_STRING;

    public static int scrollPosition;
    private Button buttonAddPost;

    private int currentId;

    public Tab2Newsfeed() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2_newsfeed, container, false);

        Intent intent = getActivity().getIntent();
        if(intent.getExtras() != null)
        {
            currentId = intent.getExtras().getInt("currentId", 0);
//            Toast.makeText(getActivity(), "currentId = " + currentId, Toast.LENGTH_LONG).show();
        }


        mPostList = new ArrayList<>();

        getJSON(currentId);

        ListView lv = (ListView)view.findViewById(R.id.listView2);
        lvPost = lv;



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Clicked =" + view.getTag(), Toast.LENGTH_LONG).show();

                Intent postIntent = new Intent(getActivity() , PostActivity.class);
                postIntent.putExtra("currentId", currentId);
                postIntent.putExtra("postId", (int) view.getTag());
                startActivity(postIntent);
            }
        });

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_tab2_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        ((Menu) getActivity()).refreshNow();
//                        Toast.makeText(getContext(), "Refresh", Toast.LENGTH_LONG).show();
                    }
                }
        );

        buttonAddPost = (Button) view.findViewById(R.id.addpostbtn_fragment_tab2_newsfeed);
        buttonAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPostIntent = new Intent(getActivity() , PostingActivity.class);
                addPostIntent.putExtra("currentId", currentId);
                startActivity(addPostIntent);
            }
        });

        return view;
    }

    private void showPost(){
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
                String image = jo.getString(Configuration.KEY_IMAGE);
                String text = jo.getString(Configuration.KEY_TEXT);

                mPostList.add(new TextPost(id, fullName, date, text, image));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new Tab2Adapter(getActivity(), mPostList);
        lvPost.setAdapter(adapter);
    }

    private void getJSON(final int id){
        class GetJSON extends AsyncTask<Void,Void,String> {

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
                showPost();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Configuration.URL_GET_POST_ALL + "?id=" + id);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

}
