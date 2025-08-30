package view.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Cliente;

public class ListagemClientesGUI extends JDialog {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnFechar;
    private JButton btnAtualizar;
    private JButton btnExcluir;

    public ListagemClientesGUI(JFrame parent) {
        super(parent, "Listagem de Clientes", true);

        this.setLayout(new BorderLayout());
        
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("CPF");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Telefone");
        modeloTabela.addColumn("CNH");
        modeloTabela.addColumn("Endereço");
        
        tabela = new JTable(modeloTabela);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir Selecionado");
        btnFechar = new JButton("Fechar");
        
        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnFechar);
        
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
        btnAtualizar.addActionListener(e -> atualizarTabela());
        btnExcluir.addActionListener(e -> excluirCliente());
        btnFechar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        
        atualizarTabela();
        
        this.setSize(800, 400);
        this.setLocationRelativeTo(parent);
    }
    
    private void atualizarTabela() {
        try {
            modeloTabela.setRowCount(0);
            
            List<Cliente> clientes = Cliente.listarTodos();
            
            for (Cliente cliente : clientes) {
                modeloTabela.addRow(new Object[]{
                    cliente.getCpf(),
                    cliente.getNome(),
                    cliente.getTelefone(),
                    cliente.getCnh(),
                    cliente.getEndereco()
                });
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar clientes: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirCliente() {
        try {
            String cpf = JOptionPane.showInputDialog(this,
                "Digite o CPF do cliente a ser excluído:",
                "Excluir Cliente",
                JOptionPane.QUESTION_MESSAGE);
            
            if (cpf != null && !cpf.trim().isEmpty()) {
                Cliente cliente = Cliente.buscarPorCPF(cpf);
                
                if (cliente == null) {
                    JOptionPane.showMessageDialog(this,
                        "Cliente não encontrado!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int opcao = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir o cliente?\n\n" + cliente.toString(),
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION);
                
                if (opcao == JOptionPane.YES_OPTION) {
                    cliente.excluir();
                    JOptionPane.showMessageDialog(this,
                        "Cliente excluído com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                    atualizarTabela();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao excluir cliente: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 