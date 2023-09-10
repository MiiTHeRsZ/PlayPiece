-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: playpiece
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id_product` binary(16) NOT NULL,
  `ativo` tinyint(1) NOT NULL,
  `category` tinyint(1) DEFAULT NULL,
  `components` mediumtext,
  `desconto` int DEFAULT NULL,
  `description` longtext,
  `image01product` varchar(255) NOT NULL,
  `image02product` varchar(255) DEFAULT NULL,
  `image03product` varchar(255) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `value` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (_binary ';�\�7�K~��rUt\\d',0,0,'1 Livro de Regras; 1 Guia de Referência; 22 Peças de Mapa de Jornada; 2 Peças de Mapa de Batalha; 31 Miniaturas de plástico (6 Heróis; 25 Inimigos); 83 Cartas de Item; 15 Cartas de Vantagem; 28 Cartas de Medo; 28 Cartas de Dano; 10 Cartas de Terreno; 30 Cartas de Perícia Básica; 72 Cartas de Perícia de Função; 20 Cartas de Perícia de Vulnerabilidade; 21 Cartas de Perícia de Título; 6 Cartas de Herói; 3 Fichas de Terreno de Buraco/Névoa; 4 Fichas de Terreno de Barris/Mesa; 4 Fichas de Terreno de Fogueira/Estátua; 10 Fichas de Terreno de Córrego/Muro; 9 Fichas de Terreno de Arbusto/Rocha; 12 Estandartes inimigos e Suportes Plástico; 20 Fichas de Busca/Ameaça; 30 Fichas de Inspiração/Exploração; 21 Fichas de Consumo; 8 Fichas de Pessoa (4 variações); 5 Fichas de Escuridão',0,'Uma grande aventura espera por você! Embarque em uma jornada inesquecível pelo incrível mundo de J. R. R. Tolkien com O Senhor dos Anéis: Jornadas na Terra Média. Um jogo de tabuleiro cooperativo em português, que possui suporte ativo de um aplicativo exclusivo, para 1 a 5 jogadores. Assuma o papel de um dos grandes heróis do povo livre da Terra Média, testando seu poder e sabedoria em aventuras desafiadoras por um mundo épico de fantasia. Pelas suas jornadas, você enfrentará inimigos poderosos e poderá customizar suas habilidades de acordo com seu papel na Sociedade do Anel. Enquanto a escuridão avança, unificando o mal, as sombras e a corrupção, é chegada a hora de novos heróis surgirem e iniciar suas jornadas pela Terra Média. Dê vida a seus personagens nesse cenário fabuloso e transforme suas aventuras épicas em parte história em O Senhor dos Anéis: Jornadas na Terra Média.','https://www.mundogalapagos.com.br/ccstore/v1/images/?source=/file/v6809750033296313747/products/JME008_frente.png&outputFormat=JPEG&quality=0.8&alphaChannelColor=ffffff',NULL,NULL,'Jornadas Na Terra Média, Galápagos',690.00),(_binary 'IB�Я@5�\n\�р�]',1,0,'1 Livro de Regras; 18 Miniaturas; 47 Folhas de Atributos do Monstro; 6 Luvas de Atributos de Monstro; 24 Cartas de Objetvo de Batalha; 50 Fichas de Dinheiro; 1 Livro de Cenários; 17 Tabuleiros de Personagem; 24 Cartas de Missão Pessoal; 46 Fichas de Dano; 1 Livro de Registros da Cidade; 504 Cartas de Habilidade do Personagem; 150 Cartas de Evento; 9 Cartas de Cenário Aleatórias; 10 Fichas de Auxílio de Cenário; 1 Mapa; 457 Cartas de Modifcador de Ataque; 253 Cartas de Item; 40 Cartas de Masmorra Aleatória; 4 Mostradores de PV/EXP; 12 Fichas de Objetvo; 4 Cartas de Referência do Jogador; 30 Peças de Mapa Frente e Verso; 236 Fichas de Monstro; 6 Discos Elementais de Madeira; 32 Fichas de Invocação; 17 Blocos de Personagem; 1 Bloco do Grupo; 155 Peças Sobrepostas Frente e Verso; 232 Cartas de Habilidade do Monstro; 35 Caixas de Personagem; 24 Bases Plástcas; 1 Tabuleiro de Infusão Elemental e 1 marcador; 60 Fichas de Status; 85 Fichas de Personagem; 4 Folhas de adesivo',0,'Gloomhaven é um jogo de combate tático inspirado em Euros, em um mundo persistente em mudança. Os jogadores assumirão o papel de um aventureiro errante com seu próprio conjunto especial de habilidades e suas próprias razões para viajar para este escuro canto do mundo. Os jogadores têm de trabalhar em conjunto em caso de necessidade para limpar dungeons ameaçadoras e ruínas esquecidas. No processo, eles melhorarão suas habilidades com experiência e pilhagem, descobrindo novos locais para explorar e saquear, e expandindo a história pelas decisões que tomarem. Este é um jogo com um mundo persistente e em mudança jogado ao longo de muitas sessões de jogo. Depois de um cenário, os jogadores vão tomar decisões sobre o que fazer, que determinará como a história continua, como uma espécie de livro \'Escolha sua própria aventura\'. Jogando através de um cenário cooperativo onde os jogadores lutarão contra monstros automatizados usando um sistema de carta inovador para determinar a ordem de jogo e o que um jogador faz em sua vez. Essencialmente, em cada rodada, um jogador jogará duas cartas da sua mão. Cada carta tem um número no topo, e o número na primeira carta jogada vai determinar a sua ordem de iniciativa. Cada carta também tem um poder superior e inferior, e quando é a vez de um jogador na ordem de iniciativa, determinará se deve usar o poder superior de uma carta e o poder inferior da outra, ou vice-versa. Os jogadores devem ter cuidado, porém, porque ao longo do tempo eles perderão cartas permanentemente de suas mãos. Se eles levam muito tempo para limpar um calabouço, eles podem acabar exaustos e serem forçados a recuar.','https://images.tcdn.com.br/img/img_prod/493620/gloomhaven_jogo_de_tabuleiro_galapagos_1693_1_20200504094901.jpg',NULL,NULL,'Gloomhaven (2017)',1199.99),(_binary 'g�nmY�I���nAɓӶ',1,0,'1 Tabuleiro Impresso dos Dois Lados; 4 Tabuleiros de Herói; 1 Tabuleiro de Equipamento e Combate; 72 Cartas Grandes; 66 Cartas Pequenas; 41 Peças de Jogo com Bases de Plástico; 1 Torre; 15 Saquinhos para Guardar Componentes; 142 Fichas de Cartão; 9 Discos de Madeira; 5 Cubos de Madeira; 1 Peão de Madeira (narrador); 20 Dados (D6); 1 Manual de Regras; 1 Suplemento de Regras.',0,'Legends of Andor é um jogo de tabuleiro cooperativo de aventura para dois a quatro jogadores em que um grupo de heróis devem trabalhar juntos para defender um reino de fantasia contra hordas invasoras. Para proteger as fronteiras de Andor, os heróis vão embarcar em missões perigosas ao longo de cinco cenários originais (assim como um cenário final criado pelos próprios jogadores). Mas, com um sistema de jogo inteligente, os monstros mantém-se em marcha na direção do castelo, e os jogadores devem equilibrar suas prioridades com cuidado.No coração do Legends of Andor há uma narrativa única, com cenários ligados que contam uma história global. Para cada cenário, ou “Legend”, um baralho de lendas transmite o enredo de um conto por vez … um desdobramento em que os jogadores são os protagonistas. Um marcador de madeira se move ao longo da trilha do tabuleiro em pontos-chave durante cada cenário, provocando o sorteio de uma nova carta de lenda que altera os efeitos, e avança a trama da história. No final, os jogadores devem se esforçar para guiar o destino de Andor através de suas ações heroicas, trazendo um final feliz para seu conto de fantasia épica','https://m.media-amazon.com/images/I/91BOBHwOvkL._AC_UF894,1000_QL80_.jpg',NULL,NULL,'Legends of Andor',390.00);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-10 19:45:31
