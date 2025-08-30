package model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import util.ConexaoMySQL;

public class Locacao {
    private Long id;
    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean ativa;
    private Connection conexao;

    public Locacao(Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim) throws SQLException {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.ativa = true;
        this.conexao = ConexaoMySQL.getConexao();
    }

    private Locacao(Long id, Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim, boolean ativa) throws SQLException {
        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.ativa = ativa;
        this.conexao = ConexaoMySQL.getConexao();
    }

    public void salvar() throws SQLException {
        String sql = "INSERT INTO locacoes (cliente_cpf, veiculo_placa, data_inicio, data_fim, ativa) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, veiculo.getPlaca());
            stmt.setDate(3, Date.valueOf(dataInicio));
            stmt.setDate(4, Date.valueOf(dataFim));
            stmt.setBoolean(5, ativa);
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getLong(1);
            }
        }
    }

    public void atualizar() throws SQLException {
        String sql = "UPDATE locacoes SET data_inicio = ?, data_fim = ?, ativa = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(dataInicio));
            stmt.setDate(2, Date.valueOf(dataFim));
            stmt.setBoolean(3, ativa);
            stmt.setLong(4, id);
            
            stmt.executeUpdate();
        }
    }

    public void excluir() throws SQLException {
        String sql = "DELETE FROM locacoes WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public static List<Locacao> listarTodas() throws SQLException {
        List<Locacao> locacoes = new ArrayList<>();
        Connection conexao = ConexaoMySQL.getConexao();
        String sql = "SELECT l.*, c.*, v.* FROM locacoes l " +
                    "JOIN clientes c ON l.cliente_cpf = c.cpf " +
                    "JOIN veiculos v ON l.veiculo_placa = v.placa";
        
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("cliente_cpf"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("cnh"),
                    rs.getString("endereco")
                );
                
                Veiculo veiculo = Veiculo.buscarPorPlaca(rs.getString("veiculo_placa"));
                
                Locacao locacao = new Locacao(
                    rs.getLong("id"),
                    cliente,
                    veiculo,
                    rs.getDate("data_inicio").toLocalDate(),
                    rs.getDate("data_fim").toLocalDate(),
                    rs.getBoolean("ativa")
                );
                
                locacoes.add(locacao);
            }
        }
        return locacoes;
    }

    public static Locacao buscarPorId(Long id) throws SQLException {
        Connection conexao = ConexaoMySQL.getConexao();
        String sql = "SELECT l.*, c.*, v.* FROM locacoes l " +
                    "JOIN clientes c ON l.cliente_cpf = c.cpf " +
                    "JOIN veiculos v ON l.veiculo_placa = v.placa " +
                    "WHERE l.id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("cliente_cpf"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("cnh"),
                    rs.getString("endereco")
                );
                
                Veiculo veiculo = Veiculo.buscarPorPlaca(rs.getString("veiculo_placa"));
                
                return new Locacao(
                    rs.getLong("id"),
                    cliente,
                    veiculo,
                    rs.getDate("data_inicio").toLocalDate(),
                    rs.getDate("data_fim").toLocalDate(),
                    rs.getBoolean("ativa")
                );
            }
            return null;
        }
    }

    public double calcularValorTotal() {
        long dias = java.time.temporal.ChronoUnit.DAYS.between(dataInicio, dataFim);
        return dias * veiculo.getValorDiaria();
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    @Override
    public String toString() {
        return "ID: " + id +
               "\nCliente: " + cliente.getNome() +
               "\nVeículo: " + veiculo.getMarca() + " " + veiculo.getModelo() +
               "\nData Início: " + dataInicio +
               "\nData Fim: " + dataFim +
               "\nValor Total: R$ " + String.format("%.2f", calcularValorTotal()) +
               "\nAtiva: " + (ativa ? "Sim" : "Não");
    }
} 