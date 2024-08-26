-- Banco de dados: "bet-betina"

CREATE DATABASE IF NOT EXISTS 'bet-betina-prod';
USE 'bet-betina-prod';

CREATE TABLE team_tb (
    team_id SERIAL PRIMARY KEY,
    team_abbreviation VARCHAR(10) NOT NULL,
    team_name VARCHAR(100) NOT NULL
);

CREATE TABLE user_tb (
    user_id SERIAL PRIMARY KEY,
    user_access_level INT NOT NULL, -- 0 ou 1. 0 para usuário normal e 1 para admnistrador
    user_name VARCHAR(100) NOT NULL,
    user_email VARCHAR(100) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL,
    user_balance FLOAT NOT NULL,
    user_favorite_team_id INT,
    FOREIGN KEY (user_favorite_team_id) REFERENCES team_tb (team_id) ON DELETE SET NULL
);

CREATE TABLE bet_tb (
    bet_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    bet_state INT NOT NULL, -- 0 para cancelada, 1 para ativa, 2 para vitória e 3 para derrota
    FOREIGN KEY (user_id) REFERENCES user_tb (user_id) ON DELETE CASCADE
);

CREATE TABLE match_tb (
    match_id SERIAL PRIMARY KEY,
    match_result INT, -- 0 para ativa, 1 para vitória do time de casa, 2 para time visitante e 3 para empate
    match_state INT NOT NULL, -- 0 para finalizada, 1 para ativa
    match_home_team INT NOT NULL,
    match_away_team INT NOT NULL,
    match_odd_home FLOAT NOT NULL,
    match_odd_away FLOAT NOT NULL,
    match_odd_draw FLOAT NOT NULL,
    FOREIGN KEY (match_home_team) REFERENCES team_tb (team_id),
    FOREIGN KEY (match_away_team) REFERENCES team_tb (team_id),
    CONSTRAINT check_teams CHECK (match_home_team <> match_away_team)
);

CREATE TABLE bid_tb (
    bet_id INT NOT NULL,
    match_id INT NOT NULL,
    bid_guess INT NOT NULL,
    bid_paid_value FLOAT NOT NULL,
    PRIMARY KEY (bet_id, match_id),
    FOREIGN KEY (bet_id) REFERENCES bet_tb (bet_id) ON DELETE CASCADE,
    FOREIGN KEY (match_id) REFERENCES match_tb (match_id) ON DELETE CASCADE
);

-- times

INSERT INTO team_tb (team_abbreviation, team_name) VALUES
('FCB', 'Futebol Clube Barcelona'),
('RMD', 'Real Madrid'),
('LIV', 'Liverpool FC'),
('MNC', 'Manchester City'),
('PSG', 'Paris Saint-Germain'),
('JUV', 'Juventus FC'),
('BAY', 'Bayern Munich'),
('CHE', 'Chelsea FC'),
('ATM', 'Atlético Madrid'),
('DOR', 'Borussia Dortmund'),
('INT', 'Inter Milan'),
('ACM', 'AC Milan'),
('TOT', 'Tottenham Hotspur'),
('ARS', 'Arsenal FC'),
('MUN', 'Manchester United'),
('ROM', 'AS Roma'),
('NAP', 'Napoli'),
('BEN', 'Benfica'),
('POR', 'FC Porto'),
('LIL', 'Lille OSC');

-- Partidas

INSERT INTO match_tb (match_result, match_state, match_home_team, match_away_team, match_odd_home, match_odd_away, match_odd_draw) VALUES
(NULL, 1, 1, 2, 1.6, 2.8, 3.1),  -- Futebol Clube Barcelona vs Real Madrid
(NULL, 1, 3, 4, 1.9, 2.4, 3.2),  -- Liverpool FC vs Manchester City
(NULL, 1, 5, 6, 2.0, 2.6, 3.3),  -- Paris Saint-Germain vs Juventus FC
(NULL, 1, 7, 8, 1.7, 2.9, 3.0),  -- Bayern Munich vs Chelsea FC
(NULL, 1, 9, 10, 2.2, 2.5, 3.1),  -- Atlético Madrid vs Borussia Dortmund
(NULL, 1, 11, 12, 1.8, 2.7, 3.4),  -- Inter Milan vs AC Milan
(NULL, 1, 13, 14, 2.1, 2.6, 3.2),  -- Tottenham Hotspur vs Arsenal FC
(NULL, 1, 15, 16, 1.5, 3.0, 3.5),  -- Manchester United vs AS Roma
(NULL, 1, 17, 18, 1.6, 2.8, 3.0),  -- Napoli vs Benfica
(NULL, 1, 19, 20, 2.0, 2.7, 3.1);  -- FC Porto vs Lille OSC

