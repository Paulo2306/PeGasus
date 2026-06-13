Como os testículos foram feitos?

Os testes foram realizados de diversas formas no sistema. Uma delas foi a diferença de Cliente e Administrador, validando se o cliente realmente não segue acessar o gerenciamento de produtos, categorias e cupons, através de testes de URL. Outro deles foi uma validação de se era possível o cadastro de mais de um usuário com o mesmo nome, erro que atualmente está destruído. Também houve a verificação do css, da objeção de informações no formulário, e das relações das categorias com os produtos, comais deletear um categoria que tem produtos pertinentes e o cadastro de produtos sem categoria. Procurando sempre por algo que poderia quebrar o sistema.

ERROS:

- CSS quebrado
- Cadastro de produtos falhando informações
- Eliminar categoria que já tem um produto persistente
- Cadastro de produtos sem categoria

MELHORIAS:
- Botão de comprar que limpa o carro
- CSS diminuido com Tailwind

Relatório de dificuldades
Tivemos dificuldade em fazer relacionamentos entre as entidades, já que era preciso saber aplicar os conceitos de cardinalidade dentro do java, seria legal revelar mais conceitos.
Além disso, temos trabalho em implementar o sistema de gerenciamento de usuários, que precisava fazer o controle de segurança, para que o usuário não pudesse acessar caminhos que ele não pode.
Também ativos difíceis em entender o funcionamento do AJAX, por isso, vamos optando por não aplicar-lo.

Divisão do trabalho

Paulo Sérgio
CRUD de produtos 
CRUD de categorias
Sistema de upload de imagem
Sistema de Cupom de desconto
Criação do CSS

Guilherme Vieira
Gerenciamento de usuários
Paginação
Sistema de carro de compras
Sistema de busca
Conversão do CSS em Tailwind
