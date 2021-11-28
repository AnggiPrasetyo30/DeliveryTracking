<?php

include 'koneksia.php';

if($_POST){

    $username = $_POST['username'] ?? '';
    $password = $_POST['password'] ?? '';

    $response = [];

    $userQuery = $koneksi->prepare("SELECT * FROM user where username = ?");
    $userQuery->execute(array($username));
    $query = $userQuery->fetch();

    if($userQuery->rowCount() == 0){
        $response['status'] = false;
        $response['pesan'] = "Username tidak terdaftar";
    } else {
        $passwordDB = $query['password'];

        if(strcmp($password,$passwordDB) === 0){
            $response['status'] = true;
            $response['pesan'] = "Login Berhasil";
            $response['data'] = [
                'user_id' => $query['user_id'],
                'username' => $query['username'],
                'posisi' => $query['posisi']
            ];
    } else {
            $response['status'] = false;
            $response['pesan'] = "Password anda salah";
        }
    }

    $json = json_encode($response, JSON_PRETTY_PRINT);
    echo $json;
}