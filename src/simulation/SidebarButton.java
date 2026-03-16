package simulation;

import helper.FontHelper;
import settings.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SidebarButton extends JButton {
    public SidebarButton(String text) {
        this.setText(text);
        this.setForeground(Settings.textColor);
        this.setFont(FontHelper.getFont("Medium").deriveFont(12f));
        this.setBorderPainted(false);
//        this.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Settings.themeColor3, 1),
//                new EmptyBorder(20, 15, 20, 0)));
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
