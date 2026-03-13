// Source - https://stackoverflow.com/a/66369769
// Posted by dmitry
// Retrieved 2026-03-13, License - CC BY-SA 4.0

import javax.swing.*;
import java.awt.*;

public class MyScrollPane extends JScrollPane {
    public MyScrollPane(Component view) {
        super(view);
    }

    @Override
    public JScrollBar createVerticalScrollBar() {
        return new MyScrollBar(JScrollBar.VERTICAL);
    }

    @Override
    public JScrollBar createHorizontalScrollBar() {
        return new MyScrollBar(JScrollBar.HORIZONTAL);
    }


    protected class MyScrollBar extends ScrollBar {
        public MyScrollBar(int orientation) {
            super(orientation);
//            setUI(this.createU);
            setOpaque(false);
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            int borderDiameter = 32;

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.gray);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, borderDiameter, borderDiameter);

            super.paintComponent(g);
        }
    }
}
