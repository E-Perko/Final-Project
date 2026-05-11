# Pellet Pursuit — Student Template

A Pac-Man clone in JavaFX. The engine compiles and launches, but the maze is
invisible and the player doesn't move yet — your job is to implement the
gameplay across four phases, from basic movement to ghost AI to a persistent
high-score leaderboard.

## Tutorials

**Start here → [sbhs-computer-science-academy.github.io/PelletPursuit](https://sbhs-computer-science-academy.github.io/PelletPursuit/)**

The guides are also available offline in the `docs/` folder and walk you through each concept
and show you where it appears in this project's code.

## Prerequisites

| Tool | Version |
|------|---------|
| JDK  | 21 or 24 |
| IntelliJ IDEA | any recent version |

## Running the game

**In the terminal (recommended):**
```bash
./mvnw javafx:run
```

**In IntelliJ:** open the Maven tool window (**View → Tool Windows → Maven**),
expand **Plugins → javafx**, and double-click **javafx:run**.

> Do not use the green Run button on `PelletPursuitDemo.java` — JavaFX requires
> extra flags that Maven sets up automatically.

See [`docs/FinalProject_Setup.md`](docs/FinalProject_Setup.md) if you get stuck.

## Controls

| Key | Action |
|-----|--------|
| Arrow keys | Move |
| SPACE | Pause / Resume |
| ENTER | Start / Restart |

---

## Your assignment

> **New to the project?** Read [`docs/FinalProject_Starter.md`](docs/FinalProject_Starter.md) first — it walks through the project structure and tells you exactly where to start.
>
> These tasks span the full project. The phase guides in `docs/` teach each concept before you implement it.

### Base tasks (everyone)

> Tasks are grouped by phase. Each phase's guide in `docs/` teaches the concept first.

#### Phase 1 — Movement & maps (`Player.java`, `GameMap.java`)

See [`docs/FinalProject_Phase1_TileMap.md`](docs/FinalProject_Phase1_TileMap.md).

**a. Implement `handleKey(KeyCode key)`** — map the four arrow keys (and WASD) to `nextDx`/`nextDy` direction values using a `switch` statement. Without it the player sits still and ignores all input.

**b. Implement `isWall(int col, int row)`** — one line: look up the tile with `getTile()` and compare to `Tile.W`. Without it everything walks through walls.

**c. Implement `canMove` in `Player.update()`** — one line: use `map.isWall(nc, nr)` to decide whether the player can enter the next tile. This connects `isWall()` to actual wall collision.

> At this point the screen is still black, but movement is working. Run the game and verify you can control the player before moving on.

**d. Design your maze** — edit `DEFAULT_LAYOUT` using the aliases `W`, `D`, `P`, `E`, `PL`, `G0`–`G3`, `BN`. Each spawn marker must appear exactly once.

**e. Implement `draw()` tile rendering** — write the nested `for` loops over rows and cols, look up each tile from `state[r][c]`, and draw walls, dots, and power pellets with `if/else if`. Change the default wall color in the no-arg `draw()` above it.

#### Phase 2 — Enemies (`Ghost.java`, `Patrol.java`, `Shy.java`, `Ambush.java`)

See [`docs/FinalProject_Phase2_Enemies.md`](docs/FinalProject_Phase2_Enemies.md).

**a. Implement `Ghost.collidesWith(Player p)`** — return true when the pixel distance between ghost and player centres is less than `size * 0.8`. Without it ghosts pass through the player harmlessly.

**b. Implement ghost personalities** — each of `Patrol`, `Shy`, and `Ambush` has a `chooseTarget()` stub. Study `Shadow.java` first — it is the worked example. After each one works, add it to the ghost list in `PelletPursuitDemo.java` (the comment shows exactly where).

**c. Tune difficulty** — edit `LevelConfig.forLevel()` so level 1 feels fair and each level is noticeably harder.

#### Phase 3 — Bonus items & file I/O (`BonusItem.java`, `ScoreTree.java`)

See [`docs/FinalProject_Phase3_State.md`](docs/FinalProject_Phase3_State.md) and [`docs/FinalProject_Phase3_FileIO.md`](docs/FinalProject_Phase3_FileIO.md).

**a. Create a custom bonus item** — extend `BonusItem` and override `getPoints()`, `getLabel()`, and `getColor()`. Swap it in for `Cherry` in `PelletPursuitDemo.java`.

**b. Implement `updateBonusItems(double dt)` in `PelletPursuitDemo.java`** — iterate the `bonusItems` list, update each item, award points on collection, and use `removeIf()` to remove collected or expired items. Without it bonus items appear but can never be collected.

**c. Implement `ScoreTree.saveToFile()` and `loadFromFile()`** — write scores using `BufferedWriter` and read them back, using a `HashSet` to skip duplicates. Without them scores reset every run.

#### Phase 4 — Recursion & BST (`ScoreTree.java`)

See [`docs/FinalProject_Phase4_RecursionBST.md`](docs/FinalProject_Phase4_RecursionBST.md).

**Implement `insert()` and `collectDescending()`** — recursive BST insert and reverse in-order traversal. Without them the leaderboard never populates.

---

### Extension tasks (going further)

These require reading more of the engine code.

#### A. Per-level layouts

Override `getLayout(int level)` in a subclass of `PelletPursuitDemo`.
Return a different `GameMap.Tile[][]` for each level number.

#### B. Re-implement player movement (`Player.java`)

`Player.update()` handles input buffering so the player can queue a turn one
tile early.  Delete the body, replace it with your own implementation, and
test that all four directions and tunnel wrapping still work.

#### C. Replace BFS with your own pathfinding (`Ghost.java`)

`bfsDirection()` in `Ghost.java` uses breadth-first search.  Try replacing it
with a depth-limited DFS, A*, or a greedy approach and compare how the ghosts
feel.

#### D. Custom sprite images

Replace the programmatic drawings with your own images for the player and/or
any ghost. Drop a square PNG with transparency into
`src/main/resources/game/images/`, then in the constructor of the class you
want to change call:

```java
spriteImage = loadImage("/game/images/yourfile.png");
```

`loadImage` is defined on `Sprite` — it loads the file from the classpath and
returns `null` silently if the file is missing, so the default drawing is used
as a fallback. Ghost frightened and dead states always use the built-in
drawings regardless of whether a sprite image is set.

---

## File guide

```
src/main/java/game/
  Sprite.java            — base class: position, size, speed, helper methods
  Player.java            — player input, movement, dot eating
  Ghost.java             — abstract ghost: BFS, frighten/dead states  ← read me
    Shadow.java          — WORKED EXAMPLE: complete chooseTarget()
    Patrol.java          — TODO: implement chooseTarget()
    Shy.java             — TODO: implement chooseTarget()
    Ambush.java          — TODO: implement chooseTarget()
  GameMap.java           — tile maze, dot/power-pellet tracking
  LevelConfig.java       — difficulty values per level  ← edit me
  BonusItem.java         — abstract bonus item + Cherry example  ← extend me
  AudioManager.java      — synthesised sound effects (no files needed)
  ScoreTree.java         — BST high-score table
  ScoreNode.java         — BST node
  PelletPursuitDemo.java — JavaFX Application, game loop, HUD
```
