CREATE DATABASE IF NOT EXISTS locadora;
USE locadora;

CREATE TABLE IF NOT EXISTS veiculos (
    placa VARCHAR(10) PRIMARY KEY,
    modelo VARCHAR(50) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    ano INT NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    valor_diaria DECIMAL(10,2) NOT NULL,
    disponivel BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE IF NOT EXISTS carros (
    placa VARCHAR(10) PRIMARY KEY,
    numero_portas INT NOT NULL,
    tipo_cambio VARCHAR(20) NOT NULL,
    ar_condicionado BOOLEAN NOT NULL,
    FOREIGN KEY (placa) REFERENCES veiculos(placa)
);

CREATE TABLE IF NOT EXISTS motos (
    placa VARCHAR(10) PRIMARY KEY,
    cilindradas INT NOT NULL,
    tipo_partida VARCHAR(20) NOT NULL,
    FOREIGN KEY (placa) REFERENCES veiculos(placa)
);

CREATE TABLE IF NOT EXISTS clientes (
    cpf VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    cnh VARCHAR(20) NOT NULL,
    endereco VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS locacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_cpf VARCHAR(14) NOT NULL,
    veiculo_placa VARCHAR(10) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    ativa BOOLEAN NOT NULL DEFAULT true,
    FOREIGN KEY (cliente_cpf) REFERENCES clientes(cpf),
    FOREIGN KEY (veiculo_placa) REFERENCES veiculos(placa)
);

-- Inserindo carros de exemplo
INSERT IGNORE INTO veiculos (placa, modelo, marca, ano, tipo, valor_diaria, disponivel) VALUES
('ABC1234', 'Onix', 'Chevrolet', 2022, 'Carro', 150.00, true),
('DEF5678', 'Civic', 'Honda', 2023, 'Carro', 200.00, true),
('GHI9012', 'Gol', 'Volkswagen', 2021, 'Carro', 120.00, true);

INSERT IGNORE INTO carros (placa, numero_portas, tipo_cambio, ar_condicionado) VALUES
('ABC1234', 4, 'Automático', true),
('DEF5678', 4, 'Automático', true),
('GHI9012', 4, 'Manual', true);

-- Inserindo motos de exemplo
INSERT IGNORE INTO veiculos (placa, modelo, marca, ano, tipo, valor_diaria, disponivel) VALUES
('JKL3456', 'CB 500', 'Honda', 2022, 'Moto', 100.00, true),
('MNO7890', 'MT-07', 'Yamaha', 2023, 'Moto', 120.00, true),
('PQR1234', 'Z400', 'Kawasaki', 2021, 'Moto', 110.00, true);

INSERT IGNORE INTO motos (placa, cilindradas, tipo_partida) VALUES
('JKL3456', 500, 'Elétrica'),
('MNO7890', 700, 'Elétrica'),
('PQR1234', 400, 'Elétrica');

-- Inserindo clientes de exemplo
INSERT IGNORE INTO clientes (cpf, nome, telefone, cnh, endereco) VALUES
('123.456.789-00', 'João Silva', '(11) 98765-4321', '12345678901', 'Rua A, 123'),
('987.654.321-00', 'Maria Santos', '(11) 91234-5678', '98765432109', 'Av. B, 456'),
('456.789.123-00', 'Pedro Oliveira', '(11) 94567-8901', '45678901234', 'Rua C, 789');

-- Inserindo locações de exemplo
INSERT IGNORE INTO locacoes (cliente_cpf, veiculo_placa, data_inicio, data_fim, ativa) VALUES
('123.456.789-00', 'ABC1234', '2024-03-01', '2024-03-05', false),
('987.654.321-00', 'JKL3456', '2024-03-10', '2024-03-15', true),
('456.789.123-00', 'DEF5678', '2024-03-20', '2024-03-25', true); 