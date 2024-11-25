import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import rules.Rule;
import rules.SeedsRule;

public class SeedsRuleTest {

    @Test
    public void testApply() {
        boolean[][] matrix = {
                {false, true, false},
                {true, false, true},
                {false, false, false}
        };

        Rule rule = new SeedsRule();

        // Halott cellák életre kelnek
        assertFalse(rule.apply(matrix, 1, 1)); // Középső cella: 3 élő szomszéd (nem kel életre)
        assertTrue(rule.apply(matrix, 0, 0)); // Bal felső cella: 2 élő szomszéd (életre kel)

        // Élő cellák mindig meghalnak
        assertFalse(rule.apply(matrix, 0, 1)); // Felső középső cella: élő, de nincs S szabály
        assertFalse(rule.apply(matrix, 1, 0)); // Bal középső cella: élő, de nincs S szabály
    }
}
