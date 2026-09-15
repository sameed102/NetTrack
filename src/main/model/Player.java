package model;

import org.json.JSONObject;

// Player with a StatLine, number, and name
public class Player {

    private String name;
    private int number;
    private StatLine statLine;

    // EFFECTS: creates player with new StatLine, name, and jersey number
    public Player(String name, int number) {
        this.name = name;
        this.number = number;
        this.statLine = new StatLine();
        EventLog.getInstance().logEvent(new Event("Player created: " + name + " (#" + number + ")"));
    }

    // EFFECTS: returns player name
    public String getName() {
        return name;
    }

    // EFFECTS: return player number
    public int getNumber() {
        return number;
    }

    // EFFECTS: returns player StatLine
    public StatLine getStatLine() {
        return statLine;
    }

    @Override
    public String toString() {
        return name + " (#" + number + ") " + statLine;
    }

    // EFFECTS: returns this player as JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("number", number);
        json.put("statLine", statLine.toJson());
        return json;
    }
}
