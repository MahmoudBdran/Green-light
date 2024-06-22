-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 22, 2024 at 11:57 PM
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
(1, b'1', '2024-06-14 16:56:31.000000', 501994.00, b'0', 'محمد', 'hkjhkjh', 0.00, 3, '2024-06-22 19:37:35.000000', 2, 1, 1, 1),
(2, b'1', '2024-06-14 18:53:30.000000', 0.00, b'1', 'مصروفات عامة', NULL, 0.00, 3, '2024-06-14 18:53:30.000000', 7, 1, NULL, 1),
(3, b'1', NULL, 0.00, b'1', 'العملاء الأب', NULL, 0.00, 3, '2024-06-14 22:03:04.000000', 9, NULL, NULL, 1),
(4, b'1', '2024-06-14 18:55:56.000000', 0.00, b'1', 'الموردين الأب', '', 0.00, 3, '2024-06-14 18:55:56.000000', 9, 1, NULL, 1),
(5, b'1', '2024-06-14 19:01:38.000000', 0.00, b'0', 'علي سعد', 'خن', 0.00, 3, '2024-06-14 19:01:38.000000', 3, 1, 3, 1);

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
  `com_code` int(11) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(225) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `username` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`id`, `added_by`, `com_code`, `created_at`, `email`, `name`, `password`, `updated_at`, `updated_by`, `username`) VALUES
(1, 1, 100, NULL, 'admin1@yourdomain.com', 'John Doe', 'password123', NULL, NULL, 'admin_john'),
(2, 2, 200, NULL, 'admin2@yourdomain.com', 'Jane Smith', 'temp_password123', NULL, NULL, 'admin_jane');

-- --------------------------------------------------------

--
-- Table structure for table `admins_shifts`
--

CREATE TABLE `admins_shifts` (
  `id` bigint(20) NOT NULL,
  `added_by` int(11) NOT NULL,
  `admin_id` int(11) NOT NULL,
  `com_code` int(11) NOT NULL,
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
  `com_code` int(11) NOT NULL,
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
  `created_at` datetime(6) NOT NULL,
  `customer_parent_account_number` bigint(20) NOT NULL,
  `employees_parent_account_number` bigint(20) NOT NULL,
  `general_alert` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) NOT NULL,
  `suppliers_parent_account_number` bigint(20) NOT NULL,
  `system_name` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin_panel_settings`
--

INSERT INTO `admin_panel_settings` (`id`, `active`, `address`, `created_at`, `customer_parent_account_number`, `employees_parent_account_number`, `general_alert`, `notes`, `phone`, `photo`, `suppliers_parent_account_number`, `system_name`, `updated_at`, `added_by`, `updated_by`) VALUES
(1, b'1', 'القاهرة صلاح سالم', '2024-06-11 10:43:27.000000', 3, 1, NULL, 'werwer', '345345345345', 'يبشيب', 4, 'Green Light', '2024-06-14 18:56:08.000000', 1, 1);

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
(1, b'1', 'كفر الزيات', '2024-06-14 19:01:38.000000', 0.00, 'علي سعد', 'خن', '0126565459', 0.00, 3, '2024-06-14 22:02:32.000000', 5, 1, 1);

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
  `gomla_price` decimal(38,2) NOT NULL,
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
  `inv_item_category_id` bigint(20) NOT NULL,
  `added_by` int(11) DEFAULT NULL,
  `parent_inv_item_id` bigint(20) DEFAULT NULL,
  `retail_uom_id` bigint(20) DEFAULT NULL,
  `uom_id` bigint(20) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inv_itemcard`
--

