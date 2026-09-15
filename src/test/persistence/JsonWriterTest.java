package persistence;

import model.League;
import model.Player;
import model.Team;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        // invalid because of null character in filename on most systems
        JsonWriter writer = new JsonWriter("./data/\0illegal.json");
        assertThrows(FileNotFoundException.class, writer::open);
    }

    @Test
    void testWriterEmptyLeague() {
        try {
            League league = new League();
            JsonWriter writer = new JsonWriter("./data/testWriterEmpty.json");
            writer.open();
            writer.write(league);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmpty.json");
            League loaded = reader.read();

            assertFalse(loaded.hasTeams());
            assertEquals(0, loaded.getTeams().size());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLeague() {
        try {
            League league = new League();
            assertTrue(league.addTeam("Warriors"));
            Team warriors = league.getTeamByName("warriors");

            Player steph = new Player("Steph", 30);
            steph.getStatLine().addPoints(12, steph.getName());
            steph.getStatLine().addAssists(3, steph.getName());
            steph.getStatLine().addRebounds(2, steph.getName());
            steph.getStatLine().addSteals(1, steph.getName());
            steph.getStatLine().addBlocks(0, steph.getName());
            steph.getStatLine().addTurnovers(4, steph.getName());

            warriors.addPlayer(steph);

            JsonWriter writer = new JsonWriter("./data/testWriterLeagueWithStuff.json");
            writer.open();
            writer.write(league);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterLeagueWithStuff.json");
            League loaded = reader.read();

            Team loadedWarriors = loaded.getTeamByName("WARRIORS");
            assertNotNull(loadedWarriors);
            Player loadedSteph = loadedWarriors.getPlayerByNumber(30);
            assertNotNull(loadedSteph);

            assertEquals("Steph", loadedSteph.getName());
            assertEquals(12, loadedSteph.getStatLine().getPoints());
            assertEquals(3, loadedSteph.getStatLine().getAssists());
            assertEquals(2, loadedSteph.getStatLine().getRebounds());
            assertEquals(1, loadedSteph.getStatLine().getSteals());
            assertEquals(0, loadedSteph.getStatLine().getBlocks());
            assertEquals(4, loadedSteph.getStatLine().getTurnovers());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
