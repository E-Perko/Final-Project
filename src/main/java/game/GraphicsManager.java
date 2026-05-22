package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GraphicsManager {
    ImageView[] imgView = new ImageView[2];
    Image[] image = new Image[2];
    Pane[] pane = new Pane[2];

    public void displayImg(String img, int width, int xPos, int yPos, int imgNum, StackPane root) {
        imgView[imgNum] = new ImageView();
        image[imgNum] = new Image("/" + img + ".png", GameMap.TILE * 7, GameMap.TILE * 7, true, false);
        imgView[imgNum].setFitWidth(GameMap.TILE * width);
        imgView[imgNum].setPreserveRatio(true);
        imgView[imgNum].setX(GameMap.TILE * xPos);
        imgView[imgNum].setY(GameMap.TILE * yPos);
        imgView[imgNum].setImage(image[imgNum]);
        pane[imgNum] = new Pane(imgView[imgNum]);
        root.getChildren().addAll(pane[imgNum]);
    }

    public void setImgX(int imgNum, int x) {
        imgView[imgNum].setX(x);
    }

    public void setImgY(int imgNum, int y) {
        imgView[imgNum].setY(y);
    }
}
