<?php
require("koneksi.php");

$response = array();

if($_POST){
    $idtoko = $_POST['id_toko'] ?? '';

    $perintah = "SELECT * FROM faktur WHERE id_toko = '$idtoko'";
    $eksekusi = mysqli_query($koneksi, $perintah);
    $cek = mysqli_affected_rows($koneksi);

    if ($cek > 0 ){
        $response ["kode"] = 1;
        $response ["pesan"] = "data tersedia";
        $response["data"] = array();
	
	    while($get = mysqli_fetch_object($eksekusi)){
		    $field["id_faktur"] = $get->id_faktur;
			$field["status"] = $get->status;
		
		    array_push($response["data"], $field);
	    }
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