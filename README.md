# 🚗 WS Work Cars API — Kotlin ✨ Bônus

Implementação em **Kotlin 1.9 + Spring Boot 3.2** do mesmo sistema do teste WS Work.  
Atende o **item 5 (bônus)** — conversão completa do Java para Kotlin, mantendo todas as funcionalidades e rodando na porta **8081**.

---

## 📋 Requisitos do teste — todos atendidos

| # | Requisito | Status | Detalhes |
|---|-----------|--------|----------|
| 1 | Spring Boot + Spring Data JPA + PostgreSQL | ✅ | Spring Boot 3.2, JPA/Hibernate, Flyway para migrations |
| 2 | Endpoint `/api/cars` no formato de `cars.json` | ✅ | Retorna `{ "cars": [...] }` idêntico ao JSON do teste |
| 3 | Configuração de CORS | ✅ | `CorsFilter` global — suporta qualquer origem (configurável) |
| 4 | CRUD de Carros, Marcas e Modelos | ✅ | 15 endpoints REST com validação e tratamento de erros |
| 5 | **Bônus — Kotlin** | ✅ | Código ~60% menor, null safety nativo, idiomático |

---

## ✨ Kotlin vs Java — principais diferenças

| Aspecto | Java | Kotlin |
|---------|------|--------|
| Linhas de código (services) | ~150 linhas, 3 arquivos | ~80 linhas, 1 arquivo |
| DTOs | Records separados por arquivo | Data classes em `Dtos.kt` |
| Null safety | NPE em runtime | Null safety em compile time (`?` e `!!`) |
| Entidades | Classes com Lombok | Classes Kotlin sem anotações extras |
| Expressividade | `return service.criar(request)` | `fun criar(...) = service.criar(request)` |
| Lambdas/coleções | `.stream().map(...).toList()` | `.map(...)` |
| Apply/also | — | `carro.apply { ano = request.ano }` |
| Companion object | static factory methods | `companion object { fun from(c: Carro) = ... }` |

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Justificativa |
|---|---|---|
| Kotlin | 1.9.24 | Linguagem principal; null safety, data classes, expressividade |
| Spring Boot | 3.2.5 | Framework principal |
| Spring Data JPA | 3.2.5 | Repositórios sem boilerplate |
| jackson-module-kotlin | padrão SB | Serialização/deserialização de data classes Kotlin |
| kotlin-reflect | 1.9 | Necessário para o Spring injetar dependências em classes Kotlin |
| PostgreSQL | 16 | Banco relacional |
| Flyway | 10 | Migrações versionadas (mesmo SQL da versão Java) |
| H2 | 2.x | Banco em memória para testes |
| JUnit 5 + Mockito | padrão SB | Testes unitários e de integração |

---

## 🗄️ Modelo de dados (ER)

```
┌─────────────┐        ┌────────────────────────┐        ┌──────────────────────────────┐
│   Marca     │        │        Modelo          │        │           Carro              │
├─────────────┤  1─N   ├────────────────────────┤  1─N   ├──────────────────────────────┤
│ id          │◄───────│ id                     │◄───────│ id                           │
│ nome_marca  │        │ marca_id               │        │ timestamp_cadastro           │
└─────────────┘        │ nome                   │        │ modelo_id                    │
                       │ valor_fipe             │        │ ano                          │
                       └────────────────────────┘        │ combustivel                  │
                                                         │ num_portas                   │
                                                         │ cor                          │
                                                         └──────────────────────────────┘
```

---

## 🚀 Como rodar

### Pré-requisitos

- **Java 21+** (a JVM do Kotlin)
- **Maven 3.9+**
- **Docker + Docker Compose**

> A versão Kotlin usa a porta **5433** para o PostgreSQL e **8081** para a API,  
> para não conflitar com a versão Java caso ambas rodem ao mesmo tempo.

### 1. Clonar o repositório

```bash
git clone <url-do-repositorio>
cd wswork-cars-api-kotlin
```

### 2. Subir o PostgreSQL com Docker

```bash
docker-compose up -d
```

