# 🎟️ BookMyShow Backend Clone

## 🚀 Project Overview

A scalable backend system inspired by BookMyShow, built with **Java + Spring Boot + PostgreSQL**.

This project focuses on designing a **real-world ticket booking system** with emphasis on:

* Concurrency-safe seat booking
* Scalable system design
* Clean architecture & separation of concerns
* Realistic booking lifecycle simulation

---

## 🧩 System Modules

### 👤 User Management

* Role-based access control (**USER / OWNER / ADMIN**)
* Unique username constraint
* UUID-based entity identification
* Base entity with audit fields (createdAt, updatedAt)

---

### 🎬 Movie Management

* Movies stored as a **global catalog**
* Metadata includes:

  * Name, Duration, Language, Release Date, Rating
* Decoupled from shows (same movie can run in multiple theaters)

---

### 🏢 Theater & Screen Management

* Theater registration with:

  * GST, PAN, Business License
  * Location mapping
* Screen management:

  * Multiple screens per theater
  * Custom seat layout per screen
* Flexible seat configuration:

  * Row-based layout (A, B, C...)
  * Configurable seat ranges

---

### 💺 Seat & ShowSeat Design (Core Concept 🔥)

#### Static vs Dynamic Separation

* **Seat** → Fixed per screen (structure)
* **ShowSeat** → Created per show (state)

#### Seat Types:

* SILVER
* GOLD
* PREMIUM

#### Seat Status:

* AVAILABLE
* LOCKED
* BOOKED

👉 Each show acts as an **independent seat universe**

---

### 🎭 Show Management

* Show created using:

  * Theater + Screen + Movie + Timing
* Automatic **ShowSeat initialization** during show creation
* Pricing per seat type:

  * `price_silver`
  * `price_gold`
  * `price_premium`
* Supports multiple formats (e.g., FORMAT_2D)

---

## 🔒 Concurrency-Safe Seat Locking

* Atomic seat locking using **single DB query**
* Prevents race conditions
* Supports:

  * Multi-seat selection
  * All-or-nothing locking
* Lock expiry:

  * Auto-release after **5 minutes**

---

## 💳 Booking & Payment Flow

### Booking Lifecycle:

PENDING → CONFIRMED / CANCELLED

### Flow:

1. User selects seats
2. Seats are locked
3. Booking created (PENDING)
4. Payment initiated
5. Payment result processed

### Payment Simulation:

* Fake payment gateway
* Background polling every **10 seconds**
* Updates:

  * Booking status
  * Seat status

---

## ⚙️ Tech Stack

* Java 17+
* Spring Boot
* Spring Data JPA (Hibernate)
* PostgreSQL
* Lombok
* JUnit / Mockito

---

## 🧠 Key Design Decisions

* UUID-based scalable architecture
* Clear separation of responsibilities:

  * ShowService
  * ShowSeatService
  * BookingService
* Bulk DB operations for performance
* Optimized queries for seat locking
* Normalization for unique fields
* Clean entity relationships:

  * OneToMany / ManyToOne

---

## 🚀 High-Level System Flow

User selects movie & show
↓
Fetch seats (ShowSeat)
↓
Seat locking (atomic, 5-min expiry)
↓
Booking created (PENDING)
↓
Payment initiated
↓
Transaction polling (10 sec interval)
↓
SUCCESS        FAILED
↓              ↓
CONFIRMED      CANCELLED
↓              ↓
Seats BOOKED   Seats RELEASED

---

## 🔮 Future Enhancements

* Redis for distributed seat locking & caching
* WebSocket for real-time seat updates
* Dynamic pricing (weekends, demand-based)
* Integration with real payment gateways
* Rate limiting & security improvements
* Microservices architecture

---

## 📌 Conclusion

This project demonstrates:

* Real-world system design thinking
* Concurrency handling in high-traffic systems
* Scalable backend architecture

Built as a **learning-focused production-grade system design project** 🚀
