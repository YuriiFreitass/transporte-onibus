# 🚌 Transporte API

API REST desenvolvida em **Java com Spring Boot** para gerenciamento de linhas e horários de transporte público.

O projeto foi desenvolvido com foco no estudo e aplicação de conceitos de desenvolvimento backend, incluindo arquitetura em camadas, persistência de dados, validações, tratamento de exceções, testes e containerização com Docker.

## 🚀 Tecnologias

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Docker
- Docker Compose
- MapStruct
- Bean Validation
- JUnit
- Mockito

## 📚 Funcionalidades

A API permite:

- Listar linhas de ônibus
- Consultar linhas de forma paginada
- Buscar linhas
- Consultar horários de uma linha
- Consultar horários por dia
- Trabalhar com diferentes tipos de linha
- Validar dados recebidos pela API
- Tratar exceções de forma centralizada

## 🏗️ Arquitetura

O projeto utiliza uma arquitetura em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Controller

Responsável por receber as requisições HTTP e retornar as respostas da API.

### Service

Contém as regras de negócio da aplicação.

### Repository

Responsável pelo acesso aos dados utilizando Spring Data JPA.

### DTO

Utilizado para controlar os dados recebidos e retornados pela API.

### Mapper

Responsável pela conversão entre entidades e DTOs utilizando MapStruct.

## 🗄️ Banco de dados

A aplicação utiliza **MySQL** como banco de dados relacional.

A persistência é realizada utilizando:

- Spring Data JPA
- Hibernate

## 🐳 Docker

A aplicação e o banco de dados podem ser executados utilizando Docker.

O ambiente utiliza Docker Compose para subir os serviços necessários.

```bash
docker compose up --build
```

Para encerrar os containers:

```bash
docker compose down
```

## ⚙️ Configuração

Crie um arquivo `.env` na raiz do projeto com as variáveis necessárias para o banco de dados.

Exemplo:

```env
ENV_ROOT_PASSWORD=sua_senha_root
ENV_MYSQL_USER=seu_usuario
ENV_MYSQL_PASSWORD=sua_senha
```

O arquivo `.env` não deve ser enviado para o repositório.

## 🌐 API

Por padrão, a aplicação é executada em:

```text
http://localhost:8080
```

Alguns exemplos de endpoints disponíveis:

### Linhas

```http
GET /v1/linhas
```

Exemplo com paginação:

```http
GET /v1/linhas?page=0&size=20
```

### Horários de uma linha

```http
GET /v1/horarios/linha/{numeroLinha}
```

Exemplo:

```http
GET /v1/horarios/linha/101
```

## 🔗 Integração com frontend

A API também está sendo consumida por uma aplicação frontend desenvolvida em **React + TypeScript**.

O backend possui configuração global de CORS para permitir a comunicação entre o frontend e a API durante o desenvolvimento.

```text
React
localhost:5173
      ↓
Transporte API
localhost:8080
      ↓
MySQL
```

## 🧪 Testes

O projeto utiliza:

- JUnit
- Mockito

Os testes podem ser executados com:

```bash
mvn test
```

## ▶️ Executando sem Docker

Com o MySQL configurado e disponível:

```bash
mvn spring-boot:run
```

## 📁 Estrutura

```text
src/main/java
└── com.yurifreitas.transporte_onibus
    ├── config
    ├── controller
    ├── dto
    ├── entity
    ├── exception
    ├── mapper
    ├── repository
    └── service
```

## 🎯 Objetivo do projeto

Este projeto foi desenvolvido como parte dos meus estudos de **Java Backend**, buscando aplicar conceitos utilizados no desenvolvimento de APIs REST com Spring Boot em um projeto prático.

O objetivo é evoluir a aplicação gradualmente, adicionando novas funcionalidades e melhorias conforme avanço nos estudos.
