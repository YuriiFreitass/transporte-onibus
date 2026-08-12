# Transporte Ônibus API

API REST desenvolvida em Java com Spring Boot para gerenciamento e consulta de linhas e horários do transporte público.

O projeto foi desenvolvido com foco em backend, aplicando arquitetura em camadas, persistência com banco de dados relacional, tratamento de exceções, testes automatizados, containers e versionamento do banco de dados.

## Tecnologias

- Java 21
- Spring Boot 4
- Spring MVC
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- MapStruct
- Lombok
- Bean Validation
- Flyway
- Docker
- Docker Compose
- JUnit
- Mockito
- MockMvc
- Testcontainers
- OpenAPI / Swagger
- Jsoup

## Arquitetura

A aplicação utiliza arquitetura em camadas, separando as responsabilidades entre Controller, Service e Repository.

- **Controller:** responsável por receber as requisições HTTP e expor os endpoints REST.
- **Service:** concentra as regras e operações da aplicação.
- **Repository:** realiza o acesso ao banco de dados através do Spring Data JPA.
- **DTO:** representa os dados de entrada e saída da API.
- **Mapper:** realiza a conversão entre DTOs e entidades utilizando MapStruct.
- **Entity:** representa as entidades persistidas no banco de dados.
- **Exception Handler:** centraliza o tratamento das exceções da aplicação.

## Linhas

A API permite consultar as linhas de transporte cadastradas, incluindo informações como número da linha, nome e tipo de tarifa.

### Listar linhas

```http
GET /v1/linhas
```

A consulta suporta paginação através do Spring Data.

Exemplo:

```http
GET /v1/linhas?page=0&size=10
```

### Buscar linhas por tarifa

```http
GET /v1/linhas/tarifa/{tarifa}
```

Exemplo:

```http
GET /v1/linhas/tarifa/METROPOLITANA
```

## Horários

A API permite consultar os horários associados a uma determinada linha.

```http
GET /v1/horarios/linha/{numeroLinha}
```

Exemplo:

```http
GET /v1/horarios/linha/205
```

Caso a linha informada não exista, a API retorna o status HTTP `404 Not Found`.

O tratamento de erros é realizado através de exceções customizadas e de um `GlobalExceptionHandler`.

## Swagger / OpenAPI

A documentação interativa da API é disponibilizada através do Springdoc OpenAPI.

Com a aplicação executando localmente, a interface pode ser acessada em:

```text
http://localhost:8080/swagger-ui.html
```

A especificação OpenAPI em JSON está disponível em:

```text
http://localhost:8080/v3/api-docs
```

## Banco de dados

A aplicação utiliza MySQL como banco de dados relacional.

A camada de persistência utiliza Spring Data JPA e Hibernate, com repositories baseados em `JpaRepository`.

As credenciais do banco são fornecidas através de variáveis de ambiente, evitando armazenar informações sensíveis diretamente no código-fonte.

## Flyway

O projeto utiliza Flyway para versionar e controlar alterações no schema do banco de dados.

O Hibernate está configurado para validar o schema:

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate
```

Dessa forma, alterações estruturais no banco de dados passam a ser controladas através das migrations do Flyway.

O histórico das versões aplicadas é armazenado na tabela `flyway_schema_history`.

## Docker

O projeto possui configuração Docker para facilitar a execução da aplicação e do banco de dados.

Para iniciar os containers:

```bash
docker compose up -d
```

Para encerrá-los:

```bash
docker compose down
```

Os dados do MySQL são persistidos através de volume Docker.

## Variáveis de ambiente

As configurações sensíveis são fornecidas através de variáveis de ambiente.

Exemplo:

```env
ENV_MYSQL_USER=usuario
ENV_MYSQL_PASSWORD=senha
```

O arquivo `.env` não deve ser versionado no Git.

## Testes

O projeto possui testes unitários e testes de integração.

### Testes unitários

Os testes unitários validam comportamentos isolados da aplicação utilizando JUnit e Mockito.

### Testes de integração

Os testes de integração utilizam JUnit, MockMvc, Testcontainers e MySQL.

O Testcontainers cria uma instância real e descartável do MySQL durante a execução dos testes. Dessa forma, é possível validar a integração entre as camadas da aplicação e o banco de dados em um ambiente isolado e reproduzível.

Entre os cenários testados estão:

- persistência de linhas;
- busca de linhas;
- filtro por tipo de tarifa;
- listagem de linhas através dos endpoints;
- consulta de horários por linha;
- retorno `404 Not Found` para linhas inexistentes.

## Executando o projeto

### Pré-requisitos

Para executar o projeto é necessário possuir:

- Java 21
- Maven
- Docker
- Docker Compose

### 1. Clone o repositório

```bash
git clone https://github.com/YuriiFreitass/transporte-onibus
```

Entre no diretório:

```bash
cd transporte-onibus
```

### 2. Configure as variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto e configure as credenciais necessárias para o MySQL.

### 3. Inicie os containers

```bash
docker compose up -d
```

### 4. Execute os testes

Linux/macOS:

```bash
./mvnw test
```

Windows:

```bash
mvnw.cmd test
```

### 5. Execute a aplicação

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

## Testando a API

Após iniciar a aplicação, os endpoints podem ser testados através do Swagger UI, Postman ou outro cliente HTTP.

A documentação interativa estará disponível em:

```text
http://localhost:8080/swagger-ui.html
```

## Objetivo do projeto

Este projeto foi desenvolvido como parte dos meus estudos em desenvolvimento Java Backend, com o objetivo de aplicar conceitos utilizados no desenvolvimento de APIs REST.

Os principais conceitos praticados incluem:

- desenvolvimento de APIs REST;
- arquitetura em camadas;
- DTOs e mapeamento de objetos;
- persistência com JPA/Hibernate;
- banco de dados MySQL;
- validação de dados;
- tratamento global de exceções;
- paginação;
- testes unitários;
- testes de integração;
- Docker;
- Testcontainers;
- migrations com Flyway;
- documentação com OpenAPI/Swagger.
