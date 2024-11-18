package rules;
import automatonSimulation.Rule;

public class ReplicatorRule implements Rule{
    @Override
    public boolean apply(boolean[][] matrix, int row, int col){
        int aliveNeighbors = countAliveNeighbors(matrix, row, col);
        return aliveNeighbors % 2 == 1;
    }

    private int countAliveNeighbors(boolean[][] matrix, int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue; // Középpont kihagyása
                }
                int r = row + i;
                int c = col + j;
                if (r >= 0 && r < matrix.length && c >= 0 && c < matrix[0].length && matrix[r][c]) {
                    count++;
                }
            }
        }
        return count;
    }
}
