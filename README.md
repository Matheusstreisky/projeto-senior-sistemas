# Projeto Senior Sistemas
Projeto realizado como parte do processo de seleção da Senior Sistemas.

## Tecnologias utilizadas
* Java 17
* Maven
* Spring 3.3.3
* Spring Data JPA
* Spring Validation
* Banco de dados PostgreSQL 16.4
* JUnit e Mockito para testes unitários
* Swagger para documentação

## Informações do projeto
O projeto consiste em uma aplicação Spring REST que possui CRUD para Item, Pedido e Itens de Pedido.

Os requisitos do projeto consistem em:
* Deverá ser desenvolvido um cadastro (Create/Read/Update/Delete/List com paginação) para as seguintes entidades: produto/serviço, pedido e itens de pedido.
* Deverá ser possível aplicar filtros na listagem.
* As entidades deverão utilizar Bean Validation.
* Deverá ser implementado um ControllerAdvice para customizar os HTTP Response das requisições (mínimo BAD REQUEST).
* Todos as entidades deverão ter um ID único do tipo UUID gerado automaticamente.
* No cadastro de produto/serviço deverá ter uma indicação para diferenciar um produto de um serviço.
* Deverá ser possível aplicar um percentual de desconto no pedido, porém apenas para os itens que sejam produto (não serviço); o desconto será sobre o valor total dos produtos.
* Somente será possível aplicar desconto no pedido se ele estiver na situação Aberto (Fechado bloqueia).
* Não deve ser possível excluir um produto/serviço se ele estiver associado a algum pedido .
* Não deve ser possível adicionar um produto desativado em um pedido

Os criterios de aceitação do projeto são:
* A prova deverá ser entregue completa (todos os itens resolvidos).
* Deverão ser criados testes automatizados.
* O código não poderá ter erros de compilação.
* Deverá haver uma documentação mínima de como executar o projeto e suas funcionalidades

## Execução do projeto
Para executar o projeto, basta baixar o fonte, abrir em sua IDE de preferencia e executar o arquivo ProjetoSeniorSistemasApplication.java.

Também é possível executar via linha de comando, indo na raiz do projeto e executando o comando:

### Linux
```
./mvnw spring-boot:run
```

### Windows
```
mvnw.cmd spring-boot:run
```

### Documentação
Para listar os endpoints do projeto, assim como as informações do que cada endpoint faz, seus parâmetros e seus retornos, foi utilizado Swagger.

Então para acessar a documentação é necessário executar o projeto, e com ele em execução acessar a seguinte URL:

```
http://localhost:8080/swagger-ui/index.html#/
```
