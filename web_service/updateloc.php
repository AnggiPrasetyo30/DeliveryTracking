<?php
require("koneksi.php");
$response = array();

if($_POST){
    $user_id = $_POST['user_id']?? '';
    $long = $_POST['longitude']?? '';
    $lat = $_POST['latitude']?? '';

    $sqlcommand = "UPDATE user SET longitude ='$long', latitude ='$lat' WHERE user_id='$user_id'";
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