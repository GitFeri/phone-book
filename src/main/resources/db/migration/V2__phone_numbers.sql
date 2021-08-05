CREATE TABLE phone_numbers
(
    id          BIGINT AUTO_INCREMENT,
    phone_number VARCHAR (20),
    phone_number_type VARCHAR (10),
    phone_number_access varchar (10),
    person_id BIGINT,

        PRIMARY KEY (id),
    FOREIGN KEY (person_id) REFERENCES people (id)
);
