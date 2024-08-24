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
    user_favorite_team_id INT,
    FOREIGN KEY (user_favorite_team_id) REFERENCES team_tb (team_id) ON DELETE SET NULL
);

CREATE TABLE bet_tb (
    bet_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    bet_state INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_tb (user_id) ON DELETE CASCADE
);

CREATE TABLE match_tb (
    match_id SERIAL PRIMARY KEY,
    match_result INT,
    match_state INT NOT NULL,
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

-- Inserir Times
INSERT INTO team_tb (team_abbreviation, team_name) VALUES
('FCB', 'Futebol Clube de Barcelona'),
('RMD', 'Real Madrid'),
('LIV', 'Liverpool FC'),
('MNC', 'Manchester City');

-- Inserir Usuários
INSERT INTO user_tb (user_access_level, user_name, user_email, user_password, user_balance, user_favorite_team_id) VALUES
(1, 'João Silva', 'joao.silva@example.com', 'senha123', 100.50, 1),  -- FCB
(2, 'Maria Oliveira', 'maria.oliveira@example.com', 'senha456', 200.75, 2),  -- RMD
(1, 'Carlos Souza', 'carlos.souza@example.com', 'senha789', 150.00, 3);  -- LIV

-- Inserir Partidas
INSERT INTO match_tb (match_name, match_result, match_state, match_home_team, match_away_team, match_date, match_odd_home, match_odd_away, match_odd_draw) VALUES
('FCB vs RMD', NULL, 1, 1, 2, '2024-09-01', 1.80, 2.00, 3.50),  -- FCB vs RMD
('LIV vs MNC', NULL, 1, 3, 4, '2024-09-02', 2.10, 1.90, 3.60);  -- LIV vs MNC

-- Inserir Apostas
INSERT INTO bet_tb (user_id, bet_state) VALUES
(1, 1),  -- João Silva
(2, 1);  -- Maria Oliveira

-- Inserir Lances
INSERT INTO bid_tb (bet_id, match_id, bid_guess, bid_paid_value) VALUES
(1, 1, 1, 50.00),  -- João Silva aposta na vitória do time da casa (FCB vs RMD)
(2, 2, 2, 75.00);  -- Maria Oliveira aposta na vitória do time visitante (LIV vs MNC)

-- Consulta para obter todos os lances corretos

SELECT COUNT(*) AS correct_bids
FROM match_tb 
INNER JOIN (
    SELECT match_id, bid_guess
    FROM bid_tb 
    WHERE bet_id = 1
) AS bids_tb
ON bids_tb.match_id = match_tb.match_id
WHERE bids_tb.bid_guess = match_tb.match_result;

-- Consulta para o valor total investido na aposta

SELECT SUM (bid_tb.bid_paid_value) AS total_bet_value
FROM bid_tb WHERE bet_id = 1

-- consulta para determinar o valor a ser pago por uma aposta
select * from match_tb
	



-- Consulta que determina se uma aposta está finalizada ou não

select * from match_tb inner join (
    SELECT match_id, bid_guess
    FROM bid_tb 
    WHERE bet_id = 1
) AS bids_tb
on(match_tb.match_id = bids_tb.match_id)
where(match_tb.match_state != 0)
