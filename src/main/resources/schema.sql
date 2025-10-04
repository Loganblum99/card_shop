DROP TABLE IF EXISTS purchase_card;
DROP TABLE IF EXISTS card;
DROP TABLE IF EXISTS purchase;
DROP TABLE IF EXISTS customer;



CREATE TABLE customer (
	customer_id int NOT NULL AUTO_INCREMENT,
	first_name varchar(256) NOT NULL,
	last_name varchar(256) NOT NULL,
	customer_address varchar(256) NOT NULL,
	phone_number varchar(15),
	PRIMARY KEY (customer_id)
);

CREATE TABLE purchase (
	purchase_id int NOT NULL AUTO_INCREMENT,
	customer_id int NOT NULL,
	total_price DECIMAL(19,4) NOT NULL,
	quantity int,
	purchase_date DATE,
	PRIMARY KEY (purchase_id),
	FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);

CREATE TABLE card (
	card_id int NOT NULL AUTO_INCREMENT,
	card_name varchar(128) NOT NULL,
	set_name varchar(128),
	rarity varchar(15),
	price DECIMAL(19,4),
	PRIMARY KEY (card_id)
);

CREATE TABLE purchase_card (
	purchase_id int NOT NULL,
	card_id int NOT NULL, 
	FOREIGN KEY (purchase_id) REFERENCES purchase (purchase_id) ON DELETE CASCADE,
	FOREIGN KEY (card_id) REFERENCES card (card_id) ON DELETE CASCADE
);