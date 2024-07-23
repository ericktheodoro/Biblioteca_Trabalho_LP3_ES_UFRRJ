-- -----------------------------------------------------
--             Inserts no Schema Biblioteca
-- -----------------------------------------------------

INSERT INTO usuarios(nome, endereco, tel, usuario, senha, status, tipo) 
	VALUES('Baliu Fulano de Tal', 'Rua do Arroz, 20 - Mercado de São Sebastião', '(11) 11111-1111', 'baliu1', '1234', 'H', 'P');
INSERT INTO usuarios(nome, endereco, tel, usuario, senha, status, tipo) 
	VALUES('Pedro Fulano de Tal', 'Rua do Rio Morto, 256 - Petrópolis', '(22) 22222-2222', 'baliu2', '1234','H', 'F');
INSERT INTO usuarios(nome, endereco, tel, usuario, senha, status, tipo) 
	VALUES('CAUÃ Fulano de Tal', 'Travessa 15 de Novembro, S/N', '(33) 33333-3333', 'baliu3', '1234','H', 'A');
INSERT INTO usuarios(nome, endereco, tel, usuario, senha, status, tipo) 
	VALUES('Iago Fulano de Tal', 'AV. Brasil, 20867 - Nova Iguaçu', '(22) 44444-4444', 'baliu4', '1234','D', 'A');
-- SELECT * FROM usuarios
-- ALTER TABLE usuarios CHANGE id_usuario idUsuario VARCHAR(255);
-- ALTER TABLE usuarios CHANGE tel telefone VARCHAR(20);
-- ============================================================================
INSERT INTO editoras(nome, endereco, telefone, status) VALUES('Editora Boa Esperança', 'AV. Bras de Pina, 208 - Nova Iguaçu', '(22) 2222-2222', 'H');
INSERT INTO editoras(nome, endereco, telefone, status) VALUES('Arco Íris', 'AV. Brasil, 34208 - Quadalupe', '(33) 33333-3333', 'H');
INSERT INTO editoras(nome, endereco, telefone, status) VALUES('Moderna', 'Rua do Matoso, 699 - Centro', '(44) 44444-4444', 'H');
-- ============================================================================
INSERT INTO autores(nome, telefone, status) VALUES('Paulo Coelho', '(11) 11111-1111', 'H');
INSERT INTO autores(nome, telefone, status) VALUES('Jorge Amado', '(22) 22222-2222', 'H');
INSERT INTO autores(nome, telefone, status) VALUES('Mário Quintana', '(33) 33333-3333', 'H');
-- ============================================================================
INSERT INTO palavrachave(nome, status) VALUES('Análise de Sistemas Orientada à Objetos', 'H');
INSERT INTO palavrachave(nome, status) VALUES('Engenharia de Software', 'H');
INSERT INTO palavrachave(nome, status) VALUES('Linguagem de Programação 1', 'H');
INSERT INTO palavrachave(nome, status) VALUES('Linguagem de Programação 2', 'H');
INSERT INTO palavrachave(nome, status) VALUES('Linguagem de Programação 3', 'H');
INSERT INTO palavrachave(nome, status) VALUES('Linguagem de Programação 4', 'H');
INSERT INTO palavrachave(nome, status) VALUES('Linguagem de Programação 5', 'H');
INSERT INTO palavrachave(nome, status) VALUES('Qualidade', 'H');
INSERT INTO palavrachave(nome, status) VALUES('Teste de Software', 'H');
INSERT INTO palavrachave(nome, status) VALUES('Projeto Final', 'H');
INSERT INTO palavrachave(nome, status) VALUES('Inteligência Artificial', 'H');
INSERT INTO palavrachave(nome, status) VALUES('banco de Dados', 'H');
-- ============================================================================
INSERT INTO livros(nome, data_publicacao, prefacio) VALUES('Programação Fácil', '2001-10-30', 'H');
INSERT INTO livros(nome, data_publicacao, prefacio) VALUES('Aprendendo Java', '2010-10-30', 'H');
INSERT INTO livros(nome, data_publicacao, prefacio) VALUES('Qualidade de Software Fácil', '2006-10-30', 'H');
INSERT INTO livros(nome, data_publicacao, prefacio) VALUES('Desvendando Teste de Sofware', '1990-10-30', 'H');
INSERT INTO livros(nome, data_publicacao, prefacio) VALUES('Pyton em 10 aulas', '1995-10-30', 'H');
INSERT INTO livros(nome, data_publicacao, prefacio) VALUES('Programação Pyton', '1980-10-30', 'H');
INSERT INTO livros(nome, data_publicacao, prefacio) VALUES('Programação C++', '1966-10-30', 'H');
-- ============================================================================
INSERT INTO livros_autores(id_livro, id_autor, data) VALUES(1, 1, '2010-10-30');
INSERT INTO livros_autores(id_livro, id_autor, data) VALUES(1, 2, '2011-10-30');
INSERT INTO livros_autores(id_livro, id_autor, data) VALUES(1, 3, '2012-10-30');
INSERT INTO livros_autores(id_livro, id_autor, data) VALUES(2, 1, '2013-10-30');
INSERT INTO livros_autores(id_livro, id_autor, data) VALUES(2, 2, '2014-10-30');
INSERT INTO livros_autores(id_livro, id_autor, data) VALUES(2, 3, '2015-10-30');
INSERT INTO livros_autores(id_livro, id_autor, data) VALUES(3, 1, '2016-10-30');
-- ============================================================================
INSERT INTO livros_palavrachave(id_livro, id_palavrachave, data) VALUES(1, 1, '2010-10-30');
INSERT INTO livros_palavrachave(id_livro, id_palavrachave, data) VALUES(1, 2, '2011-10-30');
INSERT INTO livros_palavrachave(id_livro, id_palavrachave, data) VALUES(1, 3, '2012-10-30');
INSERT INTO livros_palavrachave(id_livro, id_palavrachave, data) VALUES(2, 1, '2013-10-30');
INSERT INTO livros_palavrachave(id_livro, id_palavrachave, data) VALUES(2, 2, '2014-10-30');
INSERT INTO livros_palavrachave(id_livro, id_palavrachave, data) VALUES(2, 3, '2015-10-30');
-- ============================================================================
INSERT INTO exemplares(id_livro, id_editora, reservado) VALUES(1, 1, 'S');
INSERT INTO exemplares(id_livro, id_editora, reservado) VALUES(1, 1, 'S');
INSERT INTO exemplares(id_livro, id_editora, reservado) VALUES(1, 1, 'N');
INSERT INTO exemplares(id_livro, id_editora, reservado) VALUES(2, 2, 'S');
INSERT INTO exemplares(id_livro, id_editora, reservado) VALUES(2, 2, 'S');
INSERT INTO exemplares(id_livro, id_editora, reservado) VALUES(2, 2, 'N');
INSERT INTO exemplares(id_livro, id_editora, reservado) VALUES(3, 3, 'S');
INSERT INTO exemplares(id_livro, id_editora, reservado) VALUES(3, 3, 'S');
-- ============================================================================
INSERT INTO reservas(dt_reserva, dt_reserva_emprestimo, id_usuario) VALUES('2015-03-15', '2015-04-15', 1);
INSERT INTO reservas(dt_reserva, dt_reserva_emprestimo, id_usuario) VALUES('2015-03-15', '2015-04-15', 2);
INSERT INTO reservas(dt_reserva, dt_reserva_emprestimo, id_usuario) VALUES('2015-03-15', '2015-04-15', 3);

