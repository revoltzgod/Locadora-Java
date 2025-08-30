package view.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Locacao;
import model.Veiculo;

public class FinalizarLocacaoGUI extends JDialog {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnFinalizar;
    private JButton btnFechar;

    public FinalizarLocacaoGUI(JFrame parent) {
        super(parent, "Finalizar Locação", true);

        this.setLayout(new BorderLayout());
        
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Cliente");
        modeloTabela.addColumn("Veículo");
        modeloTabela.addColumn("Data Início");
        modeloTabela.addColumn("Data Fim");
        modeloTabela.addColumn("Valor Total");
        
        tabela = new JTable(modeloTabela);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        JScrollPane scrollPane = new JScrollPane(tabela);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnFinalizar = new JButton("Finalizar Locação");
        btnFechar = new JButton("Fechar");
        
        buttonPanel.add(btnFinalizar);
        buttonPanel.add(btnFechar);
        
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
        btnFinalizar.addActionListener(e -> finalizarLocacao());
        btnFechar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        
        carregarLocacoesAtivas();
        
        this.setSize(800, 400);
        this.setLocationRelativeTo(parent);
    }
    
    private void carregarLocacoesAtivas() {
        try {
            modeloTabela.setRowCount(0);
            
            List<Locacao> locacoes = Locacao.listarTodas();
            
            for (Locacao locacao : locacoes) {
                if (locacao.isAtiva()) {
                    modeloTabela.addRow(new Object[]{
                        locacao.getId(),
                        locacao.getCliente().getNome(),
                        locacao.getVeiculo().getMarca() + " " + locacao.getVeiculo().getModelo(),
                        locacao.getDataInicio(),
                        locacao.getDataFim(),
                        String.format("R$ %.2f", locacao.calcularValorTotal())
                    });
                }
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar locações: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void finalizarLocacao() {
        try {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Selecione uma locação para finalizar!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Long id = (Long) modeloTabela.getValueAt(selectedRow, 0);
            
            Locacao locacao = Locacao.buscarPorId(id);
            if (locacao == null) {
                JOptionPane.showMessageDialog(this,
                    "Locação não encontrada!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            locacao.setAtiva(false);
            locacao.atualizar();
            
            Veiculo veiculo = locacao.getVeiculo();
            veiculo.setDisponivel(true);
            veiculo.atualizar();
            
            carregarLocacoesAtivas();
            
            JOptionPane.showMessageDialog(this,
                "Locação finalizada com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao finalizar locação: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 