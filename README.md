### Instruções para rodar o projeto

Certifique-se que tenha instalado na maquina as seguintes ferramentas:

* Java (Runtime)
* Maven
* Docker

Certifique-se que o seu usuario do SO tenha permissão para executar o script init.sh localizado na raiz do projeto, e que também possa executar comandos do docker.

Execute o seguinte comando na raiz de cada projeto (nosql-version e sql-version):

    $ ./init.sh


### Melhorias

* Testes Unitários.
* Inserir informaçes adicionas (films, starships) de forma assincrona.
* Busca por "name" ser um filtro no endpoint GET /planets, e nao um endpoint exclusivo para isso.
* Swagger.
* Na versão do NoQSL verificar alternativa para a busca de todos os planetas.
