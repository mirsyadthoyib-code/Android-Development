<?php

 /*
	add friend
 
 */

	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$idUser = $_POST['idUser'];
		$idFriend = $_POST['idFriend'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO friendlist (idUser, idFriend) VALUES ('$idUser', '$idFriend')";
		
		//Import File Koneksi database
		require_once('connection.php');
		
		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan Friend';
		}else{
			echo 'Gagal Menambahkan Friend';
		}
		
		mysqli_close($con);
	}
?>