 /*
 
 penulis:	Muhammad Irsyad Thoyib
 proyek:	FaceTeknik
 
 */

public class Configuration {

    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
	
    //PENTING! JANGAN LUPA GANTI IP SESUAI IP LOCALHOST
	public static final String IP="127.0.0.0"
	//URL to send data to database
    public static final String URL_ADD_POST="http://"+ IP +"/FaceTeknik/addPost.php";
    public static final String URL_ADD_COMMENT="http://"+ IP +"/FaceTeknik/addComment.php";
    public static final String URL_ADD_FRIEND="http://"+ IP +"/FaceTeknik/addFriend.php";
    public static final String URL_ADD_NOTIFICATION="http://"+ IP +"/FaceTeknik/addNotification.php";
    public static final String URL_ADD_USER="http://"+ IP +"/FaceTeknik/addUser.php";
	
	//URL to get data from database
    public static final String URL_GET_POST_ALL="http://"+ IP +"/FaceTeknik/selectPostAll.php";
	public static final String URL_GET_POST_ONE="http://"+ IP +"/FaceTeknik/selectPostOne.php";
    public static final String URL_GET_COMMENT="http://"+ IP +"/FaceTeknik/selectComment.php";
    public static final String URL_GET_FRIEND="http://"+ IP +"/FaceTeknik/selectFriend.php";
    public static final String URL_GET_NOTIFICATION="http://"+ IP +"/FaceTeknik/selectNotification.php";
    public static final String URL_GET_USER="http://"+ IP +"/FaceTeknik/selectUser.php";
	
	//URL to login
    public static final String URL_LOGIN="http://"+ IP +"/FaceTeknik/login.php";
	
	//URL to set already read notification
    public static final String URL_LOGIN="http://"+ IP +"/FaceTeknik/readNotification.php";
	
    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_ID = "id";
    public static final String KEY_ID_USER = "idUser";
    public static final String KEY_ID_POST = "idPost";
    public static final String KEY_ID_FRIEND = "idFriend";
    public static final String KEY_ALREADY_READ = "alreadyRead";
    public static final String KEY_NAMA = "name";
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_DATE = "date";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_TEXT = "text";
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_USERNAME = "userName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TANGGALLAHIR = "tanggalLahir";
    public static final String KEY_BIO = "bio";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";

    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";
}