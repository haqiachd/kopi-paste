-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 23, 2022 at 12:16 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.29

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
-- Table structure for table `bahan`
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
-- Dumping data for table `bahan`
--

INSERT INTO `bahan` (`id_bahan`, `nama_bahan`, `jenis`, `stok`, `satuan`, `harga`) VALUES
('BA000', 'Coklat', 'Perasa', 15485, 'gr', 57500),
('BA001', 'Susu Coklat', 'Cairan', 30350, 'ml', 25000),
('BA002', 'Kentang', 'Nabati', 4800, 'gr', 50000),
('BA003', 'Jeruk', 'Nabati', 5000, 'gr', 25000),
('BA005', 'Biji Kopi', 'Coffee', 37469, 'gr', 120000),
('BA007', 'Jeruk Bali', 'Nabati', 10700, 'gr', 13500),
('BA008', 'Kopi Hitam', 'Coffee', 10400, 'gr', 122250),
('BA011', 'Kacang', 'Nabati', 3449, 'gr', 5000),
('BA015', 'Apel', 'Nabati', 8500, 'gr', 5000),
('BA018', 'Kecap', 'Perasa', 5230, 'ml', 1500),
('BA019', 'Cabe Rawit', 'Nabati', 12740, 'gr', 5000),
('BA020', 'Bawang Merah', 'Nabati', 5000, 'gr', 25000),
('BA021', 'Bawang Putih', 'Nabati', 8000, 'gr', 10000),
('BA022', 'Daging Ayam', 'Hewani', 20250, 'gr', 20000),
('BA025', 'Air Mineral', 'Cairan', 15000, 'ml', 3000),
('BA026', 'Gula Pasir', 'Perasa', 3125, 'gr', 14000),
('BA027', 'Anggur', 'Nabati', 9250, 'gr', 20000),
('BA028', 'Nasi', 'Nabati', 6350, 'gr', 8000),
('BA029', 'Jamur', 'Nabati', 11000, 'gr', 15000),
('BA030', 'Garam', 'Perasa', 11840, 'gr', 6000),
('BA032', 'Daging Babi', 'Hewani', 17400, 'gr', 150000);

-- --------------------------------------------------------

--
-- Table structure for table `detail_menu`
--

