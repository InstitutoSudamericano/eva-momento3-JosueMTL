CREATE TABLE IF NOT EXISTS film (
    id SERIAL PRIMARY KEY,
    title VARCHAR(50),
    director VARCHAR(50),
    duration VARCHAR(50)
    );

CREATE  TABLE IF NOT EXISTS scene(
    id SERIAL PRIMARY KEY,
    description VARCHAR(100),
    budget DECIMAL,
    minutes INT,
    film_id INT,
    FOREIGN KEY (film_id) REFERENCES film(id)
    );

CREATE TABLE IF NOT EXISTS character(
    id SERIAL PRIMARY KEY,
    description VARCHAR(100),
    cost DECIMAL,
    stock INT,
    scene_id INT,
    FOREIGN KEY (scene_id) REFERENCES scene(id)
    );