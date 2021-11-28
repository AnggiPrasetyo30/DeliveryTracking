<?php
require("koneksi.php");

$response = array();

if($_POST){
    $user_id = $_POST['user_id'] ?? '';

    $perintah = "UPDATE loading SET status = 'Terkonfirmasi' WHERE user_id = '$user_id'";
    $eksekusi = mysqli_query($koneksi, $perintah);
    $cek = mysqli_affected_rows($koneksi);

    if ($cek > 0 ){
        $response ["kode"] = 1;
        $response ["pesan"] = "data tersedia";
	    }else{
        $response["kode"] = 0;
        $response["pesan"] = "data tidak tersedia";
    }
    
}else{
	$response["kode"] = 0;
    $response["pesan"] = "SERVER ERROR";
}

echo json_encode($response);
mysqli_close($koneksi);