-- Tabelas de Lookup

CREATE TABLE user_access_level_tb (
    access_level_id SERIAL PRIMARY KEY,
    access_level_name VARCHAR(50) NOT NULL UNIQUE
);


CREATE TABLE bet_state_tb (
    bet_state_id SERIAL PRIMARY KEY,
    bet_state_name VARCHAR(50) NOT NULL UNIQUE
);


CREATE TABLE match_state_tb (
    match_state_id SERIAL PRIMARY KEY,
    match_state_name VARCHAR(50) NOT NULL UNIQUE
);


CREATE TABLE match_result_tb (
    match_result_id SERIAL PRIMARY KEY,
    match_result_name VARCHAR(50) NOT NULL UNIQUE
);


-- Tabelas Principais

CREATE TABLE user_tb (
    user_id SERIAL PRIMARY KEY,
    access_level_id INT NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    user_email VARCHAR(100) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL, 
    FOREIGN KEY (access_level_id) REFERENCES user_access_level_tb (access_level_id)
);


CREATE TABLE bet_tb (
    bet_id SERIAL PRIMARY KEY,
    bet_state_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (bet_state_id) REFERENCES bet_state_tb (bet_state_id),
    FOREIGN KEY (user_id) REFERENCES user_tb (user_id) ON DELETE CASCADE
);


CREATE TABLE bid_tb (
    bid_id SERIAL PRIMARY KEY,
    bid_paid_value FLOAT NOT NULL,
    bid_guess INT NOT NULL,
    bet_id INT NOT NULL,
    match_id INT NOT NULL,
    FOREIGN KEY (bet_id) REFERENCES bet_tb (bet_id) ON DELETE CASCADE,
    FOREIGN KEY (match_id) REFERENCES match_tb (match_id) ON DELETE CASCADE
);


CREATE TABLE team_tb (
    team_id SERIAL PRIMARY KEY,
    team_abbreviation VARCHAR(10) NOT NULL,
    team_name VARCHAR(100) NOT NULL
);


CREATE TABLE match_tb (
    match_id SERIAL PRIMARY KEY,
    match_state_id INT NOT NULL,
    match_odd FLOAT NOT NULL,
    match_result_id INT,
    match_home_team INT NOT NULL,
    match_away_team INT NOT NULL,
    FOREIGN KEY (match_state_id) REFERENCES match_state_tb (match_state_id),
    FOREIGN KEY (match_result_id) REFERENCES match_result_tb (match_result_id),
    FOREIGN KEY (match_home_team) REFERENCES team_tb (team_id),
    FOREIGN KEY (match_away_team) REFERENCES team_tb (team_id),
    CONSTRAINT check_teams CHECK (match_home_team <> match_away_team)
);

-- Povoamento

INSERT INTO user_access_level_tb (access_level_name) VALUES
('admin'),
('user');

INSERT INTO bet_state_tb (bet_state_name) VALUES
('Active'),
('Completed'),
('Cancelled');

INSERT INTO match_state_tb (match_state_name) VALUES
('Scheduled'),
('In Progress'),
('Finished');

INSERT INTO match_result_tb (match_result_name) VALUES
('Home Win'),
('Away Win'),
('Draw');

-- Times
INSERT INTO team_tb (team_abbreviation, team_name) VALUES
('FCB', 'FC Barcelona'),
('RM', 'Real Madrid'),
('MNU', 'Manchester United'),
('LIV', 'Liverpool'),
('BAY', 'Bayern Munich'),
('PSG', 'Paris Saint-Germain');

-- Usuários
INSERT INTO user_tb (access_level_id, user_name, user_email, user_password) VALUES
(1, 'Admin User', 'admin@example.com', 'adminpassword'),
(2, 'John Doe', 'john.doe@example.com', 'johnpassword'),
(2, 'Jane Smith', 'jane.smith@example.com', 'janepassword');

-- Partidas
INSERT INTO match_tb (match_state_id, match_odd, match_result_id, match_home_team, match_away_team) VALUES
(1, 1.75, NULL, 1, 2), -- FC Barcelona vs Real Madrid
(1, 2.10, NULL, 3, 4), -- Manchester United vs Liverpool
(2, 1.90, NULL, 5, 6), -- Bayern Munich vs Paris Saint-Germain
(3, 2.00, 1, 1, 2),    -- FC Barcelona vs Real Madrid (Home Win)
(3, 2.20, 2, 3, 4);    -- Manchester United vs Liverpool (Away Win)

-- Apostas
INSERT INTO bet_tb (bet_state_id, user_id) VALUES
(1, 2), -- John Doe's active bet
(2, 3); -- Jane Smith's completed bet

-- Lances
INSERT INTO bid_tb (bid_paid_value, bid_guess, bet_id, match_id) VALUES
(100.00, 1, 1, 1), -- John Doe bets 100 on FC Barcelona to win against Real Madrid
(50.00, 2, 2, 2),  -- Jane Smith bets 50 on Liverpool to win against Manchester United
(200.00, 1, 1, 4); -- John Doe bets 200 on FC Barcelona to win against Real Madrid (already completed)
