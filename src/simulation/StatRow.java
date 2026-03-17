package simulation;

import enums.FontWeight;
import enums.TextSize;
import enums.UnitType;
import helper.FontHelper;
import helper.MyLabel;
import settings.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.function.Supplier;

public class StatRow extends JPanel {
    JLabel valueLabel;
    JProgressBar progressBar;
    UnitType unit;
    public StatRow(String title, JPanel parent, UnitType unit) {
        this.unit = unit;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setOpaque(false);
        JLabel item = new MyLabel(title, FontWeight.MEDIUM, TextSize.SMALL);

        Dimension size = item.getPreferredSize();
        item.setPreferredSize(new Dimension(size.width + 20, size.height));

        Component x = Box.createHorizontalGlue();
        this.add(item);
        this.add(x);


        if (this.unit == UnitType.PERCENTAGE) {
            createProgressBar();
        }
        createNumericalValueLabel();


        this.setBorder(new EmptyBorder(0, 2, 10, 25));

        this.setMaximumSize(new Dimension(Short.MAX_VALUE, this.getPreferredSize().height));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);

        parent.add(this);
    }

    public void createNumericalValueLabel() {
        this.valueLabel = new MyLabel("0", FontWeight.MEDIUM, TextSize.SMALL);
        this.valueLabel.setBorder(new EmptyBorder(0, 25, 0 ,0));
        this.valueLabel.setForeground(Settings.textColor2);
        this.add(this.valueLabel);
    }

    public void createProgressBar() {
        JProgressBar bar = new JProgressBar();
        bar.setValue(0);
        bar.setForeground(Settings.textColor);
        bar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI() {
            protected Color getSelectionBackground() { return Color.black; } // Text color over background
            protected Color getSelectionForeground() { return Color.white; } // Text color over progress
        });

        bar.setMaximumSize(new Dimension(10, 4));
        bar.setMinimumSize(new Dimension(10, 4));
        bar.setForeground(new Color(46, 204, 113));
        bar.setBackground(Settings.themeColor2);
        bar.setOpaque(true);
        bar.setBorderPainted(false);

        this.progressBar = bar;
        this.add(this.progressBar);
    }



    public void update(String newStat) {
         if (this.unit == UnitType.PERCENTAGE) {
             int percentage = (int) Double.parseDouble(newStat);
             if (percentage > 50) this.progressBar.setForeground(Color.YELLOW);
             if (percentage > 90) this.progressBar.setForeground(Color.RED);
             this.progressBar.setValue(percentage);
             this.valueLabel.setText(" " + newStat + "%");
         } else {
             this.valueLabel.setText(newStat);
         }
    }
}
