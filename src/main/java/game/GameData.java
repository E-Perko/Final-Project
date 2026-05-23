package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GameData {
    public int[] pStats = {5, 15, 15, 8, 8, 8, 8, 8};
    public int[] eStats = {5, 15, 15, 8, 8, 8, 8, 8};

    public void generateStats(String pokemon, String owner) {
        try
        {
            File statsFile = new File("/data/stats.txt");
            Scanner statsScanner = new Scanner(statsFile);

            while (statsScanner.hasNext())
            {
                if (statsScanner.next().equals(pokemon)) {
                    int[] stats = new int[8];
                    stats[0] = 5;
                    //stats[1] = (int) ((2 * statsScanner.nextInt() + 15 + (int) (0 / 4)) / 100.0) + stats[0] + 10;
                    stats[1] = (int) ((2 * statsScanner.nextInt() + 15) * stats[0] / 100.0) + stats[0] + 10;
                    stats[2] = stats[1];
                    for (int i = 3; i < 8; i++) {
                        //stats[i] = (int) ((int) ((2 * statsScanner.nextInt() + 15 + (int) (0 / 4) / 100.0) + 5) * 1);
                        stats[i] = (int) ((int) (((2 * statsScanner.nextInt() + 15) * stats[0] / 100.0) + 5));
                    }
                    if (owner.equals("Player")) {
                        pStats = stats;
                    } else {
                        eStats = stats;
                    }
                }
            }
            statsScanner.close();
        }
        catch (IOException e)
        {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
