CREATE TABLE team_tb (
    team_id SERIAL PRIMARY KEY,
    team_abbreviation VARCHAR(10) NOT NULL,
    team_name VARCHAR(100) NOT NULL
);


CREATE TABLE user_tb (
    user_id SERIAL PRIMARY KEY,
    user_access_level INT NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    user_email VARCHAR(100) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL,
	user_balance FLOAT NOT NULL,
	user_favorite_team_id INT REFERENCES team_tb (team_id)
);


CREATE TABLE bet_tb (
    bet_id SERIAL PRIMARY KEY,
    bet_state INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_tb (user_id) ON DELETE CASCADE
);


CREATE TABLE match_tb (
    match_id SERIAL PRIMARY KEY,
    match_state INT NOT NULL,
    match_home_team INT NOT NULL,
    match_away_team INT NOT NULL,
	match_result INT,
	match_home_team_odd FLOAT NOT NULL,
	match_away_Team_odd FLOAT NOT NULL,
	match_draw_odd FLOAT NOT NULL,
    FOREIGN KEY (match_home_team) REFERENCES team_tb (team_id) ON DELETE CASCADE,
    FOREIGN KEY (match_away_team) REFERENCES team_tb (team_id) ON DELETE CASCADE,
    CONSTRAINT check_teams CHECK (match_home_team <> match_away_team)
);


CREATE TABLE bid_tb (
    bid_id SERIAL PRIMARY KEY,
    bid_guess INT NOT NULL,
    bet_id INT NOT NULL,
    match_id INT NOT NULL,
	bid_paid_value FLOAT NOT NULL,
    FOREIGN KEY (bet_id) REFERENCES bet_tb (bet_id) ON DELETE CASCADE,
    FOREIGN KEY (match_id) REFERENCES match_tb (match_id) ON DELETE CASCADE
);


-- Insere dados na tabela team_tb
INSERT INTO team_tb (team_abbreviation, team_name) VALUES
('FLA', 'Flamengo'),
('PAL', 'Palmeiras'),
('SAN', 'Santos'),
('COR', 'Corinthians'),
('SAO', 'São Paulo'),
('GREM', 'Grêmio'),
('CRU', 'Cruzeiro'),
('INT', 'Internacional'),
('VAS', 'Vasco da Gama'),
('BOT', 'Botafogo');

select * from team_tb

-- Insere dados na tabela user_tb
INSERT INTO user_tb (user_access_level, user_name, user_email, user_password, user_balance, user_favorite_team_id) VALUES
(1, 'João Silva', 'joao.silva@email.com', 'senha123', 500.00, 1),
(2, 'Maria Souza', 'maria.souza@email.com', 'senha456', 300.00, 2),
(1, 'Carlos Pereira', 'carlos.pereira@email.com', 'senha789', 150.00, 3),
(3, 'Ana Oliveira', 'ana.oliveira@email.com', 'senhaabc', 1000.00, 4),
(2, 'Bruno Costa', 'bruno.costa@email.com', 'senhadef', 250.00, 5);

-- Insere dados na tabela bet_tb
INSERT INTO bet_tb (bet_state, user_id) VALUES
(1, 1),
(2, 2),
(1, 3),
(2, 4),
(1, 5);

-- Insere dados na tabela match_tb
INSERT INTO match_tb (match_state, match_home_team, match_away_team, match_result, match_home_team_odd, match_away_team_odd, match_draw_odd) VALUES
(1, 1, 2, NULL, 1.80, 2.10, 3.30),
(1, 3, 4, NULL, 2.00, 1.90, 3.10),
(1, 5, 6, NULL, 1.95, 2.00, 3.25),
(2, 7, 8, 1, 1.75, 2.20, 3.50),
(2, 9, 10, 2, 2.05, 1.85, 3.00);

-- Insere dados na tabela bid_tb
INSERT INTO bid_tb (bid_guess, bet_id, match_id, bid_paid_value) VALUES
(1, 1, 1, 100.00),
(2, 2, 2, 50.00),
(3, 3, 3, 75.00),
(1, 4, 4, 200.00),
(2, 5, 5, 30.00),
(1, 1, 3, 60.00);

-- Verificar os dados inseridos

SELECT * FROM team_tb;
SELECT * FROM user_tb;
SELECT * FROM bet_tb;
SELECT * FROM match_tb;
SELECT * FROM bid_tb;

update user_tb set user_access_level = 1
where user_id = 6