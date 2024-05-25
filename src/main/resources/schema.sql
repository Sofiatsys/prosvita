-- Updated: 22.05.2024

-- DROP DATABASE IF EXISTS prosvita_db;
CREATE DATABASE IF NOT EXIST prosvita_db;
USE prosvita_db;

CREATE TABLE `user` (
                      id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(16) UNIQUE NOT NULL,
                      name VARCHAR(50) NOT NULL,
                      surname VARCHAR(50) NOT NULL,
                      email VARCHAR(255) UNIQUE NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      gender VARCHAR(30),
                      bio TEXT,
                      profile_picture VARCHAR(255),
                      role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
                      is_consultant BOOLEAN DEFAULT FALSE,
                      status ENUM('ACTIVE', 'INACTIVE', 'BANNED') NOT NULL DEFAULT 'ACTIVE',
                      enabled BOOLEAN DEFAULT FALSE,  -- Added enabled column
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE confirmation_token (
                                    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                    token VARCHAR(255) NOT NULL,
                                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    expires_at TIMESTAMP NOT NULL,
                                    confirmed_at TIMESTAMP NULL,
                                    user_id INT UNSIGNED NOT NULL,
                                    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE subject_area (
                              id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(255) NOT NULL
);

CREATE TABLE article (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    subject_area_id SMALLINT UNSIGNED NOT NULL,
    title VARCHAR(255) NOT NULL,
    content JSON NOT NULL,
    owner_id INT UNSIGNED NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (subject_area_id) REFERENCES subject_area(id), -- TODO: add on delete?
    FOREIGN KEY (owner_id) REFERENCES user(id) ON DELETE CASCADE -- TODO: change on delete?
);