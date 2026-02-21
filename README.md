# Master-API: End-to-End API Automation with RestAssured

[![Java](https://img.shields.io/badge/Java-11+-blue)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
[![Maven](https://img.shields.io/badge/Maven-3.6+-orange)](https://maven.apache.org/)
[![Build](https://img.shields.io/badge/Build-Passing-brightgreen)]()

This repository contains **end-to-end API automation tests** using **RestAssured**, **Java**, and **JUnit 5**. The tests cover CRUD operations, token authentication, logging, and validations for both positive, negative, and edge cases.

---

## Table of Contents

1. [Project Overview](#project-overview)  
2. [Features](#features)  
3. [Folder Structure](#folder-structure)  
4. [Prerequisites](#prerequisites)  
5. [Setup & Installation](#setup--installation)  
6. [Running Tests](#running-tests)  
7. [Test Reports & Logs](#test-reports--logs)  
8. [Examples of API Requests](#examples-of-api-requests)  
9. [Contributing](#contributing)  
10. [License](#license)  

---

## Project Overview

The project automates testing for a booking API system and includes:

- **Authentication:** CreateToken, Bearer Token handling
- **CRUD Operations:** CreateBooking, GetBooking, GetBookingIds, UpdateBooking, PartialUpdateBooking, DeleteBooking
- **Health Checks:** Ping, HealthCheck endpoints
- **Logging:** Detailed request and response logs for debugging
- **Validation:** Status codes, JSON schema, and field-level verification
- **Scenarios Covered:** Positive, Negative, Edge cases

---

## Features

- Full **CRUD automation**
- **Token-based authentication**
- **GET request handling** with query parameters and filtering
- **POST, PUT, PATCH, DELETE** testing with proper validation
- **Error handling & logging** for API failures
- Maven-based project for easy build & test
- Structured **E2E test flow** from token creation to deletion

---

## Folder Structure
