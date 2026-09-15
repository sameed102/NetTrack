package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.EventLog;
import model.League;
import model.Player;
import model.Team;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.Event;

import java.awt.*;

public class LeagueGUI extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/league.json";

    private League league;

    private DefaultListModel<Team> teamModel;
    private DefaultListModel<Player> playerModel;

    private JList<Team> teamList;
    private JList<Player> playerList;

    private JButton addTeamButton;
    private JButton addPlayerButton;
    private JButton totalsButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton recordStatsButton;

    private JLabel statsLabel;

    // EFFECTS: constructs GUI, initializes components, and displays window
    public LeagueGUI() {
        super("Basketball Stat Tracker");

        league = new League();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());

        setupLists();
        setupButtons();
        setupStatsLabel();

        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printEventLog();
            }
        });

        setVisible(true);

    }

    // EFFECTS: prints all logged events to console
    private void printEventLog() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes team and player lists and adds them to the GUI
    private void setupLists() {
        teamModel = new DefaultListModel<>();
        playerModel = new DefaultListModel<>();
        teamList = new JList<>(teamModel);
        playerList = new JList<>(playerModel);
        add(new JScrollPane(teamList), BorderLayout.WEST);
        add(new JScrollPane(playerList), BorderLayout.CENTER);
        teamList.addListSelectionListener(e -> updatePlayers());
    }

    // MODIFIES: this
    // EFFECTS: initializes and adds a label used to display team stats
    private void setupStatsLabel() {
        statsLabel = new JLabel("Select a team to view stats");
        statsLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(statsLabel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons, assigns action listeners, and adds them to the
    // GUI
    private void setupButtons() {
        JPanel panel = new JPanel();

        addTeamButton = new JButton("Add Team");
        addPlayerButton = new JButton("Add Player");
        totalsButton = new JButton("Team Totals");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        recordStatsButton = new JButton("Record Stats");

        addTeamButton.addActionListener(this);
        addPlayerButton.addActionListener(this);
        totalsButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        recordStatsButton.addActionListener(this);

        panel.add(addTeamButton);
        panel.add(addPlayerButton);
        panel.add(totalsButton);
        panel.add(saveButton);
        panel.add(loadButton);
        panel.add(recordStatsButton);

        add(panel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: updates the player list to display players from the selected team;
    // if no team is selected, clears the player list
    private void updatePlayers() {
        playerModel.clear();

        Team selected = teamList.getSelectedValue();

        if (selected != null) {
            for (Player p : selected.getRoster()) {
                playerModel.addElement(p);
            }

            statsLabel.setText(
                    "<html>Team: " + selected.getName() + "<br>" +
                            selected.teamTotals().toString() +
                            "</html>");
        } else {
            statsLabel.setText("Select a team to view stats");
        }
    }

    // REQUIRES: e is not null
    // MODIFIES: this, league, selected team (if applicable)
    // EFFECTS: responds to button clicks by performing the corresponding action
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addTeamButton) {
            addTeamUI();
        }

        if (e.getSource() == addPlayerButton) {
            addPlayerUI();
        }

        if (e.getSource() == totalsButton) {
            showTeamTotalsUI();
        }

        if (e.getSource() == saveButton) {
            saveLeagueUI();
        }

        if (e.getSource() == loadButton) {
            loadLeagueUI();
        }

        if (e.getSource() == recordStatsButton) {
            recordStatsUI();
        }
    }

    // MODIFIES: this, league
    // EFFECTS: prompts user for a team name;
    // if a team with that name can be added, adds it and updates display;
    // otherwise shows an error message
    private void addTeamUI() {
        String name = JOptionPane.showInputDialog("Enter team name:");

        if (name == null || !league.addTeam(name)) {
            JOptionPane.showMessageDialog(this, "Team already exists or invalid name.");
            return;
        }

        teamModel.addElement(league.getTeamByName(name));
    }

    // REQUIRES: a team is selected
    // MODIFIES: selected team, this
    // EFFECTS: prompts user for player info;
    // if jersey number is not taken, adds player and updates display;
    // otherwise shows an error message
    private void addPlayerUI() {
        Team selected = teamList.getSelectedValue();

        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a team first.");
            return;
        }

        String name = JOptionPane.showInputDialog("Player name:");
        String numberStr = JOptionPane.showInputDialog("Jersey number:");

        try {
            int number = Integer.parseInt(numberStr);

            if (selected.getPlayerByNumber(number) != null) {
                JOptionPane.showMessageDialog(this, "Number already taken.");
                return;
            }

            Player p = new Player(name, number);
            selected.addPlayer(p);
            playerModel.addElement(p);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number.");
        }
    }

    // REQUIRES: a team is selected
    // EFFECTS: displays the total accumulated stats for the selected team
    private void showTeamTotalsUI() {
        Team selected = teamList.getSelectedValue();

        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a team first.");
            return;
        }

        JOptionPane.showMessageDialog(this, selected.teamTotals().toString());
    }

    // REQUIRES: league is not null
    // MODIFIES: the file at data/league.json
    // EFFECTS: attempts to save the current league to file;
    // if successful, shows confirmation; otherwise shows an error message
    private void saveLeagueUI() {
        JsonWriter writer = new JsonWriter(JSON_STORE);

        try {
            writer.open();
            writer.write(league);
            writer.close();
            JOptionPane.showMessageDialog(this, "League saved to " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to save league: " + e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: attempts to load a league from file;
    // if successful, replaces current league and updates display;
    // otherwise shows an error message
    private void loadLeagueUI() {
        JsonReader reader = new JsonReader(JSON_STORE);

        try {
            league = reader.read();

            teamModel.clear();
            for (Team t : league.getTeams()) {
                teamModel.addElement(t);
            }

            playerModel.clear();
            statsLabel.setText("Select a team to view stats");

            JOptionPane.showMessageDialog(this, "League loaded from " + JSON_STORE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to load league: " + e.getMessage());
        }
    }

    // REQUIRES: a team and player are selected
    // MODIFIES: selected player's StatLine, this
    // EFFECTS: prompts user to select a stat type and amount, then updates that
    // stat
    private void recordStatsUI() {
        Team team = teamList.getSelectedValue();
        Player player = playerList.getSelectedValue();
        if (team == null || player == null) {
            JOptionPane.showMessageDialog(this, "Select a team and player first.");
            return;
        }

        String[] options = { "Points", "Assists", "Rebounds", "Steals", "Blocks", "Turnovers" };

        String choice = (String) JOptionPane.showInputDialog(
                this,
                "Select stat to update:",
                "Record Stats",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == null) {
            return;
        }

        String amountStr = JOptionPane.showInputDialog("Enter amount:");

        try {
            int amount = Integer.parseInt(amountStr);

            if (choice.equals("Points")) {
                player.getStatLine().addPoints(amount, player.getName());
            } else if (choice.equals("Assists")) {
                player.getStatLine().addAssists(amount, player.getName());
            } else if (choice.equals("Rebounds")) {
                player.getStatLine().addRebounds(amount, player.getName());
            } else if (choice.equals("Steals")) {
                player.getStatLine().addSteals(amount, player.getName());
            } else if (choice.equals("Blocks")) {
                player.getStatLine().addBlocks(amount, player.getName());
            } else if (choice.equals("Turnovers")) {
                player.getStatLine().addTurnovers(amount, player.getName());
            }

            updatePlayers();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number.");
        }
    }

}
