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

```

---
## 🧠 Architecture

The project is built following **modular Clean Architecture**, where concerns are separated clearly and modules are responsible for their own domains.

### 🔹 Layers

- **Presentation Layer**:  
  UI built with Jetpack Compose + ViewModel using `StateFlow`.

- **Domain Layer**:  
  Business logic and use cases (can be extended to a separate module).

- **Data Layer**:  
  Handles data operations through repositories using Room and SharedPreferences.

- **DI Layer**:  
  Hilt provides dependency injection setup in each module.

### ✨ Highlights

- **MVVM architecture** (Model-View-ViewModel)
- **Unidirectional Data Flow** from ViewModel → UI
- **Feature-based modularization**: each feature is self-contained and independently testable
- **UI state** is modeled using sealed classes or data classes and observed via `StateFlow`

## 🛠 Setup

1. Clone this repository:
   ```bash
   git clone https://github.com/vincenzocostagliola/dodo.git
   ```

2. Open in **Android Studio** (Meerkat or newer).

3. Sync Gradle.

4. Run the app!

---


## 📸 Screenshots

*Coming soon... Stay tuned!* 🚀

---

## 🧹 Future Improvements

- Implement crypto favorites/bookmarks.
- Add a search bar to filter crypto by name.
- Dark mode support.
- UI/UX enhancements with animated transitions.

---

## 🧑‍💻 Contributing

Pull requests and suggestions are welcome!  
For major changes, please open an issue first to discuss what you would like to change.

---

## 📝 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

---
