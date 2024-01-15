# product-management
-A API atende a todas as funcionalidades solicitadas.                                                                   

-Para testar qualquer endpoint, primeiro é necessário realizar a autenticação:

/users -> Registrar um novo usuário <br /> 
/users/login -> Realizar login e obter o token <br /> 
-O token tem um período de validade de 5 minutos. Após esse período, é necessário realizar o login novamente. <br /> 

-Os endpoints podem ser visualizados no Swagger, mas o token é necessário para testá-los. <br /> 

-Para visualizar os relatórios em plataformas como Postman ou Insomnia, é possível fazer o download do arquivo usando o botão "Preview" -> "Export".

-Foi utilizado tecnologias como Jacoco para visualização da cobertura dos testes, Swagger para documentação, JUnit 5 e Mockito para a realização dos testes, Lombok, JWT, Spring Security,
Spring Data JPA, Spring Boot 3, Java 17, PostgreSQL, Maven entre outras. 
