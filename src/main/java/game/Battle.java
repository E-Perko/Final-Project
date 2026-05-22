package game;

public class Battle {
    private int turn = 0;
    public int[] pStats = {5, 15, 15, 8, 8, 8, 8, 8};
    private int[] eStats = {5, 15, 15, 8, 8, 8, 8, 8};
    // {Level, Current HP, Max HP, Attack, Defense, SAttack, SDefense, Speed}

//    public void startBattle() {
//
//    }

    public void playTurn() {
        turn++;
        if (turn % 2 == 0) {
            pStats[1] -= calcDamage(pStats, eStats);
        } else {
            eStats[1] -= calcDamage(eStats, pStats);
        }
    }

    public int calcDamage(int[] atkStats, int[] defStats) {
        double estimate = (((atkStats[0] * 2 / 5.0) + 2) * 40 * atkStats[3] / ((double) defStats[4]) / 50.0);
        double critical = 1;
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
        if (pStats[1] <= 0) {
            return 2;
        } else if (eStats[1] <= 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getTurn() {
        return turn;
    }

    public int[] getPStats() {
        return pStats;
    }

    public int[] getEStats() {
        return eStats;
    }

    public void resetBattle() {
        pStats[1] = 15;
        pStats[2] = 15;
        eStats[1] = 15;
        eStats[2] = 15;
        turn = 0;
    }
}
