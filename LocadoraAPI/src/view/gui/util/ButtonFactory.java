package view.gui.util;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonFactory {
    public static JButton createButton(String text, Color backgroundColor, Color textColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBorder(new CompoundBorder(
            new LineBorder(GUIColors.BUTTON_BORDER, 1, true),
            new EmptyBorder(8, 16, 8, 16)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(GUIColors.BUTTON_HOVER);
                button.setBorder(new CompoundBorder(
                    new LineBorder(backgroundColor, 1, true),
                    new EmptyBorder(8, 16, 8, 16)
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
                button.setBorder(new CompoundBorder(
                    new LineBorder(GUIColors.BUTTON_BORDER, 1, true),
                    new EmptyBorder(8, 16, 8, 16)
                ));
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(GUIColors.withAlpha(backgroundColor, 180));
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });
        
        return button;
    }
    
    public static JButton createMenuButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 45));
        button.setMaximumSize(new Dimension(200, 45));
        button.setMinimumSize(new Dimension(200, 45));
        button.setBackground(GUIColors.CARD_BACKGROUND);
        button.setForeground(GUIColors.TEXT);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(new CompoundBorder(
            new LineBorder(backgroundColor, 2, true),
            new EmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        
        // Ícone colorido à esquerda do texto
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(10);
        
        // Efeitos de hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor);
                button.setForeground(Color.WHITE);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(GUIColors.CARD_BACKGROUND);
                button.setForeground(GUIColors.TEXT);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(GUIColors.withAlpha(backgroundColor, 200));
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (button.getModel().isRollover()) {
                    button.setBackground(backgroundColor);
                } else {
                    button.setBackground(GUIColors.CARD_BACKGROUND);
                }
            }
        });
        
        return button;
    }
    
    public static JButton createActionButton(String text, Color backgroundColor) {
        JButton button = createButton(text, backgroundColor, Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return button;
    }
    
    public static JButton createCancelButton(String text) {
        JButton button = createButton(text, GUIColors.CARD_BACKGROUND, GUIColors.TEXT);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return button;
    }
} 