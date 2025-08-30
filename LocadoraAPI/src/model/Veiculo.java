package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.ConexaoMySQL;

// Classe abstrata para demonstrar herança
public abstract class Veiculo {
    // Atributos básicos de um veículo
    private String placa;
    private String modelo;
    private String marca;
    private int ano;
    private double valorDiaria;
    private boolean disponivel;
    private Connection conexao;
    
    // Construtor
    public Veiculo(String placa, String modelo, String marca, int ano, double valorDiaria) throws SQLException {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.valorDiaria = valorDiaria;
        this.disponivel = true;
        this.conexao = ConexaoMySQL.getConexao();
    }

    // Método abstrato para demonstrar polimorfismo
    public abstract String getTipo();

    // Métodos de acesso ao banco de dados
    public void salvar() throws SQLException {
        String sql = "INSERT INTO veiculos (placa, modelo, marca, ano, valor_diaria, disponivel, tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, this.placa);
            stmt.setString(2, this.modelo);
            stmt.setString(3, this.marca);
            stmt.setInt(4, this.ano);
            stmt.setDouble(5, this.valorDiaria);
            stmt.setBoolean(6, this.disponivel);
            stmt.setString(7, this.getTipo());
            
            stmt.executeUpdate();
            
            if (this instanceof Carro) {
                Carro carro = (Carro) this;
                sql = "INSERT INTO carros (placa, numero_portas, tipo_cambio, ar_condicionado) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmtCarro = conexao.prepareStatement(sql)) {
                    stmtCarro.setString(1, this.placa);
                    stmtCarro.setInt(2, carro.getNumeroPortas());
                    stmtCarro.setString(3, carro.getTipoCambio());
                    stmtCarro.setBoolean(4, carro.hasArCondicionado());
                    stmtCarro.executeUpdate();
                }
            } else if (this instanceof Moto) {
                Moto moto = (Moto) this;
                sql = "INSERT INTO motos (placa, cilindradas, tipo_partida) VALUES (?, ?, ?)";
                try (PreparedStatement stmtMoto = conexao.prepareStatement(sql)) {
                    stmtMoto.setString(1, this.placa);
                    stmtMoto.setInt(2, moto.getCilindradas());
                    stmtMoto.setString(3, moto.getTipoPartida());
                    stmtMoto.executeUpdate();
                }
            }
        }
    }

    public void atualizar() throws SQLException {
        String sql = "UPDATE veiculos SET modelo = ?, marca = ?, ano = ?, valor_diaria = ?, disponivel = ?, tipo = ? WHERE placa = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, this.modelo);
            stmt.setString(2, this.marca);
            stmt.setInt(3, this.ano);
            stmt.setDouble(4, this.valorDiaria);
            stmt.setBoolean(5, this.disponivel);
            stmt.setString(6, this.getTipo());
            stmt.setString(7, this.placa);
            
            stmt.executeUpdate();
            
            if (this instanceof Carro) {
                Carro carro = (Carro) this;
                sql = "UPDATE carros SET numero_portas = ?, tipo_cambio = ?, ar_condicionado = ? WHERE placa = ?";
                try (PreparedStatement stmtCarro = conexao.prepareStatement(sql)) {
                    stmtCarro.setInt(1, carro.getNumeroPortas());
                    stmtCarro.setString(2, carro.getTipoCambio());
                    stmtCarro.setBoolean(3, carro.hasArCondicionado());
                    stmtCarro.setString(4, this.placa);
                    stmtCarro.executeUpdate();
                }
            } else if (this instanceof Moto) {
                Moto moto = (Moto) this;
                sql = "UPDATE motos SET cilindradas = ?, tipo_partida = ? WHERE placa = ?";
                try (PreparedStatement stmtMoto = conexao.prepareStatement(sql)) {
                    stmtMoto.setInt(1, moto.getCilindradas());
                    stmtMoto.setString(2, moto.getTipoPartida());
                    stmtMoto.setString(3, this.placa);
                    stmtMoto.executeUpdate();
                }
            }
        }
    }

    public void excluir() throws SQLException {
        String sql = "DELETE FROM locacoes WHERE veiculo_placa = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, this.placa);
            stmt.executeUpdate();
        }

        if (this instanceof Carro) {
            sql = "DELETE FROM carros WHERE placa = ?";
        } else {
            sql = "DELETE FROM motos WHERE placa = ?";
        }
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, this.placa);
            stmt.executeUpdate();
        }

        sql = "DELETE FROM veiculos WHERE placa = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, this.placa);
            stmt.executeUpdate();
        }
    }

    public static List<Veiculo> listarTodos() throws SQLException {
        List<Veiculo> veiculos = new ArrayList<>();
        Connection conexao = ConexaoMySQL.getConexao();
        
        String sql = "SELECT v.*, c.numero_portas, c.tipo_cambio, c.ar_condicionado, " +
                    "m.cilindradas, m.tipo_partida " +
                    "FROM veiculos v " +
                    "LEFT JOIN carros c ON v.placa = c.placa " +
                    "LEFT JOIN motos m ON v.placa = m.placa " +
                    "ORDER BY v.placa";
        
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Veiculo veiculo;
                String placa = rs.getString("placa");
                String modelo = rs.getString("modelo");
                String marca = rs.getString("marca");
                int ano = rs.getInt("ano");
                double valorDiaria = rs.getDouble("valor_diaria");
                boolean disponivel = rs.getBoolean("disponivel");
                String tipo = rs.getString("tipo");
                
                if (tipo.equals("Carro")) {
                    int numeroPortas = rs.getInt("numero_portas");
                    String tipoCambio = rs.getString("tipo_cambio");
                    boolean arCondicionado = rs.getBoolean("ar_condicionado");
                    veiculo = new Carro(placa, modelo, marca, ano, valorDiaria,
                                      numeroPortas, tipoCambio, arCondicionado);
                } else {
                    int cilindradas = rs.getInt("cilindradas");
                    String tipoPartida = rs.getString("tipo_partida");
                    veiculo = new Moto(placa, modelo, marca, ano, valorDiaria,
                                     cilindradas, tipoPartida);
                }
                veiculo.setDisponivel(disponivel);
                veiculos.add(veiculo);
            }
        }
        return veiculos;
    }

    public static Veiculo buscarPorPlaca(String placa) throws SQLException {
        Connection conexao = ConexaoMySQL.getConexao();
        String sql = "SELECT v.*, c.numero_portas, c.tipo_cambio, c.ar_condicionado, " +
                    "m.cilindradas, m.tipo_partida " +
                    "FROM veiculos v " +
                    "LEFT JOIN carros c ON v.placa = c.placa " +
                    "LEFT JOIN motos m ON v.placa = m.placa " +
                    "WHERE v.placa = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String modelo = rs.getString("modelo");
                String marca = rs.getString("marca");
                int ano = rs.getInt("ano");
                double valorDiaria = rs.getDouble("valor_diaria");
                boolean disponivel = rs.getBoolean("disponivel");
                String tipo = rs.getString("tipo");
                
                Veiculo veiculo;
                if (tipo.equals("Carro")) {
                    int numeroPortas = rs.getInt("numero_portas");
                    String tipoCambio = rs.getString("tipo_cambio");
                    boolean arCondicionado = rs.getBoolean("ar_condicionado");
                    veiculo = new Carro(placa, modelo, marca, ano, valorDiaria,
                                      numeroPortas, tipoCambio, arCondicionado);
                } else {
                    int cilindradas = rs.getInt("cilindradas");
                    String tipoPartida = rs.getString("tipo_partida");
                    veiculo = new Moto(placa, modelo, marca, ano, valorDiaria,
                                     cilindradas, tipoPartida);
                }
                veiculo.setDisponivel(disponivel);
                return veiculo;
            }
            return null;
        }
    }

    // Getters e Setters
    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public int getAno() {
        return ano;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    // Método toString para exibição dos dados
    @Override
    public String toString() {
        return "Placa: " + placa +
               "\nModelo: " + modelo +
               "\nMarca: " + marca +
               "\nAno: " + ano +
               "\nValor Diária: R$ " + String.format("%.2f", valorDiaria) +
               "\nDisponível: " + (disponivel ? "Sim" : "Não");
    }
} 