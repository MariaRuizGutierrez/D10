drop database if exists `acme-newspaper`;
create database `acme-newspaper`;

grant select, insert, update, delete 
	on `acme-newspaper`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `acme-newspaper`.* to 'acme-manager'@'%';