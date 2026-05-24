package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GameData {
    private int[] pStats = new int[8];
    private int[] eStats = new int[8];
    int[] battleStats = new int[8];
    int[] stats = new int[6];
    int count = 0;

    public void generateStats(String pokemon, String owner) {
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
            System.out.println();
        } else {
            for (int i = 0; i < 8; i++) {
                eStats[i] = battleStats[i];
            }
        }
    }

    public int[] getPStats() {
        return pStats;
    }

    public int[] getEStats() {
        return eStats;
    }
}
