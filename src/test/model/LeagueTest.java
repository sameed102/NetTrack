package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LeagueTest {

    @Test
    void newLeagueHasNoTeams() {
        League l = new League();
        assertFalse(l.hasTeams());
        assertTrue(l.getTeams().isEmpty());
    }

    @Test
    void addTeamAddsTeamAndPreventsDuplicatesIgnoringCase() {
        League l = new League();

        assertTrue(l.addTeam("Warriors"));
        assertFalse(l.addTeam("Warriors"));  
        assertFalse(l.addTeam("warriors"));   

        assertTrue(l.hasTeams());
        assertEquals(1, l.getTeams().size());
    }

    @Test
    void addTeamRejectsBlankName() {
        League l = new League();
        assertFalse(l.addTeam(""));
        assertFalse(l.addTeam("   "));
        assertFalse(l.addTeam(null));
        assertFalse(l.hasTeams());
    }

    @Test
    void getTeamByNameFindsIgnoringCase() {
        League l = new League();
        l.addTeam("Celtics");

        Team t1 = l.getTeamByName("Celtics");
        Team t2 = l.getTeamByName("cElTiCs");

        assertNotNull(t1);
        assertNotNull(t2);
        assertEquals("Celtics", t1.getName());
    }

    @Test
    void getTeamByIndexReturnsNullIfOutOfBounds() {
        League l = new League();
        l.addTeam("A");
        l.addTeam("B");

        assertNotNull(l.getTeamByIndex(0));
        assertNotNull(l.getTeamByIndex(1));

        assertNull(l.getTeamByIndex(-1));
        assertNull(l.getTeamByIndex(2));
        assertNull(l.getTeamByIndex(999));
    }
}