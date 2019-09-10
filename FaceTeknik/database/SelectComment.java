implements ListView.OnItemClickListener{

    private ListView listView;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_semua_pgw);
		
        getJSON();
    }


    private void showComment(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Configuration.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Configuration.KEY_ID);
                String fullName = jo.getString(Configuration.KEY_FULLNAME);
                String date = jo.getString(Configuration.KEY_DATE);
                String comment = jo.getString(Configuration.KEY_COMMENT);

                HashMap<String,String> data = new HashMap<>();
                data.put(Configuration.KEY_ID, id);
                data.put(Configuration.KEY_FULLNAME, fullName);
                data.put(Configuration.KEY_DATE, date);
                data.put(Configuration.KEY_COMMENT, comment);
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
                showComment();
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