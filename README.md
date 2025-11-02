# Jackpot System

![Java](https://img.shields.io/badge/java-21-orange)
![Spring Boot](https://img.shields.io/badge/spring%20boot-3.5.7-brightgreen)
![Kafka](https://img.shields.io/badge/kafka-apache-black)
![Tests](https://img.shields.io/badge/tests-61%20passing-success)

## Overview
A scalable, event-driven jackpot betting system built with **Hexagonal Architecture**, **Domain-Driven Design (DDD)**, and **Command Query Responsibility Segregation (CQRS)** patterns.
The Jackpot System is a multi-module Maven project designed to be easily split into microservices because each module is fully independent. 
It implements hexagonal architecture (Ports & Adapters) where **ports** are domain interfaces and **adapters** are infrastructure implementations, ensuring clean separation between business logic and technical concerns.

### Key Features

- **Multi-Module Architecture**: Fully independent modules designed for easy microservices split

- **Hexagonal Architecture**: Core logic isolated via ports and adapters

- **Event-Driven**: Asynchronous bet processing via Kafka

- **Configurable Strategies**: Flexible contribution and reward calculation models

- **CQRS Pattern**: Commands for writes, Queries for reads with clear Input/Output

- **Multi-Database**: Separate PostgreSQL schemas per domain module

- **Comprehensive Testing**: Tests covering units, integrations, and architecture validation

>Note*: Security (Authentication and Authorization) is compromised to simplify project testing.
>- **End users (players)**: Access to public endpoints (`/public/v1/*`)
>- **Admin**: Access to protected endpoints (`/protected/v1/*`) for jackpot management
>- **Internal services**: Access to internal endpoints (`/internal/v1/*`), which should not be exposed externally
>
> *The endpoint naming convention intentionally separates concerns to enable proper security implementation.*

## Modules

- **jackpot-core** - Shared infrastructure (exceptions, Kafka config, Feign clients, test utilities)

- **jackpot-bet** - Bet management (place bets, publish events, retrieve bet details)

- **jackpot-contribution** - Jackpot configuration and contribution calculation with fixed/variable strategies

- **jackpot-reward** - Reward evaluation with fixed/variable win probability strategies

- **jackpot-app** - Spring Boot application entry point and Docker configuration

## Technologies

- **Core**: Java 21, Spring Boot 3.5.7, Maven
- **Data**: PostgreSQL 16, H2, Liquibase, Apache Kafka, Zookeeper, Kafka UI
- **Spring**: Data JPA, Kafka, Cloud OpenFeign, Actuator, Validation
- **Testing**: JUnit 5, Mockito, ArchUnit, MockMvc
- **API**: SpringDoc OpenAPI 3 (Swagger)
- **Utilities**: Lombok, Jackson
- **Infrastructure**: Docker, Docker Compose

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- Docker & Docker Compose (for containerized setup)

### Running with Docker Compose

1. **Build**:
   ```bash
   mvn clean install
   ```

2. **Start**:
   ```bash
   docker compose up -d
   ```

3. **Access**:
   - API: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/api-docs`
   - Kafka UI: `http://localhost:8081`

4. **Stop**:
   ```bash
   docker compose down
   ```

5. **Clean** (including data):
   ```bash
   docker compose down -v
   ```

### Running Locally

**With H2 database**:
```bash
mvn spring-boot:run -pl jackpot-app -Dspring-boot.run.profiles=local
```

**Note**: Uses `application-local.yaml` for H2 in-memory database. For full functionality with Kafka, run `docker compose up -d` for PostgreSQL and Kafka services.

## API Documentation

- Swagger UI: `http://localhost:8080/api-docs`
- OpenAPI Docs: `http://localhost:8080/api/v3/api-docs`

### Available Endpoints

**Public**:
- `POST /public/v1/bets` - Place a bet
- `POST /public/v1/rewards/evaluate` - Evaluate if bet wins

**Protected**:
- `POST /protected/v1/jackpots` - Create jackpot configuration

**Internal**:
- `GET /internal/v1/bets/{betId}` - Get bet details
- `GET /internal/v1/contributions/jackpot/{jackpotId}` - Get contributions
- `GET /internal/v1/contributions/bet/{betId}` - Get contribution by bet
- `DELETE /internal/v1/contributions/jackpot/{jackpotId}` - Reset jackpot

## Business Features

### Contribution Strategies

- **Fixed**: Constant contribution percentage (e.g., 5% of every bet)

- **Variable**: Dynamic contribution percentage based on jackpot size with linear decrease

### Reward Strategies

- **Fixed**: Constant win probability (e.g., 0.1% chance to win)

- **Variable**: Dynamic win probability based on jackpot size with linear increase

### Strategy Configuration

Two jackpots are auto-generated on startup:

- **Fixed Jackpot** (UUID: `550e8400-e29b-41d4-a716-446655440000`)
  - Fixed 5% contribution percentage
  - Initial pool: 1000.00
- **Variable Jackpot** (UUID: `550e8400-e29b-41d4-a716-446655440001`)
  - Variable contribution (5% to 2% based on pool size)
  - Initial pool: 5000.00
  - Threshold: 10000.00

**Create Additional Fixed Jackpot**:
```bash
curl -X POST http://localhost:8080/protected/v1/jackpots \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Fixed Jackpot",
    "contributionType": "FIXED",
    "initialPool": 1000.00,
    "fixedPercentage": 0.05
  }'
```

**Create Additional Variable Jackpot**:
```bash
curl -X POST http://localhost:8080/protected/v1/jackpots \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Variable Jackpot",
    "contributionType": "VARIABLE",
    "initialPool": 5000.00,
    "variableInitialPercentage": 0.05,
    "variableMinPercentage": 0.02,
    "variableThreshold": 10000.00
  }'
```

## Testing

**Coverage**: 61 tests, all passing

- Bet Module: 8 tests (4 integration, 4 unit)
- Contribution Module: 19 tests (8 integration, 11 unit)
- Reward Module: 22 tests (3 integration, 19 unit)
- Architecture Tests: 12 tests (DDD and CQRS compliance)

**Run all tests**:
```bash
mvn test
```

**Run specific module**:
```bash
mvn test -pl jackpot-bet
```

**Run specific test**:
```bash
mvn test -pl jackpot-bet -Dtest=BetControllerIntegrationTest
```

All integration tests use `BaseIntegrationTest`, module-specific `TestApplication`, mocked Feign clients with `@MockitoBean`, and H2 in-memory database.

## Project Structure

```
jackpot-system/
├── jackpot-core/
│   ├── domain/
│   │   ├── messaging/      # Ports: EventPublisher, BetEvent
│   │   └── store/          # Ports: BetStore, ContributionStore
│   ├── exception/          # Error handling hierarchy
│   ├── infrastructure/
│   │   ├── config/         # Kafka, Feign, OpenAPI
│   │   ├── messaging/      # Adapter: KafkaEventPublisher
│   │   └── store/          # Adapters: Rest clients
│   └── test/               # BaseIntegrationTest
│
├── jackpot-bet/
│   ├── domain/
│   │   ├── Bet.java        # Domain model
│   │   ├── BetStore.java   # Port
│   │   ├── BetPublisher.java
│   │   ├── PlaceBetCommand.java
│   │   └── GetBetQuery.java
│   ├── infrastructure/
│   │   ├── config/         # DataSource, JPA, Liquibase
│   │   ├── endpoint/       # BetController, DTOs
│   │   ├── messaging/      # Adapter: BetEventPublisher
│   │   └── store/
│   │       ├── BetStoreDatabase.java  # Adapter
│   │       ├── repository/            # JPA Repository
│   │       └── entity/                # JPA Entity
│   └── db/changelog/       # Liquibase migrations
│
├── jackpot-contribution/
│   ├── domain/
│   │   ├── contribution/   # Contribution models, commands, queries
│   │   ├── jackpot/        # Jackpot models, commands
│   │   └── strategy/       # Fixed, Variable strategies
│   ├── infrastructure/
│   │   ├── config/
│   │   ├── endpoint/
│   │   │   ├── contribution/  # ContributionController
│   │   │   └── jackpot/       # JackpotController
│   │   ├── messaging/      # Adapter: BetEventConsumer
│   │   └── store/          # Adapters for stores
│   └── db/changelog/
│
├── jackpot-reward/
│   ├── domain/
│   │   ├── EvaluateRewardCommand.java
│   │   ├── RewardStore.java  # Port
│   │   └── strategy/       # Fixed, Variable strategies
│   ├── infrastructure/
│   │   ├── config/
│   │   ├── endpoint/       # RewardController
│   │   └── store/          # Adapter: RewardStoreDatabase
│   └── db/changelog/
│
├── jackpot-app/
│   ├── Application.java    # Spring Boot entry point
│   ├── application.yaml    # PostgreSQL config
│   └── application-local.yaml  # H2 config
│
├── init/
│   └── 00-create-schemas.sql
├── docker-compose.yml
├── Dockerfile
└── pom.xml
```