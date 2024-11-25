import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import rules.Rule;
import rules.GameOfLifeRule;

import java.util.List;

public class GameOfLifeRuleTest {
    @Test
    public void testApply() {
        List<List<Boolean>> matrix = List.of(List.of(false, true, false), List.of(true, true, false), List.of(false, false, false));

        Rule rule = new GameOfLifeRule();
        assertTrue(rule.apply(matrix, 1, 1)); // A cella túléli (2 szomszéd)
        assertTrue(rule.apply(matrix, 0, 0)); // A cella halott marad
    }
}
