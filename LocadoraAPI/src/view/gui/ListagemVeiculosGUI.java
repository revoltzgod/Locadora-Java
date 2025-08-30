package view.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Veiculo;

public class ListagemVeiculosGUI extends JDialog {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnFechar;
    private JButton btnAtualizar;
    private JButton btnExcluir;

    public ListagemVeiculosGUI(JFrame parent) {
        super(parent, "Listagem de Veículos", true);

        this.setLayout(new BorderLayout());
        
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Placa");
        modeloTabela.addColumn("Modelo");
        modeloTabela.addColumn("Marca");
        modeloTabela.addColumn("Ano");
        modeloTabela.addColumn("Tipo");
        modeloTabela.addColumn("Valor Diária");
        modeloTabela.addColumn("Status");
        
        tabela = new JTable(modeloTabela);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAtualizar = new JButton("Atualizar");
        btnFechar = new JButton("Fechar");
        btnExcluir = new JButton("Excluir Selecionado");
        
        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnFechar);
        buttonPanel.add(btnExcluir);
        
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
        btnAtualizar.addActionListener(e -> atualizarTabela());
        btnFechar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        btnExcluir.addActionListener(e -> excluirVeiculo());
        
        atualizarTabela();
        
        this.setSize(900, 400);
        this.setLocationRelativeTo(parent);
    }
    
    private void atualizarTabela() {
        try {
            modeloTabela.setRowCount(0);
            
            List<Veiculo> veiculos = Veiculo.listarTodos();
            
            for (Veiculo veiculo : veiculos) {
                modeloTabela.addRow(new Object[]{
                    veiculo.getPlaca(),
                    veiculo.getModelo(),
                    veiculo.getMarca(),
                    veiculo.getAno(),
                    veiculo.getTipo(),
                    String.format("R$ %.2f", veiculo.getValorDiaria()),
                    veiculo.isDisponivel() ? "Disponível" : "Locado"
                });
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar veículos: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirVeiculo() {
        try {
            String placa = JOptionPane.showInputDialog(this,
                "Digite a placa do veículo a ser excluído:",
                "Excluir Veículo",
                JOptionPane.QUESTION_MESSAGE);
            
            if (placa != null && !placa.trim().isEmpty()) {
                Veiculo veiculo = Veiculo.buscarPorPlaca(placa);
                
                if (veiculo == null) {
                    JOptionPane.showMessageDialog(this,
                        "Veículo não encontrado!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int opcao = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir o veículo?\n\n" + veiculo.toString(),
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION);
                
                if (opcao == JOptionPane.YES_OPTION) {
                    veiculo.excluir();
                    JOptionPane.showMessageDialog(this,
                        "Veículo excluído com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                    atualizarTabela();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao excluir veículo: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 