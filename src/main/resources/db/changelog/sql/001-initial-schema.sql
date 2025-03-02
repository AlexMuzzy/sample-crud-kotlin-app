--liquibase formatted sql

--changeset author:todos-1
CREATE SEQUENCE todos_seq;

CREATE TABLE todos
(
    id          BIGINT PRIMARY KEY DEFAULT nextval('todos_seq'),
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    completed   BOOLEAN      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL
);
--rollback DROP TABLE todos;
--rollback DROP SEQUENCE todos_seq;