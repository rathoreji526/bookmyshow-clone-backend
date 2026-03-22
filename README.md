# 🎟️ BookMyShow Backend Clone

## 🚀 Project Overview
Building a scalable backend system inspired by BookMyShow using **Java + Spring Boot + PostgreSQL**.

The focus is on designing real-world backend systems involving:
- Seat management
- Concurrency handling
- Booking lifecycle
- Transaction flow

---

## ✅ Features Implemented

### 👤 User & Core Entities
- Role-based User system (USER / OWNER / ADMIN)
- Global base entity with UUID + timestamps
- Location, Theater, Screen modeling

---

### 🎬 Show & Seat System
- Show creation with Movie + Screen mapping
- Automatic **ShowSeat generation** for every show
- Each show acts as an independent **seat universe**
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

### 🧠 Booking & Transaction Design (In Progress)
- Booking entity to manage booking lifecycle
- Transaction entity for payment simulation
- Designed flow:
    - Booking (PENDING → CONFIRMED / CANCELLED)
    - Transaction (PENDING → SUCCESS / FAILED)

---

## ⚙️ Tech Stack
- Java
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Lombok

---

## 🧠 Key Concepts Implemented
- UUID-based scalable design
- Entity relationships (OneToMany / ManyToOne)
- Transaction management (`@Transactional`)
- Concurrency handling (atomic updates)
- Bulk operations for performance
- Separation of static vs dynamic data:
    - Seat → static
    - ShowSeat → dynamic

---

## 🔄 System Flow (High Level)

```text
User selects seats
        ↓
Seat locking (atomic, with expiry)
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