12545. GooseInZooDivTwo
=======================

Problem
-------

Crow Keith is looking at the goose cage in the zoo. The bottom of the
cage is divided into a grid of square cells. There are some birds
sitting on those cells (with at most one bird per cell). Some of them
are geese and all the others are ducks. Keith wants to know which birds
are geese. He knows the following facts about them:

-   There is at least one goose in the cage.
-   Each bird within Manhattan distance **dist** of any goose is also a
    goose.

You are given a String[] **field** and the int **dist**. The array
**field** describes the bottom of the cage. Each character of each
element of **field** describes one of the cells. The meaning of
individual characters follows.

-   The character 'v' represents a cell that contains a bird.
-   The character '.' represents an empty cell.

Return the number of possible sets of geese in the cage, modulo
1,000,000,007. Note that for some of the test cases there can be no
possible sets of geese.

Definition
----------

### Filename

GooseInZooDivTwo.py

### Signature

int count(String[] field, int dist)

Constraints
-----------

-   **field** will contain between 1 and 50 elements, inclusive.
-   Each element of **field** will contain between 1 and 50 characters,
    inclusive.
-   Each element of **field** will contain the same number of
    characters.
-   Each character of each element of **field** will be 'v' or '.'.
-   **dist** will be between 0 and 100, inclusive.

Examples
--------

-   count(['vvv'], 0) = 7
      -----------------------------------------------------------------------------------------------------------------------------------------
      There are seven possible sets of positions of geese: "ddg", "dgd", "dgg", "gdd", "gdg", "ggd", "ggg" ('g' are geese and 'd' are ducks).
      -----------------------------------------------------------------------------------------------------------------------------------------

-   count(['.'], 100) = 0
      ---------------------------------------------------------------------------
      The number of geese must be positive, but there are no birds in the cage.
      ---------------------------------------------------------------------------

-   count(['vvv'], 1) = 1
-   count(['v.v..................v............................',
    '.v......v..................v.....................v',
    '..v.....v....v.........v...............v......v...',
    '.........vvv...vv.v.........v.v..................v',
    '.....v..........v......v..v...v.......v...........',
    '...................vv...............v.v..v.v..v...',
    '.v.vv.................v..............v............',
    '..vv.......v...vv.v............vv.....v.....v.....',
    '....v..........v....v........v.......v.v.v........',
    '.v.......v.............v.v..........vv......v.....',
    '....v.v.......v........v.....v.................v..',
    '....v..v..v.v..............v.v.v....v..........v..',
    '..........v...v...................v..............v',
    '..v........v..........................v....v..v...',
    '....................v..v.........vv........v......',
    '..v......v...............................v.v......',
    '..v.v..............v........v...............vv.vv.',
    '...vv......v...............v.v..............v.....',
    '............................v..v.................v',
    '.v.............v.......v..........................',
    '......v...v........................v..............',
    '.........v.....v..............vv..................',
    '................v..v..v.........v....v.......v....',
    '........v.....v.............v......v.v............',
    '...........v....................v.v....v.v.v...v..',
    '...........v......................v...v...........',
    '..........vv...........v.v.....................v..',
    '.....................v......v............v...v....',
    '.....vv..........................vv.v.....v.v.....',
    '.vv.......v...............v.......v..v.....v......',
    '............v................v..........v....v....',
    '................vv...v............................',
    '................v...........v........v...v....v...',
    '..v...v...v.............v...v........v....v..v....',
    '......v..v.......v........v..v....vv..............',
    '...........v..........v........v.v................',
    'v.v......v................v....................v..',
    '.v........v................................v......',
    '............................v...v.......v.........',
    '........................vv.v..............v...vv..',
    '.......................vv........v.............v..',
    '...v.............v.........................v......',
    '....v......vv...........................v.........',
    '....vv....v................v...vv..............v..',
    '..................................................',
    'vv........v...v..v.....v..v..................v....',
    '.........v..............v.vv.v.............v......',
    '.......v.....v......v...............v.............',
    '..v..................v................v....v......',
    '.....v.....v.....................v.v......v.......'], 3) =
    797922654

