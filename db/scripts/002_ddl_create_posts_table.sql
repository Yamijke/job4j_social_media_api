CREATE TABLE posts (
    id          SERIAL PRIMARY KEY,
    user_id     int references users(id) on delete cascade,
    title       varchar not null,
    context     text not null,
    image_url   varchar,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);