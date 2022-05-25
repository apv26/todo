FROM nginx:alpine
COPY client/public /usr/share/nginx/html
LABEL maintainer = "alex809021@docker.com"