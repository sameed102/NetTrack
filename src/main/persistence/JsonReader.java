package persistence;

import model.League;
import model.Team;
import model.Player;
import model.StatLine;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Represents a reader that reads league data from a JSON file.
 */
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads league from file and returns it
    // throws IOException if an error occurs reading data
    public League read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLeague(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source))) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses league from JSON object and returns it
    private League parseLeague(JSONObject jsonObject) {
        League league = new League();
        addTeams(league, jsonObject);
        return league;
    }

    // MODIFIES: league
    // EFFECTS: parses teams from JSON and adds them to league
    private void addTeams(League league, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");

        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(league, nextTeam);
        }
    }

    // MODIFIES: league
    // EFFECTS: parses a single team and adds it to league
    private void addTeam(League league, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        league.addTeam(name);
        Team team = league.getTeamByName(name);
        addPlayers(team, jsonObject);
    }

    // MODIFIES: team
    // EFFECTS: parses players and adds them to team
    private void addPlayers(Team team, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("roster");

        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(team, nextPlayer);
        }
    }

    // MODIFIES: team
    // EFFECTS: parses a single player and adds to team
    private void addPlayer(Team team, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int number = jsonObject.getInt("number");

        Player player = new Player(name, number);

        JSONObject stats = jsonObject.getJSONObject("statLine");
        StatLine statLine = player.getStatLine();

        statLine.addPoints(stats.getInt("points"), player.getName());
        statLine.addAssists(stats.getInt("assists"), player.getName());
        statLine.addRebounds(stats.getInt("rebounds"), player.getName());
        statLine.addSteals(stats.getInt("steals"), player.getName());
        statLine.addBlocks(stats.getInt("blocks"), player.getName());
        statLine.addTurnovers(stats.getInt("turnovers"), player.getName());

        team.addPlayer(player);
    }
}