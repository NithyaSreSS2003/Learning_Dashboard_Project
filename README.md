# Learning_Dashboard_Project

## 1. Architecture

I chose **MVVM with a Repository pattern** to separate UI, business logic, and data access. Jetpack Compose handles the UI, ViewModels manage UI state using StateFlow, and the Repository coordinates remote and local data sources. This separation improves testability, maintainability, and scalability.

## 2. Offline Support

I use **Room** as the local database to persist course and lesson data. The Repository loads cached data when network access is unavailable and refreshes it when remote data is available. This allows users to access previously cached content offline. In a production application, I would also implement cache invalidation and background synchronization.

## 3. Security

In production, I would store authentication tokens using **Android Keystore-backed secure storage**, with encrypted token persistence where appropriate. I would avoid storing tokens in plain-text SharedPreferences or logs. Access tokens would be short-lived, refresh tokens would be handled securely, and authenticated API requests would use HTTPS.

## 4. Scale

For an application with 1 million users and hundreds of courses, I would:

1. **Backend and APIs:** Use scalable backend services, pagination, and efficient API responses.
2. **Caching:** Implement cache expiration, background synchronization, and appropriate local database indexing.
3. **Performance:** Use lazy lists, efficient Compose recomposition, and background coroutines for expensive operations.
4. **Reliability and monitoring:** Add crash reporting, analytics, structured logging, and performance monitoring.
5. **Testing and infrastructure:** Add automated unit, integration, and UI tests, along with CI/CD and load testing.

## 5. Second Platform – iOS/macOS

I developed this application for Android using Kotlin and Jetpack Compose. For iOS, I would implement the UI using **SwiftUI** and follow an MVVM-style architecture. I would use async/await for asynchronous operations, URLSession for API calls, and Core Data or SwiftData for local persistence. Authentication credentials would be stored securely in the Apple Keychain.

Both platforms would share the same backend APIs and business requirements while using platform-specific UI and storage technologies.

### Demo Video

A short demonstration of the Learning Dashboard application, showcasing the login flow, course dashboard, course details, lesson completion, and progress tracking.

**Demo Video:** [Demo video](./demo/

https://github.com/user-attachments/assets/26b9f4a8-fcbc-4a29-ae74-978f6f8a1beb

learning-dashboard-demo.mp4)

### How to Run

1. Download the APK using the link above.
2. Transfer it to an Android device.
3. Install the APK and open the application.
4. Explore the dashboard, courses, and lesson progress features.

> Note: This is a debug build intended for evaluation and testing.

