INSERT INTO category (id, description) VALUES(1, 'Comic Books');
INSERT INTO category (id, description) VALUES(2, 'Movies');
INSERT INTO category (id, description) VALUES(3, 'Books');

INSERT INTO supplier (id, name) VALUES(1, 'Amazon');
INSERT INTO supplier (id, name) VALUES(2, 'Ali Express');

INSERT INTO product (id, name, available_quantity, fk_category, fk_supplier) VALUES(1, 'The know unknow', 10, 1, 1);
INSERT INTO product (id, name, available_quantity, fk_category, fk_supplier) VALUES(2, 'Hereditary', 5, 2, 1);
INSERT INTO product (id, name, available_quantity, fk_category, fk_supplier) VALUES(3, 'Scream', 18, 2, 2);