package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.ConexaoMySQL;

public class Cliente {
    private String cpf;
    private String nome;
    private String telefone;
    private String cnh;
    private String endereco;
    private Connection conexao;

    public Cliente(String cpf, String nome, String telefone, String cnh, String endereco) throws SQLException {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.cnh = cnh;
        this.endereco = endereco;
        this.conexao = ConexaoMySQL.getConexao();
    }

    public void salvar() throws SQLException {
        String sql = "INSERT INTO clientes (cpf, nome, telefone, cnh, endereco) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, this.cpf);
            stmt.setString(2, this.nome);
            stmt.setString(3, this.telefone);
            stmt.setString(4, this.cnh);
            stmt.setString(5, this.endereco);
            
            stmt.executeUpdate();
        }
    }

    public void atualizar() throws SQLException {
        String sql = "UPDATE clientes SET nome = ?, telefone = ?, cnh = ?, endereco = ? WHERE cpf = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, this.nome);
            stmt.setString(2, this.telefone);
            stmt.setString(3, this.cnh);
            stmt.setString(4, this.endereco);
            stmt.setString(5, this.cpf);
            
            stmt.executeUpdate();
        }
    }

    public void excluir() throws SQLException {
        String sql = "DELETE FROM locacoes WHERE cliente_cpf = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, this.cpf);
            stmt.executeUpdate();
        }

        sql = "DELETE FROM clientes WHERE cpf = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, this.cpf);
            stmt.executeUpdate();
        }
    }

    public static List<Cliente> listarTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        Connection conexao = ConexaoMySQL.getConexao();
        String sql = "SELECT * FROM clientes";
        
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("cnh"),
                    rs.getString("endereco")
                );
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public static Cliente buscarPorCPF(String cpf) throws SQLException {
        Connection conexao = ConexaoMySQL.getConexao();
        String sql = "SELECT * FROM clientes WHERE cpf = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Cliente(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("cnh"),
                    rs.getString("endereco")
                );
            }
            return null;
        }
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCnh() {
        return cnh;
    }

    public String getEndereco() {
        return endereco;
    }

    @Override
    public String toString() {
        return "CPF: " + cpf +
               "\nNome: " + nome +
               "\nTelefone: " + telefone +
               "\nCNH: " + cnh +
               "\nEndereço: " + endereco;
    }
} 