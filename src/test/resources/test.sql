DROP TABLE IF EXISTS pay;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS web_user;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS prod_category;
DROP TABLE IF EXISTS prod_supplier;

CREATE TABLE prod_category (
                               cat_id SERIAL PRIMARY KEY,
                               cat_name VARCHAR(30) NOT NULL,
                               department VARCHAR(20),
                               cat_description TEXT
);

CREATE TABLE prod_supplier(
                              sup_id SERIAL PRIMARY KEY,
                              sup_name VARCHAR(30) NOT NULL,
                              sup_description TEXT
);

CREATE TABLE product (
                         prod_id SERIAL PRIMARY KEY,
                         prod_name VARCHAR(40) NOT NULL,
                         prod_description TEXT,
                         price FLOAT NOT NULL,
                         currency VARCHAR(3) NOT NULL,
                         image_file VARCHAR(30) NOT NULL,
                         category_id INTEGER REFERENCES prod_category(cat_id) NOT NULL,
                         supplier_id INTEGER REFERENCES prod_supplier(sup_id) NOT NULL
);

CREATE TABLE web
(
                         user_id SERIAL PRIMARY KEY,
                         user_name VARCHAR(20) UNIQUE NOT NULL,
                         email VARCHAR(40) UNIQUE NOT NULL,
                         password TEXT NOT NULL,
                         user_is_active BOOLEAN NOT NULL
);

CREATE TABLE cart(
                     user_id INTEGER REFERENCES web(user_id) NOT NULL,
                     prod_id INTEGER REFERENCES product (prod_id) NOT NULL,
                     prod_quantity INT                                 NOT NULL
);

CREATE TABLE pay
(
                    pay_id SERIAL PRIMARY KEY,
                    user_id INTEGER REFERENCES web(user_id) NOT NULL,
                    prod_id1 INTEGER REFERENCES product (prod_id),
                    prod_id2 INTEGER REFERENCES product (prod_id),
                    prod_id3 INTEGER REFERENCES product (prod_id),
                    prod_id4 INTEGER REFERENCES product (prod_id),
                    prod_id5 INTEGER REFERENCES product (prod_id),
                    prod_id6 INTEGER REFERENCES product (prod_id),
                    prod_id7 INTEGER REFERENCES product (prod_id),
                    prod_id8 INTEGER REFERENCES product (prod_id),
                    prod_id9 INTEGER REFERENCES product (prod_id),
                    prod_id10 INTEGER REFERENCES product (prod_id),
                    pay_date TIMESTAMP NOT NULL,
                    pay_type VARCHAR(20) NOT NULL,
                    full_price FLOAT NOT NULL,
                    pay_currency VARCHAR(3) NOT NULL
);