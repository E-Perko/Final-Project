package game;

import javafx.scene.canvas.GraphicsContext;

public class GameInterface {

    public int selectX = 0;
    public int selectY = 0;

    public int[][] battleMoves = {{1, 1}, {0, 0}};

    public int battleState = 0;
    // 0: Initial Options
    // 1: Move Select
    // 2: Use Move
    // 2: Post Move Text

    public void renderBattle(GraphicsContext gc) {
        switch (battleState) {
            case 0 -> {
                gc.fillText("What will", GameMap.TILE, GameMap.TILE * 8);
                gc.fillText("Charmander do?", GameMap.TILE, GameMap.TILE * 9);
                gc.fillArc((GameMap.TILE * 8) + (GameMap.TILE * selectX * 3.5), (GameMap.TILE * 7.75) + (GameMap.TILE * selectY), 10, 10, 0, 360, javafx.scene.shape.ArcType.ROUND);
                gc.fillText("Fight", GameMap.TILE * 8.5, GameMap.TILE * 8);
                gc.fillText("Bag", GameMap.TILE * 12, GameMap.TILE * 8);
                gc.fillText("Pokémon", GameMap.TILE * 8.5, GameMap.TILE * 9);
                gc.fillText("Run", GameMap.TILE * 12, GameMap.TILE * 9);
            }
            case 1 -> {
                gc.fillArc((GameMap.TILE) + (GameMap.TILE * selectX * 5), (GameMap.TILE * 7.75) + (GameMap.TILE * selectY), 10, 10, 0, 360, javafx.scene.shape.ArcType.ROUND);
                gc.fillText("Scratch", GameMap.TILE * 1.5, GameMap.TILE * 8);
                gc.fillText("Growl", GameMap.TILE * 6.5, GameMap.TILE * 8);
                gc.fillText("------", GameMap.TILE * 1.5, GameMap.TILE * 9);
                gc.fillText("------", GameMap.TILE * 6.5, GameMap.TILE * 9);
            }
            case 2 -> {

            }
            case 3 -> {

            }
            default -> {}
        }
    }
}
