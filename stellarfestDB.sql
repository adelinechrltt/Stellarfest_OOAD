-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 19, 2024 at 06:38 PM
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
('E4443633', 'Testing123', '2024-12-20', 'AAAAAAAAAHHH(1)', 'AAAAAAAAAHHH(2)', 'U3730035'),
('E9471165', 'YAYAYA', '2024-12-20', 'lorem ipsum', 'lorem ipsum', 'U3730035');

-- --------------------------------------------------------

--
-- Table structure for table `invitations`
--

CREATE TABLE `invitations` (
  `InvID` varchar(8) NOT NULL,
  `EventID` varchar(8) NOT NULL,
  `InvStatus` varchar(20) NOT NULL,
  `InvRole` varchar(20) NOT NULL,
  `UserID` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `invitations`
--

INSERT INTO `invitations` (`InvID`, `EventID`, `InvStatus`, `InvRole`, `UserID`) VALUES
('I4369525', 'E4443633', 'Accepted', 'Vendor', 'U4330719'),
('I4374132', 'E9471165', 'Accepted', 'Guest', 'U7174840'),
('I6895027', 'E9471165', 'Pending', 'Guest', 'U1411415'),
('I7292303', 'E4443633', 'Accepted', 'Guest', 'U1411415'),
('I9163111', 'E9471165', 'Pending', 'Vendor', 'U4330719');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `ProductID` varchar(8) NOT NULL,
  `UserID` varchar(8) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`ProductID`, `UserID`, `Name`, `Description`) VALUES
('P3294019', 'U4330719', 'silly product', ':3'),
('P3789674', 'U4330719', 'silly product (2)', ':3c');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `Email` varchar(100) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `Role` varchar(30) NOT NULL,
  `UserID` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`Email`, `Name`, `Password`, `Role`, `UserID`) VALUES
('admin', 'admin', 'admin', 'Admin', 'U1234567'),
('a@gmail.com', 'aaa', 'a123456', 'Guest', 'U1411415'),
('shawn@gmail.com', 'Shawn', '123456', 'Event Organizer', 'U3730035'),
('adeline@gmail.com', 'adeline', '123456', 'Vendor', 'U4330719'),
('hehe@gmail.com', 'silly1234', 'asdfghjkl', 'Guest', 'U7174840');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`EventID`),
  ADD KEY `fk_events_organizerID` (`OrganizerID`);

--
-- Indexes for table `invitations`
--
ALTER TABLE `invitations`
  ADD PRIMARY KEY (`InvID`),
  ADD KEY `EventID` (`EventID`),
  ADD KEY `UserID` (`UserID`);

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
  ADD PRIMARY KEY (`UserID`),
  ADD KEY `UserID` (`UserID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `events`
--
ALTER TABLE `events`
  ADD CONSTRAINT `fk_events_organizerID` FOREIGN KEY (`OrganizerID`) REFERENCES `users` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `invitations`
--
ALTER TABLE `invitations`
  ADD CONSTRAINT `invitations_ibfk_1` FOREIGN KEY (`EventID`) REFERENCES `events` (`EventID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `invitations_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
