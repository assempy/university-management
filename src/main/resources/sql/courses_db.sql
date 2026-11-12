CREATE TABLE IF NOT EXISTS courses (
    id BIGSERIAL PRIMARY KEY,
    course_name VARCHAR(255) NOT NULL,
    course_code VARCHAR(255) NOT NULL UNIQUE,
    credits INTEGER NOT NULL CHECK (credits > 0),
    instructor VARCHAR(255) NOT NULL
);