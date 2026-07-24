-- Pulizia tabelle esistenti
TRUNCATE TABLE change_of_date_req, lecture, course, classroom, teacher, student, app_user RESTART IDENTITY CASCADE;

INSERT INTO app_user (fname, lname, email, login, password) VALUES
('Mario', 'Rossi', 'mario.rossi@universita.it', 'mrossi', 'password123'),       -- Coordinatore
('Luigi', 'Bianchi', 'luigi.bianchi@universita.it', 'lbianchi', 'password123'), -- Professore
('Giovanna', 'Verdi', 'giovanna.verdi@universita.it', 'gverdi', 'password123'), -- Professore
('Andrea', 'Ferrari', 'andrea.ferrari@studenti.it', 'aferrari', 'password123'), -- Studente
('Giulia', 'Russo', 'giulia.russo@studenti.it', 'grusso', 'password123'),       -- Studente
('Matteo', 'Colombo', 'matteo.colombo@studenti.it', 'mcolombo', 'password123'), -- Studente
('Sofia', 'Romano', 'sofia.romano@studenti.it', 'sromano', 'password123');      -- Studente

INSERT INTO teacher (user_id, is_coordinator) VALUES
(1, true),
(2, false),
(3, false);

INSERT INTO student (user_id, academic_year) VALUES
(4, 1),
(5, 1),
(6, 2),
(7, 3);

INSERT INTO classroom (name) VALUES
('Aula Magna'),
('Aula 1'),
('Aula 2'),
('Laboratorio Info');

INSERT INTO course (teacher_uid, name, cfu, academic_year, is_active) VALUES
(2, 'Programmazione', 9, 1, true),          -- Bianchi
(1, 'Analisi', 9, 1, true),      		    -- Rossi
(3, 'Fisica Generale', 6, 2, true),         -- Verdi
(2, 'Basi di Dati', 9, 2, true),            -- Bianchi
(1, 'Algebra', 6, 3, true);                 -- Rossi

INSERT INTO lecture (course_id, classroom_name, dayofweek, start_time, end_time) VALUES
(1, 'Aula Magna', 'MONDAY', '09:00', '11:00'),          -- Programmazione (Lun 09-11)
(2, 'Aula Magna', 'MONDAY', '11:30', '13:30'),          -- Analisi (Lun 11:30-13:30)
(3, 'Aula 1', 'TUESDAY', '09:00', '12:00'),             -- Fisica (Mar 09-12)
(4, 'Laboratorio Info', 'WEDNESDAY', '14:00', '17:00'), -- Basi di Dati (Mer 14-17)
(5, 'Aula 2', 'THURSDAY', '10:00', '13:00');            -- Algebra (Gio 10-13)

INSERT INTO change_of_date_req (asking_teacher_id, reviewing_coord_id, lecture_id, new_dayofweek, new_start_time, new_end_time, status) 
VALUES (2, 1, 1, 'FRIDAY', '09:00', '11:00', 'WAITING');

INSERT INTO change_of_date_req (asking_teacher_id, reviewing_coord_id, lecture_id, new_dayofweek, new_start_time, new_end_time, status) 
VALUES (3, NULL, 3, 'TUESDAY', '14:00', '17:00', 'WAITING');

INSERT INTO change_of_date_req (asking_teacher_id, reviewing_coord_id, lecture_id, new_dayofweek, new_start_time, new_end_time, status) 
VALUES (2, 1, 4, 'WEDNESDAY', '09:00', '12:00', 'WAITING');
