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

drop table if exists produto;
create table produto(
id bigint auto_increment primary key,
nome varchar(200) not null,
avaliacao decimal(2,1) not null,
descricao varchar(2000) not null,
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


insert into cargo (nome) value ("Administrador"), ("Estoquista");
insert into usuario (nome, cpf, id_cargo, email, senha, ativo) values('Leonardo Noboru Machado Fujimura', '43183345897', 1, 'lfujimura.pp1@playpiece.com', '-1956131982', true), ('Vinicios Mendes', '97876694993', 2, 'vmendes.pp2@playpiece.com', '-1956131982', true);
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

81 Cartas que descrevem itens mágicos, ajudantes e mais', 178.60, 1, true),('Ordem Paranormal RPG - Versão Física',0, 'DESAFIOS ALÉM DA REALIDADE: 

O domínio do sobrenatural não se manifesta em nosso mundo de maneira simples. Uma membrana oculta separa e resguarda a Realidade do Outro Lado, uma dimensão habitada por criaturas monstruosas e demônios. No entanto, essa fronteira pode ser enfraquecida pelo Medo. Aproveitando-se dessa vulnerabilidade, cultistas executam rituais sinistros para romper essa barreira e convocar seres sobrenaturais, desencadeando um caos avassalador. Para frustrar esses nefastos intentos, várias organizações de investigadores operam em escala global. Contra as forças paranormais, esses agentes constituem nossa primeira e última defesa. Neste RPG, você incorporará um agente da Ordo Realitas, uma dessas organizações, levando uma vida dupla enquanto luta tenazmente para prevenir a ascensão do caos. Seja utilizando sua astúcia, um arsenal tecnológico de ponta ou mesmo poderes advindos do Outro Lado, a responsabilidade de proteger nosso mundo recai sobre seus ombros. Este jogo oferece todos os elementos necessários para que o seu grupo vivencie suas próprias missões no cenário criado por Cellbit e lapidado por uma equipe de game designers renomados e veteranos, com mais de uma década de experiência na publicação de jogos de RPG. Agora, o destino do mundo está em suas mãos, pronto para ser moldado por suas decisões e ações.', 239.90,1,true);

insert into imagem(id_produto, caminho, padrao, ativo) values (1, 'https://a-static.mlcdn.com.br/800x560/kit-essencial-dungeons-dragons-rpg-dnd-portugues-inicial-original/lucasjogos/dnd012/3638337d7aec0977acc43902bdb4a025.jpeg', true, true), (1, 'https://a-static.mlcdn.com.br/800x560/kit-essencial-dungeons-dragons-rpg-dnd-portugues-inicial-original/lucasjogos/dnd012/b57ce455deaf5fd29f2cadf8a37b6442.jpeg', false, true), (2, 'https://images.tcdn.com.br/img/img_prod/599593/ordem_paranormal_rpg_versao_fisica_25462281_1_a00c3520424507b6535cce8b6569a131.jpg', true, true), (2,'https://images.tcdn.com.br/img/img_prod/599593/ordem_paranormal_rpg_versao_fisica_25462281_4_ec28873155858efab0833cbe85ed2bf7.jpg', false, true);

SET FOREIGN_KEY_CHECKS=1;
