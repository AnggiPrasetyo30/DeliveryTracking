<?php
require("koneksi.php");

    $query2 = "SELECT * FROM loading WHERE status != 'Terkonfirmasi'";
    $find2 = mysqli_query($koneksi, $query2);
    $cek = mysqli_affected_rows($koneksi);

    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "data tersedia";
        $response["data"] = array();
        $id = array();

        while ($get2 = mysqli_fetch_object($find2)) {
            array_push($id, $get2->user_id);
        }

        $id = array_values(array_unique($id));

        $x = 0;
        while($x < count($id)){
        $query = "SELECT * FROM mobil WHERE user_id = '$id[$x]'";
        $find = mysqli_query($koneksi, $query);
        while ($get = mysqli_fetch_object($find)) {
            $field["user_id"] = $id[$x];
            $field["sopir"] = $get->sopir;
            $query1 = "SELECT username FROM user where user_id = '$id[$x]'";
            $find1 = mysqli_query($koneksi, $query1);
            $field["username"] = mysqli_fetch_object($find1)->username;
            array_push($response["data"], $field);
        }
        $x++;
    }
    } else {
        $response["kode"] = 0;
        $response["pesan"] = "data tidak tersedia";
    }
echo json_encode($response);
mysqli_close($koneksi);
