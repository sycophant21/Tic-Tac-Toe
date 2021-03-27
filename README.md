# Tic-Tac-Toe

## A game of tic-tac-toe.

### Implementation
1. Choose from two playing modes, console or windowed.
2. The player gets to choose their token the machine takes the other.
3. The program then creates two board objects.
4. The first object is sent to a function which then stores all the possible moves on a particular state and then calls itself recursivly and store all the possible moves corresponding to each and every state in the last iteration and then again calls itself till it finds a terminal state.
5. It assigns a value to a state using min-max algorithm.
6. The second board object is used to play the game.
7. On machine's turn, it chooses the best state from the set of all possible child states of the current state of board and then create a move object that will take the current state of board to the machine's preffered state.
