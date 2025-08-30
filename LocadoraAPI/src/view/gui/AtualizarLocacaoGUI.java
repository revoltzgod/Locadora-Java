package view.gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.Locacao;

public class AtualizarLocacaoGUI extends JDialog {
    private JTextField txtId;
    private JTextField txtDataInicio;
    private JTextField txtDataFim;
    private JTextField txtValorTotal;
    private JCheckBox chkAtiva;
    private Locacao locacaoAtual;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public AtualizarLocacaoGUI(JFrame parent) {
        super(parent, "Atualizar Locação", true);

        // Painel de busca
        JPanel painelBusca = new JPanel(new FlowLayout());
        txtId = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar");
        painelBusca.add(new JLabel("ID:"));
        painelBusca.add(txtId);
        painelBusca.add(btnBuscar);

        // Painel de dados
        JPanel painelDados = new JPanel(new GridLayout(4, 2, 5, 5));
        txtDataInicio = new JTextField();
        txtDataFim = new JTextField();
        txtValorTotal = new JTextField();
        chkAtiva = new JCheckBox("Locação Ativa");

        painelDados.add(new JLabel("Data Início:"));
        painelDados.add(txtDataInicio);
        painelDados.add(new JLabel("Data Fim:"));
        painelDados.add(txtDataFim);
        painelDados.add(new JLabel("Valor Total:"));
        painelDados.add(txtValorTotal);
        painelDados.add(new JLabel("Status:"));
        painelDados.add(chkAtiva);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelPrincipal.add(painelBusca);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(painelDados);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(painelBotoes);

        // Configuração inicial
        desabilitarCampos();

        // Listeners
        btnBuscar.addActionListener(e -> buscarLocacao());
        btnSalvar.addActionListener(e -> salvarLocacao());
        btnCancelar.addActionListener(e -> dispose());

        this.add(painelPrincipal);
        this.pack();
        this.setLocationRelativeTo(parent);
    }

    private void buscarLocacao() {
        try {
            String idStr = txtId.getText().trim();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o ID da locação!");
                return;
            }

            Long id = Long.parseLong(idStr);
            locacaoAtual = Locacao.buscarPorId(id);
            if (locacaoAtual == null) {
                JOptionPane.showMessageDialog(this, "Locação não encontrada!");
                return;
            }

            txtDataInicio.setText(locacaoAtual.getDataInicio().format(dateFormatter));
            txtDataFim.setText(locacaoAtual.getDataFim().format(dateFormatter));
            txtValorTotal.setText(String.format("%.2f", locacaoAtual.calcularValorTotal()));
            chkAtiva.setSelected(locacaoAtual.isAtiva());

            habilitarCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar locação: " + e.getMessage());
        }
    }

    private void salvarLocacao() {
        try {
            if (locacaoAtual == null) {
                JOptionPane.showMessageDialog(this, "Primeiro busque uma locação!");
                return;
            }

            LocalDate dataInicio = LocalDate.parse(txtDataInicio.getText(), dateFormatter);
            LocalDate dataFim = LocalDate.parse(txtDataFim.getText(), dateFormatter);

            locacaoAtual.setDataInicio(dataInicio);
            locacaoAtual.setDataFim(dataFim);
            locacaoAtual.setAtiva(chkAtiva.isSelected());

            locacaoAtual.atualizar();
            JOptionPane.showMessageDialog(this, "Locação atualizada com sucesso!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar locação: " + e.getMessage());
        }
    }

    private void habilitarCampos() {
        txtDataInicio.setEnabled(true);
        txtDataFim.setEnabled(true);
        txtValorTotal.setEnabled(true);
        chkAtiva.setEnabled(true);
    }

    private void desabilitarCampos() {
        txtDataInicio.setEnabled(false);
        txtDataFim.setEnabled(false);
        txtValorTotal.setEnabled(false);
        chkAtiva.setEnabled(false);
    }
} 