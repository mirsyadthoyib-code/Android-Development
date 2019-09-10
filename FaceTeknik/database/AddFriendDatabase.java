import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.HashMap;

public class AddFriendDatabase {

    public static String idUser;
    public static String idFriend;

    private void addFriend(int idUser, int idFriend){

        this.idUser = Integer.toString(idUser);
        this.idFriend = Integer.toString(idFriend);

        class AddFriend extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            String idUser = AddFriendDatabase.idUser;
            String idFriend = AddFriendDatabase.idFriend;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddFriendDatabase.this,"Menambahkan Friend...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(AddFriendDatabase.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_ID_USER, idUser);
                params.put(Configuration.KEY_ID_FRIEND, idFriend);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Configuration.URL_ADD_FRIEND, params);
                return res;
            }
        }

        AddFriend ae = new AddFriend();
        ae.execute();
    }
}