CREATE DATABASE IF NOT EXISTS garment;
USE garment;

CREATE TABLE customer (
                          customer_id VARCHAR(6) NOT NULL PRIMARY KEY,
                          name VARCHAR(50),
                          contact INT
);

CREATE TABLE payment (
                         payment_id VARCHAR(6) NOT NULL PRIMARY KEY,
                         amount DECIMAL(8, 3),
                         date DATE
);

CREATE TABLE orders (
                        order_id VARCHAR(6) NOT NULL PRIMARY KEY,
                        date DATE,
                        qty INT,
                        customer_id VARCHAR(6),
                        payment_id VARCHAR(6),
                        FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON UPDATE CASCADE ON DELETE CASCADE,
                        FOREIGN KEY (payment_id) REFERENCES payment(payment_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE employee (
                          employee_id VARCHAR(6) NOT NULL PRIMARY KEY,
                          name VARCHAR(50),
                          contact INT
);

CREATE TABLE fabric (
                        fabric_id VARCHAR(6) NOT NULL PRIMARY KEY,
                        color VARCHAR(15),
                        weight_kg DECIMAL(10, 4),
                        width_inch DECIMAL(10, 4)
);
CREATE TABLE sewn_clothes_stock (
                                    stock_id VARCHAR(6) NOT NULL PRIMARY KEY,
                                    qty INT,
                                    fabric_id VARCHAR(6),
                                    FOREIGN KEY (fabric_id) REFERENCES fabric(fabric_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE style (
                       style_id VARCHAR(6) NOT NULL PRIMARY KEY,
                       size VARCHAR(5),
                       qty INT,
                       employee_id VARCHAR(6),
                       stock_id VARCHAR(6),
                       FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON UPDATE CASCADE ON DELETE CASCADE,
                       FOREIGN KEY (stock_id) REFERENCES sewn_clothes_stock(stock_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE supplier (
                          supplier_id VARCHAR(6) NOT NULL PRIMARY KEY,
                          name VARCHAR(50),
                          contact INT
);

CREATE TABLE supplier_order (
                                order_id VARCHAR(6) NOT NULL PRIMARY KEY,
                                qty_kg DECIMAL(10, 4),
                                date DATE,
                                supplier_id VARCHAR(6),
                                FOREIGN KEY (supplier_id) REFERENCES supplier(supplier_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE user (
                      userId VARCHAR(10) PRIMARY KEY,
                      firstName VARCHAR(50) NOT NULL,
                      lastName VARCHAR(50) NOT NULL,
                      username VARCHAR(50) NOT NULL UNIQUE,
                      email VARCHAR(100) NOT NULL UNIQUE,
                      password VARCHAR(100) NOT NULL
);

CREATE TABLE clothes_order (
                               stock_id VARCHAR(6),
                               order_id VARCHAR(6),
                               FOREIGN KEY (stock_id) REFERENCES sewn_clothes_stock(stock_id) ON UPDATE CASCADE ON DELETE CASCADE,
                               FOREIGN KEY (order_id) REFERENCES orders(order_id) ON UPDATE CASCADE ON DELETE CASCADE

);


CREATE TABLE fabric_order (
                              fabric_id VARCHAR(6),
                              order_id VARCHAR(6),
                              FOREIGN KEY (fabric_id) REFERENCES fabric(fabric_id) ON UPDATE CASCADE ON DELETE CASCADE,
                              FOREIGN KEY (order_id) REFERENCES supplier_order(order_id) ON UPDATE CASCADE ON DELETE CASCADE
);
