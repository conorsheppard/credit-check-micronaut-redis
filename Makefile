SHELL := /bin/bash

default: run

package:
	mvn package

package-native:
	./mvnw package -Dpackaging=docker-native -Pgraalvm

run:
	java -jar target/fraud-check-micronaut-0.1.jar

docker-run:
	docker run --rm --name fraud-check-micronaut -p 80:80 fraud-check-micronaut

.SILENT:
.PHONY: default package package-native run docker-run