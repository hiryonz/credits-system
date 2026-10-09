FROM node:24-alpine AS build
WORKDIR /app

RUN corepack enable && corepack prepare pnpm@10.12.1 --activate

COPY package.json ./
RUN pnpm install

COPY . .

RUN pnpm exec ng test --watch=false

ARG CONFIGURATION=production
RUN pnpm exec ng build --configuration ${CONFIGURATION}

FROM nginx:1.29-alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/www /usr/share/nginx/html

EXPOSE 80
