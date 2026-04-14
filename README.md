# 🎟️ BookMyShow Backend Clone (Scalable Ticket Booking System)

## 🚀 Project Overview

A production-inspired backend system modeled after BookMyShow, built using **Java + Spring Boot + PostgreSQL**.

This project focuses on solving **real-world backend challenges**, especially:

* High concurrency seat booking
* Transaction consistency
* Scalable system design
* Clean architecture

---

## 🔥 Key Highlights

* ⚡ **Concurrency-safe seat locking (DB-level atomic update)**
* 🎯 **Bulk operations for performance optimization**
* 🧠 **State-driven booking lifecycle (AVAILABLE → LOCKED → BOOKED)**
* ⏱ **Auto seat unlock with expiry handling**
* 💳 **End-to-end booking + payment simulation**
* 🏗 **Clean layered architecture (Controller → Service → Repository)**

---

## 🧩 Core Features

### 👤 User Management

* Role-based system (**USER / OWNER / ADMIN**)
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

* Theater onboarding with GST, PAN, license
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
* Prevents race conditions
* Supports multi-seat selection
* Lock expiry: **5 minutes**

```sql
UPDATE show_seat
SET status = 'LOCKED'
WHERE ...
```

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
4. Payment initiated
5. Transaction processed
6. SUCCESS → CONFIRMED → Seats BOOKED
7. FAILURE → CANCELLED → Seats RELEASED

---

## 🔄 Scheduler System

* Seat lock expiry cleanup
* Transaction status polling (every 10 sec)

> ⚠️ Note: Will be replaced with event-driven architecture (Kafka)

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

---

## 📡 API Modules

* Movie API
* Theater API
* Screen API
* Show API
* Booking API
* Transaction API
* User API

---

## 🚀 System Flow (High-Level)

```
User → Select Show → Fetch Seats
      ↓
Seat Locking (Atomic)
      ↓
Booking (PENDING)
      ↓
Payment
      ↓
SUCCESS → BOOKED + Email
FAILURE → RELEASE SEATS
```

---

## 📈 Current Limitations

* DB-based locking (not horizontally scalable)
* Scheduler-based polling (not real-time)
* Monolithic architecture

---

## 🔮 Future Enhancements

### 🚀 Scalability Upgrades

* Redis-based seat locking (SETNX + TTL)
* Kafka for async event processing
* Read caching (movies, shows, seats)

---

### 🔐 Security

* Spring Security + JWT
* Role-based authorization
* API protection

---

### ⚡ Advanced Features

* WebSocket for real-time seat updates
* Dynamic pricing
* Rate limiting
* Idempotency handling

---

## 🏗 Architecture Vision

### Current:

```
Monolithic Spring Boot + PostgreSQL
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
* Built scalable-ready architecture

---

## ⭐ Final Note

This project goes beyond CRUD —
it focuses on **real-world backend challenges like concurrency, consistency, and scalability**.

---
