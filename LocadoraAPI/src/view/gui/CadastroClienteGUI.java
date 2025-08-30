package view.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import model.Cliente;
import view.gui.util.*;

public class CadastroClienteGUI extends JDialog {
    private JTextField txtCPF;
    private JTextField txtNome;
    private JTextField txtTelefone;
    private JTextField txtCNH;
    private JTextField txtEndereco;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public CadastroClienteGUI(JFrame parent) {
        super(parent, "Cadastro de Cliente", true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(GUIColors.BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(new CompoundBorder(
            new EmptyBorder(20, 20, 20, 20),
            new CompoundBorder(
                new LineBorder(GUIColors.CADASTROS, 2, true),
                new EmptyBorder(20, 20, 20, 20)
            )
        ));
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título
        JLabel titulo = new JLabel("Cadastro de Cliente");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(GUIColors.CADASTROS);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(titulo, gbc);
        
        // Campos
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // CPF
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblCPF = new JLabel("CPF:");
        lblCPF.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCPF.setForeground(GUIColors.TEXT);
        panel.add(lblCPF, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        txtCPF = criarCampoTexto();
        panel.add(txtCPF, gbc);
        
        // Nome
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNome.setForeground(GUIColors.TEXT);
        panel.add(lblNome, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        txtNome = criarCampoTexto();
        panel.add(txtNome, gbc);
        
        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTelefone.setForeground(GUIColors.TEXT);
        panel.add(lblTelefone, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        txtTelefone = criarCampoTexto();
        panel.add(txtTelefone, gbc);
        
        // CNH
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblCNH = new JLabel("CNH:");
        lblCNH.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCNH.setForeground(GUIColors.TEXT);
        panel.add(lblCNH, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        txtCNH = criarCampoTexto();
        panel.add(txtCNH, gbc);
        
        // Endereço
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel lblEndereco = new JLabel("Endereço:");
        lblEndereco.setFont(new Font("Arial", Font.PLAIN, 14));
        lblEndereco.setForeground(GUIColors.TEXT);
        panel.add(lblEndereco, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        txtEndereco = criarCampoTexto();
        panel.add(txtEndereco, gbc);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(GUIColors.BACKGROUND);
        btnSalvar = ButtonFactory.createActionButton("Salvar", GUIColors.CADASTROS);
        btnCancelar = ButtonFactory.createCancelButton("Cancelar");
        
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        panel.add(buttonPanel, gbc);
        
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        
        btnSalvar.addActionListener(e -> salvarCliente());
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
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setBorder(new CompoundBorder(
            new LineBorder(GUIColors.withAlpha(GUIColors.TEXT, 100), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        return campo;
    }

    private void salvarCliente() {
        try {
            if (txtCPF.getText().trim().isEmpty() ||
                txtNome.getText().trim().isEmpty() ||
                txtTelefone.getText().trim().isEmpty() ||
                txtCNH.getText().trim().isEmpty() ||
                txtEndereco.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                    "Todos os campos são obrigatórios!",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cliente cliente = new Cliente(
                txtCPF.getText(),
                txtNome.getText(),
                txtTelefone.getText(),
                txtCNH.getText(),
                txtEndereco.getText()
            );
            
            cliente.salvar();
            
            JOptionPane.showMessageDialog(this,
                "Cliente cadastrado com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            
            setVisible(false);
            dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao cadastrar cliente: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 