
# InstaLab Back-End

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/sPaRahhH)  
[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=18622885&assignment_repo_type=AssignmentRepo)

---

## Desenvolvimento Back End  
**Turma 01 – Noturno – Análise e Desenvolvimento de Sistemas**  
Linguagem: **Java 17** com **Spring Boot**.

---

# InstaLab Back-End

**Versão:** 0.0.1-SNAPSHOT  
**Tecnologias:** Java 17 · Spring Boot 3 · Spring Security · JWT (HS512) · Spring Data JPA/Hibernate · PostgreSQL · Docker · Docker Compose

---

## 🛠 Pré-requisitos

- Java 17+ (JDK)  
- Maven (embutido: `./mvnw`)  
- Docker & Docker Compose  
- (Opcional) Cliente `psql` ou H2-Console para inspeção do banco

---

## Como rodar via Docker

1. Crie um arquivo `.env` na raiz (copiando de `.env.example`) com:

    ```dotenv
    # JWT
    JWT_SECRET=⟨chave-Base64-512bits⟩
    JWT_EXPIRATION=3600000

    # PostgreSQL
    POSTGRES_USER=instalab
    POSTGRES_PASSWORD=instalab123
    POSTGRES_DB=instalab
    DB_HOST=localhost
    DB_PORT=5432
    ```

2. Suba o banco PostgreSQL em container:

    ```bash
    docker-compose up -d
    ```

3. Limpe & inicie o back-end:

    ```bash
    ./mvnw clean spring-boot:run -Dspring-boot.run.profiles=prod
    ```

    - O **DataLoader** criará automaticamente:
      - as roles (`ROLE_USER`, `ROLE_PROFESSOR`, `ROLE_ADMIN`)
      - um usuário `admin@ucsas.com` / `admin123` com `ROLE_ADMIN` e `ROLE_PROFESSOR`.

4. Verifique no console:

    ```
    DataLoader: roles created and admin@ucsas.com seeded
    ```

---

## Segurança & JWT

- **Algoritmo**: HS512  
- **Claim** `"roles"`: lista de perfis (`["ROLE_ADMIN","ROLE_PROFESSOR"]`, etc.)  
- **Filtro**: `JwtAuthenticationFilter` extrai o claim e popula o `SecurityContext`.  
- **Métodos** protegidos via `@EnableMethodSecurity` + `@PreAuthorize`.

---

## Endpoints & Permissões

### 1. Autenticação

| Método | Rota           | Acesso   | Descrição                           |
| ------ | -------------- | -------- | ----------------------------------- |
| POST   | `/auth/login`  | Public   | Autentica e retorna JWT             |

**Body exemplo**:
```json
{ "username":"admin@ucsas.com", "password":"admin123" }
```






### 2. Usuários (`/user`)


| Método | Rota         | Permissão  | Descrição               |
| ------ | ------------ | ---------- | ----------------------- |
| POST   | `/user/new`  | ROLE_ADMIN | Cadastra novo usuário   |
| GET    | `/user`      | ROLE_ADMIN | Lista todos usuários    |
| GET    | `/user/{id}` | ROLE_ADMIN | Busca usuário por ID    |
| PUT    | `/user/{id}` | ROLE_ADMIN | Atualiza usuário        |
| DELETE | `/user/{id}` | ROLE_ADMIN | Deleta usuário          |

**Body exemplo**:

`{  "fullname":"Maria Silva",  "email":"maria@example.com",  "password":"SenhaForte!234",  "enterprise":"UCSal",  "roles":["ROLE_USER"]  // opcional; padrão ROLE_USER  }` 

----------


### 3. Software (`/software`)

| Método | Rota                      | Permissão                 | Descrição               |
| ------ | ------------------------- | ------------------------- | ----------------------- |
| POST   | `/software/new`           | ROLE_ADMIN                | Cadastrar software      |
| GET    | `/software`               | ROLE_ADMIN,ROLE_PROFESSOR | Listar todos softwares  |
| GET    | `/software/{softwareId}`  | ROLE_ADMIN                | Buscar software por ID  |
| PUT    | `/software/{softwareId}`  | ROLE_ADMIN                | Atualizar software      |

**Body exemplo (POST & PUT)**  
```json
{
  "name": "Visual Studio",
  "version": "1.0.0",
  "laboratoryId": 1
}
```
----------

### 4. Laboratório (`/laboratory`)

| Método | Rota                   | Permissão   | Descrição                       |
| ------ | ---------------------- | ----------- | ------------------------------- |
| GET    | `/laboratory`          | ROLE_ADMIN  | Listar todos laboratórios       |
| GET    | `/laboratory/{id}`     | ROLE_ADMIN  | Buscar laboratório por ID       |
| POST   | `/laboratory/new`      | ROLE_ADMIN  | Cadastrar novo laboratório      |
| PUT    | `/laboratory/{id}`     | ROLE_ADMIN  | Atualizar laboratório           |
| DELETE | `/laboratory/{id}`     | ROLE_ADMIN  | Remover software de laboratório |
**Body (POST & PUT)**  
```json
{
  "name": "Lab de Química",
  "location": "Bloco A"
}
```
---

### 5. Solicitações (`/solicitation`)

| Método | Rota                          | Permissão     | Descrição                    |
| ------ | ----------------------------- | ------------- | ---------------------------- |
| GET    | `/solicitation`               | AUTHENTICATED | Listar todas solicitações    |
| POST   | `/solicitation/new`           | AUTHENTICATED | Criar nova solicitação       |
| PUT    | `/solicitation/execute/{id}`  | AUTHENTICATED | Executar solicitação         |
| PUT    | `/solicitation/edit/{id}`     | AUTHENTICATED | Editar laboratório da solicitação |

**Body (POST `/solicitation/new`)**

```json
`{  "userId":  "uuid-do-usuario",  "softwareId":  "uuid-do-software",  "laboratoryId":  1  }`
```
**Body (PUT `/solicitation/edit/{id}`)**

```json
`1  // novo laboratoryId`
```
---

### 6. Notificações (`/notifications`)

| Método | Rota                                      | Permissão     | Descrição                       |
| ------ | ----------------------------------------- | ------------- | ------------------------------- |
| POST   | `/notifications/{professorId}`            | AUTHENTICATED | Enviar notificação a professor  |
| GET    | `/notifications?professorId={profId}`     | AUTHENTICATED | Listar notificações de professor|
| PATCH  | `/notifications/{notificationId}/read`    | AUTHENTICATED | Marcar notificação como lida    |
**Body (POST `/notifications/{professorId}`)**

```
`?message=Seu software foi aprovado`
```
----------

## Docker Compose

```version: '3.8'

services:
  db:
    image: postgres:15
    ports:
      - "${DB_PORT:-5432}:5432"
    environment:
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
      POSTGRES_DB: ${POSTGRES_DB}
    volumes:
      - db_data:/var/lib/postgresql/data

volumes:
  db_data:` 
```
----------