<?php
require("koneksi.php");
$response = array();

if($_POST){
    $id = $_POST['id_toko']??'';

    $perintah = "UPDATE toko SET status = 'Check Out' WHERE id_toko = '$id';";
    $eksekusi = mysqli_query($koneksi, $perintah);
    $cek = mysqli_affected_rows($koneksi);
    

    if ($cek > 0 ){
        $response["kode"]=1;
        $response["pesan"] = "update status berhasil";

    }else {
        $response["kode"]=2;
        $response["pesan"] = "update status gagal";
    }

}

else{ 
    $response["kode"]=0;
    $response["pesan"] = "tidak ada post";
}
echo json_encode($response);
mysqli_close($koneksi);