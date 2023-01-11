-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 10, 2023 at 06:48 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

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
-- Table structure for table `bahan`
--

CREATE TABLE `bahan` (
  `id_bahan` varchar(6) NOT NULL,
  `nama_bahan` varchar(50) NOT NULL,
  `jenis` enum('Hewani','Nabati','Coffee','Perasa','Cairan','Bahan Jadi') NOT NULL,
  `satuan` enum('kg','lt','ds','rc','ls','bt') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bahan`
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
('BA025', 'Air Mineral', 'Cairan', 'lt'),
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
-- Triggers `bahan`
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
-- Table structure for table `detail_tr_beli`
--

CREATE TABLE `detail_tr_beli` (
  `id_tr_beli` varchar(7) NOT NULL,
  `id_bahan` varchar(6) DEFAULT NULL,
  `nama_bahan` varchar(50) NOT NULL,
  `jenis_bahan` enum('Hewani','Nabati','Coffee','Perasa','Cairan','Bahan Jadi') NOT NULL,
  `jumlah` float NOT NULL,
  `satuan_bahan` enum('kg','lt','ds','rc','ls','bt') NOT NULL,
  `total_harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `detail_tr_beli`
--

INSERT INTO `detail_tr_beli` (`id_tr_beli`, `id_bahan`, `nama_bahan`, `jenis_bahan`, `jumlah`, `satuan_bahan`, `total_harga`) VALUES
('TRB0001', 'BA003', 'Jeruk', 'Nabati', 1, 'kg', 25000),
('TRB0002', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 50000),
('TRB0003', 'BA003', 'Jeruk', 'Nabati', 2, 'kg', 50000),
('TRB0004', 'BA025', 'Air Mineral', 'Cairan', 5, 'lt', 15000),
('TRB0005', 'BA011', 'Kacang', 'Nabati', 2, 'kg', 10000),
('TRB0006', 'BA029', 'Jamur', 'Nabati', 1, 'kg', 15000),
('TRB0007', 'BA025', 'Air Mineral', 'Cairan', 5, 'lt', 15000),
('TRB0007', 'BA022', 'Daging Ayam', 'Hewani', 2, 'kg', 40000),
('TRB0007', 'BA027', 'Anggur', 'Nabati', 2, 'kg', 40000),
('TRB0008', 'BA026', 'Gula Pasir', 'Perasa', 4, 'kg', 56000),
('TRB0009', 'BA005', 'Biji Kopi', 'Coffee', 1, 'kg', 120000),
('TRB0010', 'BA005', 'Biji Kopi', 'Coffee', 2, 'kg', 140000),
('TRB0010', 'BA029', 'Jamur', 'Nabati', 1, 'kg', 15000),
('TRB0011', 'BA003', 'Jeruk', 'Nabati', 3, 'kg', 75000),
('TRB0012', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 50000),
('TRB0013', 'BA002', 'Kentang', 'Nabati', 3, 'kg', 150000),
('TRB0014', 'BA028', 'Nasi', 'Nabati', 2, 'kg', 16000),
('TRB0015', 'BA030', 'Garam', 'Perasa', 5, 'kg', 30000),
('TRB0016', 'BA003', 'Jeruk', 'Nabati', 2, 'kg', 50000),
('TRB0017', 'BA028', 'Nasi', 'Nabati', 2, 'kg', 16000),
('TRB0017', 'BA021', 'Bawang Putih', 'Nabati', 1, 'kg', 10000),
('TRB0018', 'BA022', 'Daging Ayam', 'Hewani', 2, 'kg', 40000),
('TRB0019', 'BA003', 'Jeruk', 'Nabati', 5, 'kg', 125000),
('TRB0020', 'BA026', 'Gula Pasir', 'Perasa', 5, 'kg', 70000),
('TRB0021', 'BA025', 'Air Mineral', 'Cairan', 12, 'lt', 36000),
('TRB0022', 'BA028', 'Nasi', 'Nabati', 8, 'kg', 64000),
('TRB0023', 'BA029', 'Jamur', 'Nabati', 2, 'kg', 30000),
('TRB0024', 'BA021', 'Bawang Putih', 'Nabati', 5, 'kg', 50000),
('TRB0025', 'BA020', 'Bawang Merah', 'Nabati', 4, 'kg', 100000),
('TRB0025', 'BA022', 'Daging Ayam', 'Hewani', 5, 'kg', 100000),
('TRB0026', 'BA019', 'Cabe Rawit', 'Nabati', 2, 'kg', 10000),
('TRB0026', 'BA025', 'Air Mineral', 'Cairan', 10, 'lt', 30000),
('TRB0026', 'BA018', 'Kecap', 'Perasa', 1, 'lt', 1500),
('TRB0027', 'BA011', 'Kacang', 'Nabati', 5, 'kg', 25000),
('TRB0028', 'BA005', 'Biji Kopi', 'Coffee', 2, 'kg', 140000),
('TRB0028', 'BA025', 'Air Mineral', 'Cairan', 4, 'lt', 12000),
('TRB0028', 'BA001', 'Susu Coklat', 'Cairan', 2, 'lt', 50000),
('TRB0029', 'BA002', 'Kentang', 'Nabati', 5, 'kg', 250000),
('TRB0030', 'BA018', 'Kecap', 'Perasa', 3, 'lt', 4500),
('TRB0031', 'BA005', 'Biji Kopi', 'Coffee', 2, 'kg', 140000),
('TRB0032', 'BA002', 'Kentang', 'Nabati', 3, 'kg', 150000),
('TRB0033', 'BA025', 'Air Mineral', 'Cairan', 10, 'lt', 30000),
('TRB0034', 'BA002', 'Kentang', 'Nabati', 5, 'kg', 250000),
('TRB0035', 'BA005', 'Biji Kopi', 'Coffee', 2, 'kg', 140000),
('TRB0036', 'BA018', 'Kecap', 'Perasa', 5, 'lt', 7500),
('TRB0037', 'BA005', 'Biji Kopi', 'Coffee', 3, 'kg', 210000),
('TRB0038', 'BA026', 'Gula Pasir', 'Perasa', 4, 'kg', 56000),
('TRB0039', 'BA025', 'Air Mineral', 'Cairan', 5, 'lt', 15000),
('TRB0040', 'BA001', 'Susu Coklat', 'Cairan', 3, 'lt', 75000),
('TRB0041', 'BA002', 'Kentang', 'Nabati', 3, 'kg', 150000),
('TRB0042', 'BA015', 'Apel', 'Nabati', 8, 'kg', 40000),
('TRB0042', 'BA011', 'Kacang', 'Nabati', 2, 'kg', 10000),
('TRB0043', 'BA005', 'Biji Kopi', 'Coffee', 1, 'kg', 70000),
('TRB0044', 'BA025', 'Air Mineral', 'Cairan', 5, 'lt', 15000),
('TRB0045', 'BA022', 'Daging Ayam', 'Hewani', 1, 'kg', 20000),
('TRB0046', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 10000),
('TRB0046', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 8000),
('TRB0047', 'BA011', 'Kacang', 'Nabati', 1, 'kg', 12000),
('TRB0048', 'BA003', 'Jeruk', 'Nabati', 2, 'kg', 20000),
('TRB0049', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 10000),
('TRB0050', 'BA018', 'Kecap', 'Perasa', 2, 'lt', 30000),
('TRB0051', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 50000),
('TRB0052', 'BA008', 'Kopi Hitam', 'Coffee', 1, 'kg', 10000),
('TRB0053', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 10000),
('TRB0054', 'BA032', 'Kapal Api', 'Coffee', 3, 'rc', 30000),
('TRB0054', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 50000),
('TRB0056', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 40000),
('TRB0056', 'BA032', 'Kapal Api', 'Coffee', 1, 'rc', 25000),
('TRB0057', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 15000),
('TRB0058', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 1),
('TRB0058', 'BA032', 'Kapal Api', 'Coffee', 1, 'rc', 1),
('TRB0059', 'BA008', 'Kopi Hitam', 'Coffee', 3, 'kg', 30000),
('TRB0061', 'BA032', 'Kapal Api', 'Coffee', 1, 'rc', 10000),
('TRB0063', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 15000),
('TRB0064', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 45000),
('TRB0064', 'BA032', 'Kapal Api', 'Coffee', 1, 'rc', 12000),
('TRB0064', 'BA033', 'Telur Ayam', 'Nabati', 1, 'ls', 16000),
('TRB0065', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 50000),
('TRB0065', 'BA032', 'Kapal Api', 'Coffee', 2, 'rc', 26000),
('TRB0065', 'BA033', 'Telur Ayam', 'Nabati', 2, 'ls', 24000),
('TRB0066', 'BA032', 'Kapal Api', 'Coffee', 1, 'rc', 3000),
('TRB0066', 'BA033', 'Telur Ayam', 'Nabati', 2, 'ls', 40000),
('TRB0066', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 25000),
('TRB0067', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 12000),
('TRB0068', 'BA033', 'Telur Ayam', 'Nabati', 1, 'ls', 16000),
('TRB0068', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 1),
('TRB0069', 'BA028', 'Nasi', 'Nabati', 1, 'kg', 1000),
('TRB0070', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 8000),
('TRB0073', 'BA018', 'Kecap', 'Perasa', 1, 'bt', 12000),
('TRB0074', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 12000),
('TRB0075', 'BA001', 'Susu Coklat', 'Cairan', 5, 'lt', 50000),
('TRB0076', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 15000),
('TRB0077', 'BA002', 'Kentang', 'Nabati', 2, 'kg', 32000),
('TRB0078', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 12000),
('TRB0079', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 8000),
('TRB0080', 'BA011', 'Kacang', 'Nabati', 1, 'kg', 15000),
('TRB0080', 'BA033', 'Coklat', 'Nabati', 0.5, 'rc', 20000),
('TRB0081', 'BA018', 'Kecap', 'Perasa', 1, 'bt', 10000),
('TRB0082', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 5000),
('TRB0082', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 8000),
('TRB0082', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 40000),
('TRB0082', 'BA033', 'Telur Ayam', 'Nabati', 1, 'ls', 14000),
('TRB0083', 'BA032', 'Kapal Api', 'Coffee', 1, 'rc', 14000),
('TRB0084', 'BA005', 'Biji Kopi', 'Coffee', 1, 'kg', 16000),
('TRB0087', 'BA002', 'Kentang', 'Nabati', 2, 'kg', 15000),
('TRB0088', 'BA008', 'Kopi Hitam', 'Coffee', 2.5, 'kg', 50000),
('TRB0089', 'BA001', 'Susu Coklat', 'Cairan', 1.5, 'lt', 15000),
('TRB0089', 'BA003', 'Jeruk', 'Nabati', 1.25, 'kg', 15000),
('TRB0090', 'BA001', 'Susu Coklat', 'Cairan', 3, 'lt', 150000),
('TRB0090', 'BA029', 'Jamur', 'Nabati', 1, 'kg', 50000),
('TRB0090', 'BA033', 'Telur Ayam', 'Nabati', 1.5, 'ls', 50000),
('TRB0090', 'BA018', 'Kecap', 'Perasa', 1, 'bt', 50000),
('TRB0091', 'BA008', 'Kopi Hitam', 'Coffee', 1, 'kg', 15000),
('TRB0091', 'BA005', 'Biji Kopi', 'Coffee', 2, 'kg', 80000),
('TRB0091', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 40000),
('TRB0091', 'BA032', 'Kapal Api', 'Coffee', 1.5, 'rc', 12000),
('TRB0092', 'BA001', 'Susu Coklat', 'Cairan', 2, 'lt', 12000),
('TRB0092', 'BA001', 'Susu Coklat', 'Cairan', 2, 'lt', 10000),
('TRB0093', 'BA005', 'Biji Kopi', 'Coffee', 2, 'kg', 2000),
('TRB0094', 'BA001', 'Susu Coklat', 'Cairan', 4, 'lt', 10000),
('TRB0094', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 15000),
('TRB0094', 'BA011', 'Kacang', 'Nabati', 1.5, 'kg', 14000),
('TRB0094', 'BA015', 'Apel', 'Nabati', 1.1, 'kg', 11000),
('TRB0094', 'BA022', 'Daging Ayam', 'Hewani', 2, 'kg', 9000),
('TRB0095', 'BA018', 'Kecap', 'Perasa', 3, 'bt', 15000),
('TRB0095', 'BA019', 'Cabe Rawit', 'Nabati', 1, 'kg', 18000),
('TRB0097', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 12000),
('TRB0099', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 10000),
('TRB0100', 'BA027', 'Anggur', 'Nabati', 1, 'kg', 6000),
('TRB0101', 'BA026', 'Gula Pasir', 'Perasa', 2, 'kg', 6000),
('TRB0102', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 12000),
('TRB0103', 'BA005', 'Biji Kopi', 'Coffee', 2, 'kg', 12000),
('TRB0104', 'BA002', 'Kentang', 'Nabati', 2.5, 'kg', 15000),
('TRB0105', 'BA022', 'Daging Ayam', 'Hewani', 1, 'kg', 14000),
('TRB0106', 'BA011', 'Kacang', 'Nabati', 3, 'kg', 12000),
('TRB0106', 'BA001', 'Susu Coklat', 'Cairan', 2.001, 'lt', 8000),
('TRB0106', 'BA018', 'Kecap', 'Perasa', 1, 'bt', 9500),
('TRB0106', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 13000),
('TRB0106', 'BA033', 'Telur Ayam', 'Nabati', 1, 'ls', 8500),
('TRB0106', 'BA032', 'Kapal Api', 'Coffee', 1, 'rc', 15000),
('TRB0106', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 9000),
('TRB0107', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 12000),
('TRB0107', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 8000),
('TRB0108', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 12000),
('TRB0109', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 12000),
('TRB0110', 'BA001', 'Susu Coklat', 'Cairan', 1.5, 'lt', 15000),
('TRB0111', 'BA025', 'Air Mineral', 'Cairan', 1, 'lt', 14000),
('TRB0112', 'BA025', 'Air Mineral', 'Cairan', 1.5, 'lt', 15000),
('TRB0113', 'BA001', 'Susu Coklat', 'Cairan', 1.25, 'lt', 12000),
('TRB0113', 'BA002', 'Kentang', 'Nabati', 2.75, 'kg', 20000),
('TRB0114', 'BA029', 'Jamur', 'Nabati', 2.75, 'kg', 15000),
('TRB0115', 'BA020', 'Bawang Merah', 'Nabati', 1.2, 'kg', 15000),
('TRB0116', 'BA003', 'Jeruk', 'Nabati', 0.1, 'kg', 500),
('TRB0117', 'BA005', 'Biji Kopi', 'Coffee', 1.444, 'kg', 13000),
('TRB0118', 'BA001', 'Susu Coklat', 'Cairan', 0.000001, 'lt', 12000),
('TRB0119', 'BA002', 'Kentang', 'Nabati', 0.11333, 'kg', 8000),
('TRB0120', 'BA008', 'Kopi Hitam', 'Coffee', 1.2, 'kg', 8000),
('TRB0121', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 12000),
('TRB0122', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 10000),
('TRB0123', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 30000),
('TRB0123', 'BA001', 'Susu Coklat', 'Cairan', 1, 'lt', 12000),
('TRB0125', 'BA008', 'Kopi Hitam', 'Coffee', 1.5, 'kg', 15000),
('TRB0124', 'BA026', 'Gula Pasir', 'Perasa', 1, 'kg', 15000),
('TRB0124', 'BA033', 'Telur Ayam', 'Nabati', 1, 'ls', 10000),
('TRB0124', 'BA002', 'Kentang', 'Nabati', 1, 'kg', 25000),
('TRB0124', 'BA011', 'Kacang', 'Nabati', 1, 'kg', 10000),
('TRB0124', 'BA031', 'Indomie Goreng', 'Bahan Jadi', 1, 'ds', 40000),
('TRB0126', 'BA003', 'Jeruk', 'Nabati', 1, 'kg', 12000),
('TRB0126', 'BA026', 'Gula Pasir', 'Perasa', 1, 'kg', 20000),
('TRB0126', 'BA018', 'Kecap', 'Perasa', 1, 'bt', 8000),
('TRB0127', 'BA030', 'Garam', 'Perasa', 1, 'kg', 10000),
('TRB0129', 'BA029', 'Jamur', 'Nabati', 1, 'kg', 15000),
('TRB0129', 'BA018', 'Kecap', 'Perasa', 1, 'bt', 6000),
('TRB0129', 'BA033', 'Telur Ayam', 'Nabati', 1, 'ls', 12000),
('TRB0128', 'BA002', 'Kentang', 'Nabati', 1.3, 'kg', 12000),
('TRB0128', 'BA029', 'Jamur', 'Nabati', 1.1, 'kg', 18000),
('TRB0128', 'BA027', 'Anggur', 'Nabati', 0.5, 'kg', 10000),
('TRB0128', 'BA018', 'Kecap', 'Perasa', 1, 'bt', 10000),
('TRB0131', 'BA005', 'Biji Kopi', 'Coffee', 1, 'kg', 15000);

-- --------------------------------------------------------

--
-- Table structure for table `detail_tr_jual`
--

CREATE TABLE `detail_tr_jual` (
  `id_tr_jual` varchar(7) NOT NULL,
  `id_menu` varchar(5) DEFAULT NULL,
  `nama_menu` varchar(30) NOT NULL,
  `jenis_menu` enum('Minuman','Makanan','Original Coffee','Falvoured Coffee','Snack') NOT NULL,
  `harga_menu` int(11) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `detail_tr_jual`
--

INSERT INTO `detail_tr_jual` (`id_tr_jual`, `id_menu`, `nama_menu`, `jenis_menu`, `harga_menu`, `jumlah`, `total_harga`) VALUES
('TRJ0001', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0001', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ0002', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0003', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ0004', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0005', 'MN001', 'Orange Juice', 'Minuman', 10000, 2, 20000),
('TRJ0005', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 4, 60000),
('TRJ0005', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 3, 36000),
('TRJ0006', 'MN023', 'Kopi Susu', 'Minuman', 3000, 1, 3000),
('TRJ0007', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 6, 90000),
('TRJ0008', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 2, 24000),
('TRJ0009', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ0010', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 2, 24000),
('TRJ0010', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0010', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 3, 36000),
('TRJ0011', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 3, 45000),
('TRJ0011', 'MN001', 'Orange Juice', 'Minuman', 10000, 2, 20000),
('TRJ0011', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0012', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0013', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0013', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ0014', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0014', 'MN015', 'Jus Anggur', 'Minuman', 12000, 1, 12000),
('TRJ0014', 'MN023', 'Kopi Susu', 'Minuman', 3000, 2, 6000),
('TRJ0015', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 5, 75000),
('TRJ0015', 'MN019', 'Jamur Crispy', 'Snack', 10000, 2, 20000),
('TRJ0015', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ0016', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ0016', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0016', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0016', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0016', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0016', 'MN020', 'Jus Apel', 'Minuman', 11000, 1, 11000),
('TRJ0017', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 4, 60000),
('TRJ0017', 'MN001', 'Orange Juice', 'Minuman', 10000, 3, 30000),
('TRJ0017', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0018', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 4, 60000),
('TRJ0018', 'MN018', 'Keripik Kentang', 'Snack', 8000, 3, 24000),
('TRJ0018', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ0019', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 3, 45000),
('TRJ0019', 'MN023', 'Kopi Susu', 'Minuman', 3000, 2, 6000),
('TRJ0019', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0019', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 2, 24000),
('TRJ0019', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 5, 50000),
('TRJ0020', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 3, 45000),
('TRJ0020', 'MN022', 'Cappucino', 'Original Coffee', 10000, 3, 30000),
('TRJ0020', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0021', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0021', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 1, 12000),
('TRJ0022', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ0022', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ0023', 'MN001', 'Orange Juice', 'Minuman', 10000, 2, 20000),
('TRJ0023', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0024', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 3, 36000),
('TRJ0024', 'MN023', 'Kopi Susu', 'Minuman', 3000, 1, 3000),
('TRJ0024', 'MN019', 'Jamur Crispy', 'Snack', 10000, 8, 80000),
('TRJ0025', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 7, 105000),
('TRJ0025', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 5, 75000),
('TRJ0025', 'MN018', 'Keripik Kentang', 'Snack', 8000, 1, 8000),
('TRJ0025', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ0026', 'MN015', 'Jus Anggur', 'Minuman', 12000, 2, 24000),
('TRJ0026', 'MN001', 'Orange Juice', 'Minuman', 10000, 7, 70000),
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
('TRJ0034', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0035', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 10, 120000),
('TRJ0036', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ0036', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0036', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0036', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ0036', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ0037', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0037', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0037', 'MN019', 'Jamur Crispy', 'Snack', 10000, 2, 20000),
('TRJ0037', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0038', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0038', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0038', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0039', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
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
('TRJ0048', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0048', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ0048', 'MN001', 'Orange Juice', 'Minuman', 10000, 3, 30000),
('TRJ0049', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0049', 'MN015', 'Jus Anggur', 'Minuman', 12000, 3, 36000),
('TRJ0049', 'MN022', 'Cappucino', 'Original Coffee', 10000, 1, 10000),
('TRJ0049', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0049', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0050', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ0050', 'MN020', 'Jus Apel', 'Minuman', 11000, 1, 11000),
('TRJ0051', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0052', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 4, 60000),
('TRJ0052', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ0052', 'MN018', 'Keripik Kentang', 'Snack', 8000, 3, 24000),
('TRJ0053', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 4, 48000),
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
('TRJ0063', 'MN001', 'Orange Juice', 'Minuman', 10000, 2, 20000),
('TRJ0064', 'MN023', 'Kopi Susu', 'Minuman', 3000, 2, 6000),
('TRJ0065', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 4, 48000),
('TRJ0065', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ0066', 'MN020', 'Jus Apel', 'Minuman', 11000, 3, 33000),
('TRJ0067', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 5, 60000),
('TRJ0068', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ0068', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
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
('TRJ0085', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0086', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0086', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0087', 'MN019', 'Jamur Crispy', 'Snack', 10000, 2, 20000),
('TRJ0088', 'MN018', 'Keripik Kentang', 'Snack', 8000, 3, 24000),
('TRJ0089', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 4, 48000),
('TRJ0090', 'MN022', 'Cappucino', 'Original Coffee', 10000, 4, 40000),
('TRJ0091', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 1, 12000),
('TRJ0091', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0092', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ0093', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0093', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ0094', 'MN022', 'Cappucino', 'Original Coffee', 10000, 1, 10000),
('TRJ0095', 'MN001', 'Orange Juice', 'Minuman', 10000, 2, 20000),
('TRJ0096', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ0097', 'MN015', 'Jus Anggur', 'Minuman', 12000, 2, 24000),
('TRJ0098', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0099', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ0100', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ0101', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ0102', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0103', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ0104', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0105', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0106', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ0107', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0107', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ0108', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ0108', 'MN020', 'Jus Apel', 'Minuman', 11000, 9, 99000),
('TRJ0108', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0108', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 1, 12000),
('TRJ0109', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 8, 80000),
('TRJ0110', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0111', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0111', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ0112', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ0113', 'MN023', 'Kopi Susu Dingin', 'Minuman', 3000, 1, 3000),
('TRJ0114', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0115', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0116', 'MN001', 'Orange Juice', 'Minuman', 10000, 9, 90000),
('TRJ0117', 'MN015', 'Jus Anggur', 'Minuman', 12000, 1, 12000),
('TRJ0118', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0119', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0119', 'MN020', 'Jus Apel', 'Minuman', 11000, 2, 22000),
('TRJ0119', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ0119', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ0119', 'MN019', 'Jamur Crispy', 'Snack', 10000, 3, 30000),
('TRJ0119', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 2, 20000),
('TRJ0119', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0120', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0120', 'MN013', 'Kentang Goreng', 'Snack', 12000, 4, 48000),
('TRJ0120', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0120', 'MN022', 'Cappucino', 'Original Coffee', 10000, 2, 20000),
('TRJ0121', 'MN022', 'Cappucino', 'Original Coffee', 10000, 5, 50000),
('TRJ0122', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 10, 150000),
('TRJ0123', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ0123', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0124', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ0125', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0125', 'MN020', 'Jus Apel', 'Minuman', 11000, 1, 11000),
('TRJ0125', 'MN022', 'Cappucino', 'Original Coffee', 10000, 1, 10000),
('TRJ0126', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ0127', 'MN001', 'Orange Juice', 'Minuman', 10000, 3, 30000),
('TRJ0127', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0131', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 3, 45000),
('TRJ0131', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ0132', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 5, 75000),
('TRJ0132', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0132', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ0132', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0132', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 2, 24000),
('TRJ0133', 'MN018', 'Keripik Kentang', 'Snack', 8000, 4, 32000),
('TRJ0134', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0135', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0136', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0137', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0138', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0139', 'MN022', 'Cappucino', 'Original Coffee', 10000, 1, 10000),
('TRJ0140', 'MN022', 'Cappucino', 'Original Coffee', 10000, 1, 10000),
('TRJ0141', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ0142', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ0143', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 2, 24000),
('TRJ0144', 'MN013', 'Kentang Goreng', 'Snack', 12000, 4, 48000),
('TRJ0145', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ0145', 'MN001', 'Orange Juice', 'Minuman', 10000, 4, 40000),
('TRJ0145', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0146', 'MN023', 'Kopi Susu Dingin', 'Minuman', 3000, 2, 6000),
('TRJ0146', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 3, 36000),
('TRJ0146', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ0147', 'MN016', 'Vanilla Latte', 'Falvoured Coffee', 12000, 4, 48000),
('TRJ0147', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ0148', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 3, 36000),
('TRJ0148', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0149', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0151', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ0151', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ0155', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0150', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 1, 12000),
('TRJ0150', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0150', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ0150', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ0150', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0150', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ0157', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0157', 'MN022', 'Cappucino', 'Original Coffee', 10000, 1, 10000),
('TRJ0156', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0156', 'MN021', 'Moca Latte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0159', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ0160', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0161', 'MN001', 'Orange Juice', 'Minuman', 10000, 4, 40000),
('TRJ0162', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0163', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0164', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0165', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ0166', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0170', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0171', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ0169', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ0172', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ0172', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0172', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ0172', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0167', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ0167', 'MN001', 'Orange Juice', 'Minuman', 10000, 2, 20000),
('TRJ0167', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0154', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0154', 'MN019', 'Jamur Crispy', 'Snack', 10000, 5, 50000),
('TRJ0154', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0154', 'MN013', 'Kentang Goreng', 'Snack', 12000, 3, 36000),
('TRJ0168', 'MN002', 'Coffee Latte', 'Original Coffee', 10000, 1, 10000),
('TRJ0174', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0175', 'MN001', 'Orange Juice', 'Minuman', 10000, 1, 10000),
('TRJ0175', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ0176', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000);

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE `karyawan` (
  `id_karyawan` varchar(6) NOT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `no_telp` varchar(13) NOT NULL,
  `alamat` text NOT NULL,
  `shif` enum('Siang (07:00-17:59)','Malam (18:00-22:59)') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `nama_karyawan`, `no_telp`, `alamat`, `shif`) VALUES
('KY001', 'Achmad Baihaqi', '085655864624', 'Jombang, Jawa Timur, Indonesia', 'Siang (07:00-17:59)'),
('KY002', 'Mohammad Ilham Islamy', '085690123458', 'Nganjuk, Jawa Timur', 'Malam (18:00-22:59)'),
('KY003', 'Widyasari Raisya Salsabilla', '085690239023', 'Mojokerto, Jawa Timur', 'Siang (07:00-17:59)'),
('KY004', 'Septian Yoga Pamungkas', '084590120912', 'Nganjuk, Jawa Timur', 'Malam (18:00-22:59)'),
('KY005', 'Akun Tester', '085655864624', 'Jombang, Jawa Timur', 'Siang (07:00-17:59)');

--
-- Triggers `karyawan`
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
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `id_menu` varchar(5) NOT NULL,
  `nama_menu` varchar(30) NOT NULL,
  `jenis` enum('Minuman','Makanan','Original Coffee','Falvoured Coffee','Snack') NOT NULL,
  `harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu`
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
('MN023', 'Kopi Susu Dingin', 'Minuman', 3000);

--
-- Triggers `menu`
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
-- Table structure for table `transaksi_beli`
--

CREATE TABLE `transaksi_beli` (
  `id_tr_beli` varchar(7) NOT NULL,
  `id_karyawan` varchar(6) DEFAULT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `total_bahan` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `tanggal` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaksi_beli`
--

INSERT INTO `transaksi_beli` (`id_tr_beli`, `id_karyawan`, `nama_karyawan`, `total_bahan`, `total_harga`, `tanggal`) VALUES
('TRB0001', 'KY001', 'Achmad Baihaqi', 1, 25000, '2022-10-29 23:30:42'),
('TRB0002', 'KY001', 'Achmad Baihaqi', 1, 50000, '2022-10-30 23:32:44'),
('TRB0003', 'KY001', 'Achmad Baihaqi', 2, 50000, '2022-10-30 23:33:00'),
('TRB0004', 'KY001', 'Achmad Baihaqi', 5, 15000, '2022-10-31 23:38:03'),
('TRB0005', 'KY001', 'Achmad Baihaqi', 2, 10000, '2022-10-09 12:41:03'),
('TRB0006', 'KY001', 'Achmad Baihaqi', 1, 15000, '2022-10-09 12:42:02'),
('TRB0007', 'KY001', 'Achmad Baihaqi', 9, 95000, '2022-11-05 12:48:33'),
('TRB0008', 'KY001', 'Achmad Baihaqi', 4, 56000, '2022-11-05 12:50:14'),
('TRB0009', 'KY001', 'Achmad Baihaqi', 1, 120000, '2022-11-05 12:50:25'),
('TRB0010', 'KY001', 'Achmad Baihaqi', 3, 155000, '2022-11-05 12:52:13'),
('TRB0011', 'KY001', 'Achmad Baihaqi', 3, 75000, '2022-11-05 12:52:24'),
('TRB0012', 'KY001', 'Achmad Baihaqi', 1, 50000, '2022-11-05 12:52:43'),
('TRB0013', 'KY001', 'Achmad Baihaqi', 3, 150000, '2022-11-13 04:26:14'),
('TRB0014', 'KY001', 'Achmad Baihaqi', 2, 16000, '2022-11-13 04:26:34'),
('TRB0015', 'KY001', 'Achmad Baihaqi', 5, 30000, '2022-11-13 04:26:46'),
('TRB0016', 'KY001', 'Achmad Baihaqi', 2, 50000, '2022-11-13 04:27:06'),
('TRB0017', 'KY001', 'Achmad Baihaqi', 3, 26000, '2022-11-15 05:28:28'),
('TRB0018', 'KY001', 'Achmad Baihaqi', 2, 40000, '2022-11-15 05:29:05'),
('TRB0019', 'KY001', 'Achmad Baihaqi', 5, 125000, '2022-11-17 05:30:02'),
('TRB0020', 'KY001', 'Achmad Baihaqi', 5, 70000, '2022-11-17 05:30:47'),
('TRB0021', 'KY001', 'Achmad Baihaqi', 12, 36000, '2022-11-17 05:31:28'),
('TRB0022', 'KY001', 'Achmad Baihaqi', 8, 64000, '2022-11-21 10:11:11'),
('TRB0023', 'KY001', 'Achmad Baihaqi', 2, 30000, '2022-11-21 10:11:24'),
('TRB0024', 'KY001', 'Achmad Baihaqi', 5, 50000, '2022-11-21 10:11:55'),
('TRB0025', 'KY001', 'Achmad Baihaqi', 9, 200000, '2022-11-21 10:12:21'),
('TRB0026', 'KY001', 'Achmad Baihaqi', 13, 41500, '2022-11-27 03:16:09'),
('TRB0027', 'KY001', 'Achmad Baihaqi', 5, 25000, '2022-12-01 08:17:41'),
('TRB0028', 'KY001', 'Achmad Baihaqi', 8, 202000, '2022-12-01 08:18:14'),
('TRB0029', 'KY001', 'Achmad Baihaqi', 5, 250000, '2022-12-01 08:18:38'),
('TRB0030', 'KY001', 'Achmad Baihaqi', 3, 4500, '2022-12-03 08:19:23'),
('TRB0031', 'KY001', 'Achmad Baihaqi', 2, 140000, '2022-12-03 08:19:35'),
('TRB0032', 'KY001', 'Achmad Baihaqi', 3, 150000, '2022-12-14 03:32:42'),
('TRB0033', 'KY001', 'Achmad Baihaqi', 10, 30000, '2022-12-14 03:32:57'),
('TRB0034', 'KY001', 'Achmad Baihaqi', 5, 250000, '2022-12-14 03:33:24'),
('TRB0035', 'KY001', 'Achmad Baihaqi', 2, 140000, '2022-12-11 03:34:02'),
('TRB0036', 'KY001', 'Achmad Baihaqi', 5, 7500, '2022-12-07 03:34:47'),
('TRB0037', 'KY001', 'Achmad Baihaqi', 3, 210000, '2022-12-07 03:35:30'),
('TRB0038', 'KY004', 'Septian Yoga Pamungkas', 4, 56000, '2022-12-18 12:45:06'),
('TRB0039', 'KY004', 'Septian Yoga Pamungkas', 5, 15000, '2022-12-21 12:45:51'),
('TRB0040', 'KY004', 'Septian Yoga Pamungkas', 3, 75000, '2022-12-21 12:46:15'),
('TRB0041', 'KY001', 'Achmad Baihaqi', 3, 150000, '2022-12-25 12:47:52'),
('TRB0042', 'KY001', 'Achmad Baihaqi', 10, 50000, '2022-12-28 12:49:13'),
('TRB0043', 'KY001', 'Achmad Baihaqi', 1, 70000, '2022-12-31 12:52:23'),
('TRB0044', 'KY004', 'Septian Yoga Pamungkas', 5, 15000, '2023-01-03 06:57:51'),
('TRB0045', 'KY004', 'Septian Yoga Pamungkas', 1, 20000, '2023-01-03 06:58:24'),
('TRB0046', 'KY001', 'Achmad Baihaqi', 2, 18000, '2023-01-05 22:30:01'),
('TRB0047', 'KY001', 'Achmad Baihaqi', 1, 12000, '2023-01-05 22:30:13'),
('TRB0048', 'KY001', 'Achmad Baihaqi', 2, 20000, '2023-01-05 22:31:52'),
('TRB0049', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, '2023-01-05 22:34:27'),
('TRB0050', 'KY004', 'Septian Yoga Pamungkas', 2, 30000, '2023-01-05 22:56:05'),
('TRB0051', 'KY003', 'Widyasari Raisya Salsabilla', 1, 50000, '2023-01-05 23:01:57'),
('TRB0052', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, '2023-01-05 23:05:35'),
('TRB0053', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, '2023-01-05 23:07:29'),
('TRB0054', 'KY003', 'Widyasari Raisya Salsabilla', 4, 80000, '2023-01-05 23:07:59'),
('TRB0055', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, '2023-01-05 23:14:57'),
('TRB0056', 'KY004', 'Septian Yoga Pamungkas', 3, 80000, '2023-01-05 23:15:31'),
('TRB0057', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, '2023-01-05 23:15:40'),
('TRB0058', 'KY004', 'Septian Yoga Pamungkas', 3, 3, '2023-01-05 23:16:38'),
('TRB0059', 'KY004', 'Septian Yoga Pamungkas', 4, 46000, '2023-01-05 23:18:05'),
('TRB0060', 'KY003', 'Widyasari Raisya Salsabilla', 1, 1, '2023-01-05 23:20:01'),
('TRB0061', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, '2023-01-05 23:23:22'),
('TRB0063', 'KY003', 'Widyasari Raisya Salsabilla', 1, 15000, '2023-01-06 00:21:34'),
('TRB0064', 'KY003', 'Widyasari Raisya Salsabilla', 3, 73000, '2023-01-06 00:22:27'),
('TRB0065', 'KY003', 'Widyasari Raisya Salsabilla', 5, 100000, '2023-01-06 00:31:56'),
('TRB0066', 'KY002', 'Mohammad Ilham Islamy', 4, 68000, '2023-01-06 00:32:40'),
('TRB0067', 'KY004', 'Septian Yoga Pamungkas', 1, 12000, '2023-01-06 00:41:18'),
('TRB0068', 'KY002', 'Mohammad Ilham Islamy', 2, 16001, '2023-01-06 00:43:30'),
('TRB0069', 'KY003', 'Widyasari Raisya Salsabilla', 1, 1000, '2023-01-06 15:34:06'),
('TRB0070', 'KY003', 'Widyasari Raisya Salsabilla', 1, 8000, '2023-01-06 15:38:50'),
('TRB0071', 'KY004', 'Septian Yoga Pamungkas', 1, 12000, '2023-01-06 20:04:29'),
('TRB0072', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, '2023-01-06 20:05:03'),
('TRB0073', 'KY002', 'Mohammad Ilham Islamy', 1, 12000, '2023-01-06 20:09:29'),
('TRB0074', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, '2023-01-06 20:21:48'),
('TRB0075', 'KY002', 'Mohammad Ilham Islamy', 5, 50000, '2023-01-06 22:10:15'),
('TRB0076', 'KY003', 'Widyasari Raisya Salsabilla', 1, 15000, '2023-01-06 22:13:15'),
('TRB0077', 'KY001', 'Achmad Baihaqi', 2, 32000, '2023-01-07 13:04:40'),
('TRB0078', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, '2023-01-07 13:05:24'),
('TRB0079', 'KY003', 'Widyasari Raisya Salsabilla', 1, 8000, '2023-01-07 17:33:13'),
('TRB0080', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, '2023-01-07 17:36:01'),
('TRB0081', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, '2023-01-07 20:16:19'),
('TRB0082', 'KY002', 'Mohammad Ilham Islamy', 4, 67000, '2023-01-07 20:17:01'),
('TRB0083', 'KY002', 'Mohammad Ilham Islamy', 1, 14000, '2023-01-07 20:17:14'),
('TRB0084', 'KY004', 'Septian Yoga Pamungkas', 1, 16000, '2023-01-07 20:19:32'),
('TRB0085', 'KY002', 'Mohammad Ilham Islamy', 5, 15000, '2023-01-07 22:49:40'),
('TRB0086', 'KY002', 'Mohammad Ilham Islamy', 13, 108000, '2023-01-07 22:53:53'),
('TRB0087', 'KY003', 'Widyasari Raisya Salsabilla', 2, 15000, '2023-01-07 22:54:53'),
('TRB0088', 'KY003', 'Widyasari Raisya Salsabilla', 2, 50000, '2023-01-07 22:55:39'),
('TRB0089', 'KY004', 'Septian Yoga Pamungkas', 2, 30000, '2023-01-07 22:58:00'),
('TRB0090', 'KY001', 'Achmad Baihaqi', 6, 300000, '2023-01-07 23:44:27'),
('TRB0091', 'KY003', 'Widyasari Raisya Salsabilla', 5, 147000, '2023-01-07 23:51:58'),
('TRB0092', 'KY002', 'Mohammad Ilham Islamy', 4, 22000, '2023-01-08 00:08:49'),
('TRB0093', 'KY001', 'Achmad Baihaqi', 1, 2000, '2023-01-08 00:12:41'),
('TRB0094', 'KY001', 'Achmad Baihaqi', 7, 59000, '2023-01-08 00:15:07'),
('TRB0095', 'KY003', 'Widyasari Raisya Salsabilla', 2, 33000, '2023-01-08 00:16:43'),
('TRB0096', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, '2023-01-08 00:20:25'),
('TRB0097', 'KY001', 'Achmad Baihaqi', 1, 12000, '2023-01-08 00:21:54'),
('TRB0098', 'KY001', 'Achmad Baihaqi', 1, 12000, '2023-01-08 00:24:28'),
('TRB0099', 'KY001', 'Achmad Baihaqi', 1, 10000, '2023-01-08 00:25:31'),
('TRB0100', 'KY003', 'Widyasari Raisya Salsabilla', 1, 6000, '2023-01-08 00:26:19'),
('TRB0101', 'KY003', 'Widyasari Raisya Salsabilla', 1, 6000, '2023-01-08 00:26:46'),
('TRB0102', 'KY002', 'Mohammad Ilham Islamy', 1, 12000, '2023-01-08 00:29:37'),
('TRB0103', 'KY004', 'Septian Yoga Pamungkas', 1, 12000, '2023-01-08 00:31:37'),
('TRB0104', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, '2023-01-08 13:35:21'),
('TRB0105', 'KY003', 'Widyasari Raisya Salsabilla', 1, 14000, '2023-01-08 13:52:33'),
('TRB0106', 'KY003', 'Widyasari Raisya Salsabilla', 7, 75000, '2023-01-08 14:30:26'),
('TRB0107', 'KY002', 'Mohammad Ilham Islamy', 2, 20000, '2023-01-08 14:40:10'),
('TRB0108', 'KY001', 'Achmad Baihaqi', 1, 12000, '2023-01-08 14:41:51'),
('TRB0109', 'KY001', 'Achmad Baihaqi', 1, 12000, '2023-01-08 14:46:32'),
('TRB0110', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, '2023-01-08 14:54:48'),
('TRB0111', 'KY001', 'Achmad Baihaqi', 1, 14000, '2023-01-08 15:01:31'),
('TRB0112', 'KY001', 'Achmad Baihaqi', 1, 15000, '2023-01-08 15:01:50'),
('TRB0113', 'KY001', 'Achmad Baihaqi', 2, 32000, '2023-01-08 15:40:10'),
('TRB0114', 'KY001', 'Achmad Baihaqi', 1, 15000, '2023-01-08 15:41:03'),
('TRB0115', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, '2023-01-08 16:08:41'),
('TRB0116', 'KY003', 'Widyasari Raisya Salsabilla', 1, 500, '2023-01-08 17:08:20'),
('TRB0117', 'KY002', 'Mohammad Ilham Islamy', 1, 13000, '2023-01-08 17:17:41'),
('TRB0118', 'KY001', 'Achmad Baihaqi', 1, 12000, '2023-01-08 17:45:06'),
('TRB0119', 'KY003', 'Widyasari Raisya Salsabilla', 1, 8000, '2023-01-08 17:54:31'),
('TRB0120', 'KY001', 'Achmad Baihaqi', 1, 8000, '2023-01-08 19:43:53'),
('TRB0121', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, '2023-01-08 20:03:30'),
('TRB0122', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, '2023-01-08 21:10:37'),
('TRB0123', 'KY001', 'Achmad Baihaqi', 2, 42000, '2023-01-09 14:44:18'),
('TRB0124', 'KY002', 'Mohammad Ilham Islamy', 5, 100000, '2023-01-09 15:17:55'),
('TRB0125', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, '2023-01-09 15:12:22'),
('TRB0126', 'KY001', 'Achmad Baihaqi', 3, 40000, '2023-01-09 15:18:31'),
('TRB0127', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, '2023-01-09 15:19:08'),
('TRB0128', 'KY002', 'Mohammad Ilham Islamy', 4, 50000, '2023-01-09 15:28:59'),
('TRB0129', 'KY004', 'Septian Yoga Pamungkas', 3, 33000, '2023-01-09 15:20:46'),
('TRB0130', 'KY003', 'Widyasari Raisya Salsabilla', 1, 16000, '2023-01-09 15:25:57'),
('TRB0131', 'KY003', 'Widyasari Raisya Salsabilla', 1, 15000, '2023-01-10 11:42:36');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_jual`
--

CREATE TABLE `transaksi_jual` (
  `id_tr_jual` varchar(7) NOT NULL,
  `id_karyawan` varchar(6) DEFAULT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `total_menu` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `total_bayar` int(11) NOT NULL,
  `total_kembalian` int(11) NOT NULL,
  `tanggal` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaksi_jual`
--

INSERT INTO `transaksi_jual` (`id_tr_jual`, `id_karyawan`, `nama_karyawan`, `total_menu`, `total_harga`, `total_bayar`, `total_kembalian`, `tanggal`) VALUES
('TRJ0001', 'KY001', 'Achmad Baihaqi', 3, 35000, 35000, 0, '2022-10-28 23:28:00'),
('TRJ0002', 'KY001', 'Achmad Baihaqi', 1, 12000, 12000, 0, '2022-10-28 23:28:29'),
('TRJ0003', 'KY001', 'Achmad Baihaqi', 2, 30000, 30000, 0, '2022-10-29 23:28:57'),
('TRJ0004', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2022-10-29 23:29:21'),
('TRJ0005', 'KY001', 'Achmad Baihaqi', 9, 116000, 116000, 0, '2022-10-30 23:31:51'),
('TRJ0006', 'KY001', 'Achmad Baihaqi', 1, 3000, 3000, 0, '2022-10-30 23:32:21'),
('TRJ0007', 'KY001', 'Achmad Baihaqi', 6, 90000, 90000, 0, '2022-10-30 23:33:23'),
('TRJ0008', 'KY001', 'Achmad Baihaqi', 2, 24000, 24000, 0, '2022-10-30 23:33:55'),
('TRJ0009', 'KY001', 'Achmad Baihaqi', 2, 20000, 20000, 0, '2022-10-30 23:34:14'),
('TRJ0010', 'KY001', 'Achmad Baihaqi', 6, 75000, 75000, 0, '2022-10-31 23:35:45'),
('TRJ0011', 'KY001', 'Achmad Baihaqi', 6, 80000, 80000, 0, '2022-10-31 23:36:41'),
('TRJ0012', 'KY001', 'Achmad Baihaqi', 1, 15000, 15000, 0, '2022-10-07 12:44:16'),
('TRJ0013', 'KY001', 'Achmad Baihaqi', 2, 22000, 22000, 0, '2022-10-07 12:44:39'),
('TRJ0014', 'KY001', 'Achmad Baihaqi', 5, 48000, 48000, 0, '2022-10-07 02:45:18'),
('TRJ0015', 'KY001', 'Achmad Baihaqi', 8, 108000, 108000, 0, '2022-11-08 08:46:36'),
('TRJ0016', 'KY001', 'Achmad Baihaqi', 8, 88000, 88000, 0, '2022-11-09 12:47:40'),
('TRJ0017', 'KY001', 'Achmad Baihaqi', 8, 102000, 102000, 0, '2022-11-11 22:01:57'),
('TRJ0018', 'KY001', 'Achmad Baihaqi', 8, 94000, 94000, 0, '2022-11-11 22:02:54'),
('TRJ0019', 'KY001', 'Achmad Baihaqi', 13, 135000, 135000, 0, '2022-11-11 22:03:44'),
('TRJ0020', 'KY001', 'Achmad Baihaqi', 8, 99000, 99000, 0, '2022-11-13 04:23:46'),
('TRJ0021', 'KY001', 'Achmad Baihaqi', 3, 36000, 36000, 0, '2022-11-13 04:24:08'),
('TRJ0022', 'KY001', 'Achmad Baihaqi', 5, 56000, 56000, 0, '2022-11-13 04:24:19'),
('TRJ0023', 'KY001', 'Achmad Baihaqi', 4, 44000, 44000, 0, '2022-11-15 05:34:32'),
('TRJ0024', 'KY001', 'Achmad Baihaqi', 12, 119000, 119000, 0, '2022-11-15 05:34:59'),
('TRJ0025', 'KY001', 'Achmad Baihaqi', 15, 208000, 208000, 0, '2022-11-16 11:35:48'),
('TRJ0026', 'KY001', 'Achmad Baihaqi', 15, 130000, 130000, 0, '2022-11-17 09:05:32'),
('TRJ0027', 'KY001', 'Achmad Baihaqi', 22, 262000, 262000, 0, '2022-11-17 09:06:12'),
('TRJ0028', 'KY002', 'Mohammad Ilham Islamy', 5, 48000, 48000, 0, '2022-11-17 09:06:53'),
('TRJ0029', 'KY002', 'Mohammad Ilham Islamy', 18, 221000, 221000, 0, '2022-11-19 10:08:00'),
('TRJ0030', 'KY002', 'Mohammad Ilham Islamy', 6, 69000, 69000, 0, '2022-11-19 10:09:24'),
('TRJ0031', 'KY001', 'Achmad Baihaqi', 4, 60000, 60000, 0, '2022-11-23 10:12:53'),
('TRJ0032', 'KY001', 'Achmad Baihaqi', 9, 108000, 108000, 0, '2022-11-23 10:13:08'),
('TRJ0033', 'KY001', 'Achmad Baihaqi', 3, 36000, 36000, 0, '2022-11-25 10:13:32'),
('TRJ0034', 'KY001', 'Achmad Baihaqi', 5, 48000, 48000, 0, '2022-11-27 03:14:11'),
('TRJ0035', 'KY001', 'Achmad Baihaqi', 10, 120000, 120000, 0, '2022-11-29 03:14:48'),
('TRJ0036', 'KY001', 'Achmad Baihaqi', 8, 88000, 88000, 0, '2022-12-01 08:21:00'),
('TRJ0037', 'KY001', 'Achmad Baihaqi', 6, 72000, 72000, 0, '2022-12-02 23:21:42'),
('TRJ0038', 'KY001', 'Achmad Baihaqi', 5, 69000, 69000, 0, '2022-12-03 02:22:13'),
('TRJ0039', 'KY001', 'Achmad Baihaqi', 1, 12000, 12000, 0, '2022-12-03 02:22:27'),
('TRJ0040', 'KY001', 'Achmad Baihaqi', 3, 40000, 40000, 0, '2022-12-03 02:22:38'),
('TRJ0041', 'KY001', 'Achmad Baihaqi', 3, 25000, 25000, 0, '2022-12-03 02:22:55'),
('TRJ0042', 'KY001', 'Achmad Baihaqi', 2, 24000, 24000, 0, '2022-12-05 12:23:33'),
('TRJ0043', 'KY001', 'Achmad Baihaqi', 5, 52000, 52000, 0, '2022-12-05 12:23:49'),
('TRJ0044', 'KY001', 'Achmad Baihaqi', 2, 24000, 24000, 0, '2022-12-05 12:24:12'),
('TRJ0045', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2022-12-05 12:24:18'),
('TRJ0046', 'KY001', 'Achmad Baihaqi', 1, 15000, 15000, 0, '2022-12-05 12:24:25'),
('TRJ0047', 'KY001', 'Achmad Baihaqi', 4, 60000, 60000, 0, '2022-12-07 12:24:46'),
('TRJ0048', 'KY001', 'Achmad Baihaqi', 7, 74000, 74000, 0, '2022-12-09 12:25:50'),
('TRJ0049', 'KY001', 'Achmad Baihaqi', 8, 95000, 95000, 0, '2022-12-09 12:26:33'),
('TRJ0050', 'KY001', 'Achmad Baihaqi', 3, 41000, 41000, 0, '2022-12-09 12:26:52'),
('TRJ0051', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2022-12-09 12:26:58'),
('TRJ0052', 'KY001', 'Achmad Baihaqi', 9, 114000, 114000, 0, '2022-12-09 12:27:19'),
('TRJ0053', 'KY001', 'Achmad Baihaqi', 14, 192000, 192000, 0, '2022-12-10 12:28:19'),
('TRJ0054', 'KY001', 'Achmad Baihaqi', 14, 160000, 160000, 0, '2022-12-11 03:28:54'),
('TRJ0055', 'KY001', 'Achmad Baihaqi', 13, 168000, 168000, 0, '2022-12-12 03:29:26'),
('TRJ0056', 'KY001', 'Achmad Baihaqi', 27, 324000, 324000, 0, '2022-12-13 03:30:10'),
('TRJ0057', 'KY001', 'Achmad Baihaqi', 4, 48000, 48000, 0, '2022-12-14 03:31:10'),
('TRJ0058', 'KY001', 'Achmad Baihaqi', 5, 60000, 60000, 0, '2022-12-15 03:36:11'),
('TRJ0059', 'KY001', 'Achmad Baihaqi', 5, 50000, 50000, 0, '2022-12-15 03:36:35'),
('TRJ0060', 'KY001', 'Achmad Baihaqi', 8, 86000, 86000, 0, '2022-12-16 03:37:19'),
('TRJ0061', 'KY001', 'Achmad Baihaqi', 3, 45000, 45000, 0, '2022-12-17 03:37:43'),
('TRJ0062', 'KY001', 'Achmad Baihaqi', 3, 36000, 36000, 0, '2022-12-17 03:37:56'),
('TRJ0063', 'KY001', 'Achmad Baihaqi', 9, 55000, 55000, 0, '2022-12-17 03:38:23'),
('TRJ0064', 'KY001', 'Achmad Baihaqi', 2, 6000, 6000, 0, '2022-12-17 03:38:41'),
('TRJ0065', 'KY001', 'Achmad Baihaqi', 6, 68000, 68000, 0, '2022-12-17 03:38:53'),
('TRJ0066', 'KY001', 'Achmad Baihaqi', 3, 33000, 33000, 0, '2022-12-19 12:39:23'),
('TRJ0067', 'KY001', 'Achmad Baihaqi', 5, 60000, 60000, 0, '2022-12-19 12:39:42'),
('TRJ0068', 'KY002', 'Mohammad Ilham Islamy', 10, 106000, 106000, 0, '2022-12-19 12:40:31'),
('TRJ0069', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2022-12-19 12:40:44'),
('TRJ0070', 'KY002', 'Mohammad Ilham Islamy', 5, 60000, 60000, 0, '2022-12-19 12:40:52'),
('TRJ0071', 'KY001', 'Achmad Baihaqi', 3, 36000, 36000, 0, '2022-12-21 12:41:25'),
('TRJ0072', 'KY003', 'Widyasari Raisya Salsabilla', 6, 48000, 48000, 0, '2022-12-21 12:41:51'),
('TRJ0073', 'KY003', 'Widyasari Raisya Salsabilla', 8, 96000, 96000, 0, '2022-12-21 12:42:09'),
('TRJ0074', 'KY004', 'Septian Yoga Pamungkas', 2, 24000, 24000, 0, '2022-12-21 12:42:41'),
('TRJ0075', 'KY004', 'Septian Yoga Pamungkas', 4, 48000, 48000, 0, '2022-12-23 12:43:04'),
('TRJ0076', 'KY004', 'Septian Yoga Pamungkas', 5, 50000, 50000, 0, '2022-12-23 12:43:15'),
('TRJ0077', 'KY004', 'Septian Yoga Pamungkas', 2, 30000, 30000, 0, '2022-12-23 12:43:23'),
('TRJ0078', 'KY004', 'Septian Yoga Pamungkas', 8, 120000, 120000, 0, '2022-12-25 12:43:46'),
('TRJ0079', 'KY004', 'Septian Yoga Pamungkas', 2, 30000, 30000, 0, '2022-12-18 12:44:48'),
('TRJ0080', 'KY001', 'Achmad Baihaqi', 4, 40000, 40000, 0, '2022-12-28 12:49:48'),
('TRJ0081', 'KY001', 'Achmad Baihaqi', 4, 45000, 45000, 0, '2022-12-28 12:50:12'),
('TRJ0082', 'KY001', 'Achmad Baihaqi', 1, 13000, 13000, 0, '2022-12-29 12:50:37'),
('TRJ0083', 'KY001', 'Achmad Baihaqi', 4, 40000, 40000, 0, '2022-12-29 12:50:53'),
('TRJ0084', 'KY001', 'Achmad Baihaqi', 5, 60000, 60000, 0, '2022-12-31 12:51:14'),
('TRJ0085', 'KY001', 'Achmad Baihaqi', 1, 12000, 12000, 0, '2023-01-01 01:53:40'),
('TRJ0086', 'KY001', 'Achmad Baihaqi', 2, 25000, 25000, 0, '2023-01-01 01:54:02'),
('TRJ0087', 'KY002', 'Mohammad Ilham Islamy', 2, 20000, 20000, 0, '2023-01-01 01:54:53'),
('TRJ0088', 'KY003', 'Widyasari Raisya Salsabilla', 3, 24000, 24000, 0, '2023-01-02 02:55:28'),
('TRJ0089', 'KY003', 'Widyasari Raisya Salsabilla', 4, 48000, 48000, 0, '2023-01-02 02:56:02'),
('TRJ0090', 'KY004', 'Septian Yoga Pamungkas', 4, 40000, 40000, 0, '2023-01-02 02:56:37'),
('TRJ0091', 'KY004', 'Septian Yoga Pamungkas', 2, 22000, 22000, 0, '2023-01-03 06:57:11'),
('TRJ0092', 'KY001', 'Achmad Baihaqi', 1, 12000, 12000, 0, '2023-01-04 17:00:15'),
('TRJ0093', 'KY001', 'Achmad Baihaqi', 2, 25000, 25000, 0, '2023-01-04 17:17:24'),
('TRJ0094', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2023-01-04 20:37:04'),
('TRJ0095', 'KY001', 'Achmad Baihaqi', 2, 20000, 20000, 0, '2023-01-04 20:40:42'),
('TRJ0096', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2023-01-04 21:59:30'),
('TRJ0097', 'KY001', 'Achmad Baihaqi', 2, 24000, 24000, 0, '2023-01-04 22:04:57'),
('TRJ0098', 'KY001', 'Achmad Baihaqi', 1, 15000, 15000, 0, '2023-01-04 22:07:18'),
('TRJ0099', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2023-01-04 22:21:39'),
('TRJ0100', 'KY002', 'Mohammad Ilham Islamy', 2, 20000, 20000, 0, '2023-01-04 22:21:46'),
('TRJ0101', 'KY003', 'Widyasari Raisya Salsabilla', 1, 13000, 13000, 0, '2023-01-04 22:21:55'),
('TRJ0102', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, 15000, 0, '2023-01-04 22:22:04'),
('TRJ0103', 'KY002', 'Mohammad Ilham Islamy', 1, 12000, 12000, 0, '2023-01-04 22:24:29'),
('TRJ0104', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, 12000, 0, '2023-01-04 22:26:07'),
('TRJ0105', 'KY003', 'Widyasari Raisya Salsabilla', 1, 15000, 15000, 0, '2023-01-04 22:27:18'),
('TRJ0106', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2023-01-04 23:24:40'),
('TRJ0107', 'KY001', 'Achmad Baihaqi', 4, 40000, 40000, 0, '2023-01-04 23:35:01'),
('TRJ0108', 'KY002', 'Mohammad Ilham Islamy', 12, 136000, 136000, 0, '2023-01-04 23:53:23'),
('TRJ0109', 'KY002', 'Mohammad Ilham Islamy', 8, 80000, 80000, 0, '2023-01-04 23:53:46'),
('TRJ0110', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, 15000, 0, '2023-01-04 23:55:34'),
('TRJ0111', 'KY002', 'Mohammad Ilham Islamy', 2, 28000, 28000, 0, '2023-01-05 00:01:02'),
('TRJ0112', 'KY002', 'Mohammad Ilham Islamy', 1, 13000, 13000, 0, '2023-01-05 00:01:09'),
('TRJ0113', 'KY002', 'Mohammad Ilham Islamy', 1, 3000, 3000, 0, '2023-01-05 00:01:15'),
('TRJ0114', 'KY001', 'Achmad Baihaqi', 1, 15000, 15000, 0, '2023-01-05 00:01:23'),
('TRJ0115', 'KY001', 'Achmad Baihaqi', 1, 15000, 15000, 0, '2023-01-05 00:06:37'),
('TRJ0116', 'KY003', 'Widyasari Raisya Salsabilla', 9, 90000, 90000, 0, '2023-01-05 00:08:41'),
('TRJ0117', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, 12000, 0, '2023-01-05 00:08:50'),
('TRJ0118', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, 12000, 0, '2023-01-05 00:09:05'),
('TRJ0119', 'KY003', 'Widyasari Raisya Salsabilla', 13, 135000, 135000, 0, '2023-01-05 00:57:48'),
('TRJ0120', 'KY001', 'Achmad Baihaqi', 10, 122000, 122000, 0, '2023-01-05 11:17:19'),
('TRJ0121', 'KY001', 'Achmad Baihaqi', 5, 50000, 50000, 0, '2023-01-05 11:19:58'),
('TRJ0122', 'KY001', 'Achmad Baihaqi', 10, 150000, 150000, 0, '2023-01-05 11:22:23'),
('TRJ0123', 'KY002', 'Mohammad Ilham Islamy', 2, 27000, 27000, 0, '2023-01-05 11:43:25'),
('TRJ0124', 'KY002', 'Mohammad Ilham Islamy', 1, 12000, 12000, 0, '2023-01-05 22:32:02'),
('TRJ0125', 'KY002', 'Mohammad Ilham Islamy', 3, 36000, 36000, 0, '2023-01-06 15:15:30'),
('TRJ0126', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2023-01-06 15:35:31'),
('TRJ0127', 'KY003', 'Widyasari Raisya Salsabilla', 4, 42000, 42000, 0, '2023-01-06 15:38:34'),
('TRJ0128', 'KY002', 'Mohammad Ilham Islamy', 4, 38000, 38000, 0, '2023-01-06 17:47:00'),
('TRJ0129', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, 10000, 0, '2023-01-06 17:59:56'),
('TRJ0130', 'KY002', 'Mohammad Ilham Islamy', 1, 12000, 12000, 0, '2023-01-06 18:01:05'),
('TRJ0131', 'KY003', 'Widyasari Raisya Salsabilla', 4, 57000, 57000, 0, '2023-01-06 19:35:27'),
('TRJ0132', 'KY004', 'Septian Yoga Pamungkas', 10, 131000, 131000, 0, '2023-01-06 22:12:29'),
('TRJ0133', 'KY003', 'Widyasari Raisya Salsabilla', 4, 32000, 32000, 0, '2023-01-06 22:16:07'),
('TRJ0134', 'KY004', 'Septian Yoga Pamungkas', 1, 12000, 12000, 0, '2023-01-07 12:55:21'),
('TRJ0135', 'KY002', 'Mohammad Ilham Islamy', 1, 12000, 12000, 0, '2023-01-07 12:56:06'),
('TRJ0136', 'KY001', 'Achmad Baihaqi', 2, 24000, 24000, 0, '2023-01-07 12:56:55'),
('TRJ0137', 'KY002', 'Mohammad Ilham Islamy', 1, 12000, 12000, 0, '2023-01-07 12:57:47'),
('TRJ0138', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, 12000, 0, '2023-01-07 12:58:20'),
('TRJ0139', 'KY004', 'Septian Yoga Pamungkas', 1, 10000, 10000, 0, '2023-01-07 13:00:55'),
('TRJ0140', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-07 13:01:39'),
('TRJ0141', 'KY001', 'Achmad Baihaqi', 2, 16000, 16000, 0, '2023-01-07 17:05:23'),
('TRJ0142', 'KY002', 'Mohammad Ilham Islamy', 2, 16000, 16000, 0, '2023-01-07 17:06:37'),
('TRJ0143', 'KY001', 'Achmad Baihaqi', 2, 24000, 24000, 0, '2023-01-08 15:36:24'),
('TRJ0144', 'KY003', 'Widyasari Raisya Salsabilla', 4, 48000, 48000, 0, '2023-01-08 16:07:49'),
('TRJ0145', 'KY001', 'Achmad Baihaqi', 7, 77000, 77000, 0, '2023-01-08 19:45:46'),
('TRJ0146', 'KY004', 'Septian Yoga Pamungkas', 6, 52000, 52000, 0, '2023-01-08 19:46:18'),
('TRJ0147', 'KY002', 'Mohammad Ilham Islamy', 6, 78000, 78000, 0, '2023-01-08 19:46:47'),
('TRJ0148', 'KY003', 'Widyasari Raisya Salsabilla', 5, 66000, 66000, 0, '2023-01-08 19:47:10'),
('TRJ0149', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, 15000, 0, '2023-01-08 21:10:22'),
('TRJ0150', 'KY001', 'Achmad Baihaqi', 7, 92000, 92000, 0, '2023-01-09 14:36:16'),
('TRJ0151', 'KY002', 'Mohammad Ilham Islamy', 2, 23000, 23000, 0, '2023-01-09 00:07:16'),
('TRJ0152', 'KY004', 'Septian Yoga Pamungkas', 4, 50000, 50000, 0, '2023-01-09 14:03:18'),
('TRJ0153', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, 15000, 0, '2023-01-09 14:28:47'),
('TRJ0154', 'KY004', 'Septian Yoga Pamungkas', 10, 116000, 116000, 0, '2023-01-09 23:02:40'),
('TRJ0155', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-09 14:31:07'),
('TRJ0156', 'KY003', 'Widyasari Raisya Salsabilla', 3, 34000, 34000, 0, '2023-01-09 14:41:29'),
('TRJ0157', 'KY002', 'Mohammad Ilham Islamy', 3, 34000, 34000, 0, '2023-01-09 14:40:59'),
('TRJ0159', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, 10000, 0, '2023-01-09 14:42:16'),
('TRJ0160', 'KY001', 'Achmad Baihaqi', 1, 10000, 10000, 0, '2023-01-09 17:45:07'),
('TRJ0161', 'KY003', 'Widyasari Raisya Salsabilla', 4, 40000, 40000, 0, '2023-01-09 17:57:38'),
('TRJ0162', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-09 17:58:21'),
('TRJ0163', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-09 17:58:27'),
('TRJ0164', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-09 17:58:33'),
('TRJ0165', 'KY003', 'Widyasari Raisya Salsabilla', 1, 12000, 12000, 0, '2023-01-09 18:12:26'),
('TRJ0166', 'KY003', 'Widyasari Raisya Salsabilla', 1, 15000, 15000, 0, '2023-01-09 21:38:50'),
('TRJ0167', 'KY002', 'Mohammad Ilham Islamy', 5, 54000, 60000, 6000, '2023-01-09 22:12:16'),
('TRJ0168', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, 10000, 0, '2023-01-09 23:50:57'),
('TRJ0169', 'KY002', 'Mohammad Ilham Islamy', 1, 10000, 10000, 0, '2023-01-09 22:08:16'),
('TRJ0170', 'KY004', 'Septian Yoga Pamungkas', 1, 15000, 20000, 5000, '2023-01-09 21:54:17'),
('TRJ0171', 'KY004', 'Septian Yoga Pamungkas', 1, 10000, 10000, 0, '2023-01-09 21:54:27'),
('TRJ0172', 'KY001', 'Achmad Baihaqi', 5, 54000, 54000, 0, '2023-01-09 22:10:38'),
('TRJ0173', 'KY003', 'Widyasari Raisya Salsabilla', 1, 10000, 20000, 10000, '2023-01-09 22:11:59'),
('TRJ0174', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, 20000, 5000, '2023-01-09 23:57:57'),
('TRJ0175', 'KY004', 'Septian Yoga Pamungkas', 3, 26000, 30000, 4000, '2023-01-10 00:02:55'),
('TRJ0176', 'KY002', 'Mohammad Ilham Islamy', 1, 15000, 20000, 5000, '2023-01-10 11:53:13');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_login` int(3) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(70) NOT NULL,
  `level` enum('ADMIN','KARYAWAN') NOT NULL,
  `id_karyawan` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_login`, `username`, `password`, `level`, `id_karyawan`) VALUES
(1, 'ADMIN', '$2a$12$R3PIb2.OWpNX1l4vVHuojuOl8HAfda5LqGYJdup3KrR0x0XfPe41G', 'ADMIN', 'KY001'),
(2, 'ilham', '$2a$12$G9rldUjX0SjYYn169mMO3uLoRJOnlYKggCG5nojWOeOYSFxRnckNm', 'KARYAWAN', 'KY002'),
(3, 'widya', '$2a$12$a2hZcCF4Of0r65cL1ef4/.GVnaCWf6/S0Z//MGtJLnSrpZaiJ7saK', 'KARYAWAN', 'KY003'),
(4, 'yoga', '$2a$12$a.WxR/w.Jd8MZ9KYWbcazuOwx9Y936X/QfhpQJwNT.Qe9ASei3PXu', 'KARYAWAN', 'KY004'),
(7, 'tester', '$2a$12$vo3Su5mFytzQklgnhtwbJ.sBJoUH3ebH84TugA959Ecwl8Vso5KuW', 'ADMIN', 'KY005');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bahan`
--
ALTER TABLE `bahan`
  ADD PRIMARY KEY (`id_bahan`);

--
-- Indexes for table `detail_tr_beli`
--
ALTER TABLE `detail_tr_beli`
  ADD KEY `id_tr_beli` (`id_tr_beli`),
  ADD KEY `id_bahan` (`id_bahan`);

--
-- Indexes for table `detail_tr_jual`
--
ALTER TABLE `detail_tr_jual`
  ADD KEY `id_transaksi` (`id_tr_jual`),
  ADD KEY `id_menu` (`id_menu`);

--
-- Indexes for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`id_karyawan`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id_menu`);

--
-- Indexes for table `transaksi_beli`
--
ALTER TABLE `transaksi_beli`
  ADD PRIMARY KEY (`id_tr_beli`),
  ADD KEY `id_karyawan` (`id_karyawan`);

--
-- Indexes for table `transaksi_jual`
--
ALTER TABLE `transaksi_jual`
  ADD PRIMARY KEY (`id_tr_jual`),
  ADD KEY `id_karyawan` (`id_karyawan`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_login`),
  ADD KEY `id_karyawan` (`id_karyawan`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_login` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_tr_beli`
--
ALTER TABLE `detail_tr_beli`
  ADD CONSTRAINT `detail_tr_beli_ibfk_1` FOREIGN KEY (`id_tr_beli`) REFERENCES `transaksi_beli` (`id_tr_beli`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_tr_beli_ibfk_2` FOREIGN KEY (`id_bahan`) REFERENCES `bahan` (`id_bahan`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `detail_tr_jual`
--
ALTER TABLE `detail_tr_jual`
  ADD CONSTRAINT `detail_tr_jual_ibfk_1` FOREIGN KEY (`id_tr_jual`) REFERENCES `transaksi_jual` (`id_tr_jual`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_tr_jual_ibfk_2` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id_menu`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `transaksi_beli`
--
ALTER TABLE `transaksi_beli`
  ADD CONSTRAINT `transaksi_beli_ibfk_1` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `transaksi_jual`
--
ALTER TABLE `transaksi_jual`
  ADD CONSTRAINT `transaksi_jual_ibfk_2` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
