package helper;

import enums.FontWeight;
import enums.TextSize;

import javax.swing.*;

public class MyLabel extends JLabel {
    public MyLabel(String text, FontWeight weight, TextSize textSize) {
        this.setText(text);
        switch (textSize) {
            case SMALL -> this.setFont(FontHelper.getFont(weight).deriveFont(12f));
            case MEDIUM -> this.setFont(FontHelper.getFont(weight).deriveFont(16f));
            case LARGE -> this.setFont(FontHelper.getFont(weight).deriveFont(24f));
        }

    }
}
