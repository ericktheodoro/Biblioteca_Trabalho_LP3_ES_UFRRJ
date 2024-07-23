-- -----------------------------------------------------
--                  Schema Biblioteca
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
    usuario     VARCHAR(50),
    senha       VARCHAR(50),
    status 		VARCHAR(1),         -- Pode ser: H-abilitado ou D-esabilitado
    tipo        varchar(1), 		-- Pode ser: P-rofessor ou A-luno ou F-uncionário
    PRIMARY KEY (id_usuario)
);

-- Tabela: Alunos
CREATE TABLE IF NOT EXISTS biblioteca.alunos (
    id_usuario 		int NOT NULL AUTO_INCREMENT,
    matricula 		VARCHAR(255),
    ano 			VARCHAR(4),
	PRIMARY KEY (id_usuario),
    CONSTRAINT fk_usuarios_alunos FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario)
);

-- Tabela: Professores
CREATE TABLE IF NOT EXISTS biblioteca.professores (
    id_usuario int NOT NULL AUTO_INCREMENT,
    nivel 		VARCHAR(30),
    PRIMARY KEY (id_usuario),
    CONSTRAINT fk_usuarios_professsores FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario)
);

-- Tabela: Funcionarios
CREATE TABLE IF NOT EXISTS biblioteca.funcionarios (
    id_usuario int NOT NULL AUTO_INCREMENT,
    cargo 		VARCHAR(100),
	PRIMARY KEY (id_usuario),
    CONSTRAINT fk_usuarios_funcionarios FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario)
);

-- Tabela: Editoras
CREATE TABLE IF NOT EXISTS biblioteca.editoras (
    id_editora 	int NOT NULL AUTO_INCREMENT,
    nome 		VARCHAR(50),
    endereco 	VARCHAR(100),
    telefone 	VARCHAR(20),
    contato 	VARCHAR(30),
    status 		VARCHAR(1),
    PRIMARY KEY (id_editora)
);

-- Tabela: Autores
CREATE TABLE IF NOT EXISTS biblioteca.autores (
    id_autor 	int NOT NULL AUTO_INCREMENT,
    nome 		VARCHAR(50),
    telefone 	VARCHAR(20),
    status 		VARCHAR(1),
	PRIMARY KEY (id_autor)
);

-- Tabela: PalavraChave
CREATE TABLE IF NOT EXISTS biblioteca.palavrachave (
    id_palavrachave int NOT NULL AUTO_INCREMENT,
    nome 			VARCHAR(50),
    status 			VARCHAR(1),
	PRIMARY KEY (id_palavrachave)
);

-- Tabela: Livros
CREATE TABLE IF NOT EXISTS biblioteca.livros (
    id_livro 		int NOT NULL AUTO_INCREMENT,
    nome 			VARCHAR(50),
    data_publicacao DATE,
    prefacio 		VARCHAR(255),
	PRIMARY KEY (id_livro)
);

-- Tabela: Exemplar
CREATE TABLE IF NOT EXISTS biblioteca.Exemplares (
    id_exemplar int NOT NULL AUTO_INCREMENT,
    id_livro    int NOT NULL,
    id_editora 	int,
	reservado	varchar(1),
	PRIMARY KEY (id_exemplar),
    CONSTRAINT fk_livros_exemplares  FOREIGN KEY (id_livro) REFERENCES Livros (id_livro),
    CONSTRAINT fk_editora_exemplares FOREIGN KEY (id_editora) REFERENCES Editoras (id_editora)
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
CREATE TABLE IF NOT EXISTS biblioteca.livros_palavrachave (
    id_livro 		int,
    id_palavrachave int,
	data			date,
    PRIMARY KEY (id_livro, id_palavrachave),
    CONSTRAINT fk_palavrachave_livros_palavrachave  FOREIGN KEY (id_palavrachave) REFERENCES palavrachave (id_palavrachave),
    CONSTRAINT fk_livros_livros_palavrachave		FOREIGN KEY (id_livro)        REFERENCES livros (id_livro)
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
    id_reserva 		int,
    id_livro 		int,
    id_exemplar 	int,
    dt_cancelamento DATE,
    PRIMARY KEY (id_reserva, id_livro, id_exemplar),
    CONSTRAINT fk_reservas_reservas_exemplares     	FOREIGN KEY (id_reserva)  	REFERENCES reservas (id_reserva),
	CONSTRAINT fk_livros_reservas_exemplares		FOREIGN KEY (id_livro)  	REFERENCES livros (id_livro),	
    CONSTRAINT fk_exemplares_reservas_exemplares 	FOREIGN KEY (id_exemplar) 	REFERENCES Exemplares (id_exemplar)
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
    dt_devolucao DATE,
    PRIMARY KEY (id_emprestimo, id_livro, id_exemplar),
    CONSTRAINT fk_emprestimos_emprestimos_exemplares 	FOREIGN KEY (id_emprestimo) REFERENCES emprestimos (id_emprestimo),
	CONSTRAINT fk_livros_emprestimos_exemplares			FOREIGN KEY (id_livro)  	REFERENCES livros (id_livro),
    CONSTRAINT fk_exemplares_emprestimos_exemplares   	FOREIGN KEY (id_exemplar)   REFERENCES exemplares (id_exemplar)
    -- ON DELETE NO ACTION
    -- ON UPDATE NO ACTION)

);
-- ENGINE = InnoDB;


