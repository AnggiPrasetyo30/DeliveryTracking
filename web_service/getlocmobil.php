<?php
require("koneksi.php");
$response = array();

if($_POST){
    $user_id = $_POST['user_id']?? '';

    $sqlcommand = "SELECT * FROM user WHERE user_id='$user_id'";
    $eksekusi = mysqli_query($koneksi, $sqlcommand);
    $cek = mysqli_affected_rows($koneksi);
    

    if ($cek > 0 ){
        $response["pesan"] = "update status berhasil";

        while ($get = mysqli_fetch_object($eksekusi)) {
			$response["longitude"] = $get->longitude;
			$response["latitude"] = $get->latitude;
        }

    }else {
        $response["pesan"] = "update status gagal";
    }
}

else{ 
    $response["pesan"] = "tidak ada post";
}

echo json_encode($response);
mysqli_close($koneksi);