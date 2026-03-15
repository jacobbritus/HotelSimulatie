import settings.Settings;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class MyScrollPane extends JScrollPane {
    public MyScrollPane(Component view) {
        super(view);

        // Change scroll speed
        this.getVerticalScrollBar().setUnitIncrement(9);
        this.getHorizontalScrollBar().setUnitIncrement(9);
        this.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);


        this.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            protected void configureScrollBarColors() {
                this.trackColor = Color.RED;
            }
        });


        this.setBackground(Settings.themeColor);
        this.setOpaque(true);
        this.setBorder(null);

        this.getVerticalScrollBar().setFocusable(false);
        this.getHorizontalScrollBar().setFocusable(false);

        this.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        this.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 8));


        this.getVerticalScrollBar().setUI(new BasicScrollBarUI() {

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Settings.themeColor2; // draggable part
                this.trackColor = Settings.themeColor;    // background track
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0,0));
                button.setMinimumSize(new Dimension(0,0));
                button.setMaximumSize(new Dimension(0,0));
                button.setFocusable(false);
                return button;
            }
        });



        this.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Settings.themeColor2; // draggable part
                this.trackColor = Settings.themeColor;    // background track
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0,0));
                button.setMinimumSize(new Dimension(0,0));
                button.setMaximumSize(new Dimension(0,0));
                button.setFocusable(false);
                return button;
            }
        });



        JPanel cornerTopRight = new JPanel();
        cornerTopRight.setBackground(Settings.themeColor);
        this.setCorner(JScrollPane.UPPER_RIGHT_CORNER, cornerTopRight);


        JPanel cornerTopLeft = new JPanel();
        cornerTopLeft.setBackground(Settings.themeColor);
        this.setCorner(JScrollPane.UPPER_LEFT_CORNER, cornerTopLeft);

        JPanel cornerBottomRight = new JPanel();
        cornerBottomRight.setBackground(Settings.themeColor);
        this.setCorner(JScrollPane.LOWER_RIGHT_CORNER, cornerBottomRight);
    }
}