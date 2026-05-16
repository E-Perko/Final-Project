package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GraphicsManager {
    public void draw(GraphicsContext gc) {
        Image image = new Image("file:/graphics/charmander_back.png");
        gc.drawImage(image, GameMap.TILE * 5, GameMap.TILE * 5, 64, 64);
    }
}
