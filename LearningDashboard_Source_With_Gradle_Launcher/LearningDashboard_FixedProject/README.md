# Learning Dashboard (Android / Kotlin / Jetpack Compose)

## Build on Windows
1. Open this folder in Android Studio and allow Android Studio to finish syncing.
2. Ensure Android SDK Platform 35 is installed (Tools → SDK Manager). Use JDK 17 for Gradle.
3. Open the Android Studio Terminal at the project root (the folder containing `settings.gradle.kts`).
4. Run:
   ```powershell
   .\gradlew.bat assembleDebug --no-daemon
   ```
   The first run downloads Gradle 8.9 if it is not already present, so it needs internet access.
5. The debug APK should be created at `app\build\outputs\apk\debug\app-debug.apk`.

## Demo login
Use any valid email address and a password with at least four characters. The app uses a mock/static course data source for the assignment; it does not connect to a production backend.

## Architecture
- Compose screens render UI state.
- ViewModels expose state with Kotlin Flow/StateFlow.
- Repository separates data access from UI.
- Room persists course data locally.
- Unit test covers progress calculation.

## Important
This project ZIP includes a lightweight Gradle launcher script because the original archive omitted the standard Gradle wrapper files. The launcher downloads Gradle 8.9 on first use. This environment has not built the Android project or produced a verified APK; run the build steps above and resolve any SDK/build errors before submitting.
