-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 30 Jan 2023 pada 18.09
-- Versi server: 10.4.27-MariaDB
-- Versi PHP: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kopi_paste_oversion`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `bahan`
--

CREATE TABLE `bahan` (
  `id_bahan` varchar(6) NOT NULL,
  `nama_bahan` varchar(50) NOT NULL,
  `jenis` enum('Hewani','Nabati','Coffee','Perasa','Cairan','Bahan Jadi') NOT NULL,
  `satuan` enum('kg','lt','ds','rc','ls','bt','gl') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `bahan`
--

INSERT INTO `bahan` (`id_bahan`, `nama_bahan`, `jenis`, `satuan`) VALUES
('BA001', 'Susu Coklat', 'Cairan', 'lt'),
('BA002', 'Kentang', 'Nabati', 'kg'),
('BA003', 'Jeruk', 'Nabati', 'kg'),
('BA004', 'Biji Kopi', 'Coffee', 'kg'),
('BA005', 'Kopi Hitam', 'Coffee', 'kg'),
('BA006', 'Kacang', 'Nabati', 'kg'),
('BA007', 'Apel', 'Nabati', 'kg'),
('BA008', 'Kecap', 'Perasa', 'bt'),
('BA009', 'Cabe Rawit', 'Nabati', 'kg'),
('BA010', 'Bawang Merah', 'Nabati', 'kg'),
('BA011', 'Bawang Putih', 'Nabati', 'kg'),
('BA012', 'Daging Ayam', 'Hewani', 'kg'),
('BA013', 'Air Mineral', 'Cairan', 'gl'),
('BA014', 'Gula Pasir', 'Perasa', 'kg'),
('BA015', 'Anggur', 'Nabati', 'kg'),
('BA016', 'Nasi', 'Nabati', 'kg'),
('BA017', 'Jamur', 'Nabati', 'kg'),
('BA018', 'Garam', 'Perasa', 'kg'),
('BA019', 'Indomie Goreng', 'Bahan Jadi', 'ds'),
('BA020', 'Kapal Api', 'Coffee', 'rc'),
('BA021', 'Telur Ayam', 'Nabati', 'ls');

--
-- Trigger `bahan`
--
DELIMITER $$
CREATE TRIGGER `update_bahan` AFTER UPDATE ON `bahan` FOR EACH ROW BEGIN 
	UPDATE detail_tr_beli 
    SET nama_bahan = NEW.nama_bahan, 
    jenis_bahan = NEW.jenis, 
    satuan_bahan = NEW.satuan 
    WHERE id_bahan = NEW.id_bahan;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_tr_beli`
--

