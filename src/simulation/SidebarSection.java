package simulation;

import helper.FontHelper;
import settings.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;

public class SidebarSection extends JPanel {
    private JPanel parent;
    private ArrayList<JPanel> statRows;

    public SidebarSection(JPanel parent, String title) {
        this.parent = parent;
        parent.add(this);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);

        this.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 1, 0,
                Settings.themeColor2), new EmptyBorder(15, 15, 5, 0)));

        // --- Title ---
        JLabel titleLabel = new JLabel(title);
        titleLabel.setPreferredSize(new Dimension(Settings.sidebarWidth, 64));
        titleLabel.setFont(FontHelper.getFont("SemiBold").deriveFont(18f));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 60));
        this.add(titleLabel);
    }
}
