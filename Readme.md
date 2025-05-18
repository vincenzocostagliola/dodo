📝 Dodo – Todo List Manager
Dodo is a modern Android application built with Jetpack Compose that allows users to manage a todo list efficiently.
It supports creating, editing, and viewing tasks, and lets users customize the sorting logic via preferences.

✨ Features
🏠 Home Screen: Displays a list of todos sorted by user's selected preference (e.g., date, priority).

🔍 Details Screen: View, edit, or create a new todo entry.

⚙️ Settings Screen: Customize the way todos are sorted.

📦 Persistence: Local storage using Room database and SharedPreferences.

🛠 Tech Stack
Tool	Usage
Jetpack Compose	Declarative UI
Kotlin Coroutines	Asynchronous programming
Flow	Reactive data stream handling
Hilt	Dependency Injection
Room	Local database
SharedPreferences	User settings persistence
ThreeTenABP	Date/time handling
Kotlin Serialization	JSON serialization
Timber	Logging framework

📦 Project Structure
pgsql
Copy
Edit
dodo/
├── app/                 # Main app module (entry point, activity)
│   ├── src/
│   └── build.gradle.kts
│
├── build/               # Build outputs
│
├── data/                # Data layer shared across features
│   └── (repositories, models, etc.)
│
├── db/                  # Room database and DAOs
│
├── designsystem/        # Theme, typography, reusable UI components
│
├── features/            # Feature-based modularization
│   ├── home/            # Home screen: list of todos
│   ├── details/         # Todo details: view, create, edit
│   └── settings/        # Settings: sorting preferences
│
├── gradle/              # Gradle wrapper configuration
│
├── build.gradle.kts
├── settings.gradle.kts
└── local.properties


🧠 Architecture
The project follows a clean modular architecture:

Feature Modules (under features/) are self-contained and manage their own UI, logic, and DI setup.

Shared Modules like data/, db/, and designsystem/ are reused across features.

Uses unidirectional data flow with StateFlow and ViewModels for state management.

🚀 Getting Started
Clone the repo:

bash
Copy
Edit
git clone https://github.com/your-username/dodo.git
cd dodo
Open in Android Studio.

Run the app on an emulator or device.

📸 Screenshots
(Add screenshots here to showcase Home, Details, and Settings screens.)

🧪 Testing
Unit and integration tests are organized under each module’s test/ and androidTest/.

Future improvements: add more UI tests using Compose Testing APIs.

🛡️ License
MIT License.
See LICENSE for details.

