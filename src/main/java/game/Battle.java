package game;

public class Battle {
    GameData data = new GameData();

    private int turn = 1;
    private int[] pBattleStats = {5, 15, 15, 8, 8, 8, 8, 8};
    private int[] eBattleStats = {5, 15, 15, 8, 8, 8, 8, 8};

    public double hpAnimP = 0;
    public double hpAnimE = 0;
    public double critical = 1;
    // {Level, Current HP, Max HP, Attack, Defense, SAttack, SDefense, Speed}

    public void setBattleStats() {
        data.generateStats("Charmander", "Player");
        data.generateStats("Squirtle", "Player");
        pBattleStats = data.pStats;
        eBattleStats = data.eStats;
    }

    public void playBattleTurn() {
        if (turn % 2 == 0) {
            pBattleStats[1] -= calcDamage(pBattleStats, eBattleStats);
        } else {
            eBattleStats[1] -= calcDamage(eBattleStats, pBattleStats);
        }
        turn++;
    }

    public int calcDamage(int[] atkStats, int[] defStats) {
        double estimate = (((atkStats[0] * 2 / 5.0) + 2) * 40 * atkStats[3] / ((double) defStats[4]) / 50.0);
        critical = 1;
        if ((int) (Math.random() * 16) == 0) {
            critical = 2;
        }
        estimate *= critical * ((int) (Math.random() * 15) + 85) / 100.0;
        if (estimate > 1) {
            if (estimate % 1 <= 0.5) {
                return (int) estimate;
            } else {
                return (int) (estimate + 1);
            }
        } else {
            return 1;
        }
    }

    public int battleEnd() {
        if (hpAnimP <= 0) {
            return 2;
        } else if (hpAnimE <= 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getTurn() {
        return turn;
    }

    public int[] getPStats() {
        return pBattleStats;
    }

    public int[] getEStats() {
        return eBattleStats;
    }

    public void resetBattle() {
        for (int i = 0; i < data.pStats.length; i++) {
            pBattleStats[i] = data.pStats[i];
            eBattleStats[i] = data.eStats[i];
        }
        turn = 1;
    }
}