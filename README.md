# API de Gestão de Usuários

API REST para cadastro, autenticação e gerenciamento de usuários, desenvolvida em **Java com Spring Boot**.

## 🚀 Tecnologias

- Java + Spring Boot
- Spring Security + JWT (autenticação/autorização)
- Spring Data JPA / Hibernate
- PostgreSQL
- MapStruct (conversão entre DTOs e entidades)
- OpenFeign (integração com API externa ViaCEP para preenchimento de endereço)
- Springdoc OpenAPI (Swagger) para documentação dos endpoints
- Docker / Docker Compose

## ✅ Testes

Cobertura de testes unitários em todas as camadas principais da aplicação:

- **Converters** (`UsuarioConverter`, `UsuarioMapper`, `UsuarioUpdateMapper`)
- **Service** (`UsuarioService`)
- **Controller** (`UsuarioController`)

> Desenvolvedor certificado em Testes Unitários.

## 🔧 Como executar

```bash
git clone https://github.com/danielyassuo/usuarios.git
cd usuarios
docker-compose up -d
./gradlew bootRun
```

A documentação dos endpoints fica disponível via Swagger UI após subir a aplicação.

----------------------------------------------------------------------------------

# User Management API

REST API for user registration, authentication and management, built with **Java and Spring Boot**.

## 🚀 Tech Stack

- Java + Spring Boot
- Spring Security + JWT (authentication/authorization)
- Spring Data JPA / Hibernate
- PostgreSQL
- MapStruct (DTO ↔ entity mapping)
- OpenFeign (integration with the external ViaCEP API for address lookup)
- Springdoc OpenAPI (Swagger) for endpoint documentation
- Docker / Docker Compose

## ✅ Tests

Unit test coverage across the main application layers:

- **Converters** (`UsuarioConverter`, `UsuarioMapper`, `UsuarioUpdateMapper`)
- **Service** (`UsuarioService`)
- **Controller** (`UsuarioController`)

> Certified in Unit Testing.

## 🔧 How to run

```bash
git clone https://github.com/danielyassuo/usuarios.git
cd usuarios
docker-compose up -d
./gradlew bootRun
```

Endpoint documentation is available via Swagger UI once the application is up.

## 👤 Author

**Daniel Yassuo da Rocha Rodrigues**
Backend Developer | Java & Spring Boot
[LinkedIn](https://linkedin.com/in/danielYassuo) · [GitHub](https://github.com/danielyassuo)
