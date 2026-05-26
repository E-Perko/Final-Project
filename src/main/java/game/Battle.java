package game;

public class Battle {

    private static int turn = 1;
    private static int turnOrder = 2;
    // 0: Player Moves First
    // 1: Opponent Moves First
    // 2: Speed Tie

    private int[] pBattleStats = new int[8];
    private int[] eBattleStats = new int[8];
    // {Level, Current HP, Max HP, Attack, Defense, SAttack, SDefense, Speed}

    private int[] pStatMods = new int[7];
    private int[] eStatMods = new int[7];
    // {Attack, Defense, SAttack, SDefense, Speed, Accuracy, Evasion} Each stat ranges from -6 to 6

    public double hpAnimP = 0;
    public double hpAnimE = 0;
    // Used to animate HP bars

    public double critical = 1;
    // The standard crit rate is 1/16, multiplying damage by 1.5

    public void setBattleStats() {
        GameData.generateStats(GameData.getPPokemon(0), "Player");
        pBattleStats = GameData.getPStats();
        GameData.generateStats(GameData.getEPokemon(0), "Opponent");
        eBattleStats = GameData.getEStats();
        hpAnimP = pBattleStats[1];
        hpAnimE = eBattleStats[1];
    }

    public void playBattleTurn() {
        if (pBattleStats[7] > eBattleStats[7]) {
            turnOrder = 0;
        } else if (pBattleStats[7] < eBattleStats[7]) {
            turnOrder = 1;
        } else {
            turnOrder = 1;
        }
        if (turn % 2 == turnOrder) {
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

    public int getTurnOrder() {
        return turnOrder;
    }

    public int[] getPStats() {
        return pBattleStats;
    }

    public int[] getEStats() {
        return eBattleStats;
    }

    public void setPBattleStats(int[] stats) {
        pBattleStats = stats;
    }

    public void setEBattleStats(int[] stats) {
        eBattleStats = stats;
    }

    public void resetBattle() {
        pBattleStats = GameData.getPStats();
        eBattleStats = GameData.getEStats();
        turn = 1;
    }
}