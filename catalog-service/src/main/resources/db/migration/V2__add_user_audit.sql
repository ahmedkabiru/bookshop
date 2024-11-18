ALTER TABLE books
    ADD COLUMN created_by varchar(255);

ALTER TABLE books
    ADD COLUMN last_modified_by varchar(255);
