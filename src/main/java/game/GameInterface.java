package game;

import javafx.scene.canvas.GraphicsContext;

public class GameInterface {
    Battle battle = new Battle();

    public int selectX = 0;
    public int selectY = 0;

    public String[][] battleMoves = {{"Scratch", "Growl"}, {"------", "------"}};
    public String[][] playerMoves = new String[6][4];
    public String[][] opponentMoves = new String[6][4];

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
                gc.fillText(battleMoves[0][0], GameMap.TILE * 1.5, GameMap.TILE * 8);
                gc.fillText(battleMoves[0][1], GameMap.TILE * 6.5, GameMap.TILE * 8);
                gc.fillText(battleMoves[1][0], GameMap.TILE * 1.5, GameMap.TILE * 9);
                gc.fillText(battleMoves[1][1], GameMap.TILE * 6.5, GameMap.TILE * 9);
            }
            case 2 -> {
                gc.fillText("Charmander used Scratch!", GameMap.TILE, GameMap.TILE * 8);
            }
            case 3 -> {
                gc.fillText("Foe Squirtle used Tackle!", GameMap.TILE, GameMap.TILE * 8);
            }
            case 4 -> {
                gc.fillText("A critical hit!", GameMap.TILE, GameMap.TILE * 8);
            }
            default -> {}
            // 0: Battle Options
            // 1: Move Options
            // 2: Attack Text
            // 3: Critical Hit Text
        }
    }

    public boolean verifyMoveSlot(int x, int y) {
        return (battleState == 0 || !battleMoves[selectY + y][selectX + x].equals("------"));
    }
}