SHELL := /bin/bash

default: up

package:
	mvn package

test:
	mvn test

deps-tree:
	mvn dependency:tree

package-native:
	./mvnw package -Dpackaging=docker-native -Pgraalvm

build-docker-aot-native:
	# AOT Optimized Docker Image with a native executable inside
	./mvnw package -Dpackaging=docker-native -Dmicronaut.aot.enabled=true -Pgraalvm

ttfr:
	# Time to first request of the Dockerized and AOT optimized application
	./ttfr.sh credit-check-micronaut:latest

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

test-suite: test-coverage check-coverage coverage-badge-gen

redis-start:
	docker run --rm --name redis-credit-check-cache -p 6379:6379 redis

up:
	docker-compose -f docker-compose.yml up

.SILENT:
.PHONY: default package test package-native build-docker-aot-native ttfr run docker-run mvn-run test-coverage check-coverage coverage-badge-gen redis-start up