-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: mysql
-- Thời gian đã tạo: Th10 16, 2024 lúc 04:03 PM
-- Phiên bản máy phục vụ: 8.0.39
-- Phiên bản PHP: 8.2.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `shopme`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `currencies`
--

CREATE TABLE `currencies` (
  `id` bigint NOT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `symbol` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `code` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `currencies`
--

INSERT INTO `currencies` (`id`, `name`, `symbol`, `code`) VALUES
(1, 'United States Dollar', '$', 'USD'),
(2, 'British Pound', '£', 'GPB'),
(3, 'Japanese Yen', '¥', 'JPY'),
(4, 'Euro', '€', 'EUR'),
(5, 'Russian Ruble', '₽', 'RUB'),
(6, 'South Korean Won', '₩', 'KRW'),
(7, 'Chinese Yuan', '¥', 'CNY'),
(8, 'Brazilian Real', 'R$', 'BRL'),
(9, 'Australian Dollar', '$', 'AUD'),
(10, 'Canadian Dollar', '$', 'CAD'),
(11, 'Vietnamese đồng ', '₫', 'VND'),
(12, 'Indian Rupee', '₹', 'INR');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `settings`
--

CREATE TABLE `settings` (
  `key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `value` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `category` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `settings`
--

INSERT INTO `settings` (`key`, `value`, `category`) VALUES
('COPYRIGHT', 'Copyright (C) 2021 Shopme Ltd.', 'GENERAL'),
('CURRENCY_ID', '1', 'CURRENCY'),
('CURRENCY_SYMBOL', '$', 'CURRENCY'),
('CURRENCY_SYMBOL_POSITION', 'before', 'CURRENCY'),
('DECIMAL_DIGITS', '2', 'CURRENCY'),
('DECIMAL_POINT_TYPE', 'POINT', 'CURRENCY'),
('SITE_LOGO', 'Shopme.png', 'GENERAL'),
('SITE_NAME', 'Shopme', 'GENERAL'),
('THOUSANDS_POINT_TYPE', 'COMMA', 'CURRENCY');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `currencies`
--
ALTER TABLE `currencies`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `settings`
--
ALTER TABLE `settings`
  ADD PRIMARY KEY (`key`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `currencies`
--
ALTER TABLE `currencies`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
