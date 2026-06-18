# 🐴 PeGasus — Loja de Periféricos

> Sistema de e-commerce para periféricos e hardware, desenvolvido com **Spring Boot 4**, **Thymeleaf** e **MariaDB**.

---

## 📋 Índice

- [Tecnologias](#-tecnologias)
- [Funcionalidades](#-funcionalidades)
- [Como Rodar](#-como-rodar)
- [Testes Realizados](#-testes-realizados)
- [Dificuldades](#-relatório-de-dificuldades)
- [Divisão do Trabalho](#-divisão-do-trabalho)

---

## 🛠 Tecnologias

| Camada       | Tecnologia                          |
|--------------|-------------------------------------|
| Backend      | Java 21, Spring Boot 4.0.6         |
| Frontend     | Thymeleaf, Tailwind CSS (CDN)      |
| Banco        | MariaDB (porta 3307)               |
| Build        | Maven                              |
| Outros       | Font Awesome, Google Fonts (Inter)  |

---

## ✨ Funcionalidades

- **Catálogo de Produtos** — busca por nome/descrição, filtro por categoria, paginação
- **Carrinho de Compras** — adicionar produtos, aplicar cupons de desconto, finalizar compra
- **CRUD Completo** — Produtos, Categorias e Cupons de Desconto
- **Upload de Imagem** — armazenamento de imagem diretamente no banco (BLOB)
- **Gerenciamento de Usuários** — cadastro, login, ativação/desativação de contas
- **Controle de Acesso** — interceptor que diferencia Cliente e Administrador
- **Seeder Automático** — popula o banco com 12 produtos e 4 categorias na primeira execução

---

## 🚀 Como Rodar

1. Tenha o **Java 21** e o **MariaDB** (porta 3307) instalados
2. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/PeGasus.git
   cd PeGasus
   ```
3. Configure o banco em `src/main/resources/application.properties` (usuário `root`, senha `123`)
4. Execute:
   ```bash
   ./mvnw spring-boot:run
   ```
5. Acesse: [http://localhost:8081](http://localhost:8081)

---

## 🧪 Testes Realizados

Os testes foram realizados de diversas formas no sistema:

- **Controle de acesso** — validação de que o cliente não consegue acessar o gerenciamento de produtos, categorias e cupons, através de testes de URL
- **Unicidade de usuário** — verificação de que não é possível cadastrar mais de um usuário com o mesmo nome
- **Validação de formulários** — testes com falta de informações nos campos obrigatórios
- **Integridade referencial** — testes de deletar uma categoria que possui produtos vinculados, e cadastro de produtos sem categoria
- **Verificação visual** — revisão do CSS e responsividade das páginas

---

## 📝 Relatório de Dificuldades

- Tivemos dificuldade em fazer **relacionamentos entre as entidades**, já que era preciso saber aplicar os conceitos de cardinalidade dentro do Java — seria legal revelar mais conceitos sobre isso
- Além disso, tivemos trabalho em implementar o **sistema de gerenciamento de usuários**, que precisava fazer o controle de segurança para que o usuário não pudesse acessar caminhos que ele não deve
- Também achamos difícil entender o funcionamento do **AJAX**, por isso optamos por não aplicá-lo

---

## 👥 Divisão do Trabalho

### Paulo Sérgio
- CRUD de Produtos
- CRUD de Categorias
- Sistema de upload de imagem
- Sistema de cupom de desconto
- Criação do CSS

### Guilherme Vieira
- Gerenciamento de usuários
- Paginação
- Sistema de carrinho de compras
- Sistema de busca
- Conversão do CSS em Tailwind
