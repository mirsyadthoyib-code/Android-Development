import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.HashMap;

public class AddCommentDatabase {

    public static String idPost;
    public static String idUser;
    public static String comment;

    private void addComment(int idPost, int idUser, String comment){

        this.idPost = Integer.toString(idPost);
        this.idUser = Integer.toString(idUser);
        this.comment = comment;

        class AddComment extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            String idPost = AddCommentDatabase.idPost;
            String idUser = AddCommentDatabase.idUser;
            String comment = AddCommentDatabase.comment;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddCommentDatabase.this,"Menambahkan Comment...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(AddCommentDatabase.this,s,Toast.LENGTH_LONG).show();
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
}