INSERT INTO `inv_itemcard` (`id`, `quentity`, `quentityall_retails`, `quentityretail`, `active`, `allquentity`, `barcode`, `cost_price`, `cost_price_retail`, `created_at`, `does_has_retailunit`, `gomla_price`, `gomla_price_retail`, `has_fixced_price`, `item_type`, `name`, `nos_gomla_price`, `nos_gomla_price_retail`, `photo`, `price`, `price_retail`, `retail_uom_qunt_to_parent`, `updated_at`, `inv_item_category_id`, `added_by`, `parent_inv_item_id`, `retail_uom_id`, `uom_id`, `updated_by`) VALUES
(2, NULL, NULL, NULL, b'1', NULL, 'qwe', 12.00, 1.00, NULL, b'1', 12.00, 12.00, b'0', 1, 'لفة سلك نحاس', 12.00, 12.00, NULL, 12.00, 12.00, 12.00, '2024-06-22 19:37:35.000000', 1, NULL, NULL, 2, 1, 1),
(8, NULL, NULL, NULL, b'1', NULL, 'qwe', 12.00, 1.00, '2024-06-14 19:13:06.000000', b'1', 12.00, 12.00, b'0', 1, 'بكرة سلوك', 12.00, 12.00, NULL, 12.00, 12.00, 12.00, '2024-06-22 18:08:15.000000', 1, 1, NULL, 2, 1, 1),
(9, NULL, NULL, NULL, b'1', NULL, 'qwe', 12.00, 1.00, '2024-06-14 19:15:12.000000', b'1', 12.00, 12.00, b'0', 1, 'مسامير 10 مم', 12.00, 12.00, NULL, 12.00, 12.00, 12.00, '2024-06-22 18:08:15.000000', 1, 1, NULL, 2, 1, 1),
(10, NULL, NULL, NULL, b'1', NULL, '234234234234', 12.00, 1.00, NULL, b'1', 12.00, 12.00, b'0', 1, 'مصباح ليد', 12.00, 12.00, NULL, 12.00, 12.00, 12.00, '2024-06-22 18:08:15.000000', 1, NULL, NULL, 2, 1, 1);

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
(2, b'1', NULL, 'معدات', NULL, NULL, NULL);

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
(6, 'نظير مشتريات من المورد  محمد فاتورة رقم 1', 'عدد  23.00 كرتونة', 'عدد  23.00 كرتونة', 'عدد  null كرتونة', 'عدد  null كرتونة', 1, 1, 9, 1),
(7, 'نظير مشتريات من المورد  محمد فاتورة رقم 1', 'عدد  15.00 كرتونة', 'عدد  15.00 كرتونة', 'عدد  null كرتونة', 'عدد  null كرتونة', 1, 1, 8, 1),
(8, 'نظير مشتريات من المورد  محمد فاتورة رقم 1', 'عدد  2.00 علبة', 'عدد  2.00 علبة', 'عدد  null علبة', 'عدد  null علبة', 1, 1, 2, 1),
(9, 'نظير مشتريات من المورد  محمد فاتورة رقم 1', 'عدد  1.00 كرتونة', 'عدد  1.00 كرتونة', 'عدد  null كرتونة', 'عدد  null كرتونة', 1, 1, 10, 1),
(10, 'نظير مشتريات من المورد  محمد فاتورة رقم 1', 'عدد  23.00 كرتونة', 'عدد  23.00 كرتونة', 'عدد  23.00 كرتونة', 'عدد  23.00 كرتونة', 1, 1, 9, 1),
(11, 'نظير مشتريات من المورد  محمد فاتورة رقم 1', 'عدد  15.00 كرتونة', 'عدد  15.00 كرتونة', 'عدد  15.00 كرتونة', 'عدد  15.00 كرتونة', 1, 1, 8, 1),
(12, 'نظير مشتريات من المورد  محمد فاتورة رقم 1', 'عدد  2.00 علبة', 'عدد  2.00 علبة', 'عدد  2.00 علبة', 'عدد  2.00 علبة', 1, 1, 2, 1),
(13, 'نظير مشتريات من المورد  محمد فاتورة رقم 1', 'عدد  1.00 كرتونة', 'عدد  1.00 كرتونة', 'عدد  1.00 كرتونة', 'عدد  1.00 كرتونة', 1, 1, 10, 1),
(14, 'نظير مشتريات من المورد  محمد فاتورة رقم 1', 'عدد  23.00 كرتونة', 'عدد  23.00 كرتونة', 'عدد  23.00 كرتونة', 'عدد  23.00 كرتونة', 1, 1, 9, 1),
(15, 'نظير مشتريات من المورد  محمد فاتورة رقم 1', 'عدد  15.00 كرتونة', 'عدد  15.00 كرتونة', 'عدد  15.00 كرتونة', 'عدد  15.00 كرتونة', 1, 1, 8, 1),
(16, 'نظير مشتريات من المورد  محمد فاتورة رقم 1', 'عدد  2.00 علبة', 'عدد  2.00 علبة', 'عدد  2.00 علبة', 'عدد  2.00 علبة', 1, 1, 2, 1),
(17, 'نظير مشتريات من المورد  محمد فاتورة رقم 1', 'عدد  1.00 كرتونة', 'عدد  1.00 كرتونة', 'عدد  1.00 كرتونة', 'عدد  1.00 كرتونة', 1, 1, 10, 1),
(18, 'نظير مشتريات من المورد  محمد فاتورة رقم 1', 'عدد  6.00 كرتونة', 'عدد  6.00 كرتونة', 'عدد  2.00 كرتونة', 'عدد  2.00 كرتونة', 1, 1, 2, 1),
(19, 'نظير مشتريات من المورد  محمد فاتورة رقم 1', 'عدد  25.00 كرتونة', 'عدد  25.00 كرتونة', 'عدد  23.00 كرتونة', 'عدد  23.00 كرتونة', 1, 1, 9, 1),
(20, 'نظير مشتريات من المورد  محمد فاتورة رقم 1', 'عدد  3.00 علبة', 'عدد  3.00 علبة', 'عدد  1.00 علبة', 'عدد  1.00 علبة', 1, 1, 10, 1);

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
(9, '2024-06-22', NULL, '2024-06-22', 23.00, 276.00, 12.00, 1, 9, 1),
(10, '2024-06-22', NULL, '2024-06-22', 15.00, 180.00, 12.00, 1, 8, 1),
(11, '2024-06-22', NULL, '2024-06-22', 2.00, 20.00, 10.00, 2, 2, 1),
(12, '2024-06-22', NULL, '2024-06-22', 1.00, 12.00, 12.00, 1, 10, 1),
(13, '2024-06-22', NULL, '2024-06-22', 4.00, 48.00, 12.00, 1, 2, 1),
(14, '2024-06-22', NULL, '2024-06-22', 2.00, 24.00, 12.00, 1, 9, 1),
(15, '2024-06-22', NULL, '2024-06-22', 2.00, 24.00, 12.00, 2, 10, 1);

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
  `com_code` int(11) DEFAULT NULL,
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
  `com_code` int(11) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `inventory_date` date DEFAULT NULL,
  `inventory_type` int(11) DEFAULT NULL,
  `is_closed` bit(1) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `total_cost_batches` decimal(38,2) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
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
  `com_code` int(11) DEFAULT NULL,
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
  `name` varchar(250) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `master` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inv_uoms`
--

INSERT INTO `inv_uoms` (`id`, `active`, `created_at`, `name`, `updated_at`, `added_by`, `updated_by`, `master`) VALUES
(1, b'1', NULL, 'كرتونة', '2024-06-14 19:39:10.000000', NULL, 1, b'1'),
(2, b'1', NULL, 'علبة', '2024-06-14 19:39:14.000000', NULL, 1, b'1'),
(3, b'1', NULL, 'لفة', '2024-06-14 19:39:06.000000', NULL, 1, b'1');

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
-- Table structure for table `password_reset`
--

CREATE TABLE `password_reset` (
  `email` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
-- Table structure for table `sales_invoices`
--

CREATE TABLE `sales_invoices` (
  `id` bigint(20) NOT NULL,
  `account_number` bigint(20) DEFAULT NULL,
  `added_by` int(11) NOT NULL,
  `approved_by` int(11) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `customer_balance_after` decimal(10,2) DEFAULT NULL,
  `customer_balance_befor` decimal(10,2) DEFAULT NULL,
  `customer_code` bigint(20) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `delegate_code` bigint(20) DEFAULT NULL,
  `discount_percent` decimal(10,2) NOT NULL,
  `discount_type` tinyint(4) DEFAULT NULL,
  `discount_value` decimal(10,2) NOT NULL,
  `invoice_date` date NOT NULL,
  `is_approved` bit(1) NOT NULL,
  `is_has_customer` bit(1) NOT NULL,
  `money_for_account` decimal(10,2) DEFAULT NULL,
  `notes` varchar(225) DEFAULT NULL,
  `pill_type` tinyint(4) DEFAULT NULL,
  `sales_item_type` tinyint(4) NOT NULL,
  `sales_matrial_types` int(11) DEFAULT NULL,
  `tax_percent` decimal(10,2) NOT NULL,
  `tax_value` decimal(10,2) NOT NULL,
  `total_befor_discount` decimal(10,2) NOT NULL,
  `total_cost` decimal(10,2) NOT NULL,
  `total_cost_items` decimal(10,2) NOT NULL,
  `treasuries_transactions_id` bigint(20) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `what_paid` decimal(10,2) NOT NULL,
  `what_remain` decimal(10,2) NOT NULL,
  `customer` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sales_invoices`
