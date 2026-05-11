package game;

import javafx.scene.paint.Color;

// Shy — runs away unless cornered (few open neighbours), then attacks.
public class Shy extends Ghost {

    public Shy(GameMap map) {
        super(map, GameMap.Tile.SPAWN_G2, 1.5);
    }

    @Override
    public String getName() { return "Shy"; } // give your ghost a name

    @Override
    protected int[] chooseTarget(Player player, GameMap map) {
        // TODO (Base): Implement Shy's personality.
        //
        // Shy has two modes:
        //   1. FLEE   — target the maze corner FARTHEST from the player
        //   2. ATTACK — target the player's exact tile (like Shadow)
        //
        // Shy switches from FLEE to ATTACK when it is "cornered":
        //   Count how many of Shy's four neighbours (up/down/left/right) are open
        //   (not walls).  If only ONE neighbour is open, Shy is cornered.
        //   int gc = col(map), gr = row(map);  // Shy's current tile coordinates
        //   int[][] dirs = {{0,-1},{-1,0},{1,0},{0,1}};
        //   use map.isWall(gc + d[0], gr + d[1]) to test each neighbour.
        //
        // To find the farthest corner from the player:
        //   int[][] corners = {{1,1},{map.cols-2,1},{1,map.rows-2},{map.cols-2,map.rows-2}};
        //   pick the one with the largest Math.hypot distance to (player.col(map), player.row(map)).
        //
        // When frightened, CHASE the player instead of fleeing.
        // Note: this is intentionally the OPPOSITE of every other ghost's frightened
        // behaviour — Shy is bold when cornered and bold when scared.
        //
        // How to verify: run the game and walk toward Shy — it should move away
        // from you. Trap it in a dead-end corridor and it should turn and chase.

        return new int[]{ player.col(map), player.row(map) }; // placeholder — replace this
    }

    // When chooseTarget() is working, add this ghost to the list in PelletPursuitDemo.java:
    //   new Shy(map)

    @Override
    protected Color getBodyColor() { return Color.web("#00ffff"); }
}
