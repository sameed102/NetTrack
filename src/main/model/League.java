package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Event;
import model.EventLog;

// League of teams
public class League {

    private ArrayList<Team> teams;

    // EFFECTS: creates league with no teams
    public League() {
        teams = new ArrayList<>();
    }

    // REQUIRES: name is not null
    // MODIFIES: this
    // EFFECTS: if a team with the given name does not already exist,
    //      adds a new team with that name and returns true;
    //      otherwise false
    public boolean addTeam(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        if (getTeamByName(name) != null) {
            return false; // prevent duplicates
        }

        teams.add(new Team(name));
        EventLog.getInstance().logEvent(new Event("Team added: " + name));
        return true;
    }

    // EFFECTS: given name of team, returns team if in league,
    //          else returns null
    public Team getTeamByName(String name) {
        for (Team t : teams) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }

    // REQUIRES: 0 <= index < number of teams
    // EFFECTS: returns the team at the given index,
    //          or null if index is invalid
    public Team getTeamByIndex(int index) {
        if (index < 0 || index >= teams.size()) {
            return null;
        }
        return teams.get(index);
    }

    // EFFECTS: returns this league as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("teams", teamsToJson());
        return json;
    }

    // EFFECTS: returns teams in this league as a JSONArray
    private JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Team t : teams) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public boolean hasTeams() {
        return !teams.isEmpty();

    }
}
