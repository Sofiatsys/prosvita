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

INSERT INTO article (id, subject_area_id, title, content, owner_id, created_at, updated_at, image_url)
VALUES
    ('11', '1', 'dshggdhs', '{\"time\": 1716661025348, \"blocks\": [{\"id\": \"0ZSSR5y2EE\", \"data\": {\"text\": \"dfmdkf\"}, \"type\": \"paragraph\"}, {\"id\": \"NOqasBGINe\", \"data\": {\"text\": \"dkdkfld\"}, \"type\": \"paragraph\"}], \"version\": \"2.29.1\"}', '4', '2024-05-25 21:17:07', '2024-05-25 21:17:07', '67c95ba4-3ac3-4c11-a3ba-8d0de10c717cворкшоп2.jpg'),
    ('12', '1', 'feuiruie', '{\"time\": 1716661598806, \"blocks\": [{\"id\": \"UjgtatvhEs\", \"data\": {\"text\": \"fdoppd\"}, \"type\": \"paragraph\"}, {\"id\": \"VljzkAun1S\", \"data\": {\"text\": \"kdjfkdf\"}, \"type\": \"paragraph\"}, {\"id\": \"Un8qB1p8yc\", \"data\": {\"text\": \"jdkfjkdfd\"}, \"type\": \"paragraph\"}], \"version\": \"2.29.1\"}', '4', '2024-05-25 21:26:41', '2024-05-25 21:26:41', '59cdb6fe-f265-46ae-8654-2001c1b61a64динамічна.jpg'),
    ('13', '1', 'jfoeiro', '{\"time\": 1716662044594, \"blocks\": [{\"id\": \"DBaph4Kd1k\", \"data\": {\"text\": \"hjko\"}, \"type\": \"paragraph\"}, {\"id\": \"TP7QdNMC9T\", \"data\": {\"text\": \"jhuy\"}, \"type\": \"paragraph\"}], \"version\": \"2.29.1\"}', '4', '2024-05-25 21:34:06', '2024-05-25 21:34:06', '246f91b2-c730-49c8-9a7c-abe4b69ab1daScreenshot 2024-05-09 220900.jpg'),
    ('14', '1', 'jkjjlk', '{\"time\": 1716663052709, \"blocks\": [{\"id\": \"im7qmVHAzl\", \"data\": {\"text\": \"jkllhsdjh\"}, \"type\": \"paragraph\"}], \"version\": \"2.29.1\"}', '4', '2024-05-25 21:50:55', '2024-05-25 21:50:55', '07c38a91-3689-4379-a717-b1631720bed8communication.png'),
    ('15', '1', 'jklljjj', '{\"time\": 1716663118297, \"blocks\": [{\"id\": \"51Vtm7CQ27\", \"data\": {\"text\": \"djksdklsd\"}, \"type\": \"paragraph\"}], \"version\": \"2.29.1\"}', '4', '2024-05-25 21:52:11', '2024-05-25 21:52:11', '99ef2bb2-a053-4544-b187-23c888981326james-wainscoat-hjmED1qivmc-unsplash (2).jpg'),
    ('16', '1', 'jljjjj', '{\"time\": 1716663331346, \"blocks\": [{\"id\": \"bvMJn1pFMF\", \"data\": {\"text\": \"hkhkhhj\"}, \"type\": \"paragraph\"}, {\"id\": \"atGTg4vcd_\", \"data\": {\"text\": \"jhj\"}, \"type\": \"paragraph\"}], \"version\": \"2.29.1\"}', '4', '2024-05-25 21:55:42', '2024-05-25 21:55:42', 'f4060ec3-a44b-4daa-8479-eca7c781d0bejames-wainscoat-hjmED1qivmc-unsplash (1).jpg'),
    ('17', '1', 'jljjjj', '{\"time\": 1716663331346, \"blocks\": [{\"id\": \"bvMJn1pFMF\", \"data\": {\"text\": \"hkhkhhj\"}, \"type\": \"paragraph\"}, {\"id\": \"atGTg4vcd_\", \"data\": {\"text\": \"jhj\"}, \"type\": \"paragraph\"}], \"version\": \"2.29.1\"}', '4', '2024-05-25 21:55:50', '2024-05-25 21:55:50', '5d5e9ceb-f057-47eb-8720-430e75e5a2d5james-wainscoat-hjmED1qivmc-unsplash (1).jpg'),
    ('18', '1', 'kkkkkkk', '{\"time\": 1716663402365, \"blocks\": [{\"id\": \"-Pog1wxEcW\", \"data\": {\"text\": \"hgjfghfgff\"}, \"type\": \"paragraph\"}], \"version\": \"2.29.1\"}', '4', '2024-05-25 21:56:52', '2024-05-25 21:56:52', '4b8e7462-f3b1-4f7d-9723-612945dc23f5sequence.png'),
    ('19', '1', 'kfdlkfl', '{\"time\": 1716664134350, \"blocks\": [{\"id\": \"nufe6ewjz9\", \"data\": {\"text\": \"fdlkfdlkfld\"}, \"type\": \"paragraph\"}], \"version\": \"2.29.1\"}', '4', '2024-05-25 22:09:01', '2024-05-25 22:09:01', '3baeb682-411f-4f45-b62c-61ba286c364fkarsten-winegeart-oU6KZTXhuvk-unsplash.jpg');

# INSERT INTO article (title, subject_area_id, content, owner_id)
# VALUES ('An In-depth Guide to Machine Learning',
#         1,
#         '{
#             "time": 1653499651460,
#             "blocks": [
#                 {
#                     "type": "header",
#                     "data": {
#                         "text": "Machine Learning Overview",
#                         "level": 2
#                     }
#                 },
#                 {
#                     "type": "paragraph",
#                     "data": {
#                         "text": "Machine learning is a field of artificial intelligence that uses statistical techniques to give computer systems the ability to learn from data, without being explicitly programmed."
#                     }
#                 },
#                 {
#                     "type": "header",
#                     "data": {
#                         "text": "Supervised Learning",
#                         "level": 3
#                     }
#                 },
#                 {
#                     "type": "paragraph",
#                     "data": {
#                         "text": "Supervised learning is the task of learning a function that maps an input to an output based on example input-output pairs."
#                     }
#                 },
#                 {
#                     "type": "header",
#                     "data": {
#                         "text": "Unsupervised Learning",
#                         "level": 3
#                     }
#                 },
#                 {
#                     "type": "paragraph",
#                     "data": {
#                         "text": "Unsupervised learning is a type of machine learning that looks for previously undetected patterns in a data set with no pre-existing labels."
#                     }
#                 }
#             ],
#             "version": "2.22.2"
#         }',
#         1);
