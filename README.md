# E-Commerce Microservices Architecture
**Spring Boot 3 | Spring Cloud | Kafka | Keycloak | Zipkin | MongoDB**

This repository contains a **production-grade E-Commerce Microservices system** built using modern backend architecture principles.  
The project demonstrates real-world implementations of **DDD**, **event-driven communication**, **centralized configuration**, **security**, and **distributed tracing**.

## 🎯 Business Requirements

The system supports the following capabilities:

- Customer management
- Product catalog management
- Order lifecycle handling
- Payment processing
- Notification delivery
- Secure API access
- Asynchronous event-driven communication
- End-to-end observability

---

## 🧱 Global System Design

The system follows a **distributed microservices architecture** where:

- Each service is independently deployable
- Services communicate synchronously via REST
- Asynchronous communication is handled via Kafka
- All external access is routed through an API Gateway

---

## 📐 Domain Driven Design (DDD)

The architecture is designed using **DDD principles**:

- Each microservice represents a **bounded context**
- Clear separation of concerns:
  - Domain Layer
  - Application Layer
  - Infrastructure Layer
- Business logic remains isolated from technical frameworks

---

## 🖼️ Application Architecture Diagram
<img width="1611" height="842" alt="micro-services drawio" src="https://github.com/user-attachments/assets/c9b9a6de-0d42-445f-ad84-575051967754" />
---

## 🗂️ Mono-Repository Strategy

This project uses a **mono-repo approach**:

- All microservices reside in a single repository
- Centralized dependency and version management
- Shared configuration through Config Server
- Easier onboarding and maintenance

---

## ⚙️ Infrastructure & Tooling

- Docker & Docker Compose
- Apache Kafka & Zookeeper
- MongoDB
- Zipkin (Distributed Tracing)
- Keycloak (Authentication & Authorization)

---

## 🔧 Core Microservices

### 🧍 Customer Service
- Manages customer data
- MongoDB datastore
- RESTful API exposure

---

### 📦 Product Service
- Handles product catalog
- Manages pricing and stock
- MongoDB datastore

---

### 🧾 Order Service
- Manages order creation and lifecycle
- Communicates with:
  - Customer Service
  - Product Service
  - Payment Service
- Publishes **Order Confirmation Events** to Kafka

---

### 💳 Payment Service
- Processes payment transactions
- Publishes **Payment Confirmation Events** to Kafka

---

### 📧 Notification Service
- Consumes Kafka events
- Sends notifications (email/logs)
- Persists notification history

---

## 🌐 API Gateway

- Central entry point for all clients
- Routes requests to downstream services
- Integrated with Keycloak for security enforcement

---

## 🔍 Service Discovery (Eureka Server)

- All services register dynamically
- Enables load balancing and failover
- Eliminates hardcoded service addresses

---

## ⚙️ Configuration Server

- Centralized external configuration management
- Git-backed configuration repository
- Environment-based configurations (dev, prod, etc.)

---

## 📬 Event-Driven Architecture (Kafka)

Kafka is used for asynchronous communication:

- Order confirmation events
- Payment confirmation events

Benefits:
- Loose coupling
- Scalability
- Fault tolerance

---

## 🔎 Distributed Tracing (Zipkin)

- Tracks requests across microservices
- Provides visibility into:
  - Latency
  - Errors
  - Bottlenecks

---

## 🔐 Security (Keycloak)

- OAuth2 / OpenID Connect
- Centralized authentication server
- API Gateway enforces security policies
- Role-based access control

---

## 🗄️ Databases

Each microservice owns its database:

| Service        | Database |
|---------------|----------|
| Customer       | MongoDB |
| Product        | PostgreSql |
| Order          | PostgreSql |
| Payment          | PostgreSql |
| Notification  | MongoDB |

---

## 🚀 Running the Project

### Prerequisites

- Java 17+
- Docker & Docker Compose
- Maven

### Startup Steps

```bash
git clone https://github.com/enescelebii/microservice-e-commerce-app.git
cd microservices-full-code
docker-compose up -d
```
## ▶️ After Docker Startup

# Once Docker containers are up and running, start the Spring Boot services **in the correct order** to ensure proper service discovery and configuration loading.
- 1️⃣ Start the Configuration Server
- 2️⃣ Start the Eureka Discovery Server
- 3️⃣ Start the API Gateway
- 4️⃣ Start Core Microservices
  - customer-service
  - product-service
  - order-service
  - payment-service
  - notification-service
---
## 🌐 Service Access URLs

| Component | URL | Description |
|----------|-----|-------------|
| Eureka Server | http://localhost:8761 | Service discovery dashboard |
| API Gateway | http://localhost:8222 | Single entry point for all APIs |
| Zipkin | http://localhost:9411 | Distributed tracing UI |
| Keycloak | http://localhost:8080 | Authentication & authorization server |
| Kafka Broker | http://localhost:9092 | Event streaming platform |
| MongoDB | http://localhost:27017 | Database service |

---

## 🔐 Security Configuration (Keycloak)

The application uses **Keycloak** for authentication and authorization.

### Default Setup
- Create a **realm**
- Create a **client** for the API Gateway
- Enable:
  - OAuth2
  - OpenID Connect
- Configure redirect URIs

### Authentication Flow
1. Client sends request to API Gateway
2. Gateway redirects to Keycloak
3. Access token is issued
4. Token is validated by Gateway
5. Request is routed to target microservice

---
