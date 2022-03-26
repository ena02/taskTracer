CREATE DATABASE task_tracer;

CREATE TABLE task (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(45),
    description VARCHAR(90),
    status VARCHAR(10),
    priority INT,
    project_id BIGINT REFERENCES project(id)
);

    CREATE TABLE project (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(45),
    start_date TIMESTAMP,
    completed_date TIMESTAMP,
    current_status VARCHAR(10),
    priority INT
);

DROP TABLE task;
DROP TABLE project;

CREATE TYPE task_status AS ENUM ('ToDo', 'InProgress', 'Done');

CREATE TYPE project_status AS ENUM ('NotStarted', 'Active', 'Completed');


