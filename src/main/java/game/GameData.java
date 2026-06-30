package game;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GameData {
    public int[] battleStats = new int[8];
    public int[] stats = new int[6];
    public String[][] moves = new String[6][4];
    public String[] pokemon = new String[6];
    public int[] levels = new int[6];
    public String[][] types = new String[2][6];
    public String[] statuses = {"None", "None", "None", "None", "None", "None"};
    //Burned, Paralyzed, Frozen, Asleep, Poisoned, Badly Poisoned

    public String moveEffect;
    public int effectChance;
    public int basePower;
    public String moveType;
    public int moveAccuracy;
    public int powerPoints;
    public int movePriority;
    public String moveCategory;

    public String[] moveInfo = {"Description", "Effect", "Effect Chance", "Base Power", "Type", "Accuracy", "Power Points", "Priority", "Category", "Sub Category", "Contact"};

    public static boolean changedStats = false;
    public static boolean status = false;
    public double para = 1;
    public double burn = 1;

    public int[] moveEffectTurns = new int[1];
    //Freeze/Sleep/Toxic Poison

    public void generateStats(String pokemon) {
        try
        {
            File statsFile = new File("src/main/resources/data/stats.txt");
            Scanner statsScanner = new Scanner(statsFile);

            while (statsScanner.hasNext())
            {
                if (statsScanner.nextLine().equals(pokemon)) {
                    types[0][0] = statsScanner.nextLine();
                    types[1][0] = statsScanner.nextLine();
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
        battleStats[0] = levels[0];
        battleStats[1] = (int) ((stats[0] * 2 + 31) * battleStats[0] / 100.0) + battleStats[0] + 10;
        battleStats[2] = battleStats[1];
        for (int i = 3; i < 8; i++) {
            battleStats[i] = (int) ((stats[i - 2] * 2 + 31) * battleStats[0] / 100.0) + 5;
        }
    }

    public void generateTeam(String battle, String person) {
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
                        pokemon[i] = teamsScanner.nextLine();
                        levels[i] = teamsScanner.nextInt();
                        a = teamsScanner.nextLine();
                        a = teamsScanner.nextLine();
                        for (int j = 0; j < 4; j++) {
                            moves[i][j] = teamsScanner.nextLine();
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

    public void getMoveInfo(String move) {
        try {
            File teamsFile = new File("src/main/resources/data/moves.txt");
            Scanner movesScanner = new Scanner(teamsFile);

            while (movesScanner.hasNext()) {
                if (movesScanner.nextLine().equals(move)) {
                    String a = movesScanner.nextLine();
                    moveEffect = movesScanner.nextLine();
                    effectChance = movesScanner.nextInt();
                    basePower = movesScanner.nextInt();
                    a = movesScanner.nextLine();
                    moveType = movesScanner.nextLine();
                    moveAccuracy = movesScanner.nextInt();
                    powerPoints = movesScanner.nextInt();
                    movePriority = movesScanner.nextInt();
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
        String opponent;
        if (trainer.equals("Player")) {
            opponent = "Opponent";
        } else {
            opponent = "Player";
        }
        if ((int) (Math.random() * 100) < Battle.effectChance) {
            switch (Battle.moveEffect) {
                case "EFFECT_ATTACK_DOWN" -> Battle.setStatChanges(opponent, 3, -1);
                case "EFFECT_SPATTACK_DOWN" -> Battle.setStatChanges(opponent, 5, -1);
                case "EFFECT_DEFENSE_DOWN" -> Battle.setStatChanges(opponent, 4, -1);
                case "EFFECT_SPDEFENSE_DOWN" -> Battle.setStatChanges(opponent, 6, -1);
                case "EFFECT_SPEED_DOWN" -> Battle.setStatChanges(opponent, 7, -1);

                case "EFFECT_SELF_ATTACK_UP" -> Battle.setStatChanges(trainer, 3, 1);
                case "EFFECT_SELF_SPEED_UP" -> Battle.setStatChanges(trainer, 7, 1);
                case "EFFECT_SWORDS_DANCE" -> Battle.setStatChanges(trainer, 3, 2);

                case "EFFECT_CLOSE_COMBAT" -> {
                    Battle.setStatChanges(trainer, 4, -1);
                    Battle.setStatChanges(trainer, 6, -1);
                }
                case "EFFECT_DRACO_METEOR" -> Battle.setStatChanges(trainer, 5, -2);

                case "EFFECT_BURN" -> Battle.inflictStatus(opponent, "Burned");
                case "EFFECT_PARALYZE" -> Battle.inflictStatus(opponent, "Paralyzed");
                case "EFFECT_FREEZE" -> Battle.inflictStatus(opponent, "Frozen");
                case "EFFECT_SLEEP" -> Battle.inflictStatus(opponent, "Asleep");
                case "EFFECT_POISON" -> Battle.inflictStatus(opponent, "Poisoned");

                case "EFFECT_FLINCH" -> {
                    if (Battle.turn % 2 == 1) {
                        Battle.missCases[3] = true;
                    }
                }

                case "EFFECT_HEAL_HALF_MAX" -> Battle.heal(trainer);

                default -> {}
            }
        }
    }
}
