package ui;

import java.util.ArrayList;
import java.util.Scanner;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Team;
import model.League;
import model.Player;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

@ExcludeFromJacocoGeneratedReport
public class Main {

    public static void main(String[] args) {
        new LeagueGUI();
    }

    /*
     * COMMENTED OUT JUST IN CASE
     * public static void main(String[] args) {
     * 
     * League league = new League();
     * Team activeTeam = null;
     * 
     * while (true) {
     * 
     * printMenu(activeTeam);
     * String choice = readLetter();
     * 
     * switch (choice) {
     * 
     * case "C":
     * activeTeam = createTeamUI(league);
     * break;
     * 
     * case "P":
     * activeTeam = pickTeamUI(league);
     * break;
     * 
     * case "V":
     * viewTeamsUI(league);
     * break;
     * 
     * case "O": // LOAD
     * league = loadLeagueUI();
     * activeTeam = null;
     * break;
     * 
     * case "W": // SAVE
     * saveLeagueUI(league);
     * break;
     * 
     * case "Q":
     * System.out.println("Goodbye!");
     * return;
     * 
     * default:
     * if (activeTeam == null) {
     * System.out.println("Select or create a team first.");
     * break;
     * }
     * 
     * switch (choice) {
     * case "A":
     * addPlayerUI(activeTeam);
     * break;
     * case "R":
     * recordStatsUI(activeTeam);
     * break;
     * case "S":
     * showPlayerUI(activeTeam);
     * break;
     * case "T":
     * showTeamTotalsUI(activeTeam);
     * break;
     * case "L":
     * System.out.println(activeTeam.rosterString());
     * break;
     * default:
     * System.out.println("Invalid choice.");
     * }
     * }
     * }
     * }
     * // EFFECTS: displays the main menu and shows the currently active team (if
     * any)
     * private static void printMenu(Team activeTeam) {
     * System.out.println("\n=== Basketball Stat Tracker ===");
     * System.out.println("Active Team: " + (activeTeam == null ? "(none)" :
     * activeTeam.getName()));
     * 
     * System.out.println("[C] Create team");
     * System.out.println("[P] Pick team");
     * System.out.println("[V] View teams");
     * System.out.println("[A] Add player");
     * System.out.println("[R] Record stats");
     * System.out.println("[S] Show player stats");
     * System.out.println("[T] Show team totals");
     * System.out.println("[L] List roster");
     * System.out.println("[O] Load league from file");
     * System.out.println("[W] Save league to file");
     * System.out.println("[Q] Quit");
     * System.out.print("Choice: ");
     * }
     * 
     * // EFFECTS: attempts to load a league from JSON_STORE;
     * // if successful, prints a confirmation and returns the loaded league;
     * // otherwise prints an error message and returns a new empty league
     * private static League loadLeagueUI() {
     * JsonReader reader = new JsonReader(JSON_STORE);
     * try {
     * League loaded = reader.read();
     * System.out.println("League loaded from " + JSON_STORE);
     * return loaded;
     * } catch (IOException e) {
     * System.out.println("Unable to load league: " + e.getMessage());
     * return new League();
     * }
     * }
     * // REQUIRES: league is not null
     * // MODIFIES: the file at data/league.json
     * // EFFECTS: attempts to save the given league to data/league.json;
     * // prints a confirmation if successful, otherwise prints an error message
     * private static void saveLeagueUI(League league) {
     * JsonWriter writer = new JsonWriter(JSON_STORE);
     * try {
     * writer.open();
     * writer.write(league);
     * writer.close();
     * System.out.println("League saved to " + JSON_STORE);
     * } catch (IOException e) {
     * System.out.println("Unable to save league: " + e.getMessage());
     * }
     * }
     * 
     * // EFFECTS: reads a line from the user and returns the first character as an
     * uppercase String;
     * // returns the empty string if the user enters an empty line
     * private static String readLetter() {
     * String input = sc.nextLine().trim().toUpperCase();
     * if (input.length() == 0)
     * return "";
     * return input.substring(0, 1);
     * }
     * 
     * // REQUIRES: prompt is not null
     * // EFFECTS: repeatedly prompts the user until they enter a valid integer;
     * // returns the integer entered
     * private static int readInt(String prompt) {
     * while (true) {
     * System.out.print(prompt);
     * try {
     * return Integer.parseInt(sc.nextLine());
     * } catch (NumberFormatException e) {
     * System.out.println("Enter a valid number.");
     * }
     * }
     * }
     * 
     * // REQUIRES: league is not null
     * // EFFECTS: prompts user for a team name;
     * // if a team with that name can be added, adds it to league, prints
     * confirmation,
     * // and returns the created team;
     * // otherwise prints an error message and returns null
     * private static Team createTeamUI(League league) {
     * System.out.print("Enter team name: ");
     * String name = sc.nextLine();
     * 
     * if (!league.addTeam(name)) {
     * System.out.println("Team already exists or invalid name.");
     * return null;
     * }
     * 
     * System.out.println("Team created!");
     * return league.getTeamByName(name);
     * }
     * 
     * // REQUIRES: league is not null
     * // EFFECTS: if league has no teams, prints a message and returns null;
     * // otherwise displays teams and prompts user to choose one by index;
     * // returns the selected team, or null if selection is invalid
     * private static Team pickTeamUI(League league) {
     * 
     * if (!league.hasTeams()) {
     * System.out.println("No teams available.");
     * return null;
     * }
     * 
     * ArrayList<Team> teams = league.getTeams();
     * 
     * System.out.println("\n=== Teams ===");
     * for (int i = 0; i < teams.size(); i++) {
     * System.out.println((i + 1) + ") " + teams.get(i).getName());
     * }
     * 
     * int choice = readInt("Select team number: ");
     * Team selected = league.getTeamByIndex(choice - 1);
     * 
     * if (selected == null) {
     * System.out.println("Invalid selection.");
     * return null;
     * }
     * 
     * System.out.println("Active team set to: " + selected.getName());
     * return selected;
     * }
     * 
     * // REQUIRES: league is not null
     * // EFFECTS: prints all teams in the league, or prints a message if there are
     * none
     * private static void viewTeamsUI(League league) {
     * if (!league.hasTeams()) {
     * System.out.println("No teams yet.");
     * return;
     * }
     * 
     * for (Team t : league.getTeams()) {
     * System.out.println("- " + t.getName());
     * }
     * }
     * 
     * // REQUIRES: team is not null
     * // MODIFIES: team
     * // EFFECTS: prompts user for a player's name and jersey number;
     * // if the jersey number is not already taken, adds a new player to the team
     * and prints confirmation;
     * // otherwise prints an error message and does not modify team
     * private static void addPlayerUI(Team team) {
     * System.out.print("Player name: ");
     * String name = sc.nextLine();
     * 
     * int number = readInt("Jersey number: ");
     * 
     * if (team.getPlayerByNumber(number) != null) {
     * System.out.println("Number already taken.");
     * return;
     * }
     * 
     * team.addPlayer(new Player(name, number));
     * System.out.println("Player added.");
     * }
     * 
     * // REQUIRES: team is not null
     * // MODIFIES: StatLine of player
     * // EFFECTS: if team has no players, prints a message and returns;
     * // otherwise prompts user to select a player by jersey number and allows
     * adding stat amounts;
     * // continues until user chooses to go back
     * private static void recordStatsUI(Team team) {
     * 
     * if (team.getRoster().isEmpty()) {
     * System.out.println("No players on this team.");
     * return;
     * }
     * 
     * System.out.println(team.rosterString());
     * int number = readInt("Enter jersey number: ");
     * 
     * Player p = team.getPlayerByNumber(number);
     * 
     * if (p == null) {
     * System.out.println("Player not found.");
     * return;
     * }
     * 
     * while (true) {
     * System.out.
     * println("[P] Points  [A] Assists  [R] Rebounds  [S] Steals  [B] Blocks  [T] Turnovers  [X] Back"
     * );
     * String choice = readLetter();
     * 
     * if (choice.equals("X"))
     * break;
     * 
     * int amount = readInt("Add how many? ");
     * 
     * switch (choice) {
     * case "P":
     * p.getStatLine().addPoints(amount);
     * break;
     * case "A":
     * p.getStatLine().addAssists(amount);
     * break;
     * case "R":
     * p.getStatLine().addRebounds(amount);
     * break;
     * case "S":
     * p.getStatLine().addSteals(amount);
     * break;
     * case "B":
     * p.getStatLine().addBlocks(amount);
     * break;
     * case "T":
     * p.getStatLine().addTurnovers(amount);
     * break;
     * default:
     * System.out.println("Invalid stat.");
     * }
     * 
     * System.out.println("Updated: " + p.getStatLine());
     * }
     * }
     * 
     * // REQUIRES: team is not null
     * // EFFECTS: prompts user for a jersey number and prints that player's stats
     * if found;
     * // otherwise prints an error message
     * private static void showPlayerUI(Team team) {
     * int number = readInt("Enter jersey number: ");
     * Player p = team.getPlayerByNumber(number);
     * 
     * if (p == null) {
     * System.out.println("Player not found.");
     * return;
     * }
     * 
     * System.out.println(p);
     * }
     * 
     * // REQUIRES: team is not null
     * // EFFECTS: prints the total accumulated stats for the given team
     * private static void showTeamTotalsUI(Team team) {
     * System.out.println("=== " + team.getName() + " Totals ===");
     * System.out.println(team.teamTotals());
     * }
     */
}