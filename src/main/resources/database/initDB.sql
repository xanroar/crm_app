CREATE TABLE Role (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) UNIQUE NOT NULL
);

INSERT INTO Role (name) VALUES ('ADMIN'), ('USER');

CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       email VARCHAR(255),
                       password VARCHAR(255),
                       role_id INTEGER
);

CREATE TABLE orders (
                        id UUID PRIMARY KEY,
                        order_number VARCHAR(255),
                        total_price DOUBLE PRECISION,
                        user_id UUID
);
INSERT INTO users (id, email, password, role_id) VALUES
    (gen_random_uuid(), 'user1@example.com', 'password', (SELECT id FROM Role WHERE name = 'USER')),
    (gen_random_uuid(), 'admin@example.com', 'password', (SELECT id FROM Role WHERE name = 'ADMIN'));


INSERT INTO orders (id, order_number, total_price, user_id) VALUES
    (gen_random_uuid(), 'order1', 100.0, gen_random_uuid()),
    (gen_random_uuid(), 'order2', 200.0, gen_random_uuid());
