-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 14 Des 2022 pada 17.30
-- Versi server: 10.4.24-MariaDB
-- Versi PHP: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kopi_paste`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `bahan`
--

CREATE TABLE `bahan` (
  `id_bahan` varchar(6) NOT NULL,
  `nama_bahan` varchar(50) NOT NULL,
  `jenis` enum('Makanan','Minuman','Coffee','Buah') NOT NULL,
  `stok` int(5) NOT NULL,
  `satuan` enum('gr','ml') NOT NULL,
  `harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `bahan`
--

INSERT INTO `bahan` (`id_bahan`, `nama_bahan`, `jenis`, `stok`, `satuan`, `harga`) VALUES
('BA000', 'Coklat', 'Makanan', 5025, 'gr', 57500),
('BA001', 'Susu Coklat', 'Minuman', 5550, 'ml', 25000),
('BA002', 'Kentang', 'Makanan', 3550, 'gr', 50000),
('BA003', 'Jeruk', 'Minuman', 9600, 'gr', 25000),
('BA005', 'Biji Kopi', 'Minuman', 6870, 'gr', 120000),
('BA007', 'Jeruk Bali', 'Coffee', 12500, 'gr', 13500),
('BA008', 'Kopi Hitam', 'Makanan', 5400, 'gr', 122250),
('BA011', 'Kacang', 'Makanan', 4700, 'gr', 5000),
('BA015', 'Apel', 'Minuman', 4500, 'gr', 5000),
('BA018', 'Kecap', 'Makanan', 6100, 'ml', 1500),
('BA019', 'Cabe Rawit', 'Makanan', 11920, 'gr', 5000),
('BA020', 'Bawang Merah', 'Makanan', 5000, 'gr', 25000),
('BA021', 'Bawang Putih', 'Makanan', 7000, 'gr', 10000),
('BA022', 'Daging Ayam', 'Makanan', 4500, 'gr', 20000),
('BA025', 'Air Mineral', 'Minuman', 17200, 'ml', 3000),
('BA026', 'Gula Pasir', 'Makanan', 7060, 'gr', 14000),
('BA027', 'anggur', 'Buah', 5400, 'gr', 20000),
('BA028', 'Nasi', 'Makanan', 10000, 'gr', 8000),
('BA029', 'Jamur', 'Makanan', 9000, 'gr', 15000),
('BA030', 'Garam', 'Makanan', 7950, 'gr', 6000),
('BA031', 'apel', 'Buah', 8900, 'gr', 10000),
('BA032', 'Daging Babi', 'Makanan', 15400, 'gr', 150000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_menu`
--

CREATE TABLE `detail_menu` (
  `id_menu` varchar(5) DEFAULT NULL,
  `id_bahan` varchar(6) DEFAULT NULL,
  `nama_bahan` varchar(50) NOT NULL,
  `quantity` int(5) NOT NULL,
  `satuan` enum('gr','ml') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `detail_menu`
--

INSERT INTO `detail_menu` (`id_menu`, `id_bahan`, `nama_bahan`, `quantity`, `satuan`) VALUES
('MN001', 'BA003', 'Jeruk', 200, 'gr'),
('MN001', 'BA025', 'Air Mineral', 250, 'ml'),
('MN001', 'BA026', 'Gula Pasir', 20, 'gr'),
('MN013', 'BA019', 'Cabe Rawit', 80, 'gr'),
('MN013', 'BA026', 'Gula Pasir', 10, 'gr'),
('MN013', 'BA002', 'Kentang', 150, 'gr'),
('MN002', 'BA007', 'Jeruk Manis', 100, 'gr'),
('MN011', 'BA000', 'Coklat', 20, 'gr'),
('MN011', 'BA005', 'Biji Kopi', 10, 'gr'),
('MN011', 'BA025', 'Air Mineral', 200, 'ml'),
('MN011', 'BA026', 'Gula Pasir', 50, 'gr'),
('MN017', 'BA028', 'Nasi', 150, 'gr'),
('MN017', 'BA019', 'Cabe Rawit', 10, 'gr'),
('MN017', 'BA018', 'Kecap', 20, 'ml'),
('MN018', 'BA002', 'Kentang', 100, 'gr'),
('MN018', 'BA019', 'Cabe Rawit', 50, 'gr'),
('MN018', 'BA018', 'Kecap', 20, 'ml'),
('MN019', 'BA002', 'Kentang', 150, 'gr'),
('MN019', 'BA030', 'Garam', 50, 'gr'),
('MN020', 'BA031', 'apel', 100, 'gr'),
('MN020', 'BA026', 'Gula Pasir', 100, 'gr'),
('MN012', 'BA011', 'Kacang', 50, 'gr'),
('MN012', 'BA018', 'Kecap', 10, 'ml'),
('MN012', 'BA022', 'Daging Ayam', 250, 'gr'),
('MN012', 'BA019', 'Cabe Rawit', 60, 'gr'),
('MN015', 'BA026', 'Gula Pasir', 100, 'gr'),
('MN015', 'BA027', 'anggur', 150, 'gr'),
('MN015', 'BA025', 'Air Mineral', 300, 'ml'),
('MN016', 'BA001', 'Susu Coklat', 50, 'ml'),
('MN016', 'BA005', 'Biji Kopi', 20, 'gr'),
('MN016', 'BA025', 'Air Mineral', 250, 'ml'),
('MN016', 'BA026', 'Gula Pasir', 30, 'gr'),
('MN014', 'BA005', 'Biji Kopi', 50, 'gr'),
('MN014', 'BA026', 'Gula Pasir', 20, 'gr'),
('MN014', 'BA025', 'Air Mineral', 300, 'ml'),
('MN021', 'BA032', 'Daging Babi', 200, 'gr'),
('MN022', 'BA032', 'Daging Babi', 100, 'gr'),
('MN023', 'BA032', 'Daging Babi', 400, 'gr');

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_supplier`
--

CREATE TABLE `detail_supplier` (
  `id_supplier` varchar(6) NOT NULL,
  `id_bahan` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `detail_supplier`
--

INSERT INTO `detail_supplier` (`id_supplier`, `id_bahan`) VALUES
('SP002', 'BA005'),
('SP003', 'BA005'),
('SP003', 'BA003'),
('SP010', 'BA025'),
('SP010', 'BA005'),
('SP010', 'BA025'),
('SP010', 'BA022'),
('SP010', 'BA001'),
('SP010', 'BA000'),
('SP010', 'BA027'),
('SP010', 'BA031'),
('SP008', 'BA003'),
('SP008', 'BA007'),
('SP008', 'BA015'),
('SP008', 'BA032'),
('SP008', 'BA030'),
('SP008', 'BA029'),
('SP008', 'BA011'),
('SP008', 'BA000'),
('SP005', 'BA015'),
('SP005', 'BA019'),
('SP005', 'BA025'),
('SP005', 'BA028'),
('SP005', 'BA030'),
('SP005', 'BA021'),
('SP005', 'BA018'),
('SP004', 'BA005'),
('SP004', 'BA026'),
('SP001', 'BA007'),
('SP001', 'BA002'),
('SP001', 'BA030'),
('SP001', 'BA032'),
('SP001', 'BA001');

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_tr_beli`
--

CREATE TABLE `detail_tr_beli` (
  `id_tr_beli` varchar(7) NOT NULL,
  `id_bahan` varchar(6) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `detail_tr_beli`
--

INSERT INTO `detail_tr_beli` (`id_tr_beli`, `id_bahan`, `jumlah`, `harga`) VALUES
('TRB0001', 'BA001', 20, 10),
('TRB0001', 'BA001', 10, 1),
('TRB0009', 'BA007', 4, 54000),
('TRB0010', 'BA002', 10, 500000),
('TRB0011', 'BA015', 2, 10000),
('TRB0011', 'BA007', 5, 67500),
('TRB0011', 'BA003', 5, 125000),
('TRB0012', 'BA015', 5, 25000),
('TRB0013', 'BA015', 5, 25000),
('TRB0014', 'BA015', 5, 25000),
('TRB0015', 'BA003', 1, 25000),
('TRB0017', 'BA007', 2, 27000),
('TRB0018', 'BA000', 500, 287500),
('TRB0019', 'BA000', 100, 57500),
('TRB0020', 'BA000', 1000, 57500),
('TRB0022', 'BA000', 2000, 115000),
('TRB0023', 'BA007', 5000, 67500),
('TRB0024', 'BA025', 3000, 9000),
('TRB0024', 'BA022', 2000, 40000),
('TRB0025', 'BA025', 2000, 6000),
('TRB0025', 'BA022', 2000, 40000),
('TRB0027', 'BA005', 1000, 120000),
('TRB0028', 'BA000', 1000, 57500),
('TRB0029', 'BA027', 1000, 20000),
('TRB0030', 'BA027', 2000, 40000),
('TRB0031', 'BA015', 2000, 10000),
('TRB0032', 'BA005', 5000, 600000),
('TRB0032', 'BA003', 1000, 25000),
('TRB0033', 'BA031', 1000, 10000),
('TRB0034', 'BA031', 5000, 50000),
('TRB0001', 'BA000', 1, 5000),
('TRB0035', 'BA000', 1000, 57500),
('TRB0036', 'BA000', 1000, 57500),
('TRB0037', 'BA000', 1000, 57500),
('TRB0038', 'BA000', 1000, 57500),
('TRB0039', 'BA002', 3000, 150000),
('TRB0040', 'BA002', 3000, 150000),
('TRB0041', 'BA000', 1000, 57500),
('TRB0042', 'BA005', 1000, 120000),
('TRB0043', 'BA003', 5000, 125000),
('TRB0044', 'BA025', 10000, 30000),
('TRB0044', 'BA027', 5000, 100000),
('TRB0045', 'BA002', 5000, 250000),
('TRB0045', 'BA007', 8000, 108000),
('TRB0046', 'BA032', 8000, 1200000),
('TRB0046', 'BA011', 5000, 25000),
('TRB0046', 'BA029', 8000, 120000),
('TRB0047', 'BA032', 8000, 1200000),
('TRB0048', 'BA018', 5000, 7500),
('TRB0048', 'BA021', 5000, 50000),
('TRB0048', 'BA028', 7000, 56000),
('TRB0048', 'BA030', 4000, 24000),
('TRB0048', 'BA019', 10000, 50000),
('TRB0048', 'BA025', 10000, 30000),
('TRB0049', 'BA026', 6000, 84000),
('TRB0050', 'BA003', 8000, 200000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_tr_jual`
--

CREATE TABLE `detail_tr_jual` (
  `id_tr_jual` varchar(7) NOT NULL,
  `id_menu` varchar(5) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `detail_tr_jual`
--

INSERT INTO `detail_tr_jual` (`id_tr_jual`, `id_menu`, `jumlah`, `harga`) VALUES
('TRJ0001', 'MN011', 2, 5000),
('TRJ0001', 'MN002', 2, 15000),
('TRJ0007', 'MN002', 2, 20000),
('TRJ0008', 'MN020', 1, 5000),
('TRJ0009', 'MN002', 2, 20000),
('TRJ0010', 'MN001', 1, 5000),
('TRJ0011', 'MN020', 1, 5000),
('TRJ0012', 'MN020', 10, 50000),
('TRJ0013', 'MN020', 10, 50000),
('TRJ0014', 'MN016', 1, 12000),
('TRJ0014', 'MN019', 4, 40000),
('TRJ0014', 'MN001', 2, 10000),
('TRJ0014', 'MN015', 1, 2000),
('TRJ0015', 'MN011', 6, 90000),
('TRJ0016', 'MN001', 4, 20000),
('TRJ0016', 'MN002', 1, 10000),
('TRJ0017', 'MN001', 1, 5000),
('TRJ0018', 'MN002', 2, 20000),
('TRJ0019', 'MN001', 2, 10000),
('TRJ0020', 'MN014', 2, 6000),
('TRJ0020', 'MN016', 2, 24000),
('TRJ0021', 'MN014', 2, 6000),
('TRJ0022', 'MN012', 1, 15000),
('TRJ0023', 'MN012', 20, 300000),
('TRJ0023', 'MN013', 10, 120000),
('TRJ0024', 'MN011', 20, 300000),
('TRJ0025', 'MN018', 5, 40000),
('TRJ0026', 'MN011', 2, 30000),
('TRJ0027', 'MN001', 6, 30000),
('TRJ0028', 'MN012', 5, 75000),
('TRJ0028', 'MN018', 5, 40000),
('TRJ0028', 'MN016', 5, 60000),
('TRJ0029', 'MN021', 10, 350000),
('TRJ0029', 'MN022', 30, 600000),
('TRJ0030', 'MN021', 10, 350000),
('TRJ0031', 'MN022', 10, 200000),
('TRJ0032', 'MN022', 5, 100000),
('TRJ0033', 'MN021', 5, 175000),
('TRJ0034', 'MN022', 5, 100000),
('TRJ0035', 'MN002', 5, 50000),
('TRJ0035', 'MN012', 3, 45000),
('TRJ0035', 'MN021', 2, 70000),
('TRJ0035', 'MN022', 2, 40000),
('TRJ0035', 'MN011', 10, 150000),
('TRJ0035', 'MN019', 9, 90000),
('TRJ0035', 'MN018', 8, 64000),
('TRJ0035', 'MN015', 10, 20000),
('TRJ0035', 'MN016', 1, 12000),
('TRJ0036', 'MN021', 4, 140000),
('TRJ0036', 'MN022', 1, 20000),
('TRJ0037', 'MN001', 1, 5000),
('TRJ0037', 'MN013', 5, 60000),
('TRJ0037', 'MN015', 1, 2000),
('TRJ0038', 'MN019', 1, 10000),
('TRJ0038', 'MN018', 1, 8000),
('TRJ0038', 'MN020', 1, 5000),
('TRJ0038', 'MN023', 1, 350000),
('TRJ0038', 'MN001', 1, 5000),
('TRJ0038', 'MN011', 1, 15000),
('TRJ0039', 'MN002', 11, 110000),
('TRJ0039', 'MN014', 2, 6000),
('TRJ0040', 'MN014', 5, 15000),
('TRJ0040', 'MN017', 3, 36000),
('TRJ0040', 'MN012', 2, 30000),
('TRJ0041', 'MN001', 1, 5000),
('TRJ0003', 'MN012', 5, 45000),
('TRJ0003', 'MN016', 3, 45000),
('TRJ0003', 'MN021', 5, 5000),
('TRJ0003', 'MN015', 2, 15000),
('TRJ0003', 'MN016', 20, 15000),
('TRJ0003', 'MN017', 10, 45000),
('TRJ0003', 'MN020', 5, 0),
('TRJ0003', 'MN019', 5, 5000),
('TRJ0003', 'MN002', 8, 5000),
('TRJ0042', 'MN002', 5, 50000),
('TRJ0043', 'MN019', 5, 50000),
('TRJ0043', 'MN012', 2, 30000),
('TRJ0044', 'MN018', 4, 32000),
('TRJ0045', 'MN001', 2, 10000),
('TRJ0046', 'MN011', 3, 45000),
('TRJ0046', 'MN016', 2, 24000),
('TRJ0046', 'MN013', 1, 12000),
('TRJ0047', 'MN001', 7, 35000),
('TRJ0047', 'MN014', 2, 6000),
('TRJ0048', 'MN011', 4, 60000),
('TRJ0049', 'MN002', 2, 20000),
('TRJ0050', 'MN019', 5, 50000),
('TRJ0050', 'MN011', 7, 105000),
('TRJ0050', 'MN014', 2, 6000),
('TRJ0051', 'MN001', 1, 5000),
('TRJ0051', 'MN002', 1, 10000),
('TRJ0051', 'MN011', 1, 15000),
('TRJ0051', 'MN012', 1, 15000),
('TRJ0051', 'MN013', 1, 12000),
('TRJ0051', 'MN014', 1, 3000),
('TRJ0051', 'MN020', 1, 5000),
('TRJ0051', 'MN019', 1, 10000),
('TRJ0053', 'MN001', 6, 30000),
('TRJ0053', 'MN020', 4, 20000),
('TRJ0053', 'MN011', 6, 90000),
('TRJ0053', 'MN016', 2, 24000),
('TRJ0053', 'MN013', 2, 24000),
('TRJ0053', 'MN021', 3, 105000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `karyawan`
--

CREATE TABLE `karyawan` (
  `id_karyawan` varchar(6) NOT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `no_telp` varchar(13) NOT NULL,
  `alamat` text NOT NULL,
  `shif` enum('Siang (07:00-17:59)','Malam (18:00-22:59)') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `nama_karyawan`, `no_telp`, `alamat`, `shif`) VALUES
('KY001', 'Achmad Baihaqi', '085655864624', 'Jombang, Jawa Timur, Indonesia', 'Siang (07:00-17:59)'),
('KY002', 'Mohammad Ilham', '085690123458', 'Nganjuk, Jawa Timur', 'Malam (18:00-22:59)'),
('KY003', 'Widyasari Raisya', '085690239023', 'Mojokerto, Jawa Timur', 'Siang (07:00-17:59)'),
('KY004', 'Septian Yoga', '085690238912', 'Nganjuk, Jawa Timur', 'Siang (07:00-17:59)');

-- --------------------------------------------------------

--
-- Struktur dari tabel `log_tr_beli`
--

CREATE TABLE `log_tr_beli` (
  `id_tr_beli` varchar(7) NOT NULL,
  `id_bahan` varchar(6) NOT NULL,
  `jumlah` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `log_tr_beli`
--

INSERT INTO `log_tr_beli` (`id_tr_beli`, `id_bahan`, `jumlah`) VALUES
('TRB0001', 'BA000', 1),
('TRB0002', 'BA001', 1),
('TRB0001', 'BA002', 500),
('TRB0002', 'BA031', 20),
('TRB0038', 'BA000', 1000),
('TRB0039', 'BA002', 3000),
('TRB0040', 'BA002', 3000),
('TRB0041', 'BA000', 1000),
('TRB0042', 'BA005', 1000),
('TRB0043', 'BA003', 5000),
('TRB0044', 'BA025', 10000),
('TRB0044', 'BA027', 5000),
('TRB0045', 'BA002', 5000),
('TRB0045', 'BA007', 8000),
('TRB0046', 'BA032', 8000),
('TRB0046', 'BA011', 5000),
('TRB0046', 'BA029', 8000),
('TRB0047', 'BA032', 8000),
('TRB0048', 'BA018', 5000),
('TRB0048', 'BA021', 5000),
('TRB0048', 'BA028', 7000),
('TRB0048', 'BA030', 4000),
('TRB0048', 'BA019', 10000),
('TRB0048', 'BA025', 10000),
('TRB0049', 'BA026', 6000),
('TRB0050', 'BA003', 8000);

--
-- Trigger `log_tr_beli`
--
DELIMITER $$
CREATE TRIGGER `tambah_stok` AFTER INSERT ON `log_tr_beli` FOR EACH ROW BEGIN
	UPDATE bahan
    SET stok = stok + NEW.jumlah
    WHERE id_bahan = NEW.id_bahan;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `log_tr_jual`
--

CREATE TABLE `log_tr_jual` (
  `id_tr_jual` varchar(7) NOT NULL,
  `id_menu` varchar(5) NOT NULL,
  `id_bahan` varchar(6) NOT NULL,
  `quantity` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `log_tr_jual`
--

INSERT INTO `log_tr_jual` (`id_tr_jual`, `id_menu`, `id_bahan`, `quantity`) VALUES
('TRJ0014', 'MN016', 'BA001', 50),
('TRJ0014', 'MN016', 'BA025', 250),
('TRJ0014', 'MN016', 'BA026', 15),
('TRJ0014', 'MN016', 'BA005', 20),
('TRJ0014', 'MN019', 'BA002', 600),
('TRJ0014', 'MN019', 'BA030', 200),
('TRJ0014', 'MN001', 'BA003', 400),
('TRJ0014', 'MN001', 'BA025', 500),
('TRJ0014', 'MN001', 'BA026', 40),
('TRJ0014', 'MN015', 'BA026', 100),
('TRJ0014', 'MN015', 'BA027', 150),
('TRJ0015', 'MN011', 'BA000', 120),
('TRJ0015', 'MN011', 'BA025', 1200),
('TRJ0015', 'MN011', 'BA026', 300),
('TRJ0015', 'MN011', 'BA005', 60),
('TRJ0016', 'MN001', 'BA003', 800),
('TRJ0016', 'MN001', 'BA025', 1000),
('TRJ0016', 'MN001', 'BA026', 80),
('TRJ0016', 'MN002', 'BA007', 100),
('TRJ0017', 'MN001', 'BA003', 200),
('TRJ0017', 'MN001', 'BA025', 250),
('TRJ0017', 'MN001', 'BA026', 20),
('TRJ0018', 'MN002', 'BA007', 200),
('TRJ0019', 'MN001', 'BA003', 400),
('TRJ0019', 'MN001', 'BA025', 500),
('TRJ0019', 'MN001', 'BA026', 40),
('TRJ0020', 'MN014', 'BA025', 500),
('TRJ0020', 'MN014', 'BA026', 40),
('TRJ0020', 'MN014', 'BA005', 60),
('TRJ0020', 'MN016', 'BA001', 100),
('TRJ0020', 'MN016', 'BA025', 500),
('TRJ0020', 'MN016', 'BA026', 30),
('TRJ0020', 'MN016', 'BA005', 40),
('TRJ0021', 'MN014', 'BA025', 500),
('TRJ0021', 'MN014', 'BA026', 40),
('TRJ0021', 'MN014', 'BA005', 60),
('TRJ0022', 'MN012', 'BA011', 50),
('TRJ0022', 'MN012', 'BA022', 250),
('TRJ0022', 'MN012', 'BA018', 10),
('TRJ0022', 'MN012', 'BA019', 80),
('TRJ0023', 'MN012', 'BA011', 1000),
('TRJ0023', 'MN012', 'BA022', 5000),
('TRJ0023', 'MN012', 'BA018', 200),
('TRJ0023', 'MN012', 'BA019', 1600),
('TRJ0023', 'MN013', 'BA002', 1500),
('TRJ0023', 'MN013', 'BA026', 100),
('TRJ0023', 'MN013', 'BA019', 800),
('TRJ0024', 'MN011', 'BA000', 400),
('TRJ0024', 'MN011', 'BA025', 4000),
('TRJ0024', 'MN011', 'BA026', 1000),
('TRJ0024', 'MN011', 'BA005', 200),
('TRJ0025', 'MN018', 'BA002', 500),
('TRJ0025', 'MN018', 'BA018', 100),
('TRJ0025', 'MN018', 'BA019', 250),
('TRJ0026', 'MN011', 'BA000', 40),
('TRJ0026', 'MN011', 'BA025', 400),
('TRJ0026', 'MN011', 'BA026', 100),
('TRJ0026', 'MN011', 'BA005', 20),
('TRJ0027', 'MN001', 'BA003', 1200),
('TRJ0027', 'MN001', 'BA025', 1500),
('TRJ0027', 'MN001', 'BA026', 120),
('TRJ0028', 'MN012', 'BA011', 250),
('TRJ0028', 'MN012', 'BA022', 1250),
('TRJ0028', 'MN012', 'BA018', 50),
('TRJ0028', 'MN012', 'BA019', 300),
('TRJ0028', 'MN018', 'BA002', 500),
('TRJ0028', 'MN018', 'BA018', 100),
('TRJ0028', 'MN018', 'BA019', 250),
('TRJ0028', 'MN016', 'BA001', 250),
('TRJ0028', 'MN016', 'BA025', 1250),
('TRJ0028', 'MN016', 'BA026', 150),
('TRJ0028', 'MN016', 'BA005', 100),
('TRJ0029', 'MN021', 'BA032', 2000),
('TRJ0029', 'MN022', 'BA032', 3000),
('TRJ0030', 'MN021', 'BA032', 2000),
('TRJ0031', 'MN022', 'BA032', 1000),
('TRJ0032', 'MN022', 'BA032', 500),
('TRJ0033', 'MN021', 'BA032', 1000),
('TRJ0034', 'MN022', 'BA032', 500),
('TRJ0035', 'MN002', 'BA007', 500),
('TRJ0035', 'MN012', 'BA011', 150),
('TRJ0035', 'MN012', 'BA022', 750),
('TRJ0035', 'MN012', 'BA018', 30),
('TRJ0035', 'MN012', 'BA019', 180),
('TRJ0035', 'MN021', 'BA032', 400),
('TRJ0035', 'MN022', 'BA032', 200),
('TRJ0035', 'MN011', 'BA000', 200),
('TRJ0035', 'MN011', 'BA025', 2000),
('TRJ0035', 'MN011', 'BA026', 500),
('TRJ0035', 'MN011', 'BA005', 100),
('TRJ0035', 'MN019', 'BA002', 1350),
('TRJ0035', 'MN019', 'BA030', 450),
('TRJ0035', 'MN018', 'BA002', 800),
('TRJ0035', 'MN018', 'BA018', 160),
('TRJ0035', 'MN018', 'BA019', 400),
('TRJ0035', 'MN015', 'BA025', 3000),
('TRJ0035', 'MN015', 'BA026', 1000),
('TRJ0035', 'MN015', 'BA027', 1500),
('TRJ0035', 'MN016', 'BA001', 50),
('TRJ0035', 'MN016', 'BA025', 250),
('TRJ0035', 'MN016', 'BA026', 30),
('TRJ0035', 'MN016', 'BA005', 20),
('TRJ0036', 'MN021', 'BA032', 800),
('TRJ0036', 'MN022', 'BA032', 100),
('TRJ0037', 'MN001', 'BA025', 300),
('TRJ0037', 'MN001', 'BA026', 100),
('TRJ0037', 'MN001', 'BA027', 150),
('TRJ0037', 'MN013', 'BA025', 1500),
('TRJ0037', 'MN013', 'BA026', 500),
('TRJ0037', 'MN013', 'BA027', 750),
('TRJ0037', 'MN015', 'BA025', 300),
('TRJ0037', 'MN015', 'BA026', 100),
('TRJ0037', 'MN015', 'BA027', 150),
('TRJ0038', 'MN019', 'BA000', 20),
('TRJ0038', 'MN019', 'BA025', 200),
('TRJ0038', 'MN019', 'BA026', 50),
('TRJ0038', 'MN019', 'BA005', 10),
('TRJ0038', 'MN018', 'BA000', 20),
('TRJ0038', 'MN018', 'BA025', 200),
('TRJ0038', 'MN018', 'BA026', 50),
('TRJ0038', 'MN018', 'BA005', 10),
('TRJ0038', 'MN020', 'BA000', 20),
('TRJ0038', 'MN020', 'BA025', 200),
('TRJ0038', 'MN020', 'BA026', 50),
('TRJ0038', 'MN020', 'BA005', 10),
('TRJ0038', 'MN023', 'BA000', 20),
('TRJ0038', 'MN023', 'BA025', 200),
('TRJ0038', 'MN023', 'BA026', 50),
('TRJ0038', 'MN023', 'BA005', 10),
('TRJ0038', 'MN001', 'BA000', 20),
('TRJ0038', 'MN001', 'BA025', 200),
('TRJ0038', 'MN001', 'BA026', 50),
('TRJ0038', 'MN001', 'BA005', 10),
('TRJ0038', 'MN011', 'BA000', 20),
('TRJ0038', 'MN011', 'BA025', 200),
('TRJ0038', 'MN011', 'BA026', 50),
('TRJ0038', 'MN011', 'BA005', 10),
('TRJ0039', 'MN002', 'BA025', 3300),
('TRJ0039', 'MN002', 'BA026', 220),
('TRJ0039', 'MN002', 'BA005', 550),
('TRJ0039', 'MN014', 'BA025', 600),
('TRJ0039', 'MN014', 'BA026', 40),
('TRJ0039', 'MN014', 'BA005', 100),
('TRJ0040', 'MN014', 'BA011', 250),
('TRJ0040', 'MN014', 'BA022', 1250),
('TRJ0040', 'MN014', 'BA018', 50),
('TRJ0040', 'MN014', 'BA019', 300),
('TRJ0040', 'MN017', 'BA011', 150),
('TRJ0040', 'MN017', 'BA022', 750),
('TRJ0040', 'MN017', 'BA018', 30),
('TRJ0040', 'MN017', 'BA019', 180),
('TRJ0040', 'MN012', 'BA011', 100),
('TRJ0040', 'MN012', 'BA022', 500),
('TRJ0040', 'MN012', 'BA018', 20),
('TRJ0040', 'MN012', 'BA019', 120),
('TRJ0041', 'MN001', 'BA003', 200),
('TRJ0041', 'MN001', 'BA025', 250),
('TRJ0041', 'MN001', 'BA026', 20),
('TRJ0042', 'MN002', 'BA007', 500),
('TRJ0043', 'MN019', 'BA011', 250),
('TRJ0043', 'MN019', 'BA022', 1250),
('TRJ0043', 'MN019', 'BA018', 50),
('TRJ0043', 'MN019', 'BA019', 300),
('TRJ0043', 'MN012', 'BA011', 100),
('TRJ0043', 'MN012', 'BA022', 500),
('TRJ0043', 'MN012', 'BA018', 20),
('TRJ0043', 'MN012', 'BA019', 120),
('TRJ0044', 'MN018', 'BA002', 400),
('TRJ0044', 'MN018', 'BA018', 80),
('TRJ0044', 'MN018', 'BA019', 200),
('TRJ0045', 'MN001', 'BA003', 400),
('TRJ0045', 'MN001', 'BA025', 500),
('TRJ0045', 'MN001', 'BA026', 40),
('TRJ0046', 'MN011', 'BA002', 450),
('TRJ0046', 'MN011', 'BA026', 30),
('TRJ0046', 'MN011', 'BA019', 240),
('TRJ0046', 'MN016', 'BA002', 300),
('TRJ0046', 'MN016', 'BA026', 20),
('TRJ0046', 'MN016', 'BA019', 160),
('TRJ0046', 'MN013', 'BA002', 150),
('TRJ0046', 'MN013', 'BA026', 10),
('TRJ0046', 'MN013', 'BA019', 80),
('TRJ0047', 'MN001', 'BA003', 1400),
('TRJ0047', 'MN001', 'BA025', 1750),
('TRJ0047', 'MN001', 'BA026', 140),
('TRJ0047', 'MN014', 'BA003', 400),
('TRJ0047', 'MN014', 'BA025', 500),
('TRJ0047', 'MN014', 'BA026', 40),
('TRJ0048', 'MN011', 'BA000', 80),
('TRJ0048', 'MN011', 'BA025', 800),
('TRJ0048', 'MN011', 'BA026', 200),
('TRJ0048', 'MN011', 'BA005', 40),
('TRJ0049', 'MN002', 'BA007', 200),
('TRJ0050', 'MN019', 'BA025', 1500),
('TRJ0050', 'MN019', 'BA026', 100),
('TRJ0050', 'MN019', 'BA005', 250),
('TRJ0050', 'MN011', 'BA025', 2100),
('TRJ0050', 'MN011', 'BA026', 140),
('TRJ0050', 'MN011', 'BA005', 350),
('TRJ0050', 'MN014', 'BA025', 600),
('TRJ0050', 'MN014', 'BA026', 40),
('TRJ0050', 'MN014', 'BA005', 100),
('TRJ0051', 'MN001', 'BA002', 150),
('TRJ0051', 'MN001', 'BA030', 50),
('TRJ0051', 'MN002', 'BA002', 150),
('TRJ0051', 'MN002', 'BA030', 50),
('TRJ0051', 'MN011', 'BA002', 150),
('TRJ0051', 'MN011', 'BA030', 50),
('TRJ0051', 'MN012', 'BA002', 150),
('TRJ0051', 'MN012', 'BA030', 50),
('TRJ0051', 'MN013', 'BA002', 150),
('TRJ0051', 'MN013', 'BA030', 50),
('TRJ0051', 'MN014', 'BA002', 150),
('TRJ0051', 'MN014', 'BA030', 50),
('TRJ0051', 'MN020', 'BA002', 150),
('TRJ0051', 'MN020', 'BA030', 50),
('TRJ0051', 'MN019', 'BA002', 150),
('TRJ0051', 'MN019', 'BA030', 50),
('TRJ0053', 'MN001', 'BA032', 1200),
('TRJ0053', 'MN020', 'BA032', 800),
('TRJ0053', 'MN011', 'BA032', 1200),
('TRJ0053', 'MN016', 'BA032', 400),
('TRJ0053', 'MN013', 'BA032', 400),
('TRJ0053', 'MN021', 'BA032', 600);

--
-- Trigger `log_tr_jual`
--
DELIMITER $$
CREATE TRIGGER `kurang_stok` AFTER INSERT ON `log_tr_jual` FOR EACH ROW BEGIN
	UPDATE bahan
    SET stok = stok - NEW.quantity
    WHERE id_bahan = NEW.id_bahan;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `menu`
--

INSERT INTO `menu` (`id_menu`, `nama_menu`, `jenis`, `harga`) VALUES
('MN001', 'Es Jeruk', 'Minuman', 5000),
('MN002', 'Coffee Latee', 'Original Coffee', 10000),
('MN011', 'Chocholate', 'Falvoured Coffee', 15000),
('MN012', 'Ayam Bakar', 'Makanan', 15000),
('MN013', 'Kentang Goreng', 'Snack', 12000),
('MN014', 'Black Coffee', 'Original Coffee', 3000),
('MN015', 'Jus Anggur', 'Minuman', 2000),
('MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000),
('MN017', 'Nasi Goreng', 'Makanan', 12000),
('MN018', 'Kentang Goreng', 'Snack', 8000),
('MN019', 'Jamur Crispy', 'Snack', 10000),
('MN020', 'Jus Apel', 'Minuman', 5000),
('MN021', 'Babi Bakar', 'Makanan', 35000),
('MN022', 'Sate Babi', 'Makanan', 20000),
('MN023', 'Babi Guling', 'Makanan', 350000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `supplier`
--

CREATE TABLE `supplier` (
  `id_supplier` varchar(6) NOT NULL,
  `nama_supplier` varchar(50) NOT NULL,
  `no_telp` varchar(13) NOT NULL,
  `alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `supplier`
--

INSERT INTO `supplier` (`id_supplier`, `nama_supplier`, `no_telp`, `alamat`) VALUES
('SP001', 'Moch. Alvian Hidayatulloh', '085990128912', 'Nganjuk, Jawa Timur, Indonesia'),
('SP002', 'Afrizal Wahyu Alkautsar ', '086790238923', 'Nganjuk, Jawa Timur, Indonesia'),
('SP003', 'Syamaidzar Adani Syah', '085690231830', 'Nganjuk, Jawa Timur, Indonesia'),
('SP004', 'Pramudya Putra Pratama', '081289378712', 'Jombang, Jawa Timur, Indonesia'),
('SP005', 'Syafrizal Wd Mahendra', '085690237823', 'Kediri, Jawa Timur, Indonesia'),
('SP008', 'M. Ferdiansyah', '085690238923', 'Jombang, Jawa Timur'),
('SP010', 'Amirzan Fikri Prasetyo', '085690238923', 'Jombang, Jawa Timur');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi_beli`
--

CREATE TABLE `transaksi_beli` (
  `id_tr_beli` varchar(7) NOT NULL,
  `id_karyawan` varchar(6) NOT NULL,
  `id_supplier` varchar(6) NOT NULL,
  `total_bahan` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `tanggal` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `transaksi_beli`
--

INSERT INTO `transaksi_beli` (`id_tr_beli`, `id_karyawan`, `id_supplier`, `total_bahan`, `total_harga`, `tanggal`) VALUES
('TRB0001', 'KY001', 'SP001', 1, 5000, '2022-11-30 00:00:00'),
('TRB0002', 'KY002', 'SP002', 1, 20000, '2022-12-03 00:00:00'),
('TRB0003', 'KY001', 'SP008', 6, 90000, '2022-12-03 00:00:00'),
('TRB0004', 'KY001', 'SP001', 3, 150000, '2022-12-03 00:00:00'),
('TRB0005', 'KY001', 'SP004', 10, 1200000, '2022-12-03 00:00:00'),
('TRB0006', 'KY001', 'SP008', 8, 154000, '2022-12-04 01:37:12'),
('TRB0007', 'KY001', 'SP008', 10, 135000, '2022-12-04 01:48:33'),
('TRB0008', 'KY001', 'SP008', 4, 54000, '2022-12-04 01:49:46'),
('TRB0009', 'KY001', 'SP008', 4, 54000, '2022-12-04 01:50:47'),
('TRB0010', 'KY001', 'SP001', 10, 500000, '2022-12-04 01:51:27'),
('TRB0011', 'KY001', 'SP008', 12, 202500, '2022-12-04 01:52:14'),
('TRB0012', 'KY001', 'SP008', 5, 25000, '2022-12-04 01:57:54'),
('TRB0013', 'KY001', 'SP008', 5, 25000, '2022-12-04 01:58:16'),
('TRB0014', 'KY001', 'SP008', 5, 25000, '2022-12-04 12:37:29'),
('TRB0015', 'KY001', 'SP008', 100, 25000, '2022-12-04 15:29:57'),
('TRB0016', 'KY001', 'SP008', 0, 0, '2022-12-04 15:31:05'),
('TRB0017', 'KY001', 'SP008', 200, 27000, '2022-12-04 15:32:06'),
('TRB0018', 'KY001', 'SP010', 500, 287500, '2022-12-04 23:18:15'),
('TRB0019', 'KY001', 'SP010', 1000, 57500, '2022-12-04 23:19:56'),
('TRB0020', 'KY001', 'SP010', 1, 57500, '2022-12-04 23:21:39'),
('TRB0021', 'KY001', 'SP010', 0, 0, '2022-12-04 23:22:48'),
('TRB0022', 'KY001', 'SP010', 2, 115000, '2022-12-04 23:23:05'),
('TRB0023', 'KY001', 'SP001', 5, 67500, '2022-12-04 23:31:03'),
('TRB0024', 'KY001', 'SP010', 5, 49000, '2022-12-04 23:32:15'),
('TRB0025', 'KY001', 'SP010', 4, 46000, '2022-12-04 23:34:19'),
('TRB0026', 'KY001', 'SP010', 0, 0, '2022-12-05 11:47:00'),
('TRB0027', 'KY001', 'SP010', 1, 120000, '2022-12-05 11:47:18'),
('TRB0028', 'KY001', 'SP010', 1, 57500, '2022-12-05 11:48:15'),
('TRB0029', 'KY001', 'SP010', 1, 20000, '2022-12-05 11:48:41'),
('TRB0030', 'KY001', 'SP010', 2, 40000, '2022-12-05 13:54:35'),
('TRB0031', 'KY001', 'SP005', 2, 10000, '2022-12-05 14:36:46'),
('TRB0032', 'KY001', 'SP003', 6, 625000, '2022-12-05 14:37:59'),
('TRB0033', 'KY001', 'SP010', 1, 10000, '2022-12-05 21:42:29'),
('TRB0034', 'KY001', 'SP010', 5, 50000, '2022-12-05 21:42:54'),
('TRB0035', 'KY001', 'SP010', 1, 57500, '2022-12-06 15:14:09'),
('TRB0036', 'KY001', 'SP010', 1, 57500, '2022-12-06 15:14:32'),
('TRB0037', 'KY001', 'SP010', 1, 57500, '2022-12-06 15:14:56'),
('TRB0038', 'KY001', 'SP010', 1, 57500, '2022-12-06 15:17:29'),
('TRB0039', 'KY001', 'SP001', 3, 150000, '2022-12-07 20:51:33'),
('TRB0040', 'KY001', 'SP001', 3, 150000, '2022-12-07 20:52:58'),
('TRB0041', 'KY001', 'SP010', 1, 57500, '2022-12-07 20:54:06'),
('TRB0042', 'KY001', 'SP003', 1, 120000, '2022-12-08 20:44:10'),
('TRB0043', 'KY001', 'SP003', 5, 125000, '2022-12-08 23:00:20'),
('TRB0044', 'KY001', 'SP010', 15, 130000, '2022-12-10 20:01:09'),
('TRB0045', 'KY001', 'SP001', 13, 358000, '2022-12-10 20:01:57'),
('TRB0046', 'KY001', 'SP008', 21, 1345000, '2022-12-10 20:04:42'),
('TRB0047', 'KY001', 'SP008', 8, 1200000, '2022-12-10 20:05:13'),
('TRB0048', 'KY001', 'SP005', 41, 217500, '2022-12-10 20:08:14'),
('TRB0049', 'KY001', 'SP004', 6, 84000, '2022-12-10 20:09:09'),
('TRB0050', 'KY001', 'SP008', 8, 200000, '2022-12-10 20:11:55'),
('TRB0051', 'KY001', 'SP001', 0, 0, '2022-12-10 22:31:41');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi_jual`
--

CREATE TABLE `transaksi_jual` (
  `id_tr_jual` varchar(7) NOT NULL,
  `id_karyawan` varchar(6) NOT NULL,
  `nama_pembeli` varchar(30) NOT NULL,
  `total_menu` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `tanggal` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `transaksi_jual`
--

INSERT INTO `transaksi_jual` (`id_tr_jual`, `id_karyawan`, `nama_pembeli`, `total_menu`, `total_harga`, `tanggal`) VALUES
('TRJ0001', 'KY001', '', 2, 20000, '2022-10-31 00:00:00'),
('TRJ0002', 'KY001', '', 3, 15000, '2022-10-31 00:00:00'),
('TRJ0003', 'KY002', 'paijo', 8, 570000, '2022-11-02 09:34:05'),
('TRJ0006', 'KY001', 'tes', 2, 20000, '2022-12-07 22:35:40'),
('TRJ0007', 'KY001', 'tes', 2, 20000, '2022-12-07 22:36:29'),
('TRJ0008', 'KY001', 'tes', 1, 5000, '2022-12-07 22:37:09'),
('TRJ0009', 'KY001', 'd', 2, 20000, '2022-12-07 22:38:59'),
('TRJ0010', 'KY001', 'tes', 1, 5000, '2022-12-07 22:40:46'),
('TRJ0011', 'KY001', 'tes', 1, 5000, '2022-12-07 22:58:35'),
('TRJ0012', 'KY001', 'tester', 10, 50000, '2022-12-07 23:00:32'),
('TRJ0013', 'KY001', 'ilham', 10, 50000, '2022-12-07 23:02:41'),
('TRJ0014', 'KY001', '', 8, 64000, '2022-12-08 15:03:30'),
('TRJ0015', 'KY001', '', 6, 90000, '2022-12-08 21:33:44'),
('TRJ0016', 'KY001', '', 5, 30000, '2022-12-08 21:33:55'),
('TRJ0017', 'KY001', '', 1, 5000, '2022-12-08 21:35:07'),
('TRJ0018', 'KY001', 'samsul', 2, 20000, '2022-12-08 22:12:09'),
('TRJ0019', 'KY001', '', 2, 10000, '2022-12-08 22:13:18'),
('TRJ0020', 'KY001', '', 4, 30000, '2022-12-08 22:16:47'),
('TRJ0021', 'KY001', '', 2, 6000, '2022-12-08 22:17:16'),
('TRJ0022', 'KY001', '', 1, 15000, '2022-12-08 22:17:51'),
('TRJ0023', 'KY001', '', 30, 420000, '2022-12-08 22:19:38'),
('TRJ0024', 'KY001', '', 20, 300000, '2022-12-08 22:54:36'),
('TRJ0025', 'KY001', '', 5, 40000, '2022-12-08 22:55:18'),
('TRJ0026', 'KY001', '', 2, 30000, '2022-12-08 22:59:18'),
('TRJ0027', 'KY001', '', 6, 30000, '2022-12-10 11:39:29'),
('TRJ0028', 'KY001', '', 15, 175000, '2022-12-10 11:40:14'),
('TRJ0029', 'KY001', '', 40, 950000, '2022-12-10 18:10:11'),
('TRJ0030', 'KY001', '', 10, 350000, '2022-12-10 18:22:25'),
('TRJ0031', 'KY001', '', 10, 200000, '2022-12-10 18:23:02'),
('TRJ0032', 'KY001', '', 5, 100000, '2022-12-10 18:23:35'),
('TRJ0033', 'KY001', '', 5, 175000, '2022-12-10 18:24:25'),
('TRJ0034', 'KY001', '', 5, 100000, '2022-12-10 18:24:34'),
('TRJ0035', 'KY001', '', 50, 541000, '2022-12-10 18:26:11'),
('TRJ0036', 'KY001', '', 5, 160000, '2022-12-10 20:12:37'),
('TRJ0037', 'KY001', '', 7, 67000, '2022-12-11 00:14:23'),
('TRJ0038', 'KY001', 'tester', 6, 393000, '2022-12-11 00:38:43'),
('TRJ0039', 'KY001', 'burhan', 13, 116000, '2022-12-11 16:21:25'),
('TRJ004', 'KY003', 'tes', 8, 21000, '2022-11-25 10:23:59'),
('TRJ0040', 'KY002', 'ferdi', 10, 81000, '2022-12-11 16:37:23'),
('TRJ0041', 'KY001', '', 1, 5000, '2022-12-11 19:22:45'),
('TRJ0042', 'KY003', '', 5, 50000, '2022-12-12 12:52:41'),
('TRJ0043', 'KY003', 'erdi', 7, 80000, '2022-12-12 12:53:39'),
('TRJ0044', 'KY002', '', 4, 32000, '2022-12-12 12:54:13'),
('TRJ0045', 'KY002', '', 2, 10000, '2022-12-12 12:56:29'),
('TRJ0046', 'KY001', '', 6, 81000, '2022-12-12 13:33:58'),
('TRJ0047', 'KY001', '', 9, 41000, '2022-12-12 13:36:31'),
('TRJ0048', 'KY001', '', 4, 60000, '2022-12-13 09:59:17'),
('TRJ0049', 'KY001', '', 2, 20000, '2022-12-13 13:04:43'),
('TRJ0050', 'KY001', '', 14, 161000, '2022-12-14 11:04:34'),
('TRJ0051', 'KY001', '', 8, 75000, '2022-12-14 12:09:38'),
('TRJ0052', 'KY001', '', 3, 15, '2022-12-14 14:42:22'),
('TRJ0053', 'KY001', '', 23, 293000, '2022-12-14 14:48:43');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `level` enum('ADMIN','KARYAWAN') NOT NULL,
  `id_karyawan` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`username`, `password`, `level`, `id_karyawan`) VALUES
('ADMIN', '12345678', 'ADMIN', 'KY001'),
('ilham', '12345678', 'KARYAWAN', 'KY002'),
('tes2', '?P	?P?X??<???', 'ADMIN', 'KY001'),
('tes3', '%?Z҃?@\n?d?mq<?', 'ADMIN', 'KY001'),
('tesuser', '?P	?P?X??<???', 'ADMIN', 'KY004'),
('widya', '12345678', 'KARYAWAN', 'KY003'),
('yoga', 'U??˻?QZ3Ph?<', 'KARYAWAN', 'KY004');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `bahan`
--
ALTER TABLE `bahan`
  ADD PRIMARY KEY (`id_bahan`);

--
-- Indeks untuk tabel `detail_menu`
--
ALTER TABLE `detail_menu`
  ADD KEY `id_menu` (`id_menu`,`id_bahan`),
  ADD KEY `id_bahan` (`id_bahan`);

--
-- Indeks untuk tabel `detail_supplier`
--
ALTER TABLE `detail_supplier`
  ADD KEY `id_supplier` (`id_supplier`),
  ADD KEY `id_bahan` (`id_bahan`);

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
-- Indeks untuk tabel `log_tr_beli`
--
ALTER TABLE `log_tr_beli`
  ADD KEY `id_tr_beli` (`id_tr_beli`),
  ADD KEY `id_bahan` (`id_bahan`);

--
-- Indeks untuk tabel `log_tr_jual`
--
ALTER TABLE `log_tr_jual`
  ADD KEY `id_tr_jual` (`id_tr_jual`),
  ADD KEY `id_menu` (`id_menu`),
  ADD KEY `id_bahan` (`id_bahan`);

--
-- Indeks untuk tabel `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id_menu`);

--
-- Indeks untuk tabel `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id_supplier`);

--
-- Indeks untuk tabel `transaksi_beli`
--
ALTER TABLE `transaksi_beli`
  ADD PRIMARY KEY (`id_tr_beli`),
  ADD KEY `id_karyawan` (`id_karyawan`),
  ADD KEY `id_supplier` (`id_supplier`);

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
  ADD PRIMARY KEY (`username`),
  ADD KEY `id_karyawan` (`id_karyawan`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `detail_menu`
--
ALTER TABLE `detail_menu`
  ADD CONSTRAINT `detail_menu_ibfk_1` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id_menu`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_menu_ibfk_2` FOREIGN KEY (`id_bahan`) REFERENCES `bahan` (`id_bahan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `detail_supplier`
--
ALTER TABLE `detail_supplier`
  ADD CONSTRAINT `detail_supplier_ibfk_1` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_supplier_ibfk_2` FOREIGN KEY (`id_bahan`) REFERENCES `bahan` (`id_bahan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `detail_tr_beli`
--
ALTER TABLE `detail_tr_beli`
  ADD CONSTRAINT `detail_tr_beli_ibfk_1` FOREIGN KEY (`id_tr_beli`) REFERENCES `transaksi_beli` (`id_tr_beli`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_tr_beli_ibfk_2` FOREIGN KEY (`id_bahan`) REFERENCES `bahan` (`id_bahan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `detail_tr_jual`
--
ALTER TABLE `detail_tr_jual`
  ADD CONSTRAINT `detail_tr_jual_ibfk_1` FOREIGN KEY (`id_tr_jual`) REFERENCES `transaksi_jual` (`id_tr_jual`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_tr_jual_ibfk_2` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id_menu`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `log_tr_beli`
--
ALTER TABLE `log_tr_beli`
  ADD CONSTRAINT `log_tr_beli_ibfk_1` FOREIGN KEY (`id_tr_beli`) REFERENCES `transaksi_beli` (`id_tr_beli`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `log_tr_beli_ibfk_2` FOREIGN KEY (`id_bahan`) REFERENCES `bahan` (`id_bahan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `log_tr_jual`
--
ALTER TABLE `log_tr_jual`
  ADD CONSTRAINT `log_tr_jual_ibfk_1` FOREIGN KEY (`id_tr_jual`) REFERENCES `transaksi_jual` (`id_tr_jual`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `log_tr_jual_ibfk_2` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id_menu`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `log_tr_jual_ibfk_3` FOREIGN KEY (`id_bahan`) REFERENCES `bahan` (`id_bahan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `transaksi_beli`
--
ALTER TABLE `transaksi_beli`
  ADD CONSTRAINT `transaksi_beli_ibfk_1` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaksi_beli_ibfk_2` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `transaksi_jual`
--
ALTER TABLE `transaksi_jual`
  ADD CONSTRAINT `transaksi_jual_ibfk_2` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
