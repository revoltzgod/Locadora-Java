-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS locadora;
USE locadora;

-- Tabela de veículos
CREATE TABLE IF NOT EXISTS veiculo (
    placa VARCHAR(8) PRIMARY KEY,
    modelo VARCHAR(50) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    ano INT NOT NULL,
    valor_diaria DECIMAL(10,2) NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    disponivel BOOLEAN NOT NULL DEFAULT true
);

-- Tabela de carros
CREATE TABLE IF NOT EXISTS carro (
    placa VARCHAR(8) PRIMARY KEY,
    numero_portas INT NOT NULL,
    tipo_cambio VARCHAR(20) NOT NULL,
    ar_condicionado BOOLEAN NOT NULL,
    FOREIGN KEY (placa) REFERENCES veiculo(placa) ON DELETE CASCADE
);

-- Tabela de motos
CREATE TABLE IF NOT EXISTS moto (
    placa VARCHAR(8) PRIMARY KEY,
    cilindradas INT NOT NULL,
    tipo_partida VARCHAR(20) NOT NULL,
    FOREIGN KEY (placa) REFERENCES veiculo(placa) ON DELETE CASCADE
);

-- Tabela de clientes
CREATE TABLE IF NOT EXISTS cliente (
    cpf VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    cnh VARCHAR(20) NOT NULL,
    endereco VARCHAR(200) NOT NULL
);

-- Tabela de locações
CREATE TABLE IF NOT EXISTS locacao (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cpf_cliente VARCHAR(14) NOT NULL,
    placa_veiculo VARCHAR(8) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    finalizada BOOLEAN NOT NULL DEFAULT false,
    FOREIGN KEY (cpf_cliente) REFERENCES cliente(cpf),
    FOREIGN KEY (placa_veiculo) REFERENCES veiculo(placa)
);

-- Inserção de dados de exemplo
INSERT INTO veiculo (placa, modelo, marca, ano, valor_diaria, tipo) VALUES
('ABC1234', 'Onix', 'Chevrolet', 2020, 100.00, 'Carro'),
('DEF5678', 'Civic', 'Honda', 2021, 150.00, 'Carro'),
('GHI9012', 'Gol', 'Volkswagen', 2019, 90.00, 'Carro'),
('JKL3456', 'CB 500', 'Honda', 2021, 80.00, 'Moto'),
('MNO7890', 'MT-07', 'Yamaha', 2022, 100.00, 'Moto'),
('PQR1234', 'Z400', 'Kawasaki', 2020, 90.00, 'Moto');

INSERT INTO carro (placa, numero_portas, tipo_cambio, ar_condicionado) VALUES
('ABC1234', 4, 'Manual', true),
('DEF5678', 4, 'Automático', true),
('GHI9012', 4, 'Manual', false);

INSERT INTO moto (placa, cilindradas, tipo_partida) VALUES
('JKL3456', 500, 'Elétrica'),
('MNO7890', 700, 'Elétrica'),
('PQR1234', 400, 'Elétrica');

INSERT INTO cliente (cpf, nome, telefone, cnh, endereco) VALUES
('123.456.789-00', 'João Silva', '(11) 99999-9999', '12345678901', 'Rua A, 123'),
('987.654.321-00', 'Maria Santos', '(11) 88888-8888', '98765432109', 'Rua B, 456'),
('456.789.123-00', 'Pedro Oliveira', '(11) 77777-7777', '45678912345', 'Rua C, 789');

INSERT INTO locacao (cpf_cliente, placa_veiculo, data_inicio, data_fim, valor_total, finalizada) VALUES
('123.456.789-00', 'ABC1234', '2024-03-01', '2024-03-05', 500.00, true),
('987.654.321-00', 'JKL3456', '2024-03-10', '2024-03-15', 400.00, false),
('456.789.123-00', 'DEF5678', '2024-03-12', '2024-03-17', 750.00, false); 