package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GraphicsManager {
    public static ImageView[] imgView = new ImageView[3];
    public static Image[] image = new Image[3];
    public static Pane[] pane = new Pane[3];

    public static void displayImg(String img, double width, double xPos, double yPos, int imgNum, StackPane root) {
        imgView[imgNum] = new ImageView();
        image[imgNum] = new Image("/" + img.toLowerCase() + ".png", GameMap.TILE * 30, 0, true, false);
        imgView[imgNum].setFitWidth(GameMap.TILE * width);
        imgView[imgNum].setPreserveRatio(true);
        imgView[imgNum].setX(GameMap.TILE * xPos);
        imgView[imgNum].setY(GameMap.TILE * yPos);
        imgView[imgNum].setImage(image[imgNum]);
        pane[imgNum] = new Pane(imgView[imgNum]);
        root.getChildren().addAll(pane[imgNum]);
    }

    public static void setImgX(int imgNum, double x) {
        imgView[imgNum].setX(x * GameMap.TILE);
    }

    public static void setImgY(int imgNum, double y) {
        imgView[imgNum].setY(y * GameMap.TILE);
    }

    public static void deleteImage(int imgNum) {
        imgView[imgNum].setImage(null);
    }
}
