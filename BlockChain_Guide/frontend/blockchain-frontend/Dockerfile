# Use uma imagem base oficial do Node.js
FROM node:14


WORKDIR /app


COPY package*.json ./


RUN npm install


COPY . .


RUN npm run build

# imagem base oficial do Nginx para servir a aplicação
FROM nginx:alpine
COPY --from=0 /app/build /usr/share/nginx/html


EXPOSE 80


CMD ["nginx", "-g", "daemon off;"]