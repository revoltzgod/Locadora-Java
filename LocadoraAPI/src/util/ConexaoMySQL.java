package util;

import java.sql.*;
import java.io.*;
import java.nio.file.*;

public class ConexaoMySQL {
    private static Connection conexao = null;
    private static final String URL = "jdbc:mysql://localhost:3306/locadora";
    private static final String USUARIO = "root";
    private static final String SENHA = "password1@";

    public static Connection getConexao() throws SQLException {
        if (conexao == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                try {
                    conexao = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306",
                        USUARIO,
                        SENHA
                    );
                    
                    Statement stmt = conexao.createStatement();
                    stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS locadora");
                    stmt.close();
                    
                    conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
                    
                    executarScript();
                } catch (SQLException e) {
                    System.err.println("Erro ao conectar ao MySQL: " + e.getMessage());
                    throw e;
                }
            } catch (ClassNotFoundException e) {
                System.err.println("Driver MySQL não encontrado: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return conexao;
    }

    private static void executarScript() throws SQLException {
        try {
            String script = new String(Files.readAllBytes(Paths.get("src/sql/locadora.sql")));
            
            for (String comando : script.split(";")) {
                if (!comando.trim().isEmpty()) {
                    try (Statement stmt = conexao.createStatement()) {
                        stmt.execute(comando);
                    } catch (SQLException e) {
                        if (!e.getMessage().contains("already exists")) {
                            throw e;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo SQL: " + e.getMessage());
            throw new SQLException("Erro ao ler arquivo SQL", e);
        }
    }

    public static void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null;
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
} 