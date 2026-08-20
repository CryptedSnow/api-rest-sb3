CREATE TABLE characters (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    race VARCHAR(15) NOT NULL,
    gender VARCHAR(15) NOT NULL,
    type_class VARCHAR(15) NOT NULL,
    age INT NOT NULL,
    height DECIMAL(3,2) NOT NULL,
    element VARCHAR(15) NOT NULL,
    origin VARCHAR(15) NOT NULL,
    weapon VARCHAR(15) NOT NULL,
    alignment VARCHAR(15) NOT NULL,
    alive BOOLEAN NOT NULL
);