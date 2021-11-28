<?php
require("koneksi.php");

if($_POST){
$tanggal = $_POST['tanggal'] ?? '';
$query1 = "SELECT * FROM loading WHERE tanggal = '$tanggal'";
$find1 = mysqli_query($koneksi, $query1);
$cek = mysqli_affected_rows($koneksi);

if ($cek > 0) {
	$response["kode"] = 1;
	$response["pesan"] = "data tersedia";
	$response["data"] = array();

	$draft = array();
	$tokoid = array();

	while ($get1 = mysqli_fetch_object($find1)) {
		array_push($draft, $get1->id_draft);
	}

	$x = 0;
	while($x < count($draft)){
		$query2 = "SELECT * FROM faktur WHERE id_draft = '$draft[$x]'";
		$find2 = mysqli_query($koneksi, $query2);
		while ($get2 = mysqli_fetch_object($find2)) {
			array_push($tokoid, $get2->id_toko);
		}
		$x++;
	}
	$tokoid = array_values(array_unique($tokoid));

	$y = 0;
	while($y < count($tokoid)){
		$query3 = "SELECT * FROM toko WHERE id_toko = '$tokoid[$y]' AND status = 'Check Out'";
		$find3 = mysqli_query($koneksi, $query3);
		$field["id_toko"] = $tokoid[$y];
		while ($get3 = mysqli_fetch_object($find3)) {
			$field["nama"] = $get3->nama;
			$field["alamat"] = $get3->alamat;
			$field["status"] = $get3->status;
	 
			array_push($response["data"], $field);
		}
		$y++;
	}

} else {
	$response["kode"] = 0;
	$response["pesan"] = "data tidak tersedia";
}

echo json_encode($response);
mysqli_close($koneksi);

}