CREATE TABLE `detail_tr_beli` (
  `id_tr_beli` varchar(8) NOT NULL,
  `id_bahan` varchar(6) DEFAULT NULL,
  `nama_bahan` varchar(50) NOT NULL,
  `jenis_bahan` enum('Hewani','Nabati','Coffee','Perasa','Cairan','Bahan Jadi') NOT NULL,
  `jumlah` float NOT NULL,
  `satuan_bahan` enum('kg','lt','ds','rc','ls','bt','gl') NOT NULL,
  `total_harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_tr_jual`
--

CREATE TABLE `detail_tr_jual` (
  `id_tr_jual` varchar(8) NOT NULL,
  `id_menu` varchar(5) DEFAULT NULL,
  `nama_menu` varchar(30) NOT NULL,
  `jenis_menu` enum('Minuman','Makanan','Original Coffee','Falvoured Coffee','Snack') NOT NULL,
  `harga_menu` int(11) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `karyawan`
--

CREATE TABLE `karyawan` (
  `id_karyawan` varchar(6) NOT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `no_telp` varchar(13) NOT NULL,
  `alamat` text NOT NULL,
  `shif` enum('Siang','Malam','No Shif') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `nama_karyawan`, `no_telp`, `alamat`, `shif`) VALUES
('KY001', 'Achmad Baihaqi', '085655864624', 'Jombang, Jawa Timur, Indonesia', 'No Shif'),
('KY002', 'Mohammad Ilham Islamy', '085690123458', 'Nganjuk, Jawa Timur', 'No Shif'),
('KY003', 'Widyasari Raisya Salsabilla', '085690239023', 'Mojokerto, Jawa Timur', 'No Shif'),
('KY004', 'Septian Yoga Pamungkas', '084590120912', 'Nganjuk, Jawa Timur', 'No Shif'),
('KY005', 'Admin', '085655864624', 'Nganjuk, Jawa Timur', 'Siang'),
('KY006', 'Karyawan', '085655869012', '13123', 'Malam');

--
-- Trigger `karyawan`
--
DELIMITER $$
CREATE TRIGGER `update_ky_beli` AFTER UPDATE ON `karyawan` FOR EACH ROW BEGIN 
	UPDATE transaksi_beli 
    SET nama_karyawan = NEW.nama_karyawan 
    WHERE id_karyawan = NEW.id_karyawan;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `update_ky_jual` AFTER UPDATE ON `karyawan` FOR EACH ROW BEGIN 
	UPDATE transaksi_jual 
    SET nama_karyawan = NEW.nama_karyawan 
    WHERE id_karyawan = NEW.id_karyawan;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `menu`
--

CREATE TABLE `menu` (
  `id_menu` varchar(5) NOT NULL,
  `nama_menu` varchar(30) NOT NULL,
  `jenis` enum('Minuman','Makanan','Original Coffee','Falvoured Coffee','Snack') NOT NULL,
  `harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `menu`
--

INSERT INTO `menu` (`id_menu`, `nama_menu`, `jenis`, `harga`) VALUES
('MN001', 'Air Mineral', 'Minuman', 4000),
('MN002', 'Kopi Hitam', 'Minuman', 3000),
('MN003', 'Kopi Susu Tubruk', 'Minuman', 5000),
('MN004', 'Teh Panas / Es', 'Minuman', 3000),
('MN005', 'Susu Panas / Es', 'Minuman', 5000),
('MN006', 'Es Lemon Tea Panas / Es', 'Minuman', 5000),
('MN007', 'Wedhang Uwuh', 'Minuman', 5000),
('MN008', 'Es Jeruk', 'Minuman', 5000),
('MN009', 'Teh Tarik', 'Minuman', 7000),
('MN010', 'Jahe Susu Panas', 'Minuman', 7000),
('MN011', 'Joss Susu Es', 'Minuman', 6000),
('MN012', 'KukuBima Susu Es', 'Minuman', 6000),
('MN013', 'Jahe Panas', 'Minuman', 5000),
('MN014', 'Vietnam Drip', 'Original Coffee', 10000),
('MN015', 'Espresso', 'Original Coffee', 8000),
('MN016', 'Long Black', 'Original Coffee', 10000),
('MN017', 'Coffee Latte', 'Original Coffee', 10000),
('MN018', 'Cappucino', 'Original Coffee', 10000),
('MN019', 'Moca Latte', 'Falvoured Coffee', 12000),
('MN020', 'Vanilla Latte', 'Falvoured Coffee', 12000),
('MN021', 'Chocholate', 'Falvoured Coffee', 10000),
('MN022', 'Salted Caramel', 'Falvoured Coffee', 10000),
('MN023', 'Taro', 'Falvoured Coffee', 10000),
('MN024', 'Green Tea Latte', 'Falvoured Coffee', 12000),
('MN025', 'Kentang Goreng', 'Snack', 8000),
('MN026', 'Cireng', 'Snack', 8000),
('MN027', 'Tahu Tuna', 'Snack', 8000),
('MN028', 'Shio May Ayam', 'Snack', 10000),
('MN029', 'Pisang Coklat', 'Snack', 10000),
('MN030', 'Pisang Nugget', 'Snack', 8000),
('MN031', 'Tempe Medoan', 'Snack', 8000),
('MN032', 'Jamur Crispy', 'Snack', 10000),
('MN033', 'Nasi Goreng', 'Makanan', 12000),
('MN034', 'Nasi Ayam Penyet', 'Makanan', 12000),
('MN035', 'Nasi Ayam Geprek', 'Makanan', 13000),
('MN036', 'Ayam Taliwang', 'Makanan', 13000),
('MN037', 'Nasi Sop', 'Makanan', 8000),
('MN038', 'Nasi Sayur Asem', 'Makanan', 8000),
('MN039', 'Nasi Tempe / Tahu Penyet', 'Makanan', 8000),
('MN040', 'Nasi Telur Penyet', 'Makanan', 10000),
('MN041', 'Indomie Goreng Telur', 'Makanan', 8000),
('MN042', 'Indomie Rebus Telur', 'Makanan', 8000),
('MN043', 'Nasi Putih', 'Makanan', 4000),
('MN045', 'Telur Ceplok / Dadar', 'Makanan', 4000);

--
-- Trigger `menu`
--
DELIMITER $$
CREATE TRIGGER `update_menu` AFTER UPDATE ON `menu` FOR EACH ROW BEGIN 
	UPDATE detail_tr_jual 
    SET nama_menu = NEW.nama_menu, jenis_menu = NEW.jenis
    WHERE id_menu = NEW.id_menu;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi_beli`
--

CREATE TABLE `transaksi_beli` (
  `id_tr_beli` varchar(8) NOT NULL,
  `id_karyawan` varchar(6) DEFAULT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `total_bahan` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `tanggal` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi_jual`
--

CREATE TABLE `transaksi_jual` (
  `id_tr_jual` varchar(8) NOT NULL,
  `id_karyawan` varchar(6) DEFAULT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `total_menu` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `total_bayar` int(11) NOT NULL,
  `total_kembalian` int(11) NOT NULL,
  `tanggal` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_login` int(3) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(70) NOT NULL,
  `level` enum('DEVELOPER','ADMIN','KARYAWAN') NOT NULL,
  `id_karyawan` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_login`, `username`, `password`, `level`, `id_karyawan`) VALUES
(1, 'dev2003', '$2a$12$ZfnIOGZkXzScrwt05DfXMuWemSONcdYNcjJ2s1A7IAXJyjDXCCl6S', 'DEVELOPER', 'KY001'),
(2, 'ilham', '$2a$12$G9rldUjX0SjYYn169mMO3uLoRJOnlYKggCG5nojWOeOYSFxRnckNm', 'DEVELOPER', 'KY002'),
(3, 'widya', '$2a$12$a2hZcCF4Of0r65cL1ef4/.GVnaCWf6/S0Z//MGtJLnSrpZaiJ7saK', 'DEVELOPER', 'KY003'),
(4, 'yoga', '$2a$12$a.WxR/w.Jd8MZ9KYWbcazuOwx9Y936X/QfhpQJwNT.Qe9ASei3PXu', 'DEVELOPER', 'KY004'),
(7, 'admin', '$2a$12$qX4DF.CWRtQuTtF41CbX9edipbjiHmL9gy1dvQnHl4co47z3RE6BK', 'ADMIN', 'KY005'),
(8, 'ky01', '$2a$12$pb6.chJadqwc1S85sYFrjOlgiw0m9w0KW6j6RVSSVDIvHS8i.FUGG', 'KARYAWAN', 'KY006');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `bahan`
--
ALTER TABLE `bahan`
  ADD PRIMARY KEY (`id_bahan`);

--
-- Indeks untuk tabel `detail_tr_beli`
--
ALTER TABLE `detail_tr_beli`
  ADD KEY `id_tr_beli` (`id_tr_beli`),
  ADD KEY `id_bahan` (`id_bahan`);

--
-- Indeks untuk tabel `detail_tr_jual`
--
ALTER TABLE `detail_tr_jual`
  ADD KEY `id_transaksi` (`id_tr_jual`),
  ADD KEY `id_menu` (`id_menu`);

--
-- Indeks untuk tabel `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`id_karyawan`);

--
-- Indeks untuk tabel `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id_menu`);

--
-- Indeks untuk tabel `transaksi_beli`
--
ALTER TABLE `transaksi_beli`
  ADD PRIMARY KEY (`id_tr_beli`),
  ADD KEY `id_karyawan` (`id_karyawan`);

--
-- Indeks untuk tabel `transaksi_jual`
--
ALTER TABLE `transaksi_jual`
  ADD PRIMARY KEY (`id_tr_jual`),
  ADD KEY `id_karyawan` (`id_karyawan`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_login`),
  ADD KEY `id_karyawan` (`id_karyawan`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id_login` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `detail_tr_beli`
--
ALTER TABLE `detail_tr_beli`
  ADD CONSTRAINT `detail_tr_beli_ibfk_1` FOREIGN KEY (`id_tr_beli`) REFERENCES `transaksi_beli` (`id_tr_beli`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_tr_beli_ibfk_2` FOREIGN KEY (`id_bahan`) REFERENCES `bahan` (`id_bahan`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `detail_tr_jual`
--
ALTER TABLE `detail_tr_jual`
  ADD CONSTRAINT `detail_tr_jual_ibfk_1` FOREIGN KEY (`id_tr_jual`) REFERENCES `transaksi_jual` (`id_tr_jual`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_tr_jual_ibfk_2` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id_menu`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `transaksi_beli`
--
ALTER TABLE `transaksi_beli`
  ADD CONSTRAINT `transaksi_beli_ibfk_1` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `transaksi_jual`
--
ALTER TABLE `transaksi_jual`
  ADD CONSTRAINT `transaksi_jual_ibfk_2` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
