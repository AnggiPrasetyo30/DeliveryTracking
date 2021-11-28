-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 19, 2021 at 03:42 PM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `budimas`
--

-- --------------------------------------------------------

--
-- Table structure for table `draft`
--

CREATE TABLE `draft` (
  `id_draft` int(11) NOT NULL,
  `gudang` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `draft`
--

INSERT INTO `draft` (`id_draft`, `gudang`) VALUES
(1, 'GD1'),
(2, 'GD1'),
(3, 'GD2'),
(4, 'GD3'),
(5, 'GD3'),
(6, 'GD2'),
(7, 'GD3'),
(8, 'GD1');

-- --------------------------------------------------------

--
-- Table structure for table `faktur`
--

CREATE TABLE `faktur` (
  `id_faktur` int(11) NOT NULL,
  `id_draft` int(11) NOT NULL,
  `id_toko` int(11) NOT NULL,
  `status` varchar(30) NOT NULL DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `faktur`
--

INSERT INTO `faktur` (`id_faktur`, `id_draft`, `id_toko`, `status`) VALUES
(2, 1, 1, 'Pending'),
(3, 1, 1, 'Pending'),
(4, 2, 2, 'Pending'),
(5, 2, 13, 'Pending'),
(6, 3, 3, 'Pending'),
(7, 3, 8, 'Pending'),
(8, 4, 7, 'Pending'),
(9, 4, 7, 'Pending'),
(10, 5, 9, 'Pending'),
(11, 6, 10, 'Pending'),
(12, 7, 11, 'Pending'),
(13, 8, 12, 'Pending');

-- --------------------------------------------------------

--
-- Table structure for table `loading`
--

CREATE TABLE `loading` (
  `id_loading` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `id_draft` int(11) NOT NULL,
  `tanggal` date NOT NULL DEFAULT current_timestamp(),
  `status` varchar(20) NOT NULL DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `loading`
--

INSERT INTO `loading` (`id_loading`, `user_id`, `id_draft`, `tanggal`, `status`) VALUES
(1, 3, 1, '2021-07-19', 'Pending'),
(2, 3, 2, '2021-07-19', 'Pending'),
(3, 3, 3, '2021-07-19', 'Pending'),
(4, 4, 4, '2021-07-19', 'Pending'),
(5, 4, 5, '2021-07-19', 'Pending'),
(6, 4, 6, '2021-07-19', 'Pending'),
(7, 6, 7, '2021-07-19', 'Pending'),
(8, 6, 8, '2021-07-19', 'Pending');

-- --------------------------------------------------------

--
-- Table structure for table `mobil`
--

CREATE TABLE `mobil` (
  `id_mobil` int(11) NOT NULL,
  `user_id` int(20) NOT NULL,
  `sopir` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `mobil`
--

INSERT INTO `mobil` (`id_mobil`, `user_id`, `sopir`) VALUES
(1, 3, 'Anggi'),
(2, 4, 'Adi'),
(3, 5, 'Bintang'),
(4, 6, 'Abah');

-- --------------------------------------------------------

--
-- Table structure for table `toko`
--

CREATE TABLE `toko` (
  `Id_toko` int(30) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `latitude` varchar(30) NOT NULL,
  `longitude` varchar(30) NOT NULL,
  `status` varchar(30) NOT NULL DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `toko`
--

INSERT INTO `toko` (`Id_toko`, `nama`, `alamat`, `latitude`, `longitude`, `status`) VALUES
(1, 'Toko A', 'A A/A, AA, AAA', '-7.5726534', '110.837232', 'Pending'),
(2, 'Toko B', 'B B/B, BB, BBB', '-7.5726534', '110.837232', 'Pending'),
(3, 'Toko C', 'C C/C, CC, CCC', '-7.5726534', '110.837232', 'Pending'),
(7, 'Toko D', 'D D/D, DD, DDD', '-7.5726534', '110.837232', 'Pending'),
(8, 'Toko E', 'E E/E, EE, EEE', '-7.5726534', '110.837232', 'Pending'),
(9, 'Toko F', 'F F/F, FF, FFF', '-7.5726534', '110.837232', 'Pending'),
(10, 'Toko G', 'G G/G, GG, GGG', '-7.5726534', '110.837232', 'Pending'),
(11, 'Toko H', 'H H/H, HH, HHH', '-7.5726534', '110.837232', 'Pending'),
(12, 'Toko I', 'I I/I, II, III', '-7.5726534', '110.837232', 'Pending'),
(13, 'Toko J', 'J J/J, JJ, JJJ', '-7.5726534', '110.837232', 'Pending');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `posisi` varchar(20) NOT NULL,
  `longitude` varchar(100) NOT NULL,
  `latitude` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `username`, `password`, `posisi`, `longitude`, `latitude`) VALUES
(1, 'pemilik', 'pemilik', 'pemilik', '110.773418', '-7.6246153'),
(2, 'kepalagudang', 'kepalagudang', 'kepala gudang', '110.773418', '-7.6246153'),
(3, 'AD123AA', 'mobil1', 'mobil', '110.773418', '-7.6246254'),
(4, 'AD123AB', 'mobil2', 'mobil', '110.7734486', '-7.6246153'),
(5, 'AD123AC', 'mobil3', 'mobil', '110.7734486', '-7.6246153'),
(6, 'AD123AD', 'mobil4', 'mobil', '110.7734486', '-7.6246153'),
(7, 'admin', 'admin', 'admin', '110.7734486', '-7.6246153');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `draft`
--
ALTER TABLE `draft`
  ADD PRIMARY KEY (`id_draft`);

--
-- Indexes for table `faktur`
--
ALTER TABLE `faktur`
  ADD PRIMARY KEY (`id_faktur`),
  ADD KEY `faktur_ibfk_2` (`id_toko`),
  ADD KEY `id_draft` (`id_draft`);

--
-- Indexes for table `loading`
--
ALTER TABLE `loading`
  ADD PRIMARY KEY (`id_loading`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `id_draft` (`id_draft`);

--
-- Indexes for table `mobil`
--
ALTER TABLE `mobil`
  ADD PRIMARY KEY (`id_mobil`),
  ADD KEY `plat` (`user_id`);

--
-- Indexes for table `toko`
--
ALTER TABLE `toko`
  ADD PRIMARY KEY (`Id_toko`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `draft`
--
ALTER TABLE `draft`
  MODIFY `id_draft` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `faktur`
--
ALTER TABLE `faktur`
  MODIFY `id_faktur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `loading`
--
ALTER TABLE `loading`
  MODIFY `id_loading` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `mobil`
--
ALTER TABLE `mobil`
  MODIFY `id_mobil` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `toko`
--
ALTER TABLE `toko`
  MODIFY `Id_toko` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `faktur`
--
ALTER TABLE `faktur`
  ADD CONSTRAINT `faktur_ibfk_2` FOREIGN KEY (`id_toko`) REFERENCES `toko` (`Id_toko`),
  ADD CONSTRAINT `faktur_ibfk_3` FOREIGN KEY (`id_draft`) REFERENCES `draft` (`id_draft`);

--
-- Constraints for table `loading`
--
ALTER TABLE `loading`
  ADD CONSTRAINT `loading_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `loading_ibfk_2` FOREIGN KEY (`id_draft`) REFERENCES `draft` (`id_draft`);

--
-- Constraints for table `mobil`
--
ALTER TABLE `mobil`
  ADD CONSTRAINT `mobil_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
