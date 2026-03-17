package simulation;

import enums.SidebarPageType;
import helper.MyButton;
import settings.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class SidebarNavigationPanel extends JPanel {
    public SidebarNavigationPanel(SimulationSidebar simulationSidebar) {
        this.setOpaque(true);
        this.setBackground(Settings.themeColor2);
        this.setPreferredSize(new Dimension(Settings.sidebarWidth / 6, Settings.schermHoogte));

        this.setBorder(BorderFactory.createCompoundBorder( new MatteBorder(0, 1, 1, 1,
                Settings.themeColor3), new EmptyBorder(0, 0, 20, 0)));

        MyButton button =new MyButton("EV", _ -> simulationSidebar.openPage(SidebarPageType.EVENTS));

        button.setPreferredSize(new Dimension(360, 65));
        button.setBorder(new MatteBorder(0, 1, 1, 1, Settings.themeColor3));

        MyButton button2 =new MyButton("OV", _ -> simulationSidebar.openPage(SidebarPageType.OVERVIEW));

        button2.setPreferredSize(new Dimension(360, 65));


        this.add(button2);
        this.add(button);

    }


}
