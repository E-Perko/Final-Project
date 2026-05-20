package game;

import javafx.scene.canvas.GraphicsContext;

public class GameInterface {
    PelletPursuitDemo main = new PelletPursuitDemo();

    private int battleState = 0;
    // 0: Initial Options
    // 1: Move Select
    // 2: Use Move
    // 2: Post Move Text

    public void renderBattle(GraphicsContext gc) {
        switch (battleState) {
            case 0 -> {
//                gc.fillArc((GameMap.TILE * 8) + (GameMap.TILE * main.selectX * 3.5), (GameMap.TILE * 7.75) + (GameMap.TILE * main.selectY), 10, 10, 0, 360, javafx.scene.shape.ArcType.ROUND);
//                gc.fillText("Fight", GameMap.TILE * 8.5, GameMap.TILE * 8);
//                gc.fillText("Bag", GameMap.TILE * 12, GameMap.TILE * 8);
//                gc.fillText("Pokémon", GameMap.TILE * 8.5, GameMap.TILE * 9);
//                gc.fillText("Run", GameMap.TILE * 12, GameMap.TILE * 9);
            }
            case 1 -> {

            }
            case 2 -> {

            }
            case 3 -> {

            }
            default -> {}
        }
    }
}
