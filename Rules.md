# RULES #

![http://jkemik.googlecode.com/files/board.png](http://jkemik.googlecode.com/files/board.png)

https://drive.google.com/?tab=wo&authuser=0#folders/0B_2hSa-zO45OVVNuVUFWYmZ1Nkk

## A. Play Rules ##

  * **One Dot play**

> Players can only place one dot during their turn.

  * **Adjacent dot capture**

> Dots are placed where the grid lines intersect with each other, a valid capture should only be formed by adjacent **Dots**. Two **Dots** are adjacent if and only if they are located on the same board square.

  * **Nonempty Capture**

> A capture consists of a polygon of adjacent **Dots**, as defined in the second rule. These **Dots** must form a wall around one or more of the opponent's **Dots**. Therefore, a Cell can also be captured.

See the [StartGuide](GetStarted.md) for details on how to run the application.

## B. Scoring Rules ##

  * **Dot Rule**: One point is earned for every captured **Dot**. 1 **Dot** = 1 Point
  * **Cell Value Rule**: The value of a captured Cell equates the number of captured points within itself. Therefore the value of this **Cell** is also subtracted from the score of its former owner.
  * **Cell Wall Rule**: **Dots** that make the building blocks of a captured cell are counted as captured **Dots** for the player who is capturing the cell.
  * **Empty Cell Rule**: An empty capture is invalid

> SCORING FORMULA

> Supposed:
    * D the sum of captured **Dots**.
    * C the Sum of captured **Cell** values.

> _SCORE = D + C_