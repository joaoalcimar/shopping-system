FROM node:14

ENV NODE_OPTIONS="--max-old-space-size=4096"

WORKDIR .

COPY package*.json ./

ENV GENERATE_SOURCEMAP=false

RUN yarn

COPY . .

EXPOSE 8080

CMD ["yarn", "startDev"]