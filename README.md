
  

  

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/sPaRahhH)

  

[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=18622885&assignment_repo_type=AssignmentRepo)

  

----------

  

  

# Desenvolvimento Back End

  

  

## Turma 01 - Noturno - Análise e Desenvolvimento de Sistemas

  

  

Deve ser utilizado obrigatoriamente a linguagem JAVA com Spring.

  

Necessário a identificação da tecnologia utilizada na entrega do formulário com o link desse projeto

  

  

----------


## Pré-requisitos

-   Java 17
    
-   Maven (ou use o wrapper `./mvnw` incluído)
    
-   (Opcional) [Postman](https://www.postman.com/) ou `curl` para testar as APIs
    

----------

## Configuração

1.  Clone este repositório:
    
    ```bash
    git clone <repo-url>
    cd des-back-end-imula_back
    
    ```
    
2.  Crie um arquivo `.env` na raiz do projeto:
    
    ```properties
    # Chave secreta para assinatura do JWT (em Base64)
    JWT_SECRET=sua chave secreta
    
    # Tempo de expiração do token em milissegundos (ex: 3600000 = 1h)
    JWT_EXPIRATION=3600000
    
    ```
    
    > **IMPORTANTE:** adicione `.env` ao `.gitignore` para não comitar segredos.
    
3.  Gere sua própria chave secreta com OpenSSL:
    
    ```bash
    openssl rand -base64 64
    
    ```
    
    Copie a saída para `JWT_SECRET`.
    
4.  No `application.properties`, as propriedades devem apontar para as variáveis:
    
    ```properties
    token.jwt.secret=${JWT_SECRET}
    token.jwt.expiration=${JWT_EXPIRATION:3600000}
    spring.profiles.active=dev
    
    ```
    

----------

## Executando a aplicação

Use o wrapper Maven:

```bash
chmod +x mvnw            # caso ainda não tenha permissão
./mvnw clean spring-boot:run

```

Ou, se preferir, com Maven instalado:

```bash
mvn clean spring-boot:run

```

A API ficará disponível em `http://localhost:8080/`.

----------

## Endpoints principais

### 1. Cadastro de usuário (público)

```
POST /user/new

```

**Headers:** `Content-Type: application/json`

**Body:**

```json
{
  "fullname": "Nome Completo",
  "email": "usuario@exemplo.com",
  "password": "suaSenha",
  "enterprise": "NomeEmpresa"
}

```

**Resposta:** `201 Created`

----------

### 2. Login (público)

```
POST /auth/login

```

**Headers:** `Content-Type: application/json`

**Body:**

```json
{
  "username": "usuario@exemplo.com",
  "password": "suaSenha"
}

```

**Resposta:** `200 OK`

```json
{ "token": "<JWT_TOKEN>" }

```

> Use este token para acessar as rotas protegidas.

----------

### 3. Rotas protegidas (exemplo: listar usuários)

```
GET /user

```

**Headers:**

```
Authorization: Bearer <JWT_TOKEN>

```

**Resposta:** `200 OK` com JSON:

```json
[
  {
    "userId": "...",
    "fullname": "...",
    "enterprise": "..."
  }
]

```

> Retorna `401 Unauthorized` se o token faltar ou for inválido.

----------

## Fluxo de autenticação

1.  **Crie** um usuário via `POST /user/new`.
    
2.  **Realize** login em `POST /auth/login` e obtenha o token.
    
3.  **Inclua** o header `Authorization: Bearer <token>` em chamadas a rotas protegidas.
    

----------

## Testando com `curl`

```bash
# Criar usuário
curl -X POST http://localhost:8080/user/new \
  -H "Content-Type: application/json" \
  -d '{"fullname":"Teste","email":"teste@ex.com","password":"123","enterprise":"Lab"}'

# Login e pegar o token
curl -i -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"teste@ex.com","password":"123"}'

# Chamada protegida
curl http://localhost:8080/user \
  -H "Authorization: Bearer <JWT_TOKEN>"

```
