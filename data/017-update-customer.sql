ALTER TABLE `customers`
    ADD `reset_password_code` VARCHAR(64) NULL DEFAULT NULL AFTER `verification_code`, ADD `authentication_type` VARCHAR(10) NULL DEFAULT NULL AFTER `reset_password_code`;
