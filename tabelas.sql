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
	user_balance FLOAT NOT NULL,
    FOREIGN KEY (access_level_id) REFERENCES user_access_level_tb (access_level_id)
);


CREATE TABLE bet_tb (
    bet_id SERIAL PRIMARY KEY,
    bet_state_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (bet_state_id) REFERENCES bet_state_tb (bet_state_id),
    FOREIGN KEY (user_id) REFERENCES user_tb (user_id) ON DELETE CASCADE
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


CREATE TABLE bid_tb (
    bid_id SERIAL PRIMARY KEY,
    bid_paid_value FLOAT NOT NULL,
    bid_guess INT NOT NULL,
    bet_id INT NOT NULL,
    match_id INT NOT NULL,
    FOREIGN KEY (bet_id) REFERENCES bet_tb (bet_id) ON DELETE CASCADE,
    FOREIGN KEY (match_id) REFERENCES match_tb (match_id) ON DELETE CASCADE
);


-- Povoamento

-- Níveis de acesso
INSERT INTO user_access_level_tb (access_level_name) VALUES
('admin'),
('user');

-- Estados das apostas
INSERT INTO bet_state_tb (bet_state_name) VALUES
('Active'),
('Completed'),
('Cancelled');

-- Estados das partidas
INSERT INTO match_state_tb (match_state_name) VALUES
('Scheduled'),
('In Progress'),
('Finished');

-- Resultados das partidas
INSERT INTO match_result_tb (match_result_name) VALUES
('Home Win'),
('Away Win'),
('Draw');

-- Inserir dados na tabela user_tb

-- Nomes fictícios e emails para os usuários
INSERT INTO user_tb (access_level_id, user_name, user_balance, user_email, user_password) VALUES
(2, 'Alice Oliveira', 500.00, 'alice.oliveira@example.com', 'senha123'),
(2, 'Bruno Santos', 500.00, 'bruno.santos@example.com', 'senha123'),
(2, 'Carla Almeida', 500.00, 'carla.almeida@example.com', 'senha123'),
(2, 'David Costa', 500.00, 'david.costa@example.com', 'senha123'),
(2, 'Eva Pereira', 500.00, 'eva.pereira@example.com', 'senha123'),
(2, 'Fernanda Silva', 500.00, 'fernanda.silva@example.com', 'senha123'),
(2, 'Gabriel Lima', 500.00, 'gabriel.lima@example.com', 'senha123'),
(2, 'Helena Martins', 500.00, 'helena.martins@example.com', 'senha123'),
(2, 'Igor Sousa', 500.00, 'igor.sousa@example.com', 'senha123'),
(2, 'Juliana Rodrigues', 500.00, 'juliana.rodrigues@example.com', 'senha123');

-- Inserir dados na tabela team_tb

INSERT INTO team_tb (team_abbreviation, team_name) VALUES
('FLC', 'Flamengo'),
('VAS', 'Vasco'),
('FLU', 'Fluminense'),
('BOT', 'Botafogo'),
('PAL', 'Palmeiras'),
('COR', 'Corinthians'),
('SAO', 'São Paulo'),
('SPT', 'Sport'),
('GRM', 'Grêmio'),
('INT', 'Internacional');

-- Inserir dados na tabela match_tb

INSERT INTO match_tb (match_state_id, match_odd, match_result_id, match_home_team, match_away_team) VALUES
(1, 2.50, NULL, 1, 2),
(1, 1.80, NULL, 3, 4),
(2, 3.10, NULL, 5, 6),
(1, 2.00, NULL, 7, 8),
(3, 1.70, 1, 9, 10),
(2, 3.50, NULL, 4, 5),
(1, 2.25, NULL, 6, 7),
(3, 1.90, 3, 8, 9),
(1, 2.75, NULL, 2, 3),
(2, 2.00, 2, 10, 1);

-- Inserir dados na tabela bet_tb

INSERT INTO bet_tb (bet_state_id, user_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(1, 4),
(2, 5),
(1, 6),
(3, 7),
(2, 8),
(1, 9),
(2, 10);

-- Inserir dados na tabela bid_tb

INSERT INTO bid_tb (bid_paid_value, bid_guess, bet_id, match_id) VALUES
(50.00, 1, 1, 1),
(100.00, 2, 2, 2),
(75.00, 0, 3, 3),
(60.00, 1, 4, 4),
(85.00, 2, 5, 5),
(55.00, 0, 6, 6),
(90.00, 1, 7, 7),
(40.00, 2, 8, 8),
(70.00, 1, 9, 9),
(65.00, 0, 10, 10);

select * from user_tb
