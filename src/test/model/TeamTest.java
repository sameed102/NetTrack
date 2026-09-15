package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {

    @Test
    void teamStartsWithEmptyRoster() {
        Team t = new Team("Lakers");
        assertEquals("Lakers", t.getName());
        assertNotNull(t.getRoster());
        assertTrue(t.getRoster().isEmpty());
        assertEquals("(No players)", t.rosterString());
    }

    @Test
    void addPlayerAddsToRoster() {
        Team t = new Team("Lakers");
        Player p = new Player("LeBron", 6);

        t.addPlayer(p);

        assertEquals(1, t.getRoster().size());
        assertSame(p, t.getRoster().get(0));
    }

    @Test
    void getPlayerByNumberFindsCorrectPlayer() {
        Team t = new Team("Lakers");
        Player a = new Player("A", 1);
        Player b = new Player("B", 2);

        t.addPlayer(a);
        t.addPlayer(b);

        assertSame(b, t.getPlayerByNumber(2));
        assertSame(a, t.getPlayerByNumber(1));
        assertNull(t.getPlayerByNumber(99));
    }

    @Test
    void teamTotalsSumsAllPlayersStats() {
        Team t = new Team("Lakers");

        Player p1 = new Player("P1", 1);
        p1.getStatLine().addPoints(10, p1.getName());
        p1.getStatLine().addAssists(2, p1.getName());

        Player p2 = new Player("P2", 2);
        p2.getStatLine().addPoints(5, p2.getName());
        p2.getStatLine().addRebounds(7, p2.getName());

        t.addPlayer(p1);
        t.addPlayer(p2);

        StatLine totals = t.teamTotals();

        assertEquals(15, totals.getPoints());
        assertEquals(2, totals.getAssists());
        assertEquals(7, totals.getRebounds());
        assertEquals(0, totals.getSteals());
        assertEquals(0, totals.getBlocks());
        assertEquals(0, totals.getTurnovers());
    }

    @Test
    void rosterStringShowsTeamNameAndPlayers() {
        Team t = new Team("Heat");
        t.addPlayer(new Player("Jimmy", 22));
        t.addPlayer(new Player("Bam", 13));

        String out = t.rosterString();
        assertTrue(out.contains("Roster: Heat"));
        assertTrue(out.contains("Jimmy (#22)"));
        assertTrue(out.contains("Bam (#13)"));
    }
}
