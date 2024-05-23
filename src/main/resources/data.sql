INSERT INTO `user` (username, name, surname, email, password, gender, bio, role, is_consultant, status, enabled)
VALUES ('user1', 'John', 'Doe', 'john@example.com', 'password123', 'Male', NULL, 'USER', FALSE, 'ACTIVE', 1),
       ('admin1', 'Admin', 'Smith', 'admin@example.com', 'adminpassword', 'Male', NULL, 'ADMIN', FALSE, 'ACTIVE', 1),
       ('consultant1', 'Alice', 'Johnson', 'alice@example.com', 'consultantpass', 'Female', 'bio1', 'USER', TRUE,
        'ACTIVE', 1),
       ('jennydoe', 'Jane', 'Doe', 'jenny@example.com', '$2a$10$s0k5PVZqETDBucYoATAEPuIJbXpUFphye6W5We.UgW6Hl8I5XXbSy',
        'Female', 'bio1', 'USER', FALSE, 'ACTIVE', 1);

-- for testing login use username: jennydoe, password: "securepassword123"

INSERT INTO subject_area (name)
VALUES ('Machine Learning'),
       ('Data Science'),
       ('Artificial Intelligence');

INSERT INTO article (title, subject_area_id, content, owner_id)
VALUES ('An In-depth Guide to Machine Learning',
        1,
        '{
            "time": 1653499651460,
            "blocks": [
                {
                    "type": "header",
                    "data": {
                        "text": "Machine Learning Overview",
                        "level": 2
                    }
                },
                {
                    "type": "paragraph",
                    "data": {
                        "text": "Machine learning is a field of artificial intelligence that uses statistical techniques to give computer systems the ability to learn from data, without being explicitly programmed."
                    }
                },
                {
                    "type": "header",
                    "data": {
                        "text": "Supervised Learning",
                        "level": 3
                    }
                },
                {
                    "type": "paragraph",
                    "data": {
                        "text": "Supervised learning is the task of learning a function that maps an input to an output based on example input-output pairs."
                    }
                },
                {
                    "type": "header",
                    "data": {
                        "text": "Unsupervised Learning",
                        "level": 3
                    }
                },
                {
                    "type": "paragraph",
                    "data": {
                        "text": "Unsupervised learning is a type of machine learning that looks for previously undetected patterns in a data set with no pre-existing labels."
                    }
                }
            ],
            "version": "2.22.2"
        }',
        1);
