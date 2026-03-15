package simulation;

import helper.FontHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Supplier;

public class SidebarRow extends JPanel {
    Supplier<String> supplier;
    JLabel valueLabel;
    public SidebarRow(String title, JPanel parent, Supplier<String> supplier) {
        this.supplier = supplier;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setOpaque(false);
        JLabel item = new JLabel(title);
        item.setFont(FontHelper.getFont("Medium").deriveFont(14f));

        Dimension size = item.getPreferredSize();
        item.setPreferredSize(new Dimension(size.width + 20, size.height));

        this.valueLabel = new JLabel();
        this.valueLabel.setFont(FontHelper.getFont("Medium").deriveFont(14f));

        Component x = Box.createHorizontalGlue();
        this.add(item);
        this.add(x);

        this.add(this.valueLabel);

        this.setBorder(new EmptyBorder(0, 2, 10, 25));

        this.setMaximumSize(new Dimension(Short.MAX_VALUE, this.getPreferredSize().height));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);

        parent.add(this);
    }

    public void update() {
        this.valueLabel.setText(this.supplier.get());
    }
}
