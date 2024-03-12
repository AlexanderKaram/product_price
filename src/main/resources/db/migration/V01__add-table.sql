CREATE SCHEMA IF NOT EXISTS test;

CREATE TABLE IF NOT EXISTS price
(
    id           bigserial,
    product_code varchar   NOT NULL,
    number       int       NOT NULL,
    depart       int       NOT NULL,
    begin_date   timestamp NOT NULL,
    end_date     timestamp NOT NULL,
    value        int       NOT NULL,
    PRIMARY KEY (id)
);