# Guida all'Uso dell'Interfaccia Grafica

## Autenticazione e Registrazione (`LoginPage` e `RegPage`)

All'avvio, l'applicazione presenta la schermata di Login.

* **Accesso:** Inserire l'username (o l'email) e la password. Il sistema smisterà automaticamente l'utente nella dashboard corretta in base al suo Ruolo.

* **Registrazione:** Cliccando su "Registrati", si aprirà una finestra che permette di creare un nuovo account. È possibile scegliere dal menu a tendina il ruolo (Studente, Docente, Coordinatore) e compilare i dati anagrafici e accademici (es. anno di corso per lo studente).

## Homepage

La pagina principale si adatta dinamicamente all'utente loggato:

* **Vista Studente:** Verrà visualizzata solo la tabella oraria corrispondente all'anno accademico di iscrizione dello studente (Anno 1, 2 o 3).

* **Vista Docente:** Viene mostrato l'orario e un bottone aggiuntivo "Richiedi Spostamento".

* **Vista Coordinatore:** Oltre al bottone di spostamento, compare il bottone "Gestisci Richieste".

* Sono presenti bottoni "Aggiorna" per ricaricare la tabella dal database e riflettere eventuali cambi d'orario recenti.

### Richiesta Spostamento Lezione (`ReqPage`)

Accessibile ai Docenti e Coordinatori.

1. Selezionare dal menu a tendina la lezione da spostare (il sistema mostrerà solo le lezioni del docente in sessione).

2. Selezionare il nuovo giorno della settimana.

3. Inserire il nuovo orario di inizio e di fine nel formato `ora:minuti` (es. `09:30`).

4. Cliccare su "Conferma". La richiesta andrà nello stato *WAITING*.

### Gestione Richieste Spostamento Lezione (`ManagePage`)

Accessibile solo ai Coordinatori.

1. Dal menu a tendina superiore è possibile selezionare una delle richieste attualmente in sospeso.

2. Una volta selezionata, a schermo comparirà il confronto tra il *Vecchio orario* e il *Nuovo orario* proposto.

3. Il coordinatore può premere:

- **Accetta:** Lo stato della richiesta diventa *APPROVED* e la lezione cambia ufficialmente giorno e ora nel database.

- **Rifiuta:** Lo stato diventa *REJECTED* e l'orario della lezione rimane invariato.

---

# Architettura del Sistema (Documentazione Tecnica)

Il progetto è stato sviluppato seguendo l'architettura BCE + DAO:

- **Package Model (`model`):** Contiene le classi che rappresentano le entità del dominio (User, Student, Teacher, Course, Lecture, ChangeOfDateReq). Le entità implementano l'interfaccia `Identifiable<I>` per uniformare la gestione delle chiavi primarie.

- **Package DAO (`dao` e `dao.impl`):** Gestisce la persistenza dei dati e come questi ultimi vengono ricavati.

  - Le classi in `dao.impl` interagiscono direttamente con PostgreSQL tramite query SQL (utilizzando i `PreparedStatement` dove opportuno per prevenire SQL injection).

  - Le classi in `dao` agiscono da wrapper, implementando un sistema di *cache in memoria* (tramite la lista `inMem` definita in `AbstractDao`) per ridurre il numero di accessi al DB e velocizzare la lettura di record già estratti.

- **Package Controller:** Contiene la logica di business. I servizi verificano le autorizzazioni prima di effettuare chiamate al DAO. Contiene anche il `SessionManager`, un singleton che memorizza l'utente attualmente autenticato e il suo ruolo, permettendo di gestire l'autorizzazione alle varie funzionalità (es. `isCoordinator()`).

- **Package GUI:** Realizzato con Java Swing.

### Gestione delle Transazioni

Nelle operazioni critiche che coinvolgono l'inserimento di record in più tabelle connesse (ad esempio la registrazione di un utente che scrive sia su `app_user` che su `student`), la consistenza dei dati è garantita attraverso il controllo manuale delle transazioni (`con.setAutoCommit(false)`), effettuando un commit solo se tutte le query hanno successo, o un rollback in caso di eccezione.

> [!NOTE]
> Per gli inserimenti nel DB è stata sfruttata la funzionalità `Statement.RETURN_GENERATED_KEYS` del driver JDBC di Postgres, che garantisce l'immediato recupero degli ID autogenerati (`SERIAL`) dal database senza dover fare query di selezione successive.
