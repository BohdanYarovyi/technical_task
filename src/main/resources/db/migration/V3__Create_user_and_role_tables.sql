DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `role`
(
    `id`   int AUTO_INCREMENT NOT NULL,
    `name` varchar(255)       NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

CREATE TABLE `users`
(
    `id`       int AUTO_INCREMENT NOT NULL,
    `login`    varchar(255)       NOT NULL UNIQUE,
    `password` varchar(255)       NOT NULL,
    `role_id`  int                NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`role_id`) REFERENCES `role`(`id`) ON DELETE RESTRICT
);