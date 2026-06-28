# SacroSoft - Sistema de Registros Sacramentais

O **SacroSoft** é um sistema para gerenciamento de registros sacramentais (Batismo, Eucaristia, Crisma e Matrimônio) para paróquias da Igreja Católica. A API foi desenvolvida em **Spring Boot** com persistência em **MongoDB**, fornecendo controle rigoroso sobre os registros históricos e regras teológicas/canônicas de precedência sacramental.

---

## 📌 Funcionalidades Principais

- **Cadastro de Pessoas e Sacramentos**: Armazenamento centralizado de fiéis com dados pessoais, contatos, documentos e sacramentos.
- **Validação de Precedência Sacramental**: Garante que sacramentos posteriores (Eucaristia, Crisma e Matrimônio) só possam ser registrados se o fiel possuir o registro de Batismo.
- **Prevenção de Duplicidade**: Evita que o mesmo sacramento seja duplicado ou cadastrado sob outro livro/registro de forma irregular.
- **Gerenciamento de Livros de Registro**: Controle físico e temporal dos livros paroquiais onde os registros originais em papel estão arquivados.
- **Associação de Cônjuges**: Validação automática de integridade ao casar dois fiéis no sistema.

---

## 🛠️ Tecnologias e Dependências

O projeto utiliza as seguintes tecnologias no arquivo [pom.xml](file:///C:/Users/Ryan/Documents/SacroSoft/pom.xml):

- **Java 17**
- **Spring Boot 3.5.0** (Web, Security, Data MongoDB, Validation)
- **MongoDB** (Banco de dados NoSQL baseado em documentos)
- **Lombok** (Para simplificar models e reduzir código boilerplate)
- **Spring Security** (Estrutura de segurança e autenticação)

---

## 📂 Estrutura do Projeto

A arquitetura do código-fonte segue o padrão em camadas do Spring Boot sob a classe de entrada [RegistroApplication.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/RegistroApplication.java):

- **`model/`**: Definição das classes de domínio ([Pessoa.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/model/Pessoa.java), [Livro.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/model/Livro.java), [Sacramento.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/model/Sacramento.java), [Batismo.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/model/Batismo.java), [Matrimonio.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/model/Matrimonio.java), [Usuario.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/model/Usuario.java)).
- **`repository/`**: Interfaces de repositório Spring Data MongoDB ([PessoaRepository.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/repository/PessoaRepository.java), [LivroRepository.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/repository/LivroRepository.java), [UsuarioRepository.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/repository/UsuarioRepository.java)).
- **`service/`**: Regras de negócio e lógica de validação ([PessoaService.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/service/PessoaService.java), [LivroService.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/service/LivroService.java)).
- **`controller/`**: Controladores REST para expor os endpoints da API ([PessoaController.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/controller/PessoaController.java), [LivroController.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/controller/LivroController.java)).
- **`exception/`**: Exceções personalizadas de negócio, como `PessoaSemBatismoException` e `ConjugeNaoEncontradoException`.

---

## ⚙️ Regras de Negócio e Validações

As principais regras implementadas em [PessoaService.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/service/PessoaService.java) são:

1. **Precedência do Batismo**:
   - Para receber os sacramentos da **Eucaristia**, **Crisma** ou **Matrimônio**, o fiel deve ser obrigatoriamente batizado. Caso contrário, uma exceção `PessoaSemBatismoException` será disparada.
2. **Validação de Duplicidade de Registro**:
   - Não é permitido registrar o mesmo sacramento duas vezes para uma mesma pessoa se houver divergência nos identificadores de livro e registro (lançando `SacramentoRegistradoException`).
3. **Existência do Cônjuge**:
   - Ao cadastrar um casamento ([Matrimonio.java](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/java/com/paroquia/sacro/registro/model/Matrimonio.java)), o campo `idConjuge` deve conter o ID de uma pessoa já cadastrada no sistema. Se o ID for inválido ou não correspondente, é disparada a exceção `ConjugeNaoEncontradoException`.

---

## 🔌 API Endpoints Principais

### Pessoas (`/api/pessoas`)
- **GET** `/api/pessoas` - Retorna a lista de todas as pessoas cadastradas.
- **GET** `/api/pessoas/{id}` - Busca uma pessoa pelo ID único.
- **POST** `/api/pessoas` - Cadastra uma nova pessoa (dados cadastrais + sacramentos).
- **PUT** `/api/pessoas/{id}` - Atualiza dados cadastrais e registros de sacramentos (aplica regras de negócio).
- **DELETE** `/api/pessoas/{id}` - Remove uma pessoa do sistema.

### Livros Paroquiais (`/api/livros`)
- **GET** `/api/livros` - Lista os livros cadastrados.
- **GET** `/api/livros/{id}` - Busca um livro específico pelo ID.
- **POST** `/api/livros` - Cria um novo livro de registros sacramentais.
- **PUT** `/api/livros/{id}` - Atualiza as informações de um livro.
- **DELETE** `/api/livros/{id}` - Remove um livro.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- **Java JDK 17** ou superior.
- **Maven** instalado.
- Servidor **MongoDB** rodando localmente ou na nuvem.

### Configuração do Banco de Dados
Por padrão, a conexão com o MongoDB assume a porta `27017` em `localhost`. Caso precise alterar, ajuste o arquivo [application.properties](file:///C:/Users/Ryan/Documents/SacroSoft/src/main/resources/application.properties):

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/sacrosoft
spring.application.name=registro
```

### Compilar e Rodar

Abra o terminal na pasta raiz do projeto e execute os seguintes comandos:

```powershell
# Compilar e baixar dependências
mvn clean install

# Iniciar o servidor Spring Boot
mvn spring-boot:run
```

A API estará rodando por padrão em `http://localhost:8080`.
---

## Testes automatizados de API

Antes de executar os testes, mantenha o MongoDB rodando localmente em:

```properties
mongodb://localhost:27017/sacrosoft
```

Para subir o backend:

```powershell
mvn spring-boot:run
```

Para executar os testes de API com Playwright:

```powershell
npm run test:api
```

Para abrir o relatorio HTML gerado pelo Playwright:

```powershell
npm run test:api:report
```
