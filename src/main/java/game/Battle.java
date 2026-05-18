package game;

public class Battle {
    private int pHealth = 15;
    private int eHealth = 15;
    private int turn = 0;

//    public void startBattle() {
//
//    }

    public void calcDamage() {
        turn++;
        if (turn % 2 == 0) {
            pHealth -= (int) (Math.random() * 2) + 1;
        } else {
            eHealth -= (int) (Math.random() * 2) + 1;
        }
    }

    public int battleEnd() {
        if (pHealth <= 0) {
            return 2;
        } else if (eHealth <= 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getPHealth() {
        return pHealth;
    }

    public int getEHealth() {
        return eHealth;
    }

    public int getTurn() {
        return turn;
    }

    public void resetBattle() {
        pHealth = 15;
        eHealth = 15;
        turn = 0;
    }
}
