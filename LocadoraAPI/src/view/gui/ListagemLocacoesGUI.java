package view.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Locacao;

public class ListagemLocacoesGUI extends JDialog {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnFechar;
    private JButton btnExcluir;

    public ListagemLocacoesGUI(JFrame parent) {
        super(parent, "Listagem de Locações", true);

        this.setLayout(new BorderLayout());
        
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Cliente");
        modeloTabela.addColumn("Veículo");
        modeloTabela.addColumn("Data Início");
        modeloTabela.addColumn("Data Fim");
        modeloTabela.addColumn("Status");
        modeloTabela.addColumn("Valor Total");
        
        tabela = new JTable(modeloTabela);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnExcluir = new JButton("Excluir Selecionado");
        btnFechar = new JButton("Fechar");
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnFechar);
        
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
        btnFechar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        btnExcluir.addActionListener(e -> excluirLocacao());
        
        carregarLocacoes();
        
        this.setSize(800, 400);
        this.setLocationRelativeTo(parent);
    }
    
    private void carregarLocacoes() {
        try {
            modeloTabela.setRowCount(0);
            
            List<Locacao> locacoes = Locacao.listarTodas();
            
            for (Locacao locacao : locacoes) {
                modeloTabela.addRow(new Object[]{
                    locacao.getId(),
                    locacao.getCliente().getNome(),
                    locacao.getVeiculo().getModelo(),
                    locacao.getDataInicio(),
                    locacao.getDataFim(),
                    String.format("R$ %.2f", locacao.calcularValorTotal()),
                    locacao.isAtiva() ? "Em Andamento" : "Finalizada"
                });
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar locações: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirLocacao() {
        try {
            String idStr = JOptionPane.showInputDialog(this,
                "Digite o ID da locação a ser excluída:",
                "Excluir Locação",
                JOptionPane.QUESTION_MESSAGE);
            
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    Long id = Long.parseLong(idStr);
                    Locacao locacao = Locacao.buscarPorId(id);
                    
                    if (locacao == null) {
                        JOptionPane.showMessageDialog(this,
                            "Locação não encontrada!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    int opcao = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja excluir a locação?\n\n" + locacao.toString(),
                        "Confirmar Exclusão",
                        JOptionPane.YES_NO_OPTION);
                    
                    if (opcao == JOptionPane.YES_OPTION) {
                        locacao.excluir();
                        JOptionPane.showMessageDialog(this,
                            "Locação excluída com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                        carregarLocacoes();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this,
                        "ID inválido! Digite apenas números.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao excluir locação: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 