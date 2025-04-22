
  

  

  

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/sPaRahhH)

  

  

[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=18622885&assignment_repo_type=AssignmentRepo)

  

  

----------

  

  

  

# Desenvolvimento Back End

  

  

  

## Turma 01 - Noturno - Análise e Desenvolvimento de Sistemas

  

  

  

Deve ser utilizado obrigatoriamente a linguagem JAVA com Spring.

  

  

Necessário a identificação da tecnologia utilizada na entrega do formulário com o link desse projeto

  

  

  

----------

  
  






## 🛠️ Pré-requisitos

- **Java 17**
- **Maven** (ou use o wrapper `./mvnw` incluído)
- (Opcional) [Postman](https://www.postman.com/) ou `curl` para testes

---

##  Configuração Local

1. **Clone** o repositório e acesse a pasta:
   ```bash
   git clone <repo-url>
   cd des-back-end-imula_back
   ```

2. Crie um arquivo `.env` na raiz com estas variáveis (adapte `JWT_EXPIRATION` se quiser outro prazo):
   ```ini
   JWT_SECRET=<sua_chave_base64_com_~512_bits>
   JWT_EXPIRATION=3600000   # em milissegundos (1h)
   ```
   > Gere uma chave com:
> ```bash
> openssl rand -base64 64
> ```

3. No `src/main/resources/application-dev.properties`, verifique:
   ```properties
   token.jwt.secret=${JWT_SECRET}
   token.jwt.expiration=${JWT_EXPIRATION:3600000}
   spring.profiles.active=dev
   ```

---

##  Build e Execução

Use o wrapper:
```bash
chmod +x mvnw       # se necessário
./mvnw clean spring-boot:run
```
Ou, com Maven instalado:
```bash
mvn clean spring-boot:run
```
A aplicação estará em `http://localhost:8080/`.

Para acessar o console H2 (banco em memória):
```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:instalab
``` 

---

## Fluxo de Autenticação (JWT)

1. **Registro**: `POST /user/new` (sem autenticação)  
2. **Login**:    `POST /auth/login` (sem autenticação) → retorna `{ "token": "<JWT>" }`  
3. **Requisições Protegidas**: incluir header `Authorization: Bearer <JWT>`

---

## Endpoints

### 1. Cadastro de Usuário (público)
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
  "enterprise": "EmpresaX"
}
```
**Resposta:** `201 Created` com JSON do usuário.

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

### 3. Listar Usuários (protegido)
```
GET /user
```  
**Headers:**
```
Authorization: Bearer <JWT_TOKEN>
```
**Resposta:** `200 OK`  
```json
[
  { "userId": "...", "fullname": "...", "enterprise": "..." },
  …
]
```

### 4. Notificações

#### a) Enviar notificação (protegido)
```
POST /notifications/{professorId}?message=<texto>
```  
**Headers:** `Authorization: Bearer <JWT_TOKEN>`  
**Exemplo URL:**
```
http://localhost:8080/notifications/c0297b74-5dfd-46be-ade2-471ff1247b7d?message=Solicita%C3%A7%C3%A3o%20conclu%C3%ADda
```
**Resposta:** `200 OK` com JSON:
```json
{
  "id":"<notifUUID>",
  "message":"Solicitação concluída",
  "sentAt":"2025-04-22T...",
  "read":false
}
```

#### b) Listar notificações (protegido)
```
GET /notifications?professorId={professorId}
```  
**Headers:** `Authorization: Bearer <JWT_TOKEN>`  
**Resposta:** `200 OK`
```json
[
  { "id":"...", "message":"...","sentAt":"...","read":false },
  …
]
```

#### c) Marcar notificação como lida (protegido)
```
PATCH /notifications/{notificationId}/read
```  
**Headers:** `Authorization: Bearer <JWT_TOKEN>`  
**Resposta:** `204 No Content`

---

## Tratamento Global de Erros

Respostas padronizadas via `GlobalExceptionHandler`:

| Status | Causa                                | Exemplo de resposta                                               |
|:------:|:-------------------------------------|:------------------------------------------------------------------|
| 400    | Validação de DTO ou parâmetros errados | `{status:400, errors:[{field,..}], timestamp:...}`              |
| 401    | Credenciais inválidas                | `{status:401, error:"Usuário ou senha inválidos", timestamp:...}`|
| 404    | Recurso não encontrado               | `{status:404, error:"... not found", timestamp:...}`            |
| 500    | Falha interna                        | `{status:500, error:"Erro interno: ...", timestamp:...}`        |

---

## Testes com curl

```bash
# 1) Criar usuário
curl -i -X POST http://localhost:8080/user/new \
  -H "Content-Type: application/json" \
  -d '{"fullname":"Teste","email":"teste@ex.com","password":"123","enterprise":"Lab"}'

# 2) Login
curl -i -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"teste@ex.com","password":"123"}'

# 3) Chamada protegida
curl -i http://localhost:8080/user \
  -H "Authorization: Bearer <JWT_TOKEN>"

# 4) Enviar notificação
curl -i -X POST "http://localhost:8080/notifications/{professorId}?message=Ok" \
  -H "Authorization: Bearer <JWT_TOKEN>"

# 5) Listar notificações
curl -i http://localhost:8080/notifications?professorId={professorId} \
  -H "Authorization: Bearer <JWT_TOKEN>"

# 6) Marcar como lida
curl -i -X PATCH http://localhost:8080/notifications/{notificationId}/read \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

