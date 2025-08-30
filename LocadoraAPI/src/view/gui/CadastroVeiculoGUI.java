package view.gui;

import javax.swing.*;
import java.awt.*;
import model.*;

public class CadastroVeiculoGUI extends JDialog {
    private JTextField txtPlaca;
    private JTextField txtModelo;
    private JTextField txtMarca;
    private JTextField txtAno;
    private JTextField txtValorDiaria;
    private JTextField txtNumeroPortas;
    private JTextField txtTipoCambio;
    private JCheckBox chkArCondicionado;
    private JTextField txtCilindradas;
    private JTextField txtTipoPartida;
    private JComboBox<String> cboTipo;
    private JPanel painelCarro;
    private JPanel painelMoto;

    public CadastroVeiculoGUI(JFrame parent) {
        super(parent, "Cadastrar Veículo", true);

        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel painelComum = new JPanel(new GridLayout(6, 2, 5, 5));
        cboTipo = new JComboBox<>(new String[]{"Carro", "Moto"});
        txtPlaca = new JTextField();
        txtModelo = new JTextField();
        txtMarca = new JTextField();
        txtAno = new JTextField();
        txtValorDiaria = new JTextField();

        painelComum.add(new JLabel("Tipo:"));
        painelComum.add(cboTipo);
        painelComum.add(new JLabel("Placa:"));
        painelComum.add(txtPlaca);
        painelComum.add(new JLabel("Modelo:"));
        painelComum.add(txtModelo);
        painelComum.add(new JLabel("Marca:"));
        painelComum.add(txtMarca);
        painelComum.add(new JLabel("Ano:"));
        painelComum.add(txtAno);
        painelComum.add(new JLabel("Valor Diária:"));
        painelComum.add(txtValorDiaria);

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

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelPrincipal.add(painelComum, gbc);

        gbc.gridy = 1;
        painelPrincipal.add(painelCarro, gbc);
        painelPrincipal.add(painelMoto, gbc);
        painelMoto.setVisible(false);

        gbc.gridy = 2;
        painelPrincipal.add(painelBotoes, gbc);

        cboTipo.addActionListener(e -> {
            boolean isCarro = cboTipo.getSelectedItem().equals("Carro");
            painelCarro.setVisible(isCarro);
            painelMoto.setVisible(!isCarro);
            pack();
        });

        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());

        add(painelPrincipal);
        pack();
        setLocationRelativeTo(parent);
    }

    private void salvar() {
        try {
            String placa = txtPlaca.getText().trim();
            String modelo = txtModelo.getText().trim();
            String marca = txtMarca.getText().trim();
            String anoStr = txtAno.getText().trim();
            String valorDiariaStr = txtValorDiaria.getText().trim();

            if (placa.isEmpty() || modelo.isEmpty() || marca.isEmpty() || 
                anoStr.isEmpty() || valorDiariaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!");
                return;
            }

            int ano = Integer.parseInt(anoStr);
            double valorDiaria = Double.parseDouble(valorDiariaStr);

            Veiculo veiculo;
            if (cboTipo.getSelectedItem().equals("Carro")) {
                String numeroPortasStr = txtNumeroPortas.getText().trim();
                String tipoCambio = txtTipoCambio.getText().trim();
                
                if (numeroPortasStr.isEmpty() || tipoCambio.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!");
                    return;
                }

                int numeroPortas = Integer.parseInt(numeroPortasStr);
                veiculo = new Carro(placa, modelo, marca, ano, valorDiaria,
                                  numeroPortas, tipoCambio, chkArCondicionado.isSelected());
            } else {
                String cilindradasStr = txtCilindradas.getText().trim();
                String tipoPartida = txtTipoPartida.getText().trim();
                
                if (cilindradasStr.isEmpty() || tipoPartida.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!");
                    return;
                }

                int cilindradas = Integer.parseInt(cilindradasStr);
                veiculo = new Moto(placa, modelo, marca, ano, valorDiaria,
                                 cilindradas, tipoPartida);
            }

            veiculo.salvar();
            JOptionPane.showMessageDialog(this, "Veículo cadastrado com sucesso!");
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valores numéricos inválidos!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar veículo: " + e.getMessage());
        }
    }
} 