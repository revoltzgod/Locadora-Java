package view.gui;

import javax.swing.*;
import java.awt.*;
import model.*;

public class AtualizarVeiculoGUI extends JDialog {
    private JTextField txtPlaca;
    private JTextField txtModelo;
    private JTextField txtMarca;
    private JTextField txtAno;
    private JTextField txtValorDiaria;
    private JComboBox<String> cboTipo;
    private JPanel painelCarro;
    private JPanel painelMoto;
    private JTextField txtNumeroPortas;
    private JTextField txtTipoCambio;
    private JCheckBox chkArCondicionado;
    private JTextField txtCilindradas;
    private JTextField txtTipoPartida;
    private Veiculo veiculoAtual;

    public AtualizarVeiculoGUI(JFrame parent) {
        super(parent, "Atualizar Veículo", true);

        JPanel painelBusca = new JPanel(new FlowLayout());
        txtPlaca = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar");
        painelBusca.add(new JLabel("Placa:"));
        painelBusca.add(txtPlaca);
        painelBusca.add(btnBuscar);

        JPanel painelDados = new JPanel(new GridLayout(6, 2, 5, 5));
        cboTipo = new JComboBox<>(new String[]{"Carro", "Moto"});
        txtModelo = new JTextField();
        txtMarca = new JTextField();
        txtAno = new JTextField();
        txtValorDiaria = new JTextField();

        painelDados.add(new JLabel("Tipo:"));
        painelDados.add(cboTipo);
        painelDados.add(new JLabel("Modelo:"));
        painelDados.add(txtModelo);
        painelDados.add(new JLabel("Marca:"));
        painelDados.add(txtMarca);
        painelDados.add(new JLabel("Ano:"));
        painelDados.add(txtAno);
        painelDados.add(new JLabel("Valor Diária:"));
        painelDados.add(txtValorDiaria);

        painelCarro = new JPanel(new GridLayout(3, 2, 5, 5));
        txtNumeroPortas = new JTextField();
        txtTipoCambio = new JTextField();
        chkArCondicionado = new JCheckBox();

        painelCarro.add(new JLabel("Número de Portas:"));
        painelCarro.add(txtNumeroPortas);
        painelCarro.add(new JLabel("Tipo de Câmbio:"));
        painelCarro.add(txtTipoCambio);
        painelCarro.add(new JLabel("Ar Condicionado:"));
        painelCarro.add(chkArCondicionado);

        painelMoto = new JPanel(new GridLayout(2, 2, 5, 5));
        txtCilindradas = new JTextField();
        txtTipoPartida = new JTextField();

        painelMoto.add(new JLabel("Cilindradas:"));
        painelMoto.add(txtCilindradas);
        painelMoto.add(new JLabel("Tipo de Partida:"));
        painelMoto.add(txtTipoPartida);

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
        painelPrincipal.add(painelCarro);
        painelPrincipal.add(painelMoto);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(painelBotoes);

        desabilitarCampos();
        painelMoto.setVisible(false);

        btnBuscar.addActionListener(e -> buscarVeiculo());
        btnSalvar.addActionListener(e -> salvarVeiculo());
        btnCancelar.addActionListener(e -> dispose());

        add(painelPrincipal);
        pack();
        setLocationRelativeTo(parent);
    }

    private void buscarVeiculo() {
        try {
            String placa = txtPlaca.getText().trim();
            if (placa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite a placa do veículo!");
                return;
            }

            veiculoAtual = Veiculo.buscarPorPlaca(placa);
            if (veiculoAtual == null) {
                JOptionPane.showMessageDialog(this, "Veículo não encontrado!");
                return;
            }

            txtModelo.setText(veiculoAtual.getModelo());
            txtMarca.setText(veiculoAtual.getMarca());
            txtAno.setText(String.valueOf(veiculoAtual.getAno()));
            txtValorDiaria.setText(String.valueOf(veiculoAtual.getValorDiaria()));

            if (veiculoAtual instanceof Carro) {
                Carro carro = (Carro) veiculoAtual;
                cboTipo.setSelectedIndex(0);
                txtNumeroPortas.setText(String.valueOf(carro.getNumeroPortas()));
                txtTipoCambio.setText(carro.getTipoCambio());
                chkArCondicionado.setSelected(carro.hasArCondicionado());
                painelCarro.setVisible(true);
                painelMoto.setVisible(false);
            } else {
                Moto moto = (Moto) veiculoAtual;
                cboTipo.setSelectedIndex(1);
                txtCilindradas.setText(String.valueOf(moto.getCilindradas()));
                txtTipoPartida.setText(moto.getTipoPartida());
                painelCarro.setVisible(false);
                painelMoto.setVisible(true);
            }

            habilitarCampos();
            cboTipo.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar veículo: " + e.getMessage());
        }
    }

    private void salvarVeiculo() {
        try {
            if (veiculoAtual == null) {
                JOptionPane.showMessageDialog(this, "Primeiro busque um veículo!");
                return;
            }

            if (veiculoAtual instanceof Carro) {
                veiculoAtual = new Carro(
                    txtPlaca.getText(),
                    txtModelo.getText(),
                    txtMarca.getText(),
                    Integer.parseInt(txtAno.getText()),
                    Double.parseDouble(txtValorDiaria.getText()),
                    Integer.parseInt(txtNumeroPortas.getText()),
                    txtTipoCambio.getText(),
                    chkArCondicionado.isSelected()
                );
            } else {
                veiculoAtual = new Moto(
                    txtPlaca.getText(),
                    txtModelo.getText(),
                    txtMarca.getText(),
                    Integer.parseInt(txtAno.getText()),
                    Double.parseDouble(txtValorDiaria.getText()),
                    Integer.parseInt(txtCilindradas.getText()),
                    txtTipoPartida.getText()
                );
            }

            veiculoAtual.atualizar();
            JOptionPane.showMessageDialog(this, "Veículo atualizado com sucesso!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar veículo: " + e.getMessage());
        }
    }

    private void habilitarCampos() {
        txtModelo.setEnabled(true);
        txtMarca.setEnabled(true);
        txtAno.setEnabled(true);
        txtValorDiaria.setEnabled(true);
        txtNumeroPortas.setEnabled(true);
        txtTipoCambio.setEnabled(true);
        chkArCondicionado.setEnabled(true);
        txtCilindradas.setEnabled(true);
        txtTipoPartida.setEnabled(true);
    }

    private void desabilitarCampos() {
        txtModelo.setEnabled(false);
        txtMarca.setEnabled(false);
        txtAno.setEnabled(false);
        txtValorDiaria.setEnabled(false);
        txtNumeroPortas.setEnabled(false);
        txtTipoCambio.setEnabled(false);
        chkArCondicionado.setEnabled(false);
        txtCilindradas.setEnabled(false);
        txtTipoPartida.setEnabled(false);
    }
} 