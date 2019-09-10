<?php
 /*
	Buat koneksi dengan database
 */
 
 //Konstanta
 define('HOST','localhost');
 define('USER','root');
 define('PASS','');
 define('DB','faceteknik');
 
 //Koneksi dengan database
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
 ?>