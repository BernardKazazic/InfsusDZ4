CREATE TABLE review
(
    id      SERIAL PRIMARY KEY,
    user_id TEXT,
    review  TEXT,
    valid   BOOLEAN
);