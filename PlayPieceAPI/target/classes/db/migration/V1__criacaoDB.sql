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

drop table if exists produto;
create table produto(
id bigint auto_increment primary key,
nome varchar(200) not null,
avaliacao decimal(2,1) not null,
descricao text not null,
preco decimal(10,2) not null,
quantidade int not null,
ativo bool not null
);

drop table if exists imagem;
create table imagem(
id bigint auto_increment primary key,
id_produto bigint not null,
caminho varchar(200) not null,
padrao bool not null,
ativo bool not null
);