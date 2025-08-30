package view.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import view.gui.util.*;

public class LocadoraGUI extends JFrame {
    public LocadoraGUI() {
        setTitle("Sistema de Locadora de Veículos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(GUIColors.BACKGROUND);

        // Painel principal com sombra
        JPanel painelPrincipal = new JPanel(new BorderLayout(20, 20));
        painelPrincipal.setBorder(new CompoundBorder(
            new EmptyBorder(30, 30, 30, 30),
            new CompoundBorder(
                new LineBorder(GUIColors.BUTTON_BORDER, 1, true),
                new EmptyBorder(25, 25, 25, 25)
            )
        ));
        painelPrincipal.setBackground(GUIColors.CARD_BACKGROUND);

        // Título com estilo moderno
        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setBackground(GUIColors.CARD_BACKGROUND);
        
        JLabel titulo = new JLabel("Sistema de Locadora de Veículos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(GUIColors.TEXT);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel subtitulo = new JLabel("Gerenciamento completo de locações");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitulo.setForeground(GUIColors.TEXT_SECONDARY);
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        painelTitulo.add(titulo, BorderLayout.CENTER);
        painelTitulo.add(subtitulo, BorderLayout.SOUTH);
        painelTitulo.setBorder(new EmptyBorder(0, 0, 30, 0));

        // Painel de conteúdo com grid de cards
        JPanel painelConteudo = new JPanel(new GridLayout(1, 5, 20, 0));
        painelConteudo.setBackground(GUIColors.CARD_BACKGROUND);

        // Cards para cada seção
        painelConteudo.add(criarCardCategoria("Cadastros", GUIColors.CADASTROS, new String[][]{
            {"Cadastrar Veículo", "CadastroVeiculoGUI"},
            {"Cadastrar Cliente", "CadastroClienteGUI"},
            {"Cadastrar Locação", "RealizarLocacaoGUI"}
        }));
        
        painelConteudo.add(criarCardCategoria("Operações", GUIColors.OPERACOES, new String[][]{
            {"Realizar Locação", "RealizarLocacaoGUI"},
            {"Finalizar Locação", "FinalizarLocacaoGUI"}
        }));
        
        painelConteudo.add(criarCardCategoria("Listagens", GUIColors.LISTAGENS, new String[][]{
            {"Listar Veículos", "ListagemVeiculosGUI"},
            {"Listar Clientes", "ListagemClientesGUI"},
            {"Listar Locações", "ListagemLocacoesGUI"}
        }));
        
        painelConteudo.add(criarCardCategoria("Atualizações", GUIColors.ATUALIZACOES, new String[][]{
            {"Atualizar Veículo", "AtualizarVeiculoGUI"},
            {"Atualizar Cliente", "AtualizarClienteGUI"},
            {"Atualizar Locação", "AtualizarLocacaoGUI"}
        }));
        
        painelConteudo.add(criarCardCategoria("Exclusões", GUIColors.EXCLUSOES, new String[][]{
            {"Excluir Veículo", "ListagemVeiculosGUI"},
            {"Excluir Cliente", "ListagemClientesGUI"},
            {"Excluir Locação", "ListagemLocacoesGUI"}
        }));

        // Rodapé com botão de sair
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelRodape.setBackground(GUIColors.CARD_BACKGROUND);
        JButton btnSair = ButtonFactory.createActionButton("Sair", GUIColors.EXCLUSOES);
        painelRodape.add(btnSair);
        btnSair.addActionListener(e -> System.exit(0));

        painelPrincipal.add(painelTitulo, BorderLayout.NORTH);
        painelPrincipal.add(painelConteudo, BorderLayout.CENTER);
        painelPrincipal.add(painelRodape, BorderLayout.SOUTH);

        add(painelPrincipal);
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel criarCardCategoria(String titulo, Color cor, String[][] botoes) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(GUIColors.CARD_BACKGROUND);
        card.setBorder(new CompoundBorder(
            new LineBorder(cor, 2, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        // Título da categoria
        JLabel labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelTitulo.setForeground(cor);
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelTitulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        card.add(labelTitulo);
        
        // Adiciona os botões
        for (String[] botao : botoes) {
            JButton btn = ButtonFactory.createMenuButton(botao[0], cor);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.addActionListener(e -> {
                try {
                    String className = "view.gui." + botao[1];
                    Class<?> guiClass = Class.forName(className);
                    JDialog dialog = (JDialog) guiClass.getConstructor(JFrame.class)
                                                     .newInstance(this);
                    dialog.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                        "Erro ao abrir janela: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                }
            });
            card.add(btn);
            card.add(Box.createVerticalStrut(10));
        }
        
        return card;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Configurações globais de UI
            UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 14));
            UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 14));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            LocadoraGUI frame = new LocadoraGUI();
            frame.setVisible(true);
        });
    }
} 