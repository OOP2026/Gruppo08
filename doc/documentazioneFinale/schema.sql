CREATE USER exam WITH PASSWORD 'exam';
CREATE DATABASE exam OWNER exam;

-- TODO: vincolo di integrita' semantica: un app_user non puo' essere senza ruolo e non possono esistere uno student e un teacher con lo stesso user_id. 
CREATE TABLE app_user (
	user_id serial PRIMARY KEY, 
	fname varchar(64) NOT NULL,
	lname varchar(64) NOT NULL,
	email varchar(64) UNIQUE NOT NULL,
	login varchar(64) UNIQUE NOT NULL,
	password varchar(64) NOT NULL
);

CREATE TABLE student (
	user_id integer PRIMARY KEY,
	student_id serial UNIQUE NOT NULL,
	academic_year integer NOT NULL,

	FOREIGN KEY (user_id) REFERENCES app_user(user_id) 
	ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE teacher (
	user_id integer PRIMARY KEY,
	is_coordinator boolean NOT NULL DEFAULT false,

	FOREIGN KEY (user_id) REFERENCES app_user(user_id) 
	ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE course (
	course_id serial PRIMARY KEY,
	teacher_uid integer NOT NULL REFERENCES teacher(user_id),
	name varchar(64) NOT NULL,
	cfu integer NOT NULL,
	academic_year integer NOT NULL,
	is_active boolean NOT NULL DEFAULT false,
	UNIQUE(name, academic_year)
);

CREATE TABLE classroom (
	name varchar(16) PRIMARY KEY
);

CREATE TYPE dow AS ENUM ('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY');

CREATE TABLE lecture (
	lecture_id serial PRIMARY KEY,
	course_id integer NOT NULL REFERENCES course(course_id),
	classroom_name varchar(16) NOT NULL REFERENCES classroom(name),
	dayofweek dow NOT NULL,
	start_time time NOT NULL,
	end_time time NOT NULL,

	UNIQUE(classroom_name, dayofweek, start_time),
    CONSTRAINT chk_times CHECK (end_time > start_time)
);

CREATE TYPE request_status AS ENUM ('WAITING', 'APPROVED', 'REJECTED');

-- TODO: se status <> 'WAITING' allora reviewing_coord_id deve essere <> null
CREATE TABLE change_of_date_req (
	req_id serial PRIMARY KEY,

	asking_teacher_id integer NOT NULL REFERENCES teacher(user_id),
	reviewing_coord_id integer REFERENCES teacher(user_id),

	lecture_id integer NOT NULL REFERENCES lecture(lecture_id),
	new_dayofweek dow NOT NULL,
	new_start_time time NOT NULL,
	new_end_time time NOT NULL,
	status request_status NOT NULL DEFAULT 'WAITING',

	UNIQUE(lecture_id, new_dayofweek, new_start_time),
    CONSTRAINT chk_times CHECK (new_end_time > new_start_time),
	CONSTRAINT chk_status_integrity CHECK (status = 'WAITING' OR reviewing_coord_id <> null)
);

-- TODO: trigger per controllare che un professore non abbia due lezioni nello stesso intervallo orario

CREATE OR REPLACE FUNCTION fn_assure_no_lecture_overlapping()
RETURNS TRIGGER AS $$
BEGIN
	IF EXISTS (
		SELECT 1
		FROM lecture
		WHERE classroom_name = new.classroom_name
		AND dayofweek = new.dayofweek
		AND (
			new.start_time BETWEEN start_time AND end_time
			OR
			new.end_time BETWEEN start_time AND end_time
		)
	) THEN
		RAISE EXCEPTION 'classroom is already busy on % %-%', new.dayofweek, new.start_time, new.end_time;
	END IF;

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_assure_no_lecture_overlapping
BEFORE INSERT OR UPDATE OF classroom_name, dayofweek, start_time, end_time ON lecture
FOR EACH ROW
	EXECUTE FUNCTION fn_assure_no_lecture_overlapping();

CREATE OR REPLACE FUNCTION fn_valid_changeofdate_req()
RETURNS TRIGGER AS $$
DECLARE
	lec_classroom classroom.name%TYPE;
BEGIN
	SELECT classroom_name INTO lec_classroom
	FROM lecture
	WHERE lecture_id = new.lecture_id;

	IF EXISTS (
		SELECT 1
		FROM lecture
		WHERE classroom_name = lec_classroom
		AND dayofweek = new.new_dayofweek
		AND (
			new.new_start_time BETWEEN start_time AND end_time
			OR
			new.new_end_time BETWEEN start_time AND end_time
		)
	) THEN
		RAISE EXCEPTION 'classroom is already busy on % %-%', new.new_dayofweek, new.new_start_time, new.new_end_time;
	END IF;

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_valid_changeofdate_req
BEFORE INSERT OR UPDATE OF new_dayofweek, new_start_time, new_end_time
ON change_of_date_req
FOR EACH ROW
	EXECUTE FUNCTION fn_valid_changeofdate_req();

CREATE OR REPLACE FUNCTION fn_check_valid_reviewer()
RETURNS TRIGGER AS $$
DECLARE
	is_reviewer_coordinator teacher.is_coordinator%TYPE;
BEGIN
	SELECT is_coordinator INTO is_reviewer_coordinator
	FROM teacher 
	WHERE user_id = new.reviewing_coord_id;

	IF NOT is_reviewer_coordinator THEN
		RAISE EXCEPTION 'teacher with id % is not a coordinator', new.reviewing_coord_id;
	END IF;

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_check_valid_reviewer
BEFORE INSERT OR UPDATE OF reviewing_coord_id ON change_of_date_req
FOR EACH ROW
	EXECUTE FUNCTION fn_check_valid_reviewer();
