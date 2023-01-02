-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 03 Jan 2023 pada 00.59
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
  `jenis` enum('tes','Hewani','Nabati','Coffee','Perasa','Cairan') NOT NULL,
  `stok` int(5) NOT NULL,
  `satuan` enum('gr','ml') NOT NULL,
  `harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `bahan`
--

INSERT INTO `bahan` (`id_bahan`, `nama_bahan`, `jenis`, `stok`, `satuan`, `harga`) VALUES
('BA001', 'Susu Coklat', 'Cairan', 7055, 'ml', 25000),
('BA002', 'Kentang', 'Nabati', 4300, 'gr', 50000),
('BA003', 'Jeruk', 'Nabati', 8800, 'gr', 25000),
('BA005', 'Biji Kopi', 'Coffee', 8980, 'gr', 70000),
('BA008', 'Kopi Hitam', 'Coffee', 10190, 'gr', 65000),
('BA011', 'Kacang', 'Nabati', 10269, 'gr', 5000),
('BA015', 'Apel', 'Nabati', 16300, 'gr', 5000),
('BA018', 'Kecap', 'Perasa', 11723, 'ml', 1500),
('BA019', 'Cabe Rawit', 'Nabati', 4940, 'gr', 5000),
('BA020', 'Bawang Merah', 'Nabati', 8930, 'gr', 25000),
('BA021', 'Bawang Putih', 'Nabati', 13930, 'gr', 10000),
('BA022', 'Daging Ayam', 'Hewani', 8000, 'gr', 20000),
('BA025', 'Air Mineral', 'Cairan', 6780, 'ml', 3000),
('BA026', 'Gula Pasir', 'Perasa', 9795, 'gr', 14000),
('BA027', 'Anggur', 'Nabati', 9700, 'gr', 20000),
('BA028', 'Nasi', 'Nabati', 8900, 'gr', 8000),
('BA029', 'Jamur', 'Nabati', 11360, 'gr', 15000),
('BA030', 'Garam', 'Perasa', 14015, 'gr', 6000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_menu`
--

