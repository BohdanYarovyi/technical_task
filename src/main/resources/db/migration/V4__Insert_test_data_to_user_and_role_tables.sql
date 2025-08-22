-- fill roles
INSERT INTO `role` (`name`)
VALUES ('USER'),
       ('ADMIN');

-- fill users
INSERT INTO `users` (`login`, `password`, `role_id`)
VALUES ('user', '$2a$12$Skp3X6grggnd61KgYhopw.d5hCblg4Q3PahMp9GcnPEIZmrX2wYru', 1),
       ('admin', '$2a$12$q6HOGTT7opnscTLP1NU85eHB7mJ9r2WuSpA64Xa4j38wnsQgxrzMi', 2);

-- login: user      pass: upassword
-- login: admin     pass: apassword