import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.HashMap;

public class AddNotificationDatabase {

    public static String idPost;

    private void addNotification(int idPost){

        this.idPost = Integer.toString(idPost);

        class AddNotification extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            String idPost = AddNotificationDatabase.idPost;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddNotificationDatabase.this,"Menambahkan Friend...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(AddNotificationDatabase.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_ID_POST, idPost);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Configuration.URL_ADD_NOTIFICATION, params);
                return res;
            }
        }

        AddNotification ae = new AddNotification();
        ae.execute();
    }
}