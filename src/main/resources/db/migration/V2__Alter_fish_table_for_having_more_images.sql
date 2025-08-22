-- create a new table for storing fish images
DROP TABLE IF EXISTS `product_images`;

CREATE TABLE `product_images`
(
    `id`        int          NOT NULL AUTO_INCREMENT,
    `file_name` varchar(255) NOT NULL,
    `fish_id`   int          NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`fish_id`) REFERENCES fish (`id`) ON DELETE CASCADE
);


-- migrate data from `fish.image_file_name` to `product_images`
INSERT INTO product_images(`file_name`, `fish_id`)
SELECT `image_file_name` AS `file_name`,
       `id`              AS `fish_id`
FROM `fish`;


-- drop column fish.image_file_name
ALTER TABLE `fish`
    DROP COLUMN `image_file_name`;