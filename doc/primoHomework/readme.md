![diagramma_uml](class_diagram.pdf)

# Documentazione - Dizionario delle Classi

## Gerarchia Utenti
Rappresentano gli attori che interagiscono con il sistema.

- **`Utente` (Abstract)**: Classe base astratta che definisce le caratteristiche comuni a tutti gli utenti del sistema (nome, cognome, login, email e password) e gestisce la verifica delle credenziali.
- **`Studente`**: Estende `Utente`. Rappresenta uno studente iscritto all'università. È identificato univocamente da una `matricola` (generata in automatico) e mantiene una lista degli insegnamenti che segue.
- **`Docente`**: Estende `Utente`. Rappresenta un professore universitario autorizzato a gestire le proprie lezioni e a richiederne lo spostamento.
- **`Coordinatore`**: Estende `Docente`. Rappresenta un ruolo amministrativo. Ha i permessi per approvare/rifiutare le richieste di spostamento delle lezioni e definire l'orario settimanale.

## Modelli Accademici
Rappresentano il dominio dell'università: i corsi, le aule e la gestione del tempo.

- **`AnnoAccademico`**: Rappresenta un anno di erogazione didattica (es. 2023). Contiene e raggruppa tutti gli `Insegnamento` attivi in quell'anno.
- **`Materia`**: Rappresenta il concetto astratto di una disciplina di studio (es. "Analisi", "Programmazione"), caratterizzata principalmente dal nome.
- **`Insegnamento`**: Rappresenta l'istanza fisica di una materia erogata in un determinato anno di corso. Associa una `Materia`, i crediti formativi (`numeroCfu`), l'anno di corso (es. 1 anno, 2 anno) e contiene la lista delle `Lezione` programmate per esso.
- **`Aula`**: Rappresenta uno spazio fisico dove si tengono le lezioni. È identificata da una `lettera`, un `numero` e possiede una determinata `capacita` di posti a sedere.
- **`Lezione`**: Rappresenta il singolo evento didattico. È caratterizzata da un orario di inizio (`oraInizio`), un orario di fine (`oraFine`) e si svolge in una determinata `Aula`.
- **`OrarioLezioni`**: Entità dedicata alla strutturazione dell'orario settimanale. Associa i giorni della settimana (`DayOfWeek`) alle lezioni e agli insegnamenti per un dato `AnnoAccademico`.
- **`RichiestaSpostamento`**: Rappresenta la richiesta formulata da un `Docente` per modificare l'orario di una sua `Lezione`, indicando il `newOrario` desiderato.
- **`StatoSpostamento`**: Enumerazione che definisce l'esito di una `RichiestaSpostamento` (`IN_ATTESA`, `APPROVATO`, `RIFIUTATO`).

## Servizi
Classi che espongono le funzionalità principali del sistema e domandano i Repository.

- **`ServizioUtenti`**: Gestisce l'autenticazione e la registrazione per Studenti, Docenti e Coordinatori. Assicura l'unicita' di email/login.
- **`ServizioLezioni`**: Gestisce la logica del calendario didattico. Permette la creazione di lezioni, l'estrazione dell'orario e gestisce il flusso delle richieste di spostamento lezione tra Docente e Coordinatore.
- **`ServizioUniversita`**: Punto di accesso centralizzato per l'istanziamento e la gestione delle entità di base dell'università (creazione di Materie, Aule, Insegnamenti e Anni Accademici).

## Repository
Classi dedicate alla persistenza (attualmente in-memory tramite `ArrayList`) e al recupero degli oggetti.

- **`StudenteRepository` / `DocenteRepository` / `CoordinatoreRepository`**: Memorizzano le rispettive entità utente. Forniscono metodi di ricerca e validazione tramite `login` ed `email`.
- **`AnnoAccademicoRepository`**: Memorizza e gestisce la lista degli anni accademici.
- **`AulaRepository`**: Memorizza e gestisce la lista delle aule fisiche disponibili.
- **`InsegnamentoRepository`**: Memorizza la lista di tutti gli insegnamenti creati.
- **`LezioneRepository`**: Contiene lo storico e la programmazione di tutte le singole lezioni.
- **`MateriaRepository`**: Catalogo delle materie registrate nel sistema.
