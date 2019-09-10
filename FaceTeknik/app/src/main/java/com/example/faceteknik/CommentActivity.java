package com.example.faceteknik;

import android.app.ProgressDialog;
import android.content.pm.ConfigurationInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.faceteknik.Database.Configuration;
import com.example.faceteknik.Database.RequestHandler;

public class CommentActivity extends AppCompatActivity{

    private ListView listView;
    private String JSON_STRING;

    public static String idPost;
    public static String idUser;
    public static String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        getJSON(1);
    }

    private void addComment(int idPost, int idUser, String comment){

        this.idPost = Integer.toString(idPost);
        this.idUser = Integer.toString(idUser);
        this.comment = comment;

        class AddComment extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            String idPost = CommentActivity.idPost;
            String idUser = CommentActivity.idUser;
            String comment = CommentActivity.comment;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CommentActivity.this,"Menambahkan Comment...","",false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(CommentActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_ID_POST, idPost);
                params.put(Configuration.KEY_ID_USER, idUser);
                params.put(Configuration.KEY_COMMENT, comment);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Configuration.URL_ADD_COMMENT, params);
                return res;
            }
        }

        AddComment ae = new AddComment();
        ae.execute();
    }

    private void showComment(int midPost){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Configuration.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Configuration.KEY_ID);
                String idPost = jo.getString(Configuration.KEY_ID_POST);
                String fullName = jo.getString(Configuration.KEY_FULLNAME);
                String date = jo.getString(Configuration.KEY_DATE);
                String comment = jo.getString(Configuration.KEY_COMMENT);

                HashMap<String,String> data = new HashMap<>();
                if(Integer.toString(midPost).equals(idPost))
                {
                    data.put(Configuration.KEY_ID, id);
                    data.put(Configuration.KEY_FULLNAME, fullName);
                    data.put(Configuration.KEY_DATE, date);
                    data.put(Configuration.KEY_COMMENT, comment);
                    list.add(data);
                }
            }

            ListAdapter adapter = new SimpleAdapter(
                    CommentActivity.this, list, R.layout.layout_listview_comment,
                    new String[]{Configuration.KEY_FULLNAME,Configuration.KEY_COMMENT, Configuration.KEY_DATE},
                    new int[]{R.id.commentFullName, R.id.comment, R.id.commentDate});

            listView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON(final int idPost){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CommentActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showComment(idPost);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Configuration.URL_GET_COMMENT);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
