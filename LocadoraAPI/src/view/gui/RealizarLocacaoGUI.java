package view.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import model.Cliente;
import model.Veiculo;
import model.Locacao;
import view.gui.util.*;

public class RealizarLocacaoGUI extends JDialog {
    private JComboBox<String> cmbCliente;
    private JComboBox<String> cmbVeiculo;
    private JTextField txtDataInicio;
    private JTextField txtDataFim;
    private JButton btnSalvar;
    private JButton btnCancelar;
    
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RealizarLocacaoGUI(JFrame parent) {
        super(parent, "Realizar Locação", true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(GUIColors.CARD_BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(new CompoundBorder(
            new EmptyBorder(20, 20, 20, 20),
            new CompoundBorder(
                new LineBorder(GUIColors.OPERACOES, 2, true),
                new EmptyBorder(20, 20, 20, 20)
            )
        ));
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título
        JLabel titulo = new JLabel("Realizar Locação");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(GUIColors.OPERACOES);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(titulo, gbc);
        
        // Campos
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Cliente
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblCliente.setForeground(GUIColors.TEXT);
        panel.add(lblCliente, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        cmbCliente = new JComboBox<>();
        cmbCliente.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        carregarClientes();
        panel.add(cmbCliente, gbc);
        
        // Veículo
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblVeiculo = new JLabel("Veículo:");
        lblVeiculo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblVeiculo.setForeground(GUIColors.TEXT);
        panel.add(lblVeiculo, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        cmbVeiculo = new JComboBox<>();
        cmbVeiculo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        carregarVeiculos();
        panel.add(cmbVeiculo, gbc);
        
        // Data Início
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblDataInicio = new JLabel("Data de Início (dd/mm/aaaa):");
        lblDataInicio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDataInicio.setForeground(GUIColors.TEXT);
        panel.add(lblDataInicio, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        txtDataInicio = criarCampoTexto();
        panel.add(txtDataInicio, gbc);
        
        // Data Fim
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblDataFim = new JLabel("Data de Fim (dd/mm/aaaa):");
        lblDataFim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDataFim.setForeground(GUIColors.TEXT);
        panel.add(lblDataFim, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        txtDataFim = criarCampoTexto();
        panel.add(txtDataFim, gbc);
        
        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(GUIColors.CARD_BACKGROUND);
        btnSalvar = ButtonFactory.createActionButton("Salvar", GUIColors.OPERACOES);
        btnCancelar = ButtonFactory.createCancelButton("Cancelar");
        
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        panel.add(buttonPanel, gbc);
        
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        
        btnSalvar.addActionListener(e -> salvarLocacao());
        btnCancelar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        
        this.pack();
        this.setLocationRelativeTo(parent);
        this.setResizable(false);
    }
    
    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField(20);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(new CompoundBorder(
            new LineBorder(GUIColors.withAlpha(GUIColors.TEXT, 100), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        return campo;
    }
    
    private void carregarClientes() {
        try {
            for (Cliente cliente : Cliente.listarTodos()) {
                cmbCliente.addItem(cliente.getCpf() + " - " + cliente.getNome());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar clientes: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void carregarVeiculos() {
        try {
            for (Veiculo veiculo : Veiculo.listarTodos()) {
                if (veiculo.isDisponivel()) {
                    cmbVeiculo.addItem(veiculo.getPlaca() + " - " + veiculo.getModelo());
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar veículos: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void salvarLocacao() {
        try {
            if (cmbCliente.getSelectedIndex() == -1 ||
                cmbVeiculo.getSelectedIndex() == -1 ||
                txtDataInicio.getText().trim().isEmpty() ||
                txtDataFim.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(this,
                    "Todos os campos são obrigatórios!",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String cpf = cmbCliente.getSelectedItem().toString().split(" - ")[0];
            String placa = cmbVeiculo.getSelectedItem().toString().split(" - ")[0];
            
            Cliente cliente = Cliente.buscarPorCPF(cpf);
            Veiculo veiculo = Veiculo.buscarPorPlaca(placa);
            
            if (cliente == null || veiculo == null) {
                JOptionPane.showMessageDialog(this,
                    "Cliente ou veículo não encontrado!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            LocalDate dataInicio = LocalDate.parse(txtDataInicio.getText(), dateFormatter);
            LocalDate dataFim = LocalDate.parse(txtDataFim.getText(), dateFormatter);
            
            if (dataFim.isBefore(dataInicio)) {
                JOptionPane.showMessageDialog(this,
                    "A data de fim não pode ser anterior à data de início!",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Locacao locacao = new Locacao(cliente, veiculo, dataInicio, dataFim);
            locacao.salvar();
            
            veiculo.setDisponivel(false);
            veiculo.atualizar();
            
            JOptionPane.showMessageDialog(this,
                String.format("Locação realizada com sucesso!\nValor total: R$ %.2f", 
                    locacao.calcularValorTotal()),
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            
            setVisible(false);
            dispose();
            
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                "Data inválida! Use o formato dd/mm/aaaa",
                "Erro de Validação",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao realizar locação: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 