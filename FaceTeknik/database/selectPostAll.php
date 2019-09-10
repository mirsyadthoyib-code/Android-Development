<?php 

 /*
 
 penulis:	Muhammad Irsyad Thoyib
 proyek:	FaceTeknik
 
 */

	//Import File Koneksi Database
	require_once('connection.php');
	
	//Membuat SQL Query
	$sql = "SELECT post.id as id, fullName, date, image, text FROM post INNER JOIN (SELECT fullName, idFriend FROM user INNER JOIN friendlist ON user.id = friendlist.idUser) as temen ON post.idUser = temen.idFriend
	UNION
	SELECT post.id as id, fullName, date, image, text FROM post INNER JOIN user  ON post.idUser = user.id ORDER by date;";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan data kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"id"=>$row['id'],
			"fullName"=>$row['fullName'],
			"date"=>$row['date'],
			"image"=>$row['image'],
			"text"=>$row['text']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>