package game;

public class Battle {

    private static int turn = 1;
    private static int turnOrder = 2;
    public static String currentPlayer;
    // 0: Player Moves First
    // 1: Opponent Moves First

    private int[] pBattleStats = new int[8];
    private int[] eBattleStats = new int[8];
    // {Level, Current HP, Max HP, Attack, Defense, SAttack, SDefense, Speed}

    private int[] pStatMods = new int[7];
    private int[] eStatMods = new int[7];
    public final double[] statModList = {2/8.0, 2/7.0, 2/6.0, 2/5.0, 2/4.0, 2/3.0, 2/2.0, 3/2.0, 4/2.0, 5/2.0, 6/2.0, 7/2.0, 8/2.0};
    // {Attack, Defense, SAttack, SDefense, Speed, Accuracy, Evasion} Each stat ranges from -6 to 6

    public double hpAnimP = 0;
    public double hpAnimE = 0;
    // Used to animate HP bars

    public double critical = 1;
    // The standard crit rate is 1/16, multiplying damage by 1.5

    public static final String[] types = {"None", "Normal", "Fire", "Water", "Electric", "Grass", "Ice", "Fighting", "Poison", "Ground", "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"};
    public static final double[][] typeChart = {
            {1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1  },
            {1,  1,  1,  1,  1,  1,  2,  1,  1,  1,  1,  1,  1,  0,  1,  1,  1,  1  },
            {1,  0.5,2,  1,  0.5,0.5,1,  1,  2,  1,  1,  0.5,2,  1,  1,  1,  0.5,0.5},
            {1,  0.5,0.5,2,  2,  0.5,1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  0.5,1  },
            {1,  1,  1,  0.5,1,  1,  1,  1,  2,  0.5,1,  1,  1,  1,  1,  1,  0.5,1  },
            {1,  2,  0.5,0.5,0.5,2,  1,  2,  0.5,2,  1,  2,  1,  1,  1,  1,  1,  1  },
            {1,  2,  1,  1,  1,  0.5,2,  1,  1,  1,  1,  1,  2,  1,  1,  1,  2,  1  },
            {1,  1,  1,  1,  1,  1,  1,  1,  1,  2,  2,  0.5,0.5,1,  1,  0.5,1,  2  },
            {1,  1,  1,  1,  0.5,1,  0.5,0.5,2,  1,  2,  0.5,1,  1,  1,  1,  1,  0.5},
            {1,  1,  2,  0,  2,  2,  1,  0.5,1,  1,  1,  1,  0.5,1,  1,  1,  1,  1  },
            {1,  1,  1,  2,  0.5,2,  0.5,1,  0,  1,  1,  0.5,2,  1,  1,  1,  1,  1  },
            {1,  1,  1,  1,  1,  1,  0.5,1,  1,  1,  0.5,2,  1,  2,  1,  2,  1,  1  },
            {1,  2,  1,  1,  0.5,1,  0.5,1,  0.5,2,  1,  1,  2,  1,  1,  1,  1,  1  },
            {0.5,0.5,2,  1,  2,  1,  2,  0.5,2,  0.5,1,  1,  1,  1,  1,  1,  2,  1  },
            {0,  1,  1,  1,  1,  1,  0,  0.5,1,  1,  1,  0.5,1,  2,  1,  2,  1,  1  },
            {1,  0.5,0.5,0.5,0.5,2,  1,  1,  1,  1,  1,  1,  1,  1,  2,  1,  1,  2  },
            {1,  1,  1,  1,  1,  1,  2,  1,  1,  1,  0,  2,  0,  0.5,1,  0.5,1,  2  },
            {0.5,2,  1,  1,  0.5,0.5,2,  0,  2,  0.5,0.5,0.5,0.5,1,  0.5,1,  0.5,0.5},
            {1,  1,  1,  1,  1,  1,  0.5,2,  1,  1,  1,  0.5,1,  1,  0,  0.5,2,  1  }
    };
    //None, Normal, Fire, Water, Electric, Grass, Ice, Fighting, Poison, Ground, Flying, Psychic, Bug, Rock, Ghost, Dragon, Dark, Steel, Fairy
    //Columns represent defensive interactions and rows represent offensive interactions.
    //2 represents weaknesses/super effective hits, 0.5 represents resistances/resisted hits, 1 represents neutral hits, and 0 represents immunities.

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
        } else if (turn % 2 == 1) {
            turnOrder = (int)(Math.random() * 2);
        }
        executeMove();
    }

    public int calcDamage(int[] atkStats, int[] defStats) {
        int physSpec;
        //if (GameData.moveCategory != null) {
        if (GameData.moveCategory.equals("Physical")) {
           physSpec = 0;
        } else {
            physSpec = 2;
        }
        critical = 1;
        if ((int) (Math.random() * 16) == 0) {
            critical = 1.5;
        }
        double estimate;
        int atkMod;
        int defMod;
        if (turn % 2 == turnOrder) {
            atkMod = pStatMods[3 + physSpec];
            defMod = pStatMods[4 + physSpec];
        } else {
            atkMod = eStatMods[3 + physSpec];
            defMod = eStatMods[4 + physSpec];
        }
        if (critical == 1) {
            estimate = (((atkStats[0] * 2 / 5.0 + 2) * (GameData.basePower * atkStats[3 + physSpec] * statModList[atkMod + 6]) / (((double) defStats[4 + physSpec]) * statModList[defMod + 6] * 50.0))) + 2;
        } else {
            estimate = (((atkStats[0] * 2 / 5.0 + 2) * (GameData.basePower * atkStats[3 + physSpec] * statModList[Math.max(atkMod, 0) + 6]) / (((double) defStats[4 + physSpec]) * statModList[Math.min(defMod, 0) + 6] * 50.0))) + 2;
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

    public void setPStatMods(int stat, int change) {
        pStatMods[stat] = Math.clamp(pStatMods[stat] + change,-6, 6);
    }

    public void setEStatMods(int stat, int change) {
        pStatMods[stat] = Math.clamp(eStatMods[stat] + change, -6, 6);
    }

    public void resetBattle() {
        pBattleStats = GameData.getPStats();
        eBattleStats = GameData.getEStats();
        for (int i = 0; i < pStatMods.length; i++) {
            pStatMods[i] = 0;
            eStatMods[i] = 0;
        }
        turn = 1;
    }

    public void executeMove() {
        if (turn % 2 == turnOrder) {
            GameData.getMoveInfo("Tackle");
            if (GameData.moveCategory.equals("Status")) {
                setPStatMods(3, -1);
            } else {
                pBattleStats[1] -= calcDamage(eBattleStats, pBattleStats);
            }
        } else {
            GameData.getMoveInfo(GameInterface.getPlayerMove());
            if (GameData.moveCategory.equals("Status")) {
                setEStatMods(3, -1);
            } else {
                eBattleStats[1] -= calcDamage(pBattleStats, eBattleStats);
            }
        }
        turn++;
    }
}