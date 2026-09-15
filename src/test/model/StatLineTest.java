package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatLineTest {

    @Test
    void newStatLineStartsAtZero() {
        StatLine s = new StatLine();
        assertEquals(0, s.getPoints());
        assertEquals(0, s.getAssists());
        assertEquals(0, s.getRebounds());
        assertEquals(0, s.getSteals());
        assertEquals(0, s.getBlocks());
        assertEquals(0, s.getTurnovers());
    }

    @Test
    void addMethodsIncreaseStats() {
        StatLine s = new StatLine();
        s.addPoints(12, "");
        s.addAssists(3, "");
        s.addRebounds(8, "");
        s.addSteals(2, "");
        s.addBlocks(1, "");
        s.addTurnovers(4, "");

        assertEquals(12, s.getPoints());
        assertEquals(3, s.getAssists());
        assertEquals(8, s.getRebounds());
        assertEquals(2, s.getSteals());
        assertEquals(1, s.getBlocks());
        assertEquals(4, s.getTurnovers());
    }

    @Test
    void addAllCombinesTwoStatLines() {
        StatLine a = new StatLine();
        a.addPoints(10, "");
        a.addAssists(2, "");

        StatLine b = new StatLine();
        b.addPoints(5, "");
        b.addRebounds(7, "");
        b.addTurnovers(1, "");

        a.addAll(b);

        assertEquals(15, a.getPoints());
        assertEquals(2, a.getAssists());
        assertEquals(7, a.getRebounds());
        assertEquals(0, a.getSteals());
        assertEquals(0, a.getBlocks());
        assertEquals(1, a.getTurnovers());
    }

    @Test
    void toStringContainsAllFields() {
        StatLine s = new StatLine();
        s.addPoints(1, "");
        s.addAssists(2, "");
        s.addRebounds(3, "");
        s.addSteals(4, "");
        s.addBlocks(5, "");
        s.addTurnovers(6, "");

        String out = s.toString();
        assertTrue(out.contains("PTS=1"));
        assertTrue(out.contains("AST=2"));
        assertTrue(out.contains("REB=3"));
        assertTrue(out.contains("STL=4"));
        assertTrue(out.contains("BLK=5"));
        assertTrue(out.contains("TOV=6"));
    }
}