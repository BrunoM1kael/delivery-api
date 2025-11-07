# Delivery Tech API

Sistema de delivery desenvolvido com Spring Boot e Java 17.

## 🚀 Tecnologias

- **Java 17 LTS** (Roteiro pedia 21, mas o ambiente está com 17)
- Spring Boot 3.2.x
- Spring Web
- Spring Data JPA
- H2 Database
- Maven

## ⚡ Recursos Modernos Utilizados

- Records (Java 14+)
- Text Blocks (Java 15+)
- Pattern Matching (Java 17+)

## 🏃‍♂️ Como executar

1.  **Pré-requisitos:** JDK 17+ instalado
2.  Clone o repositório
3.  Execute: `./mvnw spring-boot:run`
4.  A aplicação estará disponível em `http://localhost:8080`

## 📋 Endpoints da API

Abaixo estão os principais endpoints disponíveis na aplicação.

### Health & Info
- `GET /health` - Status da aplicação (inclui versão Java)
- `GET /info` - Informações da aplicação
- `GET /h2-console` - Console do banco H2 (JDBC URL: `jdbc:h2:mem:deliverydb`, User: `sa`, Senha: em branco)

### 👤 Clientes
- `POST /clientes` - Cadastra um novo cliente.
- `GET /clientes` - Lista todos os clientes ativos.
- `GET /clientes/{id}` - Busca um cliente por ID.
- `PUT /clientes/{id}` - Atualiza um cliente.
- `DELETE /clientes/{id}` - Inativa um cliente (soft delete).
- `GET /clientes/buscar?nome={nome}` - Busca clientes por nome.
- `GET /clientes/email/{email}` - Busca cliente por email.

### 🍽️ Restaurantes
- `POST /restaurantes` - Cadastra um novo restaurante.
- `GET /restaurantes` - Lista todos os restaurantes ativos.
- `GET /restaurantes/{id}` - Busca um restaurante por ID.
- `PUT /restaurantes/{id}` - Atualiza um restaurante.
- `DELETE /restaurantes/{id}` - Deleta um restaurante.
- `PUT /restaurantes/{id}/inativar` - Inativa um restaurante.
- `GET /restaurantes/categoria/{categoria}` - Busca restaurantes por categoria.

### 🍔 Produtos
- `POST /produtos` - Cadastra um novo produto (associado a um restaurante).
- `GET /produtos` - Lista todos os produtos.
- `GET /produtos/{id}` - Busca um produto por ID.
- `PUT /produtos/{id}` - Atualiza um produto.
- `DELETE /produtos/{id}` - Exclui um produto.
- `PUT /produtos/{id}/inativar` - Inativa um produto.
- `GET /produtos/restaurante/{restauranteId}` - Busca produtos de um restaurante específico.

### 📦 Pedidos
- `POST /pedidos` - Cria um novo pedido (com ID do cliente e lista de IDs de produtos).
- `GET /pedidos/cliente/{clienteId}` - Lista todos os pedidos de um cliente.
- `PUT /pedidos/{pedidoId}/{status}` - Atualiza o status de um pedido (ex: PREPARANDO, A_CAMINHO).

## 🧪 Como Testar

Para testar os endpoints, você pode usar a Collection do Postman fornecida ou criar as requisições manualmente.

1.  **Crie um Cliente:** Faça um `POST` em `/clientes`.
2.  **Crie um Restaurante:** Faça um `POST` em `/restaurantes`.
3.  **Crie um Produto:** Faça um `POST` em `/produtos`, usando o ID do restaurante criado.
4.  **Crie um Pedido:** Faça um `POST` em `/pedidos`, usando o ID do cliente e o ID do produto.
5.  **Verifique no H2:** Acesse `http://localhost:8080/h2-console` para ver os dados salvos nas tabelas.

### Exemplo: Body para `POST /clientes`
```json
{
  "nome": "Bruno Mikael",
  "email": "bruno@exemplo.com",
  "telefone": "99999-8888",
  "endereco": "Rua Fictícia, 123"
}
