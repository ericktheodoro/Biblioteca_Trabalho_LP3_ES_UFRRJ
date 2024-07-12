-- -----------------------------------------------------
--                  SISTEMA DE VENDAS
-- -----------------------------------------------------
INSERT INTO clientes(nome, cpf, telefone) VALUES('Rafael','111.111.111-11','(11) 1111-1111');
INSERT INTO clientes(nome, cpf, telefone) VALUES('JoÃo'  ,'222.222.222-22','(22) 2222-2222');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Maria' ,'333.333.333-33','(33) 3333-3333');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Baliu' ,'885.519.037-72','(21) 99300-0118');

INSERT INTO categorias(descricao) VALUES('Eletronicos');
INSERT INTO categorias(descricao) VALUES('Vestuário');

INSERT INTO produtos(nome, preco, quantidade, cdCategoria) VALUES('TV 32 Sony', '2000.00', '10', '1');
INSERT INTO produtos(nome, preco, quantidade, cdCategoria) VALUES('TV 40 Sony', '3000.00', '10', '1');
INSERT INTO produtos(nome, preco, quantidade, cdCategoria) VALUES('Tenis Nike Tri Fusion Run 40', '550.00', '10', '2');
INSERT INTO produtos(nome, preco, quantidade, cdCategoria) VALUES('Tenis Adidas Galaxy Trainer 36', '215.00', '10', '2');

INSERT INTO vendas(data, valor, pago, cdCliente) VALUES('2016-04-30', '5000.00', false, '1');
INSERT INTO vendas(data, valor, pago, cdCliente) VALUES('2016-04-01', '765.00' , false, '1');

INSERT INTO itensdevenda(quantidade, valor, cdProduto, cdVenda) VALUES('1', '2000.00', '1', '1');
INSERT INTO itensdevenda(quantidade, valor, cdProduto, cdVenda) VALUES('1', '3000.00', '2', '1');
INSERT INTO itensdevenda(quantidade, valor, cdProduto, cdVenda) VALUES('1', '550.00' , '3', '2');
INSERT INTO itensdevenda(quantidade, valor, cdProduto, cdVenda) VALUES('1', '215.00' , '4', '2');
