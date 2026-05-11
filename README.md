# 🎟️ BookMyShow Backend Clone (Scalable Ticket Booking System)

## 🚀 Project Overview

A production-inspired backend system modeled after BookMyShow, built using **Java + Spring Boot + PostgreSQL**.

This project focuses on solving **real-world backend challenges**, especially:

* High concurrency seat booking
* Transaction consistency
* Event-driven architecture (Kafka)
* Clean architecture

---

## 🔥 Key Highlights

* ⚡ **Concurrency-safe seat locking (DB-level atomic update)**
* 🎯 **Bulk operations for performance optimization**
* 🧠 **State-driven booking lifecycle (AVAILABLE → LOCKED → BOOKED)**
* ⏱ **Auto seat unlock with expiry handling**
* 💳 **End-to-end booking + payment simulation**
* 📨 **Kafka-based async booking confirmation**
* 🏗 **Clean layered architecture (Controller → Service → Repository)**

---

## 🧩 Core Features

### 👤 User Management

* UUID-based entity IDs
* Audit fields (`createdAt`, `updatedAt`)
* User registration & management APIs

---

### 🎬 Movie Management

* Global movie catalog
* Metadata: name, duration, language, rating, release date
* Decoupled from shows (same movie → multiple shows)

---

### 🏢 Theater & Screen System

* Theater onboarding
* Multiple screens per theater
* Flexible seat layouts (row-based structure)

---

### 💺 Seat & ShowSeat (Core Design 🔥)

> Separation of static vs dynamic seat data

* **Seat** → static (per screen)
* **ShowSeat** → dynamic (per show)

Each show acts as an independent **seat universe**

---

### 🎭 Show Management

* Create shows using Movie + Screen + Timing
* Auto-generate ShowSeats during show creation
* Non-overlapping show validation
* Seat-type based pricing

---

## 🔒 Concurrency-Safe Seat Locking

* Atomic locking using a **single bulk DB query**
* Prevents race conditions and double booking
* Supports multi-seat selection
* Lock expiry: **5 minutes**

```sql
UPDATE show_seat
SET status = 'LOCKED', locked_by = ?, lock_expiry = ?
WHERE id IN (?) AND status = 'AVAILABLE'
```

---

## 📨 Kafka Event-Driven Architecture

* **TransactionEventProducer** → publishes event after payment completion
* **TransactionEventConsumer** → consumes async and triggers booking confirmation
* **Idempotency checks** to handle duplicate Kafka events safely
* Decouples payment processing from booking workflow

---

## 💳 Booking & Payment Flow

### Lifecycle:

```
AVAILABLE → LOCKED → BOOKED
           ↓
        EXPIRED → AVAILABLE
```

### Flow:

1. User selects seats
2. Seats locked (5 min expiry)
3. Booking created (PENDING)
4. Transaction created (PENDING)
5. Payment initiated (fake gateway)
6. Kafka event published
7. Consumer processes event
8. SUCCESS → CONFIRMED → Seats BOOKED + Email
9. FAILURE → CANCELLED → Seats RELEASED

---

## 🔄 Scheduler System

* Seat lock expiry cleanup
* **Fallback mechanism** for Kafka failures (processes pending confirmed transactions)

---

## 📧 Email Integration

* Booking confirmation emails
* Ticket delivery via email

---

## ⚙️ Tech Stack

* Java 17+
* Spring Boot
* Spring Data JPA (Hibernate)
* PostgreSQL
* Apache Kafka
* Lombok
* JavaMailSender
* Spring Scheduler

---

## 🧠 Design Decisions

* UUID-based scalable IDs
* Bulk DB operations for performance
* Optimized seat locking query
* Clean entity relationships
* Service-layer abstraction
* State-based booking system
* Event-driven decoupling with Kafka

---

## 📡 API Modules

* Movie API
* Theater API
* Screen API
* Show API
* Booking API
* Transaction API
* User API
* Location API

---

## 🚀 System Flow (High-Level)

```
User → Select Show → Fetch Seats
      ↓
Seat Locking (Atomic + 5 min expiry)
      ↓
Create Booking (PENDING) + Transaction (PENDING)
      ↓
Fake Payment Gateway
      ↓
Kafka Event Published
      ↓
Consumer → SUCCESS → BOOKED + Email
Consumer → FAILURE → RELEASE SEATS
```

---

## 📈 Current Limitations

* DB-based locking (not horizontally scalable)
* Monolithic architecture

---

## 🔮 Future Enhancements

### 🚀 Scalability Upgrades

* Redis-based seat locking (SETNX + TTL)
* Read caching (movies, shows, seats)
* Docker containerization

---

### ⚡ Advanced Features

* WebSocket for real-time seat updates
* Dynamic pricing

---

## 🏗 Architecture Vision

### Current:

```
Monolithic Spring Boot + PostgreSQL + Kafka
```

### Target:

```
API Gateway
   ↓
Microservices (Booking, Payment, Notification)
   ↓
Kafka + Redis
   ↓
Database per service
```

---

## 🧪 Learning Outcomes

* Designed a real-world booking system
* Handled concurrency using DB-level locking
* Implemented transactional workflows
* Built event-driven architecture with Kafka
* Implemented idempotency for reliable event processing

---

## ⭐ Final Note

This project goes beyond CRUD —
it focuses on **real-world backend challenges like concurrency, consistency, event-driven architecture, and scalability**.

---