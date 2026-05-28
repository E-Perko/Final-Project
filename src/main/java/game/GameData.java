package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GameData {
    private static int[] battleStats = new int[8];
    private static int[] stats = new int[6];

    private static int[] pStats = new int[8];
    private static int[] eStats = new int[8];

    private static String[][] pMoves = new String[6][4];
    private static String[][] eMoves = new String[6][4];

    private static String[] pPokemon = new String[6];
    private static String[] ePokemon = new String[6];

    private static int[] pLvls = new int[6];
    private static int[] eLvls = new int[6];

    private static String[][] pTypes = new String[2][6];
    private static String[][] eTypes = new String[2][6];

    public static String moveEffect;
    public static int basePower;
    public static String moveType;
    public static int moveAccuracy;
    public static int powerPoints;
    public static String moveCategory;

    public static void generateStats(String pokemon, String owner) {
        try
        {
            File statsFile = new File("src/main/resources/data/stats.txt");
            Scanner statsScanner = new Scanner(statsFile);

            while (statsScanner.hasNext())
            {
                if (statsScanner.nextLine().equals(pokemon)) {
                    if (owner.equals("Player")) {
                        pTypes[0][0] = statsScanner.nextLine();
                        pTypes[0][1] = statsScanner.nextLine();
                    } else {
                        eTypes[0][0] = statsScanner.nextLine();
                        eTypes[0][1] = statsScanner.nextLine();
                    }
                    for (int i = 0; i < 6; i++) {
                        stats[i] = statsScanner.nextInt();
                    }
                    break;
                }
            }
            statsScanner.close();
        }
        catch (IOException e)
        {
            System.out.println("File not found: " + e.getMessage());
        }
        if (owner.equals("Player")) {
            battleStats[0] = pLvls[0];
        } else {
            battleStats[0] = eLvls[0];
        }
        battleStats[1] = (int) ((stats[0] * 2 + 31) * battleStats[0] / 100.0) + battleStats[0] + 10;
        battleStats[2] = battleStats[1];
        for (int i = 3; i < 8; i++) {
            battleStats[i] = (int) ((stats[i - 2] * 2 + 31) * battleStats[0] / 100.0) + 5;
        }
        if (owner.equals("Player")) {
            for (int i = 0; i < 8; i++) {
                pStats[i] = battleStats[i];
            }
        } else {
            for (int i = 0; i < 8; i++) {
                eStats[i] = battleStats[i];
            }
        }
    }

    public static int[] getPStats() {
        return pStats;
    }

    public static int[] getEStats() {
        return eStats;
    }

    public static void generateTeam(String battle, String person) {
        try
        {
            File teamsFile = new File("src/main/resources/data/" + person + "Teams.txt");
            Scanner teamsScanner = new Scanner(teamsFile);

            while (teamsScanner.hasNext())
            {
                if (teamsScanner.nextLine().equals(battle)) {
                    if (person.equals("opponent")) {
                        for (int i = 0; i < 4; i++) {
                            String a = teamsScanner.nextLine();
                        }
                    }
                    int pokenum = teamsScanner.nextInt();
                    String a = teamsScanner.nextLine();
                    a = teamsScanner.nextLine();
                    for (int i = 0; i < pokenum; i++) {
                        if (person.equals("player")) {
                            pPokemon[i] = teamsScanner.nextLine();
                            pLvls[i] = teamsScanner.nextInt();
                            a = teamsScanner.nextLine();
                            a = teamsScanner.nextLine();
                            for (int j = 0; j < 4; j++) {
                                pMoves[i][j] = teamsScanner.nextLine();
                            }
                        } else {
                            ePokemon[i] = teamsScanner.nextLine();
                            eLvls[i] = teamsScanner.nextInt();
                            a = teamsScanner.nextLine();
                            a = teamsScanner.nextLine();
                            for (int j = 0; j < 4; j++) {
                                eMoves[i][j] = teamsScanner.nextLine();
                            }
                        }
                    }
                    break;
                }
            }
            teamsScanner.close();
        }
        catch (IOException e)
        {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static String[] getPMoves(int slot) {
        return pMoves[slot];
    }
    public static String[] getEMoves(int slot) {
        return eMoves[slot];
    }

    public static String getPPokemon(int slot) {
        return pPokemon[slot];
    }
    public static String getEPokemon(int slot) {
        return ePokemon[slot];
    }

    public static int getPLvls(int slot) {
        return pLvls[slot];
    }
    public static int getELvls(int slot) {
        return eLvls[slot];
    }

    public static void getMoveInfo(String move) {
        try {
            File teamsFile = new File("src/main/resources/data/moves.txt");
            Scanner movesScanner = new Scanner(teamsFile);

            while (movesScanner.hasNext()) {
                if (movesScanner.nextLine().equals(move)) {
                    String a = movesScanner.nextLine();
                    moveEffect = movesScanner.nextLine();
                    basePower = movesScanner.nextInt();
                    a = movesScanner.nextLine();
                    moveType = movesScanner.nextLine();
                    moveAccuracy = movesScanner.nextInt();
                    powerPoints = movesScanner.nextInt();
                    a = movesScanner.nextLine();
                    a = movesScanner.nextLine();
                    moveCategory = movesScanner.nextLine();
                    break;
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static void computeEffect(String trainer) {
        switch (moveEffect) {
            case "EFFECT_ATTACK_DOWN" -> {
                Battle.setStatChanges(trainer, 3, -1);
            }
            case "EFFECT_DEFENSE_DOWN" -> {
                Battle.setStatChanges(trainer, 4, -1);
            }
            default -> {}
        }
    }
}