Isso sobe PostgreSQL 16 na porta **5433** com:
- Banco: `wswork_cars_kt`
- Usuário: `wswork`
- Senha: `wswork123`

### 3. Rodar a aplicação

```bash
./mvnw spring-boot:run
```

API disponível em `http://localhost:8081`.

### 4. Verificar que está funcionando

```bash
curl http://localhost:8081/api/cars
```

---

## 🧪 Testes

```bash
./mvnw test
```

Usam H2 em memória — sem necessidade do Docker.

### Suíte de testes

| Classe | Tipo | O que testa |
|---|---|---|
| `MarcaServiceTest` | Unitário (Mockito) | listar, buscar, criar (duplicada e nova), exceções |
| `CarroControllerIntegrationTest` | Integração (MockMvc + H2) | GET `/api/cars`, POST válido/inválido, DELETE, 404 |

---

## 📡 Referência completa dos endpoints

> Todos os endpoints são idênticos à versão Java — apenas a porta muda: **8081**.

### Item 2 — Endpoint para o frontend

```
GET /api/cars
```

```json
{
  "cars": [
    {
      "id": 1,
      "timestampCadastro": 1696549488,
      "modeloId": 1,
      "ano": 2014,
      "combustivel": "FLEX",
      "numPortas": 4,
      "cor": "BRANCA",
      "nomeModelo": "Corolla",
      "valor": 120000.00,
      "brand": 1
    }
  ]
}
```

---

### Marcas — `/api/marcas`

#### `GET /api/marcas` — Listar todas
```bash
curl http://localhost:8081/api/marcas
```

#### `GET /api/marcas/{id}` — Buscar por ID
```bash
curl http://localhost:8081/api/marcas/1
```

#### `POST /api/marcas` — Criar
```bash
curl -X POST http://localhost:8081/api/marcas \
  -H "Content-Type: application/json" \
  -d '{ "nomeMarca": "Honda" }'
# Retorna 201 Created
```

#### `PUT /api/marcas/{id}` — Atualizar
```bash
curl -X PUT http://localhost:8081/api/marcas/6 \
  -H "Content-Type: application/json" \
  -d '{ "nomeMarca": "Honda Motors" }'
```

#### `DELETE /api/marcas/{id}` — Remover
```bash
curl -X DELETE http://localhost:8081/api/marcas/6
# Retorna 204 No Content
```

---

### Modelos — `/api/modelos`

#### `GET /api/modelos` — Listar todos
```bash
curl http://localhost:8081/api/modelos
```
```json
[
  {
    "id": 1,
    "marcaId": 1,
    "nomeMarca": "Toyota",
    "nome": "Corolla",
    "valorFipe": 120000.00
  }
]
```

#### `POST /api/modelos` — Criar
```bash
curl -X POST http://localhost:8081/api/modelos \
  -H "Content-Type: application/json" \
  -d '{
    "marcaId": 1,
    "nome": "Camry",
    "valorFipe": 185000.00
  }'
```

#### `PUT /api/modelos/{id}` — Atualizar
```bash
curl -X PUT http://localhost:8081/api/modelos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "marcaId": 1,
    "nome": "Corolla Cross",
    "valorFipe": 145000.00
  }'
```

#### `DELETE /api/modelos/{id}` — Remover
```bash
curl -X DELETE http://localhost:8081/api/modelos/10
```

---

### Carros — `/api/carros`

#### `GET /api/carros` — Listar todos
```bash
curl http://localhost:8081/api/carros
```

#### `POST /api/carros` — Criar novo carro
```bash
curl -X POST http://localhost:8081/api/carros \
  -H "Content-Type: application/json" \
  -d '{
    "modeloId": 1,
    "ano": 2024,
    "combustivel": "flex",
    "numPortas": 4,
    "cor": "azul"
  }'
# Retorna 201 Created — combustível e cor normalizados para MAIÚSCULAS
```

