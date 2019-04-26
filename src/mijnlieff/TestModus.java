package mijnlieff;

import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.embed.swing.SwingFXUtils;
import mijnlieff.Model.Model;
import mijnlieff.Model.SpeelveldModel;

public class TestModus {

    public TestModus(SpeelveldModel model, String bestand, Scene scene) {
        model.forwardAll();
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(scene.snapshot(new WritableImage((int) scene.getWidth(), (int) scene.getHeight())),
                    new BufferedImage((int) scene.getWidth(),(int) scene.getHeight(),BufferedImage.TYPE_INT_ARGB_PRE)), "png", new File(bestand));
        }catch (IOException ex){
            throw new RuntimeException("fout bij het maken van de foto " + ex);
        }
    }
}
