<?php

 /*
	add post
 
 */

	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		$image = NULL;
		$text = NULL;
		
		//Mendapatkan Nilai Variable
		$idUser = $_POST['idUser'];
		$image = $_POST['image'];
		$text = $_POST['text'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO post (idUser, image, text) VALUES ('$idUser', '$image', '$text')";
		
		//Import File Koneksi database
		require_once('connection.php');
		
		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan Post';
		}else{
			echo 'Gagal Menambahkan Post';
		}
		
		mysqli_close($con);
	}
?>