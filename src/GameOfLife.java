class GameOfLife {

   boolean[][] board = new boolean[8][8];

   void set(Setter setter) {
      for (int i = 0; i < 8; i++) {
         for (int j = 0; j < 8; j++) {
            board[i][j] = setter.set(i, j, board[i][j]);
         }
      }
   }

   void get(Getter getter) {
      set((i, j, s) -> {
         getter.get(i, j, s);
         return s;
      });
   }

   int countNeighbors(int i, int j) {
      var counter = new Getter() {
         int count;

         @Override
         public void get(int li, int lj, boolean state) {
            if (distance(i, j, li, lj) == 1 && board[li][lj])
               count++;
         }
      };
      get(counter);
      return counter.count;
   }

   int distance(int i, int j, int li, int lj) {
      return Math.max(
           Math.abs(i - li),
           Math.abs(j - lj));
   }

   void toggle(int i, int j) {
      board[i][j] = !board[i][j];
   }

   GameOfLife makeNextGeneration() {
      var n = new GameOfLife();
      n.set((i, j, s) -> {
         var alive = board[i][j];
         int c = countNeighbors(i, j);
         if (alive) {
            return c == 2 || c == 3;
         } else {
            return c == 3;
         }
      });
      return n;
   }

   void print() {
      get((i, j, s) -> {
         if (j == 0)
            System.out.println();
         System.out.print(s ? "■ " : "□ ");
      });
   }

   @FunctionalInterface
   interface Setter {
      boolean set(int i, int j, boolean state);
   }

   @FunctionalInterface
   interface Getter {
      void get(int i, int j, boolean state);
   }

}
