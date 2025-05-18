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

dodo/
├── app/ # Main app module (entry point, activity)
│ ├── src/
│ └── build.gradle.kts
│
├── build/ # Build outputs
│
├── data/ # Data layer shared across features
│ └── (repositories, models, DTOs, etc.)
│
├── db/ # Room database setup and DAOs
│
├── designsystem/ # Theme, typography, reusable UI components
│
├── features/ # Feature-based modules
│ ├── home/ # Shows sorted todo list
│ ├── details/ # View/edit/create todo
│ └── settings/ # Sorting preference
│
├── gradle/ # Gradle wrapper config
│
├── build.gradle.kts
├── settings.gradle.kts
└── local.properties

yaml
Copy
Edit

---

## 🧠 Architecture

The project follows a **modular and clean architecture**:

- **Feature Modules** (`features/home`, `details`, `settings`) are self-contained.
- **Shared Modules** (`data`, `db`, `designsystem`) are reused across the app.
- UI state is managed using `ViewModel` + `StateFlow` with **unidirectional data flow**.

---

## 🚀 Getting Started

1. **Clone the repo:**
   ```bash
   git clone https://github.com/your-username/dodo.git
   cd dodo
Open in Android Studio

Run the app on an emulator or real device

🧪 Testing
Unit and integration tests located under each module’s test/ and androidTest/

[TODO] Add Compose UI tests for interaction testing

📸 Screenshots
Coming soon... Add screenshots of Home, Details, and Settings.

🛡️ License
This project is licensed under the MIT License.
See the LICENSE file for more details.

markdown
Copy
Edit
