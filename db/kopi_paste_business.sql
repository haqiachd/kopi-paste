-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 30 Jan 2023 pada 17.20
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
-- Database: `kopi_paste_business`
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
('BA005', 'Biji Kopi', 'Coffee', 'kg'),
('BA008', 'Kopi Hitam', 'Coffee', 'kg'),
('BA011', 'Kacang', 'Nabati', 'kg'),
('BA015', 'Apel', 'Nabati', 'kg'),
('BA018', 'Kecap', 'Perasa', 'bt'),
('BA019', 'Cabe Rawit', 'Nabati', 'kg'),
('BA020', 'Bawang Merah', 'Nabati', 'kg'),
('BA021', 'Bawang Putih', 'Nabati', 'kg'),
('BA022', 'Daging Ayam', 'Hewani', 'kg'),
('BA025', 'Air Mineral', 'Cairan', 'gl'),
('BA026', 'Gula Pasir', 'Perasa', 'kg'),
('BA027', 'Anggur', 'Nabati', 'kg'),
('BA028', 'Nasi', 'Nabati', 'kg'),
('BA029', 'Jamur', 'Nabati', 'kg'),
('BA030', 'Garam', 'Perasa', 'kg'),
('BA031', 'Indomie Goreng', 'Bahan Jadi', 'ds'),
('BA032', 'Kapal Api', 'Coffee', 'rc'),
('BA033', 'Telur Ayam', 'Nabati', 'ls'),
('BA034', 'kg', 'Hewani', 'kg'),
('BA037', 'renceng', 'Coffee', 'rc'),
('BA038', 'lusin', 'Perasa', 'ls');

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

--
-- Dumping data untuk tabel `detail_tr_beli`
--

INSERT INTO `detail_tr_beli` (`id_tr_beli`, `id_bahan`, `nama_bahan`, `jenis_bahan`, `jumlah`, `satuan_bahan`, `total_harga`) VALUES
('TRB00001', 'BA003', 'Jeruk', 'Nabati', 1, 'kg', 25000),
('TRB00002', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 50000),
('TRB00003', 'BA003', 'Jeruk', 'Nabati', 2, 'kg', 50000),
('TRB00004', 'BA025', 'Air Mineral', 'Cairan', 5, 'gl', 15000),
('TRB00005', 'BA011', 'Kacang', 'Nabati', 2, 'kg', 10000),
('TRB00006', 'BA029', 'Jamur', 'Nabati', 1, 'kg', 15000),
('TRB00007', 'BA025', 'Air Mineral', 'Cairan', 5, 'gl', 15000),
('TRB00007', 'BA022', 'Daging Ayam', 'Hewani', 2, 'kg', 40000),
('TRB00007', 'BA027', 'Anggur', 'Nabati', 2, 'kg', 40000),
('TRB00008', 'BA026', 'Gula Pasir', 'Perasa', 4, 'kg', 56000),
('TRB00009', 'BA005', 'Biji Kopi', 'Coffee', 1, 'kg', 120000),
('TRB00010', 'BA005', 'Biji Kopi', 'Coffee', 2, 'kg', 140000),
('TRB00010', 'BA029', 'Jamur', 'Nabati', 1, 'kg', 15000),
('TRB00011', 'BA003', 'Jeruk', 'Nabati', 3, 'kg', 75000),
('TRB00012', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 50000),
('TRB00013', 'BA002', 'Kentang', 'Nabati', 3, 'kg', 150000),
('TRB00014', 'BA028', 'Nasi', 'Nabati', 2, 'kg', 16000),
('TRB00015', 'BA030', 'Garam', 'Perasa', 5, 'kg', 30000),
('TRB00016', 'BA003', 'Jeruk', 'Nabati', 2, 'kg', 50000),
('TRB00017', 'BA028', 'Nasi', 'Nabati', 2, 'kg', 16000),
('TRB00017', 'BA021', 'Bawang Putih', 'Nabati', 1, 'kg', 10000),
('TRB00018', 'BA022', 'Daging Ayam', 'Hewani', 2, 'kg', 40000),
('TRB00019', 'BA003', 'Jeruk', 'Nabati', 5, 'kg', 125000),
('TRB00020', 'BA026', 'Gula Pasir', 'Perasa', 5, 'kg', 70000),
('TRB00021', 'BA025', 'Air Mineral', 'Cairan', 12, 'gl', 36000),
('TRB00022', 'BA028', 'Nasi', 'Nabati', 8, 'kg', 64000),
('TRB00023', 'BA029', 'Jamur', 'Nabati', 2, 'kg', 30000),
('TRB00024', 'BA021', 'Bawang Putih', 'Nabati', 5, 'kg', 50000),
('TRB00025', 'BA020', 'Bawang Merah', 'Nabati', 4, 'kg', 100000),
('TRB00025', 'BA022', 'Daging Ayam', 'Hewani', 5, 'kg', 100000),
('TRB00026', 'BA019', 'Cabe Rawit', 'Nabati', 2, 'kg', 10000),
('TRB00026', 'BA025', 'Air Mineral', 'Cairan', 10, 'gl', 30000),
('TRB00026', 'BA018', 'Kecap', 'Perasa', 1, 'lt', 1500),
('TRB00027', 'BA011', 'Kacang', 'Nabati', 5, 'kg', 25000),
('TRB00028', 'BA005', 'Biji Kopi', 'Coffee', 2, 'kg', 140000),
('TRB00028', 'BA025', 'Air Mineral', 'Cairan', 4, 'gl', 12000),
('TRB00028', 'BA001', 'Susu Coklat', 'Cairan', 2, 'lt', 50000),
('TRB00029', 'BA002', 'Kentang', 'Nabati', 5, 'kg', 250000),
('TRB00030', 'BA018', 'Kecap', 'Perasa', 3, 'lt', 4500),
('TRB00031', 'BA005', 'Biji Kopi', 'Coffee', 2, 'kg', 140000),
('TRB00032', 'BA002', 'Kentang', 'Nabati', 3, 'kg', 150000),
('TRB00033', 'BA025', 'Air Mineral', 'Cairan', 10, 'gl', 30000),
('TRB00034', 'BA002', 'Kentang', 'Nabati', 5, 'kg', 250000),
('TRB00035', 'BA005', 'Biji Kopi', 'Coffee', 2, 'kg', 140000),
('TRB00036', 'BA018', 'Kecap', 'Perasa', 5, 'lt', 7500),
('TRB00037', 'BA005', 'Biji Kopi', 'Coffee', 3, 'kg', 210000),
('TRB00038', 'BA026', 'Gula Pasir', 'Perasa', 4, 'kg', 56000),
('TRB00039', 'BA025', 'Air Mineral', 'Cairan', 5, 'gl', 15000),
('TRB00040', 'BA001', 'Susu Coklat', 'Cairan', 3, 'lt', 75000),
('TRB00041', 'BA002', 'Kentang', 'Nabati', 3, 'kg', 150000),
('TRB00042', 'BA015', 'Apel', 'Nabati', 8, 'kg', 40000),
('TRB00042', 'BA011', 'Kacang', 'Nabati', 2, 'kg', 10000),
('TRB00043', 'BA005', 'Biji Kopi', 'Coffee', 1, 'kg', 70000),
('TRB00044', 'BA025', 'Air Mineral', 'Cairan', 5, 'gl', 15000),
('TRB00045', 'BA022', 'Daging Ayam', 'Hewani', 1, 'kg', 20000),
('TRB00046', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 10000),
('TRB00046', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 8000),
('TRB00047', 'BA011', 'Kacang', 'Nabati', 1, 'kg', 12000),
('TRB00048', 'BA003', 'Jeruk', 'Nabati', 2, 'kg', 20000),
('TRB00049', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 10000),
('TRB00050', 'BA018', 'Kecap', 'Perasa', 2, 'lt', 30000),
('TRB00051', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 50000),
('TRB00052', 'BA008', 'Kopi Hitam', 'Coffee', 1, 'kg', 10000),
('TRB00053', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 10000),
('TRB00054', 'BA032', 'Kapal Api', 'Coffee', 3, 'rc', 30000),
('TRB00054', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 50000),
('TRB00056', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 40000),
('TRB00056', 'BA032', 'Kapal Api', 'Coffee', 1, 'rc', 25000),
('TRB00057', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 15000),
('TRB00058', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 1),
('TRB00058', 'BA032', 'Kapal Api', 'Coffee', 1, 'rc', 1),
('TRB00059', 'BA008', 'Kopi Hitam', 'Coffee', 3, 'kg', 30000),
('TRB00061', 'BA032', 'Kapal Api', 'Coffee', 1, 'rc', 10000),
('TRB00063', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 15000),
('TRB00064', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 45000),
('TRB00064', 'BA032', 'Kapal Api', 'Coffee', 1, 'rc', 12000),
('TRB00064', 'BA033', 'Telur Ayam', 'Nabati', 1, 'ls', 16000),
('TRB00065', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 50000),
('TRB00065', 'BA032', 'Kapal Api', 'Coffee', 2, 'rc', 26000),
('TRB00065', 'BA033', 'Telur Ayam', 'Nabati', 2, 'ls', 24000),
('TRB00066', 'BA032', 'Kapal Api', 'Coffee', 1, 'rc', 3000),
('TRB00066', 'BA033', 'Telur Ayam', 'Nabati', 2, 'ls', 40000),
('TRB00066', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 25000),
('TRB00067', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 12000),
('TRB00068', 'BA033', 'Telur Ayam', 'Nabati', 1, 'ls', 16000),
('TRB00068', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 1),
('TRB00069', 'BA028', 'Nasi', 'Nabati', 1, 'kg', 1000),
('TRB00070', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 8000),
('TRB00073', 'BA018', 'Kecap', 'Perasa', 1, 'bt', 12000),
('TRB00074', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 12000),
('TRB00075', 'BA001', 'Susu Coklat', 'Cairan', 5, 'lt', 50000),
('TRB00076', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 15000),
('TRB00077', 'BA002', 'Kentang', 'Nabati', 2, 'kg', 32000),
('TRB00078', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 12000),
('TRB00079', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 8000),
('TRB00080', 'BA011', 'Kacang', 'Nabati', 1, 'kg', 15000),
('TRB00080', 'BA033', 'Coklat', 'Nabati', 0.5, 'rc', 20000),
('TRB00081', 'BA018', 'Kecap', 'Perasa', 1, 'bt', 10000),
('TRB00082', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 5000),
('TRB00082', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 8000),
('TRB00082', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 40000),
('TRB00082', 'BA033', 'Telur Ayam', 'Nabati', 1, 'ls', 14000),
('TRB00083', 'BA032', 'Kapal Api', 'Coffee', 1, 'rc', 14000),
('TRB00084', 'BA005', 'Biji Kopi', 'Coffee', 1, 'kg', 16000),
('TRB00087', 'BA002', 'Kentang', 'Nabati', 2, 'kg', 15000),
('TRB00088', 'BA008', 'Kopi Hitam', 'Coffee', 2.5, 'kg', 50000),
('TRB00089', 'BA001', 'Susu Coklat', 'Cairan', 1.5, 'lt', 15000),
('TRB00089', 'BA003', 'Jeruk', 'Nabati', 1.25, 'kg', 15000),
('TRB00090', 'BA001', 'Susu Coklat', 'Cairan', 3, 'lt', 150000),
('TRB00090', 'BA029', 'Jamur', 'Nabati', 1, 'kg', 50000),
('TRB00090', 'BA033', 'Telur Ayam', 'Nabati', 1.5, 'ls', 50000),
('TRB00090', 'BA018', 'Kecap', 'Perasa', 1, 'bt', 50000),
('TRB00091', 'BA008', 'Kopi Hitam', 'Coffee', 1, 'kg', 15000),
('TRB00091', 'BA005', 'Biji Kopi', 'Coffee', 2, 'kg', 80000),
('TRB00091', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 40000),
('TRB00091', 'BA032', 'Kapal Api', 'Coffee', 1.5, 'rc', 12000),
('TRB00092', 'BA001', 'Susu Coklat', 'Cairan', 2, 'lt', 12000),
('TRB00092', 'BA001', 'Susu Coklat', 'Cairan', 2, 'lt', 10000),
('TRB00093', 'BA005', 'Biji Kopi', 'Coffee', 2, 'kg', 2000),
('TRB00094', 'BA001', 'Susu Coklat', 'Cairan', 4, 'lt', 10000),
('TRB00094', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 15000),
('TRB00094', 'BA011', 'Kacang', 'Nabati', 1.5, 'kg', 14000),
('TRB00094', 'BA015', 'Apel', 'Nabati', 1.1, 'kg', 11000),
('TRB00094', 'BA022', 'Daging Ayam', 'Hewani', 2, 'kg', 9000),
('TRB00095', 'BA018', 'Kecap', 'Perasa', 3, 'bt', 15000),
('TRB00095', 'BA019', 'Cabe Rawit', 'Nabati', 1, 'kg', 18000),
('TRB00097', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 12000),
('TRB00099', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 10000),
('TRB00100', 'BA027', 'Anggur', 'Nabati', 1, 'kg', 6000),
('TRB00101', 'BA026', 'Gula Pasir', 'Perasa', 2, 'kg', 6000),
('TRB00102', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 12000),
('TRB00103', 'BA005', 'Biji Kopi', 'Coffee', 2, 'kg', 12000),
('TRB00104', 'BA002', 'Kentang', 'Nabati', 2.5, 'kg', 15000),
('TRB00105', 'BA022', 'Daging Ayam', 'Hewani', 1, 'kg', 14000),
('TRB00106', 'BA011', 'Kacang', 'Nabati', 3, 'kg', 12000),
('TRB00106', 'BA001', 'Susu Coklat', 'Cairan', 2.001, 'lt', 8000),
('TRB00106', 'BA018', 'Kecap', 'Perasa', 1, 'bt', 9500),
('TRB00106', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 13000),
('TRB00106', 'BA033', 'Telur Ayam', 'Nabati', 1, 'ls', 8500),
('TRB00106', 'BA032', 'Kapal Api', 'Coffee', 1, 'rc', 15000),
('TRB00106', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 9000),
('TRB00107', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 12000),
('TRB00107', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 8000),
('TRB00108', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 12000),
('TRB00109', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 12000),
('TRB00110', 'BA001', 'Susu Coklat', 'Cairan', 1.5, 'lt', 15000),
('TRB00111', 'BA025', 'Air Mineral', 'Cairan', 1, 'gl', 14000),
('TRB00112', 'BA025', 'Air Mineral', 'Cairan', 1.5, 'gl', 15000),
('TRB00113', 'BA001', 'Susu Coklat', 'Cairan', 1.25, 'lt', 12000),
('TRB00113', 'BA002', 'Kentang', 'Nabati', 2.75, 'kg', 20000),
('TRB00114', 'BA029', 'Jamur', 'Nabati', 2.75, 'kg', 15000),
('TRB00115', 'BA020', 'Bawang Merah', 'Nabati', 1.2, 'kg', 15000),
('TRB00116', 'BA003', 'Jeruk', 'Nabati', 0.1, 'kg', 500),
('TRB00117', 'BA005', 'Biji Kopi', 'Coffee', 1.444, 'kg', 13000),
('TRB00118', 'BA001', 'Susu Coklat', 'Cairan', 0.000001, 'lt', 12000),
('TRB00119', 'BA002', 'Kentang', 'Nabati', 0.11333, 'kg', 8000),
('TRB00120', 'BA008', 'Kopi Hitam', 'Coffee', 1.2, 'kg', 8000),
('TRB00121', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 12000),
('TRB00122', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 10000),
('TRB00123', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 30000),
('TRB00123', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 12000),
('TRB00124', 'BA026', 'Gula Pasir', 'Perasa', 1, 'kg', 15000),
('TRB00124', 'BA033', 'Telur Ayam', 'Nabati', 1, 'ls', 10000),
('TRB00124', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 25000),
('TRB00124', 'BA011', 'Kacang', 'Nabati', 1, 'kg', 10000),
('TRB00124', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 40000),
('TRB00126', 'BA003', 'Jeruk', 'Nabati', 1, 'kg', 12000),
('TRB00126', 'BA026', 'Gula Pasir', 'Perasa', 1, 'kg', 20000),
('TRB00126', 'BA018', 'Kecap', 'Perasa', 1, 'bt', 8000),
('TRB00127', 'BA030', 'Garam', 'Perasa', 1, 'kg', 10000),
('TRB00128', 'BA002', 'Kentang', 'Nabati', 1.3, 'kg', 12000),
('TRB00128', 'BA029', 'Jamur', 'Nabati', 1.1, 'kg', 18000),
('TRB00128', 'BA027', 'Anggur', 'Nabati', 0.5, 'kg', 10000),
('TRB00128', 'BA018', 'Kecap', 'Perasa', 1, 'bt', 10000),
('TRB00131', 'BA005', 'Biji Kopi', 'Coffee', 1, 'kg', 15000),
('TRB00132', 'BA011', 'Kacang', 'Nabati', 1, 'kg', 12000),
('TRB00133', 'BA022', 'Daging Ayam', 'Hewani', 2.5, 'kg', 26000),
('TRB00134', 'BA003', 'Jeruk', 'Nabati', 1.2, 'kg', 15000),
('TRB00135', 'BA019', 'Cabe Rawit', 'Nabati', 1, 'kg', 50000),
('TRB00136', 'BA003', 'Jeruk', 'Nabati', 1, 'kg', 10000);

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

