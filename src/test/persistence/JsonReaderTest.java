package persistence;

import model.League;
import model.Player;
import model.Team;


import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        assertThrows(IOException.class, reader::read);
    }

    @Test
    void testReaderEmptyLeague() {
        JsonReader reader = new JsonReader("./data/testReaderEmpty.json");
        try {
            League league = reader.read();
            assertFalse(league.hasTeams());
            assertEquals(0, league.getTeams().size());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testReaderGeneralLeague() {
        JsonReader reader = new JsonReader("./data/testReaderLeagueWithStuff.json");
        try {
            League league = reader.read();
            assertTrue(league.hasTeams());
            assertEquals(1, league.getTeams().size());

            Team warriors = league.getTeamByName("warriors");
            assertNotNull(warriors);
            assertEquals("Warriors", warriors.getName());
            assertEquals(1, warriors.getRoster().size());

            Player steph = warriors.getPlayerByNumber(30);
            assertNotNull(steph);
            assertEquals("Steph", steph.getName());

            assertEquals(12, steph.getStatLine().getPoints());
            assertEquals(3, steph.getStatLine().getAssists());
            assertEquals(2, steph.getStatLine().getRebounds());
            assertEquals(1, steph.getStatLine().getSteals());
            assertEquals(0, steph.getStatLine().getBlocks());
            assertEquals(4, steph.getStatLine().getTurnovers());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
