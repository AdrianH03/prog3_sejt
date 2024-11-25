import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import rules.Rule;
import rules.SeedsRule;

import java.util.List;

public class SeedsRuleTest {

    @Test
    public void testApply() {
        List<List<Boolean>> matrix = List.of(List.of(false, true, false), List.of(true, false, true), List.of(false, false, false));

        Rule rule = new SeedsRule();

        // Halott cellák életre kelnek
        assertFalse(rule.apply(matrix, 1, 1)); // Középső cella: 3 élő szomszéd (nem kel életre)
        assertTrue(rule.apply(matrix, 0, 0)); // Bal felső cella: 2 élő szomszéd (életre kel)

        // Élő cellák mindig meghalnak
        assertFalse(rule.apply(matrix, 0, 1)); // Felső középső cella: élő, de nincs S szabály
        assertFalse(rule.apply(matrix, 1, 0)); // Bal középső cella: élő, de nincs S szabály
    }
}
