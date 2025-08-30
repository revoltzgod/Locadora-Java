package view.gui;

import javax.swing.*;
import java.awt.*;
import model.Cliente;

public class AtualizarClienteGUI extends JDialog {
    private JTextField txtCPF;
    private JTextField txtNome;
    private JTextField txtTelefone;
    private JTextField txtCNH;
    private JTextField txtEndereco;
    private Cliente clienteAtual;

    public AtualizarClienteGUI(JFrame parent) {
        super(parent, "Atualizar Cliente", true);

        JPanel painelBusca = new JPanel(new FlowLayout());
        txtCPF = new JTextField(14);
        JButton btnBuscar = new JButton("Buscar");
        painelBusca.add(new JLabel("CPF:"));
        painelBusca.add(txtCPF);
        painelBusca.add(btnBuscar);

        JPanel painelDados = new JPanel(new GridLayout(4, 2, 5, 5));
        txtNome = new JTextField();
        txtTelefone = new JTextField();
        txtCNH = new JTextField();
        txtEndereco = new JTextField();

        painelDados.add(new JLabel("Nome:"));
        painelDados.add(txtNome);
        painelDados.add(new JLabel("Telefone:"));
        painelDados.add(txtTelefone);
        painelDados.add(new JLabel("CNH:"));
        painelDados.add(txtCNH);
        painelDados.add(new JLabel("Endereço:"));
        painelDados.add(txtEndereco);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelPrincipal.add(painelBusca);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(painelDados);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(painelBotoes);

        desabilitarCampos();

        btnBuscar.addActionListener(e -> buscarCliente());
        btnSalvar.addActionListener(e -> salvarCliente());
        btnCancelar.addActionListener(e -> dispose());

        add(painelPrincipal);
        pack();
        setLocationRelativeTo(parent);
    }

    private void buscarCliente() {
        try {
            String cpf = txtCPF.getText().trim();
            if (cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o CPF do cliente!");
                return;
            }

            clienteAtual = Cliente.buscarPorCPF(cpf);
            if (clienteAtual == null) {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado!");
                return;
            }

            txtNome.setText(clienteAtual.getNome());
            txtTelefone.setText(clienteAtual.getTelefone());
            txtCNH.setText(clienteAtual.getCnh());
            txtEndereco.setText(clienteAtual.getEndereco());

            habilitarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar cliente: " + e.getMessage());
        }
    }

    private void salvarCliente() {
        try {
            if (clienteAtual == null) {
                JOptionPane.showMessageDialog(this, "Primeiro busque um cliente!");
                return;
            }

            clienteAtual = new Cliente(
                txtCPF.getText(),
                txtNome.getText(),
                txtTelefone.getText(),
                txtCNH.getText(),
                txtEndereco.getText()
            );

            clienteAtual.atualizar();
            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    private void habilitarCampos() {
        txtNome.setEnabled(true);
        txtTelefone.setEnabled(true);
        txtCNH.setEnabled(true);
        txtEndereco.setEnabled(true);
    }

    private void desabilitarCampos() {
        txtNome.setEnabled(false);
        txtTelefone.setEnabled(false);
        txtCNH.setEnabled(false);
        txtEndereco.setEnabled(false);
    }
} 