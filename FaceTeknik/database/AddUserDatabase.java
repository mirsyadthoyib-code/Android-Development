import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.HashMap;

public class AddUserDatabase {

    public static String fullName;
    public static String userName;
    public static String email;
    public static String password;
    public static String tanggalLahir;
    public static String bio;

    private void addUser(String fullName, String userName, String email, String password, String tanggalLahir, String bio){

        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.tanggalLahir = tanggalLahir;
        this.bio = bio;
        
        class AddUser extends AsyncTask<Void,Void,String>{

            String fullName = AddUserDatabase.fullName;
            String userName = AddUserDatabase.userName;
            String email = AddUserDatabase.email;
            String password = AddUserDatabase.password;
            String tanggalLahir = AddUserDatabase.tanggalLahir;
            String bio = AddUserDatabase.bio;
            
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddUserDatabase.this,"Menambahkan Friend...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(AddUserDatabase.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_ID_FULLNAME, fullName);
                params.put(Configuration.KEY_ID_USERNAME, userName);
                params.put(Configuration.KEY_ID_EMAIL, email);
                params.put(Configuration.KEY_ID_PASSWORD, password);
                params.put(Configuration.KEY_ID_TANGGALLAHIR, tanggalLahir);
                params.put(Configuration.KEY_ID_BIO, bio);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Configuration.URL_ADD_USER, params);
                return res;
            }
        }

        AddUser ae = new AddUser();
        ae.execute();
    }
}