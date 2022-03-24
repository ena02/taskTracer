CREATE DATABASE task_tracer;

CREATE TABLE task (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(45),
    description VARCHAR(90),
    status INT,
    priority INT
);

    CREATE TABLE project (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(45),
    start_date TIMESTAMP,
    completed_date TIMESTAMP,
    current_status INT,
    priority INT,
    task_id BIGINT REFERENCES task(id)
);