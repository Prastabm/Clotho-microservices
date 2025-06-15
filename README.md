# 🛍️ Spring Boot Microservices E-Commerce Platform

This project is a modular, production-grade **E-Commerce backend system** built using **Spring Boot microservices** architecture. It supports secure user management, real-time inventory/order updates, asynchronous communication, and seamless third-party integrations.

## 🔧 Tech Stack

- **Backend Framework**: Spring Boot
- **Microservices**: Product, Order, Inventory, Notification
- **API Gateway**: Custom Spring Cloud Gateway
- **Authentication & Authorization**: Keycloak (via Docker Compose)
- **Databases**:
  - PostgreSQL (hosted on **NeonDB**)
- **Message Broker**: Apache Kafka (via **Confluent Cloud**)
- **Communication**:
  - REST (synchronous)
  - Kafka (asynchronous)
- **Containerization**: Docker (each service has its own Dockerfile)
- **Deployment Target**: Google Cloud Platform (Compute Engine, 2 VMs)
- **Monitoring**: Planned
- **Circuit Breaker**: Planned via Resilience4j

---

## 🧩 Microservices Overview

### 📦 Product Service
- CRUD for product catalog
- REST APIs
- PostgreSQL integration

### 🛒 Order Service
- Manages order placement and tracking
- Sends Kafka events to Notification service
- Uses PostgreSQL (via NeonDB)

### 📦 Inventory Service
- Manages product inventory levels
- Consumes order events
- Decreases stock, updates product availability
- Uses PostgreSQL (via NeonDB)

### 🔔 Notification Service
- Listens to order events via Kafka
- Sends email/SMS notifications (placeholder logic)
- Stateless service

### 🌐 API Gateway
- Acts as a unified entry point for external clients
- Handles routing and forwarding
- Integrated with Keycloak for secured endpoints

---

## 🛡️ Security

- **Keycloak** manages user registration, login, and RBAC (Role-Based Access Control).
- Keycloak is containerized and run locally using Docker Compose.

---

## 📦 Dockerization

Each service includes its own `Dockerfile`:
- Simple multi-stage builds
- Environment-specific variables injected at runtime
- Compatible with local Docker Desktop and cloud deployment

> ✅ **Note**: Database services are not containerized. PostgreSQL is hosted on [NeonDB](https://neon.tech/).

---

## ☁️ Deployment Plan

### ✅ Local Development
- Run all services using Docker Desktop
- Kafka handled via Confluent Cloud (no local Kafka setup needed)
- NeonDB used for PostgreSQL access

### ☁️ Cloud Deployment (GCP)
- **2 VMs** on **Google Compute Engine**
- Docker images pulled or built directly on VMs
- LoadBalancer service for **API Gateway**
- Free-tier compliant setup

---

## ⚙️ Kafka Topics & Flow

1. `order-events`  
   - Published by **Order Service**  
   - Consumed by **Inventory Service** & **Notification Service**

2. `inventory-updates` *(Optional)*  
   - Published by Inventory after stock update

> Kafka is fully managed via **Confluent Cloud**, reducing ops overhead.

---

## 🔐 Environment Config

Use `.env` files or secure secrets management to inject:

```env
# NeonDB URLs
ORDER_DB_URL=jdbc:postgresql://<order-neon-url>
INVENTORY_DB_URL=jdbc:postgresql://<inventory-neon-url>

# Kafka
KAFKA_BOOTSTRAP_SERVERS=<confluent-cloud-bootstrap>
KAFKA_API_KEY=...
KAFKA_API_SECRET=...

# Keycloak
KEYCLOAK_AUTH_URL=http://localhost:8085/realms/<realm>
