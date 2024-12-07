# **PacePal**

### **A Unique Experience for Every Runner**

PacePal is a **multi-module running tracker app** designed to provide a seamless, feature-rich experience for runners. With its **offline-first capabilities**, **real-time tracking**, and **analytics dashboard**, PacePal ensures runners stay on track whether they’re online or off. Built with cutting-edge technologies and a scalable architecture, PacePal is a testament to modern Android development principles.

---

## **Features**

### **Run Tracking**
- **Real-time GPS-based run tracking** with live metrics like pace, distance, and elevation.
- **Background run tracking** using a foreground service.
- **Offline-first architecture** for data persistence, ensuring runs are never lost.
- **Google Maps SDK integration** for custom maps and route drawing.
- **Analytics dashboard** to review past runs and monitor performance.

### **Authentication**
- **Secure and user-friendly authentication system** with OAuth 2.0.
- **Token-based session management** with automatic refresh.
- Seamless onboarding experience to connect users with their data.

### **Future Enhancements**
#### **Dynamic Features**
- **Modularized dynamic features** that can be downloaded on demand, minimizing app size.
- **Google Play's dynamic feature delivery** to keep non-critical features optional.

#### **Wear OS Integration**
- Planned support for Wear OS devices, including:
  - **Health Services API** for step counting and heart rate monitoring.
  - Data sync between Wear OS and the main app for a cohesive experience.
  - Intuitive UI for quick access to running stats on your wrist.

---

## **Technical Highlights**

### **Architectural Principles**

#### **MVVM (Model-View-ViewModel)**
- Used across the app to separate concerns, ensure testability, and streamline UI state management.

#### **Multi-Module Architecture**
- **Decoupled modules** for scalability, faster builds, and maintainability.
- Adheres to **SOLID principles** with distinct modules for `run`, `auth`, and shared utilities.

#### **Offline-First Development**
- **Robust caching strategy** combining local Room database and Ktor-based remote sync.
- **Functional retry mechanisms** for syncing unsynced data with the server.

#### **Clean Architecture**
- **Domain-driven design** to separate core business logic from infrastructure details.
- **Repositories** to unify data sources and expose clean APIs to the app.

---

### **Libraries, Frameworks, and Tools Used**

#### **Languages**
- **Kotlin**: Primary programming language for all app components.
- **Kotlin Coroutines**: For asynchronous programming and managing concurrency.

#### **UI Framework**
- **Jetpack Compose**: Declarative UI toolkit for building responsive and modern UIs.
- **Material Design 3**: Ensures consistency with modern Android design standards.

#### **Networking**
- **Ktor**: Lightweight HTTP client for communicating with the backend.
- **Kotlinx Serialization**: For seamless JSON parsing and serialization.

#### **Database**
- **Room**: Local database for offline data persistence.
- **Type Converters**: Handle custom data types like `ZoneDateTime` and `Duration`.

#### **Dependency Injection**
- **Koin**: Lightweight and developer-friendly DI framework.

#### **Authentication**
- **OAuth 2.0**: Secure authentication and token-based session management.

#### **Maps**
- **Google Maps SDK**: For real-time route drawing and custom map views.

#### **Build System**
- **Gradle (Kotlin DSL)**: Modularized build scripts for efficient dependency management.
- **Version Catalogs**: Centralized dependency versions for consistency.
- **Convention Plugins**: Standardized build logic across modules.

---

## **Key Learnings and Accomplishments**
- **Designed and implemented a multi-module architecture**, significantly improving build times and maintainability.
- Built an **offline-first application** with a reliable sync strategy, providing uninterrupted user experience.
- Implemented **MVVM architecture** for clean separation of concerns and streamlined UI management.
- Mastered **Jetpack Compose** in a multi-module setup, delivering a modern and scalable UI.
- Integrated **dynamic feature modules**, reducing app size and providing an on-demand feature installation experience.
- Developed a robust **authentication system** using OAuth 2.0 for secure user login and session management.
- Streamlined builds with **version catalogs** and **convention plugins**, making the project highly maintainable.

---

## **Future Plans**

### **Dynamic Analytics Dashboard**
- Enhance the analytics feature with customizable insights and long-term trends tracking.

### **Wear OS Integration**
- Build a companion app for Wear OS devices, providing real-time metrics on users' wrists.

### **Advanced Sync Features**
- Improve offline-first functionality with granular sync controls and improved retry mechanisms.

---

## **About the Author**

PacePal was born from a personal inspiration. When my cousin recently started running, he struggled to find a running tracker app that was intuitive, offline-friendly, and catered to his needs. Most apps were either overly complex, required constant internet connectivity, or lacked key features like real-time metrics and reliable data syncing. 

Motivated by his experience, I set out to create **PacePal**—a robust, user-centric running tracker that prioritizes simplicity, offline-first functionality, and meaningful insights for runners.

What began as a way to help my cousin has grown into a project that showcases my technical expertise and passion for solving real-world problems. 

As a recent **Computer Science graduate** and enthusiastic Android developer, I thrive on building meaningful applications that prioritize user experience and technical excellence. I specialize in **Kotlin**, **Jetpack Compose**, and **REST APIs**, with a strong foundation in implementing modern architecture patterns like **MVVM** and **Clean Architecture**, guided by **SOLID principles**. With a focus on delivering high-quality, performant, and scalable applications, I excel at collaborating with cross-functional teams to bring innovative ideas to life.

My unique background, combined with an analytical mindset, attention to detail, and problem-solving skills, positions me to create applications that are not only functional but also resonate with users on a personal level.

