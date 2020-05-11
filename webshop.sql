DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS prod_category;
DROP TABLE IF EXISTS prod_supplier;
DROP TABLE IF EXISTS web_user;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS pay;

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    description TEXT,
    price FLOAT NOT NULL,
    currency VARCHAR(3) NOT NULL,
    category_id INTEGER REFERENCES prod_category(id),
    supplier_id INTEGER REFERENCES prod_supplier(id)
);

CREATE TABLE prod_category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE prod_supplier(
    id SERIAL PRIMARY KEY,
    name VARCHAR(30)
);

CREATE TABLE web_user(
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(40) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    active BOOLEAN
);

CREATE TABLE cart(
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES web_user(id),
    prod_id1 INTEGER REFERENCES product(id),
    prod_id2 INTEGER REFERENCES product(id),
    prod_id3 INTEGER REFERENCES product(id),
    prod_id4 INTEGER REFERENCES product(id),
    prod_id5 INTEGER REFERENCES product(id),
    prod_id6 INTEGER REFERENCES product(id),
    prod_id7 INTEGER REFERENCES product(id),
    prod_id8 INTEGER REFERENCES product(id),
    prod_id9 INTEGER REFERENCES product(id),
    prod_id10 INTEGER REFERENCES product(id),
    active BOOLEAN NOT NULL
);

CREATE TABLE pay(
    id SERIAL PRIMARY KEY,
    cart_id INTEGER REFERENCES cart(id) NOT NULL,
    pay_date TIMESTAMP NOT NULL,
    pay_type VARCHAR(20) NOT NULL,
    full_price FLOAT NOT NULL,
    currency VARCHAR(3) NOT NULL
)