create database if not exists playpiece;
use playpiece;

SET FOREIGN_KEY_CHECKS=0;

drop table if exists usuario;
create table usuario(
usuario_id bigint primary key auto_increment,
nome varchar(50) not null,
cpf varchar(14) not null unique,
fk_cargo_id smallint not null,
email varchar(60) not null unique,
senha varchar(255) not null,
ativo bool not null
);

drop table if exists cargo;
create table cargo(
cargo_id smallint primary key auto_increment,
nome varchar(25) not null unique
);

drop table if exists produto;
create table produto(
produto_id bigint auto_increment primary key,
nome varchar(200) not null,
avaliacao decimal(2,1) not null,
descricao text not null,
preco decimal(10,2) not null,
quantidade int not null,
ativo bool not null
);

drop table if exists imagem;
create table imagem(
imagem_id bigint auto_increment primary key,
fk_produto_id bigint not null,
caminho varchar(200) not null,
padrao bool not null,
ativo bool not null
);

drop table if exists cliente;
create table cliente(
	cliente_id bigint primary key auto_increment,
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
	endereco_id bigint primary key auto_increment,
	fk_cliente_id bigint default 0,
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

drop table if exists pedido;
create table pedido(
        pedido_id bigint primary key auto_increment,
        valor_total decimal(10,2) not null,
        fk_cliente_id bigint not null,
        end_entrega_id bigint not null,
        valor_frete decimal(10,2) not null,
		data_pedido datetime not null,
        modo_pagamento varchar(2),
		pg_status varchar(2),

        foreign key (end_entrega_id) references endereco(endereco_id),
        foreign key (fk_cliente_id) references cliente(cliente_id)
);

drop table if exists item_pedido;
create table item_pedido(
	item_pedido_id bigint primary key auto_increment,
	fk_produto_id bigint not null,
	quantidade int not null,
	valor_unitario decimal(15,2) not null,
	valor_total decimal(15,2) not null,
	fk_pedido_id bigint not null,
	foreign key (fk_produto_id) references produto(produto_id),
	foreign key (fk_pedido_id) references pedido(pedido_id)
);

DROP TABLE IF EXISTS carrinho;
CREATE TABLE carrinho (
  carrinho_id bigint NOT NULL primary key AUTO_INCREMENT,
  fk_cliente_id bigint,
  FOREIGN KEY (fk_cliente_id) REFERENCES cliente (cliente_id)
);

drop table if exists item_carrinho;
create table item_carrinho(
	item_carrinho_id bigint primary key auto_increment,
	fk_produto_id bigint not null,
	quantidade int not null,
	fk_carrinho_id bigint not null,
	foreign key (fk_produto_id) references produto(produto_id),
	foreign key (fk_carrinho_id) references carrinho(carrinho_id)
);



insert into cargo (nome) value ("Administrador"), ("Estoquista");

insert into usuario (nome, cpf, fk_cargo_id, email, senha, ativo) values('Leonardo Noboru Machado Fujimura', '43183345897', 1, 'lfujimura.pp1@playpiece.com', '$2a$05$LTPYtURk5yTPzY8C3vJE6ewczRyG8JSygT1IBhOyzpRwX3YnkX8VS', true),
('Vinicios Mendes', '97876694993', 2, 'vmendes.pp2@playpiece.com', '$2a$05$LTPYtURk5yTPzY8C3vJE6ewczRyG8JSygT1IBhOyzpRwX3YnkX8VS', true);

INSERT INTO `produto` VALUES (1,"Dungeons & Dragons: Kit Essencial",5,"Kit Essencial Dungeons Dragons Rpg Dnd Português Inicial Original\nTudo o que você precisa para criar personagens e jogar as novas aventuras nesta introdução ao maior RPG do mundo.\n\nDungeons & Dragons é um jogo cooperativo de narrativa que explora sua imaginação e o convida a explorar um fantástico mundo de aventuras, onde heróis lutam contra monstros, encontram tesouros e superam missões. O D&D Essentials Kit é um novo produto introdutório destinado a levar o D&D ao público interessado em mergulhar em uma história de fantasia.\n\nEsta caixa contém o essencial que você precisa para executar um jogo de D&D com um Dungeon Master e um a cinco aventureiros. Um livro de regras recém-projetado integra os jogadores, ensinando-os a criar personagens, e a aventura incluída, Dragon of Icespire Peak , apresenta uma nova variante de regras 1 contra 1.\n\n\nCONTEÚDO:\n\n1 Livro de Regras de 72 Páginas que ensina como criar personagens dos níveis 1 a 6 e como jogar\n\nDragão do Pico da Ponta Gélida, uma aventura introdutória.\n\nMapa pôster frente e verso\n\nEscudo do Dungeon Master\n\n6 Planilhas de personagem em branco\n\n11 dados poliédricos\n\n81 Cartas que descrevem itens mágicos, ajudantes e mais.",178.60,92,1), 
(2,"Ordem Paranormal RPG - Versão Física",0,"DESAFIOS ALÉM DA REALIDADE: \n\nO domínio do sobrenatural não se manifesta em nosso mundo de maneira simples. Uma membrana oculta separa e resguarda a Realidade do Outro Lado, uma dimensão habitada por criaturas monstruosas e demônios. No entanto, essa fronteira pode ser enfraquecida pelo Medo. Aproveitando-se dessa vulnerabilidade, cultistas executam rituais sinistros para romper essa barreira e convocar seres sobrenaturais, desencadeando um caos avassalador. Para frustrar esses nefastos intentos, várias organizações de investigadores operam em escala global. Contra as forças paranormais, esses agentes constituem nossa primeira e última defesa. Neste RPG, você incorporará um agente da Ordo Realitas, uma dessas organizações, levando uma vida dupla enquanto luta tenazmente para prevenir a ascensão do caos. Seja utilizando sua astúcia, um arsenal tecnológico de ponta ou mesmo poderes advindos do Outro Lado, a responsabilidade de proteger nosso mundo recai sobre seus ombros. Este jogo oferece todos os elementos necessários para que o seu grupo vivencie suas próprias missões no cenário criado por Cellbit e lapidado por uma equipe de game designers renomados e veteranos, com mais de uma década de experiência na publicação de jogos de RPG. Agora, o destino do mundo está em suas mãos, pronto para ser moldado por suas decisões e ações.",239.90,97,1), 
(3,"YU-GI-OH! LEGENDARY COLLECTION 25TH ANNIVERSARY EDITION",3.5,"Um dos mais famosos itens de Yu-Gi-Oh! ESTAMPAS ILUSTRADAS retornou para o 25º aniversário do card game! A primeira Coleção Lendária de Yu-Gi-Oh! ESTAMPAS ILUSTRADAS renasceu como a Legendary Collection: 25th Anniversary Edition! Cada caixa contém 6 pacotes e 6 estampas Ultra Raras, incluindo estampas variantes especiais de alguns dos monstros mais famosos da série animada clássica. Além das 6 estampas Ultra Raras, entre as quais estão as favoritas e cobiçadas versões originais dos Cards Promocionais dos Deuses Egípcios, a 25th Anniversary Edition acompanha uma 7ª estampa bônus! Através dela, os Duelistas terão a prévia de uma novíssima raridade criada apenas para as celebrações do 25º aniversário: Quarter Century Secret Rare! Cada Legendary Collection: 25th Anniversary Edition inclui 1 versão Quarter Century Secret Rare aleatória de 1 de 6 estampas variantes especiais para dar aos Duelistas um gostinho do que está por vir! A Legendary Collection: 25th Anniversary Edition contém: 1 pacote de A Lenda do Dragão Branco de Olhos Azuis 1 pacote de Predadores Metálicos 1 pacote de Spell Ruler 1 pacote de Pharaoh\'s Servant 1 pacote de Dark Crisis 1 pacote de Invasion of Chaos 1 estampa Ultra Rara de Obelisco, o Atormentador 1 estampa Ultra Rara de Slifer, o Dragão Celeste 1 estampa Ultra Rara de O Dragão Alado de Rá 1 estampa Ultra Rara de Dragão Branco de Olhos Azuis 1 estampa Ultra Rara de Mago Negro 1 estampa Ultra Rara de Dragão Negro de Olhos Vermelhos 1 versão Quarter Century Secret Rare de 1 das 6 estampas mencionadas acima (aleatória) Edição de Colecionador.",179.00,100,1), 
(4,"Pokemon Card Game - Charizard Deck",0,"Desafio Estratégico Pokémon Escarlate e Violeta.Obsidiana em Chamas.Copag 88 cards em português. Chegou a nova coleção.Escarlate e Violeta.Obsidiana em Chamas COMPONENTES: 1 pacote de Evolução com 40 cartas, incluindo 1 de 4 cartas holográficas promocionais! (em português) 8 boosters de.Escarlate e Violeta.Obsidiana em Chamas.do Pokémon Estampas Ilustradas! 1 guia com dicas de construção de baralho. 1 carta codificada para o Pokémon Estampas Ilustradas Online. 1 caixa para guardar o deck. Contém 88 unidades de cartas no formato 63mm x 88mm.",89.99,100,1), 
(5,"O Senhor Dos Anéis Jornadas Na Terra Média Tabuleiro RPG",4.5,"Uma grande aventura espera por você! Embarque em uma jornada inesquecível pelo incrível mundo de J. R. R. Tolkien com O Senhor dos Anéis: Jornadas na Terra Média. Um jogo de tabuleiro cooperativo em português, que possui suporte ativo de um aplicativo exclusivo, para 1 a 5 jogadores. Assuma o papel de um dos grandes heróis do povo livre da Terra Média, testando seu poder e sabedoria em aventuras desafiadoras por um mundo épico de fantasia. Pelas suas jornadas, você enfrentará inimigos poderosos e poderá customizar suas habilidades de acordo com seu papel na Sociedade do Anel. Enquanto a escuridão avança, unificando o mal, as sombras e a corrupção, é chegada a hora de novos heróis surgirem e iniciar suas jornadas pela Terra Média. Dê vida a seus personagens nesse cenário fabuloso e transforme suas aventuras épicas em parte história em O Senhor dos Anéis: Jornadas na Terra Média.",664.90,100,1), 
(6,"Bloodborne The Board Game",3,"Chegou a hora de despertar yharnam precisa de um caçador. Em bloodborne: the board game, até 4 caçadores irão juntar forças para combater terríveis monstros que uma vez já foram humanos. Com as ruas repletas de sangue e doença, cabe a vocês colocarem um fim nos mistérios que assolam yharnam, uma cidade gótica/vitoriana em ruínas. Com criaturas bestiais e seres enlouquecidos pelo caminho, as cartas de combate serão de grande ajuda em sua jornada, oferecendo uma mecânica única para enfrentar inimigos e mitigar a sorte. Ainda assim, a morte é quase certa, mas não se preocupe, porque em bloodborne ela não é o fim. Desperte no sonho do caçador, recupere suas energias e volte pronto para derrotar monstros e chefões sedentos por sangue. Além de uma narrativa única, o jogo que é sucesso no kickstarter ainda traz um modo solo para os caçadores mais corajosos, prontos para percorrer as ruas de yharnam à própria sorte. com mecânicas desenvolvidas pelo renomado designer eric lang, miniaturas incríveis de chefes, monstros e caçadores, além de uma campanha completa e imersiva, Bloodborne: The Board Game é a experiência definitiva para os amantes da franquia de videogames e para aqueles apaixonados por histórias de horror e mistério.",776.38,100,1), 
(7,"War Jogo Tabuleiro Edição 50 Anos",4,"WAR 50 ANOS Nesse ano a GROW comemora seus 50 anos junto com o jogo que inicio sua história, o WAR! Para celebrar essa data especial estamos lançando uma quantidade limitada dessa edição de 50 anos do WAR. Foi feito todo um trabalho para deixar esse produto único e comemorar com todos essa data especial! Além disso, este produto pode ser jogado com a regra tradicional do WAR ou com a nova regra para jogar em equipes!",206.79,100,1), 
(8,"UNO!",5,"UNO™, o clássico jogo de cartas de combinar cores e números que é fácil de pegar...impossível de largar, agora vem com curingas personalizáveis para maior emoção! Os jogadores revezam na competição para se livrar de todas as cartas, combinando uma carta da mão com a carta atual mostrada no topo do baralho. Cartas de ação especiais proporcionam momentos que mudam o jogo e ajudam a derrotar os oponentes! Use a carta Trocar as mãos para trocar cartas com qualquer outro oponente e use os 3 curingas personalizáveis (e apagáveis) para escrever suas próprias regras! Você encontrará 19 cartas de cada cor (vermelho, verde, azul e amarelo), além de 8 cartas Comprar duas, Inverter e Pular em todas as cores, junto com 4 cartas Curingas, 4 cartas Comprar mais quatro, 1 carta Trocar as mãos e 3 cartas Personalizáveis. Se você não conseguir fazer uma combinação, você deve comprar da pilha central! Não se esqueça de gritar \"UNO\" quando tiver apenas uma carta restante! O jogador que se livrar de todas as cartas em sua mão marca pontos por quaisquer cartas que seus oponentes estiverem segurando. O primeiro jogador a atingir 500 pontos ganha.",19.90,100,1), 
(9,"JOGO DE TABULEIRO DETETIVE COM APLICATIVO -ESTRELA",3.5,"Jogo Detetive com App - Estrela!\n\nUm jogo de investigação acima de qualquer suspeita! Tudo começou na mansão de um rico industrial, Dr. Pessoa, a vítima do crime. Como um verdadeiro Sherlock, você está lá. Só que além de detetive, você também é um suspeito! Para chegar cada vez mais perto da solução deste mistério, vá entrando com seu peão nos possíveis locais do crime e dando palpites sobre o culpado e arma usada. Percorra a cidade e colete as provas que apontemos assassino, os inocentes, a cena e a arma do crime. Tire sua deduções e descubra a cada partida, um novo e emocionante mistério!\n\nNúmero de Participantes: De 2 a 6 pessoas",135.90,100,1), 
(10,"Super Banco Imobiliário",4.5,"O mercado de imóveis se modernizou e está cada vez mais competitivo. Diversificar os investimentos virou palavra de ordem. O Super Banco Imobiliário traz todo o dinamismo do mundo dos negócios para os dias de hoje. O tradicional jogo Super Banco Imobiliário, com a máquina de crédito e débito para fazer as transações bancárias. Inclui 1 tabuleiro, 28 títulos de posse, 6 cartões, 80 casas, 2 dados, 6 marcadores de metal, 1 máquina de cartão (pilhas não inclusas), 32 cartões noticia e 1 manual de instruções.",176.90,100,1), 
(11,"Jogo da Vida",4,"Trilhe o seu caminho em busca do sucesso. Desenvolva a sua carreira, ganhe dinheiro, case e tenha filhos. O jogo da vida é a simulação da vida real com muita diversão.",101.18,100,1), 
(12,"Jogo Tabuleiro Firmamentum Guerra Sobrenatural - Pais e Filhos",3.5,"Jogo Tabuleiro Firmamentum Guerra Sobrenatural - Pais e Filhos\n\nUma batalha direta e dinâmica repleta de emoções. O Jogo contém um ritmo alucinante, reviravoltas constantes, que tornam a dinâmica intensa e envolvente e uma mecânica fácil. Firmamentum A Guerra Sobrenatural traz uma movimentação simples e estratégica, combates emocionantes e muita tensão no melhor estilo. Morte súbita? Onde tudo pode acontecer a qualquer momento.\n\nRecomendado a partir dos 12 anos\n\n7 Ficheiros (personagens)\n1 Ficheiro (resumo de regras)\n60 Cartas\n1 Tabuleiro\n32 Personagens\n1 Baú\n32 Suportes\n32 Peões Plásticos\n7 Dados\n50 Fichas Cartonadas\n1 Regra",109.27,100,1);

INSERT INTO `imagem` VALUES (1,1,'/src/main/resources/static/images/Produtos/1/0.jpeg',1,1),
(2,1,'/src/main/resources/static/images/Produtos/1/1.jpeg',0,1),
(3,2,'/src/main/resources/static/images/Produtos/2/0.webp',1,1),
(4,2,'/src/main/resources/static/images/Produtos/2/1.webp',0,1),
(5,3,'/src/main/resources/static/images/Produtos/3/000.jpeg',0,1),
(6,3,'/src/main/resources/static/images/Produtos/3/001.jpeg',1,1),
(7,3,'/src/main/resources/static/images/Produtos/3/002.jpeg',0,1),
(8,4,'/src/main/resources/static/images/Produtos/4/000.jpeg',1,1),
(9,5,'/src/main/resources/static/images/Produtos/5/000.jpeg',1,1),
(10,6,'/src/main/resources/static/images/Produtos/6/000.jpeg',1,1),
(11,7,'/src/main/resources/static/images/Produtos/7/000.jpeg',1,1),
(12,8,'/src/main/resources/static/images/Produtos/8/000.jpeg',1,1),
(13,9,'/src/main/resources/static/images/Produtos/9/000.jpeg',1,1),
(14,10,'/src/main/resources/static/images/Produtos/10/000.jpeg',1,1),
(15,11,'/src/main/resources/static/images/Produtos/11/000.jpeg',1,1),
(16,12,'/src/main/resources/static/images/Produtos/12/000.jpeg',1,1);

insert into endereco (fk_cliente_id,cep,logradouro,numero,complemento,bairro,cidade,uf,padrao,ativo) values (1, '04671071','Rua Sócrates','853', 'apt. 193 D', 'Vila Sofia', 'São Paulo', 'SP', true, true ),
(1, '04671071','Rua Sócrates','853', 'apt. 44 C', 'Vila Sofia', 'São Paulo', 'SP', false, true),
(2, '06331110','Rua São Lucas Evangelista','377', '', 'Jardim Cibele', 'Carapicuíba', 'SP', true, true),
(2, '06331110','Rua São Lucas Evangelista','377', '', 'Jardim Cibele', 'Carapicuíba', 'SP', false, true);

insert into cliente (cpf,nome,dt_nascimento,genero,email,senha,end_fat,ativo) values('43183345897', 'Leonardo Fujimura', '2002-03-30', 'M', 'l.fujimura@teste.com', '$2a$05$LTPYtURk5yTPzY8C3vJE6ewczRyG8JSygT1IBhOyzpRwX3YnkX8VS', 1, true),
('38479946890', 'Gustavo Leme', '1999-05-29', 'M', 'g.leme@test.com', '$2a$05$LTPYtURk5yTPzY8C3vJE6ewczRyG8JSygT1IBhOyzpRwX3YnkX8VS', 3, true);

INSERT INTO carrinho VALUES (1,1),(2,2);

SET FOREIGN_KEY_CHECKS=1;