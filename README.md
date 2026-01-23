# E-Commerce Microservices Architecture
**Spring Boot 3 | Spring Cloud | Kafka | Keycloak | Zipkin | MongoDB | PostgreSQL | Docker | Flyway**

This repository contains a **production-grade, fully containerized E-Commerce Microservices system** built using modern backend architecture principles.  
The project demonstrates real-world implementations of **DDD**, **event-driven communication**, **centralized configuration**, **security**, **database migrations**, and **distributed tracing**.

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
<img width="1668" height="1019" alt="image" src="https://github.com/user-attachments/assets/e061882a-832c-4641-beb4-d45296863c35" />

---

## 🗂️ Mono-Repository Strategy

This project uses a **mono-repo approach**:

- All microservices reside in a single repository
- Centralized dependency and version management
- Shared configuration through Config Server
- Easier onboarding and maintenance

---
## ⚙️ Infrastructure & Tooling

- **Docker & Docker Compose:** Fully containerized environment for consistent deployment.
- **Apache Kafka:** High-throughput event streaming.
- **Keycloak:** Identity and Access Management (IAM).
- **Flyway:** Robust database migration and version control.
- **Zipkin:** Distributed Tracing and latency analysis.
- **MailDev:** SMTP Server for testing email deliveries.
- **Databases:** PostgreSQL (Relational) & MongoDB (NoSQL).

---

## 📦 Out-of-the-Box Ready Infrastructure

The following systems are pre-configured and ready to launch via Docker Compose:

* ✅ **PostgreSQL 16:** Primary relational database (Product, Order, Payment).
* ✅ **MongoDB:** Document store (Customer, Notification).
* ✅ **Apache Kafka & Zookeeper:** Event bus configuration.
* ✅ **Keycloak:** Auth server with auto-imported realms.
* ✅ **Zipkin:** Tracing server.
* ✅ **MailDev:** Email testing tool.
* ✅ **Config Server:** Centralized configuration management.
* ✅ **Discovery Server:** Eureka service registry.

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
- **Flyway** integration for schema migrations
- PostgreSQL datastore

---

### 🧾 Order Service
- Manages order creation and lifecycle
- Communicates with Customer, Product, and Payment services
- Publishes **Order Confirmation Events** to Kafka
- **Flyway** integration for schema migrations
- PostgreSQL datastore

---

### 💳 Payment Service
- Processes payment transactions
- Publishes **Payment Confirmation Events** to Kafka
- **Flyway** integration for schema migrations
- PostgreSQL datastore

---

### 📧 Notification Service
- Consumes Kafka events (Order & Payment)
- Sends emails via SMTP
- Persists notification history in MongoDB

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
- Git-backed configuration repository (or Native/Local)
- Environment-based configurations (docker, dev, prod)

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
- Role-based access control (RBAC)

---

## 🗄️ Databases & Migrations (Flyway)

Each microservice owns its database schema to ensure loose coupling. **Flyway** is used for PostgreSQL-based services to manage database schema evolution, ensuring that the database state is always consistent with the application code.

| Service         | Database Technology | Database Name | Migration Tool |
|-----------------|---------------------|---------------|----------------|
| Customer        | MongoDB             | customer      | -              |
| Product         | PostgreSQL          | product       | **Flyway** |
| Order           | PostgreSQL          | order_db      | **Flyway** |
| Payment         | PostgreSQL          | payment       | **Flyway** |
| Notification    | MongoDB             | customer      | -              |
---

## 🚀 Quick Start (Zero-Configuration)

This project is designed to be "plug-and-play" using Docker Compose.

### Prerequisites

- Java 21+
- Docker & Docker Compose
- Maven
- PowerShell (for setup script)

### 1. Environment Setup
Create a `.env` file in the root directory and populate it. **Do not commit this file.**
**Generate a strong secret:**
* **Linux/macOS:** `openssl rand -base64 32`
* **Windows PowerShell:** `[Convert]::ToBase64String((1..32 | ForEach-Object { Get-Random -Minimum 0 -Maximum 256 }))`
```env
KEYCLOAK_ADMIN=admin
KEYCLOAK_ADMIN_PASSWORD=your_secure_password
MICRO_SERVICES_API_SECRET=your_generated_base64_secret
```

### 2. Generate Keycloak Realm
Run the helper script to inject your secret into the Keycloak configuration:

```powershell
.\scripts\generate-realms.ps1
```

### 3. Build & Run
Build the JAR files and start the entire infrastructure (Databases, Kafka, Keycloak, and Microservices) with a single command.

```bash
# Build the project
mvn clean package -DskipTests

# Start Docker containers
docker compose up -d --build
```

*Note: The `init.sql` script will automatically create the necessary PostgreSQL databases on the first run.*

---

## 🌐 Service Access URLs

| Component | URL | Description |
|----------|-----|-------------|
| **API Gateway** | `http://localhost:8222` | Single entry point for all APIs |
| **Eureka Server** | `http://localhost:8761` | Service discovery dashboard |
| **Keycloak** | `http://localhost:9099` | Authentication & authorization server |
| **Zipkin** | `http://localhost:9411` | Distributed tracing UI |
| **MailDev** | `http://localhost:1080` | Email testing dashboard |
| **Config Server** | `http://localhost:8888` | Central config management |
| **Kafka Broker** | `localhost:9092` | Event streaming platform |

---

## 🧪 Testing the Flow

1.  **Get Token:** Obtain an access token from Keycloak (`http://localhost:9099`).
2.  **Place Order:** Send a POST request to `http://localhost:8222/api/v1/orders` with the Oauth2 token.
3.  **Verify:**
    * Check **MailDev** (`http://localhost:1080`) for the confirmation email.
    * Check **Zipkin** (`http://localhost:9411`) to trace the request flow.
