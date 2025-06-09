-- V1__init.sql
CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        order_id VARCHAR(255),
                        customer_id VARCHAR(255),
                        product_id VARCHAR(255),
                        quantity INTEGER,
                        shipping_address VARCHAR(255),
                        amount NUMERIC(19,2)
);