--

INSERT INTO `sales_invoices` (`id`, `account_number`, `added_by`, `approved_by`, `created_at`, `customer_balance_after`, `customer_balance_befor`, `customer_code`, `date`, `delegate_code`, `discount_percent`, `discount_type`, `discount_value`, `invoice_date`, `is_approved`, `is_has_customer`, `money_for_account`, `notes`, `pill_type`, `sales_item_type`, `sales_matrial_types`, `tax_percent`, `tax_value`, `total_befor_discount`, `total_cost`, `total_cost_items`, `treasuries_transactions_id`, `updated_at`, `updated_by`, `what_paid`, `what_remain`, `customer`) VALUES
(2, 5, 1, NULL, '2024-06-22 23:56:03.000000', NULL, NULL, NULL, NULL, NULL, 0.00, NULL, 0.00, '2024-06-22', b'0', b'1', 0.00, 'this is my note', 1, 1, NULL, 0.00, 0.00, 0.00, 0.00, 0.00, NULL, '2024-06-22 23:56:03.000000', 1, 0.00, 0.00, 1);

-- --------------------------------------------------------

--
-- Table structure for table `sales_invoices_details`
--

CREATE TABLE `sales_invoices_details` (
  `id` bigint(20) NOT NULL,
  `added_by` int(11) NOT NULL,
  `batch_auto_serial` bigint(20) DEFAULT NULL,
  `com_code` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `date` date NOT NULL,
  `expire_date` date DEFAULT NULL,
  `invoice_date` date NOT NULL,
  `is_normal_or_other` tinyint(4) NOT NULL,
  `isparentuom` bit(1) NOT NULL,
  `item_code` bigint(20) NOT NULL,
  `production_date` date DEFAULT NULL,
  `quantity` decimal(10,4) NOT NULL,
  `sales_invoices_auto_serial` bigint(20) NOT NULL,
  `sales_item_type` tinyint(4) NOT NULL,
  `store_id` int(11) NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `uom_id` bigint(20) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `sales_invoice` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sales_invoices_details_stores`
--

CREATE TABLE `sales_invoices_details_stores` (
  `sales_invoice_detail_id` bigint(20) NOT NULL,
  `stores_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sales_invoices_details_store_id`
--

CREATE TABLE `sales_invoices_details_store_id` (
  `sales_invoice_detail_id` bigint(20) NOT NULL,
  `store_id_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sales_invoices_return`
--

CREATE TABLE `sales_invoices_return` (
  `id` bigint(20) NOT NULL,
  `account_number` bigint(20) DEFAULT NULL,
  `added_by` int(11) NOT NULL,
  `approved_by` int(11) DEFAULT NULL,
  `auto_serial` bigint(20) NOT NULL,
  `com_code` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `customer_balance_after` decimal(10,2) DEFAULT NULL,
  `customer_balance_befor` decimal(10,2) DEFAULT NULL,
  `customer_code` bigint(20) DEFAULT NULL,
  `date` date NOT NULL,
  `delegate_code` bigint(20) DEFAULT NULL,
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
  `sales_matrial_types` int(11) DEFAULT NULL,
  `tax_percent` decimal(10,2) NOT NULL,
  `tax_value` decimal(10,2) NOT NULL,
  `total_befor_discount` decimal(10,2) NOT NULL,
  `total_cost` decimal(10,2) NOT NULL,
  `total_cost_items` decimal(10,2) NOT NULL,
  `treasuries_transactions_id` bigint(20) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `what_paid` decimal(10,2) NOT NULL,
  `what_remain` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sales_invoices_return_details`
--

CREATE TABLE `sales_invoices_return_details` (
  `id` bigint(20) NOT NULL,
  `added_by` int(11) NOT NULL,
  `batch_auto_serial` bigint(20) DEFAULT NULL,
  `com_code` int(11) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `date` date NOT NULL,
  `expire_date` date DEFAULT NULL,
  `invoice_date` date NOT NULL,
  `is_normal_or_other` int(11) NOT NULL,
  `isparentuom` int(11) NOT NULL,
  `item_code` bigint(20) NOT NULL,
  `production_date` date DEFAULT NULL,
  `quantity` decimal(10,4) DEFAULT NULL,
  `sales_invoices_auto_serial` bigint(20) NOT NULL,
  `sales_item_type` int(11) NOT NULL,
  `store_id` int(11) NOT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `unit_cost_price` decimal(10,2) DEFAULT NULL,
  `unit_price` decimal(10,2) DEFAULT NULL,
  `uom_id` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `id` int(11) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `com_code` int(11) DEFAULT NULL,
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
  `com_code` int(11) DEFAULT NULL,
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
  `com_code` int(11) DEFAULT NULL,
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
  `updated_by` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stores`
--

INSERT INTO `stores` (`id`, `address`, `created_at`, `active`, `name`, `phones`, `updated_at`, `added_by`, `updated_by`, `date`) VALUES
(1, 'حلوان', NULL, b'1', 'المخزن الرئيسي', '0101845545454', NULL, NULL, NULL, NULL);

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
(1, b'1', 'kjh', '2024-06-14 16:56:31.000000', 501994.00, 'محمد', 'hkjhkjh', '987987987', 0.00, 3, '2024-06-22 19:37:35.000000', 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `suppliers_categories`
--

CREATE TABLE `suppliers_categories` (
  `id` int(11) NOT NULL,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `name` varchar(250) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `added_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `suppliers_categories`
--

INSERT INTO `suppliers_categories` (`id`, `active`, `created_at`, `name`, `updated_at`, `added_by`, `updated_by`) VALUES
(1, b'1', '2024-06-08 23:00:59.000000', 'كهرباء', '2024-06-08 23:00:59.000000', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `suppliers_with_orders`
--

CREATE TABLE `suppliers_with_orders` (
  `id` bigint(20) NOT NULL,
  `auto_serial` bigint(20) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `discount_percent` decimal(10,2) DEFAULT NULL,
  `discount_type` tinyint(4) DEFAULT NULL,
  `discount_value` decimal(10,2) NOT NULL,
  `doc_no` varchar(25) DEFAULT NULL,
  `is_approved` bit(1) NOT NULL,
  `money_for_account` decimal(10,2) DEFAULT NULL,
  `notes` varchar(225) DEFAULT NULL,
  `order_date` date NOT NULL,
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

INSERT INTO `suppliers_with_orders` (`id`, `auto_serial`, `created_at`, `discount_percent`, `discount_type`, `discount_value`, `doc_no`, `is_approved`, `money_for_account`, `notes`, `order_date`, `order_type`, `pill_type`, `supplier_balance_after`, `supplier_balance_befor`, `tax_percent`, `tax_value`, `total_befor_discount`, `total_cost`, `total_cost_items`, `treasuries_transactions_id`, `updated_at`, `what_paid`, `what_remain`, `account_number`, `added_by`, `approved_by`, `store_id`, `supplier_id`, `updated_by`) VALUES
(1, NULL, '2024-06-22 19:18:04.000000', NULL, 2, 0.00, '1234', b'1', -96.00, 'asd', '2024-06-22', 1, 2, NULL, NULL, NULL, NULL, 96.00, 96.00, 0.00, NULL, '2024-06-22 19:37:34.000000', 90.00, 0.00, 1, 1, NULL, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `suppliers_with_orders_details`
--

CREATE TABLE `suppliers_with_orders_details` (
  `id` bigint(20) NOT NULL,
  `batch_auto_serial` bigint(20) DEFAULT NULL,
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
  `inv_item_id` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `uom_id` bigint(20) NOT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `suppliers_with_orders_details`
--

INSERT INTO `suppliers_with_orders_details` (`id`, `batch_auto_serial`, `created_at`, `deliverd_quantity`, `expire_date`, `isparentuom`, `item_card_type`, `order_type`, `production_date`, `total_price`, `unit_price`, `updated_at`, `added_by`, `inv_item_id`, `order_id`, `uom_id`, `updated_by`) VALUES
(1, 1, '2024-06-22 19:18:13.000000', 4.00, NULL, b'1', 1, 1, NULL, 48.00, 12.00, '2024-06-22 19:19:02.000000', 1, 2, 1, 1, 1),
(5, 1, '2024-06-22 19:34:52.000000', 2.00, NULL, b'1', 1, 1, NULL, 24.00, 12.00, '2024-06-22 19:34:52.000000', 1, 9, 1, 1, 1),
(6, 1, '2024-06-22 19:35:43.000000', 2.00, NULL, b'1', 1, 1, NULL, 24.00, 12.00, '2024-06-22 19:35:43.000000', 1, 10, 1, 2, 1);

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
(1, '2024-06-22 15:35:03.000000', b'1', b'1', 1, 17, 'الرئيسية', '2024-06-22 19:37:34.000000', 1, 1);

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
(1, 'asd', NULL, b'1', b'1', 1, 500000.00, 500000.00, 5, '2024-06-22', 1, NULL, NULL, 1, 1, 1, NULL),
(4, 'صرف نظير فاتورة مشتريات  رقم1', '2024-06-22 16:13:23.000000', b'1', b'1', 1, 500.00, 500.00, 9, '2024-06-22', 1, NULL, '2024-06-22 16:13:23.000000', 1, 1, 1, 1),
(14, 'صرف نظير فاتورة مشتريات  رقم1', '2024-06-22 18:08:15.000000', b'1', b'1', 1, 500.00, 500.00, 9, '2024-06-22', 1, NULL, '2024-06-22 18:08:15.000000', 1, 1, 1, 1),
(15, 'صرف نظير فاتورة مشتريات  رقم1', '2024-06-22 18:11:51.000000', b'1', b'1', 1, 500.00, 500.00, 9, '2024-06-22', 1, NULL, '2024-06-22 18:11:51.000000', 1, 1, 1, 1),
(16, 'صرف نظير فاتورة مشتريات  رقم1', '2024-06-22 18:46:59.000000', b'1', b'1', 1, 500.00, 500.00, 9, '2024-06-22', 1, NULL, '2024-06-22 18:46:59.000000', 1, 1, 1, 1),
(17, 'صرف نظير فاتورة مشتريات  رقم1', '2024-06-22 19:37:34.000000', b'1', b'1', 1, 90.00, 90.00, 9, '2024-06-22', 1, NULL, '2024-06-22 19:37:34.000000', 1, 1, 1, 1);

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
  ADD KEY `FKra1weq6voniankqvuomucbpmq` (`added_by`),
  ADD KEY `FKlw1wofen9xd5t27inakpaitkb` (`updated_by`);

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
-- Indexes for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `inv_itemcard`
--
ALTER TABLE `inv_itemcard`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4na9v0u5i99ghnuk4md7msnfp` (`inv_item_category_id`),
  ADD KEY `FKjj4w2uaiideywn6425t8esau2` (`added_by`),
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
  ADD PRIMARY KEY (`id`);

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
-- Indexes for table `password_reset`
--
ALTER TABLE `password_reset`
  ADD PRIMARY KEY (`email`);

--
-- Indexes for table `personal_access_token`
--
ALTER TABLE `personal_access_token`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sales_invoices`
--
ALTER TABLE `sales_invoices`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK85o6smi8xee5nkqwvycw34fw5` (`added_by`),
  ADD KEY `FKo8mnudm8g2jxhvuhg0vbwpq2u` (`approved_by`),
  ADD KEY `FKp8x36wfin689hyv8ghhlxywib` (`customer`),
  ADD KEY `FKjn4w8rpfp8a9x10437ygwx1iq` (`sales_matrial_types`),
  ADD KEY `FK562eayn9py4l2iuenm6il8dqy` (`updated_by`),
  ADD KEY `FK40wjhpbae6076emb1xxla1lgr` (`account_number`);

--
-- Indexes for table `sales_invoices_details`
--
ALTER TABLE `sales_invoices_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKag6b0jaodfoknhf54fvqgsadq` (`added_by`),
  ADD KEY `FKosv29ya9t993634rax7pe7288` (`item_code`),
  ADD KEY `FK44edolp01tr8vme7x9yggr3h8` (`sales_invoice`),
  ADD KEY `FKis64kjb9j0ai9e40rfeul7yy2` (`uom_id`),
  ADD KEY `FKfr9xanxdxaro4u95nveqi923o` (`updated_by`);

--
-- Indexes for table `sales_invoices_details_stores`
--
ALTER TABLE `sales_invoices_details_stores`
  ADD KEY `FKfkaul42h0lhw4yji5cw4uqj08` (`stores_id`),
  ADD KEY `FKhi0vhur35sup6mkbewjpup8l0` (`sales_invoice_detail_id`);

--
-- Indexes for table `sales_invoices_details_store_id`
--
ALTER TABLE `sales_invoices_details_store_id`
  ADD KEY `FKbndj4qn876aidnbfcqh0q6mig` (`store_id_id`),
  ADD KEY `FKc9tr25nrmird1nv5akswyhe7b` (`sales_invoice_detail_id`);

--
-- Indexes for table `sales_invoices_return`
--
ALTER TABLE `sales_invoices_return`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sales_invoices_return_details`
--
ALTER TABLE `sales_invoices_return_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sales_matrial_types`
--
ALTER TABLE `sales_matrial_types`
  ADD PRIMARY KEY (`id`);

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
  ADD KEY `FK5udjhv8jd8rbx0wtm1kyn8kir` (`inv_item_id`),
  ADD KEY `FKsavkx70bnrd4hi3p5ptrqahb7` (`order_id`),
  ADD KEY `FKc9ouf1y9in0xro35cjeto9ehy` (`uom_id`),
  ADD KEY `FK3c359f6l3hqbjn4g27v6tdfma` (`updated_by`);

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
  ADD KEY `FKgafgth7ob7cm0gtrks3fexhbr` (`updated_by`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `delegate`
--
ALTER TABLE `delegate`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `failed_jobs`
--
ALTER TABLE `failed_jobs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `inv_itemcard`
--
ALTER TABLE `inv_itemcard`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `inv_itemcard_categories`
--
ALTER TABLE `inv_itemcard_categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `inv_itemcard_movement`
--
ALTER TABLE `inv_itemcard_movement`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `inv_uoms`
--
ALTER TABLE `inv_uoms`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `mov_type`
--
ALTER TABLE `mov_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `personal_access_token`
--
ALTER TABLE `personal_access_token`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sales_invoices`
--
ALTER TABLE `sales_invoices`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `sales_invoices_details`
--
ALTER TABLE `sales_invoices_details`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sales_invoices_return`
--
ALTER TABLE `sales_invoices_return`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sales_invoices_return_details`
--
ALTER TABLE `sales_invoices_return_details`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sales_matrial_types`
--
ALTER TABLE `sales_matrial_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `suppliers_categories`
--
ALTER TABLE `suppliers_categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `suppliers_with_orders`
--
ALTER TABLE `suppliers_with_orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `suppliers_with_orders_details`
--
ALTER TABLE `suppliers_with_orders_details`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

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
  ADD CONSTRAINT `FKlw1wofen9xd5t27inakpaitkb` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKra1weq6voniankqvuomucbpmq` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `customers`
--
ALTER TABLE `customers`
  ADD CONSTRAINT `FKb3jo3nimp5vbdsp5p8oobuu3c` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKm57ejbuucgtyv8xkf2o98hhn2` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKor0fx9fttvasr4grtaqnltyrl` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`);

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
-- Constraints for table `inv_uoms`
--
ALTER TABLE `inv_uoms`
  ADD CONSTRAINT `FK40a85k1pyvvjktcwcsejf7pwd` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKexbqunjsgcyi7rsrj4w99qwfl` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`);

--
-- Constraints for table `sales_invoices`
--
ALTER TABLE `sales_invoices`
  ADD CONSTRAINT `FK40wjhpbae6076emb1xxla1lgr` FOREIGN KEY (`account_number`) REFERENCES `accounts` (`id`),
  ADD CONSTRAINT `FK562eayn9py4l2iuenm6il8dqy` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FK85o6smi8xee5nkqwvycw34fw5` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKjn4w8rpfp8a9x10437ygwx1iq` FOREIGN KEY (`sales_matrial_types`) REFERENCES `sales_matrial_types` (`id`),
  ADD CONSTRAINT `FKo8mnudm8g2jxhvuhg0vbwpq2u` FOREIGN KEY (`approved_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKp8x36wfin689hyv8ghhlxywib` FOREIGN KEY (`customer`) REFERENCES `customers` (`id`);

--
-- Constraints for table `sales_invoices_details`
--
ALTER TABLE `sales_invoices_details`
  ADD CONSTRAINT `FK44edolp01tr8vme7x9yggr3h8` FOREIGN KEY (`sales_invoice`) REFERENCES `sales_invoices` (`id`),
  ADD CONSTRAINT `FKag6b0jaodfoknhf54fvqgsadq` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKfr9xanxdxaro4u95nveqi923o` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKis64kjb9j0ai9e40rfeul7yy2` FOREIGN KEY (`uom_id`) REFERENCES `inv_uoms` (`id`),
  ADD CONSTRAINT `FKosv29ya9t993634rax7pe7288` FOREIGN KEY (`item_code`) REFERENCES `inv_itemcard` (`id`);

--
-- Constraints for table `sales_invoices_details_stores`
--
ALTER TABLE `sales_invoices_details_stores`
  ADD CONSTRAINT `FKfkaul42h0lhw4yji5cw4uqj08` FOREIGN KEY (`stores_id`) REFERENCES `stores` (`id`),
  ADD CONSTRAINT `FKhi0vhur35sup6mkbewjpup8l0` FOREIGN KEY (`sales_invoice_detail_id`) REFERENCES `sales_invoices_details` (`id`);

--
-- Constraints for table `sales_invoices_details_store_id`
--
ALTER TABLE `sales_invoices_details_store_id`
  ADD CONSTRAINT `FKbndj4qn876aidnbfcqh0q6mig` FOREIGN KEY (`store_id_id`) REFERENCES `stores` (`id`),
  ADD CONSTRAINT `FKc9tr25nrmird1nv5akswyhe7b` FOREIGN KEY (`sales_invoice_detail_id`) REFERENCES `sales_invoices_details` (`id`);

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
  ADD CONSTRAINT `FK3c359f6l3hqbjn4g27v6tdfma` FOREIGN KEY (`updated_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FK5udjhv8jd8rbx0wtm1kyn8kir` FOREIGN KEY (`inv_item_id`) REFERENCES `inv_itemcard` (`id`),
  ADD CONSTRAINT `FKc9ouf1y9in0xro35cjeto9ehy` FOREIGN KEY (`uom_id`) REFERENCES `inv_uoms` (`id`),
  ADD CONSTRAINT `FKj8q246xxhi6hqxbk3i6pc3uep` FOREIGN KEY (`added_by`) REFERENCES `admins` (`id`),
  ADD CONSTRAINT `FKsavkx70bnrd4hi3p5ptrqahb7` FOREIGN KEY (`order_id`) REFERENCES `suppliers_with_orders` (`id`);

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
  ADD CONSTRAINT `FKik9i18ev61fpclusoakmd7bs7` FOREIGN KEY (`account_number`) REFERENCES `accounts` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
