# Pellet Pursuit Starter Guide

> The full assignment breakdown (base tasks, extension tasks, grading) is in the
> [project README](../README.md). Read that alongside this guide.

## Goal
Understand how the project is organised and identify the files you will edit.

---

## How the project is organised

Unlike a single-file starter, Pellet Pursuit is split into classes that each
have one job. You do not need to understand every file to get started — focus
on the ones listed under **Your tasks** below.

```
src/main/java/game/
  PelletPursuitDemo.java  — JavaFX Application: game loop, score, lives, HUD
  Player.java             — player movement and dot eating
  Ghost.java              — abstract ghost: movement engine, frighten/dead states
    Shadow.java           — WORKED EXAMPLE — read this first
    Patrol.java           — TODO: implement chooseTarget()
    Shy.java              — TODO: implement chooseTarget()
    Ambush.java           — TODO: implement chooseTarget()
  GameMap.java            — tile maze layout and dot tracking
  LevelConfig.java        — difficulty values per level
  BonusItem.java          — abstract bonus item + Cherry example
  ScoreNode.java          — BST node for the high-score table
  ScoreTree.java          — BST high-score table with file persistence
```

---

## The game loop

`PelletPursuitDemo.java` contains a JavaFX `AnimationTimer` that fires ~60
times per second. Each tick calls two methods:

```java
void update(double dt)   // move things, check collisions, change state
void draw()              // clear screen, draw everything
```

`dt` is the elapsed time in seconds since the last frame (~0.016 s at 60 fps).
Multiplying speeds by `dt` keeps movement frame-rate independent.

---

## Your tasks at a glance

The full task list with details is in the [README](../README.md). Here's the
short version so you know what you're getting into before you read the phase
guides.

### Phase 1 — Movement & maps
- Implement `handleKey()` in `Player.java` — arrow keys queue a direction
- Implement `isWall()` in `GameMap.java` — one line, tile lookup
- Implement `canMove` in `Player.update()` — connects `isWall()` to movement
- Design your maze in `DEFAULT_LAYOUT`
- Implement `draw()` in `GameMap.java` — renders walls, dots, power pellets

### Phase 2 — Enemies
- Implement `collidesWith()` in `Ghost.java` — distance check
- Implement `chooseTarget()` in `Patrol.java`, `Shy.java`, `Ambush.java`
- Tune `LevelConfig.forLevel()` so difficulty scales across levels

### Phase 3 — Bonus items & file I/O
- Create a custom bonus item by extending `BonusItem`
- Implement `updateBonusItems()` in `PelletPursuitDemo.java` — iterate the list, collect, remove with `removeIf()`
- Implement `saveToFile()` and `loadFromFile()` in `ScoreTree.java`

### Phase 4 — Recursion & BST
- Implement `insert()` and `collectDescending()` in `ScoreTree.java`

---

## Reading Shadow before writing your own

`Shadow.java` is the worked example. It shows exactly how `chooseTarget()`
works:

```java
@Override
protected int[] chooseTarget(Player player, GameMap map) {
    if (frightened) {
        // When frightened: flee to the corner farthest from the player
        ...
        return corner;
    }
    // Normal: target the player's exact tile
    return new int[]{ player.col(map), player.row(map) };
}
```

`player.col(map)` and `player.row(map)` convert the player's pixel position
to a tile column/row. You'll use these in every ghost you implement.

---

## Personalizing your game

These are fast, low-effort changes that make your submission feel like yours
rather than a copy of everyone else's. None require reading the engine code.

| What | Where | How |
|------|-------|-----|
| Game title & tagline | `PelletPursuitDemo.java` top | Change `GAME_TITLE` and `GAME_SUBTITLE` — shown on the start screen and window bar |
| Scores file name | `PelletPursuitDemo.java` top | Change `SCORES_FILE` — the file your leaderboard is saved to |
| Leaderboard heading | `PelletPursuitDemo.java` top | Change `LEADERBOARD_TITLE` — shown above high scores on the Game Over screen |
| Starting lives | `PelletPursuitDemo.java` top | Change `STARTING_LIVES` — try 5 for an easier game, 1 for a brutal one |
| HUD colors | `PelletPursuitDemo.java` top | Change `HUD_COLOR` and `HUD_TEXT` |
| Death / win messages | `PelletPursuitDemo.java` top | Change `MSG_DEAD`, `MSG_READY`, `MSG_WIN` |
| Player color | `Player.java` | Set `bodyColor` to any `Color` in the field declaration |
| Ghost names | `Patrol.java`, `Shy.java`, `Ambush.java` | Change the string in `getName()` — shown on screen when a ghost catches you |
| Wall color | `GameMap.java` | Change the hex code in the no-arg `draw()` (Step 8 in Phase 1) |
| Custom sprite images | See extension D in the README | Drop a PNG in `src/main/resources/game/images/` and call `loadImage()` |

---

## Getting started

1. Run the game (`./mvnw javafx:run`). The player sits still and ignores
   input — that's expected. Your first task is `handleKey()` in `Player.java`.
2. Read `Shadow.java` top to bottom — it's the worked example for Phase 2.
3. Before you implement each ghost, write one sentence describing what it
   *should* do. Keep that description next to you while you code.
