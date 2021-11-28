<?php
require("koneksi.php");
    
    $query1 = "SELECT * FROM loading WHERE status = 'Konfirmasi'";
    $find1 = mysqli_query($koneksi, $query1);
    $cek = mysqli_affected_rows($koneksi);
    
    if ($cek > 0) {
        $response["kode"] = 1;
        $response["pesan"] = "data tersedia";
        $response["data"] = array();
        $id= array();
    
        while ($get1 = mysqli_fetch_object($find1)) {
            array_push($id, $get1->user_id);
        }

        $id = array_values(array_unique($id));
    
        $x = 0;
        while($x < count($id)){
            $query2 = "SELECT * FROM mobil WHERE user_id = '$id[$x]'";
            $find2 = mysqli_query($koneksi, $query2);
            while ($get2 = mysqli_fetch_object($find2)) {
                $field["user_id"] = $get2->user_id;
                $field["sopir"] = $get2->sopir;
                $query3 = "SELECT username FROM user where user_id = '$get2->user_id'";
                $find3 = mysqli_query($koneksi, $query3);
                $field["username"] = mysqli_fetch_object($find3)->username;
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
    

    