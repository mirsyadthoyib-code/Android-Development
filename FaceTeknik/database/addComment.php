<?php

 /*
	add comment
 
 */

	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$idPost = $_POST['idPost'];
		$idUser = $_POST['idUser'];
		$comment = $_POST['comment'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO comment (idPost, idUser, comment) VALUES ('$idPost' ,'$idUser' ,'$comment')";
		
		//Import File Koneksi database
		require_once('connection.php');
		
		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan Comment';
		}else{
			echo 'Gagal Menambahkan Comment';
		}
		
		mysqli_close($con);
	}
?>