package rules;

public interface Rule {
    //Egy adott cella új állapotának meghatározása a szabály alapján.
    boolean apply(boolean[][] matrix, int row, int col);
}
