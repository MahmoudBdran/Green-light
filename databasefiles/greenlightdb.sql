-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 24, 2024 at 02:37 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `greenlightdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `current_balance` decimal(38,2) DEFAULT NULL,
  `is_parent` bit(1) DEFAULT NULL,
  `name` varchar(225) NOT NULL,
  `notes` varchar(225) DEFAULT NULL,
  `start_balance` decimal(38,2) DEFAULT NULL,
  `start_balance_status` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `account_type` bigint(20) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `parent_account_number` bigint(20) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`id`, `active`, `created_at`, `current_balance`, `is_parent`, `name`, `notes`, `start_balance`, `start_balance_status`, `updated_at`, `account_type`, `added_by`, `parent_account_number`, `updated_by`) VALUES
(9, b'1', '2024-06-30 12:00:00.000000', 0.00, b'1', 'الموردين الأب', NULL, 0.00, 3, '2024-06-30 12:00:00.000000', 9, 1, NULL, 1),
(10, b'1', '2024-06-30 12:00:24.000000', 0.00, b'1', 'العملاء الأب', 'ok', 0.00, 3, '2024-06-30 12:00:24.000000', 9, 1, NULL, 1),
(11, b'1', '2024-06-30 12:20:48.000000', 125.00, b'0', 'محمد طلعت', NULL, 0.00, 3, '2024-07-13 20:31:01.000000', 3, 1, 10, 1),
(12, b'1', '2024-06-30 12:21:16.000000', 4885.00, b'0', 'احمد علي', NULL, 0.00, 3, '2024-07-13 20:06:22.000000', 2, 1, 10, 1),
(13, b'1', '2024-06-30 19:32:35.000000', 0.00, b'0', 'السيد حسن', NULL, 0.00, 3, '2024-06-30 19:32:35.000000', 2, 1, 10, 1),
(14, b'1', '2024-06-30 20:11:14.000000', 0.00, b'0', 'محمد هشام', 'خن', 0.00, 3, '2024-06-30 20:11:14.000000', 3, 1, 10, 1),
(15, b'1', '2024-07-12 20:16:42.000000', 0.00, b'0', 'ابراهيم السيد', NULL, 0.00, 3, '2024-07-12 20:16:42.000000', 2, 1, 10, 1),
(16, b'1', '2024-07-12 20:20:41.000000', 0.00, b'0', 'محسن', NULL, 0.00, 3, '2024-07-12 20:20:41.000000', 2, 1, 10, 1),
(17, b'1', '2024-07-13 20:02:56.000000', 0.00, b'0', 'test', 'my note', 0.00, 3, '2024-07-13 20:02:56.000000', 2, 1, 10, 1);

-- --------------------------------------------------------

--
-- Table structure for table `account_types`
--

CREATE TABLE `account_types` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `relatediternalaccounts` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `account_types`
--

INSERT INTO `account_types` (`id`, `active`, `name`, `relatediternalaccounts`) VALUES
(1, b'1', 'رأس المال', b'0'),
(2, b'1', 'مورد', b'1'),
(3, b'1', 'عميل', b'1'),
(4, b'1', 'مندوب', b'1'),
(5, b'1', 'موظف', b'1'),
(6, b'1', 'بنكي', b'0'),
(7, b'1', 'مصروفات', b'0'),
(8, b'1', 'قسم داخلي', b'1'),
(9, b'1', 'عام', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `id` int(11) NOT NULL,
  `added_by` int(11) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(225) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  `is_account_non_expired` bit(1) NOT NULL,
  `is_account_non_locked` bit(1) NOT NULL,
  `is_credentials_non_expired` bit(1) NOT NULL,
  `is_enabled` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`id`, `added_by`, `created_at`, `email`, `name`, `password`, `updated_at`, `updated_by`, `username`, `is_account_non_expired`, `is_account_non_locked`, `is_credentials_non_expired`, `is_enabled`) VALUES
(1, 1, '2024-06-30 12:16:45.000000', 'admin1@yourdomain.com', 'admin', '$2a$10$SQQnd6F.YfEgQU855y37t.k3oMWgt4pRGKlzjXRB4xyrf8du/RoUe', NULL, NULL, 'admin', b'1', b'1', b'1', b'1'),
(2, 2, '2024-06-30 12:16:51.000000', 'admin2@yourdomain.com', 'Jane Smith', 'temp_password123', NULL, NULL, 'admin_jane', b'0', b'0', b'0', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `admins_shifts`
--

CREATE TABLE `admins_shifts` (
  `id` bigint(20) NOT NULL,
  `added_by` int(11) NOT NULL,
  `admin_id` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `date` date NOT NULL,
  `delivered_to_admin_id` int(11) DEFAULT NULL,
  `delivered_to_admin_sift_id` bigint(20) DEFAULT NULL,
  `delivered_to_treasuries_id` int(11) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `is_delivered_and_review` bit(1) DEFAULT NULL,
  `is_finished` bit(1) DEFAULT NULL,
  `money_should_deviled` decimal(10,2) DEFAULT NULL,
  `money_state` int(11) DEFAULT NULL,
  `money_state_value` decimal(10,2) DEFAULT NULL,
  `notes` varchar(100) DEFAULT NULL,
  `receive_type` int(11) DEFAULT NULL,
  `review_receive_date` datetime(6) DEFAULT NULL,
  `shift_code` bigint(20) NOT NULL,
  `start_date` datetime(6) NOT NULL,
  `treasuries_balnce_in_shift_start` decimal(10,2) NOT NULL,
  `treasuries_id` int(11) NOT NULL,
  `treasuries_transactions_id` bigint(20) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `what_realy_delivered` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `admins_treasuries`
--

CREATE TABLE `admins_treasuries` (
  `id` int(11) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `added_by` int(11) NOT NULL,
  `admin_id` bigint(20) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `date` date NOT NULL,
  `treasuries_id` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `admin_panel_settings`
--

CREATE TABLE `admin_panel_settings` (
  `id` int(11) NOT NULL,
  `active` bit(1) NOT NULL,
  `address` varchar(255) NOT NULL,
  `customer_parent_account_number` bigint(20) NOT NULL,
  `general_alert` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) NOT NULL,
  `suppliers_parent_account_number` bigint(20) NOT NULL,
  `system_name` varchar(255) NOT NULL,
  `added_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin_panel_settings`
--

INSERT INTO `admin_panel_settings` (`id`, `active`, `address`, `customer_parent_account_number`, `general_alert`, `notes`, `phone`, `photo`, `suppliers_parent_account_number`, `system_name`, `added_by`) VALUES
(1, b'1', 'asd', 10, NULL, NULL, '1111111111111111111111111111', 'sdf', 9, 'Green Light', 1);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `address` varchar(250) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `current_balance` decimal(38,2) DEFAULT NULL,
  `name` varchar(225) DEFAULT NULL,
  `notes` varchar(225) DEFAULT NULL,
  `phones` varchar(50) DEFAULT NULL,
  `start_balance` decimal(38,2) DEFAULT NULL,
  `start_balance_status` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `account_id` bigint(20) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `active`, `address`, `created_at`, `current_balance`, `name`, `notes`, `phones`, `start_balance`, `start_balance_status`, `updated_at`, `account_id`, `added_by`, `updated_by`) VALUES
(3, b'1', 'طنطا', '2024-06-30 12:20:48.000000', 125.00, 'محمد طلعت', NULL, NULL, 0.00, 3, '2024-07-13 20:31:01.000000', 11, 1, 1),
(4, b'1', 'الجيزة', '2024-06-30 20:11:14.000000', 0.00, 'محمد هشام', 'خن', '0231321321', 0.00, 3, '2024-06-30 20:11:14.000000', 14, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `delegate`
--

CREATE TABLE `delegate` (
  `id` bigint(20) NOT NULL,
  `account_number` bigint(20) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `com_code` int(11) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `current_balance` decimal(38,2) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `delegate_code` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `percent_collect_commission` decimal(38,2) DEFAULT NULL,
  `percent_salaes_commission_jomla` decimal(38,2) DEFAULT NULL,
  `percent_salaes_commission_kataei` decimal(38,2) DEFAULT NULL,
  `percent_salaes_commission_nosjomla` decimal(38,2) DEFAULT NULL,
  `percent_type` int(11) DEFAULT NULL,
  `phones` varchar(255) DEFAULT NULL,
  `start_balance` decimal(38,2) DEFAULT NULL,
  `start_balance_status` int(11) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `expenses`
--

CREATE TABLE `expenses` (
  `id` bigint(20) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `category` varchar(100) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `expense_date` date NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `project_id` bigint(20) NOT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `expenses`
--

INSERT INTO `expenses` (`id`, `amount`, `category`, `created_at`, `description`, `expense_date`, `updated_at`, `created_by`, `project_id`, `updated_by`) VALUES
(1, 1500.00, 'Food', '2024-07-30 22:19:30.000000', 'Food for workers', '2024-07-30', '2024-07-30 22:19:30.000000', 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `failed_jobs`
--

CREATE TABLE `failed_jobs` (
  `id` bigint(20) NOT NULL,
  `connection` text NOT NULL,
  `exception` longtext NOT NULL,
  `failed_at` datetime(6) NOT NULL,
  `payload` longtext NOT NULL,
  `queue` text NOT NULL,
  `uuid` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `inv_itemcard`
--

CREATE TABLE `inv_itemcard` (
  `id` bigint(20) NOT NULL,
  `quentity` decimal(38,2) DEFAULT NULL,
  `quentityall_retails` decimal(38,2) DEFAULT NULL,
  `quentityretail` decimal(38,2) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `allquentity` decimal(38,2) DEFAULT NULL,
  `barcode` varchar(50) DEFAULT NULL,
  `cost_price` decimal(38,2) NOT NULL,
  `cost_price_retail` decimal(38,2) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `does_has_retailunit` bit(1) NOT NULL,
  `gomla_price` decimal(38,2) DEFAULT NULL,
  `gomla_price_retail` decimal(38,2) DEFAULT NULL,
  `has_fixced_price` bit(1) NOT NULL,
  `item_type` int(11) NOT NULL,
  `name` varchar(225) NOT NULL,
  `nos_gomla_price` decimal(38,2) NOT NULL,
  `nos_gomla_price_retail` decimal(38,2) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `price` decimal(38,2) NOT NULL,
  `price_retail` decimal(38,2) DEFAULT NULL,
  `retail_uom_qunt_to_parent` decimal(38,2) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `inv_item_category_id` bigint(20) NOT NULL,
  `parent_inv_item_id` bigint(20) DEFAULT NULL,
  `retail_uom_id` bigint(20) DEFAULT NULL,
  `uom_id` bigint(20) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inv_itemcard`
--

