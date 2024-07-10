-- @author Manjunath Asundi

-- Password=Test@123
INSERT IGNORE INTO oauth_client_details
(client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, refresh_token_validity, authorized_grant_types, additional_information) 
VALUES ('R2dpxQ3vPrtfgF72', '$2a$12$XiqT0bmmBJx5Sl/dGOtSl.FrSqFzjyZuZPJVwa3In0wxKtGYeej2u', 'http://localhost:8765/login', 'READ,WRITE', '3600', '10000', 'authorization_code,password,refresh_token,implicit', '{}');

INSERT IGNORE INTO `role` VALUES
(1,'ADMIN'),
(2,'INNOVATOR'),
(3,'SPONSOR');

--INSERT IGNORE INTO `address`
--(`id`,`area`,`city`,`country`,`created_time`,`pincode`,`state`,`updated_time`)
--VALUES
--(1, 'Vijayanagar','Bangalore','india', CURRENT_TIMESTAMP, '560040',
--'Karnataka', CURRENT_TIMESTAMP);

--INSERT IGNORE INTO `corporate`
--(`id`,`gst`,`created_time`,`name`, `client_spoc_name`, `client_spoc_email`,`client_spoc_phone`,`updated_time` , `is_deleted`, `address_id`)
--VALUES
--(1, '22AAA1EA000', CURRENT_TIMESTAMP, 'sample-app', 'manjunath asundi', 'manjunathasundi07@gmail.com', '+919886988915', CURRENT_TIMESTAMP, 0, 1);
--
--INSERT IGNORE INTO `corporate_domain`(`id`, `name`, `corporate_id`)
--VALUES
--(1, 'gmail.com', 1),
--(2, 'live.com', 1);

-- password=Test@123
INSERT IGNORE INTO `user`
(`id`,`created_time`,`email`,`fname`,`lname`,`gender`,`password`,`phone`,`updated_time`,`user_name`,`is_enabled`,`is_deleted`,`created_by`,`updated_by`)
VALUES
(1,CURRENT_TIMESTAMP,'manjunath@gmail.com','manjunath','asundi','Male','$2a$12$XiqT0bmmBJx5Sl/dGOtSl.FrSqFzjyZuZPJVwa3In0wxKtGYeej2u',
'+918217641688', current_timestamp, 'manjunath@gmail.com', 1, 0, 'system', 'system');

-- password=Test@123
INSERT IGNORE INTO `user`
(`id`,`created_time`,`email`,`fname`,`lname`,`gender`,`password`,`phone`,`updated_time`,`user_name`,`is_enabled`,`is_deleted`,`created_by`,`updated_by`)
VALUES
(2,CURRENT_TIMESTAMP,'manju_innovator@gmail.com','Manjunath','Asundi','Male','$2a$12$XiqT0bmmBJx5Sl/dGOtSl.FrSqFzjyZuZPJVwa3In0wxKtGYeej2u',
'+919886988915', current_timestamp, 'manju_innovator@gmail.com', 1, 0, 'system', 'system');

-- password=Test@123
INSERT IGNORE INTO `user`
(`id`,`created_time`,`email`,`fname`,`lname`,`gender`,`password`,`phone`,`updated_time`,`user_name`,`is_enabled`,`is_deleted`,`created_by`,`updated_by`)
VALUES
(3,CURRENT_TIMESTAMP,'manju_sponsor@gmail.com','Manjunath','Asundi','Male','$2a$12$XiqT0bmmBJx5Sl/dGOtSl.FrSqFzjyZuZPJVwa3In0wxKtGYeej2u',
'+919886988915', current_timestamp, 'manju_sponsor@gmail.com', 1, 0, 'system', 'system');

INSERT IGNORE INTO `user_role`
VALUES(1,1),(2,2),(3,3);

INSERT IGNORE INTO address_seq_gen (next_val) VALUES(2);
INSERT IGNORE INTO corporate_domain_seq_gen (next_val) VALUES(2);