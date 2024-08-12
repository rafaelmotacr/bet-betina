CREATE TABLE user_tb (
    user_id SERIAL PRIMARY KEY,
    user_access_level INT NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    user_email VARCHAR(100) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL,
	user_balance FLOAT NOT NULL
);


CREATE TABLE bet_tb (
    bet_id SERIAL PRIMARY KEY,
    bet_state INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_tb (user_id) ON DELETE CASCADE
);


CREATE TABLE team_tb (
    team_id SERIAL PRIMARY KEY,
    team_abbreviation VARCHAR(10) NOT NULL,
    team_name VARCHAR(100) NOT NULL
);


CREATE TABLE match_tb (
    match_id SERIAL PRIMARY KEY,
    match_state INT NOT NULL,
    match_home_team INT NOT NULL,
    match_away_team INT NOT NULL,
	match_result INT,
	match_odd FLOAT NOT NULL,
    FOREIGN KEY (match_home_team) REFERENCES team_tb (team_id),
    FOREIGN KEY (match_away_team) REFERENCES team_tb (team_id),
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

-- Povoamento

INSERT INTO user_tb (user_access_level, user_name, user_email, user_password, user_balance)
VALUES 
    (1, 'João Silva', 'joao.silva@example.com', 'senha123', 150.75),
    (2, 'Maria Oliveira', 'maria.oliveira@example.com', 'senha456', 200.00),
    (3, 'Carlos Santos', 'carlos.santos@example.com', 'senha789', 50.00),
    (2, 'Ana Costa', 'ana.costa@example.com', 'senha321', 120.00),
    (1, 'Pedro Almeida', 'pedro.almeida@example.com', 'senha654', 90.50);

INSERT INTO team_tb (team_abbreviation, team_name)
VALUES 
    ('FCB', 'Futebol Clube do Brasil'),
    ('SCCP', 'Sport Club Corinthians Paulista'),
    ('PAL', 'Sociedade Esportiva Palmeiras'),
    ('FLA', 'Clube de Regatas do Flamengo'),
    ('VAS', 'Club de Regatas Vasco da Gama');

INSERT INTO match_tb (match_state, match_home_team, match_away_team, match_result, match_odd)
VALUES 
    (1, 1, 2, NULL, 2.50),  -- Jogo entre Futebol Clube do Brasil e Sport Club Corinthians Paulista
    (1, 3, 4, NULL, 1.80),  -- Jogo entre Sociedade Esportiva Palmeiras e Clube de Regatas do Flamengo
    (1, 5, 1, NULL, 3.00),  -- Jogo entre Club de Regatas Vasco da Gama e Futebol Clube do Brasil
    (1, 2, 3, NULL, 2.20),  -- Jogo entre Sport Club Corinthians Paulista e Sociedade Esportiva Palmeiras
    (1, 4, 5, NULL, 1.90);  -- Jogo entre Clube de Regatas do Flamengo e Club de Regatas Vasco da Gama

INSERT INTO bet_tb (bet_state, user_id)
VALUES 
    (1, 1),  -- Aposta feita por João Silva
    (1, 2),  -- Aposta feita por Maria Oliveira
    (1, 3),  -- Aposta feita por Carlos Santos
    (1, 4),  -- Aposta feita por Ana Costa
    (1, 5);  -- Aposta feita por Pedro Almeida

INSERT INTO bid_tb (bid_guess, bet_id, match_id, bid_paid_value)
VALUES 
    (1, 1, 1, 50.00),  -- João Silva apostou 50 na vitória do time da casa no jogo 1
    (2, 2, 2, 30.00),  -- Maria Oliveira apostou 30 na vitória do time visitante no jogo 2
    (1, 3, 3, 20.00),  -- Carlos Santos apostou 20 na vitória do time da casa no jogo 3
    (2, 4, 4, 40.00),  -- Ana Costa apostou 40 na vitória do time visitante no jogo 4
    (1, 5, 5, 60.00);  -- Pedro Almeida apostou 60 na vitória do time da casa no jogo 5



