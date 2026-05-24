package game;

public class Battle {
    GameData data = new GameData();

    private int turn = 1;
    private int[] pBattleStats = new int[8];
    private int[] eBattleStats = new int[8];

    public double hpAnimP = 0;
    public double hpAnimE = 0;
    public double critical = 1;
    // {Level, Current HP, Max HP, Attack, Defense, SAttack, SDefense, Speed}

    public void setBattleStats() {
        data.generateStats("Charmander", "Player");
        pBattleStats = data.getPStats();
        data.generateStats("Squirtle", "Opponent");
        eBattleStats = data.getEStats();
        hpAnimP = pBattleStats[1];
        hpAnimE = eBattleStats[1];
    }

    public void playBattleTurn() {
        if (turn % 2 == 0) {
            pBattleStats[1] -= calcDamage(eBattleStats, pBattleStats);
        } else {
            eBattleStats[1] -= calcDamage(pBattleStats, eBattleStats);
        }
        turn++;
    }

    public int calcDamage(int[] atkStats, int[] defStats) {
        double estimate = (((atkStats[0] * 2 / 5.0 + 2) * (40 * atkStats[3]) / (((double) defStats[4]) * 50.0))) + 2;
        critical = 1;
        if ((int) (Math.random() * 16) == 0) {
            critical = 1.5;
        }
        estimate *= critical * ((int) (Math.random() * 16) + 85) / 100.0;
        if (estimate < 1) {
            return 1;
        } else {
            return (int) estimate;
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
        pBattleStats = data.getPStats();
        eBattleStats = data.getEStats();
        turn = 1;
    }
}