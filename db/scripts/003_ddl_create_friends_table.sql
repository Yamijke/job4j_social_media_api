CREATE TABLE friends (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    friend_id INT REFERENCES users(id) ON DELETE CASCADE,
    status VARCHAR(10) CHECK (status IN ('pending', 'accepted', 'rejected')) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);