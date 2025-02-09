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

test-coverage:
	./mvnw clean org.jacoco:jacoco-maven-plugin:0.8.12:prepare-agent verify org.jacoco:jacoco-maven-plugin:0.8.12:report

check-coverage:
	open -a Google\ Chrome target/jacoco-report/index.html

coverage-badge-gen:
	python3 -m jacoco_badge_generator -j target/jacoco-report/jacoco.csv

.SILENT:
.PHONY: default package package-native run docker-run test-coverage check-coverage coverage-badge-gen