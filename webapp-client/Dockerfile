FROM node:12-alpine as build
WORKDIR /opt/web
COPY package.json package-lock.json ./
RUN npm install

ENV PATH="./node_modules/.bin:$PATH"
COPY . ./
RUN npm install -g @angular/cli
RUN ng build --configuration production --output-path=/dist

FROM nginx:1.17-alpine
COPY --from=build /dist /usr/share/nginx/html
CMD ["/bin/sh",  "-c",  "envsubst < /usr/share/nginx/html/assets/env.template.js > /usr/share/nginx/html/assets/env.js && exec nginx -g 'daemon off;'"]