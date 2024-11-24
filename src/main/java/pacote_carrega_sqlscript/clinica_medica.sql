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
-- Estrutura para tabela `consulta`
--

CREATE TABLE `consulta` (
  `idConsulta` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `idMedico` int(11) NOT NULL,
  `idHorario` int(11) NOT NULL,
  `dataConsulta` date NOT NULL,
  `sintomas` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
-- Estrutura para tabela `horarios`
--

CREATE TABLE `horarios` (
  `idHorario` int(11) NOT NULL,
  `horario` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `horarios`
--

INSERT INTO `horarios` (`idHorario`, `horario`) VALUES
(1, '07:00:00'),
(2, '08:00:00'),
(3, '09:00:00'),
(4, '10:00:00'),
(5, '11:00:00'),
(6, '12:00:00'),
(7, '13:00:00'),
(8, '14:00:00'),
(9, '15:00:00'),
(10, '16:00:00'),
(11, '17:00:00'),
(12, '18:00:00');

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
-- Índices de tabela `consulta`
--
ALTER TABLE `consulta`
  ADD PRIMARY KEY (`idConsulta`),
  ADD KEY `idCliente` (`idCliente`),
  ADD KEY `idMedico` (`idMedico`),
  ADD KEY `idHorario` (`idHorario`);

--
-- Índices de tabela `espec`
--
ALTER TABLE `espec`
  ADD PRIMARY KEY (`idEspec`),
  ADD UNIQUE KEY `nomeEspec` (`nomeEspec`);

--
-- Índices de tabela `horarios`
--
ALTER TABLE `horarios`
  ADD PRIMARY KEY (`idHorario`),
  ADD UNIQUE KEY `horario` (`horario`);

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
-- AUTO_INCREMENT de tabela `consulta`
--
ALTER TABLE `consulta`
  MODIFY `idConsulta` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `espec`
--
ALTER TABLE `espec`
  MODIFY `idEspec` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de tabela `horarios`
--
ALTER TABLE `horarios`
  MODIFY `idHorario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de tabela `medico`
--
ALTER TABLE `medico`
  MODIFY `idMedico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `consulta`
--
ALTER TABLE `consulta`
  ADD CONSTRAINT `consulta_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `clientes` (`idCliente`),
  ADD CONSTRAINT `consulta_ibfk_2` FOREIGN KEY (`idMedico`) REFERENCES `medico` (`idMedico`),
  ADD CONSTRAINT `consulta_ibfk_3` FOREIGN KEY (`idHorario`) REFERENCES `horarios` (`idHorario`);

--
-- Restrições para tabelas `medico`
--
ALTER TABLE `medico`
  ADD CONSTRAINT `medico_ibfk_1` FOREIGN KEY (`idEspec`) REFERENCES `espec` (`idEspec`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;