#### `PUT /api/carros/{id}` — Atualizar
```bash
curl -X PUT http://localhost:8081/api/carros/1 \
  -H "Content-Type: application/json" \
  -d '{
    "modeloId": 2,
    "ano": 2023,
    "combustivel": "GASOLINA",
    "numPortas": 4,
    "cor": "PRATA"
  }'
```

#### `DELETE /api/carros/{id}` — Remover
```bash
curl -X DELETE http://localhost:8081/api/carros/1
# Retorna 204 No Content
```

---

## ❌ Tratamento de erros

Mesmo formato da versão Java — **ProblemDetail (RFC 9457)**:

```json
{ "status": 404, "detail": "Carro não encontrado(a) com id: 999" }
{ "status": 422, "detail": "Dados inválidos", "errors": { "cor": "Cor é obrigatória" } }
{ "status": 409, "detail": "Já existe uma marca com o nome: Toyota" }
```

---

## 🏗️ Arquitetura e estrutura do código

```
src/main/kotlin/br/com/wswork/cars/
├── CarsApplication.kt          # Entry point — fun main(args) = runApplication<...>(*args)
├── config/
│   └── CorsConfig.kt           # Configuração de CORS
├── model/
│   ├── Marca.kt                # Entidade JPA
│   ├── Modelo.kt
│   └── Carro.kt
├── dto/
│   └── Dtos.kt                 # TODOS os DTOs em um único arquivo (data classes)
├── repository/
│   └── Repositories.kt         # TODAS as interfaces em um único arquivo
├── service/
│   └── Services.kt             # TODOS os services em um único arquivo
├── controller/
│   └── Controllers.kt          # TODOS os controllers em um único arquivo
└── exception/
    └── Exceptions.kt           # Exceção + GlobalExceptionHandler juntos
```

### Comparação de tamanho Java vs Kotlin

| Camada | Java (arquivos) | Kotlin (arquivos) | Redução |
|--------|-----------------|-------------------|---------|
| DTOs | 7 arquivos | 1 arquivo (`Dtos.kt`) | 86% menos arquivos |
| Repositories | 3 arquivos | 1 arquivo (`Repositories.kt`) | 67% menos |
| Services | 3 arquivos | 1 arquivo (`Services.kt`) | 67% menos |
| Controllers | 3 arquivos | 1 arquivo (`Controllers.kt`) | 67% menos |
| **Total** | **~500 linhas** | **~200 linhas** | **~60% menos código** |

---

## 🔑 Decisões técnicas — Kotlin

### Data classes para DTOs
Kotlin data classes são imutáveis por padrão, têm `equals/hashCode/toString/copy` gerados pelo compilador. Substituem Records do Java com sintaxe ainda mais concisa.

### Anotações de validação com `@field:`
Em Kotlin, anotações em parâmetros de construtores precisam do prefixo `@field:` para serem aplicadas ao campo, não ao construtor. Ex: `@field:NotBlank`.

### Plugin `noarg` do Kotlin Maven
O JPA exige construtor sem argumentos nas entidades. O plugin `kotlin-maven-noarg` gera esse construtor automaticamente para classes anotadas com `@Entity`, eliminando a necessidade de construtor explícito.

### Plugin `allopen`
Spring proxifica beans (`@Service`, `@Repository`) criando subclasses — o que requer que as classes sejam `open`. O plugin `kotlin-maven-allopen` aplica `open` automaticamente para anotações Spring.

### `jackson-module-kotlin`
O Jackson, por padrão, não sabe deserializar data classes Kotlin (sem construtor padrão). Este módulo adiciona suporte nativo, sendo registrado automaticamente pelo Spring Boot.

### Organização em arquivos únicos por camada
Diferente do Java (um arquivo por classe), Kotlin permite múltiplas classes públicas em um arquivo. Agrupa por coesão lógica (`Services.kt`, `Dtos.kt`) — menos arquivos, mesma legibilidade.

---

## 🐳 Docker Compose

```bash
docker-compose up -d        # subir na porta 5433
docker-compose down         # parar
docker-compose down -v      # reset do banco
```

---

## 👤 Autor

Desenvolvido por **Otávio Melo** como teste técnico para a WS Work — 2026.
