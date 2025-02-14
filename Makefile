SHELL := /bin/bash

default: run

package:
	mvn package

package-native:
	./mvnw package -Dpackaging=docker-native -Pgraalvm

run:
	java -jar target/credit-check-micronaut-0.1.jar

docker-run:
	docker run --rm --name credit-check-micronaut -p 8080:8080 credit-check-micronaut

mvn-run:
	mvn mn:run

test-coverage:
	./mvnw clean org.jacoco:jacoco-maven-plugin:0.8.12:prepare-agent verify org.jacoco:jacoco-maven-plugin:0.8.12:report

check-coverage:
	open -a Google\ Chrome target/jacoco-report/index.html

coverage-badge-gen:
	python3 -m jacoco_badge_generator -j target/jacoco-report/jacoco.csv

redis-run:
	docker run --rm --name redis-credit-check-cache -p 6379:6379 redis

.SILENT:
.PHONY: default package package-native run docker-run mvn-run test-coverage check-coverage coverage-badge-gen redis-run