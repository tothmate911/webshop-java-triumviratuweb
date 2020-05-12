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

CREATE TABLE web_user(
    user_id SERIAL PRIMARY KEY,
    user_name VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(40) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    user_is_active BOOLEAN NOT NULL
);

CREATE TABLE cart(
    cart_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES web_user(user_id),
    prod_id1 INTEGER REFERENCES product(prod_id),
    prod_id2 INTEGER REFERENCES product(prod_id),
    prod_id3 INTEGER REFERENCES product(prod_id),
    prod_id4 INTEGER REFERENCES product(prod_id),
    prod_id5 INTEGER REFERENCES product(prod_id),
    prod_id6 INTEGER REFERENCES product(prod_id),
    prod_id7 INTEGER REFERENCES product(prod_id),
    prod_id8 INTEGER REFERENCES product(prod_id),
    prod_id9 INTEGER REFERENCES product(prod_id),
    prod_id10 INTEGER REFERENCES product(prod_id),
    cart_is_active BOOLEAN NOT NULL
);

CREATE TABLE pay(
    pay_id SERIAL PRIMARY KEY,
    cart_id INTEGER REFERENCES cart(cart_id) NOT NULL,
    pay_date TIMESTAMP NOT NULL,
    pay_type VARCHAR(20) NOT NULL,
    full_price FLOAT NOT NULL,
    pay_currency VARCHAR(3) NOT NULL
);

INSERT INTO prod_category (cat_name, department, cat_description) VALUES
         ('Sword', 'Weapon', 'Fantasy Swords!'),
         ('Vehicle', 'Vehicle', 'Fantasy vehicles'),
         ('Guns', 'Range weapon', 'Fantasy Guns!'),
         ('Accessories', 'Accessories', 'Fantasy Accessories!');

INSERT INTO prod_supplier (sup_name, sup_description) VALUES
        ('Fantasy', 'Only Fantasy'),
        ('Sci-Fi', 'Only Sci-fi');

INSERT INTO product (prod_name, prod_description, price, currency, image_file, category_id, supplier_id) VALUES
        ('Kylo Ren Lightsaber', 'You can be the envoy of the dark side with this beauty!', 500, 'USD', 'product_1.jpg', (SELECT cat_id FROM prod_category WHERE cat_name = 'Sword'), (SELECT sup_id FROM prod_supplier WHERE sup_name = 'Fantasy')),
        ('Link Master Sword', 'Be a great king like Aragorn with his magical sword!', 99.99, 'USD', 'product_2.jpg', (SELECT cat_id FROM prod_category WHERE cat_name = 'Sword'), (SELECT sup_id FROM prod_supplier WHERE sup_name = 'Fantasy')),
        ('Alduril', 'Can not miss sword from your Zelda Collection', 300, 'USD', 'product_3.jpg', (SELECT cat_id FROM prod_category WHERE cat_name = 'Sword'), (SELECT sup_id FROM prod_supplier WHERE sup_name = 'Fantasy')),
        ('Millenium Falcon', 'Maybe old, maybe not the prettiest, but sure fly like hell!', 1000, 'USD', 'product_4.jpg', (SELECT cat_id FROM prod_category WHERE cat_name = 'Vehicle'), (SELECT sup_id FROM prod_supplier WHERE sup_name = 'Fantasy')),
        ('Enterprise', 'Go boldly where no man has ever gone before with you own spaceship!', 980.50, 'USD', 'product_5.jpg', (SELECT cat_id FROM prod_category WHERE cat_name = 'Vehicle'), (SELECT sup_id FROM prod_supplier WHERE sup_name = 'Sci-Fi')),
        ('Gotterdammerung', 'Don not have to be a dictator to look good in this!', 760, 'USD', 'product_6.jpg', (SELECT cat_id FROM prod_category WHERE cat_name = 'Vehicle'), (SELECT sup_id FROM prod_supplier WHERE sup_name = 'Sci-Fi')),
        ('The Good Samaritan', 'One HELL of a kickback!', 278, 'USD', 'product_7.jpg', (SELECT cat_id FROM prod_category WHERE cat_name = 'Guns'), (SELECT sup_id FROM prod_supplier WHERE sup_name = 'Fantasy')),
        ('Usopp Kabuto Slingshot', 'There is literally only One Piece we have of this!', 20, 'USD', 'product_8.jpg', (SELECT cat_id FROM prod_category WHERE cat_name = 'Guns'), (SELECT sup_id FROM prod_supplier WHERE sup_name = 'Fantasy')),
        ('Han Solo Blaster', 'Easily concealed under the table', 430.50, 'USD', 'product_9.jpg', (SELECT cat_id FROM prod_category WHERE cat_name = 'Guns'), (SELECT sup_id FROM prod_supplier WHERE sup_name = 'Fantasy')),
        ('Mario Hat', 'Accessory for plumbers', 15, 'USD', 'product_10.jpg', (SELECT cat_id FROM prod_category WHERE cat_name = 'Accessories'), (SELECT sup_id FROM prod_supplier WHERE sup_name = 'Fantasy')),
        ('Xenomorph Tail', 'Long,nimble,deadly!', 80, 'USD', 'product_11.jpg', (SELECT cat_id FROM prod_category WHERE cat_name = 'Accessories'), (SELECT sup_id FROM prod_supplier WHERE sup_name = 'Sci-Fi')),
        ('DragonBalls', 'You do not need to search the world for a wish', 700, 'USD', 'product_12.jpg', (SELECT cat_id FROM prod_category WHERE cat_name = 'Accessories'), (SELECT sup_id FROM prod_supplier WHERE sup_name = 'Fantasy'));
