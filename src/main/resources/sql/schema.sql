-- Updated: 15.05.2024
-- Updated: 18.05.2024

-- DROP DATABASE IF EXISTS prosvita_db;
CREATE DATABASE prosvita_db;
USE prosvita_db;

CREATE TABLE user (
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


INSERT INTO user (username, name, surname, email, password, gender, bio, role, is_consultant, status, enabled)
VALUES
    ('user1', 'John', 'Doe', 'john@example.com', 'password123', 'Male', NULL, 'USER', FALSE, 'ACTIVE', 1),
    ('admin1', 'Admin', 'Smith', 'admin@example.com', 'adminpassword', 'Male', NULL, 'ADMIN', FALSE, 'ACTIVE', 1),
    ('consultant1', 'Alice', 'Johnson', 'alice@example.com', 'consultantpass', 'Female', 'bio1', 'USER', TRUE, 'ACTIVE', 1),
    ('jennydoe', 'Jane', 'Doe', 'jenny@example.com', '$2a$10$s0k5PVZqETDBucYoATAEPuIJbXpUFphye6W5We.UgW6Hl8I5XXbSy', 'Female', 'bio1', 'USER', FALSE, 'ACTIVE', 1);

-- for testing login use username:jennydoe, password: "securepassword123"

CREATE TABLE confirmation_token (
                                    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                    token VARCHAR(255) NOT NULL,
                                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    expires_at TIMESTAMP NOT NULL,
                                    confirmed_at TIMESTAMP NULL,
                                    user_id INT UNSIGNED NOT NULL,
                                    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);