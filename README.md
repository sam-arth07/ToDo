# ToDo App

A modern task management application built with Jetpack Compose and Material3 design principles.

## 📱 About

ToDo is a clean, efficient Android application designed to help you manage your daily tasks with ease. Built using the latest Android technologies, it provides a smooth user experience with intuitive gestures and a modern UI.

## ✨ Features

-   **Task Management**: Create, update, and delete tasks effortlessly
-   **Priority Levels**: Assign different priority levels to your tasks (High, Medium, Low, None)
-   **Search Functionality**: Quickly find tasks using the search feature
-   **Sort Options**: Sort tasks by priority level or customized order
-   **Clean UI**: Modern Material3 design with smooth animations
-   **Dark Mode Support**: Seamless experience in both light and dark themes
-   **Edge-to-Edge Design**: Immersive user interface
-   **Splash Screen**: Elegant app entry experience

## 🏗️ Architecture & Tech Stack

This application is built using:

-   **Jetpack Compose**: Modern UI toolkit for building native Android UI
-   **Material3**: Latest Material Design components and theming system
-   **MVVM Architecture**: Clean separation of concerns with Model-View-ViewModel pattern
-   **Room Database**: Local storage of task information
-   **DataStore Preferences**: Store user preferences
-   **Kotlin Coroutines**: Handle asynchronous operations
-   **Hilt**: Dependency injection framework
-   **Kotlin Serialization**: Data serialization/deserialization
-   **Jetpack Navigation**: Single-activity application navigation

## 📷 Screenshots
![WhatsApp Image 2025-05-26 at 15 59 00_62e98171](https://github.com/user-attachments/assets/178b558e-e86b-428f-a153-f1a6df081548)
![WhatsApp Image 2025-05-26 at 15 59 02_cb14623a](https://github.com/user-attachments/assets/059a49f5-2fc0-4921-a37b-77b9407b8a55)
![WhatsApp Image 2025-05-26 at 15 59 02_2c8669e8](https://github.com/user-attachments/assets/7ce7f9f2-54d6-4377-9d25-a22a05882556)
![WhatsApp Image 2025-05-26 at 15 59 01_ddf663c6](https://github.com/user-attachments/assets/249647af-7af7-4f0c-a283-4cc4ef98b205)
![WhatsApp Image 2025-05-26 at 15 59 01_9f6022d1](https://github.com/user-attachments/assets/47e5ec7c-074a-4c59-b67c-74edb351cb2c)
![WhatsApp Image 2025-05-26 at 15 59 01_451b949c](https://github.com/user-attachments/assets/a84556cc-9805-412c-bf69-6920cd71be84)
![WhatsApp Image 2025-05-26 at 15 59 00_406d0cd2](https://github.com/user-attachments/assets/0c5a42b1-c772-4251-a340-f16134e1f05b)
![WhatsApp Image 2025-05-26 at 15 59 00_fe3fd41f](https://github.com/user-attachments/assets/ee033ba6-f575-4614-a9f5-708f53318631)




[_Note: App screenshots will be added here. This is a placeholder to remind you to add screenshots showing the main app screens: task list view, task detail view, task creation/editing, and other notable features._]

## 🚀 Getting Started

### Prerequisites

-   Android Studio Iguana | 2023.2.1 or newer
-   JDK 8
-   Android device or emulator running Android 7.1 (API level 25) or higher

### Installation

1. Clone this repository:

    ```bash
    git clone https://github.com/yourusername/todo-app.git
    ```

2. Open the project in Android Studio

3. Sync Gradle and build the project

4. Run on an emulator or physical device

## 🧪 Architecture

The app follows Clean Architecture principles and MVVM pattern:

```
com.example.todo/
├── components/           # Reusable UI components
├── data/
│   ├── models/          # Data entities/models
│   ├── repositories/    # Repository implementations
│   ├── ToDoDao.kt       # Data Access Object
│   └── ToDoDatabase.kt  # Room Database setup
├── di/                  # Dependency Injection modules
├── navigation/
│   ├── destinations/    # Navigation destinations
│   ├── Navigation.kt    # Navigation setup
│   └── Screens.kt       # Screen routes definition
├── ui/
│   ├── theme/           # App theme definitions
│   └── viewmodels/      # ViewModels for screens
├── util/                # Utility functions and constants
├── MainActivity.kt      # Entry point of the application
└── ToDoApplication.kt   # Application class
```

## 🛠️ Development

### Build Variants

-   **Debug**: Development build with debugging tools enabled
-   **Release**: Optimized build for distribution

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

-   Material 3 Design System
-   Jetpack Compose Community
-   Android Developer Documentation

---

_Built with ❤️ using Kotlin and Jetpack Compose_
