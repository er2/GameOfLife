import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {

   GameOfLife board = new GameOfLife();

   static class MutableInt {
      int x;
   }

   @Test
   void countAndSet() {
      var m = new MutableInt();
      GameOfLife.Getter counter = (i, j, state) -> {
         if (state)
            m.x++;
      };
      board.get(counter);
      assertEquals(0, m.x);
      board.set((i, j, s) -> true);
      board.get(counter);
      assertEquals(8 * 8, m.x);
   }

   @Test
   void print() throws InterruptedException {
      System.out.println("\n\n");
      board.print();
      System.out.println("\n\n");
      board.set((i, j, s) -> {
         return i == j || i == 7 - j;
//         return (i == 3 || i == 4)
//              && (j == 3 || j == 4);
      });
      board.print();
      for (int x = 0; x < 50; x++) {
         System.out.println("\n\n");
         board = board.makeNextGeneration();
         board.print();
         Thread.sleep(1500);
      }
   }

}