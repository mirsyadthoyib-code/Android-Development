private void addPost(int id, int idUser, FILE image, String text){

	class AddPost extends AsyncTask<Void,Void,String>{

		ProgressDialog loading;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loading = ProgressDialog.show(MainActivity.this,"Menambahkan Post...",false,false);
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			loading.dismiss();
			Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
		}

		@Override
		protected String doInBackground(Void... v) {
			HashMap<String,String> params = new HashMap<>();
			params.put(Configuration.KEY_ID, id);
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

private void addComment(int id, int idPost, int idUser, String comment){

	class AddComment extends AsyncTask<Void,Void,String>{

		ProgressDialog loading;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loading = ProgressDialog.show(MainActivity.this,"Menambahkan Comment...",false,false);
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			loading.dismiss();
			Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
		}

		@Override
		protected String doInBackground(Void... v) {
			HashMap<String,String> params = new HashMap<>();
			params.put(Configuration.KEY_ID, id);
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

private void addFriend(int idUser, int idFriend){

	class AddFriend extends AsyncTask<Void,Void,String>{

		ProgressDialog loading;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loading = ProgressDialog.show(MainActivity.this,"Menambahkan Friend...",false,false);
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			loading.dismiss();
			Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
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

private void addNotification(int id, int idPost){

	class AddNotification extends AsyncTask<Void,Void,String>{

		ProgressDialog loading;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loading = ProgressDialog.show(MainActivity.this,"Menambahkan Friend...",false,false);
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			loading.dismiss();
			Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
		}

		@Override
		protected String doInBackground(Void... v) {
			HashMap<String,String> params = new HashMap<>();
			params.put(Configuration.KEY_ID, id);
			params.put(Configuration.KEY_ID_POST, idPost);

			RequestHandler rh = new RequestHandler();
			String res = rh.sendPostRequest(Configuration.URL_ADD_NOTIFICATION, params);
			return res;
		}
	}

	AddNotification ae = new AddNotification();
	ae.execute();
}

private void addUser(int id, String fullName, String userName, String email, String password, date tanggalLahir, String bio){

	class AddUser extends AsyncTask<Void,Void,String>{

		ProgressDialog loading;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loading = ProgressDialog.show(MainActivity.this,"Menambahkan Friend...",false,false);
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			loading.dismiss();
			Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
		}

		@Override
		protected String doInBackground(Void... v) {
			HashMap<String,String> params = new HashMap<>();
			params.put(Configuration.KEY_ID, id);
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

@Override
public void onClick(View v) {
	addPost();
}