CREATE TABLE events (
    id VARCHAR(27) PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    entity_id VARCHAR(36) NOT NULL,
    data TEXT
);

CREATE INDEX entity_timestamp ON events(entity_id, id);

CREATE TABLE accounts (
    id VARCHAR(36) PRIMARY KEY,
    last_summary_event REFERENCES events(id) NOT NULL
)