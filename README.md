# 📝 AndroidTasksApp

[![Build Status](https://github.com/frlemieux/AndroidTasksApp/actions/workflows/android.yml/badge.svg)](https://github.com/frlemieux/AndroidTasksApp/actions)

A modern Android task management app built using **Kotlin**, **Jetpack Compose**, and **MVVM architecture**. Designed for simplicity and performance, this app demonstrates clean architecture principles and efficient state handling using **StateFlow** and **ViewModel**.

---

## 🚀 Features

- ✅ Add and delete tasks  
- 📱 Jetpack Compose UI for a modern and responsive experience  
- 🧠 MVVM + Clean Architecture  
- 🔁 Reactive state management with Kotlin `StateFlow`  
- ✅ Comprehensive test coverage

---

## 📊 Test Coverage

- Unit tests for core functionality
- UI tests for task management
- Test results visible in GitHub Actions

### View Test Results
- [GitHub Actions](https://github.com/frlemieux/AndroidTasksApp/actions)
- [Test Artifacts](https://github.com/frlemieux/AndroidTasksApp/actions/workflows/android.yml)

---

## 📸 Screenshots

|Dark Theme | Light Theme|
|-|-|
|<img src="screenshots%2FdarkTheme.png" width="300" />|<img src="screenshots%2FlightTheme.png" width="300" />|

---

## 🧱 Tech Stack

- **Language**: Kotlin  
- **UI**: Jetpack Compose  
- **Architecture**: MVVM + UseCase + Repository pattern  
- **State**: StateFlow, ViewModel  
- **Dependency Injection**: Hilt *(optional - confirm if used)*  
- **Testing**: JUnit  

---

## 📂 Project Structure

```plaintext
.
├── data          # Repository and model layer
├── domain        # UseCases and business logic
├── ui            # Composables, screens and ViewModels

```

---

## 🛠️ Getting Started

### Prerequisites

- Android Studio Flamingo or newer  
- Kotlin 1.8+  
- Gradle 8+  

### Installation

1. Clone the repo:
   ```bash
   git clone https://github.com/frlemieux/AndroidTasksApp.git

