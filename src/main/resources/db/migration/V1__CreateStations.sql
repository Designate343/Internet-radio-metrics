CREATE TABLE IF NOT EXISTS stations (
    station_name VARCHAR(80) UNIQUE NOT NULL,
    station_id INTEGER PRIMARY KEY AUTO_INCREMENT
)