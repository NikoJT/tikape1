CREATE TABLE Alue (
    id integer AUTO_INCREMENT PRIMARY KEY,
    name varchar(30)
);

CREATE TABLE Ketju (
    id integer AUTO_INCREMENT PRIMARY KEY,
    alue_id integer NOT NULL,
    name varchar(30),
    FOREIGN KEY (alue_id) REFERENCES Alue(id)
);

CREATE TABLE Viesti (
    id integer AUTO_INCREMENT PRIMARY KEY,
    alue_id integer NOT NULL,
    ketju_id integer NOT NULL,    
    sisalto varchar(140),
    nimimerkki varchar(20),
    aika varchar(20),
    FOREIGN KEY (ketju_id) REFERENCES Ketju(id),
    FOREIGN KEY (alue_id) REFERENCES Alue(id)
);