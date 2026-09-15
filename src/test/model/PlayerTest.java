package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void playerStoresNameAndNumber() {
        Player p = new Player("Sameed", 23);
        assertEquals("Sameed", p.getName());
        assertEquals(23, p.getNumber());
    }

    @Test
    void playerHasAStatLineByDefault() {
        Player p = new Player("A", 1);
        assertNotNull(p.getStatLine());
        assertEquals(0, p.getStatLine().getPoints());
    }

    @Test
    void playerToStringIncludesNameNumberAndStats() {
        Player p = new Player("Jordan", 23);
        p.getStatLine().addPoints(30, p.getName());

        String out = p.toString();
        assertTrue(out.contains("Jordan"));
        assertTrue(out.contains("#23"));
        assertTrue(out.contains("PTS=30"));
    }
}
