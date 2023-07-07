# Define a imagem base
FROM node:14-alpine

# Define o diretório de trabalho dentro do contêiner
WORKDIR .

# Copia os arquivos de configuração do projeto
COPY package.json yarn.lock ./

# Instala as dependências usando o Yarn
RUN yarn install --production

# Copia o código-fonte para o contêiner
COPY . .

# Exponhe a porta 8080
EXPOSE 8080

# Define o comando para iniciar a aplicação Node.js
CMD ["node", "app.js"]