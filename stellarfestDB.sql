-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 10, 2024 at 11:41 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stellarfest`
--

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `EventID` varchar(8) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Date` date DEFAULT NULL,
  `Location` varchar(100) NOT NULL,
  `Description` varchar(200) NOT NULL,
  `OrganizerID` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`EventID`, `Name`, `Date`, `Location`, `Description`, `OrganizerID`) VALUES
('E0000000', 'Lorem Ipsum', '0000-00-00', 'Lorem Ipsum', 'NULL', '12345678'),
('E3162053', 'Kpop Concert', '2024-12-19', 'Seoul, South Korea', 'lorem', 'U7494612');

-- --------------------------------------------------------

--
-- Table structure for table `invitations`
--

CREATE TABLE `invitations` (
  `InvID` varchar(8) NOT NULL,
  `EventID` varchar(8) NOT NULL,
  `UserID` varchar(8) NOT NULL,
  `InvStatus` varchar(20) NOT NULL,
  `InvRole` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `invitations`
--

INSERT INTO `invitations` (`InvID`, `EventID`, `UserID`, `InvStatus`, `InvRole`) VALUES
('I6004521', 'E3162053', 'U2381756', 'Accepted', 'Guest'),
('I6706792', 'E3162053', 'U7337645', 'Accepted', 'Vendor');

-- --------------------------------------------------------

--
-- Table structure for table `producthandler`
--

CREATE TABLE `producthandler` (
  `ProdHandID` varchar(8) NOT NULL,
  `InvID` varchar(8) NOT NULL,
  `ProdID` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `ProductID` varchar(8) NOT NULL,
  `UserID` varchar(8) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Description` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`ProductID`, `UserID`, `Name`, `Description`) VALUES
('P1234569', '12345678', 'TestProduct', 'Lorem'),
('P8491917', 'U7337645', 'Test123', 'Lorem ipsum dolor sit amet');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `UserID` varchar(8) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `Role` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `Email`, `Name`, `Password`, `Role`) VALUES
('12345678', 'adeline@gmail.com', 'adelinechrltt', '1234567', 'Admin'),
('U2381756', 'golda@gmail.com', 'golda', 'golda1234', 'Guest'),
('U7337645', 'herobrine1234@gmail.com', 'herobrine1234', 'herobrine1234', 'Vendor'),
('U7494612', 'caelus@gmail.com', 'caelus', '123456', 'Event Organizer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`EventID`),
  ADD KEY `OrganizerID` (`OrganizerID`);

--
-- Indexes for table `invitations`
--
ALTER TABLE `invitations`
  ADD PRIMARY KEY (`InvID`),
  ADD KEY `EventID` (`EventID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `producthandler`
--
ALTER TABLE `producthandler`
  ADD PRIMARY KEY (`ProdHandID`),
  ADD KEY `InvID` (`InvID`),
  ADD KEY `ProdID` (`ProdID`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`ProductID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`UserID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `events`
--
ALTER TABLE `events`
  ADD CONSTRAINT `events_ibfk_1` FOREIGN KEY (`OrganizerID`) REFERENCES `users` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `invitations`
--
ALTER TABLE `invitations`
  ADD CONSTRAINT `invitations_ibfk_1` FOREIGN KEY (`EventID`) REFERENCES `events` (`EventID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `invitations_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `producthandler`
--
ALTER TABLE `producthandler`
  ADD CONSTRAINT `producthandler_ibfk_1` FOREIGN KEY (`InvID`) REFERENCES `invitations` (`InvID`),
  ADD CONSTRAINT `producthandler_ibfk_2` FOREIGN KEY (`ProdID`) REFERENCES `products` (`ProductID`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
