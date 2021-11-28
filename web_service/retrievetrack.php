<?php
require("koneksi.php");

    $query = "SELECT * FROM mobil";
    $find = mysqli_query($koneksi, $query);
    $cek = mysqli_affected_rows($koneksi);

    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "data tersedia";
        $response["data"] = array();

        while ($get = mysqli_fetch_object($find)) {
            $field["sopir"] = $get->sopir;
            $query1 = "SELECT * FROM user where user_id = '$get->user_id'";
            $find1 = mysqli_query($koneksi, $query1);
            while ($get1 = mysqli_fetch_object($find1)) {
                $field["username"] = $get1->username;
                $field["longitude"] = $get1->longitude;
                $field["latitude"] = $get1->latitude;
            }
            array_push($response["data"], $field);
        }
    } else {
        $response["kode"] = 0;
        $response["pesan"] = "data tidak tersedia";
    }
echo json_encode($response);
mysqli_close($koneksi);
