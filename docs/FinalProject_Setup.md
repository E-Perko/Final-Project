# JavaFX Game Setup

## Goal
Get IntelliJ IDEA and the Pellet Pursuit template running on your machine.

## Prerequisites
- JDK 21 or 24 installed ([adoptium.net](https://adoptium.net))
- IntelliJ IDEA Community or Ultimate (any recent version)
- No JavaFX download needed — Maven handles it automatically

---

## 1. Clone and open the project

1. Open IntelliJ IDEA. From the Welcome screen choose **Get from Version Control**
   (or from the menu: **File → New → Project from Version Control**).
2. Paste this URL and click **Clone**:
   `https://github.com/SBHS-Computer-Science-Academy/PelletPursuit`
   IntelliJ downloads the project and opens it automatically.
3. When IntelliJ asks to load the Maven project, click **Load**.

> If IntelliJ does not ask automatically, open the Maven tool window
> (**View → Tool Windows → Maven**) and click the refresh icon.

---

## 2. Run the game

**Option A — Terminal (recommended):**
```bash
./mvnw javafx:run
```
Run this from the project's root folder. The first run downloads JavaFX
automatically (~5 MB); subsequent runs start in seconds.

**Option B — IntelliJ run configuration:**
1. Open the Maven tool window (**View → Tool Windows → Maven**).
2. Expand **Plugins → javafx**.
3. Double-click **javafx:run**.

You should see the Pellet Pursuit game window open. Use the arrow keys to move
and ENTER to start.

---

## 3. Troubleshooting

| Symptom | Fix |
|---------|-----|
| `Could not find or load main class` | Make sure IntelliJ loaded the Maven project (step 1) |
| `JDK 21 required` | Check **File → Project Structure → SDK** — set it to JDK 21 or 24 |
| Window opens but game is blank | Press ENTER to start |
| `./mvnw: Permission denied` | Run `chmod +x mvnw` in the terminal, then retry |

---

## 4. Classroom tips
- Run `./mvnw javafx:run` once yourself before class to warm up the Maven cache.
- If the school network blocks Maven Central, pre-populate `~/.m2` by running
  the command on one machine and copying the cache to others.
- Do **not** click **Build → Rebuild Project** in IntelliJ — Maven handles the build.
