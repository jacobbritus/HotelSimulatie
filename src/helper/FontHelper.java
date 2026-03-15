package helper;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontHelper {
    public static Font getFont(String weight) {
        File font_file = new File(
                String.format("assets/fonts/OpenSans-%s.ttf", weight)
        );
        System.out.println(font_file.getAbsoluteFile());
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, font_file);

        } catch (IOException | FontFormatException e) {
            System.out.println("Could not find font.");
            font = new Font("Arial", Font.PLAIN, 32);
        }

        return  font;
    }
}
