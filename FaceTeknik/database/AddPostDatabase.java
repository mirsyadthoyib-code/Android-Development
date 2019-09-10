import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.HashMap;

public class AddPostDatabase {

    public static String idUser;
    public static String image;
    public static String text;

    private void addPost(int idUser, String image, String text){

        this.idUser = Integer.toString(idUser);
        this.image = image;
        this.text = text;


        class AddPost extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            String idUser = AddPostDatabase.idUser;
            String image = AddPostDatabase.image;
            String text = AddPostDatabase.text;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddPostDatabase.this,"Menambahkan Post...","",false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(AddPostDatabase.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_ID_USER, idUser);
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