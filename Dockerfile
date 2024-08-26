FROM postgres: latest

# Copie o script SQL para o diretório de inicialização do PostgreSQL
COPY init.sql /docker-entrypoint-initdb.d/
