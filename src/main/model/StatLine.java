package model;

import org.json.JSONObject;

// Stats of a player
public class StatLine {

    private int points;
    private int assists;
    private int rebounds;
    private int steals;
    private int blocks;
    private int turnovers;

    // EFFECTS: creates empty StatLine where all stats are 0
    public StatLine() {
        points = 0;
        assists = 0;
        rebounds = 0;
        steals = 0;
        blocks = 0;
        turnovers = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds stats from other to this
    public void addAll(StatLine other) {
        this.points += other.points;
        this.assists += other.assists;
        this.rebounds += other.rebounds;
        this.steals += other.steals;
        this.blocks += other.blocks;
        this.turnovers += other.turnovers;
    }

    // EFFECTS: returns this stat line as JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("points", points);
        json.put("assists", assists);
        json.put("rebounds", rebounds);
        json.put("steals", steals);
        json.put("blocks", blocks);
        json.put("turnovers", turnovers);
        return json;
    }

    // MODIFIES: this
    // EFFECTS: adds amount to points
    public void addPoints(int amount, String name) {
        points += amount;
        EventLog.getInstance().logEvent(new Event(name + ": added " + amount + " points"));
    }

    // MODIFIES: this
    // EFFECTS: adds amount to assists
    public void addAssists(int amount, String name) {
        assists += amount;
        EventLog.getInstance().logEvent(new Event(name + ": added " + amount + " assists"));
    }

    // MODIFIES: this
    // EFFECTS: adds amount to rebounds
    public void addRebounds(int amount, String name) {
        rebounds += amount;
        EventLog.getInstance().logEvent(new Event(name + ": added " + amount + " rebounds"));
    }

    // MODIFIES: this
    // EFFECTS: adds amount to steals
    public void addSteals(int amount, String name) {
        steals += amount;
        EventLog.getInstance().logEvent(new Event(name + ": added " + amount + " steals"));
    }

    // MODIFIES: this
    // EFFECTS: adds amount to blocks
    public void addBlocks(int amount, String name) {
        blocks += amount;
        EventLog.getInstance().logEvent(new Event(name + ": added " + amount + " blocks"));
    }

    // MODIFIES: this
    // EFFECTS: adds amount to turnovers
    public void addTurnovers(int amount, String name) {
        turnovers += amount;
        EventLog.getInstance().logEvent(new Event(name + ": added " + amount + " turnovers"));
    }

    public int getPoints() {
        return points;
    }

    public int getAssists() {
        return assists;
    }

    public int getRebounds() {
        return rebounds;
    }

    public int getSteals() {
        return steals;
    }

    public int getBlocks() {
        return blocks;
    }

    public int getTurnovers() {
        return turnovers;
    }

    @Override
    public String toString() {
        return "PTS=" + points
                + " AST=" + assists
                + " REB=" + rebounds
                + " STL=" + steals
                + " BLK=" + blocks
                + " TO=" + turnovers;
    }

}