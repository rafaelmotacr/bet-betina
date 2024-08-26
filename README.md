<h1 >Bet Betina - </ <h3>Casa de apostas fictícia baseada na betina da empiricus.</h3></h1> 

Este projeto utiliza Maven como gerenciador de dependências e Docker para encapsular e automatizar o banco de dados.

<u><h3>Requisitos: </h3></u>

> Java: no mínimo java 11; <br>
Docker e Docker Compose (ou PostgreSQL instalado e configurado localmente);<br>
Eclipse IDE ou outra compatível com Maven.

Nota: É altamente recomendável o uso da IDE Eclipse com a extensão swing para manipulação do projeto. Para fins didáticos, o uso do pgAdmin pode, também, ser indicado.

<u><h3>Execução e inicialização: </h3></u>

Para inicializar o banco de dados abra um terminal na pasta do projeto e utilize o comando:

> $ docker-compose up --build 

Ou, para rodar em segundo plano:

> $ docker-compose up -d

Para finalizar o banco utilize:

> $ docker-compose down

<u><h3>Manipulação do Banco de Dados: </h3></u>

Para acesar o terminal do banco de dados (caso necessário) utilize os comandos:

>$ docker exec -it postgres-db bash

// Substitua "postgres-db" pelo nome da sua imagem docker ou pelo id dela
// Caso não saiba o nome ou o id, execute o comando docker ps

Depois, no terminal do container, para acessar o PostgreSQL, utilize:

> $ psql -U postgres -d bet-betina-prod

Projeto realizado para fins acadêmicos na disciplina de POO (Programação Orientada a Objetos). Quaisquer pessoas ou entidades citadas são fictícias e qulquer semelhança com a realidade é mera coincidência.