CREATE TABLE `detail_menu` (
  `id_menu` varchar(5) DEFAULT NULL,
  `id_bahan` varchar(6) DEFAULT NULL,
  `quantity` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detail_menu`
--

INSERT INTO `detail_menu` (`id_menu`, `id_bahan`, `quantity`) VALUES
('MN001', 'BA003', 200),
('MN001', 'BA025', 250),
('MN001', 'BA026', 20),
('MN002', 'BA007', 100),
('MN011', 'BA000', 20),
('MN011', 'BA005', 10),
('MN011', 'BA025', 200),
('MN011', 'BA026', 50),
('MN012', 'BA011', 50),
('MN012', 'BA018', 10),
('MN012', 'BA019', 60),
('MN012', 'BA022', 250),
('MN013', 'BA002', 150),
('MN013', 'BA019', 80),
('MN013', 'BA026', 10),
('MN014', 'BA005', 50),
('MN014', 'BA025', 300),
('MN014', 'BA026', 20),
('MN015', 'BA025', 300),
('MN015', 'BA026', 100),
('MN015', 'BA027', 150),
('MN016', 'BA001', 50),
('MN016', 'BA005', 20),
('MN016', 'BA025', 250),
('MN016', 'BA026', 30),
('MN017', 'BA018', 20),
('MN017', 'BA019', 10),
('MN017', 'BA028', 150),
('MN018', 'BA002', 100),
('MN018', 'BA019', 50),
('MN018', 'BA026', 15),
('MN018', 'BA030', 10),
('MN019', 'BA002', 150),
('MN019', 'BA030', 50),
('MN022', 'BA032', 100),
('MN021', 'BA032', 200),
('MN023', 'BA032', 400),
('MN020', 'BA026', 100),
('MN020', 'BA015', 150),
('MN020', 'BA025', 250),
('MN026', 'BA011', 150),
('MN026', 'BA019', 15),
('MN026', 'BA020', 10),
('MN026', 'BA021', 10),
('MN026', 'BA022', 250),
('MN026', 'BA028', 150),
('MN026', 'BA030', 10),
('MN026', 'BA018', 1);

-- --------------------------------------------------------

--
-- Table structure for table `detail_supplier`
--

CREATE TABLE `detail_supplier` (
  `id_supplier` varchar(6) NOT NULL,
  `id_bahan` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detail_supplier`
--

INSERT INTO `detail_supplier` (`id_supplier`, `id_bahan`) VALUES
('SP002', 'BA005'),
('SP010', 'BA025'),
('SP010', 'BA005'),
('SP010', 'BA025'),
('SP010', 'BA022'),
('SP010', 'BA001'),
('SP010', 'BA000'),
('SP010', 'BA027'),
('SP008', 'BA003'),
('SP008', 'BA007'),
('SP008', 'BA015'),
('SP008', 'BA032'),
('SP008', 'BA030'),
('SP008', 'BA029'),
('SP008', 'BA011'),
('SP008', 'BA000'),
('SP004', 'BA005'),
('SP004', 'BA026'),
('SP001', 'BA007'),
('SP001', 'BA002'),
('SP001', 'BA030'),
('SP001', 'BA032'),
('SP001', 'BA001'),
('SP003', 'BA005'),
('SP003', 'BA003'),
('SP003', 'BA008'),
('SP005', 'BA019'),
('SP005', 'BA025'),
('SP005', 'BA028'),
('SP005', 'BA030'),
('SP005', 'BA021'),
('SP005', 'BA018'),
('SP005', 'BA015');

-- --------------------------------------------------------

--
-- Table structure for table `detail_tr_beli`
--

CREATE TABLE `detail_tr_beli` (
  `id_tr_beli` varchar(7) NOT NULL,
  `id_bahan` varchar(6) NOT NULL,
  `nama_bahan` varchar(50) NOT NULL,
  `jenis_bahan` enum('Hewani','Nabati','Coffee','Perasa','Cairan') NOT NULL,
  `satuan_bahan` enum('gr','ml') NOT NULL,
  `harga_bahan` int(11) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detail_tr_beli`
--

INSERT INTO `detail_tr_beli` (`id_tr_beli`, `id_bahan`, `nama_bahan`, `jenis_bahan`, `satuan_bahan`, `harga_bahan`, `jumlah`, `total_harga`) VALUES
('TRB0001', 'BA001', 'Susu Coklat', 'Cairan', 'ml', 25000, 20, 10),
('TRB0001', 'BA001', 'Susu Coklat', 'Cairan', 'ml', 25000, 10, 1),
('TRB0009', 'BA007', 'Jeruk Bali', 'Nabati', 'gr', 13500, 4, 54000),
('TRB0010', 'BA002', 'Kentang', 'Nabati', 'gr', 50000, 10, 500000),
('TRB0011', 'BA015', 'Apel', 'Nabati', 'gr', 5000, 2, 10000),
('TRB0011', 'BA007', 'Jeruk Bali', 'Nabati', 'gr', 13500, 5, 67500),
('TRB0011', 'BA003', 'Jeruk', 'Nabati', 'gr', 25000, 5, 125000),
('TRB0012', 'BA015', 'Apel', 'Nabati', 'gr', 5000, 5, 25000),
('TRB0013', 'BA015', 'Apel', 'Nabati', 'gr', 5000, 5, 25000),
('TRB0014', 'BA015', 'Apel', 'Nabati', 'gr', 5000, 5, 25000),
('TRB0015', 'BA003', 'Jeruk', 'Nabati', 'gr', 25000, 1, 25000),
('TRB0020', 'BA000', 'Coklat', 'Perasa', 'gr', 57500, 1000, 57500),
('TRB0022', 'BA000', 'Coklat', 'Perasa', 'gr', 57500, 2000, 115000),
('TRB0023', 'BA007', 'Jeruk Bali', 'Nabati', 'gr', 13500, 5000, 67500),
('TRB0024', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 3000, 9000),
('TRB0024', 'BA022', 'Daging Ayam', 'Hewani', 'gr', 20000, 2000, 40000),
('TRB0025', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 2000, 6000),
('TRB0025', 'BA022', 'Daging Ayam', 'Hewani', 'gr', 20000, 2000, 40000),
('TRB0027', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 120000, 1000, 120000),
('TRB0028', 'BA000', 'Coklat', 'Perasa', 'gr', 57500, 1000, 57500),
('TRB0029', 'BA027', 'Anggur', 'Nabati', 'gr', 20000, 1000, 20000),
('TRB0030', 'BA027', 'Anggur', 'Nabati', 'gr', 20000, 2000, 40000),
('TRB0031', 'BA015', 'Apel', 'Nabati', 'gr', 5000, 2000, 10000),
('TRB0032', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 120000, 5000, 600000),
('TRB0032', 'BA003', 'Jeruk', 'Nabati', 'gr', 25000, 1000, 25000),
('TRB0001', 'BA000', 'Coklat', 'Perasa', 'gr', 57500, 1, 5000),
('TRB0035', 'BA000', 'Coklat', 'Perasa', 'gr', 57500, 1000, 57500),
('TRB0036', 'BA000', 'Coklat', 'Perasa', 'gr', 57500, 1000, 57500),
('TRB0037', 'BA000', 'Coklat', 'Perasa', 'gr', 57500, 1000, 57500),
('TRB0038', 'BA000', 'Coklat', 'Perasa', 'gr', 57500, 1000, 57500),
('TRB0039', 'BA002', 'Kentang', 'Nabati', 'gr', 50000, 3000, 150000),
('TRB0040', 'BA002', 'Kentang', 'Nabati', 'gr', 50000, 3000, 150000),
('TRB0041', 'BA000', 'Coklat', 'Perasa', 'gr', 57500, 1000, 57500),
('TRB0042', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 120000, 1000, 120000),
('TRB0043', 'BA003', 'Jeruk', 'Nabati', 'gr', 25000, 5000, 125000),
('TRB0044', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 10000, 30000),
('TRB0044', 'BA027', 'Anggur', 'Nabati', 'gr', 20000, 5000, 100000),
('TRB0045', 'BA002', 'Kentang', 'Nabati', 'gr', 50000, 5000, 250000),
('TRB0045', 'BA007', 'Jeruk Bali', 'Nabati', 'gr', 13500, 8000, 108000),
('TRB0046', 'BA032', 'Daging Babi', 'Hewani', 'gr', 150000, 8000, 1200000),
('TRB0046', 'BA011', 'Kacang', 'Nabati', 'gr', 5000, 5000, 25000),
('TRB0046', 'BA029', 'Jamur', 'Nabati', 'gr', 15000, 8000, 120000),
('TRB0047', 'BA032', 'Daging Babi', 'Hewani', 'gr', 150000, 8000, 1200000),
('TRB0048', 'BA018', 'Kecap', 'Perasa', 'ml', 1500, 5000, 7500),
('TRB0048', 'BA021', 'Bawang Putih', 'Nabati', 'gr', 10000, 5000, 50000),
('TRB0048', 'BA028', 'Nasi', 'Nabati', 'gr', 8000, 7000, 56000),
('TRB0048', 'BA030', 'Garam', 'Perasa', 'gr', 6000, 4000, 24000),
('TRB0048', 'BA019', 'Cabe Rawit', 'Nabati', 'gr', 5000, 10000, 50000),
('TRB0048', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 10000, 30000),
('TRB0049', 'BA026', 'Gula Pasir', 'Perasa', 'gr', 14000, 6000, 84000),
('TRB0050', 'BA003', 'Jeruk', 'Nabati', 'gr', 25000, 8000, 200000),
('TRB0052', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 2000, 6000),
('TRB0054', 'BA002', 'Kentang', 'Nabati', 'gr', 50000, 2000, 100000),
('TRB0055', 'BA022', 'Daging Ayam', 'Hewani', 'gr', 20000, 3000, 60000),
('TRB0057', 'BA032', 'Daging Babi', 'Hewani', 'gr', 150000, 1000, 150000),
('TRB0058', 'BA001', 'Susu Coklat', 'Cairan', 'ml', 25000, 2000, 50000),
('TRB0059', 'BA029', 'Jamur', 'Nabati', 'gr', 15000, 2000, 30000),
('TRB0060', 'BA001', 'Susu Coklat', 'Cairan', 'ml', 25000, 4000, 100000),
('TRB0060', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 3000, 9000),
('TRB0060', 'BA000', 'Coklat', 'Perasa', 'gr', 57500, 2000, 115000),
('TRB0061', 'BA019', 'Cabe Rawit', 'Nabati', 'gr', 5000, 3000, 15000),
('TRB0062', 'BA026', 'Gula Pasir', 'Perasa', 'gr', 14000, 1000, 14000),
('TRB0063', 'BA022', 'Daging Ayam', 'Hewani', 'gr', 20000, 3000, 60000),
('TRB0064', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 2000, 6000),
('TRB0064', 'BA022', 'Daging Ayam', 'Hewani', 'gr', 20000, 2000, 40000),
('TRB0065', 'BA027', 'Anggur', 'Nabati', 'gr', 20000, 1000, 20000),
('TRB0065', 'BA001', 'Susu Coklat', 'Cairan', 'ml', 25000, 1000, 25000),
('TRB0065', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 1000, 3000),
('TRB0065', 'BA022', 'Daging Ayam', 'Hewani', 'gr', 20000, 2000, 40000),
('TRB0065', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 120000, 2000, 240000),
('TRB0066', 'BA030', 'Garam', 'Perasa', 'gr', 6000, 2000, 12000),
('TRB0066', 'BA028', 'Nasi', 'Nabati', 'gr', 8000, 1000, 8000),
('TRB0066', 'BA019', 'Cabe Rawit', 'Nabati', 'gr', 5000, 2000, 10000),
('TRB0067', 'BA001', 'Susu Coklat', 'Cairan', 'ml', 25000, 4000, 100000),
('TRB0067', 'BA030', 'Garam', 'Perasa', 'gr', 6000, 2000, 12000),
('TRB0067', 'BA002', 'Kentang', 'Nabati', 'gr', 50000, 4000, 200000),
('TRB0067', 'BA032', 'Daging Babi', 'Hewani', 'gr', 150000, 2000, 300000),
('TRB0068', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 120000, 10000, 1200000),
('TRB0069', 'BA008', 'Kopi Hitam', 'Coffee', 'gr', 122250, 2000, 244500),
('TRB0070', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 10000, 30000),
('TRB0070', 'BA015', 'Apel', 'Nabati', 'gr', 5000, 4000, 20000),
('TRB0071', 'BA002', 'Kentang', 'Nabati', 'gr', 50000, 4000, 200000),
('TRB0072', 'BA007', 'Jeruk Bali', 'Nabati', 'gr', 13500, 2000, 27000),
('TRB0072', 'BA001', 'Susu Coklat', 'Cairan', 'ml', 25000, 2000, 50000),
('TRB0073', 'BA022', 'Daging Ayam', 'Hewani', 'gr', 20000, 3000, 60000),
('TRB0073', 'BA027', 'Anggur', 'Nabati', 'gr', 20000, 2000, 40000),
('TRB0073', 'BA000', 'Coklat', 'Perasa', 'gr', 57500, 1000, 57500),
('TRB0074', 'BA030', 'Garam', 'Perasa', 'gr', 6000, 2000, 12000),
('TRB0075', 'BA003', 'Jeruk', 'Nabati', 'gr', 25000, 2000, 50000),
('TRB0076', 'BA022', 'Daging Ayam', 'Hewani', 'gr', 20000, 1000, 20000),
('TRB0076', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 4000, 12000),
('TRB0077', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 120000, 1000, 120000),
('TRB0078', 'BA026', 'Gula Pasir', 'Perasa', 'gr', 14000, 2000, 28000),
('TRB0079', 'BA008', 'Kopi Hitam', 'Coffee', 'gr', 122250, 1000, 122250),
('TRB0080', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 1000, 3000),
('TRB0080', 'BA027', 'Anggur', 'Nabati', 'gr', 20000, 2000, 40000),
('TRB0080', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 120000, 1000, 120000),
('TRB0080', 'BA000', 'Coklat', 'Perasa', 'gr', 57500, 1000, 57500),
('TRB0081', 'BA022', 'Daging Ayam', 'Hewani', 'gr', 20000, 3000, 60000),
('TRB0081', 'BA027', 'Anggur', 'Nabati', 'gr', 20000, 2000, 40000),
('TRB0081', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 120000, 3000, 360000),
('TRB0081', 'BA025', 'Air Mineral', 'Cairan', 'ml', 3000, 15000, 45000),
('TRB0082', 'BA008', 'Kopi Hitam', 'Coffee', 'gr', 122250, 1000, 122250),
('TRB0083', 'BA002', 'Kentang', 'Nabati', 'gr', 50000, 2000, 100000),
('TRB0083', 'BA001', 'Susu Coklat', 'Cairan', 'ml', 25000, 2000, 50000),
('TRB0084', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 120000, 3000, 360000),
('TRB0085', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 120000, 10000, 1200000),
('TRB0086', 'BA003', 'Jeruk', 'Nabati', 'gr', 25000, 1000, 25000),
('TRB0086', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 120000, 2000, 240000),
('TRB0087', 'BA001', 'Susu Coklat', 'Cairan', 'ml', 25000, 12000, 300000),
('TRB0087', 'BA022', 'Daging Ayam', 'Hewani', 'gr', 20000, 5000, 100000),
('TRB0087', 'BA000', 'Coklat', 'Perasa', 'gr', 57500, 7000, 402500),
('TRB0088', 'BA008', 'Kopi Hitam', 'Coffee', 'gr', 122250, 1000, 122250),
('TRB0089', 'BA005', 'Biji Kopi', 'Coffee', 'gr', 120000, 1000, 120000),
('TRB0090', 'BA026', 'Gula Pasir', 'Perasa', 'gr', 14000, 1000, 14000),
('TRB0091', 'BA021', 'Bawang Putih', 'Nabati', 'gr', 10000, 1000, 10000);

-- --------------------------------------------------------

--
-- Table structure for table `detail_tr_jual`
--

CREATE TABLE `detail_tr_jual` (
  `id_tr_jual` varchar(7) NOT NULL,
  `id_menu` varchar(5) NOT NULL,
  `nama_menu` varchar(30) NOT NULL,
  `jenis_menu` enum('Minuman','Makanan','Original Coffee','Falvoured Coffee','Snack') NOT NULL,
  `harga_menu` int(11) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detail_tr_jual`
--

INSERT INTO `detail_tr_jual` (`id_tr_jual`, `id_menu`, `nama_menu`, `jenis_menu`, `harga_menu`, `jumlah`, `total_harga`) VALUES
('TRJ0001', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 5000),
('TRJ0001', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 2, 15000),
('TRJ0007', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 2, 20000),
('TRJ0008', 'MN020', 'Jus Apel', 'Minuman', 5000, 1, 5000),
('TRJ0009', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 2, 20000),
('TRJ0010', 'MN001', 'Es Jeruk', 'Minuman', 5000, 1, 5000),
('TRJ0011', 'MN020', 'Jus Apel', 'Minuman', 5000, 1, 5000),
('TRJ0012', 'MN020', 'Jus Apel', 'Minuman', 5000, 10, 50000),
('TRJ0013', 'MN020', 'Jus Apel', 'Minuman', 5000, 10, 50000),
('TRJ0014', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0014', 'MN019', 'Jamur Crispy', 'Snack', 10000, 4, 40000),
('TRJ0014', 'MN001', 'Es Jeruk', 'Minuman', 5000, 2, 10000),
('TRJ0014', 'MN015', 'Jus Anggur', 'Minuman', 2000, 1, 2000),
('TRJ0015', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 6, 90000),
('TRJ0016', 'MN001', 'Es Jeruk', 'Minuman', 5000, 4, 20000),
('TRJ0016', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 1, 10000),
('TRJ0017', 'MN001', 'Es Jeruk', 'Minuman', 5000, 1, 5000),
('TRJ0018', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 2, 20000),
('TRJ0019', 'MN001', 'Es Jeruk', 'Minuman', 5000, 2, 10000),
('TRJ0020', 'MN014', 'Black Coffee', 'Original Coffee', 3000, 2, 6000),
('TRJ0020', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0021', 'MN014', 'Black Coffee', 'Original Coffee', 3000, 2, 6000),
('TRJ0022', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0023', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 20, 300000),
('TRJ0023', 'MN013', 'Kentang Goreng', 'Snack', 12000, 10, 120000),
('TRJ0024', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 20, 300000),
('TRJ0025', 'MN018', 'Keripik Kentang', 'Snack', 8000, 5, 40000),
('TRJ0026', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0027', 'MN001', 'Es Jeruk', 'Minuman', 5000, 6, 30000),
('TRJ0028', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 5, 75000),
('TRJ0028', 'MN018', 'Keripik Kentang', 'Snack', 8000, 5, 40000),
('TRJ0028', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 5, 60000),
('TRJ0029', 'MN021', 'Babi Bakar', 'Makanan', 35000, 10, 350000),
('TRJ0029', 'MN022', 'Sate Babi', 'Makanan', 20000, 30, 600000),
('TRJ0030', 'MN021', 'Babi Bakar', 'Makanan', 35000, 10, 350000),
('TRJ0031', 'MN022', 'Sate Babi', 'Makanan', 20000, 10, 200000),
('TRJ0032', 'MN022', 'Sate Babi', 'Makanan', 20000, 5, 100000),
('TRJ0033', 'MN021', 'Babi Bakar', 'Makanan', 35000, 5, 175000),
('TRJ0034', 'MN022', 'Sate Babi', 'Makanan', 20000, 5, 100000),
('TRJ0035', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 5, 50000),
('TRJ0035', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 3, 45000),
('TRJ0035', 'MN021', 'Babi Bakar', 'Makanan', 35000, 2, 70000),
('TRJ0035', 'MN022', 'Sate Babi', 'Makanan', 20000, 2, 40000),
('TRJ0035', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 10, 150000),
('TRJ0035', 'MN019', 'Jamur Crispy', 'Snack', 10000, 9, 90000),
('TRJ0035', 'MN018', 'Keripik Kentang', 'Snack', 8000, 8, 64000),
('TRJ0035', 'MN015', 'Jus Anggur', 'Minuman', 2000, 10, 20000),
('TRJ0035', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0036', 'MN021', 'Babi Bakar', 'Makanan', 35000, 4, 140000),
('TRJ0036', 'MN022', 'Sate Babi', 'Makanan', 20000, 1, 20000),
('TRJ0037', 'MN001', 'Es Jeruk', 'Minuman', 5000, 1, 5000),
('TRJ0037', 'MN013', 'Kentang Goreng', 'Snack', 12000, 5, 60000),
('TRJ0037', 'MN015', 'Jus Anggur', 'Minuman', 2000, 1, 2000),
('TRJ0038', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ0038', 'MN018', 'Keripik Kentang', 'Snack', 8000, 1, 8000),
('TRJ0038', 'MN020', 'Jus Apel', 'Minuman', 5000, 1, 5000),
('TRJ0038', 'MN023', 'Babi Guling', 'Makanan', 350000, 1, 350000),
('TRJ0038', 'MN001', 'Es Jeruk', 'Minuman', 5000, 1, 5000),
('TRJ0038', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0039', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 11, 110000),
('TRJ0039', 'MN014', 'Black Coffee', 'Original Coffee', 3000, 2, 6000),
('TRJ0040', 'MN014', 'Black Coffee', 'Original Coffee', 3000, 5, 15000),
('TRJ0040', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 3, 36000),
('TRJ0040', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ0041', 'MN001', 'Es Jeruk', 'Minuman', 5000, 1, 5000),
('TRJ0003', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 5, 45000),
('TRJ0003', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 3, 45000),
('TRJ0003', 'MN021', 'Babi Bakar', 'Makanan', 35000, 5, 5000),
('TRJ0003', 'MN015', 'Jus Anggur', 'Minuman', 2000, 2, 15000),
('TRJ0003', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 20, 15000),
('TRJ0003', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 10, 45000),
('TRJ0003', 'MN020', 'Jus Apel', 'Minuman', 5000, 5, 0),
('TRJ0003', 'MN019', 'Jamur Crispy', 'Snack', 10000, 5, 5000),
('TRJ0003', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 8, 5000),
('TRJ0042', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 5, 50000),
('TRJ0043', 'MN019', 'Jamur Crispy', 'Snack', 10000, 5, 50000),
('TRJ0043', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ0044', 'MN018', 'Keripik Kentang', 'Snack', 8000, 4, 32000),
('TRJ0045', 'MN001', 'Es Jeruk', 'Minuman', 5000, 2, 10000),
('TRJ0046', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 3, 45000),
('TRJ0046', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0046', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ0047', 'MN001', 'Es Jeruk', 'Minuman', 5000, 7, 35000),
('TRJ0047', 'MN014', 'Black Coffee', 'Original Coffee', 3000, 2, 6000),
('TRJ0048', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 4, 60000),
('TRJ0049', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 2, 20000),
('TRJ0050', 'MN019', 'Jamur Crispy', 'Snack', 10000, 5, 50000),
('TRJ0050', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 7, 105000),
('TRJ0050', 'MN014', 'Black Coffee', 'Original Coffee', 3000, 2, 6000),
('TRJ0051', 'MN001', 'Es Jeruk', 'Minuman', 5000, 1, 5000),
('TRJ0051', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 1, 10000),
('TRJ0051', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0051', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0051', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ0051', 'MN014', 'Black Coffee', 'Original Coffee', 3000, 1, 3000),
('TRJ0051', 'MN020', 'Jus Apel', 'Minuman', 5000, 1, 5000),
('TRJ0051', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ0053', 'MN001', 'Es Jeruk', 'Minuman', 5000, 6, 30000),
('TRJ0053', 'MN020', 'Jus Apel', 'Minuman', 5000, 4, 20000),
('TRJ0053', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 6, 90000),
('TRJ0053', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0053', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0053', 'MN021', 'Babi Bakar', 'Makanan', 35000, 3, 105000),
('TRJ0054', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 5, 50000),
('TRJ0055', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 6, 72000),
('TRJ0055', 'MN001', 'Es Jeruk', 'Minuman', 5000, 3, 15000),
('TRJ0055', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0055', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0056', 'MN013', 'Kentang Goreng', 'Snack', 12000, 5, 60000),
('TRJ0057', 'MN019', 'Jamur Crispy', 'Snack', 10000, 3, 30000),
('TRJ0057', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 2, 20000),
('TRJ0057', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ0057', 'MN001', 'Es Jeruk', 'Minuman', 5000, 5, 25000),
('TRJ0058', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 2, 20000),
('TRJ0058', 'MN014', 'Black Coffee', 'Original Coffee', 3000, 5, 15000),
('TRJ0058', 'MN019', 'Jamur Crispy', 'Snack', 10000, 2, 20000),
('TRJ0059', 'MN013', 'Kentang Goreng', 'Snack', 12000, 6, 72000),
('TRJ0059', 'MN001', 'Es Jeruk', 'Minuman', 5000, 3, 15000),
('TRJ0059', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 2, 24000),
('TRJ0059', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 1, 10000),
('TRJ0060', 'MN018', 'Keripik Kentang', 'Snack', 8000, 3, 24000),
('TRJ0060', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0060', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 3, 30000),
('TRJ0064', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 9, 135000),
('TRJ0065', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0066', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 2, 20000),
('TRJ0066', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 4, 48000),
('TRJ0066', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0066', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 3, 45000),
('TRJ0066', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ0066', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0067', 'MN001', 'Es Jeruk', 'Minuman', 10000, 3, 30000),
('TRJ0067', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0067', 'MN013', 'Kentang Goreng', 'Snack', 12000, 4, 48000),
('TRJ0068', 'MN001', 'Es Jeruk', 'Minuman', 10000, 1, 10000),
('TRJ0068', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 3, 45000),
('TRJ0068', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 3, 39000),
('TRJ0068', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0068', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ0069', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 2, 20000),
('TRJ0069', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 3, 36000),
('TRJ0069', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 4, 60000),
('TRJ0072', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 7, 70000),
('TRJ0072', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 3, 36000),
('TRJ0073', 'MN022', 'Sate Babi', 'Makanan', 20000, 6, 120000),
('TRJ0074', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0075', 'MN001', 'Es Jeruk', 'Minuman', 10000, 1, 10000),
('TRJ0076', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 3, 39000),
('TRJ0077', 'MN018', 'Keripik Kentang', 'Snack', 8000, 10, 80000),
('TRJ0077', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 3, 45000),
('TRJ0077', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 5, 60000),
('TRJ0077', 'MN015', 'Jus Anggur', 'Minuman', 12000, 2, 24000),
('TRJ0078', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 3, 45000),
('TRJ0078', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ0079', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 2, 30000),
('TRJ0079', 'MN018', 'Keripik Kentang', 'Snack', 8000, 2, 16000),
('TRJ0079', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 4, 40000),
('TRJ0079', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ0080', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 7, 70000),
('TRJ0080', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 3, 39000),
('TRJ0080', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 2, 24000),
('TRJ0080', 'MN013', 'Kentang Goreng', 'Snack', 12000, 4, 48000),
('TRJ0080', 'MN019', 'Jamur Crispy', 'Snack', 10000, 2, 20000),
('TRJ0081', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 3, 39000),
('TRJ0082', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 3, 39000),
('TRJ0082', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 3, 36000),
('TRJ0083', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 9, 108000),
('TRJ0083', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 7, 84000),
('TRJ0083', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0084', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 5, 50000),
('TRJ0084', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 3, 36000),
('TRJ0084', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 7, 84000),
('TRJ0085', 'MN013', 'Kentang Goreng', 'Snack', 12000, 4, 48000),
('TRJ0086', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 5, 50000),
('TRJ0087', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 5, 50000),
('TRJ0088', 'MN001', 'Es Jeruk', 'Minuman', 10000, 5, 50000),
('TRJ0089', 'MN001', 'Es Jeruk', 'Minuman', 10000, 1, 10000),
('TRJ0089', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 1, 10000),
('TRJ0089', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0089', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0089', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ0089', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ0089', 'MN015', 'Jus Anggur', 'Minuman', 12000, 1, 12000),
('TRJ0089', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0090', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 3, 39000),
('TRJ0091', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 5, 75000),
('TRJ0092', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0092', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 3, 36000),
('TRJ0092', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 4, 40000),
('TRJ0092', 'MN019', 'Jamur Crispy', 'Snack', 10000, 1, 10000),
('TRJ0093', 'MN023', 'Babi Guling', 'Makanan', 350000, 1, 350000),
('TRJ0094', 'MN013', 'Kentang Goreng', 'Snack', 12000, 4, 48000),
('TRJ0094', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0094', 'MN017', 'Nasi Goreng', 'Makanan', 12000, 3, 36000),
('TRJ0094', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 1, 10000),
('TRJ0095', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0096', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ0096', 'MN020', 'Jus Apel', 'Minuman', 11000, 1, 11000),
('TRJ0097', 'MN016', 'Vanilla Lattte', 'Falvoured Coffee', 12000, 1, 12000),
('TRJ0098', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0099', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0100', 'MN015', 'Jus Anggur', 'Minuman', 12000, 1, 12000),
('TRJ0101', 'MN013', 'Kentang Goreng', 'Snack', 12000, 2, 24000),
('TRJ0101', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 1, 10000),
('TRJ0101', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 1, 15000),
('TRJ0102', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 1, 10000),
('TRJ0103', 'MN018', 'Keripik Kentang', 'Snack', 8000, 1, 8000),
('TRJ0104', 'MN001', 'Es Jeruk', 'Minuman', 10000, 1, 10000),
('TRJ0105', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0105', 'MN002', 'Coffee Latee', 'Original Coffee', 10000, 1, 10000),
('TRJ0106', 'MN001', 'Es Jeruk', 'Minuman', 10000, 1, 10000),
('TRJ0107', 'MN001', 'Es Jeruk', 'Minuman', 10000, 5, 50000),
('TRJ0108', 'MN014', 'Black Coffee', 'Original Coffee', 13000, 1, 13000),
('TRJ0109', 'MN011', 'Chocholate', 'Falvoured Coffee', 15000, 2, 30000),
('TRJ0110', 'MN013', 'Kentang Goreng', 'Snack', 12000, 1, 12000),
('TRJ0111', 'MN012', 'Ayam Bakar', 'Makanan', 15000, 1, 15000),
('TRJ0112', 'MN001', 'Es Jeruk', 'Minuman', 10000, 1, 10000);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `nama_karyawan`, `no_telp`, `alamat`, `shif`) VALUES
('KY001', 'Achmad Baihaqi', '085655864624', 'Jombang, Jawa Timur, Indonesia', 'Siang (07:00-17:59)'),
('KY002', 'Mohammad Ilham', '085690123458', 'Nganjuk, Jawa Timur', 'Malam (18:00-22:59)'),
('KY003', 'Widyasari Raisya', '085690239023', 'Mojokerto, Jawa Timur', 'Siang (07:00-17:59)'),
('KY004', 'Septian Yoga', '085690238912', 'Nganjuk, Jawa Timur', 'Siang (07:00-17:59)'),
('KY005', 'Ini Karyawan', '0892333', 'Jombang', 'Malam (18:00-22:59)');

-- --------------------------------------------------------

--
-- Table structure for table `log_tr_beli`
--

CREATE TABLE `log_tr_beli` (
  `id_tr_beli` varchar(7) NOT NULL,
  `id_bahan` varchar(6) NOT NULL,
  `jumlah` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `log_tr_beli`
--

INSERT INTO `log_tr_beli` (`id_tr_beli`, `id_bahan`, `jumlah`) VALUES
('TRB0001', 'BA000', 1),
('TRB0002', 'BA001', 1),
('TRB0001', 'BA002', 500),
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
('TRB0050', 'BA003', 8000),
('TRB0052', 'BA025', 2000),
('TRB0054', 'BA002', 2000),
('TRB0055', 'BA022', 3000),
('TRB0057', 'BA032', 1000),
('TRB0058', 'BA001', 2000),
('TRB0059', 'BA029', 2000),
('TRB0060', 'BA001', 4000),
('TRB0060', 'BA025', 3000),
('TRB0060', 'BA000', 2000),
('TRB0061', 'BA019', 3000),
('TRB0062', 'BA026', 1000),
('TRB0063', 'BA022', 3000),
('TRB0064', 'BA025', 2000),
('TRB0064', 'BA022', 2000),
('TRB0065', 'BA027', 1000),
('TRB0065', 'BA001', 1000),
('TRB0065', 'BA025', 1000),
('TRB0065', 'BA022', 2000),
('TRB0065', 'BA005', 2000),
('TRB0066', 'BA030', 2000),
('TRB0066', 'BA028', 1000),
('TRB0066', 'BA019', 2000),
('TRB0067', 'BA001', 4000),
('TRB0067', 'BA030', 2000),
('TRB0067', 'BA002', 4000),
('TRB0067', 'BA032', 2000),
('TRB0068', 'BA005', 10000),
('TRB0069', 'BA008', 2000),
('TRB0070', 'BA025', 10000),
('TRB0070', 'BA015', 4000),
('TRB0071', 'BA002', 4000),
('TRB0072', 'BA007', 2000),
('TRB0072', 'BA001', 2000),
('TRB0073', 'BA022', 3000),
('TRB0073', 'BA027', 2000),
('TRB0073', 'BA000', 1000),
('TRB0074', 'BA030', 2000),
('TRB0075', 'BA003', 2000),
('TRB0076', 'BA022', 1000),
('TRB0076', 'BA025', 4000),
('TRB0077', 'BA005', 1000),
('TRB0078', 'BA026', 2000),
('TRB0079', 'BA008', 1000),
('TRB0080', 'BA025', 1000),
('TRB0080', 'BA027', 2000),
('TRB0080', 'BA005', 1000),
('TRB0080', 'BA000', 1000),
('TRB0081', 'BA022', 3000),
('TRB0081', 'BA027', 2000),
('TRB0081', 'BA005', 3000),
('TRB0081', 'BA025', 15000),
('TRB0082', 'BA008', 1000),
('TRB0083', 'BA002', 2000),
('TRB0083', 'BA001', 2000),
('TRB0084', 'BA005', 3000),
('TRB0085', 'BA005', 10000),
('TRB0086', 'BA003', 1000),
('TRB0086', 'BA005', 2000),
('TRB0087', 'BA001', 12000),
('TRB0087', 'BA022', 5000),
('TRB0087', 'BA000', 7000),
('TRB0088', 'BA008', 1000),
('TRB0089', 'BA005', 1000),
('TRB0090', 'BA026', 1000),
('TRB0091', 'BA021', 1000);

--
-- Triggers `log_tr_beli`
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
-- Table structure for table `log_tr_jual`
--

CREATE TABLE `log_tr_jual` (
  `id_tr_jual` varchar(7) NOT NULL,
  `id_menu` varchar(5) NOT NULL,
  `id_bahan` varchar(6) NOT NULL,
  `quantity` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `log_tr_jual`
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
('TRJ0053', 'MN021', 'BA032', 600),
('TRJ0054', 'MN002', 'BA007', 500),
('TRJ0055', 'MN017', 'BA001', 300),
('TRJ0055', 'MN017', 'BA025', 1500),
('TRJ0055', 'MN017', 'BA026', 180),
('TRJ0055', 'MN017', 'BA005', 120),
('TRJ0055', 'MN001', 'BA001', 150),
('TRJ0055', 'MN001', 'BA025', 750),
('TRJ0055', 'MN001', 'BA026', 90),
('TRJ0055', 'MN001', 'BA005', 60),
('TRJ0055', 'MN011', 'BA001', 50),
('TRJ0055', 'MN011', 'BA025', 250),
('TRJ0055', 'MN011', 'BA026', 30),
('TRJ0055', 'MN011', 'BA005', 20),
('TRJ0055', 'MN016', 'BA001', 100),
('TRJ0055', 'MN016', 'BA025', 500),
('TRJ0055', 'MN016', 'BA026', 60),
('TRJ0055', 'MN016', 'BA005', 40),
('TRJ0056', 'MN013', 'BA002', 750),
('TRJ0056', 'MN013', 'BA026', 50),
('TRJ0056', 'MN013', 'BA019', 400),
('TRJ0057', 'MN019', 'BA003', 600),
('TRJ0057', 'MN019', 'BA025', 750),
('TRJ0057', 'MN019', 'BA026', 60),
('TRJ0057', 'MN002', 'BA003', 400),
('TRJ0057', 'MN002', 'BA025', 500),
('TRJ0057', 'MN002', 'BA026', 40),
('TRJ0057', 'MN012', 'BA003', 400),
('TRJ0057', 'MN012', 'BA025', 500),
('TRJ0057', 'MN012', 'BA026', 40),
('TRJ0057', 'MN001', 'BA003', 1000),
('TRJ0057', 'MN001', 'BA025', 1250),
('TRJ0057', 'MN001', 'BA026', 100),
('TRJ0058', 'MN002', 'BA002', 300),
('TRJ0058', 'MN002', 'BA030', 100),
('TRJ0058', 'MN014', 'BA002', 750),
('TRJ0058', 'MN014', 'BA030', 250),
('TRJ0058', 'MN019', 'BA002', 300),
('TRJ0058', 'MN019', 'BA030', 100),
('TRJ0059', 'MN013', 'BA007', 600),
('TRJ0059', 'MN001', 'BA007', 300),
('TRJ0059', 'MN016', 'BA007', 200),
('TRJ0059', 'MN002', 'BA007', 100),
('TRJ0060', 'MN018', 'BA007', 300),
('TRJ0060', 'MN013', 'BA007', 200),
('TRJ0060', 'MN002', 'BA007', 300),
('TRJ0064', 'MN012', 'BA011', 450),
('TRJ0064', 'MN012', 'BA022', 2250),
('TRJ0064', 'MN012', 'BA018', 90),
('TRJ0064', 'MN012', 'BA019', 540),
('TRJ0065', 'MN013', 'BA002', 300),
('TRJ0065', 'MN013', 'BA026', 20),
('TRJ0065', 'MN013', 'BA019', 160),
('TRJ0066', 'MN002', 'BA001', 100),
('TRJ0066', 'MN002', 'BA025', 500),
('TRJ0066', 'MN002', 'BA026', 60),
('TRJ0066', 'MN002', 'BA005', 40),
('TRJ0066', 'MN017', 'BA001', 200),
('TRJ0066', 'MN017', 'BA025', 1000),
('TRJ0066', 'MN017', 'BA026', 120),
('TRJ0066', 'MN017', 'BA005', 80),
('TRJ0066', 'MN013', 'BA001', 100),
('TRJ0066', 'MN013', 'BA025', 500),
('TRJ0066', 'MN013', 'BA026', 60),
('TRJ0066', 'MN013', 'BA005', 40),
('TRJ0066', 'MN011', 'BA001', 150),
('TRJ0066', 'MN011', 'BA025', 750),
('TRJ0066', 'MN011', 'BA026', 90),
('TRJ0066', 'MN011', 'BA005', 60),
('TRJ0066', 'MN019', 'BA001', 50),
('TRJ0066', 'MN019', 'BA025', 250),
('TRJ0066', 'MN019', 'BA026', 30),
('TRJ0066', 'MN019', 'BA005', 20),
('TRJ0066', 'MN016', 'BA001', 50),
('TRJ0066', 'MN016', 'BA025', 250),
('TRJ0066', 'MN016', 'BA026', 30),
('TRJ0066', 'MN016', 'BA005', 20),
('TRJ0067', 'MN001', 'BA002', 450),
('TRJ0067', 'MN001', 'BA026', 30),
('TRJ0067', 'MN001', 'BA019', 240),
('TRJ0067', 'MN016', 'BA002', 150),
('TRJ0067', 'MN016', 'BA026', 10),
('TRJ0067', 'MN016', 'BA019', 80),
('TRJ0067', 'MN013', 'BA002', 600),
('TRJ0067', 'MN013', 'BA026', 40),
('TRJ0067', 'MN013', 'BA019', 320),
('TRJ0068', 'MN001', 'BA025', 300),
('TRJ0068', 'MN001', 'BA026', 20),
('TRJ0068', 'MN001', 'BA005', 50),
('TRJ0068', 'MN012', 'BA025', 900),
('TRJ0068', 'MN012', 'BA026', 60),
('TRJ0068', 'MN012', 'BA005', 150),
('TRJ0068', 'MN014', 'BA025', 900),
('TRJ0068', 'MN014', 'BA026', 60),
('TRJ0068', 'MN014', 'BA005', 150),
('TRJ0068', 'MN013', 'BA025', 600),
('TRJ0068', 'MN013', 'BA026', 40),
('TRJ0068', 'MN013', 'BA005', 100),
('TRJ0068', 'MN018', 'BA025', 600),
('TRJ0068', 'MN018', 'BA026', 40),
('TRJ0068', 'MN018', 'BA005', 100),
('TRJ0069', 'MN002', 'BA011', 100),
('TRJ0069', 'MN002', 'BA022', 500),
('TRJ0069', 'MN002', 'BA018', 20),
('TRJ0069', 'MN002', 'BA019', 120),
('TRJ0069', 'MN017', 'BA011', 150),
('TRJ0069', 'MN017', 'BA022', 750),
('TRJ0069', 'MN017', 'BA018', 30),
('TRJ0069', 'MN017', 'BA019', 180),
('TRJ0069', 'MN012', 'BA011', 200),
('TRJ0069', 'MN012', 'BA022', 1000),
('TRJ0069', 'MN012', 'BA018', 40),
('TRJ0069', 'MN012', 'BA019', 240),
('TRJ0070', 'MN013', 'BA002', 450),
('TRJ0070', 'MN013', 'BA030', 150),
('TRJ0070', 'MN019', 'BA002', 300),
('TRJ0070', 'MN019', 'BA030', 100),
('TRJ0071', 'MN001', 'BA003', 400),
('TRJ0071', 'MN001', 'BA025', 500),
('TRJ0071', 'MN001', 'BA026', 40),
('TRJ0072', 'MN002', 'BA028', 1050),
('TRJ0072', 'MN002', 'BA018', 140),
('TRJ0072', 'MN002', 'BA019', 70),
('TRJ0072', 'MN017', 'BA028', 450),
('TRJ0072', 'MN017', 'BA018', 60),
('TRJ0072', 'MN017', 'BA019', 30),
('TRJ0073', 'MN022', 'BA032', 600),
('TRJ0074', 'MN012', 'BA011', 50),
('TRJ0074', 'MN012', 'BA022', 250),
('TRJ0074', 'MN012', 'BA018', 10),
('TRJ0074', 'MN012', 'BA019', 60),
('TRJ0075', 'MN001', 'BA003', 200),
('TRJ0075', 'MN001', 'BA025', 250),
('TRJ0075', 'MN001', 'BA026', 20),
('TRJ0076', 'MN014', 'BA025', 900),
('TRJ0076', 'MN014', 'BA026', 60),
('TRJ0076', 'MN014', 'BA005', 150),
('TRJ0077', 'MN018', 'BA025', 3000),
('TRJ0077', 'MN018', 'BA026', 1000),
('TRJ0077', 'MN018', 'BA027', 1500),
('TRJ0077', 'MN011', 'BA025', 900),
('TRJ0077', 'MN011', 'BA026', 300),
('TRJ0077', 'MN011', 'BA027', 450),
('TRJ0077', 'MN016', 'BA025', 1500),
('TRJ0077', 'MN016', 'BA026', 500),
('TRJ0077', 'MN016', 'BA027', 750),
('TRJ0077', 'MN015', 'BA025', 600),
('TRJ0077', 'MN015', 'BA026', 200),
('TRJ0077', 'MN015', 'BA027', 300),
('TRJ0078', 'MN012', 'BA025', 900),
('TRJ0078', 'MN012', 'BA026', 60),
('TRJ0078', 'MN012', 'BA005', 150),
('TRJ0078', 'MN014', 'BA025', 300),
('TRJ0078', 'MN014', 'BA026', 20),
('TRJ0078', 'MN014', 'BA005', 50),
('TRJ0079', 'MN012', 'BA002', 300),
('TRJ0079', 'MN012', 'BA026', 20),
('TRJ0079', 'MN012', 'BA019', 160),
('TRJ0079', 'MN018', 'BA002', 300),
('TRJ0079', 'MN018', 'BA026', 20),
('TRJ0079', 'MN018', 'BA019', 160),
('TRJ0079', 'MN002', 'BA002', 600),
('TRJ0079', 'MN002', 'BA026', 40),
('TRJ0079', 'MN002', 'BA019', 320),
('TRJ0079', 'MN013', 'BA002', 150),
('TRJ0079', 'MN013', 'BA026', 10),
('TRJ0079', 'MN013', 'BA019', 80),
('TRJ0080', 'MN002', 'BA002', 1050),
('TRJ0080', 'MN002', 'BA030', 350),
('TRJ0080', 'MN014', 'BA002', 450),
('TRJ0080', 'MN014', 'BA030', 150),
('TRJ0080', 'MN017', 'BA002', 300),
('TRJ0080', 'MN017', 'BA030', 100),
('TRJ0080', 'MN013', 'BA002', 600),
('TRJ0080', 'MN013', 'BA030', 200),
('TRJ0080', 'MN019', 'BA002', 300),
('TRJ0080', 'MN019', 'BA030', 100),
('TRJ0081', 'MN014', 'BA025', 900),
('TRJ0081', 'MN014', 'BA026', 60),
('TRJ0081', 'MN014', 'BA005', 150),
('TRJ0082', 'MN014', 'BA028', 450),
('TRJ0082', 'MN014', 'BA018', 60),
('TRJ0082', 'MN014', 'BA019', 30),
('TRJ0082', 'MN017', 'BA028', 450),
('TRJ0082', 'MN017', 'BA018', 60),
('TRJ0082', 'MN017', 'BA019', 30),
('TRJ0083', 'MN016', 'BA000', 180),
('TRJ0083', 'MN016', 'BA025', 1800),
('TRJ0083', 'MN016', 'BA026', 450),
('TRJ0083', 'MN016', 'BA005', 90),
('TRJ0083', 'MN017', 'BA000', 140),
('TRJ0083', 'MN017', 'BA025', 1400),
('TRJ0083', 'MN017', 'BA026', 350),
('TRJ0083', 'MN017', 'BA005', 70),
('TRJ0083', 'MN011', 'BA000', 40),
('TRJ0083', 'MN011', 'BA025', 400),
('TRJ0083', 'MN011', 'BA026', 100),
('TRJ0083', 'MN011', 'BA005', 20),
('TRJ0084', 'MN002', 'BA028', 750),
('TRJ0084', 'MN002', 'BA018', 100),
('TRJ0084', 'MN002', 'BA019', 50),
('TRJ0084', 'MN016', 'BA028', 450),
('TRJ0084', 'MN016', 'BA018', 60),
('TRJ0084', 'MN016', 'BA019', 30),
('TRJ0084', 'MN017', 'BA028', 1050),
('TRJ0084', 'MN017', 'BA018', 140),
('TRJ0084', 'MN017', 'BA019', 70),
('TRJ0085', 'MN013', 'BA002', 600),
('TRJ0085', 'MN013', 'BA026', 40),
('TRJ0085', 'MN013', 'BA019', 320),
('TRJ0086', 'MN002', 'BA007', 500),
('TRJ0087', 'MN002', 'BA007', 500),
('TRJ0088', 'MN001', 'BA003', 1000),
('TRJ0088', 'MN001', 'BA025', 1250),
('TRJ0088', 'MN001', 'BA026', 100),
('TRJ0089', 'MN001', 'BA001', 50),
('TRJ0089', 'MN001', 'BA025', 250),
('TRJ0089', 'MN001', 'BA026', 30),
('TRJ0089', 'MN001', 'BA005', 20),
('TRJ0089', 'MN002', 'BA001', 50),
('TRJ0089', 'MN002', 'BA025', 250),
('TRJ0089', 'MN002', 'BA026', 30),
('TRJ0089', 'MN002', 'BA005', 20),
('TRJ0089', 'MN011', 'BA001', 50),
('TRJ0089', 'MN011', 'BA025', 250),
('TRJ0089', 'MN011', 'BA026', 30),
('TRJ0089', 'MN011', 'BA005', 20),
('TRJ0089', 'MN012', 'BA001', 50),
('TRJ0089', 'MN012', 'BA025', 250),
('TRJ0089', 'MN012', 'BA026', 30),
('TRJ0089', 'MN012', 'BA005', 20),
('TRJ0089', 'MN013', 'BA001', 50),
('TRJ0089', 'MN013', 'BA025', 250),
('TRJ0089', 'MN013', 'BA026', 30),
('TRJ0089', 'MN013', 'BA005', 20),
('TRJ0089', 'MN014', 'BA001', 50),
('TRJ0089', 'MN014', 'BA025', 250),
('TRJ0089', 'MN014', 'BA026', 30),
('TRJ0089', 'MN014', 'BA005', 20),
('TRJ0089', 'MN015', 'BA001', 50),
('TRJ0089', 'MN015', 'BA025', 250),
('TRJ0089', 'MN015', 'BA026', 30),
('TRJ0089', 'MN015', 'BA005', 20),
('TRJ0089', 'MN016', 'BA001', 50),
('TRJ0089', 'MN016', 'BA025', 250),
('TRJ0089', 'MN016', 'BA026', 30),
('TRJ0089', 'MN016', 'BA005', 20),
('TRJ0090', 'MN014', 'BA025', 900),
('TRJ0090', 'MN014', 'BA026', 60),
('TRJ0090', 'MN014', 'BA005', 150),
('TRJ0091', 'MN012', 'BA011', 250),
('TRJ0091', 'MN012', 'BA022', 1250),
('TRJ0091', 'MN012', 'BA018', 50),
('TRJ0091', 'MN012', 'BA019', 300),
('TRJ0092', 'MN013', 'BA002', 300),
('TRJ0092', 'MN013', 'BA030', 100),
('TRJ0092', 'MN016', 'BA002', 450),
('TRJ0092', 'MN016', 'BA030', 150),
('TRJ0092', 'MN002', 'BA002', 600),
('TRJ0092', 'MN002', 'BA030', 200),
('TRJ0092', 'MN019', 'BA002', 150),
('TRJ0092', 'MN019', 'BA030', 50),
('TRJ0093', 'MN023', 'BA032', 400),
('TRJ0094', 'MN013', 'BA001', 200),
('TRJ0094', 'MN013', 'BA025', 1000),
('TRJ0094', 'MN013', 'BA026', 120),
('TRJ0094', 'MN013', 'BA005', 80),
('TRJ0094', 'MN011', 'BA001', 100),
('TRJ0094', 'MN011', 'BA025', 500),
('TRJ0094', 'MN011', 'BA026', 60),
('TRJ0094', 'MN011', 'BA005', 40),
('TRJ0094', 'MN017', 'BA001', 150),
('TRJ0094', 'MN017', 'BA025', 750),
('TRJ0094', 'MN017', 'BA026', 90),
('TRJ0094', 'MN017', 'BA005', 60),
('TRJ0094', 'MN002', 'BA001', 50),
('TRJ0094', 'MN002', 'BA025', 250),
('TRJ0094', 'MN002', 'BA026', 30),
('TRJ0094', 'MN002', 'BA005', 20),
('TRJ0095', 'MN011', 'BA000', 20),
('TRJ0095', 'MN011', 'BA025', 200),
('TRJ0095', 'MN011', 'BA026', 50),
('TRJ0095', 'MN011', 'BA005', 10),
('TRJ0096', 'MN013', 'BA026', 100),
('TRJ0096', 'MN020', 'BA026', 100),
('TRJ0097', 'MN016', 'BA001', 50),
('TRJ0097', 'MN016', 'BA025', 250),
('TRJ0097', 'MN016', 'BA026', 30),
('TRJ0097', 'MN016', 'BA005', 20),
('TRJ0098', 'MN011', 'BA000', 20),
('TRJ0098', 'MN011', 'BA025', 200),
('TRJ0098', 'MN011', 'BA026', 50),
('TRJ0098', 'MN011', 'BA005', 10),
('TRJ0099', 'MN011', 'BA000', 20),
('TRJ0099', 'MN011', 'BA025', 200),
('TRJ0099', 'MN011', 'BA026', 50),
('TRJ0099', 'MN011', 'BA005', 10),
('TRJ0100', 'MN015', 'BA025', 300),
('TRJ0100', 'MN015', 'BA026', 100),
('TRJ0100', 'MN015', 'BA027', 150),
('TRJ0101', 'MN013', 'BA000', 40),
('TRJ0101', 'MN013', 'BA025', 400),
('TRJ0101', 'MN013', 'BA026', 100),
('TRJ0101', 'MN013', 'BA005', 20),
('TRJ0101', 'MN002', 'BA000', 20),
('TRJ0101', 'MN002', 'BA025', 200),
('TRJ0101', 'MN002', 'BA026', 50),
('TRJ0101', 'MN002', 'BA005', 10),
('TRJ0101', 'MN011', 'BA000', 20),
('TRJ0101', 'MN011', 'BA025', 200),
('TRJ0101', 'MN011', 'BA026', 50),
('TRJ0101', 'MN011', 'BA005', 10),
('TRJ0102', 'MN002', 'BA007', 100),
('TRJ0103', 'MN018', 'BA002', 100),
('TRJ0103', 'MN018', 'BA026', 15),
('TRJ0103', 'MN018', 'BA019', 50),
('TRJ0103', 'MN018', 'BA030', 10),
('TRJ0104', 'MN001', 'BA003', 200),
('TRJ0104', 'MN001', 'BA025', 250),
('TRJ0104', 'MN001', 'BA026', 20),
('TRJ0105', 'MN012', 'BA007', 100),
('TRJ0105', 'MN002', 'BA007', 100),
('TRJ0106', 'MN001', 'BA003', 200),
('TRJ0106', 'MN001', 'BA025', 250),
('TRJ0106', 'MN001', 'BA026', 20),
('TRJ0107', 'MN001', 'BA003', 1000),
('TRJ0107', 'MN001', 'BA025', 1250),
('TRJ0107', 'MN001', 'BA026', 100),
('TRJ0108', 'MN014', 'BA025', 300),
('TRJ0108', 'MN014', 'BA026', 20),
('TRJ0108', 'MN014', 'BA005', 50),
('TRJ0109', 'MN011', 'BA000', 40),
('TRJ0109', 'MN011', 'BA025', 400),
('TRJ0109', 'MN011', 'BA026', 100),
('TRJ0109', 'MN011', 'BA005', 20),
('TRJ0110', 'MN013', 'BA002', 150),
('TRJ0110', 'MN013', 'BA026', 10),
('TRJ0110', 'MN013', 'BA019', 80),
('TRJ0111', 'MN012', 'BA011', 50),
('TRJ0111', 'MN012', 'BA022', 250),
('TRJ0111', 'MN012', 'BA018', 10),
('TRJ0111', 'MN012', 'BA019', 60),
('TRJ0112', 'MN001', 'BA003', 200),
('TRJ0112', 'MN001', 'BA025', 250),
('TRJ0112', 'MN001', 'BA026', 20);

--
-- Triggers `log_tr_jual`
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
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `id_menu` varchar(5) NOT NULL,
  `nama_menu` varchar(30) NOT NULL,
  `jenis` enum('Minuman','Makanan','Original Coffee','Falvoured Coffee','Snack') NOT NULL,
  `harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`id_menu`, `nama_menu`, `jenis`, `harga`) VALUES
('MN001', 'Es Jeruk', 'Minuman', 10000),
('MN002', 'Coffee Latee', 'Original Coffee', 10000),
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
('MN021', 'Babi Bakar', 'Makanan', 35000),
('MN022', 'Sate Babi', 'Makanan', 20000),
('MN023', 'Babi Guling', 'Makanan', 350000),
('MN026', 'Ayam Geprek', 'Makanan', 12000);

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `id_supplier` varchar(6) NOT NULL,
  `nama_supplier` varchar(50) NOT NULL,
  `no_telp` varchar(13) NOT NULL,
  `alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supplier`
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
-- Table structure for table `transaksi_beli`
--

CREATE TABLE `transaksi_beli` (
  `id_tr_beli` varchar(7) NOT NULL,
  `id_karyawan` varchar(6) NOT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `id_supplier` varchar(6) NOT NULL,
  `nama_supplier` varchar(50) NOT NULL,
  `total_bahan` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `tanggal` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi_beli`
--

INSERT INTO `transaksi_beli` (`id_tr_beli`, `id_karyawan`, `nama_karyawan`, `id_supplier`, `nama_supplier`, `total_bahan`, `total_harga`, `tanggal`) VALUES
('TRB0001', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 1, 5000, '2022-11-30 00:00:00'),
('TRB0002', 'KY002', 'Mohammad Ilham', 'SP002', 'Afrizal Wahyu Alkautsar', 1, 20000, '2022-12-03 00:00:00'),
('TRB0003', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 6, 90000, '2022-12-03 00:00:00'),
('TRB0004', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 3, 150000, '2022-12-03 00:00:00'),
('TRB0005', 'KY001', 'Achmad Baihaqi', 'SP004', 'Pramudya Putra Pratama', 10, 1200000, '2022-12-03 00:00:00'),
('TRB0006', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 8, 154000, '2022-12-04 01:37:12'),
('TRB0007', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 10, 135000, '2022-12-04 01:48:33'),
('TRB0008', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 4, 54000, '2022-12-04 01:49:46'),
('TRB0009', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 4, 54000, '2022-12-04 01:50:47'),
('TRB0010', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 10, 500000, '2022-12-04 01:51:27'),
('TRB0011', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 12, 202500, '2022-12-04 01:52:14'),
('TRB0012', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 5, 25000, '2022-12-04 01:57:54'),
('TRB0013', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 5, 25000, '2022-12-04 01:58:16'),
('TRB0014', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 5, 25000, '2022-12-04 12:37:29'),
('TRB0015', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 100, 25000, '2022-12-04 15:29:57'),
('TRB0016', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 0, 0, '2022-12-04 15:31:05'),
('TRB0020', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 1, 57500, '2022-12-04 23:21:39'),
('TRB0021', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 0, 0, '2022-12-04 23:22:48'),
('TRB0022', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 2, 115000, '2022-12-04 23:23:05'),
('TRB0023', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 5, 67500, '2022-12-04 23:31:03'),
('TRB0024', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 5, 49000, '2022-12-04 23:32:15'),
('TRB0025', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 4, 46000, '2022-12-04 23:34:19'),
('TRB0026', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 0, 0, '2022-12-05 11:47:00'),
('TRB0027', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 1, 120000, '2022-12-05 11:47:18'),
('TRB0028', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 1, 57500, '2022-12-05 11:48:15'),
('TRB0029', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 1, 20000, '2022-12-05 11:48:41'),
('TRB0030', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 2, 40000, '2022-12-05 13:54:35'),
('TRB0031', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 2, 10000, '2022-12-05 14:36:46'),
('TRB0032', 'KY001', 'Achmad Baihaqi', 'SP003', 'Syamaidzar Adani Syah', 6, 625000, '2022-12-05 14:37:59'),
('TRB0033', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 1, 10000, '2022-12-05 21:42:29'),
('TRB0034', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 5, 50000, '2022-12-05 21:42:54'),
('TRB0035', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 1, 57500, '2022-12-06 15:14:09'),
('TRB0036', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 1, 57500, '2022-12-06 15:14:32'),
('TRB0037', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 1, 57500, '2022-12-06 15:14:56'),
('TRB0038', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 1, 57500, '2022-12-06 15:17:29'),
('TRB0039', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 3, 150000, '2022-12-07 20:51:33'),
('TRB0040', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 3, 150000, '2022-12-07 20:52:58'),
('TRB0041', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 1, 57500, '2022-12-07 20:54:06'),
('TRB0042', 'KY001', 'Achmad Baihaqi', 'SP003', 'Syamaidzar Adani Syah', 1, 120000, '2022-12-08 20:44:10'),
('TRB0043', 'KY001', 'Achmad Baihaqi', 'SP003', 'Syamaidzar Adani Syah', 5, 125000, '2022-12-08 23:00:20'),
('TRB0044', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 15, 130000, '2022-12-10 20:01:09'),
('TRB0045', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 13, 358000, '2022-12-10 20:01:57'),
('TRB0046', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 21, 1345000, '2022-12-10 20:04:42'),
('TRB0047', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 8, 1200000, '2022-12-10 20:05:13'),
('TRB0048', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 41, 217500, '2022-12-10 20:08:14'),
('TRB0049', 'KY001', 'Achmad Baihaqi', 'SP004', 'Pramudya Putra Pratama', 6, 84000, '2022-12-10 20:09:09'),
('TRB0050', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 8, 200000, '2022-12-10 20:11:55'),
('TRB0051', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 0, 0, '2022-12-10 22:31:41'),
('TRB0052', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 2, 6000, '2022-12-15 10:54:41'),
('TRB0053', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 1, 50000, '2022-12-15 17:59:55'),
('TRB0054', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 2, 100000, '2022-12-15 18:03:42'),
('TRB0055', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 3, 60000, '2022-12-15 18:05:30'),
('TRB0056', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 1, 150000, '2022-12-15 18:12:07'),
('TRB0057', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 1, 150000, '2022-12-15 18:12:37'),
('TRB0058', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 2, 50000, '2022-12-15 18:13:25'),
('TRB0059', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 2, 30000, '2022-12-16 14:11:31'),
('TRB0060', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 9, 224000, '2022-12-16 14:12:35'),
('TRB0061', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 3, 15000, '2022-12-16 14:13:05'),
('TRB0062', 'KY001', 'Achmad Baihaqi', 'SP004', 'Pramudya Putra Pratama', 1, 14000, '2022-12-16 19:33:25'),
('TRB0063', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 3, 60000, '2022-12-17 18:31:04'),
('TRB0064', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 4, 46000, '2022-12-17 18:52:10'),
('TRB0065', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 7, 328000, '2022-12-17 19:04:06'),
('TRB0066', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 5, 30000, '2022-12-18 00:16:13'),
('TRB0067', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 12, 612000, '2022-12-18 00:20:30'),
('TRB0068', 'KY001', 'Achmad Baihaqi', 'SP002', 'Afrizal Wahyu Alkautsar', 10, 1200000, '2022-12-18 00:22:10'),
('TRB0069', 'KY001', 'Achmad Baihaqi', 'SP003', 'Syamaidzar Adani Syah', 2, 244500, '2022-12-18 16:02:17'),
('TRB0070', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 14, 50000, '2022-12-19 20:05:38'),
('TRB0071', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 4, 200000, '2022-12-19 23:13:20'),
('TRB0072', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 4, 77000, '2022-12-19 23:46:40'),
('TRB0073', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 6, 157500, '2022-12-19 23:51:37'),
('TRB0074', 'KY001', 'Achmad Baihaqi', 'SP008', 'M. Ferdiansyah', 2, 12000, '2022-12-19 23:52:28'),
('TRB0075', 'KY001', 'Achmad Baihaqi', 'SP003', 'Syamaidzar Adani Syah', 2, 50000, '2022-12-19 23:53:10'),
('TRB0076', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 5, 32000, '2022-12-19 23:57:41'),
('TRB0077', 'KY001', 'Achmad Baihaqi', 'SP002', 'Afrizal Wahyu Alkautsar', 1, 120000, '2022-12-20 00:00:36'),
('TRB0078', 'KY001', 'Achmad Baihaqi', 'SP004', 'Pramudya Putra Pratama', 2, 28000, '2022-12-20 00:01:53'),
('TRB0079', 'KY001', 'Achmad Baihaqi', 'SP003', 'Syamaidzar Adani Syah', 1, 122250, '2022-12-20 00:02:38'),
('TRB0080', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 5, 220500, '2022-12-21 17:55:35'),
('TRB0081', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 23, 505000, '2022-12-21 18:07:38'),
('TRB0082', 'KY001', 'Achmad Baihaqi', 'SP003', 'Syamaidzar Adani Syah', 1, 122250, '2022-12-21 19:34:49'),
('TRB0083', 'KY001', 'Achmad Baihaqi', 'SP001', 'Moch. Alvian Hidayatulloh', 4, 150000, '2022-12-21 19:38:30'),
('TRB0084', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 3, 360000, '2022-12-21 23:03:00'),
('TRB0085', 'KY001', 'Achmad Baihaqi', 'SP002', 'Afrizal Wahyu Alkautsar', 10, 1200000, '2022-12-22 12:06:47'),
('TRB0086', 'KY001', 'Achmad Baihaqi', 'SP003', 'Syamaidzar Adani Syah', 3, 265000, '2022-12-22 13:32:21'),
('TRB0087', 'KY001', 'Achmad Baihaqi', 'SP010', 'Amirzan Fikri Prasetyo', 24, 802500, '2022-12-22 13:32:56'),
('TRB0088', 'KY001', 'Achmad Baihaqi', 'SP003', 'Syamaidzar Adani Syah', 1, 122250, '2022-12-22 13:33:39'),
('TRB0089', 'KY001', 'Achmad Baihaqi', 'SP004', 'Pramudya Putra Pratama', 1, 120000, '2022-12-22 13:34:30'),
('TRB0090', 'KY001', 'Achmad Baihaqi', 'SP004', 'Pramudya Putra Pratama', 1, 14000, '2022-12-23 02:03:15'),
('TRB0091', 'KY001', 'Achmad Baihaqi', 'SP005', 'Syafrizal Wd Mahendra', 1, 10000, '2022-12-23 02:05:45');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_jual`
--

CREATE TABLE `transaksi_jual` (
  `id_tr_jual` varchar(7) NOT NULL,
  `id_karyawan` varchar(6) NOT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `nama_pembeli` varchar(30) NOT NULL,
  `total_menu` int(5) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `tanggal` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi_jual`
--

INSERT INTO `transaksi_jual` (`id_tr_jual`, `id_karyawan`, `nama_karyawan`, `nama_pembeli`, `total_menu`, `total_harga`, `tanggal`) VALUES
('TRJ0001', 'KY001', 'Achmad Baihaqi', '', 2, 20000, '2022-10-31 00:00:00'),
('TRJ0002', 'KY001', 'Achmad Baihaqi', '', 3, 15000, '2022-10-31 00:00:00'),
('TRJ0003', 'KY002', 'Mohammad Ilham', 'paijo', 8, 570000, '2022-11-02 09:34:05'),
('TRJ0006', 'KY001', 'Achmad Baihaqi', 'tes', 2, 20000, '2022-12-07 22:35:40'),
('TRJ0007', 'KY001', 'Achmad Baihaqi', 'tes', 2, 20000, '2022-12-07 22:36:29'),
('TRJ0008', 'KY001', 'Achmad Baihaqi', 'tes', 1, 5000, '2022-12-07 22:37:09'),
('TRJ0009', 'KY001', 'Achmad Baihaqi', 'd', 2, 20000, '2022-12-07 22:38:59'),
('TRJ0010', 'KY001', 'Achmad Baihaqi', 'tes', 1, 5000, '2022-12-07 22:40:46'),
('TRJ0011', 'KY001', 'Achmad Baihaqi', 'tes', 1, 5000, '2022-12-07 22:58:35'),
('TRJ0012', 'KY001', 'Achmad Baihaqi', 'tester', 10, 50000, '2022-12-07 23:00:32'),
('TRJ0013', 'KY001', 'Achmad Baihaqi', 'ilham', 10, 50000, '2022-12-07 23:02:41'),
('TRJ0014', 'KY001', 'Achmad Baihaqi', '', 8, 64000, '2022-12-08 15:03:30'),
('TRJ0015', 'KY001', 'Achmad Baihaqi', '', 6, 90000, '2022-12-08 21:33:44'),
('TRJ0016', 'KY001', 'Achmad Baihaqi', '', 5, 30000, '2022-12-08 21:33:55'),
('TRJ0017', 'KY001', 'Achmad Baihaqi', '', 1, 5000, '2022-12-08 21:35:07'),
('TRJ0018', 'KY001', 'Achmad Baihaqi', 'samsul', 2, 20000, '2022-12-08 22:12:09'),
('TRJ0019', 'KY001', 'Achmad Baihaqi', '', 2, 10000, '2022-12-08 22:13:18'),
('TRJ0020', 'KY001', 'Achmad Baihaqi', '', 4, 30000, '2022-12-08 22:16:47'),
('TRJ0021', 'KY001', 'Achmad Baihaqi', '', 2, 6000, '2022-12-08 22:17:16'),
('TRJ0022', 'KY001', 'Achmad Baihaqi', '', 1, 15000, '2022-12-08 22:17:51'),
('TRJ0023', 'KY001', 'Achmad Baihaqi', '', 30, 420000, '2022-12-08 22:19:38'),
('TRJ0024', 'KY001', 'Achmad Baihaqi', '', 20, 300000, '2022-12-08 22:54:36'),
('TRJ0025', 'KY001', 'Achmad Baihaqi', '', 5, 40000, '2022-12-08 22:55:18'),
('TRJ0026', 'KY001', 'Achmad Baihaqi', '', 2, 30000, '2022-12-08 22:59:18'),
('TRJ0027', 'KY001', 'Achmad Baihaqi', '', 6, 30000, '2022-12-10 11:39:29'),
('TRJ0028', 'KY001', 'Achmad Baihaqi', '', 15, 175000, '2022-12-10 11:40:14'),
('TRJ0029', 'KY001', 'Achmad Baihaqi', '', 40, 950000, '2022-12-10 18:10:11'),
('TRJ0030', 'KY001', 'Achmad Baihaqi', '', 10, 350000, '2022-12-10 18:22:25'),
('TRJ0031', 'KY001', 'Achmad Baihaqi', '', 10, 200000, '2022-12-10 18:23:02'),
('TRJ0032', 'KY001', 'Achmad Baihaqi', '', 5, 100000, '2022-12-10 18:23:35'),
('TRJ0033', 'KY001', 'Achmad Baihaqi', '', 5, 175000, '2022-12-10 18:24:25'),
('TRJ0034', 'KY001', 'Achmad Baihaqi', '', 5, 100000, '2022-12-10 18:24:34'),
('TRJ0035', 'KY001', 'Achmad Baihaqi', '', 50, 541000, '2022-12-10 18:26:11'),
('TRJ0036', 'KY001', 'Achmad Baihaqi', '', 5, 160000, '2022-12-10 20:12:37'),
('TRJ0037', 'KY001', 'Achmad Baihaqi', '', 7, 67000, '2022-12-11 00:14:23'),
('TRJ0038', 'KY001', 'Achmad Baihaqi', 'tester', 6, 393000, '2022-12-11 00:38:43'),
('TRJ0039', 'KY001', 'Achmad Baihaqi', 'burhan', 13, 116000, '2022-12-11 16:21:25'),
('TRJ004', 'KY003', 'Widyasari Raisya', 'tes', 8, 21000, '2022-11-25 10:23:59'),
('TRJ0040', 'KY002', 'Mohammad Ilham', 'ferdi', 10, 81000, '2022-12-11 16:37:23'),
('TRJ0041', 'KY001', 'Achmad Baihaqi', '', 1, 5000, '2022-12-11 19:22:45'),
('TRJ0042', 'KY003', 'Widyasari Raisya', '', 5, 50000, '2022-12-12 12:52:41'),
('TRJ0043', 'KY003', 'Widyasari Raisya', 'erdi', 7, 80000, '2022-12-12 12:53:39'),
('TRJ0044', 'KY002', 'Mohammad Ilham', '', 4, 32000, '2022-12-12 12:54:13'),
('TRJ0045', 'KY002', 'Mohammad Ilham', '', 2, 10000, '2022-12-12 12:56:29'),
('TRJ0046', 'KY001', 'Achmad Baihaqi', '', 6, 81000, '2022-12-12 13:33:58'),
('TRJ0047', 'KY001', 'Achmad Baihaqi', '', 9, 41000, '2022-12-12 13:36:31'),
('TRJ0048', 'KY001', 'Achmad Baihaqi', '', 4, 60000, '2022-12-13 09:59:17'),
('TRJ0049', 'KY001', 'Achmad Baihaqi', '', 2, 20000, '2022-12-13 13:04:43'),
('TRJ0050', 'KY001', 'Achmad Baihaqi', '', 14, 161000, '2022-12-14 11:04:34'),
('TRJ0051', 'KY001', 'Achmad Baihaqi', '', 8, 75000, '2022-12-14 12:09:38'),
('TRJ0052', 'KY001', 'Achmad Baihaqi', '', 3, 15, '2022-12-14 14:42:22'),
('TRJ0053', 'KY001', 'Achmad Baihaqi', '', 23, 293000, '2022-12-14 14:48:43'),
('TRJ0054', 'KY001', 'Achmad Baihaqi', '', 5, 50000, '2022-12-15 00:47:31'),
('TRJ0055', 'KY001', 'Achmad Baihaqi', '', 12, 126000, '2022-12-15 10:53:30'),
('TRJ0056', 'KY001', 'Achmad Baihaqi', '', 5, 60000, '2022-12-15 12:33:49'),
('TRJ0057', 'KY001', 'Achmad Baihaqi', '', 12, 105000, '2022-12-15 13:15:22'),
('TRJ0058', 'KY001', 'Achmad Baihaqi', '', 9, 55000, '2022-12-15 13:20:40'),
('TRJ0059', 'KY001', 'Achmad Baihaqi', '', 12, 121000, '2022-12-15 13:21:25'),
('TRJ0060', 'KY001', 'Achmad Baihaqi', '', 8, 78000, '2022-12-15 16:56:20'),
('TRJ0061', 'KY001', 'Achmad Baihaqi', '', 1, 50000, '2022-12-15 17:02:33'),
('TRJ0062', 'KY001', 'Achmad Baihaqi', '', 1, 7, '2022-12-15 17:05:56'),
('TRJ0063', 'KY001', 'Achmad Baihaqi', '', 1, 5, '2022-12-15 17:26:31'),
('TRJ0064', 'KY001', 'Achmad Baihaqi', '', 9, 135000, '2022-12-15 17:40:44'),
('TRJ0065', 'KY001', 'Achmad Baihaqi', '', 2, 24000, '2022-12-15 20:05:02'),
('TRJ0066', 'KY001', 'Achmad Baihaqi', '', 13, 159000, '2022-12-16 10:50:35'),
('TRJ0067', 'KY001', 'Achmad Baihaqi', '', 8, 90000, '2022-12-16 13:29:07'),
('TRJ0068', 'KY001', 'Achmad Baihaqi', '', 11, 134000, '2022-12-16 13:53:49'),
('TRJ0069', 'KY001', 'Achmad Baihaqi', '', 9, 116000, '2022-12-17 12:29:40'),
('TRJ0070', 'KY001', 'Achmad Baihaqi', '', 5, 56000, '2022-12-17 18:19:06'),
('TRJ0071', 'KY001', 'Achmad Baihaqi', '', 2, 20000, '2022-12-17 18:22:23'),
('TRJ0072', 'KY001', 'Achmad Baihaqi', '', 10, 106000, '2022-12-17 18:30:36'),
('TRJ0073', 'KY001', 'Achmad Baihaqi', '', 6, 120000, '2022-12-18 00:36:48'),
('TRJ0074', 'KY001', 'Achmad Baihaqi', 'pembeli', 1, 15000, '2022-12-18 00:38:04'),
('TRJ0075', 'KY001', 'Achmad Baihaqi', '', 1, 10000, '2022-12-18 20:47:45'),
('TRJ0076', 'KY001', 'Achmad Baihaqi', 'mirzan', 3, 39000, '2022-12-18 20:50:08'),
('TRJ0077', 'KY001', 'Achmad Baihaqi', '', 20, 209000, '2022-12-18 21:01:28'),
('TRJ0078', 'KY001', 'Achmad Baihaqi', '', 4, 58000, '2022-12-19 15:05:28'),
('TRJ0079', 'KY001', 'Achmad Baihaqi', '', 9, 98000, '2022-12-19 15:11:55'),
('TRJ0080', 'KY001', 'Achmad Baihaqi', '', 18, 201000, '2022-12-19 20:06:34'),
('TRJ0081', 'KY001', 'Achmad Baihaqi', '', 3, 39000, '2022-12-19 23:04:26'),
('TRJ0082', 'KY001', 'Achmad Baihaqi', '', 6, 75000, '2022-12-19 23:08:05'),
('TRJ0083', 'KY001', 'Achmad Baihaqi', '', 18, 222000, '2022-12-19 23:11:40'),
('TRJ0084', 'KY001', 'Achmad Baihaqi', '', 15, 170000, '2022-12-19 23:12:43'),
('TRJ0085', 'KY001', 'Achmad Baihaqi', '', 4, 48000, '2022-12-20 00:06:46'),
('TRJ0086', 'KY001', 'Achmad Baihaqi', '', 5, 50000, '2022-12-20 00:07:48'),
('TRJ0087', 'KY001', 'Achmad Baihaqi', 'hjkkh', 5, 50000, '2022-12-20 00:08:15'),
('TRJ0088', 'KY001', 'Achmad Baihaqi', '', 5, 50000, '2022-12-20 00:09:02'),
('TRJ0089', 'KY001', 'Achmad Baihaqi', '', 8, 99000, '2022-12-20 00:09:56'),
('TRJ0090', 'KY001', 'Achmad Baihaqi', '', 3, 39000, '2022-12-20 00:11:29'),
('TRJ0091', 'KY001', 'Achmad Baihaqi', '', 5, 75000, '2022-12-20 00:12:32'),
('TRJ0092', 'KY001', 'Achmad Baihaqi', '', 10, 110000, '2022-12-20 12:17:35'),
('TRJ0093', 'KY001', 'Achmad Baihaqi', '', 1, 350000, '2022-12-20 12:18:23'),
('TRJ0094', 'KY001', 'Achmad Baihaqi', 'pembeli', 10, 124000, '2022-12-21 18:01:52'),
('TRJ0095', 'KY001', 'Achmad Baihaqi', '', 1, 15000, '2022-12-21 19:07:26'),
('TRJ0096', 'KY001', 'Achmad Baihaqi', '', 2, 23000, '2022-12-21 19:17:06'),
('TRJ0097', 'KY001', 'Achmad Baihaqi', '', 1, 12000, '2022-12-21 19:19:30'),
('TRJ0098', 'KY001', 'Achmad Baihaqi', '', 1, 15000, '2022-12-21 19:19:41'),
('TRJ0099', 'KY001', 'Achmad Baihaqi', '', 1, 15000, '2022-12-21 19:21:53'),
('TRJ0100', 'KY001', 'Achmad Baihaqi', '', 1, 12000, '2022-12-21 19:26:33'),
('TRJ0101', 'KY001', 'Achmad Baihaqi', '', 4, 49000, '2022-12-21 19:27:26'),
('TRJ0102', 'KY001', 'Achmad Baihaqi', '', 1, 10000, '2022-12-21 19:31:58'),
('TRJ0103', 'KY001', 'Achmad Baihaqi', '', 1, 8000, '2022-12-21 19:34:11'),
('TRJ0104', 'KY001', 'Achmad Baihaqi', '', 1, 10000, '2022-12-21 19:37:49'),
('TRJ0105', 'KY001', 'Achmad Baihaqi', 'langanan', 2, 25000, '2022-12-21 19:38:08'),
('TRJ0106', 'KY001', 'Achmad Baihaqi', 'pembeli', 1, 10000, '2022-12-21 22:54:07'),
('TRJ0107', 'KY001', 'Achmad Baihaqi', '', 5, 50000, '2022-12-21 22:57:00'),
('TRJ0108', 'KY001', 'Achmad Baihaqi', 'afjals', 1, 13000, '2022-12-21 22:57:20'),
('TRJ0109', 'KY001', 'Achmad Baihaqi', '', 2, 30000, '2022-12-22 12:00:45'),
('TRJ0110', 'KY001', 'Achmad Baihaqi', '', 1, 12000, '2022-12-22 23:56:36'),
('TRJ0111', 'KY001', 'Achmad Baihaqi', 'bang jago', 1, 15000, '2022-12-23 02:00:44'),
('TRJ0112', 'KY001', 'Achmad Baihaqi', '', 1, 10000, '2022-12-23 02:05:29');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `password` varchar(70) NOT NULL,
  `level` enum('ADMIN','KARYAWAN') NOT NULL,
  `id_karyawan` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `password`, `level`, `id_karyawan`) VALUES
('ADMIN', '$2a$12$eQlUUAjkZ3Ie8URyGJb2kuNQpcdYTBSD5IWg6HIdBjWNkbgZEEJQK', 'ADMIN', 'KY001'),
('ilham', '$2a$12$G9rldUjX0SjYYn169mMO3uLoRJOnlYKggCG5nojWOeOYSFxRnckNm', 'KARYAWAN', 'KY002'),
('karyawan', '$2a$12$PfkP7PfYVELbBcNfnDyVguv7D74dzYY3PvdPpJgUxRC91PmSS3sTa', 'KARYAWAN', 'KY005'),
('widya', '$2a$12$a2hZcCF4Of0r65cL1ef4/.GVnaCWf6/S0Z//MGtJLnSrpZaiJ7saK', 'KARYAWAN', 'KY003'),
('yoga', '$2a$12$RAQpKW3nqB41R1F3rnXO7ujyHudLVQ4N/DxqmjYQPLqLLCTb0ylvW', 'KARYAWAN', 'KY004');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bahan`
--
ALTER TABLE `bahan`
  ADD PRIMARY KEY (`id_bahan`);

--
-- Indexes for table `detail_menu`
--
ALTER TABLE `detail_menu`
  ADD KEY `id_menu` (`id_menu`,`id_bahan`),
  ADD KEY `id_bahan` (`id_bahan`);

--
-- Indexes for table `detail_supplier`
--
ALTER TABLE `detail_supplier`
  ADD KEY `id_supplier` (`id_supplier`),
  ADD KEY `id_bahan` (`id_bahan`);

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
-- Indexes for table `log_tr_beli`
--
ALTER TABLE `log_tr_beli`
  ADD KEY `id_tr_beli` (`id_tr_beli`),
  ADD KEY `id_bahan` (`id_bahan`);

--
-- Indexes for table `log_tr_jual`
--
ALTER TABLE `log_tr_jual`
  ADD KEY `id_tr_jual` (`id_tr_jual`),
  ADD KEY `id_menu` (`id_menu`),
  ADD KEY `id_bahan` (`id_bahan`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id_menu`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id_supplier`);

--
-- Indexes for table `transaksi_beli`
--
ALTER TABLE `transaksi_beli`
  ADD PRIMARY KEY (`id_tr_beli`),
  ADD KEY `id_karyawan` (`id_karyawan`),
  ADD KEY `id_supplier` (`id_supplier`);

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
  ADD PRIMARY KEY (`username`),
  ADD KEY `id_karyawan` (`id_karyawan`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_menu`
--
ALTER TABLE `detail_menu`
  ADD CONSTRAINT `detail_menu_ibfk_1` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id_menu`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_menu_ibfk_2` FOREIGN KEY (`id_bahan`) REFERENCES `bahan` (`id_bahan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `detail_supplier`
--
ALTER TABLE `detail_supplier`
  ADD CONSTRAINT `detail_supplier_ibfk_1` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_supplier_ibfk_2` FOREIGN KEY (`id_bahan`) REFERENCES `bahan` (`id_bahan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `detail_tr_beli`
--
ALTER TABLE `detail_tr_beli`
  ADD CONSTRAINT `detail_tr_beli_ibfk_1` FOREIGN KEY (`id_tr_beli`) REFERENCES `transaksi_beli` (`id_tr_beli`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_tr_beli_ibfk_2` FOREIGN KEY (`id_bahan`) REFERENCES `bahan` (`id_bahan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `detail_tr_jual`
--
ALTER TABLE `detail_tr_jual`
  ADD CONSTRAINT `detail_tr_jual_ibfk_1` FOREIGN KEY (`id_tr_jual`) REFERENCES `transaksi_jual` (`id_tr_jual`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_tr_jual_ibfk_2` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id_menu`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `log_tr_beli`
--
ALTER TABLE `log_tr_beli`
  ADD CONSTRAINT `log_tr_beli_ibfk_1` FOREIGN KEY (`id_tr_beli`) REFERENCES `transaksi_beli` (`id_tr_beli`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `log_tr_beli_ibfk_2` FOREIGN KEY (`id_bahan`) REFERENCES `bahan` (`id_bahan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `log_tr_jual`
--
ALTER TABLE `log_tr_jual`
  ADD CONSTRAINT `log_tr_jual_ibfk_1` FOREIGN KEY (`id_tr_jual`) REFERENCES `transaksi_jual` (`id_tr_jual`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `log_tr_jual_ibfk_2` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id_menu`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `log_tr_jual_ibfk_3` FOREIGN KEY (`id_bahan`) REFERENCES `bahan` (`id_bahan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaksi_beli`
--
ALTER TABLE `transaksi_beli`
  ADD CONSTRAINT `transaksi_beli_ibfk_1` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaksi_beli_ibfk_2` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaksi_jual`
--
ALTER TABLE `transaksi_jual`
  ADD CONSTRAINT `transaksi_jual_ibfk_2` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`id_karyawan`) REFERENCES `karyawan` (`id_karyawan`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
