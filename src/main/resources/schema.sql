DROP TABLE IF EXISTS categories;
CREATE TABLE categories(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);
DROP TABLE IF EXISTS courses;
CREATE TABLE courses(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    category_id INT,
    teaser VARCHAR(255),
    description TEXT,
    duration INT
);