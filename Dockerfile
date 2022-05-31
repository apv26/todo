FROM eed3si9n/sbt:jdk8-alpine

WORKDIR /current

ENTRYPOINT sbt
CMD ["clean", "server/test"]
