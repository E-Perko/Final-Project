package game;

import javafx.scene.canvas.GraphicsContext;

public class GameInterface {
    Battle battle = new Battle();

    public int selectX = 0;
    public int selectY = 0;

    public String[][] battleMoves = new String[2][2];

    private String playerMove;

    public int battleState = 0;
    // 0: Initial Options
    // 1: Move Select
    // 2: Selected Move
    // 3: Critical Hit
    // 4: Other Post Battle Text

    public void renderBattle(GraphicsContext gc) {
        switch (battleState) {
            case 0 -> {
                gc.fillText("What will", GameMap.TILE, GameMap.TILE * 8);
                gc.fillText(GameData.getPPokemon(0) + " do?", GameMap.TILE, GameMap.TILE * 9);
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
                if (battle.getTurn() % 2 == battle.getTurnOrder()) {
                    gc.fillText( GameData.getPPokemon(0) + " used " + playerMove + "!", GameMap.TILE, GameMap.TILE * 8);
                } else {
                    gc.fillText("Foe " + GameData.getEPokemon(0) + " used " + GameData.getEMoves(0)[0] + "!", GameMap.TILE, GameMap.TILE * 8);
                }
            }
            case 3 -> {
                gc.fillText("A critical hit!", GameMap.TILE, GameMap.TILE * 8);
            }
            case 4 -> {

            }
            default -> {}
        }
    }

    public boolean verifyMoveSlot(int x, int y) {
        return (battleState == 0 || !battleMoves[selectY + y][selectX + x].equals("------"));
    }

    public void getPlayerMoves() {
        battleMoves[0][0] = GameData.getPMoves(0)[0];
        battleMoves[0][1] = GameData.getPMoves(0)[1];
        battleMoves[1][0] = GameData.getPMoves(0)[2];
        battleMoves[1][1] = GameData.getPMoves(0)[3];
    }

    public void setPlayerMove() {
        playerMove = battleMoves[selectY][selectX];
    }
}