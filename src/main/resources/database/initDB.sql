

CREATE TABLE IF NOT EXISTS customer_table
(
    id    INTEGER PRIMARY KEY ,
    firstName  VARCHAR(200) NOT NULL ,
    lastName VARCHAR(254) NOT NULL ,
    emailAddress VARCHAR(254) NOT NULL ,
    address VARCHAR(254) NOT NULL ,
    city VARCHAR(254) NOT NULL ,
    phoneNumber VARCHAR(254) NOT NULL
);
CREATE SEQUENCE IF NOT EXISTS customers_seq START 1;
