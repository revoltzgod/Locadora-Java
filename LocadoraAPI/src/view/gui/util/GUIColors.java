package view.gui.util;

import java.awt.Color;

public class GUIColors {
    // Cores principais - tons mais suaves e profissionais
    public static final Color CADASTROS = new Color(41, 128, 185);      // Azul mais escuro
    public static final Color OPERACOES = new Color(46, 204, 113);      // Verde mais suave
    public static final Color LISTAGENS = new Color(241, 196, 15);      // Amarelo mais suave
    public static final Color ATUALIZACOES = new Color(230, 126, 34);   // Laranja mais suave
    public static final Color EXCLUSOES = new Color(231, 76, 60);       // Vermelho mais suave
    
    // Cores de fundo e texto
    public static final Color BACKGROUND = new Color(245, 245, 245);    // Cinza muito claro
    public static final Color CARD_BACKGROUND = Color.WHITE;            // Branco para cards
    public static final Color TEXT = new Color(44, 62, 80);            // Azul escuro quase preto
    public static final Color TEXT_SECONDARY = new Color(127, 140, 141); // Cinza para textos secundários
    public static final Color HOVER = new Color(52, 152, 219, 40);     // Azul com transparência
    
    // Cores de botões
    public static final Color BUTTON_DEFAULT = Color.WHITE;
    public static final Color BUTTON_HOVER = new Color(236, 240, 241);
    public static final Color BUTTON_TEXT = new Color(44, 62, 80);
    public static final Color BUTTON_BORDER = new Color(189, 195, 199);
    
    // Cores de destaque
    public static final Color ACCENT = new Color(52, 152, 219);        // Azul para destaques
    public static final Color SUCCESS = new Color(46, 204, 113);       // Verde para sucesso
    public static final Color WARNING = new Color(241, 196, 15);       // Amarelo para avisos
    public static final Color ERROR = new Color(231, 76, 60);          // Vermelho para erros
    
    // Método auxiliar para criar cores com transparência
    public static Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
} 