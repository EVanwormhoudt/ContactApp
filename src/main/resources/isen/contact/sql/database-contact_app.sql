CREATE TABLE IF NOT EXISTS person (
    idperson INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, 
    lastname VARCHAR(45) NOT NULL,  
    firstname VARCHAR(45) NOT NULL,
    nickname VARCHAR(45) NOT NULL,
    phone_number VARCHAR(15) NULL,
    address VARCHAR(200) NULL,
    category VARCHAR(150) NULL,
    email_address VARCHAR(150) NULL,
    birth_date DATE NULL

    );


insert into  person (lastname, firstname, nickname, phone_number, address, email_address, birth_date, category)
values ('Doe', 'John', 'Johnny', '1234567890', '123 Main Street', 'ednfd@gmail.com', '1990-01-01', 'friend');

insert into  person (lastname, firstname, nickname, phone_number, address, email_address, birth_date, category)
values ( 'Lefebvre', 'Julien', 'J', '012345678910121', 'z rue des x', 'email_address_3', '2001-06-06','family');

insert into  person (lastname, firstname, nickname, phone_number, address, email_address, birth_date, category)
values ('Vanwormhoudt', 'Elliott', 'E', '012345678910121', 'x rue des z', 'email_address_2', '2001-08-05','family')
