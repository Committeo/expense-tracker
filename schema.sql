-- Run this file once in MySQL to create the database and table.
-- Command:  mysql -u root -p < schema.sql

CREATE DATABASE IF NOT EXISTS expense_db;
USE expense_db;

CREATE TABLE IF NOT EXISTS expenses (
    id           INT AUTO_INCREMENT PRIMARY KEY,   -- unique number, MySQL fills it automatically
    category     VARCHAR(50)  NOT NULL,            -- e.g. Travel, Food, Electricity
    description  VARCHAR(100),                     -- short note about the bill
    amount       DECIMAL(10,2) NOT NULL,           -- money value, 2 digits after decimal
    expense_date DATE NOT NULL                     -- format: YYYY-MM-DD
);

-- A few sample rows so you can test reports immediately
INSERT INTO expenses (category, description, amount, expense_date) VALUES
('Travel',      'Client visit flight',  8500.00, '2026-10-01'),
('Food',        'Team lunch',           2300.50, '2026-10-02'),
('Electricity', 'Office power bill',    6400.00, '2026-10-03'),
('Travel',      'Taxi fare',             750.00, '2026-10-05'),
('Internet',    'Broadband bill',       1200.00, '2026-10-06');
