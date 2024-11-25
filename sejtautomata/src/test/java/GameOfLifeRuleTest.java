import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import rules.Rule;
import rules.GameOfLifeRule;

public class GameOfLifeRuleTest {
    @Test
    public void testApply() {
        boolean[][] matrix = {
                {false, true, false},
                {true, true, false},
                {false, false, false}
        };

        Rule rule = new GameOfLifeRule();
        assertTrue(rule.apply(matrix, 1, 1)); // A cella túléli (2 szomszéd)
        assertTrue(rule.apply(matrix, 0, 0)); // A cella halott marad
    }
}
