-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Nov 14, 2025 at 08:14 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `colonia_de_gatos`
--

-- --------------------------------------------------------

--
-- Table structure for table `diagnosticos`
--

CREATE TABLE `diagnosticos` (
  `id_diagnostico` int(11) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `diagnostico` varchar(255) DEFAULT NULL,
  `estado` bit(1) NOT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `tratamiento` varchar(255) DEFAULT NULL,
  `veterinario_dni` varchar(255) DEFAULT NULL,
  `gato_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `diagnosticos`
--

INSERT INTO `diagnosticos` (`id_diagnostico`, `descripcion`, `diagnostico`, `estado`, `fecha`, `tratamiento`, `veterinario_dni`, `gato_id`) VALUES
(1, 'Chequeo general completo', 'Saludable, sin parasitos', b'1', '2025-10-31 14:44:00.000000', 'Ninguno', '222', 1),
(2, '--', 'Garrapatas', b'0', '2025-11-01 18:00:00.000000', 'Pipeta', '222', 2),
(3, 'Complicacion en la urina puede derivar en piedras en los riñones', 'Infeccion urinaria', b'0', '2025-11-05 00:00:00.000000', 'Cambiar alimentacion', '222', 4),
(4, 'Paciente sano', 'Sano', b'1', '2025-11-07 15:31:00.000000', 'Nada', '222', 7),
(5, '', 'Sano', b'1', '2025-11-07 16:15:00.000000', 'nada', '222', 6),
(6, 'gato sano', 'Sano', b'1', '2025-11-14 12:43:00.000000', '', '222', 8);

-- --------------------------------------------------------

--
-- Table structure for table `gatos`
--

CREATE TABLE `gatos` (
  `idgato` int(11) NOT NULL,
  `adoptado` int(11) NOT NULL,
  `apto` int(11) NOT NULL,
  `color` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `esterilizado` bit(1) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `hogar_id` int(11) DEFAULT NULL,
  `zona_id` int(11) DEFAULT NULL,
  `edad` int(11) NOT NULL,
  `fotoUrl` varchar(255) DEFAULT NULL,
  `qrUrl` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `gatos`
--

INSERT INTO `gatos` (`idgato`, `adoptado`, `apto`, `color`, `descripcion`, `esterilizado`, `nombre`, `hogar_id`, `zona_id`, `edad`, `fotoUrl`, `qrUrl`) VALUES
(1, 1, 1, 'negro', 'Gordo,bigotes largos, apatico', b'0', 'Mauricio', 1, 1, 0, NULL, NULL),
(2, 0, 0, 'marron', 'calmado', b'0', 'Leandro ', NULL, 1, 4, '', NULL),
(4, 0, 0, 'blanco', 'viejo', b'0', 'Emi', NULL, 1, 0, NULL, NULL),
(6, 0, 1, 'gris', 'sociable', b'1', 'nicolah', NULL, 1, 6, 'https://i.imgur.com/pEFDit5.jpeg', 'QRS/gato_7320344e-fea4-4415-9de5-87cfe1986284.png'),
(7, 1, 1, 'Amarillo', 'Tranquilo', b'1', 'Carlitos', 1, NULL, 15, 'https://i.imgur.com/7viaKkU.jpeg', 'QRS/gato_522e50d1-8a08-4266-8804-70c3cd85290c.png'),
(8, 0, 1, 'blanco y negro', 'energetico', b'1', 'Chiki', NULL, 5, 12, 'https://i.imgur.com/WbmaF7E.jpeg', 'QRS/gato_6694c338-1cc0-4519-995c-2e52e81952af.png'),
(9, 0, 0, 'Atiigrado gris', 'gata hembra , hostil', b'0', 'Alma', NULL, 5, 20, 'https://i.imgur.com/HdLsqKG.jpeg', 'QRS/gato_60d5ce51-4a51-494d-9b52-fa122ba153a4.png');

-- --------------------------------------------------------

--
-- Table structure for table `hogares`
--

CREATE TABLE `hogares` (
  `idhogarfam` int(11) NOT NULL,
  `aprobado` bit(1) NOT NULL,
  `coordenadas` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `familia_dni` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hogares`
--

INSERT INTO `hogares` (`idhogarfam`, `aprobado`, `coordenadas`, `direccion`, `familia_dni`) VALUES
(1, b'1', '-33.55, -55.22', 'Buenos Aires 1521', '444');

-- --------------------------------------------------------

--
-- Table structure for table `solicitudes_adopcion`
--

CREATE TABLE `solicitudes_adopcion` (
  `idSolicitud` int(11) NOT NULL,
  `estado` int(11) NOT NULL,
  `fechaSolicitud` datetime(6) DEFAULT NULL,
  `familia_dni` varchar(255) DEFAULT NULL,
  `gato_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `solicitudes_adopcion`
--

INSERT INTO `solicitudes_adopcion` (`idSolicitud`, `estado`, `fechaSolicitud`, `familia_dni`, `gato_id`) VALUES
(1, 2, '2025-11-07 15:50:16.000000', '444', 7),
(2, 1, '2025-11-07 19:02:13.000000', '444', 7),
(3, 2, '2025-11-09 20:42:13.000000', '444', 6);

-- --------------------------------------------------------

--
-- Table structure for table `tareas`
--

CREATE TABLE `tareas` (
  `idtarea` int(11) NOT NULL,
  `coordenadas` varchar(255) DEFAULT NULL,
  `estado` int(11) NOT NULL,
  `fechahora` datetime(6) DEFAULT NULL,
  `tipo` int(11) NOT NULL,
  `gato_id` int(11) DEFAULT NULL,
  `voluntario_dni` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tareas`
--

INSERT INTO `tareas` (`idtarea`, `coordenadas`, `estado`, `fechahora`, `tipo`, `gato_id`, `voluntario_dni`) VALUES
(1, '-44.55,-22.00', 1, '2025-11-10 14:00:00.000000', 1, 2, NULL),
(2, '-22.44,-11.23', 1, '2025-11-05 15:59:00.000000', 2, 4, '666'),
(4, '-33.22,-12,33', 1, '2025-11-07 23:56:00.000000', 3, 4, '333'),
(5, 'bla bla', 0, '2025-11-10 01:15:00.000000', 1, 2, '333'),
(6, '231234325', 0, '2025-11-08 01:55:00.000000', 1, 2, '666'),
(7, '213124', 0, '2025-11-08 01:37:00.000000', 4, 4, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `usuarios`
--

CREATE TABLE `usuarios` (
  `roles` int(11) NOT NULL,
  `DNI` varchar(255) NOT NULL,
  `clave` varchar(255) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `postulado` bit(1) DEFAULT NULL,
  `reputacion` int(11) DEFAULT NULL,
  `matricula` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `usuarios`
--

INSERT INTO `usuarios` (`roles`, `DNI`, `clave`, `correo`, `nombre`, `telefono`, `postulado`, `reputacion`, `matricula`) VALUES
(1, '111', 'admin', 'admin@mail.com', 'Admin', '1234', NULL, NULL, NULL),
(2, '222', 'vet', 'vet@mail.com', 'Vet', '1234', NULL, NULL, 'MAT001'),
(3, '333', 'vol', 'vol@mail.com', 'Voluntario', '1234', NULL, 1, NULL),
(4, '444', 'fam', 'fam@mail.com', 'Familia', '1234', b'1', -1, NULL),
(3, '44789012', '2-0', 'riber@mail.com', 'Milton Delgado', '3764-222222', NULL, 3, NULL),
(3, '46213456', 'boca', 'bocapasion@mail.com', 'Leandro Paredes', '3764-111111', NULL, 3, NULL),
(3, '666', 'lol', 'lautiro@gmail.com', 'Lauti R', '376409091', NULL, 3, NULL),
(3, '777', 'corona', 'alanagost@gmail.com', 'Alan agostinho', '376423891', NULL, -1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `visitas`
--

CREATE TABLE `visitas` (
  `codvisita` int(11) NOT NULL,
  `cumplida` bit(1) NOT NULL,
  `fechahora` datetime(6) DEFAULT NULL,
  `observacion` varchar(1000) DEFAULT NULL,
  `hogar_id` int(11) DEFAULT NULL,
  `voluntario_dni` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `visitas`
--

INSERT INTO `visitas` (`codvisita`, `cumplida`, `fechahora`, `observacion`, `hogar_id`, `voluntario_dni`) VALUES
(1, b'1', '2025-11-01 15:00:00.000000', 'Todo correcto', 1, '333'),
(2, b'1', '2025-11-04 13:12:00.000000', 'Gato en buen ambiente, adaptandose', 1, '333'),
(3, b'1', '2025-11-12 17:00:00.000000', 'Gato adaptado', 1, '333'),
(4, b'0', '2025-11-06 12:49:00.000000', 'visita gato', 1, '333'),
(5, b'0', '2025-11-06 14:22:00.000000', 'visita 3\r\n', 1, '777');

-- --------------------------------------------------------

--
-- Table structure for table `zonas_geograficas`
--

CREATE TABLE `zonas_geograficas` (
  `idzona` int(11) NOT NULL,
  `coordenadas` varchar(255) DEFAULT NULL,
  `nombrezona` varchar(255) DEFAULT NULL,
  `tipo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `zonas_geograficas`
--

INSERT INTO `zonas_geograficas` (`idzona`, `coordenadas`, `nombrezona`, `tipo`) VALUES
(1, '-27.36, -55.89', 'Centro', 3),
(5, '-33.44,-11.23', 'Itaembe guazu', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `diagnosticos`
--
ALTER TABLE `diagnosticos`
  ADD PRIMARY KEY (`id_diagnostico`),
  ADD KEY `FKkpag1b0jpsc8h2o6htxh3ts0` (`veterinario_dni`),
  ADD KEY `FKhamkny4tuuln2by5trasm4ubw` (`gato_id`);

--
-- Indexes for table `gatos`
--
ALTER TABLE `gatos`
  ADD PRIMARY KEY (`idgato`),
  ADD KEY `FKqnhhah5semd2q5rayrukfjoq4` (`hogar_id`),
  ADD KEY `FKnrhbth14sm7yiwaub3eccfiri` (`zona_id`);

--
-- Indexes for table `hogares`
--
ALTER TABLE `hogares`
  ADD PRIMARY KEY (`idhogarfam`),
  ADD UNIQUE KEY `UK_qwqhid7t1g7y85nwsmgs080hm` (`familia_dni`);

--
-- Indexes for table `solicitudes_adopcion`
--
ALTER TABLE `solicitudes_adopcion`
  ADD PRIMARY KEY (`idSolicitud`),
  ADD KEY `FKlav0m5aw0p8qebocur7wkqnt4` (`familia_dni`),
  ADD KEY `FK32750xg7fwleb0324iv4l1e2b` (`gato_id`);

--
-- Indexes for table `tareas`
--
ALTER TABLE `tareas`
  ADD PRIMARY KEY (`idtarea`),
  ADD KEY `FKs58faay6cba2tx607g8cfrwhc` (`gato_id`),
  ADD KEY `FKjs9e0jravxk39os58och9hoia` (`voluntario_dni`);

--
-- Indexes for table `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`DNI`);

--
-- Indexes for table `visitas`
--
ALTER TABLE `visitas`
  ADD PRIMARY KEY (`codvisita`),
  ADD KEY `FKcpej2f5qkvtnp45eeyjqda661` (`hogar_id`),
  ADD KEY `FK5rppto9iqgutu0gr1is5ecjkd` (`voluntario_dni`);

--
-- Indexes for table `zonas_geograficas`
--
ALTER TABLE `zonas_geograficas`
  ADD PRIMARY KEY (`idzona`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `diagnosticos`
--
ALTER TABLE `diagnosticos`
  MODIFY `id_diagnostico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `gatos`
--
ALTER TABLE `gatos`
  MODIFY `idgato` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `hogares`
--
ALTER TABLE `hogares`
  MODIFY `idhogarfam` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `solicitudes_adopcion`
--
ALTER TABLE `solicitudes_adopcion`
  MODIFY `idSolicitud` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tareas`
--
ALTER TABLE `tareas`
  MODIFY `idtarea` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `visitas`
--
ALTER TABLE `visitas`
  MODIFY `codvisita` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `zonas_geograficas`
--
ALTER TABLE `zonas_geograficas`
  MODIFY `idzona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `diagnosticos`
--
ALTER TABLE `diagnosticos`
  ADD CONSTRAINT `FKhamkny4tuuln2by5trasm4ubw` FOREIGN KEY (`gato_id`) REFERENCES `gatos` (`idgato`),
  ADD CONSTRAINT `FKkpag1b0jpsc8h2o6htxh3ts0` FOREIGN KEY (`veterinario_dni`) REFERENCES `usuarios` (`DNI`);

--
-- Constraints for table `gatos`
--
ALTER TABLE `gatos`
  ADD CONSTRAINT `FKnrhbth14sm7yiwaub3eccfiri` FOREIGN KEY (`zona_id`) REFERENCES `zonas_geograficas` (`idzona`),
  ADD CONSTRAINT `FKqnhhah5semd2q5rayrukfjoq4` FOREIGN KEY (`hogar_id`) REFERENCES `hogares` (`idhogarfam`);

--
-- Constraints for table `hogares`
--
ALTER TABLE `hogares`
  ADD CONSTRAINT `FKm6ldioohq93txjnwukgpbnll2` FOREIGN KEY (`familia_dni`) REFERENCES `usuarios` (`DNI`);

--
-- Constraints for table `solicitudes_adopcion`
--
ALTER TABLE `solicitudes_adopcion`
  ADD CONSTRAINT `FK32750xg7fwleb0324iv4l1e2b` FOREIGN KEY (`gato_id`) REFERENCES `gatos` (`idgato`),
  ADD CONSTRAINT `FKlav0m5aw0p8qebocur7wkqnt4` FOREIGN KEY (`familia_dni`) REFERENCES `usuarios` (`DNI`);

--
-- Constraints for table `tareas`
--
ALTER TABLE `tareas`
  ADD CONSTRAINT `FKjs9e0jravxk39os58och9hoia` FOREIGN KEY (`voluntario_dni`) REFERENCES `usuarios` (`DNI`),
  ADD CONSTRAINT `FKs58faay6cba2tx607g8cfrwhc` FOREIGN KEY (`gato_id`) REFERENCES `gatos` (`idgato`);

--
-- Constraints for table `visitas`
--
ALTER TABLE `visitas`
  ADD CONSTRAINT `FK5rppto9iqgutu0gr1is5ecjkd` FOREIGN KEY (`voluntario_dni`) REFERENCES `usuarios` (`DNI`),
  ADD CONSTRAINT `FKcpej2f5qkvtnp45eeyjqda661` FOREIGN KEY (`hogar_id`) REFERENCES `hogares` (`idhogarfam`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
