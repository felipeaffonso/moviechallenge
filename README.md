# Movie Challenge

## Conteúdo

- [Introdução](#introduçãoo)
- [Informações Técnicas](#informações-técnicas)
- [Requisitos para executar a aplicação](#requisitos-para-executar-a-aplicaçãoo-e-dependências)
- [Como usar](#como-usar)
- [Cobertura de Testes](#cobertura-de-testes)
- [Classes não Testadas](#classes-não-testadas)

## Introdução

Essa é uma API REST criada com Spring Boot 2.1.12.RELEASE que utiliza o MongoDB como Banco de Dados.

A aplicação tem como principais requisitos criar *Filmes* e expor uma busca dos mesmos a partir do nível de censura.

O fluxo de negócio foi controlado a partir de Exceptions `RuntimeException`, afetando inclusive as respostas da API.

Foi criada uma camada de objetos Request e Response a partir da Controller, para os contratos de entrada e saída. Para a conversão dos modelos foi utilizada uma interface de Transformação de objetos, onde cada implementação faz a conversão de um tipo externo para um tipo mais interno, e vice-versa.

Embora não fosse um requisito, foi implementada a paginação na busca de filmes, que facilmente pode ser extendida com ordenação.

Para uma maior flexibilidade e segurança, utilizei os dois produtos da Hashicorp, o `Consul` para deixar as propriedades que por ventura possam necessitar de alterações, enfim, facilitando e deixando externa as propriedades não sensíveis que a aplicação necessita, e o por fim, as informações sensíveis ficam no `Vault`, protegidas e criptografadas.

## Informações Técnicas

O Software foi construído utilizando a seguinte stack:
- [Linux Mint 19](https://www.linuxmint.com/) - Porque o MacBook está bem caro :-(
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) - A IDE que amo e que me fez evoluir muito como profissional
- [Java 11](https://www.java.com/pt_BR/) - **THE PROGRAMMING LANGUAGE**
- [Spring Boot 2.1.12](http://spring.io/projects/spring-boot) - O framework que utilizo diariamente
- [MongoDB](https://www.mongodb.com/) - NoSQL Database
- [Consul](https://www.consul.io/) - Gestão das Propriedades da aplicação
- [Vault]() - Gestão das Propriedades Sensíveis da Aplicação
- [Lombok](https://projectlombok.org/) - Framework para reduzir código boilerplate
- [SpringFox](http://springfox.github.io/springfox/) - Geração de Documentação da API com Swagger
- [Maven](https://maven.apache.org/) - Gestão de Dependências e Automação de Tarefas no Projeto
- [JUnit](https://junit.org/) - Bibilioteca para Testes
- [AssertJ](http://joel-costigliola.github.io/assertj/) - assertThat() como um *PROFISSIONAL*
- [Mockito](https://site.mockito.org/) - Essencial para mockar dependências e comportamentos
- [Fixture Factory](https://github.com/six2six/fixture-factory) - Biblioteca escrita por Brasileiros para geração de Fixtures de Teste
- [Jacoco](https://www.eclemma.org/jacoco/trunk/doc/maven.html) - Biblioteca para análise de cobertura de código

A arquitetura foi separada em alguns pacotes principais:
 - Config: Classes de Configuração
 - Controller
    - Request: Objetos de Entrada da API
    - Response: Objetos de Saída da API
    - Transformer: Classes de Transformação de Modelos
 - Model: Entidades e Domínios
 - Service: Interface e Implementação das Regras de Negócio
 - Repository: Interface de Acesso ao MongoDB
 - Exceptions: `RuntimeExcpetion` de Negócio
 

## Requisitos para executar a aplicação e dependências

1. [Java 11](https://www.java.com/pt_BR/)
2. [Maven](https://maven.apache.org/)
3. [Docker](https://www.docker.com/)

## Como usar

Primeiramente, você precisa gerar o pacote `jar` com o `mvn`:

```sh
$ mvn clean package
```

Então você está pronto para executar o comando:

```sh
$ docker-compose up --build -d
```

Em poucos segundos a aplicação estará funcional na porta 8080, então você pode navegar para [Swagger UI](http://localhost:8080/swagger-ui.html)

Swagger Documentation pode ser encontrada [AQUI](http://localhost:8080/v2/api-docs) e pode ser importada no Postman, por examplo.

Quando quiser parar a aplicação e todas as suas dependências, basta executar o comando abaixo:

```sh
$ docker-compose up --build -d
```

## Cobertura de Testes

Tentei me concentrar no *CORE BUSINESS*, testando as partes que fazem sentido testar, e algumas partes não foram testadas propositalmente.

1. Para todas as classes testadas, a cobertura é de 100% de Linhas, Métodos e Branches.
2. A cobertura é medida pela biblioteca Jacoco que disponibiliza um relatório HTML que pode ser encontra na pasta `target/site/jacoco/index.html`.
3. As validações das regras foram feitas via Bean Validation no Objeto de Request, todas as validações foram feitas nos testes da `MovieController`
4. `FixtureFactory` foi utilizado para a criação dos stubs para teste, a biblioteca é bem flexível e simples.

## Classes não Testadas
1. `Exceptions` não foram testadas.
2. `MoviechallengeApplication` não foi testada.
3. `JacksonConfig`, `SwaggerConfig` e `WebSecurityConfig` não foram testadas
3. O pacote `model` não foi testado
4. `DTOs` dentro de `request` e `response` não foram testados
