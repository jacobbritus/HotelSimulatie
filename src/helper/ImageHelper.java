package helper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Objects;


public class ImageHelper {
    public static Image getImage(String imagePath) {
        Image img;
        try {
            img = ImageIO.read(
                    Objects.requireNonNull(ImageHelper.class.getResource(imagePath)));
        } catch (Exception e) {
            System.out.println(e);
            img = null;
        }

        return img;
    }
}
