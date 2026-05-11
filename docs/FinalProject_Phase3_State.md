# JavaFX Game State and Collision Logic

## Goal
Explain how to track game state, detect collisions, and show game status to the player.

## Common game state variables
```java
boolean gameOver = false;
boolean gameStarted = false;
int score = 0;
int lives = 3;
```

## Collision helper method
Use the same method for player/enemy, player/wall, or player/goal collisions.

```java
boolean intersects(double x1, double y1, double w1, double h1,
                   double x2, double y2, double w2, double h2) {
    return x1 < x2 + w2 &&
           x1 + w1 > x2 &&
           y1 < y2 + h2 &&
           y1 + h1 > y2;
}
```

## Managing game states
### Start screen
```java
if (!gameStarted) {
    gc.setFill(Color.WHITE);
    gc.fillText("Press ENTER to start", 300, 320);
    if (keysPressed.contains(KeyCode.ENTER)) {
        gameStarted = true;
    }
    return;
}
```

### Game over
```java
if (gameOver) {
    gc.setFill(Color.WHITE);
    gc.fillText("Game Over", 360, 280);
    gc.fillText("Press ENTER to restart", 300, 310);
    if (keysPressed.contains(KeyCode.ENTER)) {
        resetGame();
    }
    return;
}
```

## Restart and reset methods
```java
void resetPlayer() {
    playerX = TILE_SIZE + 4;
    playerY = TILE_SIZE + 4;
}

void resetGame() {
    score = 0;
    lives = 3;
    gameOver = false;
    gameStarted = false;
    resetPlayer();
    createEnemies();
}
```

## Drawing status text
Add clear feedback so players always know the goal.

```java
gc.setFill(Color.WHITE);
gc.fillText("Score: " + score, 10, 18);
gc.fillText("Lives: " + lives, 10, 34);
```

## Using state in update()
- only move player when `gameStarted && !gameOver`
- adjust score when the player reaches a goal
- subtract life when an enemy hits the player
- set `gameOver` when lives reach zero

## Classroom activity
- Have students add a start screen and a restart key.
- Ask them to implement `gameOver` text when the player loses.
- Challenge them to add a victory state when the player reaches a goal.

---

## In Pellet Pursuit

`PelletPursuitDemo.java` uses a `State` enum instead of boolean flags, which
scales better when there are many distinct states:

```java
enum State { START, PLAYING, PAUSED, DEAD_PAUSE, LEVEL_CLEAR, WIN, GAME_OVER }
```

The main `update()` and `draw()` methods both switch on the current state,
so each state's logic is kept in one place. This is the same idea as the
`gameStarted` / `gameOver` booleans in the example above — just more explicit.

### Score and lives
`score` and `lives` are tracked as fields in `PelletPursuitDemo`. When lives
reach zero, the state transitions to `GAME_OVER`; when all dots are eaten it
transitions to `LEVEL_CLEAR`.

### Your task
Find the `PLAYING` branch in `update()` and trace how score is incremented
when the player eats a dot. Then find where `lives` is decremented and follow
the state transition to `DEAD_PAUSE`.

---

## Creating a custom bonus item

Bonus items are collectibles that appear mid-level for a limited time.
The project includes `Cherry` as a worked example. Your job is to replace it
with something of your own.

### How BonusItem works

`BonusItem` is an abstract class with three methods to implement:

> **Reference:** [W3Schools — Java Abstract Classes](https://www.w3schools.com/java/java_abstract.asp)

```java
public abstract int    getPoints();  // points awarded on collection
public abstract String getLabel();   // short text drawn on the icon
public abstract Color  getColor();   // fill colour of the circle
```

The lifetime (how long it stays on screen) is set in the constructor.

### The Cherry example

```java
class Cherry extends BonusItem {
    public Cherry(double pixelX, double pixelY) {
        super(pixelX, pixelY, GameMap.TILE, 8.0); // size=TILE, lifetime=8s
    }
    @Override public int    getPoints() { return 200; }
    @Override public String getLabel()  { return "200"; }
    @Override public Color  getColor()  { return Color.RED; }
}
```

### Your task
1. Add a new class below `Cherry` in `BonusItem.java` that extends `BonusItem`.
2. Override `getPoints()`, `getLabel()`, and `getColor()` with values that
   differ from Cherry's.
3. In `PelletPursuitDemo.java`, search for `new Cherry` and replace it with
   `new YourClassName`.

> **Going further:** override `draw()` to add a visual feature that reflects
> the time remaining. `getLifetime()` returns how many seconds are left — use
> it to make the item look different as it counts down (shrinking circle,
> countdown label, pulsing size). Call `super.draw(gc)` first to keep the base
> circle, then add your feature on top.

---

## Implementing `updateBonusItems()`

Once your custom bonus item class exists, you need to wire up the collection
logic in `PelletPursuitDemo.java`. Find the `updateBonusItems()` stub and
implement it in two steps.

### Step 1 — update each item and award points on contact

Loop over `bonusItems`, tick each item's countdown timer, and add points if
the player is touching it:

```java
for (BonusItem item : bonusItems) {
    item.update(dt, map);
    if (item.collidesWith(player)) {
        score += item.getPoints();
        audio.playBonus();
    }
}
```

### Step 2 — remove collected or expired items

You cannot call `bonusItems.remove()` *inside* the loop above — Java throws
a `ConcurrentModificationException` if you modify a list while a `for-each`
is still iterating it. The safe pattern is to collect the items to remove
first, then delete them after the loop:

```java
List<BonusItem> toRemove = new ArrayList<>();
for (BonusItem item : bonusItems) {
    if (item.collidesWith(player) || item.isExpired()) {
        toRemove.add(item);
    }
}
bonusItems.removeAll(toRemove);
```

> **Going further — the one-liner version**
>
> Java has a built-in method called `removeIf` that combines Steps 1 and 2
> into a single line:
> ```java
> bonusItems.removeIf(item -> item.collidesWith(player) || item.isExpired());
> ```
> The `item -> ...` syntax is called a **lambda expression** — a short,
> unnamed function written inline. `removeIf` calls it on every element and
> removes the ones where it returns `true`. You'll see this pattern often in
> professional Java code.
