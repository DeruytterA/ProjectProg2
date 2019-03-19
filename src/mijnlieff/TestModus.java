package mijnlieff;

import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.embed.swing.SwingFXUtils;

public class TestModus {

    public TestModus(SpelModel model, String bestand, Scene scene) {
        model.forwardAll();
        WritableImage image = new WritableImage((int) scene.getWidth(), (int) scene.getHeight());
        scene.snapshot(image);
        File file = new File(bestand);
        BufferedImage bimg = new BufferedImage((int) scene.getWidth(),(int) scene.getHeight(),BufferedImage.TYPE_INT_ARGB_PRE);
        bimg = SwingFXUtils.fromFXImage(image, bimg);
        try {
            ImageIO.write(bimg, "png", file);
        }catch (IOException ex){
            throw new RuntimeException("fout bij het maken van de foto" + ex);
        }
    }
}
