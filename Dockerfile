FROM eed3si9n/sbt:jdk8-alpine

WORKDIR /current

CMD sbt server/test
