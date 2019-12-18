INSERT INTO utilisateur (utilisateur_id, name, last_name, email, password,active) VALUES (100, 'Alexis', 'Dumay', 'alexis.dumay@axa.fr', '$2a$10$RyY4bXtV3LKkDCutlUTYDOKd2AiJYZGp4Y7MPVdLzWzT1RX.JRZyG',true);

INSERT INTO role (role_id, role) VALUES (10, 'ROLE_ADMIN');
INSERT INTO role (role_id, role) VALUES (20, 'ROLE_UTILISATEUR');
INSERT INTO role (role_id, role) VALUES (30, 'ROLE_AMI_ESCALADE');

INSERT INTO utilisateur_role (utilisateur_id, role_id) VALUES (100, 10);
INSERT INTO utilisateur_role (utilisateur_id, role_id) VALUES (100, 20);
INSERT INTO utilisateur_role (utilisateur_id, role_id) VALUES (100, 30);

INSERT INTO utilisateur (utilisateur_id, name, last_name, email, password,active) VALUES (200, 'Tom', 'Dumoulin', 'alexlanoisette@gmail.fr', '$2a$10$RyY4bXtV3LKkDCutlUTYDOKd2AiJYZGp4Y7MPVdLzWzT1RX.JRZyG',true );
INSERT INTO utilisateur_role (utilisateur_id, role_id) VALUES (200, 10);
INSERT INTO utilisateur_role (utilisateur_id, role_id) VALUES (200, 20);
