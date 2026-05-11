# JavaFX Game Enemies with ArrayList

## Goal
Teach students how to manage multiple moving enemies using `ArrayList` and a custom enemy class.

## Why use `ArrayList`?
An `ArrayList` can grow and shrink at runtime. It is ideal for storing enemies, bullets, and collectibles.

> **Reference:** [W3Schools — Java ArrayList](https://www.w3schools.com/java/java_arraylist.asp)

## Sample enemy class
```java
private class Enemy {
    double x;
    double y;
    double size = 32;
    double speed = 2;
    double dx;
    double dy;

    Enemy(double x, double y) {
        this.x = x;
        this.y = y;
        chooseDirection();
    }

    void chooseDirection() {
        int direction = random.nextInt(4);
        dx = 0;
        dy = 0;
        if (direction == 0) dx = speed;
        else if (direction == 1) dx = -speed;
        else if (direction == 2) dy = speed;
        else dy = -speed;
    }

    void update() {
        x += dx;
        y += dy;
        // Add wall collision or bounds checks here.
    }
}
```

## Create an enemy list
```java
List<Enemy> enemies = new ArrayList<>();

void createEnemies() {
    enemies.add(new Enemy(100, 100));
    enemies.add(new Enemy(300, 200));
    enemies.add(new Enemy(500, 120));
}
```

## Update enemies every frame
```java
for (Enemy enemy : enemies) {
    enemy.update();
}
```

## Draw enemies
```java
for (Enemy enemy : enemies) {
    gc.setFill(Color.RED);
    gc.fillOval(enemy.x, enemy.y, enemy.size, enemy.size);
}
```

## Collision with the player
Use the same rectangle overlap function for player and enemies:

```java
boolean intersects(double x1, double y1, double w1, double h1,
                   double x2, double y2, double w2, double h2) {
    return x1 < x2 + w2 &&
           x1 + w1 > x2 &&
           y1 < y2 + h2 &&
           y1 + h1 > y2;
}
```

Then check each enemy:

```java
for (Enemy enemy : enemies) {
    if (intersects(playerX, playerY, playerSize, playerSize,
                   enemy.x, enemy.y, enemy.size, enemy.size)) {
        lives--;
        resetPlayer();
        break;
    }
}
```

## Classroom activity
- Have students add a new enemy every few seconds.
- Ask them to make enemies move in different ways.
- Extend the example to remove enemies when hit by a bullet.

---

## In Pellet Pursuit

Pellet Pursuit uses the same `ArrayList` pattern, but the enemies are more
sophisticated because each ghost has a different personality.

### The ghost list
In `PelletPursuitDemo.java`, the ghosts are stored exactly like the example above:

```java
List<Ghost> ghosts = new ArrayList<>();
ghosts.add(new Shadow());
ghosts.add(new Patrol());
ghosts.add(new Shy());
ghosts.add(new Ambush());
```

Each frame they are updated and checked for collisions in a loop, just like
the simple `Enemy` example.

### Why abstract class instead of a single Enemy class?

The simple `Enemy` class above works when all enemies behave the same way.
When enemies need *different* behaviours, an **abstract class** is a better
tool:

> **Reference:** [W3Schools — Java Abstract Classes](https://www.w3schools.com/java/java_abstract.asp)

```
Ghost  (abstract — handles movement, BFS, drawing)
  └── Shadow   — overrides chooseTarget() to chase directly
  └── Patrol   — overrides chooseTarget() to patrol a corner
  └── Shy      — overrides chooseTarget() to flee until cornered
  └── Ambush   — overrides chooseTarget() to cut off the player
```

`Ghost` handles everything the ghosts share (movement, wall avoidance, the
frightened/dead states). Each subclass only needs to answer one question:
*"given where the player is, which tile should I head toward?"*

That one method — `chooseTarget()` — is what you implement.

### Collision detection
`Ghost.collidesWith(Player p)` uses **distance-based** collision rather than
rectangle overlap. This is a different strategy from tile collision (`isWall`):
instead of looking up a grid cell, you measure the pixel distance between two
sprite centres and check if it is less than a threshold.

This is also a stub — you implement it in Phase 2 (see Your task below).

### Your task

**1. Implement `Ghost.collidesWith(Player p)` in `Ghost.java`**

The method should return `true` when the ghost is close enough to the player
to count as a collision. `distanceTo()` is already implemented in `Sprite` —
it returns the straight-line pixel distance between two centre points.

Use it to compare the distance between the ghost and the player against a
threshold based on `size`. Consider: should the threshold be exactly `size`,
slightly less, or slightly more? Think about what each choice would feel like
to a player — a threshold that is too large means dying when a ghost barely
clips the edge of your tile; one that is too small means ghosts walk through
you.

This method uses pixel distance rather than tile distance. Add a short comment
in your implementation explaining why checking which tile each sprite is on
would be less accurate than measuring the pixel gap between their centres.

Without this, ghosts pass through the player with no effect — no death, no
eating frightened ghosts.

**2. Implement the ghost personalities — one at a time**

Each ghost file (`Patrol.java`, `Shy.java`, `Ambush.java`) has a `chooseTarget()`
stub with TODO comments. Read `Shadow.java` first — it is the worked example.

Work through them one at a time. When a ghost's `chooseTarget()` is working,
add it to the ghost list in `PelletPursuitDemo.java`:

```java
ghosts = new ArrayList<>(List.of(
    new Shadow(map),
    new Patrol(map),   // add after Patrol works
    new Shy(map),      // add after Shy works
    new Ambush(map)    // add after Ambush works
));
```

Each ghost you add should feel visibly different from Shadow — that is how you
know the personality is working.

---

## Tuning difficulty with LevelConfig

Once your ghosts have distinct personalities, the next step is making the game
get harder as the level number rises.

### How it works

`LevelConfig.forLevel(int level)` is called once at the start of each level
and returns four values the game loop reads directly:

| Field | What it controls |
|-------|-----------------|
| `ghostSpeedMultiplier` | How fast all ghosts move (1.0 = normal) |
| `frightenDuration` | Seconds ghosts stay blue after a power pellet |
| `spawnDelay` | Seconds between each ghost leaving the house |
| `bonusThreshold` | Dots eaten before a bonus item appears |

### Reading the current values

```java
public static LevelConfig forLevel(int level) {
    double speed   = 1.0 + (level - 1) * 0.08;   // 1.0, 1.08, 1.16 ...
    double frighten = 6.0 - (level - 1) * 0.4;   // 6.0, 5.6, 5.2 ...
    double spawn   = 3.0 - (level - 1) * 0.2;    // 3.0, 2.8, 2.6 ...
    int    bonus   = 70;
    return new LevelConfig(speed, frighten, spawn, bonus);
}
```

Each value changes linearly per level and is clamped so it never goes out of
range. The field guide comment at the top of `LevelConfig.java` shows the
safe min/max for each field.

### Your task
Edit `forLevel()` so that:
- Level 1 feels fair for a new player
- Each level is noticeably harder than the last
- No value goes outside its sensible range

**Concrete targets to aim for:**

| Value | Level 1 | Level 3 |
|-------|---------|---------|
| `ghostSpeedMultiplier` | ≤ 1.1 | ≤ 1.5 |
| `frightenDuration` | ≥ 5 s | ≥ 2 s |
| `spawnDelay` | ≥ 2.5 s | ≥ 1 s |

Test by playing level 1 after each change — you should be able to survive for
at least 30 seconds without eating a power pellet.
