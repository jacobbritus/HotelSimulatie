import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseWheel implements MouseWheelListener {
    JFrame frame;
    JPanel panel;

    public MouseWheel(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println(e);
    }
}