CREATE TABLE `detail_menu` (
  `id_menu` varchar(5) DEFAULT NULL,
  `id_bahan` varchar(6) DEFAULT NULL,
  `quantity` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `detail_menu`
--

INSERT INTO `detail_menu` (`id_menu`, `id_bahan`, `quantity`) VALUES
('MN017', 'BA018', 20),
('MN017', 'BA019', 10),
('MN017', 'BA028', 150),
('MN020', 'BA026', 100),
('MN020', 'BA015', 150),
('MN020', 'BA025', 250),
('MN019', 'BA002', 150),
('MN019', 'BA030', 50),
('MN019', 'BA029', 120),
('MN018', 'BA002', 100),
('MN018', 'BA019', 50),
('MN018', 'BA026', 15),
('MN018', 'BA030', 10),
('MN016', 'BA001', 50),
('MN016', 'BA026', 30),
('MN016', 'BA025', 250),
('MN016', 'BA005', 25),
('MN012', 'BA011', 50),
('MN012', 'BA018', 10),
('MN012', 'BA019', 60),
('MN012', 'BA022', 250),
('MN012', 'BA019', 10),
('MN022', 'BA005', 50),
('MN022', 'BA026', 25),
('MN022', 'BA025', 250),
('MN022', 'BA001', 10),
('MN001', 'BA003', 200),
('MN001', 'BA026', 20),
('MN001', 'BA025', 300),
('MN002', 'BA005', 40),
('MN002', 'BA025', 250),
('MN013', 'BA002', 150),
('MN013', 'BA019', 80),
('MN013', 'BA026', 10),
('MN013', 'BA030', 10),
('MN011', 'BA005', 10),
('MN011', 'BA025', 200),
('MN011', 'BA026', 50),
('MN011', 'BA001', 15),
('MN023', 'BA005', 60),
('MN023', 'BA025', 270),
('MN023', 'BA026', 25),
('MN023', 'BA001', 50),
('MN021', 'BA001', 10),
('MN021', 'BA005', 50),
('MN021', 'BA026', 30),
('MN021', 'BA025', 260),
('MN015', 'BA026', 100),
('MN015', 'BA027', 150),
('MN015', 'BA025', 240),
('MN014', 'BA005', 50),
('MN014', 'BA008', 10),
('MN014', 'BA026', 20),
('MN014', 'BA025', 320);

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
('SP008', 'BA003'),
('SP008', 'BA015'),
('SP008', 'BA030'),
('SP008', 'BA029'),
('SP008', 'BA011'),
('SP001', 'BA002'),
('SP001', 'BA030'),
('SP001', 'BA001'),
('SP002', 'BA005'),
('SP002', 'BA001'),
('SP002', 'BA029'),
('SP003', 'BA005'),
('SP003', 'BA003'),
('SP003', 'BA008'),
('SP004', 'BA005'),
('SP004', 'BA026'),
('SP005', 'BA019'),
('SP005', 'BA025'),
('SP005', 'BA028'),
('SP005', 'BA030'),
('SP005', 'BA021'),
('SP005', 'BA018'),
('SP005', 'BA015'),
('SP010', 'BA005'),
('SP010', 'BA025'),
('SP010', 'BA022'),
('SP010', 'BA001'),
('SP010', 'BA027'),
('SP010', 'BA020');

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_tr_beli`
--

CREATE TABLE `detail_tr_beli` (
  `id_tr_beli` varchar(7) NOT NULL,
  `id_bahan` varchar(6) DEFAULT NULL,
  `nama_bahan` varchar(50) NOT NULL,
  `jenis_bahan` enum('Hewani','Nabati','Coffee','Perasa','Cairan') NOT NULL,
  `satuan_bahan` enum('gr','ml') NOT NULL,
  `harga_bahan` int(11) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `detail_tr_beli`
--

INSERT INTO `detail_tr_beli` (`id_tr_beli`, `id_bahan`, `nama_bahan`, `jenis_bahan`, `satuan_bahan`, `harga_bahan`, `jumlah`, `total_harga`) VALUES
('TRB0001', 'BA003', 'Jeruk', 'Nabati', 'gr', 25000, 1000, 25000),
('TRB0002', 'BA002', 'Kentang', 'Nabati', 'gr', 50000, 1000, 50000),
('TRB0003', 'BA003', 'Jeruk', 'Nabati', 'gr', 25000, 2000, 50000),
('TRB0004', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 5000, 15000),
('TRB0005', 'BA011', 'Kacang', 'Nabati', 'gr', 5000, 2000, 10000),
('TRB0006', 'BA029', 'Jamur', 'Nabati', 'gr', 15000, 1000, 15000),
('TRB0007', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 5000, 15000),
('TRB0007', 'BA022', 'Daging Ayam', 'Hewani', 'gr', 20000, 2000, 40000),
('TRB0007', 'BA027', 'Anggur', 'Nabati', 'gr', 20000, 2000, 40000),
('TRB0008', 'BA026', 'Gula Pasir', 'Perasa', 'gr', 14000, 4000, 56000),
('TRB0009', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 120000, 1000, 120000),
('TRB0010', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 70000, 2000, 140000),
('TRB0010', 'BA029', 'Jamur', 'Nabati', 'gr', 15000, 1000, 15000),
('TRB0011', 'BA003', 'Jeruk', 'Nabati', 'gr', 25000, 3000, 75000),
('TRB0012', 'BA002', 'Kentang', 'Nabati', 'gr', 50000, 1000, 50000),
('TRB0013', 'BA002', 'Kentang', 'Nabati', 'gr', 50000, 3000, 150000),
('TRB0014', 'BA028', 'Nasi', 'Nabati', 'gr', 8000, 2000, 16000),
('TRB0015', 'BA030', 'Garam', 'Perasa', 'gr', 6000, 5000, 30000),
('TRB0016', 'BA003', 'Jeruk', 'Nabati', 'gr', 25000, 2000, 50000),
('TRB0017', 'BA028', 'Nasi', 'Nabati', 'gr', 8000, 2000, 16000),
('TRB0017', 'BA021', 'Bawang Putih', 'Nabati', 'gr', 10000, 1000, 10000),
('TRB0018', 'BA022', 'Daging Ayam', 'Hewani', 'gr', 20000, 2000, 40000),
('TRB0019', 'BA003', 'Jeruk', 'Nabati', 'gr', 25000, 5000, 125000),
('TRB0020', 'BA026', 'Gula Pasir', 'Perasa', 'gr', 14000, 5000, 70000),
('TRB0021', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 12000, 36000),
('TRB0022', 'BA028', 'Nasi', 'Nabati', 'gr', 8000, 8000, 64000),
('TRB0023', 'BA029', 'Jamur', 'Nabati', 'gr', 15000, 2000, 30000),
('TRB0024', 'BA021', 'Bawang Putih', 'Nabati', 'gr', 10000, 5000, 50000),
('TRB0025', 'BA020', 'Bawang Merah', 'Nabati', 'gr', 25000, 4000, 100000),
('TRB0025', 'BA022', 'Daging Ayam', 'Hewani', 'gr', 20000, 5000, 100000),
('TRB0026', 'BA019', 'Cabe Rawit', 'Nabati', 'gr', 5000, 2000, 10000),
('TRB0026', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 10000, 30000),
('TRB0026', 'BA018', 'Kecap', 'Perasa', 'ml', 1500, 1000, 1500),
('TRB0027', 'BA011', 'Kacang', 'Nabati', 'gr', 5000, 5000, 25000),
('TRB0028', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 70000, 2000, 140000),
('TRB0028', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 4000, 12000),
('TRB0028', 'BA001', 'Susu Coklat', 'Cairan', 'ml', 25000, 2000, 50000),
('TRB0029', 'BA002', 'Kentang', 'Nabati', 'gr', 50000, 5000, 250000),
('TRB0030', 'BA018', 'Kecap', 'Perasa', 'ml', 1500, 3000, 4500),
('TRB0031', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 70000, 2000, 140000),
('TRB0032', 'BA002', 'Kentang', 'Nabati', 'gr', 50000, 3000, 150000),
('TRB0033', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 10000, 30000),
('TRB0034', 'BA002', 'Kentang', 'Nabati', 'gr', 50000, 5000, 250000),
('TRB0035', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 70000, 2000, 140000),
('TRB0036', 'BA018', 'Kecap', 'Perasa', 'ml', 1500, 5000, 7500),
('TRB0037', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 70000, 3000, 210000),
('TRB0038', 'BA026', 'Gula Pasir', 'Perasa', 'gr', 14000, 4000, 56000),
('TRB0039', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 5000, 15000),
('TRB0040', 'BA001', 'Susu Coklat', 'Cairan', 'ml', 25000, 3000, 75000),
('TRB0041', 'BA002', 'Kentang', 'Nabati', 'gr', 50000, 3000, 150000),
('TRB0042', 'BA015', 'Apel', 'Nabati', 'gr', 5000, 8000, 40000),
('TRB0042', 'BA011', 'Kacang', 'Nabati', 'gr', 5000, 2000, 10000),
('TRB0043', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 70000, 1000, 70000),
('TRB0044', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 5000, 15000),
('TRB0045', 'BA022', 'Daging Ayam', 'Hewani', 'gr', 20000, 1000, 20000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_tr_jual`
--

CREATE TABLE `detail_tr_jual` (
  `id_tr_jual` varchar(7) NOT NULL,
  `id_menu` varchar(5) DEFAULT NULL,
  `nama_menu` varchar(30) NOT NULL,
  `jenis_menu` enum('Minuman','Makanan','Original Coffee','Falvoured Coffee','Snack') NOT NULL,
  `harga_menu` int(11) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `detail_tr_jual`
--

INSERT INTO `detail_tr_jual` (`id_tr_jual`, `id_menu`, `nama_menu`, `jenis_menu`, `harga_menu`, `jumlah`, `total_harga`) VALUES
('TRJ0001', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0001', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ0002', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0003', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ0004', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 1, 10000),
('TRJ0005', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 2, 20000),
('TRJ0005', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 4, 60000),
('TRJ0005', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 3, 36000),
('TRJ0006', 'MN023', 'Kopi Susu', 'Minuman', 3000, 1, 3000),
('TRJ0007', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 6, 90000),
('TRJ0008', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 2, 24000),
('TRJ0009', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ0010', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 2, 24000),
('TRJ0010', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0010', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 3, 36000),
('TRJ0011', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 3, 45000),
('TRJ0011', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 2, 20000),
('TRJ0011', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0012', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0013', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0013', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ0014', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0014', 'MN015', 'Jus Anggur', 'Minuman', 12000, 1, 12000),
('TRJ0014', 'MN023', 'Kopi Susu', 'Minuman', 3000, 2, 6000),
('TRJ0015', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 5, 75000),
('TRJ0015', 'MN019', 'Jamur Crispy', 'Snack', 10000, 2, 20000),
('TRJ0015', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ0016', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ0016', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 1, 10000),
('TRJ0016', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0016', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0016', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0016', 'MN020', 'Jus Apel', 'Minuman', 11000, 1, 11000),
('TRJ0017', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 4, 60000),
('TRJ0017', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 3, 30000),
('TRJ0017', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0018', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 4, 60000),
('TRJ0018', 'MN018', 'Keripik Kentang', 'Snack', 8000, 3, 24000),
('TRJ0018', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ0019', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 3, 45000),
('TRJ0019', 'MN023', 'Kopi Susu', 'Minuman', 3000, 2, 6000),
('TRJ0019', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 1, 10000),
('TRJ0019', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 2, 24000),
('TRJ0019', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 5, 50000),
('TRJ0020', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 3, 45000),
('TRJ0020', 'MN022', 'Cappucino', 'Original Coffee', 10000, 3, 30000),
('TRJ0020', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0021', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0021', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 1, 12000),
('TRJ0022', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ0022', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ0023', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 2, 20000),
('TRJ0023', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0024', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 3, 36000),
('TRJ0024', 'MN023', 'Kopi Susu', 'Minuman', 3000, 1, 3000),
('TRJ0024', 'MN019', 'Jamur Crispy', 'Snack', 10000, 8, 80000),
('TRJ0025', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 7, 105000),
('TRJ0025', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 5, 75000),
('TRJ0025', 'MN018', 'Keripik Kentang', 'Snack', 8000, 1, 8000),
('TRJ0025', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ0026', 'MN015', 'Jus Anggur', 'Minuman', 12000, 2, 24000),
('TRJ0026', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 7, 70000),
('TRJ0026', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0026', 'MN023', 'Kopi Susu', 'Minuman', 3000, 4, 12000),
('TRJ0027', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 9, 90000),
('TRJ0027', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0027', 'MN018', 'Keripik Kentang', 'Snack', 8000, 1, 8000),
('TRJ0027', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ0027', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 8, 120000),
('TRJ0028', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ0028', 'MN015', 'Jus Anggur', 'Minuman', 12000, 1, 12000),
('TRJ0028', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ0029', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 7, 105000),
('TRJ0029', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 4, 60000),
('TRJ0029', 'MN018', 'Keripik Kentang', 'Snack', 8000, 7, 56000),
('TRJ0030', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0030', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0030', 'MN018', 'Keripik Kentang', 'Snack', 8000, 3, 24000),
('TRJ0031', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 4, 60000),
('TRJ0032', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 9, 108000),
('TRJ0033', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ0034', 'MN018', 'Keripik Kentang', 'Snack', 8000, 3, 24000),
('TRJ0034', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0035', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 10, 120000),
('TRJ0036', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ0036', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0036', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 1, 10000),
('TRJ0036', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ0036', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ0037', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0037', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0037', 'MN019', 'Jamur Crispy', 'Snack', 10000, 2, 20000),
('TRJ0037', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 1, 10000),
('TRJ0038', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0038', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0038', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0039', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0040', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0040', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ0041', 'MN023', 'Kopi Susu', 'Minuman', 3000, 1, 3000),
('TRJ0041', 'MN020', 'Jus Apel', 'Minuman', 11000, 2, 22000),
('TRJ0042', 'MN015', 'Jus Anggur', 'Minuman', 12000, 2, 24000),
('TRJ0043', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ0043', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ0044', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0045', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ0046', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0047', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 4, 60000),
('TRJ0048', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0048', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ0048', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 3, 30000),
('TRJ0049', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0049', 'MN015', 'Jus Anggur', 'Minuman', 12000, 3, 36000),
('TRJ0049', 'MN022', 'Cappucino', 'Original Coffee', 10000, 1, 10000),
('TRJ0049', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 1, 10000),
('TRJ0049', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0050', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ0050', 'MN020', 'Jus Apel', 'Minuman', 11000, 1, 11000),
('TRJ0051', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 1, 10000),
('TRJ0052', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 4, 60000),
('TRJ0052', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ0052', 'MN018', 'Keripik Kentang', 'Snack', 8000, 3, 24000),
('TRJ0053', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 4, 48000),
('TRJ0053', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0053', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 8, 120000),
('TRJ0054', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 4, 60000),
('TRJ0054', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 10, 100000),
('TRJ0055', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 4, 60000),
('TRJ0055', 'MN013', 'Kentang Goreng', 'Snack', 12000, 9, 108000),
('TRJ0056', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 20, 240000),
('TRJ0056', 'MN013', 'Kentang Goreng', 'Snack', 12000, 7, 84000),
('TRJ0057', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 4, 48000),
('TRJ0058', 'MN013', 'Kentang Goreng', 'Snack', 12000, 5, 60000),
('TRJ0059', 'MN022', 'Cappucino', 'Original Coffee', 10000, 5, 50000),
('TRJ0060', 'MN022', 'Cappucino', 'Original Coffee', 10000, 5, 50000),
('TRJ0060', 'MN015', 'Jus Anggur', 'Minuman', 12000, 3, 36000),
('TRJ0061', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 3, 45000),
('TRJ0062', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ0063', 'MN019', 'Jamur Crispy', 'Snack', 10000, 2, 20000),
('TRJ0063', 'MN023', 'Kopi Susu', 'Minuman', 3000, 5, 15000),
('TRJ0063', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 2, 20000),
('TRJ0064', 'MN023', 'Kopi Susu', 'Minuman', 3000, 2, 6000),
('TRJ0065', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 4, 48000),
('TRJ0065', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ0066', 'MN020', 'Jus Apel', 'Minuman', 11000, 3, 33000),
('TRJ0067', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 5, 60000),
('TRJ0068', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ0068', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 1, 10000),
('TRJ0068', 'MN019', 'Jamur Crispy', 'Snack', 10000, 6, 60000),
('TRJ0069', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ0070', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 5, 60000),
('TRJ0071', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ0072', 'MN018', 'Keripik Kentang', 'Snack', 8000, 6, 48000),
('TRJ0073', 'MN013', 'Kentang Goreng', 'Snack', 12000, 4, 48000),
('TRJ0073', 'MN015', 'Jus Anggur', 'Minuman', 12000, 4, 48000),
('TRJ0074', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0075', 'MN013', 'Kentang Goreng', 'Snack', 12000, 4, 48000),
('TRJ0076', 'MN019', 'Jamur Crispy', 'Snack', 10000, 5, 50000),
('TRJ0077', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0078', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 8, 120000),
('TRJ0079', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0080', 'MN022', 'Cappucino', 'Original Coffee', 10000, 4, 40000),
('TRJ0081', 'MN022', 'Cappucino', 'Original Coffee', 10000, 3, 30000),
('TRJ0081', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0082', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ0083', 'MN019', 'Jamur Crispy', 'Snack', 10000, 4, 40000),
('TRJ0084', 'MN013', 'Kentang Goreng', 'Snack', 12000, 5, 60000),
('TRJ0085', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0086', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 1, 10000),
('TRJ0086', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0087', 'MN019', 'Jamur Crispy', 'Snack', 10000, 2, 20000),
('TRJ0088', 'MN018', 'Keripik Kentang', 'Snack', 8000, 3, 24000),
('TRJ0089', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 4, 48000),
('TRJ0090', 'MN022', 'Cappucino', 'Original Coffee', 10000, 4, 40000),
('TRJ0091', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 1, 12000),
('TRJ0091', 'MN001', 'Jus Jeruk', 'Minuman', 10000, 1, 10000);

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
('TRB0001', 'BA003', 1000),
('TRB0002', 'BA002', 1000),
('TRB0003', 'BA003', 2000),
('TRB0004', 'BA025', 5000),
('TRB0005', 'BA011', 2000),
('TRB0006', 'BA029', 1000),
('TRB0007', 'BA025', 5000),
('TRB0007', 'BA022', 2000),
('TRB0007', 'BA027', 2000),
('TRB0008', 'BA026', 4000),
('TRB0009', 'BA005', 1000),
('TRB0010', 'BA005', 2000),
('TRB0010', 'BA029', 1000),
('TRB0011', 'BA003', 3000),
('TRB0012', 'BA002', 1000),
('TRB0013', 'BA002', 3000),
('TRB0014', 'BA028', 2000),
('TRB0015', 'BA030', 5000),
('TRB0016', 'BA003', 2000),
('TRB0017', 'BA028', 2000),
('TRB0017', 'BA021', 1000),
('TRB0018', 'BA022', 2000),
('TRB0019', 'BA003', 5000),
('TRB0020', 'BA026', 5000),
('TRB0021', 'BA025', 12000),
('TRB0022', 'BA028', 8000),
('TRB0023', 'BA029', 2000),
('TRB0024', 'BA021', 5000),
('TRB0025', 'BA020', 4000),
('TRB0025', 'BA022', 5000),
('TRB0026', 'BA019', 2000),
('TRB0026', 'BA025', 10000),
('TRB0026', 'BA018', 1000),
('TRB0027', 'BA011', 5000),
('TRB0028', 'BA005', 2000),
('TRB0028', 'BA025', 4000),
('TRB0028', 'BA001', 2000),
('TRB0029', 'BA002', 5000),
('TRB0030', 'BA018', 3000),
('TRB0031', 'BA005', 2000),
('TRB0032', 'BA002', 3000),
('TRB0033', 'BA025', 10000),
('TRB0034', 'BA002', 5000),
('TRB0035', 'BA005', 2000),
('TRB0036', 'BA018', 5000),
('TRB0037', 'BA005', 3000),
('TRB0038', 'BA026', 4000),
('TRB0039', 'BA025', 5000),
('TRB0040', 'BA001', 3000),
('TRB0041', 'BA002', 3000),
('TRB0042', 'BA015', 8000),
('TRB0042', 'BA011', 2000),
('TRB0043', 'BA005', 1000),
('TRB0044', 'BA025', 5000),
('TRB0045', 'BA022', 1000);

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
('TRJ0001', 'MN012', 'BA011', 50),
('TRJ0001', 'MN012', 'BA022', 250),
('TRJ0001', 'MN012', 'BA018', 10),
('TRJ0001', 'MN012', 'BA019', 10),
('TRJ0001', 'MN022', 'BA001', 20),
('TRJ0001', 'MN022', 'BA025', 500),
('TRJ0001', 'MN022', 'BA026', 50),
('TRJ0001', 'MN022', 'BA005', 100),
('TRJ0002', 'MN016', 'BA001', 50),
('TRJ0002', 'MN016', 'BA025', 250),
('TRJ0002', 'MN016', 'BA026', 30),
('TRJ0002', 'MN016', 'BA005', 25),
('TRJ0003', 'MN012', 'BA011', 100),
('TRJ0003', 'MN012', 'BA022', 500),
('TRJ0003', 'MN012', 'BA018', 20),
('TRJ0003', 'MN012', 'BA019', 20),
('TRJ0004', 'MN001', 'BA003', 200),
('TRJ0004', 'MN001', 'BA025', 300),
('TRJ0004', 'MN001', 'BA026', 20),
('TRJ0005', 'MN001', 'BA003', 400),
('TRJ0005', 'MN001', 'BA025', 600),
('TRJ0005', 'MN001', 'BA026', 40),
('TRJ0005', 'MN012', 'BA011', 200),
('TRJ0005', 'MN012', 'BA022', 1000),
('TRJ0005', 'MN012', 'BA018', 40),
('TRJ0005', 'MN012', 'BA019', 40),
('TRJ0005', 'MN017', 'BA028', 450),
('TRJ0005', 'MN017', 'BA018', 60),
('TRJ0005', 'MN017', 'BA019', 30),
('TRJ0006', 'MN023', 'BA001', 50),
('TRJ0006', 'MN023', 'BA025', 270),
('TRJ0006', 'MN023', 'BA026', 25),
('TRJ0006', 'MN023', 'BA005', 60),
('TRJ0007', 'MN011', 'BA001', 90),
('TRJ0007', 'MN011', 'BA025', 1200),
('TRJ0007', 'MN011', 'BA026', 300),
('TRJ0007', 'MN011', 'BA005', 60),
('TRJ0008', 'MN017', 'BA028', 300),
('TRJ0008', 'MN017', 'BA018', 40),
('TRJ0008', 'MN017', 'BA019', 20),
('TRJ0009', 'MN022', 'BA001', 20),
('TRJ0009', 'MN022', 'BA025', 500),
('TRJ0009', 'MN022', 'BA026', 50),
('TRJ0009', 'MN022', 'BA005', 100),
('TRJ0010', 'MN017', 'BA028', 300),
('TRJ0010', 'MN017', 'BA018', 40),
('TRJ0010', 'MN017', 'BA019', 20),
('TRJ0010', 'MN012', 'BA011', 50),
('TRJ0010', 'MN012', 'BA022', 250),
('TRJ0010', 'MN012', 'BA018', 10),
('TRJ0010', 'MN012', 'BA019', 10),
('TRJ0010', 'MN016', 'BA001', 150),
('TRJ0010', 'MN016', 'BA025', 750),
('TRJ0010', 'MN016', 'BA026', 90),
('TRJ0010', 'MN016', 'BA005', 75),
('TRJ0011', 'MN012', 'BA011', 150),
('TRJ0011', 'MN012', 'BA022', 750),
('TRJ0011', 'MN012', 'BA018', 30),
('TRJ0011', 'MN012', 'BA019', 30),
('TRJ0011', 'MN001', 'BA003', 400),
('TRJ0011', 'MN001', 'BA025', 600),
('TRJ0011', 'MN001', 'BA026', 40),
('TRJ0011', 'MN011', 'BA001', 15),
('TRJ0011', 'MN011', 'BA025', 200),
('TRJ0011', 'MN011', 'BA026', 50),
('TRJ0011', 'MN011', 'BA005', 10),
('TRJ0012', 'MN012', 'BA011', 50),
('TRJ0012', 'MN012', 'BA022', 250),
('TRJ0012', 'MN012', 'BA018', 10),
('TRJ0012', 'MN012', 'BA019', 10),
('TRJ0013', 'MN016', 'BA001', 50),
('TRJ0013', 'MN016', 'BA025', 250),
('TRJ0013', 'MN016', 'BA026', 30),
('TRJ0013', 'MN016', 'BA005', 25),
('TRJ0013', 'MN002', 'BA025', 250),
('TRJ0013', 'MN002', 'BA005', 40),
('TRJ0014', 'MN011', 'BA001', 30),
('TRJ0014', 'MN011', 'BA025', 400),
('TRJ0014', 'MN011', 'BA026', 100),
('TRJ0014', 'MN011', 'BA005', 20),
('TRJ0014', 'MN015', 'BA025', 300),
('TRJ0014', 'MN015', 'BA026', 100),
('TRJ0014', 'MN015', 'BA027', 150),
('TRJ0014', 'MN023', 'BA001', 100),
('TRJ0014', 'MN023', 'BA025', 540),
('TRJ0014', 'MN023', 'BA026', 50),
('TRJ0014', 'MN023', 'BA005', 120),
('TRJ0015', 'MN012', 'BA011', 250),
('TRJ0015', 'MN012', 'BA022', 1250),
('TRJ0015', 'MN012', 'BA018', 50),
('TRJ0015', 'MN012', 'BA019', 50),
('TRJ0015', 'MN019', 'BA002', 300),
('TRJ0015', 'MN019', 'BA029', 240),
('TRJ0015', 'MN019', 'BA030', 100),
('TRJ0015', 'MN014', 'BA025', 300),
('TRJ0015', 'MN014', 'BA026', 20),
('TRJ0015', 'MN014', 'BA005', 50),
('TRJ0016', 'MN018', 'BA002', 200),
('TRJ0016', 'MN018', 'BA026', 30),
('TRJ0016', 'MN018', 'BA019', 100),
('TRJ0016', 'MN018', 'BA030', 20),
('TRJ0016', 'MN001', 'BA003', 200),
('TRJ0016', 'MN001', 'BA025', 300),
('TRJ0016', 'MN001', 'BA026', 20),
('TRJ0016', 'MN021', 'BA025', 600),
('TRJ0016', 'MN021', 'BA026', 60),
('TRJ0016', 'MN021', 'BA005', 100),
('TRJ0016', 'MN012', 'BA011', 50),
('TRJ0016', 'MN012', 'BA022', 250),
('TRJ0016', 'MN012', 'BA018', 10),
('TRJ0016', 'MN012', 'BA019', 10),
('TRJ0016', 'MN016', 'BA001', 50),
('TRJ0016', 'MN016', 'BA025', 250),
('TRJ0016', 'MN016', 'BA026', 30),
('TRJ0016', 'MN016', 'BA005', 25),
('TRJ0016', 'MN020', 'BA025', 250),
('TRJ0016', 'MN020', 'BA015', 150),
('TRJ0016', 'MN020', 'BA026', 100),
('TRJ0017', 'MN012', 'BA011', 200),
('TRJ0017', 'MN012', 'BA022', 1000),
('TRJ0017', 'MN012', 'BA018', 40),
('TRJ0017', 'MN012', 'BA019', 40),
('TRJ0017', 'MN001', 'BA003', 600),
('TRJ0017', 'MN001', 'BA025', 900),
('TRJ0017', 'MN001', 'BA026', 60),
('TRJ0017', 'MN021', 'BA025', 300),
('TRJ0017', 'MN021', 'BA026', 30),
('TRJ0017', 'MN021', 'BA005', 50),
('TRJ0018', 'MN012', 'BA011', 200),
('TRJ0018', 'MN012', 'BA022', 1000),
('TRJ0018', 'MN012', 'BA018', 40),
('TRJ0018', 'MN012', 'BA019', 40),
('TRJ0018', 'MN018', 'BA002', 300),
('TRJ0018', 'MN018', 'BA026', 45),
('TRJ0018', 'MN018', 'BA019', 150),
('TRJ0018', 'MN018', 'BA030', 30),
('TRJ0018', 'MN002', 'BA025', 250),
('TRJ0018', 'MN002', 'BA005', 40),
('TRJ0019', 'MN012', 'BA011', 150),
('TRJ0019', 'MN012', 'BA022', 750),
('TRJ0019', 'MN012', 'BA018', 30),
('TRJ0019', 'MN012', 'BA019', 30),
('TRJ0019', 'MN023', 'BA001', 100),
('TRJ0019', 'MN023', 'BA025', 540),
('TRJ0019', 'MN023', 'BA026', 50),
('TRJ0019', 'MN023', 'BA005', 120),
('TRJ0019', 'MN001', 'BA003', 200),
('TRJ0019', 'MN001', 'BA025', 300),
('TRJ0019', 'MN001', 'BA026', 20),
('TRJ0019', 'MN017', 'BA028', 300),
('TRJ0019', 'MN017', 'BA018', 40),
('TRJ0019', 'MN017', 'BA019', 20),
('TRJ0019', 'MN002', 'BA025', 1250),
('TRJ0019', 'MN002', 'BA005', 200),
('TRJ0020', 'MN012', 'BA011', 150),
('TRJ0020', 'MN012', 'BA022', 750),
('TRJ0020', 'MN012', 'BA018', 30),
('TRJ0020', 'MN012', 'BA019', 30),
('TRJ0020', 'MN022', 'BA001', 30),
('TRJ0020', 'MN022', 'BA025', 750),
('TRJ0020', 'MN022', 'BA026', 75),
('TRJ0020', 'MN022', 'BA005', 150),
('TRJ0020', 'MN013', 'BA002', 300),
('TRJ0020', 'MN013', 'BA026', 20),
('TRJ0020', 'MN013', 'BA019', 160),
('TRJ0020', 'MN013', 'BA030', 20),
('TRJ0021', 'MN021', 'BA025', 600),
('TRJ0021', 'MN021', 'BA026', 60),
('TRJ0021', 'MN021', 'BA005', 100),
('TRJ0021', 'MN017', 'BA028', 150),
('TRJ0021', 'MN017', 'BA018', 20),
('TRJ0021', 'MN017', 'BA019', 10),
('TRJ0022', 'MN022', 'BA001', 20),
('TRJ0022', 'MN022', 'BA025', 500),
('TRJ0022', 'MN022', 'BA026', 50),
('TRJ0022', 'MN022', 'BA005', 100),
('TRJ0022', 'MN013', 'BA002', 450),
('TRJ0022', 'MN013', 'BA026', 30),
('TRJ0022', 'MN013', 'BA019', 240),
('TRJ0022', 'MN013', 'BA030', 30),
('TRJ0023', 'MN001', 'BA003', 400),
('TRJ0023', 'MN001', 'BA025', 600),
('TRJ0023', 'MN001', 'BA026', 40),
('TRJ0023', 'MN013', 'BA002', 300),
('TRJ0023', 'MN013', 'BA026', 20),
('TRJ0023', 'MN013', 'BA019', 160),
('TRJ0023', 'MN013', 'BA030', 20),
('TRJ0024', 'MN021', 'BA001', 30),
('TRJ0024', 'MN021', 'BA025', 780),
('TRJ0024', 'MN021', 'BA026', 90),
('TRJ0024', 'MN021', 'BA005', 150),
('TRJ0024', 'MN023', 'BA001', 50),
('TRJ0024', 'MN023', 'BA025', 270),
('TRJ0024', 'MN023', 'BA026', 25),
('TRJ0024', 'MN023', 'BA005', 60),
('TRJ0024', 'MN019', 'BA002', 1200),
('TRJ0024', 'MN019', 'BA029', 960),
('TRJ0024', 'MN019', 'BA030', 400),
('TRJ0025', 'MN011', 'BA001', 105),
('TRJ0025', 'MN011', 'BA025', 1400),
('TRJ0025', 'MN011', 'BA026', 350),
('TRJ0025', 'MN011', 'BA005', 70),
('TRJ0025', 'MN012', 'BA011', 250),
('TRJ0025', 'MN012', 'BA022', 1250),
('TRJ0025', 'MN012', 'BA018', 50),
('TRJ0025', 'MN012', 'BA019', 50),
('TRJ0025', 'MN018', 'BA002', 100),
('TRJ0025', 'MN018', 'BA026', 15),
('TRJ0025', 'MN018', 'BA019', 50),
('TRJ0025', 'MN018', 'BA030', 10),
('TRJ0025', 'MN022', 'BA001', 20),
('TRJ0025', 'MN022', 'BA025', 500),
('TRJ0025', 'MN022', 'BA026', 50),
('TRJ0025', 'MN022', 'BA005', 100),
('TRJ0026', 'MN015', 'BA025', 480),
('TRJ0026', 'MN015', 'BA026', 200),
('TRJ0026', 'MN015', 'BA027', 300),
('TRJ0026', 'MN001', 'BA003', 1400),
('TRJ0026', 'MN001', 'BA025', 2100),
('TRJ0026', 'MN001', 'BA026', 140),
('TRJ0026', 'MN021', 'BA001', 20),
('TRJ0026', 'MN021', 'BA025', 520),
('TRJ0026', 'MN021', 'BA026', 60),
('TRJ0026', 'MN021', 'BA005', 100),
('TRJ0026', 'MN023', 'BA001', 200),
('TRJ0026', 'MN023', 'BA025', 1080),
('TRJ0026', 'MN023', 'BA026', 100),
('TRJ0026', 'MN023', 'BA005', 240),
('TRJ0027', 'MN002', 'BA025', 2250),
('TRJ0027', 'MN002', 'BA005', 360),
('TRJ0027', 'MN013', 'BA002', 300),
('TRJ0027', 'MN013', 'BA026', 20),
('TRJ0027', 'MN013', 'BA019', 160),
('TRJ0027', 'MN013', 'BA030', 20),
('TRJ0027', 'MN018', 'BA002', 100),
('TRJ0027', 'MN018', 'BA026', 15),
('TRJ0027', 'MN018', 'BA019', 50),
('TRJ0027', 'MN018', 'BA030', 10),
('TRJ0027', 'MN022', 'BA001', 20),
('TRJ0027', 'MN022', 'BA025', 500),
('TRJ0027', 'MN022', 'BA026', 50),
('TRJ0027', 'MN022', 'BA005', 100),
('TRJ0027', 'MN012', 'BA011', 400),
('TRJ0027', 'MN012', 'BA022', 2000),
('TRJ0027', 'MN012', 'BA018', 80),
('TRJ0027', 'MN012', 'BA019', 80),
('TRJ0028', 'MN002', 'BA025', 500),
('TRJ0028', 'MN002', 'BA005', 80),
('TRJ0028', 'MN015', 'BA025', 240),
('TRJ0028', 'MN015', 'BA026', 100),
('TRJ0028', 'MN015', 'BA027', 150),
('TRJ0028', 'MN018', 'BA002', 200),
('TRJ0028', 'MN018', 'BA026', 30),
('TRJ0028', 'MN018', 'BA019', 100),
('TRJ0028', 'MN018', 'BA030', 20),
('TRJ0029', 'MN012', 'BA011', 350),
('TRJ0029', 'MN012', 'BA022', 1750),
('TRJ0029', 'MN012', 'BA018', 70),
('TRJ0029', 'MN012', 'BA019', 70),
('TRJ0029', 'MN011', 'BA001', 60),
('TRJ0029', 'MN011', 'BA025', 800),
('TRJ0029', 'MN011', 'BA026', 200),
('TRJ0029', 'MN011', 'BA005', 40),
('TRJ0029', 'MN018', 'BA002', 700),
('TRJ0029', 'MN018', 'BA026', 105),
('TRJ0029', 'MN018', 'BA019', 350),
('TRJ0029', 'MN018', 'BA030', 70),
('TRJ0030', 'MN011', 'BA001', 30),
('TRJ0030', 'MN011', 'BA025', 400),
('TRJ0030', 'MN011', 'BA026', 100),
('TRJ0030', 'MN011', 'BA005', 20),
('TRJ0030', 'MN012', 'BA011', 50),
('TRJ0030', 'MN012', 'BA022', 250),
('TRJ0030', 'MN012', 'BA018', 10),
('TRJ0030', 'MN012', 'BA019', 10),
('TRJ0030', 'MN018', 'BA002', 300),
('TRJ0030', 'MN018', 'BA026', 45),
('TRJ0030', 'MN018', 'BA019', 150),
('TRJ0030', 'MN018', 'BA030', 30),
('TRJ0031', 'MN012', 'BA011', 200),
('TRJ0031', 'MN012', 'BA022', 1000),
('TRJ0031', 'MN012', 'BA018', 40),
('TRJ0031', 'MN012', 'BA019', 40),
('TRJ0032', 'MN017', 'BA028', 1350),
('TRJ0032', 'MN017', 'BA018', 180),
('TRJ0032', 'MN017', 'BA019', 90),
('TRJ0033', 'MN013', 'BA002', 450),
('TRJ0033', 'MN013', 'BA026', 30),
('TRJ0033', 'MN013', 'BA019', 240),
('TRJ0033', 'MN013', 'BA030', 30),
('TRJ0034', 'MN018', 'BA002', 300),
('TRJ0034', 'MN018', 'BA026', 45),
('TRJ0034', 'MN018', 'BA019', 150),
('TRJ0034', 'MN018', 'BA030', 30),
('TRJ0034', 'MN016', 'BA001', 100),
('TRJ0034', 'MN016', 'BA025', 500),
('TRJ0034', 'MN016', 'BA026', 60),
('TRJ0034', 'MN016', 'BA005', 50),
('TRJ0035', 'MN017', 'BA028', 1500),
('TRJ0035', 'MN017', 'BA018', 200),
('TRJ0035', 'MN017', 'BA019', 100),
('TRJ0036', 'MN002', 'BA025', 500),
('TRJ0036', 'MN002', 'BA005', 80),
('TRJ0036', 'MN016', 'BA001', 50),
('TRJ0036', 'MN016', 'BA025', 250),
('TRJ0036', 'MN016', 'BA026', 30),
('TRJ0036', 'MN016', 'BA005', 25),
('TRJ0036', 'MN001', 'BA003', 200),
('TRJ0036', 'MN001', 'BA025', 300),
('TRJ0036', 'MN001', 'BA026', 20),
('TRJ0036', 'MN012', 'BA011', 100),
('TRJ0036', 'MN012', 'BA022', 500),
('TRJ0036', 'MN012', 'BA018', 20),
('TRJ0036', 'MN012', 'BA019', 20),
('TRJ0036', 'MN018', 'BA002', 200),
('TRJ0036', 'MN018', 'BA026', 30),
('TRJ0036', 'MN018', 'BA019', 100),
('TRJ0036', 'MN018', 'BA030', 20),
('TRJ0037', 'MN011', 'BA001', 30),
('TRJ0037', 'MN011', 'BA025', 400),
('TRJ0037', 'MN011', 'BA026', 100),
('TRJ0037', 'MN011', 'BA005', 20),
('TRJ0037', 'MN016', 'BA001', 50),
('TRJ0037', 'MN016', 'BA025', 250),
('TRJ0037', 'MN016', 'BA026', 30),
('TRJ0037', 'MN016', 'BA005', 25),
('TRJ0037', 'MN019', 'BA002', 300),
('TRJ0037', 'MN019', 'BA029', 240),
('TRJ0037', 'MN019', 'BA030', 100),
('TRJ0037', 'MN001', 'BA003', 200),
('TRJ0037', 'MN001', 'BA025', 300),
('TRJ0037', 'MN001', 'BA026', 20),
('TRJ0038', 'MN016', 'BA001', 100),
('TRJ0038', 'MN016', 'BA025', 500),
('TRJ0038', 'MN016', 'BA026', 60),
('TRJ0038', 'MN016', 'BA005', 50),
('TRJ0038', 'MN011', 'BA001', 30),
('TRJ0038', 'MN011', 'BA025', 400),
('TRJ0038', 'MN011', 'BA026', 100),
('TRJ0038', 'MN011', 'BA005', 20),
('TRJ0038', 'MN012', 'BA011', 50),
('TRJ0038', 'MN012', 'BA022', 250),
('TRJ0038', 'MN012', 'BA018', 10),
('TRJ0038', 'MN012', 'BA019', 10),
('TRJ0039', 'MN016', 'BA001', 50),
('TRJ0039', 'MN016', 'BA025', 250),
('TRJ0039', 'MN016', 'BA026', 30),
('TRJ0039', 'MN016', 'BA005', 25),
('TRJ0040', 'MN011', 'BA001', 30),
('TRJ0040', 'MN011', 'BA025', 400),
('TRJ0040', 'MN011', 'BA026', 100),
('TRJ0040', 'MN011', 'BA005', 20),
('TRJ0040', 'MN019', 'BA002', 150),
('TRJ0040', 'MN019', 'BA029', 120),
('TRJ0040', 'MN019', 'BA030', 50),
('TRJ0041', 'MN023', 'BA001', 50),
('TRJ0041', 'MN023', 'BA025', 270),
('TRJ0041', 'MN023', 'BA026', 25),
('TRJ0041', 'MN023', 'BA005', 60),
('TRJ0041', 'MN020', 'BA025', 500),
('TRJ0041', 'MN020', 'BA015', 300),
('TRJ0041', 'MN020', 'BA026', 200),
('TRJ0042', 'MN015', 'BA025', 480),
('TRJ0042', 'MN015', 'BA026', 200),
('TRJ0042', 'MN015', 'BA027', 300),
('TRJ0043', 'MN013', 'BA002', 450),
('TRJ0043', 'MN013', 'BA026', 30),
('TRJ0043', 'MN013', 'BA019', 240),
('TRJ0043', 'MN013', 'BA030', 30),
('TRJ0043', 'MN018', 'BA002', 200),
('TRJ0043', 'MN018', 'BA026', 30),
('TRJ0043', 'MN018', 'BA019', 100),
('TRJ0043', 'MN018', 'BA030', 20),
('TRJ0044', 'MN021', 'BA001', 20),
('TRJ0044', 'MN021', 'BA025', 520),
('TRJ0044', 'MN021', 'BA026', 60),
('TRJ0044', 'MN021', 'BA005', 100),
('TRJ0045', 'MN019', 'BA002', 150),
('TRJ0045', 'MN019', 'BA029', 120),
('TRJ0045', 'MN019', 'BA030', 50),
('TRJ0046', 'MN012', 'BA011', 50),
('TRJ0046', 'MN012', 'BA022', 250),
('TRJ0046', 'MN012', 'BA018', 10),
('TRJ0046', 'MN012', 'BA019', 10),
('TRJ0047', 'MN011', 'BA001', 60),
('TRJ0047', 'MN011', 'BA025', 800),
('TRJ0047', 'MN011', 'BA026', 200),
('TRJ0047', 'MN011', 'BA005', 40),
('TRJ0048', 'MN016', 'BA001', 100),
('TRJ0048', 'MN016', 'BA025', 500),
('TRJ0048', 'MN016', 'BA026', 60),
('TRJ0048', 'MN016', 'BA005', 50),
('TRJ0048', 'MN002', 'BA025', 500),
('TRJ0048', 'MN002', 'BA005', 80),
('TRJ0048', 'MN001', 'BA003', 600),
('TRJ0048', 'MN001', 'BA025', 900),
('TRJ0048', 'MN001', 'BA026', 60),
('TRJ0049', 'MN011', 'BA001', 15),
('TRJ0049', 'MN011', 'BA025', 200),
('TRJ0049', 'MN011', 'BA026', 50),
('TRJ0049', 'MN011', 'BA005', 10),
('TRJ0049', 'MN015', 'BA025', 720),
('TRJ0049', 'MN015', 'BA026', 300),
('TRJ0049', 'MN015', 'BA027', 450),
('TRJ0049', 'MN022', 'BA001', 10),
('TRJ0049', 'MN022', 'BA025', 250),
('TRJ0049', 'MN022', 'BA026', 25),
('TRJ0049', 'MN022', 'BA005', 50),
('TRJ0049', 'MN001', 'BA003', 200),
('TRJ0049', 'MN001', 'BA025', 300),
('TRJ0049', 'MN001', 'BA026', 20),
('TRJ0049', 'MN013', 'BA002', 300),
('TRJ0049', 'MN013', 'BA026', 20),
('TRJ0049', 'MN013', 'BA019', 160),
('TRJ0049', 'MN013', 'BA030', 20),
('TRJ0050', 'MN012', 'BA011', 100),
('TRJ0050', 'MN012', 'BA022', 500),
('TRJ0050', 'MN012', 'BA018', 20),
('TRJ0050', 'MN012', 'BA019', 20),
('TRJ0050', 'MN020', 'BA025', 250),
('TRJ0050', 'MN020', 'BA015', 150),
('TRJ0050', 'MN020', 'BA026', 100),
('TRJ0051', 'MN001', 'BA003', 200),
('TRJ0051', 'MN001', 'BA025', 300),
('TRJ0051', 'MN001', 'BA026', 20),
('TRJ0052', 'MN011', 'BA001', 60),
('TRJ0052', 'MN011', 'BA025', 800),
('TRJ0052', 'MN011', 'BA026', 200),
('TRJ0052', 'MN011', 'BA005', 40),
('TRJ0052', 'MN012', 'BA011', 100),
('TRJ0052', 'MN012', 'BA022', 500),
('TRJ0052', 'MN012', 'BA018', 20),
('TRJ0052', 'MN012', 'BA019', 20),
('TRJ0052', 'MN018', 'BA002', 300),
('TRJ0052', 'MN018', 'BA026', 45),
('TRJ0052', 'MN018', 'BA019', 150),
('TRJ0052', 'MN018', 'BA030', 30),
('TRJ0053', 'MN016', 'BA001', 200),
('TRJ0053', 'MN016', 'BA025', 1000),
('TRJ0053', 'MN016', 'BA026', 120),
('TRJ0053', 'MN016', 'BA005', 100),
('TRJ0053', 'MN013', 'BA002', 300),
('TRJ0053', 'MN013', 'BA026', 20),
('TRJ0053', 'MN013', 'BA019', 160),
('TRJ0053', 'MN013', 'BA030', 20),
('TRJ0053', 'MN012', 'BA011', 400),
('TRJ0053', 'MN012', 'BA022', 2000),
('TRJ0053', 'MN012', 'BA018', 80),
('TRJ0053', 'MN012', 'BA019', 80),
('TRJ0054', 'MN012', 'BA011', 200),
('TRJ0054', 'MN012', 'BA022', 1000),
('TRJ0054', 'MN012', 'BA018', 40),
('TRJ0054', 'MN012', 'BA019', 40),
('TRJ0054', 'MN002', 'BA025', 2500),
('TRJ0054', 'MN002', 'BA005', 400),
('TRJ0055', 'MN011', 'BA001', 60),
('TRJ0055', 'MN011', 'BA025', 800),
('TRJ0055', 'MN011', 'BA026', 200),
('TRJ0055', 'MN011', 'BA005', 40),
('TRJ0055', 'MN013', 'BA002', 1350),
('TRJ0055', 'MN013', 'BA026', 90),
('TRJ0055', 'MN013', 'BA019', 720),
('TRJ0055', 'MN013', 'BA030', 90),
('TRJ0056', 'MN017', 'BA028', 3000),
('TRJ0056', 'MN017', 'BA018', 400),
('TRJ0056', 'MN017', 'BA019', 200),
('TRJ0056', 'MN013', 'BA002', 1050),
('TRJ0056', 'MN013', 'BA026', 70),
('TRJ0056', 'MN013', 'BA019', 560),
('TRJ0056', 'MN013', 'BA030', 70),
('TRJ0057', 'MN021', 'BA001', 40),
('TRJ0057', 'MN021', 'BA025', 1040),
('TRJ0057', 'MN021', 'BA026', 120),
('TRJ0057', 'MN021', 'BA005', 200),
('TRJ0058', 'MN013', 'BA002', 750),
('TRJ0058', 'MN013', 'BA026', 50),
('TRJ0058', 'MN013', 'BA019', 400),
('TRJ0058', 'MN013', 'BA030', 50),
('TRJ0059', 'MN022', 'BA001', 50),
('TRJ0059', 'MN022', 'BA025', 1250),
('TRJ0059', 'MN022', 'BA026', 125),
('TRJ0059', 'MN022', 'BA005', 250),
('TRJ0060', 'MN022', 'BA001', 50),
('TRJ0060', 'MN022', 'BA025', 1250),
('TRJ0060', 'MN022', 'BA026', 125),
('TRJ0060', 'MN022', 'BA005', 250),
('TRJ0060', 'MN015', 'BA025', 720),
('TRJ0060', 'MN015', 'BA026', 300),
('TRJ0060', 'MN015', 'BA027', 450),
('TRJ0061', 'MN011', 'BA001', 45),
('TRJ0061', 'MN011', 'BA025', 600),
('TRJ0061', 'MN011', 'BA026', 150),
('TRJ0061', 'MN011', 'BA005', 30),
('TRJ0062', 'MN013', 'BA002', 450),
('TRJ0062', 'MN013', 'BA026', 30),
('TRJ0062', 'MN013', 'BA019', 240),
('TRJ0062', 'MN013', 'BA030', 30),
('TRJ0063', 'MN019', 'BA002', 300),
('TRJ0063', 'MN019', 'BA029', 240),
('TRJ0063', 'MN019', 'BA030', 100),
('TRJ0063', 'MN023', 'BA001', 250),
('TRJ0063', 'MN023', 'BA025', 1350),
('TRJ0063', 'MN023', 'BA026', 125),
('TRJ0063', 'MN023', 'BA005', 300),
('TRJ0063', 'MN001', 'BA003', 400),
('TRJ0063', 'MN001', 'BA025', 600),
('TRJ0063', 'MN001', 'BA026', 40),
('TRJ0064', 'MN023', 'BA001', 100),
('TRJ0064', 'MN023', 'BA025', 540),
('TRJ0064', 'MN023', 'BA026', 50),
('TRJ0064', 'MN023', 'BA005', 120),
('TRJ0065', 'MN016', 'BA001', 200),
('TRJ0065', 'MN016', 'BA025', 1000),
('TRJ0065', 'MN016', 'BA026', 120),
('TRJ0065', 'MN016', 'BA005', 100),
('TRJ0065', 'MN002', 'BA025', 500),
('TRJ0065', 'MN002', 'BA005', 80),
('TRJ0066', 'MN020', 'BA025', 750),
('TRJ0066', 'MN020', 'BA015', 450),
('TRJ0066', 'MN020', 'BA026', 300),
('TRJ0067', 'MN017', 'BA028', 750),
('TRJ0067', 'MN017', 'BA018', 100),
('TRJ0067', 'MN017', 'BA019', 50),
('TRJ0068', 'MN013', 'BA002', 450),
('TRJ0068', 'MN013', 'BA026', 30),
('TRJ0068', 'MN013', 'BA019', 240),
('TRJ0068', 'MN013', 'BA030', 30),
('TRJ0068', 'MN001', 'BA003', 200),
('TRJ0068', 'MN001', 'BA025', 300),
('TRJ0068', 'MN001', 'BA026', 20),
('TRJ0068', 'MN019', 'BA002', 900),
('TRJ0068', 'MN019', 'BA029', 720),
('TRJ0068', 'MN019', 'BA030', 300),
('TRJ0069', 'MN019', 'BA002', 150),
('TRJ0069', 'MN019', 'BA029', 120),
('TRJ0069', 'MN019', 'BA030', 50),
('TRJ0070', 'MN017', 'BA028', 750),
('TRJ0070', 'MN017', 'BA018', 100),
('TRJ0070', 'MN017', 'BA019', 50),
('TRJ0071', 'MN013', 'BA002', 450),
('TRJ0071', 'MN013', 'BA026', 30),
('TRJ0071', 'MN013', 'BA019', 240),
('TRJ0071', 'MN013', 'BA030', 30),
('TRJ0072', 'MN018', 'BA002', 600),
('TRJ0072', 'MN018', 'BA026', 90),
('TRJ0072', 'MN018', 'BA019', 300),
('TRJ0072', 'MN018', 'BA030', 60),
('TRJ0073', 'MN013', 'BA002', 600),
('TRJ0073', 'MN013', 'BA026', 40),
('TRJ0073', 'MN013', 'BA019', 320),
('TRJ0073', 'MN013', 'BA030', 40),
('TRJ0073', 'MN015', 'BA025', 960),
('TRJ0073', 'MN015', 'BA026', 400),
('TRJ0073', 'MN015', 'BA027', 600),
('TRJ0074', 'MN013', 'BA002', 300),
('TRJ0074', 'MN013', 'BA026', 20),
('TRJ0074', 'MN013', 'BA019', 160),
('TRJ0074', 'MN013', 'BA030', 20),
('TRJ0075', 'MN013', 'BA002', 600),
('TRJ0075', 'MN013', 'BA026', 40),
('TRJ0075', 'MN013', 'BA019', 320),
('TRJ0075', 'MN013', 'BA030', 40),
('TRJ0076', 'MN019', 'BA002', 750),
('TRJ0076', 'MN019', 'BA029', 600),
('TRJ0076', 'MN019', 'BA030', 250),
('TRJ0077', 'MN011', 'BA001', 30),
('TRJ0077', 'MN011', 'BA025', 400),
('TRJ0077', 'MN011', 'BA026', 100),
('TRJ0077', 'MN011', 'BA005', 20),
('TRJ0078', 'MN011', 'BA001', 120),
('TRJ0078', 'MN011', 'BA025', 1600),
('TRJ0078', 'MN011', 'BA026', 400),
('TRJ0078', 'MN011', 'BA005', 80),
('TRJ0079', 'MN011', 'BA001', 30),
('TRJ0079', 'MN011', 'BA025', 400),
('TRJ0079', 'MN011', 'BA026', 100),
('TRJ0079', 'MN011', 'BA005', 20),
('TRJ0080', 'MN022', 'BA001', 40),
('TRJ0080', 'MN022', 'BA025', 1000),
('TRJ0080', 'MN022', 'BA026', 100),
('TRJ0080', 'MN022', 'BA005', 200),
('TRJ0081', 'MN022', 'BA001', 30),
('TRJ0081', 'MN022', 'BA025', 750),
('TRJ0081', 'MN022', 'BA026', 75),
('TRJ0081', 'MN022', 'BA005', 150),
('TRJ0081', 'MN012', 'BA011', 50),
('TRJ0081', 'MN012', 'BA022', 250),
('TRJ0081', 'MN012', 'BA018', 10),
('TRJ0081', 'MN012', 'BA019', 10),
('TRJ0082', 'MN014', 'BA025', 320),
('TRJ0082', 'MN014', 'BA026', 20),
('TRJ0082', 'MN014', 'BA005', 50),
('TRJ0082', 'MN014', 'BA008', 10),
('TRJ0083', 'MN019', 'BA002', 600),
('TRJ0083', 'MN019', 'BA029', 480),
('TRJ0083', 'MN019', 'BA030', 200),
('TRJ0084', 'MN013', 'BA002', 750),
('TRJ0084', 'MN013', 'BA026', 50),
('TRJ0084', 'MN013', 'BA019', 400),
('TRJ0084', 'MN013', 'BA030', 50),
('TRJ0085', 'MN016', 'BA001', 50),
('TRJ0085', 'MN016', 'BA025', 250),
('TRJ0085', 'MN016', 'BA026', 30),
('TRJ0085', 'MN016', 'BA005', 25),
('TRJ0086', 'MN001', 'BA003', 200),
('TRJ0086', 'MN001', 'BA025', 300),
('TRJ0086', 'MN001', 'BA026', 20),
('TRJ0086', 'MN012', 'BA011', 50),
('TRJ0086', 'MN012', 'BA022', 250),
('TRJ0086', 'MN012', 'BA018', 10),
('TRJ0086', 'MN012', 'BA019', 10),
('TRJ0087', 'MN019', 'BA002', 300),
('TRJ0087', 'MN019', 'BA029', 240),
('TRJ0087', 'MN019', 'BA030', 100),
('TRJ0088', 'MN018', 'BA002', 300),
('TRJ0088', 'MN018', 'BA026', 45),
('TRJ0088', 'MN018', 'BA019', 150),
('TRJ0088', 'MN018', 'BA030', 30),
('TRJ0089', 'MN021', 'BA001', 40),
('TRJ0089', 'MN021', 'BA025', 1040),
('TRJ0089', 'MN021', 'BA026', 120),
('TRJ0089', 'MN021', 'BA005', 200),
('TRJ0090', 'MN022', 'BA001', 40),
('TRJ0090', 'MN022', 'BA025', 1000),
('TRJ0090', 'MN022', 'BA026', 100),
('TRJ0090', 'MN022', 'BA005', 200),
('TRJ0091', 'MN017', 'BA028', 150),
('TRJ0091', 'MN017', 'BA018', 20),
('TRJ0091', 'MN017', 'BA019', 10),
('TRJ0091', 'MN001', 'BA003', 200),
('TRJ0091', 'MN001', 'BA025', 300),
('TRJ0091', 'MN001', 'BA026', 20);

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
('MN001', 'Jus Jeruk', 'Minuman', 10000),
('MN002', 'Coffee Latte', 'Original Coffee', 10000),
('MN011', 'Chocholate', 'Falvoured Coffee', 15000),
('MN012', 'Ayam Bakar', 'Makanan', 15000),
('MN013', 'Kentang Goreng', 'Snack', 12000),
('MN014', 'Black Coffee', 'Original Coffee', 13000),
('MN015', 'Jus Anggur', 'Minuman', 12000),
('MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000),
('MN017', 'Nasi Goreng', 'Makanan', 12000),
('MN018', 'Keripik Kentang', 'Snack', 8000),
('MN019', 'Jamur Crispy', 'Snack', 10000),
('MN020', 'Jus Apel', 'Minuman', 11000),
('MN021', 'Moca Latte', 'Falvoured Coffee', 12000),
('MN022', 'Cappucino', 'Original Coffee', 10000),
('MN023', 'Kopi Susu', 'Minuman', 3000);

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
('SP001', 'Moch. Alvian Hidayatulloh', '085990128912', 'Nganjuk, Jawa Timur'),
('SP002', 'Afrizal Wahyu Alkautsar ', '086790238923', 'Nganjuk, Jawa Timur'),
('SP003', 'Syamaidzar Adani Syah', '085690231830', 'Nganjuk, Jawa Timur'),
('SP004', 'Pramudya Putra Pratama', '081289378712', 'Kediri, Jawa Timur'),
('SP005', 'Syafrizal Wd Mahendra', '085690237823', 'Jobang, Jawa Timur'),
('SP008', 'M. Ferdiansyah', '085690238923', 'Jombang, Jawa Timur'),
('SP010', 'Amirzan Fikri Prasetyo', '085690238923', 'Jombang, Jawa Timur');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi_beli`
--

CREATE TABLE `transaksi_beli` (
  `id_tr_beli` varchar(7) NOT NULL,
  `id_karyawan` varchar(6) DEFAULT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `id_supplier` varchar(6) DEFAULT NULL,
  `nama_supplier` varchar(50) NOT NULL,
  `total_bahan` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `tanggal` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `transaksi_beli`
--

INSERT INTO `transaksi_beli` (`id_tr_beli`, `id_karyawan`, `nama_karyawan`, `id_supplier`, `nama_supplier`, `total_bahan`, `total_harga`, `tanggal`) VALUES
('TRB0001', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 1, 25000, '2022-10-29 23:30:42'),
('TRB0002', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 1, 50000, '2022-10-30 23:32:44'),
('TRB0003', 'KY001', 'Achmad Baihaqi', 'SP003', 'Syamaidzar Adani Syah', 2, 50000, '2022-10-30 23:33:00'),
('TRB0004', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 5, 15000, '2022-10-31 23:38:03'),
('TRB0005', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 2, 10000, '2022-10-09 12:41:03'),
('TRB0006', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 1, 15000, '2022-10-09 12:42:02'),
('TRB0007', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 9, 95000, '2022-11-05 12:48:33'),
('TRB0008', 'KY001', 'Achmad Baihaqi', 'SP004', 'Pramudya Putra Pratama', 4, 56000, '2022-11-05 12:50:14'),
('TRB0009', 'KY001', 'Achmad Baihaqi', 'SP002', 'Afrizal Wahyu Alkautsar ', 1, 120000, '2022-11-05 12:50:25'),
('TRB0010', 'KY001', 'Achmad Baihaqi', 'SP002', 'Afrizal Wahyu Alkautsar ', 3, 155000, '2022-11-05 12:52:13'),
('TRB0011', 'KY001', 'Achmad Baihaqi', 'SP003', 'Syamaidzar Adani Syah', 3, 75000, '2022-11-05 12:52:24'),
('TRB0012', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 1, 50000, '2022-11-05 12:52:43'),
('TRB0013', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 3, 150000, '2022-11-13 04:26:14'),
('TRB0014', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 2, 16000, '2022-11-13 04:26:34'),
('TRB0015', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 5, 30000, '2022-11-13 04:26:46'),
('TRB0016', 'KY001', 'Achmad Baihaqi', 'SP003', 'Syamaidzar Adani Syah', 2, 50000, '2022-11-13 04:27:06'),
('TRB0017', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 3, 26000, '2022-11-15 05:28:28'),
('TRB0018', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 2, 40000, '2022-11-15 05:29:05'),
('TRB0019', 'KY001', 'Achmad Baihaqi', 'SP003', 'Syamaidzar Adani Syah', 5, 125000, '2022-11-17 05:30:02'),
('TRB0020', 'KY001', 'Achmad Baihaqi', 'SP004', 'Pramudya Putra Pratama', 5, 70000, '2022-11-17 05:30:47'),
('TRB0021', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 12, 36000, '2022-11-17 05:31:28'),
('TRB0022', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 8, 64000, '2022-11-21 10:11:11'),
('TRB0023', 'KY001', 'Achmad Baihaqi', 'SP002', 'Afrizal Wahyu Alkautsar ', 2, 30000, '2022-11-21 10:11:24'),
('TRB0024', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 5, 50000, '2022-11-21 10:11:55'),
('TRB0025', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 9, 200000, '2022-11-21 10:12:21'),
('TRB0026', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 13, 41500, '2022-11-27 03:16:09'),
('TRB0027', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 5, 25000, '2022-12-01 08:17:41'),
('TRB0028', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 8, 202000, '2022-12-01 08:18:14'),
('TRB0029', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 5, 250000, '2022-12-01 08:18:38'),
('TRB0030', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 3, 4500, '2022-12-03 08:19:23'),
('TRB0031', 'KY001', 'Achmad Baihaqi', 'SP003', 'Syamaidzar Adani Syah', 2, 140000, '2022-12-03 08:19:35'),
('TRB0032', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 3, 150000, '2022-12-14 03:32:42'),
('TRB0033', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 10, 30000, '2022-12-14 03:32:57'),
('TRB0034', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 5, 250000, '2022-12-14 03:33:24'),
('TRB0035', 'KY001', 'Achmad Baihaqi', 'SP004', 'Pramudya Putra Pratama', 2, 140000, '2022-12-11 03:34:02'),
('TRB0036', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 5, 7500, '2022-12-07 03:34:47'),
('TRB0037', 'KY001', 'Achmad Baihaqi', 'SP003', 'Syamaidzar Adani Syah', 3, 210000, '2022-12-07 03:35:30'),
('TRB0038', 'KY004', 'Septian Yoga', 'SP004', 'Pramudya Putra Pratama', 4, 56000, '2022-12-18 12:45:06'),
('TRB0039', 'KY004', 'Septian Yoga', 'SP005', 'Syafrizal Wd Mahendra', 5, 15000, '2022-12-21 12:45:51'),
('TRB0040', 'KY004', 'Septian Yoga', 'SP002', 'Afrizal Wahyu Alkautsar ', 3, 75000, '2022-12-21 12:46:15'),
('TRB0041', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 3, 150000, '2022-12-25 12:47:52'),
('TRB0042', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 10, 50000, '2022-12-28 12:49:13'),
('TRB0043', 'KY001', 'Achmad Baihaqi', 'SP002', 'Afrizal Wahyu Alkautsar ', 1, 70000, '2022-12-31 12:52:23'),
('TRB0044', 'KY004', 'Septian Yoga', 'SP005', 'Syafrizal Wd Mahendra', 5, 15000, '2023-01-03 06:57:51'),
('TRB0045', 'KY004', 'Septian Yoga', 'SP010', 'Amirzan Fikri Prasetyo', 1, 20000, '2023-01-03 06:58:24');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi_jual`
--

CREATE TABLE `transaksi_jual` (
  `id_tr_jual` varchar(7) NOT NULL,
  `id_karyawan` varchar(6) DEFAULT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `nama_pembeli` varchar(30) NOT NULL,
  `total_menu` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `tanggal` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `transaksi_jual`
--

INSERT INTO `transaksi_jual` (`id_tr_jual`, `id_karyawan`, `nama_karyawan`, `nama_pembeli`, `total_menu`, `total_harga`, `tanggal`) VALUES
('TRJ0001', 'KY001', 'Achmad Baihaqi', '', 3, 35000, '2022-10-28 23:28:00'),
('TRJ0002', 'KY001', 'Achmad Baihaqi', '', 1, 12000, '2022-10-28 23:28:29'),
('TRJ0003', 'KY001', 'Achmad Baihaqi', '', 2, 30000, '2022-10-29 23:28:57'),
('TRJ0004', 'KY001', 'Achmad Baihaqi', '', 1, 10000, '2022-10-29 23:29:21'),
('TRJ0005', 'KY001', 'Achmad Baihaqi', '', 9, 116000, '2022-10-30 23:31:51'),
('TRJ0006', 'KY001', 'Achmad Baihaqi', '', 1, 3000, '2022-10-30 23:32:21'),
('TRJ0007', 'KY001', 'Achmad Baihaqi', '', 6, 90000, '2022-10-30 23:33:23'),
('TRJ0008', 'KY001', 'Achmad Baihaqi', '', 2, 24000, '2022-10-30 23:33:55'),
('TRJ0009', 'KY001', 'Achmad Baihaqi', '', 2, 20000, '2022-10-30 23:34:14'),
('TRJ0010', 'KY001', 'Achmad Baihaqi', '', 6, 75000, '2022-10-31 23:35:45'),
('TRJ0011', 'KY001', 'Achmad Baihaqi', '', 6, 80000, '2022-10-31 23:36:41'),
('TRJ0012', 'KY001', 'Achmad Baihaqi', '', 1, 15000, '2022-10-07 12:44:16'),
('TRJ0013', 'KY001', 'Achmad Baihaqi', '', 2, 22000, '2022-10-07 12:44:39'),
('TRJ0014', 'KY001', 'Achmad Baihaqi', '', 5, 48000, '2022-10-07 02:45:18'),
('TRJ0015', 'KY001', 'Achmad Baihaqi', '', 8, 108000, '2022-11-08 08:46:36'),
('TRJ0016', 'KY001', 'Achmad Baihaqi', '', 8, 88000, '2022-11-09 12:47:40'),
('TRJ0017', 'KY001', 'Achmad Baihaqi', 'Budi', 8, 102000, '2022-11-11 22:01:57'),
('TRJ0018', 'KY001', 'Achmad Baihaqi', 'sulton', 8, 94000, '2022-11-11 22:02:54'),
('TRJ0019', 'KY001', 'Achmad Baihaqi', 'sutrisno', 13, 135000, '2022-11-11 22:03:44'),
('TRJ0020', 'KY001', 'Achmad Baihaqi', '', 8, 99000, '2022-11-13 04:23:46'),
('TRJ0021', 'KY001', 'Achmad Baihaqi', '', 3, 36000, '2022-11-13 04:24:08'),
('TRJ0022', 'KY001', 'Achmad Baihaqi', '', 5, 56000, '2022-11-13 04:24:19'),
('TRJ0023', 'KY001', 'Achmad Baihaqi', '', 4, 44000, '2022-11-15 05:34:32'),
('TRJ0024', 'KY001', 'Achmad Baihaqi', 'Bambang', 12, 119000, '2022-11-15 05:34:59'),
('TRJ0025', 'KY001', 'Achmad Baihaqi', '', 15, 208000, '2022-11-16 11:35:48'),
('TRJ0026', 'KY001', 'Achmad Baihaqi', '', 15, 130000, '2022-11-17 09:05:32'),
('TRJ0027', 'KY001', 'Achmad Baihaqi', 'sultan', 22, 262000, '2022-11-17 09:06:12'),
('TRJ0028', 'KY002', 'Mohammad Ilham', '', 5, 48000, '2022-11-17 09:06:53'),
('TRJ0029', 'KY002', 'Mohammad Ilham', '', 18, 221000, '2022-11-19 10:08:00'),
('TRJ0030', 'KY002', 'Mohammad Ilham', '', 6, 69000, '2022-11-19 10:09:24'),
('TRJ0031', 'KY001', 'Achmad Baihaqi', '', 4, 60000, '2022-11-23 10:12:53'),
('TRJ0032', 'KY001', 'Achmad Baihaqi', '', 9, 108000, '2022-11-23 10:13:08'),
('TRJ0033', 'KY001', 'Achmad Baihaqi', '', 3, 36000, '2022-11-25 10:13:32'),
('TRJ0034', 'KY001', 'Achmad Baihaqi', '', 5, 48000, '2022-11-27 03:14:11'),
('TRJ0035', 'KY001', 'Achmad Baihaqi', '', 10, 120000, '2022-11-29 03:14:48'),
('TRJ0036', 'KY001', 'Achmad Baihaqi', 'agung', 8, 88000, '2022-12-01 08:21:00'),
('TRJ0037', 'KY001', 'Achmad Baihaqi', '', 6, 72000, '2022-12-02 23:21:42'),
('TRJ0038', 'KY001', 'Achmad Baihaqi', '', 5, 69000, '2022-12-03 02:22:13'),
('TRJ0039', 'KY001', 'Achmad Baihaqi', '', 1, 12000, '2022-12-03 02:22:27'),
('TRJ0040', 'KY001', 'Achmad Baihaqi', '', 3, 40000, '2022-12-03 02:22:38'),
('TRJ0041', 'KY001', 'Achmad Baihaqi', '', 3, 25000, '2022-12-03 02:22:55'),
('TRJ0042', 'KY001', 'Achmad Baihaqi', '', 2, 24000, '2022-12-05 12:23:33'),
('TRJ0043', 'KY001', 'Achmad Baihaqi', '', 5, 52000, '2022-12-05 12:23:49'),
('TRJ0044', 'KY001', 'Achmad Baihaqi', '', 2, 24000, '2022-12-05 12:24:12'),
('TRJ0045', 'KY001', 'Achmad Baihaqi', '', 1, 10000, '2022-12-05 12:24:18'),
('TRJ0046', 'KY001', 'Achmad Baihaqi', '', 1, 15000, '2022-12-05 12:24:25'),
('TRJ0047', 'KY001', 'Achmad Baihaqi', '', 4, 60000, '2022-12-07 12:24:46'),
('TRJ0048', 'KY001', 'Achmad Baihaqi', '', 7, 74000, '2022-12-09 12:25:50'),
('TRJ0049', 'KY001', 'Achmad Baihaqi', '', 8, 95000, '2022-12-09 12:26:33'),
('TRJ0050', 'KY001', 'Achmad Baihaqi', '', 3, 41000, '2022-12-09 12:26:52'),
('TRJ0051', 'KY001', 'Achmad Baihaqi', '', 1, 10000, '2022-12-09 12:26:58'),
('TRJ0052', 'KY001', 'Achmad Baihaqi', '', 9, 114000, '2022-12-09 12:27:19'),
('TRJ0053', 'KY001', 'Achmad Baihaqi', '', 14, 192000, '2022-12-10 12:28:19'),
('TRJ0054', 'KY001', 'Achmad Baihaqi', '', 14, 160000, '2022-12-11 03:28:54'),
('TRJ0055', 'KY001', 'Achmad Baihaqi', '', 13, 168000, '2022-12-12 03:29:26'),
('TRJ0056', 'KY001', 'Achmad Baihaqi', '', 27, 324000, '2022-12-13 03:30:10'),
('TRJ0057', 'KY001', 'Achmad Baihaqi', '', 4, 48000, '2022-12-14 03:31:10'),
('TRJ0058', 'KY001', 'Achmad Baihaqi', '', 5, 60000, '2022-12-15 03:36:11'),
('TRJ0059', 'KY001', 'Achmad Baihaqi', '', 5, 50000, '2022-12-15 03:36:35'),
('TRJ0060', 'KY001', 'Achmad Baihaqi', '', 8, 86000, '2022-12-16 03:37:19'),
('TRJ0061', 'KY001', 'Achmad Baihaqi', '', 3, 45000, '2022-12-17 03:37:43'),
('TRJ0062', 'KY001', 'Achmad Baihaqi', '', 3, 36000, '2022-12-17 03:37:56'),
('TRJ0063', 'KY001', 'Achmad Baihaqi', '', 9, 55000, '2022-12-17 03:38:23'),
('TRJ0064', 'KY001', 'Achmad Baihaqi', '', 2, 6000, '2022-12-17 03:38:41'),
('TRJ0065', 'KY001', 'Achmad Baihaqi', '', 6, 68000, '2022-12-17 03:38:53'),
('TRJ0066', 'KY001', 'Achmad Baihaqi', '', 3, 33000, '2022-12-19 12:39:23'),
('TRJ0067', 'KY001', 'Achmad Baihaqi', '', 5, 60000, '2022-12-19 12:39:42'),
('TRJ0068', 'KY002', 'Mohammad Ilham', '', 10, 106000, '2022-12-19 12:40:31'),
('TRJ0069', 'KY002', 'Mohammad Ilham', '', 1, 10000, '2022-12-19 12:40:44'),
('TRJ0070', 'KY002', 'Mohammad Ilham', '', 5, 60000, '2022-12-19 12:40:52'),
('TRJ0071', 'KY001', 'Achmad Baihaqi', '', 3, 36000, '2022-12-21 12:41:25'),
('TRJ0072', 'KY003', 'Widyasari Raisya', '', 6, 48000, '2022-12-21 12:41:51'),
('TRJ0073', 'KY003', 'Widyasari Raisya', '', 8, 96000, '2022-12-21 12:42:09'),
('TRJ0074', 'KY004', 'Septian Yoga', '', 2, 24000, '2022-12-21 12:42:41'),
('TRJ0075', 'KY004', 'Septian Yoga', '', 4, 48000, '2022-12-23 12:43:04'),
('TRJ0076', 'KY004', 'Septian Yoga', '', 5, 50000, '2022-12-23 12:43:15'),
('TRJ0077', 'KY004', 'Septian Yoga', '', 2, 30000, '2022-12-23 12:43:23'),
('TRJ0078', 'KY004', 'Septian Yoga', '', 8, 120000, '2022-12-25 12:43:46'),
('TRJ0079', 'KY004', 'Septian Yoga', '', 2, 30000, '2022-12-18 12:44:48'),
('TRJ0080', 'KY001', 'Achmad Baihaqi', '', 4, 40000, '2022-12-28 12:49:48'),
('TRJ0081', 'KY001', 'Achmad Baihaqi', '', 4, 45000, '2022-12-28 12:50:12'),
('TRJ0082', 'KY001', 'Achmad Baihaqi', 'pembeli', 1, 13000, '2022-12-29 12:50:37'),
('TRJ0083', 'KY001', 'Achmad Baihaqi', '', 4, 40000, '2022-12-29 12:50:53'),
('TRJ0084', 'KY001', 'Achmad Baihaqi', '', 5, 60000, '2022-12-31 12:51:14'),
('TRJ0085', 'KY001', 'Achmad Baihaqi', '', 1, 12000, '2023-01-01 01:53:40'),
('TRJ0086', 'KY001', 'Achmad Baihaqi', '', 2, 25000, '2023-01-01 01:54:02'),
('TRJ0087', 'KY002', 'Mohammad Ilham', '', 2, 20000, '2023-01-01 01:54:53'),
('TRJ0088', 'KY003', 'Widyasari Raisya', '', 3, 24000, '2023-01-02 02:55:28'),
('TRJ0089', 'KY003', 'Widyasari Raisya', '', 4, 48000, '2023-01-02 02:56:02'),
('TRJ0090', 'KY004', 'Septian Yoga', '', 4, 40000, '2023-01-02 02:56:37'),
('TRJ0091', 'KY004', 'Septian Yoga', '', 2, 22000, '2023-01-03 06:57:11');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `password` varchar(70) NOT NULL,
  `level` enum('ADMIN','KARYAWAN') NOT NULL,
  `id_karyawan` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`username`, `password`, `level`, `id_karyawan`) VALUES
('ADMIN', '$2a$12$uxtGbykPWmB9WrpkTQ5GoOl/ja/76l0C8bGvdWW532MswWpBqft.u', 'ADMIN', 'KY001'),
('ilham', '$2a$12$G9rldUjX0SjYYn169mMO3uLoRJOnlYKggCG5nojWOeOYSFxRnckNm', 'KARYAWAN', 'KY002'),
('widya', '$2a$12$a2hZcCF4Of0r65cL1ef4/.GVnaCWf6/S0Z//MGtJLnSrpZaiJ7saK', 'KARYAWAN', 'KY003'),
('yoga', '$2a$12$RAQpKW3nqB41R1F3rnXO7ujyHudLVQ4N/DxqmjYQPLqLLCTb0ylvW', 'KARYAWAN', 'KY004');

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
  ADD CONSTRAINT `detail_tr_beli_ibfk_2` FOREIGN KEY (`id_bahan`) REFERENCES `bahan` (`id_bahan`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `detail_tr_jual`
--
ALTER TABLE `detail_tr_jual`
  ADD CONSTRAINT `detail_tr_jual_ibfk_1` FOREIGN KEY (`id_tr_jual`) REFERENCES `transaksi_jual` (`id_tr_jual`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_tr_jual_ibfk_2` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id_menu`) ON DELETE SET NULL ON UPDATE CASCADE;

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
  ADD CONSTRAINT `transaksi_beli_ibfk_1` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `transaksi_beli_ibfk_2` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`) ON DELETE SET NULL ON UPDATE CASCADE;

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
