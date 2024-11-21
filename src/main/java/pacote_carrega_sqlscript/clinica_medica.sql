-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 21/11/2024 às 01:38
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `clinica_medica`
--
CREATE DATABASE IF NOT EXISTS `clinica_medica` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `clinica_medica`;

-- --------------------------------------------------------

--
-- Estrutura para tabela `clientes`
--

CREATE TABLE `clientes` (
  `idCliente` int(11) NOT NULL,
  `nomeCliente` varchar(100) DEFAULT NULL,
  `cpf` varchar(14) DEFAULT NULL,
  `dataNasc` date DEFAULT NULL,
  `convOuPart` char(1) DEFAULT NULL,
  `planoConv` varchar(40) DEFAULT NULL,
  `telefone` varchar(17) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `clientes`
--

INSERT INTO `clientes` (`idCliente`, `nomeCliente`, `cpf`, `dataNasc`, `convOuPart`, `planoConv`, `telefone`) VALUES
(1, 'Guilherme', '123456', '2005-08-01', 'P', 'Não possui convênio', '1234');

-- --------------------------------------------------------

--
-- Estrutura para tabela `espec`
--

CREATE TABLE `espec` (
  `idEspec` int(11) NOT NULL,
  `nomeEspec` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `espec`
--

INSERT INTO `espec` (`idEspec`, `nomeEspec`) VALUES
(11, 'Dermatologista'),
(14, 'Oftalmologista'),
(13, 'Ortopedista'),
(12, 'Otorrinolaringologista');

-- --------------------------------------------------------

--
-- Estrutura para tabela `medico`
--

CREATE TABLE `medico` (
  `idMedico` int(11) NOT NULL,
  `nomeMedico` varchar(100) DEFAULT NULL,
  `crm` varchar(9) DEFAULT NULL,
  `dataNasc` date DEFAULT NULL,
  `telefone` varchar(17) DEFAULT NULL,
  `idEspec` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`idCliente`),
  ADD UNIQUE KEY `unique_cpf` (`cpf`);

--
-- Índices de tabela `espec`
--
ALTER TABLE `espec`
  ADD PRIMARY KEY (`idEspec`),
  ADD UNIQUE KEY `nomeEspec` (`nomeEspec`);

--
-- Índices de tabela `medico`
--
ALTER TABLE `medico`
  ADD PRIMARY KEY (`idMedico`),
  ADD KEY `idEspec` (`idEspec`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `clientes`
--
ALTER TABLE `clientes`
  MODIFY `idCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `espec`
--
ALTER TABLE `espec`
  MODIFY `idEspec` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de tabela `medico`
--
ALTER TABLE `medico`
  MODIFY `idMedico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `medico`
--
ALTER TABLE `medico`
  ADD CONSTRAINT `medico_ibfk_1` FOREIGN KEY (`idEspec`) REFERENCES `espec` (`idEspec`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

