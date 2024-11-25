import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import rules.Rule;
import rules.ReplicatorRule;

public class ReplicatorRuleTest {

    @Test
    public void testApply() {
        boolean[][] matrix = {
                {false, true, false},
                {true, false, true},
                {false, true, false}
        };

        Rule rule = new ReplicatorRule();

        // Halott cellák életre kelnek
        assertFalse(rule.apply(matrix, 1, 1)); // Középső cella: 4 élő szomszéd (nem kel életre)
        assertFalse(rule.apply(matrix, 0, 0)); // Bal felső cella: 2 élő szomszéd (életre kel)
        assertFalse(rule.apply(matrix, 1, 0)); // Bal középső cella: 0 élő szomszéd (elhal)

        // Élő cellák életben maradnak
        assertFalse(rule.apply(matrix, 0, 1)); // Felső középső cella: 0 élő szomszéd (elhal)
        assertFalse(rule.apply(matrix, 2, 2)); // Jobb alsó cella: 2 élő szomszéd (meghal)
    }
}