INSERT INTO `inv_itemcard` (`id`, `quentity`, `quentityall_retails`, `quentityretail`, `active`, `allquentity`, `barcode`, `cost_price`, `cost_price_retail`, `created_at`, `does_has_retailunit`, `gomla_price`, `gomla_price_retail`, `has_fixced_price`, `item_type`, `name`, `nos_gomla_price`, `nos_gomla_price_retail`, `photo`, `price`, `price_retail`, `retail_uom_qunt_to_parent`, `updated_at`, `added_by`, `inv_item_category_id`, `parent_inv_item_id`, `retail_uom_id`, `uom_id`, `updated_by`) VALUES
(5, 21.00, 0.00, 0.00, b'1', 21.00, NULL, 50.00, NULL, NULL, b'0', 40.00, NULL, b'0', 1, 'سلك نحاس 2 مم', 45.00, NULL, NULL, 50.00, NULL, NULL, '2024-07-03 12:43:59.000000', 1, 3, NULL, NULL, 1, 1),
(7, 3.75, 75.00, 15.00, b'1', 3.75, NULL, 1000.00, 50.00, '2024-07-03 13:50:44.000000', b'1', 900.00, 40.00, b'0', 1, 'شاسيه سبوت دائري ابيض', 950.00, 45.00, NULL, 1000.00, 50.00, 20.00, '2024-07-12 20:38:46.000000', 1, 2, NULL, 2, 1, 1),
(8, 0.00, NULL, NULL, b'1', 0.00, NULL, 3.00, NULL, '2024-07-13 20:14:57.000000', b'0', 9.00, NULL, b'0', 1, 'بواط', 5.00, NULL, NULL, 10.00, NULL, NULL, '2024-07-13 20:14:57.000000', 1, 4, NULL, NULL, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `inv_itemcard_categories`
--

CREATE TABLE `inv_itemcard_categories` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `name` varchar(250) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inv_itemcard_categories`
--

INSERT INTO `inv_itemcard_categories` (`id`, `active`, `created_at`, `name`, `updated_at`, `added_by`, `updated_by`) VALUES
(1, b'1', NULL, 'مسامير', NULL, NULL, NULL),
(2, b'1', NULL, 'معدات', NULL, NULL, NULL),
(3, b'1', NULL, 'مصابيح ليد', NULL, NULL, NULL),
(4, b'0', NULL, 'كهربا', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `inv_itemcard_movement`
--

CREATE TABLE `inv_itemcard_movement` (
  `id` bigint(20) NOT NULL,
  `byan` varchar(255) DEFAULT NULL,
  `quantity_after_move` varchar(255) DEFAULT NULL,
  `quantity_after_move_store` varchar(255) DEFAULT NULL,
  `quantity_befor_move_store` varchar(255) DEFAULT NULL,
  `quantity_befor_movement` varchar(255) DEFAULT NULL,
  `inv_itemcard_movements_category_id` int(11) DEFAULT NULL,
  `items_movements_type_id` int(11) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inv_itemcard_movement`
--

INSERT INTO `inv_itemcard_movement` (`id`, `byan`, `quantity_after_move`, `quantity_after_move_store`, `quantity_befor_move_store`, `quantity_befor_movement`, `inv_itemcard_movements_category_id`, `items_movements_type_id`, `item_id`, `store_id`) VALUES
(3, 'نظير مشتريات من المورد  احمد علي فاتورة رقم 1', 'عدد  20.00 كرتونة', 'عدد  20.00 كرتونة', 'عدد  null كرتونة', 'عدد  null كرتونة', 1, 1, 5, 1),
(4, 'نظير مبيعات  للعميل  محمد طلعت فاتورة رقم 1', 'عدد  19.00 كرتونة', 'عدد  19.00 كرتونة', 'عدد  20.00 كرتونة', 'عدد  20.00 كرتونة', 2, 4, 5, 1),
(5, 'نظير مبيعات  للعميل  محمد هشام فاتورة رقم 3', 'عدد  17.00 كرتونة', 'عدد  17.00 كرتونة', 'عدد  19.00 كرتونة', 'عدد  19.00 كرتونة', 2, 4, 5, 1),
(9, 'نظير مبيعات  للعميل  محمد هشام فاتورة رقم 3', 'عدد  19.00 كرتونة', 'عدد  19.00 كرتونة', 'عدد  17.00 كرتونة', 'عدد  17.00 كرتونة', 2, 16, 5, 1),
(10, 'نظير مشتريات من المورد  احمد علي فاتورة رقم 2', 'عدد  20.00 كرتونة', 'عدد  20.00 كرتونة', 'عدد  19.00 كرتونة', 'عدد  19.00 كرتونة', 1, 1, 5, 1),
(11, 'نظير مشتريات من المورد  احمد علي فاتورة رقم 7', 'عدد  23.00 كرتونة', 'عدد  23.00 كرتونة', 'عدد  20.00 كرتونة', 'عدد  20.00 كرتونة', 1, 1, 5, 1),
(12, 'نظير مشتريات من المورد  احمد علي فاتورة رقم 8', 'عدد  23.00 كرتونة', 'عدد  23.00 كرتونة', 'عدد  23.00 كرتونة', 'عدد  23.00 كرتونة', 1, 1, 5, 1),
(13, 'نظير مرتجع مشتريات عام الي المورد السيد حسن فاتورة رقم 5', 'عدد  22.00 كرتونة', 'عدد  22.00 كرتونة', 'عدد  23.00 كرتونة', 'عدد  23.00 كرتونة', 1, 3, 5, 1),
(14, 'نظير مرتجع مشتريات عام الي المورد السيد حسن فاتورة رقم 5', 'عدد  21.00 كرتونة', 'عدد  21.00 كرتونة', 'عدد  22.00 كرتونة', 'عدد  22.00 كرتونة', 1, 3, 5, 1),
(15, ' نظير حذف سطر الصنف من فاتورة مرتجع مشتريات عام   الي المورد  السيد حسن فاتورة رقم 5', 'عدد  22.00 كرتونة', 'عدد  22.00 كرتونة', 'عدد  21.00 كرتونة', 'عدد  21.00 كرتونة', 1, 3, 5, 1),
(16, 'نظير مرتجع مشتريات عام الي المورد السيد حسن فاتورة رقم 5', 'عدد  21.00 كرتونة', 'عدد  21.00 كرتونة', 'عدد  22.00 كرتونة', 'عدد  22.00 كرتونة', 1, 3, 5, 1),
(17, ' نظير حذف سطر الصنف من فاتورة مرتجع مشتريات عام   الي المورد  السيد حسن فاتورة رقم 5', 'عدد  22.00 كرتونة', 'عدد  22.00 كرتونة', 'عدد  21.00 كرتونة', 'عدد  21.00 كرتونة', 1, 3, 5, 1),
(18, 'نظير مرتجع مشتريات عام الي المورد السيد حسن فاتورة رقم 5', 'عدد  21.00 كرتونة', 'عدد  21.00 كرتونة', 'عدد  22.00 كرتونة', 'عدد  22.00 كرتونة', 1, 3, 5, 1),
(19, ' نظير حذف سطر الصنف من فاتورة مرتجع مشتريات عام   الي المورد  السيد حسن فاتورة رقم 5', 'عدد  22.00 كرتونة', 'عدد  22.00 كرتونة', 'عدد  21.00 كرتونة', 'عدد  21.00 كرتونة', 1, 3, 5, 1),
(20, 'نظير مرتجع مشتريات عام الي المورد السيد حسن فاتورة رقم 5', 'عدد  21.00 كرتونة', 'عدد  21.00 كرتونة', 'عدد  22.00 كرتونة', 'عدد  22.00 كرتونة', 1, 3, 5, 1),
(21, ' نظير حذف سطر الصنف من فاتورة مرتجع مشتريات عام   الي المورد  السيد حسن فاتورة رقم 5', 'عدد  22.00 كرتونة', 'عدد  22.00 كرتونة', 'عدد  21.00 كرتونة', 'عدد  21.00 كرتونة', 1, 3, 5, 1),
(22, 'نظير مرتجع مشتريات عام الي المورد السيد حسن فاتورة رقم 5', 'عدد  21.00 كرتونة', 'عدد  21.00 كرتونة', 'عدد  22.00 كرتونة', 'عدد  22.00 كرتونة', 1, 3, 5, 1),
(23, ' نظير حذف سطر الصنف من فاتورة مرتجع مشتريات عام   الي المورد  السيد حسن فاتورة رقم 5', 'عدد  22.00 كرتونة', 'عدد  22.00 كرتونة', 'عدد  21.00 كرتونة', 'عدد  21.00 كرتونة', 1, 3, 5, 1),
(24, 'نظير مرتجع مشتريات عام الي المورد احمد علي فاتورة رقم 10', 'عدد  21.00 كرتونة', 'عدد  21.00 كرتونة', 'عدد  22.00 كرتونة', 'عدد  22.00 كرتونة', 1, 3, 5, 1),
(27, 'نظير مبيعات  للعميل  محمد هشام فاتورة رقم 4', 'عدد  20.00 كرتونة', 'عدد  20.00 كرتونة', 'عدد  21.00 كرتونة', 'عدد  21.00 كرتونة', 2, 4, 5, 1),
(28, 'نظير مبيعات  للعميل  محمد طلعت فاتورة رقم 1', 'عدد  21.00 كرتونة', 'عدد  21.00 كرتونة', 'عدد  20.00 كرتونة', 'عدد  20.00 كرتونة', 2, 4, 5, 1),
(29, 'نظير مبيعات  للعميل  محمد طلعت فاتورة رقم 1', 'عدد  20.00 كرتونة', 'عدد  20.00 كرتونة', 'عدد  21.00 كرتونة', 'عدد  21.00 كرتونة', 2, 4, 5, 1),
(30, 'نظير مبيعات  للعميل  محمد هشام فاتورة رقم 4', 'عدد  21.00 كرتونة', 'عدد  21.00 كرتونة', 'عدد  20.00 كرتونة', 'عدد  20.00 كرتونة', 2, 16, 5, 1),
(31, 'نظير مشتريات من المورد  احمد علي فاتورة رقم 13', 'عدد  2.00 كرتونة', 'عدد  2.00 كرتونة', 'عدد  null كرتونة', 'عدد  null كرتونة', 1, 1, 7, 1),
(32, 'نظير مبيعات  للعميل  محمد طلعت فاتورة رقم 7', 'عدد  1.95 علبة', 'عدد  1.95 علبة', 'عدد  2.00 علبة', 'عدد  2.00 علبة', 2, 4, 7, 1),
(33, 'نظير مبيعات  للعميل  محمد طلعت فاتورة رقم 7', 'عدد  0.95 كرتونة', 'عدد  0.95 كرتونة', 'عدد  1.95 كرتونة', 'عدد  1.95 كرتونة', 2, 4, 7, 1),
(34, 'نظير مبيعات  للعميل  محمد طلعت فاتورة رقم 7', 'عدد  1.00 علبة', 'عدد  1.00 علبة', 'عدد  0.95 علبة', 'عدد  0.95 علبة', 2, 16, 7, 1),
(35, 'نظير مبيعات  للعميل  محمد طلعت فاتورة رقم 7', 'عدد  2.00 كرتونة', 'عدد  2.00 كرتونة', 'عدد  1.00 كرتونة', 'عدد  1.00 كرتونة', 2, 16, 7, 1),
(36, 'نظير مبيعات  للعميل  محمد طلعت فاتورة رقم 7', 'عدد  1.00 كرتونة', 'عدد  1.00 كرتونة', 'عدد  2.00 كرتونة', 'عدد  2.00 كرتونة', 2, 4, 7, 1),
(37, 'نظير مبيعات  للعميل  محمد طلعت فاتورة رقم 7', 'عدد  2.00 كرتونة', 'عدد  2.00 كرتونة', 'عدد  1.00 كرتونة', 'عدد  1.00 كرتونة', 2, 16, 7, 1),
(38, 'نظير مبيعات  للعميل  طياري فاتورة رقم 8', 'عدد  1.00 كرتونة', 'عدد  1.00 كرتونة', 'عدد  2.00 كرتونة', 'عدد  2.00 كرتونة', 2, 4, 7, 1),
(39, 'نظير مبيعات  للعميل  طياري فاتورة رقم 8', 'عدد  2.00 كرتونة', 'عدد  2.00 كرتونة', 'عدد  1.00 كرتونة', 'عدد  1.00 كرتونة', 2, 16, 7, 1),
(40, 'نظير مبيعات  للعميل  طياري فاتورة رقم 9', 'عدد  1.95 علبة', 'عدد  1.95 علبة', 'عدد  2.00 علبة', 'عدد  2.00 علبة', 2, 4, 7, 1),
(41, 'نظير مبيعات  للعميل  طياري فاتورة رقم 9', 'عدد  2.00 علبة', 'عدد  2.00 علبة', 'عدد  1.95 علبة', 'عدد  1.95 علبة', 2, 16, 7, 1),
(42, 'نظير مبيعات  للعميل  طياري فاتورة رقم 10', 'عدد  1.00 كرتونة', 'عدد  1.00 كرتونة', 'عدد  2.00 كرتونة', 'عدد  2.00 كرتونة', 2, 4, 7, 1),
(43, 'نظير مبيعات  للعميل  طياري فاتورة رقم 11', 'عدد  0.95 علبة', 'عدد  0.95 علبة', 'عدد  1.00 علبة', 'عدد  1.00 علبة', 2, 4, 7, 1),
(44, 'نظير مبيعات  للعميل  طياري فاتورة رقم 12', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 'عدد  0.95 علبة', 'عدد  0.95 علبة', 2, 4, 7, 1),
(45, 'نظير مبيعات  للعميل  طياري فاتورة رقم 13', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 2, 4, 7, 1),
(46, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 2, 4, 7, 1),
(47, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 2, 4, 7, 1),
(48, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 2, 4, 7, 1),
(49, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 2, 4, 7, 1),
(50, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 2, 4, 7, 1),
(51, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 2, 4, 7, 1),
(52, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 2, 4, 7, 1),
(53, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 2, 4, 7, 1),
(54, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 2, 4, 7, 1),
(55, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 2, 4, 7, 1),
(56, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 2, 4, 7, 1),
(57, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 2, 4, 7, 1),
(58, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 2, 4, 7, 1),
(59, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 2, 4, 7, 1),
(60, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.80 علبة', 'عدد  0.80 علبة', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 2, 4, 7, 1),
(61, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 'عدد  0.80 علبة', 'عدد  0.80 علبة', 2, 4, 7, 1),
(62, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 2, 4, 7, 1),
(63, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 2, 4, 7, 1),
(64, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 2, 4, 7, 1),
(65, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 2, 4, 7, 1),
(66, 'نظير مبيعات  للعميل  طياري فاتورة رقم 6', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 2, 4, 7, 1),
(67, 'نظير مبيعات  للعميل  محمد طلعت فاتورة رقم 14', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 2, 4, 7, 1),
(68, 'نظير مبيعات  للعميل  محمد طلعت فاتورة رقم 14', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 2, 16, 7, 1),
(69, 'نظير مبيعات  للعميل  محمد طلعت فاتورة رقم 14', 'عدد  0.85 علبة', 'عدد  0.85 علبة', 'عدد  0.90 علبة', 'عدد  0.90 علبة', 2, 4, 7, 1),
(70, 'نظير مشتريات من المورد  احمد علي فاتورة رقم 14', 'عدد  2.85 كرتونة', 'عدد  2.85 كرتونة', 'عدد  0.85 كرتونة', 'عدد  0.85 كرتونة', 1, 1, 7, 1),
(71, 'نظير مبيعات  للعميل  طياري فاتورة رقم 5', 'عدد  2.90 علبة', 'عدد  2.90 علبة', 'عدد  2.85 علبة', 'عدد  2.85 علبة', 2, 4, 7, 1),
(72, 'نظير مرتجع مشتريات عام الي المورد احمد علي فاتورة رقم 16', 'عدد  1.90 كرتونة', 'عدد  1.90 كرتونة', 'عدد  2.90 كرتونة', 'عدد  2.90 كرتونة', 1, 3, 7, 1),
(73, ' نظير حذف سطر الصنف من فاتورة مرتجع مشتريات عام   الي المورد  احمد علي فاتورة رقم 16', 'عدد  2.90 كرتونة', 'عدد  2.90 كرتونة', 'عدد  1.90 كرتونة', 'عدد  1.90 كرتونة', 1, 3, 7, 1),
(74, 'نظير مرتجع مشتريات عام الي المورد احمد علي فاتورة رقم 16', 'عدد  2.80 علبة', 'عدد  2.80 علبة', 'عدد  2.90 علبة', 'عدد  2.90 علبة', 1, 3, 7, 1),
(75, 'نظير مبيعات  للعميل  طياري فاتورة رقم 15', 'عدد  2.75 علبة', 'عدد  2.75 علبة', 'عدد  2.80 علبة', 'عدد  2.80 علبة', 2, 4, 7, 1),
(76, 'نظير مشتريات من المورد  احمد علي فاتورة رقم 22', 'عدد  3.75 كرتونة', 'عدد  3.75 كرتونة', 'عدد  2.75 كرتونة', 'عدد  2.75 كرتونة', 1, 1, 7, 1);

-- --------------------------------------------------------

--
-- Table structure for table `inv_itemcard_movements_category`
--

CREATE TABLE `inv_itemcard_movements_category` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inv_itemcard_movements_category`
--

INSERT INTO `inv_itemcard_movements_category` (`id`, `name`) VALUES
(1, 'حركة علي المشتريات'),
(2, 'حركة علي المبيعات'),
(3, 'حركة علي المخازن');

-- --------------------------------------------------------

--
-- Table structure for table `inv_itemcard_movements_type`
--

CREATE TABLE `inv_itemcard_movements_type` (
  `id` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inv_itemcard_movements_type`
--

INSERT INTO `inv_itemcard_movements_type` (`id`, `type`) VALUES
(1, 'مشتريات '),
(2, 'مرتجع مشتريات بأصل الفاتورة'),
(3, 'مرتجع مشتريات عام'),
(4, 'مبيعات'),
(5, 'مرتجع مبيعات عام'),
(6, 'جرد بالمخازن'),
(7, 'مرتجع صرف داخلي لمندوب'),
(8, 'تحويل بين مخازن'),
(9, 'مبيعات صرف مباشر لعميل'),
(10, 'مبيعات صرف لمندوب التوصيل'),
(11, 'صرف خامات لخط التصنيع'),
(12, 'رد خامات من خط التصنيع'),
(13, 'استلام انتاج تام من خط التصنيع'),
(14, 'رد انتاج تام الي خط التصنيع'),
(15, 'حذف الصنف من تفاصيل فاتورة مبيعات مفتوحة'),
(16, 'حذف الصنف من تفاصيل فاتورة مرتجع مبيعات عام مفتوحة');

-- --------------------------------------------------------

--
-- Table structure for table `inv_item_card_batch`
--

CREATE TABLE `inv_item_card_batch` (
  `id` bigint(20) NOT NULL,
  `expired_date` date DEFAULT NULL,
  `is_send_to_archived` bit(1) DEFAULT NULL,
  `production_date` date DEFAULT NULL,
  `quantity` decimal(38,2) DEFAULT NULL,
  `total_cost_price` decimal(38,2) DEFAULT NULL,
  `unit_cost_price` decimal(38,2) DEFAULT NULL,
  `inv_uom_id` bigint(20) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inv_item_card_batch`
--

INSERT INTO `inv_item_card_batch` (`id`, `expired_date`, `is_send_to_archived`, `production_date`, `quantity`, `total_cost_price`, `unit_cost_price`, `inv_uom_id`, `item_id`, `store_id`) VALUES
(3, '2024-06-30', NULL, '2024-06-30', 19.00, 950.00, 50.00, 1, 5, 1),
(4, '2024-07-01', NULL, '2024-07-01', 0.00, 0.00, 50.00, 1, 5, 1),
(5, '2024-07-02', NULL, '2024-07-02', 2.00, 100.00, 50.00, 1, 5, 1),
(8, '2024-07-03', NULL, '2024-07-03', 0.75, 750.00, 1000.00, 1, 7, 1),
(9, '2024-07-04', NULL, '2024-07-04', 2.00, 2000.00, 1000.00, 1, 7, 1),
(10, '2024-07-12', NULL, '2024-07-12', 1.00, 1000.00, 1000.00, 1, 7, 1);

-- --------------------------------------------------------

--
-- Table structure for table `inv_production_order`
--

CREATE TABLE `inv_production_order` (
  `id` bigint(20) NOT NULL,
  `added_by` int(11) DEFAULT NULL,
  `approved_at` datetime(6) DEFAULT NULL,
  `approved_by` int(11) DEFAULT NULL,
  `auto_serial` bigint(20) DEFAULT NULL,
  `closed_at` datetime(6) DEFAULT NULL,
  `closed_by` int(11) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `is_approved` bit(1) DEFAULT NULL,
  `is_closed` bit(1) DEFAULT NULL,
  `production_plane` text DEFAULT NULL,
  `production_plan_date` date DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `inv_stores_inventory`
--

CREATE TABLE `inv_stores_inventory` (
  `id` bigint(20) NOT NULL,
  `added_by` int(11) DEFAULT NULL,
  `auto_serial` bigint(20) DEFAULT NULL,
  `closed_at` datetime(6) DEFAULT NULL,
  `cloased_by` int(11) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `inventory_date` date DEFAULT NULL,
  `inventory_type` tinyint(4) DEFAULT NULL,
  `is_closed` bit(1) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `total_cost_batches` decimal(38,2) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `does_add_all_items` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `inv_stores_inventory_details`
--

CREATE TABLE `inv_stores_inventory_details` (
  `id` bigint(20) NOT NULL,
  `added_by` int(11) DEFAULT NULL,
  `batch_auto_serial` bigint(20) DEFAULT NULL,
  `cloased_by` int(11) DEFAULT NULL,
  `closed_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `diffrent_quantity` decimal(38,2) DEFAULT NULL,
  `expired_date` date DEFAULT NULL,
  `inv_stores_inventory_auto_serial` bigint(20) DEFAULT NULL,
  `inv_uoms_id` int(11) DEFAULT NULL,
  `is_closed` bit(1) DEFAULT NULL,
  `item_code` bigint(20) DEFAULT NULL,
  `new_quantity` decimal(38,2) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `old_quantity` decimal(38,2) DEFAULT NULL,
  `production_date` date DEFAULT NULL,
  `total_cost_price` decimal(38,2) DEFAULT NULL,
  `unit_cost_price` decimal(38,2) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `inv_uoms`
--

CREATE TABLE `inv_uoms` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `master` bit(1) NOT NULL,
  `name` varchar(250) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inv_uoms`
--

INSERT INTO `inv_uoms` (`id`, `active`, `created_at`, `master`, `name`, `updated_at`, `added_by`, `updated_by`) VALUES
(1, b'1', '2024-06-30 12:26:38.000000', b'1', 'كرتونة', '2024-06-30 12:26:38.000000', 1, 1),
(2, b'1', '2024-06-30 12:26:57.000000', b'0', 'علبة', '2024-06-30 12:26:57.000000', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `mov_type`
--

CREATE TABLE `mov_type` (
  `id` int(11) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `in_screen` int(11) DEFAULT NULL,
  `is_private_internal` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mov_type`
--

INSERT INTO `mov_type` (`id`, `active`, `in_screen`, `is_private_internal`, `name`) VALUES
(1, b'1', 2, b'1', 'مراجعة واستلام نقدية شفت علي نفس الخزنة'),
(2, b'1', 2, b'1', 'مراجعة واستلام نقدية شفت خزنة اخري'),
(3, b'1', 1, b'0', 'صرف مبلغ لحساب مالي'),
(4, b'1', 2, b'0', 'تحصيل مبلغ من حساب مالي'),
(5, b'1', 2, b'0', 'تحصيل ايراد مبيعات'),
(6, b'1', 1, b'0', 'صرف نظير مرتجع مبيعات'),
(8, b'1', 1, b'1', 'صرف سلفة علي راتب موظف'),
(9, b'1', 1, b'0', 'صرف نظير مشتريات من مورد'),
(10, b'1', 2, b'0', 'تحصيل نظير مرتجع مشتريات الي مورد'),
(16, b'1', 2, b'0', 'ايراد زيادة راس المال'),
(17, b'1', 1, b'0', 'مصاريف شراء مثل النولون'),
(18, b'1', 1, b'0', 'صرف للإيداع البنكي'),
(21, b'1', 2, b'1', 'رد سلفة علي راتب موظف'),
(22, b'1', 2, b'1', 'تحصيل خصومات موظفين'),
(24, b'1', 1, b'1', 'صرف مرتب لموظف'),
(25, b'1', 2, b'0', 'سحب من البنك\r\n'),
(26, b'1', 1, b'0', 'صرف لرد رأس المال'),
(27, b'1', 1, b'0', 'صرف بفاتورة خدمات مقدمة لنا'),
(28, b'1', 2, b'0', 'تحصيل بفاتورة خدمات نقدمها للغير');

-- --------------------------------------------------------

--
-- Table structure for table `owner_payment`
--

CREATE TABLE `owner_payment` (
  `id` bigint(20) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `payment_date` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `project_id` bigint(20) NOT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `owner_payment`
--

INSERT INTO `owner_payment` (`id`, `amount`, `created_at`, `description`, `payment_date`, `updated_at`, `created_by`, `project_id`, `updated_by`) VALUES
(1, 300.00, '2024-08-23 23:33:54.000000', 'تم الدفع من الاستاذ محمد في الادارة', '2024-08-23 21:00:00.000000', '2024-08-23 23:33:54.000000', 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `password_reset`
--

CREATE TABLE `password_reset` (
  `email` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `id` bigint(20) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `payment_date` date NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `project_id` bigint(20) NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `worker_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`id`, `amount`, `created_at`, `description`, `payment_date`, `updated_at`, `created_by`, `project_id`, `updated_by`, `worker_id`) VALUES
(1, 200.00, '2024-07-30 22:17:42.000000', 'Part payment', '2024-07-30', '2024-07-30 22:17:42.000000', 1, 1, 1, 1),
(2, 600.00, '2024-08-09 21:58:08.000000', 'شغل اليوم', '2024-08-08', '2024-08-09 21:58:08.000000', 1, 2, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `personal_access_token`
--

CREATE TABLE `personal_access_token` (
  `id` bigint(20) NOT NULL,
  `abilities` text DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `expires_at` datetime(6) DEFAULT NULL,
  `last_used_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `tokenable_id` bigint(20) DEFAULT NULL,
  `tokenable_type` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `price_invoices`
--

CREATE TABLE `price_invoices` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `invoice_date` date DEFAULT NULL,
  `is_approved` bit(1) NOT NULL,
  `notes` varchar(225) DEFAULT NULL,
  `total_cost` decimal(10,2) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `price_invoices`
--

INSERT INTO `price_invoices` (`id`, `created_at`, `invoice_date`, `is_approved`, `notes`, `total_cost`, `updated_at`, `added_by`, `updated_by`) VALUES
(1, '2024-07-05 22:31:24.000000', '2024-07-04', b'0', 'okkkkkk', 160.00, '2024-07-05 22:54:57.000000', 1, 1),
(2, '2024-07-05 22:34:26.000000', '2024-07-04', b'0', 'ok', 0.00, '2024-07-05 22:36:06.000000', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `price_invoices_details`
--

CREATE TABLE `price_invoices_details` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `quantity` decimal(10,4) NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `item_code` bigint(20) DEFAULT NULL,
  `price_invoice` bigint(20) DEFAULT NULL,
  `uom_id` bigint(20) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `price_invoices_details`
--

INSERT INTO `price_invoices_details` (`id`, `created_at`, `quantity`, `total_price`, `unit_price`, `updated_at`, `added_by`, `item_code`, `price_invoice`, `uom_id`, `updated_by`) VALUES
(1, '2024-07-05 22:52:16.000000', 2.0000, 100.00, 50.00, '2024-07-05 22:52:16.000000', 1, 7, 1, 2, 1),
(2, '2024-07-05 22:54:57.000000', 2.0000, 60.00, 30.00, '2024-07-05 22:54:57.000000', 1, 5, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `projects`
--

CREATE TABLE `projects` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `project_name` varchar(100) NOT NULL,
  `start_date` date NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `projects`
--

INSERT INTO `projects` (`id`, `created_at`, `end_date`, `project_name`, `start_date`, `updated_at`, `created_by`, `updated_by`) VALUES
(1, '2024-07-30 22:06:40.000000', '2024-12-31', 'aqua view', '2024-07-01', '2024-07-30 22:06:40.000000', 1, 1),
(2, '2024-08-09 21:55:26.000000', '2033-10-25', 'سجن 15 مايو', '2024-08-08', '2024-08-09 21:55:26.000000', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `salaries`
--

CREATE TABLE `salaries` (
  `id` bigint(20) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deduction` decimal(10,2) NOT NULL,
  `is_paid` bit(1) NOT NULL,
  `salary_date` date NOT NULL,
  `total_due` decimal(10,2) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `worker_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `salaries`
--

INSERT INTO `salaries` (`id`, `amount`, `created_at`, `deduction`, `is_paid`, `salary_date`, `total_due`, `updated_at`, `created_by`, `project_id`, `updated_by`, `worker_id`) VALUES
(1, 600.00, '2024-07-30 22:12:47.000000', 0.00, b'0', '2024-07-30', 600.00, '2024-07-30 22:12:47.000000', 1, 1, 1, 1),
(2, 600.00, '2024-07-30 22:15:07.000000', 100.00, b'0', '2024-07-30', 500.00, '2024-07-30 22:15:07.000000', 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `sales_invoices`
--

CREATE TABLE `sales_invoices` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `customer_balance_after` decimal(10,2) DEFAULT NULL,
  `customer_balance_befor` decimal(10,2) DEFAULT NULL,
  `discount_percent` decimal(10,2) NOT NULL,
  `discount_type` tinyint(4) DEFAULT NULL,
  `discount_value` decimal(10,2) NOT NULL,
  `invoice_date` date DEFAULT NULL,
  `is_approved` bit(1) NOT NULL,
  `is_has_customer` bit(1) NOT NULL,
  `money_for_account` decimal(10,2) DEFAULT NULL,
  `notes` varchar(225) DEFAULT NULL,
  `pill_type` tinyint(4) DEFAULT NULL,
  `sales_item_type` tinyint(4) NOT NULL,
  `tax_percent` decimal(10,2) NOT NULL,
  `tax_value` decimal(10,2) NOT NULL,
  `total_befor_discount` decimal(10,2) NOT NULL,
  `total_cost` decimal(10,2) NOT NULL,
  `total_cost_items` decimal(10,2) NOT NULL,
  `treasuries_transactions_id` bigint(20) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `what_paid` decimal(10,2) NOT NULL,
  `what_remain` decimal(10,2) NOT NULL,
  `account_number` bigint(20) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `approved_by` int(11) DEFAULT NULL,
  `customer` bigint(20) DEFAULT NULL,
  `sales_matrial_types` int(11) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sales_invoices`
--

INSERT INTO `sales_invoices` (`id`, `created_at`, `customer_balance_after`, `customer_balance_befor`, `discount_percent`, `discount_type`, `discount_value`, `invoice_date`, `is_approved`, `is_has_customer`, `money_for_account`, `notes`, `pill_type`, `sales_item_type`, `tax_percent`, `tax_value`, `total_befor_discount`, `total_cost`, `total_cost_items`, `treasuries_transactions_id`, `updated_at`, `what_paid`, `what_remain`, `account_number`, `added_by`, `approved_by`, `customer`, `sales_matrial_types`, `store_id`, `updated_by`) VALUES
(1, '2024-06-30 13:47:54.000000', NULL, NULL, 0.00, NULL, 0.00, '2024-06-30', b'1', b'1', 50.00, 'ok', 2, 1, 0.00, 0.00, 50.00, 50.00, 0.00, 1, '2024-06-30 13:57:19.000000', 30.00, 0.00, 11, 1, 1, 3, NULL, NULL, 1),
(10, '2024-07-03 15:31:50.000000', NULL, NULL, 1.00, 1, 10.00, '2024-07-03', b'1', b'0', 1000.00, NULL, 1, 1, 0.00, 0.00, 1000.00, 990.00, 0.00, 1, '2024-07-03 15:35:28.000000', 990.00, 0.00, NULL, 1, 1, NULL, NULL, NULL, 1),
(11, '2024-07-03 15:44:34.000000', NULL, NULL, 2.00, 1, 0.00, '2024-07-03', b'1', b'0', 50.00, NULL, 1, 1, 0.00, 0.00, 50.00, 49.00, 0.00, 1, '2024-07-03 15:54:26.000000', 49.00, 0.00, NULL, 1, 1, NULL, NULL, NULL, 1),
(12, '2024-07-03 15:56:07.000000', NULL, NULL, 2.00, 1, 0.00, '2024-07-03', b'1', b'0', 50.00, NULL, 1, 1, 0.00, 0.00, 50.00, 49.00, 0.00, 1, '2024-07-03 15:58:27.000000', 49.00, 0.00, NULL, 1, 1, NULL, NULL, NULL, 1),
(13, '2024-07-03 16:00:41.000000', NULL, NULL, 2.00, 1, 1.00, '2024-07-03', b'1', b'0', 50.00, NULL, 1, 1, 0.00, 0.00, 50.00, 49.00, 0.00, 1, '2024-07-03 16:01:11.000000', 49.00, 0.00, NULL, 1, 1, NULL, NULL, NULL, 1),
(14, '2024-07-04 23:00:20.000000', NULL, NULL, 0.00, 2, 5.00, '2024-07-04', b'1', b'1', 50.00, NULL, 1, 1, 0.00, 0.00, 50.00, 45.00, 0.00, 1, '2024-07-04 23:16:18.000000', 40.00, 5.00, 11, 1, 1, 3, NULL, NULL, 1),
(15, '2024-07-05 22:20:35.000000', NULL, NULL, 0.00, 0, 0.00, NULL, b'1', b'0', 50.00, NULL, 1, 1, 0.00, 0.00, 50.00, 50.00, 0.00, 1, '2024-07-07 01:17:07.000000', 40.00, 10.00, NULL, 1, 1, NULL, NULL, NULL, 1),
(16, '2024-07-07 01:19:09.000000', NULL, NULL, 0.00, NULL, 0.00, '2024-07-08', b'0', b'0', 0.00, NULL, 1, 1, 0.00, 0.00, 0.00, 0.00, 0.00, 1, '2024-07-07 01:21:36.000000', 0.00, 0.00, NULL, 1, 1, NULL, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `sales_invoices_details`
--

CREATE TABLE `sales_invoices_details` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `expire_date` date DEFAULT NULL,
  `is_normal_or_other` tinyint(4) NOT NULL,
  `isparentuom` bit(1) NOT NULL,
  `production_date` date DEFAULT NULL,
  `quantity` decimal(10,4) NOT NULL,
  `sales_item_type` tinyint(4) NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `batch_id` bigint(20) DEFAULT NULL,
  `item_code` bigint(20) DEFAULT NULL,
  `sales_invoice` bigint(20) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `uom_id` bigint(20) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `price_invoice` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sales_invoices_details`
--

INSERT INTO `sales_invoices_details` (`id`, `created_at`, `expire_date`, `is_normal_or_other`, `isparentuom`, `production_date`, `quantity`, `sales_item_type`, `total_price`, `unit_price`, `updated_at`, `added_by`, `batch_id`, `item_code`, `sales_invoice`, `store_id`, `uom_id`, `updated_by`, `price_invoice`) VALUES
(1, '2024-06-30 13:52:59.000000', '2024-06-30', 1, b'1', '2024-06-30', 1.0000, 1, 50.00, 50.00, '2024-06-30 13:52:59.000000', 1, 3, 5, 1, 1, 1, 1, NULL),
(10, '2024-07-03 15:35:03.000000', '2024-07-03', 1, b'1', '2024-07-03', 1.0000, 1, 1000.00, 1000.00, '2024-07-03 15:35:03.000000', 1, 8, 7, 10, 1, 1, 1, NULL),
(11, '2024-07-03 15:45:32.000000', '2024-07-03', 1, b'0', '2024-07-03', 1.0000, 1, 50.00, 50.00, '2024-07-03 15:45:32.000000', 1, 8, 7, 11, 1, 2, 1, NULL),
(12, '2024-07-03 15:56:25.000000', '2024-07-03', 1, b'0', '2024-07-03', 1.0000, 1, 50.00, 50.00, '2024-07-03 15:56:25.000000', 1, 8, 7, 12, 1, 2, 1, NULL),
(13, '2024-07-03 16:00:55.000000', '2024-07-03', 1, b'0', '2024-07-03', 1.0000, 1, 50.00, 50.00, '2024-07-03 16:00:55.000000', 1, 8, 7, 13, 1, 2, 1, NULL),
(15, '2024-07-04 23:13:24.000000', '2024-07-04', 1, b'0', '2024-07-04', 1.0000, 1, 50.00, 50.00, '2024-07-04 23:13:24.000000', 1, 8, 7, 14, 1, 2, 1, NULL),
(16, '2024-07-07 01:16:27.000000', '2024-07-07', 1, b'0', '2024-07-07', 1.0000, 1, 50.00, 50.00, '2024-07-07 01:16:27.000000', 1, 8, 7, 15, 1, 2, 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `sales_invoices_return`
--

CREATE TABLE `sales_invoices_return` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `customer_balance_after` decimal(10,2) DEFAULT NULL,
  `customer_balance_befor` decimal(10,2) DEFAULT NULL,
  `discount_percent` decimal(10,2) NOT NULL,
  `discount_type` tinyint(4) DEFAULT NULL,
  `discount_value` decimal(10,2) NOT NULL,
  `invoice_date` date NOT NULL,
  `is_approved` bit(1) NOT NULL,
  `is_has_customer` bit(1) NOT NULL,
  `money_for_account` decimal(10,2) DEFAULT NULL,
  `notes` varchar(225) DEFAULT NULL,
  `pill_type` tinyint(4) DEFAULT NULL,
  `return_type` tinyint(4) NOT NULL,
  `tax_percent` decimal(10,2) NOT NULL,
  `tax_value` decimal(10,2) NOT NULL,
  `total_befor_discount` decimal(10,2) NOT NULL,
  `total_cost` decimal(10,2) NOT NULL,
  `total_cost_items` decimal(10,2) NOT NULL,
  `treasuries_transactions_id` bigint(20) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `what_paid` decimal(10,2) NOT NULL,
  `what_remain` decimal(10,2) NOT NULL,
  `account_number` bigint(20) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `approved_by` int(11) DEFAULT NULL,
  `customer` bigint(20) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sales_invoices_return`
--

INSERT INTO `sales_invoices_return` (`id`, `created_at`, `customer_balance_after`, `customer_balance_befor`, `discount_percent`, `discount_type`, `discount_value`, `invoice_date`, `is_approved`, `is_has_customer`, `money_for_account`, `notes`, `pill_type`, `return_type`, `tax_percent`, `tax_value`, `total_befor_discount`, `total_cost`, `total_cost_items`, `treasuries_transactions_id`, `updated_at`, `what_paid`, `what_remain`, `account_number`, `added_by`, `approved_by`, `customer`, `updated_by`) VALUES
(5, '2024-07-03 12:39:19.000000', NULL, NULL, 0.00, NULL, 0.00, '2024-07-03', b'0', b'0', 0.00, NULL, 1, 1, 0.00, 0.00, 50.00, 50.00, 0.00, 1, '2024-07-05 00:08:14.000000', 0.00, 0.00, NULL, 1, NULL, NULL, 1),
(6, '2024-07-03 12:39:28.000000', NULL, NULL, 0.00, NULL, 0.00, '2024-07-03', b'1', b'0', -50.00, NULL, 1, 1, 0.00, 0.00, 50.00, 50.00, 0.00, 1, '2024-07-03 17:37:45.000000', 49.00, 0.00, NULL, 1, NULL, NULL, 1),
(9, '2024-07-07 20:21:32.000000', NULL, NULL, 0.00, NULL, 0.00, '2024-07-07', b'0', b'1', 0.00, NULL, 1, 1, 0.00, 0.00, 0.00, 0.00, 0.00, 1, '2024-07-07 20:21:32.000000', 0.00, 0.00, 11, 1, NULL, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `sales_invoices_return_details`
--

CREATE TABLE `sales_invoices_return_details` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `expire_date` date DEFAULT NULL,
  `isparentuom` bit(1) NOT NULL,
  `production_date` date DEFAULT NULL,
  `quantity` decimal(10,4) DEFAULT NULL,
  `sales_item_type` tinyint(4) NOT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `unit_cost_price` decimal(10,2) DEFAULT NULL,
  `unit_price` decimal(10,2) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `batch_id` bigint(20) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `sales_invoice_return` bigint(20) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `uom_id` bigint(20) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sales_invoices_return_details`
--

INSERT INTO `sales_invoices_return_details` (`id`, `created_at`, `expire_date`, `isparentuom`, `production_date`, `quantity`, `sales_item_type`, `total_price`, `unit_cost_price`, `unit_price`, `updated_at`, `added_by`, `batch_id`, `item_id`, `sales_invoice_return`, `store_id`, `uom_id`, `updated_by`) VALUES
(1, '2024-07-05 00:08:14.000000', NULL, b'0', NULL, 1.0000, 1, 50.00, 40.00, 50.00, '2024-07-05 00:08:14.000000', 1, 8, 7, 5, 1, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `sales_matrial_types`
--

CREATE TABLE `sales_matrial_types` (
  `id` int(11) NOT NULL,
  `active` bit(1) NOT NULL,
  `added_by` int(11) NOT NULL,
  `com_code` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `date` date NOT NULL,
  `name` varchar(250) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sarf_permissions`
--

CREATE TABLE `sarf_permissions` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `notes` varchar(225) DEFAULT NULL,
  `total_cost` decimal(10,2) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `customer` bigint(20) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `receiver_name` varchar(225) DEFAULT NULL,
  `permission_date` date DEFAULT NULL,
  `is_approved` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sarf_permissions`
--

INSERT INTO `sarf_permissions` (`id`, `created_at`, `notes`, `total_cost`, `updated_at`, `added_by`, `customer`, `store_id`, `updated_by`, `receiver_name`, `permission_date`, `is_approved`) VALUES
(5, '2024-07-05 15:11:04.000000', 'تمام', 0.00, '2024-07-05 19:21:25.000000', 1, 3, NULL, 1, 'محمد السيد', '2024-07-04', b'1');

-- --------------------------------------------------------

--
-- Table structure for table `sarf_permission_details`
--

CREATE TABLE `sarf_permission_details` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `quantity` decimal(10,4) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `item_code` bigint(20) DEFAULT NULL,
  `sarf_permission_id` bigint(20) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `uom_id` bigint(20) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sarf_permission_details`
--

INSERT INTO `sarf_permission_details` (`id`, `created_at`, `quantity`, `updated_at`, `added_by`, `item_code`, `sarf_permission_id`, `store_id`, `uom_id`, `updated_by`) VALUES
(5, '2024-07-05 19:21:08.000000', 1.0000, '2024-07-05 19:21:08.000000', 1, 7, 5, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `id` int(11) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `service_order`
--

CREATE TABLE `service_order` (
  `id` bigint(20) NOT NULL,
  `account_number` bigint(20) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `approved_by` int(11) DEFAULT NULL,
  `auto_serial` bigint(20) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `discount_percent` decimal(38,2) DEFAULT NULL,
  `discount_type` int(11) DEFAULT NULL,
  `discount_value` decimal(38,2) DEFAULT NULL,
  `entity_name` varchar(255) DEFAULT NULL,
  `is_account_number` bit(1) DEFAULT NULL,
  `is_approved` bit(1) DEFAULT NULL,
  `money_for_account` decimal(38,2) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `order_type` int(11) DEFAULT NULL,
  `pill_type` int(11) DEFAULT NULL,
  `tax_percent` decimal(38,2) DEFAULT NULL,
  `tax_value` decimal(38,2) DEFAULT NULL,
  `total_befor_discount` decimal(38,2) DEFAULT NULL,
  `total_cost` decimal(38,2) DEFAULT NULL,
  `total_services` decimal(38,2) DEFAULT NULL,
  `treasuries_transactions_id` bigint(20) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `what_paid` decimal(38,2) DEFAULT NULL,
  `what_remain` decimal(38,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `service_order_detail`
--

CREATE TABLE `service_order_detail` (
  `id` bigint(20) NOT NULL,
  `added_by` int(11) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `order_type` int(11) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  `services_with_orders_auto_serial` bigint(20) DEFAULT NULL,
  `total` decimal(38,2) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `stores`
--

CREATE TABLE `stores` (
  `id` int(11) NOT NULL,
  `address` varchar(250) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `name` varchar(250) NOT NULL,
  `phones` varchar(100) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stores`
--

INSERT INTO `stores` (`id`, `address`, `created_at`, `active`, `name`, `phones`, `updated_at`, `added_by`, `updated_by`) VALUES
(1, 'القاهرة', '2024-06-27 21:18:05.000000', b'1', 'الرئيسي', '321321321', '2024-06-27 21:18:05.000000', 1, 1),
(2, 'حلوان', NULL, b'1', 'ssssssس', '654654654', '2024-07-13 20:15:58.000000', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

CREATE TABLE `suppliers` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `address` varchar(250) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `current_balance` decimal(10,2) NOT NULL,
  `name` varchar(225) NOT NULL,
  `notes` varchar(225) DEFAULT NULL,
  `phones` varchar(50) DEFAULT NULL,
  `start_balance` decimal(10,2) NOT NULL,
  `start_balance_status` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `account_number` bigint(20) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `suppliers_categories_id` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `suppliers`
--

INSERT INTO `suppliers` (`id`, `active`, `address`, `created_at`, `current_balance`, `name`, `notes`, `phones`, `start_balance`, `start_balance_status`, `updated_at`, `account_number`, `added_by`, `suppliers_categories_id`, `updated_by`) VALUES
(1, b'1', 'القاهرة', '2024-06-30 12:21:16.000000', 4885.00, 'احمد علي', 'تمام', '01555555555', 0.00, 3, '2024-07-13 20:06:22.000000', 12, 1, 1, 1),
(2, b'1', 'القاهرة', '2024-06-30 19:32:35.000000', 0.00, 'السيد حسن', NULL, '01232132132', 0.00, 3, '2024-06-30 19:32:35.000000', 13, 1, 1, 1),
(3, b'1', NULL, '2024-07-12 20:16:42.000000', 0.00, 'ابراهيم السيد', NULL, NULL, 0.00, 3, '2024-07-12 20:16:42.000000', 15, 1, 2, 1),
(4, b'1', NULL, '2024-07-12 20:20:41.000000', 0.00, 'محسن', NULL, NULL, 0.00, 3, '2024-07-12 20:20:41.000000', 16, 1, 2, 1),
(5, b'1', 'test address', '2024-07-13 20:02:56.000000', 0.00, 'test', 'my note', '01223654789', 0.00, 3, '2024-07-13 20:02:56.000000', 17, 1, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `suppliers_categories`
--

CREATE TABLE `suppliers_categories` (
  `id` int(11) NOT NULL,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `name` varchar(250) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `suppliers_categories`
--

INSERT INTO `suppliers_categories` (`id`, `active`, `created_at`, `name`, `updated_at`, `added_by`, `updated_by`) VALUES
(1, b'1', '2024-06-30 12:00:43.000000', 'ادوات كهربية', '2024-06-30 12:00:43.000000', 1, 1),
(2, b'0', NULL, 'اكسسوارات', '2024-07-13 20:04:10.000000', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `suppliers_with_orders`
--

CREATE TABLE `suppliers_with_orders` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `discount_percent` decimal(10,2) DEFAULT NULL,
  `discount_type` tinyint(4) DEFAULT NULL,
  `discount_value` decimal(10,2) NOT NULL,
  `doc_no` varchar(25) DEFAULT NULL,
  `is_approved` bit(1) NOT NULL,
  `money_for_account` decimal(10,2) DEFAULT NULL,
  `notes` varchar(225) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `order_type` tinyint(4) NOT NULL,
  `pill_type` tinyint(4) NOT NULL,
  `supplier_balance_after` decimal(10,2) DEFAULT NULL,
  `supplier_balance_befor` decimal(10,2) DEFAULT NULL,
  `tax_percent` decimal(10,2) DEFAULT NULL,
  `tax_value` decimal(10,2) DEFAULT NULL,
  `total_befor_discount` decimal(10,2) NOT NULL,
  `total_cost` decimal(10,2) DEFAULT NULL,
  `total_cost_items` decimal(10,2) NOT NULL,
  `treasuries_transactions_id` bigint(20) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `what_paid` decimal(10,2) DEFAULT NULL,
  `what_remain` decimal(10,2) DEFAULT NULL,
  `account_number` bigint(20) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `approved_by` int(11) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `supplier_id` bigint(20) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `suppliers_with_orders`
--

INSERT INTO `suppliers_with_orders` (`id`, `created_at`, `discount_percent`, `discount_type`, `discount_value`, `doc_no`, `is_approved`, `money_for_account`, `notes`, `order_date`, `order_type`, `pill_type`, `supplier_balance_after`, `supplier_balance_befor`, `tax_percent`, `tax_value`, `total_befor_discount`, `total_cost`, `total_cost_items`, `treasuries_transactions_id`, `updated_at`, `what_paid`, `what_remain`, `account_number`, `added_by`, `approved_by`, `store_id`, `supplier_id`, `updated_by`) VALUES
(16, '2024-07-05 00:26:57.000000', NULL, NULL, 0.00, NULL, b'0', NULL, NULL, '2024-07-05', 1, 1, NULL, NULL, 0.00, NULL, 100.00, 100.00, 0.00, NULL, '2024-07-05 01:03:23.000000', NULL, NULL, 12, 1, NULL, 1, 1, 1),
(17, '2024-07-05 00:29:55.000000', 0.00, 0, 0.00, '234', b'0', NULL, NULL, '2024-07-05', 0, 1, NULL, NULL, 0.00, NULL, 1000.00, 1000.00, 0.00, NULL, '2024-07-05 00:30:06.000000', NULL, NULL, 12, 1, NULL, 1, 1, 1),
(18, '2024-07-06 23:56:09.000000', 0.00, 0, 0.00, '1234', b'0', NULL, NULL, '2024-07-06', 0, 1, NULL, NULL, 0.00, NULL, 0.00, 0.00, 0.00, NULL, '2024-07-06 23:56:09.000000', NULL, NULL, 12, 1, NULL, 1, 1, 1),
(19, '2024-07-06 23:56:22.000000', 0.00, 0, 0.00, '2234', b'0', NULL, NULL, '2024-07-06', 0, 1, NULL, NULL, 0.00, NULL, 0.00, 0.00, 0.00, NULL, '2024-07-06 23:56:22.000000', NULL, NULL, 13, 1, NULL, 1, 2, 1),
(20, '2024-07-06 23:56:33.000000', 0.00, 0, 0.00, '234234', b'0', NULL, NULL, '2024-07-06', 0, 1, NULL, NULL, 0.00, NULL, 0.00, 0.00, 0.00, NULL, '2024-07-06 23:56:33.000000', NULL, NULL, 12, 1, NULL, 1, 1, 1),
(21, '2024-07-06 23:56:46.000000', 0.00, 0, 0.00, '435345', b'0', NULL, NULL, '2024-07-06', 0, 2, NULL, NULL, 0.00, NULL, 0.00, 0.00, 0.00, NULL, '2024-07-06 23:56:46.000000', NULL, NULL, 13, 1, NULL, 1, 2, 1),
(22, '2024-07-06 23:57:00.000000', 0.00, 0, 0.00, '345345', b'1', -1000.00, '234', '2024-07-06', 0, 1, NULL, NULL, NULL, NULL, 1000.00, 1000.00, 0.00, NULL, '2024-07-12 20:38:46.000000', 1000.00, 0.00, 12, 1, NULL, 1, 1, 1),
(23, '2024-07-07 20:58:01.000000', NULL, NULL, 0.00, NULL, b'0', NULL, NULL, '2024-07-07', 1, 1, NULL, NULL, 0.00, NULL, 0.00, 0.00, 0.00, NULL, '2024-07-07 20:58:01.000000', NULL, NULL, 13, 1, NULL, 2, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `suppliers_with_orders_details`
--

CREATE TABLE `suppliers_with_orders_details` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `deliverd_quantity` decimal(10,2) NOT NULL,
  `expire_date` date DEFAULT NULL,
  `isparentuom` bit(1) NOT NULL,
  `item_card_type` tinyint(4) DEFAULT NULL,
  `order_type` tinyint(4) NOT NULL,
  `production_date` date DEFAULT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `batch_id` bigint(20) DEFAULT NULL,
  `inv_item_id` bigint(20) DEFAULT NULL,
  `item_code` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `uom_id` bigint(20) NOT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `suppliers_with_orders_details`
--

INSERT INTO `suppliers_with_orders_details` (`id`, `created_at`, `deliverd_quantity`, `expire_date`, `isparentuom`, `item_card_type`, `order_type`, `production_date`, `total_price`, `unit_price`, `updated_at`, `added_by`, `batch_id`, `inv_item_id`, `item_code`, `order_id`, `uom_id`, `updated_by`) VALUES
(1, '2024-07-05 00:30:06.000000', 1.00, NULL, b'1', 1, 0, NULL, 1000.00, 1000.00, '2024-07-05 00:30:06.000000', 1, NULL, 7, NULL, 17, 1, 1),
(3, '2024-07-05 01:02:28.000000', 2.00, NULL, b'0', NULL, 1, NULL, 100.00, 50.00, '2024-07-05 01:02:28.000000', 1, 8, 7, NULL, 16, 2, 1),
(4, '2024-07-12 20:38:28.000000', 1.00, NULL, b'1', 1, 0, NULL, 1000.00, 1000.00, '2024-07-12 20:38:28.000000', 1, NULL, 7, NULL, 22, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `token_info`
--

CREATE TABLE `token_info` (
  `id` bigint(20) NOT NULL,
  `access_token` varchar(800) DEFAULT NULL,
  `local_ip_address` varchar(255) DEFAULT NULL,
  `refresh_token` varchar(800) DEFAULT NULL,
  `remote_ip_address` varchar(255) DEFAULT NULL,
  `user_agent_text` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `token_info`
--

INSERT INTO `token_info` (`id`, `access_token`, `local_ip_address`, `refresh_token`, `remote_ip_address`, `user_agent_text`, `user_id`) VALUES
(6, 'eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxZTQwMWNhNC1hZDNjLTQ0N2UtYjE4ZS1kOTMwYzgxMzI3YWYiLCJzdWIiOiJhZG1pbiIsImlhdCI6MTcyMDMwMDU5MywiZXhwIjoxOTAwMzAwNTkzfQ.wM_3HkD959N9qU_4iRIq5Qk9S5pyoI0otJPTyF2gnu101VJDlyDqvJ_zFBT4JMDR2-wqHhpK01Gbm2KGcvYA8Q', '192.168.0.104', 'eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJhZDM1ZGI2ZC0wNTk1LTQyODUtYjc1MC0yYTFjZTExNjMyZWEiLCJzdWIiOiJhZG1pbiIsImlhdCI6MTcyMDMwMDU5MywiZXhwIjoxNzM4MzAwNTkzfQ.xHhV8k7q2K7FEIjJRa0Zb-zMoOvogyWDzgs4B6xxfcBIllwQd0EfldLUI6jdosQTKmtK6HyrBVtqVXYfDrAyUg', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36', 1),
(7, 'eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJmOTU3YmZhZC01MjA0LTQ2ZTYtYTliMi1mYzNkNWRhZjdhZDIiLCJzdWIiOiJhZG1pbiIsImlhdCI6MTcyMzIzMjY4NCwiZXhwIjoxOTAzMjMyNjg0fQ.CJoUk1QDMoqVlQ2RPL2V6zxUVHMtEDhYoUzVCnsobwcKd4EXLBLuPJwAtLezIo5AwpLeZz1bwEDXNlXZ06FmFQ', '192.168.137.1', 'eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI4YTBjY2Y5NC0wNzE4LTQ0MzMtYjFmMC1jMzM5NDA5NjJhZjEiLCJzdWIiOiJhZG1pbiIsImlhdCI6MTcyMzIzMjY4NCwiZXhwIjoxNzQxMjMyNjg0fQ.Xc9WG3GG0VyCWO0UjBiwZhkUxYLpD4-jglyAxYvfzYe3YqZIMdnEoAj2n4H_uxYCRveW8mDVyoRXpGV1UMfpbw', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:129.0) Gecko/20100101 Firefox/129.0', 1);

-- --------------------------------------------------------

--
-- Table structure for table `treasuries`
--

CREATE TABLE `treasuries` (
  `id` int(11) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `is_master` bit(1) NOT NULL,
  `last_isal_collect` bigint(20) NOT NULL,
  `last_isal_exhcange` bigint(20) NOT NULL,
  `name` varchar(250) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `treasuries`
--

INSERT INTO `treasuries` (`id`, `created_at`, `active`, `is_master`, `last_isal_collect`, `last_isal_exhcange`, `name`, `updated_at`, `added_by`, `updated_by`) VALUES
(1, '2024-06-22 15:35:03.000000', b'1', b'1', 1, 47, 'الرئيسية', '2024-07-13 20:31:01.000000', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `treasuries_delivery`
--

CREATE TABLE `treasuries_delivery` (
  `id` int(11) NOT NULL,
  `added_by` int(11) NOT NULL,
  `com_code` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `treasuries_can_delivery_id` int(11) NOT NULL,
  `treasuries_id` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `treasuries_transactions`
--

CREATE TABLE `treasuries_transactions` (
  `id` bigint(20) NOT NULL,
  `byan` varchar(225) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `is_account` bit(1) DEFAULT NULL,
  `is_approved` bit(1) NOT NULL,
  `isal_number` bigint(20) NOT NULL,
  `money` decimal(10,2) NOT NULL,
  `money_for_account` decimal(10,2) NOT NULL,
  `mov_type` int(11) NOT NULL,
  `move_date` date NOT NULL,
  `shift_code` bigint(20) NOT NULL,
  `the_foregin_key` bigint(20) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `account_number` bigint(20) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `treasure_id` int(11) NOT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `treasuries_transactions`
--

INSERT INTO `treasuries_transactions` (`id`, `byan`, `created_at`, `is_account`, `is_approved`, `isal_number`, `money`, `money_for_account`, `mov_type`, `move_date`, `shift_code`, `the_foregin_key`, `updated_at`, `account_number`, `added_by`, `treasure_id`, `updated_by`) VALUES
(1, 'ضبط رأس المال', '2024-06-30 13:25:00.000000', b'0', b'1', 1, 500000.00, 500000.00, 26, '2024-06-22', 1, NULL, NULL, NULL, 1, 1, 1),
(21, 'صرف نظير فاتورة مشتريات  رقم1', '2024-06-30 13:40:36.000000', b'1', b'1', 1, -1000.00, 1000.00, 9, '2024-06-30', 1, NULL, '2024-06-30 13:40:36.000000', 12, 1, 1, 1),
(22, 'تحصيل نظير فاتورة مبيعات  رقم1', '2024-06-30 13:57:19.000000', b'1', b'1', 1, 30.00, -30.00, 5, '2024-06-30', 1, NULL, '2024-06-30 13:57:19.000000', 11, 1, 1, 1),
(23, 'تحصيل ايراد مبيعات', '2024-07-01 19:02:09.000000', b'1', b'1', 1, 5.00, -5.00, 5, '2024-06-30', 1, NULL, '2024-07-01 19:02:09.000000', 11, 1, 1, 1),
(24, 'صرف نظير فاتورة مشتريات  رقم2', '2024-07-01 19:16:42.000000', b'1', b'1', 1, -40.00, 40.00, 9, '2024-07-01', 1, NULL, '2024-07-01 19:16:42.000000', 12, 1, 1, 1),
(25, 'صرف نظير مشتريات من مورد', '2024-07-01 19:19:16.000000', b'1', b'1', 1, -5.00, 5.00, 9, '2024-06-30', 1, NULL, '2024-07-01 19:19:16.000000', 12, 1, 1, 1),
(26, 'صرف نظير فاتورة مشتريات  رقم7', '2024-07-02 10:10:31.000000', b'1', b'1', 1, -145.00, 145.00, 9, '2024-07-02', 1, NULL, '2024-07-02 10:10:31.000000', 12, 1, 1, 1),
(27, 'صرف نظير فاتورة مشتريات  رقم8', '2024-07-02 10:32:10.000000', b'1', b'1', 1, -45.00, 45.00, 9, '2024-07-02', 1, NULL, '2024-07-02 10:32:10.000000', 12, 1, 1, 1),
(30, 'صرف نظير فاتورة مشتريات  رقم10', '2024-07-02 12:49:37.000000', b'1', b'1', 1, 50.00, -50.00, 9, '2024-07-02', 1, NULL, '2024-07-02 12:49:37.000000', 12, 1, 1, 1),
(31, 'صرف نظير فاتورة مشتريات  رقم13', '2024-07-03 13:52:07.000000', b'1', b'1', 1, -2000.00, 2000.00, 9, '2024-07-03', 1, NULL, '2024-07-03 13:52:07.000000', 12, 1, 1, 1),
(32, 'تحصيل نظير فاتورة مبيعات  رقم10', '2024-07-03 15:35:28.000000', NULL, b'1', 1, 990.00, -990.00, 5, '2024-07-03', 1, NULL, '2024-07-03 15:35:28.000000', NULL, 1, 1, 1),
(35, 'تحصيل نظير فاتورة مبيعات  رقم11', '2024-07-03 15:54:26.000000', NULL, b'1', 1, 49.00, -49.00, 5, '2024-07-03', 1, NULL, '2024-07-03 15:54:26.000000', NULL, 1, 1, 1),
(36, 'تحصيل نظير فاتورة مبيعات  رقم12', '2024-07-03 15:58:27.000000', NULL, b'1', 1, 49.00, -49.00, 5, '2024-07-03', 1, NULL, '2024-07-03 15:58:27.000000', NULL, 1, 1, 1),
(37, 'تحصيل نظير فاتورة مبيعات  رقم13', '2024-07-03 16:01:11.000000', NULL, b'1', 1, 49.00, -49.00, 5, '2024-07-03', 1, NULL, '2024-07-03 16:01:11.000000', NULL, 1, 1, 1),
(38, 'تحصيل نظير فاتورة مرتجع مبيعات  رقم6', '2024-07-03 17:37:45.000000', NULL, b'1', 38, -49.00, 49.00, 5, '2024-07-03', 1, NULL, '2024-07-03 17:37:45.000000', NULL, 1, 1, 1),
(39, 'تحصيل نظير فاتورة مبيعات  رقم14', '2024-07-04 23:16:18.000000', b'1', b'1', 1, 40.00, -40.00, 5, '2024-07-04', 1, NULL, '2024-07-04 23:16:18.000000', 11, 1, 1, 1),
(40, 'صرف نظير فاتورة مشتريات  رقم14', '2024-07-04 23:21:54.000000', b'1', b'1', 1, -2000.00, 2000.00, 9, '2024-07-04', 1, NULL, '2024-07-04 23:21:54.000000', 12, 1, 1, 1),
(41, 'تحصيل نظير فاتورة مبيعات  رقم15', '2024-07-07 01:17:07.000000', NULL, b'1', 1, 40.00, -40.00, 5, '2024-07-07', 1, NULL, '2024-07-07 01:17:07.000000', NULL, 1, 1, 1),
(42, 'ok', '2024-07-12 20:35:37.000000', b'1', b'1', 1, 20.00, -20.00, 5, '2024-07-11', 1, NULL, '2024-07-12 20:35:37.000000', 11, 1, 1, 1),
(43, 'صرف نظير فاتورة مشتريات  رقم22', '2024-07-12 20:38:46.000000', b'1', b'1', 1, -1000.00, 1000.00, 9, '2024-07-12', 1, NULL, '2024-07-12 20:38:46.000000', 12, 1, 1, 1),
(44, 'جه دفع 300 من الفلوس اللي عليه', '2024-07-13 20:06:22.000000', b'1', b'1', 1, 300.00, -300.00, 4, '2024-07-12', 1, NULL, '2024-07-13 20:06:22.000000', 12, 1, 1, 1),
(45, 'جه دفع 300 وعليه 5 اتبقي له 255', '2024-07-13 20:22:18.000000', b'1', b'1', 1, -300.00, 300.00, 4, '2024-07-12', 1, NULL, '2024-07-13 20:22:18.000000', 11, 1, 1, 1),
(46, 'حصلنا منه 200 من ال 305 ', '2024-07-13 20:29:44.000000', b'1', b'1', 1, 200.00, -200.00, 4, '2024-07-12', 1, NULL, '2024-07-13 20:29:44.000000', 11, 1, 1, 1),
(47, '125', '2024-07-13 20:31:00.000000', b'1', b'1', 1, -20.00, 20.00, 3, '2024-07-12', 1, NULL, '2024-07-13 20:31:00.000000', 11, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `email_verified_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `remember_token` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users_roles`
--

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users_roles`
--

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `workers`
--

CREATE TABLE `workers` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `daily_rate` decimal(10,2) NOT NULL,
  `name` varchar(100) NOT NULL,
  `position` varchar(100) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `workers`
--

INSERT INTO `workers` (`id`, `created_at`, `daily_rate`, `name`, `position`, `updated_at`, `created_by`, `updated_by`) VALUES
(1, '2024-07-30 22:06:18.000000', 600.00, 'John Doe', 'Electrician', '2024-07-30 22:06:18.000000', 1, 1),
(2, '2024-08-09 21:54:57.000000', 1000.00, 'محمود بدران', 'كهربائي', '2024-08-09 21:54:57.000000', 1, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfewvxe4khtqi148b7v7cj1o49` (`account_type`),
  ADD KEY `FKk1datdiu7myu9m45ir80cggid` (`added_by`),
  ADD KEY `FK70mh6nn5ow6yliuaeaq0kje9y` (`parent_account_number`),
  ADD KEY `FKcjt0qyitfg2qb2mmpgjm4r5xo` (`updated_by`);

--
-- Indexes for table `account_types`
--
ALTER TABLE `account_types`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `admins_shifts`
--
ALTER TABLE `admins_shifts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `admins_treasuries`
--
ALTER TABLE `admins_treasuries`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `admin_panel_settings`
--
ALTER TABLE `admin_panel_settings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKra1weq6voniankqvuomucbpmq` (`added_by`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_4njtl3pvfduamug24b9qmpy0x` (`account_id`),
  ADD KEY `FKb3jo3nimp5vbdsp5p8oobuu3c` (`added_by`),
  ADD KEY `FKm57ejbuucgtyv8xkf2o98hhn2` (`updated_by`);

--
-- Indexes for table `delegate`
--
ALTER TABLE `delegate`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `expenses`
--
ALTER TABLE `expenses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8o6ghp4tj5x7f43jeyu72tkxj` (`created_by`),
  ADD KEY `FKf2q37yjhkyxgx5n661c7e4vm8` (`project_id`),
  ADD KEY `FKsj0q9a7mwsqfo3fmvbv0xlnsr` (`updated_by`);

--
-- Indexes for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `inv_itemcard`
--
ALTER TABLE `inv_itemcard`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjj4w2uaiideywn6425t8esau2` (`added_by`),
  ADD KEY `FK4na9v0u5i99ghnuk4md7msnfp` (`inv_item_category_id`),
  ADD KEY `FKjko9x8r2d9b9yb6aabf35s5aw` (`parent_inv_item_id`),
  ADD KEY `FKr9w60cj0ws7rti7ot6o5ouylw` (`retail_uom_id`),
  ADD KEY `FKi0c9ms884ob1ylw75v7xg1pwh` (`uom_id`),
  ADD KEY `FK6ts4fqvg3euvssherm70a005p` (`updated_by`);

--
-- Indexes for table `inv_itemcard_categories`
--
ALTER TABLE `inv_itemcard_categories`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKptdsxiq1f6d3p7y1hv7t3n1r5` (`added_by`),
  ADD KEY `FK3dkchl5tm9jlmubldas0as299` (`updated_by`);

--
-- Indexes for table `inv_itemcard_movement`
--
ALTER TABLE `inv_itemcard_movement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKm50kttnc8n4of9yonmf0luj4y` (`inv_itemcard_movements_category_id`),
  ADD KEY `FK1g9frmlqoq1r3hkcs5p5dl2y8` (`items_movements_type_id`),
  ADD KEY `FKtedya2bo3iius1dit134djkbe` (`item_id`),
  ADD KEY `FK1aq16wcuhgispxuqa2w846cm0` (`store_id`);

--
-- Indexes for table `inv_itemcard_movements_category`
--
ALTER TABLE `inv_itemcard_movements_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `inv_itemcard_movements_type`
--
ALTER TABLE `inv_itemcard_movements_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `inv_item_card_batch`
--
ALTER TABLE `inv_item_card_batch`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKncd1678wr13kbyuxdmhoynktc` (`inv_uom_id`),
  ADD KEY `FKi13r3yevhkcn9yvakd3fl5jua` (`item_id`),
  ADD KEY `FK3qtihirhyemhgfvnxichx7al8` (`store_id`);

--
-- Indexes for table `inv_production_order`
--
ALTER TABLE `inv_production_order`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `inv_stores_inventory`
--
ALTER TABLE `inv_stores_inventory`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKn8oabsdtsbobbuas7mmxpgrsp` (`added_by`),
  ADD KEY `FKexf4lrxpmfldb3il3opmo3c6m` (`store_id`),
  ADD KEY `FKa7777qtsm6ydgolwhq9e2b5f1` (`updated_by`);

--
-- Indexes for table `inv_stores_inventory_details`
--
ALTER TABLE `inv_stores_inventory_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `inv_uoms`
--
ALTER TABLE `inv_uoms`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK40a85k1pyvvjktcwcsejf7pwd` (`added_by`),
  ADD KEY `FKexbqunjsgcyi7rsrj4w99qwfl` (`updated_by`);

--
-- Indexes for table `mov_type`
--
ALTER TABLE `mov_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `owner_payment`
--
ALTER TABLE `owner_payment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbfi55h77yjdf8ilt82v0a04kr` (`created_by`),
  ADD KEY `FKn9cn4nk38e1mrnkb2jgu2e4nm` (`project_id`),
  ADD KEY `FK8s83a6otvw8w5johy1np4k0fy` (`updated_by`);

--
-- Indexes for table `password_reset`
--
ALTER TABLE `password_reset`
  ADD PRIMARY KEY (`email`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpneh9iu8vpjka14dn7rj3erej` (`created_by`),
  ADD KEY `FK7h0as5hqhn845eewc7usiy0x3` (`project_id`),
  ADD KEY `FKhyg4jkv8w3ooqhj7quheddfjj` (`updated_by`),
  ADD KEY `FK67l4771i0yo03594j13kvnv9` (`worker_id`);

--
-- Indexes for table `personal_access_token`
--
ALTER TABLE `personal_access_token`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `price_invoices`
--
ALTER TABLE `price_invoices`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKto13xre20nb5i2dkoau73iow9` (`added_by`),
  ADD KEY `FK4d2vakenou1j2liuqdbjs960l` (`updated_by`);

--
-- Indexes for table `price_invoices_details`
--
ALTER TABLE `price_invoices_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKlo1vd4mbbwgxyj4ublkeuq234` (`added_by`),
  ADD KEY `FKlpbmii98juewur4fde9dwwjei` (`item_code`),
  ADD KEY `FKmj3g6j3ccf59awmc7cptkla0p` (`price_invoice`),
  ADD KEY `FKca1iblftux47mmxb5vn7pj7rl` (`uom_id`),
  ADD KEY `FKtinx3bmuw57j3kxjeetyvcnx1` (`updated_by`);

--
-- Indexes for table `projects`
--
ALTER TABLE `projects`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK3cefgj8hb8dagduakjftajaw` (`created_by`),
  ADD KEY `FKqede8rb2j9kc4y7pb1ne5ics0` (`updated_by`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_nb4h0p6txrmfc0xbrd1kglp9t` (`name`);

--
-- Indexes for table `salaries`
--
ALTER TABLE `salaries`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK76h7p36mdxfw2hl5l9a9cagsv` (`created_by`),
  ADD KEY `FKaimhu17e0xlrkbl20vonr96xo` (`project_id`),
  ADD KEY `FKoow8pu7t818f22hkh5jbttswy` (`updated_by`),
  ADD KEY `FKbeqgsaawvcgypqm8c8wn9qcbk` (`worker_id`);

--
-- Indexes for table `sales_invoices`
--
ALTER TABLE `sales_invoices`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK40wjhpbae6076emb1xxla1lgr` (`account_number`),
  ADD KEY `FK85o6smi8xee5nkqwvycw34fw5` (`added_by`),
  ADD KEY `FKo8mnudm8g2jxhvuhg0vbwpq2u` (`approved_by`),
  ADD KEY `FKp8x36wfin689hyv8ghhlxywib` (`customer`),
  ADD KEY `FKjn4w8rpfp8a9x10437ygwx1iq` (`sales_matrial_types`),
  ADD KEY `FKb6kam168iyaxcfb4lb2spqk8h` (`store_id`),
  ADD KEY `FK562eayn9py4l2iuenm6il8dqy` (`updated_by`);

--
-- Indexes for table `sales_invoices_details`
--
ALTER TABLE `sales_invoices_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKag6b0jaodfoknhf54fvqgsadq` (`added_by`),
  ADD KEY `FKd1epnralhttoosm42laid0x07` (`batch_id`),
  ADD KEY `FKosv29ya9t993634rax7pe7288` (`item_code`),
  ADD KEY `FK44edolp01tr8vme7x9yggr3h8` (`sales_invoice`),
  ADD KEY `FKhjormn4kpqchufuy4757wtfik` (`store_id`),
  ADD KEY `FKis64kjb9j0ai9e40rfeul7yy2` (`uom_id`),
  ADD KEY `FKfr9xanxdxaro4u95nveqi923o` (`updated_by`),
  ADD KEY `FK23jcndqsyta25fj2uouwvx7ky` (`price_invoice`);

--
-- Indexes for table `sales_invoices_return`
--
ALTER TABLE `sales_invoices_return`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK2u7khslor9hgb1d9paawyeqnj` (`account_number`),
  ADD KEY `FKhfsaa5iisehioccl0l46iw69o` (`added_by`),
  ADD KEY `FKkcmtn5ggxap3bn1dcnktcc9wp` (`approved_by`),
  ADD KEY `FKmqgmqrcc1jp0bfycmc3du2f9o` (`customer`),
  ADD KEY `FKp7lj7b40qeg163ldvy930w6n9` (`updated_by`);

--
-- Indexes for table `sales_invoices_return_details`
--
ALTER TABLE `sales_invoices_return_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKnu7rrls20ddgm8cwyw0sdve2b` (`added_by`),
  ADD KEY `FKjs15yn6p3ry8n9v0n8rfxlube` (`batch_id`),
  ADD KEY `FK5wat4q3ub98ygusx9vbg7u8ee` (`item_id`),
  ADD KEY `FKbago77dg1ep3soe61plmhp7vb` (`sales_invoice_return`),
  ADD KEY `FK2tm8oo3ky4tyoxv17mgodseed` (`store_id`),
  ADD KEY `FKbrw4c8s5xwqcmr7itb1iwb5l3` (`uom_id`),
  ADD KEY `FKgdl0nwdyhsdvlu6hpsn9qis74` (`updated_by`);

--
-- Indexes for table `sales_matrial_types`
--
ALTER TABLE `sales_matrial_types`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sarf_permissions`
--
ALTER TABLE `sarf_permissions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgggctg22a0t7rwf5875kf76od` (`added_by`),
  ADD KEY `FK30eum13cls4lm0ekeh2e57wvp` (`customer`),
  ADD KEY `FKaehoj2kmpap0o7uv2d7ar6j6c` (`updated_by`),
  ADD KEY `FK1jdqh7msk5xcksm2v2crbfuwm` (`store_id`);

--
-- Indexes for table `sarf_permission_details`
--
ALTER TABLE `sarf_permission_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKp3ckoxj0u0ref9y8qkk37529u` (`added_by`),
  ADD KEY `FKn514he5ua1r99vfu5roogktwm` (`item_code`),
  ADD KEY `FKgbayrbbtb5ly20r7iywsqek3` (`sarf_permission_id`),
  ADD KEY `FKjyagm2tbv5yy4wk61datecq4b` (`store_id`),
  ADD KEY `FKf5u121ucgkaxh516pclpii89q` (`uom_id`),
  ADD KEY `FKfcffi9y0jbv9qqixthxtgg2r6` (`updated_by`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `service_order`
--
ALTER TABLE `service_order`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `service_order_detail`
--
ALTER TABLE `service_order_detail`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stores`
--
ALTER TABLE `stores`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8ywlloge78y0575pujehlvryf` (`added_by`),
  ADD KEY `FK17nx8kel1ec7wd3mo44u11pxe` (`updated_by`);

--
-- Indexes for table `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_sb00eg1yi8aj5ji9my5ctl7uq` (`account_number`),
  ADD KEY `FKsl8v2scpv13itxxthds6x4ylr` (`added_by`),
  ADD KEY `FK2ly160ahwqao40hkyqos1hj5u` (`suppliers_categories_id`),
  ADD KEY `FKm0xy3i26lepd4uh6gqeht9map` (`updated_by`);

--
-- Indexes for table `suppliers_categories`
--
ALTER TABLE `suppliers_categories`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbh6pfcyylnj8agkqaa6jy3kru` (`added_by`),
  ADD KEY `FK8v59o1xogxmndtn4wepcfnjxp` (`updated_by`);

--
-- Indexes for table `suppliers_with_orders`
--
ALTER TABLE `suppliers_with_orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK92hqv1gfnjdn17cs7hl589h1t` (`account_number`),
  ADD KEY `FKcwjf3kjeeuka0h1uufil064yc` (`added_by`),
  ADD KEY `FK8ce89eg6d5juv7ygxpp15g8fw` (`approved_by`),
  ADD KEY `FKqnq8753erek0oy0n306dhqmov` (`store_id`),
  ADD KEY `FK7ra2kat6qbw5pyh70yu72o071` (`supplier_id`),
  ADD KEY `FKr1rwbwj6ugo1ql7033btxqwud` (`updated_by`);

--
-- Indexes for table `suppliers_with_orders_details`
--
ALTER TABLE `suppliers_with_orders_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKj8q246xxhi6hqxbk3i6pc3uep` (`added_by`),
  ADD KEY `FK1abyeis4hj540172ua1jj6b12` (`batch_id`),
  ADD KEY `FK5udjhv8jd8rbx0wtm1kyn8kir` (`inv_item_id`),
  ADD KEY `FK6u1mqei3e8nrrx75163f4bxqr` (`item_code`),
  ADD KEY `FKsavkx70bnrd4hi3p5ptrqahb7` (`order_id`),
  ADD KEY `FKc9ouf1y9in0xro35cjeto9ehy` (`uom_id`),
  ADD KEY `FK3c359f6l3hqbjn4g27v6tdfma` (`updated_by`);

--
-- Indexes for table `token_info`
--
ALTER TABLE `token_info`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1cgkyxrrp65u31q209f6vmhoa` (`user_id`);

--
-- Indexes for table `treasuries`
--
ALTER TABLE `treasuries`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKcaiym6r16j2o6up73mdjaxd74` (`added_by`),
  ADD KEY `FKe1is8f1l92aq34vqrrdcaijdc` (`updated_by`);

--
-- Indexes for table `treasuries_delivery`
--
ALTER TABLE `treasuries_delivery`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `treasuries_transactions`
--
ALTER TABLE `treasuries_transactions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKik9i18ev61fpclusoakmd7bs7` (`account_number`),
  ADD KEY `FK7cl0aetd4t1uwyaphsgfm8qpd` (`added_by`),
  ADD KEY `FKake3j6vcd07avitkvdp7p78dg` (`treasure_id`),
  ADD KEY `FKgafgth7ob7cm0gtrks3fexhbr` (`updated_by`),
  ADD KEY `FKomqidbcj80fr2obyyajwcljpu` (`mov_type`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users_roles`
--
ALTER TABLE `users_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`);

--
-- Indexes for table `workers`
--
ALTER TABLE `workers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK2o6s1838u17caaqt4ww4d4nb9` (`created_by`),
  ADD KEY `FKlie4qvc80qavbth5g55xdlf9c` (`updated_by`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `account_types`
--
ALTER TABLE `account_types`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `admins_shifts`
--
ALTER TABLE `admins_shifts`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `admins_treasuries`
--
ALTER TABLE `admins_treasuries`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `admin_panel_settings`
--
ALTER TABLE `admin_panel_settings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `delegate`
--
ALTER TABLE `delegate`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `expenses`
--
ALTER TABLE `expenses`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `inv_itemcard`
--
ALTER TABLE `inv_itemcard`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `inv_itemcard_categories`
--
ALTER TABLE `inv_itemcard_categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `inv_itemcard_movement`
--
ALTER TABLE `inv_itemcard_movement`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT for table `inv_itemcard_movements_category`
--
ALTER TABLE `inv_itemcard_movements_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `inv_itemcard_movements_type`
--
ALTER TABLE `inv_itemcard_movements_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `inv_item_card_batch`
--
ALTER TABLE `inv_item_card_batch`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `inv_uoms`
--
ALTER TABLE `inv_uoms`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `mov_type`
--
ALTER TABLE `mov_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `owner_payment`
--
ALTER TABLE `owner_payment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `personal_access_token`
--
ALTER TABLE `personal_access_token`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `price_invoices`
--
ALTER TABLE `price_invoices`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `price_invoices_details`
--
ALTER TABLE `price_invoices_details`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `projects`
--
ALTER TABLE `projects`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `salaries`
--
ALTER TABLE `salaries`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `sales_invoices`
--
ALTER TABLE `sales_invoices`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `sales_invoices_details`
--
ALTER TABLE `sales_invoices_details`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `sales_invoices_return`
--
ALTER TABLE `sales_invoices_return`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `sales_invoices_return_details`
--
ALTER TABLE `sales_invoices_return_details`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `sales_matrial_types`
--
ALTER TABLE `sales_matrial_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sarf_permissions`
--
ALTER TABLE `sarf_permissions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `sarf_permission_details`
--
ALTER TABLE `sarf_permission_details`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `service_order`
--
ALTER TABLE `service_order`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `service_order_detail`
--
ALTER TABLE `service_order_detail`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `stores`
--
ALTER TABLE `stores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `suppliers_categories`
--
ALTER TABLE `suppliers_categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `suppliers_with_orders`
--
ALTER TABLE `suppliers_with_orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `suppliers_with_orders_details`
--
ALTER TABLE `suppliers_with_orders_details`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `token_info`
--
ALTER TABLE `token_info`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `treasuries`
--
ALTER TABLE `treasuries`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `treasuries_delivery`
--
ALTER TABLE `treasuries_delivery`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `treasuries_transactions`
--
ALTER TABLE `treasuries_transactions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `workers`
--
ALTER TABLE `workers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `accounts`
--
ALTER TABLE `accounts`
  ADD CONSTRAINT `FK70mh6nn5ow6yliuaeaq0kje9y` FOREIGN KEY (`parent_account_number`) REFERENCES `accounts` (`id`),
  ADD CONSTRAINT `FKcjt0qyitfg2qb2mmpgjm4r5xo` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKfewvxe4khtqi148b7v7cj1o49` FOREIGN KEY (`account_type`) REFERENCES `account_types` (`id`),
  ADD CONSTRAINT `FKk1datdiu7myu9m45ir80cggid` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `admin_panel_settings`
--
ALTER TABLE `admin_panel_settings`
  ADD CONSTRAINT `FKra1weq6voniankqvuomucbpmq` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `customers`
--
ALTER TABLE `customers`
  ADD CONSTRAINT `FKb3jo3nimp5vbdsp5p8oobuu3c` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKm57ejbuucgtyv8xkf2o98hhn2` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKor0fx9fttvasr4grtaqnltyrl` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`);

--
-- Constraints for table `expenses`
--
ALTER TABLE `expenses`
  ADD CONSTRAINT `FK8o6ghp4tj5x7f43jeyu72tkxj` FOREIGN KEY (`created_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKf2q37yjhkyxgx5n661c7e4vm8` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`),
  ADD CONSTRAINT `FKsj0q9a7mwsqfo3fmvbv0xlnsr` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `inv_itemcard`
--
ALTER TABLE `inv_itemcard`
  ADD CONSTRAINT `FK4na9v0u5i99ghnuk4md7msnfp` FOREIGN KEY (`inv_item_category_id`) REFERENCES `inv_itemcard_categories` (`id`),
  ADD CONSTRAINT `FK6ts4fqvg3euvssherm70a005p` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKi0c9ms884ob1ylw75v7xg1pwh` FOREIGN KEY (`uom_id`) REFERENCES `inv_uoms` (`id`),
  ADD CONSTRAINT `FKjj4w2uaiideywn6425t8esau2` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKjko9x8r2d9b9yb6aabf35s5aw` FOREIGN KEY (`parent_inv_item_id`) REFERENCES `inv_itemcard` (`id`),
  ADD CONSTRAINT `FKr9w60cj0ws7rti7ot6o5ouylw` FOREIGN KEY (`retail_uom_id`) REFERENCES `inv_uoms` (`id`);

--
-- Constraints for table `inv_itemcard_categories`
--
ALTER TABLE `inv_itemcard_categories`
  ADD CONSTRAINT `FK3dkchl5tm9jlmubldas0as299` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKptdsxiq1f6d3p7y1hv7t3n1r5` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `inv_itemcard_movement`
--
ALTER TABLE `inv_itemcard_movement`
  ADD CONSTRAINT `FK1aq16wcuhgispxuqa2w846cm0` FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`),
  ADD CONSTRAINT `FK1g9frmlqoq1r3hkcs5p5dl2y8` FOREIGN KEY (`items_movements_type_id`) REFERENCES `inv_itemcard_movements_type` (`id`),
  ADD CONSTRAINT `FKm50kttnc8n4of9yonmf0luj4y` FOREIGN KEY (`inv_itemcard_movements_category_id`) REFERENCES `inv_itemcard_movements_category` (`id`),
  ADD CONSTRAINT `FKtedya2bo3iius1dit134djkbe` FOREIGN KEY (`item_id`) REFERENCES `inv_itemcard` (`id`);

--
-- Constraints for table `inv_item_card_batch`
--
ALTER TABLE `inv_item_card_batch`
  ADD CONSTRAINT `FK3qtihirhyemhgfvnxichx7al8` FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`),
  ADD CONSTRAINT `FKi13r3yevhkcn9yvakd3fl5jua` FOREIGN KEY (`item_id`) REFERENCES `inv_itemcard` (`id`),
  ADD CONSTRAINT `FKncd1678wr13kbyuxdmhoynktc` FOREIGN KEY (`inv_uom_id`) REFERENCES `inv_uoms` (`id`);

--
-- Constraints for table `inv_stores_inventory`
--
ALTER TABLE `inv_stores_inventory`
  ADD CONSTRAINT `FKa7777qtsm6ydgolwhq9e2b5f1` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKexf4lrxpmfldb3il3opmo3c6m` FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`),
  ADD CONSTRAINT `FKn8oabsdtsbobbuas7mmxpgrsp` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `inv_uoms`
--
ALTER TABLE `inv_uoms`
  ADD CONSTRAINT `FK40a85k1pyvvjktcwcsejf7pwd` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKexbqunjsgcyi7rsrj4w99qwfl` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `owner_payment`
--
ALTER TABLE `owner_payment`
  ADD CONSTRAINT `FK8s83a6otvw8w5johy1np4k0fy` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKbfi55h77yjdf8ilt82v0a04kr` FOREIGN KEY (`created_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKn9cn4nk38e1mrnkb2jgu2e4nm` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`);

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `FK67l4771i0yo03594j13kvnv9` FOREIGN KEY (`worker_id`) REFERENCES `workers` (`id`),
  ADD CONSTRAINT `FK7h0as5hqhn845eewc7usiy0x3` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`),
  ADD CONSTRAINT `FKhyg4jkv8w3ooqhj7quheddfjj` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKpneh9iu8vpjka14dn7rj3erej` FOREIGN KEY (`created_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `price_invoices`
--
ALTER TABLE `price_invoices`
  ADD CONSTRAINT `FK4d2vakenou1j2liuqdbjs960l` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKto13xre20nb5i2dkoau73iow9` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `price_invoices_details`
--
ALTER TABLE `price_invoices_details`
  ADD CONSTRAINT `FKca1iblftux47mmxb5vn7pj7rl` FOREIGN KEY (`uom_id`) REFERENCES `inv_uoms` (`id`),
  ADD CONSTRAINT `FKlo1vd4mbbwgxyj4ublkeuq234` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKlpbmii98juewur4fde9dwwjei` FOREIGN KEY (`item_code`) REFERENCES `inv_itemcard` (`id`),
  ADD CONSTRAINT `FKmj3g6j3ccf59awmc7cptkla0p` FOREIGN KEY (`price_invoice`) REFERENCES `price_invoices` (`id`),
  ADD CONSTRAINT `FKtinx3bmuw57j3kxjeetyvcnx1` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `projects`
--
ALTER TABLE `projects`
  ADD CONSTRAINT `FK3cefgj8hb8dagduakjftajaw` FOREIGN KEY (`created_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKqede8rb2j9kc4y7pb1ne5ics0` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `salaries`
--
ALTER TABLE `salaries`
  ADD CONSTRAINT `FK76h7p36mdxfw2hl5l9a9cagsv` FOREIGN KEY (`created_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKaimhu17e0xlrkbl20vonr96xo` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`),
  ADD CONSTRAINT `FKbeqgsaawvcgypqm8c8wn9qcbk` FOREIGN KEY (`worker_id`) REFERENCES `workers` (`id`),
  ADD CONSTRAINT `FKoow8pu7t818f22hkh5jbttswy` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `sales_invoices`
--
ALTER TABLE `sales_invoices`
  ADD CONSTRAINT `FK40wjhpbae6076emb1xxla1lgr` FOREIGN KEY (`account_number`) REFERENCES `accounts` (`id`),
  ADD CONSTRAINT `FK562eayn9py4l2iuenm6il8dqy` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FK85o6smi8xee5nkqwvycw34fw5` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKb6kam168iyaxcfb4lb2spqk8h` FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`),
  ADD CONSTRAINT `FKjn4w8rpfp8a9x10437ygwx1iq` FOREIGN KEY (`sales_matrial_types`) REFERENCES `sales_matrial_types` (`id`),
  ADD CONSTRAINT `FKo8mnudm8g2jxhvuhg0vbwpq2u` FOREIGN KEY (`approved_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKp8x36wfin689hyv8ghhlxywib` FOREIGN KEY (`customer`) REFERENCES `customers` (`id`);

--
-- Constraints for table `sales_invoices_details`
--
ALTER TABLE `sales_invoices_details`
  ADD CONSTRAINT `FK23jcndqsyta25fj2uouwvx7ky` FOREIGN KEY (`price_invoice`) REFERENCES `price_invoices` (`id`),
  ADD CONSTRAINT `FK44edolp01tr8vme7x9yggr3h8` FOREIGN KEY (`sales_invoice`) REFERENCES `sales_invoices` (`id`),
  ADD CONSTRAINT `FKag6b0jaodfoknhf54fvqgsadq` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKd1epnralhttoosm42laid0x07` FOREIGN KEY (`batch_id`) REFERENCES `inv_item_card_batch` (`id`),
  ADD CONSTRAINT `FKfr9xanxdxaro4u95nveqi923o` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKhjormn4kpqchufuy4757wtfik` FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`),
  ADD CONSTRAINT `FKis64kjb9j0ai9e40rfeul7yy2` FOREIGN KEY (`uom_id`) REFERENCES `inv_uoms` (`id`),
  ADD CONSTRAINT `FKosv29ya9t993634rax7pe7288` FOREIGN KEY (`item_code`) REFERENCES `inv_itemcard` (`id`);

--
-- Constraints for table `sales_invoices_return`
--
ALTER TABLE `sales_invoices_return`
  ADD CONSTRAINT `FK2u7khslor9hgb1d9paawyeqnj` FOREIGN KEY (`account_number`) REFERENCES `accounts` (`id`),
  ADD CONSTRAINT `FKhfsaa5iisehioccl0l46iw69o` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKkcmtn5ggxap3bn1dcnktcc9wp` FOREIGN KEY (`approved_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKmqgmqrcc1jp0bfycmc3du2f9o` FOREIGN KEY (`customer`) REFERENCES `customers` (`id`),
  ADD CONSTRAINT `FKp7lj7b40qeg163ldvy930w6n9` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `sales_invoices_return_details`
--
ALTER TABLE `sales_invoices_return_details`
  ADD CONSTRAINT `FK2tm8oo3ky4tyoxv17mgodseed` FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`),
  ADD CONSTRAINT `FK5wat4q3ub98ygusx9vbg7u8ee` FOREIGN KEY (`item_id`) REFERENCES `inv_itemcard` (`id`),
  ADD CONSTRAINT `FKbago77dg1ep3soe61plmhp7vb` FOREIGN KEY (`sales_invoice_return`) REFERENCES `sales_invoices_return` (`id`),
  ADD CONSTRAINT `FKbrw4c8s5xwqcmr7itb1iwb5l3` FOREIGN KEY (`uom_id`) REFERENCES `inv_uoms` (`id`),
  ADD CONSTRAINT `FKgdl0nwdyhsdvlu6hpsn9qis74` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKjs15yn6p3ry8n9v0n8rfxlube` FOREIGN KEY (`batch_id`) REFERENCES `inv_item_card_batch` (`id`),
  ADD CONSTRAINT `FKnu7rrls20ddgm8cwyw0sdve2b` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `sarf_permissions`
--
ALTER TABLE `sarf_permissions`
  ADD CONSTRAINT `FK1jdqh7msk5xcksm2v2crbfuwm` FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`),
  ADD CONSTRAINT `FK30eum13cls4lm0ekeh2e57wvp` FOREIGN KEY (`customer`) REFERENCES `customers` (`id`),
  ADD CONSTRAINT `FKaehoj2kmpap0o7uv2d7ar6j6c` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKgggctg22a0t7rwf5875kf76od` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `sarf_permission_details`
--
ALTER TABLE `sarf_permission_details`
  ADD CONSTRAINT `FKf5u121ucgkaxh516pclpii89q` FOREIGN KEY (`uom_id`) REFERENCES `inv_uoms` (`id`),
  ADD CONSTRAINT `FKfcffi9y0jbv9qqixthxtgg2r6` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKgbayrbbtb5ly20r7iywsqek3` FOREIGN KEY (`sarf_permission_id`) REFERENCES `sarf_permissions` (`id`),
  ADD CONSTRAINT `FKjyagm2tbv5yy4wk61datecq4b` FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`),
  ADD CONSTRAINT `FKn514he5ua1r99vfu5roogktwm` FOREIGN KEY (`item_code`) REFERENCES `inv_itemcard` (`id`),
  ADD CONSTRAINT `FKp3ckoxj0u0ref9y8qkk37529u` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `stores`
--
ALTER TABLE `stores`
  ADD CONSTRAINT `FK17nx8kel1ec7wd3mo44u11pxe` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FK8ywlloge78y0575pujehlvryf` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `suppliers`
--
ALTER TABLE `suppliers`
  ADD CONSTRAINT `FK2ly160ahwqao40hkyqos1hj5u` FOREIGN KEY (`suppliers_categories_id`) REFERENCES `suppliers_categories` (`id`),
  ADD CONSTRAINT `FKm0xy3i26lepd4uh6gqeht9map` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKp0m6v7l9vlw9t1ctwovusopab` FOREIGN KEY (`account_number`) REFERENCES `accounts` (`id`),
  ADD CONSTRAINT `FKsl8v2scpv13itxxthds6x4ylr` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `suppliers_categories`
--
ALTER TABLE `suppliers_categories`
  ADD CONSTRAINT `FK8v59o1xogxmndtn4wepcfnjxp` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKbh6pfcyylnj8agkqaa6jy3kru` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `suppliers_with_orders`
--
ALTER TABLE `suppliers_with_orders`
  ADD CONSTRAINT `FK7ra2kat6qbw5pyh70yu72o071` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`),
  ADD CONSTRAINT `FK8ce89eg6d5juv7ygxpp15g8fw` FOREIGN KEY (`approved_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FK92hqv1gfnjdn17cs7hl589h1t` FOREIGN KEY (`account_number`) REFERENCES `accounts` (`id`),
  ADD CONSTRAINT `FKcwjf3kjeeuka0h1uufil064yc` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKqnq8753erek0oy0n306dhqmov` FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`),
  ADD CONSTRAINT `FKr1rwbwj6ugo1ql7033btxqwud` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `suppliers_with_orders_details`
--
ALTER TABLE `suppliers_with_orders_details`
  ADD CONSTRAINT `FK1abyeis4hj540172ua1jj6b12` FOREIGN KEY (`batch_id`) REFERENCES `inv_item_card_batch` (`id`),
  ADD CONSTRAINT `FK3c359f6l3hqbjn4g27v6tdfma` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FK5udjhv8jd8rbx0wtm1kyn8kir` FOREIGN KEY (`inv_item_id`) REFERENCES `inv_itemcard` (`id`),
  ADD CONSTRAINT `FK6u1mqei3e8nrrx75163f4bxqr` FOREIGN KEY (`item_code`) REFERENCES `inv_itemcard` (`id`),
  ADD CONSTRAINT `FKc9ouf1y9in0xro35cjeto9ehy` FOREIGN KEY (`uom_id`) REFERENCES `inv_uoms` (`id`),
  ADD CONSTRAINT `FKj8q246xxhi6hqxbk3i6pc3uep` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKsavkx70bnrd4hi3p5ptrqahb7` FOREIGN KEY (`order_id`) REFERENCES `suppliers_with_orders` (`id`);

--
-- Constraints for table `token_info`
--
ALTER TABLE `token_info`
  ADD CONSTRAINT `FK1cgkyxrrp65u31q209f6vmhoa` FOREIGN KEY (`user_id`) REFERENCES `admins` (`id`);

--
-- Constraints for table `treasuries`
--
ALTER TABLE `treasuries`
  ADD CONSTRAINT `FKcaiym6r16j2o6up73mdjaxd74` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKe1is8f1l92aq34vqrrdcaijdc` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `treasuries_transactions`
--
ALTER TABLE `treasuries_transactions`
  ADD CONSTRAINT `FK7cl0aetd4t1uwyaphsgfm8qpd` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKake3j6vcd07avitkvdp7p78dg` FOREIGN KEY (`treasure_id`) REFERENCES `treasuries` (`id`),
  ADD CONSTRAINT `FKgafgth7ob7cm0gtrks3fexhbr` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKik9i18ev61fpclusoakmd7bs7` FOREIGN KEY (`account_number`) REFERENCES `accounts` (`id`),
  ADD CONSTRAINT `FKomqidbcj80fr2obyyajwcljpu` FOREIGN KEY (`mov_type`) REFERENCES `mov_type` (`id`);

--
-- Constraints for table `users_roles`
--
ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK8sns6t0fr1thabomy9r5dhd96` FOREIGN KEY (`user_id`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

--
-- Constraints for table `workers`
--
ALTER TABLE `workers`
  ADD CONSTRAINT `FK2o6s1838u17caaqt4ww4d4nb9` FOREIGN KEY (`created_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKlie4qvc80qavbth5g55xdlf9c` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
