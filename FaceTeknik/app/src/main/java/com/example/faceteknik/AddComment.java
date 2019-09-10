package com.example.faceteknik;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faceteknik.Database.Configuration;
import com.example.faceteknik.Database.RequestHandler;

import java.util.HashMap;

public class AddComment extends AppCompatActivity {
    private int currentId;
    private int idPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        final TextView commentText = (TextView) findViewById(R.id.commentText);
        final Button commentButton = (Button) findViewById(R.id.commentButton);
        final Button gotoHome = (Button) findViewById(R.id.gotoHomePosting);

        Intent intent = getIntent();
        if(intent.getExtras() != null)
        {
            currentId = intent.getIntExtra("currentId", 0);
            idPost = intent.getIntExtra("postId", 0);
        }

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = commentText.getText().toString();

                //DATABASE
                addComment(idPost, currentId, comment);

            }
        });

        gotoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(AddComment.this , PostActivity.class);
                mainMenuIntent.putExtra("currentId", currentId);
                mainMenuIntent.putExtra("postId", idPost);
                startActivity(mainMenuIntent);
            }
        });

    }

    private void addComment(final int idPost, final int idUser, final String comment){

        class AddCommentt extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(com.example.faceteknik.AddComment.this,"Menambahkan Comment...","",false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
//                Toast.makeText(com.example.faceteknik.AddComment.this,s,Toast.LENGTH_LONG).show();
                Intent postIntent = new Intent(com.example.faceteknik.AddComment.this , PostActivity.class);
                postIntent.putExtra("currentId", currentId);
                postIntent.putExtra("postId", idPost);
                startActivity(postIntent);
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_ID_POST, Integer.toString(idPost));
                params.put(Configuration.KEY_ID_USER, Integer.toString(idUser));
                params.put(Configuration.KEY_COMMENT, comment);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Configuration.URL_ADD_COMMENT, params);
                return res;
            }
        }

        AddCommentt ae = new AddCommentt();
        ae.execute();
    }
}
