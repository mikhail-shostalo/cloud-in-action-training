CREATE TABLE products (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  code varchar(20) NOT NULL,
  name varchar(100) NOT NULL,
  description varchar(200) DEFAULT NULL,
  price decimal(17,2) NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE products ADD CONSTRAINT UK_product_code UNIQUE (code);

INSERT INTO products(code, name, description, price) values ('iphone10', 'Iphone 10', 'Iphone 10', 1000);
INSERT INTO products(code, name, description, price) values ('samsung10', 'Samsung 10', 'Samsung 10', 900);
INSERT INTO products(code, name, description, price) values ('xiaominote', 'Xiaomi 10', 'Xiaomi 10', 350);
INSERT INTO products(code, name, description, price) values ('applewatch6', 'Apple watch 6', 'Apple watch 6', 400);
INSERT INTO products(code, name, description, price) values ('table', 'Table', 'Table', 200);
INSERT INTO products(code, name, description, price) values ('chair', 'Chair', 'Chair', 150);
INSERT INTO products(code, name, description, price) values ('playstation5', 'Play Station 5', 'Play Station 5', 890);
INSERT INTO products(code, name, description, price) values ('macbook16', 'Apple MacBook 16', 'Apple MacBook 16', 2500);
INSERT INTO products(code, name, description, price) values ('xiaomibank', 'Xiaomi Power Bank', 'Xiaomi Power Bank', 80);
INSERT INTO products(code, name, description, price) values ('vestfrost', 'Fridge Vestfrost', 'Fridge Vestfrost', 660);
INSERT INTO products(code, name, description, price) values ('sonytv', 'Sony Smart TV', 'Sony Smart TV', 1000);

CREATE TABLE categories (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  code varchar(20) NOT NULL UNIQUE,
  name varchar(100) NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE products ADD CONSTRAINT UK_products_code UNIQUE (code);

INSERT INTO categories(code, name) values ('phones', 'Phones');
INSERT INTO categories(code, name) values ('watch', 'Watch');
INSERT INTO categories(code, name) values ('furniture', 'Furniture');
INSERT INTO categories(code, name) values ('game', 'Game');
INSERT INTO categories(code, name) values ('laptops', 'Laptops');
INSERT INTO categories(code, name) values ('fridges', 'Fridges');
INSERT INTO categories(code, name) values ('tv', 'TV');
INSERT INTO categories(code, name) values ('other', 'Other');

CREATE TABLE products2categories (
  product_code varchar(20) NOT NULL,
  category_code varchar(20) NOT NULL,
  primary key (product_code, category_code),
  FOREIGN KEY (product_code) REFERENCES products(code),
  FOREIGN KEY (category_code) REFERENCES categories(code)
);

INSERT INTO products2categories(category_code, product_code) values ('phones', 'iphone10');
INSERT INTO products2categories(category_code, product_code) values ('phones', 'samsung10');
INSERT INTO products2categories(category_code, product_code) values ('phones', 'xiaominote');
INSERT INTO products2categories(category_code, product_code) values ('watch', 'applewatch6');
INSERT INTO products2categories(category_code, product_code) values ('furniture', 'table');
INSERT INTO products2categories(category_code, product_code) values ('furniture', 'chair');
INSERT INTO products2categories(category_code, product_code) values ('game', 'playstation5');
INSERT INTO products2categories(category_code, product_code) values ('laptops', 'macbook16');
INSERT INTO products2categories(category_code, product_code) values ('other', 'xiaomibank');
INSERT INTO products2categories(category_code, product_code) values ('fridges', 'vestfrost');
INSERT INTO products2categories(category_code, product_code) values ('tv', 'sonytv');