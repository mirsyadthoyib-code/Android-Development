<?php

 /*
	add user
 
 */

	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$fullName = $_POST['fullName'];
		$userName = $_POST['userName'];
		$email = $_POST['email'];
		$password = $_POST['password'];
		$tanggalLahir = $_POST['tanggalLahir'];
		$bio = $_POST['bio'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO user (fullName, userName, email, password, tanggalLahir, bio) VALUES ('$fullName', '$userName', '$email', '$password', '$tanggalLahir', '$bio')";
		
		//Import File Koneksi database
		require_once('connection.php');
		
		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan User';
		}else{
			echo 'Gagal Menambahkan User';
		}
		
		mysqli_close($con);
	}
?>