INSERT INTO reservas_exemplares(id_reserva, id_livro, id_exemplar, dt_cancelamento) VALUES(1, 1, 1, '2022-03-15');
INSERT INTO reservas_exemplares(id_reserva, id_livro, id_exemplar, dt_cancelamento) VALUES(2, 1, 2, '2022-03-15');
INSERT INTO reservas_exemplares(id_reserva, id_livro, id_exemplar, dt_cancelamento) VALUES(3, 1, 3, '2022-03-15');
-- INSERT INTO reservas_exemplares(id_reserva, id_livro, id_exemplar, dt_cancelamento) VALUES(4, 2, 1, '2022-03-15');
-- INSERT INTO reservas_exemplares(id_reserva, id_livro, id_exemplar, dt_cancelamento) VALUES(5, 2, 2, '2022-03-15');
-- INSERT INTO reservas_exemplares(id_reserva, id_livro, id_exemplar, dt_cancelamento) VALUES(6, 3, 1, '2022-03-15');
-- ============================================================================
INSERT INTO emprestimos(dt_emprestimo, id_usuario) VALUES('2015-03-15', 1);
INSERT INTO emprestimos(dt_emprestimo, id_usuario) VALUES('2015-03-15', 2);
INSERT INTO emprestimos(dt_emprestimo, id_usuario) VALUES('2015-03-15', 3);

INSERT INTO emprestimos_exemplares(id_emprestimo, id_livro, id_exemplar, dt_devolucao) VALUES(1, 1, 1, '2022-07-15');
INSERT INTO emprestimos_exemplares(id_emprestimo, id_livro, id_exemplar, dt_devolucao) VALUES(2, 2, 1, '2022-07-15');
INSERT INTO emprestimos_exemplares(id_emprestimo, id_livro, id_exemplar, dt_devolucao) VALUES(3, 3, 1, '2022-07-15');