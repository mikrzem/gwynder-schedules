CREATE TABLE scheduled_event (
    id         bigserial    NOT NULL,
    owner      varchar(255) NOT NULL,
    end_time   timestamp    NOT NULL,
    start_time timestamp    NOT NULL,
    title      varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE scheduled_event_route_point (
    id         bigserial    NOT NULL,
    owner      varchar(255) NOT NULL,
    altitude   float8       NOT NULL,
    latitude   float8       NOT NULL,
    longitude  float8       NOT NULL,
    point_time timestamp    NOT NULL,
    precision  float8       NOT NULL,
    event_id   int8         NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE scheduled_event_route_point
    ADD CONSTRAINT FKtljx0k5j60x0qewrq2csf25aa
        FOREIGN KEY (event_id)
            REFERENCES scheduled_event
            ON DELETE CASCADE;