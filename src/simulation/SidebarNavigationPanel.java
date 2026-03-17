package simulation;

import enums.SidebarPageType;
import helper.ImageHelper;
import helper.MyButton;
import settings.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.File;

public class SidebarNavigationPanel extends JPanel {
    private SimulationSidebar simulationSidebar;
    public SidebarNavigationPanel(SimulationSidebar simulationSidebar) {
        this.setOpaque(true);
        this.setBackground(Settings.themeColor2);
        this.setPreferredSize(new Dimension(Settings.sidebarWidth / 6, Settings.schermHoogte));

        this.setBorder(BorderFactory.createCompoundBorder( new MatteBorder(0, 1, 1, 1,
                Settings.themeColor3), new EmptyBorder(0, 0, 20, 0)));

        MyButton button =new MyButton("EV", e -> {
            simulationSidebar.openPage(SidebarPageType.EVENTS);
        });


        // Source - https://stackoverflow.com/a/13509865
// Posted by jlordo
// Retrieved 2026-03-18, License - CC BY-SA 3.0

        File file = new File("assets/PathItherator.png");
        String path = file.getAbsolutePath();


        System.out.println(path);


        button.setPreferredSize(new Dimension(360, 65));
        button.setBorder(new MatteBorder(0, 1, 1, 1, Settings.themeColor3));

        MyButton button2 =new MyButton("OV", e -> {
            simulationSidebar.openPage(SidebarPageType.OVERVIEW);
        });

        button2.setPreferredSize(new Dimension(360, 65));


        this.add(button2);
        this.add(button);

    }


}
