
# API Academy Code - Gerenciador de Cursos de Programação

A API Academy Code é uma aplicação para gerenciamento de cursos de programação, oferecendo um CRUD completo e filtragem por categoria. Usuários podem se cadastrar, fazer login e acessar funcionalidades conforme seus papéis: USER, com permissão para listar e filtrar cursos quando autenticado; ADMIN, responsável pelo gerenciamento completo dos cursos; e MANAGEMENT, que possui acesso total, incluindo a listagem de usuários e seu status ativo.

O projeto conta com:
- 📖 Documentação interativa via Swagger  
- ✅ Testes unitários e de integração com JUnit e Mockito  
- 📊 Controle de qualidade do código com SonarQube e JaCoCo  
- 🚀 CI/CD automatizado com GitHub Actions e Docker Hub


## Stack utilizada

- **Back-end:** Java + Spring Boot
- **Banco de Dados:** PostgreSQL + Spring Data JPA
- **Autenticação:** Spring Security + JWT 
- **Documentação:** Swagger
- **Testes:** JUnit, Mockito
- **Qualidade do Código:** SonarQube, JaCoCo
- **CI/CD:** GitHub Actions, Docker Hub, AWS EC2
- **Containerização:** Docker
  

## Instalação e Configuração

Pré-requisitos
- Docker e Docker Compose
- PostgreSQL 16.8
- JDK 17

```bash
# Clone o repositório
git clone https://github.com/brenopereira18/academy_code.git  

# Acesse o diretório do projeto
cd seu-repositorio 

# Suba os containers do PostgreSQL e da aplicação
docker-compose up -d  
```

#### Caso queira rodar localmente sem Docker:

```bash
# Clone o repositório
git clone https://github.com/brenopereira18/academy_code.git  

# Acesse o diretório do projeto
cd seu-repositorio 

# Instale as dependências do projeto
mvn clean install 

# Execute a aplicação
mvn spring-boot:run  
```

    
## Rodando os testes

Para rodar os testes, rode o seguinte comando:

```bash
# Executar testes unitários e de integração
mvn test

```


## CI/CD (GitHub Actions, Docker Hub e AWS EC2)

A aplicação possui um fluxo de CI/CD automatizado utilizando GitHub Actions, que executa as seguintes etapas:

- Build do projeto para garantir que o código está compilando corretamente.
- Criação da imagem Docker, baseada no Dockerfile.
- Publicação da imagem no Docker Hub.
- Deploy na AWS EC2, onde o servidor baixa a nova imagem do Docker Hub e cria um novo container da aplicação.

### 🌐 Acesso à API em Produção

A API está hospedada na AWS EC2. Para obter acesso, entre em contato.
