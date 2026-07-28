# 🌆 CityPulse

> **Visualize. Compare. Understand your city.**

CityPulse is a native Android application that monitors and visualizes key urban health parameters across cities. Built with **Java & Android SDK**, it parses real CSV datasets to deliver meaningful insights into infrastructure, air quality, population density, and more — empowering smarter urban decisions.

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)](https://developer.android.com)
[![Java](https://img.shields.io/badge/Language-Java-ED8B00?logo=openjdk&logoColor=white)](https://www.java.com)
[![API](https://img.shields.io/badge/Min%20SDK-24%20(Android%207.0)-blue)](https://developer.android.com/about/versions/nougat)
[![Build](https://img.shields.io/badge/Build-Gradle%208.2.1-02303A?logo=gradle)](https://gradle.org)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔐 **Auth Flow** | Splash → Login → Register screens |
| 🏠 **Home Dashboard** | City list with searchable RecyclerView |
| 📊 **City Dashboard** | Detailed health metrics per city |
| ⚖️ **City Comparison** | Side-by-side comparison of two cities |
| 👤 **Profile** | User profile management |
| ⚙️ **Settings** | App preferences |
| 📄 **CSV Integration** | Reads `cities_data.csv` from assets at runtime |

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| UI | XML Layouts + Material Design 3 |
| Components | RecyclerView, CardView, ConstraintLayout |
| Build | Gradle 8.2.1 (Kotlin DSL) |
| Min SDK | API 24 (Android 7.0 Nougat) |
| Target SDK | API 34 (Android 14) |
| Data Source | CSV file via Android Assets |

---

## 📂 Project Structure

```
CityPulse/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── assets/
│   │       │   └── cities_data.csv       # City health dataset
│   │       ├── java/com/example/cityhealth/
│   │       │   ├── MainActivity.java     # Splash / Login screen
│   │       │   ├── RegisterActivity.java # User registration
│   │       │   ├── HomeActivity.java     # City list + search
│   │       │   ├── DashboardActivity.java# Individual city metrics
│   │       │   ├── CompareActivity.java  # City comparison view
│   │       │   ├── ProfileActivity.java  # User profile
│   │       │   ├── SettingsActivity.java # App settings
│   │       │   ├── City.java             # City data model
│   │       │   ├── CityAdapter.java      # RecyclerView adapter
│   │       │   └── CSVReader.java        # CSV parser utility
│   │       ├── res/                      # Layouts, drawables, strings
│   │       └── AndroidManifest.xml
│   └── build.gradle.kts                  # App-level build config
├── build.gradle.kts                      # Project-level build config
├── settings.gradle.kts                   # Module settings
└── gradle.properties                     # Gradle JVM & project flags
```

---

## ⚙️ Getting Started

### Prerequisites

Make sure you have the following installed:

| Tool | Version | Download |
|---|---|---|
| Android Studio | Hedgehog (2023.1.1) or later | [Download](https://developer.android.com/studio) |
| JDK | 11 or later | Bundled with Android Studio |
| Android SDK | API 34 | Via SDK Manager in Android Studio |

> **Note:** Gradle and the Android SDK are managed automatically by Android Studio.

---

### 🖥️ Run on Android Studio (Recommended)

1. **Clone the repository**
   ```bash
   git clone https://github.com/invo-coder19/CityPulse.git
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Click **File → Open**
   - Select the `CityPulse` folder
   - Wait for **Gradle sync** to complete automatically

3. **Set up an Emulator or connect a Device**
   - **Emulator:** Go to `Device Manager → Create Virtual Device` → choose any Pixel profile with API 24+
   - **Physical Device:** Enable `Developer Options → USB Debugging` on your Android device

4. **Run the app**
   - Click the ▶️ **Run** button (Shift+F10)
   - Select your target emulator or device

---

### 🔧 Run via Command Line

You can also build and install the app without opening Android Studio.

**On Linux/macOS:**
```bash
# Clone and enter the project
git clone https://github.com/invo-coder19/CityPulse.git
cd CityPulse

# Build a debug APK
./gradlew assembleDebug

# Install directly on a connected device/emulator
./gradlew installDebug
```

**On Windows (PowerShell or Command Prompt):**
```powershell
# Clone and enter the project
git clone https://github.com/invo-coder19/CityPulse.git
cd CityPulse

# Build a debug APK
.\gradlew.bat assembleDebug

# Install directly on a connected device/emulator
.\gradlew.bat installDebug
```

The debug APK will be generated at:
```
app/build/outputs/apk/debug/app-debug.apk
```

> **Tip:** You can also drag-and-drop the `.apk` file onto a running Android emulator to install it.

---

### 🏗️ Build a Release APK

```powershell
# Windows
.\gradlew.bat assembleRelease

# Linux/macOS
./gradlew assembleRelease
```

> **Note:** Release builds require a signing keystore. See [Sign your app](https://developer.android.com/studio/publish/app-signing) for details.

---

## 🧪 Running Tests

```powershell
# Unit tests
.\gradlew.bat test

# Instrumented tests (requires a connected device/emulator)
.\gradlew.bat connectedAndroidTest
```

---

## 🗄️ Dataset

The app reads city data from `app/src/main/assets/cities_data.csv` at runtime using a custom `CSVReader` utility.

To add or update city data, simply edit `cities_data.csv` following the existing column schema — no code changes needed.

---

## 🤝 Contributing

Contributions are welcome! Here's how to get started:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m "feat: add your feature"`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a Pull Request

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

<div align="center">
  Made with ❤️ by <a href="https://github.com/invo-coder19">invo-coder19</a>
</div>
