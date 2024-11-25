package rules;

import java.util.List;

public interface Rule {
    //Egy adott cella új állapotának meghatározása a szabály alapján.
    boolean apply(List<List<Boolean>> matrix, int row, int col);
}
