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

    private static String[][] pMoves = {{"Scratch", "Growl", "------", "------"}};
    private static String[][] eMoves = new String[6][4];

    private static String[] pPokemon = {"Charmander", null, null, null, null, null};
    private static String[] ePokemon = new String[6];

    private static int[] pLvls = {5, 0, 0, 0, 0, 0};
    private static int[] eLvls = new int[6];

    public static void generateStats(String pokemon, String owner) {
        try
        {
            File statsFile = new File("src/main/resources/data/stats.txt");
            Scanner statsScanner = new Scanner(statsFile);

            while (statsScanner.hasNext())
            {
                if (statsScanner.nextLine().equals(pokemon)) {
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
        battleStats[0] = 5;
        battleStats[1] = (int) ((stats[0] * 2 + 15) * battleStats[0] / 100.0) + battleStats[0] + 10;
        battleStats[2] = battleStats[1];
        for (int i = 3; i < 8; i++) {
            battleStats[i] = (int) ((stats[i - 2] * 2 + 15) * battleStats[0] / 100.0) + 5;
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

    public static void generateOpponent(String trainer) {
        try
        {
            File teamsFile = new File("src/main/resources/data/teams.txt");
            Scanner teamsScanner = new Scanner(teamsFile);

            while (teamsScanner.hasNext())
            {
                if (teamsScanner.nextLine().equals(trainer)) {
                    for (int i = 0; i < 4; i++) {
                        String a = teamsScanner.nextLine();
                    }
                    int pokenum = teamsScanner.nextInt();
                    String a = teamsScanner.nextLine();
                    a = teamsScanner.nextLine();
                    for (int i = 0; i < pokenum; i++) {
                        ePokemon[i] = teamsScanner.nextLine();
                        eLvls[i] = teamsScanner.nextInt();
                        a = teamsScanner.nextLine();
                        a = teamsScanner.nextLine();
                        for (int j = 0; j < 4; j++) {
                            eMoves[i][j] = teamsScanner.nextLine();
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

    public static String getEPokemon(int slot) {
        return ePokemon[slot];
    }

    public static int getELvls(int slot) {
        return eLvls[slot];
    }
}
