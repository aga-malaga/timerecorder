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
    cost_per_hour NUMERIC                                 NOT NULL
);
CREATE TABLE projects
(
    uuid        UUID DEFAULT uuid_generate_v4(),
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name        VARCHAR(50)                             NOT NULL UNIQUE,
    description VARCHAR(1000),
    start       TIMESTAMP,
    stop        TIMESTAMP,
    budget      NUMERIC CHECK (BUDGET > 0)
);
CREATE TABLE project_user
(
    project_id 		BIGINT 						    NOT NULL,
    user_id   	 	BIGINT 							NOT NULL,
    enter_on        TIMESTAMP,
    leave_on        TIMESTAMP,
    PRIMARY KEY (project_id, user_id),
    foreign key (user_id) references users (id)
        on delete cascade,
    foreign key (project_id) references projects (id)
        on delete cascade
);

CREATE TABLE time_records
(
    uuid                    UUID DEFAULT uuid_generate_v4(),
    id                      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    start                   TIMESTAMP,
    stop                    TIMESTAMP,
    project_user_project_id BIGINT,
    project_user_user_id    BIGINT,
    FOREIGN KEY (project_user_project_id, project_user_user_id) REFERENCES project_user (project_id, user_id)
);