CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE users
(
    uuid          UUID DEFAULT uuid_generate_v4(),
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    login         VARCHAR(50)                             NOT NULL UNIQUE,
    name          VARCHAR(50)                             NOT NULL,
    surname       VARCHAR(50)                             NOT NULL,
    role          VARCHAR(50)                             NOT NULL,
    email         VARCHAR(50)                             NOT NULL UNIQUE,
    password      VARCHAR(50)                             NOT NULL,
    cost_per_hour NUMERIC                                 NOT NULL,
    project_id    BIGINT,
    FOREIGN KEY (project_id) REFERENCES projects (id)
);

CREATE TABLE projects
(
    uuid        UUID DEFAULT uuid_generate_v4(),
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name        VARCHAR(50)                             NOT NULL UNIQUE,
    description VARCHAR(1000),
    start       TIMESTAMP,
    stop        TIMESTAMP,
    budget      NUMERIC CHECK (BUDGET > 0),
    user_id     BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE project_users
(
    project_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    CONSTRAINT fk_prouse_on_project FOREIGN KEY (user_id) REFERENCES projects (id),
    CONSTRAINT fk_prouse_on_user FOREIGN KEY (project_id) REFERENCES users (id)
);

alter table users
    ADD FOREIGN KEY (project_id) REFERENCES projects(id);

alter table users
    add project_id bigint;