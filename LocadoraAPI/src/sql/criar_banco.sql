-- Este arquivo é um exemplo comum de implementação de banco de dados.
-- Foi mantido como referência de uma abordagem mais simples.
-- Para o sistema em produção, use o arquivo locadora.sql que contém a estrutura completa e dados de exemplo.

CREATE DATABASE IF NOT EXISTS locadora;
USE locadora;

CREATE TABLE IF NOT EXISTS clientes (
    cpf VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    cnh VARCHAR(20) NOT NULL,
    endereco VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS veiculos (
    placa VARCHAR(8) PRIMARY KEY,
    modelo VARCHAR(50) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    ano INT NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    valor_diaria DECIMAL(10,2) NOT NULL,
    disponivel BOOLEAN DEFAULT true
);

CREATE TABLE IF NOT EXISTS locacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_cpf VARCHAR(14) NOT NULL,
    veiculo_placa VARCHAR(8) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    ativa BOOLEAN DEFAULT true,
    FOREIGN KEY (cliente_cpf) REFERENCES clientes(cpf),
    FOREIGN KEY (veiculo_placa) REFERENCES veiculos(placa)
); 