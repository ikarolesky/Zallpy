Cooperados - Sistema de Cadastro de Cooperados
==============================================

Descrição
---------

Este projeto é um sistema completo para gerenciamento de cadastro de cooperados, desenvolvido com backend em Java Spring Boot utilizando arquitetura DDD, banco de dados MySQL, e frontend em React. O projeto é conteinerizado usando Docker e orquestrado com docker-compose para facilitar a implantação e execução.

* * *

Funcionalidades
---------------

*   Cadastro, edição, visualização e remoção (exclusão lógica) de cooperados;
*   Validação rigorosa dos dados, incluindo validação de CPF e CNPJ;
*   Diferenciação dinâmica entre CPF e CNPJ nos formulários, com alteração dos labels e validação específica;
*   Impedimento do cadastro duplicado de CPF ou CNPJ;
*   Pesquisa e filtro de cooperados por nome e CPF/CNPJ;
*   Exclusão lógica para manter histórico dos registros;
*   Frontend React responsivo e amigável com navegação SPA;
*   Backend estruturado em camadas (DDD) com validação, tratamento de erros e boas práticas;
*   Orquestração via Docker Compose, contendo containers para backend, frontend e banco MySQL;
*   Migrações automáticas de banco usando Flyway.

* * *

Tecnologias Utilizadas
----------------------

*   Java 17, Spring Boot (Web, Data JPA, Validation, Flyway)
*   MySQL 8
*   React 18, React Router DOM, Axios
*   Docker, Docker Compose
*   Bibliotecas para validação CPF/CNPJ (frontend)
*   Maven (backend)

* * *

Como Rodar o Projeto
--------------------

### Pré-requisitos

*   Docker instalado ([https://docs.docker.com/get-docker/](https://docs.docker.com/get-docker/))
*   Docker Compose instalado ([https://docs.docker.com/compose/install/](https://docs.docker.com/compose/install/))

* * *

### Passos para executar

1.  Clone este repositório:

    git clone <url-do-repositorio>
    cd <nome-do-repositorio>
    

2.  Estrutura básica de pastas:

    /
    ├── backend
    │   ├── src
    │   ├── Dockerfile
    │   ├── pom.xml
    │   └── ...
    ├── frontend
    │   ├── src
    │   ├── Dockerfile
    │   ├── package.json
    │   └── ...
    └── docker-compose.yml
    

3.  Construir e subir os containers com Docker Compose:

    docker-compose up --build
    

4.  Acesse as aplicações:

*   Frontend React (interface web): [http://localhost:3000](http://localhost:3000)
*   Backend API (REST): [http://localhost:8080/api/cooperados](http://localhost:8080/api/cooperados)

* * *

Rotas API Backend
-----------------

Método

Endpoint

Descrição

Parâmetros/Body

GET

`/api/cooperados`

Lista cooperados com filtros

Query params: `nome`, `cpfCnpj`

GET

`/api/cooperados/{id}`

Busca cooperado por ID

Path param: `id`

POST

`/api/cooperados`

Cria novo cooperado

JSON no body com dados do cooperado

PUT

`/api/cooperados/{id}`

Atualiza cooperado existente

Path param `id`, JSON no body

DELETE

`/api/cooperados/{id}`

Remove cooperado (exclusão lógica)

Path param `id`

* * *

Campos do cadastro
------------------

Campo

Tipo

Obrigatório

Observações

nome

String

Sim

Nome completo do cooperado

cpfCnpj

String

Sim

CPF (11 dígitos) ou CNPJ (14 dígitos), único

dataNascimentoConstituicao

Date

Sim

Data de nascimento (CPF) ou constituição (CNPJ)

rendaFaturamento

Decimal

Sim

Renda mensal (CPF) ou faturamento (CNPJ)

telefone

String

Sim

Telefone válido padrão Brasil

email

String

Não

Email válido (opcional)

* * *

Abordagens técnicas
-------------------

*   **DDD (Domain Driven Design):** Separação clara entre domínio, aplicação e infraestrutura para maior manutenibilidade.
*   **Validação e Regra de Negócio:** Validação no backend e frontend para garantir consistência dos dados.
*   **Exclusão lógica:** Registros marcados como removidos sem deletar do banco para rastreabilidade.
*   **Dockerização:** Facilita o deploy e consistência do ambiente.
*   **Migrações Flyway:** Controle das versões do banco de dados.
*   **Front-end React SPA:** Interface interativa, com roteamento, validação dinâmica e usabilidade.

* * *

Observações
-----------

*   A porta do backend é 8080, frontend 3000, e banco 3306.
*   Use `docker-compose down` para parar e remover containers.
*   Para desenvolvimento local, ajuste URLs do backend no frontend conforme seu ambiente.
*   Erros de validação retornam mensagens claras para facilitar o uso.

* * *

Se precisar de ajuda para rodar, modificar ou expandir o projeto, estou à disposição!