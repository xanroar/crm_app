CREATE TABLE Role (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) UNIQUE NOT NULL
);



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

