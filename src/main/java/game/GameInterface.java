package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class GameInterface {
    public static int selectX = 0;
    public static int selectY = 0;

    public static String[][] battleMoves = new String[2][2];

    private static String playerMove;
    private static String opponentMove;

    public static int battleState = 0;
    // 0: Initial Options
    // 1: Move Select
    // 2: Selected Move
    // 3: Miss/Immobile Turn (Flinch, Paralysis, Frozen, Asleep)
    // 4: Type Effectiveness
    // 5: Lower Stats
    // 6: Critical Hit
    // 7: Inflicted With Status
    // 8: Damage Over TIme (Burn, Poison)
    // 9: Fainted

    public static final String[] statNames = {"Attack", "Defense", "Special Attack", "Special Defense", "Speed"};

    public static int missMessage = -1;

    public static String statusText;
    public static String statusText1;
    public static String useMoveText;

    public static GameData person;
    public static GameData opposingPerson;
    public static String pokemonText = "";
    public static String opposingPokemonText = "";
    public static String personMove;

    public static void renderBattle(GraphicsContext gc) {
        if (Battle.turn % 2 == Battle.turnOrder) {
            person = Battle.player;
            opposingPerson = Battle.opponent;
            pokemonText = person.pokemon[0];
            opposingPokemonText = "Foe " + opposingPerson.pokemon[0];
            personMove = playerMove;
        } else {
            person = Battle.opponent;
            opposingPerson = Battle.player;
            pokemonText = "Foe " + person.pokemon[0];
            opposingPokemonText = opposingPerson.pokemon[0];
            personMove = opponentMove;
        }
        gc.setFont(Font.font("Monospace", GameMap.TILE * 3 / 5.0));
        switch (battleState) {
            case 0 -> {
                gc.fillText("What will", GameMap.TILE, GameMap.TILE * 8);
                gc.fillText(Battle.player.pokemon[0] + " do?", GameMap.TILE, GameMap.TILE * 9);
                gc.fillArc((GameMap.TILE * 8) + (GameMap.TILE * selectX * 3.5), (GameMap.TILE * 7.75) + (GameMap.TILE * selectY), 10, 10, 0, 360, javafx.scene.shape.ArcType.ROUND);
                gc.fillText("Fight", GameMap.TILE * 8.5, GameMap.TILE * 8);
                gc.fillText("Bag", GameMap.TILE * 12, GameMap.TILE * 8);
                gc.fillText("Pokémon", GameMap.TILE * 8.5, GameMap.TILE * 9);
                gc.fillText("Run", GameMap.TILE * 12, GameMap.TILE * 9);
            }
            case 1 -> {
                gc.fillArc((GameMap.TILE / 2.0) + (GameMap.TILE * selectX * 5.5), (GameMap.TILE * 7.75) + (GameMap.TILE * selectY), 10, 10, 0, 360, javafx.scene.shape.ArcType.ROUND);
                gc.fillText(battleMoves[0][0], GameMap.TILE, GameMap.TILE * 8);
                gc.fillText(battleMoves[0][1], GameMap.TILE * 6.5, GameMap.TILE * 8);
                gc.fillText(battleMoves[1][0], GameMap.TILE, GameMap.TILE * 9);
                gc.fillText(battleMoves[1][1], GameMap.TILE * 6.5, GameMap.TILE * 9);
                gc.setFont(Font.font("Monospace", GameMap.TILE * 2 / 5.0));
                gc.fillText(Battle.player.moveType, GameMap.TILE * 11.5, GameMap.TILE * 7.5);
                gc.fillText(Battle.player.moveCategory, GameMap.TILE * 11.5, GameMap.TILE * 8);
                gc.fillText("PP: " + Battle.player.powerPoints + "/" + Battle.player.powerPoints, GameMap.TILE * 11.5, GameMap.TILE * 8.5);
                gc.fillText("Power: " + Battle.player.basePower, GameMap.TILE * 11.5, GameMap.TILE * 9);
                gc.fillText("Accuracy: " + Battle.player.moveAccuracy, GameMap.TILE * 11.5, GameMap.TILE * 9.5);
            }
            case 2 -> {
                setMoveUseText();
                gc.fillText(useMoveText, GameMap.TILE, GameMap.TILE * 8);
            }
            case 3 -> {
                switch (missMessage) {
                    case 0 -> {
                        gc.fillText(pokemonText + " is paralyzed!", GameMap.TILE, GameMap.TILE * 8);
                        gc.fillText("It can't move!", GameMap.TILE, GameMap.TILE * 9);
                    }
                    case 1 -> {
                        gc.fillText(pokemonText + " is frozen!", GameMap.TILE, GameMap.TILE * 8);
                        gc.fillText("It can't move!", GameMap.TILE, GameMap.TILE * 9);
                    }
                    case 2 -> {
                        gc.fillText(pokemonText + " is asleep!", GameMap.TILE, GameMap.TILE * 8);
                    }
                    case 3 -> {
                        gc.fillText(pokemonText + " flinched!", GameMap.TILE, GameMap.TILE * 8);
                    }
                    case 4 -> {
                        gc.fillText(opposingPokemonText + " avoided the attack!", GameMap.TILE, GameMap.TILE * 8);
                    }
                }
            }
            case 4 -> {
                if (Battle.typeEffect > 1) {
                    gc.fillText("It's super effective!", GameMap.TILE, GameMap.TILE * 8);
                } else if (Battle.typeEffect == 0) {
                    gc.fillText("It doesn't affect " + opposingPerson.pokemon[0] + "...", GameMap.TILE, GameMap.TILE * 8);
                } else {
                    gc.fillText("It's not very effective...", GameMap.TILE, GameMap.TILE * 8);
                }
            }
            case 5 -> {
                String pokemonStatText;
                if (Battle.statPlayer.equals("Player")) {
                    pokemonStatText = Battle.player.pokemon[0];
                } else {
                    pokemonStatText = "Foe " + Battle.opponent.pokemon[0];
                }
                String statMessage = "";
                switch (Battle.statsChanged[Battle.statChanged]) {
                    case 1 -> statMessage = "rose";
                    case 2 -> statMessage = "sharply rose";
                    case -1 -> statMessage = "fell";
                    case -2 -> statMessage = "harshly fell";
                }
                if (Battle.statsChanged[Battle.statChanged] >= 3) {
                    statMessage = "drastically rose";
                }
                if (Battle.statsChanged[Battle.statChanged] <= -3) {
                    statMessage = "severely fell";
                }
                if (Math.abs(Battle.statsChanged[Battle.statChanged]) > 1 && (Battle.statChanged == 5 || Battle.statChanged == 6)) {
                    gc.fillText(pokemonStatText + "'s " + statNames[Battle.statChanged - 3], GameMap.TILE, GameMap.TILE * 8);
                    gc.fillText(statMessage + "!", GameMap.TILE, GameMap.TILE * 9);
                } else {
                    gc.fillText(pokemonStatText + "'s " + statNames[Battle.statChanged - 3] + " " + statMessage + "!", GameMap.TILE, GameMap.TILE * 8);
                }
            }
            case 6 -> {
                gc.fillText("A critical hit!", GameMap.TILE, GameMap.TILE * 8);
            }
            case 7 -> {
                gc.fillText(statusText, GameMap.TILE, GameMap.TILE * 8);
                gc.fillText(statusText1, GameMap.TILE, GameMap.TILE * 9);
            }
            case 8 -> {

            }
            case 9 -> {
                gc.fillText(opposingPokemonText + " fainted!", GameMap.TILE, GameMap.TILE * 8);
            }
            default -> {}
        }
    }

    public static boolean verifyMoveSlot(int x, int y) {
        return (battleState == 0 || !battleMoves[selectY + y][selectX + x].equals("------"));
    }

    public static void getPlayerMoves() {
        battleMoves[0][0] = Battle.player.moves[0][0];
        battleMoves[0][1] = Battle.player.moves[0][1];
        battleMoves[1][0] = Battle.player.moves[0][2];
        battleMoves[1][1] = Battle.player.moves[0][3];
    }

    public static String getPlayerMove() {
        return playerMove;
    }

    public static void setPlayerMove() {
        playerMove = battleMoves[selectY][selectX];
    }

    public static void setOpponentMove(String move) {
        opponentMove = move;
    }

    public static void setMoveUseText() {
        useMoveText = pokemonText + " used " + personMove + "!";
    }
}