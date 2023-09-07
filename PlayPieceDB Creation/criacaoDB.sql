create database if not exists playpiece;
use playpiece;

SET FOREIGN_KEY_CHECKS=0;

drop table if exists usuario;
create table usuario(
id bigint primary key auto_increment,
nome varchar(50) not null,
cpf varchar(14) not null unique,
id_cargo smallint not null,
email varchar(60) not null unique,
senha varchar(25) not null,
ativo bool not null
);

drop table if exists cargo;
create table cargo(
id smallint primary key auto_increment,
nome varchar(25) not null unique
);

insert into cargo (nome) value ("Administrador"), ("Estoquista");
insert into usuario (nome, cpf, id_cargo, email, senha, ativo) values('Vinicius Heitor Mendes', '97876694993', 1, 'vmendes.pp1@playpiece.com', '-1956131982', true), ('Luna Freitas', '79914773125', 2, 'lfreitas.pp2@playpiece.com', '-1956131982', true);

SET FOREIGN_KEY_CHECKS=1;
