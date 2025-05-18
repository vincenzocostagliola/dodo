# 📝 Dodo – Todo List Manager

**Dodo** is a modern Android application built with Jetpack Compose that allows users to manage a todo list efficiently.  
It supports creating, editing, and viewing tasks, and lets users customize the sorting logic via preferences.

---

## ✨ Features

- 🏠 **Home Screen** – Displays a list of todos sorted by user's selected preference (e.g., date, priority)
- 🔍 **Details Screen** – View, edit, or create a new todo entry
- ⚙️ **Settings Screen** – Customize the way todos are sorted
- 📦 **Persistence** – Local storage using Room and SharedPreferences

---

## 🛠 Tech Stack

| Tool                    | Purpose                                |
|-------------------------|----------------------------------------|
| Jetpack Compose         | Declarative UI                         |
| Kotlin Coroutines       | Asynchronous programming               |
| Flow                    | Reactive stream handling               |
| Hilt                    | Dependency Injection                   |
| Room                    | Local database for todos               |
| SharedPreferences       | Store user sorting preferences         |
| ThreeTenABP             | Date/time API                          |
| Kotlin Serialization    | JSON parsing                           |
| Timber                  | Logging framework                      |

---

## 📦 Project Structure

```plaintext
dodo/
├── app/                  # Main application module (NavGraph, App setup)
│   ├── src/
│   └── build.gradle.kts
│
├── build/                # Build outputs
│
├── data/                 # Data handling (API, models, repository abstraction)
│
├── db/                   # Room database setup and DAO interfaces
│
├── designsystem/         # Theme, typography, reusable UI components
│
├── features/             # Feature-based modules (each self-contained)
│   ├── home/             # Home screen (todo list + ViewModel)
│   ├── details/          # Add/edit/view single todo
│   └── settings/         # Sorting preference screen
│
├── gradle/               # Gradle wrapper and config
│
├── build.gradle.kts
├── settings.gradle.kts
└── local.properties
