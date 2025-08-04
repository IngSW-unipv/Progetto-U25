CREATE TABLE linea (
    nome INT PRIMARY KEY
);

CREATE TABLE conducente (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    cognome VARCHAR(100),
    cf CHAR(16),
    pass_hash CHAR(100)
);

CREATE TABLE tipi_checkpoint (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100)
);

CREATE TABLE checkpoint (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    tipo INT,
    FOREIGN KEY (tipo) REFERENCES tipi_checkpoint(id)
);

CREATE TABLE stazione (
    id INT PRIMARY KEY,
    lunghezza INT,
    durata_fermataS INT,
    FOREIGN KEY (id) REFERENCES checkpoint(id)
);

CREATE TABLE orari_inizio (
    linea INT,
    orario TIME,
    capolinea INT,
    PRIMARY KEY (linea, orario, capolinea),
    FOREIGN KEY (linea) REFERENCES linea(nome),
    FOREIGN KEY (capolinea) REFERENCES checkpoint(id)
);

CREATE TABLE checkpoint_su_linea (
    linea INT,
    checkpoint INT,
    numero INT,
    delayS INT,
    PRIMARY KEY (linea, checkpoint),
    FOREIGN KEY (linea) REFERENCES linea(nome),
    FOREIGN KEY (checkpoint) REFERENCES checkpoint(id)
);