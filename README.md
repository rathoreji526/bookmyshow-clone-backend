# 🎟️ BookMyShow Backend Clone

## 🚀 Project Overview
A scalable backend system inspired by BookMyShow, built with **Java + Spring Boot + PostgreSQL**.

Focus areas include:
- Seat management & layout per show
- Concurrency-safe booking
- Booking lifecycle & transaction simulation
- Theater, Screen, and Show management

---

## ✅ Features Implemented

### 👤 User & Core Entities
- Role-based User system (USER / OWNER / ADMIN)
- UUID-based global base entity with timestamps
- Location, Theater, Screen modeling
- Unique constraints for usernames and normalized entity fields

---

### 🎬 Movie, Show & Seat System
- Movies as independent entities (global catalog)
- Show creation linking **Movie → Screen → Timing**
- Automatic **ShowSeat generation** for every show
- Each show acts as an independent **seat universe**
- Seats support multiple types:
  - PREMIUM
  - GOLD
  - SILVER
- Seat status:
  - AVAILABLE
  - LOCKED
  - BOOKED

---

### 🔒 Seat Locking System (Concurrency Safe)
- Bulk seat locking using **atomic DB queries**
- Prevents race conditions (multiple users selecting same seat)
- Supports **multi-seat selection**
- Implements **all-or-nothing locking**
- Lock expiry mechanism (5 minutes)

---

### 🧠 Booking & Transaction Design
- Booking entity tracks lifecycle: `PENDING → CONFIRMED / CANCELLED`
- Transaction entity simulates payment flow
- Async payment handling (polling simulation, 10s intervals)
- Payment result updates booking & seat status accordingly

---

### 🏢 Theater & Screen Management
- Theater registration with address/location
- Flexible screen creation with **row & seat layout**
- Supports multiple seat types and non-contiguous seat numbers
- Normalization applied to names for consistency and uniqueness

---

## ⚙️ Tech Stack
- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Lombok
- JUnit / Mockito (for testing)

---

## 🧠 Key Concepts Implemented
- UUID-based scalable design
- Entity relationships: OneToMany / ManyToOne / ManyToMany
- Transaction management (`@Transactional`)
- Concurrency handling (atomic updates, seat locking)
- Bulk operations for performance
- Separation of static vs dynamic data:
  - Seat → static per screen
  - ShowSeat → dynamic per show
- Normalization for unique search fields (movies, screens, seats)

---

## 🔄 High-Level System Flow

```text
User selects movie & show timing
        ↓
Seats fetched for the show
        ↓
Seat locking (atomic, with 5-min expiry)
        ↓
Booking created (PENDING)
        ↓
Transaction initiated (PENDING)
        ↓
Payment result
   ↓            ↓
SUCCESS       FAILED
   ↓            ↓
BOOKED       RELEASE SEATS