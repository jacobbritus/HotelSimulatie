package helper;

import enums.FontWeight;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontHelper {
    public static Font getFont(FontWeight fontWeight) {
        String weight = fontWeight.getStr();
        File font_file = new File(
                String.format("assets/fonts/OpenSans-%s.ttf", weight)
        );
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
