-- -----------------------------------------------------
-- Schema Biblioteca
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Biblioteca` DEFAULT CHARACTER SET utf8 ;
USE `Biblioteca` ;

-- -----------------------------------------------------
-- Table `biblioteca`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS biblioteca.usuarios (
    id_usuario int NOT NULL AUTO_INCREMENT,
    nome 		VARCHAR(50),
    endereco 	VARCHAR(100),
    tel 		VARCHAR(20),
    status 		VARCHAR(1),
    tipo        varchar(1), 		-- VALUE  'P' or 'A' or 'F',
    PRIMARY KEY (id_usuario)
);

-- Tabela: Alunos
CREATE TABLE IF NOT EXISTS biblioteca.alunos (
    id_usuario 		int NOT NULL AUTO_INCREMENT,
    matricula 		VARCHAR(255),
    ano 			VARCHAR(255),
	PRIMARY KEY (id_usuario),
    CONSTRAINT fk_usuarios_alunos FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario)
);

-- Tabela: Professores
CREATE TABLE IF NOT EXISTS biblioteca.professores (
    id_usuario int NOT NULL AUTO_INCREMENT,
    nivel 		VARCHAR(255),
    PRIMARY KEY (id_usuario),
    CONSTRAINT fk_usuarios_professsores FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario)
);

-- Tabela: Funcionarios
CREATE TABLE IF NOT EXISTS biblioteca.funcionarios (
    id_usuario int NOT NULL AUTO_INCREMENT,
    cargo 		VARCHAR(255),
	PRIMARY KEY (id_usuario),
    CONSTRAINT fk_usuarios_funcionarios FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario)
);

-- Tabela: Editoras
CREATE TABLE IF NOT EXISTS biblioteca.editoras (
    id_editora 	int NOT NULL AUTO_INCREMENT,
    nome 		VARCHAR(255),
    endereco 	VARCHAR(255),
    telefone 	VARCHAR(255),
    contato 	VARCHAR(255),
    status 		VARCHAR(1),
    PRIMARY KEY (id_editora)
);

-- Tabela: Autores
CREATE TABLE IF NOT EXISTS biblioteca.autores (
    id_autor 	int NOT NULL AUTO_INCREMENT,
    nome 		VARCHAR(255),
    telefone 	VARCHAR(255),
    status 		VARCHAR(1),
	PRIMARY KEY (id_autor)
);

-- Tabela: PalavraChave
CREATE TABLE IF NOT EXISTS biblioteca.palavrachave (
    id_palavrachave int NOT NULL AUTO_INCREMENT,
    nome 			VARCHAR(255),
    status 			VARCHAR(255),
	PRIMARY KEY (id_palavrachave)
);

-- Tabela: Livros
CREATE TABLE IF NOT EXISTS biblioteca.livros (
    id_livro 		int NOT NULL AUTO_INCREMENT,
    nome 			VARCHAR(255),
    data_publicacao DATE,
    prefacio 		VARCHAR(255),
	PRIMARY KEY (id_livro)
);

-- Tabela: Exemplar
CREATE TABLE IF NOT EXISTS biblioteca.Exemplares (
    id_livro    int NOT NULL,
    id_exemplar int NOT NULL,
    id_editora 	int,
	PRIMARY KEY (id_livro, id_exemplar),
    CONSTRAINT fk_Livros_exemplares  FOREIGN KEY (id_livro) REFERENCES Livros (id_livro),
    CONSTRAINT fk_Editora_exemplares FOREIGN KEY (id_editora) REFERENCES Editoras (id_editora)
);

-- Tabela: Livros_autores
CREATE TABLE IF NOT EXISTS biblioteca.livros_autores (
    id_livro	int,
    id_autor 	int,
    data 		DATE,
    PRIMARY KEY (id_livro, id_autor),
    CONSTRAINT fk_livros_livros_autores   FOREIGN KEY (id_livro) REFERENCES Livros (id_livro),
    CONSTRAINT fk_autores_livros_autores FOREIGN KEY (id_autor) REFERENCES Autores (id_autor)
);

-- Tabela: PalavraChave_Livros
CREATE TABLE IF NOT EXISTS biblioteca.palavrachave_livros (
    id_palavrachave int,
    id_livro 		int,
    PRIMARY KEY (id_palavrachave, id_livro),
    CONSTRAINT fk_palavrachave_PalavraChave_livros FOREIGN KEY (id_palavrachave) REFERENCES palavrachave (id_palavrachave),
    CONSTRAINT fk_livro_PalavraChave_livros        FOREIGN KEY (id_livro)        REFERENCES livros (id_livro)
);

-- Tabela: reservas
CREATE TABLE IF NOT EXISTS biblioteca.reservas (
    id_reserva 				int NOT NULL AUTO_INCREMENT,
    dt_reserva 				DATE,
    dt_reserva_emprestimo 	DATE,
    id_usuario 				int,
	PRIMARY KEY (id_reserva),
    CONSTRAINT fk_usuarios_reservas FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario)
);

-- Tabela: reservas_exemplar
CREATE TABLE IF NOT EXISTS biblioteca.reservas_exemplares (
    id_reserva int,
    id_livro int,
    id_exemplar int,
    data_cancelamento DATE,
    PRIMARY KEY (id_reserva, id_livro, id_exemplar),
    CONSTRAINT fk_reservas_reservas_exemplares    FOREIGN KEY (id_reserva)  REFERENCES reservas (id_reserva),
    CONSTRAINT fk_exemplares_reservas_exemplares FOREIGN KEY (id_livro, id_exemplar) REFERENCES Exemplares (id_livro, id_exemplar)
);

-- Tabela: emprestimos
CREATE TABLE IF NOT EXISTS biblioteca.Emprestimos (
    id_emprestimo 	int NOT NULL AUTO_INCREMENT,
    dt_emprestimo 	DATE,
    id_usuario 		int,
	PRIMARY KEY (id_emprestimo),
    CONSTRAINT fk_emprestimos_usuarios FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario)
);

-- Tabela: emprestimo_exemplar
CREATE TABLE IF NOT EXISTS biblioteca.emprestimos_exemplares (
    id_emprestimo int,
    id_livro int,
    id_exemplar int,
    data_devolucao DATE,
    PRIMARY KEY (id_emprestimo, id_livro, id_exemplar),
    CONSTRAINT fk_emprestimos_emprestimos_exemplares FOREIGN KEY (id_emprestimo) REFERENCES emprestimos (id_emprestimo),
    CONSTRAINT fk_exemplar_emprestimos_exemplares   FOREIGN KEY (id_livro, id_exemplar)   REFERENCES exemplares (id_livro, id_exemplar)
    -- ON DELETE NO ACTION
    -- ON UPDATE NO ACTION)

);
-- ENGINE = InnoDB;
-- -----------------------------------------------------
--                  SISTEMA DE VENDAS
-- -----------------------------------------------------
/*
CREATE TABLE categorias(
   cdCategoria int     NOT NULL AUTO_INCREMENT,
   descricao  varchar(50) NOT NULL,
   CONSTRAINT pk_categorias
      PRIMARY KEY(cdCategoria)
);

CREATE TABLE produtos(
   cdProduto int      NOT NULL AUTO_INCREMENT,
   nome varchar(50) NOT NULL,
   preco float     NOT NULL,
   quantidade int     NOT NULL,
   cdCategoria int     NOT NULL,
   CONSTRAINT pk_produtos
      PRIMARY KEY(cdProduto),
   CONSTRAINT fk_produtos_categorias
      FOREIGN KEY(cdCategoria)
      REFERENCES categorias(cdCategoria)
);

CREATE TABLE clientes(
   cdCliente int      NOT NULL AUTO_INCREMENT,
   nome varchar(50) NOT NULL,
   cpf varchar(50) NOT NULL,
   telefone varchar(50) NOT NULL,
   CONSTRAINT pk_clientes
      PRIMARY KEY(cdCliente)
);

CREATE TABLE vendas(
   cdVenda int NOT NULL AUTO_INCREMENT,
   data date NOT NULL,
   valor float NOT NULL,
   pago boolean NOT NULL,
   cdCliente int,
   CONSTRAINT pk_vendas
      PRIMARY KEY(cdVenda),
   CONSTRAINT fk_vendas_clientes
      FOREIGN KEY(cdCliente)
      REFERENCES clientes(cdCliente)
);

CREATE TABLE itensdevenda(
   cdItemDeVenda int NOT NULL AUTO_INCREMENT,
   quantidade int NOT NULL,
   valor float NOT NULL,
   cdProduto int,
   cdVenda int,
								 -- PRIMARY KEY (cdVenda, cdProduto)
   CONSTRAINT pk_itensdevenda       PRIMARY KEY(cdItemDeVenda),
   CONSTRAINT fk_itensdevenda_produtos FOREIGN KEY(cdProduto) REFERENCES produtos(cdProduto),
   CONSTRAINT fk_itensdevenda_vendas   FOREIGN KEY(cdVenda)   REFERENCES vendas(cdVenda)
);

INSERT INTO clientes(nome, cpf, telefone) VALUES('Rafael','111.111.111-11','(11) 1111-1111');
INSERT INTO clientes(nome, cpf, telefone) VALUES('JoÃ£o'  ,'222.222.222-22','(22) 2222-2222');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Maria' ,'333.333.333-33','(33) 3333-3333');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Baliu' ,'885.519.037-72','(21) 99300-0118');

INSERT INTO categorias(descricao) VALUES('EletrÃ´nicos');
INSERT INTO categorias(descricao) VALUES('VestuÃ¡rio');

INSERT INTO produtos(nome, preco, quantidade, cdCategoria) VALUES('TV 32 Sony', '2000.00', '10', '1');
INSERT INTO produtos(nome, preco, quantidade, cdCategoria) VALUES('TV 40 Sony', '3000.00', '10', '1');
INSERT INTO produtos(nome, preco, quantidade, cdCategoria) VALUES('TÃªnis Nike Tri Fusion Run 40', '550.00', '10', '2');
INSERT INTO produtos(nome, preco, quantidade, cdCategoria) VALUES('TÃªnis Adidas Galaxy Trainer 36', '215.00', '10', '2');

INSERT INTO vendas(data, valor, pago, cdCliente) VALUES('2016-04-30', '5000.00', false, '1');
INSERT INTO vendas(data, valor, pago, cdCliente) VALUES('2016-04-01', '765.00' , false, '1');

INSERT INTO itensdevenda(quantidade, valor, cdProduto, cdVenda) VALUES('1', '2000.00', '1', '1');
INSERT INTO itensdevenda(quantidade, valor, cdProduto, cdVenda) VALUES('1', '3000.00', '2', '1');
INSERT INTO itensdevenda(quantidade, valor, cdProduto, cdVenda) VALUES('1', '550.00' , '3', '2');
INSERT INTO itensdevenda(quantidade, valor, cdProduto, cdVenda) VALUES('1', '215.00' , '4', '2');
*/
INSERT INTO usuarios(nome, endereco, tel, status, tipo) VALUES('PEDRO','111.111.111-11','(11) 1111-1111', 'A', 'P');
-- SELECT * FROM usuarios
-- ALTER TABLE usuarios CHANGE id_usuario idUsuario VARCHAR(255);
ALTER TABLE usuarios CHANGE tel telefone VARCHAR(255);