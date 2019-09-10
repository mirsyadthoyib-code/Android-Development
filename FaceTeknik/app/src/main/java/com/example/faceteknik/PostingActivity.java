package com.example.faceteknik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.faceteknik.Database.Configuration;
import com.example.faceteknik.Database.RequestHandler;

import java.util.HashMap;

public class PostingActivity extends AppCompatActivity {

    private String stickerSelect;
    private int currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        final TextView textPosting = (TextView) findViewById(R.id.textPosting);
        final Button buttonPosting = (Button) findViewById(R.id.buttonPosting);
        final Button gotoHome = (Button) findViewById(R.id.gotoHomePosting);

        final Button sticker1 = (Button) findViewById(R.id.stiker1);
        final Button sticker2 = (Button) findViewById(R.id.stiker2);
        final Button sticker3 = (Button) findViewById(R.id.stiker3);
        final Button sticker4 = (Button) findViewById(R.id.stiker4);

        sticker1.setBackgroundResource(Sticker.SATU.getID());
        sticker2.setBackgroundResource(Sticker.DUA.getID());
        sticker3.setBackgroundResource(Sticker.TIGA.getID());
        sticker4.setBackgroundResource(Sticker.EMPAT.getID());

        Intent intent = getIntent();
        if(intent.getExtras() != null)
        {
            currentId = intent.getExtras().getInt("currentId", 0);
//            Toast.makeText(PostingActivity.this, "currentId = " + currentId, Toast.LENGTH_LONG).show();
        }

        //PILIH STIKER//

        sticker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stickerSelect = Sticker.SATU.toString();

                sticker1.setAlpha((float) 1);
                sticker2.setAlpha((float) 0.25);
                sticker3.setAlpha((float) 0.25);
                sticker4.setAlpha((float) 0.25);
            }
        });

        sticker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stickerSelect = Sticker.DUA.toString();

                sticker1.setAlpha((float) 0.25);
                sticker2.setAlpha((float) 1);
                sticker3.setAlpha((float) 0.25);
                sticker4.setAlpha((float) 0.25);
            }
        });

        sticker3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stickerSelect = Sticker.TIGA.toString();

                sticker1.setAlpha((float) 0.25);
                sticker2.setAlpha((float) 0.25);
                sticker3.setAlpha((float) 1);
                sticker4.setAlpha((float) 0.25);
            }
        });


        sticker4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stickerSelect = Sticker.EMPAT.toString();

                sticker1.setAlpha((float) 0.25);
                sticker2.setAlpha((float) 0.25);
                sticker3.setAlpha((float) 0.25);
                sticker4.setAlpha((float) 1);
            }
        });
        //END PILIH STIKER//

        buttonPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String post = textPosting.getText().toString();

                //DATABASE//
                if(post != null && stickerSelect != null)
                {
                    addPost(currentId, stickerSelect, post);
                }
                else
                {
                    Toast.makeText(PostingActivity.this, "Teks dan Stiker harus diisi", Toast.LENGTH_LONG).show();
                }
            }
        });

        gotoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(PostingActivity.this , Menu.class);
                mainMenuIntent.putExtra("currentId", currentId);
                startActivity(mainMenuIntent);
            }
        });
    }

    private void addPost(final int idUser, final String image, final String text){

        class AddPost extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PostingActivity.this,"","",false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(PostingActivity.this,s,Toast.LENGTH_LONG).show();
                Intent mainMenuIntent = new Intent(PostingActivity.this , Menu.class);
                mainMenuIntent.putExtra("currentId", currentId);
                startActivity(mainMenuIntent);
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_ID_USER, Integer.toString(idUser));
                params.put(Configuration.KEY_IMAGE, image);
                params.put(Configuration.KEY_TEXT, text);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Configuration.URL_ADD_POST, params);
                return res;
            }
        }

        AddPost ae = new AddPost();
        ae.execute();
    }
}
