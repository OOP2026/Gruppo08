CREATE USER progetto WITH PASSWORD 'progetto';
CREATE DATABASE progetto OWNER progetto;

CREATE TABLE studente (
	login VARCHAR(64) PRIMARY KEY,
	email VARCHAR(64) UNIQUE NOT NULL,
	matricola INTEGER UNIQUE NOT NULL,
	pswd VARCHAR(128) NOT NULL,
	nome VARCHAR(64) NOT NULL,
	cognome VARCHAR(64) NOT NULL
);

CREATE TABLE docente (
	login VARCHAR(64) PRIMARY KEY,
	email VARCHAR(64) UNIQUE NOT NULL,
	pswd VARCHAR(128) NOT NULL,
	nome VARCHAR(64) NOT NULL,
	cognome VARCHAR(64) NOT NULL,
	is_coordinatore BOOLEAN NOT NULL
);

CREATE TABLE materia (
	nome VARCHAR(64) PRIMARY KEY
);

CREATE TABLE aula (
	nome VARCHAR(64) PRIMARY KEY
);

CREATE TABLE insegnamento (
	id_insegnamento SERIAL PRIMARY KEY,
	numero_cfu INTEGER NOT NULL,
	anno INTEGER NOT NULL,
	login_docente VARCHAR(64) NOT NULL REFERENCES docente(login),
	nome_materia VARCHAR(64) NOT NULL REFERENCES materia(nome)
);

CREATE TABLE lezione (
	id_lezione SERIAL PRIMARY KEY,
	ora_inizio TIME(0) NOT NULL,
	ora_fine TIME(0) NOT NULL,
	giorno_sett VARCHAR(16) NOT NULL,
	id_insegnamento INTEGER NOT NULL REFERENCES insegnamento(id_insegnamento),
	nome_aula VARCHAR(64) NOT NULL REFERENCES aula(nome),
	UNIQUE(giorno_sett, ora_fine, ora_inizio)
);

CREATE TABLE anno_di_corso (
	id_insegnamento INTEGER NOT NULL REFERENCES insegnamento(id_insegnamento),
	login_studente VARCHAR(64) NOT NULL REFERENCES studente(login),
	anno INTEGER,
	PRIMARY KEY (id_insegnamento, login_studente)
);

CREATE TYPE stato AS ENUM ('IN_ATTESA', 'APPROVATO', 'RIFIUTATO');

CREATE TABLE richiesta_spostamento (
	id_richiesta SERIAL PRIMARY KEY,
	nuovo_giorno VARCHAR(16) NOT NULL,
	nuova_ora_inizio TIME(0) NOT NULL,
	nuova_ora_fine TIME(0) NOT NULL,
	login_docente VARCHAR(64) NOT NULL REFERENCES docente(login),
	id_lezione INTEGER NOT NULL REFERENCES lezione(id_lezione)
);
