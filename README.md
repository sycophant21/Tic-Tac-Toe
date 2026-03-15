# Tic-Tac-Toe

> It's a solved game. The AI knows it. You will too, after round one.

A **Tic-Tac-Toe game** in Java with an unbeatable AI opponent powered by the **minimax algorithm**. Play in the console or in a graphical Swing window — either way, the machine will not lose.

## What it does

- Two play modes: **console** (text-based) and **windowed** (Java Swing with sprites)
- Player chooses their token (X or O); the AI takes the other
- AI uses **minimax** to evaluate all possible game states and always picks the optimal move — it cannot be beaten, only drawn
- Windowed mode uses custom sprite assets (board, X, O, circle-ring, etc.)

## How it works

On startup, `Main` creates two `Board` objects:
1. A **search board** — used by the minimax algorithm to recursively expand all possible future states, assign terminal values (+1 win, -1 loss, 0 draw), and bubble the best score back up the tree.
2. A **play board** — the live game state.

On each turn, the search board's state is synchronized with the play board. When it's the machine's turn, `MoveHandler` queries all child states of the current position, picks the one with the highest minimax value, and constructs a `Move` object. `StateMoveBoardHandler` applies the move to both boards. `GameHandler` detects win/draw terminal states. In windowed mode, `Window` renders the board and sprites.

## Tech stack

- **Java** (standard library)
- **Java Swing / Java2D** for windowed mode

## Getting started

### Prerequisites

- Java 8+
- IntelliJ IDEA or Eclipse (recommended)

### Run (IntelliJ)

1. Open the project root in IntelliJ IDEA.
2. Set `tic-tac-toe_2/src/` as the source root.
3. Mark `tic-tac-toe_2/src/Assets/` as a resource root (for sprites in windowed mode).
4. Run `Main.java`.
5. Follow the prompts to choose console or windowed mode and pick your token.

### Compile manually

```bash
javac -d out tic-tac-toe_2/src/*.java
java -cp out:tic-tac-toe_2/src Main
```

> **Note**: The windowed mode loads sprite images from the `Assets/` folder. Make sure the working directory is set to `tic-tac-toe_2/src/` when running from the command line.

## Project structure

```
tic-tac-toe_2/src/
├── Assets/              # sprite images (board, X, O, etc.)
├── Main.java            # entry point + mode selection
├── Board.java           # game grid state
├── BoardHandler.java    # board mutation logic
├── State.java           # board state snapshot
├── Move.java            # a single move (row, col, token)
├── MoveHandler.java     # minimax move selection
├── StateMoveBoardHandler.java  # applies moves, syncs boards
├── GameHandler.java     # win/draw detection
└── Window.java          # Swing rendering (windowed mode)
```
