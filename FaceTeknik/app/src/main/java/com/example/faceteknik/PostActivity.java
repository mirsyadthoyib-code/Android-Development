package com.example.faceteknik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.faceteknik.Database.Configuration;
import com.example.faceteknik.Database.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.faceteknik.API.Comment;

import java.util.ArrayList;
import java.util.HashMap;

public class PostActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private ArrayList<Comment> mCommentList;

    private int currentId;
    private int idPost;
    private String JSON_STRING;

    ImageView stickerPost;
    TextView isiPost;
    TextView usernamePost;
    TextView datePost;
    TextView commentPost;
    ListView commentList;
    ListView lvComment;

    String stickerSelect = "Sticker1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Intent intent = getIntent();
        if(intent.getExtras() != null)
        {
            currentId = intent.getIntExtra("currentId", 0);
            idPost = intent.getIntExtra("postId", 0);
//            Toast.makeText(PostActivity.this, "currentId = " + currentId + "postId" + idPost, Toast.LENGTH_LONG).show();
        }

        //DATABASE NOTIFICATION STATUS ALREADY READ//

        stickerPost = (ImageView) findViewById(R.id.stickerPost);
        isiPost = (TextView) findViewById(R.id.postIsi);
        usernamePost = (TextView) findViewById(R.id.usernamePost);
        datePost = (TextView) findViewById(R.id.datePost);
        commentPost= (TextView) findViewById(R.id.comment);
        commentList = (ListView) findViewById(R.id.commentList);

        mCommentList = new ArrayList<>();

        getJSON(idPost);
        getComment(idPost);
//        isiPost.setText("isi post" + idPost);
//        usernamePost.setText("username");

                    Toast.makeText(PostActivity.this, "currentId = " + stickerSelect + "postId" + idPost, Toast.LENGTH_LONG).show();

        ListView lv = (ListView) findViewById(R.id.commentList);
        lvComment = lv;

        commentPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent commentIntent = new Intent(PostActivity.this , AddComment.class);
                commentIntent.putExtra("currentId", currentId);
                commentIntent.putExtra("postId", idPost);
                startActivity(commentIntent);
            }
        });

        final Button button3 = (Button) findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commentIntent = new Intent(PostActivity.this , Menu.class);
                commentIntent.putExtra("currentId", currentId);
                startActivity(commentIntent);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    private void showPost(){
        JSONObject jsonObject = null;
        ArrayList<String> data = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Configuration.TAG_JSON_ARRAY);

            JSONObject jo = result.getJSONObject(0);
            String id = jo.getString(Configuration.KEY_ID);
            String fullName = jo.getString(Configuration.KEY_FULLNAME);
            String date = jo.getString(Configuration.KEY_DATE);
            String image = jo.getString(Configuration.KEY_IMAGE);
            String text = jo.getString(Configuration.KEY_TEXT);

            data.add(id);
            data.add(fullName);
            data.add(date);
            data.add(image);
            data.add(text);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        usernamePost.setText(data.get(1));
        datePost.setText(data.get(2));
        stickerSelect = data.get(3);

        if(stickerSelect.equals("Sticker1"))
            stickerPost.setBackgroundResource(R.drawable.sticker1);
        else if(stickerSelect.equals("Sticker2"))
            stickerPost.setBackgroundResource(R.drawable.sticker2);
        else if(stickerSelect.equals("Sticker3"))
            stickerPost.setBackgroundResource(R.drawable.sticker3);
        else
            stickerPost.setBackgroundResource(R.drawable.sticker4);

        isiPost.setText(data.get(4));

    }

    private void getJSON(final int id){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PostActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
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
                String s = rh.sendGetRequest(Configuration.URL_GET_POST_ONE + "?id=" + id);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showComment(){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Configuration.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                int id = jo.getInt(Configuration.KEY_ID);
                String fullName = jo.getString(Configuration.KEY_FULLNAME);
                String date = jo.getString(Configuration.KEY_DATE);
                String comment = jo.getString(Configuration.KEY_COMMENT);

                mCommentList.add(new Comment(id, fullName, date, comment));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        PostAdapter adapter = new PostAdapter(PostActivity.this, mCommentList);
        lvComment.setAdapter(adapter);
    }

    private void getComment(final int id){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PostActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showComment();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Configuration.URL_GET_COMMENT + "?id=" + id);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
