implements ListView.OnItemClickListener{

    private ListView listView;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_semua_pgw);
		
        getJSON();
    }


    private void showFriend(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Configuration.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String idUser = jo.getString(Configuration.KEY_ID_USER);
                String userName = jo.getString(Configuration.KEY_USERNAME);
                String bio = jo.getString(Configuration.KEY_BIO);

                HashMap<String,String> data = new HashMap<>();
                data.put(Configuration.KEY_ID_USER, idUser);
                data.put(Configuration.KEY_USERNAME, userName);
                data.put(Configuration.KEY_BIO, bio);
                list.add(data);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaPgw.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showFriend();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Configuration.URL_GET_FRIEND);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}