package rules;

import java.util.List;

public class SeedsRule implements Rule {
    //A szabály alkalmazása
    @Override
    public boolean apply(List<List<Boolean>> matrix, int row, int col) {
        int aliveNeighbors = countAliveNeighbors(matrix, row, col);
        return !matrix.get(row).get(col) && aliveNeighbors == 2;
    }

    private int countAliveNeighbors(List<List<Boolean>> matrix, int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0){
                    continue;
                }
                int r = row + i;
                int c = col + j;
                if (r >= 0 && r < matrix.size() && c >= 0 && c < matrix.get(0).size() && matrix.get(r).get(c)) {
                    count++;
                }
            }
        }
        return count;
    }
}