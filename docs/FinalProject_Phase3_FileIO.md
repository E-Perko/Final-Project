# JavaFX Game File I/O

## Goal
Teach students how to read and write simple game data such as high scores.

## Why file I/O?
Saving high scores or settings makes the game feel more complete.
It also introduces the `java.nio.file` API and exception handling.

> **Reference:** [W3Schools — Java Try-Catch](https://www.w3schools.com/java/java_try_catch.asp)

## Example: save a score to a file
```java
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

void saveScore(String name, int score) {
    String line = name + "," + score + "\n";
    Path file = Path.of("highscores.txt");
    try {
        Files.writeString(file, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

## Example: load scores from a file
```java
List<String> readScores() {
    Path file = Path.of("highscores.txt");
    try {
        return Files.readAllLines(file);
    } catch (Exception e) {
        return new ArrayList<>();
    }
}
```

## Parse saved data
```java
for (String line : readScores()) {
    String[] parts = line.split(",");
    if (parts.length == 2) {
        String name = parts[0];
        int score = Integer.parseInt(parts[1]);
        // use the parsed score
    }
}
```

## High score screen
Draw saved scores on screen:
```java
List<String> entries = readScores();
for (int i = 0; i < entries.size(); i++) {
    gc.fillText(entries.get(i), 10, 80 + 20 * i);
}
```

## Tips
- Keep file formats simple: `name,score`.
- Use `try/catch` to avoid crashes if the file is missing.
- Save only when the game ends or the player wins.
- If students are not ready for file I/O, this module can be optional.

---

## In Pellet Pursuit

`ScoreTree.java` saves and loads scores using the same `java.nio.file` APIs
shown above. The file format is identical — one `name,score` entry per line.

```java
// ScoreTree.saveToFile(path)  — writes sorted scores to disk
// ScoreTree.loadFromFile(path) — reads them back on startup
```

The scores are stored in a **binary search tree** (see Phase 4) rather than a
plain list, so they come out sorted automatically when the tree is traversed
in-order.

### Buffers

Writing to disk one byte at a time is slow. A `BufferedWriter` collects output
in an in-memory buffer and flushes it to disk in one efficient operation when
the writer is closed. Java's try-with-resources syntax handles the flush and
close automatically:

```java
try (BufferedWriter writer = Files.newBufferedWriter(path)) {
    writer.write("hello");
    writer.newLine();
} catch (IOException ignored) {}
```

### Sets

A `Set` stores values with no duplicates. `HashSet` is the most common
implementation

> **Reference:** [W3Schools — Java HashSet](https://www.w3schools.com/java/java_hashset.asp) — `add()` returns `true` if the item was new, `false` if it was
already present. This makes it easy to skip duplicates in one check:

```java
Set<String> seen = new HashSet<>();
for (String line : lines) {
    if (seen.add(line)) {   // false if already seen — skip it
        // process line
    }
}
```

### Your task

`saveToFile()` and `loadFromFile()` in `ScoreTree.java` have `TODO` stubs.

> **Note:** `loadFromFile()` calls `insert()` internally to rebuild the tree.
> If you haven't implemented `insert()` yet (Phase 4), scores will be read from
> disk but silently discarded. Implement Phase 4 first if you want to test
> persistence end-to-end.

**`saveToFile(Path path)`**
1. Build a `List<String>` of formatted score entries by calling `collectInOrder(root, lines)` — look at that method to see how entries are formatted.
2. Open a `BufferedWriter` using `Files.newBufferedWriter(path)` inside a try-with-resources block and write each line, followed by `writer.newLine()`. See the BufferedWriter example earlier in this guide.

**`loadFromFile(Path path)`**
1. Return early if the file doesn't exist (`Files.exists(path)`).
2. Read all lines with `Files.readAllLines(path)` — wrap in try/catch for `IOException`.
3. Use a `HashSet<String>` to skip duplicate lines before parsing — see the Sets section earlier in this guide for how `add()` detects duplicates in one step.
4. For each unique line, split on `" "` and parse the two integers, then call `insert()`.

Once implemented, scores will persist between sessions. Verify by playing a
game, quitting, restarting — your score should appear on the leaderboard.
