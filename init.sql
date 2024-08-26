-- Criação do banco de dados se não existir
DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'bet-betina-prod') THEN
        EXECUTE 'CREATE DATABASE bet-betina-prod';
    END IF;
END
$$;

-- Conecte-se ao banco de dados 'bet-betina-prod'
\c bet-betina-prod

-- Criação das tabelas se não existirem
CREATE TABLE IF NOT EXISTS team_tb (
    team_id SERIAL PRIMARY KEY,
    team_abbreviation VARCHAR(10) NOT NULL,
    team_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_tb (
    user_id SERIAL PRIMARY KEY,
    user_access_level INT NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    user_email VARCHAR(100) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL,
    user_balance FLOAT NOT NULL,
    user_favorite_team_id INT,
    FOREIGN KEY (user_favorite_team_id) REFERENCES team_tb (team_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS bet_tb (
    bet_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    bet_state INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_tb (user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS match_tb (
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

CREATE TABLE IF NOT EXISTS bid_tb (
    bet_id INT NOT NULL,
    match_id INT NOT NULL,
    bid_guess INT NOT NULL,
    bid_paid_value FLOAT NOT NULL,
    PRIMARY KEY (bet_id, match_id),
    FOREIGN KEY (bet_id) REFERENCES bet_tb (bet_id) ON DELETE CASCADE,
    FOREIGN KEY (match_id) REFERENCES match_tb (match_id) ON DELETE CASCADE
);
