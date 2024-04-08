CREATE DATABASE hideme;
USE hideme;
-- 

CREATE TABLE users(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- 

CREATE TABLE userdata(
    user_id INT NOT NULL,
    site_name VARCHAR(255) NOT NULL,
    site_username VARCHAR(255) NOT NULL,
    site_password VARCHAR(255) NOT NULL,
    last_modified DATETIME NOT NULL DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- 

CREATE TABLE deleted_data(
    user_id INT NOT NULL,
    site_name VARCHAR(255) NOT NULL,
    site_username VARCHAR(255) NOT NULL,
    site_password VARCHAR(255) NOT NULL,
    last_modified DATETIME NOT NULL DEFAULT NOW(),
    deleted_time DATETIME NOT NULL DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);