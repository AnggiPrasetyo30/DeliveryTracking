<?php

$koneksi = null;

try{
    //Config
    $host = "127.0.0.1";
    $username = "root";
    $password = "";
    $dbname = "budimas";

    //Connect
    $database = "mysql:dbname=$dbname;host=$host";
    $koneksi = new PDO($database, $username, $password);
    $koneksi->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);


} catch (PDOException $e){
    echo "Error ! " . $e->getMessage();
    die;
}

?>