<?php
require("koneksi.php");
$response = array();

if($_POST){
    $Id_toko = $_POST['id_toko']?? '';
    $statuus = $_POST['status']?? '';

    $sqlcommand = "UPDATE faktur SET status ='$statuus' WHERE id_faktur='$Id_toko'";
    $eksekusi = mysqli_query($koneksi, $sqlcommand);
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