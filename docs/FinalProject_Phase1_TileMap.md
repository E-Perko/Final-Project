# JavaFX Game Tile Map with 2D Arrays

## Goal
Teach students how to use a 2D array to represent a level layout and draw a tile-based map.

## Why use a 2D array?
A 2D array is a simple way to store tiles in rows and columns.
Each number in the array can represent a different tile type:

> **Reference:** [W3Schools — Java Multidimensional Arrays](https://www.w3schools.com/java/java_arrays_multi.asp)
- `0` = floor / walkable
- `1` = wall / blocked
- `2` = goal or collectible

## Example tile map
```java
static final int TILE_SIZE = 50;
static final int COLS = 16;
static final int ROWS = 12;

int[][] map = {
    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
    {1,0,0,0,0,0,0,0,1,0,0,0,0,0,2,1},
    {1,0,1,1,0,1,1,0,1,0,1,1,1,0,0,1},
    {1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1},
    {1,1,0,1,0,1,1,1,1,1,1,0,1,0,0,1},
    {1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,1},
    {1,0,1,1,1,1,1,0,1,0,1,0,1,1,0,1},
    {1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
    {1,1,1,1,1,0,1,1,1,0,1,1,1,1,0,1},
    {1,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1},
    {1,0,1,0,0,0,1,1,1,1,1,1,0,0,0,1},
    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
};
```

## Drawing tiles
Use nested loops to draw every tile in the grid.

```java
for (int row = 0; row < ROWS; row++) {
    for (int col = 0; col < COLS; col++) {
        int tile = map[row][col];
        double x = col * TILE_SIZE;
        double y = row * TILE_SIZE;

        if (tile == 1) {
            gc.setFill(Color.GRAY);
        } else if (tile == 2) {
            gc.setFill(Color.GOLD);
        } else {
            gc.setFill(Color.DARKGREEN);
        }
        gc.fillRect(x, y, TILE_SIZE, TILE_SIZE);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, TILE_SIZE, TILE_SIZE);
    }
}
```

## Using the map for collisions
Convert a pixel position to a tile index:

```java
int col = (int) x / TILE_SIZE;
int row = (int) y / TILE_SIZE;
```

Check whether the player is inside a wall tile:

```java
boolean isWallTile(int col, int row) {
    if (col < 0 || col >= COLS || row < 0 || row >= ROWS) return true;
    return map[row][col] == 1;
}
```

Use four corner checks for a square player:

```java
boolean collidesWithWall(double x, double y) {
    int leftTile = (int) x / TILE_SIZE;
    int rightTile = (int) (x + playerSize - 1) / TILE_SIZE;
    int topTile = (int) y / TILE_SIZE;
    int bottomTile = (int) (y + playerSize - 1) / TILE_SIZE;

    return isWallTile(leftTile, topTile)
        || isWallTile(rightTile, topTile)
        || isWallTile(leftTile, bottomTile)
        || isWallTile(rightTile, bottomTile);
}
```

## Classroom activity
- Build a simple map and draw different tile colors.
- Add a goal tile and make the player earn points when touching it.
- Ask students to design a maze and explain why walls need collision checks.

---

## In Pellet Pursuit

Pellet Pursuit uses the same idea but with an **enum** instead of integers,
which makes the code easier to read.

### What is an enum?

An **enum** (short for *enumeration*) is a special type that holds a fixed set
of named constants. Instead of remembering that `1` means wall and `2` means
dot, you write names like `Tile.W` and `Tile.D`.

```java
// Without enum — easy to misread
if (tile == 1) { ... }

// With enum — intention is clear
if (tile == Tile.W) { ... }
```

You compare enum values with `==`, just like integers.

> **Reference:** [W3Schools — Java Enums](https://www.w3schools.com/java/java_enums.asp)

### The tile enum
```java
public enum Tile {
    W,            // Wall
    D,            // Dot
    P,            // Power pellet
    E,            // Empty
    SPAWN_PLAYER, // Player start
    SPAWN_G0,     // Shadow (red ghost)
    SPAWN_G1,     // Patrol (pink ghost)
    SPAWN_G2,     // Shy (cyan ghost)
    SPAWN_G3,     // Ambush (orange ghost)
    SPAWN_BONUS   // Where the bonus item appears
}
```

Inside `GameMap.java` there are short aliases so the array stays readable:

```java
// W, D, P, E stay as-is
private static final Tile PL = Tile.SPAWN_PLAYER;
private static final Tile G0 = Tile.SPAWN_G0, G1 = Tile.SPAWN_G1;
private static final Tile G2 = Tile.SPAWN_G2, G3 = Tile.SPAWN_G3;
private static final Tile BN = Tile.SPAWN_BONUS;
```

### The maze layout
`GameMap.DEFAULT_LAYOUT` is a `Tile[][]` array — the same structure as the
`int[][]` example above. Each spawn marker appears exactly once, embedded
directly where you want that thing to start:

```java
public static final Tile[][] DEFAULT_LAYOUT = {
    {W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W},
    {W, D, D, D, D, D, D, D, D, D, W, D, D, D, D, D, D, D, D, D, W},
    {W, P, W, W, D, W, W, W, D, W, W, W, D, W, W, W, D, W, W, P, W},
    // ...
    {W, W, W, W, D, W, E, W, W,G0, E, E, W, W, E, W, D, W, W, W, W},
    {E, E, E, E, D, E, E, W, E,G1,G2,G3, E, W, E, E, D, E, E, E, E},
    // ...
    {W, P, D, W, D, D, D, D, D, D,PL, D, D, D, D, D, D, W, D, P, W},
    // ...
};
```

### Wall checks
`GameMap.isWall(col, row)` works exactly like `isWallTile()` in the example —
look up the tile, return whether it equals `Tile.W`. It is used by `Player` to
stop at walls and by `Ghost` to navigate the maze. This is one of your
implementation tasks (see Step 4 below).

---

## Guided activity

> **All three player tasks and the maze design are base tasks.** Every student submits working movement and a custom maze.

### Step 1 — implement keyboard input (`Player.java`)

Open `Player.java` and find the `handleKey()` stub. Add a `switch` statement
that maps each key to a direction. Each case sets `nextDx` (horizontal) and
`nextDy` (vertical) to -1, 0, or 1 depending on which way the key points.

> **Reference:** [W3Schools — Java Switch](https://www.w3schools.com/java/java_switch.asp)

Here is the RIGHT case as an example — add the remaining three directions
(and their WASD equivalents) yourself:

```java
public void handleKey(KeyCode key) {
    switch (key) {
        case RIGHT, D -> { nextDx =  1; nextDy =  0; }
        // add cases for LEFT/A, DOWN/S, UP/W
        default -> {}
    }
}
```

`nextDx` and `nextDy` store the *requested* direction. The movement engine
reads them every frame and applies the turn when the player reaches the centre
of the next tile — which is why queuing a turn slightly early feels responsive.

Without this the player sits still and ignores all keyboard input.

### Step 2 — implement `isWall()` (`GameMap.java`)

Open `GameMap.java` and find the `isWall()` stub. Use `getTile(col, row)` to
look up the tile at that position and compare it to `Tile.W`.

Open `GameMap.java` and read the `getTile()` method just above the stub —
notice what it returns when `col` or `row` is outside the grid. Then ask
yourself: does `isWall()` need its own bounds check, or does `getTile()`
already handle that? Add a short comment in your implementation explaining
what you concluded and why.

Without this, the player and all ghosts phase straight through walls.

### Step 3 — wire `isWall()` into player movement (`Player.java`)

Open `Player.java` and find the `canMove` stub inside `update()`. The hint
comment gives you the expression — use it to replace the placeholder, then
add a comment in your own words explaining why the out-of-bounds check
(`nc < 0 || nc >= map.cols || ...`) is needed alongside the `isWall()` call.

> **Why two steps?** `isWall()` answers "is this tile a wall?" and `canMove`
> uses that answer to decide "should the player move?" Keeping them separate
> makes each piece easier to understand and test on its own.

**Checkpoint:** run the game now — `./mvnw javafx:run`. The screen is still
black (the maze won't appear until Step 7), but you can test movement by ear
and feel:

1. Press any arrow key — the player should start moving (you'll hear the
   movement sound).
2. Press **UP** from the start position — in the default layout there is a
   wall directly above spawn, so the player should stop within one tile.
3. If the player drifts in all four directions without ever stopping,
   `canMove` is still returning `true` for walls — re-check your `isWall()`
   and `canMove` logic before moving on.

Once you can feel the player stopping at walls, movement is done and you're
ready to design your maze.

### Step 4 — sketch your maze on paper first

Draw a 21×23 grid (or any odd-width, odd-height size you like). Mark walls,
open corridors, dot paths, the four power-pellet corners, and where you want
each spawn marker. Checking on paper first saves a lot of debugging.

Keep these in mind:
- The outer border should be all walls (`W`).
- Every corridor must be reachable — no isolated rooms with no way in.
- Place spawn markers on open tiles, never on a wall.

### Step 5 — edit `DEFAULT_LAYOUT` in `GameMap.java`

Open `GameMap.java` and find `DEFAULT_LAYOUT`. Replace it with your design.
Use the short aliases: `W`, `D`, `P`, `E`, `PL`, `G0`, `G1`, `G2`, `G3`, `BN`.

Each of the six spawn aliases must appear **exactly once**:

| Alias | Meaning |
|-------|---------|
| `PL`  | Player start |
| `G0`  | Shadow (red) spawn |
| `G1`  | Patrol (pink) spawn |
| `G2`  | Shy (cyan) spawn |
| `G3`  | Ambush (orange) spawn |
| `BN`  | Bonus item spawn |

### Step 6 — tunnel rule

Any open tile on the **left edge** must have a matching open tile on the **right edge** (horizontal tunnel), and same for top/bottom. If you don't want tunnels, keep the entire border as `W`.

The game will throw an error with a clear message if you break this rule.

### Step 7 — implement the tile drawing (`GameMap.java`)

Open `GameMap.java` and find the `TODO` inside `draw()`. You need to:

1. Write the nested `for` loops — one over rows (`r`), one over cols (`c`)
2. Inside the loops, look up `state[r][c]` and compute `px`/`py` from `c * TILE` and `r * TILE`
3. Add an `if/else if` block that draws each tile type

The hints in the comments show you exactly which `GraphicsContext` calls to use for each tile.

Once you save and rerun, the maze will appear instead of a black screen.

### Step 8 — choose your wall color

The default wall color (`#1a1aff`, classic Pac-Man blue) is set in the no-arg
`draw()` method just above the one you edited:

```java
public void draw(GraphicsContext gc) {
    draw(gc, Color.web("#1a1aff"));   // ← change this hex code
}
```

Pick any color you like. Some ideas:

| Hex | Look |
|-----|------|
| `#1a1aff` | Classic blue |
| `#00aa44` | Green dungeon |
| `#cc2200` | Red lava |
| `#8800cc` | Purple neon |
| `#555555` | Stone grey |

Or use a named color: `Color.TEAL`, `Color.CORAL`, `Color.GOLDENROD`, etc.

### Step 9 — run and verify

```
./mvnw javafx:run
```

If a spawn marker is missing or duplicated, the game will stop at startup and tell you which tile is the problem. Fix it and rerun.

Walk every corridor to confirm there are no dead-end rooms. Try eating all the dots — if `allDotsEaten()` never triggers, you may have a dot the player can't reach.

### Common mistakes

| Symptom | Likely cause |
|---------|--------------|
| `IllegalStateException: required spawn tile SPAWN_G1 not found` | Forgot to place `G1` in the layout, or placed it twice (which overwrites the first) |
| `Maze design error: row 10 has an open left edge but not right` | Row 10 has an `E` on the left but `W` on the right — either wall both sides or open both |
| Ghost or player spawns inside a wall | You placed a spawn alias (`G0`, `PL`, etc.) on a `W` tile — move it to an open tile |
| Bonus item never appears | `BN` is missing; the game silently has no valid spawn coordinate |
