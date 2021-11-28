<?php
require("koneksi.php");

if($_POST){
$tanggal = $_POST['tanggal'] ?? '';
$userid = $_POST['user_id'] ?? '';
$query1 = "SELECT * FROM loading WHERE (tanggal = '$tanggal' OR status = 'Pending')";
$find1 = mysqli_query($koneksi, $query1);
$cek = mysqli_affected_rows($koneksi);

if ($cek > 0) {
	$response["kode"] = 1;
	$response["pesan"] = "Data Belum Loading!";
	$uid = array();

	while ($get1 = mysqli_fetch_object($find1)) {
        array_push($uid, $get1->user_id);
	}
    $uid = array_values(array_unique($uid));

	$x = 0;
	while ($x < count($uid)) {
        if($userid == $uid[$x]){
			$response["pesan"] = "Siap Berangkat!";
		}
		$x++;
	}

} else {
	$response["kode"] = 0;
	$response["pesan"] = "data tidak tersedia";
}

echo json_encode($response);
mysqli_close($koneksi);

}
