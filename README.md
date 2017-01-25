# xyinc
Autor: Lucas Daniel Ottoni (lucasottoni@gmail.com)

-------------
README
-------------
PROJETO xyinc


Foi solicitado o desenho de arquitetura e a construção de um sistema capaz de criar modelos de objetos, armazená-los e permitir ao usuários, através de chamadas REST, criar instâncias destes objetos, bem como alterá-las, recuperá-las e apagá-las.

A arquitetura proposta segue um padrão MVC clássico. Como framework básico foi utilizado o Spring. Para facilidade de configuração e manutenção, foram utilizadas anotações em detrimento de configuração por XML do mesmo.

A maior dificuldade da arquitetura é propor um modelo de armazenamento dinâmico o suficiente para criar novos modelos e novas instâncias.

Para tanto, seria possível a utilização de criação dinâmica de tabelas, que permite maior performance e gerenciamento no banco de dados, ou um modelo de pivot, onde cada coluna de uma instância de um objeto vira uma linha de uma tabela de controle, o que permite maior maleabilidade do modelo. Como não há exigências de alteração do modelo após sua inclusão, decidiu-se pela criação dinâmica de tabelas.

Como esta é uma prova de conceito, utiliza-se o banco HSQLDB, relacional, em memória para armazenamento dos dados. Com a abstração providenciada pelo Spring e o uso de ANSI SQL, é possível a alteração do banco de dados apenas com a alteração do datasource. O script para criação do banco de dados se encontra no arquivo create-db.sql, que é executado automaticamente.

A camada de modelo é composta pelas Entidades que representam as tabelas do banco de dados, as classes de acesso a dados (DAOs) e as classes de negócio (BOs).

As entidades representam apenas as tabelas básicas de armazenamento. Como os modelos são criados dinamicamente, não faz sentido utilizar ORM (Hibernate ou JPA). Portanto, utilizou-se diretamente JDBC para acesso a dados, através do JDBCTemplate providenciado pelo Spring.

As classes de acesso a dados salvam os modelos e seus campos e, dinamicamente, constroem novas tabelas com bases nesses modelos e providenciam operações de CRUD para as mesmas.

As classes de negócio possuem regras de validação e efetuam chamadas aos DAOs. Não há maiores regras de negócio envolvidas.

A camada de controle (Controller) é providenciada pelo Spring MVC. Existem controladores para as operações disponibilizadas como serviços REST e para o cadastro de modelo através de formulário HTML.

A camada de visão (View) é composta apenas por uma página com formulário HTML simples para cadastro de modelo, bem como os endpoints REST. Para a construção desta página optou-se pela facilidade de integração do Spring MVC com o Thymeleaf. Os endpoints REST são mapeados diretamente nos controladores do Spring MVC.


INSTRUÇÕES DE EXECUÇÃO

Os testes unitários foram escritos com JUnit e estão disponíveis nas pastas src/test/java de cada um dos projetos.

Executando através do Eclipse:

. Rodar a classe br.com.zup.teste.xyinc.web.config.WebApplication. Neste caso, através do Spring Boot, um container Tomcat é por padrão iniciado, com a aplicação completa rodando. 

. Para cadastrar um novo modelo, acesse a URL http://localhost:8080/ no browser. Preencha os dados na tela.

. Criado um modelo, por exemplo 'teste', com atributos 'teste1' e 'teste2', chamar as URLs abaixo para efetuar as operações, utilizando o cabeçalho Content-Type: Application/JSON na requisição HTTP:


INSERT - http://localhost:8080/teste/ - Método POST

SELECT ALL - http://localhost:8080/teste/ - Método GET

SELECT ONE - http://localhost:8080/teste/{id} - Método GET

UPDATE - http://localhost:8080/teste/{id} - Método PUT

DELETE - http://localhost:8080/teste/{id} - Método DELETE
	
Exemplo de JSON:

{"name": "teste",
	"fields": [
		{"name": "teste1", "value": "teste"},
		{"name": "teste2", "value": "1"}
	]
}
	
Executando através do Tomcat:

. Execute mvn clean install no projeto xyinc

. Copie o arquivo xyinc-web-1.0.war do diretório target do projeto xyinc-web para o diretório webapps de uma instalação do Tomcat.

. Para cadastrar um novo modelo, acesse a URL http://localhost:8080/xyinc-web-1.0/ no browser. Preencha os dados na tela.

. Criado um modelo, por exemplo 'teste', com atributos 'teste1' e 'teste2', chamar as URLs abaixo para efetuar as operações,  utilizando o cabeçalho Content-Type: Application/JSON na requisição HTTP:


INSERT - http://localhost:8080/xyinc-web-1.0/teste/ - Método POST

SELECT ALL - http://localhost:8080/xyinc-web-1.0/teste/ - Método GET

SELECT ONE - http://localhost:8080/xyinc-web-1.0/teste/{id} - Método GET

UPDATE - http://localhost:8080/xyinc-web-1.0/teste/{id} - Método PUT

DELETE - http://localhost:8080/xyinc-web-1.0/teste/{id} - Método DELETE


Exemplo de JSON:

{"name": "teste",
	"fields": [
		{"name": "teste1", "value": "teste"},
		{"name": "teste2", "value": "1"}
	]
} 
