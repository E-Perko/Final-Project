package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GraphicsManager {
    PelletPursuitDemo main = new PelletPursuitDemo();
    Image image;
    int count = 0;

    public void displayImg(String img, int scale, int xPos, int yPos) {
        if (count == 0) {
            image = new Image("/" + img + ".png", 10000.0, 10000.0, true, false);
            main.images1.setFitWidth(GameMap.TILE * scale);
            main.images1.setPreserveRatio(true);
            main.images1.setX(GameMap.TILE * xPos);
            main.images1.setY(GameMap.TILE * yPos);
            main.images1.setImage(image);
            count++;
        }
    }
}