--
-- Dumping data untuk tabel `detail_tr_jual`
--

INSERT INTO `detail_tr_jual` (`id_tr_jual`, `id_menu`, `nama_menu`, `jenis_menu`, `harga_menu`, `jumlah`, `total_harga`) VALUES
('TRJ00001', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00001', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ00002', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00003', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ00004', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00005', 'MN001', 'Orange Juice', 'Minuman', 10000, 2, 20000),
('TRJ00005', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 4, 60000),
('TRJ00005', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 3, 36000),
('TRJ00006', 'MN023', 'Kopi Susu Dingin', 'Minuman', 3000, 1, 3000),
('TRJ00007', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 6, 90000),
('TRJ00008', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 2, 24000),
('TRJ00009', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ00010', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 2, 24000),
('TRJ00010', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00010', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 3, 36000),
('TRJ00011', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 3, 45000),
('TRJ00011', 'MN001', 'Orange Juice', 'Minuman', 10000, 2, 20000),
('TRJ00011', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00012', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00013', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00013', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00014', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ00014', 'MN015', 'Jus Anggur', 'Minuman', 12000, 1, 12000),
('TRJ00014', 'MN023', 'Kopi Susu Dingin', 'Minuman', 3000, 2, 6000),
('TRJ00015', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 5, 75000),
('TRJ00015', 'MN019', 'Jamur Crispy', 'Snack', 10000, 2, 20000),
('TRJ00015', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ00016', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ00016', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00016', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ00016', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00016', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00016', 'MN020', 'Jus Apel', 'Minuman', 11000, 1, 11000),
('TRJ00017', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 4, 60000),
('TRJ00017', 'MN001', 'Orange Juice', 'Minuman', 10000, 3, 30000),
('TRJ00017', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00018', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 4, 60000),
('TRJ00018', 'MN018', 'Keripik Kentang', 'Snack', 8000, 3, 24000),
('TRJ00018', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00019', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 3, 45000),
('TRJ00019', 'MN023', 'Kopi Susu Dingin', 'Minuman', 3000, 2, 6000),
('TRJ00019', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00019', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 2, 24000),
('TRJ00019', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 5, 50000),
('TRJ00020', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 3, 45000),
('TRJ00020', 'MN022', 'Cappucino', 'Original Coffee', 10000, 3, 30000),
('TRJ00020', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ00021', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ00021', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 1, 12000),
('TRJ00022', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ00022', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ00023', 'MN001', 'Orange Juice', 'Minuman', 10000, 2, 20000),
('TRJ00023', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ00024', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 3, 36000),
('TRJ00024', 'MN023', 'Kopi Susu Dingin', 'Minuman', 3000, 1, 3000),
('TRJ00024', 'MN019', 'Jamur Crispy', 'Snack', 10000, 8, 80000),
('TRJ00025', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 7, 105000),
('TRJ00025', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 5, 75000),
('TRJ00025', 'MN018', 'Keripik Kentang', 'Snack', 8000, 1, 8000),
('TRJ00025', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ00026', 'MN015', 'Jus Anggur', 'Minuman', 12000, 2, 24000),
('TRJ00026', 'MN001', 'Orange Juice', 'Minuman', 10000, 7, 70000),
('TRJ00026', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ00026', 'MN023', 'Kopi Susu Dingin', 'Minuman', 3000, 4, 12000),
('TRJ00027', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 9, 90000),
('TRJ00027', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ00027', 'MN018', 'Keripik Kentang', 'Snack', 8000, 1, 8000),
('TRJ00027', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ00027', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 8, 120000),
('TRJ00028', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ00028', 'MN015', 'Jus Anggur', 'Minuman', 12000, 1, 12000),
('TRJ00028', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ00029', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 7, 105000),
('TRJ00029', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 4, 60000),
('TRJ00029', 'MN018', 'Keripik Kentang', 'Snack', 8000, 7, 56000),
('TRJ00030', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ00030', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00030', 'MN018', 'Keripik Kentang', 'Snack', 8000, 3, 24000),
('TRJ00031', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 4, 60000),
('TRJ00032', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 9, 108000),
('TRJ00033', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ00034', 'MN018', 'Keripik Kentang', 'Snack', 8000, 3, 24000),
('TRJ00034', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ00035', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 10, 120000),
('TRJ00036', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ00036', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00036', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00036', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ00036', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ00037', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ00037', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00037', 'MN019', 'Jamur Crispy', 'Snack', 10000, 2, 20000),
('TRJ00037', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00038', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ00038', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ00038', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00039', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00040', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ00040', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ00041', 'MN023', 'Kopi Susu Dingin', 'Minuman', 3000, 1, 3000),
('TRJ00041', 'MN020', 'Jus Apel', 'Minuman', 11000, 2, 22000),
('TRJ00042', 'MN015', 'Jus Anggur', 'Minuman', 12000, 2, 24000),
('TRJ00043', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ00043', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ00044', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ00045', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ00046', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00047', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 4, 60000),
('TRJ00048', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ00048', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ00048', 'MN001', 'Orange Juice', 'Minuman', 10000, 3, 30000),
('TRJ00049', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00049', 'MN015', 'Jus Anggur', 'Minuman', 12000, 3, 36000),
('TRJ00049', 'MN022', 'Cappucino', 'Original Coffee', 10000, 1, 10000),
('TRJ00049', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00049', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ00050', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ00050', 'MN020', 'Jus Apel', 'Minuman', 11000, 1, 11000),
('TRJ00051', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00052', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 4, 60000),
('TRJ00052', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ00052', 'MN018', 'Keripik Kentang', 'Snack', 8000, 3, 24000),
('TRJ00053', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 4, 48000),
('TRJ00053', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ00053', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 8, 120000),
('TRJ00054', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 4, 60000),
('TRJ00054', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 10, 100000),
('TRJ00055', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 4, 60000),
('TRJ00055', 'MN013', 'Kentang Goreng', 'Snack', 12000, 9, 108000),
('TRJ00056', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 20, 240000),
('TRJ00056', 'MN013', 'Kentang Goreng', 'Snack', 12000, 7, 84000),
('TRJ00057', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 4, 48000),
('TRJ00058', 'MN013', 'Kentang Goreng', 'Snack', 12000, 5, 60000),
('TRJ00059', 'MN022', 'Cappucino', 'Original Coffee', 10000, 5, 50000),
('TRJ00060', 'MN022', 'Cappucino', 'Original Coffee', 10000, 5, 50000),
('TRJ00060', 'MN015', 'Jus Anggur', 'Minuman', 12000, 3, 36000),
('TRJ00061', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 3, 45000),
('TRJ00062', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ00063', 'MN019', 'Jamur Crispy', 'Snack', 10000, 2, 20000),
('TRJ00063', 'MN023', 'Kopi Susu Dingin', 'Minuman', 3000, 5, 15000),
('TRJ00063', 'MN001', 'Orange Juice', 'Minuman', 10000, 2, 20000),
('TRJ00064', 'MN023', 'Kopi Susu Dingin', 'Minuman', 3000, 2, 6000),
('TRJ00065', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 4, 48000),
('TRJ00065', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ00066', 'MN020', 'Jus Apel', 'Minuman', 11000, 3, 33000),
('TRJ00067', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 5, 60000),
('TRJ00068', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ00068', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00068', 'MN019', 'Jamur Crispy', 'Snack', 10000, 6, 60000),
('TRJ00069', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ00070', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 5, 60000),
('TRJ00071', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ00072', 'MN018', 'Keripik Kentang', 'Snack', 8000, 6, 48000),
('TRJ00073', 'MN013', 'Kentang Goreng', 'Snack', 12000, 4, 48000),
('TRJ00073', 'MN015', 'Jus Anggur', 'Minuman', 12000, 4, 48000),
('TRJ00074', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ00075', 'MN013', 'Kentang Goreng', 'Snack', 12000, 4, 48000),
('TRJ00076', 'MN019', 'Jamur Crispy', 'Snack', 10000, 5, 50000),
('TRJ00077', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ00078', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 8, 120000),
('TRJ00079', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ00080', 'MN022', 'Cappucino', 'Original Coffee', 10000, 4, 40000),
('TRJ00081', 'MN022', 'Cappucino', 'Original Coffee', 10000, 3, 30000),
('TRJ00081', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00082', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ00083', 'MN019', 'Jamur Crispy', 'Snack', 10000, 4, 40000),
('TRJ00084', 'MN013', 'Kentang Goreng', 'Snack', 12000, 5, 60000),
('TRJ00085', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00086', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00086', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00087', 'MN019', 'Jamur Crispy', 'Snack', 10000, 2, 20000),
('TRJ00088', 'MN018', 'Keripik Kentang', 'Snack', 8000, 3, 24000),
('TRJ00089', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 4, 48000),
('TRJ00090', 'MN022', 'Cappucino', 'Original Coffee', 10000, 4, 40000),
('TRJ00091', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 1, 12000),
('TRJ00091', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00092', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ00093', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00093', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00094', 'MN022', 'Cappucino', 'Original Coffee', 10000, 1, 10000),
('TRJ00095', 'MN001', 'Orange Juice', 'Minuman', 10000, 2, 20000),
('TRJ00096', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00097', 'MN015', 'Jus Anggur', 'Minuman', 12000, 2, 24000),
('TRJ00098', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00099', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00100', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ00101', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ00102', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00103', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ00104', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00105', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00106', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00107', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ00107', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ00108', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ00108', 'MN020', 'Jus Apel', 'Minuman', 11000, 9, 99000),
('TRJ00108', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00108', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 1, 12000),
('TRJ00109', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 8, 80000),
('TRJ00110', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00111', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00111', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ00112', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ00113', 'MN023', 'Kopi Susu Dingin', 'Minuman', 3000, 1, 3000),
('TRJ00114', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00115', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00116', 'MN001', 'Orange Juice', 'Minuman', 10000, 9, 90000),
('TRJ00117', 'MN015', 'Jus Anggur', 'Minuman', 12000, 1, 12000),
('TRJ00118', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00119', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00119', 'MN020', 'Jus Apel', 'Minuman', 11000, 2, 22000),
('TRJ00119', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ00119', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ00119', 'MN019', 'Jamur Crispy', 'Snack', 10000, 3, 30000),
('TRJ00119', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ00119', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00120', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ00120', 'MN013', 'Kentang Goreng', 'Snack', 12000, 4, 48000),
('TRJ00120', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ00120', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ00121', 'MN022', 'Cappucino', 'Original Coffee', 10000, 5, 50000),
('TRJ00122', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 10, 150000),
('TRJ00123', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ00123', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00124', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ00125', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00125', 'MN020', 'Jus Apel', 'Minuman', 11000, 1, 11000),
('TRJ00125', 'MN022', 'Cappucino', 'Original Coffee', 10000, 1, 10000),
('TRJ00126', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00127', 'MN001', 'Orange Juice', 'Minuman', 10000, 3, 30000),
('TRJ00127', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00131', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 3, 45000),
('TRJ00131', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ00132', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 5, 75000),
('TRJ00132', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00132', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ00132', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00132', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 2, 24000),
('TRJ00133', 'MN018', 'Keripik Kentang', 'Snack', 8000, 4, 32000),
('TRJ00134', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00135', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00136', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ00137', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00138', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00139', 'MN022', 'Cappucino', 'Original Coffee', 10000, 1, 10000),
('TRJ00140', 'MN022', 'Cappucino', 'Original Coffee', 10000, 1, 10000),
('TRJ00141', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ00142', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ00143', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 2, 24000),
('TRJ00144', 'MN013', 'Kentang Goreng', 'Snack', 12000, 4, 48000),
('TRJ00145', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ00145', 'MN001', 'Orange Juice', 'Minuman', 10000, 4, 40000),
('TRJ00145', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ00146', 'MN023', 'Kopi Susu Dingin', 'Minuman', 3000, 2, 6000),
('TRJ00146', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 3, 36000),
('TRJ00146', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00147', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 4, 48000),
('TRJ00147', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ00148', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 3, 36000),
('TRJ00148', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ00149', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00151', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00151', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ00155', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00150', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 1, 12000),
('TRJ00150', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ00150', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ00150', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ00150', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00150', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ00157', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ00157', 'MN022', 'Cappucino', 'Original Coffee', 10000, 1, 10000),
('TRJ00156', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00156', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ00159', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00160', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00161', 'MN001', 'Orange Juice', 'Minuman', 10000, 4, 40000),
('TRJ00162', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00163', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00164', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00165', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ00166', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00170', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00171', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00169', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ00172', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00172', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00172', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ00172', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ00167', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ00167', 'MN001', 'Orange Juice', 'Minuman', 10000, 2, 20000),
('TRJ00167', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ00154', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00154', 'MN019', 'Jamur Crispy', 'Snack', 10000, 5, 50000),
('TRJ00154', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00154', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ00168', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00174', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00175', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00175', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ00176', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00177', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ00177', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ00178', 'MN019', 'Jamur Crispy', 'Snack', 10000, 2, 20000),
('TRJ00178', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00178', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00186', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00187', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00188', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00188', 'MN015', 'Jus Anggur', 'Minuman', 12000, 1, 12000),
('TRJ00189', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ00189', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00190', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ00191', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00192', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00193', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00194', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00195', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ00196', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00197', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00198', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ00199', 'MN013', 'Kentang Goreng', 'Snack', 12000, 4, 48000),
('TRJ00200', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00201', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00202', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00203', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ00203', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00204', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00206', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00207', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00208', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00205', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 3, 45000),
('TRJ00205', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00210', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ00212', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00209', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00214', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00215', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00216', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ00217', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00211', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00218', 'MN015', 'Jus Anggur', 'Minuman', 12000, 1, 12000),
('TRJ00213', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00220', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00219', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00224', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00225', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ00223', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00221', 'MN015', 'Jus Anggur', 'Minuman', 12000, 1, 12000),
('TRJ00226', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 2, 26000),
('TRJ00228', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ00229', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00227', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00222', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00232', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00233', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00234', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00237', 'MN001', 'Orange Juice', 'Minuman', 10000, 2, 20000),
('TRJ00238', 'MN020', 'Jus Apel', 'Minuman', 11000, 1, 11000),
('TRJ00239', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00236', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00230', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00230', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00235', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ00241', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ00242', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ00240', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ00243', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ00244', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ00244', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ00244', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ00244', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 3, 36000),
('TRJ00245', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ00246', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `nama_karyawan`, `no_telp`, `alamat`, `shif`) VALUES
('KY001', 'Achmad Baihaqi', '085655864624', 'Jombang, Jawa Timur, Indonesia', 'Siang (07:00-17:59)'),
('KY002', 'Mohammad Ilham Islamy', '085690123458', 'Nganjuk, Jawa Timur', 'Malam (18:00-22:59)'),
('KY003', 'Widyasari Raisya Salsabilla', '085690239023', 'Mojokerto, Jawa Timur', 'Siang (07:00-17:59)'),
('KY004', 'Septian Yoga Pamungkas', '084590120912', 'Nganjuk, Jawa Timur', 'Malam (18:00-22:59)'),
('KY005', 'Akun Tester', '085655864624', 'Jombang, Jawa Timur', 'Siang (07:00-17:59)');

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
('MN001', 'Orange Juice', 'Minuman', 10000),
('MN002', 'Coffee Latte', 'Original Coffee', 10000),
('MN011', 'Chocholate', 'Falvoured Coffee', 15000),
('MN012', 'Ayam Bakar', 'Makanan', 15000),
('MN013', 'Kentang Goreng', 'Snack', 12000),
('MN014', 'Black Coffee', 'Original Coffee', 13000),
('MN015', 'Jus Anggur', 'Minuman', 12000),
('MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000),
('MN017', 'Nasi Goreng', 'Makanan', 12000),
('MN018', 'Keripik Kentang', 'Snack', 8000),
('MN019', 'Jamur Crispy', 'Snack', 10000),
('MN020', 'Jus Apel', 'Minuman', 11000),
('MN021', 'Moca Latte', 'Falvoured Coffee', 12000),
('MN022', 'Cappucino', 'Original Coffee', 10000),
('MN023', 'Kopi Susu Dingin', 'Minuman', 3000),
('MN024', 'Air Mineral', 'Minuman', 4000),
('MN025', 'Kopi Hitam', 'Minuman', 3000),
('MN026', 'Kopi Susu Tubruk', 'Minuman', 5000),
('MN027', 'Teh Panas / Es', 'Minuman', 3000),
('MN028', 'Susu Panas / Es', 'Minuman', 5000),
('MN029', 'Es Lemon Tea Panas / Es', 'Minuman', 5000),
('MN030', 'Wedhang Uwuh', 'Minuman', 5000),
('MN031', 'Es Jeruk', 'Minuman', 5000),
('MN032', 'Teh Tarik', 'Minuman', 7000),
('MN033', 'Jahe Susu Panas', 'Minuman', 7000),
('MN034', 'Joss Susu Es', 'Minuman', 6000),
('MN035', 'KukuBima Susu Es', 'Minuman', 6000),
('MN036', 'Jahe Panas', 'Minuman', 5000),
('MN037', 'Vietnam Drip', 'Original Coffee', 10000),
('MN038', 'Espresso', 'Original Coffee', 8000),
('MN039', 'Long Black', 'Original Coffee', 10000),
('MN040', 'Coffee Latte', 'Original Coffee', 10000),
('MN041', 'Cappucino', 'Original Coffee', 10000),
('MN042', 'Moca Latte', 'Falvoured Coffee', 12000),
('MN043', 'Vanilla Latte', 'Falvoured Coffee', 12000),
('MN044', 'Chocholate', 'Falvoured Coffee', 10000),
('MN045', 'Salted Caramel', 'Falvoured Coffee', 10000),
('MN046', 'Taro', 'Falvoured Coffee', 10000),
('MN047', 'Green Tea Latte', 'Falvoured Coffee', 12000),
('MN048', 'Kentang Goreng', 'Snack', 8000),
('MN049', 'Cireng', 'Snack', 8000),
('MN050', 'Tahu Tuna', 'Snack', 8000),
('MN051', 'Shio May Ayam', 'Snack', 10000),
('MN052', 'Pisang Coklat', 'Snack', 10000),
('MN053', 'Pisang Nugget', 'Snack', 8000),
('MN054', 'Tempe Medoan', 'Snack', 8000),
('MN055', 'Jamur Crispy', 'Snack', 10000),
('MN056', 'Nasi Goreng', 'Makanan', 12000),
('MN057', 'Nasi Ayam Penyet', 'Makanan', 12000),
('MN058', 'Nasi Ayam Geprek', 'Makanan', 13000),
('MN059', 'Ayam Taliwang', 'Makanan', 13000),
('MN060', 'Nasi Sop', 'Makanan', 8000),
('MN061', 'Nasi Sayur Asem', 'Makanan', 8000),
('MN062', 'Nasi Tempe / Tahu Penyet', 'Makanan', 8000),
('MN063', 'Nasi Telur Penyet', 'Makanan', 10000),
('MN064', 'Indomie Goreng Telur', 'Makanan', 8000),
('MN065', 'Indomie Rebus Telur', 'Makanan', 8000),
('MN066', 'Nasi Putih', 'Makanan', 4000),
('MN067', 'Telur Ceplok / Dadar', 'Makanan', 4000);

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

--
-- Dumping data untuk tabel `transaksi_beli`
--

INSERT INTO `transaksi_beli` (`id_tr_beli`, `id_karyawan`, `nama_karyawan`, `total_bahan`, `total_harga`, `tanggal`) VALUES
('TRB00001', 'KY001', 'Achmad Baihaqi', 1, 25000, '2022-10-29 23:30:42'),
('TRB00002', 'KY001', 'Achmad Baihaqi', 1, 50000, '2022-10-30 23:32:44'),
('TRB00003', 'KY001', 'Achmad Baihaqi', 2, 50000, '2022-10-30 23:33:00'),
('TRB00004', 'KY001', 'Achmad Baihaqi', 5, 15000, '2022-10-31 23:38:03'),
('TRB00005', 'KY001', 'Achmad Baihaqi', 2, 10000, '2022-10-09 12:41:03'),
('TRB00006', 'KY001', 'Achmad Baihaqi', 1, 15000, '2022-10-09 12:42:02'),
('TRB00007', 'KY001', 'Achmad Baihaqi', 9, 95000, '2022-11-05 12:48:33'),
('TRB00008', 'KY001', 'Achmad Baihaqi', 4, 56000, '2022-11-05 12:50:14'),
('TRB00009', 'KY001', 'Achmad Baihaqi', 1, 120000, '2022-11-05 12:50:25'),
('TRB00010', 'KY001', 'Achmad Baihaqi', 3, 155000, '2022-11-05 12:52:13'),
('TRB00011', 'KY001', 'Achmad Baihaqi', 3, 75000, '2022-11-05 12:52:24'),
('TRB00012', 'KY001', 'Achmad Baihaqi', 1, 50000, '2022-11-05 12:52:43'),
('TRB00013', 'KY001', 'Achmad Baihaqi', 3, 150000, '2022-11-13 04:26:14'),
('TRB00014', 'KY001', 'Achmad Baihaqi', 2, 16000, '2022-11-13 04:26:34'),
('TRB00015', 'KY001', 'Achmad Baihaqi', 5, 30000, '2022-11-13 04:26:46'),
('TRB00016', 'KY001', 'Achmad Baihaqi', 2, 50000, '2022-11-13 04:27:06'),
('TRB00017', 'KY001', 'Achmad Baihaqi', 3, 26000, '2022-11-15 05:28:28'),
('TRB00018', 'KY001', 'Achmad Baihaqi', 2, 40000, '2022-11-15 05:29:05'),
('TRB00019', 'KY001', 'Achmad Baihaqi', 5, 125000, '2022-11-17 05:30:02'),
('TRB00020', 'KY001', 'Achmad Baihaqi', 5, 70000, '2022-11-17 05:30:47'),
('TRB00021', 'KY001', 'Achmad Baihaqi', 12, 36000, '2022-11-17 05:31:28'),
('TRB00022', 'KY001', 'Achmad Baihaqi', 8, 64000, '2022-11-21 10:11:11'),
('TRB00023', 'KY001', 'Achmad Baihaqi', 2, 30000, '2022-11-21 10:11:24'),
('TRB00024', 'KY001', 'Achmad Baihaqi', 5, 50000, '2022-11-21 10:11:55'),
('TRB00025', 'KY001', 'Achmad Baihaqi', 9, 200000, '2022-11-21 10:12:21'),
('TRB00026', 'KY001', 'Achmad Baihaqi', 13, 41500, '2022-11-27 03:16:09'),
('TRB00027', 'KY001', 'Achmad Baihaqi', 5, 25000, '2022-12-01 08:17:41'),
('TRB00028', 'KY001', 'Achmad Baihaqi', 8, 202000, '2022-12-01 08:18:14'),
('TRB00029', 'KY001', 'Achmad Baihaqi', 5, 250000, '2022-12-01 08:18:38'),
('TRB00030', 'KY001', 'Achmad Baihaqi', 3, 4500, '2022-12-03 08:19:23'),
('TRB00031', 'KY001', 'Achmad Baihaqi', 2, 140000, '2022-12-03 08:19:35'),
('TRB00032', 'KY001', 'Achmad Baihaqi', 3, 150000, '2022-12-14 03:32:42'),
('TRB00033', 'KY001', 'Achmad Baihaqi', 10, 30000, '2022-12-14 03:32:57'),
('TRB00034', 'KY001', 'Achmad Baihaqi', 5, 250000, '2022-12-14 03:33:24'),
('TRB00035', 'KY001', 'Achmad Baihaqi', 2, 140000, '2022-12-11 03:34:02'),
('TRB00036', 'KY001', 'Achmad Baihaqi', 5, 7500, '2022-12-07 03:34:47'),
('TRB00037', 'KY001', 'Achmad Baihaqi', 3, 210000, '2022-12-07 03:35:30'),
('TRB00038', 'KY004', 'Achmad Baihaqi', 4, 56000, '2022-12-18 12:45:06'),
('TRB00039', 'KY004', 'Achmad Baihaqi', 5, 15000, '2022-12-21 12:45:51'),
('TRB00040', 'KY004', 'Achmad Baihaqi', 3, 75000, '2022-12-21 12:46:15'),
('TRB00041', 'KY001', 'Achmad Baihaqi', 3, 150000, '2022-12-25 12:47:52'),
('TRB00042', 'KY001', 'Achmad Baihaqi', 10, 50000, '2022-12-28 12:49:13'),
('TRB00043', 'KY001', 'Achmad Baihaqi', 1, 70000, '2022-12-31 12:52:23'),
('TRB00044', 'KY004', 'Achmad Baihaqi', 5, 15000, '2023-01-03 06:57:51'),
('TRB00045', 'KY004', 'Achmad Baihaqi', 1, 20000, '2023-01-03 06:58:24'),
('TRB00046', 'KY001', 'Achmad Baihaqi', 2, 18000, '2023-01-05 22:30:01'),
('TRB00047', 'KY001', 'Achmad Baihaqi', 1, 12000, '2023-01-05 22:30:13'),
('TRB00048', 'KY001', 'Achmad Baihaqi', 2, 20000, '2023-01-05 22:31:52'),
('TRB00049', 'KY003', 'Achmad Baihaqi', 1, 10000, '2023-01-05 22:34:27'),
('TRB00050', 'KY004', 'Achmad Baihaqi', 2, 30000, '2023-01-05 22:56:05'),
('TRB00051', 'KY003', 'Achmad Baihaqi', 1, 50000, '2023-01-05 23:01:57'),
('TRB00052', 'KY002', 'Achmad Baihaqi', 1, 10000, '2023-01-05 23:05:35'),
('TRB00053', 'KY003', 'Achmad Baihaqi', 1, 10000, '2023-01-05 23:07:29'),
('TRB00054', 'KY003', 'Achmad Baihaqi', 4, 80000, '2023-01-05 23:07:59'),
('TRB00055', 'KY004', 'Achmad Baihaqi', 1, 15000, '2023-01-05 23:14:57'),
('TRB00056', 'KY004', 'Achmad Baihaqi', 3, 80000, '2023-01-05 23:15:31'),
('TRB00057', 'KY004', 'Achmad Baihaqi', 1, 15000, '2023-01-05 23:15:40'),
('TRB00058', 'KY004', 'Achmad Baihaqi', 3, 3, '2023-01-05 23:16:38'),
('TRB00059', 'KY004', 'Achmad Baihaqi', 4, 46000, '2023-01-05 23:18:05'),
('TRB00060', 'KY003', 'Achmad Baihaqi', 1, 1, '2023-01-05 23:20:01'),
('TRB00061', 'KY002', 'Achmad Baihaqi', 1, 10000, '2023-01-05 23:23:22'),
('TRB00063', 'KY003', 'Achmad Baihaqi', 1, 15000, '2023-01-06 00:21:34'),
('TRB00064', 'KY003', 'Achmad Baihaqi', 3, 73000, '2023-01-06 00:22:27'),
('TRB00065', 'KY003', 'Achmad Baihaqi', 5, 100000, '2023-01-06 00:31:56'),
('TRB00066', 'KY002', 'Achmad Baihaqi', 4, 68000, '2023-01-06 00:32:40'),
('TRB00067', 'KY004', 'Achmad Baihaqi', 1, 12000, '2023-01-06 00:41:18'),
('TRB00068', 'KY002', 'Achmad Baihaqi', 2, 16001, '2023-01-06 00:43:30'),
('TRB00069', 'KY003', 'Achmad Baihaqi', 1, 1000, '2023-01-06 15:34:06'),
('TRB00070', 'KY003', 'Achmad Baihaqi', 1, 8000, '2023-01-06 15:38:50'),
('TRB00071', 'KY004', 'Achmad Baihaqi', 1, 12000, '2023-01-06 20:04:29'),
('TRB00072', 'KY003', 'Achmad Baihaqi', 1, 12000, '2023-01-06 20:05:03'),
('TRB00073', 'KY002', 'Achmad Baihaqi', 1, 12000, '2023-01-06 20:09:29'),
('TRB00074', 'KY003', 'Achmad Baihaqi', 1, 12000, '2023-01-06 20:21:48'),
('TRB00075', 'KY002', 'Achmad Baihaqi', 5, 50000, '2023-01-06 22:10:15'),
('TRB00076', 'KY003', 'Achmad Baihaqi', 1, 15000, '2023-01-06 22:13:15'),
('TRB00077', 'KY001', 'Achmad Baihaqi', 2, 32000, '2023-01-07 13:04:40'),
('TRB00078', 'KY003', 'Achmad Baihaqi', 1, 12000, '2023-01-07 13:05:24'),
('TRB00079', 'KY003', 'Achmad Baihaqi', 1, 8000, '2023-01-07 17:33:13'),
('TRB00080', 'KY004', 'Achmad Baihaqi', 1, 15000, '2023-01-07 17:36:01'),
('TRB00081', 'KY002', 'Achmad Baihaqi', 1, 10000, '2023-01-07 20:16:19'),
('TRB00082', 'KY002', 'Achmad Baihaqi', 4, 67000, '2023-01-07 20:17:01'),
('TRB00083', 'KY002', 'Achmad Baihaqi', 1, 14000, '2023-01-07 20:17:14'),
('TRB00084', 'KY004', 'Achmad Baihaqi', 1, 16000, '2023-01-07 20:19:32'),
('TRB00085', 'KY002', 'Achmad Baihaqi', 5, 15000, '2023-01-07 22:49:40'),
('TRB00086', 'KY002', 'Achmad Baihaqi', 13, 108000, '2023-01-07 22:53:53'),
('TRB00087', 'KY003', 'Achmad Baihaqi', 2, 15000, '2023-01-07 22:54:53'),
('TRB00088', 'KY003', 'Achmad Baihaqi', 2, 50000, '2023-01-07 22:55:39'),
('TRB00089', 'KY004', 'Achmad Baihaqi', 2, 30000, '2023-01-07 22:58:00'),
('TRB00090', 'KY001', 'Achmad Baihaqi', 6, 300000, '2023-01-07 23:44:27'),
('TRB00091', 'KY003', 'Achmad Baihaqi', 5, 147000, '2023-01-07 23:51:58'),
('TRB00092', 'KY002', 'Achmad Baihaqi', 4, 22000, '2023-01-08 00:08:49'),
('TRB00093', 'KY001', 'Achmad Baihaqi', 1, 2000, '2023-01-08 00:12:41'),
('TRB00094', 'KY001', 'Achmad Baihaqi', 7, 59000, '2023-01-08 00:15:07'),
('TRB00095', 'KY003', 'Achmad Baihaqi', 2, 33000, '2023-01-08 00:16:43'),
('TRB00096', 'KY003', 'Achmad Baihaqi', 1, 12000, '2023-01-08 00:20:25'),
('TRB00097', 'KY001', 'Achmad Baihaqi', 1, 12000, '2023-01-08 00:21:54'),
('TRB00098', 'KY001', 'Achmad Baihaqi', 1, 12000, '2023-01-08 00:24:28'),
('TRB00099', 'KY001', 'Achmad Baihaqi', 1, 10000, '2023-01-08 00:25:31'),
('TRB00100', 'KY003', 'Achmad Baihaqi', 1, 6000, '2023-01-08 00:26:19'),
('TRB00101', 'KY003', 'Achmad Baihaqi', 1, 6000, '2023-01-08 00:26:46'),
('TRB00102', 'KY002', 'Achmad Baihaqi', 1, 12000, '2023-01-08 00:29:37'),
('TRB00103', 'KY004', 'Achmad Baihaqi', 1, 12000, '2023-01-08 00:31:37'),
('TRB00104', 'KY002', 'Achmad Baihaqi', 1, 15000, '2023-01-08 13:35:21'),
('TRB00105', 'KY003', 'Achmad Baihaqi', 1, 14000, '2023-01-08 13:52:33'),
('TRB00106', 'KY003', 'Achmad Baihaqi', 7, 75000, '2023-01-08 14:30:26'),
('TRB00107', 'KY002', 'Achmad Baihaqi', 2, 20000, '2023-01-08 14:40:10'),
('TRB00108', 'KY001', 'Achmad Baihaqi', 1, 12000, '2023-01-08 14:41:51'),
('TRB00109', 'KY001', 'Achmad Baihaqi', 1, 12000, '2023-01-08 14:46:32'),
('TRB00110', 'KY004', 'Achmad Baihaqi', 1, 15000, '2023-01-08 14:54:48'),
('TRB00111', 'KY001', 'Achmad Baihaqi', 1, 14000, '2023-01-08 15:01:31'),
('TRB00112', 'KY001', 'Achmad Baihaqi', 1, 15000, '2023-01-08 15:01:50'),
('TRB00113', 'KY001', 'Achmad Baihaqi', 2, 32000, '2023-01-08 15:40:10'),
('TRB00114', 'KY001', 'Achmad Baihaqi', 1, 15000, '2023-01-08 15:41:03'),
('TRB00115', 'KY004', 'Achmad Baihaqi', 1, 15000, '2023-01-08 16:08:41'),
('TRB00116', 'KY003', 'Achmad Baihaqi', 1, 500, '2023-01-08 17:08:20'),
('TRB00117', 'KY002', 'Achmad Baihaqi', 1, 13000, '2023-01-08 17:17:41'),
('TRB00118', 'KY001', 'Achmad Baihaqi', 1, 12000, '2023-01-08 17:45:06'),
('TRB00119', 'KY003', 'Achmad Baihaqi', 1, 8000, '2023-01-08 17:54:31'),
('TRB00120', 'KY001', 'Achmad Baihaqi', 1, 8000, '2023-01-08 19:43:53'),
('TRB00121', 'KY003', 'Achmad Baihaqi', 1, 12000, '2023-01-08 20:03:30'),
('TRB00122', 'KY003', 'Achmad Baihaqi', 1, 10000, '2023-01-08 21:10:37'),
('TRB00123', 'KY001', 'Achmad Baihaqi', 2, 42000, '2023-01-09 14:44:18'),
('TRB00124', 'KY002', 'Achmad Baihaqi', 5, 100000, '2023-01-09 15:17:55'),
('TRB00126', 'KY001', 'Achmad Baihaqi', 3, 40000, '2023-01-09 15:18:31'),
('TRB00127', 'KY003', 'Achmad Baihaqi', 1, 10000, '2023-01-09 15:19:08'),
('TRB00128', 'KY002', 'Achmad Baihaqi', 4, 50000, '2023-01-09 15:28:59'),
('TRB00130', 'KY003', 'Achmad Baihaqi', 1, 16000, '2023-01-09 15:25:57'),
('TRB00131', 'KY003', 'Achmad Baihaqi', 1, 15000, '2023-01-10 11:42:36'),
('TRB00132', 'KY003', 'Achmad Baihaqi', 1, 12000, '2023-01-11 21:46:25'),
('TRB00133', 'KY001', 'Achmad Baihaqi', 1, 26000, '2023-01-11 21:47:39'),
('TRB00134', 'KY004', 'Achmad Baihaqi', 1, 15000, '2023-01-12 15:52:07'),
('TRB00135', 'KY003', 'Achmad Baihaqi', 1, 50000, '2023-01-12 15:52:16'),
('TRB00136', 'KY002', 'Achmad Baihaqi', 1, 10000, '2023-01-17 16:22:10');

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

--
-- Dumping data untuk tabel `transaksi_jual`
--

INSERT INTO `transaksi_jual` (`id_tr_jual`, `id_karyawan`, `nama_karyawan`, `total_menu`, `total_harga`, `total_bayar`, `total_kembalian`, `tanggal`) VALUES
('TRJ00001', 'KY001', 'Achmad Baihaqi', 3, 35000, 35000, 0, '2022-10-28 23:28:00'),
('TRJ00002', 'KY001', 'Achmad Baihaqi', 1, 12000, 12000, 0, '2022-10-28 23:28:29'),
('TRJ00003', 'KY001', 'Achmad Baihaqi', 2, 30000, 30000, 0, '2022-10-29 23:28:57'),
('TRJ00004', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2022-10-29 23:29:21'),
('TRJ00005', 'KY001', 'Achmad Baihaqi', 9, 116000, 116000, 0, '2022-10-30 23:31:51'),
('TRJ00006', 'KY001', 'Achmad Baihaqi', 1, 3000, 3000, 0, '2022-10-30 23:32:21'),
('TRJ00007', 'KY001', 'Achmad Baihaqi', 6, 90000, 90000, 0, '2022-10-30 23:33:23'),
('TRJ00008', 'KY001', 'Achmad Baihaqi', 2, 24000, 24000, 0, '2022-10-30 23:33:55'),
('TRJ00009', 'KY001', 'Achmad Baihaqi', 2, 20000, 20000, 0, '2022-10-30 23:34:14'),
('TRJ00010', 'KY001', 'Achmad Baihaqi', 6, 75000, 75000, 0, '2022-10-31 23:35:45'),
('TRJ00011', 'KY001', 'Achmad Baihaqi', 6, 80000, 80000, 0, '2022-10-31 23:36:41'),
('TRJ00012', 'KY001', 'Achmad Baihaqi', 1, 15000, 15000, 0, '2022-10-07 12:44:16'),
('TRJ00013', 'KY001', 'Achmad Baihaqi', 2, 22000, 22000, 0, '2022-10-07 12:44:39'),
('TRJ00014', 'KY001', 'Achmad Baihaqi', 5, 48000, 48000, 0, '2022-10-07 02:45:18'),
('TRJ00015', 'KY001', 'Achmad Baihaqi', 8, 108000, 108000, 0, '2022-11-08 08:46:36'),
('TRJ00016', 'KY001', 'Achmad Baihaqi', 8, 88000, 88000, 0, '2022-11-09 12:47:40'),
('TRJ00017', 'KY001', 'Achmad Baihaqi', 8, 102000, 102000, 0, '2022-11-11 22:01:57'),
('TRJ00018', 'KY001', 'Achmad Baihaqi', 8, 94000, 94000, 0, '2022-11-11 22:02:54'),
('TRJ00019', 'KY001', 'Achmad Baihaqi', 13, 135000, 135000, 0, '2022-11-11 22:03:44'),
('TRJ00020', 'KY001', 'Achmad Baihaqi', 8, 99000, 99000, 0, '2022-11-13 04:23:46'),
('TRJ00021', 'KY001', 'Achmad Baihaqi', 3, 36000, 36000, 0, '2022-11-13 04:24:08'),
('TRJ00022', 'KY001', 'Achmad Baihaqi', 5, 56000, 56000, 0, '2022-11-13 04:24:19'),
('TRJ00023', 'KY001', 'Achmad Baihaqi', 4, 44000, 44000, 0, '2022-11-15 05:34:32'),
('TRJ00024', 'KY001', 'Achmad Baihaqi', 12, 119000, 119000, 0, '2022-11-15 05:34:59'),
('TRJ00025', 'KY001', 'Achmad Baihaqi', 15, 208000, 208000, 0, '2022-11-16 11:35:48'),
('TRJ00026', 'KY001', 'Achmad Baihaqi', 15, 130000, 130000, 0, '2022-11-17 09:05:32'),
('TRJ00027', 'KY001', 'Achmad Baihaqi', 22, 262000, 262000, 0, '2022-11-17 09:06:12'),
('TRJ00028', 'KY002', 'Mohammad Ilham Islamy', 5, 48000, 48000, 0, '2022-11-17 09:06:53'),
('TRJ00029', 'KY002', 'Mohammad Ilham Islamy', 18, 221000, 221000, 0, '2022-11-19 10:08:00'),
('TRJ00030', 'KY002', 'Mohammad Ilham Islamy', 6, 69000, 69000, 0, '2022-11-19 10:09:24'),
('TRJ00031', 'KY001', 'Achmad Baihaqi', 4, 60000, 60000, 0, '2022-11-23 10:12:53'),
('TRJ00032', 'KY001', 'Achmad Baihaqi', 9, 108000, 108000, 0, '2022-11-23 10:13:08'),
('TRJ00033', 'KY001', 'Achmad Baihaqi', 3, 36000, 36000, 0, '2022-11-25 10:13:32'),
('TRJ00034', 'KY001', 'Achmad Baihaqi', 5, 48000, 48000, 0, '2022-11-27 03:14:11'),
('TRJ00035', 'KY001', 'Achmad Baihaqi', 10, 120000, 120000, 0, '2022-11-29 03:14:48'),
('TRJ00036', 'KY001', 'Achmad Baihaqi', 8, 88000, 88000, 0, '2022-12-01 08:21:00'),
('TRJ00037', 'KY001', 'Achmad Baihaqi', 6, 72000, 72000, 0, '2022-12-02 23:21:42'),
('TRJ00038', 'KY001', 'Achmad Baihaqi', 5, 69000, 69000, 0, '2022-12-03 02:22:13'),
('TRJ00039', 'KY001', 'Achmad Baihaqi', 1, 12000, 12000, 0, '2022-12-03 02:22:27'),
('TRJ00040', 'KY001', 'Achmad Baihaqi', 3, 40000, 40000, 0, '2022-12-03 02:22:38'),
('TRJ00041', 'KY001', 'Achmad Baihaqi', 3, 25000, 25000, 0, '2022-12-03 02:22:55'),
('TRJ00042', 'KY001', 'Achmad Baihaqi', 2, 24000, 24000, 0, '2022-12-05 12:23:33'),
('TRJ00043', 'KY001', 'Achmad Baihaqi', 5, 52000, 52000, 0, '2022-12-05 12:23:49'),
('TRJ00044', 'KY001', 'Achmad Baihaqi', 2, 24000, 24000, 0, '2022-12-05 12:24:12'),
('TRJ00045', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2022-12-05 12:24:18'),
('TRJ00046', 'KY001', 'Achmad Baihaqi', 1, 15000, 15000, 0, '2022-12-05 12:24:25'),
('TRJ00047', 'KY001', 'Achmad Baihaqi', 4, 60000, 60000, 0, '2022-12-07 12:24:46'),
('TRJ00048', 'KY001', 'Achmad Baihaqi', 7, 74000, 74000, 0, '2022-12-09 12:25:50'),
('TRJ00049', 'KY001', 'Achmad Baihaqi', 8, 95000, 95000, 0, '2022-12-09 12:26:33'),
('TRJ00050', 'KY001', 'Achmad Baihaqi', 3, 41000, 41000, 0, '2022-12-09 12:26:52'),
('TRJ00051', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2022-12-09 12:26:58'),
('TRJ00052', 'KY001', 'Achmad Baihaqi', 9, 114000, 114000, 0, '2022-12-09 12:27:19'),
('TRJ00053', 'KY001', 'Achmad Baihaqi', 14, 192000, 192000, 0, '2022-12-10 12:28:19'),
('TRJ00054', 'KY001', 'Achmad Baihaqi', 14, 160000, 160000, 0, '2022-12-11 03:28:54'),
('TRJ00055', 'KY001', 'Achmad Baihaqi', 13, 168000, 168000, 0, '2022-12-12 03:29:26'),
('TRJ00056', 'KY001', 'Achmad Baihaqi', 27, 324000, 324000, 0, '2022-12-13 03:30:10'),
('TRJ00057', 'KY001', 'Achmad Baihaqi', 4, 48000, 48000, 0, '2022-12-14 03:31:10'),
('TRJ00058', 'KY001', 'Achmad Baihaqi', 5, 60000, 60000, 0, '2022-12-15 03:36:11'),
('TRJ00059', 'KY001', 'Achmad Baihaqi', 5, 50000, 50000, 0, '2022-12-15 03:36:35'),
('TRJ00060', 'KY001', 'Achmad Baihaqi', 8, 86000, 86000, 0, '2022-12-16 03:37:19'),
('TRJ00061', 'KY001', 'Achmad Baihaqi', 3, 45000, 45000, 0, '2022-12-17 03:37:43'),
('TRJ00062', 'KY001', 'Achmad Baihaqi', 3, 36000, 36000, 0, '2022-12-17 03:37:56'),
('TRJ00063', 'KY001', 'Achmad Baihaqi', 9, 55000, 55000, 0, '2022-12-17 03:38:23'),
('TRJ00064', 'KY001', 'Achmad Baihaqi', 2, 6000, 6000, 0, '2022-12-17 03:38:41'),
('TRJ00065', 'KY001', 'Achmad Baihaqi', 6, 68000, 68000, 0, '2022-12-17 03:38:53'),
('TRJ00066', 'KY001', 'Achmad Baihaqi', 3, 33000, 33000, 0, '2022-12-19 12:39:23'),
('TRJ00067', 'KY001', 'Achmad Baihaqi', 5, 60000, 60000, 0, '2022-12-19 12:39:42'),
('TRJ00068', 'KY002', 'Mohammad Ilham Islamy', 10, 106000, 106000, 0, '2022-12-19 12:40:31'),
('TRJ00069', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2022-12-19 12:40:44'),
('TRJ00070', 'KY002', 'Mohammad Ilham Islamy', 5, 60000, 60000, 0, '2022-12-19 12:40:52'),
('TRJ00071', 'KY001', 'Achmad Baihaqi', 3, 36000, 36000, 0, '2022-12-21 12:41:25'),
('TRJ00072', 'KY003', 'Widyasari Raisya Salsabilla', 6, 48000, 48000, 0, '2022-12-21 12:41:51'),
('TRJ00073', 'KY003', 'Widyasari Raisya Salsabilla', 8, 96000, 96000, 0, '2022-12-21 12:42:09'),
('TRJ00074', 'KY004', 'Septian Yoga Pamungkas', 2, 24000, 24000, 0, '2022-12-21 12:42:41'),
('TRJ00075', 'KY004', 'Septian Yoga Pamungkas', 4, 48000, 48000, 0, '2022-12-23 12:43:04'),
('TRJ00076', 'KY004', 'Septian Yoga Pamungkas', 5, 50000, 50000, 0, '2022-12-23 12:43:15'),
('TRJ00077', 'KY004', 'Septian Yoga Pamungkas', 2, 30000, 30000, 0, '2022-12-23 12:43:23'),
('TRJ00078', 'KY004', 'Septian Yoga Pamungkas', 8, 120000, 120000, 0, '2022-12-25 12:43:46'),
('TRJ00079', 'KY004', 'Septian Yoga Pamungkas', 2, 30000, 30000, 0, '2022-12-18 12:44:48'),
('TRJ00080', 'KY001', 'Achmad Baihaqi', 4, 40000, 40000, 0, '2022-12-28 12:49:48'),
('TRJ00081', 'KY001', 'Achmad Baihaqi', 4, 45000, 45000, 0, '2022-12-28 12:50:12'),
('TRJ00082', 'KY001', 'Achmad Baihaqi', 1, 13000, 13000, 0, '2022-12-29 12:50:37'),
('TRJ00083', 'KY001', 'Achmad Baihaqi', 4, 40000, 40000, 0, '2022-12-29 12:50:53'),
('TRJ00084', 'KY001', 'Achmad Baihaqi', 5, 60000, 60000, 0, '2022-12-31 12:51:14'),
('TRJ00085', 'KY001', 'Achmad Baihaqi', 1, 12000, 12000, 0, '2023-01-01 01:53:40'),
('TRJ00086', 'KY001', 'Achmad Baihaqi', 2, 25000, 25000, 0, '2023-01-01 01:54:02'),
('TRJ00087', 'KY002', 'Mohammad Ilham Islamy', 2, 20000, 20000, 0, '2023-01-01 01:54:53'),
('TRJ00088', 'KY003', 'Widyasari Raisya Salsabilla', 3, 24000, 24000, 0, '2023-01-02 02:55:28'),
('TRJ00089', 'KY003', 'Widyasari Raisya Salsabilla', 4, 48000, 48000, 0, '2023-01-02 02:56:02'),
('TRJ00090', 'KY004', 'Septian Yoga Pamungkas', 4, 40000, 40000, 0, '2023-01-02 02:56:37'),
('TRJ00091', 'KY004', 'Septian Yoga Pamungkas', 2, 22000, 22000, 0, '2023-01-03 06:57:11'),
('TRJ00092', 'KY001', 'Achmad Baihaqi', 1, 12000, 12000, 0, '2023-01-04 17:00:15'),
('TRJ00093', 'KY001', 'Achmad Baihaqi', 2, 25000, 25000, 0, '2023-01-04 17:17:24'),
('TRJ00094', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2023-01-04 20:37:04'),
('TRJ00095', 'KY001', 'Achmad Baihaqi', 2, 20000, 20000, 0, '2023-01-04 20:40:42'),
('TRJ00096', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2023-01-04 21:59:30'),
('TRJ00097', 'KY001', 'Achmad Baihaqi', 2, 24000, 24000, 0, '2023-01-04 22:04:57'),
('TRJ00098', 'KY001', 'Achmad Baihaqi', 1, 15000, 15000, 0, '2023-01-04 22:07:18'),
('TRJ00099', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2023-01-04 22:21:39'),
('TRJ00100', 'KY002', 'Mohammad Ilham Islamy', 2, 20000, 20000, 0, '2023-01-04 22:21:46'),
('TRJ00101', 'KY003', 'Widyasari Raisya Salsabilla', 1, 13000, 13000, 0, '2023-01-04 22:21:55'),
('TRJ00102', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, 15000, 0, '2023-01-04 22:22:04'),
('TRJ00103', 'KY002', 'Mohammad Ilham Islamy', 1, 12000, 12000, 0, '2023-01-04 22:24:29'),
('TRJ00104', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, 12000, 0, '2023-01-04 22:26:07'),
('TRJ00105', 'KY003', 'Widyasari Raisya Salsabilla', 1, 15000, 15000, 0, '2023-01-04 22:27:18'),
('TRJ00106', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2023-01-04 23:24:40'),
('TRJ00107', 'KY001', 'Achmad Baihaqi', 4, 40000, 40000, 0, '2023-01-04 23:35:01'),
('TRJ00108', 'KY002', 'Mohammad Ilham Islamy', 12, 136000, 136000, 0, '2023-01-04 23:53:23'),
('TRJ00109', 'KY002', 'Mohammad Ilham Islamy', 8, 80000, 80000, 0, '2023-01-04 23:53:46'),
('TRJ00110', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, 15000, 0, '2023-01-04 23:55:34'),
('TRJ00111', 'KY002', 'Mohammad Ilham Islamy', 2, 28000, 28000, 0, '2023-01-05 00:01:02'),
('TRJ00112', 'KY002', 'Mohammad Ilham Islamy', 1, 13000, 13000, 0, '2023-01-05 00:01:09'),
('TRJ00113', 'KY002', 'Mohammad Ilham Islamy', 1, 3000, 3000, 0, '2023-01-05 00:01:15'),
('TRJ00114', 'KY001', 'Achmad Baihaqi', 1, 15000, 15000, 0, '2023-01-05 00:01:23'),
('TRJ00115', 'KY001', 'Achmad Baihaqi', 1, 15000, 15000, 0, '2023-01-05 00:06:37'),
('TRJ00116', 'KY003', 'Widyasari Raisya Salsabilla', 9, 90000, 90000, 0, '2023-01-05 00:08:41'),
('TRJ00117', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, 12000, 0, '2023-01-05 00:08:50'),
('TRJ00118', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, 12000, 0, '2023-01-05 00:09:05'),
('TRJ00119', 'KY003', 'Widyasari Raisya Salsabilla', 13, 135000, 135000, 0, '2023-01-05 00:57:48'),
('TRJ00120', 'KY001', 'Achmad Baihaqi', 10, 122000, 122000, 0, '2023-01-05 11:17:19'),
('TRJ00121', 'KY001', 'Achmad Baihaqi', 5, 50000, 50000, 0, '2023-01-05 11:19:58'),
('TRJ00122', 'KY001', 'Achmad Baihaqi', 10, 150000, 150000, 0, '2023-01-05 11:22:23'),
('TRJ00123', 'KY002', 'Mohammad Ilham Islamy', 2, 27000, 27000, 0, '2023-01-05 11:43:25'),
('TRJ00124', 'KY002', 'Mohammad Ilham Islamy', 1, 12000, 12000, 0, '2023-01-05 22:32:02'),
('TRJ00125', 'KY002', 'Mohammad Ilham Islamy', 3, 36000, 36000, 0, '2023-01-06 15:15:30'),
('TRJ00126', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2023-01-06 15:35:31'),
('TRJ00127', 'KY003', 'Widyasari Raisya Salsabilla', 4, 42000, 42000, 0, '2023-01-06 15:38:34'),
('TRJ00128', 'KY002', 'Mohammad Ilham Islamy', 4, 38000, 38000, 0, '2023-01-06 17:47:00'),
('TRJ00129', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, 10000, 0, '2023-01-06 17:59:56'),
('TRJ00130', 'KY002', 'Mohammad Ilham Islamy', 1, 12000, 12000, 0, '2023-01-06 18:01:05'),
('TRJ00131', 'KY003', 'Widyasari Raisya Salsabilla', 4, 57000, 57000, 0, '2023-01-06 19:35:27'),
('TRJ00132', 'KY004', 'Septian Yoga Pamungkas', 10, 131000, 131000, 0, '2023-01-06 22:12:29'),
('TRJ00133', 'KY003', 'Widyasari Raisya Salsabilla', 4, 32000, 32000, 0, '2023-01-06 22:16:07'),
('TRJ00134', 'KY004', 'Septian Yoga Pamungkas', 1, 12000, 12000, 0, '2023-01-07 12:55:21'),
('TRJ00135', 'KY002', 'Mohammad Ilham Islamy', 1, 12000, 12000, 0, '2023-01-07 12:56:06'),
('TRJ00136', 'KY001', 'Achmad Baihaqi', 2, 24000, 24000, 0, '2023-01-07 12:56:55'),
('TRJ00137', 'KY002', 'Mohammad Ilham Islamy', 1, 12000, 12000, 0, '2023-01-07 12:57:47'),
('TRJ00138', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, 12000, 0, '2023-01-07 12:58:20'),
('TRJ00139', 'KY004', 'Septian Yoga Pamungkas', 1, 10000, 10000, 0, '2023-01-07 13:00:55'),
('TRJ00140', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-07 13:01:39'),
('TRJ00141', 'KY001', 'Achmad Baihaqi', 2, 16000, 16000, 0, '2023-01-07 17:05:23'),
('TRJ00142', 'KY002', 'Mohammad Ilham Islamy', 2, 16000, 16000, 0, '2023-01-07 17:06:37'),
('TRJ00143', 'KY001', 'Achmad Baihaqi', 2, 24000, 24000, 0, '2023-01-08 15:36:24'),
('TRJ00144', 'KY003', 'Widyasari Raisya Salsabilla', 4, 48000, 48000, 0, '2023-01-08 16:07:49'),
('TRJ00145', 'KY001', 'Achmad Baihaqi', 7, 77000, 77000, 0, '2023-01-08 19:45:46'),
('TRJ00146', 'KY004', 'Septian Yoga Pamungkas', 6, 52000, 52000, 0, '2023-01-08 19:46:18'),
('TRJ00147', 'KY002', 'Mohammad Ilham Islamy', 6, 78000, 78000, 0, '2023-01-08 19:46:47'),
('TRJ00148', 'KY003', 'Widyasari Raisya Salsabilla', 5, 66000, 66000, 0, '2023-01-08 19:47:10'),
('TRJ00149', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, 15000, 0, '2023-01-08 21:10:22'),
('TRJ00150', 'KY001', 'Achmad Baihaqi', 7, 92000, 92000, 0, '2023-01-09 14:36:16'),
('TRJ00151', 'KY002', 'Mohammad Ilham Islamy', 2, 23000, 23000, 0, '2023-01-09 00:07:16'),
('TRJ00152', 'KY004', 'Septian Yoga Pamungkas', 4, 50000, 50000, 0, '2023-01-09 14:03:18'),
('TRJ00153', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, 15000, 0, '2023-01-09 14:28:47'),
('TRJ00154', 'KY004', 'Septian Yoga Pamungkas', 10, 116000, 116000, 0, '2023-01-09 23:02:40'),
('TRJ00155', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-09 14:31:07'),
('TRJ00156', 'KY003', 'Widyasari Raisya Salsabilla', 3, 34000, 34000, 0, '2023-01-09 14:41:29'),
('TRJ00157', 'KY002', 'Mohammad Ilham Islamy', 3, 34000, 34000, 0, '2023-01-09 14:40:59'),
('TRJ00159', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, 10000, 0, '2023-01-09 14:42:16'),
('TRJ00160', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2023-01-09 17:45:07'),
('TRJ00161', 'KY003', 'Widyasari Raisya Salsabilla', 4, 40000, 40000, 0, '2023-01-09 17:57:38'),
('TRJ00162', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-09 17:58:21'),
('TRJ00163', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-09 17:58:27'),
('TRJ00164', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-09 17:58:33'),
('TRJ00165', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, 12000, 0, '2023-01-09 18:12:26'),
('TRJ00166', 'KY003', 'Widyasari Raisya Salsabilla', 1, 15000, 15000, 0, '2023-01-09 21:38:50'),
('TRJ00167', 'KY002', 'Mohammad Ilham Islamy', 5, 54000, 60000, 6000, '2023-01-09 22:12:16'),
('TRJ00168', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, 10000, 0, '2023-01-09 23:50:57'),
('TRJ00169', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-09 22:08:16'),
('TRJ00170', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, 20000, 5000, '2023-01-09 21:54:17'),
('TRJ00171', 'KY004', 'Septian Yoga Pamungkas', 1, 10000, 10000, 0, '2023-01-09 21:54:27'),
('TRJ00172', 'KY001', 'Achmad Baihaqi', 5, 54000, 54000, 0, '2023-01-09 22:10:38'),
('TRJ00173', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, 20000, 10000, '2023-01-09 22:11:59'),
('TRJ00174', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, 20000, 5000, '2023-01-09 23:57:57'),
('TRJ00175', 'KY004', 'Septian Yoga Pamungkas', 3, 26000, 30000, 4000, '2023-01-10 00:02:55'),
('TRJ00176', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, 20000, 5000, '2023-01-10 16:34:35'),
('TRJ00177', 'KY001', 'Achmad Baihaqi', 4, 36000, 40000, 4000, '2023-01-11 21:42:02'),
('TRJ00178', 'KY002', 'Mohammad Ilham Islamy', 4, 42000, 50000, 8000, '2023-01-11 21:45:48'),
('TRJ00186', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2023-01-12 15:38:06'),
('TRJ00187', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, 15000, 0, '2023-01-12 15:38:24'),
('TRJ00188', 'KY001', 'Achmad Baihaqi', 2, 27000, 30000, 3000, '2023-01-12 15:38:58'),
('TRJ00189', 'KY001', 'Achmad Baihaqi', 2, 20000, 20000, 0, '2023-01-12 15:39:09'),
('TRJ00190', 'KY002', 'Mohammad Ilham Islamy', 1, 12000, 15000, 3000, '2023-01-12 15:39:22'),
('TRJ00191', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-12 15:40:34'),
('TRJ00192', 'KY004', 'Septian Yoga Pamungkas', 1, 10000, 10000, 0, '2023-01-12 15:41:36'),
('TRJ00193', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, 15000, 0, '2023-01-12 15:41:42'),
('TRJ00194', 'KY001', 'Achmad Baihaqi', 1, 15000, 15000, 0, '2023-01-12 15:45:43'),
('TRJ00195', 'KY001', 'Achmad Baihaqi', 1, 12000, 12000, 0, '2023-01-12 15:46:21'),
('TRJ00196', 'KY003', 'Widyasari Raisya Salsabilla', 1, 15000, 15000, 0, '2023-01-12 15:46:25'),
('TRJ00197', 'KY003', 'Widyasari Raisya Salsabilla', 1, 15000, 15000, 0, '2023-01-12 15:46:47'),
('TRJ00198', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, 15000, 3000, '2023-01-12 15:46:59'),
('TRJ00199', 'KY001', 'Achmad Baihaqi', 4, 48000, 50000, 2000, '2023-01-12 15:47:18'),
('TRJ00200', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, 10000, 0, '2023-01-12 15:47:36'),
('TRJ00201', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2023-01-15 18:45:12'),
('TRJ00202', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, 10000, 0, '2023-01-15 18:52:55'),
('TRJ00203', 'KY001', 'Achmad Baihaqi', 2, 22000, 25000, 3000, '2023-01-15 19:03:17'),
('TRJ00204', 'KY003', 'Widyasari Raisya Salsabilla', 1, 15000, 20000, 5000, '2023-01-15 19:04:01'),
('TRJ00205', 'KY002', 'Mohammad Ilham Islamy', 4, 55000, 60000, 5000, '2023-01-16 19:43:38'),
('TRJ00206', 'KY001', 'Achmad Baihaqi', 1, 15000, 100000, 85000, '2023-01-16 19:33:26'),
('TRJ00207', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, 10000, 0, '2023-01-16 19:36:05'),
('TRJ00208', 'KY003', 'Widyasari Raisya Salsabilla', 1, 15000, 20000, 5000, '2023-01-16 19:38:09'),
('TRJ00209', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, 20000, 5000, '2023-01-16 19:52:44'),
('TRJ00210', 'KY002', 'Mohammad Ilham Islamy', 2, 30000, 30000, 0, '2023-01-16 19:44:39'),
('TRJ00211', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, 20000, 5000, '2023-01-16 22:36:13'),
('TRJ00212', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, 10000, 0, '2023-01-16 19:48:18'),
('TRJ00213', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-16 22:42:39'),
('TRJ00214', 'KY003', 'Widyasari Raisya Salsabilla', 1, 15000, 20000, 5000, '2023-01-16 19:54:14'),
('TRJ00215', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, 15000, 0, '2023-01-16 19:56:16'),
('TRJ00216', 'KY001', 'Achmad Baihaqi', 2, 30000, 30000, 0, '2023-01-16 19:59:36'),
('TRJ00217', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, 20000, 5000, '2023-01-16 20:00:59'),
('TRJ00218', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, 15000, 3000, '2023-01-16 22:40:11'),
('TRJ00219', 'KY001', 'Achmad Baihaqi', 1, 15000, 50000, 35000, '2023-01-16 23:04:43'),
('TRJ00220', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-16 22:48:54'),
('TRJ00221', 'KY002', 'Mohammad Ilham Islamy', 1, 12000, 15000, 3000, '2023-01-16 23:20:39'),
('TRJ00222', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-16 23:34:34'),
('TRJ00223', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, 20000, 5000, '2023-01-16 23:18:52'),
('TRJ00224', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, 20000, 5000, '2023-01-16 23:06:30'),
('TRJ00225', 'KY002', 'Mohammad Ilham Islamy', 1, 13000, 15000, 2000, '2023-01-16 23:16:40'),
('TRJ00226', 'KY002', 'Mohammad Ilham Islamy', 2, 26000, 30000, 4000, '2023-01-16 23:24:44'),
('TRJ00227', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-16 23:32:19'),
('TRJ00228', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-16 23:31:06'),
('TRJ00229', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, 20000, 5000, '2023-01-16 23:31:47'),
('TRJ00230', 'KY002', 'Mohammad Ilham Islamy', 2, 25000, 30000, 5000, '2023-01-16 23:58:54'),
('TRJ00232', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, 20000, 5000, '2023-01-16 23:34:57'),
('TRJ00233', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-16 23:37:40'),
('TRJ00234', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, 20000, 5000, '2023-01-16 23:50:27'),
('TRJ00235', 'KY002', 'Mohammad Ilham Islamy', 2, 30000, 30000, 0, '2023-01-16 23:59:32'),
('TRJ00236', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-16 23:56:17'),
('TRJ00237', 'KY002', 'Mohammad Ilham Islamy', 2, 20000, 20000, 0, '2023-01-16 23:50:47'),
('TRJ00238', 'KY002', 'Mohammad Ilham Islamy', 1, 11000, 20000, 9000, '2023-01-16 23:51:22'),
('TRJ00239', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, 10000, 0, '2023-01-16 23:54:36'),
('TRJ00240', 'KY001', 'Achmad Baihaqi', 1, 15000, 30000, 15000, '2023-01-17 00:11:24'),
('TRJ00241', 'KY001', 'Achmad Baihaqi', 2, 30000, 30000, 0, '2023-01-17 00:08:39'),
('TRJ00242', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, 15000, 0, '2023-01-17 00:10:25'),
('TRJ00243', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 20000, 10000, '2023-01-17 00:15:08'),
('TRJ00244', 'KY002', 'Mohammad Ilham Islamy', 7, 91000, 100000, 9000, '2023-01-17 00:17:23'),
('TRJ00245', 'KY003', 'Widyasari Raisya Salsabilla', 2, 30000, 30000, 0, '2023-01-17 14:08:18'),
('TRJ00246', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, 20000, 5000, '2023-01-17 14:23:40');

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
(7, 'tester', '$2a$12$vo3Su5mFytzQklgnhtwbJ.sBJoUH3ebH84TugA959Ecwl8Vso5KuW', 'ADMIN', 'KY005');

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
  MODIFY `id_login` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

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
