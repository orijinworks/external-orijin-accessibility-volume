# Accessibility Volume Controller

## Overview

This project provides an **Accessibility Volume Controller** for Android devices, allowing users to independently adjust the **STREAM\_ACCESSIBILITY** volume using an Accessibility Service. It integrates a **Compose UI slider** and a **ViewModel-backed accessibility service** to provide a seamless experience.

## Features

-  **Independent Accessibility Volume Control**
-  **Smooth Slider-Based Adjustment**
-  **Non-Blocking UI for Better Performance**
-  **Auto-Restart Accessibility Service If Needed**
-  **Debounced Volume Changes to Prevent Lag**
-  **Automatic Redirect to Accessibility Settings If Disabled**

## Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/orijinworks/external-orijin-accessibility-volume
   ```
2. Open the project in **Android Studio**.
3. Build and run the app on an **Android 8.0+ (API 26+)** device.

## Usage

1. **Enable Accessibility Service**

   - The app will detect if the **Accessibility Service** is disabled and prompt the user to enable it.
   - Navigate to:
     ```
     Settings → Accessibility → Accessibility Volume Controller
     ```
   - Toggle it ON.

2. **Adjust Volume with Slider**

   - Use the **on-screen slider** to change the accessibility volume.
   - Volume changes are applied with **a slight delay** to prevent jitter.

## Technical Details

###  Architecture

- **Jetpack Compose UI** for the volume slider.
- **ViewModel** for managing accessibility service interactions.
- **AccessibilityService API** for independent volume control.
- **Kotlin Coroutines** to handle async operations efficiently.

###  Key Components

- `` → Handles Accessibility Volume adjustments.
- `` → Manages volume updates and service status.
- `` → Detects and manages service state.
- `` → Compose UI for the volume slider.

## Troubleshooting

###  Accessibility Service Not Responding?

- Try disabling and re-enabling the service manually.
- Restart the app to **force a service reconnect**.

###  UI Freezes or Delays?

- Adjusted the **debounce delay** in `VolumeViewModel.kt`.


---

