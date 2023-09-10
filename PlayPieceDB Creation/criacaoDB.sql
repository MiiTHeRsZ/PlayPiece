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
insert into usuario (nome, cpf, id_cargo, email, senha, ativo) values('Leonardo Noboru Machado Fujimura', '43183345897', 1, 'lfujimura.pp1@playpiece.com', '-1956131982', true), ('Vinicios Mendes', '97876694993', 2, 'vmendes.pp2@playpiece.com', '-1956131982', true);

SET FOREIGN_KEY_CHECKS=1;
