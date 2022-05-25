FROM nginx:alpine
COPY static /usr/share/nginx/html
LABEL maintainer = "alex809021@docker.com"