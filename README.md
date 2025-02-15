<img src="./badges/jacoco.svg" style="display: flex;" alt="jacoco-test-coverage-badge">

## Micronaut & Redis: Credit Check System

This is a simple credit check system that uses Micronaut and Redis to store and retrieve credit scores for individuals.

### Features

- Test suite with 97%+ test coverage ✅
- Micronaut and Redis to cache and retrieve data ✅
- Simple docker-compose config and execution with 1 command (`make`) ✅
- Dockerised AOT Native Image with < 30ms average start-up time ✅
- Test suite running in GitHub Actions workflow ✅
- Jacoco & Checkstyle for code coverage & quality ✅

### Running the application

<details>
<summary>Run with Docker Compose</summary><br />
Executing the below command will boot up the Micronaut & Redis containers in a Docker network using Docker Compose.

```shell
make
```
</details>