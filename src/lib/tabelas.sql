SELECT 
match_tb.match_id AS ID,
match_tb.match_state AS state,
	match_home_team_odd,
	match_away_team_odd,
	home_team.team_name AS home_team_name,
	    away_team.team_name AS away_team_name
			FROM 
match_tb
JOIN 
			   team_tb AS home_team ON match_tb.match_home_team = home_team.team_id
			JOIN 
 team_tb AS away_team ON match_tb.match_away_team = away_team.team_id;

update match_tb set match_state = 1