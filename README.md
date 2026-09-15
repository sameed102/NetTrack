# Personal Project: Basketball Team & Player Performance Tracker

## Summary and Introduction

This desktop application allows users to **create basketball teams**, **manage player rosters**, and **track performance statistics** for players and teams across multiple games. Users will be able record game statistics such as points, assists, rebounds, fouls, steals, blocks, turnovers, field goals, and 3-points. The program focuses on organizing and analyzing basketball statistics in a clear and meaningful way. This project is intended for coaches, team managers, or basketball enthusiasts who want a way to organize and monitor player and team statistics over time.

This project is of particular interest to me because of my background in basketball. I grew up playing and watching basketball, so being able to combine my interest in both basketball and computer science is a dream come true for me. This will be an influential learning opportunity for me as I have always wondered what it takes to create statistic platforms like ESPN, although mine will be at an extremely smaller scale.

## User Stories

- As a user, I want to be able to create a new basketaball team and add it to my list of teams.
- As a user, I want to be able to add a player to a specific team and record their name, position, jersey number, height, and weight.
- As a user, I want to be able to view the list of players on a team's roster
- As a user, I want to be able to enter game statistics for each player. Stats including points, assists, rebounds, fouls, steals, blocks, turnovers, field goals and 3-pointers
- As a user, I want to be able to check averages for each player and whole teams
- As a user, I want to be able to trade or waive a player from a team

### Phase 2 User Stories

- As a user, I want to be able to save the entire state of my league (including all teams, players, and their statistics) to file.
- As a user, I want to be able to load my league from file and resume working exactly where I left off.

## Instructions for End User

- You can view the panel that displays the players that have already been added to a team by selecting a team from the list on the left side of the GUI. The players on that team will be displayed in the panel on the right side.
- You can add a player to a team by selecting a team and clicking the "Add Player" button, then entering the player's name and jersey number.
- You can update a player's statistics by selecting a team, selecting a player, and clicking the "Record Stats" button, then entering the desired stat value.
- You can locate my visual component by viewing the stats display at the top of the application window, which automatically updates to show the selected team's total statistics.
- You can save the state of my application by clicking the "Save" button, which stores the current league data in a JSON file.
- You can reload the state of my application by clicking the "Load" button, which restores the league data from the JSON file.

## Phase 4: Task 2

Sample events:

Sun Mar 29 15:12:37 PDT 2026<br>
Team added: Lakers<br>
Sun Mar 29 15:12:46 PDT 2026<br>
Player created: Lebron (#23)<br>
Sun Mar 29 15:12:46 PDT 2026<br>
Player Lebron added to team Lakers<br>
Sun Mar 29 15:12:54 PDT 2026<br>
Lebron: added 40 points<br>
Sun Mar 29 15:13:02 PDT 2026<br>
Lebron: added 12 assists<br>
Sun Mar 29 15:13:14 PDT 2026<br>
Lebron: added 10 rebounds<br>
Sun Mar 29 15:13:37 PDT 2026<br>
Player created: Luka (#77)<br>
Sun Mar 29 15:13:37 PDT 2026<br>
Player Luka added to team Lakers<br>
Sun Mar 29 15:13:43 PDT 2026<br>
Luka: added 20 points<br>
Sun Mar 29 15:13:52 PDT 2026<br>
Team added: Warriors<br>
Sun Mar 29 15:14:15 PDT 2026<br>
Player created: Curry (#30)<br>
Sun Mar 29 15:14:15 PDT 2026<br>
Player Curry added to team Warriors<br>
Sun Mar 29 15:14:21 PDT 2026<br>
Curry: added 30 points

## Phase 4: Task 3

# Phase 4: Task 3

One possible refactoring to improve the design of this application would be to introduce a separate controller class to handle interactions between the user interface and the model. Currently, the LeagueGUI class directly interacts with model classes such as League, Team, and Player. This results in the GUI having multiple responsibilities, including both handling user input and managing application logic. By introducing a controller layer, the GUI could delegate all model-related operations to the controller, which would improve separation of concerns and make the code easier to maintain and extend.

Another potential improvement would be to reduce duplication in the StatLine class by generalizing how statistics are updated. Currently, each statistic (points, assists, rebounds, etc.) has its own method, which leads to repetitive code. A refactoring could involve using a more flexible structure, such as a map of stat types to values or a single method that updates stats based on a parameter. This would make the class more scalable and easier to modify if additional statistics were added in the future. However, this change may reduce readability, so it represents a tradeoff between simplicity and extensibility.