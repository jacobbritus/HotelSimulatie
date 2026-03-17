package helper;

import enums.FontWeight;
import settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyButton extends JButton {
    private Color hoverColor;

    public MyButton(String text, ActionListener actionListener) {
        this.setText(text);
        this.setForeground(Settings.textColor);
        this.setFont(FontHelper.getFont(FontWeight.MEDIUM).deriveFont(12f));
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setFocusable(false);
        this.addActionListener(actionListener);

        this.setMaximumSize(new Dimension(Settings.sidebarWidth, 50));
        this.setBackground(Settings.themeColor2);
        this.setOpaque(true);

        JButton button = this;
        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Settings.themeColor3);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(Settings.themeColor2);
            }
        });
    }

}
