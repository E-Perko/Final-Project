package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GraphicsManager {
    PelletPursuitDemo main = new PelletPursuitDemo();
    Image image;
    int count = 0;

    public void displayImg(String img) {
        if (count == 0) {
            image = new Image("/" + img + ".png");
            main.images.setImage(image);
            count++;
        }
    }
}
