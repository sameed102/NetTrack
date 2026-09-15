package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

// Team of players
public class Team {

    private String name;
    private ArrayList<Player> roster;

    // EFFECTS: creates team with name and no players
    public Team(String name) {
        this.name = name;
        this.roster = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Player> getRoster() {
        return roster;
    }

    // REQUIRES: p is not null
    // MODIFIES: this
    // EFFECTS: adds player p to the roster
    public void addPlayer(Player p) {
        roster.add(p);
        EventLog.getInstance().logEvent(new Event("Player " + p.getName() + " added to team " + name));
    }

    // EFFECTS: returns the player with the given jersey number
    // or null if no such player exists
    public Player getPlayerByNumber(int number) {
        for (Player p : roster) {
            if (p.getNumber() == number) {
                return p;
            }
        }
        return null;
    }

    // EFFECTS: returns a Statline representing the total stats
    // accumulated by all players on this team
    public StatLine teamTotals() {
        StatLine totals = new StatLine();
        for (Player p : roster) {
            totals.addAll(p.getStatLine());
        }
        return totals;
    }

    // EFFECTS: returns a String listing all players on the roster
    public String rosterString() {
        if (roster.isEmpty()) {
            return "(No players)";
        }

        String result = "=== Roster: " + name + " ===\n";
        for (Player p : roster) {
            result += p.getName() + " (#" + p.getNumber() + ")\n";
        }

        return result;
    }

    // EFFECTS: returns this team as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("roster", rosterToJson());
        return json;
    }

    // EFFECTS: returns roster as JSONArray
    private JSONArray rosterToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p : roster) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: return name of team (used for UI)
    @Override
    public String toString() {
        return name;
    }
}
