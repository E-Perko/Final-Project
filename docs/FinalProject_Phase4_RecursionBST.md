# JavaFX Game Recursion and BSTs

## Goal
Link recursion and binary search trees to the game project as later-week extensions.

## Recursion basics
A recursive method calls itself with a smaller problem.
It needs a base case to stop.

> **Reference:** [W3Schools — Java Recursion](https://www.w3schools.com/java/java_recursion.asp)

### Example: countdown
```java
void countdown(int n) {
    if (n <= 0) {
        System.out.println("Blastoff!");
        return;
    }
    System.out.println(n);
    countdown(n - 1);
}
```

### Example: factorial
```java
int factorial(int n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);
}
```

## Recursive game-related example
A flood-fill algorithm can color a connected area on a 2D grid.

```java
void floodFill(int row, int col) {
    if (row < 0 || row >= ROWS || col < 0 || col >= COLS) return;
    if (map[row][col] != 0) return;
    map[row][col] = 3; // fill value
    floodFill(row - 1, col);
    floodFill(row + 1, col);
    floodFill(row, col - 1);
    floodFill(row, col + 1);
}
```

## Binary search tree (BST) leaderboard
A BST is a tree where left nodes are smaller and right nodes are larger.
It is a good way to sort scores and teach recursion.

### Node class
```java
class ScoreNode {
    int score;
    ScoreNode left;
    ScoreNode right;

    ScoreNode(int score) {
        this.score = score;
    }
}
```

### Insert method
```java
ScoreNode insert(ScoreNode root, int score) {
    if (root == null) return new ScoreNode(score);
    if (score < root.score) {
        root.left = insert(root.left, score);
    } else {
        root.right = insert(root.right, score);
    }
    return root;
}
```

### In-order traversal
```java
void printInOrder(ScoreNode node) {
    if (node == null) return;
    printInOrder(node.left);
    System.out.println(node.score);
    printInOrder(node.right);
}
```

## Classroom activity
- Start with simple recursion examples.
- Show how recursion matches the BST insert/search pattern.
- Use the BST to store scores and print them in sorted order.
- For advanced students, visualize nodes on the screen.

---

## References as pointers

Java doesn't have explicit pointers, but object references work the same way.
When `ScoreNode` declares:

```java
ScoreNode left;
ScoreNode right;
```

`left` and `right` are references — they store the memory address of another
`ScoreNode`, exactly like a pointer in C. Setting `node.left = new ScoreNode(…)`
makes `left` point to a new node. Setting it to `null` is the equivalent of a
null pointer.

The BST is therefore a **linked structure**: every node holds two references
that chain it to its children. This is the same idea behind a singly-linked
list, where each node holds one `next` reference instead of two.

You can see this pattern used in the engine too — open `Ghost.java` and find
`bfsDirection()`. The BFS uses:

```java
Queue<int[]> queue = new LinkedList<>();
```

`LinkedList` is Java's built-in linked list. Each element is a node that holds
a reference to the next.

> **Reference:** [W3Schools — Java LinkedList](https://www.w3schools.com/java/java_linkedlist.asp) The BFS uses it as a `Queue` (add to back, remove from
front), but the underlying structure is a chain of linked nodes — the same
concept you are implementing by hand in `ScoreTree`.

## In Pellet Pursuit

`ScoreTree.java` uses the exact same BST structure from the tutorial.
Two methods have `TODO` stubs for you to implement.

### ScoreNode.java
Compare the tutorial's `ScoreNode` to the one in `ScoreNode.java` — they are
nearly identical. The only addition is a `level` field alongside `score`.

### Your task

**`insert(ScoreNode node, int score, int level)`** — recursive BST insert.

Match the tutorial's insert pattern exactly:
- Base case: `node == null` → return `new ScoreNode(score, level)`
- Recursive case: go left if `score < node.score`, otherwise go right
- Always return `node` at the end

**`collectDescending(ScoreNode node, List<ScoreNode> result, int n)`** — reverse in-order traversal.

This is the mirror of the tutorial's `printInOrder`, but visiting **right first** so scores come out highest-to-lowest, and stopping once `result` has `n` items:
- Base cases: `node == null` or `result.size() >= n` → return
- Visit right subtree → add node → visit left subtree

Once both methods are implemented the leaderboard on the Game Over screen
will show the top 5 scores in descending order.

### Extension tasks
- Add a `search(int score)` method that returns whether a score exists in the tree (recursive, like `insert`).
- Add a `max()` method that returns the highest score by walking right until there is no right child.
- Visualise the tree on screen: draw each node as a circle with lines connecting parent to child.
