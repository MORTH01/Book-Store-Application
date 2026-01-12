# 📚 Bookstore App (Android)

A modern Android bookstore application built using **Kotlin**, **Jetpack Compose**, and **Room Database**.  
This project demonstrates clean Android architecture with local data persistence and a simple inventory management flow.

---

## 🚀 Features

- Add new books/items to inventory
- View stored inventory items
- Local data persistence using Room
- Clean MVVM-based architecture
- Fully written in Kotlin
- Modern UI using Jetpack Compose

---

## 🧱 Architecture

The project follows **MVVM (Model–View–ViewModel)** architecture:

- **UI Layer**: Jetpack Compose screens
- **ViewModel**: Manages UI state and business logic
- **Repository**: Single source of truth for data operations
- **Room Database**:
  - Entity
  - DAO
  - Database

---

## 🛠️ Tech Stack

- **Language**: Kotlin  
- **UI**: Jetpack Compose  
- **Database**: Room  
- **Architecture**: MVVM  
- **Build System**: Gradle (KTS)  
- **IDE**: Android Studio  

---

## 📂 Project Structure

Bookstore/
├── app/
│ ├── src/main/java/com/example/bookstore/
│ │ ├── InventoryItem.kt
│ │ ├── InventoryDao.kt
│ │ ├── InventoryDatabase.kt
│ │ ├── InventoryRepository.kt
│ │ ├── InventoryViewModel.kt
│ │ ├── MainActivity.kt
│ │ ├── MainScreen.kt
│ │ ├── AddEditItemScreen.kt
│ │ └── SplashScreen.kt
│ └── res/
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md


---

## ▶️ Getting Started

### Prerequisites
- Android Studio (latest stable)
- Android SDK
- Kotlin support enabled

### Steps to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/<your-username>/<repo-name>.git
2.Open the project in Android Studio
3.Let Gradle sync complete
4.Run the app on an emulator or physical device

## Database Details
1.Uses Room for local storage
2.Database initialized as a singleton
3.Includes fallback to destructive migration for schema changes

## Notes
1. local.properties and IDE-specific files are excluded via .gitignore
2. No API keys or sensitive data are committed

## Future Improvements
1. Search and filter inventory
2. Edit and delete items
3. UI theming and animations
4. Export inventory data
5. Unit and UI tests

## Author

Developed by Gouresh Subhash Vernekar.
