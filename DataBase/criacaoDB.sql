cargocreate database if not exists playpiece;
use playpiece;

SET FOREIGN_KEY_CHECKS=0;

drop table if exists usuario;
create table usuario(
id bigint primary key auto_increment,
nome varchar(50) not null,
cpf varchar(14) not null unique,
id_cargo smallint not null,
email varchar(60) not null unique,
senha varchar(255) not null,
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

drop table if exists cliente;
create table cliente(
	id bigint primary key auto_increment,
	cpf varchar(14) not null unique,
	nome varchar(100) not null,
	dt_nascimento Date not null,
	genero varchar(2) not null,
	email varchar(60) not null unique,
	senha varchar(255) not null,
	end_fat bigint not null,
	ativo bool not null
);

drop table if exists endereco;
create table endereco(
	id bigint primary key auto_increment,
	id_cliente bigint not null,
	cep varchar(8) not null,
	logradouro varchar(255) not null,
	numero int not null,
	complemento varchar(255),
	bairro varchar(255) not null,
	cidade varchar(255) not null,
	uf varchar(2) not null,
	padrao bool not null,
	ativo bool not null
);


insert into cargo (nome) value ("Administrador"), ("Estoquista");
insert into usuario (nome, cpf, id_cargo, email, senha, ativo) values('Leonardo Noboru Machado Fujimura', '43183345897', 1, 'lfujimura.pp1@playpiece.com', '$2a$05$LTPYtURk5yTPzY8C3vJE6ewczRyG8JSygT1IBhOyzpRwX3YnkX8VS', true), ('Vinicios Mendes', '97876694993', 2, 'vmendes.pp2@playpiece.com', '$2a$05$LTPYtURk5yTPzY8C3vJE6ewczRyG8JSygT1IBhOyzpRwX3YnkX8VS', true);
insert into produto(nome, avaliacao, descricao, preco, quantidade, ativo) values ('Dungeons & Dragons: Kit Essencial', 5, 'Kit Essencial Dungeons Dragons Rpg Dnd Português Inicial Original
Tudo o que você precisa para criar personagens e jogar as novas aventuras nesta introdução ao maior RPG do mundo.



Dungeons & Dragons é um jogo cooperativo de narrativa que explora sua imaginação e o convida a explorar um fantástico mundo de aventuras, onde heróis lutam contra monstros, encontram tesouros e superam missões. O D&D Essentials Kit é um novo produto introdutório destinado a levar o D&D ao público interessado em mergulhar em uma história de fantasia.



Esta caixa contém o essencial que você precisa para executar um jogo de D&D com um Dungeon Master e um a cinco aventureiros. Um livro de regras recém-projetado integra os jogadores, ensinando-os a criar personagens, e a aventura incluída, Dragon of Icespire Peak , apresenta uma nova variante de regras 1 contra 1.



CONTEÚDO



1 Livro de Regras de 72 Páginas que ensina como criar personagens dos níveis 1 a 6 e como jogar

Dragão do Pico da Ponta Gélida, uma aventura introdutória.

Mapa pôster frente e verso

Escudo do Dungeon Master

6 Planilhas de personagem em branco

11 dados poliédricos

81 Cartas que descrevem itens mágicos, ajudantes e mais.', 178.60, 1, true),('Ordem Paranormal RPG - Versão Física',0, 'DESAFIOS ALÉM DA REALIDADE: 

O domínio do sobrenatural não se manifesta em nosso mundo de maneira simples. Uma membrana oculta separa e resguarda a Realidade do Outro Lado, uma dimensão habitada por criaturas monstruosas e demônios. No entanto, essa fronteira pode ser enfraquecida pelo Medo. Aproveitando-se dessa vulnerabilidade, cultistas executam rituais sinistros para romper essa barreira e convocar seres sobrenaturais, desencadeando um caos avassalador. Para frustrar esses nefastos intentos, várias organizações de investigadores operam em escala global. Contra as forças paranormais, esses agentes constituem nossa primeira e última defesa. Neste RPG, você incorporará um agente da Ordo Realitas, uma dessas organizações, levando uma vida dupla enquanto luta tenazmente para prevenir a ascensão do caos. Seja utilizando sua astúcia, um arsenal tecnológico de ponta ou mesmo poderes advindos do Outro Lado, a responsabilidade de proteger nosso mundo recai sobre seus ombros. Este jogo oferece todos os elementos necessários para que o seu grupo vivencie suas próprias missões no cenário criado por Cellbit e lapidado por uma equipe de game designers renomados e veteranos, com mais de uma década de experiência na publicação de jogos de RPG. Agora, o destino do mundo está em suas mãos, pronto para ser moldado por suas decisões e ações.', 239.90,1,true);

insert into imagem(id_produto, caminho, padrao, ativo) values (1, 'PlayPieceAPI/src/main/resources/static/images/Produtos/1/81Yz2C9wsfL.jpg', true, true), (1, 'PlayPieceAPI/src/main/resources/static/images/Produtos/1/02.jpg', false, true), (2, 'PlayPieceAPI/src/main/resources/static/images/Produtos/2/ordem_paranormal_rpg_versao_fisica_25462281_1_a00c3520424507b6535cce8b6569a131.webp', true, true), (2,'PlayPieceAPI/src/main/resources/static/images/Produtos/2/ordem_paranormal_rpg_versao_fisica_25462281_4_ec28873155858efab0833cbe85ed2bf7.webp', false, true);

insert into endereco (id_cliente,cep,logradouro,numero,complemento,bairro,cidade,uf,padrao,ativo) values (1, '04671071','Rua Sócrates','853', 'apt. 193 D', 'Vila Sofia', 'São Paulo', 'SP', true, true ), (1, '04671071','Rua Sócrates','853', 'apt. 44 C', 'Vila Sofia', 'São Paulo', 'SP', false, true );

insert into cliente (cpf, nome,dt_nascimento,genero,email,senha,end_fat,ativo) values('43183345897', 'Leonardo Fujimura', '2002-03-30', 'M', 'l.fujimura@teste.com', '$2a$05$LTPYtURk5yTPzY8C3vJE6ewczRyG8JSygT1IBhOyzpRwX3YnkX8VS', 1, true);

SET FOREIGN_KEY_CHECKS=1;