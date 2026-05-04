# Nassem — Smart Weather Experience App

Nassem is a modern Android weather application built to provide a more **interactive and personalized weather experience**.  
Instead of just showing raw weather data, the app focuses on delivering meaningful insights through themes, smart alerts, and smooth UI.

---

## Features

- Real-time weather data using OpenWeather API  
- GPS-based automatic location detection  
- Map-based location picker  
- Favorite locations saved locally using Room Database  
- 5-day weather forecast  
- Smart weather classification (Very Hot / Hot / Mild / Cold / Very Cold)  
- Weather alerts with alarm system  
- Notification support with snooze feature  
- Dynamic themes based on weather conditions  
- Smooth and responsive UI using Jetpack Compose  

---

## Architecture

The project follows **MVVM architecture** to ensure clean separation of concerns and scalability.

- **UI Layer (Jetpack Compose)**  
  Handles all UI rendering and user interactions.

- **ViewModel Layer**  
  Manages UI state and business logic reactively.

- **Data Layer**  
  Handles API requests and local storage using Room.

This structure makes the app easier to maintain, test, and extend.

---

## Tech Stack

Kotlin • Jetpack Compose • MVVM • Retrofit • Room  
Coroutines • Flow • DataStore • WorkManager  
Google Location Services • OpenWeather API  

---





