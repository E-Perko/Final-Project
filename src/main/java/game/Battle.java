package game;

public class Battle {
    private int pHealth = 40;
    private int eHealth = 40;
    private int turn = 0;

    public void startBattle() {

    }

    public void calcDamage() {
        turn++;
        if (turn % 2 == 0) {
            pHealth -= 5;
        } else {
            eHealth -= 5;
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
        pHealth = 40;
        eHealth = 40;
        turn = 0;
    }
}
